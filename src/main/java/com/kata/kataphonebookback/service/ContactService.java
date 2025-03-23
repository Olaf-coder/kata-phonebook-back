package com.kata.kataphonebookback.service;

import com.kata.kataphonebookback.domain.model.ContactEntity;

import java.util.List;

public interface ContactService {
    Contact addNewContact(Contact contact);

    void deleteContact(Long id);

    List<Contact> getAllContacts();
    Contact getContactById(Long id);

}
