package com.meaningcloud.gate.domain;

/**
 * Java bean implementation for
 * <a href="https://www.meaningcloud.com/developer/topics-extraction/doc/2.0/response#other-expression"><code>other_epression</code></a>
 * object of the MeaningCloud Topics Extraction API.
 * 
 * @author Carlos Abad
 * @version 1.0.0
 */
public class OtherExpression {
  String form;
  String type;
  String inip;
  String endp;
  
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
