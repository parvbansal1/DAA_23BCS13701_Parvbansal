// File: src/main/java/com/example/dp/MainUI.java
package com.example.dp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;

public class MainUI extends JFrame {
    private final JLabel statusLabel = new JLabel("Ready");
    private final JProgressBar progressBar = new JProgressBar();

    public MainUI() {
        super("DP Visualizer — Classic Challenges");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(headerPanel(), BorderLayout.NORTH);
        add(centerPanel(), BorderLayout.CENTER);
        add(footerPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel headerPanel() {
        JLabel title = new JLabel("Classic Challenges — Dynamic Programming", SwingConstants.LEFT);
        title.setFont(new Font("Inter", Font.BOLD, 20));
        title.setForeground(new Color(30, 50, 90));
        title.setBorder(new EmptyBorder(10, 12, 10, 12));

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(240, 245, 250));
        p.add(title, BorderLayout.WEST);

        JLabel sub = new JLabel("Visualize · Compare · Learn", SwingConstants.RIGHT);
        sub.setFont(new Font("SansSerif", Font.ITALIC, 12));
        sub.setBorder(new EmptyBorder(0,0,0,12));
        p.add(sub, BorderLayout.EAST);
        return p;
    }

    private JPanel centerPanel() {
        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Fibonacci", fibonacciTab());
        tabs.addTab("Coin Change", coinChangeTab());
        tabs.addTab("0/1 Knapsack", knapsackTab());
        tabs.addTab("LCS", lcsTab());
        tabs.addTab("Matrix Chain Multiplication", mcmTab()); // NEW

        JPanel p = new JPanel(new BorderLayout());
        p.add(tabs, BorderLayout.CENTER);
        p.setBorder(new EmptyBorder(10, 10, 10, 10));
        return p;
    }

    private JPanel footerPanel() {
        JPanel p = new JPanel(new BorderLayout());
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        statusLabel.setBorder(new EmptyBorder(4, 6, 4, 6));
        p.add(statusLabel, BorderLayout.WEST);
        p.add(progressBar, BorderLayout.CENTER);
        return p;
    }

    // ---------- Tabs ----------
    private JPanel fibonacciTab() {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        JPanel input = new JPanel(new FlowLayout(FlowLayout.LEFT));
        input.add(new JLabel("n:"));
        JTextField nField = new JTextField(6);
        input.add(nField);

        JButton runBtn = new JButton("Compute");
        JTextArea result = new JTextArea(6, 40);
        result.setEditable(false);
        result.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane rs = new JScrollPane(result);

        runBtn.addActionListener(e -> {
            try {
                int n = Integer.parseInt(nField.getText().trim());
                status("Computing Fibonacci...");
                progressBar.setIndeterminate(true);
                long val = DPAlgorithms.fibonacci(n);
                progressBar.setIndeterminate(false);
                progressBar.setValue(100);
                result.setText("Fib(" + n + ") = " + val + "\n\nSequence:\n");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i <= Math.min(n, 30); i++) {
                    sb.append(i).append(": ").append(DPAlgorithms.fibonacci(i)).append("\n");
                }
                result.append(sb.toString());
                status("Done");
            } catch (Exception ex) {
                status("Error: " + ex.getMessage());
                result.setText("Invalid input. Enter a non-negative integer.");
                progressBar.setValue(0);
            }
        });

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(input);
        top.add(runBtn);

        panel.add(top, BorderLayout.NORTH);
        panel.add(rs, BorderLayout.CENTER);
        return panel;
    }

