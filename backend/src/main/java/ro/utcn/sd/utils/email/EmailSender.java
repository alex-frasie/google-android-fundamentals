package ro.utcn.sd.utils.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;


    /**
     * Send a simple mail, with a subject and a body text.
     * @param recipient is the email address of recipient
     * @param subject is the subject of the email
     * @param textMessage is the text present in the mail body
     */
    public void sendMail(String recipient, String subject, String textMessage){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(textMessage);
        mailSender.send(message);
    }

    /**
     * Send a mail, with a subject and a body text and attachment.
     * @param recipient is the email address of recipient
     * @param subject is the subject of the email
     * @param textMessage is the text present in the mail body
     * @param attachmentName is the name of the file to be attached to the email.
     */
    public void sendMailWithAttachment(String recipient, String subject, String textMessage, String attachmentName){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(recipient);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(textMessage);
        simpleMailMessage.setFrom("noreply.haiengi@gmail.com");

        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(simpleMailMessage.getFrom());
            helper.setTo(simpleMailMessage.getTo());
            helper.setSubject(simpleMailMessage.getSubject());
            helper.setText(String.format(
                    simpleMailMessage.getText(), subject, textMessage));

            FileSystemResource file = new FileSystemResource("C:\\Users\\Alex\\Desktop\\sd-a3\\software-design-2020-30433-assignment3-alex-frasie\\sd\\" + attachmentName);
            helper.addAttachment(file.getFilename(), file);

        }catch (MessagingException e) {
            throw new MailParseException(e);
        }
        mailSender.send(message);
    }
}
