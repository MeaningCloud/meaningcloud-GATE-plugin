package com.meaningcloud.gate.domain;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Segment {
  String text;
  @SerializedName("segment_type")
  String segmentType;
  String inip;
  String endp;
  String confidence;
  @SerializedName("score_tag")
  String scoreTag;
  String agreement;
  @SerializedName("polarity_term_list")
  List<PolarityTerm> polarityTerms;
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
  public String getSegmentType() {
    return segmentType;
  }
  public void setSegmentType(String segmentType) {
    this.segmentType = segmentType;
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
  public List<PolarityTerm> getPolarityTerms() {
    return polarityTerms;
  }
  public void setPolarityTerms(List<PolarityTerm> polarityTerms) {
    this.polarityTerms = polarityTerms;
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
