//Alumnos: Serrano Espinosa Brandon Emerson
//         Espinosa Robles Luis Daniel
//Compilacion y ejecucion: javac HamiltonianCycle.java
//                        : java HamiltonianCycle
class HamiltonianCycle
{
    final int V = 5;
    int path[];
    boolean isSafe(int v, int graph[][], int path[], int pos)
    {
        /* Revisa si este vertice es adyacente o esta a lado del que se acaba de agregar */
        if (graph[path[pos - 1]][v] == 0)
            return false;
 
        /* Revisa si este vertice ya ha sido creado*/
        for (int i = 0; i < pos; i++)
            if (path[i] == v)
                return false;
 
        return true;
    }
 
    /* Una funcion recursiva para solucionar el problema del ciclo Hamiltoniano*/
    boolean hamCycleUtil(int graph[][], int path[], int pos)
    {

        if (pos == V)
        {
            if (graph[path[pos - 1]][path[0]] == 1)
                return true;
            else
                return false;
        }
 
        // Probamos diferentes vertices para el ciclo hamiltoniano
        for (int v = 1; v < V; v++)
        {
            /* Verifica si el vertice lo podemos agregar al ciclo hamiltoniano */
            if (isSafe(v, graph, path, pos))
            {
                path[pos] = v;
                if (hamCycleUtil(graph, path, pos + 1) == true)
                    return true;

                path[pos] = -1;
            }
        }
 
        /* Este return, nos regresa falso cuando no podemos agregarlo al ciclo hamiltoniano */
        return false;
    }
 
    int hamCycle(int graph[][])
    {
        path = new int[V];
        for (int i = 0; i < V; i++)
            path[i] = -1;
 
        path[0] = 0;
        if (hamCycleUtil(graph, path, 1) == false)
        {
            System.out.println("\nSolution does not exist");
            return 0;
        }
 
        printSolution(path);
        return 1;
    }
 
    /* Funcion que imprime la solucion*/
    void printSolution(int path[])
    {
        System.out.println("La solucion existe : Following" +
                           " is one Hamiltonian Cycle");
        for (int i = 0; i < V; i++)
            System.out.print(" " + path[i] + " ");
 
        // Imprime el primer vertice para mostrar los demas completos
        System.out.println(" " + path[0] + " ");
    }
    public static void main(String args[])
    {
        HamiltonianCycle hamiltonian =
                                new HamiltonianCycle();
        /* Creamos un grafo de este tipo
           (0)--(1)--(2)
            |   / \   |
            |  /   \  |
            | /     \ |
           (3)-------(4)    */
        int graph1[][] = {{0, 1, 0, 1, 0},
            {1, 0, 1, 1, 1},
            {0, 1, 0, 0, 1},
            {1, 1, 0, 0, 1},
            {0, 1, 1, 1, 0},
        };
		   // Print the solution
        hamiltonian.hamCycle(graph1); 
        int graph2[][] = {{0, 1, 1, 1, 1, 1},
            {1, 0, 1, 1, 1, 1},
            {1, 1, 0, 1, 1, 1},
            {1, 1, 1, 0, 1, 1},
            {1, 1, 1, 1, 0, 1},
            {1, 1, 1, 1, 1, 0},
        };
        hamiltonian.hamCycle(graph2);
        int graph3[][] = {{0, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 1, 1, 1, 1},
            {1, 1, 0, 1, 1, 1, 1},
            {1, 1, 1, 0, 1, 1, 1},
            {1, 1, 1, 1, 0, 1, 1},
            {1, 1, 1, 1, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 0},

        };
        hamiltonian.hamCycle(graph3); 
        int graph4[][] = {{0, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 1, 1, 1, 1},
            {1, 1, 1, 1, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 0, 1, 1},
            {1, 1, 1, 1, 1, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
        };
        hamiltonian.hamCycle(graph4); */
        int graph5[][] = {{0, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 0, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 0, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 0, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0},
        };
        hamiltonian.hamCycle(graph5); 
 
        // Imprimimos la solucion
        hamiltonian.hamCycle(graph1);
);
    }
}