/**
 * TestByPortResponse.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.cts;

public class TestByPortResponse implements java.io.Serializable {
    private ws.client.cts.TestByPortResponseTestByPortResult testByPortResult;

    public TestByPortResponse() {
    }

    public TestByPortResponse(
            ws.client.cts.TestByPortResponseTestByPortResult testByPortResult) {
        this.testByPortResult = testByPortResult;
    }


    /**
     * Gets the testByPortResult value for this TestByPortResponse.
     *
     * @return testByPortResult
     */
    public ws.client.cts.TestByPortResponseTestByPortResult getTestByPortResult() {
        return testByPortResult;
    }


    /**
     * Sets the testByPortResult value for this TestByPortResponse.
     *
     * @param testByPortResult
     */
    public void setTestByPortResult(ws.client.cts.TestByPortResponseTestByPortResult testByPortResult) {
        this.testByPortResult = testByPortResult;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TestByPortResponse)) return false;
        TestByPortResponse other = (TestByPortResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.testByPortResult == null && other.getTestByPortResult() == null) ||
                        (this.testByPortResult != null &&
                                this.testByPortResult.equals(other.getTestByPortResult())));
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
        if (getTestByPortResult() != null) {
            _hashCode += getTestByPortResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(TestByPortResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://vnpt.vn/", ">TestByPortResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("testByPortResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "TestByPortResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://vnpt.vn/", ">>TestByPortResponse>TestByPortResult"));
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
