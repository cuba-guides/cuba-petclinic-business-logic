package com.cubaplatform.petclinic.web.pet.pet;

import com.cubaplatform.petclinic.entity.pet.Pet;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.GroupTable;

import javax.inject.Inject;

public class PetBrowse extends AbstractLookup {


    @Inject
    protected GroupTable<Pet> petsTable;

    public void calculateDiscount() {

        Pet pet = petsTable.getSingleSelected();

        int discount = 0;

        int visitAmount = pet.getVisits().size();
        if (visitAmount > 10) {
            discount = 10;
        }
        else if (visitAmount > 5) {
            discount = 5;
        }

        showNotification("Discount for " + pet.getName() + ": " + discount + "%", NotificationType.TRAY);
    }
}