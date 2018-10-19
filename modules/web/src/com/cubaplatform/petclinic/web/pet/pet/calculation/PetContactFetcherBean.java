package com.cubaplatform.petclinic.web.pet.pet.calculation;

import com.cubaplatform.petclinic.entity.owner.Owner;
import com.cubaplatform.petclinic.entity.pet.Pet;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Optional;

@Component(PetContactFetcher.NAME)
public class PetContactFetcherBean implements PetContactFetcher {

    @Inject
    DataManager dataManager;


    @Override
    public Optional<Contact> findContact(Pet pet) {

        Optional<Owner> petOwner = loadOwnerFor(pet);

        if (petOwner.isPresent()) {

            String telephone = petOwner.get().getTelephone();
            String email = petOwner.get().getEmail();

            if (isAvailable(telephone)) {
                return createContact(telephone, ContactType.TELEPHONE);
            } else if (isAvailable(email)) {
                return createContact(email, ContactType.EMAIL);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }

    }

    private Optional<Contact> createContact(String contactValue, ContactType contactType) {
        Contact contact = new Contact();
        contact.setValue(contactValue);
        contact.setType(contactType);
        return Optional.of(contact);
    }

    private Optional<Owner> loadOwnerFor(Pet pet) {
        if (pet.getOwner() == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(dataManager.reload(pet.getOwner(), View.LOCAL));
    }

    private boolean isAvailable(String contactOption) {
        return StringUtils.isNotBlank(contactOption);
    }
}
