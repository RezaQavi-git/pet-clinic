package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PetServiceValidator {
	@Autowired
	PetService petService;
	@Autowired
	OwnerRepository ownerRepository;

	private Owner lastOwnerFound;
	private Pet lastPetAddedToOwner;

	Integer ownerIdCounter = 1;

	private final HashMap<String, Integer> ownersId = new HashMap<>();

	@Given("One of owners is {string} from {string} with telephone number {string}")
	public void addOwner(String name, String city, String telephone){
		Owner owner = new Owner();
		owner.setFirstName(name);
		owner.setLastName(name);
		owner.setCity(city);
		owner.setAddress(city);
		owner.setTelephone(telephone);
		owner.setId(ownerIdCounter);
		ownersId.put(name, ownerIdCounter);
		ownerIdCounter += 1;
		ownerRepository.save(owner);
	}

	@When("We are finding owner with {string} name")
	public void findOwnerInService(String name){
		System.out.println(ownersId.get(name));
		lastOwnerFound = petService.findOwner(ownersId.get(name));
		System.out.println(lastOwnerFound);
	}

	@Then("We found owner with {string} name")
	public void ownerWasTheOneWeWantTo(String name){
		assertEquals(name, lastOwnerFound.getFirstName());
	}
	@Then("We did not found owner with {string} name")
	public void ownerWasNotTheOneWeWantTo(String name){
		assertNotEquals(name, lastOwnerFound.getFirstName());
	}

	@Given("Owner {string} has a new pet with id {int}")
	public void assignPetToOwner(String ownerName, int petId){
		Owner owner = petService.findOwner(ownersId.get(ownerName));
		lastPetAddedToOwner = petService.newPet(owner);
		lastPetAddedToOwner.setName("cat");
		PetType petType = new PetType();
		petType.setName("heyvan");
		petType.setId(1);
		lastPetAddedToOwner.setType(petType);
		lastPetAddedToOwner.setBirthDate(LocalDate.now());
		lastPetAddedToOwner.setId(petId);
		petService.savePet(lastPetAddedToOwner, owner);
	}

	@Then("New pet is now owner {string}'s pet")
	public void petIsOwnersPet(String ownerName){
		Owner owner = petService.findOwner(ownersId.get(ownerName));
		System.out.println(owner.getPets());
		System.out.println(lastPetAddedToOwner);
		Boolean findPet = false;
		List<Pet> pets = owner.getPets();
		for(Pet pet : pets){
			if(pet.getName().equals(lastPetAddedToOwner.getName()))
				findPet = true;
		}
		assertTrue(findPet);
	}

	@Then("We can find pet with Id {int} in pet service")
	public void petWithIdIsInPetService(int petId){
		Pet pet = petService.findPet(petId);
		assertEquals(pet.getName(), lastPetAddedToOwner.getName());
	}

}
