package pl.dors.radek.kucharz.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dors.radek.kucharz.domain.PracochlonnoscPrzepisu;
import pl.dors.radek.kucharz.repository.PracochlonnoscPrzepisuRepository;
import pl.dors.radek.kucharz.web.rest.util.HeaderUtil;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PracochlonnoscPrzepisu.
 */
@RestController
@RequestMapping("/api")
public class PracochlonnoscPrzepisuResource {

    private final Logger log = LoggerFactory.getLogger(PracochlonnoscPrzepisuResource.class);

    @Inject
    private PracochlonnoscPrzepisuRepository pracochlonnoscPrzepisuRepository;

    /**
     * POST  /pracochlonnoscPrzepisus -> Create a new pracochlonnoscPrzepisu.
     */
    @RequestMapping(value = "/pracochlonnoscPrzepisus",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PracochlonnoscPrzepisu> createPracochlonnoscPrzepisu(@RequestBody PracochlonnoscPrzepisu pracochlonnoscPrzepisu) throws URISyntaxException {
        log.debug("REST request to save PracochlonnoscPrzepisu : {}", pracochlonnoscPrzepisu);
        if (pracochlonnoscPrzepisu.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pracochlonnoscPrzepisu cannot already have an ID").body(null);
        }
        PracochlonnoscPrzepisu result = pracochlonnoscPrzepisuRepository.save(pracochlonnoscPrzepisu);
        return ResponseEntity.created(new URI("/api/pracochlonnoscPrzepisus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pracochlonnoscPrzepisu", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pracochlonnoscPrzepisus -> Updates an existing pracochlonnoscPrzepisu.
     */
    @RequestMapping(value = "/pracochlonnoscPrzepisus",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PracochlonnoscPrzepisu> updatePracochlonnoscPrzepisu(@RequestBody PracochlonnoscPrzepisu pracochlonnoscPrzepisu) throws URISyntaxException {
        log.debug("REST request to update PracochlonnoscPrzepisu : {}", pracochlonnoscPrzepisu);
        if (pracochlonnoscPrzepisu.getId() == null) {
            return createPracochlonnoscPrzepisu(pracochlonnoscPrzepisu);
        }
        PracochlonnoscPrzepisu result = pracochlonnoscPrzepisuRepository.save(pracochlonnoscPrzepisu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pracochlonnoscPrzepisu", pracochlonnoscPrzepisu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pracochlonnoscPrzepisus -> get all the pracochlonnoscPrzepisus.
     */
    @RequestMapping(value = "/pracochlonnoscPrzepisus",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PracochlonnoscPrzepisu> getAllPracochlonnoscPrzepisus() {
        log.debug("REST request to get all PracochlonnoscPrzepisus");
        return pracochlonnoscPrzepisuRepository.findAll();
    }

    /**
     * GET  /pracochlonnoscPrzepisus/:id -> get the "id" pracochlonnoscPrzepisu.
     */
    @RequestMapping(value = "/pracochlonnoscPrzepisus/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PracochlonnoscPrzepisu> getPracochlonnoscPrzepisu(@PathVariable Long id) {
        log.debug("REST request to get PracochlonnoscPrzepisu : {}", id);
        return Optional.ofNullable(pracochlonnoscPrzepisuRepository.findOne(id))
            .map(pracochlonnoscPrzepisu -> new ResponseEntity<>(
                pracochlonnoscPrzepisu,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pracochlonnoscPrzepisus/:id -> delete the "id" pracochlonnoscPrzepisu.
     */
    @RequestMapping(value = "/pracochlonnoscPrzepisus/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePracochlonnoscPrzepisu(@PathVariable Long id) {
        log.debug("REST request to delete PracochlonnoscPrzepisu : {}", id);
        pracochlonnoscPrzepisuRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pracochlonnoscPrzepisu", id.toString())).build();
    }
}
