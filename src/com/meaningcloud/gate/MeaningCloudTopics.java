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
import com.meaningcloud.gate.domain.MoneyExpression;
import com.meaningcloud.gate.domain.OtherExpression;
import com.meaningcloud.gate.domain.QuantityExpression;
import com.meaningcloud.gate.domain.Quotation;
import com.meaningcloud.gate.domain.Relation;
import com.meaningcloud.gate.domain.Relation.Complement;
import com.meaningcloud.gate.domain.TimeExpression;
import com.meaningcloud.gate.domain.Topic;
import com.meaningcloud.gate.domain.Topic.Semgeo;
import com.meaningcloud.gate.domain.Topic.Semrefer;
import com.meaningcloud.gate.domain.Topic.Semtheme;
import com.meaningcloud.gate.domain.Topic.Standard;
import com.meaningcloud.gate.domain.Topic.Variant;
import com.meaningcloud.gate.param.DisambiguationType;
import com.meaningcloud.gate.param.SemanticDisambiguationGrouping;
import com.meaningcloud.gate.response.TopicsResponse;

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
 * MeaningCloud Topics Extraction API Processing Resource for GATE.
 * <p>This class implements the required methods to implement a ProcessingResource.</p>
 * <p>It carries the analysis of a document and extract named entities, concepts
 * and different types of expressions from its text.</p>
 * 
 * @author Carlos Abad
 * @version 1.0.0
 */
@SuppressWarnings("serial")
@CreoleResource(name = "MeaningCloud Topics Extraction", comment = "Meaningcloud Topics Extraction",
    helpURL = "https://www.meaningcloud.com/developer/topics-extraction/doc/2.0",
    icon = "/MeaningCloud.png")
public class MeaningCloudTopics extends AbstractLanguageAnalyser implements ProcessingResource {

  private String url, key, lang, ilang, topicTypes, disambiguationContext, outputASName;
  private List<String> userDictionaries = new ArrayList<String>();
  private Boolean unknownWords, relaxedTypography, subtopics;
  private DisambiguationType disambiguationType;
  private SemanticDisambiguationGrouping semanticDisambiguationGrouping;

  public String getUrl() {
    return url;
  }

  @CreoleParameter(comment = "API endpoint",
      defaultValue = "https://api.meaningcloud.com/topics-2.0")
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

  public String getTopicTypes() {
    return topicTypes;
  }

  @RunTime
  @Optional
  @CreoleParameter(defaultValue = "a", comment = "Topic Types to detect")
  public void setTopicTypes(String topicTypes) {
    this.topicTypes = topicTypes;
  }

  public String getOutputASName() {
    return outputASName;
  }

  @RunTime
  @Optional
  @CreoleParameter(comment = "Output Annotation Set", defaultValue = "MeaningCloud Topics")
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

  public Boolean getUnknownWords() {
    return unknownWords;
  }

  @RunTime
  @Optional
  @CreoleParameter(defaultValue = "false",
      comment = "This feature adds a stage to the topic extraction in which the engine, much like a spellchecker, tries to find a suitable analysis to the unknown words resulted from the initial analysis assignment. It is specially useful to decrease the impact typos have in text analyses.")
  public void setUnknownWords(Boolean unknownWords) {
    this.unknownWords = unknownWords;
  }

  public Boolean getRelaxedTypography() {
    return relaxedTypography;
  }

  @RunTime
  @Optional
  @CreoleParameter(defaultValue = "false",
      comment = "This paramater indicates how reliable the text (as far as spelling, typography, etc. are concerned) to analyze is, and influences how strict the engine will be when it comes to take these factors into account in the topic extraction.")
  public void setRelaxedTypography(Boolean relaxedTypography) {
    this.relaxedTypography = relaxedTypography;
  }

  public Boolean getSubtopics() {
    return subtopics;
  }

  @RunTime
  @Optional
  @CreoleParameter(defaultValue = "false", comment = "This parameter will indicate if subtopics are to be shown.")
  public void setSubtopics(Boolean subtopics) {
    this.subtopics = subtopics;
  }

