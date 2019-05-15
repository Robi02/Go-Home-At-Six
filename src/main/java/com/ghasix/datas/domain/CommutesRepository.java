package com.ghasix.datas.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommutesRepository extends JpaRepository<Commutes, Long> {
    
    public List<Commutes> findByOwnUserIdOrderByIdDesc(Users ownUser);
    public List<Commutes> findByOwnUserIdAndCheckInTimeBetween(Users ownUser, Long beginTime, Long endTime);
    public Optional<Commutes> findByIdAndOwnUserId(Long id, Users ownUser);
    public void deleteByIdAndOwnUserId(Long id, Users ownUser);
}