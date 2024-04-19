import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static ArrayList<String[]> slidingPuzzle = new ArrayList<>();

    static class Point {
        int x;
        int y;
        String path;

        public Point(int x, int y, String path) {
            this.x = x;
            this.y = y;
            this.path = path;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("puzzle.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] list_view = line.split("");
            slidingPuzzle.add(list_view);
        }

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

        LinkedList<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[slidingPuzzle.size()][slidingPuzzle.getFirst().length];
        visited[startRow][startCol] = true;
        queue.add(new Point(startRow, startCol, "1. Start at (" + startCol + "," + startRow + ")"));

        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        String[] dir = {"Move right to ", "Move down to ", "Move left to ", "Move up to "};

        while (!queue.isEmpty()) {
            Point curr = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nx = curr.x + dx[i];
                int ny = curr.y + dy[i];
                String nPath = curr.path + (curr.path.isEmpty() ? "" : "\n") + (curr.path.split("\n").length) + ". " + dir[i] + "(" + ny + "," + nx + ")";

                if (isValid(nx, ny) && slidingPuzzle.get(nx)[ny].equalsIgnoreCase("F")) {
                    System.out.println("Shortest path found:\n" + nPath);
                    System.out.println("Done!");
                    return;
                }

                if (isValid(nx, ny) && !visited[nx][ny] && slidingPuzzle.get(nx)[ny].equals(".")) {
                    visited[nx][ny] = true;
                    queue.add(new Point(nx, ny, nPath));
                }

            }
        }

        System.out.println("No path found.");
    }

    // Check if the coordinates are within the bounds of the maze
    private static boolean isValid(int row, int col) {
        return row >= 0 && row < slidingPuzzle.size() && col >= 0 && col < slidingPuzzle.get(row).length;
    }
}
