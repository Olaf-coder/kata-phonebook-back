package com.kata.kataphonebookback.service;

import com.kata.kataphonebookback.domain.mapper.ContactMapper;
import com.kata.kataphonebookback.domain.model.dto.ContactDto;
import com.kata.kataphonebookback.exceptions.InvalidDataException;
import com.kata.kataphonebookback.exceptions.RessourceNotFoundException;
import com.kata.kataphonebookback.domain.model.entity.ContactEntity;
import com.kata.kataphonebookback.domain.repository.ContactRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
    private ContactMapper contactMapper;

    @Mock
    private ContactRepository contactRepository;

    @BeforeEach
    void setUp() {
        contactService = new ContactServiceImpl(contactRepository, contactMapper);
    }

//CREATE
    @Test
    void should_call_save_once_with_correct_values_and_return_saved_contact_when_addNewContact_is_called_with_full_contact_and_no_id() {
        //GIVEN
        ContactDto inputDto = new ContactDto(null,"John", "Smith", "john.smith@gmail.com", "0102030405");
        ContactDto expectedDto = new ContactDto(1L,"John", "Smith", "john.smith@gmail.com", "0102030405");

        ContactEntity inputEntity = createContactEntity(null,"John", "Smith", "john.smith@gmail.com", "0102030405");
        ContactEntity savedEntity = createContactEntity(1L,"John", "Smith", "john.smith@gmail.com", "0102030405");

        when(contactMapper.toEntity(inputDto)).thenReturn(inputEntity);
        when(contactRepository.save(inputEntity)).thenReturn(savedEntity);
        when(contactMapper.toDto(savedEntity)).thenReturn(expectedDto);

        //WHEN
        ContactDto actualContact = contactService.addNewContact(inputDto);


        //THEN
        verify(contactMapper, times(1)).toEntity(inputDto);
        verify(contactRepository, times(1)).save(inputEntity);
        verify(contactMapper, times(1)).toDto(savedEntity);
        assertThat(actualContact).isEqualTo(expectedDto);

    }

    @Test
    void should_call_save_once_with_correct_values_and_return_saved_contact_when_addNewContact_is_called_with_only_mandatory_data_and_no_id() {
        //GIVEN
        ContactDto inputDto = new ContactDto(null, "John", "Smith", null, null);
        ContactDto expectedContact = new ContactDto(1L, "John", "Smith", null, null);

        ContactEntity inputEntity = createContactEntity(null, "John", "Smith", null, null);
        ContactEntity savedEntity = createContactEntity(1L, "John", "Smith", null, null);

        when(contactMapper.toEntity(inputDto)).thenReturn(inputEntity);
        when(contactRepository.save(inputEntity)).thenReturn(savedEntity);
        when(contactMapper.toDto(savedEntity)).thenReturn(expectedContact);


        //WHEN
        ContactDto actualContact = contactService.addNewContact(inputDto);


        //THEN
        verify(contactRepository, times(1)).save(inputEntity);
        verify(contactMapper, times(1)).toDto(savedEntity);
        assertThat(actualContact).isEqualTo(expectedContact);

    }

    @Test
    void should_not_call_save_and_throw_InvalidDataException_when_addNewContact_is_called_with_not_all_mandatory_datas() {
        //GIVEN
        ContactDto inputDto = new ContactDto(null, "John", null, null, null);

        //WHEN THEN
        verify(contactRepository, never()).save(any(ContactEntity.class));
        assertThatExceptionOfType(InvalidDataException.class).isThrownBy(() -> contactService.addNewContact(inputDto));

    }

