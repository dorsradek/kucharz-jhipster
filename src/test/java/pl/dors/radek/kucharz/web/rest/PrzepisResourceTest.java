package pl.dors.radek.kucharz.web.rest;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
import pl.dors.radek.kucharz.domain.Przepis;
import pl.dors.radek.kucharz.repository.PrzepisRepository;
import pl.dors.radek.kucharz.service.PrzepisService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PrzepisResource REST controller.
 *
 * @see PrzepisResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PrzepisResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_DURATION = "AAAAA";
    private static final String UPDATED_DURATION = "BBBBB";

    private static final DateTime DEFAULT_CREATION_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_CREATION_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_CREATION_DATE_STR = dateTimeFormatter.print(DEFAULT_CREATION_DATE);

    private static final DateTime DEFAULT_MODIFICATION_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_MODIFICATION_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_MODIFICATION_DATE_STR = dateTimeFormatter.print(DEFAULT_MODIFICATION_DATE);
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private PrzepisRepository przepisRepository;

    @Inject
    private PrzepisService przepisService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPrzepisMockMvc;

    private Przepis przepis;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PrzepisResource przepisResource = new PrzepisResource();
        ReflectionTestUtils.setField(przepisResource, "przepisRepository", przepisRepository);
        ReflectionTestUtils.setField(przepisResource, "przepisService", przepisService);
        this.restPrzepisMockMvc = MockMvcBuilders.standaloneSetup(przepisResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        przepis = new Przepis();
        przepis.setDuration(DEFAULT_DURATION);
        przepis.setCreationDate(DEFAULT_CREATION_DATE);
        przepis.setModificationDate(DEFAULT_MODIFICATION_DATE);
        przepis.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createPrzepis() throws Exception {
        int databaseSizeBeforeCreate = przepisRepository.findAll().size();

        // Create the Przepis

        restPrzepisMockMvc.perform(post("/api/przepiss")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(przepis)))
            .andExpect(status().isCreated());

        // Validate the Przepis in the database
        List<Przepis> przepiss = przepisRepository.findAll();
        assertThat(przepiss).hasSize(databaseSizeBeforeCreate + 1);
        Przepis testPrzepis = przepiss.get(przepiss.size() - 1);
        assertThat(testPrzepis.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testPrzepis.getCreationDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testPrzepis.getModificationDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_MODIFICATION_DATE);
        assertThat(testPrzepis.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllPrzepiss() throws Exception {
        // Initialize the database
        przepisRepository.saveAndFlush(przepis);

        // Get all the przepiss
        restPrzepisMockMvc.perform(get("/api/przepiss"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[*].id").value(hasItem(przepis.getId().intValue())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE_STR)))
            .andExpect(jsonPath("$.[*].modificationDate").value(hasItem(DEFAULT_MODIFICATION_DATE_STR)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPrzepis() throws Exception {
        // Initialize the database
        przepisRepository.saveAndFlush(przepis);

        // Get the przepis
        restPrzepisMockMvc.perform(get("/api/przepiss/{id}", przepis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(przepis.getId().intValue()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE_STR))
            .andExpect(jsonPath("$.modificationDate").value(DEFAULT_MODIFICATION_DATE_STR))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrzepis() throws Exception {
        // Get the przepis
        restPrzepisMockMvc.perform(get("/api/przepiss/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrzepis() throws Exception {
        // Initialize the database
        przepisRepository.saveAndFlush(przepis);

        int databaseSizeBeforeUpdate = przepisRepository.findAll().size();

        // Update the przepis
        przepis.setDuration(UPDATED_DURATION);
        przepis.setCreationDate(UPDATED_CREATION_DATE);
        przepis.setModificationDate(UPDATED_MODIFICATION_DATE);
        przepis.setName(UPDATED_NAME);

        restPrzepisMockMvc.perform(put("/api/przepiss")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(przepis)))
            .andExpect(status().isOk());

        // Validate the Przepis in the database
        List<Przepis> przepiss = przepisRepository.findAll();
        assertThat(przepiss).hasSize(databaseSizeBeforeUpdate);
        Przepis testPrzepis = przepiss.get(przepiss.size() - 1);
        assertThat(testPrzepis.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testPrzepis.getCreationDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testPrzepis.getModificationDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_MODIFICATION_DATE);
        assertThat(testPrzepis.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deletePrzepis() throws Exception {
        // Initialize the database
        przepisRepository.saveAndFlush(przepis);

        int databaseSizeBeforeDelete = przepisRepository.findAll().size();

        // Get the przepis
        restPrzepisMockMvc.perform(delete("/api/przepiss/{id}", przepis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Przepis> przepiss = przepisRepository.findAll();
        assertThat(przepiss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
