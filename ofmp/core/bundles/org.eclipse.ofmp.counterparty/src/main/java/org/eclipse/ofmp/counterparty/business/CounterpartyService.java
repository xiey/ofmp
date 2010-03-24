package org.eclipse.ofmp.counterparty.business;

import java.util.List;

import org.eclipse.ofmp.common.exception.OFMPException;
import org.eclipse.ofmp.counterparty.dom.Bank;
import org.eclipse.ofmp.counterparty.dom.Counterparty;
import org.eclipse.ofmp.counterparty.dom.Customer;
import org.springframework.security.annotation.Secured;

public interface CounterpartyService
{
    @Secured(
    { "ROLE_CREATE_COUNTERPARTY" })
    Counterparty create(Counterparty counterparty) throws OFMPException;

    @Secured(
    { "ROLE_UPDATE_COUNTERPARTY" })
    Counterparty update(Counterparty counterparty) throws OFMPException;

    @Secured(
    { "ROLE_VIEW_COUNTERPARTY" })
    List<Counterparty> enumerateCounterparties() throws OFMPException;

    @Secured(
    { "ROLE_VIEW_COUNTERPARTY" })
    List<Bank> enumerateBanks() throws OFMPException;

    @Secured(
    { "ROLE_VIEW_COUNTERPARTY" })
    List<Customer> enumerateCustomers() throws OFMPException;

    @Secured(
    { "ROLE_VIEW_COUNTERPARTY" })
    Counterparty findCounterparty(Integer id) throws OFMPException;

    @Secured(
    { "ROLE_VIEW_COUNTERPARTY" })
    Bank findBank(Integer id) throws OFMPException;

    @Secured(
    { "ROLE_VIEW_COUNTERPARTY" })
    Bank findBankByExternalId(String aSystemId, String aCode) throws OFMPException;

    @Secured(
    { "ROLE_VIEW_COUNTERPARTY" })
    Customer findCustomerByName(String aName) throws OFMPException;

    @Secured(
    { "ROLE_VIEW_COUNTERPARTY" })
    Customer findCustomerByShortName(String aShortName) throws OFMPException;

    Bank getInstanceCounterparty() throws OFMPException;
}