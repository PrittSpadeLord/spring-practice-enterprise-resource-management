package io.github.prittspadelord.erm;

import io.github.prittspadelord.erm.config.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringPracticeEnterpriseResourceManagementApplication {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringApplicationConfiguration.class);

        System.out.println(context);
    }
}