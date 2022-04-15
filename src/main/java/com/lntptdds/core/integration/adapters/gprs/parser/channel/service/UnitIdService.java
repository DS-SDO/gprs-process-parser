package com.lntptdds.core.integration.adapters.gprs.parser.channel.service;

import com.lntptdds.core.integration.adapters.gprs.parser.channel.model.UnitIds;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitIdService {

    UnitIds unitId = new UnitIds();

    public UnitIds getUnitIdList()
    {
        return unitId;

    }

    public UnitIds addUnitId(UnitIds unitIds)
    {
        unitId.setUnitId(unitIds.getUnitId());

        unitId.toString();
        return unitId;

    }


}
