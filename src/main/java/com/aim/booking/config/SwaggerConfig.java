package com.aim.booking.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Value("${springdoc.swagger-ui.server.url}")
  private String serverUrl;

  private String buildId = "1.0";

  @Bean
  public OpenAPI customOpenAPI() {
    Components components = new Components()
        .addSecuritySchemes("Authorization header", authComponent());

    return new OpenAPI()
        .components(components)
        .info(apiInfo())
        .servers(getApiServers())
        .addSecurityItem(
            new SecurityRequirement().addList("Authorization header", Collections.emptyList()));
  }

  private Info apiInfo() {
    String desc = "Booking REST API documentation";
    if (StringUtils.isNotBlank(buildId)) {
      desc += String.format("  (Build: %s)", buildId);
    }
    return new Info()
        .title("Booking REST API")
        .description(desc)
        .version(buildId);
  }

  private List<Server> getApiServers() {
    return Collections.singletonList(
        new Server().description("development Url").url(serverUrl)
    );
  }

  private SecurityScheme authComponent() {
    return new SecurityScheme()
        .type(Type.HTTP)
        .scheme("bearer")
        .in(SecurityScheme.In.HEADER)
        .name("Authorization")
        .bearerFormat("JWT")
        .description("Authorization header for requests.");
  }

  @Bean
  public OpenApiCustomiser requiredFixOpenApiCustomizer() {

    return openApi -> openApi.getPaths()
        .forEach((s, pathItem) -> pathItem.readOperationsMap()
            .forEach((method, operation) -> {
                  if (operation.getParameters() == null) {
                    return;
                  }
                  operation.getParameters().forEach(parameter -> {
                    if (parameter.getAllowEmptyValue() != null && parameter.getAllowEmptyValue()) {
                      parameter.setRequired(false);
                    }
                  });
                }
            ));
  }

}
