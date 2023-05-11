package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MazeGenerator {

    private int[][] maze;
    private Random rand = new Random();

    public MazeGenerator(int numRows, int numCols) {
        maze = new int[numRows][numCols];
        // initialize all cells to walls
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                maze[row][col] = 21;
            }
        }
    }

    public int[][] generateMaze() {
        
        int numWalls = maze.length * maze[0].length; // total number of cells
        int numWallCells = numWalls; // initialize to maximum value
        while (numWallCells > numWalls * 0.4) { // regenerate maze if too many walls
            // start at a random cell
            int startRow = rand.nextInt(maze.length);
            int startCol = rand.nextInt(maze[0].length);
            maze[startRow][startCol] = 0; // mark the starting cell as a passage
            List<int[]> stack = new ArrayList<>();
            stack.add(new int[]{startRow, startCol});

            while (!stack.isEmpty()) {
                int[] currentCell = stack.get(stack.size() - 1);
                int row = currentCell[0];
                int col = currentCell[1];
                List<int[]> neighbors = getUnvisitedNeighbors(row, col);
                if (!neighbors.isEmpty()) {
                    int[] neighbor = neighbors.get(rand.nextInt(neighbors.size()));
                    int neighborRow = neighbor[0];
                    int neighborCol = neighbor[1];
                    // remove the wall between the current cell and the chosen neighbor
                    int wallRow = (row + neighborRow) / 2;
                    int wallCol = (col + neighborCol) / 2;
                    maze[wallRow][wallCol] = 0;
                    // mark the chosen neighbor as a passage and add it to the stack
                    maze[neighborRow][neighborCol] = 0;
                    stack.add(neighbor);

                } else {
                    stack.remove(stack.size() - 1);
                }
            }

                        
            // calculate the percentage of walls
            numWallCells = 0;
            for (int row = 0; row < maze.length; row++) {
                for (int col = 0; col < maze[0].length; col++) {
                    if (maze[row][col] == 21) {
                        numWallCells++;
                    }
                }
            }
        }

        return maze;
    }

    private List<int[]> getUnvisitedNeighbors(int row, int col) {
        List<int[]> neighbors = new ArrayList<>();
        if (row >= 2 && maze[row - 2][col] == 21) {
            neighbors.add(new int[]{row - 2, col});
        }
        if (row < maze.length - 2 && maze[row + 2][col] == 21) {
            neighbors.add(new int[]{row + 2, col});
        }
        if (col >= 2 && maze[row][col - 2] == 21) {
            neighbors.add(new int[]{row, col - 2});
        }
        if (col < maze[0].length - 2 && maze[row][col + 2] == 21) {
            neighbors.add(new int[]{row, col + 2});
        }
        Collections.shuffle(neighbors);
        return neighbors;
    }
}
