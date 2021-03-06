package notification;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dataabstractions.Customer;
import dbutil.CustomerDAO;

/**
 * @author Mugen on 5/16/15.
 *
 * Modified from http://crunchify.com/java-mailapi-example-send-an-email-via-gmail-smtp/
 */
public class AutoEmail {

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;
    private static String selectedRow;

    public static void generateAndSendEmail() throws MessagingException {
        Customer c = new Customer();
        try {
            c = CustomerDAO.getSingleData(Integer.parseInt(selectedRow));
        } catch (Exception e) {
            e.printStackTrace();
        }

//Step1 -- Sends message via TLS
        System.out.println("\n 1st ===> setup Mail Server Properties..");
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        System.out.println("Mail Server Properties have been setup successfully..");

//Step2 -- Create Session and message (Recipients, subject, text)
        System.out.println("\n\n 2nd ===> get Mail Session..");
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(c.getEmail()));

        // @TODO need to check what the email message should be -- May need to include the invoice as an attachment
        generateMailMessage.setSubject("Your repair is ready for pickup");
        String emailBody = "Test email by JavaMail API example. " + "<br><br> Regards, <br>David";
        generateMailMessage.setContent(emailBody, "text/html");
        System.out.println("Mail Session has been created successfully..");

//Step3 -- Use Session var to get transport protocol and send message via the smtp server
        System.out.println("\n\n 3rd ===> Get Session and Send mail");
        Transport transport = getMailSession.getTransport("smtp");
        // @TODO need to figure out how we can input this and not save it as plain text
        transport.connect("smtp.gmail.com", "shohei741@gmail.com", "xxxx");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }

    public static void setSelectedRow(String row) {
        selectedRow = row;
    }

}
