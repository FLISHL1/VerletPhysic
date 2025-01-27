package ru.flish1.circleLogic;

import ru.flish1.constraints.Constraint;
import ru.flish1.figure.Circle;
import ru.flish1.model.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CircleUpdater implements Runnable{
    public final List<Circle> circles;
    public final double timeForRepaint;
    public final Vector gravity = new Vector(0, 1);
    public double subSteps = 16;
    public Constraint constraint;

    public CircleUpdater(Constraint constraint, List<Circle> circles, double timeForRepaint) {
        this.circles = circles;
        this.timeForRepaint = timeForRepaint;
        this.constraint = constraint;
    }

    public void start() {
        for (int i = 0; i < subSteps; i++) {
            applyAccelerate();
            updatePosition();
            solveCollision();
            applyConstraints();
        }
    }

    /*private void solveCollision() {
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
    }*/
    private static final int CELL_SIZE = 100; // Размер ячейки сетки

    private void solveCollision() {
        // Создание сетки
        Map<Point, List<Circle>> grid = new HashMap<>();

        // Заполнение сетки кругами
        for(Circle circle: circles) {
            Point gridPosition = getGridPosition(circle.getPosition());
            grid.computeIfAbsent(gridPosition, k -> new ArrayList<>()).add(circle);
        }

        // Проверка коллизий
        grid.entrySet().parallelStream().forEach( entry -> {
            List<Circle> cellCircles = entry.getValue();
            Point gridPosition = entry.getKey();

            // Проверяем круги внутри текущей ячейки
            checkCollisions(cellCircles);

            // Проверяем круги в соседних ячейках
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) continue;

                    Point neighborPosition = new Point(gridPosition.x + dx, gridPosition.y + dy);
                    List<Circle> neighborCircles = grid.get(neighborPosition);
                    if (neighborCircles != null) {
                        checkCollisions(cellCircles, neighborCircles);
                    }
                }
            }
        });
    }

    private Point getGridPosition(Vector position) {
        int x = (int) Math.floor(position.getX() / CELL_SIZE);
        int y = (int) Math.floor(position.getY() / CELL_SIZE);
        return new Point(x, y);
    }
    private void gravityPoint(){
        circles.parallelStream().forEach(circle -> {
        });
    }
    private void checkCollisions(List<Circle> cellCircles) {
        for (int i = 0; i < cellCircles.size(); i++) {
            Circle circle1 = cellCircles.get(i);
            for (int j = i + 1; j < cellCircles.size(); j++) {
                Circle circle2 = cellCircles.get(j);
                resolveCollision(circle1, circle2);
            }
        }
    }

    private void checkCollisions(List<Circle> cellCircles, List<Circle> neighborCircles) {
        for (Circle circle1 : cellCircles) {
            for (Circle circle2 : neighborCircles) {
                resolveCollision(circle1, circle2);
            }
        }
    }

    private void resolveCollision(Circle circle1, Circle circle2) {
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


    private void updatePosition() {
        circles.parallelStream().forEach(circle -> circle.updatePosition(timeForRepaint / subSteps));
    }

    private void applyAccelerate() {
        circles.parallelStream().forEach(circle -> {
            circle.accelerate(gravity);
        });
    }

    private void applyConstraints() {
        circles.parallelStream().forEach(circle -> constraint.applyConstraint(circle));
    }

    @Override
    public void run() {
        start();
    }
}
