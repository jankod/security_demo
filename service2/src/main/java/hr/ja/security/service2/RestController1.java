package hr.ja.security.service2;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestController1 {

	@RequestMapping("/data1")
//    @PreAuthorize("hasAnyAuthority('READ_SERVICE2_DATA1')")
	public Data1 data1() {
		// cient need role: READ_SERVICE2_DATA1
		Data1 model1 = new Data1();
		model1.setData1("data1");
		log.debug("call !!!!!!!!!!!");
		return model1;
	}
	
	
	@RequestMapping("/")
	public Data1 home() {
		Data1 model1 = new Data1();
		model1.setData1("home");
		return model1;
	}
}