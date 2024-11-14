package com.example.backend.order.repository;

import java.util.List;

import com.example.backend.order.entity.ParentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<ParentOrder, Integer>, OrderRepositoryCustom {
	List<ParentOrder> findByTableId(String tableId);
}
