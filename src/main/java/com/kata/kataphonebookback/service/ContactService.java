package com.kata.kataphonebookback.service;

import com.kata.kataphonebookback.domain.model.dto.ContactDto;

import java.util.List;
import java.util.Optional;

public interface ContactService {
    ContactDto addNewContact(ContactDto contact);

    void deleteContact(Long id);

    ContactDto updateContact(Long id, ContactDto contact);

    List<ContactDto> getAllContacts();

    Optional<ContactDto> getContactById(Long id);
}
