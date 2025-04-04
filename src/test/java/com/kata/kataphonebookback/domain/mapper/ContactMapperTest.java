package com.kata.kataphonebookback.domain.mapper;

import com.kata.kataphonebookback.domain.model.dto.ContactDto;
import com.kata.kataphonebookback.domain.model.entity.ContactEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ContactMapperTest {

    private ContactMapper contactMapper;

    @BeforeEach
    void setUp() {
        contactMapper = new ContactMapper();
    }

    //DTO
    @Test
    void SHOULD_return_complete_contactDto_WHEN_toDto_is_called_with_complete_contactEntity() {

        ContactDto contactDtoActual = contactMapper.toDto(generateExpectedCompleteContactEntity());

        Assertions.assertEquals(generateExpectedCompleteContactDto(), contactDtoActual);

    }

    @Test
    void SHOULD_return_partial_contactDto_WHEN_toDto_is_called_with_partial_contactEntity() {
        ContactDto contactDtoActual = contactMapper.toDto(generateExpectedPartialContactEntity());

        Assertions.assertEquals(generateExpectedPartialContactDto(), contactDtoActual);
    }

    //ENTITY
    @Test
    void SHOULD_return_complete_contactEntity_WHEN_toEntity_is_called_with_complete_contactDto() {
        ContactEntity contactEntityActual = contactMapper.toEntity(generateExpectedCompleteContactDto());

        Assertions.assertEquals(generateExpectedCompleteContactEntity(), contactEntityActual);
    }

    @Test
    void SHOULD_return_partial_contactEntity_WHEN_toEntity_is_called_with_partial_contactDto() {
        ContactEntity contactEntityActual = contactMapper.toEntity(generateExpectedPartialContactDto());

        Assertions.assertEquals(generateExpectedPartialContactEntity(), contactEntityActual);
    }

    private ContactDto generateExpectedCompleteContactDto() {
        return new ContactDto(1L, "Prenom", "Nom", "0102030405", "mail@mail.com");
    }

    private ContactDto generateExpectedPartialContactDto() {
        return new ContactDto(1L, "Prenom", "Nom", null, null);
    }

    private ContactEntity generateExpectedCompleteContactEntity() {
        ContactEntity entity = new ContactEntity();

        entity.setId(1L);
        entity.setFirstName("Prenom");
        entity.setFamilyName("Nom");
        entity.setPhoneNumber("0102030405");
        entity.setEmail("mail@mail.com");

        return entity;
    }

    private ContactEntity generateExpectedPartialContactEntity() {
        ContactEntity entity = new ContactEntity();

        entity.setId(1L);
        entity.setFirstName("Prenom");
        entity.setFamilyName("Nom");

        return entity;
    }


}