package data.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import data.dao.OrderLineDao;
import data.dto.OrderLineDTO;
import data.mapper.OrderLineMapper;
import data.repositories.OrderLineRepository;

@Service
public class OrderLineDaoImpl implements OrderLineDao {

    @Autowired
    private OrderLineMapper mapper;

    @Autowired
    private OrderLineRepository repository;

    @Override
    public void createManyOrderLine(List<OrderLineDTO> orderLine) {
        repository.saveAll(mapper.toEntity(orderLine));
    }

    @Override
    public List<OrderLineDTO> getManyOrderLine(Long idOrder) {
        return mapper.toDto(repository.findManyOrderLineByIdOrder(idOrder));
    }
}
