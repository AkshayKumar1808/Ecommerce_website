package com.akshay.project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akshay.project.project.dto.AddToCartRequest;
import com.akshay.project.project.dto.CartDTO;
import com.akshay.project.project.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/{userId}")
	public ResponseEntity<CartDTO> addProductToCart(@PathVariable("userId") Long userId,

			@RequestBody AddToCartRequest request) {
		try {
			return new ResponseEntity<>(cartService.addProductToCart(userId, request), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/{cartId}")
	public ResponseEntity<CartDTO> getProductOfCart(@PathVariable("cartId") Long cartId) {
		try {
			return new ResponseEntity<>(cartService.getProductOfCart(cartId), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
