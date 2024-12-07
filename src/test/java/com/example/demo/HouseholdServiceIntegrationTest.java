package com.example.demo;
import com.example.demo.entities.Household;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.HouseholdRepository;
import com.example.demo.services.HouseholdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class HouseholdServiceIntegrationTest {
    @Autowired
    private HouseholdService householdService;

    @Autowired
    private HouseholdRepository householdRepository;

    private Household household;

    @BeforeEach
    void setUp() {
        household = new Household("V23ST01", 3, 5, true, null);
        householdRepository.save(household);
    }

    @Test
    void testGetHouseholdByEircode_HouseholdExists() {
        Household foundHousehold = householdService.getHouseholdByEircode("V23ST01");

        assertNotNull(foundHousehold);
        assertEquals("V23ST01", foundHousehold.getEircode());
    }

    @Test
    void testGetHouseholdByEircode_HouseholdNotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            householdService.getHouseholdByEircode("UNKNOWN");
        });

        assertEquals("Household not found with eircode: UNKNOWN", exception.getMessage());
    }

    @Test
    void testGetHouseholdWithPetsByEircode_HouseholdExists() {
        Household foundHousehold = householdService.getHouseholdWithPetsByEircode("V23ST01");

        assertNotNull(foundHousehold);
        assertEquals("V23ST01", foundHousehold.getEircode());
    }

    @Test
    void testGetHouseholdWithPetsByEircode_HouseholdNotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            householdService.getHouseholdWithPetsByEircode("UNKNOWN");
        });

        assertEquals("Household not found with eircode: UNKNOWN", exception.getMessage());
    }

    @Test
    void testGetHouseholdsWithNoPets() {
        List<Household> households = householdService.getAllHouseholdsNoPets();

        assertNotNull(households);
        assertFalse(households.isEmpty());
        assertEquals("V23ST01", households.get(0).getEircode());
    }

}
