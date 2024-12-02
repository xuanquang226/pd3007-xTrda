package utils.objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import data.dao.AccountDao;
import data.dto.AccountDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class AccountAuth {
    private AccountDTO accountDTO;

    @Autowired
    private AccountDao accountDao;

    public AccountDTO getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDTO account = authentication != null ? accountDao.getOneAccountByUserName(authentication.getName())
                : new AccountDTO();
        return account;
    }
}
