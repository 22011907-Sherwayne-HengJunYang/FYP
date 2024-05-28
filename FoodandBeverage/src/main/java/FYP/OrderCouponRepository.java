package FYP;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCouponRepository extends JpaRepository<OrderCoupon, Integer> {
	
	public List<OrderCoupon>findByClaimant_Id(int claimantId);
}

