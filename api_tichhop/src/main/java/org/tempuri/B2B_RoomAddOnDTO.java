/**
 * B2B_RoomAddOnDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:06:07 GMT)
 */
package org.tempuri;


/**
 *  B2B_RoomAddOnDTO bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class B2B_RoomAddOnDTO implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = B2B_RoomAddOnDTO
       Namespace URI = http://tempuri.org/
       Namespace Prefix = ns1
     */

    /**
     * field for HOTEL_CODE
     */
    protected java.lang.String localHOTEL_CODE;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localHOTEL_CODETracker = false;

    /**
     * field for ROOM_CODE
     */
    protected java.lang.String localROOM_CODE;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localROOM_CODETracker = false;

    /**
     * field for USERNAME
     */
    protected java.lang.String localUSERNAME;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localUSERNAMETracker = false;

    /**
     * field for REGISTDATE
     */
    protected java.lang.String localREGISTDATE;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localREGISTDATETracker = false;

    /**
     * field for CANCELDATE
     */
    protected java.lang.String localCANCELDATE;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localCANCELDATETracker = false;

    /**
     * field for ADDON_CODE
     */
    protected java.lang.String localADDON_CODE;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localADDON_CODETracker = false;

    /**
     * field for ZIPCD
     */
    protected java.lang.String localZIPCD;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localZIPCDTracker = false;

    /**
     * field for UNITPRICE
     */
    protected int localUNITPRICE;

    public boolean isHOTEL_CODESpecified() {
        return localHOTEL_CODETracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getHOTEL_CODE() {
        return localHOTEL_CODE;
    }

    /**
     * Auto generated setter method
     * @param param HOTEL_CODE
     */
    public void setHOTEL_CODE(java.lang.String param) {
        localHOTEL_CODETracker = param != null;

        this.localHOTEL_CODE = param;
    }

    public boolean isROOM_CODESpecified() {
        return localROOM_CODETracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getROOM_CODE() {
        return localROOM_CODE;
    }

    /**
     * Auto generated setter method
     * @param param ROOM_CODE
     */
    public void setROOM_CODE(java.lang.String param) {
        localROOM_CODETracker = param != null;

        this.localROOM_CODE = param;
    }

    public boolean isUSERNAMESpecified() {
        return localUSERNAMETracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getUSERNAME() {
        return localUSERNAME;
    }

    /**
     * Auto generated setter method
     * @param param USERNAME
     */
    public void setUSERNAME(java.lang.String param) {
        localUSERNAMETracker = param != null;

        this.localUSERNAME = param;
    }

    public boolean isREGISTDATESpecified() {
        return localREGISTDATETracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getREGISTDATE() {
        return localREGISTDATE;
    }

    /**
     * Auto generated setter method
     * @param param REGISTDATE
     */
    public void setREGISTDATE(java.lang.String param) {
        localREGISTDATETracker = param != null;

        this.localREGISTDATE = param;
    }

    public boolean isCANCELDATESpecified() {
        return localCANCELDATETracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getCANCELDATE() {
        return localCANCELDATE;
    }

    /**
     * Auto generated setter method
     * @param param CANCELDATE
     */
    public void setCANCELDATE(java.lang.String param) {
        localCANCELDATETracker = param != null;

        this.localCANCELDATE = param;
    }

    public boolean isADDON_CODESpecified() {
        return localADDON_CODETracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getADDON_CODE() {
        return localADDON_CODE;
    }

    /**
     * Auto generated setter method
     * @param param ADDON_CODE
     */
    public void setADDON_CODE(java.lang.String param) {
        localADDON_CODETracker = param != null;

        this.localADDON_CODE = param;
    }

    public boolean isZIPCDSpecified() {
        return localZIPCDTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getZIPCD() {
        return localZIPCD;
    }

    /**
     * Auto generated setter method
     * @param param ZIPCD
     */
    public void setZIPCD(java.lang.String param) {
        localZIPCDTracker = param != null;

        this.localZIPCD = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getUNITPRICE() {
        return localUNITPRICE;
    }

    /**
     * Auto generated setter method
     * @param param UNITPRICE
     */
    public void setUNITPRICE(int param) {
        this.localUNITPRICE = param;
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
                    namespacePrefix + ":B2B_RoomAddOnDTO", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "B2B_RoomAddOnDTO", xmlWriter);
            }
        }

        if (localHOTEL_CODETracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "HOTEL_CODE", xmlWriter);

            if (localHOTEL_CODE == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "HOTEL_CODE cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localHOTEL_CODE);
            }

            xmlWriter.writeEndElement();
        }

        if (localROOM_CODETracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "ROOM_CODE", xmlWriter);

            if (localROOM_CODE == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "ROOM_CODE cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localROOM_CODE);
            }

            xmlWriter.writeEndElement();
        }

        if (localUSERNAMETracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "USERNAME", xmlWriter);

            if (localUSERNAME == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "USERNAME cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localUSERNAME);
            }

            xmlWriter.writeEndElement();
        }

        if (localREGISTDATETracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "REGISTDATE", xmlWriter);

            if (localREGISTDATE == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "REGISTDATE cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localREGISTDATE);
            }

            xmlWriter.writeEndElement();
        }

        if (localCANCELDATETracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "CANCELDATE", xmlWriter);

            if (localCANCELDATE == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "CANCELDATE cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localCANCELDATE);
            }

            xmlWriter.writeEndElement();
        }

        if (localADDON_CODETracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "ADDON_CODE", xmlWriter);

            if (localADDON_CODE == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "ADDON_CODE cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localADDON_CODE);
            }

            xmlWriter.writeEndElement();
        }

        if (localZIPCDTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "ZIPCD", xmlWriter);

            if (localZIPCD == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "ZIPCD cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localZIPCD);
            }

            xmlWriter.writeEndElement();
        }

        namespace = "http://tempuri.org/";
        writeStartElement(null, namespace, "UNITPRICE", xmlWriter);

        if (localUNITPRICE == java.lang.Integer.MIN_VALUE) {
            throw new org.apache.axis2.databinding.ADBException(
                "UNITPRICE cannot be null!!");
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localUNITPRICE));
        }

        xmlWriter.writeEndElement();

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
        public static B2B_RoomAddOnDTO parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            B2B_RoomAddOnDTO object = new B2B_RoomAddOnDTO();

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

                        if (!"B2B_RoomAddOnDTO".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (B2B_RoomAddOnDTO) org.tempuri.ExtensionMapper.getTypeObject(nsUri,
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
                            "HOTEL_CODE").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "HOTEL_CODE" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setHOTEL_CODE(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "ROOM_CODE").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "ROOM_CODE" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setROOM_CODE(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "USERNAME").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "USERNAME" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setUSERNAME(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "REGISTDATE").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "REGISTDATE" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setREGISTDATE(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "CANCELDATE").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "CANCELDATE" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setCANCELDATE(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "ADDON_CODE").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "ADDON_CODE" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setADDON_CODE(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "ZIPCD").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "ZIPCD" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setZIPCD(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "UNITPRICE").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "UNITPRICE" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setUNITPRICE(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
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
