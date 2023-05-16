import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.IllegalArgumentException;
import java.lang.IndexOutOfBoundsException;

// Use arr to store status of sites
public class Percolation {
    // 0(000) not open, 4(二进制100) open, 6(110) open&topConnected, 5(101) open&bottomConnected, 7(111)full
    private final byte[] sites;
    private final int N; // number of rows and cols
    private boolean isPercolated = false;
    private final WeightedQuickUnionUF union;
//    private QuickFindUF union;
    private int openedSites = 0;

    public Percolation(int N) {
        // create N*N grid with all sites blocked
        if(N <= 0) {
            throw new IllegalArgumentException("N should be greater than 0");
        }
        this.N = N;
        sites = new byte[N * N];
        union = new WeightedQuickUnionUF(N * N);
    }

    public void open(int row, int col) {
        if(isOpen(row, col)) {
            return;
        }
        // 计算新状态--自身状态
        int index = rcToIndex(row, col);
        sites[index] = 4;
        // 检查顶部或底部节点
        if(index < N) { // top site
            sites[index] = 6;
        } else if (index >= N * (N - 1)) { // bottom site
            sites[index] = 5;
        }

        openedSites += 1;

        // 获取周围节点的 root 节点的状态的合并状态
        byte mergedStatus = 0;
        int[][] neighbors = getNeighbors(row, col);
        for (int[] neighbor : neighbors) {
            int r = neighbor[0];
            int c = neighbor[1];
            if (isValidGrid(r, c) && isOpen(r, c)) {
                int rootIdx = union.find(rcToIndex(r, c));
                mergedStatus = (byte)(mergedStatus | sites[rootIdx]);
                union.union(rcToIndex(r, c), index);
            }
        }
        // 计算最终状态，赋给新的 root 节点
        // 节点的是否连接到 top 或者 bottom 的信息保存在所在集合的根节点上。也可以保存到节点自身
        mergedStatus = (byte)(mergedStatus | sites[index]);
        int newRootIndex = union.find(index);
        sites[newRootIndex] = (byte)(sites[newRootIndex] | mergedStatus);

        if(sites[newRootIndex] == 7) {
            isPercolated = true;
        }
    }

    public boolean isOpen(int row, int col) {
        int i = rcToIndex(row, col);
        return sites[i] != 0;
    }

    public boolean isFull(int row, int col) {
        int i = rcToIndex(row, col);
        int root = union.find(i);
        return sites[root] == 6 || sites[root] == 7;
    }

    public int numberOfOpenSites() {
        return openedSites;
    }

    public boolean percolates() {
        return isPercolated;
    }

    // map grid row and col to index
    private int rcToIndex(int row, int col) {
        if(!isValidIdx(row) || !isValidIdx(col)) {
            throw new IndexOutOfBoundsException("Row and Col indices should between [0,N-1]");
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

    // get neighbors of target grid
    private int[][] getNeighbors(int row, int col) {
        return new int[][]{ {row,col-1}, {row,col+1}, {row+1, col}, {row-1, col} };
    }
}
