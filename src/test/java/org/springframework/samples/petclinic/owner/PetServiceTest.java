package org.springframework.samples.petclinic.owner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class PetServiceTest {

	private final PetService petService;

	static Pet jack, jhon, frank;
	private final int petId;
	private final Pet expectedPet;


	public PetServiceTest(int petId, Pet expectedPet) {
		this.petId = petId;
		this.expectedPet = expectedPet;
		petService = mock(PetService.class);
	}

	@Parameters
	public static Collection<Object[]> parameters() {
		jack = new Pet(); jack.setId(111);
		jhon = new Pet(); jhon.setId(121);
		frank = new Pet(); frank.setId(151);
		return Arrays.asList(new Object[][] {
			{111, jack},
			{121, jhon},
			{151, frank}}
		);
	}

	@Test
	public void testFindPet() {
		when(petService.findPet(111)).thenReturn(jack);
		when(petService.findPet(121)).thenReturn(jhon);
		when(petService.findPet(151)).thenReturn(frank);
		System.out.println("Test for Id : " + petId);
		assertTrue(expectedPet == petService.findPet(petId));
	}

}
