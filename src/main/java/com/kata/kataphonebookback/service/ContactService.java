package com.kata.kataphonebookback.service;

import com.kata.kataphonebookback.domain.model.ContactEntity;

import java.util.List;

public interface ContactService {
    Contact addNewContact(ContactEntity contact);
    void deleteContact(ContactEntity contact);
    List<Contact> getAllContacts();
    Contact getContactById(Long id);

}
