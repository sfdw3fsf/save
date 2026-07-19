/**
 * LogModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.application;

public class LogModel  implements java.io.Serializable {
    private boolean _new;

    private String accountName;

    private String actionDesc;

    private long actionId;

    private java.util.Calendar beginDate;

    private java.util.Calendar dateTime;

    private String description;

    private java.util.Calendar endDate;

    private long id;

    private long objectId;

    private String other;

    private int pageNumber;

    private String reason;

    private long refObjectId;

    private int result;

    private long userId;

    private String userName;

    public LogModel() {
    }

    public LogModel(
           boolean _new,
           String accountName,
           String actionDesc,
           long actionId,
           java.util.Calendar beginDate,
           java.util.Calendar dateTime,
           String description,
           java.util.Calendar endDate,
           long id,
           long objectId,
           String other,
           int pageNumber,
           String reason,
           long refObjectId,
           int result,
           long userId,
           String userName) {
           this._new = _new;
           this.accountName = accountName;
           this.actionDesc = actionDesc;
           this.actionId = actionId;
           this.beginDate = beginDate;
           this.dateTime = dateTime;
           this.description = description;
           this.endDate = endDate;
           this.id = id;
           this.objectId = objectId;
           this.other = other;
           this.pageNumber = pageNumber;
           this.reason = reason;
           this.refObjectId = refObjectId;
           this.result = result;
           this.userId = userId;
           this.userName = userName;
    }


    /**
     * Gets the _new value for this LogModel.
     * 
     * @return _new
     */
    public boolean is_new() {
        return _new;
    }


    /**
     * Sets the _new value for this LogModel.
     * 
     * @param _new
     */
    public void set_new(boolean _new) {
        this._new = _new;
    }


    /**
     * Gets the accountName value for this LogModel.
     * 
     * @return accountName
     */
    public String getAccountName() {
        return accountName;
    }


    /**
     * Sets the accountName value for this LogModel.
     * 
     * @param accountName
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    /**
     * Gets the actionDesc value for this LogModel.
     * 
     * @return actionDesc
     */
    public String getActionDesc() {
        return actionDesc;
    }


    /**
     * Sets the actionDesc value for this LogModel.
     * 
     * @param actionDesc
     */
    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }


    /**
     * Gets the actionId value for this LogModel.
     * 
     * @return actionId
     */
    public long getActionId() {
        return actionId;
    }


    /**
     * Sets the actionId value for this LogModel.
     * 
     * @param actionId
     */
    public void setActionId(long actionId) {
        this.actionId = actionId;
    }


    /**
     * Gets the beginDate value for this LogModel.
     * 
     * @return beginDate
     */
    public java.util.Calendar getBeginDate() {
        return beginDate;
    }


    /**
     * Sets the beginDate value for this LogModel.
     * 
     * @param beginDate
     */
    public void setBeginDate(java.util.Calendar beginDate) {
        this.beginDate = beginDate;
    }


    /**
     * Gets the dateTime value for this LogModel.
     * 
     * @return dateTime
     */
    public java.util.Calendar getDateTime() {
        return dateTime;
    }


    /**
     * Sets the dateTime value for this LogModel.
     * 
     * @param dateTime
     */
    public void setDateTime(java.util.Calendar dateTime) {
        this.dateTime = dateTime;
    }


    /**
     * Gets the description value for this LogModel.
     * 
     * @return description
     */
    public String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this LogModel.
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Gets the endDate value for this LogModel.
     * 
     * @return endDate
     */
    public java.util.Calendar getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value for this LogModel.
     * 
     * @param endDate
     */
    public void setEndDate(java.util.Calendar endDate) {
        this.endDate = endDate;
    }


    /**
     * Gets the id value for this LogModel.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this LogModel.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the objectId value for this LogModel.
     * 
     * @return objectId
     */
    public long getObjectId() {
        return objectId;
    }


    /**
     * Sets the objectId value for this LogModel.
     * 
     * @param objectId
     */
    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }


    /**
     * Gets the other value for this LogModel.
     * 
     * @return other
     */
    public String getOther() {
        return other;
    }


    /**
     * Sets the other value for this LogModel.
     * 
     * @param other
     */
    public void setOther(String other) {
        this.other = other;
    }


    /**
     * Gets the pageNumber value for this LogModel.
     * 
     * @return pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }


    /**
     * Sets the pageNumber value for this LogModel.
     * 
     * @param pageNumber
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }


    /**
     * Gets the reason value for this LogModel.
     * 
     * @return reason
     */
    public String getReason() {
        return reason;
    }


    /**
     * Sets the reason value for this LogModel.
     * 
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }


    /**
     * Gets the refObjectId value for this LogModel.
     * 
     * @return refObjectId
     */
    public long getRefObjectId() {
        return refObjectId;
    }


    /**
     * Sets the refObjectId value for this LogModel.
     * 
     * @param refObjectId
     */
    public void setRefObjectId(long refObjectId) {
        this.refObjectId = refObjectId;
    }


    /**
     * Gets the result value for this LogModel.
     * 
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this LogModel.
     * 
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the userId value for this LogModel.
     * 
     * @return userId
     */
    public long getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this LogModel.
     * 
     * @param userId
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }


    /**
     * Gets the userName value for this LogModel.
     * 
     * @return userName
     */
    public String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this LogModel.
     * 
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof LogModel)) return false;
        LogModel other = (LogModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this._new == other.is_new() &&
            ((this.accountName==null && other.getAccountName()==null) || 
             (this.accountName!=null &&
              this.accountName.equals(other.getAccountName()))) &&
            ((this.actionDesc==null && other.getActionDesc()==null) || 
             (this.actionDesc!=null &&
              this.actionDesc.equals(other.getActionDesc()))) &&
            this.actionId == other.getActionId() &&
            ((this.beginDate==null && other.getBeginDate()==null) || 
             (this.beginDate!=null &&
              this.beginDate.equals(other.getBeginDate()))) &&
            ((this.dateTime==null && other.getDateTime()==null) || 
             (this.dateTime!=null &&
              this.dateTime.equals(other.getDateTime()))) &&
            ((this.description==null && other.getDescription()==null) || 
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.endDate==null && other.getEndDate()==null) || 
             (this.endDate!=null &&
              this.endDate.equals(other.getEndDate()))) &&
            this.id == other.getId() &&
            this.objectId == other.getObjectId() &&
            ((this.other==null && other.getOther()==null) || 
             (this.other!=null &&
              this.other.equals(other.getOther()))) &&
            this.pageNumber == other.getPageNumber() &&
            ((this.reason==null && other.getReason()==null) || 
             (this.reason!=null &&
              this.reason.equals(other.getReason()))) &&
            this.refObjectId == other.getRefObjectId() &&
            this.result == other.getResult() &&
            this.userId == other.getUserId() &&
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += (is_new() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getAccountName() != null) {
            _hashCode += getAccountName().hashCode();
        }
        if (getActionDesc() != null) {
            _hashCode += getActionDesc().hashCode();
        }
        _hashCode += new Long(getActionId()).hashCode();
        if (getBeginDate() != null) {
            _hashCode += getBeginDate().hashCode();
        }
        if (getDateTime() != null) {
            _hashCode += getDateTime().hashCode();
        }
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        _hashCode += new Long(getId()).hashCode();
        _hashCode += new Long(getObjectId()).hashCode();
        if (getOther() != null) {
            _hashCode += getOther().hashCode();
        }
        _hashCode += getPageNumber();
        if (getReason() != null) {
            _hashCode += getReason().hashCode();
        }
        _hashCode += new Long(getRefObjectId()).hashCode();
        _hashCode += getResult();
        _hashCode += new Long(getUserId()).hashCode();
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LogModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:log.model.hyframe.hyperlogy", "LogModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("_new");
        elemField.setXmlName(new javax.xml.namespace.QName("", "_new"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accountName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "actionDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "actionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beginDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "beginDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dateTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "endDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("objectId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "objectId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("other");
        elemField.setXmlName(new javax.xml.namespace.QName("", "other"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pageNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reason");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refObjectId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "refObjectId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("", "result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
