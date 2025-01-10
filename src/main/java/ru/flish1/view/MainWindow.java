package ru.flish1.view;

import ru.flish1.circleLogic.CircleUpdater;
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
    private final Vector CENTER_CONSTRAINS = new Vector(RADIUS_CONSTRAINS + 50, RADIUS_CONSTRAINS + 50);
    private final int HEIGHT = 800;
    private final int WIDTH = 800;
    private final String title = "VerletPhysic";
    private final List<Circle> circles;
    private int timeForRepaint = 16;
    private final CircleUpdater circleUpdater;

    public MainWindow() {
        super("VerletPhysic");
        circles = new ArrayList<>();
        applySettings();
        circleUpdater = new CircleUpdater(CENTER_CONSTRAINS, circles, timeForRepaint, RADIUS_CONSTRAINS);
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
        drawConstraints(g2d);
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


    private void drawConstraints(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.draw(new Circle(CENTER_CONSTRAINS.getVector()[0] - RADIUS_CONSTRAINS, CENTER_CONSTRAINS.getVector()[1] - RADIUS_CONSTRAINS, Color.black, RADIUS_CONSTRAINS));
    }


    private void paintObj(Graphics2D g2d) {
        for (Circle circle : circles) {
            circle.paintCircle(g2d);
        }
    }




}
