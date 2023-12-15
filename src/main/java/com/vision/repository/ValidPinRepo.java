package com.vision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vision.entity.ValidationPinLogs;

@Repository
public interface ValidPinRepo extends JpaRepository<ValidationPinLogs, Integer>{

}
