package com.cubaplatform.petclinic.service;

import com.cubaplatform.petclinic.entity.pet.Pet;
import com.cubaplatform.petclinic.entity.pet.PetType;
import com.haulmont.cuba.core.app.EmailerAPI;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.EmailInfo;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(DiseaseWarningMailingService.NAME)
public class DiseaseWarningMailingServiceBean implements DiseaseWarningMailingService {

  @Inject
  protected DataManager dataManager;


  @Inject
  protected EmailerAPI emailerAPI;


  @Override
  public int warnAboutDisease(PetType petType, String disease, String city) {

    List<Pet> petsInDiseaseCity = dataManager.load(Pet.class)
        .query(
            "select e from petclinic_Pet e where e.owner.city = :ownerCity and e.type = :petType")
        .parameter("ownerCity", city)
        .parameter("petType", petType)
        .view("pet-with-owner-and-type")
        .list();

    List<Pet> petsWithEmail = petsInDiseaseCity
        .stream()
        .filter(this::ownerHasEmail)
        .collect(Collectors.toList());


    petsWithEmail
        .forEach(pet -> {

          String emailSubject = "Warning about " + disease + " in the Area of " + city;

          Map<String, Serializable> templateParameters = getTemplateParams(disease, city, pet);

          String ownerEmail = pet.getOwner().getEmail();

          EmailInfo email = new EmailInfo(
              ownerEmail,
              emailSubject,
              null,
              "com/cubaplatform/petclinic/templates/disease-warning-mailing.txt",
              templateParameters
          );

          emailerAPI.sendEmailAsync(email);

        });

    return petsWithEmail.size();
  }

  private boolean ownerHasEmail(Pet pet) {
    String ownerEmail = pet.getOwner().getEmail();
    return !StringUtils.isEmpty(ownerEmail);
  }

  private Map<String, Serializable> getTemplateParams(String disease, String city, Pet pet) {
    Map<String, Serializable> templateParameters = new HashMap<>();

    templateParameters.put("owner", pet.getOwner());
    templateParameters.put("pet", pet);
    templateParameters.put("disease", disease);
    templateParameters.put("city", city);
    return templateParameters;
  }

}