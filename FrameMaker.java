
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class FrameMaker {
    public static Frame LoginFrame(){
        JFrame frame = new JFrame("Đăng nhập");
        JPanel panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 1));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300,500));
        frame.setSize(500, 600);

        JLabel userLabel = new JLabel("Tên đăng nhập:", JLabel.LEFT);
        userLabel.setPreferredSize(new Dimension(100, 20));
        JTextField userField = new JTextField(15);
        JPanel userPanel = new JPanel();
        userPanel.add(userLabel);
        userPanel.add(userField);

        //userField.setPreferredSize(new Dimension(200, 10));
        JLabel passLabel = new JLabel("Mật khẩu:", JLabel.LEFT);
        passLabel.setPreferredSize(new Dimension(100, 20));
        JPasswordField passField = new JPasswordField(15);
        JPanel passPanel = new JPanel();
        passPanel.add(passLabel);
        passPanel.add(passField);
        JButton loginButton = new JButton("Đăng nhập");
        loginButton.setPreferredSize(new Dimension(100, 30));
        panel.add(userPanel);
        panel.add(passPanel);
        panel.add(loginButton);
        JLabel header = new JLabel("",JLabel.CENTER);
        frame.add(header);
        frame.add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                if (OracleJDBC.login(username, password).equals("Success")){
                    System.out.println("Success");
                    MainFrame();
                    frame.dispose();
                }
                else {
                    System.out.println("Fail");
                }
            }
        });
        frame.setVisible(true);
        return frame;
    }
    public static Frame MainFrame(){
        JFrame frame = new JFrame("Main");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(3, 2));

        JLabel userLabel = new JLabel("Đăng nhập thành công:");
        JButton loginButton = new JButton("Logout");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    LoginFrame();
                    frame.dispose();
            }
        });
        frame.add(userLabel);
        frame.add(loginButton);
        frame.setVisible(true);
        return frame;
    }
}
