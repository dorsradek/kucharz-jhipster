package pl.dors.radek.kucharz.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.dors.radek.kucharz.domain.PrzepisProdukt;
import pl.dors.radek.kucharz.repository.PrzepisProduktRepository;
import pl.dors.radek.kucharz.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PrzepisProdukt.
 */
@RestController
@RequestMapping("/api")
public class PrzepisProduktResource {

    private final Logger log = LoggerFactory.getLogger(PrzepisProduktResource.class);

    @Inject
    private PrzepisProduktRepository przepisProduktRepository;

    /**
     * POST  /przepisProdukts -> Create a new przepisProdukt.
     */
    @RequestMapping(value = "/przepisProdukts",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrzepisProdukt> createPrzepisProdukt(@RequestBody PrzepisProdukt przepisProdukt) throws URISyntaxException {
        log.debug("REST request to save PrzepisProdukt : {}", przepisProdukt);
        if (przepisProdukt.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new przepisProdukt cannot already have an ID").body(null);
        }
        PrzepisProdukt result = przepisProduktRepository.save(przepisProdukt);
        return ResponseEntity.created(new URI("/api/przepisProdukts/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("przepisProdukt", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /przepisProdukts -> Updates an existing przepisProdukt.
     */
    @RequestMapping(value = "/przepisProdukts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrzepisProdukt> updatePrzepisProdukt(@RequestBody PrzepisProdukt przepisProdukt) throws URISyntaxException {
        log.debug("REST request to update PrzepisProdukt : {}", przepisProdukt);
        if (przepisProdukt.getId() == null) {
            return createPrzepisProdukt(przepisProdukt);
        }
        PrzepisProdukt result = przepisProduktRepository.save(przepisProdukt);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("przepisProdukt", przepisProdukt.getId().toString()))
                .body(result);
    }

    /**
     * GET  /przepisProdukts -> get all the przepisProdukts.
     */
    @RequestMapping(value = "/przepisProdukts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrzepisProdukt> getAllPrzepisProdukts() {
        log.debug("REST request to get all PrzepisProdukts");
        return przepisProduktRepository.findAll();
    }

    /**
     * GET  /przepisProdukts/:id -> get the "id" przepisProdukt.
     */
    @RequestMapping(value = "/przepisProdukts/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrzepisProdukt> getPrzepisProdukt(@PathVariable Long id) {
        log.debug("REST request to get PrzepisProdukt : {}", id);
        return Optional.ofNullable(przepisProduktRepository.findOne(id))
            .map(przepisProdukt -> new ResponseEntity<>(
                przepisProdukt,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /przepisProdukts/:id -> delete the "id" przepisProdukt.
     */
    @RequestMapping(value = "/przepisProdukts/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrzepisProdukt(@PathVariable Long id) {
        log.debug("REST request to delete PrzepisProdukt : {}", id);
        przepisProduktRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("przepisProdukt", id.toString())).build();
    }
}
