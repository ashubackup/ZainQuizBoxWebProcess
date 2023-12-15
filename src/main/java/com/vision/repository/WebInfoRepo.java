package com.vision.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vision.entity.WebInfo;

@Repository
public interface WebInfoRepo extends JpaRepository<WebInfo, Integer>{

}
