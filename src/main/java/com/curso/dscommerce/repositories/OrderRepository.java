package com.curso.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.dscommerce.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
	


}
