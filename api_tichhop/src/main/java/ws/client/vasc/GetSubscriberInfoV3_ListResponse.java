/**
 * GetSubscriberInfoV3_ListResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetSubscriberInfoV3_ListResponse  implements java.io.Serializable {
    private GetSubscriberV3InfoVO[] getSubscriberInfoV3_ListResult;

    public GetSubscriberInfoV3_ListResponse() {
    }

    public GetSubscriberInfoV3_ListResponse(
           GetSubscriberV3InfoVO[] getSubscriberInfoV3_ListResult) {
           this.getSubscriberInfoV3_ListResult = getSubscriberInfoV3_ListResult;
    }


    /**
     * Gets the getSubscriberInfoV3_ListResult value for this GetSubscriberInfoV3_ListResponse.
     * 
     * @return getSubscriberInfoV3_ListResult
     */
    public GetSubscriberV3InfoVO[] getGetSubscriberInfoV3_ListResult() {
        return getSubscriberInfoV3_ListResult;
    }


    /**
     * Sets the getSubscriberInfoV3_ListResult value for this GetSubscriberInfoV3_ListResponse.
     * 
     * @param getSubscriberInfoV3_ListResult
     */
    public void setGetSubscriberInfoV3_ListResult(GetSubscriberV3InfoVO[] getSubscriberInfoV3_ListResult) {
        this.getSubscriberInfoV3_ListResult = getSubscriberInfoV3_ListResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetSubscriberInfoV3_ListResponse)) return false;
        GetSubscriberInfoV3_ListResponse other = (GetSubscriberInfoV3_ListResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getSubscriberInfoV3_ListResult==null && other.getGetSubscriberInfoV3_ListResult()==null) || 
             (this.getSubscriberInfoV3_ListResult!=null &&
              java.util.Arrays.equals(this.getSubscriberInfoV3_ListResult, other.getGetSubscriberInfoV3_ListResult())));
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
        if (getGetSubscriberInfoV3_ListResult() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGetSubscriberInfoV3_ListResult());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getGetSubscriberInfoV3_ListResult(), i);
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
        new org.apache.axis.description.TypeDesc(GetSubscriberInfoV3_ListResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetSubscriberInfoV3_ListResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getSubscriberInfoV3_ListResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetSubscriberInfoV3_ListResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "GetSubscriberV3InfoVO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://tempuri.org/", "GetSubscriberV3InfoVO"));
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
