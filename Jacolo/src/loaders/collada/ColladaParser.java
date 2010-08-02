package loaders.collada;

import java.io.File;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NamedNodeMap;

import org.w3c.dom.Node;

public class ColladaParser {    

    public static Collada getCollada(File file) {
        Collada collada = null;
        try {
            DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fact.newDocumentBuilder();
            collada = parseCollada(builder.parse(file).getDocumentElement());
        } catch (Exception ex) {
            Logger.getLogger(ColladaParser.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return collada;
    }

    private static Collada parseCollada(Node node){
        Collada collada = new Collada();
        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            String child = node.getChildNodes().item(i).getNodeName();
            Node subNode = node.getChildNodes().item(i);
            if(child.equals("asset")) collada.setAsset(parseAsset(subNode));
            if(child.equals("library_geometries")) collada.setGeometries(parseLibrary_geometries(subNode));
        }
        return collada;
    }

    private static Asset parseAsset(Node assetNode){
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
        return asset;
    }

    private static Geometry[] parseLibrary_geometries(Node library) {
        int length = 0, cursor = 0;
        for (int i = 0; i < library.getChildNodes().getLength(); i++)
            if(library.getChildNodes().item(i).getNodeName().equals("geometry")) length++;
        Geometry[] geometries = new Geometry[length];
        for (int i = 0; i < library.getChildNodes().getLength(); i++)
            if(library.getChildNodes().item(i).getNodeName().equals("geometry"))
                geometries[cursor++] = parseGeometry(library.getChildNodes().item(i));
        return geometries;
    }

    private static Geometry parseGeometry(Node geometry){
        String id = null, name = null; Mesh mesh = null;
        for (int i = 0; i < geometry.getAttributes().getLength(); i++)
            if(geometry.getAttributes().item(i).getNodeName().equals("id"))
                id = geometry.getAttributes().item(i).getNodeValue();
            else if(geometry.getAttributes().item(i).getNodeName().equals("name"))
                name = geometry.getAttributes().item(i).getNodeValue();        
        for (int i = 0; i < geometry.getChildNodes().getLength(); i++)
            if(geometry.getChildNodes().item(i).getNodeName().equals("mesh"))
                mesh = parseMesh(geometry.getChildNodes().item(i));
        return new Geometry(mesh, id, name);
    }

    private static Mesh parseMesh(Node mesh){
        float[] vertexArray = null, normalArray = null, texCoordArray = null;
        int vertexStride = 0, normalStride = 0, texCoordStride = 0;
        int[][] polygonArray = null;
        String vertexSource = null, normalSource = null, texCoordSource = null;
        int polygonsIndex = 0, verticesIndex = 0, verticesInputIndex = 0;
        // FIND POLYGON INDEX
        for (int i = 0; i < mesh.getChildNodes().getLength(); i++)
            if(mesh.getChildNodes().item(i).getNodeName().equals("vertices")){
                verticesIndex = i;
                Node vertices = mesh.getChildNodes().item(i);
                for (int j = 0; j < vertices.getChildNodes().getLength(); j++)
                    if(vertices.getChildNodes().item(j).getNodeName().equals("input")){
                        verticesInputIndex = j;
                        break;
                    }
            } else if(mesh.getChildNodes().item(i).getNodeName().equals("polygons"))
                polygonsIndex = i;
        // FILL SOURCES
        Node polygons = mesh.getChildNodes().item(polygonsIndex);
        int polygonCount = Integer.parseInt(polygons.getAttributes().getNamedItem("count").getNodeValue());
        int polygonCursor = 0;
        int polygonSubCount = 0;        
        for (int j = 0; j < polygons.getChildNodes().getLength(); j++){
            if(polygons.getChildNodes().item(j).getNodeName().equals("p")){
                polygonSubCount = 1+numberOfSpaces(polygons.getChildNodes().item(j).getTextContent());
                break;
            }
        }
        polygonArray = new int[polygonCount][polygonSubCount];
        for (int j = 0; j < polygons.getChildNodes().getLength(); j++){
            if(polygons.getChildNodes().item(j).getNodeName().equals("p")){
                polygonArray[polygonCursor++] = parseIndexArray(
                        polygons.getChildNodes().item(j).getTextContent(), polygonSubCount);
            }
            else if(polygons.getChildNodes().item(j).getNodeName().equals("input")){
                NamedNodeMap inputAttributes = polygons.getChildNodes().item(j).getAttributes();
                String semantic = inputAttributes.getNamedItem("semantic").getNodeValue();
                String source = inputAttributes.getNamedItem("source").getNodeValue();
                if(semantic.equals("VERTEX")) vertexSource = source;
                else if(semantic.equals("NORMAL")) normalSource = source;
                else if(semantic.equals("TEXCOORD")) texCoordSource = source;
            }
        }
        // FIX VERTICES
        vertexSource =
                mesh.getChildNodes().item(verticesIndex).getChildNodes(
                ).item(verticesInputIndex).getAttributes().getNamedItem("source").getNodeValue();
        //
        for (int i = 0; i < mesh.getChildNodes().getLength(); i++){
            if(mesh.getChildNodes().item(i).getNodeName().equals("source")){
                Node source = mesh.getChildNodes().item(i);
                NamedNodeMap sourceAttributes = source.getAttributes();
                String id = '#'+sourceAttributes.getNamedItem("id").getNodeValue();
                String textConent = null;
                int count = 0;
                int stride = 0;
                for (int j = 0; j < source.getChildNodes().getLength(); j++){
                    if(source.getChildNodes().item(j).getNodeName().equals("float_array")){
                        Node float_array = source.getChildNodes().item(j);
                        count = Integer.parseInt(float_array.getAttributes().getNamedItem("count").getNodeValue());
                        textConent = float_array.getTextContent();
                    }
                    else if(source.getChildNodes().item(j).getNodeName().equals("technique_common")){
                        Node technique_common = source.getChildNodes().item(j);
                        for (int k = 0; k < technique_common.getChildNodes().getLength(); k++){
                            if(technique_common.getChildNodes().item(k).getNodeName().equals("accessor")){
                                Node accesor = technique_common.getChildNodes().item(k);
                                stride = Integer.parseInt(accesor.getAttributes().getNamedItem("stride").getNodeValue());
                            }
                        }
                    }
                }
                if(id.equals(vertexSource)){
                    vertexArray = parseFloatArray(textConent, count);
                    vertexStride = stride;
                }else if(id.equals(normalSource)){
                    normalArray = parseFloatArray(textConent, count);
                    normalStride = stride;
                }else if(id.equals(texCoordSource)){
                    texCoordArray = parseFloatArray(textConent, count);
                    texCoordStride = stride;
                }
            }
        }
        return new Mesh(
                vertexArray, vertexStride,
                normalArray, normalStride,
                texCoordArray, texCoordStride,
                polygonArray);
    }

    private static float[] parseFloatArray(String float_array, int count) {
        float[] array = new float[count];
        // double time = System.currentTimeMillis();
        int i = 0;
        int bi = 0;
        int ei = 1;
        while(!isParseableCharacter(float_array.charAt(bi))) bi++;
        while(i<count){
            while(isParseableCharacter(float_array.charAt(ei))) ei++;
            array[i++] = Float.parseFloat(float_array.substring(bi, ei));
            bi = ++ei;
        }
        // System.out.println("parseTime:" + (System.currentTimeMillis()-time));
        return array;
    }

    private static int[] parseIndexArray(String indices, int count) {
        int[] array = new int[count];
        int i = 0;
        int bi = 0;
        int ei = 1;
        while(!isParseableCharacter(indices.charAt(bi))) bi++;
        while(i<count){
            while(ei != indices.length() && indices.charAt(ei)!=' ') ei++;
            array[i++] = Integer.parseInt(indices.substring(bi, ei));
            bi = ++ei;
        }
        return array;
    }

    private static int numberOfSpaces(String s){
        int n = 0;
        for (int i = 0; i < s.length(); i++) if(s.charAt(i)==' ') n++;        
        return n;
    }

    private static boolean isParseableCharacter(char c){
        if(Character.isDigit(c)) return true;
        if(c == '-') return true;
        if(c == '.') return true;
        return false;
    }
}