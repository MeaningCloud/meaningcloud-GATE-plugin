package com.meaningcloud.gate.common;

import java.util.HashSet;
import java.util.List;

import com.meaningcloud.gate.param.DisambiguationType;
import com.meaningcloud.gate.param.RelaxedTypography;
import com.meaningcloud.gate.param.SemanticDisambiguationGrouping;

import gate.AnnotationSet;
import gate.Factory;
import gate.FeatureMap;

/**
 * This class implements common methods used for parameter handling
 * or particular functionality of some PRs
 * 
 * @author Carlos Abad
 * @version 1.0.0
 */
public class Utils {
  
  /**
   * Filters an AnnotationSet by an expression. This expression could be:
   * <ul>
   *    <li>AnnotationSet.FeatureName</li>
   *    <li>AnnotationSet.FeatureName==FeatureValue</li>
   * </ul>
   * Only the annotations that match this expression will be returned.
   * 
   * @param inputAS AnnotationSet to filter
   * @param inputAnnExpr expression used as pattern to filter the AnotationSet
   * @return AnnotationSet containing matching annotations
   */
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

  /**
   * Transforms parameters from Boolean to String, to match MeaningCloud
   * APIs request parameters format ('y' or 'n').
   * 
   * @param bool parameter to transform
   * @return transformed parameter as String
   */
  public static String boolTransform(boolean bool) {
    return bool ? "y" : "n";
  }
  
  /**
   * Translates <code>dm</code> parameter from values of an Enum to String, to match
   * MeaningCloud APIs request parameters format ('n', 'm' or 's').
   * 
   * @param dm parameter to translate
   * @return translated parameter as String
   * @see com.meaningcloud.gate.param.DisambiguationType
   */
  public static String translateDM(DisambiguationType dm) {
    String stringDM = "s";
    if (dm != null && dm.equals(DisambiguationType.NO_DISAMBIGUATION))
      stringDM = "n";
    else if (dm != null && dm.equals(DisambiguationType.MORPHOSYNTACTIC_DISAMBIGUATION))
      stringDM = "m";
    else if (dm != null && dm.equals(DisambiguationType.SEMANTIC_DISAMBIGUATION))
      stringDM = "s";

    return stringDM;
  }

  /**
   * Translates <code>sdg</code> parameter from values of an Enum to String, to match
   * MeaningCloud APIs request parameters format ('n', 'g', 't, or 'l').
   * 
   * @param sdg parameter to translate
   * @return translated parameter as String
   * @see com.meaningcloud.gate.param.SemanticDisambiguationGrouping
   */
  public static String translateSDG(SemanticDisambiguationGrouping sdg) {
    String stringSDG = "l";
    if (sdg != null && sdg.equals(SemanticDisambiguationGrouping.NONE))
      stringSDG = "n";
    else if (sdg != null && sdg.equals(SemanticDisambiguationGrouping.GLOBAL_INTERSECTION))
      stringSDG = "g";
    else if (sdg != null && sdg.equals(SemanticDisambiguationGrouping.INTERSECTION_BY_TYPE))
      stringSDG = "t";
    else if (sdg != null && sdg.equals(SemanticDisambiguationGrouping.INTERSECTION_BY_TYPE_SMALLEST_LOCATION))
      stringSDG = "l";

    return stringSDG;
  }
  
  /**
   * Translates <code>rt</code> parameter from values of an Enum to String, to match
   * MeaningCloud APIs request parameters format ('n', 'u' or 'y').
   * 
   * @param rt parameter to translate
   * @return translated parameter as String
   * @see com.meaningcloud.gate.param.RelaxedTypography
   */
  public static String translateRT(RelaxedTypography rt) {
    String stringRT = "n";
    if (rt != null && rt.equals(RelaxedTypography.DISABLED))
      stringRT = "n";
    else if (rt != null && rt.equals(RelaxedTypography.ENABLED_FOR_USER_DICTIONARIES))
      stringRT = "u";
    else if (rt != null && rt.equals(RelaxedTypography.ENABLED_FOR_ALL_RESOURCES))
      stringRT = "y";

    return stringRT;
  }
  
  /**
   * Translates <code>ud</code> parameter from a list of Strings to a String with values 
   * separated with <code>|</code> character, to match MeaningCloud APIs request parameters
   * format.
   * 
   * @param userDictionaries list of user dictionaries names
   * @return String with the user dictionaries separated with <code>|</code>
   */
  public static String translateUD(List<String> userDictionaries) {
    String ud = "";
    if (userDictionaries.size() > 0) {
      for (String userDict : userDictionaries) {
        ud += userDict + "|";
      }
      ud = ud.substring(0, ud.length() - 1);
    }
    return ud;
  }
}
