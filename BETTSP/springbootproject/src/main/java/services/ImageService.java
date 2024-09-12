package services;

import java.util.List;

import data.dto.ImageDTO;

public interface ImageService {
    List<ImageDTO> getAllImage();

    ImageDTO getOneImage(Long id);

    void createOneImage(ImageDTO imageDTO);

    void updateOneImage(ImageDTO imageDTO);

    void deleteOneImage(Long id);

    void deleteManyImage(List<Long> listId);
}
