package com.complexcalc.complexcalc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ComplexcalcApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComplexcalcApplication.class, args);
    }

    @RestController
    @RequestMapping("/api/complex")
    class ComplexController {

        // Suma de dos números complejos en formato rectangular
        @GetMapping("/suma-rect")
        public RectComplex suma(@RequestParam double real1, @RequestParam double imag1,
                                  @RequestParam double real2, @RequestParam double imag2) {
            double real = real1 + real2;
            double imag = imag1 + imag2;
            return new RectComplex(real, imag);
        }

        // Resta de dos números complejos en formato rectangular
        @GetMapping("/resta-rect")
        public RectComplex resta(@RequestParam double real1, @RequestParam double imag1,
                                   @RequestParam double real2, @RequestParam double imag2) {
            double real = real1 - real2;
            double imag = imag1 - imag2;
            return new RectComplex(real, imag);
        }

        // Multiplicación de dos números complejos en formato rectangular
        @GetMapping("/multiplicacion-rect")
        public RectComplex multiplicacion(@RequestParam double real1, @RequestParam double imag1,
                                            @RequestParam double real2, @RequestParam double imag2) {
            double real = real1 * real2 - imag1 * imag2;
            double imag = real1 * imag2 + imag1 * real2;
            return new RectComplex(real, imag);
        }

        // División de dos números complejos en formato rectangular
        @GetMapping("/division-rect")
        public RectComplex division(@RequestParam double real1, @RequestParam double imag1,
                                      @RequestParam double real2, @RequestParam double imag2) {
            double denominator = real2 * real2 + imag2 * imag2;
            double real = (real1 * real2 + imag1 * imag2) / denominator;
            double imag = (imag1 * real2 - real1 * imag2) / denominator;
            return new RectComplex(real, imag);
        }

        // Convierte un número complejo rectangular a polar
        @GetMapping("/a-polares")
        public PolarComplex aPolares(@RequestParam double real, @RequestParam double imag) {
            double r = Math.sqrt(real * real + imag * imag);
            double theta = Math.atan2(imag, real);
            return new PolarComplex(r, theta);
        }

        // Convierte un número polar a rectangular
        @GetMapping("/a-rectangulares")
        public RectComplex aRectangulares(@RequestParam double r, @RequestParam double theta) {
            double real = r * Math.cos(theta);
            double imag = r * Math.sin(theta);
            return new RectComplex(real, imag);
        }

        // Suma de dos números complejos en formato polar
        @GetMapping("/suma-polar")
        public PolarComplex sumaPolar(@RequestParam double r1, @RequestParam double theta1,
                                       @RequestParam double r2, @RequestParam double theta2) {
            // Calcular r y theta para la suma en coordenadas polares
            double r = Math.sqrt(r1 * r1 + r2 * r2 + 2 * r1 * r2 * Math.cos(theta1 - theta2));
            double theta = Math.atan2(r1 * Math.sin(theta1) + r2 * Math.sin(theta2),
                                      r1 * Math.cos(theta1) + r2 * Math.cos(theta2));
            return new PolarComplex(r, theta);
        }

        // Resta de dos números complejos en formato polar
        @GetMapping("/resta-polar")
        public PolarComplex restaPolar(@RequestParam double r1, @RequestParam double theta1,
                                       @RequestParam double r2, @RequestParam double theta2) {
            // Similar a la suma pero con diferencia en el cálculo
            double r = Math.sqrt(r1 * r1 + r2 * r2 - 2 * r1 * r2 * Math.cos(theta1 - theta2));
            double theta = Math.atan2(r1 * Math.sin(theta1) - r2 * Math.sin(theta2),
                                      r1 * Math.cos(theta1) - r2 * Math.cos(theta2));
            return new PolarComplex(r, theta);
        }

        // Multiplicación de dos números complejos en formato polar
        @GetMapping("/multiplicacion-polar")
        public PolarComplex multiplicacionPolar(@RequestParam double r1, @RequestParam double theta1,
                                                 @RequestParam double r2, @RequestParam double theta2) {
            // La multiplicación en polar es simple: multiplicamos los radios y sumamos los ángulos
            double r = r1 * r2;
            double theta = theta1 + theta2;
            return new PolarComplex(r, theta);
        }

        // División de dos números complejos en formato polar
        @GetMapping("/division-polar")
        public PolarComplex divisionPolar(@RequestParam double r1, @RequestParam double theta1,
                                          @RequestParam double r2, @RequestParam double theta2) {
            // La división en polar se hace dividiendo los radios y restando los ángulos
            double r = r1 / r2;
            double theta = theta1 - theta2;
            return new PolarComplex(r, theta);
        }
    }

    // Clase para representar un número complejo en formato rectangular
    class RectComplex {
        private double real;
        private double imaginary;

        public RectComplex(double real, double imaginary) {
            this.real = real;
            this.imaginary = imaginary;
        }

        public double getReal() {
            return real;
        }

        public double getImaginary() {
            return imaginary;
        }
    }

    // Clase para representar un número complejo en formato polar
    class PolarComplex {
        private double r;
        private double theta;

        public PolarComplex(double r, double theta) {
            this.r = r;
            this.theta = theta;
        }

        public double getR() {
            return r;
        }

        public double getTheta() {
            return theta;
        }
    }
}
