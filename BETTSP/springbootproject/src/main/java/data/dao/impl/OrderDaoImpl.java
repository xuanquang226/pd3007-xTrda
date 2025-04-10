package data.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import data.dao.OrderDao;
import data.dto.OrderDTO;
import data.entities.OrderEntity;
import data.mapper.OrderMapper;
import data.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void createOrderByIdCustomer(OrderDTO dto) {
        orderRepository.save(orderMapper.toEntity(dto));
    }

    @Override
    public void deleteOrder(Long id) {
        Optional<OrderDTO> optional = Optional.ofNullable(getOneOrder(id));
        if (optional.isPresent()) {
            orderRepository.deleteById(id);
        }
    }

    @Override
    public List<OrderDTO> getManyOrder() {
        return orderMapper.toDto(orderRepository.findAll());
    }

    @Override
    public OrderDTO getOneOrder(Long id) {
        return orderMapper.toDto(
                orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Khong tim thay entity")));
    }

    @Override
    public OrderDTO getOneOrderByIdCustomer(Long idCustomer) {
        Pageable pageable = PageRequest.of(0, 1);
        return orderMapper.toDto(
                orderRepository.findFirstByIdCustomerOrderByIdDesc(idCustomer, pageable).getContent().get(0));

    }

    @Override
    public List<OrderDTO> getManyOrderByIdCustomer(Long idCustomer) {
        return orderMapper.toDto(orderRepository.findAllByIdCustomer(idCustomer));
    }

    @Override
    public Page<OrderDTO> getManyOrderByIdCustomer(Long idCustomer, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderEntity> pageEntity = orderRepository.findManyByIdCustomerOrderByIdDesc(idCustomer, pageable);
        Page<OrderDTO> pageDtoPage = pageEntity.map(orderMapper::toDto);
        return pageDtoPage;
    }
}
