/**
 * SendRequestResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.ivan;

public class SendRequestResponse  implements java.io.Serializable {
    private String sendRequestResult;

    private Object[] outputVal;

    public SendRequestResponse() {
    }

    public SendRequestResponse(
           String sendRequestResult,
           Object[] outputVal) {
           this.sendRequestResult = sendRequestResult;
           this.outputVal = outputVal;
    }


    /**
     * Gets the sendRequestResult value for this SendRequestResponse.
     * 
     * @return sendRequestResult
     */
    public String getSendRequestResult() {
        return sendRequestResult;
    }


    /**
     * Sets the sendRequestResult value for this SendRequestResponse.
     * 
     * @param sendRequestResult
     */
    public void setSendRequestResult(String sendRequestResult) {
        this.sendRequestResult = sendRequestResult;
    }


    /**
     * Gets the outputVal value for this SendRequestResponse.
     * 
     * @return outputVal
     */
    public Object[] getOutputVal() {
        return outputVal;
    }


    /**
     * Sets the outputVal value for this SendRequestResponse.
     * 
     * @param outputVal
     */
    public void setOutputVal(Object[] outputVal) {
        this.outputVal = outputVal;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof SendRequestResponse)) return false;
        SendRequestResponse other = (SendRequestResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sendRequestResult==null && other.getSendRequestResult()==null) || 
             (this.sendRequestResult!=null &&
              this.sendRequestResult.equals(other.getSendRequestResult()))) &&
            ((this.outputVal==null && other.getOutputVal()==null) || 
             (this.outputVal!=null &&
              java.util.Arrays.equals(this.outputVal, other.getOutputVal())));
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
        if (getSendRequestResult() != null) {
            _hashCode += getSendRequestResult().hashCode();
        }
        if (getOutputVal() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getOutputVal());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getOutputVal(), i);
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
        new org.apache.axis.description.TypeDesc(SendRequestResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">SendRequestResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sendRequestResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "SendRequestResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outputVal");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "outputVal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "anyType"));
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
