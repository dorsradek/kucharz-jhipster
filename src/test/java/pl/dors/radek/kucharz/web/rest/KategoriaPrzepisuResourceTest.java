package pl.dors.radek.kucharz.web.rest;

import pl.dors.radek.kucharz.Application;
import pl.dors.radek.kucharz.domain.KategoriaPrzepisu;
import pl.dors.radek.kucharz.repository.KategoriaPrzepisuRepository;

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
 * Test class for the KategoriaPrzepisuResource REST controller.
 *
 * @see KategoriaPrzepisuResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class KategoriaPrzepisuResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private KategoriaPrzepisuRepository kategoriaPrzepisuRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restKategoriaPrzepisuMockMvc;

    private KategoriaPrzepisu kategoriaPrzepisu;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KategoriaPrzepisuResource kategoriaPrzepisuResource = new KategoriaPrzepisuResource();
        ReflectionTestUtils.setField(kategoriaPrzepisuResource, "kategoriaPrzepisuRepository", kategoriaPrzepisuRepository);
        this.restKategoriaPrzepisuMockMvc = MockMvcBuilders.standaloneSetup(kategoriaPrzepisuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        kategoriaPrzepisu = new KategoriaPrzepisu();
        kategoriaPrzepisu.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createKategoriaPrzepisu() throws Exception {
        int databaseSizeBeforeCreate = kategoriaPrzepisuRepository.findAll().size();

        // Create the KategoriaPrzepisu

        restKategoriaPrzepisuMockMvc.perform(post("/api/kategoriaPrzepisus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kategoriaPrzepisu)))
                .andExpect(status().isCreated());

        // Validate the KategoriaPrzepisu in the database
        List<KategoriaPrzepisu> kategoriaPrzepisus = kategoriaPrzepisuRepository.findAll();
        assertThat(kategoriaPrzepisus).hasSize(databaseSizeBeforeCreate + 1);
        KategoriaPrzepisu testKategoriaPrzepisu = kategoriaPrzepisus.get(kategoriaPrzepisus.size() - 1);
        assertThat(testKategoriaPrzepisu.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllKategoriaPrzepisus() throws Exception {
        // Initialize the database
        kategoriaPrzepisuRepository.saveAndFlush(kategoriaPrzepisu);

        // Get all the kategoriaPrzepisus
        restKategoriaPrzepisuMockMvc.perform(get("/api/kategoriaPrzepisus"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(kategoriaPrzepisu.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getKategoriaPrzepisu() throws Exception {
        // Initialize the database
        kategoriaPrzepisuRepository.saveAndFlush(kategoriaPrzepisu);

        // Get the kategoriaPrzepisu
        restKategoriaPrzepisuMockMvc.perform(get("/api/kategoriaPrzepisus/{id}", kategoriaPrzepisu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(kategoriaPrzepisu.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKategoriaPrzepisu() throws Exception {
        // Get the kategoriaPrzepisu
        restKategoriaPrzepisuMockMvc.perform(get("/api/kategoriaPrzepisus/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKategoriaPrzepisu() throws Exception {
        // Initialize the database
        kategoriaPrzepisuRepository.saveAndFlush(kategoriaPrzepisu);

		int databaseSizeBeforeUpdate = kategoriaPrzepisuRepository.findAll().size();

        // Update the kategoriaPrzepisu
        kategoriaPrzepisu.setName(UPDATED_NAME);

        restKategoriaPrzepisuMockMvc.perform(put("/api/kategoriaPrzepisus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(kategoriaPrzepisu)))
                .andExpect(status().isOk());

        // Validate the KategoriaPrzepisu in the database
        List<KategoriaPrzepisu> kategoriaPrzepisus = kategoriaPrzepisuRepository.findAll();
        assertThat(kategoriaPrzepisus).hasSize(databaseSizeBeforeUpdate);
        KategoriaPrzepisu testKategoriaPrzepisu = kategoriaPrzepisus.get(kategoriaPrzepisus.size() - 1);
        assertThat(testKategoriaPrzepisu.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteKategoriaPrzepisu() throws Exception {
        // Initialize the database
        kategoriaPrzepisuRepository.saveAndFlush(kategoriaPrzepisu);

		int databaseSizeBeforeDelete = kategoriaPrzepisuRepository.findAll().size();

        // Get the kategoriaPrzepisu
        restKategoriaPrzepisuMockMvc.perform(delete("/api/kategoriaPrzepisus/{id}", kategoriaPrzepisu.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<KategoriaPrzepisu> kategoriaPrzepisus = kategoriaPrzepisuRepository.findAll();
        assertThat(kategoriaPrzepisus).hasSize(databaseSizeBeforeDelete - 1);
    }
}
