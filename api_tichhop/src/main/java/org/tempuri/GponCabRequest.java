/**
 * GponCabRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:06:07 GMT)
 */
package org.tempuri;


/**
 *  GponCabRequest bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class GponCabRequest implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = GponCabRequest
       Namespace URI = http://tempuri.org/
       Namespace Prefix = ns1
     */

    /**
     * field for BillAccount
     */
    protected java.lang.String localBillAccount;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localBillAccountTracker = false;

    /**
     * field for ZipCD
     */
    protected java.lang.String localZipCD;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localZipCDTracker = false;

    /**
     * field for AreaCD
     */
    protected java.lang.String localAreaCD;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localAreaCDTracker = false;

    /**
     * field for FullName
     */
    protected java.lang.String localFullName;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localFullNameTracker = false;

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
     * field for CMT
     */
    protected java.lang.String localCMT;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localCMTTracker = false;

    /**
     * field for DistrictCD
     */
    protected java.lang.String localDistrictCD;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localDistrictCDTracker = false;

    /**
     * field for TownCD
     */
    protected java.lang.String localTownCD;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localTownCDTracker = false;

    /**
     * field for Address
     */
    protected java.lang.String localAddress;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localAddressTracker = false;

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
     * field for Email
     */
    protected java.lang.String localEmail;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localEmailTracker = false;

    /**
     * field for Note
     */
    protected java.lang.String localNote;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localNoteTracker = false;

    /**
     * field for CabUsername
     */
    protected java.lang.String localCabUsername;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localCabUsernameTracker = false;

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

    public boolean isBillAccountSpecified() {
        return localBillAccountTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getBillAccount() {
        return localBillAccount;
    }

    /**
     * Auto generated setter method
     * @param param BillAccount
     */
    public void setBillAccount(java.lang.String param) {
        localBillAccountTracker = param != null;

        this.localBillAccount = param;
    }

    public boolean isZipCDSpecified() {
        return localZipCDTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getZipCD() {
        return localZipCD;
    }

    /**
     * Auto generated setter method
     * @param param ZipCD
     */
    public void setZipCD(java.lang.String param) {
        localZipCDTracker = param != null;

        this.localZipCD = param;
    }

    public boolean isAreaCDSpecified() {
        return localAreaCDTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getAreaCD() {
        return localAreaCD;
    }

    /**
     * Auto generated setter method
     * @param param AreaCD
     */
    public void setAreaCD(java.lang.String param) {
        localAreaCDTracker = param != null;

        this.localAreaCD = param;
    }

    public boolean isFullNameSpecified() {
        return localFullNameTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getFullName() {
        return localFullName;
    }

    /**
     * Auto generated setter method
     * @param param FullName
     */
    public void setFullName(java.lang.String param) {
        localFullNameTracker = param != null;

        this.localFullName = param;
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

    public boolean isCMTSpecified() {
        return localCMTTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getCMT() {
        return localCMT;
    }

    /**
     * Auto generated setter method
     * @param param CMT
     */
    public void setCMT(java.lang.String param) {
        localCMTTracker = param != null;

        this.localCMT = param;
    }

    public boolean isDistrictCDSpecified() {
        return localDistrictCDTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getDistrictCD() {
        return localDistrictCD;
    }

    /**
     * Auto generated setter method
     * @param param DistrictCD
     */
    public void setDistrictCD(java.lang.String param) {
        localDistrictCDTracker = param != null;

        this.localDistrictCD = param;
    }

    public boolean isTownCDSpecified() {
        return localTownCDTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getTownCD() {
        return localTownCD;
    }

    /**
     * Auto generated setter method
     * @param param TownCD
     */
    public void setTownCD(java.lang.String param) {
        localTownCDTracker = param != null;

        this.localTownCD = param;
    }

    public boolean isAddressSpecified() {
        return localAddressTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getAddress() {
        return localAddress;
    }

    /**
     * Auto generated setter method
     * @param param Address
     */
    public void setAddress(java.lang.String param) {
        localAddressTracker = param != null;

        this.localAddress = param;
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

    public boolean isNoteSpecified() {
        return localNoteTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getNote() {
        return localNote;
    }

    /**
     * Auto generated setter method
     * @param param Note
     */
    public void setNote(java.lang.String param) {
        localNoteTracker = param != null;

        this.localNote = param;
    }

    public boolean isCabUsernameSpecified() {
        return localCabUsernameTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getCabUsername() {
        return localCabUsername;
    }

    /**
     * Auto generated setter method
     * @param param CabUsername
     */
    public void setCabUsername(java.lang.String param) {
        localCabUsernameTracker = param != null;

        this.localCabUsername = param;
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
                    namespacePrefix + ":GponCabRequest", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "GponCabRequest", xmlWriter);
            }
        }

        if (localBillAccountTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "BillAccount", xmlWriter);

            if (localBillAccount == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "BillAccount cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localBillAccount);
            }

            xmlWriter.writeEndElement();
        }

        if (localZipCDTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "ZipCD", xmlWriter);

            if (localZipCD == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "ZipCD cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localZipCD);
            }

            xmlWriter.writeEndElement();
        }

        if (localAreaCDTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "AreaCD", xmlWriter);

            if (localAreaCD == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "AreaCD cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localAreaCD);
            }

            xmlWriter.writeEndElement();
        }

        if (localFullNameTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "FullName", xmlWriter);

            if (localFullName == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "FullName cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localFullName);
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

        if (localCMTTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "CMT", xmlWriter);

            if (localCMT == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "CMT cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localCMT);
            }

            xmlWriter.writeEndElement();
        }

        if (localDistrictCDTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "DistrictCD", xmlWriter);

            if (localDistrictCD == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "DistrictCD cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localDistrictCD);
            }

            xmlWriter.writeEndElement();
        }

        if (localTownCDTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "TownCD", xmlWriter);

            if (localTownCD == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "TownCD cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localTownCD);
            }

            xmlWriter.writeEndElement();
        }

        if (localAddressTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "Address", xmlWriter);

            if (localAddress == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Address cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localAddress);
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

        if (localNoteTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "Note", xmlWriter);

            if (localNote == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "Note cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localNote);
            }

            xmlWriter.writeEndElement();
        }

        if (localCabUsernameTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "CabUsername", xmlWriter);

            if (localCabUsername == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "CabUsername cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localCabUsername);
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
        public static GponCabRequest parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            GponCabRequest object = new GponCabRequest();

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

                        if (!"GponCabRequest".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (GponCabRequest) org.tempuri.ExtensionMapper.getTypeObject(nsUri,
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
                            "BillAccount").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "BillAccount" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setBillAccount(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "ZipCD").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "ZipCD" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setZipCD(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "AreaCD").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "AreaCD" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setAreaCD(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "FullName").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "FullName" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setFullName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
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
                            "CMT").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "CMT" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setCMT(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "DistrictCD").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "DistrictCD" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setDistrictCD(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "TownCD").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "TownCD" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setTownCD(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "Address").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Address" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setAddress(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
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
                            "Note").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "Note" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setNote(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "CabUsername").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "CabUsername" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setCabUsername(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
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
