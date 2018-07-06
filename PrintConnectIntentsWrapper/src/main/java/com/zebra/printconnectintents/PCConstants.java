package com.zebra.printconnectintents;

public class PCConstants {

    public static final String PCComponentName = "com.zebra.printconnect";
    public static final String PCErrorMessage = "com.zebra.printconnect.PrintService.ERROR_MESSAGE";

    /*
    Print service from zpl filename
     */
    public static final String PCTemplatePrintService = "com.zebra.printconnect.print.TemplatePrintService";
    public static final String PCTemplatePrintServiceTemplateFileName = "com.zebra.printconnect.PrintService.TEMPLATE_FILE_NAME";
    public static final String PCTemplatePrintServiceVariableData = "com.zebra.printconnect.PrintService.VARIABLE_DATA";
    public static final String PCTemplatePrintServiceResultReceiver = "com.zebra.printconnect.PrintService.RESULT_RECEIVER";

    /*
    Print service from zpl string
     */
    public static final String PCTemplatePrintWithContentService = "com.zebra.printconnect.print.TemplatePrintWithContentService";
    public static final String PCTemplatePrintWithContentServiceTemplateData = "com.zebra.printconnect.PrintService.TEMPLATE_DATA";

    /*
    Line PassThrough
     */
    public static final String PCLinePrintPassthroughService = "com.zebra.printconnect.print.LinePrintPassthroughService";
    public static final String PCLinePrintPassthroughData = "com.zebra.printconnect.PrintService.LINE_PRINT_DATA";

    /*
    PassThrough
    */
    public static final String PCPassthroughService = "com.zebra.printconnect.print.PassthroughService";
    public static final String PCPassthroughData = "com.zebra.printconnect.PrintService.PASSTHROUGH_DATA";

    /*
    Graphic Print
     */
    public static final String PCGraphicPrintService = "com.zebra.printconnect.print.GraphicPrintService";
    public static final String PCGraphicPrintServiceFileName = "com.zebra.printconnect.PrintService.GRAPHIC_FILE_NAME";
    public static final String PCGraphicPrintServiceRotation = "com.zebra.printconnect.PrintService.GRAPHIC_FILE_ROTATION";
    public static final String PCGraphicPrintServiceMarginTop = "com.zebra.printconnect.PrintService.GRAPHIC_FILE_MARGIN_TOP";
    public static final String PCGraphicPrintServiceMarginLeft = "com.zebra.printconnect.PrintService.GRAPHIC_FILE_MARGIN_LEFT";
    public static final String PCGraphicPrintServiceMarginBottom = "com.zebra.printconnect.PrintService.GRAPHIC_FILE_MARGIN_BOTTOM";
    public static final String PCGraphicPrintServiceCenter = "com.zebra.printconnect.PrintService.GRAPHIC_FILE_CENTER";
    public static final String PCGraphicPrintServiceScaleX = "com.zebra.printconnect.PrintService.GRAPHIC_FILE_SCALE_X";
    public static final String PCGraphicPrintServiceScaleY = "com.zebra.printconnect.PrintService.GRAPHIC_FILE_SCALE_Y";

    /*
    Printer Status
     */
    public static final String PCPrinterStatusService = "com.zebra.printconnect.print.GetPrinterStatusService";
    public static final String PCPrinterStatusMap = "PrinterStatusMap";

    /*
    Unselect Printer
     */
    public static final String PCUnselectPrinterService = "com.zebra.printconnect.print.UnselectPrinterService";

    public static final String PCIntentsFileNotFoundError = "File not found";
    public static final String PCIntentsNoCallbackError = "Error: callback member is null.";
    public static final String PCIntentsNoZPLDataError = "Error: no zpl data.";
    public static final String PCIntentsNoFileNameError = "Error: no filename specified.";
    public static final String PCIntentsNoDataToPrintError = "Error: no data to print.";
}
