/*
 * MIT License
 *
 * Copyright (c) 2016 Maksim Frolov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.maksofrol.gameoflife.forms;

import com.maksofrol.gameoflife.controller.LifeController;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Label;

import java.awt.*;

/**
 * TO DO
 */
/* TODO
Перевести проект на стримы.
 */
public class FieldForm {

    private static volatile FieldForm instance;

    public static FieldForm getInstance() {
        FieldForm localInstance = instance;
        if (localInstance == null) {
            synchronized (FieldForm.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new FieldForm();
                }
            }
        }
        return localInstance;
    }

    private FieldForm(){}

    private final Display fieldDisplay = new Display();
    private final Shell fieldShell = new Shell(fieldDisplay, SWT.CLOSE | SWT.TITLE);
    private final Canvas field = new Canvas(fieldShell, SWT.BORDER);
    private final Composite menu = new Composite(fieldShell, SWT.BORDER);

    private final Button addB = new Button(menu, SWT.PUSH);
    private final Button clearB = new Button(menu, SWT.PUSH);
    private final Button startB = new Button(menu, SWT.PUSH);
    private final Button exitB = new Button(menu, SWT.PUSH);
    private final Button stopB = new Button(menu, SWT.PUSH);


    private final Text xCellC = new Text(menu, SWT.SINGLE | SWT.BORDER);
    private final Text yCellC = new Text(menu, SWT.SINGLE | SWT.BORDER);
    private final Label xyLabel = new Label(menu, SWT.HORIZONTAL);

    private ListenersFactory factory = new ListenersFactory();
    private final Image cell = new Image(fieldDisplay, new ImageData("cell.png"));

    private final LifeController controller = LifeController.getInstance();

    public void init() {
        fieldShell.setText("Game of Life");
        fieldShell.setSize(1200, 1040);
        fieldShell.setLocation(0, 0);

        field.setSize(1006, 1006);
        field.setLocation(0, 0);
        field.setBackground(new Color(null, 255, 255, 255));

        menu.setSize(180, 1000);
        menu.setLocation(1010, 5);

        xCellC.setSize(50, 20);
        xCellC.setLocation(30, 90);
        xCellC.setTextLimit(4);
        xCellC.addVerifyListener(factory.getVerifyTextListener());
        yCellC.setSize(50, 20);
        yCellC.setLocation(95, 90);
        yCellC.setTextLimit(4);
        yCellC.addVerifyListener(factory.getVerifyTextListener());

        xyLabel.setSize(100, 20);
        xyLabel.setLocation(30, 75);
        xyLabel.setText("X:                  Y:");

        addB.setText("Add cell");
        addB.setSize(120, 30);
        addB.setLocation(30, 120);
        addB.addListener(SWT.MouseDown, factory.getDrawCellListener(field, cell, xCellC, yCellC));
        clearB.setText("Clear field");
        clearB.setSize(120, 40);
        clearB.setLocation(30, 170);
        clearB.addListener(SWT.MouseDown, factory.getClearFieldListener(field));

        Control[] disAfterStart = new Control[] {xCellC, yCellC, addB, clearB, startB};
        startB.setText("Start game!");
        startB.setSize(120, 40);
        startB.setLocation(30, 300);
        startB.addListener(SWT.MouseDown, factory.getStartListener(disAfterStart));
        exitB.setText("Exit");
        exitB.setSize(120, 40);
        exitB.setLocation(30, 800);
        exitB.addListener(SWT.MouseDown, factory.getCloseBListener(fieldShell));
        stopB.setText("Stop");
        stopB.setSize(120, 40);
        stopB.setLocation(30, 350);
        stopB.addListener(SWT.MouseDown, factory.getStopListener(disAfterStart));

        fieldShell.open();

        while (!fieldShell.isDisposed()) {
            if (!fieldDisplay.readAndDispatch()) {
                fieldDisplay.sleep();
            }
        }

        fieldDisplay.dispose();
    }

    public static void centerShell(Shell shell) {
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((screenSize.getWidth() - shell.getSize().x) / 2);
        if (x < 0) {
            x = 0;
        }
        int y = (int) ((screenSize.getHeight() - shell.getSize().y) / 2);
        if (y < 0) {
            y = 0;
        }
        shell.setBounds(x, y, shell.getSize().x, shell.getSize().y);
    }

}
