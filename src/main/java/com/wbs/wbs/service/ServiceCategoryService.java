package com.wbs.wbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wbs.wbs.model.ServiceCategory;
import com.wbs.wbs.repository.ServiceCategoryRepository;

@Service
public class ServiceCategoryService {
    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    public List<ServiceCategory> getAllServices() {
        return serviceCategoryRepository.findAll();
    }

    public ServiceCategory saveService(ServiceCategory serviceCategory) {
        return serviceCategoryRepository.save(serviceCategory);
    }

    public void deleteService(Long id) {
        serviceCategoryRepository.deleteById(id);
    }

    public ServiceCategory getServiceById(Long id) {
        return serviceCategoryRepository.findById(id).orElse(null);
    }
}
