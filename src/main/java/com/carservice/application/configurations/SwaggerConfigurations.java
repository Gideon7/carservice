/**
 * 
 */
package com.carservice.application.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * @author Ojo Gideon .O 8th February, 2022
 *
 */
@Configuration
@OpenAPIDefinition(
		 info = @Info(
	               title = "Car Service ",
	               version = "v1",
		       description = "Car apis for fetching, sorting, filtering and adding of cars to list",
		       contact = @Contact(
					name = "Ojo Gideon .O",
					email = "ojoGideon5@gmail.com"
				)
			),
	     
	     servers = {
	           
	            @Server( 
	 	               url="http://localhost:8080/carservice",
	 	               description="local"
	 	            ),
	            @Server( 
			       url="https://cars-api-service.herokuapp.com/carservice",
			       description="live"
		 	    )
	     }
	)
public class SwaggerConfigurations {
	@Bean
	  public OpenAPI carServiceOpenAPI() {
	    return (new OpenAPI())
	      .components((new Components()).addSecuritySchemes("apiKeyScheme", (new SecurityScheme())
	          .type(SecurityScheme.Type.APIKEY)
	          .in(SecurityScheme.In.HEADER)
	          .name("Authorization")))
	      
	      .addSecurityItem((new SecurityRequirement()).addList("apiKeyScheme"));
	  }
}
