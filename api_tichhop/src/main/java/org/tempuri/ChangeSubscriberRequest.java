/**
 * ChangeSubscriberRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.9  Built on : Nov 16, 2018 (12:06:07 GMT)
 */
package org.tempuri;


/**
 *  ChangeSubscriberRequest bean class
 */
@SuppressWarnings({"unchecked",
    "unused"
})
public class ChangeSubscriberRequest implements org.apache.axis2.databinding.ADBBean {
    /* This type was generated from the piece of schema that had
       name = ChangeSubscriberRequest
       Namespace URI = http://tempuri.org/
       Namespace Prefix = ns1
     */

    /**
     * field for ContractSignDate
     */
    protected java.lang.String localContractSignDate;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localContractSignDateTracker = false;

    /**
     * field for MIService
     */
    protected java.lang.String localMIService;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localMIServiceTracker = false;

    /**
     * field for FeePrepaid
     */
    protected java.lang.String localFeePrepaid;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localFeePrepaidTracker = false;

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
     * field for CMT
     */
    protected java.lang.String localCMT;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localCMTTracker = false;

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
     * field for FaxNo
     */
    protected java.lang.String localFaxNo;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localFaxNoTracker = false;

    /**
     * field for ContractCode
     */
    protected java.lang.String localContractCode;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localContractCodeTracker = false;

    /**
     * field for IPTVAccount
     */
    protected java.lang.String localIPTVAccount;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localIPTVAccountTracker = false;

    /**
     * field for PortDSL
     */
    protected java.lang.String localPortDSL;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localPortDSLTracker = false;

    /**
     * field for DeviceName
     */
    protected java.lang.String localDeviceName;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localDeviceNameTracker = false;

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
     * field for PromoCD
     */
    protected java.lang.String localPromoCD;

    /*  This tracker boolean wil be used to detect whether the user called the set method
     *   for this attribute. It will be used to determine whether to include this field
     *   in the serialized XML
     */
    protected boolean localPromoCDTracker = false;

    public boolean isContractSignDateSpecified() {
        return localContractSignDateTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getContractSignDate() {
        return localContractSignDate;
    }

    /**
     * Auto generated setter method
     * @param param ContractSignDate
     */
    public void setContractSignDate(java.lang.String param) {
        localContractSignDateTracker = param != null;

        this.localContractSignDate = param;
    }

    public boolean isMIServiceSpecified() {
        return localMIServiceTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getMIService() {
        return localMIService;
    }

    /**
     * Auto generated setter method
     * @param param MIService
     */
    public void setMIService(java.lang.String param) {
        localMIServiceTracker = param != null;

        this.localMIService = param;
    }

    public boolean isFeePrepaidSpecified() {
        return localFeePrepaidTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getFeePrepaid() {
        return localFeePrepaid;
    }

    /**
     * Auto generated setter method
     * @param param FeePrepaid
     */
    public void setFeePrepaid(java.lang.String param) {
        localFeePrepaidTracker = param != null;

        this.localFeePrepaid = param;
    }

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

    public boolean isContractCodeSpecified() {
        return localContractCodeTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getContractCode() {
        return localContractCode;
    }

    /**
     * Auto generated setter method
     * @param param ContractCode
     */
    public void setContractCode(java.lang.String param) {
        localContractCodeTracker = param != null;

        this.localContractCode = param;
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

    public boolean isPortDSLSpecified() {
        return localPortDSLTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getPortDSL() {
        return localPortDSL;
    }

    /**
     * Auto generated setter method
     * @param param PortDSL
     */
    public void setPortDSL(java.lang.String param) {
        localPortDSLTracker = param != null;

        this.localPortDSL = param;
    }

    public boolean isDeviceNameSpecified() {
        return localDeviceNameTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getDeviceName() {
        return localDeviceName;
    }

    /**
     * Auto generated setter method
     * @param param DeviceName
     */
    public void setDeviceName(java.lang.String param) {
        localDeviceNameTracker = param != null;

        this.localDeviceName = param;
    }

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

    public boolean isPromoCDSpecified() {
        return localPromoCDTracker;
    }

    /**
     * Auto generated getter method
     * @return java.lang.String
     */
    public java.lang.String getPromoCD() {
        return localPromoCD;
    }

    /**
     * Auto generated setter method
     * @param param PromoCD
     */
    public void setPromoCD(java.lang.String param) {
        localPromoCDTracker = param != null;

        this.localPromoCD = param;
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
                    namespacePrefix + ":ChangeSubscriberRequest", xmlWriter);
            } else {
                writeAttribute("xsi",
                    "http://www.w3.org/2001/XMLSchema-instance", "type",
                    "ChangeSubscriberRequest", xmlWriter);
            }
        }

        if (localContractSignDateTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "ContractSignDate", xmlWriter);

            if (localContractSignDate == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "ContractSignDate cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localContractSignDate);
            }

            xmlWriter.writeEndElement();
        }

        if (localMIServiceTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "MIService", xmlWriter);

            if (localMIService == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "MIService cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localMIService);
            }

            xmlWriter.writeEndElement();
        }

        if (localFeePrepaidTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "FeePrepaid", xmlWriter);

            if (localFeePrepaid == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "FeePrepaid cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localFeePrepaid);
            }

            xmlWriter.writeEndElement();
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

        if (localContractCodeTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "ContractCode", xmlWriter);

            if (localContractCode == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "ContractCode cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localContractCode);
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

        if (localPortDSLTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "PortDSL", xmlWriter);

            if (localPortDSL == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "PortDSL cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localPortDSL);
            }

            xmlWriter.writeEndElement();
        }

        if (localDeviceNameTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "DeviceName", xmlWriter);

            if (localDeviceName == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "DeviceName cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localDeviceName);
            }

            xmlWriter.writeEndElement();
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

        if (localPromoCDTracker) {
            namespace = "http://tempuri.org/";
            writeStartElement(null, namespace, "PromoCD", xmlWriter);

            if (localPromoCD == null) {
                // write the nil attribute
                throw new org.apache.axis2.databinding.ADBException(
                    "PromoCD cannot be null!!");
            } else {
                xmlWriter.writeCharacters(localPromoCD);
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
        public static ChangeSubscriberRequest parse(
            javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception {
            ChangeSubscriberRequest object = new ChangeSubscriberRequest();

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

                        if (!"ChangeSubscriberRequest".equals(type)) {
                            //find namespace for the prefix
                            java.lang.String nsUri = reader.getNamespaceContext()
                                                           .getNamespaceURI(nsPrefix);

                            return (ChangeSubscriberRequest) org.tempuri.ExtensionMapper.getTypeObject(nsUri,
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
                            "ContractSignDate").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "ContractSignDate" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setContractSignDate(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "MIService").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "MIService" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setMIService(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "FeePrepaid").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "FeePrepaid" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setFeePrepaid(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

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
                            "ContractCode").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "ContractCode" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setContractCode(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
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

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "PortDSL").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "PortDSL" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setPortDSL(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                if (reader.isStartElement() &&
                        new javax.xml.namespace.QName("http://tempuri.org/",
                            "DeviceName").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "DeviceName" +
                            "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setDeviceName(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            content));

                    reader.next();
                } // End of if for expected property start element

                else {
                }

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
                            "PromoCD").equals(reader.getName())) {
                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                            "nil");

                    if ("true".equals(nillableValue) ||
                            "1".equals(nillableValue)) {
                        throw new org.apache.axis2.databinding.ADBException(
                            "The element: " + "PromoCD" + "  cannot be null");
                    }

                    java.lang.String content = reader.getElementText();

                    object.setPromoCD(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
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
