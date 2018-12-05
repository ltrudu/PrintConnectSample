Documentation:

Le code utilise PrintConnect pour effectuer l'appairage avec l'imprimante:
https://www.zebra.com/us/en/products/software/barcode-printers/link-os/print-connect.html

Documentation de PrintConnect (dans la section Intents se trouve la description des méthodes implémentées par le wrapper):
https://www.zebra.com/content/dam/zebra_new_ia/en-us/solutions-verticals/product/Software/Printer%20Software/Link-OS/print-connect/PC-UserGuide-P1082444-001.pdf

PrintConnect est disponible sur le Google play store, mais vous le trouverez dans le dossier partagé.
Il faut l'installer sur le device, puis installer BrowserIntentURLServices-release.apk

Le code source du wrapper est disponible à l'adresse suivante:
https://github.com/ltrudu/PrintConnectSample
Veuillez prendre connaissance de la licence Zebra associée à ce projet avant toute utilisation.

L'imprimante doit être appairée à l'aide de PrintConnect pour que le wrapper fonctionne.
Pour cela, il suffit d'installer PrintConnect, puis de faire par ex un appairage NFC avec une imprimante mobile bluetooth ou un appairage Wifi/Ethernet.
Le process est expliqué dans le user guide de PrintConnect. 

Les commandes peuvent s'executer en utilisant un href dans un élément HTML ou simplement en utilisant la méthode window.open("");

Exemple d'impression en mode ligne à ligne utilisant la methode javascript window.open
window.open("intent://printsingleline/#Intent;scheme=plaintext;package=com.zebra.browserintenturl;S.text=This is the text to print;S.verbose=true;end")

L'extra S.verbose permet de controler l'affichage de popups d'informations ou non (true or false)

Les données peuvent être encodées en base64 ou en texte plein.
Le choix se fait à l'aide de l'attribut scheme qui peut prendre les valeurs plaintext ou base64encoded
L'extra S.standardCharsets spécifie le type de la chaine encodée en base64 peut prendre les valeurs suivantes : UTF_8, UTF_16, UTF_16BE, UTF_16LE, ISO_8859_1, US_ASCII

Les tests d'encodage ont été faits avec le site suivant:
https://www.base64encode.org/

Vous pouvez utiliser l'API suivante (licence MIT) pour encoder directement depuis javascript:
https://github.com/emn178/hi-base64

Si vous utilisez des chaines non encodées, il est préférable de les "escaper" avant de les passer à l'url.
https://www.freeformatter.com/javascript-escape.html

Voici les urls disponibles pour imprimer:

// ##########################################################################
// Print directZPL
// Extras: S.template contient le code ZPL
// ##########################################################################

"intent://printzpl/#Intent;scheme=plaintext;package=com.zebra.browserintenturl;S.template=CT~~CD,~CC^~CT~^XA~TA000~JSN^LT0^MNN^MTD^PON^PMN^LH0,0^JMA^PR5,5~SD10^JUS^LRN^CI0^XZ^XA^MMT^PW406^LL0200^LS0^FT38,83^A0N,27,28^FH\\^FDPierre d\'ouverture, ^FS^FT38,116^A0N,27,28^FH\\^FDli\\82e au chakra du coeur et ^FS^FT38,149^A0N,27,28^FH\\^FD\\85 v\\82nus apaisante, favorise ^FS^FT38,182^A0N,27,28^FH\\^FDla gu\\82rison des \\82motions^FS^FT107,44^A0N,39,38^FH\\^FDMALACHITE^FS^FO304,29^GB79,0,8^FS^FO13,29^GB79,0,8^FS^PQ1,0,1,Y^XZ;S.verbose=true;end"


