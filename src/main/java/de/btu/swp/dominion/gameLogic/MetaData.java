package de.btu.swp.dominion.gameLogic;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.Toolkit;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MetaData {
    private String styleMetaFileName = "Settings.xml";
    private String styleMetaPath = styleMetaFileName;
    private File styleFile = new File(styleMetaPath);
    private int defaultWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int defaultHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    public MetaData() {
        createDefaultMetaData();
    }

    /** will create a default Settings.xml in the repo */
    private void createDefaultMetaData() {
        // create File
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("dominionSettings");
            document.appendChild(root);

            // settings element
            Element setting = document.createElement("setting");

            root.appendChild(setting);

            // set an attribute to the last Settings
            Attr attr = document.createAttribute("id");
            attr.setValue("1");
            setting.setAttributeNode(attr);

            // PlayerName element
            Element PlayerName = document.createElement("PlayerName");
            PlayerName.appendChild(document.createTextNode("null"));
            setting.appendChild(PlayerName);

            // IP element
            Element ip = document.createElement("ip");
            ip.appendChild(document.createTextNode("null"));
            setting.appendChild(ip);

            // Height element
            Element height = document.createElement("height");
            height.appendChild(document.createTextNode(String.valueOf(defaultHeight)));
            setting.appendChild(height);

            // width element
            Element width = document.createElement("width");
            width.appendChild(document.createTextNode(String.valueOf(defaultWidth)));
            setting.appendChild(width);

            // soundOn element
            Element soundOn = document.createElement("soundOn");
            soundOn.appendChild(document.createTextNode("true"));
            setting.appendChild(soundOn);

            // last Settings date
            Element lastSettingsdate = document.createElement("creationDate");
            lastSettingsdate.appendChild(document.createTextNode(lastSettingDate()));
            setting.appendChild(lastSettingsdate);

            Element fullscreen = document.createElement("fullscreen");
            fullscreen.appendChild(document.createTextNode("false"));
            setting.appendChild(fullscreen);

            // create the xml file
            // transform the DOM Object to an XML File
            if (!styleFile.exists()) {
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(document);
                StreamResult streamResult = new StreamResult(new File("Settings.xml"));

                transformer.transform(domSource, streamResult);
            }

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

    }

    /** well get the date of today and save it in the xml */
    private String lastSettingDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime lastSettings = LocalDateTime.now();
        return dtf.format(lastSettings);
    }

    /** will read from a xml the value of given Tag */
    private String readFromXml(String tagName) {
        String temp = "";
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document;
            document = documentBuilder.parse(styleFile);
            temp = document.getElementsByTagName(tagName).item(0).getTextContent();
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /** get the value of music from the Settings.xml */
    public Boolean getSoundOnMeta() {
        boolean SoundOn;
        SoundOn = Boolean.valueOf(readFromXml("soundOn"));
        return SoundOn;
    }

    /** get the value of Height of the screen from the Settings.xml */
    public double getHeightMeta() {
        double HeightMeta = Double.parseDouble(readFromXml("height"));
        return HeightMeta;
    }

    /** get the value of width of the screen from the Settings.xml */
    public double getWidthMeta() {
        double HeightMeta = Double.parseDouble(readFromXml("width"));
        return HeightMeta;
    }

    /** get the value of Player Name from the Settings.xml */
    public String getPlayerNameMeta() {
        String PlayerName = readFromXml("PlayerName");
        return PlayerName;
    }

    /** get the value of ip from the Settings.xml */
    public String getIpMeta() {
        String ip = readFromXml("ip");
        return ip;
    }

    public Boolean getFullscreen() {
        Boolean screen;
        screen = Boolean.valueOf(readFromXml("fullscreen"));
        return screen;
    }

    /** well save the value of ip in the xml */
    public void setIPMeta(String ip) {
        editXML("ip", ip);
    }

    /** well save the value of PlayerName in the xml */
    public void setPlayerNameMeta(String PlayerName) {
        editXML("PlayerName", PlayerName);
    }

    /** well save the value of sound On in the xml */
    public void setSoundOnMeta(Boolean soundOn) {
        String newSoundOn = soundOn.toString();
        editXML("soundOn", newSoundOn);
    }

    /** well save the value of Height of the Screen On in the xml */
    public void setHeightMeta(double height) {
        String newheight = Double.toString(height);
        editXML("height", newheight);
    }

    /** well save the value of Width of the Screen On in the xml */
    public void setWidthMeta(double width) {
        String newWidth = Double.toString(width);
        editXML("width", newWidth);
    }
    
    public void setFullScreenMeta(Boolean screen) {
        String newFullscreen = screen.toString();
        editXML("fullscreen", newFullscreen);
    }

    /** well modifiy the content of a given tag name in the Setting.xml */
    private void editXML(String tagName, String newValue) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(styleMetaPath);

            // Get the staff element by tag name directly
            Node setting = doc.getElementsByTagName("setting").item(0);

            // loop the staff child node
            NodeList list = setting.getChildNodes();

            for (int i = 0; i < list.getLength(); i++) {

                Node node = list.item(i);

                // get the salary element, and update the value
                if (tagName.equals(node.getNodeName())) {
                    node.setTextContent(newValue);
                }

            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(styleFile);
            transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }

}