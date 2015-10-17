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
import pl.dors.radek.kucharz.domain.RodzajProduktu;
import pl.dors.radek.kucharz.repository.RodzajProduktuRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the RodzajProduktuResource REST controller.
 *
 * @see RodzajProduktuResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RodzajProduktuResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private RodzajProduktuRepository rodzajProduktuRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRodzajProduktuMockMvc;

    private RodzajProduktu rodzajProduktu;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RodzajProduktuResource rodzajProduktuResource = new RodzajProduktuResource();
        ReflectionTestUtils.setField(rodzajProduktuResource, "rodzajProduktuRepository", rodzajProduktuRepository);
        this.restRodzajProduktuMockMvc = MockMvcBuilders.standaloneSetup(rodzajProduktuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rodzajProduktu = new RodzajProduktu();
        rodzajProduktu.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createRodzajProduktu() throws Exception {
        int databaseSizeBeforeCreate = rodzajProduktuRepository.findAll().size();

        // Create the RodzajProduktu

        restRodzajProduktuMockMvc.perform(post("/api/rodzajProduktus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rodzajProduktu)))
            .andExpect(status().isCreated());

        // Validate the RodzajProduktu in the database
        List<RodzajProduktu> rodzajProduktus = rodzajProduktuRepository.findAll();
        assertThat(rodzajProduktus).hasSize(databaseSizeBeforeCreate + 1);
        RodzajProduktu testRodzajProduktu = rodzajProduktus.get(rodzajProduktus.size() - 1);
        assertThat(testRodzajProduktu.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllRodzajProduktus() throws Exception {
        // Initialize the database
        rodzajProduktuRepository.saveAndFlush(rodzajProduktu);

        // Get all the rodzajProduktus
        restRodzajProduktuMockMvc.perform(get("/api/rodzajProduktus"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rodzajProduktu.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getRodzajProduktu() throws Exception {
        // Initialize the database
        rodzajProduktuRepository.saveAndFlush(rodzajProduktu);

        // Get the rodzajProduktu
        restRodzajProduktuMockMvc.perform(get("/api/rodzajProduktus/{id}", rodzajProduktu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rodzajProduktu.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRodzajProduktu() throws Exception {
        // Get the rodzajProduktu
        restRodzajProduktuMockMvc.perform(get("/api/rodzajProduktus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRodzajProduktu() throws Exception {
        // Initialize the database
        rodzajProduktuRepository.saveAndFlush(rodzajProduktu);

        int databaseSizeBeforeUpdate = rodzajProduktuRepository.findAll().size();

        // Update the rodzajProduktu
        rodzajProduktu.setName(UPDATED_NAME);

        restRodzajProduktuMockMvc.perform(put("/api/rodzajProduktus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rodzajProduktu)))
            .andExpect(status().isOk());

        // Validate the RodzajProduktu in the database
        List<RodzajProduktu> rodzajProduktus = rodzajProduktuRepository.findAll();
        assertThat(rodzajProduktus).hasSize(databaseSizeBeforeUpdate);
        RodzajProduktu testRodzajProduktu = rodzajProduktus.get(rodzajProduktus.size() - 1);
        assertThat(testRodzajProduktu.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteRodzajProduktu() throws Exception {
        // Initialize the database
        rodzajProduktuRepository.saveAndFlush(rodzajProduktu);

        int databaseSizeBeforeDelete = rodzajProduktuRepository.findAll().size();

        // Get the rodzajProduktu
        restRodzajProduktuMockMvc.perform(delete("/api/rodzajProduktus/{id}", rodzajProduktu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RodzajProduktu> rodzajProduktus = rodzajProduktuRepository.findAll();
        assertThat(rodzajProduktus).hasSize(databaseSizeBeforeDelete - 1);
    }
}
