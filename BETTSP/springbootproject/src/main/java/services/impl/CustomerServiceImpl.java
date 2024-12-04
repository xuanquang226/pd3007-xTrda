package services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.CustomerDao;
import data.dto.CustomerDTO;
import services.CustomerService;
import utils.objects.AccountAuth;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private AccountAuth accountAuth;

    @Override
    public CustomerDTO getOneCustomer() {
        return customerDao.getOneCustomerById(accountAuth.getAccount().getIdCustomer());
    }

}
