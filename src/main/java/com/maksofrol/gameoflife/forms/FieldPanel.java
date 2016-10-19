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

import javax.swing.*;
import java.awt.*;

/**
 * TO DO
 */
public class FieldPanel extends JPanel {
    private final JPanel field;
    private final JButton addB;
    private final JButton startB;
    private final JButton stopB;
    private final JButton exitB;
    private final JButton clearB;
    private final JTextField xText;
    private final JTextField yText;
    private final JLabel xyLabel;

    public FieldPanel() {
        setLayout(null);

        field = new Canvas();
        field.setBounds(0, 0, 1002, 1002);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createEtchedBorder());

        addB = new JButton();
        startB = new JButton();
        stopB = new JButton();
        exitB = new JButton();
        clearB = new JButton();
        addB.setText("Add cell");
        addB.setBounds(1040, 150, 110,30);
        clearB.setFont(new Font("Verdana",Font.PLAIN, 12));
        clearB.setText("Clear field");
        clearB.setBounds(1040, 190, 110,40);
        addB.setFont(new Font("Verdana",Font.PLAIN, 12));
        startB.setText("Start game!");
        startB.setBounds(1040, 300,110,40);
        startB.setFont(new Font("Verdana",Font.PLAIN, 12));
        stopB.setText("Stop");
        stopB.setBounds(1040, 350,110,40);
        stopB.setFont(new Font("Verdana",Font.PLAIN, 12));
        exitB.setText("Exit");
        exitB.setBounds(1040, 600,110,40);
        exitB.setFont(new Font("Verdana",Font.PLAIN, 12));

        xText = new JTextField();
        xText.setBounds(1040, 120, 50, 20);
        xText.setFont(new Font("Verdana",Font.PLAIN, 12));
        yText = new JTextField();
        yText.setBounds(1095, 120, 50, 20);
        yText.setFont(new Font("Verdana",Font.PLAIN, 12));

        xyLabel = new JLabel();
        xyLabel.setBounds(1040, 102, 110, 20);
        xyLabel.setFont(new Font("Verdana",Font.PLAIN, 12));
        xyLabel.setText("X:          Y:");

        add(field);
        add(addB);
        add(clearB);
        add(startB);
        add(stopB);
        add(exitB);
        add(xText);
        add(yText);
        add(xyLabel);
    }

}
