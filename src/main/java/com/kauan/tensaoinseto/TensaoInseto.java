package com.kauan.tensaoinseto;

import java.awt.*;
import javax.swing.*;

public class TensaoInseto {

    public static void main(String[] args) {
        // Criar a janela do aplicativo:
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Calculadora de tensao - Inseto");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);

            // GridBagLayout para controle do layout
            frame.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(5, 10, 5, 10); // Espaçamento entre os componentes

            // Campos de entrada:
            JTextField anguloField = new JTextField(10);
            JTextField pernasField = new JTextField("6", 10); // valor padrão
            JTextField massaField = new JTextField(10); // massa em gramas
            JTextField gravidadeField = new JTextField("9.8", 10); // valor padrão
            JButton calcularButton = new JButton("Calcular");
            JTextArea resultadoArea = new JTextArea(5, 30);
            resultadoArea.setEditable(false);

            // Adicionando rótulos e campos ao layout:
            gbc.gridx = 0;
            gbc.gridy = 0;
            frame.add(new JLabel("Ângulo (em graus):"), gbc);

            gbc.gridx = 1;
            frame.add(anguloField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            frame.add(new JLabel("Número de pernas:"), gbc);

            gbc.gridx = 1;
            frame.add(pernasField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            frame.add(new JLabel("Peso do inseto (g) [opcional]:"), gbc);

            gbc.gridx = 1;
            frame.add(massaField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            frame.add(new JLabel("Gravidade (m/s²) [padrão 9.8]:"), gbc);

            gbc.gridx = 1;
            frame.add(gravidadeField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            frame.add(calcularButton, gbc);

            // Botão de limpar
            JButton limparButton = new JButton("Limpar");
            gbc.gridx = 1;
            frame.add(limparButton, gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 2;
            frame.add(new JScrollPane(resultadoArea), gbc);

            frame.setVisible(true);

            // Ação do botão Calcular
            calcularButton.addActionListener(e -> {
                try {
                    double angulo = Double.parseDouble(anguloField.getText());
                    int pernas = Integer.parseInt(pernasField.getText());

                    // Verifica massa (em gramas)
                    double massaGramas = massaField.getText().isEmpty() ? -1 : Double.parseDouble(massaField.getText());

                    double gravidade = gravidadeField.getText().isEmpty() ? 9.8 : Double.parseDouble(gravidadeField.getText());

                    if (angulo <= 0 || angulo >= 90) {
                        JOptionPane.showMessageDialog(frame, "O ângulo deve estar entre 0 e 90 graus.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (pernas <= 0) {
                        JOptionPane.showMessageDialog(frame, "Número de pernas deve ser maior que zero.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (massaGramas > 0 && (massaGramas < 0.1 || massaGramas > 80)) {
                        JOptionPane.showMessageDialog(frame, "O peso do inseto deve estar entre 0.1 g e 80 g.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    double anguloRad = Math.toRadians(angulo);
                    double sinTheta = Math.sin(anguloRad);
                    double razao = 1 / (pernas * sinTheta);

                    StringBuilder resultado = new StringBuilder();
                    resultado.append("Ângulo: ").append(angulo).append("°\n");
                    resultado.append("Número de pernas: ").append(pernas).append("\n");
                    resultado.append("Razão T/(mg) = 1 / (").append(pernas)
                             .append(" * sin(").append(angulo).append("°)) = ")
                             .append(String.format("%.4f", razao)).append("\n");

                    if (massaGramas > 0) {
                        double massaKg = massaGramas * 0.001;
                        double tensao = (massaKg * gravidade) / (pernas * sinTheta);
                        resultado.append("Tensão real por perna: ").append(String.format("%.4f", tensao)).append(" N\n");
                    }

                    resultadoArea.setText(resultado.toString());

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, insira apenas números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });

            // Ação do botão Limpar
            limparButton.addActionListener(e -> {
                anguloField.setText("");
                pernasField.setText("");
                massaField.setText("");
                gravidadeField.setText("");
                resultadoArea.setText("");
            });
        });
    }
}
