package com.akshay.project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akshay.project.project.dto.OrderDTO;
import com.akshay.project.project.service.OrderServiceImpl;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderServiceImpl orderService;

	@PostMapping("/{userId}/{orderId}")
	public ResponseEntity<OrderDTO> addProductToOrder(@PathVariable("userId") Long userId,
			@PathVariable("orderId") Long orderId, @RequestParam("productId") Long productId,
			@RequestParam("quantity") int quantity) {
		try {
			return new ResponseEntity<>(orderService.addProductToOrder(userId, orderId, productId, quantity),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