    private JPanel coinChangeTab() {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.WEST;
        form.add(new JLabel("Coins (comma separated):"), c);
        c.gridx = 1;
        JTextField coinsField = new JTextField(20);
        coinsField.setText("1,2,5");
        form.add(coinsField, c);

        c.gridx = 0; c.gridy = 1;
        form.add(new JLabel("Amount:"), c);
        c.gridx = 1;
        JTextField amountField = new JTextField(8);
        amountField.setText("11");
        form.add(amountField, c);

        JButton runBtn = new JButton("Find Minimum Coins");
        JTextArea out = new JTextArea(6, 40);
        out.setEditable(false);
        JScrollPane sp = new JScrollPane(out);

        // DP table area
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        JScrollPane tableScroll = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        runBtn.addActionListener(e -> {
            try {
                int[] coins = Utils.parseArrayFromCsvString(coinsField.getText());
                int amount = Integer.parseInt(amountField.getText().trim());
                status("Computing coin-change DP...");
                int[] dp = DPAlgorithms.coinChangeTable(coins, amount); // full dp array

                out.setText("Coins: " + Arrays.toString(coins) + "\nAmount: " + amount + "\nMin coins: " + (dp[amount] < 0 ? "Impossible" : dp[amount]));

                // prepare table model (1 row: amounts, 1 row: dp values)
                model.setRowCount(0);
                model.setColumnCount(0);
                for (int a = 0; a <= amount; a++) model.addColumn(String.valueOf(a));
                Object[] valRow = new Object[amount + 1];
                for (int a = 0; a <= amount; a++) valRow[a] = dp[a] < 0 ? "∞" : dp[a];
                model.addRow(valRow);

                // animate fill
                animateTableFill(table, model, dp, 0, () -> status("Done"));
            } catch (Exception ex) {
                status("Error: " + ex.getMessage());
            }
        });

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(runBtn, BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScroll, sp);
        split.setResizeWeight(0.6);

        panel.add(top, BorderLayout.NORTH);
        panel.add(split, BorderLayout.CENTER);
        return panel;
    }

    private JPanel knapsackTab() {
        JPanel panel = new JPanel(new BorderLayout(10,10));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.WEST;
        form.add(new JLabel("Weights:"), c);
        c.gridx = 1;
        JTextField weightsField = new JTextField(20);
        weightsField.setText("[2,3,4,5]");
        form.add(weightsField, c);

        c.gridx = 0; c.gridy = 1; form.add(new JLabel("Values:"), c);
        c.gridx = 1;
        JTextField valuesField = new JTextField(20);
        valuesField.setText("[3,4,5,6]");
        form.add(valuesField, c);

        c.gridx = 0; c.gridy = 2; form.add(new JLabel("Capacity:"), c);
        c.gridx = 1;
        JTextField capacityField = new JTextField(6);
        capacityField.setText("5");
        form.add(capacityField, c);

        JButton runBtn = new JButton("Solve Knapsack");
        JTextArea out = new JTextArea(6,40); out.setEditable(false);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        JScrollPane tableScroll = new JScrollPane(table);

        runBtn.addActionListener(e -> {
            try {
                int[] w = Utils.parseIntArrayFromLabel(weightsField.getText(), "weights");
                int[] v = Utils.parseIntArrayFromLabel(valuesField.getText(), "values");
                int cap = Integer.parseInt(capacityField.getText().replaceAll("[^\\d-]", "").trim());
                status("Building DP table...");
                int[][] dp = DPAlgorithms.knapsackDPTable(w, v, cap);
                out.setText("Max Value = " + dp[w.length][cap]); // last cell

                // build table model: rows = items (0..n), cols = 0..cap
                model.setRowCount(0); model.setColumnCount(0);
                for (int j = 0; j <= cap; j++) model.addColumn(String.valueOf(j));
                for (int i = 0; i <= w.length; i++) {
                    Object[] row = new Object[cap+1];
                    for (int j = 0; j <= cap; j++) row[j] = dp[i][j];
                    model.addRow(row);
                }
                animateTableFill(table, model, flatten2D(dp), 0, () -> status("Done"));
            } catch (Exception ex) {
                status("Error: " + ex.getMessage());
            }
        });

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(runBtn, BorderLayout.EAST);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScroll, new JScrollPane(out));
        split.setResizeWeight(0.7);

