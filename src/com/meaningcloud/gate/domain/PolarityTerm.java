package com.meaningcloud.gate.domain;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PolarityTerm {
  String text;
  String inip;
  String endp;
  @SerializedName("tag_stack")
  String tagStack;
  String confidence;
  @SerializedName("score_tag")
  String scoreTag;
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
  public String getTagStack() {
    return tagStack;
  }
  public void setTagStack(String tagStack) {
    this.tagStack = tagStack;
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