// ASCII Base64 Encoding 
"intent://printzpl/#Intent;scheme=base64encoded;package=com.zebra.browserintenturl;S.template=Q1R+fkNELH5DQ15+Q1R+XlhBflRBMDAwfkpTTl5MVDBeTU5XXk1URF5QT05eUE1OXkxIMCwwXkpNQV5QUjUsNX5TRDEwXkpVU15MUk5eQ0kwXlhaXlhBXk1NVF5QVzQwNl5MTDAyMDBeTFMwXkZUMzgsODNeQTBOLDI3LDI4XkZIXF5GRFBpZXJyZSBkJ291dmVydHVyZSwgXkZTXkZUMzgsMTE2XkEwTiwyNywyOF5GSFxeRkRsaVw4MmUgYXUgY2hha3JhIGR1IGNvZXVyIGV0IF5GU15GVDM4LDE0OV5BME4sMjcsMjheRkhcXkZEXDg1IHZcODJudXMgYXBhaXNhbnRlLCBmYXZvcmlzZSBeRlNeRlQzOCwxODJeQTBOLDI3LDI4XkZIXF5GRGxhIGd1XDgycmlzb24gZGVzIFw4Mm1vdGlvbnNeRlNeRlQxMDcsNDReQTBOLDM5LDM4XkZIXF5GRE1BTEFDSElURV5GU15GTzMwNCwyOV5HQjc5LDAsOF5GU15GTzEzLDI5XkdCNzksMCw4XkZTXlBRMSwwLDEsWV5YWg==;S.verbose=true;end"

// UTF-16 Encoding
"intent://printzpl/#Intent;scheme=base64encoded;package=com.zebra.browserintenturl;S.template=//5DAFQAfgB+AEMARAAsAH4AQwBDAF4AfgBDAFQAfgBeAFgAQQB+AFQAQQAwADAAMAB+AEoAUwBOAF4ATABUADAAXgBNAE4AVwBeAE0AVABEAF4AUABPAE4AXgBQAE0ATgBeAEwASAAwACwAMABeAEoATQBBAF4AUABSADUALAA1AH4AUwBEADEAMABeAEoAVQBTAF4ATABSAE4AXgBDAEkAMABeAFgAWgBeAFgAQQBeAE0ATQBUAF4AUABXADQAMAA2AF4ATABMADAAMgAwADAAXgBMAFMAMABeAEYAVAAzADgALAA4ADMAXgBBADAATgAsADIANwAsADIAOABeAEYASABcAF4ARgBEAFAAaQBlAHIAcgBlACAAZAAnAG8AdQB2AGUAcgB0AHUAcgBlACwAIABeAEYAUwBeAEYAVAAzADgALAAxADEANgBeAEEAMABOACwAMgA3ACwAMgA4AF4ARgBIAFwAXgBGAEQAbABpAFwAOAAyAGUAIABhAHUAIABjAGgAYQBrAHIAYQAgAGQAdQAgAGMAbwBlAHUAcgAgAGUAdAAgAF4ARgBTAF4ARgBUADMAOAAsADEANAA5AF4AQQAwAE4ALAAyADcALAAyADgAXgBGAEgAXABeAEYARABcADgANQAgAHYAXAA4ADIAbgB1AHMAIABhAHAAYQBpAHMAYQBuAHQAZQAsACAAZgBhAHYAbwByAGkAcwBlACAAXgBGAFMAXgBGAFQAMwA4ACwAMQA4ADIAXgBBADAATgAsADIANwAsADIAOABeAEYASABcAF4ARgBEAGwAYQAgAGcAdQBcADgAMgByAGkAcwBvAG4AIABkAGUAcwAgAFwAOAAyAG0AbwB0AGkAbwBuAHMAXgBGAFMAXgBGAFQAMQAwADcALAA0ADQAXgBBADAATgAsADMAOQAsADMAOABeAEYASABcAF4ARgBEAE0AQQBMAEEAQwBIAEkAVABFAF4ARgBTAF4ARgBPADMAMAA0ACwAMgA5AF4ARwBCADcAOQAsADAALAA4AF4ARgBTAF4ARgBPADEAMwAsADIAOQBeAEcAQgA3ADkALAAwACwAOABeAEYAUwBeAFAAUQAxACwAMAAsADEALABZAF4AWABaAA==;S.standardCharsets=UTF_16;S.verbose=true;end"


// ##########################################################################
// Direct ZPL with Variable data
// Extras: 
// S.template contient le code ZPL
// s.variables (optionnel) contient les données variables séparées par :
// ##########################################################################

"intent://printzpl/#Intent;scheme=plaintext;package=com.zebra.browserintenturl;S.template=CT~~CD,~CC^~CT~^XA~TA000~JSN^LT0^MNN^MTD^PON^PMN^LH0,0^JMA^PR5,5~SD10^JUS^LRN^CI0^XZ^XA^MMT^PW406^LL0200^LS0^FT38,83^A0N,27,28^FH\\^FDPierre d\'ouverture, ^FS^FT38,116^A0N,27,28^FH\\^FDli\\82e au %TEXTE% du coeur et ^FS^FT38,149^A0N,27,28^FH\\^FD\\85 v\\82nus apaisante, favorise ^FS^FT38,182^A0N,27,28^FH\\^FDla gu\\82rison des \\82motions %CODE%^FS^FT107,44^A0N,39,38^FH\\^FDMALACHITE^FS^FO304,29^GB79,0,8^FS^FO13,29^GB79,0,8^FS^PQ1,0,1,Y^XZ;S.variables=%TEXTE%:montexte:%CODE%:12345;S.verbose=true;end"


