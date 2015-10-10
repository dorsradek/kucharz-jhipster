package pl.dors.radek.kucharz.web.rest;

import pl.dors.radek.kucharz.Application;
import pl.dors.radek.kucharz.domain.Produkt;
import pl.dors.radek.kucharz.repository.ProduktRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ProduktResource REST controller.
 *
 * @see ProduktResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ProduktResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private ProduktRepository produktRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProduktMockMvc;

    private Produkt produkt;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProduktResource produktResource = new ProduktResource();
        ReflectionTestUtils.setField(produktResource, "produktRepository", produktRepository);
        this.restProduktMockMvc = MockMvcBuilders.standaloneSetup(produktResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        produkt = new Produkt();
        produkt.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProdukt() throws Exception {
        int databaseSizeBeforeCreate = produktRepository.findAll().size();

        // Create the Produkt

        restProduktMockMvc.perform(post("/api/produkts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(produkt)))
                .andExpect(status().isCreated());

        // Validate the Produkt in the database
        List<Produkt> produkts = produktRepository.findAll();
        assertThat(produkts).hasSize(databaseSizeBeforeCreate + 1);
        Produkt testProdukt = produkts.get(produkts.size() - 1);
        assertThat(testProdukt.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllProdukts() throws Exception {
        // Initialize the database
        produktRepository.saveAndFlush(produkt);

        // Get all the produkts
        restProduktMockMvc.perform(get("/api/produkts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(produkt.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getProdukt() throws Exception {
        // Initialize the database
        produktRepository.saveAndFlush(produkt);

        // Get the produkt
        restProduktMockMvc.perform(get("/api/produkts/{id}", produkt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(produkt.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProdukt() throws Exception {
        // Get the produkt
        restProduktMockMvc.perform(get("/api/produkts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProdukt() throws Exception {
        // Initialize the database
        produktRepository.saveAndFlush(produkt);

		int databaseSizeBeforeUpdate = produktRepository.findAll().size();

        // Update the produkt
        produkt.setName(UPDATED_NAME);

        restProduktMockMvc.perform(put("/api/produkts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(produkt)))
                .andExpect(status().isOk());

        // Validate the Produkt in the database
        List<Produkt> produkts = produktRepository.findAll();
        assertThat(produkts).hasSize(databaseSizeBeforeUpdate);
        Produkt testProdukt = produkts.get(produkts.size() - 1);
        assertThat(testProdukt.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteProdukt() throws Exception {
        // Initialize the database
        produktRepository.saveAndFlush(produkt);

		int databaseSizeBeforeDelete = produktRepository.findAll().size();

        // Get the produkt
        restProduktMockMvc.perform(delete("/api/produkts/{id}", produkt.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Produkt> produkts = produktRepository.findAll();
        assertThat(produkts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
