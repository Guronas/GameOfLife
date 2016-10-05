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
import org.eclipse.swt.internal.gdip.PointF;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

/**
 * TO DO
 */
public class FieldForm {

    private final Display fieldDisplay = new Display();
    private final Shell fieldShell = new Shell(fieldDisplay, SWT.CLOSE | SWT.TITLE);
    private final Button start = new Button(fieldShell, SWT.PUSH);
    private final Button exit = new Button(fieldShell, SWT.PUSH);

    public FieldForm() {
    }

    public void init() {
        fieldShell.setText("Game of Life");
        fieldShell.setSize(1300, 1024);
        fieldShell.setLocation(0, 0);

        start.setText("Start game!");
        start.setSize(100, 30);
        start.setLocation(1100, 100);
        exit.setText("Exit");
        exit.setSize(100, 30);
        exit.setLocation(1100, 150);

        fieldShell.open();

        while (!fieldShell.isDisposed()) {
            if (!fieldDisplay.readAndDispatch()) {
                fieldDisplay.sleep();
            }
        }

        fieldDisplay.dispose();
    }

}
