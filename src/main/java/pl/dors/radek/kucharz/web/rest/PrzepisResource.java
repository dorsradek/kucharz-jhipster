package pl.dors.radek.kucharz.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.dors.radek.kucharz.domain.Przepis;
import pl.dors.radek.kucharz.repository.PrzepisRepository;
import pl.dors.radek.kucharz.service.PrzepisService;
import pl.dors.radek.kucharz.web.rest.util.HeaderUtil;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Przepis.
 */
@RestController
@RequestMapping("/api")
public class PrzepisResource {

    private final Logger log = LoggerFactory.getLogger(PrzepisResource.class);

    @Inject
    private PrzepisRepository przepisRepository;

    @Inject
    private PrzepisService przepisService;

    /**
     * POST  /przepiss -> Create a new przepis.
     */
    @RequestMapping(value = "/przepiss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Przepis> createPrzepis(@RequestBody Przepis przepis) throws URISyntaxException {
        log.debug("REST request to save Przepis : {}", przepis);
        if (przepis.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new przepis cannot already have an ID").body(null);
        }
        Przepis result = przepisRepository.save(przepis);
        return ResponseEntity.created(new URI("/api/przepiss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("przepis", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /przepiss -> Updates an existing przepis.
     */
    @RequestMapping(value = "/przepiss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Przepis> updatePrzepis(@RequestBody Przepis przepis) throws URISyntaxException {
        log.debug("REST request to update Przepis : {}", przepis);
        if (przepis.getId() == null) {
            return createPrzepis(przepis);
        }
        Przepis result = przepisRepository.save(przepis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("przepis", przepis.getId().toString()))
            .body(result);
    }

    /**
     * GET  /przepiss -> get all the przepiss.
     */
    @RequestMapping(value = "/przepiss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Przepis> getAllPrzepiss(HttpServletRequest request) {
        log.debug("REST request to get all Przepiss");

        //TODO: przeniesc do servicu
        List<Przepis> przepisy = przepisRepository.findAll();
        String globalPath = request.getSession().getServletContext().getRealPath("/");
        for (Przepis p : przepisy) {
            String imagePath = globalPath + File.separator + p.getId() + ".png";
            String imageBase64String = "";
            try {
                byte[] data = Base64.encode(FileUtils.readFileToByteArray(new File(imagePath)));
                imageBase64String = new String(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            p.setImage(imageBase64String);
        }
        return przepisy;
    }

    /**
     * GET  /przepiss/:id -> get the "id" przepis.
     */
    @RequestMapping(value = "/przepiss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Przepis> getPrzepis(@PathVariable Long id) {
        log.debug("REST request to get Przepis : {}", id);
        return przepisService.getPrzepis(id)
            .map(przepis -> new ResponseEntity<>(
                przepis,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /przepiss/:id -> delete the "id" przepis.
     */
    @RequestMapping(value = "/przepiss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrzepis(@PathVariable Long id) {
        log.debug("REST request to delete Przepis : {}", id);
        przepisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("przepis", id.toString())).build();
    }

    @RequestMapping(value = "/przepisimage",
        method = RequestMethod.POST)
    @Timed
    public ResponseEntity<Void> uploadFile(@RequestPart("przepisId") String przepisId, @RequestParam("file") MultipartFile file, HttpServletRequest request) {

        String rpath = request.getSession().getServletContext().getRealPath("/");
        rpath = rpath + File.separator + przepisId + ".png";

        try {
            FileUtils.writeByteArrayToFile(new File(rpath), file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }


//        return przepisService.getPrzepis(Long.valueOf(przepisId))
//            .map(przepis -> {
//                try {
//                    Blob blob = new SerialBlob(file.getBytes());
//                    przepis.setImage(blob);
//                    przepisRepository.save(przepis);
//                } catch (SQLException e) {
//                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
//                } catch (IOException e) {
//                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
//                }
//                return new ResponseEntity<>(przepis, HttpStatus.OK);
//            }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "przepisimage/{przepisId}", method = RequestMethod.GET)
    @Timed
    public ResponseEntity<String> downloadImage(@PathVariable Long przepisId, HttpServletRequest request) {
        Optional<Przepis> przepis = przepisService.getPrzepis(Long.valueOf(przepisId));
        String rpath = request.getSession().getServletContext().getRealPath("/");
        rpath = rpath + File.separator + przepisId + ".png";

        String response = "";
        try {
            byte[] data = Base64.encode(FileUtils.readFileToByteArray(new File(rpath)));
            response = "{\"test\": \"" + new String(data) + "\"}";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
