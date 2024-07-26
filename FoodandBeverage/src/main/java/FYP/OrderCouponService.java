package FYP;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OrderCouponService {

    @Autowired
    private OrderCouponRepository orderCouponRepository;

 

    public Page<OrderCoupon> findPaginatedByClaimant_Id(int claimantId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderCouponRepository.findByClaimantId(claimantId, pageable);
    }
    
    public Page<OrderCoupon> findPaginatedByVendorName(int claimantId, String vendorName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderCouponRepository.findByCouponVendorNameContainingIgnoreCaseAndClaimantId(vendorName, claimantId, pageable);
    }
}
