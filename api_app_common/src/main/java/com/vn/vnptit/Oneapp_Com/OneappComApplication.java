package com.vn.vnptit.Oneapp_Com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.vn.vnptit.Oneapp_Com.managed.HikariCPDataSource;
import com.vn.vnptit.Oneapp_Com.model.DataSource;
import com.vn.vnptit.Oneapp_Com.model.DataSourceProperties;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableScheduling
@EnableAsync
@EnableCaching
public class OneappComApplication {
	@Autowired
	DataSourceProperties dataSourceProperties;

	public static void main(String[] args) {
		innit();
		SpringApplication.run(OneappComApplication.class, args);
	}

	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("API Oneapp").description("Api OneApp phục vụ bán hàng").version("v0.0.1")
						.license(new License().name("Gp1-It3-VNPTIT").url("http://springdoc.org")))
				.externalDocs(new ExternalDocumentation().description("Spring Boot Reference Documentation")
						.url("https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/"));
	}

	@SuppressWarnings("static-access")
	private static void innit() {

		DataSourceProperties dataSourceProperties = new DataSourceProperties();
		DataSource dataSource = dataSourceProperties.getDataSource_oneApp_By_Id_Phanvung(1);
		if (dataSource.getUrl() != null) {
			try {
				HikariCPDataSource hikariCPDataSource = new HikariCPDataSource();
				hikariCPDataSource.getConnection(dataSource.getUrl(), dataSource.getUsername(),
						dataSource.getPassword(), 300, "oneapp", dataSource.getCryptoKey());
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}
}
