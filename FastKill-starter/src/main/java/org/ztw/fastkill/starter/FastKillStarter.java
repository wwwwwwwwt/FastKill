package org.ztw.fastkill.starter;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@Configurable
public class FastKillStarter {
    public static void main(String[] args) {
        SpringApplication.run(FastKillStarter.class, args);
    }
}
