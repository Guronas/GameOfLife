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

package com.maksofrol.gameoflife.controller;

import com.maksofrol.gameoflife.components.Cell;
import com.maksofrol.gameoflife.forms.FieldForm;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * TO DO
 */
public class LifeController {

    private FieldForm form;

    private final Cell[] cells = new Cell[251_001];
    private BlockingQueue<Cell> activeCells = new LinkedBlockingDeque<>();

    public LifeController(FieldForm form) {
        this.form = form;

        for (int i = 0, index = 0; i <= 500; i++) {
            for (int j = 0; j <= 500; j++, index++) {
                cells[index] = new Cell(i, j, false);
            }
        }
    }

    public void addAliveCell(Canvas field, Image cell, int x, int y) {
        try {
            int index = x * 501 + y;
            Cell mainCell = cells[index];
            if (!mainCell.isAlive()) {
                mainCell.setLivingState(true);

                if (!mainCell.isActive()) {
                    mainCell.setActive(true);
                    activeCells.add(mainCell);
                }
                for (int neighborIndex : mainCell.getNeighborsIndex()) {
                    if (!cells[neighborIndex].isActive()) {
                        cells[neighborIndex].setActive(true);
                        activeCells.add(cells[neighborIndex]);
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("lool");
        }

        drawCell(field, cell, x, y);
    }

    public void drawCell(Canvas field, Image cell, int x, int y) {
        GC gc = new GC(field);
        gc.drawImage(cell, x * 2, y * 2);
    }

    public void checkAndRedraw() {

    }
}
