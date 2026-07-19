/**
 * STB_Prom_ActiveDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:06:07 GMT)
 */
package org.tempuri;


/**
 *  STB_Prom_ActiveDTO bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class STB_Prom_ActiveDTO implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = STB_Prom_ActiveDTO
       Namespace URI = http://tempuri.org/
       Namespace Prefix = ns1
     */

    /**
     * field for PROGRAM
     */
    protected java.lang.String localPROGRAM;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localPROGRAMTracker = false;

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
     * field for IS_FIRST
     */
    protected boolean localIS_FIRST;

    /**
     * field for STB_TYPE
     */
    protected java.lang.String localSTB_TYPE;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSTB_TYPETracker = false;

    /**
     * field for STB_SERIAL
     */
    protected java.lang.String localSTB_SERIAL;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSTB_SERIALTracker = false;

    /**
     * field for STB_DATE
     */
    protected java.lang.String localSTB_DATE;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSTB_DATETracker = false;

    /**
     * field for NOTE
     */
    protected java.lang.String localNOTE;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNOTETracker = false;

    public boolean isPROGRAMSpecified() {
        return localPROGRAMTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getPROGRAM() {
        return localPROGRAM;
    }

    /**
     * Auto generated setter method
     * @param param PROGRAM
     */
    public void setPROGRAM(java.lang.String param) {
        localPROGRAMTracker = param != null;

        this.localPROGRAM = param;
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

    /**
     * Auto generated getter method
     * @return boolean
     */
    public boolean getIS_FIRST() {
        return localIS_FIRST;
    }

    /**
     * Auto generated setter method
     * @param param IS_FIRST
     */
    public void setIS_FIRST(boolean param) {
        this.localIS_FIRST = param;
    }

    public boolean isSTB_TYPESpecified() {
        return localSTB_TYPETracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getSTB_TYPE() {
        return localSTB_TYPE;
    }

    /**
     * Auto generated setter method
     * @param param STB_TYPE
     */
    public void setSTB_TYPE(java.lang.String param) {
        localSTB_TYPETracker = param != null;

        this.localSTB_TYPE = param;
    }

    public boolean isSTB_SERIALSpecified() {
        return localSTB_SERIALTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getSTB_SERIAL() {
        return localSTB_SERIAL;
    }

    /**
     * Auto generated setter method
     * @param param STB_SERIAL
     */
    public void setSTB_SERIAL(java.lang.String param) {
        localSTB_SERIALTracker = param != null;

        this.localSTB_SERIAL = param;
    }

    public boolean isSTB_DATESpecified() {
        return localSTB_DATETracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getSTB_DATE() {
        return localSTB_DATE;
    }

    /**
     * Auto generated setter method
     * @param param STB_DATE
     */
    public void setSTB_DATE(java.lang.String param) {
        localSTB_DATETracker = param != null;

        this.localSTB_DATE = param;
    }

    public boolean isNOTESpecified() {
        return localNOTETracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getNOTE() {
        return localNOTE;
    }

    /**
     * Auto generated setter method
     * @param param NOTE
     */
    public void setNOTE(java.lang.String param) {
        localNOTETracker = param != null;

        this.localNOTE = param;
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
                    namespacePrefix + ":STB_Prom_ActiveDTO", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "STB_Prom_ActiveDTO", xmlWriter);
            }
        }

        if (localPROGRAMTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "PROGRAM", xmlWriter);

            if (localPROGRAM == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "PROGRAM cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localPROGRAM);
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

        namespace = "http://tempuri.org/";
        writeStartElement(null, namespace, "IS_FIRST", xmlWriter);

        if (false) {
            throw new org.apache.axis2.databinding.ADBException(
                "IS_FIRST cannot be null!!");
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localIS_FIRST));
        }

        xmlWriter.writeEndElement();

        if (localSTB_TYPETracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "STB_TYPE", xmlWriter);

            if (localSTB_TYPE == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "STB_TYPE cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localSTB_TYPE);
            }

            xmlWriter.writeEndElement();
        }

        if (localSTB_SERIALTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "STB_SERIAL", xmlWriter);

            if (localSTB_SERIAL == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "STB_SERIAL cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localSTB_SERIAL);
            }

            xmlWriter.writeEndElement();
        }

        if (localSTB_DATETracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "STB_DATE", xmlWriter);

            if (localSTB_DATE == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "STB_DATE cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localSTB_DATE);
            }

            xmlWriter.writeEndElement();
        }

        if (localNOTETracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "NOTE", xmlWriter);

            if (localNOTE == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "NOTE cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localNOTE);
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
        public static STB_Prom_ActiveDTO parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            STB_Prom_ActiveDTO object = new STB_Prom_ActiveDTO();

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

                        if (!"STB_Prom_ActiveDTO".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (STB_Prom_ActiveDTO) org.tempuri.ExtensionMapper.getTypeObject(nsUri,
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
                            "PROGRAM").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "PROGRAM" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setPROGRAM(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
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
                            "IS_FIRST").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "IS_FIRST" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setIS_FIRST(org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(
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
                            "STB_TYPE").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "STB_TYPE" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setSTB_TYPE(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "STB_SERIAL").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "STB_SERIAL" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setSTB_SERIAL(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "STB_DATE").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "STB_DATE" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setSTB_DATE(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "NOTE").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "NOTE" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setNOTE(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
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
