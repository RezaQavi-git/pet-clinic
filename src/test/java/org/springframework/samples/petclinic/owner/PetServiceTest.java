package org.springframework.samples.petclinic.owner;

import io.cucumber.java.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


@RunWith(Parameterized.class)
class PetServiceTest {

	private PetService petService;

	Owner owner;
	static Pet jack, jhon, frank;
	public int petId;
	public Pet expectedPet;

	public PetServiceTest(int petId, Pet expectedPet) {
		this.petId = petId;
		this.expectedPet = expectedPet;
	}

	@BeforeEach
	public void setup() {
		petService = mock(PetService.class);
		jack = new Pet(); jack.setId(11);
		jhon = new Pet(); jhon.setId(15);
		frank = new Pet(); frank.setId(15);
		owner = new Owner();
		petService.savePet(jack, owner);
		petService.savePet(jhon, owner);
		petService.savePet(frank, owner);
	}


	@Parameterized.Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
			{11, jack},
			{12, jhon},
			{15, frank}}
		);
	}

	@Test
	public void testFindPet() {
		System.out.println("Test for Id : " + petId);
		assertSame(petService.findPet(petId), expectedPet);
	}

}
