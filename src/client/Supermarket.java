package client;

import customers.Customer;
import supermarket.StoreAttendant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by sumateja on 01/09/20
 *
 * Supermarket Main page, which starts when customer packs some items to bill
 */
class Login extends JFrame implements ActionListener {
    final JTextField text1, text2;
    JButton SUBMIT;
    JPanel panel;
    JLabel label1, label2;

    Login() {
        label1 = new JLabel();
        label1.setText("Username:");
        text1 = new JTextField(15);

        label2 = new JLabel();
        label2.setText("Password:");
        text2 = new JPasswordField(15);

        SUBMIT = new JButton("SUBMIT");

        panel = new JPanel(new GridLayout(3, 1));
        panel.add(label1);
        panel.add(text1);
        panel.add(label2);
        panel.add(text2);
        panel.add(SUBMIT);
        add(panel, BorderLayout.CENTER);
        SUBMIT.addActionListener(this);
        setTitle("LOGIN FORM");
    }

    public void actionPerformed(ActionEvent ae) {
        String value1 = text1.getText();
        String value2 = text2.getText();
        if (value1.equals("supermarket") && value2.equals("supermarket")) {
            NextPage page = new NextPage();
            page.setVisible(true);
            JLabel label = new JLabel("Welcome:" + value1);
            page.getContentPane().add(label);

            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new GroceryList().setVisible(true);
                }
            });
        } else {
            System.out.println("enter the valid username and password");
            JOptionPane.showMessageDialog(this, "Incorrect login or password",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


class BillingSystem {
    static int counter = 1024;

    public static void main(String[] arg) throws Exception {
        try {
            Login frame = new Login();
            frame.setSize(300, 100);
            frame.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        Customer robert = new Customer();
        robert.setName("Robert");
        robert.setListOfItems("potatoes");
        robert.setQuantity(2);
        robert.setOrderNumber(counter++);

        Customer bill = new Customer();
        bill.setName("Bill");
        bill.setListOfItems("onions");
        bill.setQuantity(3);
        bill.setOrderNumber(counter++);

        DAOImpl impl = new DAOImpl();

        impl.createTable();
        impl.insert(robert.getName(), robert.getListOfItems(), robert.getQuantity(), robert.getPrice(robert.getListOfItems()));
        impl.insert(bill.getName(), bill.getListOfItems(), bill.getQuantity(), bill.getPrice(bill.getListOfItems()));

        System.out.println("----------------------------");
        System.out.println("----------------------------");
        System.out.println("----------------------------");
        impl.get();
        StoreAttendant storeAttendant = new StoreAttendant();
        storeAttendant.takeOrder(robert);
        storeAttendant.takeOrder(bill);
        System.out.println("...");

        // prepare billing
        storeAttendant.prepareBilling("potatoes");
        storeAttendant.prepareBilling("onions");

        // order up...
        storeAttendant.callOutCompletedOrders();
    }
}