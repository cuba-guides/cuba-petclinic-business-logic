package com.cubaplatform.petclinic.web.pet.pet;

import com.cubaplatform.petclinic.entity.pet.Pet;
import com.cubaplatform.petclinic.web.pet.pet.contact.Contact;
import com.cubaplatform.petclinic.web.pet.pet.contact.PetContactFetcher;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.GroupTable;

import javax.inject.Inject;
import java.util.Optional;

public class PetBrowse extends AbstractLookup {

    @Inject
    PetContactFetcher petContactFetcher;

    @Inject
    PetContactDisplay petContactDisplay;

    @Inject
    protected GroupTable<Pet> petsTable;


    public void onFetchContact() {

        Pet pet = petsTable.getSingleSelected();

        Optional<Contact> contactInformation = petContactFetcher.findContact(pet);

        petContactDisplay.displayContact(contactInformation, frame);
    }


    public void onCalculateDiscount() {

        Pet pet = petsTable.getSingleSelected();

        int discount = calculateDiscount(pet);

        String discountMessage = "Discount for " + pet.getName() + ": " + discount + "%";

        showNotification(discountMessage, NotificationType.TRAY);
    }

    private int calculateDiscount(Pet pet) {
        int discount = 0;

        int visitAmount = pet.getVisits().size();
        if (visitAmount > 10) {
            discount = 10;
        }
        else if (visitAmount > 5) {
            discount = 5;
        }
        return discount;
    }

}