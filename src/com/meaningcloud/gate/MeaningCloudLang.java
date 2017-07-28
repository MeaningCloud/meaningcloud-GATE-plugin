package com.meaningcloud.gate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.meaningcloud.gate.common.Utils;
import com.meaningcloud.gate.domain.Language;
import com.meaningcloud.gate.response.LangResponse;

import gate.Annotation;
import gate.AnnotationSet;
import gate.DocumentContent;
import gate.Factory;
import gate.FeatureMap;
import gate.ProcessingResource;
import gate.creole.AbstractLanguageAnalyser;
import gate.creole.ExecutionException;
import gate.creole.metadata.CreoleParameter;
import gate.creole.metadata.CreoleResource;
import gate.creole.metadata.Optional;
import gate.creole.metadata.RunTime;
import gate.util.InvalidOffsetException;

/**
 * MeaningCloud Language Identification API Processing Resource for GATE.
 * <p>This class implements the required methods to implement a ProcessingResource.</p>
 * <p>It carries the analysis of a document and guess the language of its text.</p>
 * 
 * @author Carlos Abad
 * @version 1.0.0
 */
@SuppressWarnings("serial")
@CreoleResource(name = "MeaningCloud Language Identification",
    comment = "Meaningcloud Language Identification",
    helpURL = "https://www.meaningcloud.com/developer/language-identification/doc/2.0",
    icon = "/MeaningCloud.png")
public class MeaningCloudLang extends AbstractLanguageAnalyser implements ProcessingResource {

  private String url, key, selection, threshold;
  private String inputASName, outputASName;
  private List<String> annotationTypes = new ArrayList<String>();

  public String getUrl() {
    return url;
  }

  @CreoleParameter(comment = "API endpoint", defaultValue = "https://api.meaningcloud.com/lang-2.0")
  public void setUrl(String url) {
    this.url = url;
  }

  public String getKey() {
    return key;
  }

  @RunTime
  @CreoleParameter(
      comment = "Authorization key required for making requests to any of our web services")
  public void setKey(String key) {
    this.key = key;
  }

  public String getSelection() {
    return selection;
  }

  @RunTime
  @Optional
  @CreoleParameter(comment = "List of expected languages, separated by |")
  public void setSelection(String selection) {
    this.selection = selection;
  }

  public String getThreshold() {
    return threshold;
  }

  @RunTime
  @Optional
  @CreoleParameter(
      comment = "Language detection threshold as a percentage of similarity with respect to the top result")
  public void setThreshold(String threshold) {
    this.threshold = threshold;
  }

  public String getInputASName() {
    return inputASName;
  }

  @RunTime
  @Optional
  @CreoleParameter(comment = "AnnotationSet with the input content")
  public void setInputASName(String inputASName) {
    this.inputASName = inputASName;
  }

  public List<String> getAnnotationTypes() {
    return annotationTypes;
  }

  @RunTime
  @Optional
  @CreoleParameter(comment = "Filter content by this expression. It allows format: \n"
      + "Type.FeatureName  \n" + "or  \n" + "Type.FeatureName==FeatureValue  \n")
  public void setAnnotationTypes(List<String> annotationTypes) {
    this.annotationTypes = annotationTypes;
  }

