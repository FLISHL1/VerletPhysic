package ru.flish1.figure;

import java.awt.*;
import java.util.Random;

public class CircleFactory {
    private static final Random random = new Random();
    private static final double radiusDefault = 6;

    public static Circle createCircleWithRandomColorAndDefaultRadius(double x, double y) {
        return new Circle(x - radiusDefault, y - radiusDefault, new Color(1, random.nextFloat(), random.nextFloat()), radiusDefault);
    }
}
