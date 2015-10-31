package pl.dors.radek.kucharz.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import pl.dors.radek.kucharz.Application;
import pl.dors.radek.kucharz.domain.PrzepisPartProdukt;
import pl.dors.radek.kucharz.repository.PrzepisProduktRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
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
public class PrzepisPartProduktResourceTest {


    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    @Inject
    private PrzepisProduktRepository przepisProduktRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPrzepisProduktMockMvc;

    private PrzepisPartProdukt przepisPartProdukt;

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
        przepisPartProdukt = new PrzepisPartProdukt();
        przepisPartProdukt.setQuantity(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createPrzepisProdukt() throws Exception {
        int databaseSizeBeforeCreate = przepisProduktRepository.findAll().size();

        // Create the PrzepisPartProdukt

        restPrzepisProduktMockMvc.perform(post("/api/przepisPartProdukts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(przepisPartProdukt)))
            .andExpect(status().isCreated());

        // Validate the PrzepisPartProdukt in the database
        List<PrzepisPartProdukt> przepisPartProdukts = przepisProduktRepository.findAll();
        assertThat(przepisPartProdukts).hasSize(databaseSizeBeforeCreate + 1);
        PrzepisPartProdukt testPrzepisPartProdukt = przepisPartProdukts.get(przepisPartProdukts.size() - 1);
        assertThat(testPrzepisPartProdukt.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllPrzepisProdukts() throws Exception {
        // Initialize the database
        przepisProduktRepository.saveAndFlush(przepisPartProdukt);

        // Get all the przepisProdukts
        restPrzepisProduktMockMvc.perform(get("/api/przepisProdukts"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(przepisPartProdukt.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));
    }

    @Test
    @Transactional
    public void getPrzepisProdukt() throws Exception {
        // Initialize the database
        przepisProduktRepository.saveAndFlush(przepisPartProdukt);

        // Get the przepisPartProdukt
        restPrzepisProduktMockMvc.perform(get("/api/przepisProdukts/{id}", przepisPartProdukt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(przepisPartProdukt.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrzepisProdukt() throws Exception {
        // Get the przepisPartProdukt
        restPrzepisProduktMockMvc.perform(get("/api/przepisProdukts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrzepisProdukt() throws Exception {
        // Initialize the database
        przepisProduktRepository.saveAndFlush(przepisPartProdukt);

        int databaseSizeBeforeUpdate = przepisProduktRepository.findAll().size();

        // Update the przepisPartProdukt
        przepisPartProdukt.setQuantity(UPDATED_QUANTITY);

        restPrzepisProduktMockMvc.perform(put("/api/przepisPartProdukts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(przepisPartProdukt)))
            .andExpect(status().isOk());

        // Validate the PrzepisPartProdukt in the database
        List<PrzepisPartProdukt> przepisPartProdukts = przepisProduktRepository.findAll();
        assertThat(przepisPartProdukts).hasSize(databaseSizeBeforeUpdate);
        PrzepisPartProdukt testPrzepisPartProdukt = przepisPartProdukts.get(przepisPartProdukts.size() - 1);
        assertThat(testPrzepisPartProdukt.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void deletePrzepisProdukt() throws Exception {
        // Initialize the database
        przepisProduktRepository.saveAndFlush(przepisPartProdukt);

        int databaseSizeBeforeDelete = przepisProduktRepository.findAll().size();

        // Get the przepisPartProdukt
        restPrzepisProduktMockMvc.perform(delete("/api/przepisPartProdukts/{id}", przepisPartProdukt.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrzepisPartProdukt> przepisPartProdukts = przepisProduktRepository.findAll();
        assertThat(przepisPartProdukts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
