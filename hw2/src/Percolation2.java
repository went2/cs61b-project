import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation2 {
    private final boolean[] sites; // sites trace openness, true means open
    private final int N; // number of rows and cols
    private final WeightedQuickUnionUF sitesUnion;
    private final int top; // top node of union
    private final int bottom; // bottom node of union
    private int openNumbs = 0;

    public Percolation2(int N) {
        // create N*N grid with all sites blocked
        if(N <= 0) {
            throw new java.lang.IllegalArgumentException("N should be greater than 0");
        }
        this.N = N;
        sites = new boolean[N * N];
        sitesUnion = new WeightedQuickUnionUF(N * N + 2);
        // 增加额外两个点，作为虚拟的顶部和底部，N * N 表示顶部，N * N + 1 表示底部
        top = N * N;
        bottom = N * N + 1;
    }

    public void open(int row, int col) {
        if(isOpen(row, col)) {
            return;
        }

        int index = rcToIndex(row, col);
        sites[index] = true;
        openNumbs += 1;

        // check if there is need to union
        // first check top and bottom
        if(index < N) { // top site
            sitesUnion.union(index, top);
        } else if (index >= N * (N - 1)) { // bottom site
            sitesUnion.union(index, bottom);
        }
        // then check left sites
        int[][] neighbors = getNeighbors(row, col);
        for (int[] neighbor : neighbors) {
            int r = neighbor[0];
            int c = neighbor[1];
            if (isValidGrid(r, c) && isOpen(r, c)) { // 找到合法且 open 的邻居进行连接
                sitesUnion.union(index, rcToIndex(r, c));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        int i = rcToIndex(row, col);
        return sites[i];
    }

    // return whether gird is connected to top node
    public boolean isFull(int row, int col) {
        int i = rcToIndex(row, col);
        return isOpen(row, col) && sitesUnion.connected(i,top);
    }

    public int numberOfOpenSites() {
        return openNumbs;
    }

    public boolean percolates() {
        return sitesUnion.find(top) == sitesUnion.find(bottom);
    }

    // map grid row and col to index
    private int rcToIndex(int row, int col) {
        if(!isValidIdx(row) || !isValidIdx(col)) {
            throw new java.lang.IndexOutOfBoundsException("Row and Col indices should between [0,N-1]");
        }
        return N * row + col;
    }

    private boolean isValidIdx(int index) {
        return index >= 0 && index < N;
    }
    // return if a grid is in N*N grids
    private boolean isValidGrid(int row, int col) {
        return isValidIdx(row) && isValidIdx(col);
    }

    // get neighbors of specific grid
    private int[][] getNeighbors(int row, int col) {
        return new int[][]{ {row,col-1}, {row,col+1}, {row+1, col}, {row-1, col} };
    }
}
