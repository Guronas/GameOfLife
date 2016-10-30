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

import java.awt.*;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * TO DO
 */
public class LifeController {
    private static volatile LifeController instance;

    private final Cell[] cells = new Cell[251_001];
    private final ConcurrentLinkedQueue<Cell> activeCells = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Point> tempActiveCells = new ConcurrentLinkedQueue<>();
    private final ExecutorService executor;

    private LifeController() {
        for (int i = 0, index = 0; i <= 500; i++) {
            for (int j = 0; j <= 500; j++, index++) {
                cells[index] = new Cell(i, j, this);
            }
        }
        for (Cell cell : cells) {
            cell.setNeighbors();
        }

        executor = new ThreadPoolExecutor(10_000, 10_000, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(251_001));
        for (Cell cell : cells) {
            activeCells.offer(cell);
        }
        try {
            executor.invokeAll(activeCells);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            activeCells.clear();
        }

        instance = this;
    }

    public ConcurrentLinkedQueue<Cell> getActiveCells() {
        return activeCells;
    }

    public Cell[] getCells() {
        return cells;
    }

    public Queue<Point> getTempActiveCells() {
        return tempActiveCells;
    }

    public static LifeController getInstance() {
        LifeController localInstance = instance;
        if (localInstance == null) {
            synchronized (LifeController.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new LifeController();
                }
            }
        }
        return localInstance;
    }

    public void addAliveCell(int x, int y) {
        int index = x * 501 + y;
        if (!cells[index].isAlive()) {
            cells[index].setLivingState(true);

            if (!cells[index].isActive()) {
                cells[index].setActive(true);
                activeCells.offer(cells[index]);
            }
            for (Cell neighbor : cells[index].getNeighbors()) {
                if (!neighbor.isActive()) {
                    neighbor.setActive(true);
                    activeCells.offer(neighbor);
                }
            }
        }
    }

    public void clearActiveCells() {
        activeCells.forEach(cell -> {
            cell.setActive(false);
            cell.setLivingState(false);
        });
        activeCells.clear();
    }

    public void checkCells() throws InterruptedException {
        executor.invokeAll(activeCells);
        clearActiveCells();
        tempActiveCells.forEach(point -> {
            addAliveCell(point.x, point.y);
        });
        tempActiveCells.clear();
    }
}
