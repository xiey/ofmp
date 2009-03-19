package org.eclipse.ofmp.common.utils;

import java.util.Date;
import java.util.GregorianCalendar;

public class OFMPCalendar extends GregorianCalendar
{
    private static final long serialVersionUID = 1L;

    public OFMPCalendar(Date aDate)
    {
        super();

        setTime(aDate);
    }
}
