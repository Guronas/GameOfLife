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

/**
 * TO DO
 */

public class Cell implements Runnable {
    private final int xCoordinate;
    private final int yCoordinate;
    private boolean livingState;
    private boolean active;
    private final int[] neighborsIndex = new int[8];

    public Cell(int x, int y, boolean livingState) {
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.livingState = livingState;

        for (int i = x - 1, index = 0; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (!(i == x && j == y)) {
                    neighborsIndex[index] = i * 501 + j;
                    index++;
                }
            }
        }
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

    public int[] getNeighborsIndex(){
        return neighborsIndex;
    }

    public void run() {
    }

    @Override
    public int hashCode() {
        int p = 31765;
        int q = 31764;
        return (p * xCoordinate) + (q * yCoordinate);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Cell && this.hashCode() == obj.hashCode();
    }
}
