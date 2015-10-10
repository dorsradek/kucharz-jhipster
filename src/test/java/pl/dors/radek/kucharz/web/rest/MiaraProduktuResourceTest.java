package pl.dors.radek.kucharz.web.rest;

import pl.dors.radek.kucharz.Application;
import pl.dors.radek.kucharz.domain.MiaraProduktu;
import pl.dors.radek.kucharz.repository.MiaraProduktuRepository;

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
 * Test class for the MiaraProduktuResource REST controller.
 *
 * @see MiaraProduktuResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MiaraProduktuResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_SHORTCUT = "AAAAA";
    private static final String UPDATED_SHORTCUT = "BBBBB";

    @Inject
    private MiaraProduktuRepository miaraProduktuRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMiaraProduktuMockMvc;

    private MiaraProduktu miaraProduktu;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MiaraProduktuResource miaraProduktuResource = new MiaraProduktuResource();
        ReflectionTestUtils.setField(miaraProduktuResource, "miaraProduktuRepository", miaraProduktuRepository);
        this.restMiaraProduktuMockMvc = MockMvcBuilders.standaloneSetup(miaraProduktuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        miaraProduktu = new MiaraProduktu();
        miaraProduktu.setName(DEFAULT_NAME);
        miaraProduktu.setShortcut(DEFAULT_SHORTCUT);
    }

    @Test
    @Transactional
    public void createMiaraProduktu() throws Exception {
        int databaseSizeBeforeCreate = miaraProduktuRepository.findAll().size();

        // Create the MiaraProduktu

        restMiaraProduktuMockMvc.perform(post("/api/miaraProduktus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(miaraProduktu)))
                .andExpect(status().isCreated());

        // Validate the MiaraProduktu in the database
        List<MiaraProduktu> miaraProduktus = miaraProduktuRepository.findAll();
        assertThat(miaraProduktus).hasSize(databaseSizeBeforeCreate + 1);
        MiaraProduktu testMiaraProduktu = miaraProduktus.get(miaraProduktus.size() - 1);
        assertThat(testMiaraProduktu.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMiaraProduktu.getShortcut()).isEqualTo(DEFAULT_SHORTCUT);
    }

    @Test
    @Transactional
    public void getAllMiaraProduktus() throws Exception {
        // Initialize the database
        miaraProduktuRepository.saveAndFlush(miaraProduktu);

        // Get all the miaraProduktus
        restMiaraProduktuMockMvc.perform(get("/api/miaraProduktus"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(miaraProduktu.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].shortcut").value(hasItem(DEFAULT_SHORTCUT.toString())));
    }

    @Test
    @Transactional
    public void getMiaraProduktu() throws Exception {
        // Initialize the database
        miaraProduktuRepository.saveAndFlush(miaraProduktu);

        // Get the miaraProduktu
        restMiaraProduktuMockMvc.perform(get("/api/miaraProduktus/{id}", miaraProduktu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(miaraProduktu.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.shortcut").value(DEFAULT_SHORTCUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMiaraProduktu() throws Exception {
        // Get the miaraProduktu
        restMiaraProduktuMockMvc.perform(get("/api/miaraProduktus/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMiaraProduktu() throws Exception {
        // Initialize the database
        miaraProduktuRepository.saveAndFlush(miaraProduktu);

		int databaseSizeBeforeUpdate = miaraProduktuRepository.findAll().size();

        // Update the miaraProduktu
        miaraProduktu.setName(UPDATED_NAME);
        miaraProduktu.setShortcut(UPDATED_SHORTCUT);

        restMiaraProduktuMockMvc.perform(put("/api/miaraProduktus")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(miaraProduktu)))
                .andExpect(status().isOk());

        // Validate the MiaraProduktu in the database
        List<MiaraProduktu> miaraProduktus = miaraProduktuRepository.findAll();
        assertThat(miaraProduktus).hasSize(databaseSizeBeforeUpdate);
        MiaraProduktu testMiaraProduktu = miaraProduktus.get(miaraProduktus.size() - 1);
        assertThat(testMiaraProduktu.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMiaraProduktu.getShortcut()).isEqualTo(UPDATED_SHORTCUT);
    }

    @Test
    @Transactional
    public void deleteMiaraProduktu() throws Exception {
        // Initialize the database
        miaraProduktuRepository.saveAndFlush(miaraProduktu);

		int databaseSizeBeforeDelete = miaraProduktuRepository.findAll().size();

        // Get the miaraProduktu
        restMiaraProduktuMockMvc.perform(delete("/api/miaraProduktus/{id}", miaraProduktu.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MiaraProduktu> miaraProduktus = miaraProduktuRepository.findAll();
        assertThat(miaraProduktus).hasSize(databaseSizeBeforeDelete - 1);
    }
}
