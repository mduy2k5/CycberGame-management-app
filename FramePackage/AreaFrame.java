package FramePackage;

import DatabaseFunction.Area;
import DatabaseFunction.AreaType;
import DatabaseFunction.Computer;
import DatabaseFunction.Promotion;
import TempClass.AreaClass;
import TempClass.AreaTypeClass;
import TempClass.ComputerClass;
import TempClass.PromotionClass;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent; // Import DefaultCellEditor
import java.util.ArrayList; // Import BorderFactory
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel; // Import Color class
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AreaFrame {
    // Tạo trang quản lý khu vực
    public static JPanel createAreaPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
    
        // Table to display KHU_VUC records
        String[] columnNames = {"Mã KV", "Loại KV", "Tên KV", "Số Tầng", "Số Lượng Máy KV", "Trạng Thái", "Actions"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable areaTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only the "Actions" column is editable
            }
        };
    

        JScrollPane scrollPane = new JScrollPane(areaTable);
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
            ArrayList<AreaClass> areas = Area.SelectArea();
            for (AreaClass area : areas) {
                tableModel.addRow(new Object[]{
                        area.getMaKV(),
                        area.getMaLKV(),
                        area.getTenKV(),
                        area.getSoTang(),
                        area.getSoLuongMayKV(),
                        area.getTrangThai(),
                        "Details"
                });
            }
        };
        areaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = areaTable.getSelectedRow();
                int selectedColumn = areaTable.getSelectedColumn();
                if (selectedRow != -1) {   
                    if (selectedColumn == 6) { 
                        String maKV = tableModel.getValueAt(selectedRow, 0).toString();
                        System.out.println(maKV);
                        showAreaDetail(maKV); 
                    }
                }
            }
        });
        // Initial load of table data
        reloadTable.run();
    
        // Insert button action
        insertButton.addActionListener(e -> openInsertAreaDialog(reloadTable));
    
        // Update button action
        updateButton.addActionListener(e -> openUpdateAreaDialog(reloadTable));
    
        // Delete button action
        deleteButton.addActionListener(e -> openDeleteAreaDialog(reloadTable));
    
        return panel;
    }

    private static void openInsertAreaDialog(Runnable reloadTable) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Insert Area");
        dialog.setSize(400, 400);
        dialog.setLayout(new BorderLayout());
    
        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField maKVField = new JTextField();
        JComboBox<String> maLKVComboBox = new JComboBox<>();
        JTextField tenKVField = new JTextField();
        JTextField soTangField = new JTextField();
        JTextField soLuongMayKVField = new JTextField();
        JTextField trangThaiField = new JTextField();

        inputPanel.add(new JLabel("Loại KV:"));
        inputPanel.add(maLKVComboBox);
        inputPanel.add(new JLabel("Tên KV:"));
        inputPanel.add(tenKVField);
        inputPanel.add(new JLabel("Số Tầng:"));
        inputPanel.add(soTangField);
        inputPanel.add(new JLabel("Số Lượng Máy KV:"));
        inputPanel.add(soLuongMayKVField);
        inputPanel.add(new JLabel("Trạng Thái:"));
        inputPanel.add(trangThaiField);
    
        dialog.add(inputPanel, BorderLayout.CENTER);
    
        // Load LOAI_KHU_VUC names into the combo box
        ArrayList<AreaTypeClass> areaTypes = AreaType.SelectAType();
        for (AreaTypeClass areaType : areaTypes) {
            maLKVComboBox.addItem(areaType.getMaLKV() + " - " + areaType.getTenLoai());
        }
    
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton actionButton = new JButton("Insert");
        JButton searchButton = new JButton("Search");
    
        buttonPanel.add(actionButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
    
    
        // Search button action
        searchButton.addActionListener(e -> {
            String searchMaKV = maKVField.getText();
            AreaClass area = Area.SelectAreaById(searchMaKV);
            if (area != null) {
                maKVField.setText(area.getMaKV());
                for (int i = 0; i < maLKVComboBox.getItemCount(); i++) {
                    if (maLKVComboBox.getItemAt(i).startsWith(area.getMaLKV())) {
                        maLKVComboBox.setSelectedIndex(i);
                        break;
                    }
                }
                tenKVField.setText(area.getTenKV());
                soTangField.setText(String.valueOf(area.getSoTang()));
                soLuongMayKVField.setText(String.valueOf(area.getSoLuongMayKV()));
                trangThaiField.setText(area.getTrangThai());
            } else {
                JOptionPane.showMessageDialog(dialog, "Area not found!");
            }
        });
    
        // Action button action
        actionButton.addActionListener(e -> {
            String maLKV = maLKVComboBox.getSelectedItem().toString().split(" - ")[0];
            String tenKV = tenKVField.getText();
            int soTang = Integer.parseInt(soTangField.getText());
            int soLuongMayKV = Integer.parseInt(soLuongMayKVField.getText());
            String trangThai = trangThaiField.getText();
    
            Area.AddArea(maLKV, tenKV, trangThai, soTang, soLuongMayKV);
            JOptionPane.showMessageDialog(dialog, "Area added successfully!");

            reloadTable.run(); // Reload table data
            dialog.dispose(); // Close the dialog
        });
    
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    private static void openUpdateAreaDialog(Runnable reloadTable) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Update Area");
        dialog.setSize(800, 600);
        dialog.setLayout(new BorderLayout());
    
        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        JTextField maKVField = new JTextField();
        JComboBox<String> maLKVComboBox = new JComboBox<>();
        JTextField tenKVField = new JTextField();
        JTextField soTangField = new JTextField();
        JTextField soLuongMayKVField = new JTextField();
        JTextField trangThaiField = new JTextField();
    
        inputPanel.add(new JLabel("Mã KV:"));
        inputPanel.add(maKVField);
        inputPanel.add(new JLabel("Loại KV:"));
        inputPanel.add(maLKVComboBox);
        inputPanel.add(new JLabel("Tên KV:"));
        inputPanel.add(tenKVField);
        inputPanel.add(new JLabel("Số Tầng:"));
        inputPanel.add(soTangField);
        inputPanel.add(new JLabel("Số Lượng Máy KV:"));
        inputPanel.add(soLuongMayKVField);
        inputPanel.add(new JLabel("Trạng Thái:"));
        inputPanel.add(trangThaiField);
    
        dialog.add(inputPanel, BorderLayout.NORTH);
    
        // Load LOAI_KHU_VUC names into the combo box
        ArrayList<AreaTypeClass> areaTypes = AreaType.SelectAType();
        for (AreaTypeClass areaType : areaTypes) {
            maLKVComboBox.addItem(areaType.getMaLKV() + " - " + areaType.getTenLoai());
        }
    
        // Table to display KHU_VUC records
        String[] columnNames = {"Mã KV", "Loại KV", "Tên KV", "Số Tầng", "Số Lượng Máy KV", "Trạng Thái"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable areaTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
    
        JScrollPane scrollPane = new JScrollPane(areaTable);
        dialog.add(scrollPane, BorderLayout.CENTER);
    
        // Load table data
        ArrayList<AreaClass> areas = Area.SelectArea();
        for (AreaClass area : areas) {
            tableModel.addRow(new Object[]{
                    area.getMaKV(),
                    area.getMaLKV(),
                    area.getTenKV(),
                    area.getSoTang(),
                    area.getSoLuongMayKV(),
                    area.getTrangThai()
            });
        }
    
        // Add MouseListener to populate input fields when a row is clicked
        areaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = areaTable.getSelectedRow();
                if (selectedRow != -1) {
                    maKVField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    String maLKV = tableModel.getValueAt(selectedRow, 1).toString();
                    for (int i = 0; i < maLKVComboBox.getItemCount(); i++) {
                        if (maLKVComboBox.getItemAt(i).startsWith(maLKV)) {
                            maLKVComboBox.setSelectedIndex(i);
                            break;
                        }
                    }
                    tenKVField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    soTangField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    soLuongMayKVField.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    trangThaiField.setText(tableModel.getValueAt(selectedRow, 5).toString());
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
            String searchMaKV = maKVField.getText();
            AreaClass area = Area.SelectAreaById(searchMaKV);
            if (area != null) {
                maKVField.setText(area.getMaKV());
                for (int i = 0; i < maLKVComboBox.getItemCount(); i++) {
                    if (maLKVComboBox.getItemAt(i).startsWith(area.getMaLKV())) {
                        maLKVComboBox.setSelectedIndex(i);
                        break;
                    }
                }
                tenKVField.setText(area.getTenKV());
                soTangField.setText(String.valueOf(area.getSoTang()));
                soLuongMayKVField.setText(String.valueOf(area.getSoLuongMayKV()));
                trangThaiField.setText(area.getTrangThai());
            } else {
                JOptionPane.showMessageDialog(dialog, "Area not found!");
            }
        });
    
        // Update button action
        updateButton.addActionListener(e -> {
            String maKV = maKVField.getText();
            String maLKV = maLKVComboBox.getSelectedItem().toString().split(" - ")[0];
            String tenKV = tenKVField.getText();
            int soTang = Integer.parseInt(soTangField.getText());
            int soLuongMayKV = Integer.parseInt(soLuongMayKVField.getText());
            String trangThai = trangThaiField.getText();
    
            Area.UpdateArea(maKV, maLKV, tenKV, trangThai, soTang, soLuongMayKV);
            JOptionPane.showMessageDialog(dialog, "Area updated successfully!");
            reloadTable.run(); // Reload table data
            dialog.dispose(); // Close the dialog
        });
    
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    private static void openDeleteAreaDialog(Runnable reloadTable) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Delete Area");
        dialog.setSize(800, 600);
        dialog.setLayout(new BorderLayout());
    
        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        JTextField maKVField = new JTextField();
        JComboBox<String> maLKVComboBox = new JComboBox<>();
        JTextField tenKVField = new JTextField();
        JTextField soTangField = new JTextField();
        JTextField soLuongMayKVField = new JTextField();
        JTextField trangThaiField = new JTextField();
    
        inputPanel.add(new JLabel("Mã KV:"));
        inputPanel.add(maKVField);
        inputPanel.add(new JLabel("Loại KV:"));
        inputPanel.add(maLKVComboBox);
        inputPanel.add(new JLabel("Tên KV:"));
        inputPanel.add(tenKVField);
        inputPanel.add(new JLabel("Số Tầng:"));
        inputPanel.add(soTangField);
        inputPanel.add(new JLabel("Số Lượng Máy KV:"));
        inputPanel.add(soLuongMayKVField);
        inputPanel.add(new JLabel("Trạng Thái:"));
        inputPanel.add(trangThaiField);
    
        dialog.add(inputPanel, BorderLayout.NORTH);
    
        // Load LOAI_KHU_VUC names into the combo box
        ArrayList<AreaTypeClass> areaTypes = AreaType.SelectAType();
        for (AreaTypeClass areaType : areaTypes) {
            maLKVComboBox.addItem(areaType.getMaLKV() + " - " + areaType.getTenLoai());
        }
    
        // Table to display KHU_VUC records
        String[] columnNames = {"Mã KV", "Loại KV", "Tên KV", "Số Tầng", "Số Lượng Máy KV", "Trạng Thái"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable areaTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
    
        JScrollPane scrollPane = new JScrollPane(areaTable);
        dialog.add(scrollPane, BorderLayout.CENTER);
    
        // Load table data
        ArrayList<AreaClass> areas = Area.SelectArea();
        for (AreaClass area : areas) {
            tableModel.addRow(new Object[]{
                    area.getMaKV(),
                    area.getMaLKV(),
                    area.getTenKV(),
                    area.getSoTang(),
                    area.getSoLuongMayKV(),
                    area.getTrangThai()
            });
        }
    
        // Add MouseListener to populate input fields when a row is clicked
        areaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = areaTable.getSelectedRow();
                if (selectedRow != -1) {
                    maKVField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    String maLKV = tableModel.getValueAt(selectedRow, 1).toString();
                    for (int i = 0; i < maLKVComboBox.getItemCount(); i++) {
                        if (maLKVComboBox.getItemAt(i).startsWith(maLKV)) {
                            maLKVComboBox.setSelectedIndex(i);
                            break;
                        }
                    }
                    tenKVField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    soTangField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    soLuongMayKVField.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    trangThaiField.setText(tableModel.getValueAt(selectedRow, 5).toString());
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
            String searchMaKV = maKVField.getText();
            AreaClass area = Area.SelectAreaById(searchMaKV);
            if (area != null) {
                maKVField.setText(area.getMaKV());
                for (int i = 0; i < maLKVComboBox.getItemCount(); i++) {
                    if (maLKVComboBox.getItemAt(i).startsWith(area.getMaLKV())) {
                        maLKVComboBox.setSelectedIndex(i);
                        break;
                    }
                }
                tenKVField.setText(area.getTenKV());
                soTangField.setText(String.valueOf(area.getSoTang()));
                soLuongMayKVField.setText(String.valueOf(area.getSoLuongMayKV()));
                trangThaiField.setText(area.getTrangThai());
            } else {
                JOptionPane.showMessageDialog(dialog, "Area not found!");
            }
        });
    
        // Delete button action
        deleteButton.addActionListener(e -> {
            String maKV = maKVField.getText();
    
            int confirm = JOptionPane.showConfirmDialog(dialog, "Are you sure you want to delete this area?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Area.DeleteArea(maKV);
                JOptionPane.showMessageDialog(dialog, "Area deleted successfully!");
                reloadTable.run(); // Reload table data
                dialog.dispose(); // Close the dialog
            }
        });
    
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    // Tạo trang quản lý loại khu vực
    public static JPanel createAreaTypePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Top panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField maLKVField = new JTextField();
        JTextField tenLoaiField = new JTextField();
        JTextField giaTienField = new JTextField();
        JTextField soLuongMayField = new JTextField();

        inputPanel.add(new JLabel("Mã Loại KV:"));
        inputPanel.add(maLKVField);
        inputPanel.add(new JLabel("Tên Loại KV:"));
        inputPanel.add(tenLoaiField);
        inputPanel.add(new JLabel("Giá Tiền:"));
        inputPanel.add(giaTienField);
        inputPanel.add(new JLabel("Số Lượng Máy:"));
        inputPanel.add(soLuongMayField);

        panel.add(inputPanel, BorderLayout.NORTH);

        // Table to display LOAI_KHU_VUC records
        String[] columnNames = {"Mã Loại KV", "Tên Loại KV", "Giá Tiền", "Số Lượng Máy"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable areaTypeTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };

        JScrollPane scrollPane = new JScrollPane(areaTypeTable);
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
            ArrayList<AreaTypeClass> areaTypes = AreaType.SelectAType();
            for (AreaTypeClass areaType : areaTypes) {
                tableModel.addRow(new Object[]{
                        areaType.getMaLKV(),
                        areaType.getTenLoai(),
                        areaType.getGiaTien(),
                        areaType.getSoLuongMay()
                });
            }
        };

        // Initial load of table data
        reloadTable.run();

        // Add MouseListener to populate input fields when a row is clicked
        areaTypeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = areaTypeTable.getSelectedRow();
                if (selectedRow != -1) {
                    maLKVField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    tenLoaiField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    giaTienField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    soLuongMayField.setText(tableModel.getValueAt(selectedRow, 3).toString());
                }
            }
        });

        // Insert button action
        insertButton.addActionListener(e -> {
            String tenLoai = tenLoaiField.getText();
            double giaTien = Double.parseDouble(giaTienField.getText());
            int soLuongMay = Integer.parseInt(soLuongMayField.getText());

            AreaType.AddAType(tenLoai, giaTien, soLuongMay);
            reloadTable.run(); // Reload table data
            JOptionPane.showMessageDialog(panel, "Area type added successfully!");
        });

        // Update button action
        updateButton.addActionListener(e -> {
            String maLKV = maLKVField.getText();
            String tenLoai = tenLoaiField.getText();
            double giaTien = Double.parseDouble(giaTienField.getText());
            int soLuongMay = Integer.parseInt(soLuongMayField.getText());

            AreaType.UpdateAType(maLKV, tenLoai, giaTien, soLuongMay);
            reloadTable.run(); // Reload table data
            JOptionPane.showMessageDialog(panel, "Area type updated successfully!");
        });

        // Delete button action
        deleteButton.addActionListener(e -> {
            String maLKV = maLKVField.getText();

            AreaType.DeleteAType(maLKV);
            reloadTable.run(); // Reload table data
            JOptionPane.showMessageDialog(panel, "Area type deleted successfully!");
        });

        return panel;
    }

    public static void showAreaDetail(String maKV) {
        // Create a dialog to display area details
        JDialog dialog = new JDialog();
        dialog.setTitle("Area Details");
        dialog.setSize(800, 700);
        dialog.setLayout(new BorderLayout());

        // Fetch area details
        AreaClass area = Area.SelectAreaById(maKV);
        AreaTypeClass areaType = AreaType.SelectAreaTypeById(area.getMaLKV());
        ArrayList<ComputerClass> computers = Computer.SelectComputerByArea(maKV);
        ArrayList<PromotionClass> promotions = Promotion.SelectPromotionsByArea(maKV);

        // Count online and offline computers
        int onlineCount = 0;
        int offlineCount = 0;
        for (ComputerClass computer : computers) {
            if ("Online".equalsIgnoreCase(computer.getTrangThai())) {
                onlineCount++;
            } else {
                offlineCount++;
            }
        }

        // Top panel for area and area type details
        JPanel topPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        topPanel.add(new JLabel("Mã KV:"));
        topPanel.add(new JLabel(area.getMaKV()));
        topPanel.add(new JLabel("Tên KV:"));
        topPanel.add(new JLabel(area.getTenKV()));
        topPanel.add(new JLabel("Loại KV:"));
        topPanel.add(new JLabel(areaType.getTenLoai()));
        topPanel.add(new JLabel("Giá Tiền:"));
        topPanel.add(new JLabel(String.valueOf(areaType.getGiaTien())));
        topPanel.add(new JLabel("Số Máy KV:"));
        topPanel.add(new JLabel(String.valueOf(areaType.getSoLuongMay())));
        topPanel.add(new JLabel("Trạng Thái:"));
        topPanel.add(new JLabel(area.getTrangThai()));

        dialog.add(topPanel, BorderLayout.NORTH);

        // Middle panel for computer statistics
        JPanel middlePanel = new JPanel(new GridLayout(3, 2, 5, 5));
        middlePanel.add(new JLabel("Tổng số máy:"));
        middlePanel.add(new JLabel(String.valueOf(computers.size())));
        middlePanel.add(new JLabel("Số máy Online:"));
        middlePanel.add(new JLabel(String.valueOf(onlineCount)));
        middlePanel.add(new JLabel("Số máy Offline:"));
        middlePanel.add(new JLabel(String.valueOf(offlineCount)));

        dialog.add(middlePanel, BorderLayout.CENTER);

        // Bottom panel for promotions table
        JPanel bottomPanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Mã CTR", "Tên CTR", "Ngày BĐ", "Ngày KT","Chiết Khấu"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable promotionTable = new JTable(tableModel);

        for (PromotionClass promotion : promotions) {
            tableModel.addRow(new Object[]{
                    promotion.getMaCTR(),
                    promotion.getTenCTR(),
                    promotion.getNgayBD(),
                    promotion.getNgayKT(),
                    promotion.getChietKhau()
            });
        }

        JScrollPane scrollPane = new JScrollPane(promotionTable);
        bottomPanel.add(new JLabel("Promotions:"), BorderLayout.NORTH);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        dialog.add(bottomPanel, BorderLayout.SOUTH);

        // Add a new panel for the computer table
        JPanel computerPanel = new JPanel(new BorderLayout());
        String[] computerColumnNames = {"Mã PC", "RAM", "ROM", "CPU", "VGA", "Số Máy", "Trạng Thái"};
        DefaultTableModel computerTableModel = new DefaultTableModel(computerColumnNames, 0);
        JTable computerTable = new JTable(computerTableModel);

        for (ComputerClass computer : computers) {
            computerTableModel.addRow(new Object[]{
                    computer.getMaKv(),
                    computer.getRam(),
                    computer.getRom(),
                    computer.getCpu(),
                    computer.getVga(),
                    computer.getSoMay(),
                    computer.getTrangThai()
            });
        }

        JScrollPane computerScrollPane = new JScrollPane(computerTable);
        computerPanel.add(new JLabel("Computers in this Area:"), BorderLayout.NORTH);
        computerPanel.add(computerScrollPane, BorderLayout.CENTER);

        // Add the computer panel below the promotions table
        dialog.add(computerPanel, BorderLayout.EAST);


        // Show the dialog
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    
    
    }


}
