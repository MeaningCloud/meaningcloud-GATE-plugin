package com.meaningcloud.gate.domain;

import com.google.gson.annotations.SerializedName;

public class QuantityExpression {
  String form;
  @SerializedName("amount_form")
  String amountForm;
  @SerializedName("numeric_value")
  String numericValue;
  String unit;
  String inip;
  String endp;
  
  public String getForm() {
    return form;
  }
  public void setForm(String form) {
    this.form = form;
  }
  public String getAmountForm() {
    return amountForm;
  }
  public void setAmountForm(String amountForm) {
    this.amountForm = amountForm;
  }
  public String getNumericValue() {
    return numericValue;
  }
  public void setNumericValue(String numericValue) {
    this.numericValue = numericValue;
  }
  public String getUnit() {
    return unit;
  }
  public void setUnit(String currency) {
    this.unit = currency;
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
