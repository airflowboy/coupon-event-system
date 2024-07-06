package com.airflowboy.couponcore.service;

import com.airflowboy.couponcore.exception.CouponIssueException;
import com.airflowboy.couponcore.model.Coupon;
import com.airflowboy.couponcore.model.CouponIssue;
import com.airflowboy.couponcore.repository.mysql.CouponIssueJpaRepository;
import com.airflowboy.couponcore.repository.mysql.CouponIssueRepository;
import com.airflowboy.couponcore.repository.mysql.CouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.airflowboy.couponcore.exception.ErrorCode.COUPON_NOT_EXIST;
import static com.airflowboy.couponcore.exception.ErrorCode.DUPLICATE_COUPON_ISSUE;

@RequiredArgsConstructor
@Service
public class CouponIssueService {

    private final CouponJpaRepository couponJpaRepository;
    private final CouponIssueJpaRepository couponIssueJpaRepository;
    private final CouponIssueRepository couponIssueRepository;

    @Transactional
    public void issue(long couponId, long userId) {
        Coupon coupon = findCoupon(couponId);
        coupon.issue();
        saveCouponIssue(couponId, userId);
    }

    @Transactional(readOnly = true)
    public Coupon findCoupon(long couponId) {
        return couponJpaRepository.findById(couponId).orElseThrow(() -> {
            throw new CouponIssueException(COUPON_NOT_EXIST, "쿠폰 정책이 존재하지 않습니다. " +
                    "%s".formatted(couponId));
        });
    }

    @Transactional
    public CouponIssue saveCouponIssue(long couponId, long userId) {
        checkAlreadyIssued(couponId, userId);
        CouponIssue issue = CouponIssue.builder()
                .couponId(couponId)
                .userId(userId)
                .build();
        return couponIssueJpaRepository.save(issue);
    }

    private void checkAlreadyIssued(long couponId, long userId) {
        CouponIssue firstCouponIssue = couponIssueRepository.findFirstCouponIssue(couponId, userId);
        if(firstCouponIssue != null) {
            throw new CouponIssueException(DUPLICATE_COUPON_ISSUE, "이미 발급된 쿠폰입니다." +
                    "user_id : %s, coupon_id : %s".formatted(userId, couponId));
        }
    }

}
