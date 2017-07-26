package com.meaningcloud.gate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.meaningcloud.gate.common.Utils;
import com.meaningcloud.gate.domain.PolarityTerm;
import com.meaningcloud.gate.domain.Segment;
import com.meaningcloud.gate.domain.Sentence;
import com.meaningcloud.gate.domain.SentimentedTopic;
import com.meaningcloud.gate.param.DisambiguationType;
import com.meaningcloud.gate.param.RelaxedTypography;
import com.meaningcloud.gate.param.SemanticDisambiguationGrouping;
import com.meaningcloud.gate.response.SentimentResponse;

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
 * MeaningCloud Sentiment Analysis API Processing Resource for GATE.
 * <p>This class implements the required methods to implement a ProcessingResource.</p>
 * <p>It carries the analysis of a document and provide document and aspect level sentiment
 * of its text. It also extract named entities and concepts and identifies the terms that
 * provide polarity to the text.</p>
 * 
 * @author Carlos Abad
 * @version 1.0.0
 */
@SuppressWarnings("serial")
@CreoleResource(name = "MeaningCloud Sentiment analysis", comment = "Meaningcloud Sentiment Analysis",
    helpURL = "https://www.meaningcloud.com/developer/sentiment-analysis/doc/2.1",
    icon = "/MeaningCloud.png")
public class MeaningCloudSentiment extends AbstractLanguageAnalyser implements ProcessingResource {
  
  private String url, key, lang, ilang, model, disambiguationContext, outputASName;
  private List<String> userDictionaries = new ArrayList<String>();
  private Boolean verbose, expandGlobalPolarity, unknownWords;
  private RelaxedTypography relaxedTypography;
  private DisambiguationType disambiguationType;
  private SemanticDisambiguationGrouping semanticDisambiguationGrouping;
  
  public String getUrl() {
    return url;
  }

  @CreoleParameter(comment = "API endpoint",
      defaultValue = "https://api.meaningcloud.com/sentiment-2.1")
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

  public String getLang() {
    return lang;
  }

  @RunTime
  @CreoleParameter(defaultValue = "en",
      comment = "It specifies the language in which the text must be analyzed.")
  public void setLang(String lang) {
    this.lang = lang;
  }

  public String getIlang() {
    return ilang;
  }

  @RunTime
  @Optional
  @CreoleParameter(defaultValue = "",
      comment = "It specifies the languages in which the values returned will appear (in the cases where they are known).")
  public void setIlang(String ilang) {
    this.ilang = ilang;
  }

  public String getModel() {
    return model;
  }

  @RunTime
  @Optional
  @CreoleParameter(defaultValue = "general", comment = "Sentiment model chosen.")
  public void setModel(String model) {
    this.model = model;
  }

  public String getDisambiguationContext() {
    return disambiguationContext;
  }

  @RunTime
  @Optional
  @CreoleParameter(defaultValue = "", comment = "Disambiguation context. Context prioritization for entity semantic disambiguation.")
  public void setDisambiguationContext(String disambiguationContext) {
    this.disambiguationContext = disambiguationContext;
  }

  public String getOutputASName() {
    return outputASName;
  }

  @RunTime
  @Optional
  @CreoleParameter(comment = "Output Annotation Set", defaultValue = "MeaningCloud")
  public void setOutputASName(String outputASName) {
    this.outputASName = outputASName;
  }

  public List<String> getUserDictionaries() {
    return userDictionaries;
  }

  @Optional
  @RunTime
  @CreoleParameter(comment = "Name of the User defined Dictionaries to be used")
  public void setUserDictionaries(List<String> userDictionaries) {
    this.userDictionaries = userDictionaries;
  }

  public Boolean getVerbose() {
    return verbose;
  }

  @RunTime
  @Optional
  @CreoleParameter(comment = "Verbose mode. When active, it shows additional information about the sentiment analysis specifically, it shows the changes applied to the basic polarity of the different polarity terms detected.",
      defaultValue = "false")
  public void setVerbose(Boolean verbose) {
    this.verbose = verbose;
  }

  public Boolean getExpandGlobalPolarity() {
    return expandGlobalPolarity;
  }

  @RunTime
  @Optional
  @CreoleParameter(comment = "Expand global polarity. This mode allows you to choose between two different algorithms for the polarity detection of entities and concepts. Enabling the parameter gives less weight to the syntactic relationships, so it's recommended for short texts with unreliable typography.",
      defaultValue = "false")
  public void setExpandGlobalPolarity(Boolean expandGlobalPolarity) {
    this.expandGlobalPolarity = expandGlobalPolarity;
  }

  public Boolean getUnknownWords() {
    return unknownWords;
  }

  @RunTime
  @Optional
  @CreoleParameter(defaultValue = "false",
      comment = "Deal with unknown words. This feature adds a stage to the topic extraction in which the engine, much like a spellchecker, tries to find a suitable analysis to the unknown words resulted from the initial analysis assignment. It is specially useful to decrease the impact typos have in text analyses.")
  public void setUnknownWords(Boolean unknownWords) {
    this.unknownWords = unknownWords;
  }

