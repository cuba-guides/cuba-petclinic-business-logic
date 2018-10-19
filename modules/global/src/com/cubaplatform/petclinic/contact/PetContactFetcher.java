package com.cubaplatform.petclinic.contact;

import com.cubaplatform.petclinic.entity.pet.Pet;

import java.util.Optional;

public interface PetContactFetcher {

    String NAME = "petclinic_PetContactFetcher";

    Optional<Contact> findContact(Pet pet);

}
