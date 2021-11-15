package org.springframework.samples.petclinic.model.priceCalculators;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CustomerDependentPriceCalculatorTest {

	private CustomerDependentPriceCalculator customerDependentPriceCalculator;
	private PetType rareType;
	private PetType commonType;
	private DateTime now;


	@Before
	public void setUp() {
		this.customerDependentPriceCalculator = new CustomerDependentPriceCalculator();

		this.rareType = new PetType();
		this.rareType.setRare(true);
		this.commonType = new PetType();
		this.commonType.setRare(false);
		this.now = new DateTime();
	}

	public List<Pet> listPetGenerator(int rareOld, int rareNew, int commonOld, int commonNew) {
		List<Pet> pets = new ArrayList<>();

		for (int i = 0; i < rareOld; i++) {
			Pet pet = new Pet();
			pet.setType(rareType);
			pet.setBirthDate(now.minusYears(3).toDate());
			pets.add(pet);
		}
		for (int i = 0; i < rareNew; i++) {
			Pet pet = new Pet();
			pet.setType(rareType);
			pet.setBirthDate(now.minusYears(1).toDate());
			pets.add(pet);
		}
		for (int i = 0; i < commonOld; i++) {
			Pet pet = new Pet();
			pet.setType(commonType);
			pet.setBirthDate(now.minusYears(3).toDate());
			pets.add(pet);
		}
		for (int i = 0; i < commonNew; i++) {
			Pet pet = new Pet();
			pet.setType(commonType);
			pet.setBirthDate(now.minusYears(1).toDate());
			pets.add(pet);
		}
		return pets;
	}

	@Test
	public void CustomDependentPriceCalculatorForDiscountCounterMoreThanMinScoreWithNewUser() {
		List<Pet> pets = listPetGenerator(4, 3, 5, 6);
		double actualPrice = customerDependentPriceCalculator.calcPrice(pets, 10, 100, UserType.NEW);
		double totalPrice = 4 * 100 * 1.2 + 3 * 100 * 1.2 * 1.4 + 5 * 100 + 6 * 100 * 1.2;
		double expectedPrice = totalPrice * 0.95 + 10;
		assertEquals(expectedPrice, actualPrice, 0.001);
	}

	@Test
	public void CustomDependentPriceCalculatorForDiscountCounterMoreThanMinScoreWithSilverUser() {
		List<Pet> pets = listPetGenerator(4, 3, 5, 6);
		double actualPrice = customerDependentPriceCalculator.calcPrice(pets, 10, 100, UserType.SILVER);
		double totalPrice = 4 * 100 * 1.2 + 3 * 100 * 1.2 * 1.4 + 5 * 100 + 6 * 100 * 1.2;
		double expectedPrice = (totalPrice + 10) * 0.90;
		assertEquals(expectedPrice, actualPrice, 0.001);
	}

	@Test
	public void CustomDependentPriceCalculatorForDiscountCounterLessThanMinScoreWithGoldUser() {
		List<Pet> pets = listPetGenerator(2, 1, 3, 2);
		double actualPrice = customerDependentPriceCalculator.calcPrice(pets, 10, 100, UserType.GOLD);
		double totalPrice = 2 * 100 * 1.2 + 1 * 100 * 1.2 * 1.4 + 3 * 100 + 2 * 100 * 1.2;
		double expectedPrice = totalPrice * 0.80 + 10;
		assertEquals(expectedPrice, actualPrice, 0.001);
	}

	@Test
	public void CustomDependentPriceCalculatorForDiscountCounterLessThanMinScoreWithNewUser() {
		List<Pet> pets = listPetGenerator(2, 1, 3, 2);
		double actualPrice = customerDependentPriceCalculator.calcPrice(pets, 10, 100, UserType.NEW);
		double totalPrice = 2 * 100 * 1.2 + 1 * 100 * 1.2 * 1.4 + 3 * 100 + 2 * 100 * 1.2;
		double expectedPrice = totalPrice;
		assertEquals(expectedPrice, actualPrice, 0.001);
	}
}
