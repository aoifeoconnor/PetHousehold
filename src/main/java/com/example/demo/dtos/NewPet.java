package com.example.demo.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewPet(
        @NotEmpty @NotNull(message = "Name is required")
        String name,

        @NotEmpty @NotNull(message = "Animal type is required")
        String animalType,

        @NotEmpty @NotNull(message = "Breed is required")
        String breed,

        @Min(value = 0, message = "Age must be 0 or greater")
        int age,

        @NotEmpty @NotNull(message = "Eircode is required")
        String householdEircode
) {}
