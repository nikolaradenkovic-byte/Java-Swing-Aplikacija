package matrica;

import java.io.Serializable;

public class Matrica implements Serializable {
    public double[][] m = new double[3][4];

    public Matrica(double[][] m) {
        this.m = m;
    }
}
