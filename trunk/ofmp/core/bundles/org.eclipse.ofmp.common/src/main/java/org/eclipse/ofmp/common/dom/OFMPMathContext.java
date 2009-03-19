package org.eclipse.ofmp.common.dom;

import java.math.MathContext;
import java.math.RoundingMode;

public class OFMPMathContext
{
    public static final int numberScale = 2, numberPrecision = 20;

    public static final RoundingMode defaultRoundingMode = RoundingMode.HALF_UP;

    private static final MathContext s_MathContext = new MathContext(numberPrecision, defaultRoundingMode);

    public static MathContext getContext()
    {
        return s_MathContext;
    }

}
