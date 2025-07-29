package xml;

import unicode.font_colors_styles;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Scans and parses XML files to extract and organize data
 *
 * - Reads an XML file (demo-patch-opc.xml)
 * - Traverses XML nodes (grandparent, parent, and child nodes)
 * - Collects attributes like NodeId, DisplayName, and references
 * - Stores data in ArrayLists (IDs, names, parents, children)
 * - Optionally prints the parsed structure and data matrix
 * - Includes methods to handle mapping XML (scanMappingXML)
 * 
 * @author jatkinson
 */

public class ScanXMLFiles {
    
        // xml file to scan
    public String xmlDempModel = "demo-model.xml";               // Name of .xml File To Analysis
    String xmlConfig = "Config.xml";                   // Not Used Yet
    String xmlMapping = "mapping.xml";                // Not Used Yet
    public String ubuntuFilePath = "/example/location/app/config/demo-model.xml";
    public String personalFilePath = "demo-patch-opc.xml";
    //public String filePath = xmlFileOne;
   public String filePath = personalFilePath;
    //  MyFunctions fun = new MyFunctions();
    //build data 
    public ArrayList dataMatrix = new ArrayList();             // No longer Needed
    public ArrayList idList = new ArrayList();                 // GRPC Reference Name
    public ArrayList nameList = new ArrayList();               // Display Name
    public ArrayList shortNameList = new ArrayList();          // ShortNames 
    public ArrayList parentList = new ArrayList();             // Parent Node GRPC Reference Name
    public ArrayList parentNameList = new ArrayList();
    public ArrayList childTotalList = new ArrayList();         // Total # of Child Nodes
    public ArrayList childArrayList = new ArrayList();         // Array of Child Nodes
    public ArrayList voltageList = new ArrayList();
    public ArrayList currentList = new ArrayList();

    int len;                                            // Size() ArrayLists (Above) -- See Method updateListLength()
    int childTotal;                                     // tmpVariable That Counts Child Nodes
    //Pointers to location in arrays
    int rowNum = 0;
    int rowPointer = 0;
    int idPointer = 1;
    int namePointer = 2;
    int parentPointer = 3;
    int childTotalPointer = 4;
    int childPointer = 5;
    int listLength = 6;                                 // Inital List Length
    String UAModelerVersion = "";
    public boolean FLGprint = false;                                               // Prints All Info As Program Runs
    public boolean FLGprtMatrix = false;                                            // Prints Output Data

    
    
