package com.wbs.wbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wbs.wbs.model.ServiceCategory;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {
}
