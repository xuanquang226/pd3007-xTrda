package data.dao.impl;

import java.lang.System.Logger.Level;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import data.dao.ImageDao;
import data.dto.ImageDTO;
import data.dto.OrderDTO;
import data.entities.ImageEntity;
import data.entities.OrderEntity;
import data.mapper.ImageMapper;
import data.repositories.ImageRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ImageDaoImpl implements ImageDao {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageMapper imageMapper;

    @Override
    public List<ImageDTO> getAllImage() {
        return imageMapper.toDto(imageRepository.findAll());
    }

    @Override
    public ImageDTO getOneImage(Long id) {
        return imageMapper.toDto(
                imageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Khong tim thay entity")));
    }

    @Override
    public void createOneImage(ImageDTO imageDTO) {
        imageRepository.save(imageMapper.toEntity(imageDTO));
    }

    @Override
    public void createManyImage(List<ImageDTO> imageDTOs) {
        imageRepository.saveAll(imageMapper.toEntity(imageDTOs));
    }

    @Override
    public void updateOneImage(ImageDTO imageDTO) {

        Optional<ImageDTO> optional = Optional.ofNullable(getOneImage(imageDTO.getId()));
        if (optional.isPresent()) {
            imageRepository.save(imageMapper.toEntity(imageDTO));
        } else {
            throw new EntityNotFoundException("Khong tim thay entity");
        }
        // } catch (EntityNotFoundException e) {
        // System.getLogger(ImageDTO.class.getName()).log(Level.WARNING, "Khong tim thay
        // entity", e);
        // }
    }

    @Override
    @Transactional(rollbackFor = EntityNotFoundException.class)
    public void deleteOneImage(Long id) {
        Optional<ImageDTO> optionalImageDto = Optional.ofNullable(getOneImage(id));
        if (!optionalImageDto.isPresent()) {
            throw new EntityNotFoundException("Khong tim thay entity");
        }

        ImageDTO imageDTO = optionalImageDto.get();
        imageRepository.delete(imageMapper.toEntity(imageDTO));
    }

    @Override
    public void deleteManyImage(List<Long> listId) {
        for (Long id : listId) {
            try {
                Optional<ImageDTO> optionalImage = Optional.ofNullable(getOneImage(id));
                if (optionalImage.isPresent()) {
                    imageRepository.delete(imageMapper.toEntity(optionalImage.get()));
                } else {
                    throw new EntityNotFoundException("Khong tim thay entity voi id = " + id);
                }
            } catch (EntityNotFoundException entityNotFoundException) {
                System.getLogger(ImageDaoImpl.class.getName()).log(Level.INFO, entityNotFoundException);
            } catch (Exception e) {
                System.getLogger(ImageDaoImpl.class.getName()).log(Level.INFO, "Ngoai le ngoai mong muon", e);
            }

        }
    }

    @Override
    public void updateManyImage(List<ImageDTO> imageDTOs) {
        for (ImageDTO imageDTO : imageDTOs) {
            try {
                Optional<ImageDTO> optionalImage = Optional.ofNullable(getOneImage(imageDTO.getId()));
                if (optionalImage.isPresent()) {
                    imageRepository.save(imageMapper.toEntity(imageDTO));
                } else {
                    throw new EntityNotFoundException("Khong tim thay entity voi id = ");
                }
            } catch (EntityNotFoundException entityNotFoundException) {
                System.getLogger(ImageDaoImpl.class.getName()).log(Level.INFO, entityNotFoundException);
            } catch (Exception e) {
                System.getLogger(ImageDaoImpl.class.getName()).log(Level.INFO, "Ngoai le ngoai mong muon", e);
            }

        }

    }

    @Override
    public List<ImageDTO> findImageDTOsByIdProduct(Long id) {
        return imageMapper.toDto(imageRepository.findImagesByIdProduct(id));
    }

    @Override
    public void deleteImagesByIdProduct(Long id) {
        imageRepository.deleteImagesByIdProduct(id);
    }

    @Override
    public Page<ImageDTO> findManyImageOrderByIdDesc(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ImageEntity> pageEntity = imageRepository.findAllByOrderByIdAsc(pageable);
        Page<ImageDTO> pageDtoPage = pageEntity.map(imageMapper::toDto);
        return pageDtoPage;
    }
}
