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
import pl.dors.radek.kucharz.domain.PrzepisDescription;
import pl.dors.radek.kucharz.repository.PrzepisDescriptionRepository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PrzepisDescriptionResource REST controller.
 *
 * @see PrzepisDescriptionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PrzepisDescriptionResourceTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_TEXT = "AAAAA";
    private static final String UPDATED_TEXT = "BBBBB";

    @Inject
    private PrzepisDescriptionRepository przepisDescriptionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPrzepisDescriptionMockMvc;

    private PrzepisDescription przepisDescription;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PrzepisDescriptionResource przepisDescriptionResource = new PrzepisDescriptionResource();
        ReflectionTestUtils.setField(przepisDescriptionResource, "przepisDescriptionRepository", przepisDescriptionRepository);
        this.restPrzepisDescriptionMockMvc = MockMvcBuilders.standaloneSetup(przepisDescriptionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        przepisDescription = new PrzepisDescription();
        przepisDescription.setTitle(DEFAULT_TITLE);
        przepisDescription.setText(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void createPrzepisDescription() throws Exception {
        int databaseSizeBeforeCreate = przepisDescriptionRepository.findAll().size();

        // Create the PrzepisDescription

        restPrzepisDescriptionMockMvc.perform(post("/api/przepisDescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(przepisDescription)))
            .andExpect(status().isCreated());

        // Validate the PrzepisDescription in the database
        List<PrzepisDescription> przepisDescriptions = przepisDescriptionRepository.findAll();
        assertThat(przepisDescriptions).hasSize(databaseSizeBeforeCreate + 1);
        PrzepisDescription testPrzepisDescription = przepisDescriptions.get(przepisDescriptions.size() - 1);
        assertThat(testPrzepisDescription.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPrzepisDescription.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void getAllPrzepisDescriptions() throws Exception {
        // Initialize the database
        przepisDescriptionRepository.saveAndFlush(przepisDescription);

        // Get all the przepisDescriptions
        restPrzepisDescriptionMockMvc.perform(get("/api/przepisDescriptions"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(przepisDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())));
    }

    @Test
    @Transactional
    public void getPrzepisDescription() throws Exception {
        // Initialize the database
        przepisDescriptionRepository.saveAndFlush(przepisDescription);

        // Get the przepisDescription
        restPrzepisDescriptionMockMvc.perform(get("/api/przepisDescriptions/{id}", przepisDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(przepisDescription.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrzepisDescription() throws Exception {
        // Get the przepisDescription
        restPrzepisDescriptionMockMvc.perform(get("/api/przepisDescriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrzepisDescription() throws Exception {
        // Initialize the database
        przepisDescriptionRepository.saveAndFlush(przepisDescription);

        int databaseSizeBeforeUpdate = przepisDescriptionRepository.findAll().size();

        // Update the przepisDescription
        przepisDescription.setTitle(UPDATED_TITLE);
        przepisDescription.setText(UPDATED_TEXT);

        restPrzepisDescriptionMockMvc.perform(put("/api/przepisDescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(przepisDescription)))
            .andExpect(status().isOk());

        // Validate the PrzepisDescription in the database
        List<PrzepisDescription> przepisDescriptions = przepisDescriptionRepository.findAll();
        assertThat(przepisDescriptions).hasSize(databaseSizeBeforeUpdate);
        PrzepisDescription testPrzepisDescription = przepisDescriptions.get(przepisDescriptions.size() - 1);
        assertThat(testPrzepisDescription.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPrzepisDescription.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void deletePrzepisDescription() throws Exception {
        // Initialize the database
        przepisDescriptionRepository.saveAndFlush(przepisDescription);

        int databaseSizeBeforeDelete = przepisDescriptionRepository.findAll().size();

        // Get the przepisDescription
        restPrzepisDescriptionMockMvc.perform(delete("/api/przepisDescriptions/{id}", przepisDescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PrzepisDescription> przepisDescriptions = przepisDescriptionRepository.findAll();
        assertThat(przepisDescriptions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
