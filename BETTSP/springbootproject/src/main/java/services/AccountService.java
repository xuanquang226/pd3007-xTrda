package services;

import java.util.List;

import data.dto.AccountDTO;
import data.dto.CustomerDTO;
import jakarta.servlet.http.HttpServletRequest;
import utils.TupleToken;
import utils.objects.AccessTokenAndIdAccount;

public interface AccountService {
    void createAccount(AccountDTO accountDTO, CustomerDTO customerDTO);

    AccountDTO getOneAccountById(Long id);

    List<AccountDTO> getManyAccount();

    AccountDTO getOneAccountByIdCustomer(Long idCustomer);

    AccountDTO getOneAccountByUserName(String userName);

    void updateAccount(AccountDTO accountDTO);

    void deleteAccount(Long id);

    TupleToken loginAccountWithoutToken(String username, String password, HttpServletRequest request);

    TupleToken validateRefreshToken(HttpServletRequest request);

    boolean validateExistsUsername(String userName);

    void verifyAccount(String token);

    Boolean forgotPassword(String email);

    void updateAccountToResetPassword(AccountDTO accountDTO);

    AccessTokenAndIdAccount verifyTokenReset(String tokenReset);
}
