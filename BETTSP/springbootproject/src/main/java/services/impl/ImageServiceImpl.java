package services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import data.dao.ImageDao;
import data.dto.ImageDTO;
import services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageDao dao;

    @Override
    public List<ImageDTO> getAllImage() {
        return dao.getAllImage();
    }

    @Override
    public ImageDTO getOneImage(Long id) {
        return dao.getOneImage(id);
    }

    @Override
    public void createOneImage(ImageDTO imageDTO) {
        dao.createOneImage(imageDTO);
    }

    @Override
    public void updateOneImage(ImageDTO imageDTO) {
        dao.updateOneImage(imageDTO);
    }

    @Override
    public void deleteOneImage(Long id) {
        dao.deleteOneImage(id);
    }

    @Override
    public void deleteManyImage(List<Long> listId) {
        dao.deleteManyImage(listId);
    }

    @Override
    public Page<ImageDTO> findManyImage(int page, int size) {
        return dao.findManyImageOrderByIdDesc(page, size);
    }
}
