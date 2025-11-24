package com.akshay.project.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akshay.project.project.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
