package a18_the_honors_question;

public class TicTacToe {
    private int[] rows;
    private int[] cols;
    private int diagonal;
    private int antiDiagonal;

    public TicTacToe(int n) {
        rows = new int[n];
        cols = new int[n];
    }

    public int move(int row, int col, int player) {
        int toAdd = player == 1 ? 1 : -1;

        rows[row] += toAdd;
        cols[col] += toAdd;
        if (row == col)
            diagonal += toAdd;
        if (col == cols.length - row - 1)
            antiDiagonal += toAdd;

        int size = rows.length;
        if (Math.abs(rows[row]) == size || Math.abs(cols[col]) == size || Math.abs(diagonal) == size
                || Math.abs(antiDiagonal) == size) {
            return player;
        }

        return 0;
    }

    public int[] pourWater(int[] H, int V, int K) {
        while (V-- > 0) {
            droplet: {
                for (int d = -1; d <= 1; d++) {
                    int i = K, best = K;
                    while (0 <= i + d && i + d <= H.length && H[i + d] <= H[i]) {
                        if (H[i + d] < H[i])
                            best = i + d;
                        i += d;
                    }
                    if (H[best] < H[K]) {
                        H[best]++;
                        break droplet;
                    }
                }
            }
        }
        return H;
    }
}
