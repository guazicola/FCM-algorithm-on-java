import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main extends JFrame implements ActionListener {
    JLabel jLabel1,jLabel2;
    JTextField jTextField1,jTextField2;
    JButton jButton;
    JPanel jPanel;

    public static void main(String[] args)
    {
        new Main();
    }

    Main()
    {
        jLabel1=new JLabel("number of clusters(1-10):");
        jLabel2=new JLabel("number of data(1-200):");

        jTextField1=new JTextField(5);
        jTextField2=new JTextField(5);

       jButton=new JButton("确定");
       jButton.setBounds(350,400,80,30);
       jButton.addActionListener(this);

        jPanel=new JPanel();
        jPanel.add(jLabel1);
        jPanel.add(jTextField1);
        jPanel.add(jLabel2);
        jPanel.add(jTextField2);
        jPanel.setBounds(150,300,500,80);


        this.setLayout(null);
        this.add(jPanel);
        this.add(jButton);

        this.setTitle("FCM");
        this.setBounds(500,200,800,700);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==jButton)
        {
            new Coordinate(Integer.parseInt(jTextField1.getText()),Integer.parseInt(jTextField2.getText()));
        }

    }
}
