/**
 * Cust_Trial_UpdateInfoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class Cust_Trial_UpdateInfoResponse  implements java.io.Serializable {
    private CommonVO cust_Trial_UpdateInfoResult;

    public Cust_Trial_UpdateInfoResponse() {
    }

    public Cust_Trial_UpdateInfoResponse(
           CommonVO cust_Trial_UpdateInfoResult) {
           this.cust_Trial_UpdateInfoResult = cust_Trial_UpdateInfoResult;
    }


    /**
     * Gets the cust_Trial_UpdateInfoResult value for this Cust_Trial_UpdateInfoResponse.
     * 
     * @return cust_Trial_UpdateInfoResult
     */
    public CommonVO getCust_Trial_UpdateInfoResult() {
        return cust_Trial_UpdateInfoResult;
    }


    /**
     * Sets the cust_Trial_UpdateInfoResult value for this Cust_Trial_UpdateInfoResponse.
     * 
     * @param cust_Trial_UpdateInfoResult
     */
    public void setCust_Trial_UpdateInfoResult(CommonVO cust_Trial_UpdateInfoResult) {
        this.cust_Trial_UpdateInfoResult = cust_Trial_UpdateInfoResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Cust_Trial_UpdateInfoResponse)) return false;
        Cust_Trial_UpdateInfoResponse other = (Cust_Trial_UpdateInfoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.cust_Trial_UpdateInfoResult==null && other.getCust_Trial_UpdateInfoResult()==null) || 
             (this.cust_Trial_UpdateInfoResult!=null &&
              this.cust_Trial_UpdateInfoResult.equals(other.getCust_Trial_UpdateInfoResult())));
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
        if (getCust_Trial_UpdateInfoResult() != null) {
            _hashCode += getCust_Trial_UpdateInfoResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Cust_Trial_UpdateInfoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Cust_Trial_UpdateInfoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cust_Trial_UpdateInfoResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Cust_Trial_UpdateInfoResult"));
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
