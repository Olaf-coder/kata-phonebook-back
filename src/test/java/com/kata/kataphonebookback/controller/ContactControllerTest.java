package com.kata.kataphonebookback.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.kataphonebookback.Exceptions.InvalidDataException;
import com.kata.kataphonebookback.Exceptions.RessourceNotFoundException;
import com.kata.kataphonebookback.service.Contact;
import com.kata.kataphonebookback.service.ContactService;
import com.kata.kataphonebookback.service.ContactServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class ContactControllerTest {

    private static final String ENDPOINT = "/v1.0/contacts";
    private final ContactService contactService = Mockito.mock(ContactServiceImpl.class);

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ContactController(contactService)).build();
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(contactService);
    }

    @Test
    void should_call_contactService_and_return_200_and_Contacts_getAllContact_when_GET_contacts_is_called() throws Exception{
        //GIVEN
        List<Contact> contacts = List.of(
                new Contact(1L, "John", "Smith", null, null),
                new Contact(2L, "William", "Saurin", "saurinwilliam@mail.com", "060102030405"),
                new Contact(3L, "Sophie", "Saurin", "saurinsophie@mail.com", null),
                new Contact(4L, "Michel", "Palaref", null, null));
        Mockito.when(contactService.getAllContacts()).thenReturn(contacts);

        //WHEN THEN
        mockMvc.perform(get(ENDPOINT + "/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].firstName", is("John")))
                .andExpect(jsonPath("$.[0].familyName", is("Smith")))
                .andExpect(jsonPath("$.[0].phoneNumber", is(nullValue())))
                .andExpect(jsonPath("$.[0].email", is(nullValue())))
                .andReturn();

    }

    @Test
    void should_call_getContactById_and_return_200_and_Contact_when_GET_contacts_with_id_is_called() throws Exception {
        //GIVEN
        Contact contact = new Contact(1L, "John", "Smith", null, null);
        Mockito.when(contactService.getContactById(1L)).thenReturn(Optional.of(contact));

        //WHEN THEN
        mockMvc.perform(get(ENDPOINT +"/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.familyName", is("Smith")))
                .andExpect(jsonPath("$.phoneNumber", is(nullValue())))
                .andExpect(jsonPath("$.email", is(nullValue())))
                .andReturn();

    }

    @Test
    void should_call_getContactById_and_return_404_and_Contact_when_GET_contacts_with_id_is_called() throws Exception {
        //GIVEN
        Long contactId = 50L;
        Mockito.when(contactService.getContactById(contactId)).thenReturn(Optional.empty());

        //WHEN THEN
        mockMvc.perform(get(ENDPOINT +"/" + contactId))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void should_call_addNewContact_and_return_201_and_Contact_when_POST_contacts_with_contact_requestbody_is_called() throws Exception {
        //GIVEN
        Contact contactInput = new Contact(null, "John", "Smith", null, null);
        Contact contactSaved = new Contact(1L, "John", "Smith", null, null);
        Mockito.when(contactService.addNewContact(contactInput)).thenReturn(contactSaved);

        String contactJson = """
        {
            "firstName": "John",
            "familyName": "Smith"
        }
        """;

        //WHEN THEN
        mockMvc.perform(post(ENDPOINT + "/").contentType(MediaType.APPLICATION_JSON).content(contactJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.familyName", is("Smith")))
                .andExpect(jsonPath("$.phoneNumber", is(nullValue())))
                .andExpect(jsonPath("$.email", is(nullValue())))
                .andReturn();
    }

    @Test
    void should_call_addNewContact_and_return_400_when_POST_contacts_with_bad_Contact_requestbody_is_called() throws Exception {
        //GIVEN
        Contact contactBad = new Contact(null, "John", null, null, null);
        Mockito.when(contactService.addNewContact(contactBad)).thenThrow(new InvalidDataException("bad datas"));

        String contactJson = """
        {
            "firstName": "John"
        }
        """;

        //WHEN THEN
        mockMvc.perform(post(ENDPOINT + "/").contentType(MediaType.APPLICATION_JSON).content(contactJson))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void should_call_updateContact_and_return_200_and_updated_Contact_when_PUT_contacts_with_contact_requestbody_is_called() throws Exception {
        //GIVEN
        Long contactId = 1L;
        Contact contactToUpdate = new Contact(1L, "Steven", "Seagull", "0123456789", "mine@mail.com");

        Mockito.when(contactService.updateContact(1L, contactToUpdate)).thenReturn(contactToUpdate);


        //WHEN THEN
        mockMvc.perform(put(ENDPOINT + "/" + contactId).content(new ObjectMapper().writeValueAsString(contactToUpdate)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Steven")))
                .andExpect(jsonPath("$.familyName", is("Seagull")))
                .andExpect(jsonPath("$.phoneNumber", is("0123456789")))
                .andExpect(jsonPath("$.email", is("mine@mail.com")))
                .andReturn();
    }

    @Test
    void should_call_updateContact_and_return_400_when_PUT_contacts_with_bad_Contact_requestbody_is_called() throws Exception {
        //GIVEN
        Long contactId = 1L;
        Contact contactToUpdate = new Contact(1L, "Steven", null, "0123456789", "mine@mail.com");

        Mockito.when(contactService.updateContact(1L, contactToUpdate)).thenThrow(new InvalidDataException("bad datas"));


        //WHEN THEN
        mockMvc.perform(put(ENDPOINT + "/" + contactId).content(new ObjectMapper().writeValueAsString(contactToUpdate)).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void should_call_updateContact_and_return_404_when_PUT_contacts_with_id_in_url_not_found_is_called() throws Exception {
        //GIVEN
        Long contactId = 1L;
        Contact contactToUpdate = new Contact(1L, "Steven", null, "0123456789", "mine@mail.com");

        Mockito.when(contactService.updateContact(1L, contactToUpdate)).thenThrow(new RessourceNotFoundException("Contact does not exist"));


        //WHEN THEN
        mockMvc.perform(put(ENDPOINT + "/" + contactId).content(new ObjectMapper().writeValueAsString(contactToUpdate)).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void should_call_deleteContact_when_DELETE_contacts_with_id_is_called() throws Exception {
        //GIVEN
        Long id = 1L;
        Mockito.doNothing().when(contactService).deleteContact(id);

        //WHEN THEN
        mockMvc.perform(delete(ENDPOINT +"/"+id)).andExpect(status().isNoContent()).andReturn();

    }
}