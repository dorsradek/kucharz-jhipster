package pl.dors.radek.kucharz.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dors.radek.kucharz.domain.Produkt;
import pl.dors.radek.kucharz.repository.ProduktRepository;
import pl.dors.radek.kucharz.web.rest.util.HeaderUtil;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Produkt.
 */
@RestController
@RequestMapping("/api")
public class ProduktResource {

    private final Logger log = LoggerFactory.getLogger(ProduktResource.class);

    @Inject
    private ProduktRepository produktRepository;

    /**
     * POST /produkts -> Create a new produkt.
     */
    @RequestMapping(value = "/produkts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Produkt> createProdukt(@RequestBody Produkt produkt) throws URISyntaxException {
        log.debug("REST request to save Produkt : {}", produkt);
        if (produkt.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new produkt cannot already have an ID").body(null);
        }
        Produkt result = produktRepository.save(produkt);
        return ResponseEntity.created(new URI("/api/produkts/" + result.getId())).headers(HeaderUtil.createEntityCreationAlert("produkt", result.getName()))
            .body(result);
    }

    /**
     * PUT /produkts -> Updates an existing produkt.
     */
    @RequestMapping(value = "/produkts", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Produkt> updateProdukt(@RequestBody Produkt produkt) throws URISyntaxException {
        log.debug("REST request to update Produkt : {}", produkt);
        if (produkt.getId() == null) {
            return createProdukt(produkt);
        }
        Produkt result = produktRepository.save(produkt);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("produkt", produkt.getName())).body(result);
    }

    /**
     * GET /produkts -> get all the produkts.
     */
    @RequestMapping(value = "/produkts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Produkt> getAllProdukts() {
        log.debug("REST request to get all Produkts");
        return produktRepository.findAll();
    }

    /**
     * GET /produkts/:id -> get the "id" produkt.
     */
    @RequestMapping(value = "/produkts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Produkt> getProdukt(@PathVariable Long id) {
        log.debug("REST request to get Produkt : {}", id);
        return Optional.ofNullable(produktRepository.findOne(id)).map(produkt -> new ResponseEntity<>(produkt, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /produkts/:id -> delete the "id" produkt.
     */
    @RequestMapping(value = "/produkts/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProdukt(@PathVariable Long id) {
        log.debug("REST request to delete Produkt : {}", id);
        Produkt product = produktRepository.findOne(id);
        produktRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("produkt", product.getName())).build();
    }
}
