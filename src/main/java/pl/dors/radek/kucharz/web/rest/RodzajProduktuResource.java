package pl.dors.radek.kucharz.web.rest;

import com.codahale.metrics.annotation.Timed;
import pl.dors.radek.kucharz.domain.RodzajProduktu;
import pl.dors.radek.kucharz.repository.RodzajProduktuRepository;
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
 * REST controller for managing RodzajProduktu.
 */
@RestController
@RequestMapping("/api")
public class RodzajProduktuResource {

    private final Logger log = LoggerFactory.getLogger(RodzajProduktuResource.class);

    @Inject
    private RodzajProduktuRepository rodzajProduktuRepository;

    /**
     * POST  /rodzajProduktus -> Create a new rodzajProduktu.
     */
    @RequestMapping(value = "/rodzajProduktus",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RodzajProduktu> createRodzajProduktu(@RequestBody RodzajProduktu rodzajProduktu) throws URISyntaxException {
        log.debug("REST request to save RodzajProduktu : {}", rodzajProduktu);
        if (rodzajProduktu.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new rodzajProduktu cannot already have an ID").body(null);
        }
        RodzajProduktu result = rodzajProduktuRepository.save(rodzajProduktu);
        return ResponseEntity.created(new URI("/api/rodzajProduktus/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("rodzajProduktu", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /rodzajProduktus -> Updates an existing rodzajProduktu.
     */
    @RequestMapping(value = "/rodzajProduktus",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RodzajProduktu> updateRodzajProduktu(@RequestBody RodzajProduktu rodzajProduktu) throws URISyntaxException {
        log.debug("REST request to update RodzajProduktu : {}", rodzajProduktu);
        if (rodzajProduktu.getId() == null) {
            return createRodzajProduktu(rodzajProduktu);
        }
        RodzajProduktu result = rodzajProduktuRepository.save(rodzajProduktu);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("rodzajProduktu", rodzajProduktu.getId().toString()))
                .body(result);
    }

    /**
     * GET  /rodzajProduktus -> get all the rodzajProduktus.
     */
    @RequestMapping(value = "/rodzajProduktus",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RodzajProduktu> getAllRodzajProduktus() {
        log.debug("REST request to get all RodzajProduktus");
        return rodzajProduktuRepository.findAll();
    }

    /**
     * GET  /rodzajProduktus/:id -> get the "id" rodzajProduktu.
     */
    @RequestMapping(value = "/rodzajProduktus/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RodzajProduktu> getRodzajProduktu(@PathVariable Long id) {
        log.debug("REST request to get RodzajProduktu : {}", id);
        return Optional.ofNullable(rodzajProduktuRepository.findOne(id))
            .map(rodzajProduktu -> new ResponseEntity<>(
                rodzajProduktu,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rodzajProduktus/:id -> delete the "id" rodzajProduktu.
     */
    @RequestMapping(value = "/rodzajProduktus/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRodzajProduktu(@PathVariable Long id) {
        log.debug("REST request to delete RodzajProduktu : {}", id);
        rodzajProduktuRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rodzajProduktu", id.toString())).build();
    }
}
