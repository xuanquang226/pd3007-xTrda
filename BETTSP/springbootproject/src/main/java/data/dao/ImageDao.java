package data.dao;

import java.util.List;

import data.dto.ImageDTO;

public interface ImageDao {
    List<ImageDTO> getAllImage();

    ImageDTO getOneImage(Long id);

    void createOneImage(ImageDTO imageDTO);

    void createManyImage(List<ImageDTO> imageDTOs);

    void updateOneImage(ImageDTO imageDTO);

    void updateManyImage(List<ImageDTO> imageDTOs);

    void deleteOneImage(Long id);

    void deleteManyImage(List<Long> ids);

    List<ImageDTO> findImageDTOsByIdProduct(Long id);

    void deleteImagesByIdProduct(Long id);
}
