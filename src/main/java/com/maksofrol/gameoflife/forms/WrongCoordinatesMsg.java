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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * TO DO
 */
public class WrongCoordinatesMsg implements Runnable {
    private final Display msgdDisplay = Display.getDefault();
    private final Shell msgShell = new Shell(msgdDisplay, SWT.CLOSE | SWT.TITLE | SWT.APPLICATION_MODAL);
    private final Button okB = new Button(msgShell, SWT.PUSH);
    private final Label xyLabel = new Label(msgShell, SWT.HORIZONTAL);
    private ListenersFactory factory = new ListenersFactory();



    @Override
    public void run() {
        msgdDisplay.asyncExec(new Runnable() {
            @Override
            public void run() {
                msgShell.setText("Wrong parameters!");
                msgShell.setSize(600, 200);
                FieldForm.centerShell(msgShell);

                okB.setSize(120,30);
                okB.setText("OK");
                okB.setLocation(240, 100);
                okB.addListener(SWT.MouseDown, factory.getCloseBListener(msgShell));

                FontData fontData = xyLabel.getFont().getFontData()[0];
                fontData.setHeight(12);
                final Font font = new Font(msgdDisplay, fontData);

                xyLabel.setText("Wrong parameters. Please choose coordinates from 0 to 500.");
                xyLabel.setSize(500, 100);
                xyLabel.setLocation(80, 50);
                xyLabel.setFont(font);

                msgShell.open();
                while (!msgShell.isDisposed()) {
                    if (!msgdDisplay.readAndDispatch()) {
                        msgdDisplay.sleep();
                    }
                }
            }
        });
    }
}
