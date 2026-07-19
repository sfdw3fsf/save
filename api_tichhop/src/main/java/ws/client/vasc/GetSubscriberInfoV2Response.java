/**
 * GetSubscriberInfoV2Response.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetSubscriberInfoV2Response  implements java.io.Serializable {
    private GetSubscriberVO getSubscriberInfoV2Result;

    public GetSubscriberInfoV2Response() {
    }

    public GetSubscriberInfoV2Response(
           GetSubscriberVO getSubscriberInfoV2Result) {
           this.getSubscriberInfoV2Result = getSubscriberInfoV2Result;
    }


    /**
     * Gets the getSubscriberInfoV2Result value for this GetSubscriberInfoV2Response.
     * 
     * @return getSubscriberInfoV2Result
     */
    public GetSubscriberVO getGetSubscriberInfoV2Result() {
        return getSubscriberInfoV2Result;
    }


    /**
     * Sets the getSubscriberInfoV2Result value for this GetSubscriberInfoV2Response.
     * 
     * @param getSubscriberInfoV2Result
     */
    public void setGetSubscriberInfoV2Result(GetSubscriberVO getSubscriberInfoV2Result) {
        this.getSubscriberInfoV2Result = getSubscriberInfoV2Result;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetSubscriberInfoV2Response)) return false;
        GetSubscriberInfoV2Response other = (GetSubscriberInfoV2Response) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getSubscriberInfoV2Result==null && other.getGetSubscriberInfoV2Result()==null) || 
             (this.getSubscriberInfoV2Result!=null &&
              this.getSubscriberInfoV2Result.equals(other.getGetSubscriberInfoV2Result())));
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
        if (getGetSubscriberInfoV2Result() != null) {
            _hashCode += getGetSubscriberInfoV2Result().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetSubscriberInfoV2Response.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetSubscriberInfoV2Response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getSubscriberInfoV2Result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetSubscriberInfoV2Result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "GetSubscriberVO"));
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
