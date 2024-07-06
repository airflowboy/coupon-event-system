package com.airflowboy.couponcore.repository.mysql;

import com.airflowboy.couponcore.model.CouponIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CouponIssueJpaRepository extends JpaRepository<CouponIssue, Long> {
}
