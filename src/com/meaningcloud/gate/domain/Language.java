package com.meaningcloud.gate.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Java bean implementation for
 * <a href="https://www.meaningcloud.com/developer/language-identification/doc/2.0/response"><code>language</code></a>
 * object of the MeaningCloud Language Identification API.
 * 
 * @author Carlos Abad
 * @version 1.0.0
 */
public class Language {
  String language;
  String relevance;
  String name;
  @SerializedName("iso639-3")
  String iso639_3;
  @SerializedName("iso639-2")
  String iso639_2;
  String script;
  String speakers;

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getRelevance() {
    return relevance;
  }

  public void setRelevance(String relevance) {
    this.relevance = relevance;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIso639_3() {
    return iso639_3;
  }

  public void setIso639_3(String iso639_3) {
    this.iso639_3 = iso639_3;
  }

  public String getIso639_2() {
    return iso639_2;
  }

  public void setIso639_2(String iso639_2) {
    this.iso639_2 = iso639_2;
  }

  public String getScript() {
    return script;
  }

  public void setScript(String script) {
    this.script = script;
  }

  public String getSpeakers() {
    return speakers;
  }

  public void setSpeakers(String speakers) {
    this.speakers = speakers;
  }

}
