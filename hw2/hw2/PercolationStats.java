package hw2;
import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {
    private double[] x;
    private int len;
    private int numExp;

    private static final double LAMBDA = 1.96;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        x = new double[T];
        len = N;
        numExp = T;
        for (int i = 0; i < numExp; i++) {
            Percolation perc = pf.make(N);
            int[] indices = StdRandom.permutation(N * N);
            x[i] = monteCarloSimulate(perc, indices);
        }
    }

    private double monteCarloSimulate(Percolation perc, int[] indices) {
        int index = 0;
        while (!perc.percolates()) {
            int p = indices[index];
            index++;
            int[] pos = perc.convert1to2(p);
            int row = pos[0], col = pos[1];
            perc.open(row, col);
        }
        return ((double) perc.numberOfOpenSites()) / (len * len);
    }
    public double mean() { // sample mean of percolation threshold
        double res = 0;
        for (int i = 0; i < numExp; i++) {
            res += x[i];
        }
        return res / numExp;
    }
    public double stddev() { // sample standard deviation of percolation threshold
        double sigama = 0;
        double miu = mean();
        for (int i = 0; i < numExp; i++) {
            sigama += Math.pow(x[i] - miu, 2);
        }
        sigama = sigama / numExp;
        return Math.sqrt(sigama);
    }
    public double confidenceLow() { // low endpoint of 95% confidence interval
        double miu = mean();
        double sigama = stddev();
        return miu - (LAMBDA * sigama) / Math.sqrt(numExp);
    }
    public double confidenceHigh() { // high endpoint of 95% confidence interval
        double miu = mean();
        double sigama = stddev();
        return miu + (LAMBDA * sigama) / Math.sqrt(numExp);
    }
}
