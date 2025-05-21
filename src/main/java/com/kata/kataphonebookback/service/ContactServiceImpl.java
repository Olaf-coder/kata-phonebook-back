package com.kata.kataphonebookback.service;

import com.kata.kataphonebookback.domain.mapper.ContactMapper;
import com.kata.kataphonebookback.domain.model.dto.ContactDto;
import com.kata.kataphonebookback.exceptions.InvalidDataException;
import com.kata.kataphonebookback.exceptions.RessourceNotFoundException;
import com.kata.kataphonebookback.domain.model.entity.ContactEntity;
import com.kata.kataphonebookback.domain.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ContactServiceImpl(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }


    @Override
    @Transactional
    public ContactDto addNewContact(ContactDto contact) throws InvalidDataException {
        if (ObjectUtils.isEmpty(contact.firstName()) || ObjectUtils.isEmpty(contact.familyName()) || contact.firstName().isBlank() || contact.familyName().isBlank()) {
            throw new InvalidDataException("Contact first name or family name is missing");
        }
        return contactMapper.toDto(contactRepository.save(contactMapper.toEntity(contact)));

    }


    @Transactional
    @Override
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }

    @Transactional
    @Override
    public ContactDto updateContact(Long contactId, ContactDto contactUpdated) throws RessourceNotFoundException, InvalidDataException {
        this.getContactById(contactId);

        if (ObjectUtils.isEmpty(contactUpdated.firstName()) || ObjectUtils.isEmpty(contactUpdated.familyName()) || contactUpdated.firstName().isBlank() || contactUpdated.familyName().isBlank()) {
            throw new InvalidDataException("Contact first name or family name is missing");
        }
        ContactEntity contactEntityToSave = new ContactEntity();
        contactEntityToSave.setId(contactId);
        contactEntityToSave.setFirstName(contactUpdated.firstName());
        contactEntityToSave.setFamilyName(contactUpdated.familyName());
        contactEntityToSave.setPhoneNumber(contactUpdated.phoneNumber());
        contactEntityToSave.setEmail(contactUpdated.email());
        return contactMapper.toDto(contactRepository.save(contactEntityToSave));
    }

    @Override
    public List<ContactDto> getAllContacts() {
        List<ContactEntity> contacts = contactRepository.findAll();
        if (contacts.isEmpty()) {
            throw new RessourceNotFoundException("No contacts found");
        }
        return contacts.stream().map(contactMapper::toDto).toList();
    }

    @Override
    public ContactDto getContactById(Long id) throws RessourceNotFoundException {
        Optional<ContactEntity> contactEntityOptional = contactRepository.findById(id);

        return contactMapper.toDto(contactEntityOptional.orElseThrow(() -> new RessourceNotFoundException("Contact does not exist 2222")));
    }
}
