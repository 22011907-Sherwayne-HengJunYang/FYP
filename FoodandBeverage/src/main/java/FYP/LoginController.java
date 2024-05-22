/**
 * 
 * I declare that this code was written by me, 22011907. 
 * I will not copy or allow others to copy my code. 
 * I understand that copying code is considered as plagiarism.
 * 
 * Student Name: Sherwayne Heng Jun Yang
 * Student ID: 22011907
 * Class: E6C
 * Date created: 2024-Jan-09 2:56:40 pm 
 * 
 */

package FYP;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 
 */
@Controller
public class LoginController {
	@GetMapping("/login")
	public String login() {
		return"login";
	}

}
