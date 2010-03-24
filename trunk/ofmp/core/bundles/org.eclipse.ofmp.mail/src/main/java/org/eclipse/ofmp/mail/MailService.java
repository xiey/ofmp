package org.eclipse.ofmp.mail;

import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.mail.MailSender;

public interface MailService
{
    public enum HeaderType
    {
        CC, BCC;
    }

    /**
     * Sends an email to the default system receiver. Usually helpdesk/support team.
     * 
     * @param subject
     *            the message subject. Cannot be empty.
     * @param message
     *            the message content. Cannot be empty.
     * @throws MessagingException
     *             the messaging exception
     */
    void mail(String subject, String message) throws MessagingException;

    /**
     * Mail.
     * 
     * @param to
     *            the receiver(s). If multiple receiver emails are specified, they must be comma separated.
     * @param subject
     *            the message subject. Cannot be empty.
     * @param message
     *            the message content. Cannot be empty.
     * @throws MessagingException
     *             the messaging exception
     */
    void mail(String to, String subject, String message) throws MessagingException;

    /**
     * Mail.
     * 
     * @param to
     *            the receiver(s). If multiple receiver emails are specified, they must be comma separated.
     * @param subject
     *            the message subject
     * @param message
     *            the message content
     * @param m_Headers
     *            a map of <code>HeaderType</code> to header content
     * @param attachments
     *            a map of attachments for which every provided key/value cannot be empty.
     * @throws MessagingException
     *             the messaging exception
     */
    void mail(String to, String subject, String message, Map<HeaderType, String> m_Headers,
            Map<String, byte[]> attachments) throws MessagingException;

    void setMailSender(MailSender aSender);
}
