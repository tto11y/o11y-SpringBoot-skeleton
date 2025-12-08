package com.teatown.software.springboot.demo.application.opentelemetry;

import ch.qos.logback.classic.Level;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * <p>{@link LoggerConfig} contains the basics about SLF4J, Logback, and how they interact.</p>
 * <ul>
 *     <li>SLF4J - Simple Logging Facade for Java
 *     <ul>
 *         <li>SLF4J is the de-facto standard logging API in the Java ecosystem</li>
 *         <li>It offers a unified facade where several logging implementations (e.g., Logback, Log4j, etc.) can be plugged into</li>
 *     </ul></li>
 *     <li>Logback
 *     <ul>
 *         <li>A popular, widely-used logging implementation of the SLF4J API</li>
 *         <li>It's architecture comprises three classes: {@link ch.qos.logback.classic.Logger}, {@link ch.qos.logback.core.Appender}, and {@link ch.qos.logback.core.Layout}</li>
 *         <li>A Logger is a context for log messages. This is the class that applications interact with to create log messages.</li>
 *         <li>Appenders place log messages in their final destinations. A Logger can have more than one Appender (e.g., {@link ch.qos.logback.core.ConsoleAppender}, {@link ch.qos.logback.core.rolling.RollingFileAppender}.</li>
 *         <li>Layout prepares messages for output. Logback supports the creation of custom classes for formatting messages, as well as robust configuration options for the existing ones.</li>
 *     </ul></li>
 *     <li>Setup<ul>
 *         <li>As Spring Boot ships with all necessary dependencies to leverage logging,
 *         the only thing you need to make sure is that your application has access to <a href="https://docs.spring.io/spring-boot/how-to/logging.html">spring-boot-starter-logging</a>.
 *         You can either add it explicitly to your pom.xml file, or if you already added <a href="https://docs.spring.io/spring-boot/how-to/logging.html">spring-boot-starter-web</a> to your pom.xml the logging dependencies are added automatically (transitively).</li>
 *         <li>There is no need to add Logback or Log4J manually to the pom.xml.</li>
 *     </ul></li>
 *     <li>Usage<ul>
 *         <li>To instantiate a {@link Logger}, you can use the SLF4J {@link LoggerFactory} to create an object of type {@link Logger}.</li>
 *         <li>If you want to have a less verbose approach, you can also use Project Lombok and its annotation <a href="https://projectlombok.org/api/lombok/extern/slf4j/Slf4j">@Slf4j</a> (which does basically the same as the approach above). E.g. see {@link com.teatown.software.springboot.demo.helloworld.web.TraceIdFilter}</li>
 *     </ul></li>
 * </ul>
 * <p></p>
 * */
@Configuration
public class LoggerConfig {

    // Logging contexts exist in a hierarchy that closely resembles the Java object hierarchy:
    //  1 A logger is an ancestor when its name, followed by a dot, prefixes a descendant logger‘s name
    //  2 A logger is a parent when there are no ancestors between it and a child
    private static final Logger PARENT_LOGGER = LoggerFactory.getLogger("com.teatown.software.springboot.demo.application.opentelemetry");
    private static final Logger CHILD_LOGGER = LoggerFactory.getLogger("com.teatown.software.springboot.demo.application.opentelemetry.tests");

   private BatchLogRecordProcessor batchLogRecordProcessor;

   @Autowired
   public LoggerConfig(BatchLogRecordProcessor batchLogRecordProcessor) {
       this.batchLogRecordProcessor = batchLogRecordProcessor;

       ((ch.qos.logback.classic.Logger) PARENT_LOGGER).setLevel(Level.INFO);

       PARENT_LOGGER.warn("This message is logged because WARN > INFO.");
       PARENT_LOGGER.debug("This message is not logged because DEBUG < INFO.");

       // If a Logger isn’t explicitly assigned a level, it inherits the level of its closest ancestor.
       // That's why debug logs of CHILD_LOGGER don't show up in the console logs
       CHILD_LOGGER.info("INFO == INFO");
       CHILD_LOGGER.debug("DEBUG < INFO");

       CHILD_LOGGER.debug("we never see this message");
       // A Logger has a Level, which can be set either via configuration or with Logger.setLevel().
       // Setting the level in code overrides configuration files
       // Note: this works because we know the underlying logger implementation is Logback
       ((ch.qos.logback.classic.Logger)CHILD_LOGGER).setLevel(Level.DEBUG);
       CHILD_LOGGER.debug("but we always see this message");
   }

}
