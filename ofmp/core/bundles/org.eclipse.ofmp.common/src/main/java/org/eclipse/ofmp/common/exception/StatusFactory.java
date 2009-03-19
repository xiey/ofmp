package org.eclipse.ofmp.common.exception;

import org.eclipse.ofmp.common.exception.Status.Severity;

public class StatusFactory
{
    public static final Status DBMS_ERROR = new Status("00001", Severity.ERROR, "DBMS error");
    public static final Status CONCURRENT_MODIFICATION = new Status("00002", Severity.ERROR,
            "Concurrent modification: someone else amended this entry before you started editing it.");
    public static final Status OBJECT_IS_NEW = new Status("00003", Severity.ERROR, "Object is new");
    public static final Status OBJECT_NOT_FOUND = new Status("00004", Severity.ERROR, "Object not found");

    public static final Status GENERAL_ERROR = new Status("01000", Severity.ERROR, "General application error");
    public static final Status ILLEGAL_ARGUMENT = new Status("01001", Severity.ERROR, "Illegal argument");

    public static final Status SECURITY_ERROR = new Status("02000", Severity.ERROR, "Security error");

    public static final Status ST03001 = new Status("03001", Severity.ERROR,
            "Cannot validate a deal, that has status, different from 'NEW'.");
    public static final Status ST03002 = new Status("03002", Severity.ERROR, "Deal status cannot be updated directly.");
    public static final Status ST03003 = new Status("03003", Severity.ERROR, "Deal product cannot be updated directly.");
    public static final Status ST03004 = new Status("03004", Severity.ERROR,
            "Only deals in status 'NEW' can be updated.");
    public static final Status ST03005 = new Status("03005", Severity.ERROR,
            "Cannot close a deal, that has status, different from 'VALIDATED'.");
    public static final Status ST03006 = new Status("03006", Severity.ERROR,
            "Dealer can update only deals created by himself.");
    public static final Status ST03007 = new Status("03007", Severity.ERROR, "Dealer id cannot be updated.");
    public static final Status ST03008 = new Status("03008", Severity.ERROR, "Deal status is already set to NEW");
    public static final Status ST03009 = new Status("03009", Severity.ERROR,
            "Cannot cancel a deal that has status different from 'NEW'.");
    public static final Status ST03010 = new Status("03010", Severity.ERROR,
            "A deal imported from BackOffice can only be in CLOSED status.");
    public static final Status ST03011 = new Status("03011", Severity.ERROR,
            "Only dealer and trusted external systems are allowed to create a deal");
    public static final Status ST03012 = new Status("03012", Severity.ERROR, "Cannot revert deal that is not closed");
    public static final Status ST03013 = new Status("03013", Severity.ERROR, "A reverted deal cannot be modified.");
    public static final Status ST03014 = new Status("03014", Severity.ERROR,
            "Only MM Traders and trusted external systems are allowed to create MM deals.");
    public static final Status ST03015 = new Status("03015", Severity.ERROR,
            "Only FX Traders and trusted external systems are allowed to create MM deals.");
    public static final Status ST03016 = new Status("03016", Severity.ERROR, "New deal holder is not a valid dealer.");
    public static final Status ST03017 = new Status("03017", Severity.ERROR,
            "Deal Business Line cannot be updated directly, use changeBusinessLine method.");
    public static final Status ST03018 = new Status("03018", Severity.ERROR,
            "Deal Holder cannot be updated directly, use changeHolder method.");
    public static final Status ST03019 = new Status("03019", Severity.ERROR,
            "New deal holder cannot be of a different desk than the original one.");

    public static final Status ST03020 = new Status("03020", Severity.ERROR, "General validation error");

    public static final Status ST03022 = new Status("03022", Severity.ERROR,
            "Validation error: transaction-reference is mandatory for manual deal export");

    public static final Status ST04001 = new Status("04001", Severity.ERROR, "Ticket status is different than ERROR");

    public static final Status ST05001 = new Status("05001", Severity.ERROR, "A user cannot be demoted from dealer");
    public static final Status ST05002 = new Status("05002", Severity.ERROR,
            "Either add the Dealer role or disable this user as a dealer");
    public static final Status ST05003 = new Status("05003", Severity.ERROR,
            "Either remove the Dealer role or enable this user as a dealer");
    public static final Status ST05004 = new Status("05004", Severity.ERROR,
            "A dealer with the same Reuters ID already exist");

    public static final Status ST06001 = new Status("06001", Severity.ERROR, "General reporting error");
    public static final Status ST06002 = new Status("06002", Severity.ERROR, "Cannot read report file");

    public static final Status ST07001 = new Status("07001", Severity.ERROR, "No active business day");
    public static final Status ST07002 = new Status("07002", Severity.ERROR, "Current time is not set");

    public static final Status ST08001 = new Status("08001", Severity.ERROR,
            "Non empty spot positions for new business day.");
    public static final Status ST08002 = new Status("08002", Severity.ERROR,
            "Error importing daily Forex Reference Spot positions.");
    public static final Status ST08003 = new Status("08003", Severity.ERROR,
            "Error importing initial forward positions.");

    public static final Status RCML_ERROR = new Status("09001", Severity.ERROR, "RCML Error");

    public static final Status ST10001 = new Status("10001", Severity.ERROR, "Error while processing a job.");
    public static final Status ST10002 = new Status("10002", Severity.WARN,
            "Report job has already been scheduled before");

    public static final Status ST11001 = new Status("11001", Severity.ERROR, "Invalid portfolio reference");
    public static final Status ST11002 = new Status("11002", Severity.ERROR, "Portfolio does not exist");

    public static final Status CUSTOMER_NAME_CONFLICT = new Status("12001", Severity.ERROR,
            "Customer name must be unique.");

}