  /**
   * This method carries the analysis of the document provided.
   * <p>It has two main different working methods:</p>
   * <ol>
   *    <li>When no <code>inputASName</code> parameter is provided, this PR analyze the whole
   *    document and guess its language (or most likely languages). The languages are returned 
   *    as features of the document itself.</li>
   *    <li>When <code>inputASName</code> and <code>annotationTypes</code> parameters are provided,
   *    this PR filter the categories of the input AnnotationSet according to the expression provided in 
   *    <code>annotationTypes</code>, and the language identification is carried only for the text included 
   *    in the filtered annotations. The languages are returned as features of this annotations.</li>
   * </ol>
   * 
   * @throws gate.creole.ExecutionException
   */
  @Override
  public void execute() throws ExecutionException {
    if (document == null)
      throw new ExecutionException("No Document Provided");

    if (key == null || key.trim().isEmpty())
      throw new ExecutionException("No API Key Provided");

    AnnotationSet inputAnnSet = null;
    if(inputASName != null)
      inputAnnSet = document.getAnnotations(inputASName);

    String text = "";
    DocumentContent content = document.getContent();

    if (inputAnnSet == null || inputAnnSet.isEmpty()) {
      text += content.toString();
      try {
        process(text, null);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } else {
      if (annotationTypes.size() == 0) {
        AnnotationSet filteredAS = document.getAnnotations(inputASName);
        Iterator<Annotation> it = gate.Utils.inDocumentOrder(filteredAS).iterator();
        while (it.hasNext()) {
          Annotation ann = it.next();
          try {
            text =
                content.getContent(ann.getStartNode().getOffset(), ann.getEndNode().getOffset())
                    .toString();
          } catch (InvalidOffsetException ex) {
            Logger.getLogger(MeaningCloudLang.class.getName()).log(Level.SEVERE, null, ex);
          }
          try {
            process(text, ann);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      } else {
        for (String inputAnnExpr : annotationTypes) {
          AnnotationSet filteredAS = Utils.getFilteredAS(inputAnnSet, inputAnnExpr);
          Iterator<Annotation> it = gate.Utils.inDocumentOrder(filteredAS).iterator();
          while (it.hasNext()) {
            Annotation ann = it.next();
            try {
              text =
                  content.getContent(ann.getStartNode().getOffset(), ann.getEndNode().getOffset())
                      .toString();
            } catch (InvalidOffsetException ex) {
              Logger.getLogger(MeaningCloudLang.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
              process(text, ann);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
      }
    }
  }

  private void process(String text, Annotation inputAnn) throws InterruptedException {
    String type = "";
    if (inputAnn != null)
      type = inputAnn.getType();

    try {
      HttpResponse<JsonNode> jsonResponse =
          Unirest.post(getUrl()).header("Content-Type", "application/x-www-form-urlencoded")
              .field("key", getKey()).field("src", "gate_3.0").field("txt", text)
              .field("selection", getSelection()).field("threshold", getThreshold()).asJson();

      Gson gson = new Gson();
      LangResponse langResponse =
          gson.fromJson(jsonResponse.getBody().toString(), LangResponse.class);

      if (!langResponse.getStatus().getCode().equals("0")) {
        if (langResponse.getStatus().getCode().equals("104")) {
          Thread.sleep(500);
          process(text, inputAnn);
          return;
        }
        Logger.getLogger(MeaningCloudLang.class.getName())
            .severe("API ERROR: " + langResponse.getStatus().getCode() + " - "
                + langResponse.getStatus().getMsg() + " trying to analyze document "
                + document.getName());
      } else {
        setDocFeatures(langResponse, inputAnn);
      }
    } catch (UnirestException e) {
      e.printStackTrace();
    }
  }

  private void setDocFeatures(LangResponse langResponse, Annotation inputAnn) {
    FeatureMap fm = Factory.newFeatureMap();
    List<String> langs = new ArrayList<String>();
    List<String> names = new ArrayList<String>();
    List<String> relevances = new ArrayList<String>();

    List<Language> languages = langResponse.getLanguages();
    Iterator<Language> it = languages.iterator();
    while (it.hasNext()) {
      Language language = it.next();
      langs.add(language.getLanguage());
      names.add(language.getName());
      relevances.add(language.getRelevance());
    }
    fm.put("language", langs);
    fm.put("language_name", names);
    fm.put("language_relevance", relevances);

    if (inputAnn != null) {
      FeatureMap fmAnn = inputAnn.getFeatures();
      fmAnn.putAll(fm);
    } else {
      FeatureMap fmDoc = document.getFeatures();
      fmDoc.putAll(fm);
    }
  }
} // class MeaningCloudLang
