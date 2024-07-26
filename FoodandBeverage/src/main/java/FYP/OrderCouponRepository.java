package FYP;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCouponRepository extends JpaRepository<OrderCoupon, Integer> {
	List<OrderCoupon> findAll();
	
	 Page<OrderCoupon> findByClaimantId(int claimantId, Pageable pageable);
	
	boolean existsByClaimant_IdAndCoupon_Id(int claimantId, int couponId);

	OrderCoupon findByRedeemCode(String redeemCode);
}

