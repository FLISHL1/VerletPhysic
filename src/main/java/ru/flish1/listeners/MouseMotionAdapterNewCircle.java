package ru.flish1.listeners;

import ru.flish1.figure.Circle;
import ru.flish1.figure.CircleFactory;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

public class MouseMotionAdapterNewCircle extends MouseMotionAdapter {
    private final List<Circle> circleList;

    public MouseMotionAdapterNewCircle(List<Circle> circleList) {
        this.circleList = circleList;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        circleList.add(CircleFactory.createCircleWithRandomColorAndDefaultRadius(e.getX(), e.getY()));
    }
}
