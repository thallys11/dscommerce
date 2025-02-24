package com.curso.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.dscommerce.entities.OrderItem;
import com.curso.dscommerce.entities.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK>{
	


}
