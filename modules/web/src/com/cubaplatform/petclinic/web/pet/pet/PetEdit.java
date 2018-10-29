package com.cubaplatform.petclinic.web.pet.pet;

import com.cubaplatform.petclinic.contact.Contact;
import com.cubaplatform.petclinic.contact.PetContactFetcher;
import com.cubaplatform.petclinic.entity.pet.Pet;
import com.haulmont.cuba.gui.screen.EditedEntityContainer;
import com.haulmont.cuba.gui.screen.StandardEditor;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import java.util.Optional;
import javax.inject.Inject;

@UiController("petclinic_Pet.edit")
@UiDescriptor("pet-edit.xml")
@EditedEntityContainer("petCt")
public class PetEdit extends StandardEditor<Pet> {

    @Inject
    PetContactFetcher petContactFetcher;

    @Inject
    PetContactDisplay petContactDisplay;


    @Subscribe("fetchContact")
    public void fetchContact() {

        Pet pet = getEditedEntity();

        Optional<Contact> contactInformation = petContactFetcher.findContact(pet);

        petContactDisplay.displayContact(contactInformation, this);
    }
}