package com.belentpatrus.gasstation.service.util;

import jakarta.mail.*;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.search.FlagTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Properties;

@Service
public class EmailService {

    MerchandiseItemSaleExcelReaderService myService;

    @Autowired
    public EmailService(MerchandiseItemSaleExcelReaderService myService) {
        this.myService = myService;
    }

    private String emailUsername = "dailyreport42020@gmail.com";
    private String emailPassword = "hhxkropkktzafqfw"; // Your app password

    public void getEmailExcelReport() {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imap.host", "imap.gmail.com");
        props.put("mail.imap.port", "993");
        props.put("mail.imap.ssl.enable", "true");
        props.put("mail.debug", "true");

        Session session = Session.getInstance(props);

        try {
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", emailUsername, emailPassword);
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            for (Message message : messages) {
                if (message.getSubject().equals("Report: daily_merchandise_report")) {
                    Multipart multipart = (Multipart) message.getContent();
                    MimeBodyPart attachmentPart = (MimeBodyPart) multipart.getBodyPart(1);
                    String fileName = attachmentPart.getFileName();
                    File file = new File(fileName);

                    // Create a temporary file in the system's temp directory
                    File tempFile = File.createTempFile("email_attachment_", "_" + fileName);

                    FileOutputStream fos = new FileOutputStream(tempFile);
                    InputStream is = attachmentPart.getInputStream();
                    is.transferTo(fos);


                    // Pass the file path to your method for processing
                    myService.readProductsFromExcel(tempFile.getAbsolutePath());
                }
            }
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

