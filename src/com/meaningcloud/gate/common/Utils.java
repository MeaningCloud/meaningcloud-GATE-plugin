package com.meaningcloud.gate.common;

import java.util.HashSet;

import gate.AnnotationSet;
import gate.Factory;
import gate.FeatureMap;

public class Utils {
  public static AnnotationSet getFilteredAS(AnnotationSet inputAS, String inputAnnExpr) {
    // We allow inputAnnExpr of the form
    // Annotation.feature == value or just Annotation.feature

    String annFeature;
    String annFeatureValue;
    String[] inputAnnArr = inputAnnExpr.split("(\\.)|(\\s*==\\s*)");

    // Assume a simple ann name unless we have a feature and feature value present
    String annName = inputAnnArr[0];
    AnnotationSet filteredAS = inputAS.get(annName);

    if (inputAnnArr.length == 3 || inputAnnArr.length == 2) {
      annFeature = inputAnnArr[1];
      if (inputAnnArr.length == 2) {
        HashSet<String> feats = new HashSet<String>();
        feats.add(annFeature);
        filteredAS = inputAS.get(annName, feats);
      } else {
        FeatureMap annFeats = Factory.newFeatureMap();
        annFeatureValue = inputAnnArr[2];
        annFeats.put(annFeature, annFeatureValue);
        filteredAS = inputAS.get(annName, annFeats);
      }
    }

    return filteredAS;
  }

  public static String boolTransform(boolean bool) {
    return bool ? "y" : "n";
  }
}
