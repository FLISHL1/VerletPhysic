package ru.flish1.constraints;

import ru.flish1.figure.Circle;
import ru.flish1.model.Vector;

import java.awt.*;

public interface Constraint {
    void fill(Graphics2D g2d);
    void draw(Graphics2D g2d);
    void applyConstraint(Circle circle);
}