        String parentId;
    public void scanDoc() {
        
        
        // Initalize Variables
        rowNum = 0;
        int childTotal;
        String nodeId;
        String displayName;
        int gParentTest = 0;
        int parentTest = 0;
        int childTest = 0;
        int thisrow = 0;
   
        try {
            
            File demoFile = new File("C:\\Users\\example\\location\\NetBeansProjects\\myApp\\demo-patch-opc.xml"); //filepath
            DocumentBuilderFactory docFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder
                    = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse("demo-patch-opc.xml");
            Node UANodeSet = doc.getFirstChild();                               //find <root> node : UANodeSet

////////////////////////////////////////////////////////////////////////////////
//Grand//Parent//Node///////////////////////////////////////////////////////////
            NodeList gParentList = UANodeSet.getChildNodes();
            //UAModelerVersion = doc.getElementsByTagName("ModelInfo").item(0).getAttributes().getNamedItem("Version").getTextContent();
                               // UAModelerVersion=parentElement
       //     }
            for (int gParent = 0; gParent < UANodeSet.getChildNodes().getLength(); gParent++) {
                rowNum = thisrow;
                Node gParentNode = gParentList.item(gParent);

                List<String> childIdList = getArray();
                childTotal = 0;                            // Initalize Child Total Count #
                List<String> tmpList = createList();       // Create New Blank List
                if (gParentNode.getNodeType() == gParentNode.ELEMENT_NODE) {
                    Element gParentElement = (Element) gParentNode;
                    gParentTest = gParent; // Update Test Value
                    if (FLGprint) {
                        if (gParentTest == gParent) {
                            gParentTest++; // don't print till next gparent node
                            System.out.println(String.format("Grand Parent Node(s): %s", gParentElement.getNodeName()));
                        }
                    } 

                    NamedNodeMap gParentAttr = gParentNode.getAttributes();
                    for (int gPAttr = 0; gPAttr < gParentAttr.getLength(); gPAttr++) {
                        String nodeName = gParentAttr.item(gPAttr).getNodeName();
                        if (true == nodeName.contentEquals("NodeId")) {
                            nodeId = gParentAttr.item(gPAttr).getTextContent(); //saves Node ID #
                            nodeId = nodeId.substring(7);
                            setId(tmpList, nodeId);                      // Adding nodeID to the tmpList
                            sendId(tmpList.get(idPointer));              // Send ID # to idList
                            thisrow++;
                            if (FLGprint) {
                                System.out.print(String.format("                                                Node ID:%s", nodeId));
                            }
                        }
                    }//FOR--gParents--Attributes        
////////////////////////////////////////////////////////////////////////////////
//Parent//Node//////////////////////////////////////////////////////////////////
                    NodeList parentList = gParentNode.getChildNodes();
                    for (int parent = 0; parent < parentList.getLength(); parent++) {
                        Node parentNode = parentList.item(parent);              // Cycles thru Child Node(s) of gParents
                        if (parentNode.getNodeType() == parentNode.ELEMENT_NODE) {
                            Element parentElement = (Element) parentNode;
                            parentTest = parent;                                // Updates the Parent Node Counter
                            
                            if ("DisplayName".equals(parentElement.getNodeName())) {
                                displayName = parentElement.getTextContent();
                                setName(tmpList, displayName);
                                sendName(tmpList.get(namePointer));      // Send Name to Array
                                if (FLGprint) {
                                    System.out.print(String.format("                                                Display Name:%s", displayName));
                                } 
                            } 
                            if (FLGprint) {
                                if (parentTest == parent) {
                                    parentTest++;  
                                    System.out.println(String.format("----->Parent Node(s): %s", parentElement.getNodeName()));
                                } // IF -- Checking If Parent Node Changed
                            } 
////////////////////////////////////////////////////////////////////////////////
//Child//Node///////////////////////////////////////////////////////////////////                   
                            NodeList childList = parentNode.getChildNodes();

                            for (int child = 0; child < childList.getLength(); child++) {
                                Node childNode = childList.item(child);

                                if (childNode.getNodeType() == childNode.ELEMENT_NODE) {
                                    Element childElement = (Element) childNode;
                                    childTest = child;                          // Signaling That Program Moved Onto Next Child Node
                                    if ("Reference".equals(childElement.getNodeName())) {
                                        // Only gointo this IF Statement if there's a "References" parent node
                                        // Looks thru all elements then goes through attributes then gets element text as needed
                                        NodeList childRefList = childElement.getElementsByTagName("Reference"); //list of all elements Reference
                                        int attrNum = childElement.getAttributes().getLength(); // Find # of Attributes For Every Element

                                        for (int ref = 0; (ref < attrNum); ref++) {
                                            // Access to  > UANodeSet>>UAObject>> References >> Reference 
                                            // This is Where All Child & Parent Id #'s Are Listed 
                                            String referenceType = childElement.getAttributes().getNamedItem("ReferenceType").getTextContent();
                                            boolean hasCom = referenceType.contentEquals("HasComponent");
                                            if (attrNum == 2) {                 // Only The Parent Id # Is Listed When There Is (2) Attributes per Element 
                                                String isForwarding = childElement.getAttributes().getNamedItem("IsForward").getTextContent();
                                                boolean isRoot = referenceType.contentEquals("Organizes");
                                                boolean notForwarding = isForwarding.contentEquals("false");

                                                if (notForwarding) {
                                                    if (isRoot) {
                                                        // Checking For Root Node (No Parent)
                                                        // Addparent Id to list
                                                        parentId = "root";
                                                        setParent(tmpList, "root");            // Adds Parent ID# to tmpList
                                                    }//IF-root check
                                                    else if (hasCom) {
                                                        // Geting Parent ID# If NOT Root Node
                                                        parentId = childElement.getTextContent();
                                                        parentId = parentId.substring(7);            // Gets Last 4 Digits of Text
                                                        // Add Parent ID# to Lists: 
                                                        setParent(tmpList, parentId);         // Adds Parent ID# to tmpList

                                                    } // ELSE IF -- Checking For Parent ID #
                                                } // IF -- not-forwarding = false   
                                            } // IF -- Checking For Parent Node
                                            // Adding Child ID#            
                                            else if (attrNum == 1) {
                                                // This Locates the Child Node Id#('s) 
                                                if (hasCom) {
                                                    String childId = childElement.getTextContent();
                                                    childId = childId.substring(7);
                                                    childTotal++;                                   // Inc # of Children by 1
                                                    tmpList.add("");                                // Adds a Blank Index to Add Child
                                                    if (FLGprint) {
                                                        System.out.println(String.format("=====================Child Id #...........%s", childId));
                                                    }
                                                    // Add Parent Id# to Lists
                                                    addChild(tmpList, childId);
                                                    addChildArray(childIdList, childId);
                                                } // IF -- hasComponent 
                                            } // IF -- Attributte(s) = 1
                                        } // FOR -- Cycle thru Reference Nodes
                                    } // IF -- Reference Node

                                    if (FLGprint) {
                                        if (childTest == child) {
                                            childTest++;   //don't print till next node
                                            System.out.println(String.format("---------->Child Node(s): %s", childElement.getNodeName()));
                                        } // IF -- Print Flag 
                                    } // IF -- Child Print Statement
                                } // IF -- Child is Element Node
                            } // FOR -- Every Child Node
                        } // IF -- Parent is Element Node  
                    } // FOR -- Every Parent Node
                    tmpList.set(childTotalPointer, Integer.toString(childTotal)); // Sends Total# of Child Nodes to tmpList
                    boolean listCheck = "x".equals(tmpList.get(idPointer));
                    if (listCheck == false) {
                        // IMPORTAINT -- Only sends Array to dataMatrix If an ID# was found in tmpList
                        sendArray(tmpList);                              //appends array to DataMatrix
                        sendParent(parentId);                            // Sends Parent ID# to parentList
                        sendChildTotal(childTotal);                      //Sends Total# of Child Nodes to ChildTotalList
                        sendChildArray(childIdList);                     // Sends Child List<String> to childArrayList
                        if (FLGprint) {
                            System.out.println(String.format("tmpList---sending--=-=-=-=-=-=-=-  %s", tmpList));
                        } // IF -- Print FLG
                    } // IF -- Has Node ID Send To Matrix
                } // IF -- GrandParent is Element Node
            } // FOR -- Every GrandParent Node

            // write the content on console
            TransformerFactory transformerFactory
                    = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            // Update Length of Lists & Create Blank List
            updateListLength();
            setShortNames();                   // Update Values in shortNameList thru Method
            if (FLGprtMatrix) {
                System.out.println("------------------------------------------------------------------------------");
                System.out.println("------------------------------------------------------------------------------");
                System.out.println("------------------------------------------------------------------------------");
                System.out.println("------------------------------------------------------------------------------");
                for (int array = 0; array < dataMatrix.size(); array++) {
                    System.out.println(dataMatrix.get(array));
                }
                System.out.println("------------------------------------------------------------------------------");
                System.out.println("------------------------------------------------------------------------------");
                System.out.println("------------------------------------------------------------------------------");
                System.out.println(String.format("---List Name----------<length>--Contents of Lists----------------------------------------------------------------"));
                System.out.println(String.format("Id List:                <%s>%s", idList.size(), idList));
                System.out.println(String.format("Name List:              <%s>%s", nameList.size(), nameList));
                System.out.println(String.format("Short Name List:        <%s>%s", shortNameList.size(), shortNameList));
                System.out.println(String.format("Parent List:            <%s>%s", parentList.size(), parentList));
                System.out.println(String.format("Total Children List:    <%s>%s", childTotalList.size(), childTotalList));
                System.out.println(String.format("Child Array List:       <%s>%s", childArrayList.size(), childArrayList));
            } // IF -- Print Statement (PRTflg = true)

        } // TRY
        catch (Exception e) {
            e.printStackTrace();
        } // CATCH  

    } // METHOD -- ScanDoc()   
    
    
     public void scanMappingXML(){
        Colors co = new Colors();
//        HashThat hash = new HashThat();
          String xmlConfig = "Config.xml";                   
          String xmlMapping = "demo-mappings.xml";              
          int catgCounter =0; 
          int nodeNum = 0;
           int thisNode = 0;
           int catgTest = 0;
           int sCatgTest =0;
           int nxtCatgTest =0;
           int inNxtTest =0;
           int fourTest = 0;
           int fiveTest =0;
           int sixTest = 0;
           
           boolean printFLG = true;
        try {
           // ArrayList tmpList = new ArrayList();
            Hashtable gpeTags = new Hashtable();
            Hashtable grpcTags = new Hashtable();
            Hashtable pointIdTags = new Hashtable();
            
            File demoFile = new File(xmlMapping); //filepath
            DocumentBuilderFactory docFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder
                    = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(demoFile);
            Node rootNodeSet = doc.getFirstChild();                               //find <root> node : <Converter>
            NodeList catgNodeList = rootNodeSet.getChildNodes();
            int catgNodeNums = catgNodeList.getLength();
            
             Hashtable tmpMrowHash = new Hashtable();
            Hashtable chHash = new Hashtable();
            Hashtable tmpFourRowHash = new Hashtable();
            Hashtable tmpGPEmapHash = new Hashtable();
           // boolean[] catgCheck = new boolean[catgNodeNums];
            //Arrays.fill(catgCheck, Boolean.FALSE);
            for (int catgIdx = 0; catgIdx < catgNodeNums; catgIdx++){
                
                
                Node catgNode = catgNodeList.item(catgIdx);
                nodeNum = thisNode;
                
                ArrayList tmpList = new ArrayList();
                
                if (catgNode.getNodeType() == catgNode.ELEMENT_NODE){
                    //catgCheck[catgCounter] = true;
                    catgCounter++;
                    //if (printFLG){System.out.println("Boolean List: " + catgCheck.toString());}
                    Element catgElement = (Element) catgNode;
                    catgTest = catgIdx;
                    String catgNodeName = catgElement.getNodeName();
                    if (printFLG){
                        if (catgTest == catgIdx){
                            catgTest++;
                            System.out.println("Category Node: " + co.bBLUE + catgNodeName + co.RESET);
                    }   } // IFs -- printFLG
                NodeList sCatgNodeList = catgNode.getChildNodes();
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//sCatgNode////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//sCatgNode////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                for (int sCatgIdx = 0; sCatgIdx < sCatgNodeList.getLength(); sCatgIdx++){
                    Node sCatgNode = sCatgNodeList.item(sCatgIdx);
                    if (sCatgNode.getNodeType() == sCatgNode.ELEMENT_NODE){
                        Element sCatgElement = (Element) sCatgNode;
                        sCatgTest = sCatgIdx;
                        String sCatgNodeName = sCatgElement.getNodeName();
                      if (printFLG){System.out.println("\tSub Category Node:  " + co.BLUE + sCatgNodeName + co.RESET);}
                    NodeList nxtCatgNodeList = sCatgNode.getChildNodes();
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//nxtCatgNode//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    for (int nxtCatgIdx = 0; nxtCatgIdx < nxtCatgNodeList.getLength(); nxtCatgIdx++){
                        Node nxtCatgNode = nxtCatgNodeList.item(nxtCatgIdx);
                        if (nxtCatgNode.getNodeType() == nxtCatgNode.ELEMENT_NODE){
                            Element nxtCatgElement = (Element) nxtCatgNode;
                            nxtCatgTest = nxtCatgIdx;
                            String nxtCatgNodeName = nxtCatgElement.getNodeName();
                           // System.out.println("Channel".substring(0, 7));
//                          String uniqSeqNum = nxtCatgElement.getAttributes().getNamedItem("uniqueSeqNo").getTextContent(); 
//                          String grpcId = nxtCatgElement.getAttributes().getNamedItem("uniqueSeqNo").getTextContent(); 
//                          String pointId = nxtCatgElement.getAttributes().getNamedItem("pointid").getTextContent(); 
                        String grpcId = "";
                        String pointId = "";  
                        if (sCatgNodeName.equals("OPC_TAGS_OTHERMAPPING")){
                              grpcId = nxtCatgElement.getAttributes().getNamedItem("uniqueSeqNo").getTextContent(); 
                              pointId = nxtCatgElement.getAttributes().getNamedItem("pointid").getTextContent(); 
                              grpcTags.put(pointId, grpcId);
                          }
                          if (sCatgNodeName.equals("GPE_TAGS")){
                              String gpeId = nxtCatgElement.getAttributes().getNamedItem("uniqueSeqNo").getTextContent(); 
                             grpcId = nxtCatgElement.getAttributes().getNamedItem("uniqueSeqNo").getTextContent(); 
                              pointId = nxtCatgElement.getAttributes().getNamedItem("pointid").getTextContent(); 
                              gpeTags.put(pointId, grpcId);
                          }
                          String opcId = nxtCatgElement.getAttributes().getNamedItem("pointid").getTextContent(); 
                            if (printFLG){System.out.println("\t\tNext Category Node: " + co.GREEN+ nxtCatgNodeName + co.BLUE +"    :  " + grpcId+ "    : pointId:  "+pointId + co.RESET);} // IF -- printFLG
                            
                            NodeList inNxtNodeList = nxtCatgNode.getChildNodes();
                            
                           

                                                
                        }
                    
                    }
                    pointIdTags.put("grpc", grpcTags);
                    pointIdTags.put("gpe", gpeTags);
                    }
                }
                }
            }    
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        
    }
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
       public static void main(String arg[]) {
          ScanXMLFiles scan = new ScanXMLFiles();
          scan.scanMappingXML();
            //scan.scanConfigXML();
       }


