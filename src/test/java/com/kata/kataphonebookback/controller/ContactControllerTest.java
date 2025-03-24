package com.kata.kataphonebookback.controller;

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
    void should_call_contactService_getAllContact_when_GET_contacts_is_called() throws Exception{
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
    void should_call_getContactById_when_GET_contacts_with_id_is_called() throws Exception {
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
    void should_call_addNewContact_when_POST_contacts_with_contact_requestbody_is_called() throws Exception {
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
    void should_call_updateContact_when_PUT_contacts_with_contact_requestbody_is_called() throws Exception {
        //GIVEN
        Contact contactInput = new Contact(1L, "John", "Smith", null, null);
        Contact contactSaved = new Contact(1L, "Steven", "Seagull", "0123456789", "mine@mail.com");
        Mockito.when(contactService.updateContact(1L, contactInput)).thenReturn(contactSaved);

        String contactJson = """
        {
            "id": "1"
            "firstName": "Steven",
            "familyName": "Seagull",
            "phoneNumber": "0123456789",
            "email": "mine@mail.com"
        }
        """;

        //WHEN THEN
        mockMvc.perform(put(ENDPOINT + "/1").contentType(MediaType.APPLICATION_JSON).content(contactJson))
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
    void should_call_deleteContact_when_DELETE_contacts_with_id_is_called() throws Exception {
        //GIVEN
        Long id = 1L;
        Mockito.doNothing().when(contactService).deleteContact(id);

        //WHEN THEN
        mockMvc.perform(delete(ENDPOINT +"/"+id)).andExpect(status().isNoContent()).andReturn();

    }
}