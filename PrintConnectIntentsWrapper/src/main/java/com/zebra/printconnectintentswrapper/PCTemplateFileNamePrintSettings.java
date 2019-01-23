package com.zebra.printconnectintentswrapper;

import java.util.HashMap;

public class PCTemplateFileNamePrintSettings extends PCIntentsBaseSettings {
    /*
    Store here a template name
     */
    public String mTemplateFileName = "";

    /*
    Store here the data that have to be replaced in the template
    Keep it to null if there are no variable in your template
     */
    public HashMap<String,String> mVariableData = null;
}