//UPDATE
    @Test
    void should_call_save_with_new_correct_datas_and_return_updated_contact_when_updateContact_is_called_with_full_contact() {
        //GIVEN
        Long contactId = 1L;

        ContactDto expectedDto = new ContactDto(1L, "Sarah", "Connor", null, null);
        ContactDto existingDto = new ContactDto(1L,"John", "Smith", "john.smith@gmail.com", "0102030405");

        ContactEntity expectedEntity = createContactEntity(1L, "Sarah", "Connor", null, null);
        ContactEntity existingContactEntity = createContactEntity(1L,"John", "Smith", "john.smith@gmail.com", "0102030405");

        when(contactRepository.findById(contactId)).thenReturn(Optional.of(existingContactEntity));
        when(contactMapper.toDto(existingContactEntity)).thenReturn(existingDto);
        when(contactRepository.save(expectedEntity)).thenReturn(expectedEntity);
        when(contactMapper.toDto(expectedEntity)).thenReturn(expectedDto);

        //WHEN
        ContactDto actualContact = contactService.updateContact(contactId, expectedDto);

        //THEN
        verify(contactRepository, times(1)).save(expectedEntity);
        verify(contactMapper, times(1)).toDto(expectedEntity);
        verify(contactMapper, times(1)).toDto(existingContactEntity);
        assertThat(actualContact).isEqualTo(expectedDto);
    }

    @Test
    void should_call_save_with_new_correct_datas_and_url_id_and_return_updated_contact_when_updateContact_is_called_with_full_contact() {
        //GIVEN
        Long contactId = 3L;

        ContactDto expectedDto = new ContactDto(3L, "John", "Smith", "john.smith@gmail.com", "0102030405");

        ContactEntity existingEntity = createContactEntity(1L,"John", "Smith", "john.smith@gmail.com", "0102030405");
        ContactEntity savedContactEntity = createContactEntity(3L,"John", "Smith", "john.smith@gmail.com", "0102030405");

        when(contactRepository.findById(contactId)).thenReturn(Optional.of(existingEntity));
        when(contactRepository.save(savedContactEntity)).thenReturn(savedContactEntity);
        when(contactMapper.toDto(savedContactEntity)).thenReturn(expectedDto);
        when(contactMapper.toDto(existingEntity)).thenReturn(expectedDto);

        //WHEN
        ContactDto actualContact = contactService.updateContact(contactId, expectedDto);

        //THEN
        verify(contactRepository, times(1)).save(savedContactEntity);
        verify(contactMapper, times(1)).toDto(savedContactEntity);
        verify(contactMapper, times(1)).toDto(existingEntity);
        assertThat(actualContact).isEqualTo(expectedDto);
    }

    @Test
    void should_throw_RessourceNotFoundException_when_updateContact_is_called_with_contact_and_unknown_id() {
        //GIVEN
        Long contactId = 5000L;

        ContactDto contactUnknownId = new ContactDto(5000L, "John", "Smith", "0102030405", "mail@mail.com");

        when(contactRepository.findById(contactId)).thenReturn(Optional.empty());

        //WHEN THEN
        verify(contactRepository, never()).save(any(ContactEntity.class));
        assertThatExceptionOfType(RessourceNotFoundException.class).isThrownBy(() -> contactService.updateContact(contactId, contactUnknownId));
    }

    @Test
    void should_throw_InvalidDataException_when_updateContact_is_called_with_contact_and_no_firstName() {
        //GIVEN
        Long contactId = 1L;

        ContactDto dtoUnknownFirstName = new ContactDto(1L, null, "Smith", "0102030405", "mail@mail.com");

        ContactEntity existingEntity = createContactEntity(1L,"John", "Smith", "john.smith@gmail.com", "0102030405");

        when(contactRepository.findById(contactId)).thenReturn(Optional.of(existingEntity));
        when(contactMapper.toDto(any(ContactEntity.class))).thenReturn(dtoUnknownFirstName);


        //WHEN THEN
        assertThatExceptionOfType(InvalidDataException.class).isThrownBy(() -> contactService.updateContact(contactId, dtoUnknownFirstName));
        verify(contactRepository, never()).save(any(ContactEntity.class));
    }

    @Test
    void should_throw_InvalidDataException_when_updateContact_is_called_with_contact_and_familyName_Blank() {
        //GIVEN
        Long contactId = 1L;

        ContactDto dtoUnknownFamilyName = new ContactDto(1L, "John", "   ", "0102030405", "mail@mail.com");

        ContactEntity existingEntity = createContactEntity(1L,"John", "Smith", "john.smith@gmail.com", "0102030405");

        when(contactRepository.findById(contactId)).thenReturn(Optional.of(existingEntity));
        when(contactMapper.toDto(any(ContactEntity.class))).thenReturn(dtoUnknownFamilyName);


        //WHEN THEN
        verify(contactRepository, never()).save(any(ContactEntity.class));
        assertThatExceptionOfType(InvalidDataException.class).isThrownBy(() -> contactService.updateContact(contactId, dtoUnknownFamilyName));
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
    //TODO return not an Optional but value directly
    @Test
    void should_call_getReferenceById_once_and_return_existing_contact_when_getContactById_is_called_with_correct_id() {
        //GIVEN
        Long id = 1L;

        ContactDto expectedDto = new ContactDto(1L,"John", "Smith", "john.smith@gmail.com", "0102030405");new ContactDto(1L,"John", "Smith", "john.smith@gmail.com", "0102030405");

        ContactEntity existingEntity = createContactEntity(1L,"John", "Smith", "john.smith@gmail.com", "0102030405");

        when(contactRepository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(contactMapper.toDto(existingEntity)).thenReturn(expectedDto);


        //WHEN
        Optional<ContactDto> actualContact = contactService.getContactById(id);


        //THEN
        verify(contactRepository, times(1)).findById(id);
        assertThat(actualContact).isNotEmpty().contains(expectedDto);
    }

    //TODO and raise RessourceNotFoundException.
    @Test
    void should_call_getReferenceById_once_and_return_Optional_empty_when_getContactById_is_called_with_non_existing_id() {
        //GIVEN
        Long id = 50L;

        when(contactRepository.findById(id)).thenReturn(Optional.empty());

        //WHEN
        Optional<ContactDto> actualContact = contactService.getContactById(id);


        //THEN
        verify(contactRepository, times(1)).findById(id);
        assertThat(actualContact).isEmpty();
    }

    @Test
    void should_call_findAll_once_and_return_existing_contact_when_getAllContacts_is_called_with_correct_id() {
        //GIVEN
        List<ContactDto> expectedDtos = createExpectedContactDtos();

        List<ContactEntity> existingEntities = createExistingContactEntities();

        when(contactRepository.findAll()).thenReturn(existingEntities);
        when(contactMapper.toDto(any(ContactEntity.class)))
                .thenReturn(expectedDtos.get(0),
                        expectedDtos.get(1),
                        expectedDtos.get(2),
                        expectedDtos.get(3));
        //WHEN
        List<ContactDto> actualDtos = contactService.getAllContacts();

        //THEN
        verify(contactRepository, times(1)).findAll();
        verify(contactMapper, times(expectedDtos.size())).toDto(any(ContactEntity.class));
        assertThat(actualDtos).isEqualTo(expectedDtos);
    }

    private List<ContactEntity> createExistingContactEntities()
    {
        return List.of(createContactEntity(1L, "John", "Smith", null, null),
                createContactEntity(2L, "William", "Saurin", "saurinwilliam@mail.com", "060102030405"),
                createContactEntity(3L, "Sophie", "Saurin", "saurinsophie@mail.com", null),
                createContactEntity(4L, "Michel", "Palaref", null, null)
        );
    }

    private List<ContactDto> createExpectedContactDtos()
    {
        return List.of(new ContactDto(1L, "John", "Smith", null, null),
                new ContactDto(2L, "William", "Saurin", "saurinwilliam@mail.com", "060102030405"),
                new ContactDto(3L, "Sophie", "Saurin", "saurinsophie@mail.com", null),
                new ContactDto(4L, "Michel", "Palaref", null, null)
        );
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
}