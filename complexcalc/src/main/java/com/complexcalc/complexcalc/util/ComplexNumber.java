package com.complexcalc.complexcalc.util;

public class ComplexNumber {
    private double real;
    private double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexNumber sumar(ComplexNumber num) {
        return new ComplexNumber(this.real + num.real, this.imaginary + num.imaginary);
    }

    public ComplexNumber restar(ComplexNumber num) {
        return new ComplexNumber(this.real - num.real, this.imaginary - num.imaginary);
    }

    public ComplexNumber multiplicar(ComplexNumber num) {
        return new ComplexNumber(
            this.real * num.real - this.imaginary * num.imaginary,
            this.real * num.imaginary + this.imaginary * num.real
        );
    }

    public ComplexNumber dividir(ComplexNumber num) {
        double denominator = num.real * num.real + num.imaginary * num.imaginary;
        return new ComplexNumber(
            (this.real * num.real + this.imaginary * num.imaginary) / denominator,
            (this.imaginary * num.real - this.real * num.imaginary) / denominator
        );
    }

    public String toRectangular() {
        return String.format(" z = %.2f + %.2fi", real, imaginary);
    }

    public String toPolar() {
        double r = Math.sqrt(real * real + imaginary * imaginary);
        double theta = Math.atan2(imaginary, real);
        return String.format("z = %.2f(cos(%.2f) + i*sin(%.2f))", r, theta, theta);
    }
}
