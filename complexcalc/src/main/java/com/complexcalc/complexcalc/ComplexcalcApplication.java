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

        @GetMapping("/operacion")
        public Object operacion(
                @RequestParam String tipoOperacion,
                @RequestParam String inputFormat,
                @RequestParam String outputFormat,
                @RequestParam double valor1,
                @RequestParam double valor2,
                @RequestParam double valor3,
                @RequestParam double valor4) {
            
            RectComplex rect1, rect2, resultadoRect;
            PolarComplex polar1, polar2, resultadoPolar;

            if (inputFormat.equals("rectangular")) {
                rect1 = new RectComplex(valor1, valor2);
                rect2 = new RectComplex(valor3, valor4);
            } else {
                rect1 = toRectangular(new PolarComplex(valor1, valor2));
                rect2 = toRectangular(new PolarComplex(valor3, valor4));
            }

            switch (tipoOperacion) {
                case "suma":
                    resultadoRect = suma(rect1, rect2);
                    break;
                case "resta":
                    resultadoRect = resta(rect1, rect2);
                    break;
                case "multiplicacion":
                    resultadoRect = multiplicacion(rect1, rect2);
                    break;
                case "division":
                    resultadoRect = division(rect1, rect2);
                    break;
                default:
                    throw new IllegalArgumentException("Operación no válida");
            }

            if (outputFormat.equals("polar")) {
                resultadoPolar = toPolar(resultadoRect);
                return resultadoPolar;
            } else {
                return resultadoRect;
            }
        }

        private RectComplex suma(RectComplex c1, RectComplex c2) {
            return new RectComplex(c1.real + c2.real, c1.imaginary + c2.imaginary);
        }

        private RectComplex resta(RectComplex c1, RectComplex c2) {
            return new RectComplex(c1.real - c2.real, c1.imaginary - c2.imaginary);
        }

        private RectComplex multiplicacion(RectComplex c1, RectComplex c2) {
            return new RectComplex(
                c1.real * c2.real - c1.imaginary * c2.imaginary,
                c1.real * c2.imaginary + c1.imaginary * c2.real
            );
        }

        private RectComplex division(RectComplex c1, RectComplex c2) {
            double denominator = c2.real * c2.real + c2.imaginary * c2.imaginary;
            return new RectComplex(
                (c1.real * c2.real + c1.imaginary * c2.imaginary) / denominator,
                (c1.imaginary * c2.real - c1.real * c2.imaginary) / denominator
            );
        }

        private RectComplex toRectangular(PolarComplex p) {
            return new RectComplex(p.r * Math.cos(p.theta), p.r * Math.sin(p.theta));
        }

        private PolarComplex toPolar(RectComplex r) {
            return new PolarComplex(
                Math.sqrt(r.real * r.real + r.imaginary * r.imaginary),
                Math.atan2(r.imaginary, r.real)
            );
        }
    }

    class RectComplex {
        double real;
        double imaginary;

        public RectComplex(double real, double imaginary) {
            this.real = real;
            this.imaginary = imaginary;
        }

        public double getReal() { return real; }
        public double getImaginary() { return imaginary; }
    }

    class PolarComplex {
        double r;
        double theta;

        public PolarComplex(double r, double theta) {
            this.r = r;
            this.theta = theta;
        }

        public double getR() { return r; }
        public double getTheta() { return theta; }
    }
}
