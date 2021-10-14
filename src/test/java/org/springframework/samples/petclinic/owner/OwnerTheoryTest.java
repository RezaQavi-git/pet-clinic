package org.springframework.samples.petclinic.owner;

import org.junit.After;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(Theories.class)
public class OwnerTheoryTest {

	public Owner owner;

	@Before
	public void setup() {
		owner = new Owner();
	}

	@After
	public void teardown() {
		owner = null;
	}

	@DataPoints
	public static String[] pets = {"jack", "jhon", "frank", "tony", null};

	@DataPoints
	public static boolean[] isNew = {true, false};

	@Theory
	public void testGetPet(String _pet, boolean _isNew) {
		Pet expectedPet = null;
		assumeTrue(_pet != null);
		for(String iPet: pets) {
			if (iPet != null) {
				Pet newPet = new Pet();
				newPet.setName(iPet);
				owner.addPet(newPet);
				if (_pet.equals(newPet.getName())) {
					expectedPet = newPet;
				}
			}
		}
		if(_isNew) {
			assertNull(owner.getPet(_pet, true));
		} else {
			assertEquals(expectedPet, owner.getPet(_pet));
		}
	}

}
