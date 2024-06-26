/*
Student ID: w1998881
IIT ID: 20220662
Name: Dinushi Wanniarachchi
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static ArrayList<String[]> slidingPuzzle = new ArrayList<>();

    //Point class to represent coordinates and path
    static class Point {
        int x;
        int y;
        String path;

        //Constructor
        public Point(int x, int y, String path) {
            this.x = x;
            this.y = y;
            this.path = path;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        //Read the puzzle map from a file
        File file = new File("puzzle.txt");
        Scanner scanner = new Scanner(file);

        //Parse the input file and populate the slidingPuzzle Arraylist
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] list_view = line.split("");
            slidingPuzzle.add(list_view);
        }

        //Find the starting position('S')
        int startRow = -1;
        int startCol = -1;

        for (int c = 0; c < slidingPuzzle.size(); ++c) {
            for (int num = 0; num < slidingPuzzle.get(c).length; ++num) {
                if (slidingPuzzle.get(c)[num].equalsIgnoreCase("S")) {
                    startRow = c;
                    startCol = num;
                }
            }
        }

        //Initialize a queue for BFS and a boolean array to track visited cells
        LinkedList<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[slidingPuzzle.size()][slidingPuzzle.getFirst().length];
        visited[startRow][startCol] = true;
        queue.add(new Point(startRow, startCol, "1. Start at (" + startCol + "," + startRow + ")"));

        //Arrays to represent possible direction
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        String[] dir = {"Move right to ", "Move down to ", "Move left to ", "Move up to "};

        long startTime = System.currentTimeMillis();

        //BFS traversal to find the shortest path
        while (!queue.isEmpty()) {
            Point curr = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nx = curr.x + dx[i];
                int ny = curr.y + dy[i];
                String nPath = curr.path + (curr.path.isEmpty() ? "" : "\n") + (curr.path.split("\n").length) + ". " + dir[i] + "(" + ny + "," + nx + ")";

                //If finish point("F") is reached, print done and exit
                if (isValid(nx, ny) && slidingPuzzle.get(nx)[ny].equalsIgnoreCase("F")) {
                    long endTime = System.currentTimeMillis();
                    System.out.println("Shortest path found:\n" + nPath);
                    System.out.println("Done!");
                    System.out.println("Runtime: " + (endTime - startTime) + " milliseconds");
                    return;
                }

                //If the new position is valid and not visited, add to the queue
                if (isValid(nx, ny) && !visited[nx][ny] && slidingPuzzle.get(nx)[ny].equals(".")) {
                    visited[nx][ny] = true;
                    queue.add(new Point(nx, ny, nPath));
                }
            }
        }

        //If no path to finish point found
        long endTime = System.currentTimeMillis();
        System.out.println("No path found.");
        System.out.println("Runtime: " + (endTime - startTime) + " milliseconds");
    }

    // Check if the coordinates are within the bounds of the maze
    private static boolean isValid(int row, int col) {
        return row >= 0 && row < slidingPuzzle.size() && col >= 0 && col < slidingPuzzle.get(row).length;
    }
}
