package com.curso.dscommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.curso.dscommerce.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	

}
