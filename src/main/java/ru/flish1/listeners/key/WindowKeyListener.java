package ru.flish1.listeners.key;

import ru.flish1.constraints.CircleConstraint;
import ru.flish1.constraints.Constraint;
import ru.flish1.constraints.RectangleConstraint;
import ru.flish1.view.MainWindow;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WindowKeyListener extends KeyAdapter {
    private MainWindow mainWindow;
    private Constraint constraintCircle;
    private Constraint constraintRect;

    public WindowKeyListener(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        constraintCircle = new CircleConstraint(50, 50, 356);
        constraintRect = new RectangleConstraint(50, 50, 700, 700);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'c' || e.getKeyChar() == 'с'){
            mainWindow.getCircles().clear();
        } else if (e.getKeyChar() == 'z' || e.getKeyChar() == 'я'){
            if (mainWindow.getConstraint() instanceof CircleConstraint){
                mainWindow.setConstraint(constraintRect);
            } else if (mainWindow.getConstraint() instanceof RectangleConstraint) {
                mainWindow.setConstraint(constraintCircle);
            }
        }

    }
}
