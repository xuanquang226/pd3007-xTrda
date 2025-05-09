package services;

import data.dto.CustomerDTO;

public interface CustomerService {
    CustomerDTO getOneCustomer();

    CustomerDTO getCustomerByMail(String mail);
}
