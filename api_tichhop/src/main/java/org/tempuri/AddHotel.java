/**
 * AddHotel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:06:07 GMT)
 */
package org.tempuri;


/**
 *  AddHotel bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class AddHotel implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://tempuri.org/",
            "AddHotel", "ns1");

    /**
     * field for StrUserName
     */
    protected java.lang.String localStrUserName;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localStrUserNameTracker = false;

    /**
     * field for StrPassword
     */
    protected java.lang.String localStrPassword;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localStrPasswordTracker = false;

    /**
     * field for StrName
     */
    protected java.lang.String localStrName;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localStrNameTracker = false;

    /**
     * field for StrOwner
     */
    protected java.lang.String localStrOwner;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localStrOwnerTracker = false;

    /**
     * field for StrCard
     */
    protected java.lang.String localStrCard;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localStrCardTracker = false;

    /**
     * field for StrAddress
     */
    protected java.lang.String localStrAddress;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localStrAddressTracker = false;

    /**
     * field for StrSigndate
     */
    protected java.lang.String localStrSigndate;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localStrSigndateTracker = false;

    /**
     * field for StrContractID
     */
    protected java.lang.String localStrContractID;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localStrContractIDTracker = false;

    /**
     * field for StrBillDate
     */
    protected java.lang.String localStrBillDate;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localStrBillDateTracker = false;

    /**
     * field for StrMethod
     */
    protected java.lang.String localStrMethod;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localStrMethodTracker = false;

    /**
     * field for StrPayMode
     */
    protected java.lang.String localStrPayMode;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localStrPayModeTracker = false;

    /**
     * field for StrPackageID
     */
    protected java.lang.String localStrPackageID;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localStrPackageIDTracker = false;

    /**
     * field for StrProviceID
     */
    protected java.lang.String localStrProviceID;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localStrProviceIDTracker = false;

    /**
     * field for IAccNumber
     */
    protected int localIAccNumber;

    public boolean isStrUserNameSpecified() {
        return localStrUserNameTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStrUserName() {
        return localStrUserName;
    }

    /**
     * Auto generated setter method
     * @param param StrUserName
     */
    public void setStrUserName(java.lang.String param) {
        localStrUserNameTracker = param != null;

        this.localStrUserName = param;
    }

    public boolean isStrPasswordSpecified() {
        return localStrPasswordTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStrPassword() {
        return localStrPassword;
    }

    /**
     * Auto generated setter method
     * @param param StrPassword
     */
    public void setStrPassword(java.lang.String param) {
        localStrPasswordTracker = param != null;

        this.localStrPassword = param;
    }

    public boolean isStrNameSpecified() {
        return localStrNameTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStrName() {
        return localStrName;
    }

    /**
     * Auto generated setter method
     * @param param StrName
     */
    public void setStrName(java.lang.String param) {
        localStrNameTracker = param != null;

        this.localStrName = param;
    }

    public boolean isStrOwnerSpecified() {
        return localStrOwnerTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStrOwner() {
        return localStrOwner;
    }

    /**
     * Auto generated setter method
     * @param param StrOwner
     */
    public void setStrOwner(java.lang.String param) {
        localStrOwnerTracker = param != null;

        this.localStrOwner = param;
    }

    public boolean isStrCardSpecified() {
        return localStrCardTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStrCard() {
        return localStrCard;
    }

    /**
     * Auto generated setter method
     * @param param StrCard
     */
    public void setStrCard(java.lang.String param) {
        localStrCardTracker = param != null;

        this.localStrCard = param;
    }

    public boolean isStrAddressSpecified() {
        return localStrAddressTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStrAddress() {
        return localStrAddress;
    }

    /**
     * Auto generated setter method
     * @param param StrAddress
     */
    public void setStrAddress(java.lang.String param) {
        localStrAddressTracker = param != null;

        this.localStrAddress = param;
    }

    public boolean isStrSigndateSpecified() {
        return localStrSigndateTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStrSigndate() {
        return localStrSigndate;
    }

    /**
     * Auto generated setter method
     * @param param StrSigndate
     */
    public void setStrSigndate(java.lang.String param) {
        localStrSigndateTracker = param != null;

        this.localStrSigndate = param;
    }

    public boolean isStrContractIDSpecified() {
        return localStrContractIDTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStrContractID() {
        return localStrContractID;
    }

    /**
     * Auto generated setter method
     * @param param StrContractID
     */
    public void setStrContractID(java.lang.String param) {
        localStrContractIDTracker = param != null;

        this.localStrContractID = param;
    }

    public boolean isStrBillDateSpecified() {
        return localStrBillDateTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStrBillDate() {
        return localStrBillDate;
    }

    /**
     * Auto generated setter method
     * @param param StrBillDate
     */
    public void setStrBillDate(java.lang.String param) {
        localStrBillDateTracker = param != null;

        this.localStrBillDate = param;
    }

    public boolean isStrMethodSpecified() {
        return localStrMethodTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStrMethod() {
        return localStrMethod;
    }

    /**
     * Auto generated setter method
     * @param param StrMethod
     */
    public void setStrMethod(java.lang.String param) {
        localStrMethodTracker = param != null;

        this.localStrMethod = param;
    }

    public boolean isStrPayModeSpecified() {
        return localStrPayModeTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStrPayMode() {
        return localStrPayMode;
    }

    /**
     * Auto generated setter method
     * @param param StrPayMode
     */
    public void setStrPayMode(java.lang.String param) {
        localStrPayModeTracker = param != null;

        this.localStrPayMode = param;
    }

    public boolean isStrPackageIDSpecified() {
        return localStrPackageIDTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStrPackageID() {
        return localStrPackageID;
    }

    /**
     * Auto generated setter method
     * @param param StrPackageID
     */
    public void setStrPackageID(java.lang.String param) {
        localStrPackageIDTracker = param != null;

        this.localStrPackageID = param;
    }

    public boolean isStrProviceIDSpecified() {
        return localStrProviceIDTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStrProviceID() {
        return localStrProviceID;
    }

    /**
     * Auto generated setter method
     * @param param StrProviceID
     */
    public void setStrProviceID(java.lang.String param) {
        localStrProviceIDTracker = param != null;

        this.localStrProviceID = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getIAccNumber() {
        return localIAccNumber;
    }

    /**
     * Auto generated setter method
     * @param param IAccNumber
     */
    public void setIAccNumber(int param) {
        this.localIAccNumber = param;
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
                this, MY_QNAME));
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
                    namespacePrefix + ":AddHotel", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "AddHotel", xmlWriter);
            }
        }

        if (localStrUserNameTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "strUserName", xmlWriter);

            if (localStrUserName == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "strUserName cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localStrUserName);
            }

            xmlWriter.writeEndElement();
        }

        if (localStrPasswordTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "strPassword", xmlWriter);

            if (localStrPassword == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "strPassword cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localStrPassword);
            }

            xmlWriter.writeEndElement();
        }

        if (localStrNameTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "strName", xmlWriter);

            if (localStrName == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "strName cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localStrName);
            }

            xmlWriter.writeEndElement();
        }

        if (localStrOwnerTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "strOwner", xmlWriter);

            if (localStrOwner == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "strOwner cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localStrOwner);
            }

            xmlWriter.writeEndElement();
        }

        if (localStrCardTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "strCard", xmlWriter);

            if (localStrCard == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "strCard cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localStrCard);
            }

            xmlWriter.writeEndElement();
        }

        if (localStrAddressTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "strAddress", xmlWriter);

            if (localStrAddress == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "strAddress cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localStrAddress);
            }

            xmlWriter.writeEndElement();
        }

        if (localStrSigndateTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "strSigndate", xmlWriter);

            if (localStrSigndate == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "strSigndate cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localStrSigndate);
            }

            xmlWriter.writeEndElement();
        }

        if (localStrContractIDTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "strContractID", xmlWriter);

            if (localStrContractID == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "strContractID cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localStrContractID);
            }

            xmlWriter.writeEndElement();
        }

        if (localStrBillDateTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "strBillDate", xmlWriter);

            if (localStrBillDate == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "strBillDate cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localStrBillDate);
            }

            xmlWriter.writeEndElement();
        }

        if (localStrMethodTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "strMethod", xmlWriter);

            if (localStrMethod == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "strMethod cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localStrMethod);
            }

            xmlWriter.writeEndElement();
        }

        if (localStrPayModeTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "strPayMode", xmlWriter);

            if (localStrPayMode == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "strPayMode cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localStrPayMode);
            }

            xmlWriter.writeEndElement();
        }

        if (localStrPackageIDTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "strPackageID", xmlWriter);

            if (localStrPackageID == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "strPackageID cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localStrPackageID);
            }

            xmlWriter.writeEndElement();
        }

        if (localStrProviceIDTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "strProviceID", xmlWriter);

            if (localStrProviceID == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "strProviceID cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localStrProviceID);
            }

            xmlWriter.writeEndElement();
        }

        namespace = "http://tempuri.org/";
        writeStartElement(null, namespace, "iAccNumber", xmlWriter);

        if (localIAccNumber == java.lang.Integer.MIN_VALUE) {
            throw new org.apache.axis2.databinding.ADBException(
                "iAccNumber cannot be null!!");
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localIAccNumber));
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
        public static AddHotel parse(javax.xml.stream.XMLStreamReader reader)
            throws java.lang.Exception {
            AddHotel object = new AddHotel();

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

                        if (!"AddHotel".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (AddHotel) org.tempuri.ExtensionMapper.getTypeObject(nsUri,
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
                            "strUserName").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "strUserName" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setStrUserName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "strPassword").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "strPassword" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setStrPassword(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "strName").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "strName" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setStrName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "strOwner").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "strOwner" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setStrOwner(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "strCard").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "strCard" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setStrCard(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "strAddress").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "strAddress" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setStrAddress(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "strSigndate").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "strSigndate" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setStrSigndate(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "strContractID").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "strContractID" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setStrContractID(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "strBillDate").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "strBillDate" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setStrBillDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "strMethod").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "strMethod" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setStrMethod(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "strPayMode").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "strPayMode" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setStrPayMode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "strPackageID").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "strPackageID" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setStrPackageID(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "strProviceID").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "strProviceID" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setStrProviceID(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "iAccNumber").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "iAccNumber" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setIAccNumber(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
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
