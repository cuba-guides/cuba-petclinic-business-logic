package com.cubaplatform.petclinic.web.pet.pet;

import com.cubaplatform.petclinic.web.pet.pet.contact.Contact;
import com.haulmont.cuba.gui.components.Frame;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("petclinic_PetContactDisplay")
public class PetContactDisplay {


    public void displayContact(Optional<Contact> contactInformation, Frame frame) {

        if (contactInformation.isPresent()) {
            frame.showNotification(contactInformation.get().toString());
        }
        else {
            frame.showNotification("No contact information found", Frame.NotificationType.ERROR);
        }

    }
}
