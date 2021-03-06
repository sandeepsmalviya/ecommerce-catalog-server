package com.ecommerce.catalog.server.exception.handler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ecommerce.catalog.server.exception.ProductCategoryNotFoundException;
import com.ecommerce.catalog.server.exception.ProductNotFoundException;
import com.ecommerce.catalog.server.exception.ServiceException;
import com.ecommerce.catalog.server.exception.error.DemoErrorMessage;
import com.ecommerce.catalog.server.exception.error.ErrorMessage;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

	private void printStackTrace(Exception ex, String errorMessageDescription) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);

		String errorLogForException = errorMessageDescription + "\n" + sw.toString();
		logger.error(errorLogForException);

	}
	@ExceptionHandler(value = UnexpectedRollbackException.class)
	public ResponseEntity<ErrorMessage> handleUnexpectedRollbackException(UnexpectedRollbackException ex, WebRequest webRequest) {

		String errorMessageDescription = ex.getLocalizedMessage();
		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}

		errorMessageDescription = errorMessageDescription + "\t" + " UnexpectedRollbackException Exception Handler is called";
		printStackTrace(ex, errorMessageDescription);

		//DemoErrorMessage errorMessage = new DemoErrorMessage(errorMessageDescription, new Date());
		
		ErrorMessage errorMessage2 = new ErrorMessage(HttpStatus.NOT_FOUND, errorMessageDescription, ex);
		return new ResponseEntity<ErrorMessage>(errorMessage2, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(value = ProductNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleProductNotFoundException(ProductNotFoundException ex, WebRequest webRequest) {

		String errorMessageDescription = ex.getLocalizedMessage();
		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}

		//errorMessageDescription = errorMessageDescription + "\t" + " ProductNotFoundException Exception Handler is called";
		printStackTrace(ex, errorMessageDescription);

		//DemoErrorMessage errorMessage = new DemoErrorMessage(errorMessageDescription, new Date());
		
		ErrorMessage errorMessage2 = new ErrorMessage(HttpStatus.NOT_FOUND, errorMessageDescription, ex);
		return new ResponseEntity<ErrorMessage>(errorMessage2, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = ProductCategoryNotFoundException.class)
	public ResponseEntity<Object> handleProductCategoryNotFoundException(ProductCategoryNotFoundException ex,
			WebRequest webRequest) {

		String errorMessageDescription = ex.getLocalizedMessage();
		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}

		//errorMessageDescription = errorMessageDescription + "\t" + " ProductCategoryNotFoundException Exception Handler is called";
		printStackTrace(ex, errorMessageDescription);

		DemoErrorMessage errorMessage = new DemoErrorMessage(errorMessageDescription, new Date());
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = NullPointerException.class)
	public ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest webRequest) {

		String errorMessageDescription = ex.getLocalizedMessage();
		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}

	//	errorMessageDescription = errorMessageDescription + "\t" + " NullPointerException Exception Handler is called";
		printStackTrace(ex, errorMessageDescription);

		DemoErrorMessage errorMessage = new DemoErrorMessage(errorMessageDescription, new Date());
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = EntityNotFoundException.class)
	public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest webRequest) {

		String errorMessageDescription = ex.getLocalizedMessage();
		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}

		errorMessageDescription = errorMessageDescription + "\t"
				+ " EntityNotFoundException Exception Handler is called";
		printStackTrace(ex, errorMessageDescription);

		DemoErrorMessage errorMessage = new DemoErrorMessage(errorMessageDescription, new Date());
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = IllegalArgumentException.class)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest webRequest) {

		String errorMessageDescription = ex.getLocalizedMessage();
		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}

		errorMessageDescription = errorMessageDescription + "\t"
				+ " IllegalArgumentException Exception Handler is called";
		printStackTrace(ex, errorMessageDescription);

		DemoErrorMessage errorMessage = new DemoErrorMessage(errorMessageDescription, new Date());
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = ServiceException.class)
	public ResponseEntity<Object> handleServiceException(ServiceException ex, WebRequest webRequest) {

		String errorMessageDescription = ex.getLocalizedMessage();
		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}

		errorMessageDescription = errorMessageDescription + "\t" + " ServiceException Handler is called";

		printStackTrace(ex, errorMessageDescription);

		DemoErrorMessage errorMessage = new DemoErrorMessage(errorMessageDescription, new Date());
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest webRequest) {

		String errorMessageDescription = ex.getLocalizedMessage();
		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}

		errorMessageDescription = errorMessageDescription + "\t" + " Default Exception Handler is called";

		printStackTrace(ex, errorMessageDescription);

		DemoErrorMessage errorMessage = new DemoErrorMessage(errorMessageDescription, new Date());
		return new ResponseEntity<Object>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/*
	 * @ExceptionHandler(value = ValidationConstr.class) public
	 * ResponseEntity<Object>
	 * handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
	 * WebRequest webRequest) {
	 * 
	 * String errorMessageDescription = ex.getLocalizedMessage(); if
	 * (errorMessageDescription == null) { errorMessageDescription = ex.toString();
	 * }
	 * 
	 * errorMessageDescription = errorMessageDescription + "\n" +
	 * " MethodArgumentNotValidException Exception Handler is called";
	 * System.out.println(errorMessageDescription);
	 * 
	 * ErrorMessage errorMessage = new ErrorMessage(errorMessageDescription, new
	 * Date()); return new ResponseEntity<Object>(errorMessage, new HttpHeaders(),
	 * HttpStatus.BAD_REQUEST); }
	 */}
