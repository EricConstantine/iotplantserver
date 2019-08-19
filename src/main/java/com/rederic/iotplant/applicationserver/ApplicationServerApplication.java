package com.rederic.iotplant.applicationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author redeirc
 */
@SpringBootApplication
@EnableSwagger2
@EnableTransactionManagement
@EnableCaching
public class ApplicationServerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationServerApplication.class, args);
	}

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.rederic.iotplant.applicationserver.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		Contact contact = new Contact("teejo", "https://www.rederic.com.cn", "2505068703@qq.com");
		ApiInfo apiInfo = new ApiInfoBuilder()
				.title("物联网平台")
				.description("物联网IOT-后台管理系统-服务端")
				.version("1.0.0")
				.termsOfServiceUrl("https://www.rederic.com.cn")
				.contact(contact)
				.license("REDERIC ALL RIGHT RESERVED")
				.licenseUrl("https://www.rederic.com.cn")
				.build();
		return apiInfo;
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ApplicationServerApplication.class);
	}
	@RequestMapping("/")
	public String index() {
		return "hello spring boot!!!";
	}


}
