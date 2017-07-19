package com.meaningcloud.gate.domain;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Relation {
  String form;
  String inip;
  String endp;
  Subject subject;
  Verb verb;
  @SerializedName("complement_list")
  List<Complement> complements;
  String degree;
  
  public String getForm() {
    return form;
  }

  public void setForm(String form) {
    this.form = form;
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

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public Verb getVerb() {
    return verb;
  }

  public void setVerb(Verb verb) {
    this.verb = verb;
  }

  public List<Complement> getComplements() {
    return complements;
  }

  public void setComplements(List<Complement> complements) {
    this.complements = complements;
  }

  public String getDegree() {
    return degree;
  }

  public void setDegree(String degree) {
    this.degree = degree;
  }

  class Subject {
    String form;
    @SerializedName("lemma_list")
    List<String> lemmas;
    @SerializedName("sense_id_list")
    List<String> senseIds;
    
    public String getForm() {
      return form;
    }
    public void setForm(String form) {
      this.form = form;
    }
    public List<String> getLemmas() {
      return lemmas;
    }
    public void setLemmas(List<String> lemmas) {
      this.lemmas = lemmas;
    }
    public List<String> getSenseIds() {
      return senseIds;
    }
    public void setSenseIds(List<String> senseIds) {
      this.senseIds = senseIds;
    }
  }
  
  class Verb {
    String form;
    @SerializedName("lemma_list")
    List<String> lemmas;
    @SerializedName("sense_id_list")
    List<String> senseIds;
    @SerializedName("semantic_lemma_list")
    List<String> semanticLemmas;
    
    public String getForm() {
      return form;
    }
    public void setForm(String form) {
      this.form = form;
    }
    public List<String> getLemmas() {
      return lemmas;
    }
    public void setLemmas(List<String> lemmas) {
      this.lemmas = lemmas;
    }
    public List<String> getSenseIds() {
      return senseIds;
    }
    public void setSenseIds(List<String> senseIds) {
      this.senseIds = senseIds;
    }
    public List<String> getSemanticLemmas() {
      return semanticLemmas;
    }
    public void setSemanticLemmas(List<String> semanticLemmas) {
      this.semanticLemmas = semanticLemmas;
    }
  }
  
  class Complement {
    String form;
    String type;
    
    public String getForm() {
      return form;
    }
    public void setForm(String form) {
      this.form = form;
    }
    public String getType() {
      return type;
    }
    public void setType(String type) {
      this.type = type;
    }
  }
}
