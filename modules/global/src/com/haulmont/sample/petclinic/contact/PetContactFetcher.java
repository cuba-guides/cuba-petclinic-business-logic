package com.haulmont.sample.petclinic.contact;

import com.haulmont.sample.petclinic.entity.pet.Pet;

import java.util.Optional;

public interface PetContactFetcher {

    String NAME = "petclinic_PetContactFetcher";

    Optional<Contact> findContact(Pet pet);

}
