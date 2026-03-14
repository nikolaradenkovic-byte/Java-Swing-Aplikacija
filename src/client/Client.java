package client;

import matrica.Matrica;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.text.DecimalFormat;


public class Client extends JFrame {

    private JTextField[][] fields = new JTextField[3][4];
    private JLabel resultLabel = new JLabel("3 Linearne Jednacine");

    public Client() {

        setTitle("3 Linearne Jednacine");
        setSize(620, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);

        String[] vars = {"x", "y", "z"};

        for (int i = 0; i < 3; i++) {

            int col = 0;

            for (int j = 0; j < 3; j++) {

                fields[i][j] = new JTextField(3);

                gbc.gridx = col++;
                gbc.gridy = i;
                panel.add(fields[i][j], gbc);

                gbc.gridx = col++;
                panel.add(new JLabel(vars[j]), gbc);

                if (j < 2) {
                    gbc.gridx = col++;
                    panel.add(new JLabel("+"), gbc);
                }

            }

            gbc.gridx = col++;
            panel.add(new JLabel("="), gbc);

            fields[i][3] = new JTextField(3);
            gbc.gridx = col;
            panel.add(fields[i][3], gbc);
        }

        JButton solveBtn = new JButton("Izračunaj");
        solveBtn.addActionListener(e -> posaljiMatricu());

        add(resultLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(solveBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

        private void posaljiMatricu() {
            try {

                double[][] matrica = new double[3][4];

                for(int i =0; i <3; i++) {

                    for (int j =0; j<4; j++) {

                        matrica[i][j] = Double.parseDouble(fields[i][j].getText());

                    }

                }

                DecimalFormat df = new DecimalFormat("0.########");
                Socket socket = new Socket("127.0.0.1", 1235);

                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                out.writeObject(new Matrica(matrica));
                out.flush();

                double[] rezultat = (double[]) in.readObject();

                resultLabel.setText("x="+df.format(rezultat[0]) + " y="+df.format(rezultat[1]) + " z="+df.format(rezultat[2]));

                socket.close();

            } catch(Exception e) {
                resultLabel.setText("Greska u unosu / Sistem nema resenje");
            }
        }
    public static void main(String[] args) {
                new Client();
        }
    }

