package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.dto.ImageDTO;
import data.dto.OrderDTO;
import services.ImageService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    /*
     * @PostMapping("/upload")
     * public ResponseEntity<String> upload(@RequestParam("file") MultipartFile
     * file) {
     * 
     * // Get directory
     * String directory =
     * "D:/Quang/project-30-07-2024/BETTSP/springbootproject/src/main/resources/static/images/";
     * 
     * // Combine directory + fileName
     * String getFileName = file.getOriginalFilename();
     * Path filePath = Paths.get(directory, getFileName);
     * 
     * // Save file into filePath
     * try {
     * Files.write(filePath, file.getBytes());
     * return new ResponseEntity<>(filePath.toString(), HttpStatus.OK);
     * } catch (IOException e) {
     * e.printStackTrace();
     * return new ResponseEntity<>("File not found",
     * HttpStatus.INTERNAL_SERVER_ERROR);
     * }
     * }
     * 
     * @PostMapping("/upload2")
     * public ResponseEntity<String> upload2(@RequestParam("file") MultipartFile
     * file) {
     * try {
     * String directory =
     * "D:/Quang/project-30-07-2024/BETTSP/springbootproject/src/main/resources/static/images/";
     * Path filePath = Paths.get(directory, file.getOriginalFilename());
     * 
     * // Tao uri cua file
     * // Resource resource = new UrlResource(filePath.toUri());
     * // System.out.println(resource);
     * // if (resource.exists() || resource.isReadable()) {
     * // return
     * ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
     * // } else {
     * // Files.write(filePath, file.getBytes());
     * // return
     * ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
     * // }
     * String urlResource = "http://localhost:8082/images/";
     * String urlImage = urlResource + file.getOriginalFilename();
     * Files.write(filePath, file.getBytes());
     * return new ResponseEntity<>(urlImage, HttpStatus.OK);
     * 
     * } catch (MalformedURLException mException) {
     * throw new RuntimeException("error:" + mException.getMessage());
     * } catch (IOException e) {
     * throw new RuntimeException("error: " + e.getMessage());
     * }
     * }
     */

    @GetMapping("/many")
    public List<ImageDTO> getImageAll() {
        return imageService.getAllImage();
    }

    @GetMapping("/{id}")
    public ImageDTO getOneImage(@PathVariable Long id) {
        return imageService.getOneImage(id);
    }

    @PostMapping
    public void createOneImage(@RequestBody ImageDTO imageDTO) {
        imageService.createOneImage(imageDTO);
    }

    @PutMapping
    public void updateOneImage(@RequestBody ImageDTO imageDTO) {
        imageService.updateOneImage(imageDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOneImage(@PathVariable Long id) {
        imageService.deleteOneImage(id);
    }

    @DeleteMapping
    public void deleteManyImage(@RequestBody List<Long> ids) {
        imageService.deleteManyImage(ids);
    }

    @GetMapping("/many2")
    public ResponseEntity<Page<ImageDTO>> getManyOrder(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<ImageDTO> imageList = imageService.findManyImage(page, size);
        return ResponseEntity.ok(imageList);
    };
}