        panel.add(top, BorderLayout.NORTH);
        panel.add(split, BorderLayout.CENTER);
        return panel;
    }

    private JPanel lcsTab() {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("s1:"));
        JTextField s1 = new JTextField(12); s1.setText("AGGTAB");
        form.add(s1);
        form.add(new JLabel("s2:"));
        JTextField s2 = new JTextField(12); s2.setText("GXTXAYB");
        form.add(s2);

        JButton runBtn = new JButton("Compute LCS");
        JTextArea out = new JTextArea(6,40); out.setEditable(false);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        runBtn.addActionListener(e -> {
            try {
                String a = s1.getText().trim();
                String b = s2.getText().trim();
                status("Computing LCS table...");
                int[][] dp = DPAlgorithms.lcsDPTable(a, b);
                out.setText("LCS length = " + dp[a.length()][b.length()]);
                // build model with header characters
                model.setRowCount(0); model.setColumnCount(0);
                // columns: 0..m
                for (int j = 0; j <= b.length(); j++) model.addColumn(j==0 ? "" : String.valueOf(b.charAt(j-1)));
                for (int i = 0; i <= a.length(); i++) {
                    Object[] row = new Object[b.length()+1];
                    for (int j = 0; j <= b.length(); j++) row[j] = dp[i][j];
                    model.addRow(row);
                }
                animateTableFill(table, model, flatten2D(dp), 0, () -> status("Done"));
            } catch (Exception ex) {
                status("Error: " + ex.getMessage());
            }
        });

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(runBtn, BorderLayout.EAST);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(table), new JScrollPane(out));
        split.setResizeWeight(0.6);

        panel.add(top, BorderLayout.NORTH);
        panel.add(split, BorderLayout.CENTER);
        return panel;
    }

    // ---------- Matrix Chain Multiplication Tab ----------
    private JPanel mcmTab() {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));

        form.add(new JLabel("Enter matrix dimensions (p):"));
        JTextField dimField = new JTextField(28);
        dimField.setText("[30,35,15,5,10,20,25]");
        form.add(dimField);

        JButton runBtn = new JButton("Compute Minimum Cost");
        JTextArea out = new JTextArea(6, 40);
        out.setEditable(false);
        JScrollPane outScroll = new JScrollPane(out);

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        JScrollPane tableScroll = new JScrollPane(table);

        runBtn.addActionListener(e -> {
            try {
                int[] p = Utils.parseArrayFromCsvString(dimField.getText());
                status("Calculating MCM cost table...");
                int[][] dp = DPAlgorithms.matrixChainOrder(p);
                int n = p.length - 1;
                out.setText("Minimum number of multiplications = " + dp[1][n]);

                // Fill JTable: rows/cols 0..n (we'll put '-' in row 0 / col 0)
                model.setRowCount(0);
                model.setColumnCount(0);
                for (int j = 0; j <= n; j++) model.addColumn(String.valueOf(j));
                for (int i = 0; i <= n; i++) {
                    Object[] row = new Object[n + 1];
                    for (int j = 0; j <= n; j++) {
                        if (i == 0 || j == 0) row[j] = "-";
                        else if (dp[i][j] == Integer.MAX_VALUE) row[j] = "∞";
                        else row[j] = dp[i][j];
                    }
                    model.addRow(row);
                }
                animateTableFill(table, model, flatten2D(dp), 0, () -> status("Done"));
            } catch (Exception ex) {
                out.setText("Error: " + ex.getMessage());
                status("Error: " + ex.getMessage());
            }
        });

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(runBtn, BorderLayout.EAST);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableScroll, outScroll);
        split.setResizeWeight(0.7);

        panel.add(top, BorderLayout.NORTH);
        panel.add(split, BorderLayout.CENTER);
        return panel;
    }

    // ---------- Helpers ----------

    private void status(String s) {
        statusLabel.setText(s);
    }

    // animate filling by revealing values one by one with a Timer
    private void animateTableFill(JTable table, DefaultTableModel model, int[] flatValues, int startIndex, Runnable whenDone) {
        progressBar.setValue(0);
        progressBar.setMaximum(flatValues.length);
        if (flatValues.length == 0) {
            progressBar.setValue(0);
            whenDone.run();
            return;
        }

        int delay = Math.max(20, 600 / Math.max(1, flatValues.length)); // speed
        Timer timer = new Timer(delay, null);
        final int[] idx = {startIndex};
        // initially blank out all cells
        for (int r = 0; r < model.getRowCount(); r++) {
            for (int c = 0; c < model.getColumnCount(); c++) {
                model.setValueAt("", r, c);
            }
        }

        timer.addActionListener(evt -> {
            if (idx[0] >= flatValues.length) {
                timer.stop();
                progressBar.setValue(progressBar.getMaximum());
                whenDone.run();
                return;
            }
            int r = idx[0] / model.getColumnCount();
            int c = idx[0] % model.getColumnCount();
            Object val = flatValues[idx[0]];
            model.setValueAt(val, r, c);
            idx[0]++;
            progressBar.setValue(idx[0]);
        });
        timer.start();
    }

    // flatten 2D table row-major into 1D
    private int[] flatten2D(int[][] table) {
        int rows = table.length;
        int cols = rows == 0 ? 0 : table[0].length;
        int[] flat = new int[rows * cols];
        int k = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) flat[k++] = table[i][j];
        }
        return flat;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainUI::new);
    }
}
