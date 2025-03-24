package com.kata.kataphonebookback.service;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    Contact addNewContact(Contact contact);

    void deleteContact(Long id);

    Contact updateContact(Long id, Contact contact);

    List<Contact> getAllContacts();

    Optional<Contact> getContactById(Long id);
}
