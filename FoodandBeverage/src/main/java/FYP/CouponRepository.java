/**
 * 
 * I declare that this code was written by me, 22011907. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Sherwayne Heng Jun Yang
 * Student ID: 22011907
 * Class: E6C
 * Date created: 2024-Apr-24 2:18:48 pm 
 * 
 */

package FYP;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CouponRepository extends JpaRepository<Coupon, Integer> {
	 List<Coupon> findByPublicCoupon(boolean publicCoupon);
}
//
