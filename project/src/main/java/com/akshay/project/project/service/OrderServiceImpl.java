package com.akshay.project.project.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.akshay.project.project.Repository.OrderItemRepository;
import com.akshay.project.project.Repository.OrderRepository;
import com.akshay.project.project.Repository.ProductRepository;
import com.akshay.project.project.Repository.UserRepository;
import com.akshay.project.project.dto.OrderDTO;
import com.akshay.project.project.exception.QuantityNotAvailableException;
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

		log.info("checking the stock quantity");

		int availableQuantity = product.getQuantity();
		if (availableQuantity <= 0) {
			throw new QuantityNotAvailableException(
					String.format("Product '%s' is out of stock", product.getProductName()));
		}
		if (availableQuantity < quantity) {
			throw new QuantityNotAvailableException(
					String.format("Insufficient stock for '%s'. Available: %d, Requested: %d", product.getProductName(),
							availableQuantity, quantity));
		}

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

			// Deduct product quantity
			product.setQuantity(availableQuantity - quantity);
			productRepository.save(product);
			log.info("Product quantity updated. Remaining: {}", product.getQuantity());
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

			// Deduct product quantity
			product.setQuantity(availableQuantity - quantity);
			productRepository.save(product);
			log.info("Product quantity updated. Remaining: {}", product.getQuantity());

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

	@Override
	public OrderDTO updateOrderQuantity(Long orderId, Long productId, int newQuantity) {

		if (newQuantity <= 0) {
			log.warn("new quantity is less than or equal to 0");
			throw new QuantityNotAvailableException("qunatity not valid, is greater than 0");
		}

		log.info("validating the order with id :{}", orderId);
		Order order = orderRepository.findById(orderId).orElseThrow(() -> {
			log.warn("Order Not Found");
			return new RecordNotFoundException("Order Not Found Against the OrderId " + orderId);
		});

		log.info("checking product is found against the order :{}/{}", orderId, productId);
		OrderItemId orderItemId = new OrderItemId(orderId, productId);

		log.info("checcking the composite key is present");
		OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(() -> {
			log.warn("the product is not found against the order");
			return new RecordNotFoundException("product is not found against the order");
		});

		Products product = orderItem.getProduct();
		int oldQuantity = orderItem.getQuantity();

		int quantityDiff = newQuantity - oldQuantity;
		if (quantityDiff > 0) {
			int availableQuantity = product.getQuantity();
			if (availableQuantity < quantityDiff) {
				log.error("product is not available");
				throw new QuantityNotAvailableException(
						String.format("Product '%s' is out of stock", product.getProductName()));
			}
			product.setQuantity(availableQuantity - quantityDiff);
		} else if (quantityDiff < 0) {
			product.setQuantity(product.getQuantity() + Math.abs(quantityDiff));
		}

		orderItem.setQuantity(newQuantity);
		orderItemRepository.save(orderItem);
		log.info("orderItem saved successfully with newQuantity");

		if (quantityDiff != 0) {
			productRepository.save(product);
			log.info("update the product quantity successfully");
		}

		// recalculate the total Amount
		double oldTotal = oldQuantity * product.getPrice();
		double newTotal = newQuantity * product.getPrice();
		double diffOfTotal = newTotal - oldTotal;

		order.setTotalAmount(diffOfTotal + order.getTotalAmount());
		orderRepository.save(order);
		log.info("update the Total amount successfully");
		return orderMapper.getOrderDetail(order);
	}

}
