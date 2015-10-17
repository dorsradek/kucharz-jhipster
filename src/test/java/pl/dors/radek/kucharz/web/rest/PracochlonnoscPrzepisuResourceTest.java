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
import pl.dors.radek.kucharz.domain.PracochlonnoscPrzepisu;
import pl.dors.radek.kucharz.repository.PracochlonnoscPrzepisuRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PracochlonnoscPrzepisuResource REST controller.
 *
 * @see PracochlonnoscPrzepisuResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PracochlonnoscPrzepisuResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private PracochlonnoscPrzepisuRepository pracochlonnoscPrzepisuRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPracochlonnoscPrzepisuMockMvc;

    private PracochlonnoscPrzepisu pracochlonnoscPrzepisu;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PracochlonnoscPrzepisuResource pracochlonnoscPrzepisuResource = new PracochlonnoscPrzepisuResource();
        ReflectionTestUtils.setField(pracochlonnoscPrzepisuResource, "pracochlonnoscPrzepisuRepository", pracochlonnoscPrzepisuRepository);
        this.restPracochlonnoscPrzepisuMockMvc = MockMvcBuilders.standaloneSetup(pracochlonnoscPrzepisuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pracochlonnoscPrzepisu = new PracochlonnoscPrzepisu();
        pracochlonnoscPrzepisu.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPracochlonnoscPrzepisu() throws Exception {
        int databaseSizeBeforeCreate = pracochlonnoscPrzepisuRepository.findAll().size();

        // Create the PracochlonnoscPrzepisu

        restPracochlonnoscPrzepisuMockMvc.perform(post("/api/pracochlonnoscPrzepisus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pracochlonnoscPrzepisu)))
            .andExpect(status().isCreated());

        // Validate the PracochlonnoscPrzepisu in the database
        List<PracochlonnoscPrzepisu> pracochlonnoscPrzepisus = pracochlonnoscPrzepisuRepository.findAll();
        assertThat(pracochlonnoscPrzepisus).hasSize(databaseSizeBeforeCreate + 1);
        PracochlonnoscPrzepisu testPracochlonnoscPrzepisu = pracochlonnoscPrzepisus.get(pracochlonnoscPrzepisus.size() - 1);
        assertThat(testPracochlonnoscPrzepisu.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllPracochlonnoscPrzepisus() throws Exception {
        // Initialize the database
        pracochlonnoscPrzepisuRepository.saveAndFlush(pracochlonnoscPrzepisu);

        // Get all the pracochlonnoscPrzepisus
        restPracochlonnoscPrzepisuMockMvc.perform(get("/api/pracochlonnoscPrzepisus"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pracochlonnoscPrzepisu.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPracochlonnoscPrzepisu() throws Exception {
        // Initialize the database
        pracochlonnoscPrzepisuRepository.saveAndFlush(pracochlonnoscPrzepisu);

        // Get the pracochlonnoscPrzepisu
        restPracochlonnoscPrzepisuMockMvc.perform(get("/api/pracochlonnoscPrzepisus/{id}", pracochlonnoscPrzepisu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pracochlonnoscPrzepisu.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPracochlonnoscPrzepisu() throws Exception {
        // Get the pracochlonnoscPrzepisu
        restPracochlonnoscPrzepisuMockMvc.perform(get("/api/pracochlonnoscPrzepisus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePracochlonnoscPrzepisu() throws Exception {
        // Initialize the database
        pracochlonnoscPrzepisuRepository.saveAndFlush(pracochlonnoscPrzepisu);

        int databaseSizeBeforeUpdate = pracochlonnoscPrzepisuRepository.findAll().size();

        // Update the pracochlonnoscPrzepisu
        pracochlonnoscPrzepisu.setName(UPDATED_NAME);

        restPracochlonnoscPrzepisuMockMvc.perform(put("/api/pracochlonnoscPrzepisus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pracochlonnoscPrzepisu)))
            .andExpect(status().isOk());

        // Validate the PracochlonnoscPrzepisu in the database
        List<PracochlonnoscPrzepisu> pracochlonnoscPrzepisus = pracochlonnoscPrzepisuRepository.findAll();
        assertThat(pracochlonnoscPrzepisus).hasSize(databaseSizeBeforeUpdate);
        PracochlonnoscPrzepisu testPracochlonnoscPrzepisu = pracochlonnoscPrzepisus.get(pracochlonnoscPrzepisus.size() - 1);
        assertThat(testPracochlonnoscPrzepisu.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deletePracochlonnoscPrzepisu() throws Exception {
        // Initialize the database
        pracochlonnoscPrzepisuRepository.saveAndFlush(pracochlonnoscPrzepisu);

        int databaseSizeBeforeDelete = pracochlonnoscPrzepisuRepository.findAll().size();

        // Get the pracochlonnoscPrzepisu
        restPracochlonnoscPrzepisuMockMvc.perform(delete("/api/pracochlonnoscPrzepisus/{id}", pracochlonnoscPrzepisu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PracochlonnoscPrzepisu> pracochlonnoscPrzepisus = pracochlonnoscPrzepisuRepository.findAll();
        assertThat(pracochlonnoscPrzepisus).hasSize(databaseSizeBeforeDelete - 1);
    }
}
