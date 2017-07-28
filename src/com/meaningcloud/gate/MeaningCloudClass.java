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
import com.meaningcloud.gate.domain.Category;
import com.meaningcloud.gate.domain.Category.Term;
import com.meaningcloud.gate.response.ClassResponse;

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
 * MeaningCloud Text Classification API Processing Resource for GATE.
 * <p>This class implements the required methods to implement a ProcessingResource.</p>
 * <p>It carries the analysis of a document and classifies its text according to an 
 * input classification model, assigning one or more categories to the text provided.</p>
 * 
 * @author Carlos Abad
 * @version 1.0.0
 */
@SuppressWarnings("serial")
@CreoleResource(name = "MeaningCloud Text Classification",
    comment = "Meaningcloud Text Classification",
    helpURL = "https://www.meaningcloud.com/developer/text-classification/doc/1.1",
    icon = "/MeaningCloud.png")
public class MeaningCloudClass extends AbstractLanguageAnalyser implements ProcessingResource {
  
  private String url, key, title, model, categories, inputASName;
  private List<String> annotationTypes = new ArrayList<String>();
  private Boolean verbose;

  public String getUrl() {
    return url;
  }

  @CreoleParameter(comment = "API endpoint",
      defaultValue = "https://api.meaningcloud.com/class-1.1")
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

  public String getTitle() {
    return title;
  }

  @RunTime
  @Optional
  @CreoleParameter(comment = "Descriptive title of the content")
  public void setTitle(String title) {
    this.title = title;
  }

  public String getModel() {
    return model;
  }

  @RunTime
  @CreoleParameter(defaultValue = "IPTC_en",
      comment = "Classification model to use. It will define into which categories the text may be classified.")
  public void setModel(String model) {
    this.model = model;
  }

  public String getCategories() {
    return categories;
  }

  @RunTime
  @Optional
  @CreoleParameter(
      comment = "List of prefixes of categories to which the classification is limited. Each value will be separated by '|'. All the categories that do not start with any of the prefixes specified in the list will not be taken account in the classification.")
  public void setCategories(String categories) {
    this.categories = categories;
  }

  public Boolean getVerbose() {
    return verbose;
  }

  @RunTime
  @Optional
  @CreoleParameter(defaultValue = "false",
      comment = "Verbose mode. Shows additional information about the classification.")
  public void setVerbose(Boolean verbose) {
    this.verbose = verbose;
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
   *    document and categorize the text according to the model provided. The categories are returned
   *    as features of the document itself.</li>
   *    <li>When <code>inputASName</code> and <code>annotationTypes</code> parameters are provided,
   *    this PR filter the categories of the input AnnotationSet according to the expression provided in 
   *    <code>annotationTypes</code>, and the categorization is carried only for the text included 
   *    in the filtered annotations. The categories are returned as features of this annotations.</li>
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
    try {
      HttpResponse<JsonNode> jsonResponse = Unirest.post(getUrl())
          .header("Content-Type", "application/x-www-form-urlencoded").field("key", getKey())
          .field("src", "gate_3.0").field("verbose", Utils.boolTransform(getVerbose()))
          .field("title", getTitle()).field("txt", text).field("model", getModel())
          .field("categories", getCategories()).asJson();

      Gson gson = new Gson();
      ClassResponse classResponse =
          gson.fromJson(jsonResponse.getBody().toString(), ClassResponse.class);

      if (!classResponse.getStatus().getCode().equals("0")) {
        if (classResponse.getStatus().getCode().equals("104")) {
          Thread.sleep(500);
          process(text, inputAnn);
          return;
        }
        Logger.getLogger(MeaningCloudLang.class.getName())
            .severe("API ERROR: " + classResponse.getStatus().getCode() + " - "
                + classResponse.getStatus().getMsg() + " trying to analyze document "
                + document.getName());
      } else {
        setDocFeatures(classResponse, inputAnn);
      }
    } catch (UnirestException e) {
      e.printStackTrace();
    }
  }

  private void setDocFeatures(ClassResponse classResponse, Annotation inputAnn) {
    List<Category> categories = classResponse.getCategories();
    if (categories.size() > 0) {
      FeatureMap fm = Factory.newFeatureMap();
      List<String> cat_code = new ArrayList<String>();
      List<String> cat_label = new ArrayList<String>();
      List<String> cat_relevance = new ArrayList<String>();
      List<String> cat_abs_relevance = new ArrayList<String>();
      List<ArrayList<String>> cat_term_forms = new ArrayList<ArrayList<String>>();
      List<ArrayList<String>> cat_term_abs_relevance = new ArrayList<ArrayList<String>>();

      for (Category cat : categories) {
        cat_code.add((cat.getCode() != null ? cat.getCode() : ""));
        cat_label.add((cat.getLabel() != null ? cat.getLabel() : ""));
        cat_relevance.add((cat.getRelevance() != null ? cat.getRelevance() : ""));
        cat_abs_relevance.add((cat.getAbsRelevance() != null ? cat.getAbsRelevance() : ""));
        if (cat.getTerms() != null && cat.getTerms().size() > 0) {
          ArrayList<String> forms = new ArrayList<String>();
          ArrayList<String> relevances = new ArrayList<String>();
          for (Term term : cat.getTerms()) {
            forms.add((term.getForm() == null || term.getForm().isEmpty()) ? " " : term.getForm());
            relevances
                .add((term.getAbsRelevance() == null || term.getAbsRelevance().isEmpty()) ? " "
                    : term.getAbsRelevance());
          }
          cat_term_forms.add(forms);
          cat_term_abs_relevance.add(relevances);
        }
      }
      fm.put("category_code", cat_code);
      fm.put("category_label", cat_label);
      fm.put("category_relevance", cat_relevance);
      fm.put("category_abs_relevance", cat_abs_relevance);
      fm.put("category_terms_form", cat_term_forms);
      fm.put("category_terms_abs_relevance", cat_term_abs_relevance);

      if (inputAnn != null) {
        FeatureMap fmAnn = inputAnn.getFeatures();
        fmAnn.putAll(fm);
      } else {
        FeatureMap fmDoc = document.getFeatures();
        fmDoc.putAll(fm);
      }
    }
  }
}
