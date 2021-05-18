package com.devsuperioi.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperioi.dscatalog.dto.ProductDTO;
import com.devsuperioi.dscatalog.entities.Product;
import com.devsuperioi.dscatalog.repositories.ProductRepository;
import com.devsuperioi.dscatalog.services.exceptions.DataBaseException;
import com.devsuperioi.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;

	@Transactional(readOnly = true)
	public Page<ProductDTO> getAllProductPaged(PageRequest pageRequest) {
		Page<Product> list = productRepo.findAll(pageRequest);
		return list.map(x -> new ProductDTO(x, x.getCategories()));
	}

	@Transactional(readOnly = true)
	public ProductDTO getById(Long id) {
		Optional<Product> obj = productRepo.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		// entity.setName(dto.getName());

		entity = productRepo.save(entity);

		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			Product entity = productRepo.getOne(id);
			//entity.setName(dto.getName());
			entity = productRepo.save(entity);
			return new ProductDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	public void delete(Long id) {
		try {
			productRepo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
	}
}
