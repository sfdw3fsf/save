/**
 * ChangeSubscriber.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:06:07 GMT)
 */
package org.tempuri;


/**
 *  ChangeSubscriber bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class ChangeSubscriber implements org.apache.axis2.databinding.ADBBean {
    public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://tempuri.org/",
            "ChangeSubscriber", "ns1");

    /**
     * field for Name
     */
    protected java.lang.String localName;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNameTracker = false;

    /**
     * field for Birthday
     */
    protected java.lang.String localBirthday;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localBirthdayTracker = false;

    /**
     * field for Sex
     */
    protected java.lang.String localSex;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSexTracker = false;

    /**
     * field for FirstAdd
     */
    protected java.lang.String localFirstAdd;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localFirstAddTracker = false;

    /**
     * field for SecondAdd
     */
    protected java.lang.String localSecondAdd;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSecondAddTracker = false;

    /**
     * field for IDNo
     */
    protected java.lang.String localIDNo;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localIDNoTracker = false;

    /**
     * field for TelNo
     */
    protected java.lang.String localTelNo;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localTelNoTracker = false;

    /**
     * field for CellNo
     */
    protected java.lang.String localCellNo;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localCellNoTracker = false;

    /**
     * field for AreaCode
     */
    protected java.lang.String localAreaCode;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localAreaCodeTracker = false;

    /**
     * field for Email
     */
    protected java.lang.String localEmail;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localEmailTracker = false;

    /**
     * field for YM
     */
    protected java.lang.String localYM;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localYMTracker = false;

    /**
     * field for Website
     */
    protected java.lang.String localWebsite;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localWebsiteTracker = false;

    /**
     * field for FaxNo
     */
    protected java.lang.String localFaxNo;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localFaxNoTracker = false;

    /**
     * field for IPTVAccount
     */
    protected java.lang.String localIPTVAccount;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localIPTVAccountTracker = false;

    public boolean isNameSpecified() {
        return localNameTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getName() {
        return localName;
    }

    /**
     * Auto generated setter method
     * @param param Name
     */
    public void setName(java.lang.String param) {
        localNameTracker = param != null;

        this.localName = param;
    }

    public boolean isBirthdaySpecified() {
        return localBirthdayTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getBirthday() {
        return localBirthday;
    }

    /**
     * Auto generated setter method
     * @param param Birthday
     */
    public void setBirthday(java.lang.String param) {
        localBirthdayTracker = param != null;

        this.localBirthday = param;
    }

    public boolean isSexSpecified() {
        return localSexTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getSex() {
        return localSex;
    }

    /**
     * Auto generated setter method
     * @param param Sex
     */
    public void setSex(java.lang.String param) {
        localSexTracker = param != null;

        this.localSex = param;
    }

    public boolean isFirstAddSpecified() {
        return localFirstAddTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getFirstAdd() {
        return localFirstAdd;
    }

    /**
     * Auto generated setter method
     * @param param FirstAdd
     */
    public void setFirstAdd(java.lang.String param) {
        localFirstAddTracker = param != null;

        this.localFirstAdd = param;
    }

    public boolean isSecondAddSpecified() {
        return localSecondAddTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getSecondAdd() {
        return localSecondAdd;
    }

    /**
     * Auto generated setter method
     * @param param SecondAdd
     */
    public void setSecondAdd(java.lang.String param) {
        localSecondAddTracker = param != null;

        this.localSecondAdd = param;
    }

    public boolean isIDNoSpecified() {
        return localIDNoTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getIDNo() {
        return localIDNo;
    }

    /**
     * Auto generated setter method
     * @param param IDNo
     */
    public void setIDNo(java.lang.String param) {
        localIDNoTracker = param != null;

        this.localIDNo = param;
    }

    public boolean isTelNoSpecified() {
        return localTelNoTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getTelNo() {
        return localTelNo;
    }

    /**
     * Auto generated setter method
     * @param param TelNo
     */
    public void setTelNo(java.lang.String param) {
        localTelNoTracker = param != null;

        this.localTelNo = param;
    }

    public boolean isCellNoSpecified() {
        return localCellNoTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getCellNo() {
        return localCellNo;
    }

    /**
     * Auto generated setter method
     * @param param CellNo
     */
    public void setCellNo(java.lang.String param) {
        localCellNoTracker = param != null;

        this.localCellNo = param;
    }

    public boolean isAreaCodeSpecified() {
        return localAreaCodeTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getAreaCode() {
        return localAreaCode;
    }

    /**
     * Auto generated setter method
     * @param param AreaCode
     */
    public void setAreaCode(java.lang.String param) {
        localAreaCodeTracker = param != null;

        this.localAreaCode = param;
    }

    public boolean isEmailSpecified() {
        return localEmailTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getEmail() {
        return localEmail;
    }

    /**
     * Auto generated setter method
     * @param param Email
     */
    public void setEmail(java.lang.String param) {
        localEmailTracker = param != null;

        this.localEmail = param;
    }

    public boolean isYMSpecified() {
        return localYMTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getYM() {
        return localYM;
    }

    /**
     * Auto generated setter method
     * @param param YM
     */
    public void setYM(java.lang.String param) {
        localYMTracker = param != null;

        this.localYM = param;
    }

    public boolean isWebsiteSpecified() {
        return localWebsiteTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getWebsite() {
        return localWebsite;
    }

    /**
     * Auto generated setter method
     * @param param Website
     */
    public void setWebsite(java.lang.String param) {
        localWebsiteTracker = param != null;

        this.localWebsite = param;
    }

    public boolean isFaxNoSpecified() {
        return localFaxNoTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getFaxNo() {
        return localFaxNo;
    }

    /**
     * Auto generated setter method
     * @param param FaxNo
     */
    public void setFaxNo(java.lang.String param) {
        localFaxNoTracker = param != null;

        this.localFaxNo = param;
    }

    public boolean isIPTVAccountSpecified() {
        return localIPTVAccountTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getIPTVAccount() {
        return localIPTVAccount;
    }

    /**
     * Auto generated setter method
     * @param param IPTVAccount
     */
    public void setIPTVAccount(java.lang.String param) {
        localIPTVAccountTracker = param != null;

        this.localIPTVAccount = param;
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
                    namespacePrefix + ":ChangeSubscriber", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "ChangeSubscriber", xmlWriter);
            }
        }

        if (localNameTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "Name", xmlWriter);

            if (localName == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Name cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localName);
            }

            xmlWriter.writeEndElement();
        }

        if (localBirthdayTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "Birthday", xmlWriter);

            if (localBirthday == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Birthday cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localBirthday);
            }

            xmlWriter.writeEndElement();
        }

        if (localSexTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "Sex", xmlWriter);

            if (localSex == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Sex cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localSex);
            }

            xmlWriter.writeEndElement();
        }

        if (localFirstAddTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "FirstAdd", xmlWriter);

            if (localFirstAdd == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "FirstAdd cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localFirstAdd);
            }

            xmlWriter.writeEndElement();
        }

        if (localSecondAddTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "SecondAdd", xmlWriter);

            if (localSecondAdd == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "SecondAdd cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localSecondAdd);
            }

            xmlWriter.writeEndElement();
        }

        if (localIDNoTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "IDNo", xmlWriter);

            if (localIDNo == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "IDNo cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localIDNo);
            }

            xmlWriter.writeEndElement();
        }

        if (localTelNoTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "TelNo", xmlWriter);

            if (localTelNo == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "TelNo cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localTelNo);
            }

            xmlWriter.writeEndElement();
        }

        if (localCellNoTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "CellNo", xmlWriter);

            if (localCellNo == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "CellNo cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localCellNo);
            }

            xmlWriter.writeEndElement();
        }

        if (localAreaCodeTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "AreaCode", xmlWriter);

            if (localAreaCode == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "AreaCode cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localAreaCode);
            }

            xmlWriter.writeEndElement();
        }

        if (localEmailTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "Email", xmlWriter);

            if (localEmail == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Email cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localEmail);
            }

            xmlWriter.writeEndElement();
        }

        if (localYMTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "YM", xmlWriter);

            if (localYM == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "YM cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localYM);
            }

            xmlWriter.writeEndElement();
        }

        if (localWebsiteTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "Website", xmlWriter);

            if (localWebsite == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Website cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localWebsite);
            }

            xmlWriter.writeEndElement();
        }

        if (localFaxNoTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "FaxNo", xmlWriter);

            if (localFaxNo == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "FaxNo cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localFaxNo);
            }

            xmlWriter.writeEndElement();
        }

        if (localIPTVAccountTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "IPTVAccount", xmlWriter);

            if (localIPTVAccount == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "IPTVAccount cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localIPTVAccount);
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
        public static ChangeSubscriber parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            ChangeSubscriber object = new ChangeSubscriber();

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

                        if (!"ChangeSubscriber".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (ChangeSubscriber) org.tempuri.ExtensionMapper.getTypeObject(nsUri,
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
                            "Name").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Name" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "Birthday").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Birthday" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setBirthday(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "Sex").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Sex" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setSex(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "FirstAdd").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "FirstAdd" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setFirstAdd(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "SecondAdd").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "SecondAdd" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setSecondAdd(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "IDNo").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "IDNo" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setIDNo(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "TelNo").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "TelNo" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setTelNo(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "CellNo").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "CellNo" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setCellNo(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "AreaCode").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "AreaCode" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setAreaCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "Email").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Email" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setEmail(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "YM").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "YM" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setYM(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "Website").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Website" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setWebsite(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "FaxNo").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "FaxNo" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setFaxNo(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "IPTVAccount").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "IPTVAccount" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setIPTVAccount(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
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