///////////////////////////////////////////////////////////////////     
//Searching/Finding/Locating/Creating//////////////////////////////
///////////////////////////////////////////////////////////////////   

    public void updateListLength() { 
        len = idList.size();
    }

    public void setShortNames() {
        for (int index = 0; index < len; index++) {
            len = idList.size();
            shortNameList.add(getShortName(index));
        }
    } 
    
    public int getIndex(String id){
        int index = 99;
        for (int i=0; i < idList.size(); i++){
            if (id.equals( idList.get(i)) ){
                index = i;
                return index;
            }
        }
        return index;
    }
    
    public String getShortName(int index) {
        //updateListLength();
        String tmpName;
        String name = String.valueOf(nameList.get(index));
        String firstLetter = name.substring(0, 1);
        String lastLetter;
        String shortName;
        String abbv;
        if ((name.contains("_"))) {
            int ind = name.indexOf("_");
            lastLetter = name.substring(ind - 1, ind);
        } else {
            int ind = name.length();
            lastLetter = name.substring(ind - 1, ind);
        }
        abbv = String.format("%s%s", firstLetter, lastLetter);
        String parentId = String.valueOf(parentList.get(index));
        int valueId = Integer.valueOf(String.valueOf(idList.get(index)));
        if (valueId > 5999) {
            shortName = String.format("--%s--", idList.get(index));
        } else if ("x".equals(parentId)) {
            shortName = "-";
        } else if ("root".equals(parentId)) {
            shortName = abbv;
        } else {
            int parentIndex = getIndex(parentId);
            shortName = String.format("%s%s", shortNameList.get(parentIndex), abbv);
        }
        //shortNameList.set(index, shortName);
        return shortName;
    }

    public ArrayList getBlankList() {
        updateListLength();
        ArrayList sendList = new ArrayList();
        for (int i = 0; i < len; i++) {
            sendList.add("");
        }
        return sendList;
    }

