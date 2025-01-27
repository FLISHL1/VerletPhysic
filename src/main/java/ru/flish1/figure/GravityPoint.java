package ru.flish1.figure;

import ru.flish1.model.Vector;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GravityPoint extends Ellipse2D.Double {
    private double radius;
    private final double DIVISION_DT = 7;
    private Color color;

    public GravityPoint(double x, double y, Color color, double radius) {
        super(x, y, radius * 2, radius * 2);
        this.color = color;
        this.radius = radius;
    }

    public Vector getPosition(){
        return new Vector(getCenterX(), getCenterY());
    }
    public void paint(Graphics2D g2d){
        
    }
}
