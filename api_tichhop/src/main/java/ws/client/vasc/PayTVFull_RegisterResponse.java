/**
 * PayTVFull_RegisterResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class PayTVFull_RegisterResponse  implements java.io.Serializable {
    private ChangeSubscriberVO payTVFull_RegisterResult;

    public PayTVFull_RegisterResponse() {
    }

    public PayTVFull_RegisterResponse(
           ChangeSubscriberVO payTVFull_RegisterResult) {
           this.payTVFull_RegisterResult = payTVFull_RegisterResult;
    }


    /**
     * Gets the payTVFull_RegisterResult value for this PayTVFull_RegisterResponse.
     * 
     * @return payTVFull_RegisterResult
     */
    public ChangeSubscriberVO getPayTVFull_RegisterResult() {
        return payTVFull_RegisterResult;
    }


    /**
     * Sets the payTVFull_RegisterResult value for this PayTVFull_RegisterResponse.
     * 
     * @param payTVFull_RegisterResult
     */
    public void setPayTVFull_RegisterResult(ChangeSubscriberVO payTVFull_RegisterResult) {
        this.payTVFull_RegisterResult = payTVFull_RegisterResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof PayTVFull_RegisterResponse)) return false;
        PayTVFull_RegisterResponse other = (PayTVFull_RegisterResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.payTVFull_RegisterResult==null && other.getPayTVFull_RegisterResult()==null) || 
             (this.payTVFull_RegisterResult!=null &&
              this.payTVFull_RegisterResult.equals(other.getPayTVFull_RegisterResult())));
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
        if (getPayTVFull_RegisterResult() != null) {
            _hashCode += getPayTVFull_RegisterResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PayTVFull_RegisterResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">PayTVFull_RegisterResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payTVFull_RegisterResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PayTVFull_RegisterResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ChangeSubscriberVO"));
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
