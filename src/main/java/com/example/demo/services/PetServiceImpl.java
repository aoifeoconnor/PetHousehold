package com.example.demo.services;

import com.example.demo.dtos.PetNameTypeBreed;
import com.example.demo.dtos.PetStats;
import com.example.demo.entities.Pet;
import com.example.demo.exceptions.BadDataException;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.PetRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PetServiceImpl implements PetService {
    private PetRepository petRepository;

    @Override
    public Pet createPet(Pet pet) throws BadDataException{
        if (pet.getName().isBlank() || pet.getAnimalType().isBlank() || pet.getBreed().isBlank()) {
            throw new BadDataException("Pet name cannot be empty");
        }
        if (pet.getAge() < 0) {
            throw new BadDataException("Pet age cannot be negative");
        }
        return petRepository.save(pet);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public List<PetNameTypeBreed> getPetRecords() {
        return List.of();
    }


    @Override
    public Pet getPetById(Long id) throws NotFoundException {
        return petRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Pet not found")
        );

    }


    @Transactional
    @Override
    public Pet updatePet(Long id, Pet pet) throws BadDataException, NotFoundException {
        Pet existingPet = getPetById(id);
        if (pet.getName().isBlank() || pet.getAnimalType().isBlank()) {
            throw new BadDataException("Cant be empty");
        }
        if (pet.getAge() < 0) {
            throw new BadDataException("cant be negative");
        }
        existingPet.setName(pet.getName());
        existingPet.setAnimalType(pet.getAnimalType());
        existingPet.setBreed(pet.getBreed());
        existingPet.setAge(pet.getAge());
        return petRepository.save(existingPet);
    }

    @Transactional
    @Override
    public void deletePetById(Long id) throws NotFoundException {
        if (!petRepository.existsById(id)) {
            throw new NotFoundException("Pet not found");
        }
        petRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void deletePetByName(String name) throws NotFoundException {
        int rowsAffected = petRepository.deletePetByName(name);
        if (rowsAffected == 0) {
            throw new NotFoundException("No pets found with name: " + name);
        }
    }

    @Override
    public List<Pet> getPetByAnimalType(String animalType) {
        return petRepository.findPetByAnimalType(animalType);
    }

    @Override
    public List<Pet> getPetByBreed(String breed) {
        return petRepository.findPetByBreed(breed);
    }

    @Override
    public List<PetNameTypeBreed> getPetBasicInfo() {
        return petRepository.findAllBasicRecords();
    }

    @Override
    public PetStats getPetStats() {
        return petRepository.getPetStats();

    }

    @Override
    public Pet updatePetName(Long id, String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Cannot be null or blank");
        }

        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet not found with ID: " + id));

        pet.setName(newName);
        return petRepository.save(pet);
    }

}
