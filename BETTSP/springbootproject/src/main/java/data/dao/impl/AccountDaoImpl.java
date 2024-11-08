package data.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.AccountDao;
import data.dto.AccountDTO;
import data.mapper.AccountMapper;
import data.repositories.AccountRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private AccountMapper mapper;

    @Autowired
    private AccountRepository repository;

    @Override
    public void createAccount(AccountDTO accountDTO) {
        repository.save(mapper.toEntity(accountDTO));
    }

    @Override
    public void deleteAccount(Long id) {
        Optional<AccountDTO> optional = Optional.ofNullable(getOneAccountById(id));
        if (optional.isPresent()) {
            repository.delete(mapper.toEntity(optional.get()));
        } else {
            throw new EntityNotFoundException("Khong co entity voi idAccount = " + id);
        }
    }

    @Override
    public List<AccountDTO> getManyAccount() {
        return mapper.toDto(repository.findAll());
    }

    @Override
    public AccountDTO getOneAccountById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay entity voi idAccount = " + id)));
    }

    @Override
    public AccountDTO getOneAccountByIdCustomer(Long idCustomer) {
        return mapper.toDto(repository.findByIdCustomer(idCustomer)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay entity voi idAccount = " + idCustomer)));
    }

    @Override
    public void updateAccount(AccountDTO accountDTO) {
        Optional<AccountDTO> optional = Optional.ofNullable(getOneAccountById(accountDTO.getId()));
        if (optional.isPresent()) {
            repository.save(mapper.toEntity(accountDTO));
        } else {
            throw new EntityNotFoundException("Khong tim thay entity voi idAccount = " + accountDTO.getId());
        }
    }

    @Override
    public AccountDTO getOneAccountByUserName(String userName) {
        return mapper.toDto(repository.findByUserName(userName)
                .orElseThrow(() -> new EntityNotFoundException("Khong tim thay entity voi username la " + userName)));
    }

    @Override
    public void updateIdCustomerById(Long idCustomer, Long id) {
        repository.updateIdCustomerById(idCustomer, id);
    }

}
