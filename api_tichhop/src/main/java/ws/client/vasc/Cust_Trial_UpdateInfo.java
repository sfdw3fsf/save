/**
 * Cust_Trial_UpdateInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class Cust_Trial_UpdateInfo  implements java.io.Serializable {
    private CustTrialDTO custDTO;

    public Cust_Trial_UpdateInfo() {
    }

    public Cust_Trial_UpdateInfo(
           CustTrialDTO custDTO) {
           this.custDTO = custDTO;
    }


    /**
     * Gets the custDTO value for this Cust_Trial_UpdateInfo.
     * 
     * @return custDTO
     */
    public CustTrialDTO getCustDTO() {
        return custDTO;
    }


    /**
     * Sets the custDTO value for this Cust_Trial_UpdateInfo.
     * 
     * @param custDTO
     */
    public void setCustDTO(CustTrialDTO custDTO) {
        this.custDTO = custDTO;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Cust_Trial_UpdateInfo)) return false;
        Cust_Trial_UpdateInfo other = (Cust_Trial_UpdateInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.custDTO==null && other.getCustDTO()==null) || 
             (this.custDTO!=null &&
              this.custDTO.equals(other.getCustDTO())));
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
        if (getCustDTO() != null) {
            _hashCode += getCustDTO().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Cust_Trial_UpdateInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Cust_Trial_UpdateInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custDTO");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "custDTO"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "CustTrialDTO"));
        elemField.setMinOccurs(0);
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
