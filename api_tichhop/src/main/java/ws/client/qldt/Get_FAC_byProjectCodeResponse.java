/**
 * Get_FAC_byProjectCodeResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.qldt;

public class Get_FAC_byProjectCodeResponse  implements java.io.Serializable {
    private String get_FAC_byProjectCodeResult;

    public Get_FAC_byProjectCodeResponse() {
    }

    public Get_FAC_byProjectCodeResponse(
           String get_FAC_byProjectCodeResult) {
           this.get_FAC_byProjectCodeResult = get_FAC_byProjectCodeResult;
    }


    /**
     * Gets the get_FAC_byProjectCodeResult value for this Get_FAC_byProjectCodeResponse.
     * 
     * @return get_FAC_byProjectCodeResult
     */
    public String getGet_FAC_byProjectCodeResult() {
        return get_FAC_byProjectCodeResult;
    }


    /**
     * Sets the get_FAC_byProjectCodeResult value for this Get_FAC_byProjectCodeResponse.
     * 
     * @param get_FAC_byProjectCodeResult
     */
    public void setGet_FAC_byProjectCodeResult(String get_FAC_byProjectCodeResult) {
        this.get_FAC_byProjectCodeResult = get_FAC_byProjectCodeResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Get_FAC_byProjectCodeResponse)) return false;
        Get_FAC_byProjectCodeResponse other = (Get_FAC_byProjectCodeResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.get_FAC_byProjectCodeResult==null && other.getGet_FAC_byProjectCodeResult()==null) || 
             (this.get_FAC_byProjectCodeResult!=null &&
              this.get_FAC_byProjectCodeResult.equals(other.getGet_FAC_byProjectCodeResult())));
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
        if (getGet_FAC_byProjectCodeResult() != null) {
            _hashCode += getGet_FAC_byProjectCodeResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Get_FAC_byProjectCodeResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Get_FAC_byProjectCodeResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("get_FAC_byProjectCodeResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Get_FAC_byProjectCodeResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
