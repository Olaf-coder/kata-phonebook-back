package com.kata.kataphonebookback.domain.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record ContactDto(
        @Schema(description="L'identifiant unique et auto-généré du contact", accessMode = Schema.AccessMode.READ_ONLY)
        @Nullable
        Long id,

        @Schema(description="Prénom du contact")
        @NotBlank(message = "Le prénom est obligatoire")
        String firstName,

        @Schema(description="Nom de famille du contact")
        @NotBlank(message="Le nom de famille est obligatoire")
        String familyName,

        @Schema(description="Numéro de téléphone, facultatif")
        @Nullable
        String phoneNumber,

        @Schema(description="Adresse Mail, facultatif")
        @Nullable
        String email
) {}
