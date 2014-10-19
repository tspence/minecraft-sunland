package net.spence.minecraft.sunland;

import java.util.Collections;
import java.util.Arrays;

/*
 * recursive backtracking algorithm
 * shamelessly borrowed from the ruby at
 * http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking
 */
public class MazeGenerator
{
    private final int _x;
    private final int _y;
    private final int[][] _maze;

    public MazeGenerator(int x, int y) 
    {
        this._x = x;
        this._y = y;
        _maze = new int[this._x][this._y];
        generateMaze(0, 0);
    }

    public int[][] getMaze()
    {
        return _maze;
    }

    private void generateMaze(int cx, int cy)
    {
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (DIR dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;
            if (between(nx, _x) && between(ny, _y) && (_maze[nx][ny] == 0)) {
                _maze[cx][cy] |= dir.bit;
                _maze[nx][ny] |= dir.opposite.bit;
                generateMaze(nx, ny);
            }
        }
    }

    private static boolean between(int v, int upper)
    {
        return (v >= 0) && (v < upper);
    }

    private enum DIR
    {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
        private final int bit;
        private final int dx;
        private final int dy;
        private DIR opposite;

        // use the static initializer to resolve forward references
        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        private DIR(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    };
}