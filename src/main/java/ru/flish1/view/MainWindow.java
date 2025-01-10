package ru.flish1.view;

import ru.flish1.figure.Circle;
import ru.flish1.listeners.MouseAdapterNewCircle;
import ru.flish1.listeners.MouseMotionAdapterNewCircle;
import ru.flish1.model.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainWindow extends JFrame {
    private double RADIUS_CONSTRAINS = 356;
    private final int HEIGHT = 800;
    private final int WIDTH = 800;
    private final String title = "VerletPhysic";
    private final Vector gravity = new Vector(0, 1);
    private final List<Circle> circles;
    private int timeForRepaint = 16;

    public MainWindow() {
        super("VerletPhysic");
        circles = new ArrayList<>();
        applySettings();

//        addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                if (e.getKeyChar() == 'w')
//                    RADIUS++;
//                else{
//                    RADIUS--;
//                }
//                System.out.println(RADIUS);
//            }
//
//        });
    }

    private void applySettings() {
        setSize(new Dimension(WIDTH, HEIGHT));
        addMouseMotionListener(new MouseMotionAdapterNewCircle(circles));
        addMouseListener(new MouseAdapterNewCircle(circles));
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bf = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bf.createGraphics();
        applySettingsG2D(g2d);
        super.paint(g2d);

        drawBackGround(g2d);

        startMagic(g2d);

        g2d.dispose();
        g.drawImage(bf, 0, 0, null);
        repaint(timeForRepaint);
    }

    private void drawBackGround(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private void applySettingsG2D(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(Map.of(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY, RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE));
    }

    private void applyConstraints(Graphics2D g2d) {
        Vector position = new Vector(RADIUS_CONSTRAINS +50, RADIUS_CONSTRAINS +50);
        g2d.setColor(Color.WHITE);
        g2d.draw(new Circle(position.getVector()[0] - RADIUS_CONSTRAINS, position.getVector()[1] - RADIUS_CONSTRAINS, Color.black, RADIUS_CONSTRAINS));
        for (Circle circle : circles) {
            Vector toObj = circle.getPosition().minus(position);
            double dist = toObj.length();
            if (dist > RADIUS_CONSTRAINS - circle.getRadius()) {
                Vector n = toObj.division(dist);
                circle.setPosition(position.append(n.multiply(RADIUS_CONSTRAINS - circle.getRadius())));
            }
        }
    }

    private void startMagic(Graphics2D g2d) {
        applyAccelerate();
        updatePosition();
        applyConstraints(g2d);
        paintObj(g2d);
    }

    private void paintObj(Graphics2D g2d) {
        for (Circle circle : circles) {
            circle.paintCircle(g2d);
        }
    }

    private void updatePosition() {
        for (Circle circle : circles) {
            circle.updatePosition(timeForRepaint);
        }
    }

    private void applyAccelerate() {
        for (Circle circle : circles) {
            circle.accelerate(gravity);
        }
    }


}
