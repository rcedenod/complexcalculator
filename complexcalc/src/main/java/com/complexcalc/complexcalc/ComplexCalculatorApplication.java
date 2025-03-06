package com.complexcalc.complexcalc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.complexcalc.complexcalc.web")
public class ComplexCalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComplexCalculatorApplication.class, args);
    }
}
