/**
 * ResetOnuConnectionResponse.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.cts;

public class ResetOnuConnectionResponse implements java.io.Serializable {
    private java.lang.String resetOnuConnectionResult;

    public ResetOnuConnectionResponse() {
    }

    public ResetOnuConnectionResponse(
            java.lang.String resetOnuConnectionResult) {
        this.resetOnuConnectionResult = resetOnuConnectionResult;
    }


    /**
     * Gets the resetOnuConnectionResult value for this ResetOnuConnectionResponse.
     *
     * @return resetOnuConnectionResult
     */
    public java.lang.String getResetOnuConnectionResult() {
        return resetOnuConnectionResult;
    }


    /**
     * Sets the resetOnuConnectionResult value for this ResetOnuConnectionResponse.
     *
     * @param resetOnuConnectionResult
     */
    public void setResetOnuConnectionResult(java.lang.String resetOnuConnectionResult) {
        this.resetOnuConnectionResult = resetOnuConnectionResult;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResetOnuConnectionResponse)) return false;
        ResetOnuConnectionResponse other = (ResetOnuConnectionResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.resetOnuConnectionResult == null && other.getResetOnuConnectionResult() == null) ||
                        (this.resetOnuConnectionResult != null &&
                                this.resetOnuConnectionResult.equals(other.getResetOnuConnectionResult())));
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
        if (getResetOnuConnectionResult() != null) {
            _hashCode += getResetOnuConnectionResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(ResetOnuConnectionResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://vnpt.vn/", ">ResetOnuConnectionResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resetOnuConnectionResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "ResetOnuConnectionResult"));
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
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new org.apache.axis.encoding.ser.BeanSerializer(
                        _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
            java.lang.String mechType,
            java.lang.Class _javaType,
            javax.xml.namespace.QName _xmlType) {
        return
                new org.apache.axis.encoding.ser.BeanDeserializer(
                        _javaType, _xmlType, typeDesc);
    }

}
