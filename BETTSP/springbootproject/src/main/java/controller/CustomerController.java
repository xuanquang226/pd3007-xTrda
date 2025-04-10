package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import data.dto.CustomerDTO;
import services.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<CustomerDTO> getOneCustomer() {
        return ResponseEntity.ok(customerService.getOneCustomer());
    }

    @GetMapping("/validate")
    public ResponseEntity<CustomerDTO> getCustomerByMail(@RequestParam String mail) {
        return ResponseEntity.ok(customerService.getCustomerByMail(mail));
    }
}
