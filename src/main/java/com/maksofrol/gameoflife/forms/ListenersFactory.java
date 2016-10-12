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
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;

/**
 * TO DO
 */
public class ListenersFactory {

    public VerifyListener getVerifyTextListener() {
        return e -> {
            if (!parseble(e.text)) {
                e.doit = false;
            }
        };
    }

    public Listener getAddCellListener(LifeController controller, Canvas field, Image cell, Text x, Text y) {
        return event -> {
            String xText = x.getText();
            String yText = y.getText();
            if (xText.equals("") || yText.equals("") || Integer.parseInt(xText) > 500 || Integer.parseInt(yText) > 500) {
                new Thread(new WrongCoordinatesMsg()).start();
                return;
            }

            controller.addAliveCell(field, cell, Integer.parseInt(x.getText()), Integer.parseInt(y.getText()));
        };
    }

    public Listener getCloseBListener(Shell shell) {
        return event -> {
            shell.dispose();
        };
    }

    public Listener getClearFieldListener(Canvas field) {
        return event -> {
            field.redraw();
        };
    }

    public Listener getStartListener(LifeController controller, Control... ctrls) {
        return event -> {
            for (Control ctrl : ctrls) {
                ctrl.setEnabled(false);
            }

        };
    }

    public Listener getStopListener(Control... ctrls) {
        return event -> {
            for (Control ctrl : ctrls) {
                ctrl.setEnabled(true);
            }
        };
    }

    private boolean parseble(String text) {
        for (char ch : text.toCharArray()) {
            if (Character.isDigit(ch)) continue;
            return false;
        }
        return true;

    }

}
