package pl.dors.radek.kucharz.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.dors.radek.kucharz.domain.KategoriaPrzepisu;
import pl.dors.radek.kucharz.repository.KategoriaPrzepisuRepository;
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
 * REST controller for managing KategoriaPrzepisu.
 */
@RestController
@RequestMapping("/api")
public class KategoriaPrzepisuResource {

    private final Logger log = LoggerFactory.getLogger(KategoriaPrzepisuResource.class);

    @Inject
    private KategoriaPrzepisuRepository kategoriaPrzepisuRepository;

    /**
     * POST  /kategoriaPrzepisus -> Create a new kategoriaPrzepisu.
     */
    @RequestMapping(value = "/kategoriaPrzepisus",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KategoriaPrzepisu> createKategoriaPrzepisu(@RequestBody KategoriaPrzepisu kategoriaPrzepisu) throws URISyntaxException {
        log.debug("REST request to save KategoriaPrzepisu : {}", kategoriaPrzepisu);
        if (kategoriaPrzepisu.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new kategoriaPrzepisu cannot already have an ID").body(null);
        }
        KategoriaPrzepisu result = kategoriaPrzepisuRepository.save(kategoriaPrzepisu);
        return ResponseEntity.created(new URI("/api/kategoriaPrzepisus/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("kategoriaPrzepisu", result.getName()))
                .body(result);
    }

    /**
     * PUT  /kategoriaPrzepisus -> Updates an existing kategoriaPrzepisu.
     */
    @RequestMapping(value = "/kategoriaPrzepisus",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KategoriaPrzepisu> updateKategoriaPrzepisu(@RequestBody KategoriaPrzepisu kategoriaPrzepisu) throws URISyntaxException {
        log.debug("REST request to update KategoriaPrzepisu : {}", kategoriaPrzepisu);
        if (kategoriaPrzepisu.getId() == null) {
            return createKategoriaPrzepisu(kategoriaPrzepisu);
        }
        KategoriaPrzepisu result = kategoriaPrzepisuRepository.save(kategoriaPrzepisu);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("kategoriaPrzepisu", result.getName()))
                .body(result);
    }

    /**
     * GET  /kategoriaPrzepisus -> get all the kategoriaPrzepisus.
     */
    @RequestMapping(value = "/kategoriaPrzepisus",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<KategoriaPrzepisu> getAllKategoriaPrzepisus() {
        log.debug("REST request to get all KategoriaPrzepisus");
        return kategoriaPrzepisuRepository.findAll();
    }

    /**
     * GET  /kategoriaPrzepisus/:id -> get the "id" kategoriaPrzepisu.
     */
    @RequestMapping(value = "/kategoriaPrzepisus/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<KategoriaPrzepisu> getKategoriaPrzepisu(@PathVariable Long id) {
        log.debug("REST request to get KategoriaPrzepisu : {}", id);
        return Optional.ofNullable(kategoriaPrzepisuRepository.findOne(id))
            .map(kategoriaPrzepisu -> new ResponseEntity<>(
                kategoriaPrzepisu,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /kategoriaPrzepisus/:id -> delete the "id" kategoriaPrzepisu.
     */
    @RequestMapping(value = "/kategoriaPrzepisus/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteKategoriaPrzepisu(@PathVariable Long id) {
        log.debug("REST request to delete KategoriaPrzepisu : {}", id);
        kategoriaPrzepisuRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("kategoriaPrzepisu", id.toString())).build();
    }
}
