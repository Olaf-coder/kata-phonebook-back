package com.kata.kataphonebookback.controller;

import com.kata.kataphonebookback.domain.model.ContactEntity;
import com.kata.kataphonebookback.service.Contact;
import com.kata.kataphonebookback.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

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
            @ApiResponse(responseCode = "200", description = "Contacts récupérés",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Contact.class)
                    )}),
            @ApiResponse(responseCode = "400", description = "Echec de la récuperation",
                    content = @Content)
    })

    public List<Contact> getAllContact() {
        return contactService.getAllContacts();
    }

    @GetMapping("/{contactId}")
    @Operation(summary = "Recuperation d'un contact précis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact récupéré",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Contact.class)
            )}),
            @ApiResponse(responseCode = "400", description = "Echec de la récuperation", content = @Content )
    })
    public Contact getContact(@Schema(description = "id du contact") @PathVariable Long contactId) {
        return contactService.getContactById(contactId);
    }

    @PostMapping("/")
    @Operation(summary = "Ajout d'un nouveau contact")
    public Contact addNewContact(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "contact a ajouter") @RequestBody ContactEntity contact) {
        return contactService.addNewContact(contact);
    }


    @DeleteMapping("/{contactId}")
    @Operation(summary= "Suppression d'un contact précis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact supprimé",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Echec de la récuperation", content = @Content)
    })
    public void supressionContact(@Schema(description = "id du contact") @PathVariable Long contactId) {
        contactService.deleteContact(contactId);
    }
}
