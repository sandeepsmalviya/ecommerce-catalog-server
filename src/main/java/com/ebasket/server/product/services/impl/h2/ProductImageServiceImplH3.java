package com.ebasket.server.product.services.impl.h2;

import java.util.List;
import java.util.NoSuchElementException;

//import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ebasket.server.product.entity.ProductImage;
import com.ebasket.server.product.exception.ProductImageNotFoundException;
import com.ebasket.server.product.exception.ServiceException;
import com.ebasket.server.product.repository.ProductImageRepository;
import com.ebasket.server.product.services.ProductImageService;

@Service
@Transactional(rollbackFor = ProductImageNotFoundException.class)
public class ProductImageServiceImplH3 implements ProductImageService {

	private static final Logger logger = LoggerFactory.getLogger(ProductImageServiceImplH3.class);

	@Autowired
	ProductImageRepository productImageRepository;

	@Override
	public ProductImage findById(int imageId) throws ProductImageNotFoundException, ServiceException {

		try {
			return productImageRepository.findById(imageId).get();
		} catch (NoSuchElementException exception) {
			throw new ProductImageNotFoundException("ProductImage with id = " + imageId + " not found", exception);
		} catch (Exception exception) {
			throw new ServiceException(exception);
		}
	}

	@Override
	public List<ProductImage> findAll() throws ServiceException {

		try {
			return productImageRepository.findAll();
		} catch (Exception exception) {
			throw new ServiceException(exception);
		}
	}

	@Override
	public ProductImage create(ProductImage productImage) throws ServiceException {

		try {
			return productImageRepository.save(productImage);
		} catch (Exception exception) {
			throw new ServiceException(exception);
		}
	}

	@Override
	public void delete(int imageId) throws ProductImageNotFoundException, ServiceException {

		try {
			productImageRepository.deleteById(imageId);
		} catch (NoSuchElementException elementException) {
			throw new ProductImageNotFoundException("ProductImage with id = " + imageId + " not found, cant' delete",
					elementException);
		} catch (EmptyResultDataAccessException elementException) {
			throw new ProductImageNotFoundException(
					"ProductImage with id = " + imageId + " not found, and its empty, cant' delete", elementException);
		} catch (Exception exception) {
			logger.error("My Error = " + exception.toString());
			throw new ServiceException(exception);
		}
	}

	@Override
	public ProductImage update(ProductImage productImage) throws ProductImageNotFoundException, ServiceException {

		try {
			return productImageRepository.saveAndFlush(productImage);
		} catch (NoSuchElementException elementException) {
			throw new ProductImageNotFoundException(
					"ProductImage with id = " + productImage + " not found, cant' update", elementException);
		} catch (Exception exception) {
			throw new ServiceException(exception);
		}
	}

	@Override
	public long count() throws ServiceException {

		try {
			return productImageRepository.count();
		} catch (Exception exception) {
			throw new ServiceException(exception);
		}
	}
}
