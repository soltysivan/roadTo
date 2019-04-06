package org.euro.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@Profile("heroku")
@Configuration
public class JavaHerokuConfig {

    @PostConstruct
    public void test(){
        System.out.println("Hello from Heroku");
    }
}
