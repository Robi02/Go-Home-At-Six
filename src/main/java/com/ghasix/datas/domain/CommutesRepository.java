package com.ghasix.datas.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommutesRepository extends JpaRepository<Commutes, Long> {
    
    public List<Commutes> findByOwnUserIdOrderByIdDesc(Users ownUser);
    public List<Commutes> findByOwnUserIdAndCheckInTimeBetweenOrderByCheckInTimeDesc(Users ownUser, Long beginTime, Long endTime);
    public List<Commutes> findByOwnUserIdAndCheckOutTime(Users ownUser, Long checkOutTime);
    public List<Commutes> findTop1ByOwnUserIdOrderByCheckInTimeDesc(Users ownUser);
    public Optional<Commutes> findByIdAndOwnUserId(Long id, Users ownUser);
    public Integer deleteByIdAndOwnUserId(Long id, Users ownUser);
}