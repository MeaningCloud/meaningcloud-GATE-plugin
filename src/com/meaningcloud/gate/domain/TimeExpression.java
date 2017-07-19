package com.meaningcloud.gate.domain;

import com.google.gson.annotations.SerializedName;

public class TimeExpression {
  String form;
  @SerializedName("normalized_form")
  String normalizedForm;
  @SerializedName("actual_time")
  String actualTime;
  String precision;
  String inip;
  String endp;
  
  public String getForm() {
    return form;
  }
  public void setForm(String form) {
    this.form = form;
  }
  public String getNormalizedForm() {
    return normalizedForm;
  }
  public void setNormalizedForm(String normalizedForm) {
    this.normalizedForm = normalizedForm;
  }
  public String getActualTime() {
    return actualTime;
  }
  public void setActualTime(String actualTime) {
    this.actualTime = actualTime;
  }
  public String getPrecision() {
    return precision;
  }
  public void setPrecision(String precision) {
    this.precision = precision;
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
}
