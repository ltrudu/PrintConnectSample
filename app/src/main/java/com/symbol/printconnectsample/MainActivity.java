package com.symbol.printconnectsample;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zebra.printconnectintentswrapperenums.*;
import com.zebra.printconnectintentswrapper.*;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;


import fr.w3blog.zpl.constant.ZebraFont;
import fr.w3blog.zpl.model.ZebraLabel;
import fr.w3blog.zpl.model.element.ZebraBarCode39;
import fr.w3blog.zpl.model.element.ZebraText;


public class MainActivity extends AppCompatActivity {

    private static String TAG = "PrintConnectSample";
    private static long mDemoTimeOutMS = 30000; //30s timeout...
    private static boolean mOptmizeRefresh = true;

    private TextView et_results;
    private ScrollView sv_results;
    private String mResults = "";
    private Date mIntentStartDate = null;

    /*
        Handler and runnable to scroll down textview
     */
    private Handler mScrollDownHandler = null;
    private Runnable mScrollDownRunnable = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        et_results = (TextView)findViewById(R.id.et_results);
        sv_results = (ScrollView)findViewById(R.id.sv_results);

        Button btTemplatePrint = (Button) findViewById(R.id.button_templateprint);
        btTemplatePrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  templatePrint();  }
        });

        Button btTemplatePrintWithContent = (Button) findViewById(R.id.button_templateprintwithcontent);
        btTemplatePrintWithContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { templatePrintWithContent(); }
        });

        Button btLinePassthrough = (Button) findViewById(R.id.button_lppassthrough);
        btLinePassthrough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { linePrintPassThrough(); }
        });

        Button btPassthrough = (Button) findViewById(R.id.button_passthrough);
        btPassthrough.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passthrough();
            }
        });

        Button btGraphicPrint = (Button) findViewById(R.id.button_graphicprint);
        btGraphicPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphicPrint();
            }
        });

        Button btPrinterStatus = (Button) findViewById(R.id.button_printerstatus);
        btPrinterStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printerStatus();
            }
        });

        Button btUnselectPrinter = (Button) findViewById(R.id.button_unselect);
        btUnselectPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unselectPrinter();
            }
        });

        Button btConnectToPrinter = (Button) findViewById(R.id.button_connect_printer);
        btConnectToPrinter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectPrinter();
            }
        });

        Button btClear = (Button) findViewById(R.id.button_clear);
        btClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResults = "";
                et_results.setText(mResults);
            }
        });
    }

    private void templatePrintWithContent(final String zplToPrint, final HashMap<String, String> variableData) {

        PCTemplateStringPrint templateStringPrint = new PCTemplateStringPrint(this);

        PCTemplateStringPrintSettings settings = new PCTemplateStringPrintSettings()
        {{
            mZPLTemplateString = zplToPrint;
            mVariableData = variableData;
        }};

        templateStringPrint.execute(settings, new PCTemplateStringPrint.onPrintTemplateStringResult() {
            @Override
            public void success(PCTemplateStringPrintSettings settings) {
                Log.d(TAG, "Template print string succeeded");
                Toast.makeText(MainActivity.this, "Print succeeded", Toast.LENGTH_LONG).show();
            }

            @Override
            public void error(String errorMessage, int resultCode, Bundle resultData, PCTemplateStringPrintSettings settings) {
                Log.e(TAG, "Error while trying to template string print: \n" + errorMessage);
                Toast.makeText(MainActivity.this, "Error while trying to print: \n" + errorMessage, Toast.LENGTH_LONG).show();
            }

            @Override
            public void timeOut(PCTemplateStringPrintSettings settings) {
                Log.e(TAG, "Print error: Timeout while trying to print.");
                Toast.makeText(MainActivity.this, "Print error: Timeout while trying to print.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScrollDownHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onPause() {
         if(mScrollDownRunnable != null)
        {
            mScrollDownHandler.removeCallbacks(mScrollDownRunnable);
            mScrollDownRunnable = null;
            mScrollDownHandler = null;
        }
        super.onPause();
    }

    private void connectPrinter(){
        mIntentStartDate = new Date();
        PCConnectPrinter connectPrinter = new PCConnectPrinter(this);
        PCConnectPrinterSettings settings = new PCConnectPrinterSettings()
        {{
            mCommandId = "connectPrinter";
            mEnableTimeOutMechanism = false;
            mBluetoothMacAddress = "AC3FA4CE7931";
            //mWifiMacAddress = "AC3FA4CE7930";
            //mEthernetMacAddress = "";
        }};
        connectPrinter.execute(settings, new PCConnectPrinter.onConnectPrinterResult() {
            @Override
            public void success(PCConnectPrinterSettings settings) {
                addLineToResults("Connect to printer succeeded");
                addTotalTimeToResults();
            }

            @Override
            public void error(String errorMessage, PCConnectPrinterSettings settings) {
                addLineToResults("Error while trying to connect to printer: \n" + errorMessage+ "\nBTMac=" + settings.mBluetoothMacAddress + "\nWifiMac="+settings.mWifiMacAddress+"\nEthernetMac="+settings.mEthernetMacAddress);
                addTotalTimeToResults();
            }

            @Override
            public void timeOut(PCConnectPrinterSettings settings) {
                addLineToResults("Timeout while trying to connect to printer:\nBTMac=" + settings.mBluetoothMacAddress + "\nWifiMac="+settings.mWifiMacAddress+"\nEthernetMac="+settings.mEthernetMacAddress);
            }
        });
    }

    private void unselectPrinter() {

        mIntentStartDate = new Date();

        PCUnselectPrinter unselectPrinter = new PCUnselectPrinter(MainActivity.this);
        PCIntentsBaseSettings settings = new PCIntentsBaseSettings()
        {{
            mCommandId = "unselectPrinter";
        }};

        unselectPrinter.execute(settings, new PCUnselectPrinter.onUnselectPrinterResult() {
            @Override
            public void success(PCIntentsBaseSettings settings) {
                addLineToResults("Unselect Printer succeeded");
                addTotalTimeToResults();
            }

            @Override
            public void error(String errorMessage, int resultCode, Bundle resultData, PCIntentsBaseSettings settings) {
                addLineToResults("Error while trying to unselect printer: \n" + errorMessage);
                addTotalTimeToResults();
            }

            @Override
            public void timeOut(PCIntentsBaseSettings settings) {
                addLineToResults("Timeout while trying to unselect printer");
            }
        });

    }

    private void printerStatus() {
        mIntentStartDate = new Date();

        PCPrinterStatus printerStatus = new PCPrinterStatus(MainActivity.this);
        PCIntentsBaseSettings settings = new PCIntentsBaseSettings()
        {{
            mCommandId = "printerstatus";
        }};

        printerStatus.execute(settings, new PCPrinterStatus.onPrinterStatusResult() {
            @Override
            public void success(PCIntentsBaseSettings settings, HashMap<String, String> printerStatusMap) {
                addLineToResults("Get Printer Status succeeded");
                addTotalTimeToResults();
                addLineToResults("Printer Status:");
                for (HashMap.Entry<String, String> entry : printerStatusMap.entrySet()) {
                    addLineToResults(entry.getKey() + " = " + entry.getValue());
                }
            }

            @Override
            public void error(String errorMessage, int resultCode, Bundle resultData, PCIntentsBaseSettings settings) {
                addLineToResults("Error while trying to get printer status: \n" + errorMessage);
                addTotalTimeToResults();
            }

            @Override
            public void timeOut(PCIntentsBaseSettings settings) {
                addLineToResults("Timeout while trying to get printer status");
            }
        });
    }

    private void passthrough() {
        mIntentStartDate = new Date();


        ZebraLabel zebraLabel = new ZebraLabel(912, 912);
        zebraLabel.setDefaultZebraFont(ZebraFont.ZEBRA_ZERO);

        zebraLabel.addElement(new ZebraText(10, 84, "Product:", 14));
        zebraLabel.addElement(new ZebraText(395, 85, "Camera", 14));

        zebraLabel.addElement(new ZebraText(10, 161, "CA201212AA", 14));

        //Add Code Bar 39
        zebraLabel.addElement(new ZebraBarCode39(10, 297, "CA201212AA", 118, 2, 2));

        zebraLabel.addElement(new ZebraText(10, 365, "Qté:", 11));
        zebraLabel.addElement(new ZebraText(180, 365, "3", 11));
        zebraLabel.addElement(new ZebraText(317, 365, "QA", 11));

        zebraLabel.addElement(new ZebraText(10, 520, "Ref log:", 11));
        zebraLabel.addElement(new ZebraText(180, 520, "0035", 11));
        zebraLabel.addElement(new ZebraText(10, 596, "Ref client:", 11));
        zebraLabel.addElement(new ZebraText(180, 599, "1234", 11));

        final String zplCode = zebraLabel.getZplCode();

        PCPassthroughPrint passthroughPrint = new PCPassthroughPrint(MainActivity.this);

        PCPassthroughPrintSettings settings = new PCPassthroughPrintSettings()
        {{
            mPassthroughData = zplCode;
        }};

        passthroughPrint.execute(settings, new PCPassthroughPrint.onPassthroughResult() {
            @Override
            public void success(PCPassthroughPrintSettings settings) {
                addLineToResults("Passthrough succeeded");
                addTotalTimeToResults();
            }

            @Override
            public void error(String errorMessage, int resultCode, Bundle resultData, PCPassthroughPrintSettings settings) {
                addLineToResults("Error while trying to passthrough: \n" + errorMessage);
                addTotalTimeToResults();
            }

            @Override
            public void timeOut(PCPassthroughPrintSettings settings) {
                addLineToResults("Timeout while trying line passthrough");
            }
        });
        //*/
    }

    private void linePrintPassThrough() {
        mIntentStartDate = new Date();

        PCLinePrintPassthroughPrint linePrintPassthroughPrint = new PCLinePrintPassthroughPrint(MainActivity.this);

        PCLinePrintPassthroughPrintSettings settings = new PCLinePrintPassthroughPrintSettings()
        {{
            mLineToPrint = "";//"Hello Printer\n€\n\n\n";
        }};

        linePrintPassthroughPrint.execute(settings, new PCLinePrintPassthroughPrint.onLinePrintPassthroughResult() {
            @Override
            public void success(PCLinePrintPassthroughPrintSettings settings) {
                addLineToResults("Line print passthrough succeeded");
                addTotalTimeToResults();
            }

            @Override
            public void error(String errorMessage, int resultCode, Bundle resultData, PCLinePrintPassthroughPrintSettings settings) {
                addLineToResults("Error while trying to line passthrough: \n" + errorMessage);
                addTotalTimeToResults();

            }

            @Override
            public void timeOut(PCLinePrintPassthroughPrintSettings settings) {
                addLineToResults("Timeout while trying line passthrough");
            }
        });

    }

    private void templatePrintWithContent() {
        mIntentStartDate = new Date();

        PCTemplateStringPrint templateStringPrint = new PCTemplateStringPrint(MainActivity.this);

        final String zplTemplateString = "\u0010CT~~CD,~CC^~CT~\n" +
                "^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR4,4~SD10^JUS^LRN^CI0^XZ\n" +
                "^XA\n" +
                "^MMT\n" +
                "^PW591\n" +
                "^LL0203\n" +
                "^LS0\n" +
                "^FT171,82^A0N,27,26^FH\\^FD%PRODUCT_NAME%^FS\n" +
                "^FT222,107^A0N,17,16^FH\\^FD%MSRP%^FS\n" +
                "^FT424,163^A0N,23,24^FB82,1,0,R^FH\\^FD%PCT%^FS\n" +
                "^FT314,167^A0N,28,28^FH\\^FD%FINAL%^FS\n" +
                "^FT367,107^A0N,17,16^FH\\^FD%UPC_CODE%^FS\n" +
                "^FT471,138^A0N,14,14^FH\\^FDYou saved:^FS\n" +
                "^FO451,119^GB103,54,2^FS\n" +
                "^FT171,20^A0N,17,16^FH\\^FDPrintConnect Template Print Example^FS\n" +
                "^FT171,167^A0N,28,28^FH\\^FDFinal Price:^FS\n" +
                "^FT171,51^A0N,17,16^FH\\^FDProduct:^FS\n" +
                "^FT171,107^A0N,17,16^FH\\^FDMSRP:^FS\n" +
                "^FT508,163^A0N,23,24^FH\\^FD%^FS\n" +
                "^FT328,107^A0N,17,16^FH\\^FDUPC:^FS\n" +
                "^FO171,119^GB259,0,2^FS\n" +
                "^PQ1,0,1,Y^XZ\n";



        // Define a hash map of variable data
        // Strings used for keys will be replaced by their corresponding values in your template file's ZPL
        final HashMap<String, String> variableData = new HashMap<>();
        variableData.put("%PRODUCT_NAME%", "Apples");
        variableData.put("%MSRP%", "$1.00");
        variableData.put("%PCT%", "50");
        variableData.put("%FINAL%", "$0.50");
        variableData.put("%UPC_CODE%", "12345678");

        PCTemplateStringPrintSettings settings = new PCTemplateStringPrintSettings()
        {{
            mZPLTemplateString = zplTemplateString;
            mVariableData = variableData;
        }};

        templateStringPrint.execute(settings, new PCTemplateStringPrint.onPrintTemplateStringResult() {
            @Override
            public void success(PCTemplateStringPrintSettings settings) {
                addLineToResults("Template print string succeeded");
                addTotalTimeToResults();
            }

            @Override
            public void error(String errorMessage, int resultCode, Bundle resultData, PCTemplateStringPrintSettings settings) {
                addLineToResults("Error while trying to template string print: \n" + errorMessage);
                addTotalTimeToResults();
            }

            @Override
            public void timeOut(PCTemplateStringPrintSettings settings) {
                addLineToResults("Timeout while trying template print string");
            }
        });
    }

    private void templatePrint() {
        mIntentStartDate = new Date();

        PCTemplateFileNamePrint templateFileNamePrint = new PCTemplateFileNamePrint(MainActivity.this);

        PCTemplateFileNamePrintSettings settings = new PCTemplateFileNamePrintSettings()
        {{
            mTemplateFileName = "HelloWorld.zpl";
            mFileMode = PC_E_FILEMODE.PRINTCONNECTCONFIGFOLDER;
        }};

        templateFileNamePrint.execute(settings, new PCTemplateFileNamePrint.onPrintFileNameResult() {
            @Override
            public void success(PCTemplateFileNamePrintSettings settings) {
                addLineToResults("Print Template file succeeded");
                addTotalTimeToResults();
            }

            @Override
            public void error(String errorMessage, int resultCode, Bundle resultData, PCTemplateFileNamePrintSettings settings) {
                addLineToResults("Error while trying to print filename: " + settings.mTemplateFileName + "\n" + errorMessage);
                addTotalTimeToResults();

            }

            @Override
            public void timeOut(PCTemplateFileNamePrintSettings settings) {
                addLineToResults("Timeout while trying to print filename: "+ settings.mTemplateFileName);
            }
        });
    }

    private void graphicPrint() {
        mIntentStartDate = new Date();

        PCGraphicPrint graphicPrint = new PCGraphicPrint(MainActivity.this);

        PCGraphicPrintSettings settings = new PCGraphicPrintSettings()
        {{
            mFileName = "ZebraLogo.jpg";
            mRotation = PC_E_ROTATION.ZERO;
            mMarginTop = 10;
            mMarginLeft = 0;
            mMarginBottom = 10;
            mCenter = true;
            mScaleX = 25;
            mScaleY = 25;
        }};

        graphicPrint.execute(settings, new PCGraphicPrint.onPrintGraphicResult() {
            @Override
            public void success(PCGraphicPrintSettings settings) {
                addLineToResults("Print graphic file succeeded");
                addTotalTimeToResults();
            }

            @Override
            public void error(String errorMessage, int resultCode, Bundle resultData, PCGraphicPrintSettings settings) {
                addLineToResults("Error while trying to print graphic file: \n" + errorMessage);
                addTotalTimeToResults();

            }

            @Override
            public void timeOut(PCGraphicPrintSettings settings) {
                addLineToResults("Timeout while trying to print graphic file");
            }
        });
    }


    private void addTotalTimeToResults()
    {
        Date current = new Date();
        long timeDiff = current.getTime() - mIntentStartDate.getTime();
        addLineToResults("Total time: " + timeDiff + "ms");
        mIntentStartDate = null;
    }

    private void addLineToResults(final String lineToAdd)
    {
        mResults += lineToAdd + "\n";
        updateAndScrollDownTextView();
    }

    private void updateAndScrollDownTextView()
    {
        if(mOptmizeRefresh)
        {
            if(mScrollDownRunnable == null)
            {
                mScrollDownRunnable = new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                et_results.setText(mResults);
                                sv_results.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        sv_results.fullScroll(ScrollView.FOCUS_DOWN);
                                    }
                                });
                            }
                        });
                    }
                };
            }
            else
            {
                // A new line has been added while we were waiting to scroll down
                // reset handler to repost it....
                mScrollDownHandler.removeCallbacks(mScrollDownRunnable);
            }
            mScrollDownHandler.postDelayed(mScrollDownRunnable, 300);
        }
        else
        {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    et_results.setText(mResults);
                    sv_results.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }

    }
}
