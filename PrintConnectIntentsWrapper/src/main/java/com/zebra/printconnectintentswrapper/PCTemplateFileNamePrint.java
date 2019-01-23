package com.zebra.printconnectintentswrapper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import java.io.File;

public class PCTemplateFileNamePrint extends PCIntentsBase {

    /*
    An interface callback to be informed of the result
    of the print template intent
     */
    public interface onPrintFileNameResult
    {
        void success(PCTemplateFileNamePrintSettings settings);
        void error(String errorMessage, int resultCode, Bundle resultData, PCTemplateFileNamePrintSettings settings);
        void timeOut(PCTemplateFileNamePrintSettings settings);
    }

    private onPrintFileNameResult mPrintTemplateFileNameCallback = null;

    public PCTemplateFileNamePrint(Context aContext)
    {
        super(aContext);
    }

    public void execute(PCTemplateFileNamePrintSettings settings, onPrintFileNameResult callback)
    {
        if(callback == null)
        {
            Log.e(TAG, PCConstants.PCIntentsNoCallbackError);
            return;
        }

        mPrintTemplateFileNameCallback = callback;

        if(settings.mTemplateFileName.isEmpty())
        {
            if(mPrintTemplateFileNameCallback != null)
            {
                mPrintTemplateFileNameCallback.error(PCConstants.PCIntentsNoFileNameError, -1, null, settings);
            }
        }

        File myFile = new File(settings.mTemplateFileName);
        if(myFile.exists() == false)
        {
            if(mPrintTemplateFileNameCallback != null)
            {
                mPrintTemplateFileNameCallback.error(PCConstants.PCIntentsFileNotFoundError, -1, null, settings);
            }
        }


        /*
        Launch timeout mechanism
         */
        super.execute(settings);

        PrintTemplateFileName(settings);
    }

    private void PrintTemplateFileName(final PCTemplateFileNamePrintSettings settings)
    {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(PCConstants.PCComponentName,PCConstants.PCTemplatePrintService));
        intent.putExtra(PCConstants.PCTemplatePrintServiceTemplateFileName, settings.mTemplateFileName);
        if(settings.mVariableData != null && settings.mVariableData.size() > 0)
            intent.putExtra(PCConstants.PCTemplatePrintServiceVariableData, settings.mVariableData);
        ResultReceiver receiver = buildIPCSafeReceiver(new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                // Stop timeout mechanism
                cleanAll();
                if (resultCode == 0) {
                    // Result code 0 indicates success
                    if(mPrintTemplateFileNameCallback != null)
                    {
                        mPrintTemplateFileNameCallback.success(settings);
                    }
                } else {
                    // Handle unsuccessful print
                    // Error message (null on successful print)
                    String errorMessage = resultData.getString(PCConstants.PCErrorMessage);
                    if(errorMessage == null)
                        errorMessage = PCConstants.getErrorMessage(resultCode);
                    if(mPrintTemplateFileNameCallback != null)
                    {
                        mPrintTemplateFileNameCallback.error(errorMessage, resultCode, resultData, settings);
                    }
                }
            }
        });
        intent.putExtra(PCConstants.PCTemplatePrintServiceResultReceiver, receiver);
        mContext.startService(intent);
    }

    @Override
    protected void onTimeOut(PCIntentsBaseSettings settings) {
        if(mPrintTemplateFileNameCallback != null)
        {
            mPrintTemplateFileNameCallback.timeOut((PCTemplateFileNamePrintSettings)settings);
        }
    }
}
