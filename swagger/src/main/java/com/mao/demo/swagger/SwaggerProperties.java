package com.mao.demo.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("swagger")
public class SwaggerProperties {

	private String title = "Project API";
	private String version = "v1.0.0";
	private String basepackage;
	private String description = "This is the default project description";
	private String contractName = "default";
	private String contractUrl = "default.com";
	private String contractEmail = "default@gmail.com";
	private String license = "Apache License 2.0";
	private String licenseUrl = "https://www.apache.org/licenses/LICENSE-2.0";

}
