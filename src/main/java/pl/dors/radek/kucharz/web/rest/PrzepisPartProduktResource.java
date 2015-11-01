package pl.dors.radek.kucharz.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dors.radek.kucharz.domain.PrzepisPartProdukt;
import pl.dors.radek.kucharz.repository.PrzepisPartProduktRepository;
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
public class PrzepisPartProduktResource {

    private final Logger log = LoggerFactory.getLogger(PrzepisPartProduktResource.class);

    @Inject
    private PrzepisPartProduktRepository przepisPartProduktRepository;

    /**
     * POST  /przepisPartProdukts -> Create a new przepisPartProdukt.
     */
    @RequestMapping(value = "/przepisPartProdukts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrzepisPartProdukt> createPrzepisPartProdukt(@RequestBody PrzepisPartProdukt przepisPartProdukt) throws URISyntaxException {
        log.debug("REST request to save PrzepisPartProdukt : {}", przepisPartProdukt);
        if (przepisPartProdukt.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new przepisPartProdukt cannot already have an ID").body(null);
        }
        PrzepisPartProdukt result = przepisPartProduktRepository.save(przepisPartProdukt);
        return ResponseEntity.created(new URI("/api/przepisPartProdukts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("przepisPartProdukt", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /przepisPartProdukts -> Updates an existing przepisPartProdukt.
     */
    @RequestMapping(value = "/przepisPartProdukts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrzepisPartProdukt> updatePrzepisPartProdukt(@RequestBody PrzepisPartProdukt przepisPartProdukt) throws URISyntaxException {
        log.debug("REST request to update PrzepisPartProdukt : {}", przepisPartProdukt);
        if (przepisPartProdukt.getId() == null) {
            return createPrzepisPartProdukt(przepisPartProdukt);
        }
        PrzepisPartProdukt result = przepisPartProduktRepository.save(przepisPartProdukt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("przepisPartProdukt", przepisPartProdukt.getId().toString()))
            .body(result);
    }

    /**
     * GET  /przepisPartProdukts -> get all the przepisPartProdukts.
     */
    @RequestMapping(value = "/przepisPartProdukts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrzepisPartProdukt> getAllPrzepisPartProdukts() {
        log.debug("REST request to get all PrzepisPartProdukts");
        return przepisPartProduktRepository.findAll();
    }

    /**
     * GET  /przepisPartProdukts/:id -> get the "id" przepisPartProdukt.
     */
    @RequestMapping(value = "/przepisPartProdukts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrzepisPartProdukt> getPrzepisPartProdukt(@PathVariable Long id) {
        log.debug("REST request to get PrzepisPartProdukt : {}", id);
        return Optional.ofNullable(przepisPartProduktRepository.findOne(id))
            .map(przepisPartProdukt -> new ResponseEntity<>(
                przepisPartProdukt,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /przepisPartProdukts/:id -> delete the "id" przepisPartProdukt.
     */
    @RequestMapping(value = "/przepisPartProdukts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrzepisPartProdukt(@PathVariable Long id) {
        log.debug("REST request to delete PrzepisPartProdukt : {}", id);
        przepisPartProduktRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("przepisPartProdukt", id.toString())).build();
    }
}
