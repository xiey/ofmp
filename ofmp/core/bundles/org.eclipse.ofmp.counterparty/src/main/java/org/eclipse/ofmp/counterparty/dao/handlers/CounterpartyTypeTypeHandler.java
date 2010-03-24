package org.eclipse.ofmp.counterparty.dao.handlers;

import org.eclipse.ofmp.common.dao.ibatis.handlers.EnumTypeHandler;
import org.eclipse.ofmp.counterparty.dom.Counterparty;

public class CounterpartyTypeTypeHandler extends EnumTypeHandler
{

    @Override
    protected Enum<Counterparty.Type> resolve(String aId)
    {
        return Counterparty.Type.valueOf(aId);
    }

}