  public RelaxedTypography getRelaxedTypography() {
    return relaxedTypography;
  }

  @RunTime
  @Optional
  @CreoleParameter(comment = "Deal with unknown words. This feature adds a stage to the topic extraction in which the engine, much like a spellchecker, tries to find a suitable analysis to the unknown words resulted from the initial analysis assignment. It is specially useful to decrease the impact typos have in text analyses.",
      defaultValue = "DISABLED")
  public void setRelaxedTypography(RelaxedTypography relaxedTypography) {
    this.relaxedTypography = relaxedTypography;
  }

  public DisambiguationType getDisambiguationType() {
    return disambiguationType;
  }

  @RunTime
  @Optional
  @CreoleParameter(comment = "Type of disambiguation applied. It is accumulative, that is, the semantic disambiguation mode will also include morphosyntactic disambiguation.",
      defaultValue = "SEMANTIC_DISAMBIGUATION")
  public void setDisambiguationType(DisambiguationType disambiguationType) {
    this.disambiguationType = disambiguationType;
  }

  public SemanticDisambiguationGrouping getSemanticDisambiguationGrouping() {
    return semanticDisambiguationGrouping;
  }

  @RunTime
  @Optional
  @CreoleParameter(comment = "Semantic disambiguation grouping. This parameter will only apply when semantic disambiguation is activated.",
      defaultValue = "INTERSECTION_BY_TYPE_SMALLEST_LOCATION")
  public void setSemanticDisambiguationGrouping(
      SemanticDisambiguationGrouping semanticDisambiguationGrouping) {
    this.semanticDisambiguationGrouping = semanticDisambiguationGrouping;
  }
  
