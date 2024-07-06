package com.airflowboy.couponcore.repository.mysql;

import com.airflowboy.couponcore.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.cdi.JpaRepositoryExtension;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {
}
