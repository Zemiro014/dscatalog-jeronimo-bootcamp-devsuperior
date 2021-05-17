package com.devsuperioi.dscatalog.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperioi.dscatalog.dto.CategoryDTO;
import com.devsuperioi.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> getAllCategory(){
		return categoryRepo.findAll().stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}
}
