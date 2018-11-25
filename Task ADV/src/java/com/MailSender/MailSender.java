/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.MailSender;

import javax.ejb.Stateless;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Properties;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 *
 *
 * @author AHMED 50070
 */

@Stateless
public class MailSender {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void sendEmail(String from, String username, String passord, String to, int message) {

        try {
            Properties prop = System.getProperties();
            
            prop.put("mail.smtp.host", "smtp.gmail.com"); // send to gmail accounts
            
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.port", "465");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.fallback", "false");
            
            
            Session mailSession = Session.getDefaultInstance(prop, null);
            
            mailSession.setDebug(true);
            
            Message mailMsg = new MimeMessage(mailSession);
            
            
            mailMsg.setFrom(new InternetAddress(from));
            mailMsg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mailMsg.setContent(message,"text/html");
            
            
            Transport trans =  mailSession.getTransport("smtp");
            
            trans.connect("smtp.gmail.com", username, passord);
            
            trans.sendMessage(mailMsg,mailMsg.getAllRecipients());
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    
    }
}
