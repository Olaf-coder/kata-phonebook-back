package com.kata.kataphonebookback.service;

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
    //todo methodes en erreur

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
    void should_call_getReferenceById_once_with_correct_values_and_return_existing_contact_when_getContactById_is_called_with_correct_id() {
        //GIVEN
        Long id = 1L;

        ContactEntity existingContactEntity = createExistingContactEntity();
        Contact expectedContact = createExpectedContact();
        when(contactRepository.getReferenceById(id)).thenReturn(existingContactEntity);


        //WHEN
        Contact actualContact = contactService.getContactById(id);


        //THEN
        verify(contactRepository, times(1)).getReferenceById(id);
        assertThat(actualContact).isEqualTo(expectedContact);
    }
    //todo invalid parameter => exception

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
    //todo invalid parameter => exception

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
