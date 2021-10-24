package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class PetManagerTest {

	@MockBean
	private PetTimedCache pets;

	@MockBean
	private OwnerRepository owners ;

	@MockBean
	private Logger log;

	public PetManager petManager;
	public Owner Andy, Shahram;
	public Pet Alvin, Simon, Teodor;



	@BeforeEach
	public void setUp() {
		petManager = new PetManager(pets, owners, log);

		Andy = new Owner();
		Shahram = new Owner();

		Alvin = new Pet();
		PetType dog = new PetType();
		dog.setName("dog");
		Alvin.setType(dog);
		Simon = new Pet();
		PetType cat = new PetType();
		cat.setName("cat");
		Simon.setType(cat);
		Teodor = new Pet();
		PetType rat = new PetType();
		rat.setName("rat");
		Teodor.setType(rat);

	}

	// Test Model :
	// Validation :
	// Approach :
	@Test
	public void testFindOwner() {
		when(owners.findById(1)).thenReturn(Andy);
		assertEquals(Andy, petManager.findOwner(1));
		verify(owners).findById(1);
		verify(log).info("find owner {}", 1);

		//owner ont exist
		assertNull(petManager.findOwner(11));
		verify(owners).findById(11);
		verify(log).info("find owner {}", 11);
	}

	// Test Model :
	// Validation :
	// Approach :
	@Test
	public void testFindPet () {
		when(pets.get(1)).thenReturn(Alvin);
		assertEquals(Alvin, petManager.findPet(1));
		verify(pets).get(1);
		verify(log).info("find pet by id {}", 1);

		//pet ont exist
		assertNull(petManager.findPet(11));
		verify(pets).get(11);
		verify(log).info("find pet by id {}", 11);
	}


	// Test Model :
	// Validation :
	// Approach :
	@Test
	public void testNewPet() {
		Owner owner = spy(Owner.class);
		owner.setId(10);
		Pet pet = petManager.newPet(owner);
		verify(owner).addPet(pet);
		verify(log).info("add pet for owner {}", 10);
	}

	// Test Model :
	// Validation :
	// Approach :
	@Test
	public void testSavePet() {
		Owner owner = mock(Owner.class);
		Alvin.setId(1);
		petManager.savePet(Alvin, owner);

		verify(owner).addPet(Alvin);
		verify(pets).save(Alvin);
		verify(log).info("save pet {}", 1);
	}

	// Test Model :
	// Validation :
	// Approach :
	@Test
	public void testGetOwnerPets() {
		Owner owner = mock(Owner.class);
		when(petManager.findOwner(1)).thenReturn(owner);
		when(petManager.findOwner(1).getPets()).thenReturn(Arrays.asList(Alvin, Simon, Teodor));
		assertEquals(Arrays.asList(Alvin, Simon, Teodor), petManager.getOwnerPets(1));

		verify(owner).getPets();
		verify(log).info("finding the owner's pets by id {}", 1);
	}

	// Test Model :
	// Validation :
	// Approach :
	@Test
	public void testGetOwnerPetTypes() {
		Owner owner = mock(Owner.class);
		when(petManager.findOwner(1)).thenReturn(owner);
		when(petManager.findOwner(1).getPets()).thenReturn(Arrays.asList(Alvin, Simon, Teodor));
		Set<PetType> petTypes = new HashSet<>();
		petTypes.add(Alvin.getType()); petTypes.add(Simon.getType()); petTypes.add(Teodor.getType());
		assertEquals(petTypes, petManager.getOwnerPetTypes(1));

		verify(owner).getPets();
		verify(log).info("finding the owner's petTypes by id {}", 1);
	}

	// Test Model :
	// Validation :
	// Approach :
	@Test
	public void testGetVisitsBetween() {
		LocalDate startDate = LocalDate.of(2021, 11, 13);
		LocalDate endDate = LocalDate.of(2021, 10, 13);
		Visit visit1 = new Visit();
		Visit visit2 = new Visit();

		Pet pet = mock(Pet.class);
		when(pets.get(1)).thenReturn(pet);
		when(pet.getVisitsBetween(startDate, endDate)).thenReturn(Arrays.asList(visit1, visit2));
		assertEquals(Arrays.asList(visit1, visit2), petManager.getVisitsBetween(1, startDate, endDate));

		verify(pets).get(1);
		verify(pet).getVisitsBetween(startDate, endDate);
		verify(log).info("get visits for pet {} from {} since {}", 1, startDate, endDate);
	}


}
