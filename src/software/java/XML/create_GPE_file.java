package xml;

import UniCode.Colors;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Creates a GPE XML file with a fixed structure.
 * 
 * - Asks user how many data points to add
 * - Builds an XML document with EDGE_MAP as the root
 * - Adds EDGECOM_TAGS with GPE_TAGS, GRPC_TAGS, MEM_TAGS, OPC_TAGS
 * - Loops to create MAPENTRY elements with unique IDs
 * - Adds EDGECOM_MAPS with other mapping tags
 * - Saves the XML to console and to myFilePath
 * 
 * @author jatkinson
 */


public class createGPEFile {
    public static final String myFilePath = "C:\\Users\\example\\location\\NetBeansProjects\\WriteHere";

    public void getGPEFile() throws IOException{
        
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("How many Data Points would you like to enter?");
            String quest = br.readLine();
            int questInt = Integer.parseInt(quest);
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root element -- EDGE_MAP
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("EDGE_MAP");
            doc.appendChild(rootElement);
            // add Attribute to root element
            Attr attr = doc.createAttribute("version");
            attr.setValue("1.0");
            rootElement.setAttributeNode(attr);

            // add EDGECOM_TAGS
            Element ecTags = doc.createElement("EDGECOM_TAGS");
            rootElement.appendChild(ecTags);
            
                // Add GPE_TAGS
                Element gpeTags = doc.createElement("GPE_TAGS");
                ecTags.appendChild(gpeTags);
                gpeTags.setAttribute("description", "");
            
                Element grpcTags = doc.createElement("GRPC_TAGS");
                grpcTags.setAttribute("description", "");
                ecTags.appendChild(grpcTags);
               
            for (int idx = 0; idx < questInt; idx++) {
                String uniqSeqNum = String.valueOf(200+idx) ;
                String pointId = String.valueOf(8000+idx) ;
                Element mapEntry = doc.createElement("MAPENTRY"); // 
                grpcTags.appendChild(mapEntry);
                mapEntry.setAttribute("pointId", pointId ) ;
                mapEntry.setAttribute("uniqueSeqNo", uniqSeqNum) ;
                
            }
            /*
                Element map2Entry = doc.createElement("MAPENTRY"); // 
                grpcTags.appendChild(map2Entry);
                Element map3Entry = doc.createElement("MAPENTRY"); //
                grpcTags.appendChild(map3Entry);
                Element map4Entry = doc.createElement("MAPENTRY"); //
                grpcTags.appendChild(map4Entry);
                Element map5Entry = doc.createElement("MAPENTRY"); //
                grpcTags.appendChild(map5Entry);
            */
                Element memTags = doc.createElement("MEM_TAGS"); // 
                ecTags.appendChild(memTags);
                memTags.setAttribute("description", "");
                Element opcTags = doc.createElement("OPC_TAGS"); // 
                ecTags.appendChild(opcTags);
                opcTags.setAttribute("description","");
            // Add EDGECOM_MAPS
            Element ecMaps = doc.createElement("EDGECOM_MAPS");
            rootElement.appendChild(ecMaps);
            
                Element opcTagsOtherMap = doc.createElement("OPC_TAGS_OTHERMAPPING");
                ecMaps.appendChild(opcTagsOtherMap);
                Element grpcTagsOtherMap = doc.createElement("GRPC_TAGS_OTHERMAPPING");
                ecMaps.appendChild(grpcTagsOtherMap);
                Element gpeTagsOtherMap = doc.createElement("GPE_TAGS_OTHERMAPPING");
                ecMaps.appendChild(gpeTagsOtherMap);
            
           
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
//		StreamResult result = new StreamResult(new File("C:\\file.xml"));
            StreamResult result = new StreamResult(System.out);
            StreamResult saveXMLresult = new StreamResult(new File(myFilePath));
            // Output to console for testing
//		 StreamResult result = new StreamResult(new File( ));
            transformer.transform(source, result);
            transformer.transform(source, saveXMLresult);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    } 

    
    public static void main(String argv[]) throws IOException {
        createGPEFile
        ScanXMLFiles scan = new ScanXMLFiles();
        scan.scanDoc();

    }
}