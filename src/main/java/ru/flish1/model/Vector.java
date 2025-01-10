package ru.flish1.model;

public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double[] getVector() {
        return new double[]{x, y};
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void clear(){
        x = 0;
        y = 0;
    }

    public Vector append(Vector vector){
        return new Vector(x+vector.getVector()[0], y + vector.getVector()[1]);
    }
    public Vector minus(Vector vector){
        return new Vector(x - vector.x, y - vector.y);
    }

    public double length(){
        return Math.sqrt(x*x + y*y);
    }
    public Vector division(double num){
        return new Vector(x/num, y/num);
    }

    public Vector multiply(double num){
        return new Vector(x*num, y*num);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
