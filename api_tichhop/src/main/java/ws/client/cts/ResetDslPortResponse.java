/**
 * ResetDslPortResponse.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.cts;

public class ResetDslPortResponse implements java.io.Serializable {
    private ws.client.cts.TaskOfString resetDslPortResult;

    public ResetDslPortResponse() {
    }

    public ResetDslPortResponse(
            ws.client.cts.TaskOfString resetDslPortResult) {
        this.resetDslPortResult = resetDslPortResult;
    }


    /**
     * Gets the resetDslPortResult value for this ResetDslPortResponse.
     *
     * @return resetDslPortResult
     */
    public ws.client.cts.TaskOfString getResetDslPortResult() {
        return resetDslPortResult;
    }


    /**
     * Sets the resetDslPortResult value for this ResetDslPortResponse.
     *
     * @param resetDslPortResult
     */
    public void setResetDslPortResult(ws.client.cts.TaskOfString resetDslPortResult) {
        this.resetDslPortResult = resetDslPortResult;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResetDslPortResponse)) return false;
        ResetDslPortResponse other = (ResetDslPortResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.resetDslPortResult == null && other.getResetDslPortResult() == null) ||
                        (this.resetDslPortResult != null &&
                                this.resetDslPortResult.equals(other.getResetDslPortResult())));
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
        if (getResetDslPortResult() != null) {
            _hashCode += getResetDslPortResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(ResetDslPortResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://vnpt.vn/", ">ResetDslPortResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("resetDslPortResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "ResetDslPortResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://vnpt.vn/", "TaskOfString"));
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
