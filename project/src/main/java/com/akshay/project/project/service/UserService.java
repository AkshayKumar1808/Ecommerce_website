package com.akshay.project.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshay.project.project.Repository.AddressRepository;
import com.akshay.project.project.Repository.UserRepository;
import com.akshay.project.project.model.Address;
import com.akshay.project.project.model.User;
import com.akshay.project.project.exception.*;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements CRUDOperation<User> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Override
	public User addModel(User model) {
		try {
			log.info("model is :{}", model.toString());
			User user = this.mapToUser(model, null);
			User newUser = userRepository.save(user);
			log.info("new user saved successfully");
			log.info("address are {}," + model.getAddresses());
			newUser = this.addressMap(model.getAddresses(), newUser);
			return newUser;
		} catch (Exception ex) {
			log.error("error in saving the new user :{}", ex);
			throw ex;
		}

	}

	@Override
	public User updateModel(User model, Long ID) {
		try {
			log.info("validating user Id :{}", ID);
			User existingUser = userRepository.findById(ID).orElseThrow(() -> {
				log.warn("user not found Id :{}", ID);
				return new RecordNotFoundException("user not found by Id :" + ID);
			});
			// map the data to the existing user
			User updatedUser = mapToUser(model, existingUser);
			User user = userRepository.save(updatedUser);
			user = this.addressMap(model.getAddresses(), user);

			log.info("user updated successfully");
			return user;
		} catch (Exception ex) {
			throw ex;
		}
	}

	private User addressMap(List<Address> addresses, User user) {
		List<Address> listAddress = new ArrayList<>();
		for (Address address : addresses) {
			address.setUser(user);
			Address newAddress = addressRepository.save(address);
			log.info("address successfully saved");
			listAddress.add(newAddress);
		}
		user.setAddresses(listAddress);
		return user;
	}

	private User mapToUser(User model, User existingUser) {
		User user = (existingUser != null) ? existingUser : new User();
		user.setUserName(model.getUserName());
		user.setPasswordHash(model.getPasswordHash());
		user.setPhoneNumber(model.getPhoneNumber());
		user.setActive(model.isActive());
//		List<Address> listAddress = new ArrayList<>();
//		for (Address address : model.getAddresses()) {
//			listAddress.add(address);
//		}
//		user.setAddresses(listAddress);

		return user;
	}

	@Override
	public User getModel(Long Id) {
		try {
			log.info("validating the user Id :{}", Id);
			User user = userRepository.findById(Id).orElseThrow(() -> {
				log.warn("user not found by Id :{}", Id);
				return new RecordNotFoundException("user not found by Id :" + Id);
			});
			return user;
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public User deleteModel(Long Id) {
		log.info("validating the user Id :{}", Id);
		User user = userRepository.findById(Id).orElseThrow(() -> {
			log.warn("user not found by Id :{}", Id);
			return new RecordNotFoundException("user not found by Id :" + Id);
		});
		user.setActive(false);
		User deletdUser = userRepository.save(user);
		log.info("successfully deleted user by Id :{}", Id);
		return deletdUser;
	}

	public List<User> getAllActiveUser() {
		List<User> user = userRepository.findByActiveTrue();
		return user;
	}
}
