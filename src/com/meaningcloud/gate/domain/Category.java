package com.meaningcloud.gate.domain;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Java bean implementation for 
 * <a href="https://www.meaningcloud.com/developer/text-classification/doc/1.1/response"><code>category</code></a>
 * object of the MeaningCloud Text Classification API.
 * 
 * @author Carlos Abad
 * @version 1.0.0
 */
public class Category {
  String code;
  String label;
  @SerializedName("abs_relevance")
  String absRelevance;
  String relevance;
  @SerializedName("term_list")
  List<Term> terms;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getAbsRelevance() {
    return absRelevance;
  }

  public void setAbsRelevance(String absRelevance) {
    this.absRelevance = absRelevance;
  }

  public String getRelevance() {
    return relevance;
  }

  public void setRelevance(String relevance) {
    this.relevance = relevance;
  }

  public List<Term> getTerms() {
    return terms;
  }

  public void setTerms(List<Term> terms) {
    this.terms = terms;
  }

  public class Term {
    String form;
    @SerializedName("abs_relevance")
    String absRelevance;

    public String getForm() {
      return form;
    }

    public void setForm(String form) {
      this.form = form;
    }

    public String getAbsRelevance() {
      return absRelevance;
    }

    public void setAbsRelevance(String absRelevance) {
      this.absRelevance = absRelevance;
    }
  }
}
