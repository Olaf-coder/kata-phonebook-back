package com.kata.kataphonebookback.service;

import com.kata.kataphonebookback.Exceptions.InvalidDataException;
import com.kata.kataphonebookback.Exceptions.RessourceNotFoundException;
import com.kata.kataphonebookback.domain.model.ContactEntity;
import com.kata.kataphonebookback.domain.repository.ContactRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
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

//CREATE
    @Test
    void should_call_save_once_with_correct_values_and_return_saved_contact_when_addNewContact_is_called_with_full_contact_and_no_id() {
        //GIVEN
        ContactEntity inputContactEntity = createExistingContactEntity();
        inputContactEntity.setId(null);
        Contact inputContactTranslated = new Contact(null,"John", "Smith", "john.smith@gmail.com", "0102030405");

        ContactEntity savedContactEntity = createExistingContactEntity();
        Contact expectedContact = createExpectedContact();
        Mockito.when(contactRepository.save(inputContactEntity)).thenReturn(savedContactEntity);

        //WHEN
        Contact actualContact = contactService.addNewContact(inputContactTranslated);


        //THEN
        verify(contactRepository, times(1)).save(inputContactEntity);
        assertThat(actualContact).isEqualTo(expectedContact);

    }

    @Test
    void should_call_save_once_with_correct_values_and_return_saved_contact_when_addNewContact_is_called_with_only_mandatory_data_and_no_id() {
        //GIVEN
        ContactEntity inputContactEntity = createContactEntity(null, "John", "Smith", null, null);
        Contact inputContactTranslated = new Contact(null, "John", "Smith", null, null);

        ContactEntity savedContactEntity = createContactEntity(1L, "John", "Smith", null, null);
        Contact expectedContact = new Contact(1L, "John", "Smith", null, null);
        Mockito.when(contactRepository.save(inputContactEntity)).thenReturn(savedContactEntity);


        //WHEN
        Contact actualContact = contactService.addNewContact(inputContactTranslated);


        //THEN
        verify(contactRepository, times(1)).save(inputContactEntity);
        assertThat(actualContact).isEqualTo(expectedContact);

    }

    @Test
    void should_not_call_save_and_throw_InvalidDataException_when_addNewContact_is_called_with_not_all_mandatory_datas() {
        //GIVEN
        Contact inputContactTranslated = new Contact(null, "John", null, null, null);

        //WHEN THEN
        verify(contactRepository, never()).save(any(ContactEntity.class));
        assertThatExceptionOfType(InvalidDataException.class).isThrownBy(() -> contactService.addNewContact(inputContactTranslated));

    }

//UPDATE
    @Test
    void should_call_save_with_new_correct_datas_and_return_updated_contact_when_updateContact_is_called_with_full_contact() {
        //GIVEN
        Long contactId = 1L;
        Contact expectedContact = createExpectedContact();
        ContactEntity existingContactEntity = createExistingContactEntity();
        Mockito.when(contactRepository.findById(expectedContact.id())).thenReturn(Optional.of(existingContactEntity));
        Mockito.when(contactRepository.save(existingContactEntity)).thenReturn(existingContactEntity);

        //WHEN
        Contact actualContact = contactService.updateContact(contactId, expectedContact);

        //THEN
        verify(contactRepository, times(1)).save(existingContactEntity);
        assertThat(actualContact).isEqualTo(expectedContact);
    }

    @Test
    void should_call_save_with_new_correct_datas_and_url_id_and_return_updated_contact_when_updateContact_is_called_with_full_contact() {
        //GIVEN
        Long contactId = 3L;
        Contact expectedContact = new Contact(3L, "John", "Smith", "john.smith@gmail.com", "0102030405");
        ContactEntity existingContactEntity = createContactEntity(1L,"John", "Smith", "john.smith@gmail.com", "0102030405");
        ContactEntity savedContactEntity = createContactEntity(3L,"John", "Smith", "john.smith@gmail.com", "0102030405");


        Mockito.when(contactRepository.findById(expectedContact.id())).thenReturn(Optional.of(existingContactEntity));
        Mockito.when(contactRepository.save(savedContactEntity)).thenReturn(savedContactEntity);

        //WHEN
        Contact actualContact = contactService.updateContact(contactId, expectedContact);

        //THEN
        verify(contactRepository, times(1)).save(savedContactEntity);
        assertThat(actualContact).isEqualTo(expectedContact);
    }

    @Test
    void should_throw_RessourceNotFoundException_when_updateContact_is_called_with_contact_and_unknown_id() {
        //GIVEN
        Long contactId = 5000L;
        Contact contactUnknownId = new Contact(5000L, "John", "Smith", "0102030405", "mail@mail.com");
        Mockito.when(contactRepository.findById(contactUnknownId.id())).thenReturn(Optional.empty());

        //WHEN THEN
        verify(contactRepository, never()).save(any(ContactEntity.class));
        assertThatExceptionOfType(RessourceNotFoundException.class).isThrownBy(() -> contactService.updateContact(contactId, contactUnknownId));
    }

    @Test
    void should_throw_InvalidDataException_when_updateContact_is_called_with_contact_and_no_firstName() {
        //GIVEN
        Long contactId = 1L;
        ContactEntity contactEntityExisting = createExistingContactEntity();
        Contact contactUnknownFirstName = new Contact(1L, null, "Smith", "0102030405", "mail@mail.com");
        Mockito.when(contactRepository.findById(contactUnknownFirstName.id())).thenReturn(Optional.of(contactEntityExisting));


        //WHEN THEN
        verify(contactRepository, never()).save(any(ContactEntity.class));
        assertThatExceptionOfType(InvalidDataException.class).isThrownBy(() -> contactService.updateContact(contactId, contactUnknownFirstName));
    }

    @Test
    void should_throw_InvalidDataException_when_updateContact_is_called_with_contact_and_familyName_Blank() {
        //GIVEN
        Long contactId = 1L;
        ContactEntity contactEntityExisting = createExistingContactEntity();
        Contact contactUnknownFamilyName = new Contact(1L, "John", "   ", "0102030405", "mail@mail.com");
        Mockito.when(contactRepository.findById(contactUnknownFamilyName.id())).thenReturn(Optional.of(contactEntityExisting));


        //WHEN THEN
        verify(contactRepository, never()).save(any(ContactEntity.class));
        assertThatExceptionOfType(InvalidDataException.class).isThrownBy(() -> contactService.updateContact(contactId, contactUnknownFamilyName));
    }

