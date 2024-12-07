package com.example.demo.repositories;

import com.example.demo.dtos.PetNameTypeBreed;
import com.example.demo.dtos.PetStats;
import com.example.demo.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// <Pet, Long> = the entity class from
//which the JpaRepository is created and
//Long is the data type of its ID.

public interface PetRepository extends JpaRepository<Pet, Long> {
    @Transactional
    @Modifying
    int deletePetByName(String name);

    List<Pet> findPetByName(String name);

    List<Pet> findPetByAnimalType(String animalType);

    List<Pet> findPetByBreed(String breed);

    @Query("SELECT new com.example.demo.dtos.PetNameTypeBreed(p.name, p.animalType, p.breed) FROM Pet p")
    List<PetNameTypeBreed> findAllBasicRecords();

    @Query("""
        SELECT new com.example.demo.dtos.PetStats(
            COALESCE(AVG(p.age), 0.0),
            COALESCE(MAX(p.age), 0),
            COUNT(p)
        ) FROM Pet p
    """)
    PetStats getPetStats();


}
