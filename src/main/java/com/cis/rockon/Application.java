package com.cis.rockon;

import com.cis.rockon.model.User;
import com.cis.rockon.repository.UserRepository;
import com.cis.rockon.util.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;


@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories
public class Application {

    @Autowired
    UserRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "RockOn Core",
                "REST API for RockOn",
                "1.0.0",
                null,
                new Contact("Yaki Lebovits","https://www.cis.temple.edu","yakir@temple.edu"),
                "GPL 2",
                "https://www.gnu.org/licenses/old-licenses/gpl-2.0.html",
                Collections.emptyList()
        );
    }

    @Bean
    public CommandLineRunner CommandLineRunnerBean() {
        return (args) -> {

            User[] mockUsers = {
                    new User()
                            .setId(1L).setFirstName("Hooter T.").setLastName("Owl").setPhoneNumber("215 420 6969")
                            .setEmail("cis1@temple.edu").setBirthday(new Date(0)).setLastSeenLocation(new Location(0, 0))
                            .setBiography("I'm just here to test!").setYearsOfExperience(69).setTypeSportClimbing(true)
                            .setTypeFreeSolo(true).setTypeTopRope(false).setTypeFreeSolo(false).setTypeBouldering(null)
                            .setSearchRadius(1),
                    new User()
                            .setId(2L).setFirstName("Dick").setLastName("Englert").setPhoneNumber("215 420 6969")
                            .setEmail("cis2@temple.edu").setBirthday(new Date(0)).setLastSeenLocation(new Location(0, 0))
                            .setBiography("$$$$$$$$").setYearsOfExperience(69).setTypeSportClimbing(true)
                            .setTypeFreeSolo(true).setTypeTopRope(false).setTypeFreeSolo(false).setTypeBouldering(null)
                            .setSearchRadius(1),
                    new User()
                            .setId(3L).setFirstName("Gritty").setLastName(" ").setPhoneNumber("911")
                            .setEmail("cis3@temple.edu").setBirthday(new Date(0)).setLastSeenLocation(new Location(0, 0))
                            .setBiography("gritty").setYearsOfExperience(420).setTypeSportClimbing(true)
                            .setTypeFreeSolo(true).setTypeTopRope(false).setTypeFreeSolo(false).setTypeBouldering(null)
                            .setSearchRadius(1)
            };

            repository.saveAll(Arrays.asList(mockUsers));
        };
    }
}