//DELETE
    @Test
    void should_call_deleteById_once_when_deleteContact_is_called_with_id() {
        //GIVEN
        Long id = 1L;
        doNothing().when(contactRepository).deleteById(id);


        //WHEN
        contactService.deleteContact(id);


        //THEN
        verify(contactRepository, times(1)).deleteById(id);
        Assertions.assertThatNoException().isThrownBy(()->contactService.deleteContact(id));
    }

//READ
    @Test
    void should_call_getReferenceById_once_and_return_existing_contact_when_getContactById_is_called_with_correct_id() {
        //GIVEN
        Long id = 1L;

        ContactEntity existingContactEntity = createExistingContactEntity();
        Contact expectedContact = createExpectedContact();
        when(contactRepository.findById(id)).thenReturn(Optional.of(existingContactEntity));


        //WHEN
        Optional<Contact> actualContact = contactService.getContactById(id);


        //THEN
        verify(contactRepository, times(1)).findById(id);
        assertThat(actualContact).isNotEmpty().contains(expectedContact);
    }

    @Test
    void should_call_getReferenceById_once_and_return_Optional_empty_when_getContactById_is_called_with_non_existing_id() {
        //GIVEN
        Long id = 50L;

        when(contactRepository.findById(id)).thenReturn(Optional.empty());


        //WHEN
        Optional<Contact> actualContact = contactService.getContactById(id);


        //THEN
        verify(contactRepository, times(1)).findById(id);
        assertThat(actualContact).isEmpty();
    }

    @Test
    void should_call_findAll_once_and_return_existing_contact_when_getAllContacts_is_called_with_correct_id() {
        //GIVEN
        List<ContactEntity> contactEntities = createExistingContactEntities();
        List<Contact> expectedContacts = createExpectedContacts();
        Mockito.when(contactRepository.findAll()).thenReturn(contactEntities);

        //WHEN
        List<Contact> actualContacts = contactService.getAllContacts();

        //THEN
        verify(contactRepository, times(1)).findAll();
        assertThat(actualContacts).isEqualTo(expectedContacts);
    }

    private List<ContactEntity> createExistingContactEntities()
    {
        return List.of(createContactEntity(1L, "John", "Smith", null, null),
                createContactEntity(2L, "William", "Saurin", "saurinwilliam@mail.com", "060102030405"),
                createContactEntity(3L, "Sophie", "Saurin", "saurinsophie@mail.com", null),
                createContactEntity(4L, "Michel", "Palaref", null, null)
        );
    }

    private List<Contact> createExpectedContacts()
    {
        return List.of(new Contact(1L, "John", "Smith", null, null),
                new Contact(2L, "William", "Saurin", "saurinwilliam@mail.com", "060102030405"),
                new Contact(3L, "Sophie", "Saurin", "saurinsophie@mail.com", null),
                new Contact(4L, "Michel", "Palaref", null, null)
        );
    }

    private ContactEntity createExistingContactEntity() {
        return createContactEntity(1L,"John", "Smith", "john.smith@gmail.com", "0102030405");
    }

    private Contact createExpectedContact() {
        return new Contact(1L,"John", "Smith", "john.smith@gmail.com", "0102030405");
    }

    private ContactEntity createContactEntity(Long id, String firstName, String familyName, String phoneNumber, String email) {
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setId(id);
        contactEntity.setFirstName(firstName);
        contactEntity.setFamilyName(familyName);
        contactEntity.setEmail(email);
        contactEntity.setPhoneNumber(phoneNumber);
        return contactEntity;
    }

    private Contact convertEntityToContact(ContactEntity contactEntity) {
        return new Contact(contactEntity.getId(), contactEntity.getFirstName(), contactEntity.getFamilyName(), contactEntity.getPhoneNumber(), contactEntity.getEmail());
    }


}
