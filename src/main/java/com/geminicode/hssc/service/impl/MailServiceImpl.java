package com.geminicode.hssc.service.impl;

import com.geminicode.hssc.service.MailService;
import com.google.common.base.Strings;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailServiceImpl implements MailService {

    private static final Logger LOGGER = Logger.getLogger(MailServiceImpl.class.getName());
    //TODO externalize this params
    public static final String MAIL = "angelomiguellima@gmail.com";
    public static final String NAME = "Angélo LIMA";
    public static final String OBJECT = "Feedback from HearthStone SearchCard";

    @Override
    public void send(String name, String email, String message) {

        if (Strings.isNullOrEmpty(name) || Strings.isNullOrEmpty(email) || Strings.isNullOrEmpty(message)) {
            LOGGER.log(Level.SEVERE, "One param is null or empty.");
            return;
        }

        LOGGER.log(Level.INFO, name + " " + email + " " + message);
        final Properties props = new Properties();
        final Session session = Session.getDefaultInstance(props, null);

        try {
            final Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(email, name));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(MAIL, NAME));
            msg.setSubject(OBJECT);
            msg.setText(message);
            Transport.send(msg);

        } catch (MessagingException | UnsupportedEncodingException e) {
            LOGGER.log(Level.SEVERE, "Message can't be sent, from : " + email + ", message : " + message);
        }
    }
}
