/**
 * GetSubscriberInfoV2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class GetSubscriberInfoV2  implements java.io.Serializable {
    private GetSubscriberRequest objRequest;

    public GetSubscriberInfoV2() {
    }

    public GetSubscriberInfoV2(
           GetSubscriberRequest objRequest) {
           this.objRequest = objRequest;
    }


    /**
     * Gets the objRequest value for this GetSubscriberInfoV2.
     * 
     * @return objRequest
     */
    public GetSubscriberRequest getObjRequest() {
        return objRequest;
    }


    /**
     * Sets the objRequest value for this GetSubscriberInfoV2.
     * 
     * @param objRequest
     */
    public void setObjRequest(GetSubscriberRequest objRequest) {
        this.objRequest = objRequest;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetSubscriberInfoV2)) return false;
        GetSubscriberInfoV2 other = (GetSubscriberInfoV2) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.objRequest==null && other.getObjRequest()==null) || 
             (this.objRequest!=null &&
              this.objRequest.equals(other.getObjRequest())));
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
        if (getObjRequest() != null) {
            _hashCode += getObjRequest().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetSubscriberInfoV2.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetSubscriberInfoV2"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("objRequest");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "objRequest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "GetSubscriberRequest"));
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
