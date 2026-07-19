/**
 * TestOnuPortResponse.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.cts;

public class TestOnuPortResponse implements java.io.Serializable {
    private ws.client.cts.TestOnuPortResponseTestOnuPortResult testOnuPortResult;

    public TestOnuPortResponse() {
    }

    public TestOnuPortResponse(
            ws.client.cts.TestOnuPortResponseTestOnuPortResult testOnuPortResult) {
        this.testOnuPortResult = testOnuPortResult;
    }


    /**
     * Gets the testOnuPortResult value for this TestOnuPortResponse.
     *
     * @return testOnuPortResult
     */
    public ws.client.cts.TestOnuPortResponseTestOnuPortResult getTestOnuPortResult() {
        return testOnuPortResult;
    }


    /**
     * Sets the testOnuPortResult value for this TestOnuPortResponse.
     *
     * @param testOnuPortResult
     */
    public void setTestOnuPortResult(ws.client.cts.TestOnuPortResponseTestOnuPortResult testOnuPortResult) {
        this.testOnuPortResult = testOnuPortResult;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TestOnuPortResponse)) return false;
        TestOnuPortResponse other = (TestOnuPortResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.testOnuPortResult == null && other.getTestOnuPortResult() == null) ||
                        (this.testOnuPortResult != null &&
                                this.testOnuPortResult.equals(other.getTestOnuPortResult())));
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
        if (getTestOnuPortResult() != null) {
            _hashCode += getTestOnuPortResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(TestOnuPortResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://vnpt.vn/", ">TestOnuPortResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("testOnuPortResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "TestOnuPortResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://vnpt.vn/", ">>TestOnuPortResponse>TestOnuPortResult"));
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
