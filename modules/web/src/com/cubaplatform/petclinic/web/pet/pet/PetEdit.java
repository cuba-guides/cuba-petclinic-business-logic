package com.cubaplatform.petclinic.web.pet.pet;

import com.cubaplatform.petclinic.contact.Contact;
import com.cubaplatform.petclinic.contact.PetContactFetcher;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.cubaplatform.petclinic.entity.pet.Pet;

import javax.inject.Inject;
import java.util.Optional;

public class PetEdit extends AbstractEditor<Pet> {

    @Inject
    PetContactFetcher petContactFetcher;

    @Inject
    PetContactDisplay petContactDisplay;

    public void onFetchContact() {

        Pet pet = getItem();

        Optional<Contact> contactInformation = petContactFetcher.findContact(pet);

        petContactDisplay.displayContact(contactInformation, frame);
    }
}