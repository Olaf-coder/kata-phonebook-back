package com.kata.kataphonebookback.service;

//import swagger

import io.swagger.v3.oas.annotations.media.Schema;

public record Contact (
        @Schema(description="L'identifiant unique et auto-généré du contact") Long id,
        @Schema(description="Prénom du contact") String firstName,
        @Schema(description="Nom de famille du contact") String familyName,
        @Schema(description="Numéro de téléphone, facultatif") String phoneNumber,
        @Schema(description="Adresse Mail, facultatif") String email
) {}
