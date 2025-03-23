package com.kata.kataphonebookback.service;

import com.kata.kataphonebookback.domain.model.ContactEntity;
import com.kata.kataphonebookback.domain.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    private ContactServiceImpl contactService;

    @Mock
    private ContactRepository contactRepository;

    @BeforeEach
    void setUp() {
        contactService = new ContactServiceImpl(contactRepository);
    }

    private ContactEntity createExistingContactEntity() {
        return createContactEntity(1L,"John", "Smith", "john.smith@gmail.com", "0102030405");
    }

    private Contact createExpectedContact() {
        return createContact(1L,"John", "Smith", "john.smith@gmail.com", "0102030405");
    }

    private ContactEntity createContactEntity(Long id, String firstName, String familyName, String email, String phoneNumber) {
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setId(id);
        contactEntity.setFirstName(firstName);
        contactEntity.setFamilyName(familyName);
        contactEntity.setEmail(email);
        contactEntity.setPhoneNumber(phoneNumber);
        return contactEntity;
    }

    private Contact createContact(Long id, String firstName, String familyName, String email, String phoneNumber) {
        return new Contact(id, firstName, familyName, phoneNumber, email);
    }

    private Contact convertEntityToContact(ContactEntity contactEntity) {
        return new Contact(contactEntity.getId(), contactEntity.getFirstName(), contactEntity.getFamilyName(), contactEntity.getPhoneNumber(), contactEntity.getEmail());
    }

    @Test
    void should_call_save_once_with_correct_values_when_addNewContact_is_called_with_full_contact_and_no_id() {
        //GIVEN
        ContactEntity inputContactEntity = createExistingContactEntity();
        inputContactEntity.setId(null);

        ContactEntity savedContactEntity = createExistingContactEntity();
        Contact expectedContact = createExpectedContact();

        Mockito.when(contactRepository.save(inputContactEntity)).thenReturn(savedContactEntity);


        //WHEN
        Contact actualContact = contactService.addNewContact(inputContactEntity);


        //THEN
        verify(contactRepository, times(1)).save(inputContactEntity);
        assertThat(actualContact).isEqualTo(expectedContact);

    }

    @Test
    void should_call_save_once_with_correct_values_when_addNewContact_is_called_with_only_mandatory_data_and_no_id() {
        //GIVEN
        ContactEntity inputContactEntity = createContactEntity(null, "John", "Smith", null, null);
        ContactEntity savedContactEntity = createContactEntity(1L, "John", "Smith", null, null);
        Contact expectedContact = createContact(1L, "John", "Smith", null, null);
        Mockito.when(contactRepository.save(inputContactEntity)).thenReturn(savedContactEntity);


        //WHEN
        Contact actualContact = contactService.addNewContact(inputContactEntity);


        //THEN
        verify(contactRepository, times(1)).save(inputContactEntity);
        assertThat(actualContact).isEqualTo(expectedContact);

    }

    @Test
    void deleteContact() {
        //GIVEN


        //WHEN


        //THEN

    }

    @Test
    void getAllContacts() {
        //GIVEN


        //WHEN


        //THEN

    }

    @Test
    void getContactById() {
        //GIVEN


        //WHEN


        //THEN

    }
}