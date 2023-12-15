package com.vision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vision.entity.RequestPinLogs;

@Repository
public interface RequestPinRepo extends JpaRepository<RequestPinLogs, Integer>{

}
