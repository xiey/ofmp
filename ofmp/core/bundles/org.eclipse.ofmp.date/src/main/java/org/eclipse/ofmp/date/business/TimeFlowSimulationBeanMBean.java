package org.eclipse.ofmp.date.business;

import java.util.Date;

import org.eclipse.ofmp.common.exception.OFMPException;

public interface TimeFlowSimulationBeanMBean
{

    public void initialize();

    public void initialize(Date aDate, Boolean aFireListeners);

    public void rollByHour() throws OFMPException;

    public void rollByDay() throws OFMPException;

    public void rollNumberOfBusinessDays(int aCount) throws OFMPException;

    public void rollToTheEndOfCurrentBusinessDay() throws OFMPException;

    public void rollToTheBeginningOfNextBusinessDay() throws OFMPException;
}
