package loaders.collada;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class ColladaParser {
    private static Collada collada = null;

    public static Collada getCollada(File file) {
        collada = new Collada();
        try {
            DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fact.newDocumentBuilder();
            method(builder.parse(file));
        } catch (Exception ex) {}
        Collada toReturn = collada;
        collada = null;
        return toReturn;
    }

    private static void method(Document doc){
        Node node = doc.getDocumentElement();
        System.out.println(node.getNodeName());
        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            String child = node.getChildNodes().item(i).getNodeName();
            if(child.equals("asset")) asset(node.getChildNodes().item(i));
        }
    }

    private static void asset(Node assetNode){
        Asset asset = new Asset();
        asset.setComments("not parseable yet");
        asset.setKeywords("not parseable yet");
        asset.setRevision("not parseable yet");
        asset.setSubject("not parseable yet");
        asset.setTitle("not parseable yet");
        asset.setUpAxis("not parseable yet");
        asset.setCreated("not parseable yet");
        asset.setModified("not parseable yet");
        //
        for (int i = 0; i < assetNode.getChildNodes().getLength(); i++) {
            Node child = assetNode.getChildNodes().item(i);
            String id = child.getNodeName();
            if(id.equals("contributor")){
                for (int j = 0; j < child.getChildNodes().getLength(); j++) {
                    Node node = child.getChildNodes().item(j);
                    String nid = node.getNodeName();
                    if(nid.equals("author")) asset.setAuthor(node.getTextContent());
                    if(nid.equals("authoring_tool")) asset.setAuthoringTool(node.getTextContent());
                }
            }
            if(id.equals("unit")){                
                asset.setUnitName(child.getAttributes().item(0).getNodeName());
                asset.setUnitValue(Float.parseFloat(child.getAttributes().item(0).getNodeValue()));
            }
        }
        //
        collada.setAsset(asset);
    }
}