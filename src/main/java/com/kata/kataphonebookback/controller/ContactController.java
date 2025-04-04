package com.kata.kataphonebookback.controller;

import com.kata.kataphonebookback.exceptions.InvalidDataException;
import com.kata.kataphonebookback.exceptions.RessourceNotFoundException;
import com.kata.kataphonebookback.domain.model.dto.ContactDto;
import com.kata.kataphonebookback.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


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
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ContactDto.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "Echec de la récuperation",
                    content = @Content)
    })
    public ResponseEntity<List<ContactDto>> getAllContact() {
        return new ResponseEntity<>(contactService.getAllContacts(), HttpStatus.OK);
    }

    @GetMapping("/{contactId}")
    @Operation(summary = "Recuperation d'un contact précis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact récupéré",
                content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ContactDto.class)
            )}),
            @ApiResponse(responseCode = "404", description = "Echec de la récuperation, la ressource n'existe pas", content = @Content )
    })
    public ResponseEntity<ContactDto> getContact(@Schema(description = "id du contact") @PathVariable Long contactId) {
        Optional<ContactDto> contactOpt = contactService.getContactById(contactId);
        return contactOpt.map(contact -> new ResponseEntity<>(contact, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping("/")
    @Operation(summary = "Ajout d'un nouveau contact")
    public ResponseEntity<ContactDto> createNewContact(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "contact a ajouter") @RequestBody ContactDto contact) {
        try {
            return new ResponseEntity<>(contactService.addNewContact(contact), HttpStatus.CREATED);
        } catch (InvalidDataException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/{contactId}")
    @Operation(summary= "Suppression d'un contact précis")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contact supprimé",
                    content = @Content)
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void supressionContact(@Schema(description = "id du contact") @PathVariable Long contactId) {
        contactService.deleteContact(contactId);
    }

    @PutMapping("/{contactId}")
    @Operation(summary="Mise à jour d'un contact précis")
    public ResponseEntity<ContactDto> updateContact(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "contact a mettre à jour") @RequestBody ContactDto contact, @Schema(description = "id du contact") @PathVariable Long contactId) {
        try {
            return new ResponseEntity<>(contactService.updateContact(contactId, contact), HttpStatus.OK);
        } catch (RessourceNotFoundException e) {
            return new ResponseEntity<> (HttpStatus.NOT_FOUND);
        } catch (InvalidDataException e) {
            return new ResponseEntity<> (HttpStatus.BAD_REQUEST);
        }
    }

}
