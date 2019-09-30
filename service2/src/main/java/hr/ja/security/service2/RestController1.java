package hr.ja.security.service2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestController1 {

	@RequestMapping("/data1")
	public Data1 data1() {
		Data1 model1 = new Data1();
		model1.setData1("data1");
		return model1;
	}
	
	
	@RequestMapping("/")
	public Data1 home() {
		Data1 model1 = new Data1();
		model1.setData1("home");
		return model1;
	}
}