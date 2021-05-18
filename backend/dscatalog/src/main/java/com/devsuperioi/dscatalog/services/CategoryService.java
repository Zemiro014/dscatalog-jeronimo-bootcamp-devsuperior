package com.devsuperioi.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperioi.dscatalog.dto.CategoryDTO;
import com.devsuperioi.dscatalog.entities.Category;
import com.devsuperioi.dscatalog.repositories.CategoryRepository;
import com.devsuperioi.dscatalog.services.exceptions.EntityNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> getAllCategory(){
		return categoryRepo.findAll().stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO getById(Long id) {
		Optional<Category> obj = categoryRepo.findById(id);
		Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
		return new CategoryDTO(entity);
	}

	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		
		entity = categoryRepo.save(entity);
		
		return new CategoryDTO(entity);
	}
}
