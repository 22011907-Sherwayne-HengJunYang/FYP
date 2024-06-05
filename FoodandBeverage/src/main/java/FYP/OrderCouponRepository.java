package FYP;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCouponRepository extends JpaRepository<OrderCoupon, Integer> {
	List<OrderCoupon> findAll();
	
	public List<OrderCoupon>findByClaimant_Id(int claimantId);
	
	boolean existsByClaimant_IdAndCoupon_Id(int claimantId, int couponId);
}

