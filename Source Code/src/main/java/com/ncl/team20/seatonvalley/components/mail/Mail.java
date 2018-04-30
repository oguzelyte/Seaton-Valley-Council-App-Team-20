package com.ncl.team20.seatonvalley.components.mail;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Class used to create a Mail Object for ReportActivity and ContactActivity.
 * Creates the Send Mail object and the Verification Mail Object.
 * Used Gmail as an SMPT provider,and used a 2-pass verification then generated an app password for this application
 * in order to avoid suspicious activities when other devices from various places. <p>
 * Last Edit: 22/03/2018 by Stelios Ioannou <p>
 * Documentation Edit: 22/04/2018 by Alex Peebles
 * @author Stelios Ioannou
 * @author Olivija Guzelyte
 * @since 20/02/2018
 * @see Session
 * @see MimeMessage
 * @see Properties
 * @see SendMail
 * @see com.ncl.team20.seatonvalley.activities.ContactActivity
 * @see com.ncl.team20.seatonvalley.activities.ReportActivity
 */
class Mail {

    // Mail fields
    private final String fromEmail;
    private final String fromPassword;
    private final String toEmail;
    private final String emailSubject;
    private final String emailBody;
    // Mail Properties
    private final Properties emailProperties;
    // Mail Session
    private Session mailSession;

    // Mail message for the send message.
    private MimeMessage emailMessage;
    // Mail message for the verification message.
    private MimeMessage verificationMessage;

    /**
     * Mail Constructor.
     * @param fromEmail email the form is sent from
     * @param fromPassword password for the fromEmail
     * @param toEmail email the form is sent too
     * @param emailSubject subject of the email
     * @param emailBody email content
     *
     */
    public Mail(String fromEmail, String fromPassword,
                String toEmail, String emailSubject, String emailBody) {
        this.fromEmail = fromEmail;
        this.fromPassword = fromPassword;
        this.toEmail = toEmail;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;

        emailProperties = System.getProperties();
        String emailPort = "587";
        emailProperties.put("mail.smtp.port", emailPort);
        String smtpAuth = "true";
        emailProperties.put("mail.smtp.auth", smtpAuth);
        String starttls = "true";
        emailProperties.put("mail.smtp.starttls.enable", starttls);
        String auth = "true";
        emailProperties.put("mail.smtp.auth", auth);
        Log.i("Mail", "Mail server properties set.");
    }

    /**
     * Creates both eMailMessage and Verification Message.
     * @exception  MessagingException base class for email exceptions
     * @exception  UnsupportedEncodingException thrown if the wrong encoding is specified
     */
    public void createEmailMessage() throws
            MessagingException, UnsupportedEncodingException {

        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);
        emailMessage.setFrom(new InternetAddress(fromEmail, fromEmail));
        emailMessage.setSubject(emailSubject);
        emailMessage.setContent(emailBody, "text/html");
        verificationMessage = new MimeMessage(mailSession);
        verificationMessage.setFrom(new InternetAddress(fromEmail, fromEmail));
        verificationMessage.setSubject("Seaton Valley Council: Query Successfully submitted.");
        verificationMessage.setContent("Your query has been successfully submitted. We will review it as soon as possible.\n" +
                "<br>The Seaton Valley Community Council . \n" + "  \n", "text/html");
    }

    /**
     * Sends the mail and the verification mail.
     * @throws MessagingException if error occurs
     */
    public void sendEmail() throws MessagingException {
        Transport transport = mailSession.getTransport("smtp");
        String emailHost = "smtp.gmail.com";
        transport.connect(emailHost, fromEmail, fromPassword);
        transport.sendMessage(emailMessage, new InternetAddress[]{new InternetAddress(fromEmail)});
        transport.sendMessage(verificationMessage, new InternetAddress[]{new InternetAddress(toEmail)});
        transport.close();
    }

}