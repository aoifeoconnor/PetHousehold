package com.example.demo.controllers.rest;

import com.example.demo.dtos.NewPet;
import com.example.demo.entities.Household;
import com.example.demo.entities.Pet;
import com.example.demo.exceptions.BadDataException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.services.HouseholdService;
import com.example.demo.services.PetService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/myapi/pets")
public class PetsWebService {
    private final PetService petService;
    private final HouseholdService householdService;

    @GetMapping({"", "/"})
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public Pet getPetById(@PathVariable Long id) throws NotFoundException {
        return petService.getPetById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePetById(@PathVariable Long id) {
        petService.deletePetById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pet addPet(@RequestBody @Valid NewPet newPet) throws BadDataException, NotFoundException {
        Household household = householdService.getHouseholdByEircode(newPet.householdEircode());
        Pet pet = new Pet(
                null,
                newPet.name(),
                newPet.animalType(),
                newPet.breed(),
                newPet.age(),
                household
        );
        return petService.createPet(pet);
    }

    @PatchMapping("/{id}/name/{newName}")
    public Pet updatePetName(@PathVariable Long id, @PathVariable String newName) {
        return petService.updatePetName(id, newName);
    }

}
