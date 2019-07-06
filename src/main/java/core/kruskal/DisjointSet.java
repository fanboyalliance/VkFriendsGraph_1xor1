package core.kruskal;

//union–find data structure
public class DisjointSet {
    private int[] set;
    private int[] rnk;

    DisjointSet(int size) {
        set = new int [size];
        rnk = new int [size];
        for (int i = 0; i < size; i++) {
            set[i] = i;
        }
    }

    private int set(int x) {
        return x == set[x] ? x : (set[x] = set(set[x]));
    }

    public boolean union(int u, int v) {
        if ((u = set(u)) == (v = set(v))) {
            return false;
        }
        if (rnk[u] < rnk[v]) {
            set[u] = v;
        } else {
            set[v] = u;
            if (rnk[u] == rnk[v]) {
                rnk[u]++;
            }
        }

        return true;
    }
}