  /**
   * This method carries the analysis of the document provided.
   * <p>This PR analyze the whole document and tag its text with different annotations,
   * as described in the 
   * <a heref="https://www.meaningcloud.com/developer/sentiment-analysis/doc/2.1/response">documentation</a></p>
   *  
   * @throws gate.creole.ExecutionException
   */
  @Override
  public void execute() throws ExecutionException {
    if (document == null)
      throw new ExecutionException("No Document Provided");

    if (key == null || key.trim().isEmpty())
      throw new ExecutionException("No API Key Provided");
    
    AnnotationSet outputAnnSet = document.getAnnotations(outputASName);
    
    DocumentContent content = document.getContent();
    String text = content.toString();
    
    try {
      process(text, outputAnnSet);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NumberFormatException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvalidOffsetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  private void process(String text, AnnotationSet outputAnnSet) throws InterruptedException, NumberFormatException, InvalidOffsetException {
    try {
      HttpResponse<JsonNode> jsonResponse =
          Unirest.post(getUrl()).header("Content-Type", "application/x-www-form-urlencoded")
              .field("key", getKey()).field("src", "gate_3.0").field("lang", getLang())
              .field("ilang", getIlang()).field("txt", text)
              .field("verbose", Utils.boolTransform(getVerbose()))
              .field("model", getModel())
              .field("egp", Utils.boolTransform(getExpandGlobalPolarity()))
              .field("uw", Utils.boolTransform(getUnknownWords()))
              .field("rt", Utils.translateRT(getRelaxedTypography()))
              .field("ud", Utils.translateUD(getUserDictionaries()))
              .field("dm", Utils.translateDM(getDisambiguationType()))
              .field("sdg", Utils.translateSDG(getSemanticDisambiguationGrouping()))
              .field("cont", getDisambiguationContext()).asJson();
      Gson gson = new Gson();
      SentimentResponse sentimentResponse =
          gson.fromJson(jsonResponse.getBody().toString(), SentimentResponse.class);
      
      if (!sentimentResponse.getStatus().getCode().equals("0")) {
        if (sentimentResponse.getStatus().getCode().equals("104")) {
          Thread.sleep(500);
          process(text, outputAnnSet);
          return;
        }
        Logger.getLogger(MeaningCloudLang.class.getName())
            .severe("API ERROR: " + sentimentResponse.getStatus().getCode() + " - "
                + sentimentResponse.getStatus().getMsg() + " trying to analyze document "
                + document.getName());
      } else {
        setDocAnnotationsAndFeatures(sentimentResponse, outputAnnSet);
      }
    } catch (UnirestException e) {
      e.printStackTrace();
    }
  }
  
  private void setDocAnnotationsAndFeatures(SentimentResponse sentimentResponse, AnnotationSet outputAnnSet) throws NumberFormatException, InvalidOffsetException {
    FeatureMap docFeatures = Factory.newFeatureMap();
    docFeatures.put("model", sentimentResponse.getModel());
    docFeatures.put("score_tag", sentimentResponse.getScoreTag());
    docFeatures.put("agreement", sentimentResponse.getAgreement());
    docFeatures.put("subjectivity", sentimentResponse.getSubjectivity());
    docFeatures.put("confidence", sentimentResponse.getConfidence());
    docFeatures.put("irony", sentimentResponse.getIrony());
    
    FeatureMap fmDoc = document.getFeatures();
    fmDoc.putAll(docFeatures);
    
    if (!sentimentResponse.getSentimentedEntities().isEmpty()) {
      setAnnotationsForSentimentedTopics(sentimentResponse.getSentimentedEntities(), outputAnnSet, "SentimentedEntity");
    }
    if (!sentimentResponse.getSentimentedConcepts().isEmpty()) {
      setAnnotationsForSentimentedTopics(sentimentResponse.getSentimentedConcepts(), outputAnnSet, "SentimentedConcept");
    }
    if (!sentimentResponse.getSentences().isEmpty()) {
      for (Sentence sentence : sentimentResponse.getSentences()) {
        FeatureMap features = Factory.newFeatureMap();
        features.put("text", sentence.getText());
        features.put("beginning_of_paragraph", sentence.getBop());
        features.put("confidence", sentence.getConfidence());
        features.put("score_tag", sentence.getScoreTag());
        features.put("agreement", sentence.getAgreement());
        outputAnnSet.add(Long.parseLong(sentence.getInip()), Long.parseLong(sentence.getEndp()) + 1, "Sentence", features);
        if (!sentence.getSegments().isEmpty()) {
          setAnnotationsForSegments(sentence.getSegments(), outputAnnSet);
        }
      }
    }
  }
  
  private void setAnnotationsForSentimentedTopics(List<SentimentedTopic> topics, AnnotationSet outputAnnSet, String topicType) throws NumberFormatException, InvalidOffsetException {
    for (SentimentedTopic topic : topics) {
      if (topic.getInip() != null && topic.getEndp() != null) {
        FeatureMap features = Factory.newFeatureMap();
        features.put("form", topic.getForm());
        features.put("id", topic.getId());
        if (topic.getVariant() != null)
          features.put("variant", topic.getVariant());
        features.put("type", topic.getType());
        if (topic.getScoreTag() != null)
          features.put("score_tag", topic.getScoreTag());
        outputAnnSet.add(Long.parseLong(topic.getInip()), Long.parseLong(topic.getEndp()) + 1, topicType, features);
      }
    }
  }
  
  private void setAnnotationsForSegments(List<Segment> segments, AnnotationSet outputAnnSet) throws NumberFormatException, InvalidOffsetException {
    for (Segment segment : segments) {
      FeatureMap features = Factory.newFeatureMap();
      features.put("text", segment.getText());
      features.put("type", segment.getSegmentType());
      features.put("score_tag", segment.getScoreTag());
      features.put("confidence", segment.getConfidence());
      features.put("agreement", segment.getAgreement());
      if (!segment.getPolarityTerms().isEmpty()) {
        setAnnotationsForPolarityTerms(segment.getPolarityTerms(), outputAnnSet);
      }
      if (segment.getSegments() != null && !segment.getSegments().isEmpty()) {
        setAnnotationsForSegments(segment.getSegments(), outputAnnSet);
      }
      if (segment.getSentimentedEntities() != null && !segment.getSentimentedEntities().isEmpty()) {
        setAnnotationsForSentimentedTopics(segment.getSentimentedEntities(), outputAnnSet, "SentimentedEntity");
      }
      if (segment.getSentimentedConcepts() != null && !segment.getSentimentedConcepts().isEmpty()) {
        setAnnotationsForSentimentedTopics(segment.getSentimentedConcepts(), outputAnnSet, "SentimentedConcept");
      }
      outputAnnSet.add(Long.parseLong(segment.getInip()), Long.parseLong(segment.getEndp()) + 1, "Segment", features);
    }
  }
  
  private void setAnnotationsForPolarityTerms(List<PolarityTerm> terms, AnnotationSet outputAnnSet) throws NumberFormatException, InvalidOffsetException {
    for (PolarityTerm term : terms) {
      FeatureMap features = Factory.newFeatureMap();
      features.put("text", term.getText());
      if(term.getTagStack() != null)
        features.put("tag_stack", term.getTagStack());
      features.put("confidence", term.getConfidence());
      features.put("score_tag", term.getScoreTag());
      if (term.getSentimentedEntities() != null && !term.getSentimentedEntities().isEmpty()) {
        setAnnotationsForSentimentedTopics(term.getSentimentedEntities(), outputAnnSet, "SentimentedEntity");
      }
      if (term.getSentimentedConcepts() != null && !term.getSentimentedConcepts().isEmpty()) {
        setAnnotationsForSentimentedTopics(term.getSentimentedConcepts(), outputAnnSet, "SentimentedConcept");
      }
      outputAnnSet.add(Long.parseLong(term.getInip()), Long.parseLong(term.getEndp()) + 1, "PolarityTerm", features);
    }
  }
}
