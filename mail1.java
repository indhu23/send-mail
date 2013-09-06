package mailsending;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import  javax.mail.internet.MimeMultipart;
import  javax.swing.JFileChooser;
import  javax.swing.*;
import  sun.jdbc.odbc.ee.DataSource;

class mail1 extends JFrame{

JLabel from, to, sub, mes, attachment_name;
JTextField t1, t2, t3, t4, t5;
 JTextArea area;
String attachment_path;

    public mail1() {
        setTitle("mailsending");
        setVisible(true);
        setSize(500, 500);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ActionListener send_button = new sendaction();
        ActionListener attach_button = new attachaction();
        JPanel p = new JPanel();
        from = new JLabel("from");
        t1 = new JTextField(10);
        to = new JLabel("to");
        t2 = new JTextField(10);
        sub = new JLabel("subject");
        t3 = new JTextField(10);
        mes = new JLabel("message");
        area = new JTextArea(5, 35);
        JButton j2 = new JButton("attach");
        j2.addActionListener(attach_button);
        t4 = new JTextField(30);
        attachment_name = new JLabel("attachment name");
        t5 = new JTextField(20);
        p.add(from);
        p.add(t1);
        p.add(to);
        p.add(t2);
        p.add(sub);
        p.add(t3);
        p.add(mes);
        p.add(area);
        p.add(j2);
        p.add(t4);
        p.add(attachment_name);
        p.add(t5);
        add(p, BorderLayout.CENTER);
        JPanel  p1 = new JPanel();
        JButton  j1 = new JButton("send");
        p1.add(j1);
        j1.addActionListener(send_button);
        add(p1, BorderLayout.SOUTH);
    }

    private class sendaction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String From = t1.getText();
            String To = t2.getText();
            String Subject = t3.getText();
            String Text = area.getText();
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {

                        protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("xyz@gmail.com", "password");

                        }
                    });
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(From));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(To));
                message.setSubject(Subject);
                MimeBodyPart messagebodypart = new MimeBodyPart();
                messagebodypart.setText(Text);
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messagebodypart);
                messagebodypart = new MimeBodyPart();
                javax.activation.DataSource source = new FileDataSource(attachment_path);
                messagebodypart.setDataHandler(new DataHandler(source));
                messagebodypart.setFileName(t5.getText());
                multipart.addBodyPart(messagebodypart);
                message.setContent(multipart);
                Transport.send(message);
                JOptionPane.showMessageDialog(null, "message sent");
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, e);
            }

        }
    }

    private class attachaction implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            File f = chooser.getSelectedFile();
            attachment_path = f.getAbsolutePath();
            t4.setText(attachment_path);


        }
    }
}

public class mailsending {

    public static void main(String[] args) 
   {
        mail1 m = new mail1();
    }
}
