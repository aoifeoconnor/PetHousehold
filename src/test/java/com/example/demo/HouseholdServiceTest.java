package com.example.demo;

import com.example.demo.entities.Household;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.repositories.HouseholdRepository;
import com.example.demo.services.HouseholdServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HouseholdServiceTest {
    @Mock
    private HouseholdRepository householdRepository;

    @InjectMocks
    private HouseholdServiceImpl householdService;

    private Household household;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        household = new Household("D02XY45", 3, 5, true, null);
    }

    @Test
    void testGetHouseholdByEircode_HouseholdExists() {
        when(householdRepository.findById("D02XY45")).thenReturn(Optional.of(household));

        Household foundHousehold = householdService.getHouseholdByEircode("D02XY45");

        assertNotNull(foundHousehold);
        assertEquals("D02XY45", foundHousehold.getEircode());
        verify(householdRepository, times(1)).findById("D02XY45");
    }

    @Test
    void testGetHouseholdByEircode_HouseholdNotFound() {
        when(householdRepository.findById("UNKNOWN")).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            householdService.getHouseholdByEircode("UNKNOWN");
        });

        assertEquals("Household not found with eircode: UNKNOWN", exception.getMessage());
        verify(householdRepository, times(1)).findById("UNKNOWN");
    }

    @Test
    void testGetHouseholdWithPetsByEircode_HouseholdExists() {
        when(householdRepository.findPetByEircode("D02XY45")).thenReturn(household);

        Household foundHousehold = householdService.getHouseholdWithPetsByEircode("D02XY45");

        assertNotNull(foundHousehold);
        assertEquals("D02XY45", foundHousehold.getEircode());
        verify(householdRepository, times(1)).findPetByEircode("D02XY45");
    }

    @Test
    void testGetHouseholdWithPetsByEircode_HouseholdNotFound() {
        when(householdRepository.findPetByEircode("UNKNOWN")).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            householdService.getHouseholdWithPetsByEircode("UNKNOWN");
        });

        assertEquals("Household not found with eircode: UNKNOWN", exception.getMessage());
        verify(householdRepository, times(1)).findPetByEircode("UNKNOWN");
    }

    @Test
    void testGetHouseholdsWithNoPets() {
        when(householdRepository.findHouseholdsWithNoPets()).thenReturn(Collections.singletonList(household));

        List<Household> households = householdService.getAllHouseholdsNoPets();

        assertNotNull(households);
        assertEquals(1, households.size());
        assertEquals("D02XY45", households.get(0).getEircode());
        verify(householdRepository, times(1)).findHouseholdsWithNoPets();
    }

    @Test
    void testGetHouseholdsWithNoPets_NoHouseholds() {
        when(householdRepository.findHouseholdsWithNoPets()).thenReturn(Collections.emptyList());

        List<Household> households = householdService.getAllHouseholdsNoPets();

        assertNotNull(households);
        assertTrue(households.isEmpty());
        verify(householdRepository, times(1)).findHouseholdsWithNoPets();
    }
}
