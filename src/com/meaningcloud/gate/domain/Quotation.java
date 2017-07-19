package com.meaningcloud.gate.domain;

public class Quotation {
  String form;
  Aux who;
  Aux verb;
  String inip;
  String endp;
  
  public String getForm() {
    return form;
  }

  public void setForm(String form) {
    this.form = form;
  }

  public Aux getWho() {
    return who;
  }

  public void setWho(Aux who) {
    this.who = who;
  }

  public Aux getVerb() {
    return verb;
  }

  public void setVerb(Aux verb) {
    this.verb = verb;
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

  class Aux {
    String form;
    String lemma;
    
    public String getForm() {
      return form;
    }
    public void setForm(String form) {
      this.form = form;
    }
    public String getLemma() {
      return lemma;
    }
    public void setLemma(String lemma) {
      this.lemma = lemma;
    }
  }
}
