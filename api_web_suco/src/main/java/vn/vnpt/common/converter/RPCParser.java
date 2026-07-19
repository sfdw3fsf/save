package vn.vnpt.common.converter;

import org.apache.commons.collections.IteratorUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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

    static final String attributeInformationList = "attributeInformationList";

    public String buildMembers(JSONObject jmembers) {
        StringBuilder sb = new StringBuilder();

        jmembers.keySet().forEach(key -> {
            Object member = jmembers.get(key);

            if (member instanceof JSONArray) {
                sb.append(buildArray(key, (JSONArray) member));
            } else if (member instanceof JSONObject) {
                sb.append(buildStruct(key, (JSONObject) member));
            } else {
                sb.append(buildValue(key, member.toString()));
            }
        });

        return sb.toString();
    }

    private String buildStruct(String key, JSONObject member) {
        StringBuilder sb = new StringBuilder();

        sb.append("<member>")
                .append("<name>").append(key).append("</name>")
                .append("<value>").append(buildStructInArray(member)).append("</value>")
                .append("</member>");

        return sb.toString();
    }

    private String buildValue(String key, String value) {
        StringBuilder sb = new StringBuilder();
        String[] arrKey = key.split("\\.", 2);
        String field = arrKey[0];
        String type = arrKey.length == 1 ? "string" : arrKey[1];

        sb.append("<member>")
                .append("<name>").append(field).append("</name>")
                .append("<value>")
                .append("<").append(type).append(">")
                .append(value)
                .append("</").append(type).append(">")
                .append("</value>")
                .append("</member>");

        return sb.toString();
    }

    private String buildArray(String key, JSONArray values) {
        StringBuilder sb = new StringBuilder();
        String[] arrKey = key.split("\\.", 2);
        String field = arrKey[0];
        String type = arrKey.length == 1 ? "string" : arrKey[1];

        sb.append("<member>")
                .append("<name>").append(field).append("</name>")
                .append("<value>").append("<array>").append("<data>");
        values.forEach(jo -> {
            if (jo instanceof JSONObject) {
                sb.append("<value>").append(buildStructInArray((JSONObject) jo)).append("</value>");
            } else {
                sb.append("<value>").append("<").append(type).append(">")
                        .append(jo.toString())
                        .append("</").append(type).append(">").append("</value>");
            }
        });

        sb.append("</data>").append("</array>").append("</value>")
                .append("</member>");
        return sb.toString();
    }

    private String buildStructInArray(JSONObject jo) {
        StringBuilder sb = new StringBuilder();

        sb.append("<struct>");
        List<String> keys = IteratorUtils.toList(jo.keys());
        keys.forEach(key -> {
            sb.append(buildValue(key, jo.get(key).toString()));
        });
        sb.append("</struct>");

        return sb.toString();
    }

    public JSONArray readMethodResponse(InputStream is) throws ParserConfigurationException, IOException, SAXException {
        JSONArray ja = new JSONArray();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setValidating(false);
        dbFactory.setNamespaceAware(false);
        dbFactory.setCoalescing(true);
        dbFactory.setIgnoringComments(true);
        DocumentBuilder db = dbFactory.newDocumentBuilder();
        Document doc = db.parse(is);

        Node methodResponse = getFirstChild(doc); //methodResponse
        Node paramsOrFault = getFirstChild(methodResponse);
        String nodeName = paramsOrFault.getNodeName();
        if (nodeName.equals("params")) {
            NodeList params = paramsOrFault.getChildNodes();
            for (int i = 0; i < params.getLength(); i++) {
                Node param = params.item(i);
                // params/param/value
                NodeList values = param.getChildNodes();
                for (int j = 0; j < values.getLength(); j++) {
                    Node value = values.item(j);
                    if (value.getNodeName().equals("value")) {
                        Object jValue = readValue(value); // read value
                        ja.put(jValue);
                    }
                }
            }
        } else {
            Node faultValue = getFirstChild(paramsOrFault);
            if (faultValue.getNodeName().equals("value")) {
                Object jValue = readValue(faultValue); // read value
                ja.put(jValue);
            }
        }

        return ja;
    }
    /*public JSONArray readMethodResponse(String methodResponse) throws ParserConfigurationException, IOException, SAXException {
        JSONArray ja = new JSONArray();

        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse(new InputSource(new StringReader(methodResponse)));

        Node paramsOrFault = doc.getFirstChild().getFirstChild();

        if (paramsOrFault.getNodeName().equals("params")) {
            NodeList params = paramsOrFault.getChildNodes();
            for (int i = 0; i < params.getLength(); i++) {
                Node param = params.item(i);
                // params/params/value
                for(int j=0;j<param.getChildNodes().getLength();j++){
                    Node value = param.getChildNodes().item(j);
                    if(value.getNodeName().equals("value")){
                        Object jValue = readValue(value); // read value
                        ja.put(jValue);
                    }
                }
            }
        }
        return ja;
    }*/

    private Object readValue(Node value) {
        Object ret = null;
        //System.out.println(value.toString());
        Node structOrArrayOrType = getFirstChild(value);

        String sat = structOrArrayOrType.getNodeName();
        //System.out.println(sat);
        if (sat.equals("struct")) {
            ret = readStruct(structOrArrayOrType);
        } else if (sat.equals("array")) {
            ret = readArray(structOrArrayOrType);
        } else if (isNode(structOrArrayOrType)) {
            if (structOrArrayOrType.getNodeName().equals("i4")) {
                ret = Integer.parseInt(structOrArrayOrType.getTextContent());
            } else if (structOrArrayOrType.getNodeName().equals("int")) {
                ret = Integer.parseInt(structOrArrayOrType.getTextContent());
            } else if (structOrArrayOrType.getNodeName().equals("boolean")) {
                ret = structOrArrayOrType.getTextContent().equals("1");
            } else {
                ret = structOrArrayOrType.getTextContent();
            }
        }

        return ret;
    }

    private JSONArray readArray(Node array) {
        JSONArray ja = new JSONArray();
        //array/data/value
        NodeList values = getFirstChild(array).getChildNodes();
        for (int i = 0; i < values.getLength(); i++) {
            Node value = values.item(i);
            if (isNode(value)) {
                Object jValue = readValue(value); // read value
                ja.put(jValue);
            }
        }
        return ja;
    }

    private JSONObject readStruct(Node struct) {
        JSONObject jo = new JSONObject();

        NodeList members = struct.getChildNodes();

        for (int i = 0; i < members.getLength(); i++) {
            Node member = members.item(i);
            if (isNode(member)) {
                String name = getFirstChild(member).getTextContent();
                Object value = readValue(getLastChild(member));

                if (name.equals(attributeInformationList)
                        && value instanceof JSONArray) {
                    JSONArray ja = (JSONArray) value;
                    for (int j = 0; j < ja.length(); j++) {
                        JSONObject attr = ja.getJSONObject(j);
                        String group = "";
                        if (attr.has("attributeSource")) {
                            group = Objects.toString(attr.get("attributeSource"), "");
                        }
                        if (!jo.has(attributeInformationList + group)) {
                            jo.put(attributeInformationList + group, new JSONObject());
                        }

                        JSONObject ailx = jo.getJSONObject(attributeInformationList + group);
                        String attributeName = attr.getString("attributeName");

                        if (ailx.has(attributeName)) {
                            if (!(ailx.get(attributeName) instanceof JSONArray)) {
                                ailx.put(attributeName, new JSONArray().put(ailx.get(attributeName)));
                            }

                            if (attr.has("attributeValueString")) {
                                ailx.getJSONArray(attributeName).put(attr.getString("attributeValueString"));
                            } else if (attr.has("attributeValueInteger")) {
                                ailx.getJSONArray(attributeName).put(attr.getInt("attributeValueInteger"));
                            } else if (attr.has("attributeValueDate")) {
                                ailx.getJSONArray(attributeName).put(attr.getInt("attributeValueDate"));
                            } else if (attr.has("attributeValueDecimal")) {
                                JSONObject decObj = attr.getJSONObject("attributeValueDecimal");
                                String attributeValueNumber = decObj.getString("attributeValueNumber");
                                Integer numberOfDecimals = decObj.getInt("numberOfDecimals");
                                ailx.getJSONArray(attributeName).put(Double.parseDouble(attributeValueNumber) / Math.pow(10, numberOfDecimals));
                            } else {
                                ailx.getJSONArray(attributeName).put("null");
                            }
                        } else {
                            if (attr.has("attributeValueString")) {
                                ailx.put(attributeName, attr.getString("attributeValueString"));
                            } else if (attr.has("attributeValueInteger")) {
                                ailx.put(attributeName, attr.getInt("attributeValueInteger"));
                            } else if (attr.has("attributeValueDate")) {
                                ailx.put(attributeName, attr.getInt("attributeValueDate"));
                            } else if (attr.has("attributeValueDecimal")) {
                                JSONObject decObj = attr.getJSONObject("attributeValueDecimal");
                                String attributeValueNumber = decObj.getString("attributeValueNumber");
                                Integer numberOfDecimals = decObj.getInt("numberOfDecimals");
                                ailx.put(attributeName, Double.parseDouble(attributeValueNumber) / Math.pow(10, numberOfDecimals));
                            } else {
                                ailx.put(attributeName, "null");
                            }
                        }
                    }
                } else {
                    jo.put(name, value);
                }
            }
        }

        return jo;
    }

    private Node getFirstChild(Node node) {
        Node n = null;
        NodeList nl = node.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            n = nl.item(i);
            if (isNode(n)) return n;
        }
        return n;
    }

    private Node getLastChild(Node node) {
        Node n = null;
        NodeList nl = node.getChildNodes();
        for (int i = nl.getLength() - 1; i >= 0; i--) {
            n = nl.item(i);
            if (isNode(n)) return n;
        }
        return n;
    }

    private boolean isNode(Node node) {
        return node.getNodeType() != Node.TEXT_NODE;
    }
}
