public class TestGraphA {
    public static void main(String[] args) {
        int[][] A = {
                {0, 1, 1, 1, 0},
                {1, 0, 0, 1, 1},
                {1, 0, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {0, 1, 0, 0, 0}};
        Graph test = new Graph();
        System.out.println(test.DFS(A));
        System.out.println(test.BFS(A));
        System.out.println("----------------------------------");

        A = new int[][]{
                {0, 7, 8, 6, 0, 0, 0, 0},
                {7, 0, 2, 0, 11, 0, 0, 0},
                {8, 2, 0, 7, 10, 3, 5, 0},
                {6, 0, 7, 0, 0, 0, 10, 0},
                {0, 11, 10, 0, 0, 6, 0, 19},
                {0, 0, 3, 0, 6, 0, 12, 7},
                {0, 0, 5, 10, 0, 12, 0, 4},
                {0, 0, 0, 0, 19, 7, 4, 0}};
        System.out.println(test.Dijkstra(A, 0));
        System.out.println("----------------------------------");
        node[] path = test.Prima(A, 2);
        for (int i = 0; i < A[0].length; ++i)
            System.out.println(path[i].prev + " - " + i);
        System.out.println("----------------------------------");
        path = test.Kruskala(A);
        for (int i = 0; i < A[0].length - 1; ++i)
            System.out.println(path[i].prev + " - " + path[i].next);
        System.out.println("----------------------------------");
        int[][] AnswerA = test.FloydWarshall(A);
        for(int[] row : AnswerA)
            printRow(row);
    }

    public static void printRow(int[] row) {
        for (int i : row) {
            System.out.print(i);
            System.out.print("\t");
        }
        System.out.println();
    }
}