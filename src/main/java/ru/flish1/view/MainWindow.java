package ru.flish1.view;

import ru.flish1.circleLogic.CircleUpdater;
import ru.flish1.constraints.Constraint;
import ru.flish1.constraints.RectangleConstraint;
import ru.flish1.figure.Circle;
import ru.flish1.listeners.key.WindowKeyListener;
import ru.flish1.listeners.mouse.MouseAdapterNewCircle;
import ru.flish1.listeners.mouse.MouseMotionAdapterNewCircle;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainWindow extends JFrame {
    private final int HEIGHT = 800;
    private final int WIDTH = 800;
    private final String title = "VerletPhysic";
    private final List<Circle> circles;
    private int timeForRepaint = 16;
    private final CircleUpdater circleUpdater;
    private Constraint constraint;

    public MainWindow() {
        super("VerletPhysic");
        circles = new ArrayList<>();
        applySettings();
//        constraint = new CircleConstraint(50, 50, 356);
        constraint = new RectangleConstraint(50, 50, 700, 700);
        circleUpdater = new CircleUpdater(constraint, circles, timeForRepaint);

    }

    public List<Circle> getCircles() {
        return circles;
    }

    public Constraint getConstraint() {
        return constraint;
    }

    public void setConstraint(Constraint constraint) {
        this.constraint = constraint;
        circleUpdater.constraint = constraint;
    }

    private void applySettings() {
        setSize(new Dimension(WIDTH, HEIGHT));
        addMouseMotionListener(new MouseMotionAdapterNewCircle(circles));
        addMouseListener(new MouseAdapterNewCircle(circles));
        addKeyListener(new WindowKeyListener(this));
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bf = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bf.createGraphics();
        applySettingsG2D(g2d);
        super.paint(g2d);
        drawBackGround(g2d);
        constraint.draw(g2d);
        startMagic(g2d);

        g2d.dispose();
        g.setColor(Color.WHITE);
        g.drawImage(bf, 0, 0, null);
        g.drawString(String.format("Circle count: %d; Sub-step: %(.2f", circles.size(), circleUpdater.subSteps), 10, 50);
        repaint(timeForRepaint);
    }

    private void startMagic(Graphics2D g2d) {
        circleUpdater.start();
        paintObj(g2d);
    }

    private void drawBackGround(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
    private void applySettingsG2D(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(Map.of(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY, RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE));
    }


    private void paintObj(Graphics2D g2d) {
        for (Circle circle : circles) {
            circle.paintCircle(g2d);
        }
    }




}
