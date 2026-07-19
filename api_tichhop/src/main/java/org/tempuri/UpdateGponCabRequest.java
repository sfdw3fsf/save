/**
 * UpdateGponCabRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:06:07 GMT)
 */
package org.tempuri;


/**
 *  UpdateGponCabRequest bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class UpdateGponCabRequest implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = UpdateGponCabRequest
       Namespace URI = http://tempuri.org/
       Namespace Prefix = ns1
     */

    /**
     * field for UserType
     */
    protected java.lang.String localUserType;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localUserTypeTracker = false;

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
     * field for MethodFlg
     */
    protected java.lang.String localMethodFlg;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localMethodFlgTracker = false;

    /**
     * field for ComboUsername
     */
    protected java.lang.String localComboUsername;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localComboUsernameTracker = false;

    /**
     * field for CabNumber
     */
    protected java.lang.String localCabNumber;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localCabNumberTracker = false;

    /**
     * field for ContractCD
     */
    protected java.lang.String localContractCD;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localContractCDTracker = false;

    /**
     * field for SignDate
     */
    protected java.lang.String localSignDate;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSignDateTracker = false;

    /**
     * field for ActiveDate
     */
    protected java.lang.String localActiveDate;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localActiveDateTracker = false;

    /**
     * field for ChargeType
     */
    protected java.lang.String localChargeType;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localChargeTypeTracker = false;

    public boolean isUserTypeSpecified() {
        return localUserTypeTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getUserType() {
        return localUserType;
    }

    /**
     * Auto generated setter method
     * @param param UserType
     */
    public void setUserType(java.lang.String param) {
        localUserTypeTracker = param != null;

        this.localUserType = param;
    }

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

    public boolean isMethodFlgSpecified() {
        return localMethodFlgTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getMethodFlg() {
        return localMethodFlg;
    }

    /**
     * Auto generated setter method
     * @param param MethodFlg
     */
    public void setMethodFlg(java.lang.String param) {
        localMethodFlgTracker = param != null;

        this.localMethodFlg = param;
    }

    public boolean isComboUsernameSpecified() {
        return localComboUsernameTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getComboUsername() {
        return localComboUsername;
    }

    /**
     * Auto generated setter method
     * @param param ComboUsername
     */
    public void setComboUsername(java.lang.String param) {
        localComboUsernameTracker = param != null;

        this.localComboUsername = param;
    }

    public boolean isCabNumberSpecified() {
        return localCabNumberTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getCabNumber() {
        return localCabNumber;
    }

    /**
     * Auto generated setter method
     * @param param CabNumber
     */
    public void setCabNumber(java.lang.String param) {
        localCabNumberTracker = param != null;

        this.localCabNumber = param;
    }

    public boolean isContractCDSpecified() {
        return localContractCDTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getContractCD() {
        return localContractCD;
    }

    /**
     * Auto generated setter method
     * @param param ContractCD
     */
    public void setContractCD(java.lang.String param) {
        localContractCDTracker = param != null;

        this.localContractCD = param;
    }

    public boolean isSignDateSpecified() {
        return localSignDateTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getSignDate() {
        return localSignDate;
    }

    /**
     * Auto generated setter method
     * @param param SignDate
     */
    public void setSignDate(java.lang.String param) {
        localSignDateTracker = param != null;

        this.localSignDate = param;
    }

    public boolean isActiveDateSpecified() {
        return localActiveDateTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getActiveDate() {
        return localActiveDate;
    }

    /**
     * Auto generated setter method
     * @param param ActiveDate
     */
    public void setActiveDate(java.lang.String param) {
        localActiveDateTracker = param != null;

        this.localActiveDate = param;
    }

    public boolean isChargeTypeSpecified() {
        return localChargeTypeTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getChargeType() {
        return localChargeType;
    }

    /**
     * Auto generated setter method
     * @param param ChargeType
     */
    public void setChargeType(java.lang.String param) {
        localChargeTypeTracker = param != null;

        this.localChargeType = param;
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
                    namespacePrefix + ":UpdateGponCabRequest", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "UpdateGponCabRequest", xmlWriter);
            }
        }

        if (localUserTypeTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "UserType", xmlWriter);

            if (localUserType == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "UserType cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localUserType);
            }

            xmlWriter.writeEndElement();
        }

        if (localUsernameTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "Username", xmlWriter);

            if (localUsername == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Username cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localUsername);
            }

            xmlWriter.writeEndElement();
        }

        if (localMethodFlgTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "MethodFlg", xmlWriter);

            if (localMethodFlg == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "MethodFlg cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localMethodFlg);
            }

            xmlWriter.writeEndElement();
        }

        if (localComboUsernameTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "ComboUsername", xmlWriter);

            if (localComboUsername == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "ComboUsername cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localComboUsername);
            }

            xmlWriter.writeEndElement();
        }

        if (localCabNumberTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "CabNumber", xmlWriter);

            if (localCabNumber == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "CabNumber cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localCabNumber);
            }

            xmlWriter.writeEndElement();
        }

        if (localContractCDTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "ContractCD", xmlWriter);

            if (localContractCD == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "ContractCD cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localContractCD);
            }

            xmlWriter.writeEndElement();
        }

        if (localSignDateTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "SignDate", xmlWriter);

            if (localSignDate == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "SignDate cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localSignDate);
            }

            xmlWriter.writeEndElement();
        }

        if (localActiveDateTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "ActiveDate", xmlWriter);

            if (localActiveDate == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "ActiveDate cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localActiveDate);
            }

            xmlWriter.writeEndElement();
        }

        if (localChargeTypeTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "ChargeType", xmlWriter);

            if (localChargeType == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "ChargeType cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localChargeType);
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
        public static UpdateGponCabRequest parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            UpdateGponCabRequest object = new UpdateGponCabRequest();

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

                        if (!"UpdateGponCabRequest".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (UpdateGponCabRequest) org.tempuri.ExtensionMapper.getTypeObject(nsUri,
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
                            "UserType").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "UserType" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setUserType(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "Username").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Username" + "  cannot be null");
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
                            "MethodFlg").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "MethodFlg" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setMethodFlg(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "ComboUsername").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "ComboUsername" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setComboUsername(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "CabNumber").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "CabNumber" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setCabNumber(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "ContractCD").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "ContractCD" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setContractCD(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "SignDate").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "SignDate" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setSignDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "ActiveDate").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "ActiveDate" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setActiveDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "ChargeType").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "ChargeType" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setChargeType(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
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
