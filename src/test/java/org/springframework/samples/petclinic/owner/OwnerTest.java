package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {
	Owner owner;
	Pet jack;
	Pet frank;
	Pet newJessi;

	@BeforeEach
	public void setup() {
		jack = new Pet();
		jack.setName("jack");

		frank = new Pet();
		frank.setName("frank");

		newJessi = new Pet();
		newJessi.setName("newJessi");
		owner = new Owner();
		owner.setAddress("Enghelab Square, University of Tehran, Faculty of Electrical and Computer Engineering");
		owner.setCity("Tehran");
		owner.setTelephone("0218882362");
		owner.addPet(jack);
		owner.addPet(frank);
	}

	@Test
	void testGetAddress() {
		assertEquals("Enghelab Square, University of Tehran, Faculty of Electrical and Computer Engineering", owner.getAddress());
	}

	@Test
	void testGetCity() {
		assertEquals("Tehran", owner.getCity());
	}

	@Test
	void testGetTelephone() {
		assertEquals("0218882362", owner.getTelephone());
	}

	@Test
	void testSetAddress() {
		owner.setAddress("Amirabad, Faculty of Electrical and Computer Engineering");
		assertEquals("Amirabad, Faculty of Electrical and Computer Engineering", owner.getAddress());
	}

	@Test
	void testSetCity() {
		owner.setCity("Rasht");
		assertEquals("Rasht", owner.getCity());
	}

	@Test
	void testSetTelephone() {
		owner.setTelephone("0132563265");
		assertEquals("0132563265", owner.getTelephone());
	}

	@Test
	void testGetPets() {
		List<Pet> pets = new ArrayList<>();
		pets.add(jack);
		pets.add(frank);
		PropertyComparator.sort(pets, new MutableSortDefinition("name", true, true));

		assertEquals(Collections.unmodifiableList(pets), owner.getPets());
	}

	@Test
	void testAddPet() {
		owner.addPet(newJessi);
		List<Pet> pets = new ArrayList<>();
		pets.add(jack);
		pets.add(frank);
		pets.add(newJessi);
		PropertyComparator.sort(pets, new MutableSortDefinition("name", true, true));
		assertEquals(Collections.unmodifiableList(pets), owner.getPets());
	}

	@Test
	void testRemovePet() {
		owner.removePet(jack);
		List<Pet> pets = new ArrayList<>();
		pets.add(frank);
		PropertyComparator.sort(pets, new MutableSortDefinition("name", true, true));
		assertEquals(Collections.unmodifiableList(pets), owner.getPets());
	}

	@Test
	void testGetPetIgnore() {
		newJessi.isNew();
		owner.addPet(newJessi);
		assertEquals(newJessi, owner.getPet("newJessi", false));
	}

	@AfterEach
	public void teardown() {
		owner = null;
	}
}
