package data.dao.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.CustomerDao;
import data.dto.CustomerDTO;
import data.mapper.CustomerMapper;
import data.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class CustomerDaoImpl implements CustomerDao {
    @Autowired
    private CustomerMapper mapper;

    @Autowired
    private CustomerRepository repository;

    @Override
    public void createOneCustomer(CustomerDTO customerDTO) {
        repository.save(mapper.toEntity(customerDTO));
    }

    @Override
    public void deleteCustomerByIdAccount(Long idAccount) {
        repository.delete(mapper.toEntity(getOneCustomerByIdAccount(idAccount)));
    }

    @Override
    public CustomerDTO getOneCustomerByIdAccount(Long idAccount) {
        return mapper.toDto(repository.findByIdAccount(idAccount)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay entity voi idAccount = " + idAccount)));
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO) {
        Optional<CustomerDTO> optional = Optional
                .ofNullable(mapper.toDto(repository.findById(customerDTO.getId()).orElseThrow()));
        if (optional.isPresent()) {
            repository.save(mapper.toEntity(customerDTO));
        }
    }

    @Override
    public CustomerDTO getOneCustomerById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay entity voi id customer = " + id)));
    }

    @Override
    public CustomerDTO getOneCustomerByMail(String mail) {
        return mapper.toDto(repository.findFirstByMailOrderByIdDesc(mail)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay entity voi mail = " + mail)));
    }
}
