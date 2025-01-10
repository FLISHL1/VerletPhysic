package ru.flish1.listeners;

import ru.flish1.figure.Circle;
import ru.flish1.figure.CircleFactory;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MouseAdapterNewCircle extends MouseAdapter {
    private final List<Circle> circleList;

    public MouseAdapterNewCircle(List<Circle> circleList) {
        this.circleList = circleList;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        circleList.add(CircleFactory.createCircleWithRandomColorAndDefaultRadius(e.getX(), e.getY()));
    }
}
