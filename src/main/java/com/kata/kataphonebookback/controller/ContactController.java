package com.kata.kataphonebookback.controller;

import com.kata.kataphonebookback.service.Contact;
import com.kata.kataphonebookback.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/v1.0/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/")
    @Operation(summary = "Recuperation des tous les contacts du repertoire")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commentaires",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Contact.class)
                    )}),
            @ApiResponse(responseCode = "400", description = "Echec de la récuperation",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Contact.class)
                    )})
    })
    public List<Contact> getAllContact() {
        return contactService.getAllContacts();
    }

    @GetMapping("/{contactId}")
    @Operation(summary = "Recuperation d'un intervenant précis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Commentaires",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Contact.class)
            )}),
            @ApiResponse(responseCode = "400", description = "Echec de la récuperation",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Contact.class)
                    )})
    })
    public Contact getContact(@Schema(description = "id du contact") @PathVariable Long contactId) {
        return contactService.getContactById(contactId);
    }
}
