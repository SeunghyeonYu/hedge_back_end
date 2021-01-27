package com.koscom.project.hedge.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.koscom.project.hedge.domain.Hedge;

public interface HedgeRepository extends JpaRepository<Hedge, Long> {

}