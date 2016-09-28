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

package com.maksofrol.gameoflife;

import java.awt.*;
import java.util.ArrayList;

/**
 * TO DO
 */

public class Cell implements Runnable {
    private final Point coordinate;
    private final ArrayList<Cell> neighbors = new ArrayList<>();
    private boolean livingState;

    Cell(Point coordinate, boolean livingState) {
        this.coordinate = coordinate;
        this.livingState = livingState;
        if (livingState) {
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++)
                    neighbors.add(new Cell(new Point(x, y)));
            }
        }
    }

    public Cell(Point coordinate) {
        this.coordinate = coordinate;
    }

    private void checkItLiving() {
        int livingNeighborsCount = 0;
        if (!livingState) {

        }
        for (Cell neighbor : neighbors) {
            if (neighbor.itsLiving()) {
                livingNeighborsCount++;
            }
            if (!livingState && livingNeighborsCount == 3) {
                livingState = true;
                break;
            }
        }

        if (!(livingNeighborsCount == 2 || livingNeighborsCount == 3)) ;
    }

    private boolean itsLiving() {
        return livingState;
    }

    public void run() {
        checkItLiving();
    }
}
