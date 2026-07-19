/**
 * StbInfoVO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:06:07 GMT)
 */
package org.tempuri;


/**
 *  StbInfoVO bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class StbInfoVO implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = StbInfoVO
       Namespace URI = http://tempuri.org/
       Namespace Prefix = ns1
     */

    /**
     * field for NhaCungCap
     */
    protected java.lang.String localNhaCungCap;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNhaCungCapTracker = false;

    /**
     * field for NgaySX
     */
    protected java.lang.String localNgaySX;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNgaySXTracker = false;

    /**
     * field for NgayHetHanBH
     */
    protected java.lang.String localNgayHetHanBH;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNgayHetHanBHTracker = false;

    /**
     * field for MAC
     */
    protected java.lang.String localMAC;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localMACTracker = false;

    /**
     * field for Loai
     */
    protected java.lang.String localLoai;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localLoaiTracker = false;

    /**
     * field for ChungLoai
     */
    protected java.lang.String localChungLoai;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localChungLoaiTracker = false;

    /**
     * field for Serial
     */
    protected java.lang.String localSerial;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSerialTracker = false;

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

    public boolean isNhaCungCapSpecified() {
        return localNhaCungCapTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getNhaCungCap() {
        return localNhaCungCap;
    }

    /**
     * Auto generated setter method
     * @param param NhaCungCap
     */
    public void setNhaCungCap(java.lang.String param) {
        localNhaCungCapTracker = param != null;

        this.localNhaCungCap = param;
    }

    public boolean isNgaySXSpecified() {
        return localNgaySXTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getNgaySX() {
        return localNgaySX;
    }

    /**
     * Auto generated setter method
     * @param param NgaySX
     */
    public void setNgaySX(java.lang.String param) {
        localNgaySXTracker = param != null;

        this.localNgaySX = param;
    }

    public boolean isNgayHetHanBHSpecified() {
        return localNgayHetHanBHTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getNgayHetHanBH() {
        return localNgayHetHanBH;
    }

    /**
     * Auto generated setter method
     * @param param NgayHetHanBH
     */
    public void setNgayHetHanBH(java.lang.String param) {
        localNgayHetHanBHTracker = param != null;

        this.localNgayHetHanBH = param;
    }

    public boolean isMACSpecified() {
        return localMACTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getMAC() {
        return localMAC;
    }

    /**
     * Auto generated setter method
     * @param param MAC
     */
    public void setMAC(java.lang.String param) {
        localMACTracker = param != null;

        this.localMAC = param;
    }

    public boolean isLoaiSpecified() {
        return localLoaiTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getLoai() {
        return localLoai;
    }

    /**
     * Auto generated setter method
     * @param param Loai
     */
    public void setLoai(java.lang.String param) {
        localLoaiTracker = param != null;

        this.localLoai = param;
    }

    public boolean isChungLoaiSpecified() {
        return localChungLoaiTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getChungLoai() {
        return localChungLoai;
    }

    /**
     * Auto generated setter method
     * @param param ChungLoai
     */
    public void setChungLoai(java.lang.String param) {
        localChungLoaiTracker = param != null;

        this.localChungLoai = param;
    }

    public boolean isSerialSpecified() {
        return localSerialTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getSerial() {
        return localSerial;
    }

    /**
     * Auto generated setter method
     * @param param Serial
     */
    public void setSerial(java.lang.String param) {
        localSerialTracker = param != null;

        this.localSerial = param;
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
                    namespacePrefix + ":StbInfoVO", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "StbInfoVO", xmlWriter);
            }
        }

        if (localNhaCungCapTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "NhaCungCap", xmlWriter);

            if (localNhaCungCap == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "NhaCungCap cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localNhaCungCap);
            }

            xmlWriter.writeEndElement();
        }

        if (localNgaySXTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "NgaySX", xmlWriter);

            if (localNgaySX == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "NgaySX cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localNgaySX);
            }

            xmlWriter.writeEndElement();
        }

        if (localNgayHetHanBHTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "NgayHetHanBH", xmlWriter);

            if (localNgayHetHanBH == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "NgayHetHanBH cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localNgayHetHanBH);
            }

            xmlWriter.writeEndElement();
        }

        if (localMACTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "MAC", xmlWriter);

            if (localMAC == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "MAC cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localMAC);
            }

            xmlWriter.writeEndElement();
        }

        if (localLoaiTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "Loai", xmlWriter);

            if (localLoai == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Loai cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localLoai);
            }

            xmlWriter.writeEndElement();
        }

        if (localChungLoaiTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "ChungLoai", xmlWriter);

            if (localChungLoai == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "ChungLoai cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localChungLoai);
            }

            xmlWriter.writeEndElement();
        }

        if (localSerialTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "Serial", xmlWriter);

            if (localSerial == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Serial cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localSerial);
            }

            xmlWriter.writeEndElement();
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
        public static StbInfoVO parse(javax.xml.stream.XMLStreamReader reader)
            throws java.lang.Exception {
            StbInfoVO object = new StbInfoVO();

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

                        if (!"StbInfoVO".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (StbInfoVO) org.tempuri.ExtensionMapper.getTypeObject(nsUri,
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
                            "NhaCungCap").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "NhaCungCap" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setNhaCungCap(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "NgaySX").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "NgaySX" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setNgaySX(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "NgayHetHanBH").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "NgayHetHanBH" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setNgayHetHanBH(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "MAC").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "MAC" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setMAC(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "Loai").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Loai" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setLoai(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "ChungLoai").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "ChungLoai" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setChungLoai(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "Serial").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Serial" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setSerial(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

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
