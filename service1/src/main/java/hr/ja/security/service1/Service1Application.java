package hr.ja.security.service1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
		SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class })
public class Service1Application {

	public static void main(String[] args) {
		SpringApplication.run(Service1Application.class, args);
		log.debug("SERVICE 1");
		// CALL_SERVICE2
	}

}
