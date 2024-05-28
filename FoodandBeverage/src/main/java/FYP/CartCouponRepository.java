package FYP;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartCouponRepository extends JpaRepository<CartCoupon, Integer>{
	
	public List <CartCoupon> findByClaimant_Id(int id);
	
	public CartCoupon findByClaimant_IdAndCoupon_Id(int id, int couponId);
}
