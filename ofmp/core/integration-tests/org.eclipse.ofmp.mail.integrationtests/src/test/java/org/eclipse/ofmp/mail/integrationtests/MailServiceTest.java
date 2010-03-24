//package org.eclipse.ofmp.mail.integrationtests;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.mail.MessagingException;
//
//import org.eclipse.ofmp.mail.MailService;
//import org.eclipse.ofmp.mail.MailService.HeaderType;
//import org.eclipse.ofmp.test.AbstractOFMPSpringDMTest;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import com.dumbster.smtp.SimpleSmtpServer;
//
///**
// * issue javax.activation.UnsupportedDataTypeException: no object DCH for MIME type multipart/mixed; to be fixed
// */
//public class MailServiceTest extends AbstractOFMPSpringDMTest
//{
//    private MailService m_Service;
//    private SimpleSmtpServer m_Server;
//
//    @Override
//    protected String[] getConfigLocations()
//    {
//        return new String[]
//        { "mail-context-test.xml" };
//    }
//
//    @Override
//    protected String getManifestLocation()
//    {
//        return "classpath:org/eclipse/ofmp/mail/test/MANIFEST.MF";
//    }
//
//    @Override
//    protected String[] getTestBundlesNames()
//    {
//        List<String> list = new ArrayList<String>(Arrays.asList(super.getTestBundlesNames()));
//
//        list.add("org.eclipse.ofmp,org.eclipse.ofmp.test,1.0.0-M1");
//        list.add("org.eclipse.ofmp,org.eclipse.ofmp.security,1.0.0-M1");
//        list.add("org.eclipse.ofmp,org.eclipse.ofmp.mail,1.0.0-M1");
//        list.add("com.dumbster,com.springsource.com.dumbster.smtp,1.6.0");
//
//        return list.toArray(new String[] {});
//    }
//
//    @Override
//    protected boolean needClean()
//    {
//        return false;
//    }
//
//    private void useFakeServer()
//    {
//        m_Server = SimpleSmtpServer.start();
//
//        JavaMailSenderImpl sender = new org.springframework.mail.javamail.JavaMailSenderImpl();
//        sender.setHost("localhost");
//        sender.setPort(25);
//
//        m_Service.setMailSender(sender);
//    }
//
//    public void testSimpleMail()
//    {
//        useFakeServer();
//
//        try
//        {
//            m_Service.mail("someone@company.com", "test", "test");
//        }
//        catch (MessagingException aEx)
//        {
//            assertTrue(false);
//        }
//
//        assertEquals(1, m_Server.getReceivedEmailSize());
//    }
//
//    public void testSimpleMailToDefaultReceiver()
//    {
//        useFakeServer();
//        try
//        {
//            m_Service.mail("test", "test");
//        }
//        catch (MessagingException aEx)
//        {
//            assertTrue(false);
//        }
//
//        assertEquals(1, m_Server.getReceivedEmailSize());
//    }
//
//    public void testMailWithAttachment()
//    {
//        useFakeServer();
//        try
//        {
//            Map<HeaderType, String> headers = new HashMap<HeaderType, String>();
//            headers.put(HeaderType.CC, "");
//
//            Map<String, byte[]> attachments = new HashMap<String, byte[]>();
//
//            attachments.put("attachment1.txt", "Test".getBytes());
//
//            m_Service.mail("someone@company.com", "test", "test", headers, attachments);
//
//            assertTrue(false);
//        }
//        catch (IllegalArgumentException aEx)
//        {
//        }
//        catch (MessagingException aEx)
//        {
//        }
//
//        try
//        {
//            Map<HeaderType, String> headers = new HashMap<HeaderType, String>();
//            headers.put(HeaderType.CC, "mail@company.com");
//
//            Map<String, byte[]> attachments = new HashMap<String, byte[]>();
//
//            attachments.put(null, "Test".getBytes());
//
//            m_Service.mail("someone@company.com", "test", "test", headers, attachments);
//
//            assertTrue(false);
//        }
//        catch (IllegalArgumentException aEx)
//        {
//        }
//        catch (MessagingException aEx)
//        {
//        }
//
//        try
//        {
//            Map<HeaderType, String> headers = new HashMap<HeaderType, String>();
//            headers.put(HeaderType.CC, "mail@company.com, someone@company.com");
//            headers.put(HeaderType.BCC, "someone@company.com");
//
//            Map<String, byte[]> attachments = new HashMap<String, byte[]>();
//
//            attachments.put("attachment1", "Test".getBytes());
//            attachments.put("attachment2", "Test2".getBytes());
//
//            m_Service.mail("frederic@conrotte.be", "test", "test", headers, attachments);
//        }
//        catch (MessagingException aEx)
//        {
//            assertTrue(false);
//        }
//
//        assertEquals(1, m_Server.getReceivedEmailSize());
//    }
//
//    public void testSimpleMailToRealTestReceiver()
//    {
//        try
//        {
//            m_Service.mail("test", "test");
//        }
//        catch (MessagingException aEx)
//        {
//            assertTrue(false);
//        }
//    }
//
//    public void setMailService(MailService aMailService)
//    {
//        m_Service = aMailService;
//    }
//
// }
