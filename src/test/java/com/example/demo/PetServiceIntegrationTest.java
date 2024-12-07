package com.example.demo;

import com.example.demo.entities.Household;
import com.example.demo.entities.Pet;
import com.example.demo.dtos.PetStats;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.exceptions.BadDataException;
import com.example.demo.repositories.HouseholdRepository;
import com.example.demo.repositories.PetRepository;
import com.example.demo.services.PetService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(2)
public class PetServiceIntegrationTest {
    @Autowired
    PetService petService;

    @Autowired
    PetRepository petRepository;

    @Autowired
    HouseholdRepository householdRepository;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(petService);
    }

    @Nested
    @Order(1)
    class TestGets {
        @Test
        void findPets_shouldReturnPets() {
            int count = petService.getAllPets().size();
            Assertions.assertEquals(10, count);
        }

        @Test
        void findPet_shouldReturnPet() throws NotFoundException {
            Pet pet = petService.getPetById(1L);
            Assertions.assertNotNull(pet);
            Assertions.assertEquals("Buddy", pet.getName());
        }

        @Test
        void findPet_notFound_shouldThrowException() {
            Assertions.assertThrows(NotFoundException.class, () -> {
                petService.getPetById(111L);
            });
        }

        @Test
        void findPetsByType_shouldReturnFilteredPets() {
            var pets = petService.getPetByAnimalType("Dog");
            Assertions.assertTrue(pets.size() >= 3);
            Assertions.assertTrue(pets.stream().allMatch(p -> p.getAnimalType().equals("Dog")));
        }

        @Test
        void getBasicInfo_shouldReturnRecords() {
            var records = petService.getPetBasicInfo();
            Assertions.assertFalse(records.isEmpty());
            Assertions.assertNotNull(records.get(0).name());
        }

        @Test
        void getStats_shouldReturnValidStats() {
            PetStats stats = petService.getPetStats();
            Assertions.assertTrue(stats.averageAge() > 0);
            Assertions.assertTrue(stats.oldestAge() > 0);
        }

        @Test
        void findPetsByBreed_shouldReturnOrderedPets() {
            var pets = petService.getPetByBreed("Golden Retriever");
            Assertions.assertFalse(pets.isEmpty());
            Assertions.assertTrue(pets.stream().allMatch(p -> p.getBreed().equals("Golden Retriever")));
            for (int i = 0; i < pets.size() - 1; i++) {
                Assertions.assertTrue(pets.get(i).getAge() <= pets.get(i + 1).getAge());
            }
        }
    }

    @Nested
    @Order(2)
    class TestDelete {
        @Test
        void deletePet_shouldDeletePet() throws NotFoundException {
            Pet testPet = new Pet();
            testPet.setName("TestPet");
            testPet.setAnimalType("Dog");
            testPet.setBreed("TestBreed");
            testPet.setAge(3);

            Pet savedPet = petRepository.save(testPet);
            Long petId = savedPet.getId();
            Assertions.assertNotNull(petService.getPetById(petId));
            petService.deletePetById(petId);
            Assertions.assertThrows(NotFoundException.class, () -> {
                petService.getPetById(petId);
            });
        }

        @Test
        void deletePet_notFound_shouldThrowException() {
            Assertions.assertThrows(NotFoundException.class, () -> {
                petService.deletePetById(111L);
            });
        }

        @Test
        void deletePetByName_shouldDeletePets() throws NotFoundException {
            petService.deletePetByName("Mittens");
            var remainingPets = petService.getAllPets();
            Assertions.assertTrue(remainingPets.stream()
                    .noneMatch(p -> p.getName().equalsIgnoreCase("Mittens")));
        }

        @Test
        void deletePetByName_notFound_shouldThrowException() {
            Assertions.assertThrows(NotFoundException.class, () -> {
                petService.deletePetByName("NonExistentPet");
            });
        }
        @Test
        void deletePetByName_shouldDeleteMultiple() throws BadDataException, NotFoundException {
            Household someHousehold = new Household("D02XY45", 3, 5, true, null);
            householdRepository.save(someHousehold);
            Pet pet1 = new Pet(null, "DuplicateName", "Dog", 1, someHousehold);
            Pet pet2 = new Pet(null, "DuplicateName", "Cat", 2, null);

            petService.createPet(pet1);
            petService.createPet(pet2);
            petService.deletePetByName("DuplicateName");

            List<Pet> remainingPets = petService.getAllPets();
            Assertions.assertTrue(remainingPets.stream()
                    .noneMatch(p -> p.getName().equalsIgnoreCase("DuplicateName")));
        }
    }

    @Nested
    @Order(3)
    class TestUpdate {
        private Household household;

        @BeforeEach
        void setUp() {
            household = new Household("D02XY45", 3, 5, true, null);
            householdRepository.save(household);
        }

        @Test
        void save_shouldSavePet_returnWithNewId() throws BadDataException {
            Pet pet = new Pet(
                    null,
                    "TestPet",
                    "Dog",
                    "TestBreed",
                    2,
                    household
            );
            pet = petService.createPet(pet);
            Assertions.assertNotNull(pet);
            Assertions.assertTrue(pet.getId() > 0);
            Assertions.assertEquals(household.getEircode(), pet.getHousehold().getEircode());
        }

        @Test
        void save_invalidAge_throwBadDataException() {
            Pet pet = new Pet(
                    null,
                    "TestPet",
                    "Dog",
                    "TestBreed",
                    -1,
                    household
            );
            Assertions.assertThrows(BadDataException.class, () -> petService.createPet(pet));
        }

        @Test
        void updatePet_shouldUpdatePet() throws NotFoundException, BadDataException {
            Pet updatePet = new Pet(
                    null,
                    "UpdatedName",
                    "Dog",
                    "UpdatedBreed",
                    3,
                    household
            );
            Pet updated = petService.updatePet(3L, updatePet);
            Assertions.assertEquals("UpdatedName", updated.getName());
            Assertions.assertEquals("Dog", updated.getAnimalType());
            Assertions.assertEquals("UpdatedBreed", updated.getBreed());
            Assertions.assertEquals(household.getEircode(), updated.getHousehold().getEircode());
        }

        @Test
        void updatePet_notFound_shouldThrowException() {
            Pet updatePet = new Pet(
                    null,
                    "UpdatedName",
                    "Dog",
                    "UpdatedBreed",
                    3,
                    household
            );
            Assertions.assertThrows(NotFoundException.class, () -> petService.updatePet(111L, updatePet));
        }

        @Test
        void updatePet_invalidData_shouldThrowBadDataException() {
            Pet updatePet = new Pet(
                    null,
                    "",
                    "Dog",
                    "UpdatedBreed",
                    3,
                    household
            );
            Assertions.assertThrows(BadDataException.class, () -> petService.updatePet(1L, updatePet));
        }
    }
    @Nested
    @Order(4)
    class TestStats {
        @Test
        void getPetStats_shouldReturnStats() {
            PetStats stats = petService.getPetStats();
            Assertions.assertNotNull(stats);
            Assertions.assertTrue(stats.averageAge() >= 0);
            Assertions.assertTrue(stats.oldestAge() >= 0);
            Assertions.assertTrue(stats.totalCount() > 0);
        }
    }


}
