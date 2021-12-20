package org.springframework.samples.petclinic.utility;

import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PriceCalculatorTest {

	private PriceCalculator priceCalculator;
	private List<Pet> pets;

	@Before
	public void setup() {
		this.priceCalculator = new PriceCalculator();
		this.pets = new ArrayList<>();

	}

	@Test
	public void price_test1() {
		Pet pet1 = new Pet();
		pet1.setBirthDate(LocalDate.now().minusYears(2));
		Visit visit1 = new Visit();
		visit1.setDate(LocalDate.now().minusDays(100));
		Visit visit2 = new Visit();
		visit2.setDate(LocalDate.now().minusDays(99));
		pet1.addVisit(visit1);
		pet1.addVisit(visit2);
		for (int i = 0; i < 5; i++)
			this.pets.add(pet1);

		Pet pet2 = new Pet();
		pet2.setBirthDate(LocalDate.now().minusYears(3));
		pet2.addVisit(visit1);
		for (int i = 0; i < 5; i++)
			this.pets.add(pet2);

		Pet pet3 = new Pet();
		pet3.setBirthDate(LocalDate.now());
		for (int i = 0; i < 5; i++)
			this.pets.add(pet3);

		double expected = 2280.3359999999993;
		double output = this.priceCalculator.calcPrice(this.pets, 0.2, 0.1);
		assertEquals(expected, output);
	}
}
