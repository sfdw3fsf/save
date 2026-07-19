/**
 * ChangeDslPortProfileByPortVisaResponse.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.cts;

public class ChangeDslPortProfileByPortVisaResponse implements java.io.Serializable {
    private java.lang.String changeDslPortProfileByPortVisaResult;

    public ChangeDslPortProfileByPortVisaResponse() {
    }

    public ChangeDslPortProfileByPortVisaResponse(
            java.lang.String changeDslPortProfileByPortVisaResult) {
        this.changeDslPortProfileByPortVisaResult = changeDslPortProfileByPortVisaResult;
    }


    /**
     * Gets the changeDslPortProfileByPortVisaResult value for this ChangeDslPortProfileByPortVisaResponse.
     *
     * @return changeDslPortProfileByPortVisaResult
     */
    public java.lang.String getChangeDslPortProfileByPortVisaResult() {
        return changeDslPortProfileByPortVisaResult;
    }


    /**
     * Sets the changeDslPortProfileByPortVisaResult value for this ChangeDslPortProfileByPortVisaResponse.
     *
     * @param changeDslPortProfileByPortVisaResult
     */
    public void setChangeDslPortProfileByPortVisaResult(java.lang.String changeDslPortProfileByPortVisaResult) {
        this.changeDslPortProfileByPortVisaResult = changeDslPortProfileByPortVisaResult;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ChangeDslPortProfileByPortVisaResponse)) return false;
        ChangeDslPortProfileByPortVisaResponse other = (ChangeDslPortProfileByPortVisaResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.changeDslPortProfileByPortVisaResult == null && other.getChangeDslPortProfileByPortVisaResult() == null) ||
                        (this.changeDslPortProfileByPortVisaResult != null &&
                                this.changeDslPortProfileByPortVisaResult.equals(other.getChangeDslPortProfileByPortVisaResult())));
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
        if (getChangeDslPortProfileByPortVisaResult() != null) {
            _hashCode += getChangeDslPortProfileByPortVisaResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(ChangeDslPortProfileByPortVisaResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://vnpt.vn/", ">ChangeDslPortProfileByPortVisaResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changeDslPortProfileByPortVisaResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "ChangeDslPortProfileByPortVisaResult"));
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
