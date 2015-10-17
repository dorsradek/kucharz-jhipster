package pl.dors.radek.kucharz.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dors.radek.kucharz.domain.PrzepisDescription;
import pl.dors.radek.kucharz.repository.PrzepisDescriptionRepository;
import pl.dors.radek.kucharz.web.rest.util.HeaderUtil;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PrzepisDescription.
 */
@RestController
@RequestMapping("/api")
public class PrzepisDescriptionResource {

    private final Logger log = LoggerFactory.getLogger(PrzepisDescriptionResource.class);

    @Inject
    private PrzepisDescriptionRepository przepisDescriptionRepository;

    /**
     * POST  /przepisDescriptions -> Create a new przepisDescription.
     */
    @RequestMapping(value = "/przepisDescriptions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrzepisDescription> createPrzepisDescription(@RequestBody PrzepisDescription przepisDescription) throws URISyntaxException {
        log.debug("REST request to save PrzepisDescription : {}", przepisDescription);
        if (przepisDescription.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new przepisDescription cannot already have an ID").body(null);
        }
        PrzepisDescription result = przepisDescriptionRepository.save(przepisDescription);
        return ResponseEntity.created(new URI("/api/przepisDescriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("przepisDescription", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /przepisDescriptions -> Updates an existing przepisDescription.
     */
    @RequestMapping(value = "/przepisDescriptions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrzepisDescription> updatePrzepisDescription(@RequestBody PrzepisDescription przepisDescription) throws URISyntaxException {
        log.debug("REST request to update PrzepisDescription : {}", przepisDescription);
        if (przepisDescription.getId() == null) {
            return createPrzepisDescription(przepisDescription);
        }
        PrzepisDescription result = przepisDescriptionRepository.save(przepisDescription);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("przepisDescription", przepisDescription.getId().toString()))
            .body(result);
    }

    /**
     * GET  /przepisDescriptions -> get all the przepisDescriptions.
     */
    @RequestMapping(value = "/przepisDescriptions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrzepisDescription> getAllPrzepisDescriptions() {
        log.debug("REST request to get all PrzepisDescriptions");
        return przepisDescriptionRepository.findAll();
    }

    /**
     * GET  /przepisDescriptions/:id -> get the "id" przepisDescription.
     */
    @RequestMapping(value = "/przepisDescriptions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrzepisDescription> getPrzepisDescription(@PathVariable Long id) {
        log.debug("REST request to get PrzepisDescription : {}", id);
        return Optional.ofNullable(przepisDescriptionRepository.findOne(id))
            .map(przepisDescription -> new ResponseEntity<>(
                przepisDescription,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /przepisDescriptions/:id -> delete the "id" przepisDescription.
     */
    @RequestMapping(value = "/przepisDescriptions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrzepisDescription(@PathVariable Long id) {
        log.debug("REST request to delete PrzepisDescription : {}", id);
        przepisDescriptionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("przepisDescription", id.toString())).build();
    }
}
