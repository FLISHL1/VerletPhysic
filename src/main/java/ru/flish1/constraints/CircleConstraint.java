package ru.flish1.constraints;

import ru.flish1.figure.Circle;
import ru.flish1.model.Vector;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CircleConstraint extends Ellipse2D.Double implements Constraint {
    private double radius;
    private Color color;

    public CircleConstraint(double x, double y, double radius) {
        super(x, y, radius * 2, radius * 2);
        this.radius = radius;
        this.color = Color.WHITE;
    }

    public Vector getPosition() {
        return new Vector(x + radius, y + radius);
    }

    @Override
    public void applyConstraint(Circle circle) {
        Vector toObj = circle.getPosition().minus(getPosition());
        double dist = toObj.length();
        if (dist > radius - circle.getRadius()) {
            if (!circle.isConstraint) {
                circle.accelerate(circle.getVelocity().division(16 / 1000.0).multiply(-1).multiply(0.8));
                circle.isConstraint = true;
            }
            Vector n = toObj.division(dist);
            circle.setPosition(getPosition().append(n.multiply(radius - circle.getRadius())));
        } else {
            circle.isConstraint = false;
        }
    }

    @Override
    public void fill(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fill(this);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.draw(this);
    }
}
