package hr.ja.security.service1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MyController {

	@Autowired
	RemoteService2 service2;
	
	//@PreAuthorize("hasAnyAuthority('READ_SERVICE2_DATA1')")
	  @Secured("READ_SERVICE2_DATA1")
	@GetMapping("/service2")
	public String callService2() {
		String data = service2.getData();
		log.info("call service 2, result: "+ data);
		return "OK";
	}

	
	@GetMapping("/")
	public String home() {
		log.info("home");
		return "index";
	}
}
