package kr.or.bit3004.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Configuration;

/*
DB 사용을 위한 설정 파일로 application.properties에 선언된 spring.datasource.* 값들로 DataSource로 빌드하기 위해 사용합니다.
DB 연동은 다른 방법으로도 가능하지만 보다 편리한 유지보수를 위해 클래스 파일로 분리하는 작업
 */

@Configuration
public class DataSourceConfig {
	
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}
	
}
