package com.meaningcloud.gate.domain;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Java bean implementation for
 * <a href="https://www.meaningcloud.com/developer/sentiment-analysis/doc/2.1/response#sentence"><code>sentence</code></a>
 * object of the MeaningCloud Sentiment Analysis API.
 * 
 * @author Carlos Abad
 * @version 1.0.0
 */
public class Sentence {
  String text;
  String inip;
  String endp;
  String bop;
  String confidence;
  @SerializedName("score_tag")
  String scoreTag;
  String agreement;
  @SerializedName("segment_list")
  List<Segment> segments;
  @SerializedName("sentimented_entity_list")
  List<SentimentedTopic> sentimentedEntities;
  @SerializedName("sentimented_concept_list")
  List<SentimentedTopic> sentimentedConcepts;
  
  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
  }
  public String getInip() {
    return inip;
  }
  public void setInip(String inip) {
    this.inip = inip;
  }
  public String getEndp() {
    return endp;
  }
  public void setEndp(String endp) {
    this.endp = endp;
  }
  public String getBop() {
    return bop;
  }
  public void setBop(String bop) {
    this.bop = bop;
  }
  public String getConfidence() {
    return confidence;
  }
  public void setConfidence(String confidence) {
    this.confidence = confidence;
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
  public List<Segment> getSegments() {
    return segments;
  }
  public void setSegments(List<Segment> segments) {
    this.segments = segments;
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
