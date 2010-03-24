package org.eclipse.ofmp.date.business;

import java.util.Date;

/**
 * The listener interface for receiving date events. The class that is interested in processing a date event implements
 * this interface, and the object created with that class is registered with a component using the component's
 * <code>addDateListener<code> method. When
 * the date event occurs, that object's appropriate
 * method is invoked.
 */
public interface DateListener
{
    void beforeBusinessDayBegin(Date aDay);

    void afterBusinessDayBegin(Date aDay);

    void beforeBusinessDayEnd(Date aDay);

    void afterBusinessDayEnd(Date aDay);
}
