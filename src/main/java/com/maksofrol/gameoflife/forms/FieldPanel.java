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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

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
    private final JButton randomizeB;
    private final JTextField xText;
    private final JTextField yText;
    private final JLabel xyLabel;
    private final ListenersFactory factory = new ListenersFactory();
    private final LifeController controller;
    private final Timer startGame;
    private final JLabel version;
    private final JLabel popCount;
    private final JLabel count;
    private final JLabel seedLabel;
    private final JTextField seedText;

    private long delay;

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
        randomizeB = new JButton();
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
        randomizeB.setText("Randomize!");
        randomizeB.setBounds(1040, 480, 110, 40);
        randomizeB.setFont(new Font("Verdana", Font.PLAIN, 12));

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

        version = new JLabel();
        version.setText("version 0.4.2");
        version.setBounds(1100, 950, 100, 50);
        version.setFont(new Font("Verdana", Font.PLAIN, 10));
        version.setEnabled(false);

        popCount = new JLabel();
        popCount.setText("Population:");
        popCount.setBounds(1040, 240, 110, 40);
        popCount.setBorder(BorderFactory.createEtchedBorder());
        popCount.setFont(new Font("Verdana", Font.PLAIN, 11));

        count = new JLabel();
        count.setText("0");
        count.setBounds(1108, 240, 50, 40);
        count.setFont(new Font("Verdana", Font.PLAIN, 11));

        seedLabel = new JLabel();
        seedLabel.setText("Seed:");
        seedLabel.setBounds(1040, 440, 40, 40);
        seedLabel.setFont(new Font("Verdana", Font.PLAIN, 11));

        seedText = new JTextField();
        seedText.setBounds(1080, 450, 60, 20);
        seedText.setFont(new Font("Verdana", Font.PLAIN, 12));

        startGame = new Timer(0, factory.getTimerListener());

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
        add(field);
        add(addB);
        add(clearB);
        add(startB);
        add(stopB);
        add(exitB);
        add(randomizeB);
        add(xText);
        add(yText);
        add(xyLabel);
        add(version);
        add(popCount);
        add(count);
        add(seedLabel);
        add(seedText);

        textLimit(xText, 3, "1234567890");
        textLimit(yText, 3, "1234567890");
        textLimit(seedText, 6, "1234567890");
        stopB.setEnabled(false);

        addB.addActionListener(factory.getAddListener());
        exitB.addActionListener(factory.getExitListener());
        clearB.addActionListener(factory.getClearFListener());
        startB.addActionListener(factory.getStartListener());
        stopB.addActionListener(factory.getStopListener());
        randomizeB.addActionListener(factory.getRandomizerListener());
        field.addMouseListener(factory.getTouchListener());
        field.addMouseMotionListener(factory.getMouseMListener());
    }

    private class ListenersFactory {

        private ActionListener getTimerListener() {
            return e -> {
                delay = System.currentTimeMillis();
                try {
                    controller.checkCells();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                field.repaint();
                count.setText(Long.toString(controller.getPopulationCount()));
                delay = System.currentTimeMillis() - delay;
                delay = delay > 50 ? 0 : 50 - delay;
                startGame.setDelay((int) delay);
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
                controller.setPopulationCount(0);
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

        private ActionListener getRandomizerListener() {
            return e -> {
                controller.clearActiveCells();
                String seed = seedText.getText();
                if (seed.equals("")) {
                    controller.generateField();
                } else {
                    controller.generateField(Long.parseLong(seed));
                }
                repaint();
                controller.setPopulationCount(0);
            };
        }

        private MouseMotionListener getMouseMListener() {
            return new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    int x = e.getX() / 2 - 1;
                    int y = e.getY() / 2 - 1;
                    if (x >= 0 && x <= 500 && y >= 0 && y <= 500) {
                        controller.addAliveCell(x, y);
                    }
                    field.repaint();
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                }
            };
        }

        private MouseListener getTouchListener() {
            return new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    int x = e.getX() / 2 - 1;
                    int y = e.getY() / 2 - 1;
                    controller.addAliveCell(x, y);
                    field.repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            };
        }
    }
}
