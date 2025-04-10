package data.dao;

import java.util.List;

import data.dto.AccountDTO;

public interface AccountDao {
    void createAccount(AccountDTO accountDTO);

    AccountDTO getOneAccountById(Long id);

    List<AccountDTO> getManyAccount();

    AccountDTO getOneAccountByIdCustomer(Long idCustomer);

    AccountDTO getOneAccountByUserName(String userName);

    void updateAccount(AccountDTO accountDTO);

    void deleteAccount(Long id);

    void updateIdCustomerById(Long idCustomer, Long id);

}
