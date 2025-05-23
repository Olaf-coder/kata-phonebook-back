package com.kata.kataphonebookback.domain.model;

import com.kata.kataphonebookback.domain.model.entity.ContactEntity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ContactEntityTest {

    @Test
    void should_equals_return_true_when_entity_are_equals() {
        ContactEntity contactEntity1 = createContactEntity(1L, "familyName", "firstName", "email@mail.com","010203040506");
        ContactEntity contactEntity2 = createContactEntity(1L, "familyName", "firstName", "email@mail.com","010203040506");

        SoftAssertions softAssert = new SoftAssertions();
        softAssert.assertThat(contactEntity1).isEqualTo(contactEntity2);
        softAssert.assertThat(contactEntity1).hasSameHashCodeAs(contactEntity2);
        softAssert.assertAll();
    }

    @ParameterizedTest
    @MethodSource("contactProviderIncorrect")
    void should_equals_return_false_when_entity_are_not_equals(ContactEntity contactEntityIncorrect) {
        ContactEntity contactEntity1 = createContactEntity(1L, "familyName", "firstName", "email@mail.com","010203040506");

        SoftAssertions softAssert = new SoftAssertions();
        softAssert.assertThat(contactEntity1).isNotEqualTo(contactEntityIncorrect);
        softAssert.assertThat(contactEntity1).doesNotHaveSameHashCodeAs(contactEntityIncorrect);
        softAssert.assertAll();
    }

    @Test
    void should_equals_return_false_when_one_entity_is_null_or_not_same_class() {
        ContactEntity contactEntity1 = createContactEntity(1L, "familyName", "firstName", "email@mail.com","010203040506");
        ContactEntity contactAnonymous = new ContactEntity() { };


        SoftAssertions softAssert = new SoftAssertions();
        softAssert.assertThat(contactEntity1).isNotEqualTo(null);
        softAssert.assertThat(contactEntity1).isNotEqualTo(contactAnonymous);
        softAssert.assertAll();
    }
    private static Stream<Arguments> contactProviderIncorrect() {
        return Stream.of(
                Arguments.of(createContactEntity(0L, "familyName", "firstName", "email@mail.com","010203040506")),
                Arguments.of(createContactEntity(1L, "XXXXXXXXXX", "firstName", "email@mail.com","010203040506")),
                Arguments.of(createContactEntity(1L, "familyName", "XXXXXXXXXX", "email@mail.com","010203040506")),
                Arguments.of(createContactEntity(1L, "familyName", "firstName", "XXXXXXXXXX","010203040506")),
                Arguments.of(createContactEntity(1L, "familyName", "firstName", "email@mail.com","XXXXXXXXXX"))
        );
    }

    private static ContactEntity createContactEntity(long contactId, String familyName, String firstName, String email, String phoneNumber) {
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setId(contactId);
        contactEntity.setFamilyName(familyName);
        contactEntity.setFirstName(firstName);
        contactEntity.setEmail(email);
        contactEntity.setPhoneNumber(phoneNumber);

        return contactEntity;
    }

}
