package com.chathuru.sesclient;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

@Controller
@RequestMapping(path="/")
public class SendEmail {

    @GetMapping(path="/send")
    public @ResponseBody String sendEmail() throws MessagingException, IOException {
        Region region = Region.US_EAST_1;
        SesClient client = SesClient.builder()
                .region(region)
                .build();

        send(client);

        return "Send";
    }

    public static void send(SesClient client) throws AddressException, MessagingException, IOException {
        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);

        message.setSubject("Testing AWS SES Permissions", "UTF-8");
        message.setFrom(new InternetAddress("amal.l@x-venture.io"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("chathuru1993@gmail.com"));
        MimeMultipart msgBody = new MimeMultipart("alternative");
        MimeBodyPart wrap = new MimeBodyPart();

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent("Hello,\r\n" + "See the list of customers. FROM JAVA ", "text/plain; charset=UTF-8");

        msgBody.addBodyPart(textPart);
        wrap.setContent(msgBody);
        MimeMultipart msg = new MimeMultipart("mixed");
        message.setContent(msg);
        msg.addBodyPart(wrap);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        message.writeTo(outputStream);
        ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());

        byte[] arr = new byte[buf.remaining()];
        buf.get(arr);

        SdkBytes data = SdkBytes.fromByteArray(arr);
        RawMessage rawMessage = RawMessage.builder()
                .data(data)
                .build();

        SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder()
                .rawMessage(rawMessage)
                .build();

        client.sendRawEmail(rawEmailRequest);
    }
}
