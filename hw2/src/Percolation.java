import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[] sites; // sites trace openness, true means open
    private final int N; // numbers of sites in a row
    private final WeightedQuickUnionUF sitesUnion;
    private final int top; // top node of union
    private final int bottom; // bottom node of union
    private int openNumbs = 0;

    public Percolation(int N) {
        // create N*N grid with all sites blocked
        if(N <= 0) {
            throw new java.lang.IllegalArgumentException("N should be greater than 0");
        }
        this.N = N;
        sites = new boolean[N * N - 1];
        sitesUnion = new WeightedQuickUnionUF(N * N + 2);
        // 额外两个点，N * N 代表顶部，N * N + 1 代表底部
        top = N * N;
        bottom = N * N + 1;
        for(int i = 0; i < N; i++) { // 标记顶层节点
            sitesUnion.connected(i, top);
        }
        for(int j = (N-1) * N;j < N * N; j++) { // 标记底层节点
            sitesUnion.connected(j, bottom);
        }
    }

    public void open(int row, int col) {
        if(isOpen(row, col)) {
            return;
        }
        int index = rcToIndex(row, col);
        sites[index] = true;
        openNumbs += 1;

        // check if there is need to union
        int[][] neighbors = getNeighbors(row, col);
        for (int[] neighbor : neighbors) {
            int r = neighbor[0];
            int c = neighbor[1];
            if (isValidGrid(r, c) && isOpen(r, c)) { // 找到合法且 open 的邻居进行连接
                sitesUnion.connected(index, rcToIndex(r, c));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        int i = rcToIndex(row, col);
        return sites[i];
    }

    // return gird is connected to top node
    public boolean isFull(int row, int col) {
        int i = rcToIndex(row, col);
        return sitesUnion.connected(i,top);
    }

    public int numberOfOpenSites() {
        return openNumbs;
    }

    public boolean percolates() {
        return sitesUnion.connected(bottom, top);
    }

    // map grid row and col to index
    private int rcToIndex(int row, int col) {
        if(!isValidIdx(row) || !isValidIdx(col)) {
            throw new java.lang.IndexOutOfBoundsException("Row and Col indices should between [0,N-1]");
        }
        return N * row + col;
    }

    private boolean isValidIdx(int index) {
        return index >= 0 && index <= N - 1;
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
