package pl.dors.radek.kucharz.web.rest;

import com.codahale.metrics.annotation.Timed;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
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
import pl.dors.radek.kucharz.web.rest.dto.PrzepisDTO;
import pl.dors.radek.kucharz.web.rest.util.HeaderUtil;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<Przepis> createPrzepis(@RequestBody Przepis przepis, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Przepis : {}", przepis);
        if (przepis.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new przepis cannot already have an ID").body(null);
        }
        Przepis result = przepisRepository.save(przepis);

        String base64Image = przepis.getImage().split(",", 2)[1];
        byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);

        String globalPath = request.getSession().getServletContext().getRealPath("/");
        try {
            BufferedImage image;
            try (ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes)) {
                image = ImageIO.read(bais);
            }

            ImageIO.write(image, "jpeg", new File(globalPath + File.separator + "images" + File.separator + result.getId() + "_original.jpeg"));

            Thumbnails.of(image)
                .crop(Positions.CENTER)
                .size(1750, 625)
                .outputFormat("jpeg")
                .outputQuality(1)
                .toFile(globalPath + File.separator + "images" + File.separator + result.getId() + "_medium");

            Thumbnails.of(image)
                .crop(Positions.CENTER)
                .size(420, 150)
                .outputFormat("jpeg")
                .outputQuality(1)
                .toFile(globalPath + File.separator + "images" + File.separator + result.getId() + "_thumbnail");
        } catch (IOException e) {
            e.printStackTrace();
        }


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
    public ResponseEntity<Przepis> updatePrzepis(@RequestBody Przepis przepis, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to update Przepis : {}", przepis);
        if (przepis.getId() == null) {
            return createPrzepis(przepis, request);
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
    public List<PrzepisDTO> getAllPrzepiss(HttpServletRequest request) {
        log.debug("REST request to get all Przepiss");

        //TODO: przeniesc do servicu
        List<PrzepisDTO> result = new ArrayList<>();
        List<Przepis> przepisy = przepisRepository.findAll();
        String globalPath = request.getSession().getServletContext().getRealPath("/");
        for (Przepis p : przepisy) {
            String imagePath = globalPath + File.separator + "images" + File.separator + p.getId() + "_thumbnail.jpeg";
            String imageBase64String = "";
            try {
                imageBase64String = new String(Base64.encode(Files.readAllBytes(new File(imagePath).toPath())));
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrzepisDTO przepisDTO = new PrzepisDTO();
            przepisDTO.setId(p.getId());
            przepisDTO.setName(p.getName());
            przepisDTO.setImage(imageBase64String);

            result.add(przepisDTO);
        }
        return result;
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


        return new ResponseEntity<>(HttpStatus.OK);
    }

}
