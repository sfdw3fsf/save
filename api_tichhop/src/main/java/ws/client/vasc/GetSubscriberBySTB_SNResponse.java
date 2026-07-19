/**
 * GetSubscriberBySTB_SNResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetSubscriberBySTB_SNResponse  implements java.io.Serializable {
    private String[] getSubscriberBySTB_SNResult;

    public GetSubscriberBySTB_SNResponse() {
    }

    public GetSubscriberBySTB_SNResponse(
           String[] getSubscriberBySTB_SNResult) {
           this.getSubscriberBySTB_SNResult = getSubscriberBySTB_SNResult;
    }


    /**
     * Gets the getSubscriberBySTB_SNResult value for this GetSubscriberBySTB_SNResponse.
     * 
     * @return getSubscriberBySTB_SNResult
     */
    public String[] getGetSubscriberBySTB_SNResult() {
        return getSubscriberBySTB_SNResult;
    }


    /**
     * Sets the getSubscriberBySTB_SNResult value for this GetSubscriberBySTB_SNResponse.
     * 
     * @param getSubscriberBySTB_SNResult
     */
    public void setGetSubscriberBySTB_SNResult(String[] getSubscriberBySTB_SNResult) {
        this.getSubscriberBySTB_SNResult = getSubscriberBySTB_SNResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetSubscriberBySTB_SNResponse)) return false;
        GetSubscriberBySTB_SNResponse other = (GetSubscriberBySTB_SNResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getSubscriberBySTB_SNResult==null && other.getGetSubscriberBySTB_SNResult()==null) || 
             (this.getSubscriberBySTB_SNResult!=null &&
              java.util.Arrays.equals(this.getSubscriberBySTB_SNResult, other.getGetSubscriberBySTB_SNResult())));
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
        if (getGetSubscriberBySTB_SNResult() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGetSubscriberBySTB_SNResult());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getGetSubscriberBySTB_SNResult(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetSubscriberBySTB_SNResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetSubscriberBySTB_SNResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getSubscriberBySTB_SNResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetSubscriberBySTB_SNResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://tempuri.org/", "string"));
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
