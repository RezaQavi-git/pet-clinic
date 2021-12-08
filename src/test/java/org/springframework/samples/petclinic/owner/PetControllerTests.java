package org.springframework.samples.petclinic.owner;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = PetController.class,
	includeFilters = {
		@ComponentScan.Filter(value = PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = PetService.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = LoggerConfig.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = PetTimedCache.class, type = FilterType.ASSIGNABLE_TYPE),
	}
)
class PetControllerTests {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PetRepository petRepository;
	@MockBean
	private OwnerRepository ownerRepository;

	private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
	private static final String REDIRECT_TO_OWNER_URL = "redirect:/owners/{ownerId}";

	@BeforeEach
	void setup() {
		Pet simon = new Pet();
		simon.setId(1);
		PetType cat = new PetType();
		cat.setName("cat");
		when(petRepository.findPetTypes()).thenReturn(Lists.newArrayList(cat));
		given(ownerRepository.findById(101)).willReturn(new Owner());
		given(petRepository.findById(1)).willReturn(simon);
	}

	@Test
	public void initCreationFormTest() throws Exception {
		mockMvc.perform(get("/owners/101/pets/new"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));
	}

	@Test
	public void processCreationFormTest() throws Exception {
		mockMvc.perform(post("/owners/101/pets/new")
				.param("name", "theodore")
				.param("type", "cat")
				.param("birthDate", "1958-06-21"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name(REDIRECT_TO_OWNER_URL));
	}

	@Test
	public void processCreationFormWithErrorTest() throws Exception {
		mockMvc.perform(post("/owners/101/pets/new"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));
	}

	@Test
	public void initUpdateFormTest() throws Exception {
		mockMvc.perform(get("/owners/101/pets/1/edit"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));
	}

	@Test
	public void processUpdateFormTest() throws Exception {
		mockMvc.perform(post("/owners/101/pets/1/edit")
				.param("name", "Alvin")
				.param("type", "cat")
				.param("birthDate", "1958-06-20"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name(REDIRECT_TO_OWNER_URL));
	}

	@Test
	public void processUpdateFormWithErrorTest() throws Exception {
		mockMvc.perform(post("/owners/101/pets/1/edit"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name(VIEWS_PETS_CREATE_OR_UPDATE_FORM));
	}
}
