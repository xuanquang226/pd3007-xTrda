package data.dao;

import data.dto.CustomerDTO;

public interface CustomerDao {
    CustomerDTO getOneCustomerByIdAccount(Long idAccount);

    void createOneCustomer(CustomerDTO customerDTO);

    void updateCustomer(CustomerDTO customerDTO);

    void deleteCustomerByIdAccount(Long idAccount);
}
