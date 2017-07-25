package com.meaningcloud.gate.domain;

import com.google.gson.annotations.SerializedName;

public class SentimentedTopic {
  String form;
  String id;
  String variant;
  String inip;
  String endp;
  String type;
  @SerializedName("score_tag")
  String scoreTag;
  
  public String getForm() {
    return form;
  }
  public void setForm(String form) {
    this.form = form;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getVariant() {
    return variant;
  }
  public void setVariant(String variant) {
    this.variant = variant;
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
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getScoreTag() {
    return scoreTag;
  }
  public void setScoreTag(String scoreTag) {
    this.scoreTag = scoreTag;
  }
}
