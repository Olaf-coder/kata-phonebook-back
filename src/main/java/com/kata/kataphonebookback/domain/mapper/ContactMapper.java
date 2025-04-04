package com.kata.kataphonebookback.domain.mapper;


import com.kata.kataphonebookback.domain.model.dto.ContactDto;
import com.kata.kataphonebookback.domain.model.entity.ContactEntity;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public ContactDto toDto(ContactEntity entity) {
        return new ContactDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getFamilyName(),
                entity.getPhoneNumber(),
                entity.getEmail()
        );
    }

    public ContactEntity toEntity(ContactDto dto) {
        ContactEntity entity = new ContactEntity();
        entity.setId(dto.id());
        entity.setFirstName(dto.firstName());
        entity.setFamilyName(dto.familyName());
        entity.setPhoneNumber(dto.phoneNumber());
        entity.setEmail(dto.email());

        return entity;
    }

}
