/**
 * Cust_UserMulti_UpdateInfoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class Cust_UserMulti_UpdateInfoResponse  implements java.io.Serializable {
    private CommonVO cust_UserMulti_UpdateInfoResult;

    public Cust_UserMulti_UpdateInfoResponse() {
    }

    public Cust_UserMulti_UpdateInfoResponse(
           CommonVO cust_UserMulti_UpdateInfoResult) {
           this.cust_UserMulti_UpdateInfoResult = cust_UserMulti_UpdateInfoResult;
    }


    /**
     * Gets the cust_UserMulti_UpdateInfoResult value for this Cust_UserMulti_UpdateInfoResponse.
     * 
     * @return cust_UserMulti_UpdateInfoResult
     */
    public CommonVO getCust_UserMulti_UpdateInfoResult() {
        return cust_UserMulti_UpdateInfoResult;
    }


    /**
     * Sets the cust_UserMulti_UpdateInfoResult value for this Cust_UserMulti_UpdateInfoResponse.
     * 
     * @param cust_UserMulti_UpdateInfoResult
     */
    public void setCust_UserMulti_UpdateInfoResult(CommonVO cust_UserMulti_UpdateInfoResult) {
        this.cust_UserMulti_UpdateInfoResult = cust_UserMulti_UpdateInfoResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Cust_UserMulti_UpdateInfoResponse)) return false;
        Cust_UserMulti_UpdateInfoResponse other = (Cust_UserMulti_UpdateInfoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cust_UserMulti_UpdateInfoResult==null && other.getCust_UserMulti_UpdateInfoResult()==null) || 
             (this.cust_UserMulti_UpdateInfoResult!=null &&
              this.cust_UserMulti_UpdateInfoResult.equals(other.getCust_UserMulti_UpdateInfoResult())));
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
        if (getCust_UserMulti_UpdateInfoResult() != null) {
            _hashCode += getCust_UserMulti_UpdateInfoResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Cust_UserMulti_UpdateInfoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Cust_UserMulti_UpdateInfoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cust_UserMulti_UpdateInfoResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Cust_UserMulti_UpdateInfoResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "CommonVO"));
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
