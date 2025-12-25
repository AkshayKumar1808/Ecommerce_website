package com.akshay.project.project.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.akshay.project.project.Repository.OrderItemRepository;
import com.akshay.project.project.Repository.OrderRepository;
import com.akshay.project.project.Repository.ProductRepository;
import com.akshay.project.project.Repository.UserRepository;
import com.akshay.project.project.dto.OrderDTO;
import com.akshay.project.project.exception.RecordNotFoundException;
import com.akshay.project.project.model.Order;
import com.akshay.project.project.model.OrderItem;
import com.akshay.project.project.model.OrderItemId;
import com.akshay.project.project.model.Products;
import com.akshay.project.project.model.User;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	private UserRepository userRepository;
	private ProductRepository productRepository;
	private OrderRepository orderRepository;
	private OrderItemRepository orderItemRepository;
	private OrderMapper orderMapper;

	public OrderServiceImpl(UserRepository userRepository, ProductRepository productRepository,
			OrderRepository orderRepository, OrderItemRepository orderItemRepository, OrderMapper orderMapper) {
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.orderMapper = orderMapper;
	}

	@Override
	@Transactional
	public OrderDTO addProductToOrder(Long userId, Long orderId, Long productId, int quantity) {

		log.info("validating the user :{}", userId);
		User user = userRepository.findById(userId).orElseThrow(() -> {
			log.warn("user is not found");
			return new RecordNotFoundException("user not found");
		});

		log.info("validating product :{}", productId);
		Products product = productRepository.findById(productId).orElseThrow(() -> {
			log.warn("product is not found");
			return new RecordNotFoundException("product Not Found.");
		});

		log.info("checking the order is present");
		Order order = orderRepository.findById(orderId).orElseGet(() -> {
			Order newOrder = new Order();
			newOrder.setUser(user);
			newOrder.setTotalAmount(0.0);
			return orderRepository.save(newOrder);
		});

		OrderItemId orderItemId = new OrderItemId(order.getOrderId(), product.getProductId());

		log.info("check the composite Key {}/{}", order.getOrderId(), product.getProductId());

		OrderItem orderItem = orderItemRepository.findById(orderItemId).orElse(null);

		double itemTotal = product.getPrice() * quantity;
		if (orderItem == null) {
			log.info("creating the OrderItem of composite key :{}", orderItemId);

			OrderItem newOrderItem = new OrderItem();
			newOrderItem.setId(orderItemId);
			newOrderItem.setQuantity(quantity);
			newOrderItem.setOrder(order);
			newOrderItem.setProduct(product);
			newOrderItem.setUnitPrice(product.getPrice());
			orderItemRepository.save(newOrderItem);
			log.info("OrderItem are saved successfully");

			// Add to total only for new items
			order.setTotalAmount(Optional.ofNullable(order.getTotalAmount()).orElse(0.0) + itemTotal);

		} else {
			log.info("OrderItem is exist to set the amount");

			// Update existing item quantity
			int oldQuantity = orderItem.getQuantity();
			int newQuantity = oldQuantity + quantity;
			orderItem.setQuantity(newQuantity);

			// Update total: remove old item total and add new item total

			double newItemTotal = product.getPrice() * newQuantity;

			order.setTotalAmount(order.getTotalAmount() + newItemTotal);

			orderItemRepository.save(orderItem);
		}

		return orderMapper.getOrderDetail(order);
	}

	@Override
	public OrderDTO getOrderItemByOrderId(Long orderId) {
		log.info("validating the orderId :{}", orderId);
		Order order = orderRepository.findById(orderId).orElseThrow(() -> {
			log.warn("OrderId Not Found");
			return new RecordNotFoundException("Order Not found against the orderId");
		});
		return orderMapper.getOrderDetail(order);
	}

}
