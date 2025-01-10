package ru.flish1.figure;

import java.awt.*;
import java.util.Random;

public class CircleFactory {
    private static final Random random = new Random();
    private static final double radiusDefault = 5;

    public static Circle createCircleWithRandomColorAndDefaultRadius(double x, double y) {
        return new Circle(x - radiusDefault, y - radiusDefault, new Color(random.nextFloat(), random.nextFloat(), random.nextFloat()), radiusDefault);
    }
}
