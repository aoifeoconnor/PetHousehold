package com.example.demo.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewHousehold(
        @NotEmpty @NotNull(message = "Eircode is required")
        String eircode,

        @Min(value = 0, message = "Must be 0 or greater")
        int numberOfOccupants,

        @Min(value = 1, message = "Must be 1 or greater")
        int maxNumberOfOccupants,

        @NotNull(message = "Must not be null")
        Boolean ownerOccupied
) {}