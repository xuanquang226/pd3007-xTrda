package services;

import java.util.List;

import data.dto.AccountDTO;
import data.dto.CustomerDTO;
import jakarta.servlet.http.HttpServletRequest;
import utils.TupleToken;

public interface AccountService {
    void createAccount(AccountDTO accountDTO, CustomerDTO customerDTO);

    AccountDTO getOneAccountById(Long id);

    List<AccountDTO> getManyAccount();

    AccountDTO getOneAccountByIdCustomer(Long idCustomer);

    AccountDTO getOneAccountByUserName(String userName);

    void updateAccount(AccountDTO accountDTO);

    void deleteAccount(Long id);

    TupleToken loginAccountWithoutToken(String username, String password, HttpServletRequest request);

    TupleToken validateRefreshToken(String refreshToken, HttpServletRequest request);
}