  public DisambiguationType getDisambiguationType() {
    return disambiguationType;
  }

  @RunTime
  @CreoleParameter(defaultValue = "SEMANTIC_DISAMBIGUATION", comment = "Disambiguation type applied.")
  public void setDisambiguationType(DisambiguationType disambiguationType) {
    this.disambiguationType = disambiguationType;
  }

  public SemanticDisambiguationGrouping getSemanticDisambiguationGrouping() {
    return semanticDisambiguationGrouping;
  }

  @RunTime
  @CreoleParameter(defaultValue = "INTERSECTION_BY_TYPE_SMALLEST_LOCATION", comment = "Semantic disambiguation grouping applied.")
  public void setSemanticDisambiguationGrouping(
      SemanticDisambiguationGrouping semanticDisambiguationGrouping) {
    this.semanticDisambiguationGrouping = semanticDisambiguationGrouping;
  }

  public String getDisambiguationContext() {
    return disambiguationContext;
  }

  @Optional
  @RunTime
  @CreoleParameter(comment = "Context prioritization for entity semantic disambiguation.")
  public void setDisambiguationContext(String disambiguationContext) {
    this.disambiguationContext = disambiguationContext;
  }

  /**
   * This method carries the analysis of the document provided.
   * <p>This PR analyze the whole document and tag its text with different annotations,
   * as described in the 
   * <a heref="https://www.meaningcloud.com/developer/topics-extraction/doc/2.0/response">documentation</a></p>
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
      try {
        process(text, outputAnnSet);
      } catch (NumberFormatException e) {
        e.printStackTrace();
      } catch (InvalidOffsetException e) {
        e.printStackTrace();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * This method makes a request to the Topics Extraction API, maps it to a 
   * {@link com.meaningcloud.gate.response.TopicsResponse} object and starts
   * the annotation process.
   * 
   * @param text text to analyze
   * @param outputAnnSet {@link gate.AnnotationSet} in which the new annotations will be added
   * @throws InterruptedException
   * @throws NumberFormatException
   * @throws InvalidOffsetException
   */
  private void process(String text, AnnotationSet outputAnnSet) throws InterruptedException, NumberFormatException, InvalidOffsetException {
    try {
      HttpResponse<JsonNode> jsonResponse =
          Unirest.post(getUrl()).header("Content-Type", "application/x-www-form-urlencoded")
              .field("key", getKey()).field("src", "gate_3.0").field("lang", getLang())
              .field("ilang", getIlang()).field("txt", text).field("tt", getTopicTypes())
              .field("uw", Utils.boolTransform(getUnknownWords()))
              .field("rt", Utils.boolTransform(getRelaxedTypography()))
              .field("ud", Utils.translateUD(getUserDictionaries()))
              .field("st", Utils.boolTransform(getSubtopics()))
              .field("dm", Utils.translateDM(getDisambiguationType()))
              .field("sdg", Utils.translateSDG(getSemanticDisambiguationGrouping()))
              .field("cont", getDisambiguationContext()).asJson();
      Gson gson = new Gson();
      TopicsResponse topicsResponse =
          gson.fromJson(jsonResponse.getBody().toString(), TopicsResponse.class);
      
      if (!topicsResponse.getStatus().getCode().equals("0")) {
        if (topicsResponse.getStatus().getCode().equals("104")) {
          Thread.sleep(500);
          process(text, outputAnnSet);
          return;
        }
        Logger.getLogger(MeaningCloudLang.class.getName())
            .severe("API ERROR: " + topicsResponse.getStatus().getCode() + " - "
                + topicsResponse.getStatus().getMsg() + " trying to analyze document "
                + document.getName());
      } else {
        setDocAnnotations(topicsResponse, outputAnnSet);
      }
    } catch (UnirestException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * This method sets the annotations and features with the analysis returned by the Topics Extraction API.
   * All first level response objects are annotated, and inner information is included as features
   * for these annotations.
   * 
   * @param topicsResponse response from the Topics Extraction API
   * @param outputAnnSet AnnotationSet in which the new annotations will be included
   * @throws NumberFormatException
   * @throws InvalidOffsetException
   */
  private void setDocAnnotations(TopicsResponse topicsResponse, AnnotationSet outputAnnSet) throws NumberFormatException, InvalidOffsetException {
    if (topicsResponse.getEntities() != null && !topicsResponse.getEntities().isEmpty()) {
      setTopicsAnnotationsAndFeatures(topicsResponse.getEntities(), outputAnnSet, "Entity");
    }
    if (topicsResponse.getConcepts() != null && !topicsResponse.getConcepts().isEmpty()) {
      setTopicsAnnotationsAndFeatures(topicsResponse.getConcepts(), outputAnnSet, "Concept");
    }
    if (topicsResponse.getTimeExpressions() != null && !topicsResponse.getTimeExpressions().isEmpty()) {
      for (TimeExpression timeExpression : topicsResponse.getTimeExpressions()) {
        FeatureMap features = Factory.newFeatureMap();
        features.put("form", timeExpression.getForm());
        if (timeExpression.getNormalizedForm() != null)
          features.put("normalized_form", timeExpression.getNormalizedForm());
        if (timeExpression.getActualTime() != null)
          features.put("actual_time", timeExpression.getActualTime());
        if (timeExpression.getPrecision() != null)
          features.put("precision", timeExpression.getPrecision());
        outputAnnSet.add(Long.parseLong(timeExpression.getInip()), Long.parseLong(timeExpression.getEndp()) + 1, "TimeExpression", features);
      }
    }
    if (topicsResponse.getMoneyExpressions() != null && !topicsResponse.getMoneyExpressions().isEmpty()) {
      for (MoneyExpression moneyExpression : topicsResponse.getMoneyExpressions()) {
        FeatureMap features = Factory.newFeatureMap();
        features.put("form", moneyExpression.getForm());
        features.put("amount_form", moneyExpression.getAmountForm());
        if (moneyExpression.getNumericValue() != null)
          features.put("numeric_value", moneyExpression.getNumericValue());
        features.put("currency", moneyExpression.getCurrency());
        outputAnnSet.add(Long.parseLong(moneyExpression.getInip()), Long.parseLong(moneyExpression.getEndp()) + 1, "MoneyExpression", features);
      }
    }
    if (topicsResponse.getQuantityExpressions() != null && !topicsResponse.getQuantityExpressions().isEmpty()) {
      for (QuantityExpression quantityExpression : topicsResponse.getQuantityExpressions()) {
        FeatureMap features = Factory.newFeatureMap();
        features.put("form", quantityExpression.getForm());
        features.put("amount_form", quantityExpression.getAmountForm());
        if (quantityExpression.getNumericValue() != null)
          features.put("numeric_value", quantityExpression.getNumericValue());
        features.put("unit", quantityExpression.getUnit());
        outputAnnSet.add(Long.parseLong(quantityExpression.getInip()), Long.parseLong(quantityExpression.getEndp()) + 1, "QuantityExpression", features);
      }
    }
    if (topicsResponse.getOtherExpressions() != null && !topicsResponse.getOtherExpressions().isEmpty()) {
      for (OtherExpression otherExpression : topicsResponse.getOtherExpressions()) {
        FeatureMap features = Factory.newFeatureMap();
        features.put("form", otherExpression.getForm());
        features.put("type", otherExpression.getType());
        outputAnnSet.add(Long.parseLong(otherExpression.getInip()), Long.parseLong(otherExpression.getEndp()) + 1, "OtherExpression", features);
      }
    }
    if (topicsResponse.getQuotations() != null && !topicsResponse.getQuotations().isEmpty()) {
      for (Quotation quotation : topicsResponse.getQuotations()) {
        FeatureMap features = Factory.newFeatureMap();
        features.put("form", quotation.getForm());
        if (quotation.getWho() != null) {
          features.put("who_form", quotation.getWho().getForm());
          features.put("who_lemma", quotation.getWho().getLemma());
        }
        if (quotation.getVerb() != null) {
          features.put("verb_form", quotation.getVerb().getForm());
          features.put("verb_lemma", quotation.getVerb().getLemma());
        }
        outputAnnSet.add(Long.parseLong(quotation.getInip()), Long.parseLong(quotation.getEndp()) + 1, "Quotation", features);
      }
    }
    if (topicsResponse.getRelations() != null && !topicsResponse.getRelations().isEmpty()) {
      for (Relation relation : topicsResponse.getRelations()) {
        FeatureMap features = Factory.newFeatureMap();
        features.put("form", relation.getForm());
        features.put("subject_form", relation.getSubject().getForm());
        if (relation.getSubject().getLemmas() != null && !relation.getSubject().getLemmas().isEmpty())
          features.put("subject_lemmas", relation.getSubject().getLemmas());
        if (relation.getSubject().getSenseIds() != null && !relation.getSubject().getSenseIds().isEmpty())
          features.put("subject_sense_ids", relation.getSubject().getSenseIds());
        features.put("verb_form", relation.getVerb().getForm());
        if (relation.getVerb().getLemmas() != null && !relation.getVerb().getLemmas().isEmpty())
          features.put("verb_lemmas", relation.getVerb().getLemmas());
        if (relation.getVerb().getSenseIds() != null && !relation.getVerb().getSenseIds().isEmpty())
          features.put("verb_sense_ids", relation.getVerb().getSenseIds());
        if (relation.getVerb().getSemanticLemmas() != null && !relation.getVerb().getSemanticLemmas().isEmpty())
          features.put("verb_semantic_lemmas", relation.getVerb().getSemanticLemmas());
        if (relation.getComplements() != null && !relation.getComplements().isEmpty()) {
          List<String> complementForms = new ArrayList<String>();
          List<String> complementTypes = new ArrayList<String>();
          for (Complement complement : relation.getComplements()) {
            complementForms.add(complement.getForm());
            complementTypes.add(complement.getType());
          }
          features.put("complement_form", complementForms);
          features.put("complement_type", complementTypes);
        }
        features.put("degree", relation.getDegree());
        outputAnnSet.add(Long.parseLong(relation.getInip()), Long.parseLong(relation.getEndp()) + 1, "Relation", features);
      }
    }
  }

  /**
   * This method sets the annotations for the <code>entity</code> and <code>concept</code> objects returned in the 
   * Topics Extraction API. Inner objects are flattened and returned as new features of the <code>entity</code>
   * and <code>concept</code> annotations. 
   * 
   * @param topics list of entities or concepts obtained from the Topics Extraction API
   * @param outputAnnSet AnnotationSet in which the new annotations will be included
   * @param topicType name of the annotation to add (<code>Entity</code> or <code>Concept</code>)
   * @throws NumberFormatException
   * @throws InvalidOffsetException
   */
  private void setTopicsAnnotationsAndFeatures(List<Topic> topics, AnnotationSet outputAnnSet, String topicType) throws NumberFormatException, InvalidOffsetException {
    for (Topic topic : topics) {
      FeatureMap features = Factory.newFeatureMap();
      features.put("form", topic.getForm());
      if (topic.getOfficialForm() != null)
        features.put("official_form", topic.getOfficialForm());
      if (topic.getDictionary() != null)
        features.put("dictionary", topic.getDictionary());
      features.put("id", topic.getId());
      features.put("sementity_class", topic.getSementity().getSemClass());
      if (topic.getSementity().getFiction() != null)
        features.put("sementity_fiction", topic.getSementity().getFiction());
      features.put("sementity_id", topic.getSementity().getId());
      features.put("sementity_type", topic.getSementity().getType());
      if (topic.getSementity().getConfidence() != null)
        features.put("sementity_confidence", topic.getSementity().getConfidence());
      if (topic.getSemgeos() != null && !topic.getSemgeos().isEmpty()) {
        for (Semgeo semgeo : topic.getSemgeos()) {
          if (semgeo.getContinent() != null) {
            features.put("continent_form", semgeo.getContinent().getForm());
            features.put("continent_id", semgeo.getContinent().getId());
          }
          if (semgeo.getCountry() != null) {
            features.put("country_form", semgeo.getCountry().getForm());
            features.put("country_id", semgeo.getCountry().getId());
            if (semgeo.getCountry().getStandards() != null) {
              List<String> ids = new ArrayList<String>();
              List<String> values = new ArrayList<String>();
              for (Standard standard : semgeo.getCountry().getStandards()) {
                ids.add(standard.getId());
                values.add(standard.getValue());
              }
              features.put("country_standard_id", ids);
              features.put("country_standard_value", values);
            }
          }
          if (semgeo.getAdm1() != null) {
            features.put("adm1_form", semgeo.getAdm1().getForm());
            features.put("adm1_id", semgeo.getAdm1().getId());
          }
          if (semgeo.getAdm2() != null) {
            features.put("adm2_form", semgeo.getAdm2().getForm());
            features.put("adm2_id", semgeo.getAdm2().getId());
          }
          if (semgeo.getAdm3() != null) {
            features.put("adm3_form", semgeo.getAdm3().getForm());
            features.put("adm3_id", semgeo.getAdm3().getId());
          }
          if (semgeo.getCity() != null) {
            features.put("city_form", semgeo.getCity().getForm());
            features.put("city_id", semgeo.getCity().getId());
          }
          if (semgeo.getDistrict() != null) {
            features.put("district_form", semgeo.getDistrict().getForm());
            features.put("district_id", semgeo.getDistrict().getId());
          }
        }
      }
      if (topic.getSemlds() != null && !topic.getSemlds().isEmpty()) {
        List<String> semlds = new ArrayList<String>();
        for (String semld : topic.getSemlds()) {
          semlds.add(semld);
        }
        features.put("semld", semlds);
      }
      if (topic.getSemrefers() != null && !topic.getSemrefers().isEmpty()) {
        for (Semrefer semrefer : topic.getSemrefers()) {
          if (semrefer.getOrganization() != null) {
            features.put("organization_form", semrefer.getOrganization().getForm());
            features.put("organization_id", semrefer.getOrganization().getId());
          }              
          if (semrefer.getAffinity() != null) {
            features.put("affinity_form", semrefer.getAffinity().getForm());
            features.put("affinity_id", semrefer.getAffinity().getId());
          }
        }
      }
      if (topic.getSemthemes() != null && !topic.getSemthemes().isEmpty()) {
        List<String> ids = new ArrayList<String>();
        List<String> types = new ArrayList<String>();
        for (Semtheme semtheme : topic.getSemthemes()) {
          ids.add(semtheme.getId());
          types.add(semtheme.getType());
        }
        features.put("semtheme_id", ids);
        features.put("semtheme_type", types);
      }
      if (topic.getStandards() != null) {
        List<String> ids = new ArrayList<String>();
        List<String> values = new ArrayList<String>();
        for (Standard standard : topic.getStandards()) {
          ids.add(standard.getId());
          values.add(standard.getValue());
        }
        features.put("standard_id", ids);
        features.put("standard_value", values);
      }
      if (topic.getVariants() != null && !topic.getVariants().isEmpty()) {
        List<String> variants = new ArrayList<String>();
        List<ArrayList<String>> offsets = new ArrayList<ArrayList<String>>();
        for (final Variant variant : topic.getVariants()) {
          variants.add(variant.getForm());
          ArrayList<String> offs = new ArrayList<String>() {{
            add(variant.getInip());
            add(variant.getEndp());
          }};
          offsets.add(offs);
        }
        if(!variants.isEmpty())
          features.put("variant", variants);
        if(!offsets.isEmpty())
          features.put("variant_offsets", offsets);
      }
      features.put("relevance", topic.getRelevance());
      if(topic.getSubentities() != null && !topic.getSubentities().isEmpty()) {
        setTopicsAnnotationsAndFeatures(topic.getSubentities(), outputAnnSet, topicType);
      }
      for (Variant variant : topic.getVariants()) {
        outputAnnSet.add(Long.parseLong(variant.getInip()), Long.parseLong(variant.getEndp()) + 1, topicType, features);
      }
    }
  }
}
