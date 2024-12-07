package com.example.demo.services;

import com.example.demo.dtos.PetNameTypeBreed;
import com.example.demo.dtos.PetStats;
import com.example.demo.entities.Pet;
import com.example.demo.exceptions.BadDataException;
import com.example.demo.exceptions.NotFoundException;

import java.util.List;

public interface PetService {

    Pet createPet(Pet pet) throws BadDataException;
    List<Pet> getAllPets();
    List<PetNameTypeBreed> getPetRecords();
    Pet getPetById(Long id) throws NotFoundException;
    Pet updatePet(Long id, Pet pet) throws BadDataException, NotFoundException;
    void deletePetById(Long id) throws NotFoundException;
    void deletePetByName(String name) throws NotFoundException;
    List<Pet>getPetByAnimalType(String animalType);
    List<Pet>getPetByBreed(String breed);
    List<PetNameTypeBreed> getPetBasicInfo();
    PetStats getPetStats();
    Pet updatePetName(Long id, String newName);
}
