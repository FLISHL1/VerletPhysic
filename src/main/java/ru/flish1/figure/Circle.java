package ru.flish1.figure;

import ru.flish1.model.Vector;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Circle extends Ellipse2D.Double {
    public boolean isConstraint = false;
    private double radius;
    private Vector acceleration = new Vector(0, 0);
    public Vector positionOld;
    private final double DIVISION_DT = 7;
    private Color color;

    public Circle(double x, double y, Color color, double radius) {
        super(x, y, radius * 2, radius * 2);
        this.color = color;
        this.radius = radius;
        this.positionOld = getPosition();
    }

    public double getRadius() {
        return radius;
    }

    public Vector getPosition() {
        return new Vector(x + radius, y + radius);
    }

    public void setPosition(Vector position) {
        x = position.getVector()[0] - radius;
        y = position.getVector()[1] - radius;
    }

    private void setPositionP(Vector position) {
        x = position.getVector()[0] - radius;
        y = position.getVector()[1] - radius;
    }


    public void updatePosition(double dt) {
        Vector velocity = getVelocity();
        positionOld = getPosition();
        dt /= DIVISION_DT;
        setPositionP(getPosition().append(velocity.append(acceleration.multiply(dt * dt))));
        acceleration.clear();
    }

    public Vector getVelocity() {
        return getPosition().minus(positionOld);
    }

    public void accelerate(Vector acc) {
        acceleration = acceleration.append(acc);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void paint(Graphics2D g2d) {
        g2d.setColor(getColor());
        g2d.fill(this);
/*        g2d.setColor(Color.WHITE);
        g2d.drawLine((int) getCenterX(), (int) getCenterY(), (int) (getVelocity().getX() + getCenterX()), (int) (getVelocity().getY() + getCenterY()));
        System.out.println(acceleration);*/
    }
}
