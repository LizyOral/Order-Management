package com.example.orderservice.service;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.model.OrderRecord;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Transactional
    public OrderRecord createOrder(OrderRecord request) {
        OrderEntity entity = new OrderEntity(
            request.productName(),
            request.quantity(),
            request.price()
        );
        OrderEntity saved = orderRepository.save(entity);
        return new OrderRecord(saved.getId(), saved.getProductName(), saved.getQuantity(), saved.getPrice());
    }
    @Transactional(readOnly = true)
    public OrderRecord getOrderById(Long id) {
        OrderEntity entity = orderRepository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(id));
        return new OrderRecord(entity.getId(), entity.getProductName(), entity.getQuantity(), entity.getPrice());
    }
}
