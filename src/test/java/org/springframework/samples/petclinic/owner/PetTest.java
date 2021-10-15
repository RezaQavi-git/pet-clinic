package org.springframework.samples.petclinic.owner;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@RunWith(Theories.class)
public class PetTest {

	public PetTest() {
	}

	@DataPoints
	public static List[] firstVisits = {
		Arrays.asList(
			new Visit().setDate(LocalDate.of(2021, 12, 13)),
			new Visit().setDate(LocalDate.of(2018, 11, 14))
		),
		Arrays.asList(
			new Visit().setDate(LocalDate.of(2022, 10, 15)),
			new Visit().setDate(LocalDate.of(2023, 9, 16))
		)
	};

	@DataPoints
	public static List[] secondVisits = {
		Arrays.asList(
			new Visit().setDate(LocalDate.of(1999, 8, 17)),
			new Visit().setDate(LocalDate.of(1984, 7, 18))
		),
		Arrays.asList(
			new Visit().setDate(LocalDate.of(2000, 6, 19)),
			new Visit().setDate(LocalDate.of(2011, 5, 20)),
			new Visit().setDate(LocalDate.of(2024, 4, 21))
			)
	};

	@Theory
	public void testGetVisits(List<Visit> firstVisits, List<Visit> secondVisits) {
		assumeTrue(firstVisits != null);
		assumeTrue(secondVisits != null);

		Pet pet = new Pet();
		for(Visit v1 : firstVisits){
			pet.addVisit(v1);
		}
		for(Visit v2: secondVisits){
			pet.addVisit(v2);
		}

		List<Visit> sortedVisits = new ArrayList<Visit>(pet.getVisits());
		PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
		assertArrayEquals((sortedVisits).toArray(), pet.getVisits().toArray());

	}
}
