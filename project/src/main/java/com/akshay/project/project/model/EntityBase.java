package com.akshay.project.project.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@MappedSuperclass
public class EntityBase {

	@Column(updatable = false)
	private LocalDate createdAt;

	@Column(insertable = false)
	private LocalDate updateAt;

	@PrePersist
	public void onCreate() {
		this.createdAt = LocalDate.now();
	}

	@PreUpdate
	public void onUpdate() {
		this.updateAt = LocalDate.now();
	}
}
