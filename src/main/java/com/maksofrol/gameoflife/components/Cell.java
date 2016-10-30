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

package com.maksofrol.gameoflife.components;

import com.maksofrol.gameoflife.controller.LifeController;

import java.awt.*;
import java.util.concurrent.Callable;

/**
 * TO DO
 */

public class Cell implements Callable<Boolean> {
    private final int xCoordinate;
    private final int yCoordinate;
    private boolean livingState;
    private boolean active;
    private final Cell[] neighbors = new Cell[8];
    private final LifeController controller;
    private int neighborAliveCount;
    private final Point point;

    public Cell(int x, int y, LifeController controller) {
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.controller = controller;
        point = new Point(x, y);
    }

    public int getXCoordinate() {
        return xCoordinate;
    }

    public int getYCoordinate() {
        return yCoordinate;
    }

    public boolean isAlive() {
        return livingState;
    }

    public void setLivingState(boolean state) {
        livingState = state;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Cell[] getNeighbors() {
        return neighbors;
    }

    public void setNeighbors() {
        for (int i = xCoordinate - 1, index = 0; i <= xCoordinate + 1; i++) {
            int iX = checkBorders(i);
            for (int j = yCoordinate - 1; j <= yCoordinate + 1; j++) {
                int jX = checkBorders(j);
                if (!(i == xCoordinate && j == yCoordinate)) {
                    neighbors[index] = controller.getCells()[iX * 501 + jX];
                    index++;
                }
            }
        }
    }

    public Boolean call() {
        neighborAliveCount = 0;
        for (Cell neighbor : neighbors) {
            if (neighbor.isAlive()) neighborAliveCount++;
            if (neighborAliveCount == 4) break;
        }
        if ((!isAlive() && neighborAliveCount == 3) || (isAlive() && (neighborAliveCount == 2 || neighborAliveCount == 3))) {
            controller.getTempActiveCells().offer(point);
            controller.incPopCount();
        }
        return true;
    }

    private int checkBorders(int n) {
        if (n == -1) {
            return 500;
        } else if (n == 501) {
            return 0;
        }
        return n;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        return xCoordinate == cell.xCoordinate && yCoordinate == cell.yCoordinate;

    }

    @Override
    public int hashCode() {
        int result = xCoordinate;
        result = 31 * result + yCoordinate;
        return result;
    }
}
