package com.meaningcloud.gate.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.meaningcloud.gate.domain.Language;
import com.meaningcloud.gate.domain.Status;

/**
 * Java bean implementation for 
 * <a href="https://www.meaningcloud.com/developer/language-identification/doc/2.0/response"><code>response</code></a>
 * object of the MeaningCloud Language Identification API.
 * 
 * @author Carlos Abad
 * @version 1.0.0
 */
public class LangResponse {
  Status status;
  @SerializedName("language_list")
  List<Language> languages;

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public List<Language> getLanguages() {
    return languages;
  }

  public void setLanguages(List<Language> languages) {
    this.languages = languages;
  }
}
