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

package com.maksofrol.gameoflife.form;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

/**
 * TO DO
 */
/* TODO
Перевести проект на стримы.
 */
public class FieldForm {

    private final Display fieldDisplay = new Display();
    private final Shell fieldShell = new Shell(fieldDisplay, SWT.CLOSE | SWT.TITLE);
    private final Composite field = new Composite(fieldShell, SWT.BORDER);
    private final Composite menu = new Composite(fieldShell, SWT.BORDER);
    private final Button addB = new Button(menu, SWT.PUSH);
    private final Button startB = new Button(menu, SWT.PUSH);
    private final Button exitB = new Button(menu, SWT.PUSH);
    private final Text xCoordinate = new Text(menu, SWT.SINGLE);
    private final Text yCoordinate = new Text(menu, SWT.SINGLE);

    public FieldForm() {
    }

    public void init() {
        fieldShell.setText("Game of Life");
        fieldShell.setSize(1200, 1040);
        fieldShell.setLocation(0, 0);

        field.setSize(1000, 1000);
        field.setLocation(5, 5);

        menu.setSize(200, 1000);
        menu.setLocation(1010, 5);

        xCoordinate.setSize(50, 20);
        xCoordinate.setLocation(30,70);
        yCoordinate.setSize(50, 20);
        yCoordinate.setLocation(100,70);

        addB.setText("Add cell");
        addB.setSize(120, 30);
        addB.setLocation(30, 100);
        startB.setText("Start game!");
        startB.setSize(120, 40);
        startB.setLocation(30, 200);
        exitB.setText("Exit");
        exitB.setSize(120, 40);
        exitB.setLocation(30, 800);

        fieldShell.open();

        while (!fieldShell.isDisposed()) {
            if (!fieldDisplay.readAndDispatch()) {
                fieldDisplay.sleep();
            }
        }

        fieldDisplay.dispose();
    }

}
