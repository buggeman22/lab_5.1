import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
    private LinkedList<Integer> elements = new LinkedList<>();

    public Graph() {
    }

    //поиск в глубину
    public ArrayList<Integer> DFS(int[][] orig) {
        ArrayList<Integer> path = new ArrayList<>();
        boolean[] visited = new boolean[orig[0].length];
        for (int i = 0; i < orig[0].length; ++i)
            visited[i] = false;
        path = InDepth(0, orig, path, visited);
        return path;
    }

    private ArrayList<Integer> InDepth(int peak, int[][] orig, ArrayList<Integer> path, boolean[] visited) {
        visited[peak] = true;
        path.add(peak);
        for (int i = peak, j = 0; j < visited.length; ++j)
            if (visited[j] == false && orig[i][j] != 0)
                path = InDepth(j, orig, path, visited);
        return path;
    }

    //поиск в ширину
    public ArrayList<Integer> BFS(int[][] orig) {
        ArrayList<Integer> path = new ArrayList<>();
        boolean[] visited = new boolean[orig[0].length];
        for (int i = 0; i < orig[0].length; ++i)
            visited[i] = false;

        elements.offer(0); //очередь
        visited[0] = true;                                       //необходимо, т.к. инчане 0 отметится в очереди потом ещё раз
        while (elements.peek() != null) {
            int peak = elements.poll();
            path.add(peak);
            for (int i = peak, j = 0; j < visited.length; ++j)
                if (visited[j] == false && orig[i][j] != 0) {
                    elements.offer(j);
                    visited[j] = true;
                }
        }
        return path;
    }

    //алгоритм Дейкстры
    public ArrayList<Integer> Dijkstra(int[][] orig, int start) {
        ArrayList<Integer> path = new ArrayList<>();
        node[] D = new node[orig[0].length];
        boolean[] visited = new boolean[orig[0].length];
        for (int i = 0; i < visited.length; ++i) {
            D[i] = new node(i, Integer.MAX_VALUE);
            visited[i] = false;
        }

        D[start].sum = 0;
        D[start].prev = -5;
        int count = visited.length, tmp = start;
        while (count > 0) {
//            System.out.println("*******************************");
//            System.out.println("Находимся в: "+tmp);
            for (int i = tmp, j = 0; j < visited.length; ++j) {
//                System.out.println(D[tmp].sum + orig[i][j]);
                if (orig[i][j] != 0 && D[tmp].sum + orig[i][j] < D[j].sum) {
                    D[j].sum = D[tmp].sum + orig[i][j];
                    D[j].prev = tmp;
//                    System.out.println("-----: "+j);
                }
            }
            visited[tmp] = true;
            count--;
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < visited.length; ++i)
                if (D[i].sum < min && visited[i] != true){
                    tmp = i;
                    min = D[i].sum;
                }
        }

        path.add(visited.length - 1);
        int i = D[visited.length - 1].prev;
        do {
            path.add(i);
            i = D[i].prev;
        } while (i != -5);
        return path;
    }

    //алгоритм Прима
    public node[] Prima(int[][] orig, int start) {
        node[] D = new node[orig[0].length];
        boolean[] visited = new boolean[orig[0].length];
        for (int i = 0; i < visited.length; ++i) {
            D[i] = new node(i, Integer.MAX_VALUE);
            visited[i] = false;
        }

        D[start].sum = 0;
        D[start].prev = -5; // пару с этим маркеров в ответе не учитываем
        int count = visited.length, tmp = start;
        while (count > 0) {
            for (int i = tmp, j = 0; j < visited.length; ++j) {
                if (orig[i][j] != 0 && orig[i][j] < D[j].sum && visited[j]==false) {
                    D[j].sum = orig[i][j];
                    D[j].prev = tmp;
                }
            }
            visited[tmp] = true;
            count--;
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < visited.length; ++i)
                if (D[i].sum < min && visited[i] != true){
                    tmp = i;
                    min = D[i].sum;
                }
        }
        return D;
    }

    //алгоритм Крускала
    public node[] Kruskala(int[][] orig){
        int count=0, tmpcount=0;                                  // количство ребер
        for (int i=0; i<orig[0].length-1; ++i)
            for (int j=i+1; j<orig[0].length; ++j)
                if (orig[i][j]!=0)
                    count++;
        node[] SortRibs = new node[count];                      // массив ребер
        for (int i=0; i<orig[0].length-1; ++i)
            for (int j=i+1; j<orig[0].length; ++j)
                if (orig[i][j]!=0){
                    SortRibs[tmpcount] = new node(i, j, orig[i][j]); // заполняем массив ребер
                    tmpcount++;
                }
        for (int i = 0; i < SortRibs.length-1; i++)
            for(int j = 0; j < SortRibs.length-i-1; j++) {
                if(SortRibs[j].sum > SortRibs[j+1].sum){ //сортируем массив ребер по весу ребра графа
                    int tempPrev = SortRibs[j+1].prev;
                    int tmpNext = SortRibs[j+1].next;
                    int tmpRib = SortRibs[j+1].sum;
                    SortRibs[j+1].prev = SortRibs[j].prev;
                    SortRibs[j+1].next = SortRibs[j].next;
                    SortRibs[j+1].sum = SortRibs[j].sum;
                    SortRibs[j].prev=tempPrev;
                    SortRibs[j].next=tmpNext;
                    SortRibs[j].sum=tmpRib;
                }
            }

        node[] edges = new node[orig[0].length-1]; // ответ
        int[] D = new int[orig[0].length]; // массив индикаторов принадлежности дереву
        for (int i=0; i<D.length; ++i) {
            D[i] = i;
            if (i<D.length-1)
                edges[i] = new node(-5, -5);
        }
        tmpcount=0;

        for (int i=0; i<count; ++i){
            int tmpA = SortRibs[i].prev, tmpB = SortRibs[i].next;
            if (D[tmpA] != D[tmpB]){
                edges[tmpcount].prev=tmpA;
                edges[tmpcount].next=tmpB;
                tmpA=D[tmpA];
                for (int j=0; j<D.length; ++j)
                    if (D[j] == tmpA)
                        D[j] = D[tmpB];
            }
            tmpcount++;
        }
        return edges;
    }

    //алгоритм Флойда-Уоршалла
    public int[][] FloydWarshall (int[][] orig){
        for (int i=0; i<orig[0].length; ++i)
            for (int j=0; j<orig[0].length; ++j)
                if (orig[i][j]==0 && i!=j)
                    orig[i][j]=Integer.MAX_VALUE/2;       // подготовка матрицы к применению алгоритма

        for (int k=0; k<orig[0].length; ++k)
            for (int i = 0; i < orig[0].length; ++i)
                for (int j = 0; j < orig[0].length; ++j)
                    orig[i][j] = Math.min(orig[i][j], orig[i][k] + orig[k][j]); //сам алгоритм
        return orig;
    }
}