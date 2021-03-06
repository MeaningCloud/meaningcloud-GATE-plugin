package com.meaningcloud.gate.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Java bean implementation for
 * <a href="https://www.meaningcloud.com/developer/topics-extraction/doc/2.0/response#money-expression"><code>money_epression</code></a>
 * object of the MeaningCloud Topics Extraction API.
 * 
 * @author Carlos Abad
 * @version 1.0.0
 */
public class MoneyExpression {
  String form;
  @SerializedName("amount_form")
  String amountForm;
  @SerializedName("numeric_value")
  String numericValue;
  String currency;
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
  public String getCurrency() {
    return currency;
  }
  public void setCurrency(String currency) {
    this.currency = currency;
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
