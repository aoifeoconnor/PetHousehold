package com.example.demo.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Lombok for getters/setters etc
@Entity // tells Spring that Pet is a JPA entity which will be mapped to a table in the database
@Table(name="pets")
@AllArgsConstructor //Lombok
@NoArgsConstructor  //Lombok

// Invalidate Cache if IDE isn't working
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String breed;
    private String animalType; // corresponds to animal_type
    private int age;

    @ManyToOne
    @JoinColumn(name = "household_eircode", nullable = false)  // FK
    @JsonBackReference // do not include in the serialisation - the child in the relationship
    private Household household;

    public Pet(String name, String breed, String animalType, int age, Household household) {
        this.name = name;
        this.breed = breed;
        this.animalType = animalType;
        this.age = age;
        this.household = household;
    }
}
