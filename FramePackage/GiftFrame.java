package FramePackage;

import DatabaseFunction.Gift;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import tempclass.GiftClass;
class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "Details" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private DefaultTableModel tableModel;

    public ButtonEditor(JCheckBox checkBox, DefaultTableModel tableModel) {
        super(checkBox);
        this.tableModel = tableModel;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "Details" : value.toString();
        button.setText(label);
        isPushed = true;

        // Add action to show details
        button.addActionListener(e -> {
            String maQT = tableModel.getValueAt(row, 0).toString();
            JOptionPane.showMessageDialog(null, "Details for Mã QT: " + maQT);
        });

        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            isPushed = false;
        }
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}

public class GiftFrame {
    public static JPanel createGiftPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table to display all gifts
        String[] columnNames = {"Mã QT", "Nội Dung", "Số Điểm Tiêu Hao", "Actions"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable giftTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only the "Actions" column is editable
            }
        };

        // Add a custom renderer and editor for the "Actions" column
        giftTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        giftTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), tableModel));

        JScrollPane scrollPane = new JScrollPane(giftTable);
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
            ArrayList<GiftClass> gifts = Gift.SelectGift();
            for (GiftClass gift : gifts) {
                tableModel.addRow(new Object[]{
                        gift.getMaQT(),
                        gift.getNoiDung(),
                        gift.getSoDiemTieuHao(),
                        "Details"
                });
            }
        };

        // Initial load of table data
        reloadTable.run();

        // Insert button action
        insertButton.addActionListener(e -> openInsertGiftPanel(reloadTable));

        // Update button action
        updateButton.addActionListener(e -> openUpdateGiftPanel(reloadTable));

        // Delete button action
        deleteButton.addActionListener(e -> openDeleteGiftPanel(reloadTable));

        return panel;
    }
    private static void openInsertGiftPanel(Runnable reloadTable) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Insert Gift");
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField noiDungField = new JTextField();
        JTextField soDiemTieuHaoField = new JTextField();

        inputPanel.add(new JLabel("Nội Dung:"));
        inputPanel.add(noiDungField);
        inputPanel.add(new JLabel("Số Điểm Tiêu Hao:"));
        inputPanel.add(soDiemTieuHaoField);

        dialog.add(inputPanel, BorderLayout.CENTER);

        // Insert button
        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(e -> {
            String noiDung = noiDungField.getText();
            int soDiemTieuHao = Integer.parseInt(soDiemTieuHaoField.getText());

            Gift.AddGift(noiDung, soDiemTieuHao);
            JOptionPane.showMessageDialog(dialog, "Gift added successfully!");
            reloadTable.run(); // Reload table data
            dialog.dispose(); // Close the dialog
        });

        dialog.add(insertButton, BorderLayout.SOUTH);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    private static void openUpdateGiftPanel(Runnable reloadTable) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Update Gift");
        dialog.setSize(800, 600);
        dialog.setLayout(new BorderLayout());
    
        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField maQTField = new JTextField();
        JTextField noiDungField = new JTextField();
        JTextField soDiemTieuHaoField = new JTextField();
    
        inputPanel.add(new JLabel("Mã QT:"));
        inputPanel.add(maQTField);
        inputPanel.add(new JLabel("Nội Dung:"));
        inputPanel.add(noiDungField);
        inputPanel.add(new JLabel("Số Điểm Tiêu Hao:"));
        inputPanel.add(soDiemTieuHaoField);
    
        dialog.add(inputPanel, BorderLayout.NORTH);
    
        // Table to display all gifts
        String[] columnNames = {"Mã QT", "Nội Dung", "Số Điểm Tiêu Hao"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable giftTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(giftTable);
        dialog.add(scrollPane, BorderLayout.CENTER);
    
        // Load table data
        ArrayList<GiftClass> gifts = Gift.SelectGift();
        for (GiftClass gift : gifts) {
            tableModel.addRow(new Object[]{
                    gift.getMaQT(),
                    gift.getNoiDung(),
                    gift.getSoDiemTieuHao()
            });
        }
    
        // Add MouseListener to populate input fields when a row is clicked
        giftTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = giftTable.getSelectedRow();
                if (selectedRow != -1) {
                    maQTField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    noiDungField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    soDiemTieuHaoField.setText(tableModel.getValueAt(selectedRow, 2).toString());
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
            String maQT = maQTField.getText();
            GiftClass gift = Gift.SelectGiftById(maQT);
            if (gift != null) {
                noiDungField.setText(gift.getNoiDung());
                soDiemTieuHaoField.setText(String.valueOf(gift.getSoDiemTieuHao()));
            } else {
                JOptionPane.showMessageDialog(dialog, "Gift not found with Mã QT: " + maQT);
            }
        });
    
        // Update button action
        updateButton.addActionListener(e -> {
            String maQT = maQTField.getText();
            String noiDung = noiDungField.getText();
            int soDiemTieuHao = Integer.parseInt(soDiemTieuHaoField.getText());
    
            Gift.UpdateGift(maQT, noiDung, soDiemTieuHao);
            JOptionPane.showMessageDialog(dialog, "Gift updated successfully!");
            reloadTable.run(); // Reload table data
            dialog.dispose(); // Close the dialog
        });
    
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    private static void openDeleteGiftPanel(Runnable reloadTable) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Delete Gift");
        dialog.setSize(800, 600);
        dialog.setLayout(new BorderLayout());
    
        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JTextField maQTField = new JTextField();
    
        inputPanel.add(new JLabel("Mã QT:"));
        inputPanel.add(maQTField);
    
        dialog.add(inputPanel, BorderLayout.NORTH);
    
        // Table to display all gifts
        String[] columnNames = {"Mã QT", "Nội Dung", "Số Điểm Tiêu Hao"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable giftTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(giftTable);
        dialog.add(scrollPane, BorderLayout.CENTER);
    
        // Load table data
        ArrayList<GiftClass> gifts = Gift.SelectGift();
        for (GiftClass gift : gifts) {
            tableModel.addRow(new Object[]{
                    gift.getMaQT(),
                    gift.getNoiDung(),
                    gift.getSoDiemTieuHao()
            });
        }
    
        // Add MouseListener to populate input fields when a row is clicked
        giftTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = giftTable.getSelectedRow();
                if (selectedRow != -1) {
                    maQTField.setText(tableModel.getValueAt(selectedRow, 0).toString());
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
            String maQT = maQTField.getText();
            GiftClass gift = Gift.SelectGiftById(maQT);
            if (gift != null) {
                JOptionPane.showMessageDialog(dialog, "Gift found: " + maQT);
            } else {
                JOptionPane.showMessageDialog(dialog, "Gift not found with Mã QT: " + maQT);
            }
        });
    
        // Delete button action
        deleteButton.addActionListener(e -> {
            String maQT = maQTField.getText();
    
            int confirm = JOptionPane.showConfirmDialog(dialog, "Are you sure you want to delete this gift?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Gift.DeleteGift(maQT);
                JOptionPane.showMessageDialog(dialog, "Gift deleted successfully!");
                reloadTable.run(); // Reload table data
                dialog.dispose(); // Close the dialog
            }
        });
    
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
