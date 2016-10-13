package hello.config;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
@Configuration
@EnableSwagger
@EnableAutoConfiguration
public class SwaggerConfig {
 
    private SpringSwaggerConfig springSwaggerConfig;
 
    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }
 
    // You can try out the swagger page at http://localhost:8080/swagger/index.html
    // NO MORE POSTMAN!
    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(new ApiInfo(
                        "My DEMO WITH SPRING BOOT REST API",
                        "This API is for demo only.",
                        null,
                        null,
                        null,
                        null
                ))
                        //Here we disable auto generating of responses for REST-endpoints
                .useDefaultResponseMessages(false)
                        //Here we specify URI patterns which will be included in Swagger docs. Use regex for this purpose.
                .includePatterns("/.*");
    }
 
}