///////////////////////////////////////////////////////////////////     
//Append values to specified List(s)///////////////////////////////
///////////////////////////////////////////////////////////////////        
    public void sendArray(List<String> getArray) { //add new array into dataMatrix
        dataMatrix.add(getArray);
    }

    public void sendId(String id) { //adds id to idList
        idList.add(id);
    }

    public void sendName(String name) { //adds display name to nameList
        nameList.add(name);
    }

    public void sendParent(String parent) { //adds parent id # to parentList
        parentList.add(parent);
    }

    public void sendChildTotal(int total) {
        childTotalList.add(total);
    }

    public List<String> getArray() {
        ArrayList getArray = new ArrayList();
        return getArray;
    }

    public List<String> addChildArray(List<String> getArray, String value) {
        getArray.add(value);
        return getArray;
    }

    public void sendChildArray(List<String> getArray) {
        childArrayList.add(getArray);
    }

    public void sendVoltage(String value) { // ads last read voltage to voltageList
        voltageList.add(value);
    }

    public void sendCurrent(String value) { // adds last read current to currentList
        currentList.add(value);
    }

    
///////////////////////////////////////////////////////////////////     
//Creates Lists, Gets and Sets Value of Specified Lists////////////
///////////////////////////////////////////////////////////////////   
    public List<String> createList() { //initalizes the list to all 'x'so you know what is overrun
        String row = Integer.toString(rowNum);
        List<String> myList = new ArrayList<>();
        myList.add(row); //row#
        for (int entry = 0; entry < listLength; entry++) {
            //create an initalize array with "x" (space holders)
            if (entry < 4) {
                myList.add("x");
            }
            //elsemyList.add("x");
        }//FOR create array
        return myList;
    } 

    public List<String> setId(List<String> getArray, String id) { // Sets Index of tmpList to 

        getArray.set(idPointer, id);
        childPointer = 5; // Resetting child Pointer to  5
        return getArray;
    }

    public List<String> setName(List<String> getArray, String name) {
        getArray.set(namePointer, name);
        return getArray;
    }

    public List<String> setParent(List<String> getArray, String parentId) {
        getArray.set(parentPointer, parentId);

        return getArray;
    }

    public List<String> addChild(List<String> getArray, String childId) {//add child to array
        getArray.set(childPointer, childId);
        childPointer++;
        return getArray;
    }

}
