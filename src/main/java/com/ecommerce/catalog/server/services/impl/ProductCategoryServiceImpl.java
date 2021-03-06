package com.ecommerce.catalog.server.services.impl;

import java.util.List;
import java.util.NoSuchElementException;

//import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.catalog.server.entity.Product;
import com.ecommerce.catalog.server.entity.ProductCategory;
import com.ecommerce.catalog.server.exception.ProductCategoryNotFoundException;
import com.ecommerce.catalog.server.exception.ServiceException;
import com.ecommerce.catalog.server.repository.h2.ProductCategoryRepositoryH2;
import com.ecommerce.catalog.server.services.ProductCategoryService;

@Service
@Transactional(rollbackFor = ProductCategoryNotFoundException.class)
public class ProductCategoryServiceImpl implements ProductCategoryService {

	private static final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

	@Autowired
	ProductCategoryRepositoryH2 productCategoryRepository;

	@Override
	public ProductCategory findById(int productCategoryId) throws ProductCategoryNotFoundException, ServiceException {

		try {
			return productCategoryRepository.findById(productCategoryId).get();
		} catch (NoSuchElementException exception) {
			throw new ProductCategoryNotFoundException("ProductCategory with id = " + productCategoryId + " not found", exception);
		} catch (Exception exception) {
			throw new ServiceException(exception);
		}
	}

	@Override
	public List<ProductCategory> findAll() throws ServiceException {

		try {
			return productCategoryRepository.findAll();
		} catch (Exception exception) {
			throw new ServiceException(exception);
		}
	}

	@Override
	public ProductCategory create(ProductCategory productCategory) throws ServiceException {

		try {
			return productCategoryRepository.save(productCategory);
		} catch (Exception exception) {
			throw new ServiceException(exception);
		}
	}

	@Override
	public void delete(int productCategoryId) throws ProductCategoryNotFoundException, ServiceException {

		try {
			productCategoryRepository.deleteById(productCategoryId);
		} catch (NoSuchElementException elementException) {
			throw new ProductCategoryNotFoundException("ProductCategory with id = " + productCategoryId + " not found, cant' delete",
					elementException);
		} catch (EmptyResultDataAccessException elementException) {
			throw new ProductCategoryNotFoundException(
					"ProductCategory with id = " + productCategoryId + " not found, and its empty, cant' delete", elementException);
		} catch (Exception exception) {
			logger.error("My Error = " + exception.toString());
			throw new ServiceException(exception);
		}
	}

	@Override
	public ProductCategory update(ProductCategory productCategory) throws ProductCategoryNotFoundException, ServiceException {

		try {
			return productCategoryRepository.saveAndFlush(productCategory);
		} catch (NoSuchElementException elementException) {
			throw new ProductCategoryNotFoundException("ProductCategory with id = " + productCategory + " not found, cant' update",
					elementException);
		} catch (Exception exception) {
			throw new ServiceException(exception);
		}
	}

	@Override
	public long count() throws ServiceException {

		try {
			return productCategoryRepository.count();
		} catch (Exception exception) {
			throw new ServiceException(exception);
		}
	}
}
