package vn.vnpt.common.converter;

import org.apache.commons.collections.IteratorUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * Created by User on 7/11/2018.
 */
public class RPCParser {
//	final String NODE_MEMBER = "<member>";
//	final String NODE_MEMBER_END = "</member>";
//	final String NODE_NAME = "<name>";
//	final String NODE_NAME_END = "</name>";
//	final String NODE_VALUE = "<value>";
//	final String NODE_VALUE_END = "</value>";
//    static final String ATTRIBUTEINFORMATIONLIST = "attributeInformationList";
//    final String ATT_VALUE_STRING = "attributeValueString";
//    final String ATT_VALUE_INT = "attributeValueInteger";
//    final String ATT_VALUE_DATE = "attributeValueDate";
//    final String ATT_VALUE_DEC = "attributeValueDecimal";
//    final String ATT_VALUE_NUM = "attributeValueNumber";
//    final String ATT_VALUE_NUMOFDEC = "numberOfDecimals";
//    
//    public String buildMembers(JSONObject jmembers) {
//        StringBuilder sb = new StringBuilder();
//
//        jmembers.keySet().forEach(key -> {
//            Object member = jmembers.get(key);
//
//            if (member instanceof JSONArray) {
//                sb.append(buildArray(key, (JSONArray) member));
//            } else if (member instanceof JSONObject) {
//                sb.append(buildStruct(key, (JSONObject) member));
//            } else {
//                sb.append(buildValue(key, member.toString()));
//            }
//        });
//
//        return sb.toString();
//    }
//
//    private String buildStruct(String key, JSONObject member) {
//        StringBuilder sb = new StringBuilder();
//
//        sb.append(NODE_MEMBER)
//                .append(NODE_NAME).append(key).append(NODE_NAME_END)
//                .append(NODE_VALUE).append(buildStructInArray(member)).append(NODE_VALUE_END)
//                .append(NODE_MEMBER_END);
//
//        return sb.toString();
//    }
//
//    private String buildValue(String key, String value) {
//        StringBuilder sb = new StringBuilder();
//        String[] arrKey = key.split("\\.", 2);
//        String field = arrKey[0];
//        String type = arrKey.length == 1 ? "string" : arrKey[1];
//
//        sb.append(NODE_MEMBER)
//                .append(NODE_NAME).append(field).append(NODE_NAME_END)
//                .append(NODE_VALUE)
//                .append("<").append(type).append(">")
//                .append(value)
//                .append("</").append(type).append(">")
//                .append(NODE_VALUE_END)
//                .append(NODE_MEMBER_END);
//
//        return sb.toString();
//    }
//
//    private String buildArray(String key, JSONArray values) {
//        StringBuilder sb = new StringBuilder();
//        String[] arrKey = key.split("\\.", 2);
//        String field = arrKey[0];
//        String type = arrKey.length == 1 ? "string" : arrKey[1];
//
//        sb.append(NODE_MEMBER)
//                .append(NODE_NAME).append(field).append(NODE_NAME_END)
//                .append(NODE_VALUE).append("<array>").append("<data>");
//        values.forEach(jo -> {
//            if (jo instanceof JSONObject) {
//                sb.append(NODE_VALUE).append(buildStructInArray((JSONObject) jo)).append(NODE_VALUE_END);
//            } else {
//                sb.append(NODE_VALUE).append("<").append(type).append(">")
//                        .append(jo.toString())
//                        .append("</").append(type).append(">").append(NODE_VALUE_END);
//            }
//        });
//
//        sb.append("</data>").append("</array>").append(NODE_VALUE_END)
//                .append(NODE_MEMBER_END);
//        return sb.toString();
//    }
//
//    private String buildStructInArray(JSONObject jo) {
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("<struct>");
//        List<String> keys = IteratorUtils.toList(jo.keys());
//        keys.forEach(key -> {
//            sb.append(buildValue(key, jo.get(key).toString()));
//        });
//        sb.append("</struct>");
//
//        return sb.toString();
//    }
//
//    public JSONArray readMethodResponse(InputStream is) throws ParserConfigurationException, IOException, SAXException {
//    	JSONArray ja = new JSONArray();
//        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//        dbFactory.setValidating(false);
//        dbFactory.setNamespaceAware(false);
//        dbFactory.setCoalescing(true);
//        dbFactory.setIgnoringComments(true);
//     // Disable XXE processing
//        dbFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
//        DocumentBuilder db = dbFactory.newDocumentBuilder();
//        Document doc = db.parse(is);
//
//        Node methodResponse = getFirstChild(doc); //methodResponse
//        if (methodResponse != null) {
//            Node paramsOrFault = getFirstChild(methodResponse);
//            if (paramsOrFault != null) {
//                String nodeName = paramsOrFault.getNodeName();
//                if ("params".equals(nodeName)) {
//                	JSONArray childs = this.getChildParams(paramsOrFault);
//                	ja.put(childs);
////                    NodeList params = paramsOrFault.getChildNodes();
////                    for (int i = 0; i < params.getLength(); i++) {
////                        Node param = params.item(i);
////                        // params/param/value
////                        NodeList values = param.getChildNodes();
////                        for (int j = 0; j < values.getLength(); j++) {
////                            Node value = values.item(j);
////                            if ("value".equals(value.getNodeName())) {
////                                Object jValue = readValue(value); // read value
////                                ja.put(jValue);
////                            }
////                        }
////                    }
//                } else {
//                    Node faultValue = getFirstChild(paramsOrFault);
//                    if (faultValue != null && "value".equals(faultValue.getNodeName())) {
//                        Object jValue = readValue(faultValue); // read value
//                        ja.put(jValue);
//                    }
//                }
//            }
//        }
//        
//        return ja;
//    }
//    
//    private JSONArray getChildParams(Node node) {
//    	JSONArray ja = new JSONArray();
//        NodeList params = node.getChildNodes();
//        for (int i = 0; i < params.getLength(); i++) {
//            Node param = params.item(i);
//            // params/param/value
//            NodeList values = param.getChildNodes();
//            for (int j = 0; j < values.getLength(); j++) {
//                Node value = values.item(j);
//                if ("value".equals(value.getNodeName())) {
//                    Object jValue = readValue(value); // read value
//                    ja.put(jValue);
//                }
//            }
//        }
//    	return ja;
//    }
//    /*public JSONArray readMethodResponse(String methodResponse) throws ParserConfigurationException, IOException, SAXException {
//        JSONArray ja = new JSONArray();
//
//        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//        Document doc = db.parse(new InputSource(new StringReader(methodResponse)));
//
//        Node paramsOrFault = doc.getFirstChild().getFirstChild();
//
//        if (paramsOrFault.getNodeName().equals("params")) {
//            NodeList params = paramsOrFault.getChildNodes();
//            for (int i = 0; i < params.getLength(); i++) {
//                Node param = params.item(i);
//                // params/params/value
//                for(int j=0;j<param.getChildNodes().getLength();j++){
//                    Node value = param.getChildNodes().item(j);
//                    if(value.getNodeName().equals("value")){
//                        Object jValue = readValue(value); // read value
//                        ja.put(jValue);
//                    }
//                }
//            }
//        }
//        return ja;
//    }*/
//
//    private Object readValue(Node value) {
//    	 Object ret = null;
//         //System.out.println(value.toString());
//         Node structOrArrayOrType = getFirstChild(value);
//
//         if (structOrArrayOrType != null) {
//             String sat = structOrArrayOrType.getNodeName();
//             if ("struct".equals(sat)) {
//                 ret = readStruct(structOrArrayOrType);
//             } else if ("array".equals(sat)) {
//                 ret = readArray(structOrArrayOrType);
//             } else if (isNode(structOrArrayOrType)) {
//                 String nodeName = structOrArrayOrType.getNodeName();
//                 if ("i4".equals(nodeName)) {
//                     ret = Integer.parseInt(structOrArrayOrType.getTextContent());
//                 } else if ("int".equals(nodeName)) {
//                     ret = Integer.parseInt(structOrArrayOrType.getTextContent());
//                 } else if ("boolean".equals(nodeName)) {
//                     ret = "1".equals(structOrArrayOrType.getTextContent());
//                 } else {
//                     ret = structOrArrayOrType.getTextContent();
//                 }
//             }
//         }
//
//
//         return ret;
//    }
//
//    private JSONArray readArray(Node array) {
//    	JSONArray ja = new JSONArray();
//        //array/data/value
//        Node firstChild = getFirstChild(array);
//        if (firstChild != null) {
//            NodeList values = firstChild.getChildNodes();
//            for (int i = 0; i < values.getLength(); i++) {
//                Node value = values.item(i);
//                if (isNode(value)) {
//                    Object jValue = readValue(value); // read value
//                    ja.put(jValue);
//                }
//            }
//        }
//        return ja;
//    }
//
//    private JSONObject readStruct(Node struct) {
//    	JSONObject jo = new JSONObject();
//
//        NodeList members = struct.getChildNodes();
//
//        for (int i = 0; i < members.getLength(); i++) {
//            Node member = members.item(i);
//            if (isNode(member)) {
//            	Node firstChild = getFirstChild(member);
//            	if (firstChild != null) {
//            		String name = firstChild.getTextContent();
//            		if (name != null && !name.isEmpty()) {
//            			Object value = readValue(getLastChild(member));
//
//                        if (name.equals(ATTRIBUTEINFORMATIONLIST)
//                                && value instanceof JSONArray) {
//                            JSONArray ja = (JSONArray) value;
//                            for (int j = 0; j < ja.length(); j++) {
//                                JSONObject attr = ja.getJSONObject(j);
//                                String group = "";
//                                if (attr.has("attributeSource")) {
//                                    group = Objects.toString(attr.get("attributeSource"), "");
//                                }
//                                if (!jo.has(ATTRIBUTEINFORMATIONLIST + group)) {
//                                    jo.put(ATTRIBUTEINFORMATIONLIST + group, new JSONObject());
//                                }
//
//                                JSONObject ailx = jo.getJSONObject(ATTRIBUTEINFORMATIONLIST + group);
//                                String attributeName = attr.getString("attributeName");
//
//                                if (ailx.has(attributeName)) {
//                                    if (!(ailx.get(attributeName) instanceof JSONArray)) {
//                                        ailx.put(attributeName, new JSONArray().put(ailx.get(attributeName)));
//                                    }
//
//                                    if (attr.has(ATT_VALUE_STRING)) {
//                                        ailx.getJSONArray(attributeName).put(attr.getString(ATT_VALUE_STRING));
//                                    } else if (attr.has(ATT_VALUE_INT)) {
//                                        ailx.getJSONArray(attributeName).put(attr.getInt(ATT_VALUE_INT));
//                                    } else if (attr.has(ATT_VALUE_DATE)) {
//                                        ailx.getJSONArray(attributeName).put(attr.getInt(ATT_VALUE_DATE));
//                                    } else if (attr.has(ATT_VALUE_DEC)) {
//                                        JSONObject decObj = attr.getJSONObject(ATT_VALUE_DEC);
//                                        String attributeValueNumber = decObj.getString(ATT_VALUE_NUM);
//                                        Integer numberOfDecimals = decObj.getInt(ATT_VALUE_NUMOFDEC);
//                                        ailx.getJSONArray(attributeName).put(Double.parseDouble(attributeValueNumber) / Math.pow(10, numberOfDecimals));
//                                    } else {
//                                        ailx.getJSONArray(attributeName).put("null");
//                                    }
//                                } else {
//                                    if (attr.has(ATT_VALUE_STRING)) {
//                                        ailx.put(attributeName, attr.getString(ATT_VALUE_STRING));
//                                    } else if (attr.has(ATT_VALUE_INT)) {
//                                        ailx.put(attributeName, attr.getInt(ATT_VALUE_INT));
//                                    } else if (attr.has(ATT_VALUE_DATE)) {
//                                        ailx.put(attributeName, attr.getInt(ATT_VALUE_DATE));
//                                    } else if (attr.has(ATT_VALUE_DEC)) {
//                                        JSONObject decObj = attr.getJSONObject(ATT_VALUE_DEC);
//                                        String attributeValueNumber = decObj.getString(ATT_VALUE_NUM);
//                                        Integer numberOfDecimals = decObj.getInt(ATT_VALUE_NUMOFDEC);
//                                        ailx.put(attributeName, Double.parseDouble(attributeValueNumber) / Math.pow(10, numberOfDecimals));
//                                    } else {
//                                        ailx.put(attributeName, "null");
//                                    }
//                                }
//                            }
//                        } else {
//                            jo.put(name, value);
//                        }
//            		}
//            	}
//            }
//        }
//
//        return jo;
//    }
//
//    private Node getFirstChild(Node node) {
//        Node n = null;
//        NodeList nl = node.getChildNodes();
//        for (int i = 0; i < nl.getLength(); i++) {
//            n = nl.item(i);
//            if (isNode(n)) return n;
//        }
//        return n;
//    }
//
//    private Node getLastChild(Node node) {
//        Node n = null;
//        NodeList nl = node.getChildNodes();
//        for (int i = nl.getLength() - 1; i >= 0; i--) {
//            n = nl.item(i);
//            if (isNode(n)) return n;
//        }
//        return n;
//    }
//
//    private boolean isNode(Node node) {
//        return node.getNodeType() != Node.TEXT_NODE;
//    }
}
