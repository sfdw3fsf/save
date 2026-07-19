/**
 * GetSubscriberV3InfoVO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:06:07 GMT)
 */
package org.tempuri;


/**
 *  GetSubscriberV3InfoVO bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class GetSubscriberV3InfoVO implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = GetSubscriberV3InfoVO
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
     * field for Fullname
     */
    protected java.lang.String localFullname;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localFullnameTracker = false;

    /**
     * field for ZipCode
     */
    protected java.lang.String localZipCode;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localZipCodeTracker = false;

    /**
     * field for Sysflg
     */
    protected java.lang.String localSysflg;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSysflgTracker = false;

    /**
     * field for SerialNumber
     */
    protected java.lang.String localSerialNumber;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localSerialNumberTracker = false;

    /**
     * field for Billdate
     */
    protected java.lang.String localBilldate;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localBilldateTracker = false;

    /**
     * field for BillFlg
     */
    protected java.lang.String localBillFlg;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localBillFlgTracker = false;

    /**
     * field for CustStatus
     */
    protected java.lang.String localCustStatus;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localCustStatusTracker = false;

    /**
     * field for MegaFlg
     */
    protected java.lang.String localMegaFlg;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localMegaFlgTracker = false;

    /**
     * field for PackCd
     */
    protected java.lang.String localPackCd;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localPackCdTracker = false;

    /**
     * field for PackName
     */
    protected java.lang.String localPackName;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localPackNameTracker = false;

    /**
     * field for StbRentFlg
     */
    protected java.lang.String localStbRentFlg;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localStbRentFlgTracker = false;

    /**
     * field for UserMultiFlg
     */
    protected java.lang.String localUserMultiFlg;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localUserMultiFlgTracker = false;

    /**
     * field for UserFirst
     */
    protected java.lang.String localUserFirst;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localUserFirstTracker = false;

    /**
     * field for UserParentFlg
     */
    protected java.lang.String localUserParentFlg;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localUserParentFlgTracker = false;

    /**
     * field for UserParent
     */
    protected java.lang.String localUserParent;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localUserParentTracker = false;

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

    public boolean isFullnameSpecified() {
        return localFullnameTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getFullname() {
        return localFullname;
    }

    /**
     * Auto generated setter method
     * @param param Fullname
     */
    public void setFullname(java.lang.String param) {
        localFullnameTracker = param != null;

        this.localFullname = param;
    }

    public boolean isZipCodeSpecified() {
        return localZipCodeTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getZipCode() {
        return localZipCode;
    }

    /**
     * Auto generated setter method
     * @param param ZipCode
     */
    public void setZipCode(java.lang.String param) {
        localZipCodeTracker = param != null;

        this.localZipCode = param;
    }

    public boolean isSysflgSpecified() {
        return localSysflgTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getSysflg() {
        return localSysflg;
    }

    /**
     * Auto generated setter method
     * @param param Sysflg
     */
    public void setSysflg(java.lang.String param) {
        localSysflgTracker = param != null;

        this.localSysflg = param;
    }

    public boolean isSerialNumberSpecified() {
        return localSerialNumberTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getSerialNumber() {
        return localSerialNumber;
    }

    /**
     * Auto generated setter method
     * @param param SerialNumber
     */
    public void setSerialNumber(java.lang.String param) {
        localSerialNumberTracker = param != null;

        this.localSerialNumber = param;
    }

    public boolean isBilldateSpecified() {
        return localBilldateTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getBilldate() {
        return localBilldate;
    }

    /**
     * Auto generated setter method
     * @param param Billdate
     */
    public void setBilldate(java.lang.String param) {
        localBilldateTracker = param != null;

        this.localBilldate = param;
    }

    public boolean isBillFlgSpecified() {
        return localBillFlgTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getBillFlg() {
        return localBillFlg;
    }

    /**
     * Auto generated setter method
     * @param param BillFlg
     */
    public void setBillFlg(java.lang.String param) {
        localBillFlgTracker = param != null;

        this.localBillFlg = param;
    }

    public boolean isCustStatusSpecified() {
        return localCustStatusTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getCustStatus() {
        return localCustStatus;
    }

    /**
     * Auto generated setter method
     * @param param CustStatus
     */
    public void setCustStatus(java.lang.String param) {
        localCustStatusTracker = param != null;

        this.localCustStatus = param;
    }

    public boolean isMegaFlgSpecified() {
        return localMegaFlgTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getMegaFlg() {
        return localMegaFlg;
    }

    /**
     * Auto generated setter method
     * @param param MegaFlg
     */
    public void setMegaFlg(java.lang.String param) {
        localMegaFlgTracker = param != null;

        this.localMegaFlg = param;
    }

    public boolean isPackCdSpecified() {
        return localPackCdTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getPackCd() {
        return localPackCd;
    }

    /**
     * Auto generated setter method
     * @param param PackCd
     */
    public void setPackCd(java.lang.String param) {
        localPackCdTracker = param != null;

        this.localPackCd = param;
    }

    public boolean isPackNameSpecified() {
        return localPackNameTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getPackName() {
        return localPackName;
    }

    /**
     * Auto generated setter method
     * @param param PackName
     */
    public void setPackName(java.lang.String param) {
        localPackNameTracker = param != null;

        this.localPackName = param;
    }

    public boolean isStbRentFlgSpecified() {
        return localStbRentFlgTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getStbRentFlg() {
        return localStbRentFlg;
    }

    /**
     * Auto generated setter method
     * @param param StbRentFlg
     */
    public void setStbRentFlg(java.lang.String param) {
        localStbRentFlgTracker = param != null;

        this.localStbRentFlg = param;
    }

    public boolean isUserMultiFlgSpecified() {
        return localUserMultiFlgTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getUserMultiFlg() {
        return localUserMultiFlg;
    }

    /**
     * Auto generated setter method
     * @param param UserMultiFlg
     */
    public void setUserMultiFlg(java.lang.String param) {
        localUserMultiFlgTracker = param != null;

        this.localUserMultiFlg = param;
    }

    public boolean isUserFirstSpecified() {
        return localUserFirstTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getUserFirst() {
        return localUserFirst;
    }

    /**
     * Auto generated setter method
     * @param param UserFirst
     */
    public void setUserFirst(java.lang.String param) {
        localUserFirstTracker = param != null;

        this.localUserFirst = param;
    }

    public boolean isUserParentFlgSpecified() {
        return localUserParentFlgTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getUserParentFlg() {
        return localUserParentFlg;
    }

    /**
     * Auto generated setter method
     * @param param UserParentFlg
     */
    public void setUserParentFlg(java.lang.String param) {
        localUserParentFlgTracker = param != null;

        this.localUserParentFlg = param;
    }

    public boolean isUserParentSpecified() {
        return localUserParentTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getUserParent() {
        return localUserParent;
    }

    /**
     * Auto generated setter method
     * @param param UserParent
     */
    public void setUserParent(java.lang.String param) {
        localUserParentTracker = param != null;

        this.localUserParent = param;
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
                    namespacePrefix + ":GetSubscriberV3InfoVO", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "GetSubscriberV3InfoVO", xmlWriter);
            }
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

        if (localFullnameTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "Fullname", xmlWriter);

            if (localFullname == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Fullname cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localFullname);
            }

            xmlWriter.writeEndElement();
        }

        if (localZipCodeTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "ZipCode", xmlWriter);

            if (localZipCode == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "ZipCode cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localZipCode);
            }

            xmlWriter.writeEndElement();
        }

        if (localSysflgTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "Sysflg", xmlWriter);

            if (localSysflg == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Sysflg cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localSysflg);
            }

            xmlWriter.writeEndElement();
        }

        if (localSerialNumberTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "SerialNumber", xmlWriter);

            if (localSerialNumber == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "SerialNumber cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localSerialNumber);
            }

            xmlWriter.writeEndElement();
        }

        if (localBilldateTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "Billdate", xmlWriter);

            if (localBilldate == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Billdate cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localBilldate);
            }

            xmlWriter.writeEndElement();
        }

        if (localBillFlgTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "BillFlg", xmlWriter);

            if (localBillFlg == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "BillFlg cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localBillFlg);
            }

            xmlWriter.writeEndElement();
        }

        if (localCustStatusTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "CustStatus", xmlWriter);

            if (localCustStatus == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "CustStatus cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localCustStatus);
            }

            xmlWriter.writeEndElement();
        }

        if (localMegaFlgTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "MegaFlg", xmlWriter);

            if (localMegaFlg == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "MegaFlg cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localMegaFlg);
            }

            xmlWriter.writeEndElement();
        }

        if (localPackCdTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "PackCd", xmlWriter);

            if (localPackCd == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "PackCd cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localPackCd);
            }

            xmlWriter.writeEndElement();
        }

        if (localPackNameTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "PackName", xmlWriter);

            if (localPackName == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "PackName cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localPackName);
            }

            xmlWriter.writeEndElement();
        }

        if (localStbRentFlgTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "StbRentFlg", xmlWriter);

            if (localStbRentFlg == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "StbRentFlg cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localStbRentFlg);
            }

            xmlWriter.writeEndElement();
        }

        if (localUserMultiFlgTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "UserMultiFlg", xmlWriter);

            if (localUserMultiFlg == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "UserMultiFlg cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localUserMultiFlg);
            }

            xmlWriter.writeEndElement();
        }

        if (localUserFirstTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "UserFirst", xmlWriter);

            if (localUserFirst == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "UserFirst cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localUserFirst);
            }

            xmlWriter.writeEndElement();
        }

        if (localUserParentFlgTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "UserParentFlg", xmlWriter);

            if (localUserParentFlg == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "UserParentFlg cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localUserParentFlg);
            }

            xmlWriter.writeEndElement();
        }

        if (localUserParentTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "UserParent", xmlWriter);

            if (localUserParent == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "UserParent cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localUserParent);
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
        public static GetSubscriberV3InfoVO parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            GetSubscriberV3InfoVO object = new GetSubscriberV3InfoVO();

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

                        if (!"GetSubscriberV3InfoVO".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (GetSubscriberV3InfoVO) org.tempuri.ExtensionMapper.getTypeObject(nsUri,
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
                            "Fullname").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Fullname" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setFullname(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "ZipCode").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "ZipCode" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setZipCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "Sysflg").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Sysflg" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setSysflg(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "SerialNumber").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "SerialNumber" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setSerialNumber(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "Billdate").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Billdate" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setBilldate(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "BillFlg").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "BillFlg" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setBillFlg(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "CustStatus").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "CustStatus" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setCustStatus(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "MegaFlg").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "MegaFlg" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setMegaFlg(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "PackCd").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "PackCd" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setPackCd(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "PackName").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "PackName" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setPackName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "StbRentFlg").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "StbRentFlg" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setStbRentFlg(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "UserMultiFlg").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "UserMultiFlg" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setUserMultiFlg(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "UserFirst").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "UserFirst" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setUserFirst(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "UserParentFlg").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "UserParentFlg" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setUserParentFlg(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "UserParent").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "UserParent" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setUserParent(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
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
