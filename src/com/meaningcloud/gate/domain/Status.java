package com.meaningcloud.gate.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Java bean implementation for <code>status</object>
 * object of the different MeaningCloud APIs.
 * 
 * @author Carlos Abad
 * @version 1.0.0
 */
public class Status {
  String code;
  String msg;
  String credits;
  @SerializedName("remaining_credits")
  String remainingCredits;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public String getCredits() {
    return credits;
  }

  public void setCredits(String credits) {
    this.credits = credits;
  }

  public String getRemainingCredits() {
    return remainingCredits;
  }

  public void setRemainingCredits(String remainingCredits) {
    this.remainingCredits = remainingCredits;
  }
}
