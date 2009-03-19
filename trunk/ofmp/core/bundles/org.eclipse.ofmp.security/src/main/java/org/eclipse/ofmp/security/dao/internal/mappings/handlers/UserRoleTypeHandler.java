package org.eclipse.ofmp.security.dao.internal.mappings.handlers;

import org.eclipse.ofmp.common.dao.ibatis.handlers.EnumTypeHandler;
import org.eclipse.ofmp.security.dom.Role;

public class UserRoleTypeHandler extends EnumTypeHandler
{

    @Override
    protected Enum<Role> resolve(String aId)
    {
        return Role.valueOf(aId);
    }

}