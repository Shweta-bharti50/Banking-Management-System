package com.Blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PersonalBlogRestApiSpringboot1Application {

	public static void main(String[] args) {
		SpringApplication.run(PersonalBlogRestApiSpringboot1Application.class, args);
	}

}
