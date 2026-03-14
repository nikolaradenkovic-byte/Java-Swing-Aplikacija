package server;

import matrica.Matrica;
import java.io.*;
import java.net.Socket;

public class ServerThread extends  Thread {

    private final Socket socket;

    ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            Matrica matrica = (Matrica) in.readObject();
            double[] result = izracunaj(matrica.m);

            out.writeObject(result);
            out.flush();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {

                socket.close();
                System.out.println("Connection with " + socket.getRemoteSocketAddress() + " is closed.");

            } catch (IOException e) {

                throw new RuntimeException(e);

            }

        }

    }

    private double[] izracunaj(double[][] a) {

        int n = 3;

        for (int i = 0; i < n; i++) {

            for (int j = i + 1; j < n; j++) {

                double factor = a[j][i] / a[i][i];

                for (int k = i; k < 4; k++) {

                    a[j][k] -= factor * a[i][k];

                }

            }

        }

        double[] x = new double[3];

        for (int i = n - 1; i >= 0; i--) {

            x[i] = a[i][3];

            for (int j = i + 1; j < n; j++) {

                x[i] -= a[i][j] * x[j];

            }

            x[i] /= a[i][i];

        }

        return x;
    }

}
