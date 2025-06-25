package com.onlineshop.springboot.service;

import com.onlineshop.springboot.entity.OrderItem;
import com.onlineshop.springboot.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final ProductService productService;

    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order item not found with id: " + id));
    }

    public List<OrderItem> findByOrderId(Long orderId) {
        orderService.findById(orderId); // Проверяем существование заказа
        return orderItemRepository.findByOrderId(orderId);
    }

    public List<OrderItem> findByProductId(Long productId) {
        productService.findById(productId); // Проверяем существование товара
        return orderItemRepository.findByProductId(productId);
    }

    public OrderItem save(OrderItem orderItem) {
        // Проверяем существование заказа и товара
        orderService.findById(orderItem.getOrder().getId());
        productService.findById(orderItem.getProduct().getId());

        return orderItemRepository.save(orderItem);
    }

    public void delete(Long id) {
        orderItemRepository.deleteById(id);
    }

    public OrderItem update(Long id, OrderItem orderItemDetails) {
        OrderItem orderItem = findById(id);
        orderItem.setQuantity(orderItemDetails.getQuantity());
        orderItem.setPrice(orderItemDetails.getPrice());
        orderItem.setSize(orderItemDetails.getSize());
        orderItem.setColor(orderItemDetails.getColor());
        return orderItemRepository.save(orderItem);
    }
}