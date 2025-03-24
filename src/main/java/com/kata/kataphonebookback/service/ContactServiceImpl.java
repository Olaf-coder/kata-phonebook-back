package com.kata.kataphonebookback.service;

import com.kata.kataphonebookback.Exceptions.InvalidDataException;
import com.kata.kataphonebookback.Exceptions.RessourceNotFoundException;
import com.kata.kataphonebookback.domain.model.ContactEntity;
import com.kata.kataphonebookback.domain.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    @Override
    @Transactional
    public Contact addNewContact(Contact contact) throws InvalidDataException {
//        return convertEntityToContact(contactRepository.save(convertContactToEntity(contact)));
        if (ObjectUtils.isEmpty(contact.firstName()) || ObjectUtils.isEmpty(contact.familyName()) || contact.firstName().isBlank() || contact.familyName().isBlank()) {
            throw new InvalidDataException("Contact first name or family name is missing");
        }
        return convertEntityToContact(contactRepository.save(convertContactToEntity(contact)));

    }


    @Transactional
    @Override
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Contact updateContact(Long contactId, Contact contactUpdated) throws RessourceNotFoundException, InvalidDataException {
        Optional<Contact> contactOptional = this.getContactById(contactId);

        if (contactOptional.isEmpty()) {
            throw new RessourceNotFoundException("Contact does not exist");
        } else {
            if (ObjectUtils.isEmpty(contactUpdated.firstName()) || ObjectUtils.isEmpty(contactUpdated.familyName()) || contactUpdated.firstName().isBlank() || contactUpdated.familyName().isBlank()) {
                throw new InvalidDataException("Contact first name or family name is missing");
            }
            ContactEntity contactEntityToSave = new ContactEntity();
            contactEntityToSave.setId(contactId);
            contactEntityToSave.setFirstName(contactUpdated.firstName());
            contactEntityToSave.setFamilyName(contactUpdated.familyName());
            contactEntityToSave.setPhoneNumber(contactUpdated.phoneNumber());
            contactEntityToSave.setEmail(contactUpdated.email());
            return convertEntityToContact(contactRepository.save(contactEntityToSave));
        }
    }

    @Override
    public List<Contact> getAllContacts() {
        List<ContactEntity> contacts = contactRepository.findAll();

        return contacts.stream().map(this::convertEntityToContact).toList();
    }

    @Override
    public Optional<Contact> getContactById(Long id) {
        Optional<ContactEntity> contactEntityOptional = contactRepository.findById(id);
        return contactEntityOptional.map(this::convertEntityToContact);
    }


    private Contact convertEntityToContact(ContactEntity contactEntity) {
        return new Contact(
                contactEntity.getId(),
                contactEntity.getFirstName(),
                contactEntity.getFamilyName(),
                contactEntity.getPhoneNumber(),
                contactEntity.getEmail()
        );
    }

    private ContactEntity convertContactToEntity(Contact contact) {
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setId(contact.id());
        contactEntity.setFirstName(contact.firstName());
        contactEntity.setFamilyName(contact.familyName());
        contactEntity.setPhoneNumber(contact.phoneNumber());
        contactEntity.setEmail(contact.email());
        return contactEntity;
    }
}
