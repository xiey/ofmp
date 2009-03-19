package org.eclipse.ofmp.security.business.internal;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.Authentication;

public class SessionService
{
    private int m_SessionCounter;
    private Map<Object, Authentication> m_Sessions;

    public SessionService()
    {
        m_Sessions = new HashMap<Object, Authentication>();
    }

    public String generateSessionId()
    {
        return "SESSION:" + m_SessionCounter++;
    }

    public void registerSession(Authentication aResult)
    {
        m_Sessions.put(aResult.getCredentials(), aResult);
    }

    public boolean isTokenValid(Authentication aAuthentication)
    {
        return m_Sessions.containsKey(aAuthentication.getCredentials());
    }

    public Authentication getRegisteredAuthentication(Object aKey)
    {
        return m_Sessions.get(aKey);
    }
}
