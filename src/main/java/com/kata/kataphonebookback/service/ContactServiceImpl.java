package com.kata.kataphonebookback.service;

import com.kata.kataphonebookback.domain.model.ContactEntity;
import com.kata.kataphonebookback.domain.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


//    @Override
//    @Transactional
//    public Contact addNewContact(ContactEntity contact) {
//        return convertEntityToContact(contactRepository.save(contact));
//    }

    @Override
    @Transactional
    public Contact addNewContact(ContactEntity contact) {
        return convertEntityToContact(contactRepository.save(contact));
    }


    @Transactional
    @Override
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

    @Override
    public List<Contact> getAllContacts() {
        List<ContactEntity> contacts = contactRepository.findAll();

        return contacts.stream().map(this::convertEntityToContact).toList();
    }

    @Override
    public Contact getContactById(Long id) {
        return convertEntityToContact(contactRepository.getReferenceById(id));
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

}
