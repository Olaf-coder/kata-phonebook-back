package com.kata.kataphonebookback.controller;

import com.kata.kataphonebookback.service.ContactService;
import com.kata.kataphonebookback.service.ContactServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

class ContactControllerTest {

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
    void getAllContact() {
    }

    @Test
    void getContact() {
    }

    @Test
    void createNewContact() {
    }

    @Test
    void supressionContact() {
    }
}