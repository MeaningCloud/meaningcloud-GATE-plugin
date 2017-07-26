package com.meaningcloud.gate.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.meaningcloud.gate.domain.Category;
import com.meaningcloud.gate.domain.Status;

/**
 * Java bean implementation for 
 * <a href="https://www.meaningcloud.com/developer/text-classification/doc/1.1/response"><code>response</code></a>
 * object of the MeaningCloud Text Classification API.
 * 
 * @author Carlos Abad
 * @version 1.0.0
 */
public class ClassResponse {
  Status status;
  @SerializedName("category_list")
  List<Category> categories;

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }
}
