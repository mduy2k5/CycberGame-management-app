package FramePackage;

import DatabaseFunction.Area;
import DatabaseFunction.Computer;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import tempclass.AreaClass;
import tempclass.ComputerClass;

public class ComputerFrame {
    public static JPanel createComputerPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table to display all computers
        String[] columnNames = {"Mã PC", "RAM", "ROM", "CPU", "VGA", "Số Máy", "Trạng Thái", "Mã KV", "Actions"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable computerTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 8; // Only the "Actions" column is editable
            }
        };


        JScrollPane scrollPane = new JScrollPane(computerTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton insertButton = new JButton("Insert");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        buttonPanel.add(insertButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Helper method to reload table data
        Runnable reloadTable = () -> {
            tableModel.setRowCount(0); // Clear existing rows
            ArrayList<ComputerClass> computers = Computer.SelectComputer();
            for (ComputerClass computer : computers) {
                tableModel.addRow(new Object[]{
                        computer.getMaKv(),
                        computer.getRam(),
                        computer.getRom(),
                        computer.getCpu(),
                        computer.getVga(),
                        computer.getSoMay(),
                        computer.getTrangThai(),
                        computer.getMaKv(),
                        "Details"
                });
            }
        };

        // Initial load of table data
        reloadTable.run();

        // Insert button action
        insertButton.addActionListener(e -> openInsertComputerPanel(reloadTable));

        // Update button action
        updateButton.addActionListener(e -> openUpdateComputerPanel(reloadTable));

        // Delete button action
        deleteButton.addActionListener(e -> openDeleteComputerPanel(reloadTable));

        return panel;
    }

    private static void openInsertComputerPanel(Runnable reloadTable) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Insert Computer");
        dialog.setSize(450, 400);
        dialog.setLayout(new BorderLayout());
    
        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        JTextField ramField = new JTextField();
        JTextField romField = new JTextField();
        JTextField cpuField = new JTextField();
        JTextField vgaField = new JTextField();
        JTextField soMayField = new JTextField();
        JTextField trangThaiField = new JTextField();
        JComboBox<String> maKVComboBox = new JComboBox<>();
    
        // Populate the ComboBox with "MAKV - TENKV" from the database
        ArrayList<AreaClass> areas = Area.SelectArea();
        for (AreaClass area : areas) {
            maKVComboBox.addItem(area.getMaKV() + " - " + area.getTenKV());
        }
    
        inputPanel.add(new JLabel("RAM:"));
        inputPanel.add(ramField);
        inputPanel.add(new JLabel("ROM:"));
        inputPanel.add(romField);
        inputPanel.add(new JLabel("CPU:"));
        inputPanel.add(cpuField);
        inputPanel.add(new JLabel("VGA:"));
        inputPanel.add(vgaField);
        inputPanel.add(new JLabel("Số Máy:"));
        inputPanel.add(soMayField);
        inputPanel.add(new JLabel("Trạng Thái:"));
        inputPanel.add(trangThaiField);
        inputPanel.add(new JLabel("Mã KV:"));
        inputPanel.add(maKVComboBox);
    
        dialog.add(inputPanel, BorderLayout.CENTER);
    
        // Insert button
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(e -> {
            String ram = ramField.getText();
            String rom = romField.getText();
            String cpu = cpuField.getText();
            String vga = vgaField.getText();
            int soMay = Integer.parseInt(soMayField.getText());
            String trangThai = trangThaiField.getText();
            String maKV = maKVComboBox.getSelectedItem().toString().split(" - ")[0]; // Extract MAKV
    
            Computer.AddComputer(ram, rom, cpu, vga, soMay, trangThai, maKV);
            JOptionPane.showMessageDialog(dialog, "Computer added successfully!");
            reloadTable.run(); // Reload table data
            dialog.dispose(); // Close the dialog
        });
    
        dialog.add(insertButton, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private static void openUpdateComputerPanel(Runnable reloadTable) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Update Computer");
        dialog.setSize(800, 600);
        dialog.setLayout(new BorderLayout());
    
        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField mapcField = new JTextField();
        JTextField ramField = new JTextField();
        JTextField romField = new JTextField();
        JTextField cpuField = new JTextField();
        JTextField vgaField = new JTextField();
        JTextField soMayField = new JTextField();
        JTextField trangThaiField = new JTextField();
        JTextField maKVField = new JTextField();
    
        inputPanel.add(new JLabel("Mã PC:"));
        inputPanel.add(mapcField);
        inputPanel.add(new JLabel("RAM:"));
        inputPanel.add(ramField);
        inputPanel.add(new JLabel("ROM:"));
        inputPanel.add(romField);
        inputPanel.add(new JLabel("CPU:"));
        inputPanel.add(cpuField);
        inputPanel.add(new JLabel("VGA:"));
        inputPanel.add(vgaField);
        inputPanel.add(new JLabel("Số Máy:"));
        inputPanel.add(soMayField);
        inputPanel.add(new JLabel("Trạng Thái:"));
        inputPanel.add(trangThaiField);
        inputPanel.add(new JLabel("Mã KV:"));
        inputPanel.add(maKVField);
    
        dialog.add(inputPanel, BorderLayout.NORTH);
    
        // Table to display all computers
        String[] columnNames = {"Mã PC", "RAM", "ROM", "CPU", "VGA", "Số Máy", "Trạng Thái", "Mã KV"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable computerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(computerTable);
        dialog.add(scrollPane, BorderLayout.CENTER);
    
        // Load table data
        ArrayList<ComputerClass> computers = Computer.SelectComputer();
        for (ComputerClass computer : computers) {
            tableModel.addRow(new Object[]{
                    computer.getMaKv(),
                    computer.getRam(),
                    computer.getRom(),
                    computer.getCpu(),
                    computer.getVga(),
                    computer.getSoMay(),
                    computer.getTrangThai(),
                    computer.getMaKv()
            });
        }
    
        // Add MouseListener to populate input fields when a row is clicked
        computerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = computerTable.getSelectedRow();
                if (selectedRow != -1) {
                    mapcField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    ramField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    romField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    cpuField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    vgaField.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    soMayField.setText(tableModel.getValueAt(selectedRow, 5).toString());
                    trangThaiField.setText(tableModel.getValueAt(selectedRow, 6).toString());
                    maKVField.setText(tableModel.getValueAt(selectedRow, 7).toString());
                }
            }
        });
    
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton updateButton = new JButton("Update");
        JButton searchButton = new JButton("Search");
    
        buttonPanel.add(updateButton);
        buttonPanel.add(searchButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
    
        // Search button action
        searchButton.addActionListener(e -> {
            String mapc = mapcField.getText();
            ComputerClass computer = Computer.SelectComputerById(mapc);
            if (computer != null) {
                ramField.setText(computer.getRam());
                romField.setText(computer.getRom());
                cpuField.setText(computer.getCpu());
                vgaField.setText(computer.getVga());
                soMayField.setText(String.valueOf(computer.getSoMay()));
                trangThaiField.setText(computer.getTrangThai());
                maKVField.setText(computer.getMaKv());
            } else {
                JOptionPane.showMessageDialog(dialog, "Computer not found!");
            }
        });
    
        // Update button action
        updateButton.addActionListener(e -> {
            String mapc = mapcField.getText();
            String ram = ramField.getText();
            String rom = romField.getText();
            String cpu = cpuField.getText();
            String vga = vgaField.getText();
            int soMay = Integer.parseInt(soMayField.getText());
            String trangThai = trangThaiField.getText();
            String maKV = maKVField.getText();
    
            Computer.UpdateComputer(mapc, ram, rom, cpu, vga, soMay, trangThai, maKV);
            JOptionPane.showMessageDialog(dialog, "Computer updated successfully!");
            reloadTable.run(); // Reload table data
            dialog.dispose(); // Close the dialog
        });
    
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }


    private static void openDeleteComputerPanel(Runnable reloadTable) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Delete Computer");
        dialog.setSize(800, 600);
        dialog.setLayout(new BorderLayout());

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JTextField mapcField = new JTextField();

        inputPanel.add(new JLabel("Mã PC:"));
        inputPanel.add(mapcField);

        dialog.add(inputPanel, BorderLayout.NORTH);

        // Table to display all computers
        String[] columnNames = {"Mã PC", "RAM", "ROM", "CPU", "VGA", "Số Máy", "Trạng Thái", "Mã KV"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable computerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(computerTable);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Load table data
        ArrayList<ComputerClass> computers = Computer.SelectComputer();
        for (ComputerClass computer : computers) {
            tableModel.addRow(new Object[]{
                    computer.getMaKv(),
                    computer.getRam(),
                    computer.getRom(),
                    computer.getCpu(),
                    computer.getVga(),
                    computer.getSoMay(),
                    computer.getTrangThai(),
                    computer.getMaKv()
            });
        }

        // Add MouseListener to populate input fields when a row is clicked
        computerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = computerTable.getSelectedRow();
                if (selectedRow != -1) {
                    mapcField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                }
            }
        });

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton deleteButton = new JButton("Delete");
        JButton searchButton = new JButton("Search");

        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Search button action
        searchButton.addActionListener(e -> {
            String mapc = mapcField.getText();
            ComputerClass computer = Computer.SelectComputerById(mapc);
            if (computer != null) {
                JOptionPane.showMessageDialog(dialog, "Computer found: " + mapc);
            } else {
                JOptionPane.showMessageDialog(dialog, "Computer not found!");
            }
        });

        // Delete button action
        deleteButton.addActionListener(e -> {
            String mapc = mapcField.getText();

            int confirm = JOptionPane.showConfirmDialog(dialog, "Are you sure you want to delete this computer?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Computer.DeleteComputer(mapc);
                JOptionPane.showMessageDialog(dialog, "Computer deleted successfully!");
                reloadTable.run(); // Reload table data
                dialog.dispose(); // Close the dialog
            }
        });

        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
