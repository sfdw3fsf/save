//package vn.vnpt.database;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//
//import vn.vnpt.Utils.BSSCrypto;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataSourceConfig {
//
//	@Bean
//	@ConfigurationProperties("spring.datasource.hikari")
//	public HikariConfig hikariConfig() {
//		return new HikariConfig();
//	}
//
//	@Bean
//	public DataSource dataSource(HikariConfig hikariConfig, @Value("${spring.datasource.url}") String url,
//			@Value("${spring.datasource.username}") String username,
//			@Value("${spring.datasource.password}") String password,
//			@Value("${spring.datasource.crypto_key}") String key) {
//		hikariConfig.setJdbcUrl(url);
//		hikariConfig.setUsername(BSSCrypto.decrypt(username, key));
//		hikariConfig.setPassword(BSSCrypto.decrypt(password, key));
//		return new HikariDataSource(hikariConfig);
//	}
//}