Documentation:

The code uses PrintConnect to do the pairing with the printer:
https://www.zebra.com/us/en/products/software/barcode-printers/link-os/print-connect.html

PrintConnect documentation (in the Intents section you'll find a description of the methods implemented by the wrapper):
https://www.zebra.com/content/dam/zebra_new_ia/en-us/solutions-verticals/product/Software/Printer%20Software/Link-OS/print-connect/PC-UserGuide-P1082444-001.pdf

PrintConnect is available on the Google play store : https://play.google.com/store/apps/details?id=com.zebra.printconnect
It must be installed on the device, then install intentprint-release.apk

The source code of the wrapper is available at the following address:
https://github.com/ltrudu/PrintConnectSample
Please read the Zebra license associated with this project before use.

The printer must be paired using PrintConnect for the wrapper to work.
To do this, simply install PrintConnect, and then do an NFC pairing with a mobile bluetooth printer or any other kind of pairing (Wifi, Ethernet)
The process is explained in the PrintConnect user guide.

Commands can be executed using a href in an HTML element or simply by using the window.open("") method;

Example of line-to-line printing using the javascript window.open method
window.open("intent://printsingleline/#Intent;scheme=plaintext;package=com.zebra.browserintenturl;S.text=This is the text to print;S.verbose=true;end")

The extra S.verbose allows to control the display of information popups or not (true or false)

The data can be encoded in base64 or in plain text.
The choice is made using the scheme attribute which can take the values: plaintext or base64encoded.
The extra S.standardCharsets specifies the type of the base64 encoded string. It supports following values: UTF_8, UTF_16, UTF_16BE, UTF_16LE, ISO_8859_1, US_ASCII

The encoding tests were done with the following site:
https://www.base64encode.org/

You can use the following API (MIT license) to encode directly from javascript:
https://github.com/emn178/hi-base64

If you use non-encoded strings, it is better to "escape" them before passing them to the URL.
https://www.freeformatter.com/javascript-escape.html

Here are the urls available for printing:

// ##########################################################################
// Print directZPL
// Extras: S.template contains the ZPL data 
// ##########################################################################

"intent://printzpl/#Intent;scheme=plaintext;package=browserintenturl;S.template=CT~~CD,~CC^~CT~^XA~TA000~JSN^LT0^MNN^MTD^PON^PMN^LH0,0^JMA^PR5,5~SD10^JUS^LRN^CI0^XZ^XA^MMT^PW406^LL0200^LS0^FT38,83^A0N,27,28^FH\\^FDPierre d\'ouverture, ^FS^FT38,116^A0N,27,28^FH\\^FDli\\82e au chakra du coeur et ^FS^FT38,149^A0N,27,28^FH\\^FD\\85 v\\82nus apaisante, favorise ^FS^FT38,182^A0N,27,28^FH\\^FDla gu\\82rison des \\82motions^FS^FT107,44^A0N,39,38^FH\\^FDMALACHITE^FS^FO304,29^GB79,0,8^FS^FO13,29^GB79,0,8^FS^PQ1,0,1,Y^XZ;S.verbose=true;end"

"intent://printzpl/#Intent;scheme=plaintext;package=browserintenturl;S.template=CT~~CD,~CC^~CT~^XA~TA000~JSN^LT0^MNN^MTD^PON^PMN^LH0,0^JMA^PR5,5~SD10^JUS^LRN^CI27^XZ^XA^MMT^PW406^LL0200^LS0^FT38,83^A0N,27,28^FH^FDééééàààààôôôô^FS^FO304,29^GB79,0,8^FS^FO13,29^GB79,0,8^FS^PQ1,0,1,Y^XZ;S.verbose=true;end"


// ASCII Base64 Encoding 
"intent://printzpl/#Intent;scheme=base64encoded;package=browserintenturl;S.template=Q1R+fkNELH5DQ15+Q1R+XlhBflRBMDAwfkpTTl5MVDBeTU5XXk1URF5QT05eUE1OXkxIMCwwXkpNQV5QUjUsNX5TRDEwXkpVU15MUk5eQ0kwXlhaXlhBXk1NVF5QVzQwNl5MTDAyMDBeTFMwXkZUMzgsODNeQTBOLDI3LDI4XkZIXF5GRFBpZXJyZSBkJ291dmVydHVyZSwgXkZTXkZUMzgsMTE2XkEwTiwyNywyOF5GSFxeRkRsaVw4MmUgYXUgY2hha3JhIGR1IGNvZXVyIGV0IF5GU15GVDM4LDE0OV5BME4sMjcsMjheRkhcXkZEXDg1IHZcODJudXMgYXBhaXNhbnRlLCBmYXZvcmlzZSBeRlNeRlQzOCwxODJeQTBOLDI3LDI4XkZIXF5GRGxhIGd1XDgycmlzb24gZGVzIFw4Mm1vdGlvbnNeRlNeRlQxMDcsNDReQTBOLDM5LDM4XkZIXF5GRE1BTEFDSElURV5GU15GTzMwNCwyOV5HQjc5LDAsOF5GU15GTzEzLDI5XkdCNzksMCw4XkZTXlBRMSwwLDEsWV5YWg==;S.verbose=true;end"

// UTF-16 Encoding
"intent://printzpl/#Intent;scheme=base64encoded;package=browserintenturl;S.template=//5DAFQAfgB+AEMARAAsAH4AQwBDAF4AfgBDAFQAfgBeAFgAQQB+AFQAQQAwADAAMAB+AEoAUwBOAF4ATABUADAAXgBNAE4AVwBeAE0AVABEAF4AUABPAE4AXgBQAE0ATgBeAEwASAAwACwAMABeAEoATQBBAF4AUABSADUALAA1AH4AUwBEADEAMABeAEoAVQBTAF4ATABSAE4AXgBDAEkAMABeAFgAWgBeAFgAQQBeAE0ATQBUAF4AUABXADQAMAA2AF4ATABMADAAMgAwADAAXgBMAFMAMABeAEYAVAAzADgALAA4ADMAXgBBADAATgAsADIANwAsADIAOABeAEYASABcAF4ARgBEAFAAaQBlAHIAcgBlACAAZAAnAG8AdQB2AGUAcgB0AHUAcgBlACwAIABeAEYAUwBeAEYAVAAzADgALAAxADEANgBeAEEAMABOACwAMgA3ACwAMgA4AF4ARgBIAFwAXgBGAEQAbABpAFwAOAAyAGUAIABhAHUAIABjAGgAYQBrAHIAYQAgAGQAdQAgAGMAbwBlAHUAcgAgAGUAdAAgAF4ARgBTAF4ARgBUADMAOAAsADEANAA5AF4AQQAwAE4ALAAyADcALAAyADgAXgBGAEgAXABeAEYARABcADgANQAgAHYAXAA4ADIAbgB1AHMAIABhAHAAYQBpAHMAYQBuAHQAZQAsACAAZgBhAHYAbwByAGkAcwBlACAAXgBGAFMAXgBGAFQAMwA4ACwAMQA4ADIAXgBBADAATgAsADIANwAsADIAOABeAEYASABcAF4ARgBEAGwAYQAgAGcAdQBcADgAMgByAGkAcwBvAG4AIABkAGUAcwAgAFwAOAAyAG0AbwB0AGkAbwBuAHMAXgBGAFMAXgBGAFQAMQAwADcALAA0ADQAXgBBADAATgAsADMAOQAsADMAOABeAEYASABcAF4ARgBEAE0AQQBMAEEAQwBIAEkAVABFAF4ARgBTAF4ARgBPADMAMAA0ACwAMgA5AF4ARwBCADcAOQAsADAALAA4AF4ARgBTAF4ARgBPADEAMwAsADIAOQBeAEcAQgA3ADkALAAwACwAOABeAEYAUwBeAFAAUQAxACwAMAAsADEALABZAF4AWABaAA==;S.standardCharsets=UTF_16;S.verbose=true;end"


// ##########################################################################
// Direct ZPL with Variable data
// Extras: 
// S.template contains the ZPL data
// s.variables (optional) contains variable data separated by :
// ##########################################################################

"intent://printzpl/#Intent;scheme=plaintext;package=browserintenturl;S.template=CT~~CD,~CC^~CT~^XA~TA000~JSN^LT0^MNN^MTD^PON^PMN^LH0,0^JMA^PR5,5~SD10^JUS^LRN^CI0^XZ^XA^MMT^PW406^LL0200^LS0^FT38,83^A0N,27,28^FH\\^FDPierre d\'ouverture, ^FS^FT38,116^A0N,27,28^FH\\^FDli\\82e au %TEXTE% du coeur et ^FS^FT38,149^A0N,27,28^FH\\^FD\\85 v\\82nus apaisante, favorise ^FS^FT38,182^A0N,27,28^FH\\^FDla gu\\82rison des \\82motions %CODE%^FS^FT107,44^A0N,39,38^FH\\^FDMALACHITE^FS^FO304,29^GB79,0,8^FS^FO13,29^GB79,0,8^FS^PQ1,0,1,Y^XZ;S.variables=%TEXTE%:montexte:%CODE%:12345;S.verbose=true;end"


// ASCII Encoding
"intent://printzpl/#Intent;scheme=base64encoded;package=browserintenturl;S.template=Q1R+fkNELH5DQ15+Q1R+XlhBflRBMDAwfkpTTl5MVDBeTU5XXk1URF5QT05eUE1OXkxIMCwwXkpNQV5QUjUsNX5TRDEwXkpVU15MUk5eQ0kwXlhaXlhBXk1NVF5QVzQwNl5MTDAyMDBeTFMwXkZUMzgsODNeQTBOLDI3LDI4XkZIXF5GRFBpZXJyZSBkJ291dmVydHVyZSwgXkZTXkZUMzgsMTE2XkEwTiwyNywyOF5GSFxeRkRsaVw4MmUgYXUgJVRFWFRFJSBkdSBjb2V1ciBldCBeRlNeRlQzOCwxNDleQTBOLDI3LDI4XkZIXF5GRFw4NSB2XDgybnVzIGFwYWlzYW50ZSwgZmF2b3Jpc2UgXkZTXkZUMzgsMTgyXkEwTiwyNywyOF5GSFxeRkRsYSBndVw4MnJpc29uIGRlcyBcODJtb3Rpb25zICVDT0RFJV5GU15GVDEwNyw0NF5BME4sMzksMzheRkhcXkZETUFMQUNISVRFXkZTXkZPMzA0LDI5XkdCNzksMCw4XkZTXkZPMTMsMjleR0I3OSwwLDheRlNeUFExLDAsMSxZXlha;S.variables=JVRFWFRFJTptb250ZXh0ZTolQ09ERSU6MTIzNDU=;S.standardCharsets=US_ASCII;S.verbose=true;end"

// ##########################################################################
// FileName Print
// Extra S.filename contains the name of the template file (refer to printconnect user guide)
// Extra s.variables (optional) contains variable data separated by :
// ##########################################################################

"intent://printtemplatefile/#Intent;scheme=plaintext;package=browserintenturl;S.filename=mytemplate.zpl;S.verbose=true;end"

"intent://printtemplatefile/#Intent;scheme=plaintext;package=browserintenturl;S.filename=mytemplate.zpl;S.variables=%TEXTE%:montexte:%CODE%:12345;S.verbose=true;end"


// ##########################################################################
// Single Line Print
// Extras S.text contains text to print in single line print mode
// ##########################################################################

"intent://printsingleline/#Intent;scheme=plaintext;package=browserintenturl;S.text=This is the text to print;S.verbose=true;end"

// Print line with carriage return encoded in base64 UTF-16 CRLF
"intent://printsingleline/#Intent;scheme=base64encoded;package=browserintenturl;S.text=//5UAGgAaQBzACAAaQBzACAAbQB5ACAAdABlAHgAdAANAAoAVABvACAAcAByAGkAbgB0AA0ACgBFAG4AYwBvAGQAZQBkACAAaQBuACAAYgBhAHMAZQA2ADQADQAKAFcAaQB0AGgAIABjAGEAcgByAGkAYQBnAGUAIAByAGUAdAB1AHIAbgBzAA0ACgANAAoA;S.standardCharsets=UTF_16;S.verbose=true;end"

// ##########################################################################
// Passthrough Print
// Extras S.data contains the data to send to the printer (see PrintConnect user guide for more information on passthrough printing)
// ##########################################################################

"intent://passthrough/#Intent;scheme=plaintext;package=browserintenturl;S.data=CT~~CD,~CC^~CT~^XA~TA000~JSN^LT0^MNW^MTD^PON^PMN^LH0,0^JMA^PR5,5~SD10^JUS^LRN^CI0^XZ^XA^MMT^PW406^LL0200^LS0^FT38,83^A0N,27,28^FH\\^FDPierre d\'ouverture, ^FS^FT38,116^A0N,27,28^FH\\^FDli\\82e au chakra du coeur et ^FS^FT38,149^A0N,27,28^FH\\^FD\\85 v\\82nus apaisante, favorise ^FS^FT38,182^A0N,27,28^FH\\^FDla gu\\82rison des \\82motions^FS^FT107,44^A0N,39,38^FH\\^FDMALACHITE^FS^FO304,29^GB79,0,8^FS^FO13,29^GB79,0,8^FS^PQ1,0,1,Y^XZ;S.verbose=true;end"


// ##########################################################################
// Unselect printer
// ##########################################################################

"intent://unselect/#Intent;scheme=plaintext;package=browserintenturl;S.verbose=true;end"



