package org.eclipse.ofmp.mail.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.StringTokenizer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.eclipse.ofmp.mail.MailService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailServiceImpl implements MailService
{
    private final Logger log = Logger.getLogger("MAILING");

    private JavaMailSender m_MailSender;

    private String m_From, m_DefaultReceiver;

    // private static final EmailValidator emailValidator = EmailValidator.getInstance();

    private static Method addTo, addCC, addBCC;

    public MailServiceImpl() throws MessagingException
    {
        String methodName = "";
        try
        {
            addTo = MimeMessageHelper.class.getMethod(methodName = "addTo", String.class);
            addCC = MimeMessageHelper.class.getMethod(methodName = "addCc", String.class);
            addBCC = MimeMessageHelper.class.getMethod(methodName = "addBcc", String.class);
        }
        catch (NoSuchMethodException aEx)
        {
            String errorMessage = methodName + " does not exist in MimeMessageHelper class";
            log.error(errorMessage, aEx);
            throw new MessagingException(errorMessage, aEx);
        }
    }

    public void mail(String aSubject, String aMessage) throws MessagingException
    {
        Validate.notEmpty(m_DefaultReceiver, "The default receiver is not specified.");

        mail(m_DefaultReceiver, aSubject, aMessage);
    }

    public void mail(String aTo, String aSubject, String aMessage) throws MessagingException
    {
        mail(aTo, aSubject, aMessage, null, null);
    }

    public void mail(String aTo, String aSubject, String aMessage, Map<HeaderType, String> aHeaders,
            Map<String, byte[]> aAttachments) throws MessagingException
    {
        Validate.notEmpty(aTo, "The receiver must be specified.");
        Validate.notEmpty(aSubject, "The subject must be specified.");
        Validate.notEmpty(aMessage, "The message cannot be empty.");

        MimeMessage message = m_MailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(m_From);
        helper.setSubject(aSubject);
        helper.setText(aMessage);

        setHelperMethod(addTo, aTo, helper);

        if (aHeaders != null)
            for (HeaderType headerType : aHeaders.keySet())
            {
                Validate.notEmpty(aHeaders.get(headerType), "The value attached to headers key " + headerType
                        + " cannot be empty.");

                if (HeaderType.CC.equals(headerType))
                    setHelperMethod(addCC, aHeaders.get(headerType), helper);

                if (HeaderType.BCC.equals(headerType))
                    setHelperMethod(addBCC, aHeaders.get(headerType), helper);
            }

        if (aAttachments != null)
            for (String attachmentName : aAttachments.keySet())
            {
                Validate.notEmpty(attachmentName, "All attachments must have a non empty name");
                Validate.isTrue(aAttachments.get(attachmentName).length > 0, "The content of attachment "
                        + attachmentName + " cannot be empty.");

                helper.addAttachment(attachmentName, new ByteArrayResource(aAttachments.get(attachmentName)));
            }

        log.info("Sending message " + aSubject + " to " + aTo);

        m_MailSender.send(message);
    }

    public void sendTestMail() throws Exception
    {
        mail("Test email", "Test message");
    }

    /**
     * Sets the helper method.
     * 
     * @param aSetterMethod
     *            the a setter method
     * @param aEmailsString
     *            the a emails string
     * @param helper
     *            the helper
     * @throws MessagingException
     *             the messaging exception
     */
    private void setHelperMethod(Method aSetterMethod, String aEmailsString, final MimeMessageHelper helper)
            throws MessagingException
    {
        StringTokenizer tokenizer = new StringTokenizer(aEmailsString, ",");
        while (tokenizer.hasMoreTokens())
        {
            String email = tokenizer.nextToken().trim();
            // if (emailValidator.isValid(email))
            try
            {
                aSetterMethod.invoke(helper, email);
            }
            catch (IllegalAccessException aEx)
            {
                log.error(aEx);
            }
            catch (InvocationTargetException aEx)
            {
                log.error(aEx);
            }
            // else
            // throw new MessagingException("Invalid email address " + email);
        }
    }

    public void setMailSender(MailSender aMailSender)
    {
        m_MailSender = (JavaMailSender) aMailSender;
    }

    public void setFrom(String aFrom)
    {
        m_From = aFrom;
    }

    public void setDefaultReceiver(String aDefaultReceiver)
    {
        m_DefaultReceiver = aDefaultReceiver;
    }

}
