package org.euro.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Value("${spring.mail.host}")
    public String host;

    @Value("${spring.mail.username}")
    public String username;

    @Value("${spring.mail.password}")
    public String password;

    @Value("${spring.mail.port}")
    public int port;

    @Value("${spring.mail.protocol}")
    public String protocol;

    @Value("${mail.debug}")
    public String debug;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    public String auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    public String enable;

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);


        Properties properties = javaMailSender.getJavaMailProperties();
        properties.setProperty("mail.smtp.auth", auth);
        properties.setProperty("mail.smtp.starttls.enable", enable);
        properties.setProperty("mail.transport.protocol",protocol);
        properties.setProperty("mail.debug", debug);
        return javaMailSender;
    }
}
