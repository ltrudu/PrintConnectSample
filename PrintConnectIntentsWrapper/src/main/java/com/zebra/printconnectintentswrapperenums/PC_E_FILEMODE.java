package com.zebra.printconnectintentswrapperenums;

public enum PC_E_FILEMODE
{
    PRINTER("PRINTER"),  FILE_SYSTEM("FILE_SYSTEM");

    private String enumString;

    private PC_E_FILEMODE(String confName)
    {
        this.enumString = confName;
    }

    public String toString()
    {
        return this.enumString;
    }

    public static PC_E_FILEMODE getFileMode(String type)
    {
        switch (type)
        {
            case "PRINTER":
                return PRINTER;
            case "FILE_SYSTEM":
                return FILE_SYSTEM;
        }
        return FILE_SYSTEM;
    }
}
