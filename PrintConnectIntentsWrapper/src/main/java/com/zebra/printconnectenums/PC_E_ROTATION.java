package com.zebra.printconnectenums;

public enum PC_E_ROTATION {
    ZERO("0"),
    NINETY("90"),
    ONEHUNDREDEIGHTY("180"),
    TWOHUNDREDSEVENTY("270");

    private String enumString;
    private PC_E_ROTATION(String confName)
    {
        this.enumString = confName;
    }

    @Override
    public String toString()
    {
        return enumString;
    }
}
