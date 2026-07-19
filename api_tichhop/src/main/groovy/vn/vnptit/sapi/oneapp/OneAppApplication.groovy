package vn.vnptit.sapi.oneapp

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.ComponentScan

import groovy.transform.CompileStatic
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableCaching
@EnableEurekaClient
@EnableDiscoveryClient
@CompileStatic
@EnableSwagger2
@ComponentScan(basePackages = ["vn.vnptit.sapi.oneapp", "vn.vnptit.sapi.oneapp.resources.gnms","vn.vnptit.sapi.oneapp.models.gnms", "io.swagger.configuration", "io.swagger.api", "io.swagger.model"])
class OneAppApplication {

	static void main(String[] args) {
		SpringApplication.run(OneAppApplication, args)
	}
}
