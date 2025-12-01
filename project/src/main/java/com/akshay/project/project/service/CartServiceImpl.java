package com.akshay.project.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.akshay.project.project.Repository.CartItemRepository;
import com.akshay.project.project.Repository.CartRepository;
import com.akshay.project.project.Repository.ProductRepository;
import com.akshay.project.project.Repository.UserRepository;
import com.akshay.project.project.dto.AddToCartRequest;
import com.akshay.project.project.dto.CartDTO;
import com.akshay.project.project.model.Cart;
import com.akshay.project.project.model.CartItem;
import com.akshay.project.project.model.CartItemId;
import com.akshay.project.project.model.Products;
import com.akshay.project.project.model.User;
import com.akshay.project.project.exception.RecordNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;

	private UserRepository userRepository;

	private ProductRepository productRepository;

	private CartRepository cartRepository;

	private CartItemRepository cartItemRepository;

	public CartServiceImpl(UserRepository userRepository, ProductRepository productRepository,
			CartRepository cartRepository, CartItemRepository cartItemRepository) {
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
	}

	@Transactional
	@Override
	public CartDTO addProductToCart(Long userId, AddToCartRequest request) {

		log.info("Request received to add the product to the cart");

		log.info("validate the userId :{}", userId);
		User user = userRepository.findById(userId).orElseThrow(() -> {
			log.warn("user not valid");
			return new RecordNotFoundException("user not found");
		});

		Cart cart = cartRepository.findByUserUserId(userId).orElseGet(() -> {
			log.info("cart is not found against the user creat new cart to user");
			Cart newCart = new Cart(user);

			Cart savedCart = cartRepository.save(newCart);
			log.info("new cart save successfully in db");
			return savedCart;
		});
		log.warn("the new cart :{}", cart.toString());

		log.info("validated the product :{}", request.getProductId());
		Products product = productRepository.findById(request.getProductId()).orElseThrow(() -> {
			log.warn("Product not found");
			return new RecordNotFoundException("Product not found");
		});

		CartItemId cartItemId = new CartItemId(cart.getCartId(), product.getProductId());
		log.info("the complosite key :{}", cart.getCartId(), product.getProductId());

		log.info("existance of composite key :{}", cartItemId.toString());

		CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);
		log.info("CartItem are :{}", cartItem);
		if (cartItem != null) {
			log.info("update the quantity of product");
			cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
			cartItemRepository.save(cartItem);
			log.info("cartItem saved successfully");
		} else {
			log.info("saved the product inside the CartItem");
			CartItem newCartItem = new CartItem(cart, product, request.getQuantity());

			cartItemRepository.save(newCartItem);
			log.info("new CartItem saved successfully");
		}

		Cart updatedCart = cartRepository.findById(cart.getCartId()).get();
		return cartMapper.toDTO(updatedCart);
	}

	@Override
	public CartDTO getProductOfCart(Long cartId) {

		log.info("validate the cartId :{}", cartId);
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> {
			log.warn("cart is not found of cartId :{}", cartId);
			return new RecordNotFoundException("cart not found");
		});
		return cartMapper.toDTO(cart);
	}

}
