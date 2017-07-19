package com.meaningcloud.gate.domain;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Topic {
  String form;
  @SerializedName("official_form")
  String officialForm;
  String dictionary;
  String id;
  Sementity sementity;
  @SerializedName("semgeo_list")
  List<Semgeo> semgeos;
  @SerializedName("semld_list")
  List<String> semlds;
  @SerializedName("semrefer_list")
  List<Semrefer> semrefers;
  @SerializedName("semtheme_list")
  List<Semtheme> semthemes;
  @SerializedName("variant_list")
  List<Variant> variants;
  String relevance;
  @SerializedName("subentity_list")
  List<Topic> subentities;  
  
  public String getForm() {
    return form;
  }

  public void setForm(String form) {
    this.form = form;
  }

  public String getOfficialForm() {
    return officialForm;
  }

  public void setOfficialForm(String officialForm) {
    this.officialForm = officialForm;
  }

  public String getDictionary() {
    return dictionary;
  }

  public void setDictionary(String dictionary) {
    this.dictionary = dictionary;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Sementity getSementity() {
    return sementity;
  }

  public void setSementity(Sementity sementity) {
    this.sementity = sementity;
  }

  public List<Semgeo> getSemgeos() {
    return semgeos;
  }

  public void setSemgeos(List<Semgeo> semgeos) {
    this.semgeos = semgeos;
  }

  public List<String> getSemlds() {
    return semlds;
  }

  public void setSemlds(List<String> semlds) {
    this.semlds = semlds;
  }

  public List<Semrefer> getSemrefers() {
    return semrefers;
  }

  public void setSemrefers(List<Semrefer> semrefers) {
    this.semrefers = semrefers;
  }

  public List<Semtheme> getSemthemes() {
    return semthemes;
  }

  public void setSemthemes(List<Semtheme> semthemes) {
    this.semthemes = semthemes;
  }

  public List<Variant> getVariants() {
    return variants;
  }

  public void setVariants(List<Variant> variants) {
    this.variants = variants;
  }

  public String getRelevance() {
    return relevance;
  }

  public void setRelevance(String relevance) {
    this.relevance = relevance;
  }

  public List<Topic> getSubentities() {
    return subentities;
  }

  public void setSubentities(List<Topic> subentities) {
    this.subentities = subentities;
  }

  class Sementity {
    @SerializedName("class")
    String semClass;
    String fiction;
    String id;
    String type;
    String confidence;
    
    public String getSemClass() {
      return semClass;
    }
    public void setSemClass(String semClass) {
      this.semClass = semClass;
    }
    public String getFiction() {
      return fiction;
    }
    public void setFiction(String fiction) {
      this.fiction = fiction;
    }
    public String getId() {
      return id;
    }
    public void setId(String id) {
      this.id = id;
    }
    public String getType() {
      return type;
    }
    public void setType(String type) {
      this.type = type;
    }
    public String getConfidence() {
      return confidence;
    }
    public void setConfidence(String confidence) {
      this.confidence = confidence;
    }
    
  }
  
  class Semgeo {
    HierarchyLevel continent;
    HierarchyLevel country;
    HierarchyLevel adm1;
    HierarchyLevel adm2;
    HierarchyLevel adm3;
    HierarchyLevel city;
    HierarchyLevel district;
    
    public HierarchyLevel getContinent() {
      return continent;
    }

    public void setContinent(HierarchyLevel continent) {
      this.continent = continent;
    }

    public HierarchyLevel getCountry() {
      return country;
    }

    public void setCountry(HierarchyLevel country) {
      this.country = country;
    }

    public HierarchyLevel getAdm1() {
      return adm1;
    }

    public void setAdm1(HierarchyLevel adm1) {
      this.adm1 = adm1;
    }

    public HierarchyLevel getAdm2() {
      return adm2;
    }

    public void setAdm2(HierarchyLevel adm2) {
      this.adm2 = adm2;
    }

    public HierarchyLevel getAdm3() {
      return adm3;
    }

    public void setAdm3(HierarchyLevel adm3) {
      this.adm3 = adm3;
    }

    public HierarchyLevel getCity() {
      return city;
    }

    public void setCity(HierarchyLevel city) {
      this.city = city;
    }

    public HierarchyLevel getDistrict() {
      return district;
    }

    public void setDistrict(HierarchyLevel district) {
      this.district = district;
    }

    class HierarchyLevel {
      String form;
      String id;
      @SerializedName("standard_list")
      List<Standard> standards;
      
      public String getForm() {
        return form;
      }
      public void setForm(String form) {
        this.form = form;
      }
      public String getId() {
        return id;
      }
      public void setId(String id) {
        this.id = id;
      }
      public List<Standard> getStandards() {
        return standards;
      }
      public void setStandards(List<Standard> standards) {
        this.standards = standards;
      }
    }
  }
  
  class Standard {
    String id;
    String value;
    
    public String getId() {
      return id;
    }
    public void setId(String id) {
      this.id = id;
    }
    public String getValue() {
      return value;
    }
    public void setValue(String value) {
      this.value = value;
    }
  }
  
  class Semrefer {
    SemreferType organization;
    SemreferType affinity;
    
    public SemreferType getOrganization() {
      return organization;
    }

    public void setOrganization(SemreferType organization) {
      this.organization = organization;
    }

    public SemreferType getAffinity() {
      return affinity;
    }

    public void setAffinity(SemreferType affinity) {
      this.affinity = affinity;
    }

    class SemreferType {
      String form;
      String id;
      
      public String getForm() {
        return form;
      }
      public void setForm(String form) {
        this.form = form;
      }
      public String getId() {
        return id;
      }
      public void setId(String id) {
        this.id = id;
      }
    }
  }
  
  class Semtheme {
    String id;
    String type;
    
    public String getId() {
      return id;
    }
    public void setId(String id) {
      this.id = id;
    }
    public String getType() {
      return type;
    }
    public void setType(String type) {
      this.type = type;
    }
  }
  
  class Variant {
    String form;
    String inip;
    String endp;
    
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
  }
}
