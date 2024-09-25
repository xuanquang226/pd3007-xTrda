package data.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.MailDao;
import data.dto.MailDTO;
import data.mapper.MailMapper;
import data.repositories.MailRepository;

@Service
public class MailDaoImpl implements MailDao {

    @Autowired
    private MailRepository repository;

    @Autowired
    private MailMapper mapper;

    @Override
    public void saveMail(MailDTO mailDTO) {
        repository.save(mapper.toEntity(mailDTO));
    }

}
