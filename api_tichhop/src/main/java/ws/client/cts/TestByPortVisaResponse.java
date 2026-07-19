/**
 * TestByPortVisaResponse.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.cts;

public class TestByPortVisaResponse implements java.io.Serializable {
    private ws.client.cts.TestByPortVisaResponseTestByPortVisaResult testByPortVisaResult;

    public TestByPortVisaResponse() {
    }

    public TestByPortVisaResponse(
            ws.client.cts.TestByPortVisaResponseTestByPortVisaResult testByPortVisaResult) {
        this.testByPortVisaResult = testByPortVisaResult;
    }


    /**
     * Gets the testByPortVisaResult value for this TestByPortVisaResponse.
     *
     * @return testByPortVisaResult
     */
    public ws.client.cts.TestByPortVisaResponseTestByPortVisaResult getTestByPortVisaResult() {
        return testByPortVisaResult;
    }


    /**
     * Sets the testByPortVisaResult value for this TestByPortVisaResponse.
     *
     * @param testByPortVisaResult
     */
    public void setTestByPortVisaResult(ws.client.cts.TestByPortVisaResponseTestByPortVisaResult testByPortVisaResult) {
        this.testByPortVisaResult = testByPortVisaResult;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TestByPortVisaResponse)) return false;
        TestByPortVisaResponse other = (TestByPortVisaResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.testByPortVisaResult == null && other.getTestByPortVisaResult() == null) ||
                        (this.testByPortVisaResult != null &&
                                this.testByPortVisaResult.equals(other.getTestByPortVisaResult())));
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
        if (getTestByPortVisaResult() != null) {
            _hashCode += getTestByPortVisaResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(TestByPortVisaResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://vnpt.vn/", ">TestByPortVisaResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("testByPortVisaResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "TestByPortVisaResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://vnpt.vn/", ">>TestByPortVisaResponse>TestByPortVisaResult"));
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
