package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.*;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ExtendWith(ReportingExtension.class)
@ClauseDefinition(clause = 'a', def = "t1arr[0] != t2arr[0]")
@ClauseDefinition(clause = 'b', def = "t1arr[1] != t2arr[1]")
@ClauseDefinition(clause = 'c', def = "t1arr[2] != t2arr[2]")

@ClauseDefinition(clause = 'd', def = "t1arr[0] < 0")
@ClauseDefinition(clause = 'e', def = "t1arr[0] + t1arr[1] < t1arr[2]")


class TriCongruenceTest {

//	--------Clause Coverage 14

	@ClauseCoverage(
		predicate = "a + b + c",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = true),

		}
	)

	@Test
	public void testCCAllTrue() {
		Triangle t1 = new Triangle(2, 3, 7);
		Triangle t2 = new Triangle(6, 4, 5);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	@ClauseCoverage(
		predicate = "a + b + c",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false),

		}
	)

	@Test
	public void testCCAllFalse() {
		Triangle t1 = new Triangle(2, 3, 4);
		Triangle t2 = new Triangle(4, 2, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertTrue(areCongruent);
	}

	//	--------Correlated Active Clause Coverage 14


	@CACC(
		predicate = "a + b + c",
		majorClause = 'a',
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false),
		},
		predicateValue = true
	)

	@Test
	public void testCACCMajor_A_True() {
		Triangle t1 = new Triangle(2, 3, 4);
		Triangle t2 = new Triangle(4, 1, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	@CACC(
		predicate = "a + b + c",
		majorClause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = false),
		},
		predicateValue = true
	)

	@Test
	public void testCACCMajor_B_True() {
		Triangle t1 = new Triangle(2, 3, 7);
		Triangle t2 = new Triangle(7, 2, 5);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	@CACC(
		predicate = "a + b + c",
		majorClause = 'c',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = true),
		},
		predicateValue = true
	)

	@Test
	public void testCACCMajor_C_True() {
		Triangle t1 = new Triangle(2, 3, 7);
		Triangle t2 = new Triangle(8, 2, 3);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	//In this case Near false points are the same of Unique true point 14

	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "a",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false),
		}
	)

	@Test
	public void testCUTPNFPUniqueTruePoint_A() {
		Triangle t1 = new Triangle(2, 5, 7);
		Triangle t2 = new Triangle(7, 3, 5);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	@NearFalsePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "a",
		clause = 'a',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void testCUTPNFPNearFalsePoint_A() {
		Triangle t1 = new Triangle(3, 4, 7);
		Triangle t2 = new Triangle(3, 4, 7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertTrue(areCongruent);
	}

	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "b",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = false),
		}
	)

	@Test
	public void testCUTPNFPUniqueTruePoint_B() {
		Triangle t1 = new Triangle(2, 5, 7);
		Triangle t2 = new Triangle(7, 2, 4);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	@NearFalsePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "b",
		clause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void testCUTPNFPNearFalsePoint_B() {
		Triangle t1 = new Triangle(3, 4, 7);
		Triangle t2 = new Triangle(3, 4, 7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertTrue(areCongruent);
	}

	@UniqueTruePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "c",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = true),
		}
	)

	@Test
	public void testCUTPNFPUniqueTruePoint_C() {
		Triangle t1 = new Triangle(2, 5, 7);
		Triangle t2 = new Triangle(8, 2, 5);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	@NearFalsePoint(
		predicate = "a + b + c",
		dnf = "a + b + c",
		implicant = "c",
		clause = 'c',
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void testCUTPNFPNearFalsePoint_C() {
		Triangle t1 = new Triangle(3, 4, 7);
		Triangle t2 = new Triangle(3, 4, 7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertTrue(areCongruent);
	}


//	------------------------------------------------------------
//	------------------------------------------------------------
//	------------------------------------------------------------
//	------------------------------------------------------------


	//	--------Clause Coverage 15

	@ClauseCoverage(
		predicate = "d + e",
		valuations = {
			@Valuation(clause = 'd', valuation = true),
			@Valuation(clause = 'e', valuation = true),
		}
	)

	@Test
	public void testCCAllTrue_15() {
		Triangle t1 = new Triangle(-1, 3, 7);
		Triangle t2 = new Triangle(-1, 3, 7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	@ClauseCoverage(
		predicate = "d + e",
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false),
		}
	)

	@Test
	public void testCCAllFalse_15() {
		Triangle t1 = new Triangle(2, 3, 4);
		Triangle t2 = new Triangle(2, 3, 4);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertTrue(areCongruent);
	}

	//	--------Correlated Active Clause Coverage 15

	@CACC(
		predicate = "d + e",
		majorClause = 'd',
		valuations = {
			@Valuation(clause = 'd', valuation = true),
			@Valuation(clause = 'e', valuation = false),
		},
		predicateValue = true
	)

	@Test
	public void testCACCMajor_D_True() {
		Triangle t1 = new Triangle(-1, 7, 4);
		Triangle t2 = new Triangle(-1, 7, 4);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	@CACC(
		predicate = "d + e",
		majorClause = 'e',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = true),
		},
		predicateValue = true
	)

	@Test
	public void testCACCMajor_E_True() {
		Triangle t1 = new Triangle(1, 3, 7);
		Triangle t2 = new Triangle(1, 3, 7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}


	//In this case Near false points are the same of Unique true point 15

	@UniqueTruePoint(
		predicate = "d + e",
		dnf = "d + e",
		implicant = "d",
		valuations = {
			@Valuation(clause = 'd', valuation = true),
			@Valuation(clause = 'e', valuation = false),
		}
	)

	@Test
	public void testCUTPNFPUniqueTruePoint_D() {
		Triangle t1 = new Triangle(-1, 7, 4);
		Triangle t2 = new Triangle(-1, 7, 4);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	@NearFalsePoint(
		predicate = "d + e",
		dnf = "d + e",
		implicant = "d",
		clause = 'd',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false),
		}
	)
	@Test
	public void testCUTPNFPNearFalsePoint_D() {
		Triangle t1 = new Triangle(3, 4, 7);
		Triangle t2 = new Triangle(3, 4, 7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertTrue(areCongruent);
	}

	@UniqueTruePoint(
		predicate = "d + e",
		dnf = "d + e",
		implicant = "e",
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = true),
		}
	)

	@Test
	public void testCUTPNFPUniqueTruePoint_E() {
		Triangle t1 = new Triangle(1, 3, 7);
		Triangle t2 = new Triangle(1, 3, 7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertFalse(areCongruent);
	}

	@NearFalsePoint(
		predicate = "d + e",
		dnf = "d + e",
		implicant = "e",
		clause = 'e',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false),
		}
	)
	@Test
	public void testCUTPNFPNearFalsePoint_E() {
		Triangle t1 = new Triangle(3, 4, 7);
		Triangle t2 = new Triangle(3, 4, 7);
		boolean areCongruent = TriCongruence.areCongruent(t1, t2);
		Assertions.assertTrue(areCongruent);
	}




	/**
	* TODO
	* explain your answer here
	*/
	private static boolean questionTwo(boolean a, boolean b, boolean c, boolean d, boolean e) {
		//		predicate = a predicate with any number of clauses
		return false;
	}


}




