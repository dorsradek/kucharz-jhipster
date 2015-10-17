package pl.dors.radek.kucharz.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dors.radek.kucharz.domain.MiaraProduktu;
import pl.dors.radek.kucharz.repository.MiaraProduktuRepository;
import pl.dors.radek.kucharz.web.rest.util.HeaderUtil;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MiaraProduktu.
 */
@RestController
@RequestMapping("/api")
public class MiaraProduktuResource {

    private final Logger log = LoggerFactory.getLogger(MiaraProduktuResource.class);

    @Inject
    private MiaraProduktuRepository miaraProduktuRepository;

    /**
     * POST  /miaraProduktus -> Create a new miaraProduktu.
     */
    @RequestMapping(value = "/miaraProduktus",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MiaraProduktu> createMiaraProduktu(@RequestBody MiaraProduktu miaraProduktu) throws URISyntaxException {
        log.debug("REST request to save MiaraProduktu : {}", miaraProduktu);
        if (miaraProduktu.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new miaraProduktu cannot already have an ID").body(null);
        }
        MiaraProduktu result = miaraProduktuRepository.save(miaraProduktu);
        return ResponseEntity.created(new URI("/api/miaraProduktus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("miaraProduktu", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /miaraProduktus -> Updates an existing miaraProduktu.
     */
    @RequestMapping(value = "/miaraProduktus",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MiaraProduktu> updateMiaraProduktu(@RequestBody MiaraProduktu miaraProduktu) throws URISyntaxException {
        log.debug("REST request to update MiaraProduktu : {}", miaraProduktu);
        if (miaraProduktu.getId() == null) {
            return createMiaraProduktu(miaraProduktu);
        }
        MiaraProduktu result = miaraProduktuRepository.save(miaraProduktu);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("miaraProduktu", miaraProduktu.getId().toString()))
            .body(result);
    }

    /**
     * GET  /miaraProduktus -> get all the miaraProduktus.
     */
    @RequestMapping(value = "/miaraProduktus",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MiaraProduktu> getAllMiaraProduktus() {
        log.debug("REST request to get all MiaraProduktus");
        return miaraProduktuRepository.findAll();
    }

    /**
     * GET  /miaraProduktus/:id -> get the "id" miaraProduktu.
     */
    @RequestMapping(value = "/miaraProduktus/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MiaraProduktu> getMiaraProduktu(@PathVariable Long id) {
        log.debug("REST request to get MiaraProduktu : {}", id);
        return Optional.ofNullable(miaraProduktuRepository.findOne(id))
            .map(miaraProduktu -> new ResponseEntity<>(
                miaraProduktu,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /miaraProduktus/:id -> delete the "id" miaraProduktu.
     */
    @RequestMapping(value = "/miaraProduktus/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMiaraProduktu(@PathVariable Long id) {
        log.debug("REST request to delete MiaraProduktu : {}", id);
        miaraProduktuRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("miaraProduktu", id.toString())).build();
    }
}
