/**
 * ReserveModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.common;

public class ReserveModel  implements java.io.Serializable {
    private java.util.Calendar beginDate;

    private String context;

    private java.util.Calendar endDate;

    private long id;

    private int numberPage;

    private java.util.Calendar reservedTime;

    private String resourceId;

    private long userId;

    public ReserveModel() {
    }

    public ReserveModel(
           java.util.Calendar beginDate,
           String context,
           java.util.Calendar endDate,
           long id,
           int numberPage,
           java.util.Calendar reservedTime,
           String resourceId,
           long userId) {
           this.beginDate = beginDate;
           this.context = context;
           this.endDate = endDate;
           this.id = id;
           this.numberPage = numberPage;
           this.reservedTime = reservedTime;
           this.resourceId = resourceId;
           this.userId = userId;
    }


    /**
     * Gets the beginDate value for this ReserveModel.
     * 
     * @return beginDate
     */
    public java.util.Calendar getBeginDate() {
        return beginDate;
    }


    /**
     * Sets the beginDate value for this ReserveModel.
     * 
     * @param beginDate
     */
    public void setBeginDate(java.util.Calendar beginDate) {
        this.beginDate = beginDate;
    }


    /**
     * Gets the context value for this ReserveModel.
     * 
     * @return context
     */
    public String getContext() {
        return context;
    }


    /**
     * Sets the context value for this ReserveModel.
     * 
     * @param context
     */
    public void setContext(String context) {
        this.context = context;
    }


    /**
     * Gets the endDate value for this ReserveModel.
     * 
     * @return endDate
     */
    public java.util.Calendar getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value for this ReserveModel.
     * 
     * @param endDate
     */
    public void setEndDate(java.util.Calendar endDate) {
        this.endDate = endDate;
    }


    /**
     * Gets the id value for this ReserveModel.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this ReserveModel.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the numberPage value for this ReserveModel.
     * 
     * @return numberPage
     */
    public int getNumberPage() {
        return numberPage;
    }


    /**
     * Sets the numberPage value for this ReserveModel.
     * 
     * @param numberPage
     */
    public void setNumberPage(int numberPage) {
        this.numberPage = numberPage;
    }


    /**
     * Gets the reservedTime value for this ReserveModel.
     * 
     * @return reservedTime
     */
    public java.util.Calendar getReservedTime() {
        return reservedTime;
    }


    /**
     * Sets the reservedTime value for this ReserveModel.
     * 
     * @param reservedTime
     */
    public void setReservedTime(java.util.Calendar reservedTime) {
        this.reservedTime = reservedTime;
    }


    /**
     * Gets the resourceId value for this ReserveModel.
     * 
     * @return resourceId
     */
    public String getResourceId() {
        return resourceId;
    }


    /**
     * Sets the resourceId value for this ReserveModel.
     * 
     * @param resourceId
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }


    /**
     * Gets the userId value for this ReserveModel.
     * 
     * @return userId
     */
    public long getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this ReserveModel.
     * 
     * @param userId
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ReserveModel)) return false;
        ReserveModel other = (ReserveModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.beginDate==null && other.getBeginDate()==null) || 
             (this.beginDate!=null &&
              this.beginDate.equals(other.getBeginDate()))) &&
            ((this.context==null && other.getContext()==null) || 
             (this.context!=null &&
              this.context.equals(other.getContext()))) &&
            ((this.endDate==null && other.getEndDate()==null) || 
             (this.endDate!=null &&
              this.endDate.equals(other.getEndDate()))) &&
            this.id == other.getId() &&
            this.numberPage == other.getNumberPage() &&
            ((this.reservedTime==null && other.getReservedTime()==null) || 
             (this.reservedTime!=null &&
              this.reservedTime.equals(other.getReservedTime()))) &&
            ((this.resourceId==null && other.getResourceId()==null) || 
             (this.resourceId!=null &&
              this.resourceId.equals(other.getResourceId()))) &&
            this.userId == other.getUserId();
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
        if (getBeginDate() != null) {
            _hashCode += getBeginDate().hashCode();
        }
        if (getContext() != null) {
            _hashCode += getContext().hashCode();
        }
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        _hashCode += new Long(getId()).hashCode();
        _hashCode += getNumberPage();
        if (getReservedTime() != null) {
            _hashCode += getReservedTime().hashCode();
        }
        if (getResourceId() != null) {
            _hashCode += getResourceId().hashCode();
        }
        _hashCode += new Long(getUserId()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReserveModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:reserve.model.hyframe.hyperlogy", "ReserveModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beginDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "beginDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("context");
        elemField.setXmlName(new javax.xml.namespace.QName("", "context"));
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
        elemField.setFieldName("numberPage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numberPage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reservedTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "reservedTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resourceId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "resourceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
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
