package com.example.demo.controllers.rest;

import com.example.demo.dtos.NewHousehold;
import com.example.demo.dtos.PetStats;
import com.example.demo.entities.Household;
import com.example.demo.entities.Pet;
import com.example.demo.services.HouseholdService;
import com.example.demo.services.PetService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@AllArgsConstructor

public class GraphQLAPI {

    private final HouseholdService householdService;
    private final PetService petService;

    @QueryMapping

    public List<Household> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @QueryMapping

    public Household getHouseholdByEircode(@Argument String eircode) {
        return householdService.getHouseholdByEircode(eircode);
    }

    @QueryMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public List<Pet> getAllPetsByAnimalType(@Argument String animalType) {
        return petService.getPetByAnimalType(animalType);
    }

    @QueryMapping
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public Pet getPetById(@Argument Long id) {
        return petService.getPetById(id);
    }

    @QueryMapping
    public PetStats getPetStatistics() {
        return petService.getPetStats();
    }

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public Household createHousehold(@Argument("household") NewHousehold householdInput) {
        Household household = new Household(
                householdInput.eircode(),
                householdInput.numberOfOccupants(),
                householdInput.maxNumberOfOccupants(),
                householdInput.ownerOccupied()
        );
        return householdService.createHousehold(household);
    }

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public Boolean deleteHouseholdByEircode(@Argument String eircode) {
        householdService.deleteHouseholdByEircode(eircode);
        return true;
    }

    @MutationMapping
    @Secured("ROLE_ADMIN")
    public Boolean deletePetById(@Argument Long id) {
        petService.deletePetById(id);
        return true;
    }
}
