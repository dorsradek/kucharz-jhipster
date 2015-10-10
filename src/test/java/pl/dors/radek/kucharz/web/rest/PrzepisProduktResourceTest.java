package pl.dors.radek.kucharz.web.rest;

import pl.dors.radek.kucharz.Application;
import pl.dors.radek.kucharz.domain.PrzepisProdukt;
import pl.dors.radek.kucharz.repository.PrzepisProduktRepository;

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
 * Test class for the PrzepisProduktResource REST controller.
 *
 * @see PrzepisProduktResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PrzepisProduktResourceTest {


    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    @Inject
    private PrzepisProduktRepository przepisProduktRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPrzepisProduktMockMvc;

    private PrzepisProdukt przepisProdukt;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PrzepisProduktResource przepisProduktResource = new PrzepisProduktResource();
        ReflectionTestUtils.setField(przepisProduktResource, "przepisProduktRepository", przepisProduktRepository);
        this.restPrzepisProduktMockMvc = MockMvcBuilders.standaloneSetup(przepisProduktResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        przepisProdukt = new PrzepisProdukt();
        przepisProdukt.setQuantity(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createPrzepisProdukt() throws Exception {
        int databaseSizeBeforeCreate = przepisProduktRepository.findAll().size();

        // Create the PrzepisProdukt

        restPrzepisProduktMockMvc.perform(post("/api/przepisProdukts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(przepisProdukt)))
                .andExpect(status().isCreated());

        // Validate the PrzepisProdukt in the database
        List<PrzepisProdukt> przepisProdukts = przepisProduktRepository.findAll();
        assertThat(przepisProdukts).hasSize(databaseSizeBeforeCreate + 1);
        PrzepisProdukt testPrzepisProdukt = przepisProdukts.get(przepisProdukts.size() - 1);
        assertThat(testPrzepisProdukt.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllPrzepisProdukts() throws Exception {
        // Initialize the database
        przepisProduktRepository.saveAndFlush(przepisProdukt);

        // Get all the przepisProdukts
        restPrzepisProduktMockMvc.perform(get("/api/przepisProdukts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(przepisProdukt.getId().intValue())))
                .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));
    }

    @Test
    @Transactional
    public void getPrzepisProdukt() throws Exception {
        // Initialize the database
        przepisProduktRepository.saveAndFlush(przepisProdukt);

        // Get the przepisProdukt
        restPrzepisProduktMockMvc.perform(get("/api/przepisProdukts/{id}", przepisProdukt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(przepisProdukt.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrzepisProdukt() throws Exception {
        // Get the przepisProdukt
        restPrzepisProduktMockMvc.perform(get("/api/przepisProdukts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrzepisProdukt() throws Exception {
        // Initialize the database
        przepisProduktRepository.saveAndFlush(przepisProdukt);

		int databaseSizeBeforeUpdate = przepisProduktRepository.findAll().size();

        // Update the przepisProdukt
        przepisProdukt.setQuantity(UPDATED_QUANTITY);

        restPrzepisProduktMockMvc.perform(put("/api/przepisProdukts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(przepisProdukt)))
                .andExpect(status().isOk());

        // Validate the PrzepisProdukt in the database
        List<PrzepisProdukt> przepisProdukts = przepisProduktRepository.findAll();
        assertThat(przepisProdukts).hasSize(databaseSizeBeforeUpdate);
        PrzepisProdukt testPrzepisProdukt = przepisProdukts.get(przepisProdukts.size() - 1);
        assertThat(testPrzepisProdukt.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void deletePrzepisProdukt() throws Exception {
        // Initialize the database
        przepisProduktRepository.saveAndFlush(przepisProdukt);

		int databaseSizeBeforeDelete = przepisProduktRepository.findAll().size();

        // Get the przepisProdukt
        restPrzepisProduktMockMvc.perform(delete("/api/przepisProdukts/{id}", przepisProdukt.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PrzepisProdukt> przepisProdukts = przepisProduktRepository.findAll();
        assertThat(przepisProdukts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
