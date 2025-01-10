package ru.flish1.circleLogic;

import ru.flish1.figure.Circle;
import ru.flish1.model.Vector;

import java.util.List;

public class CircleUpdater {
    public final Vector CENTER_CONSTRAINS;
    public final List<Circle> circles;
    public final double timeForRepaint;
    public final Vector gravity = new Vector(0, 1);
    public double RADIUS_CONSTRAINS;
    public double subSteps = 16;

    public CircleUpdater(Vector CENTER_CONSTRAINS, List<Circle> circles, double timeForRepaint, double RADIUS_CONSTRAINS) {
        this.CENTER_CONSTRAINS = CENTER_CONSTRAINS;
        this.circles = circles;
        this.timeForRepaint = timeForRepaint;
        this.RADIUS_CONSTRAINS = RADIUS_CONSTRAINS;
    }

    public void start() {
        for (int i = 0; i < subSteps; i++) {
            applyAccelerate();
            updatePosition();
            solveCollision();
            applyConstraints();
        }
    }

    private void solveCollision() {
        for (int i = 0; i < circles.size(); i++) {
            Circle circle1 = circles.get(i);
            for (int j = i + 1; j < circles.size(); j++) {
                Circle circle2 = circles.get(j);
                Vector collision_axis = circle1.getPosition().minus(circle2.getPosition());
                double dist = collision_axis.length();
                double twoRadius = circle1.getRadius() + circle2.getRadius();
                if (dist < twoRadius) {
                    Vector n = collision_axis.division(dist);
                    double delta = dist - twoRadius;
                    circle1.setPosition(circle1.getPosition().minus(n.multiply(delta * 0.5)));
                    circle2.setPosition(circle2.getPosition().append(n.multiply(delta * 0.5)));
                }
            }
        }
    }

    private void updatePosition() {
        for (Circle circle : circles) {
            circle.updatePosition(timeForRepaint / subSteps);
        }
    }

    private void applyAccelerate() {
        for (Circle circle : circles) {
            circle.accelerate(gravity);
        }
    }

    private void applyConstraints() {
        for (Circle circle : circles) {
            Vector toObj = circle.getPosition().minus(CENTER_CONSTRAINS);
            double dist = toObj.length();
            if (dist > RADIUS_CONSTRAINS - circle.getRadius()) {
                Vector n = toObj.division(dist);
                circle.setPosition(CENTER_CONSTRAINS.append(n.multiply(RADIUS_CONSTRAINS - circle.getRadius())));
            }
        }
    }
}
