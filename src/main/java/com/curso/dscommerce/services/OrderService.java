package com.curso.dscommerce.services;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.curso.dscommerce.dto.OrderDTO;
import com.curso.dscommerce.dto.OrderItemDTO;
import com.curso.dscommerce.entities.Order;
import com.curso.dscommerce.entities.OrderItem;
import com.curso.dscommerce.entities.OrderStatus;
import com.curso.dscommerce.entities.Product;
import com.curso.dscommerce.entities.User;
import com.curso.dscommerce.repositories.OrderItemRepository;
import com.curso.dscommerce.repositories.OrderRepository;
import com.curso.dscommerce.repositories.ProductRepository;
import com.curso.dscommerce.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;
	
	@Transactional(readOnly = true)
	public OrderDTO findById(Long id) {
		Order order = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Recurso não encontrado"));
		authService.validateSelfOrAdmin(order.getClient().getId());
		return new OrderDTO(order);
	}

	@Transactional
	public OrderDTO insert(OrderDTO dto) {
		
		Order order = new Order();
		
		order.setMoment(Instant.now());
		order.setStatus(OrderStatus.WAITING_PAYMENT);
		
		User user = userService.authenticated();
		order.setClient(user);
		
		for (OrderItemDTO itemDto : dto.getItems()) {
			Product product = productRepository.getReferenceById(itemDto.getProductId());
			OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());
			order.getItems().add(item);
		}
		
		repository.save(order);
		orderItemRepository.saveAll(order.getItems());
		
		
		return new OrderDTO(order);
	}

}
