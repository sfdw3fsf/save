/**
 * GetHisPayTVResp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:06:07 GMT)
 */
package org.tempuri;


/**
 *  GetHisPayTVResp bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class GetHisPayTVResp implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = GetHisPayTVResp
       Namespace URI = http://tempuri.org/
       Namespace Prefix = ns1
     */

    /**
     * field for DataKplus
     */
    protected org.tempuri.DataKplus_type0 localDataKplus;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localDataKplusTracker = false;

    /**
     * field for DataVTVcab
     */
    protected org.tempuri.DataVTVcab_type0 localDataVTVcab;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localDataVTVcabTracker = false;

    /**
     * field for DataHBO
     */
    protected org.tempuri.DataHBO_type0 localDataHBO;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localDataHBOTracker = false;

    /**
     * field for DataFafilm
     */
    protected org.tempuri.DataFafilm_type0 localDataFafilm;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localDataFafilmTracker = false;

    /**
     * field for ResponseTime
     */
    protected java.util.Calendar localResponseTime;

    /**
     * field for Result
     */
    protected int localResult;

    /**
     * field for ErrorDesc
     */
    protected java.lang.String localErrorDesc;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localErrorDescTracker = false;

    public boolean isDataKplusSpecified() {
        return localDataKplusTracker;
    }

    /**
     * Auto generated getter method
     * @return org.tempuri.DataKplus_type0
     */
    public org.tempuri.DataKplus_type0 getDataKplus() {
        return localDataKplus;
    }

    /**
     * Auto generated setter method
     * @param param DataKplus
     */
    public void setDataKplus(org.tempuri.DataKplus_type0 param) {
        localDataKplusTracker = param != null;

        this.localDataKplus = param;
    }

    public boolean isDataVTVcabSpecified() {
        return localDataVTVcabTracker;
    }

    /**
     * Auto generated getter method
     * @return org.tempuri.DataVTVcab_type0
     */
    public org.tempuri.DataVTVcab_type0 getDataVTVcab() {
        return localDataVTVcab;
    }

    /**
     * Auto generated setter method
     * @param param DataVTVcab
     */
    public void setDataVTVcab(org.tempuri.DataVTVcab_type0 param) {
        localDataVTVcabTracker = param != null;

        this.localDataVTVcab = param;
    }

    public boolean isDataHBOSpecified() {
        return localDataHBOTracker;
    }

    /**
     * Auto generated getter method
     * @return org.tempuri.DataHBO_type0
     */
    public org.tempuri.DataHBO_type0 getDataHBO() {
        return localDataHBO;
    }

    /**
     * Auto generated setter method
     * @param param DataHBO
     */
    public void setDataHBO(org.tempuri.DataHBO_type0 param) {
        localDataHBOTracker = param != null;

        this.localDataHBO = param;
    }

    public boolean isDataFafilmSpecified() {
        return localDataFafilmTracker;
    }

    /**
     * Auto generated getter method
     * @return org.tempuri.DataFafilm_type0
     */
    public org.tempuri.DataFafilm_type0 getDataFafilm() {
        return localDataFafilm;
    }

    /**
     * Auto generated setter method
     * @param param DataFafilm
     */
    public void setDataFafilm(org.tempuri.DataFafilm_type0 param) {
        localDataFafilmTracker = param != null;

        this.localDataFafilm = param;
    }

    /**
     * Auto generated getter method
     * @return java.util.Calendar
     */
    public java.util.Calendar getResponseTime() {
        return localResponseTime;
    }

    /**
     * Auto generated setter method
     * @param param ResponseTime
     */
    public void setResponseTime(java.util.Calendar param) {
        this.localResponseTime = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getResult() {
        return localResult;
    }

    /**
     * Auto generated setter method
     * @param param Result
     */
    public void setResult(int param) {
        this.localResult = param;
    }

    public boolean isErrorDescSpecified() {
        return localErrorDescTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getErrorDesc() {
        return localErrorDesc;
    }

    /**
     * Auto generated setter method
     * @param param ErrorDesc
     */
    public void setErrorDesc(java.lang.String param) {
        localErrorDescTracker = param != null;

        this.localErrorDesc = param;
    }

    /**
     *
     * @param parentQName
     * @param factory
     * @return org.apache.axiom.om.OMElement
     */
    public org.apache.axiom.om.OMElement getOMElement(
        final javax.xml.namespace.QName parentQName,
        final org.apache.axiom.om.OMFactory factory)
        throws org.apache.axis2.databinding.ADBException {
        return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                this, parentQName));
    }

    public void serialize(final javax.xml.namespace.QName parentQName,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException,
            org.apache.axis2.databinding.ADBException {
        serialize(parentQName, xmlWriter, false);
    }

    public void serialize(final javax.xml.namespace.QName parentQName,
        javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
        throws javax.xml.stream.XMLStreamException,
            org.apache.axis2.databinding.ADBException {
        java.lang.String prefix = null;
        java.lang.String namespace = null;

        prefix = parentQName.getPrefix();
        namespace = parentQName.getNamespaceURI();
        writeStartElement(prefix, namespace, parentQName.getLocalPart(),
            xmlWriter);

        if (serializeType) {
            java.lang.String namespacePrefix = registerPrefix(xmlWriter,
                    "http://tempuri.org/");

            if ((namespacePrefix != null) &&
                    (namespacePrefix.trim().length() > 0)) {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    namespacePrefix + ":GetHisPayTVResp", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "GetHisPayTVResp", xmlWriter);
            }
        }

        if (localDataKplusTracker) {
            if (localDataKplus == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "DataKplus cannot be null!!");
            }

            localDataKplus.serialize(new javax.xml.namespace.QName(
                    "http://tempuri.org/", "DataKplus"), xmlWriter);
        }

        if (localDataVTVcabTracker) {
            if (localDataVTVcab == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "DataVTVcab cannot be null!!");
            }

            localDataVTVcab.serialize(new javax.xml.namespace.QName(
                    "http://tempuri.org/", "DataVTVcab"), xmlWriter);
        }

        if (localDataHBOTracker) {
            if (localDataHBO == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "DataHBO cannot be null!!");
            }

            localDataHBO.serialize(new javax.xml.namespace.QName(
                    "http://tempuri.org/", "DataHBO"), xmlWriter);
        }

        if (localDataFafilmTracker) {
            if (localDataFafilm == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "DataFafilm cannot be null!!");
            }

            localDataFafilm.serialize(new javax.xml.namespace.QName(
                    "http://tempuri.org/", "DataFafilm"), xmlWriter);
        }

        namespace = "http://tempuri.org/";
        writeStartElement(null, namespace, "ResponseTime", xmlWriter);

        if (localResponseTime == null) {
            // write the nil attribute
            throw new org.apache.axis2.databinding.ADBException(
                "ResponseTime cannot be null!!");
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localResponseTime));
        }

        xmlWriter.writeEndElement();

        namespace = "http://tempuri.org/";
        writeStartElement(null, namespace, "Result", xmlWriter);

        if (localResult == java.lang.Integer.MIN_VALUE) {
            throw new org.apache.axis2.databinding.ADBException(
                "Result cannot be null!!");
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localResult));
        }

        xmlWriter.writeEndElement();

        if (localErrorDescTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "ErrorDesc", xmlWriter);

            if (localErrorDesc == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "ErrorDesc cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localErrorDesc);
            }

            xmlWriter.writeEndElement();
        }

        xmlWriter.writeEndElement();
    }

    private static java.lang.String generatePrefix(java.lang.String namespace) {
        if (namespace.equals("http://tempuri.org/")) {
            return "ns1";
        }

        return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
    }

    /**
     * Utility method to write an element start tag.
     */
    private void writeStartElement(java.lang.String prefix,
        java.lang.String namespace, java.lang.String localPart,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

        if (writerPrefix != null) {
            xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
        } else {
            if (namespace.length() == 0) {
                prefix = "";
            } else if (prefix == null) {
                prefix = generatePrefix(namespace);
            }

            xmlWriter.writeStartElement(prefix, localPart, namespace);
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }
    }

    /**
     * Util method to write an attribute with the ns prefix
     */
    private void writeAttribute(java.lang.String prefix,
        java.lang.String namespace, java.lang.String attName,
        java.lang.String attValue, javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);

        if (writerPrefix != null) {
            xmlWriter.writeAttribute(writerPrefix, namespace, attName, attValue);
        } else {
            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
            xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
        }
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeAttribute(java.lang.String namespace,
        java.lang.String attName, java.lang.String attValue,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        if (namespace.equals("")) {
            xmlWriter.writeAttribute(attName, attValue);
        } else {
            xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                namespace, attName, attValue);
        }
    }

    /**
     * Util method to write an attribute without the ns prefix
     */
    private void writeQNameAttribute(java.lang.String namespace,
        java.lang.String attName, javax.xml.namespace.QName qname,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String attributeNamespace = qname.getNamespaceURI();
        java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

        if (attributePrefix == null) {
            attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
        }

        java.lang.String attributeValue;

        if (attributePrefix.trim().length() > 0) {
            attributeValue = attributePrefix + ":" + qname.getLocalPart();
        } else {
            attributeValue = qname.getLocalPart();
        }

        if (namespace.equals("")) {
            xmlWriter.writeAttribute(attName, attributeValue);
        } else {
            registerPrefix(xmlWriter, namespace);
            xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                attributeValue);
        }
    }

    /**
     *  method to handle Qnames
     */
    private void writeQName(javax.xml.namespace.QName qname,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String namespaceURI = qname.getNamespaceURI();

        if (namespaceURI != null) {
            java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);

            if (prefix == null) {
                prefix = generatePrefix(namespaceURI);
                xmlWriter.writeNamespace(prefix, namespaceURI);
                xmlWriter.setPrefix(prefix, namespaceURI);
            }

            if (prefix.trim().length() > 0) {
                xmlWriter.writeCharacters(prefix + ":" +
                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            } else {
                // i.e this is the default namespace
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    qname));
        }
    }

    private void writeQNames(javax.xml.namespace.QName[] qnames,
        javax.xml.stream.XMLStreamWriter xmlWriter)
        throws javax.xml.stream.XMLStreamException {
        if (qnames != null) {
            // we have to store this data until last moment since it is not possible to write any
            // namespace data after writing the charactor data
            java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
            java.lang.String namespaceURI = null;
            java.lang.String prefix = null;

            for (int i = 0; i < qnames.length; i++) {
                if (i > 0) {
                    stringToWrite.append(" ");
                }

                namespaceURI = qnames[i].getNamespaceURI();

                if (namespaceURI != null) {
                    prefix = xmlWriter.getPrefix(namespaceURI);

                    if ((prefix == null) || (prefix.length() == 0)) {
                        prefix = generatePrefix(namespaceURI);
                        xmlWriter.writeNamespace(prefix, namespaceURI);
                        xmlWriter.setPrefix(prefix, namespaceURI);
                    }

                    if (prefix.trim().length() > 0) {
                        stringToWrite.append(prefix).append(":")
                                     .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                } else {
                    stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qnames[i]));
                }
            }

            xmlWriter.writeCharacters(stringToWrite.toString());
        }
    }

    /**
     * Register a namespace prefix
     */
    private java.lang.String registerPrefix(
        javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace)
        throws javax.xml.stream.XMLStreamException {
        java.lang.String prefix = xmlWriter.getPrefix(namespace);

        if (prefix == null) {
            prefix = generatePrefix(namespace);

            javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

            while (true) {
                java.lang.String uri = nsContext.getNamespaceURI(prefix);

                if ((uri == null) || (uri.length() == 0)) {
                    break;
                }

                prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
            }

            xmlWriter.writeNamespace(prefix, namespace);
            xmlWriter.setPrefix(prefix, namespace);
        }

        return prefix;
    }

    /**
     *  Factory class that keeps the parse method
     */
    public static class Factory {
        private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

        /**
         * static method to create the object
         * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
         *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
         * Postcondition: If this object is an element, the reader is positioned at its end element
         *                If this object is a complex type, the reader is positioned at the end element of its outer element
         */
        public static GetHisPayTVResp parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            GetHisPayTVResp object = new GetHisPayTVResp();

            int event;
            javax.xml.namespace.QName currentQName = null;
            java.lang.String nillableValue = null;
            java.lang.String prefix = "";
            java.lang.String namespaceuri = "";

            try {
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                currentQName = reader.getName();

                if (reader.getAttributeValue(
                            "http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
                    java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "type");

                    if (fullTypeName != null) {
                        java.lang.String nsPrefix = null;

                        if (fullTypeName.indexOf(":") > -1) {
                            nsPrefix = fullTypeName.substring(0,
                                    fullTypeName.indexOf(":"));
                        }

                        nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                        java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(
                                    ":") + 1);

                        if (!"GetHisPayTVResp".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (GetHisPayTVResp) org.tempuri.ExtensionMapper.getTypeObject(nsUri,
                                type, reader);
                        }
                    }
                }

                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();

                reader.next();

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "DataKplus").equals(reader.getName())) {
                    object.setDataKplus(org.tempuri.DataKplus_type0.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "DataVTVcab").equals(reader.getName())) {
                    object.setDataVTVcab(org.tempuri.DataVTVcab_type0.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "DataHBO").equals(reader.getName())) {
                    object.setDataHBO(org.tempuri.DataHBO_type0.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "DataFafilm").equals(reader.getName())) {
                    object.setDataFafilm(org.tempuri.DataFafilm_type0.Factory.parse(
                            reader));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "ResponseTime").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "ResponseTime" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setResponseTime(org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                    // 1 - A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "Result").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Result" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setResult(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                    // 1 - A start element we are not expecting indicates an invalid parameter was passed
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getName());
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "ErrorDesc").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "ErrorDesc" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setErrorDesc(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement()) {
                    // 2 - A start element we are not expecting indicates a trailing invalid property
                    throw new org.apache.axis2.databinding.ADBException(
                        "Unexpected subelement " + reader.getName());
                }
            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }
    } //end of factory class
}
