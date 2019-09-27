package hr.ja.security.web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class MyRestController {

	@RequestMapping("/")
	public String home() {
		return "Home";
	}
}
