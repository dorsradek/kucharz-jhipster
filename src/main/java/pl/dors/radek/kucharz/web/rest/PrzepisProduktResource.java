package pl.dors.radek.kucharz.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dors.radek.kucharz.domain.PrzepisPartProdukt;
import pl.dors.radek.kucharz.repository.PrzepisProduktRepository;
import pl.dors.radek.kucharz.web.rest.util.HeaderUtil;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PrzepisPartProdukt.
 */
@RestController
@RequestMapping("/api")
public class PrzepisProduktResource {

    private final Logger log = LoggerFactory.getLogger(PrzepisProduktResource.class);

    @Inject
    private PrzepisProduktRepository przepisProduktRepository;

    /**
     * POST  /przepisProdukts -> Create a new przepisPartProdukt.
     */
    @RequestMapping(value = "/przepisProdukts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrzepisPartProdukt> createPrzepisProdukt(@RequestBody PrzepisPartProdukt przepisPartProdukt) throws URISyntaxException {
        log.debug("REST request to save PrzepisPartProdukt : {}", przepisPartProdukt);
        if (przepisPartProdukt.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new przepisPartProdukt cannot already have an ID").body(null);
        }
        PrzepisPartProdukt result = przepisProduktRepository.save(przepisPartProdukt);
        return ResponseEntity.created(new URI("/api/przepisProdukts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("przepisPartProdukt", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /przepisProdukts -> Updates an existing przepisPartProdukt.
     */
    @RequestMapping(value = "/przepisProdukts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrzepisPartProdukt> updatePrzepisProdukt(@RequestBody PrzepisPartProdukt przepisPartProdukt) throws URISyntaxException {
        log.debug("REST request to update PrzepisPartProdukt : {}", przepisPartProdukt);
        if (przepisPartProdukt.getId() == null) {
            return createPrzepisProdukt(przepisPartProdukt);
        }
        PrzepisPartProdukt result = przepisProduktRepository.save(przepisPartProdukt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("przepisPartProdukt", przepisPartProdukt.getId().toString()))
            .body(result);
    }

    /**
     * GET  /przepisProdukts -> get all the przepisProdukts.
     */
    @RequestMapping(value = "/przepisProdukts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrzepisPartProdukt> getAllPrzepisProdukts() {
        log.debug("REST request to get all PrzepisProdukts");
        return przepisProduktRepository.findAll();
    }

    /**
     * GET  /przepisProdukts/:id -> get the "id" przepisPartProdukt.
     */
    @RequestMapping(value = "/przepisProdukts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrzepisPartProdukt> getPrzepisProdukt(@PathVariable Long id) {
        log.debug("REST request to get PrzepisPartProdukt : {}", id);
        return Optional.ofNullable(przepisProduktRepository.findOne(id))
            .map(przepisProdukt -> new ResponseEntity<>(
                przepisProdukt,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /przepisProdukts/:id -> delete the "id" przepisPartProdukt.
     */
    @RequestMapping(value = "/przepisProdukts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrzepisProdukt(@PathVariable Long id) {
        log.debug("REST request to delete PrzepisPartProdukt : {}", id);
        przepisProduktRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("przepisPartProdukt", id.toString())).build();
    }
}
