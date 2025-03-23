package com.kata.kataphonebookback.service;

import com.kata.kataphonebookback.domain.model.ContactEntity;
import com.kata.kataphonebookback.domain.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Contact addNewContact(Contact contact) {
        return convertEntityToContact(contactRepository.save(convertContactToEntity(contact)));
    }


    @Transactional
    @Override
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

//    @Override
//    public Contact updateContact(Contact contact) {
//        return null;
//    }

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

//    @Override
//    public Contact getContactById(Long id) {
//    public Contact getContactById(Long id) {
//        return convertEntityToContact(contactRepository.getReferenceById(id));
//    }


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
