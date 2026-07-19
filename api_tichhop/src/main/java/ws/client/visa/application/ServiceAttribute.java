/**
 * ServiceAttribute.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.application;

public class ServiceAttribute  implements java.io.Serializable {
    private long accountServiceId;

    private long historyId;

    private long id;

    private long serviceId;

    private String type;

    private String value;

    public ServiceAttribute() {
    }

    public ServiceAttribute(
           long accountServiceId,
           long historyId,
           long id,
           long serviceId,
           String type,
           String value) {
           this.accountServiceId = accountServiceId;
           this.historyId = historyId;
           this.id = id;
           this.serviceId = serviceId;
           this.type = type;
           this.value = value;
    }


    /**
     * Gets the accountServiceId value for this ServiceAttribute.
     * 
     * @return accountServiceId
     */
    public long getAccountServiceId() {
        return accountServiceId;
    }


    /**
     * Sets the accountServiceId value for this ServiceAttribute.
     * 
     * @param accountServiceId
     */
    public void setAccountServiceId(long accountServiceId) {
        this.accountServiceId = accountServiceId;
    }


    /**
     * Gets the historyId value for this ServiceAttribute.
     * 
     * @return historyId
     */
    public long getHistoryId() {
        return historyId;
    }


    /**
     * Sets the historyId value for this ServiceAttribute.
     * 
     * @param historyId
     */
    public void setHistoryId(long historyId) {
        this.historyId = historyId;
    }


    /**
     * Gets the id value for this ServiceAttribute.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }


    /**
     * Sets the id value for this ServiceAttribute.
     * 
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }


    /**
     * Gets the serviceId value for this ServiceAttribute.
     * 
     * @return serviceId
     */
    public long getServiceId() {
        return serviceId;
    }


    /**
     * Sets the serviceId value for this ServiceAttribute.
     * 
     * @param serviceId
     */
    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }


    /**
     * Gets the type value for this ServiceAttribute.
     * 
     * @return type
     */
    public String getType() {
        return type;
    }


    /**
     * Sets the type value for this ServiceAttribute.
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * Gets the value value for this ServiceAttribute.
     * 
     * @return value
     */
    public String getValue() {
        return value;
    }


    /**
     * Sets the value value for this ServiceAttribute.
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ServiceAttribute)) return false;
        ServiceAttribute other = (ServiceAttribute) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.accountServiceId == other.getAccountServiceId() &&
            this.historyId == other.getHistoryId() &&
            this.id == other.getId() &&
            this.serviceId == other.getServiceId() &&
            ((this.type==null && other.getType()==null) || 
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            ((this.value==null && other.getValue()==null) || 
             (this.value!=null &&
              this.value.equals(other.getValue())));
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
        _hashCode += new Long(getAccountServiceId()).hashCode();
        _hashCode += new Long(getHistoryId()).hashCode();
        _hashCode += new Long(getId()).hashCode();
        _hashCode += new Long(getServiceId()).hashCode();
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        if (getValue() != null) {
            _hashCode += getValue().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ServiceAttribute.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:prov.model.hyframe.hyperlogy", "ServiceAttribute"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountServiceId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accountServiceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("historyId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "historyId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "serviceId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("value");
        elemField.setXmlName(new javax.xml.namespace.QName("", "value"));
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
