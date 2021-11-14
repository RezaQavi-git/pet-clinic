package org.springframework.samples.petclinic.model.priceCalculators;

import org.junit.Before;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SimplePriceCalculatorTest {

	private SimplePriceCalculator simplePriceCalculator;
	private List<Pet> pets;

	@Before
	public void setUp() {
		this.simplePriceCalculator = new SimplePriceCalculator();
		this.pets = new ArrayList<>();

		PetType rareType = new PetType();
		rareType.setRare(true);
		PetType commonType = new PetType();
		commonType.setRare(false);

		for(int i = 0; i < 3; i++){
			Pet pet = new Pet();
			pet.setType(rareType);
			this.pets.add(pet);
		}
		for(int i = 0; i < 3; i++){
			Pet pet = new Pet();
			pet.setType(commonType);
			this.pets.add(pet);
		}
	}

	@Test
	public void testSimplePriceCalculatorNewUser(){
		UserType newUser = UserType.NEW;
		double actualPrice = simplePriceCalculator.calcPrice(this.pets, 10, 100, newUser);
		double expectedPrice = (10 + 3 * 100 + 3 * 100 * 1.2) * 0.95;
		assertEquals(expectedPrice, actualPrice, 0.001);
	}

	@Test
	public void testSimplePriceCalculatorSilverUser(){
		UserType newUser = UserType.SILVER;
		double actualPrice = simplePriceCalculator.calcPrice(this.pets, 10, 100, newUser);
		double expectedPrice = 10 + 3 * 100 + 3 * 100 * 1.2;
		assertEquals(expectedPrice, actualPrice, 0.001);
	}

}
