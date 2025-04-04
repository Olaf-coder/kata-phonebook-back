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
        Optional<ContactDto> contactOptional = this.getContactById(contactId);

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
            return contactMapper.toDto(contactRepository.save(contactEntityToSave));
        }
    }

    @Override
    public List<ContactDto> getAllContacts() {
        List<ContactEntity> contacts = contactRepository.findAll();
        return contacts.stream().map(contactMapper::toDto).toList();

//        return contacts.stream().map(this::convertEntityToContact).toList();
    }

    @Override
    public Optional<ContactDto> getContactById(Long id) {
        Optional<ContactEntity> contactEntityOptional = contactRepository.findById(id);
        return contactEntityOptional.map(contactMapper::toDto);
//        return contactEntityOptional.map(this::convertEntityToContact);
    }

//    private ContactDto convertEntityToContact(ContactEntity contactEntity) {// appeler mapper, et creer un mapper a coté
//        return new ContactDto(
//                contactEntity.getId(),
//                contactEntity.getFirstName(),
//                contactEntity.getFamilyName(),
//                contactEntity.getPhoneNumber(),
//                contactEntity.getEmail()
//        );
//    }
//
//    private ContactEntity convertContactToEntity(ContactDto contact) {// appeler mapper et creer un mapper a côté, mappstruct, doser, ...
//        ContactEntity contactEntity = new ContactEntity();
//        contactEntity.setId(contact.id());
//        contactEntity.setFirstName(contact.firstName());
//        contactEntity.setFamilyName(contact.familyName());
//        contactEntity.setPhoneNumber(contact.phoneNumber());
//        contactEntity.setEmail(contact.email());
//        return contactEntity;
//    }
}
