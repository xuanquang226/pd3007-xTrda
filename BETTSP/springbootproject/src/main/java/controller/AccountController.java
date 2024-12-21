package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import data.dto.AccountDTO;
import data.dto.CustomerDTO;
import jakarta.servlet.http.HttpServletRequest;
import services.AccountService;
import utils.TupleToken;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> createAccount(@RequestPart("account") String jsAccount,
            @RequestPart("customer") String jsCustomer) throws JsonProcessingException, JsonMappingException {
        ObjectMapper objectMapper = new ObjectMapper();
        AccountDTO accountDTO = objectMapper.readValue(jsAccount, AccountDTO.class);
        CustomerDTO customerDTO = objectMapper.readValue(jsCustomer, CustomerDTO.class);
        accountService.createAccount(accountDTO, customerDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/login")
    public ResponseEntity<TupleToken> loginAccount(@RequestParam String username, @RequestParam String password,
            HttpServletRequest request) {
        TupleToken tupleToken = accountService.loginAccountWithoutToken(username, password, request);
        if (tupleToken != null) {
            return ResponseEntity.ok(tupleToken);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountByUsername(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getOneAccountById(id));
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateExistsUserName(@RequestParam String userName) {
        return ResponseEntity.ok(accountService.validateExistsUsername(userName));
    }

    @GetMapping("/auth")
    public ResponseEntity<TupleToken> validateRefreshToken(HttpServletRequest request) {
        return ResponseEntity.ok(accountService.validateRefreshToken(request));
    }
}
