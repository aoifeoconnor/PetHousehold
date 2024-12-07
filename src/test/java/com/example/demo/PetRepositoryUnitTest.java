package com.example.demo;

import com.example.demo.dtos.PetNameTypeBreed;
import com.example.demo.dtos.PetStats;
import com.example.demo.entities.Pet;
import com.example.demo.repositories.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PetRepositoryUnitTest
{
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PetRepository petRepository;


    @Test
    public void testFindPetByName() {
        List<Pet> pets = petRepository.findPetByName("Buddy");
        assertEquals(1, pets.size(), "Expected exactly one pet with the name 'Buddy'");
        Pet pet = pets.get(0);
        assertEquals("Buddy", pet.getName());
        assertEquals("Golden Retriever", pet.getBreed());
        assertEquals("Dog", pet.getAnimalType());
        assertEquals(3, pet.getAge());
    }

    @Test
    public void testFindPetByAnimalType() {
        List<Pet> dogs = petRepository.findPetByAnimalType("Dog");
        assertEquals(3, dogs.size(), "Expected: three dogs");
    }

    @Test
    public void testFindPetByBreed() {
        List<Pet> retrievers = petRepository.findPetByBreed("Golden Retriever");
        assertEquals(1, retrievers.size(), "Expected:`1 Golden Retriever");
        assertEquals("Buddy", retrievers.get(0).getName());
    }

    @Test
    public void testDeletePetByName() {
        int deletedCount = petRepository.deletePetByName("Buddy");
        assertEquals(1, deletedCount, "Expected exactly one pet to be deleted");
        List<Pet> pets = petRepository.findPetByName("Buddy");
        assertTrue(pets.isEmpty(), "Expected no pets with the name 'Buddy' after deletion");
    }

    @Test
    public void testFindAllBasicRecords() {
        List<PetNameTypeBreed> basicRecords = petRepository.findAllBasicRecords();
        assertEquals(10, basicRecords.size(), "Expected 10 basic pet records");
        PetNameTypeBreed record = basicRecords.stream()
                .filter(r -> r.name().equals("Buddy"))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Buddy record not found"));
        assertEquals("Dog", record.animalType());
        assertEquals("Golden Retriever", record.breed());
    }
}
