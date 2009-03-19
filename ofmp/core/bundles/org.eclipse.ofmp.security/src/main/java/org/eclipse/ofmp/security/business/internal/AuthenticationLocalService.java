package org.eclipse.ofmp.security.business.internal;

import org.eclipse.ofmp.security.business.AuthenticationService;
import org.eclipse.ofmp.security.business.OFMPAuthenticationToken;
import org.springframework.security.Authentication;

public interface AuthenticationLocalService extends AuthenticationService
{
    // TODO Rename to FOGAuthentication
    public OFMPAuthenticationToken getTOFAuthentication();

    // TODO Rename to BackOfficeAuthentication
    public Authentication getBackOfficeAuthentication();

    public Authentication getJMXAuthentication();

    public Authentication getSchedulerAuthentication();

    public Authentication getWebServiceAuthentication();

    public Authentication getPortfolioManagementAuthentication();

    public Authentication getHistoryPositionListenerAuthentication();

}
