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

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * TO DO
 */
public class FieldPanel extends JPanel {
    private final JFrame rootFrame;
    private final JPanel field;
    private final JButton addB;
    private final JButton startB;
    private final JButton stopB;
    private final JButton exitB;
    private final JButton clearB;
    private final JTextField xText;
    private final JTextField yText;
    private final JLabel xyLabel;
    private final ListenersFactory factory = new ListenersFactory();
    private final LifeController controller;
    private final Timer startGame;

    public FieldPanel(JFrame rootFrame) {
        controller = LifeController.getInstance();
        this.rootFrame = rootFrame;
        setLayout(null);

        field = new Canvas(0, 0, 1006, 1006);

        addB = new JButton();
        startB = new JButton();
        stopB = new JButton();
        exitB = new JButton();
        clearB = new JButton();
        addB.setText("Add cell");
        addB.setBounds(1040, 150, 110, 30);
        clearB.setFont(new Font("Verdana", Font.PLAIN, 12));
        clearB.setText("Clear field");
        clearB.setBounds(1040, 190, 110, 40);
        addB.setFont(new Font("Verdana", Font.PLAIN, 12));
        startB.setText("Start game!");
        startB.setBounds(1040, 300, 110, 40);
        startB.setFont(new Font("Verdana", Font.PLAIN, 12));
        stopB.setText("Stop");
        stopB.setBounds(1040, 350, 110, 40);
        stopB.setFont(new Font("Verdana", Font.PLAIN, 12));
        exitB.setText("Exit");
        exitB.setBounds(1040, 600, 110, 40);
        exitB.setFont(new Font("Verdana", Font.PLAIN, 12));

        xText = new JTextField();
        xText.setBounds(1040, 120, 50, 20);
        xText.setFont(new Font("Verdana", Font.PLAIN, 12));
        yText = new JTextField();
        yText.setBounds(1095, 120, 50, 20);
        yText.setFont(new Font("Verdana", Font.PLAIN, 12));

        xyLabel = new JLabel();
        xyLabel.setBounds(1040, 102, 110, 20);
        xyLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        xyLabel.setText("X:          Y:");

        startGame = new Timer(50, factory.getTimerListener());

        add(field);
        add(addB);
        add(clearB);
        add(startB);
        add(stopB);
        add(exitB);
        add(xText);
        add(yText);
        add(xyLabel);

        init();
    }

    private void textLimit(JTextField textField, int limit, String charLimit) {
        textField.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
                if (str == null || !charLimit.contains(str))
                    return;
                if ((getLength() + str.length()) <= limit) {
                    super.insertString(offset, str, attr);
                }
            }
        });
    }

    private void init() {
        textLimit(xText, 3, "1234567890");
        textLimit(yText, 3, "1234567890");
        stopB.setEnabled(false);

        addB.addActionListener(factory.getAddListener());
        exitB.addActionListener(factory.getExitListener());
        clearB.addActionListener(factory.getClearFListener());
        startB.addActionListener(factory.getStartListener());
        stopB.addActionListener(factory.getStopListener());
    }

    private class ListenersFactory {

        private  ActionListener getTimerListener(){
            return e -> {
                try {
                    controller.checkAndRedraw();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                field.repaint();
            };
        }

        private ActionListener getAddListener() {
            return e -> {
                String x1 = xText.getText();
                String y1 = yText.getText();
                if (x1.equals("") || y1.equals("") || Integer.parseInt(x1) > 500 || Integer.parseInt(y1) > 500) {
                    JOptionPane.showMessageDialog(rootFrame, "Wrong parameters. Please choose coordinates from 0 to 500.");
                    return;
                }
                controller.addAliveCell(Integer.parseInt(x1), Integer.parseInt(y1));
                field.repaint();
            };
        }

        private ActionListener getExitListener() {
            return e -> {
                System.exit(0);
            };
        }

        private ActionListener getClearFListener() {
            return e -> {
                controller.clearActiveCells();
                field.repaint();
            };
        }

        private ActionListener getStartListener() {
            return e -> {
                xText.setEnabled(false);
                yText.setEnabled(false);
                addB.setEnabled(false);
                clearB.setEnabled(false);
                startB.setEnabled(false);
                stopB.setEnabled(true);
                startGame.start();
            };
        }

        private ActionListener getStopListener() {
            return e -> {
                startGame.stop();
                xText.setEnabled(true);
                yText.setEnabled(true);
                addB.setEnabled(true);
                clearB.setEnabled(true);
                stopB.setEnabled(false);
                startB.setEnabled(true);
            };
        }
    }
}