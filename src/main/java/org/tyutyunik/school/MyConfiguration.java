package org.tyutyunik.school;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

        // Customize the factory as needed (e.g., port, context path)
        return factory;
    }

    @Bean
    public GroupedOpenApi group() {
        return GroupedOpenApi.builder()
                .group("All")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public GroupedOpenApi infoGroup() {
        return GroupedOpenApi.builder()
                .group("Info")
                .pathsToMatch("/info/**")
                .build();
    }

    @Bean
    public GroupedOpenApi facultyGroup() {
        return GroupedOpenApi.builder()
                .group("Faculty")
                .pathsToMatch("/faculty/**")
                .build();
    }

    @Bean
    public GroupedOpenApi studentGroup() {
        return GroupedOpenApi.builder()
                .group("Student")
                .pathsToMatch("/student/**")
                .build();
    }

    @Bean
    public GroupedOpenApi avatarGroup() {
        return GroupedOpenApi.builder()
                .group("Avatar")
                .pathsToMatch("/avatar/**")
                .build();
    }
}