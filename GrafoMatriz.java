import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GrafoMatriz {

    private final int[][] matriz;     // matriz[i][j] = 1 si hay arista entre i y j
    private final String[] etiquetas; // nombre de cada vértice
    private final int n;              // número de vértices

    public GrafoMatriz(String[] etiquetas) {
        this.n = etiquetas.length;
        this.etiquetas = etiquetas;
        this.matriz = new int[n][n];
    }

    //Operaciones básicas 

    private void validar(int v) {
        if (v < 0 || v >= n) {
            throw new IllegalArgumentException("Vértice inválido: " + v);
        }
    }

    /** Agrega una arista no dirigida: se marca en ambas direcciones (simetría). */
    public void agregarArista(int u, int v) {
        validar(u);
        validar(v);
        matriz[u][v] = 1;
        matriz[v][u] = 1;
    }

    public void eliminarArista(int u, int v) {
        validar(u);
        validar(v);
        matriz[u][v] = 0;
        matriz[v][u] = 0;
    }

    public boolean existeArista(int u, int v) {
        validar(u);
        validar(v);
        return matriz[u][v] == 1;
    }

    public void imprimirMatriz() {
        System.out.print("    ");
        for (String e : etiquetas) System.out.print(e + " ");
        System.out.println();
        for (int i = 0; i < n; i++) {
            System.out.print(etiquetas[i] + " | ");
            for (int j = 0; j < n; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    // DFS (recursivo)

    public void dfs(int inicio) {
        validar(inicio);
        boolean[] visitado = new boolean[n];
        System.out.print("DFS recursivo: ");
        dfsRec(inicio, visitado);
        System.out.println();
    }

    private void dfsRec(int u, boolean[] visitado) {
        visitado[u] = true;
        System.out.print(etiquetas[u] + " ");
        for (int v = 0; v < n; v++) {
            if (matriz[u][v] == 1 && !visitado[v]) {
                dfsRec(v, visitado);
            }
        }
    }

    //DFS (iterativo con pila) 

    public void dfsIterativo(int inicio) {
        validar(inicio);
        boolean[] visitado = new boolean[n];
        Stack<Integer> pila = new Stack<>();
        pila.push(inicio);
        System.out.print("DFS iterativo: ");
        while (!pila.isEmpty()) {
            int u = pila.pop();
            if (visitado[u]) continue;
            visitado[u] = true;
            System.out.print(etiquetas[u] + " ");
            // Se recorre al revés para visitar en el mismo orden que el recursivo
            for (int v = n - 1; v >= 0; v--) {
                if (matriz[u][v] == 1 && !visitado[v]) {
                    pila.push(v);
                }
            }
        }
        System.out.println();
    }

    //BFS (con cola)

    public void bfs(int inicio) {
        validar(inicio);
        boolean[] visitado = new boolean[n];
        Queue<Integer> cola = new LinkedList<>();
        visitado[inicio] = true;
        cola.add(inicio);
        System.out.print("BFS: ");
        while (!cola.isEmpty()) {
            int u = cola.poll();
            System.out.print(etiquetas[u] + " ");
            for (int v = 0; v < n; v++) {
                if (matriz[u][v] == 1 && !visitado[v]) {
                    visitado[v] = true;
                    cola.add(v);
                }
            }
        }
        System.out.println();
    }

    // Prueba 

    public static void main(String[] args) {
        // Vértices: 0=A, 1=B, 2=C, 3=D, 4=E, 5=F
        String[] nombres = {"A", "B", "C", "D", "E", "F"};
        GrafoMatriz g = new GrafoMatriz(nombres);

        g.agregarArista(0, 1); // A-B
        g.agregarArista(0, 2); // A-C
        g.agregarArista(1, 3); // B-D
        g.agregarArista(1, 4); // B-E
        g.agregarArista(2, 4); // C-E
        g.agregarArista(3, 5); // D-F
        g.agregarArista(4, 5); // E-F

        System.out.println("Matriz de adyacencia:");
        g.imprimirMatriz();
        System.out.println();

        System.out.println("Recorridos desde A:");
        g.dfs(0);
        g.dfsIterativo(0);
        g.bfs(0);
    }
}