// ASCII Encoding
"intent://printzpl/#Intent;scheme=base64encoded;package=com.zebra.browserintenturl;S.template=Q1R+fkNELH5DQ15+Q1R+XlhBflRBMDAwfkpTTl5MVDBeTU5XXk1URF5QT05eUE1OXkxIMCwwXkpNQV5QUjUsNX5TRDEwXkpVU15MUk5eQ0kwXlhaXlhBXk1NVF5QVzQwNl5MTDAyMDBeTFMwXkZUMzgsODNeQTBOLDI3LDI4XkZIXF5GRFBpZXJyZSBkJ291dmVydHVyZSwgXkZTXkZUMzgsMTE2XkEwTiwyNywyOF5GSFxeRkRsaVw4MmUgYXUgJVRFWFRFJSBkdSBjb2V1ciBldCBeRlNeRlQzOCwxNDleQTBOLDI3LDI4XkZIXF5GRFw4NSB2XDgybnVzIGFwYWlzYW50ZSwgZmF2b3Jpc2UgXkZTXkZUMzgsMTgyXkEwTiwyNywyOF5GSFxeRkRsYSBndVw4MnJpc29uIGRlcyBcODJtb3Rpb25zICVDT0RFJV5GU15GVDEwNyw0NF5BME4sMzksMzheRkhcXkZETUFMQUNISVRFXkZTXkZPMzA0LDI5XkdCNzksMCw4XkZTXkZPMTMsMjleR0I3OSwwLDheRlNeUFExLDAsMSxZXlha;S.variables=JVRFWFRFJTptb250ZXh0ZTolQ09ERSU6MTIzNDU=;S.standardCharsets=US_ASCII;S.verbose=true;end"

// ##########################################################################
// FileName Print
// Extra S.filename contient le nom du fichier template à imprimer (cf. PrintConnect user guide)
// Extra S.variables (optionnel) contient les données variables séparées par :
// ##########################################################################

"intent://printtemplatefile/#Intent;scheme=plaintext;package=com.zebra.browserintenturl;S.filename=mytemplate.zpl;S.verbose=true;end"

"intent://printtemplatefile/#Intent;scheme=plaintext;package=com.zebra.browserintenturl;S.filename=mytemplate.zpl;S.variables=%TEXTE%:montexte:%CODE%:12345;S.verbose=true;end"


// ##########################################################################
// Single Line Print
// Extras S.text contient le texte à imprimer en mode ligne à ligne
// ##########################################################################

"intent://printsingleline/#Intent;scheme=plaintext;package=com.zebra.browserintenturl;S.text=This is the text to print;S.verbose=true;end"

// Print line with carriage return encoded in base64 UTF-16 CRLF
"intent://printsingleline/#Intent;scheme=base64encoded;package=com.zebra.browserintenturl;S.text=//5UAGgAaQBzACAAaQBzACAAbQB5ACAAdABlAHgAdAANAAoAVABvACAAcAByAGkAbgB0AA0ACgBFAG4AYwBvAGQAZQBkACAAaQBuACAAYgBhAHMAZQA2ADQADQAKAFcAaQB0AGgAIABjAGEAcgByAGkAYQBnAGUAIAByAGUAdAB1AHIAbgBzAA0ACgANAAoA;S.standardCharsets=UTF_16;S.verbose=true;end"

// ##########################################################################
// Passthrough Print
// Extras S.data contient les données à envoyer à l'imprimante (cf. PrintConnect user guide pour plus d'informations sur le passthrough printing)
// ##########################################################################

"intent://passthrough/#Intent;scheme=plaintext;package=com.zebra.browserintenturl;S.data=mydata;S.verbose=true;end"


// ##########################################################################
// Unselect printer
// ##########################################################################

"intent://unselect/#Intent;scheme=plaintext;package=com.zebra.browserintenturl;S.verbose=true;end"



