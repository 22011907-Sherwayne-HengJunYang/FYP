package FYP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public Page<Coupon> findPublicCoupons(Pageable pageable) {
        return couponRepository.findByPublicCoupon(true, pageable);
    }
    
    public Page<Coupon> findCoupons(Pageable pageable) {
        return couponRepository.findAll(pageable);
    }
}
