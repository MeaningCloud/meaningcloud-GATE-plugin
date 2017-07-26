package com.meaningcloud.gate.response;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.meaningcloud.gate.domain.MoneyExpression;
import com.meaningcloud.gate.domain.OtherExpression;
import com.meaningcloud.gate.domain.QuantityExpression;
import com.meaningcloud.gate.domain.Quotation;
import com.meaningcloud.gate.domain.Relation;
import com.meaningcloud.gate.domain.Status;
import com.meaningcloud.gate.domain.TimeExpression;
import com.meaningcloud.gate.domain.Topic;

/**
 * Java bean implementation for
 * <a href="https://www.meaningcloud.com/developer/topics-extraction/doc/2.0/response"><code>response</code></a>
 * object of the MeaningCloud Topics Extraction API.
 * 
 * @author Carlos Abad
 * @version 1.0.0
 */
public class TopicsResponse {
  Status status;
  @SerializedName("entity_list")
  List<Topic> entities;
  @SerializedName("concept_list")
  List<Topic> concepts;
  @SerializedName("time_expression_list")
  List<TimeExpression> timeExpressions;
  @SerializedName("money_expression_list")
  List<MoneyExpression> moneyExpressions;
  @SerializedName("quantity_expression_list")
  List<QuantityExpression> quantityExpressions;
  @SerializedName("other_expression_list")
  List<OtherExpression> otherExpressions;
  @SerializedName("quotation_list")
  List<Quotation> quotations;
  @SerializedName("relation_list")
  List<Relation> relations;
  
  public Status getStatus() {
    return status;
  }
  public void setStatus(Status status) {
    this.status = status;
  }
  public List<Topic> getEntities() {
    return entities;
  }
  public void setEntities(List<Topic> entities) {
    this.entities = entities;
  }
  public List<Topic> getConcepts() {
    return concepts;
  }
  public void setConcepts(List<Topic> concepts) {
    this.concepts = concepts;
  }
  public List<TimeExpression> getTimeExpressions() {
    return timeExpressions;
  }
  public void setTimeExpressions(List<TimeExpression> timeExpressions) {
    this.timeExpressions = timeExpressions;
  }
  public List<MoneyExpression> getMoneyExpressions() {
    return moneyExpressions;
  }
  public void setMoneyExpressions(List<MoneyExpression> moneyExpressions) {
    this.moneyExpressions = moneyExpressions;
  }
  public List<QuantityExpression> getQuantityExpressions() {
    return quantityExpressions;
  }
  public void setQuantityExpressions(List<QuantityExpression> quantityExpressions) {
    this.quantityExpressions = quantityExpressions;
  }
  public List<OtherExpression> getOtherExpressions() {
    return otherExpressions;
  }
  public void setOtherExpressions(List<OtherExpression> otherExpressions) {
    this.otherExpressions = otherExpressions;
  }
  public List<Quotation> getQuotations() {
    return quotations;
  }
  public void setQuotations(List<Quotation> quotations) {
    this.quotations = quotations;
  }
  public List<Relation> getRelations() {
    return relations;
  }
  public void setRelations(List<Relation> relations) {
    this.relations = relations;
  }
}
