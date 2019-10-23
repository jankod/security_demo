package hr.ja.security.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Command1  implements CommandLineRunner  {

	@Override
	public void run(String... args) throws Exception {
			log.debug("command runn...");
	}

}
