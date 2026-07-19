/**
 * RegistAddOnDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:06:07 GMT)
 */
package org.tempuri;


/**
 *  RegistAddOnDTO bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class RegistAddOnDTO implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = RegistAddOnDTO
       Namespace URI = http://tempuri.org/
       Namespace Prefix = ns1
     */

    /**
     * field for Username
     */
    protected java.lang.String localUsername;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localUsernameTracker = false;

    /**
     * field for Zipcode
     */
    protected java.lang.String localZipcode;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localZipcodeTracker = false;

    /**
     * field for Service_cd
     */
    protected java.lang.String localService_cd;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localService_cdTracker = false;

    /**
     * field for Pack_cd
     */
    protected java.lang.String localPack_cd;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localPack_cdTracker = false;

    /**
     * field for Subpack_cd
     */
    protected java.lang.String localSubpack_cd;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSubpack_cdTracker = false;

    /**
     * field for Unit_price
     */
    protected int localUnit_price;

    /**
     * field for Reason
     */
    protected java.lang.String localReason;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localReasonTracker = false;

    /**
     * field for Payment_type
     */
    protected java.lang.String localPayment_type;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localPayment_typeTracker = false;

    /**
     * field for Pre_period
     */
    protected java.lang.String localPre_period;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localPre_periodTracker = false;

    /**
     * field for Pre_value
     */
    protected short localPre_value;

    /**
     * field for Pre_expire_time
     */
    protected java.lang.String localPre_expire_time;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localPre_expire_timeTracker = false;

    public boolean isUsernameSpecified() {
        return localUsernameTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getUsername() {
        return localUsername;
    }

    /**
     * Auto generated setter method
     * @param param Username
     */
    public void setUsername(java.lang.String param) {
        localUsernameTracker = param != null;

        this.localUsername = param;
    }

    public boolean isZipcodeSpecified() {
        return localZipcodeTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getZipcode() {
        return localZipcode;
    }

    /**
     * Auto generated setter method
     * @param param Zipcode
     */
    public void setZipcode(java.lang.String param) {
        localZipcodeTracker = param != null;

        this.localZipcode = param;
    }

    public boolean isService_cdSpecified() {
        return localService_cdTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getService_cd() {
        return localService_cd;
    }

    /**
     * Auto generated setter method
     * @param param Service_cd
     */
    public void setService_cd(java.lang.String param) {
        localService_cdTracker = param != null;

        this.localService_cd = param;
    }

    public boolean isPack_cdSpecified() {
        return localPack_cdTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getPack_cd() {
        return localPack_cd;
    }

    /**
     * Auto generated setter method
     * @param param Pack_cd
     */
    public void setPack_cd(java.lang.String param) {
        localPack_cdTracker = param != null;

        this.localPack_cd = param;
    }

    public boolean isSubpack_cdSpecified() {
        return localSubpack_cdTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getSubpack_cd() {
        return localSubpack_cd;
    }

    /**
     * Auto generated setter method
     * @param param Subpack_cd
     */
    public void setSubpack_cd(java.lang.String param) {
        localSubpack_cdTracker = param != null;

        this.localSubpack_cd = param;
    }

    /**
     * Auto generated getter method
     * @return int
     */
    public int getUnit_price() {
        return localUnit_price;
    }

    /**
     * Auto generated setter method
     * @param param Unit_price
     */
    public void setUnit_price(int param) {
        this.localUnit_price = param;
    }

    public boolean isReasonSpecified() {
        return localReasonTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getReason() {
        return localReason;
    }

    /**
     * Auto generated setter method
     * @param param Reason
     */
    public void setReason(java.lang.String param) {
        localReasonTracker = param != null;

        this.localReason = param;
    }

    public boolean isPayment_typeSpecified() {
        return localPayment_typeTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getPayment_type() {
        return localPayment_type;
    }

    /**
     * Auto generated setter method
     * @param param Payment_type
     */
    public void setPayment_type(java.lang.String param) {
        localPayment_typeTracker = param != null;

        this.localPayment_type = param;
    }

    public boolean isPre_periodSpecified() {
        return localPre_periodTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getPre_period() {
        return localPre_period;
    }

    /**
     * Auto generated setter method
     * @param param Pre_period
     */
    public void setPre_period(java.lang.String param) {
        localPre_periodTracker = param != null;

        this.localPre_period = param;
    }

    /**
     * Auto generated getter method
     * @return short
     */
    public short getPre_value() {
        return localPre_value;
    }

    /**
     * Auto generated setter method
     * @param param Pre_value
     */
    public void setPre_value(short param) {
        this.localPre_value = param;
    }

    public boolean isPre_expire_timeSpecified() {
        return localPre_expire_timeTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getPre_expire_time() {
        return localPre_expire_time;
    }

    /**
     * Auto generated setter method
     * @param param Pre_expire_time
     */
    public void setPre_expire_time(java.lang.String param) {
        localPre_expire_timeTracker = param != null;

        this.localPre_expire_time = param;
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
                    namespacePrefix + ":RegistAddOnDTO", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "RegistAddOnDTO", xmlWriter);
            }
        }

        if (localUsernameTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "username", xmlWriter);

            if (localUsername == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "username cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localUsername);
            }

            xmlWriter.writeEndElement();
        }

        if (localZipcodeTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "zipcode", xmlWriter);

            if (localZipcode == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "zipcode cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localZipcode);
            }

            xmlWriter.writeEndElement();
        }

        if (localService_cdTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "service_cd", xmlWriter);

            if (localService_cd == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "service_cd cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localService_cd);
            }

            xmlWriter.writeEndElement();
        }

        if (localPack_cdTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "pack_cd", xmlWriter);

            if (localPack_cd == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "pack_cd cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localPack_cd);
            }

            xmlWriter.writeEndElement();
        }

        if (localSubpack_cdTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "subpack_cd", xmlWriter);

            if (localSubpack_cd == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "subpack_cd cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localSubpack_cd);
            }

            xmlWriter.writeEndElement();
        }

        namespace = "http://tempuri.org/";
        writeStartElement(null, namespace, "unit_price", xmlWriter);

        if (localUnit_price == java.lang.Integer.MIN_VALUE) {
            throw new org.apache.axis2.databinding.ADBException(
                "unit_price cannot be null!!");
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localUnit_price));
        }

        xmlWriter.writeEndElement();

        if (localReasonTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "reason", xmlWriter);

            if (localReason == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "reason cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localReason);
            }

            xmlWriter.writeEndElement();
        }

        if (localPayment_typeTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "payment_type", xmlWriter);

            if (localPayment_type == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "payment_type cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localPayment_type);
            }

            xmlWriter.writeEndElement();
        }

        if (localPre_periodTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "pre_period", xmlWriter);

            if (localPre_period == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "pre_period cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localPre_period);
            }

            xmlWriter.writeEndElement();
        }

        namespace = "http://tempuri.org/";
        writeStartElement(null, namespace, "pre_value", xmlWriter);

        if (localPre_value == java.lang.Short.MIN_VALUE) {
            throw new org.apache.axis2.databinding.ADBException(
                "pre_value cannot be null!!");
        } else {
            xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                    localPre_value));
        }

        xmlWriter.writeEndElement();

        if (localPre_expire_timeTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "pre_expire_time", xmlWriter);

            if (localPre_expire_time == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "pre_expire_time cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localPre_expire_time);
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
        public static RegistAddOnDTO parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            RegistAddOnDTO object = new RegistAddOnDTO();

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

                        if (!"RegistAddOnDTO".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (RegistAddOnDTO) org.tempuri.ExtensionMapper.getTypeObject(nsUri,
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
                            "username").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "username" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setUsername(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "zipcode").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "zipcode" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setZipcode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "service_cd").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "service_cd" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setService_cd(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "pack_cd").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "pack_cd" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setPack_cd(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "subpack_cd").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "subpack_cd" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setSubpack_cd(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "unit_price").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "unit_price" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setUnit_price(org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(
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
                            "reason").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "reason" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setReason(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "payment_type").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "payment_type" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setPayment_type(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "pre_period").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "pre_period" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setPre_period(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "pre_value").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "pre_value" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setPre_value(org.apache.axis2.databinding.utils.ConverterUtil.convertToShort(
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
                            "pre_expire_time").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "pre_expire_time" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setPre_expire_time(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
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
