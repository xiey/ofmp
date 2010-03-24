package org.eclipse.ofmp.date.business;

import java.util.Date;

import org.eclipse.ofmp.common.exception.OFMPException;

public interface DateService
{
    public boolean isBusinessDay();

    public Date getBusinessDayDate() throws OFMPException;

    public Date getLastBusinessDayDate() throws OFMPException;

    public Date getNextBusinessDayDate() throws OFMPException;

    public Date getCurrentDate() throws OFMPException;

    public Date getCurrentTimestamp() throws OFMPException;
}
