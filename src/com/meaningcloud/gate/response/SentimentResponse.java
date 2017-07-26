package com.meaningcloud.gate.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.meaningcloud.gate.domain.Sentence;
import com.meaningcloud.gate.domain.SentimentedTopic;
import com.meaningcloud.gate.domain.Status;

/**
 * Java bean implementation for
 * <a href="https://www.meaningcloud.com/developer/sentiment-analysis/doc/2.1/response"><code>response</code></a>
 * object of the MeaningCloud Sentiment Analysis API.
 * 
 * @author Carlos Abad
 * @version 1.0.0
 */
public class SentimentResponse {
  Status status;
  String model;
  @SerializedName("score_tag")
  String scoreTag;
  String agreement;
  String subjectivity;
  String confidence;
  String irony;
  @SerializedName("sentence_list")
  List<Sentence> sentences;
  @SerializedName("sentimented_entity_list")
  List<SentimentedTopic> sentimentedEntities;
  @SerializedName("sentimented_concept_list")
  List<SentimentedTopic> sentimentedConcepts;
  
  public Status getStatus() {
    return status;
  }
  public void setStatus(Status status) {
    this.status = status;
  }
  public String getModel() {
    return model;
  }
  public void setModel(String model) {
    this.model = model;
  }
  public String getScoreTag() {
    return scoreTag;
  }
  public void setScoreTag(String scoreTag) {
    this.scoreTag = scoreTag;
  }
  public String getAgreement() {
    return agreement;
  }
  public void setAgreement(String agreement) {
    this.agreement = agreement;
  }
  public String getSubjectivity() {
    return subjectivity;
  }
  public void setSubjectivity(String subjectivity) {
    this.subjectivity = subjectivity;
  }
  public String getConfidence() {
    return confidence;
  }
  public void setConfidence(String confidence) {
    this.confidence = confidence;
  }
  public String getIrony() {
    return irony;
  }
  public void setIrony(String irony) {
    this.irony = irony;
  }
  public List<Sentence> getSentences() {
    return sentences;
  }
  public void setSentences(List<Sentence> sentences) {
    this.sentences = sentences;
  }
  public List<SentimentedTopic> getSentimentedEntities() {
    return sentimentedEntities;
  }
  public void setSentimentedEntities(List<SentimentedTopic> sentimentedEntities) {
    this.sentimentedEntities = sentimentedEntities;
  }
  public List<SentimentedTopic> getSentimentedConcepts() {
    return sentimentedConcepts;
  }
  public void setSentimentedConcepts(List<SentimentedTopic> sentimentedConcepts) {
    this.sentimentedConcepts = sentimentedConcepts;
  }
}
