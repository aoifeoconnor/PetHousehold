package com.example.demo.services;

import com.example.demo.entities.Household;
import com.example.demo.entities.MyUser;
import com.example.demo.entities.Pet;
import com.example.demo.repositories.HouseholdRepository;
import com.example.demo.repositories.MyUserRepository;
import com.example.demo.repositories.PetRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
@Profile("dev")
public class DataLoader implements CommandLineRunner {

    private final HouseholdRepository householdRepository;
    private final PetRepository petRepository;
    private final MyUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        Household household1 = new Household("D02XY45", 3, 5, true);
        Household household2 = new Household("A94B6F3", 4, 6, false);
        Household household3 = new Household("T12AB34", 2, 4, true);

        householdRepository.saveAll(Arrays.asList(household1, household2, household3));

        Pet pet1 = new Pet(null, "Buddy", "Golden Retriever", "Dog", 3, household1);
        Pet pet2 = new Pet(null, "Mittens", "Siamese", "Cat", 2, household2);
        Pet pet3 = new Pet(null, "Charlie", "Beagle", "Dog", 4, household3);
        Pet pet4 = new Pet(null, "Whiskers", "Persian", "Cat", 5, household3);

        petRepository.saveAll(Arrays.asList(pet1, pet2, pet3, pet4));

        List<MyUser> users = Arrays.asList(
                new MyUser("admin@example.com", passwordEncoder.encode("password123"), "Admin", "User", "ROLE_ADMIN", true, true, true, true),
                new MyUser("user@example.com", passwordEncoder.encode("securePass!321"), "Regular", "User", "ROLE_USER", true, true, true, true),
                new MyUser("guest@example.com", passwordEncoder.encode("guestPass!2024"), "Guest", "User", "ROLE_USER", true, true, true, true)
        );

        userRepository.saveAll(users);
    }
}

