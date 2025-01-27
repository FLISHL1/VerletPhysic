package ru.flish1.constraints;

import ru.flish1.figure.Circle;
import ru.flish1.model.Vector;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RectangleConstraint extends Rectangle2D.Double implements Constraint {
    private Color color;

    public RectangleConstraint(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.color = Color.WHITE;
    }

    public Vector getPosition() {
        return new Vector(x + width/2, y + height/2);
    }

    @Override
    public void applyConstraint(Circle circle) {
        Vector rectPos = getPosition();
        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;

        Vector circlePos = circle.getPosition();
        double circleRadius = circle.getRadius();

        if (circlePos.getX() > width*2 || circlePos.getY() >width*2){
            System.out.println(circlePos);
        }
        // Вычисление ограниченных координат
        double clampedX = clamp(circlePos.getX(), rectPos.getX() - halfWidth + circleRadius, rectPos.getX() + halfWidth - circleRadius);
        double clampedY = clamp(circlePos.getY(), rectPos.getY() - halfHeight + circleRadius, rectPos.getY() + halfHeight - circleRadius);
        if (clampedX != circlePos.getX() || clampedY != circlePos.getY()) {
            // Устанавливаем новое положение круга
            if (!circle.isConstraint) {
                circle.isConstraint = true;
                double yAcc = circle.getVelocity().division(16 / 1000.0).multiply(-1).multiply(0.94).getY();
                double xAcc = 0;

                circle.accelerate(new Vector(xAcc, yAcc));
                circle.setPosition(new Vector(clampedX + circle.getVelocity().multiply(0.06).getX(), clampedY));

            } else {
                circle.setPosition(new Vector(clampedX, clampedY));
            }
        } else {
            circle.isConstraint = false;
        }
    }
    // Метод для ограничения значения в диапазоне [min, max]
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
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
