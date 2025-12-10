package com.teatown.software.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class DemoApplication {

	static void main(String[] args) {

        // Note: You can explicitly specify a logging provider (i.e., an SLF4J implementation like Logback or Log4J)
        //       via the system property "slf4j.provider"
        //       see org.slf4j.LoggerFactory.loadExplicitlySpecified
        //System.setProperty("slf4j.provider", "ch.qos.logback.classic.spi.LogbackServiceProvider");

		SpringApplication.run(DemoApplication.class, args);
	}

}
