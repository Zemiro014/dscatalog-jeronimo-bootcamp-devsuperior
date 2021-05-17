package com.devsuperioi.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperioi.dscatalog.entities.Category;
import com.devsuperioi.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Transactional(readOnly = true)
	public List<Category> getAllCategory(){
		return categoryRepo.findAll();
	}
}
