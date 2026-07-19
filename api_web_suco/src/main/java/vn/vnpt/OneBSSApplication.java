package vn.vnpt;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan
//@EnableDiscoveryClient
@EnableEurekaClient
//@ComponentScan(basePackages = "vn.vnpt")
@ComponentScan(basePackages = {"vn.vnpt", "io.swagger.configuration", "io.swagger.model"})
public class OneBSSApplication {

	public static void main(String[] args) {
		//SpringApplication.run(OneBSSApplication.class, args);
		SpringApplication oneBSSApplication = new SpringApplication(OneBSSApplication.class);
		oneBSSApplication.addInitializers(new OneBSSContextInitializer());
		oneBSSApplication.run(args);
	}

	@PostConstruct
	public void init() {
		// Setting Spring Boot SetTime Zone
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}
