/**
 * TestOnuByPortVisaResponse.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.cts;

public class TestOnuByPortVisaResponse implements java.io.Serializable {
    private ws.client.cts.TestOnuByPortVisaResponseTestOnuByPortVisaResult testOnuByPortVisaResult;

    public TestOnuByPortVisaResponse() {
    }

    public TestOnuByPortVisaResponse(
            ws.client.cts.TestOnuByPortVisaResponseTestOnuByPortVisaResult testOnuByPortVisaResult) {
        this.testOnuByPortVisaResult = testOnuByPortVisaResult;
    }


    /**
     * Gets the testOnuByPortVisaResult value for this TestOnuByPortVisaResponse.
     *
     * @return testOnuByPortVisaResult
     */
    public ws.client.cts.TestOnuByPortVisaResponseTestOnuByPortVisaResult getTestOnuByPortVisaResult() {
        return testOnuByPortVisaResult;
    }


    /**
     * Sets the testOnuByPortVisaResult value for this TestOnuByPortVisaResponse.
     *
     * @param testOnuByPortVisaResult
     */
    public void setTestOnuByPortVisaResult(ws.client.cts.TestOnuByPortVisaResponseTestOnuByPortVisaResult testOnuByPortVisaResult) {
        this.testOnuByPortVisaResult = testOnuByPortVisaResult;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TestOnuByPortVisaResponse)) return false;
        TestOnuByPortVisaResponse other = (TestOnuByPortVisaResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.testOnuByPortVisaResult == null && other.getTestOnuByPortVisaResult() == null) ||
                        (this.testOnuByPortVisaResult != null &&
                                this.testOnuByPortVisaResult.equals(other.getTestOnuByPortVisaResult())));
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
        if (getTestOnuByPortVisaResult() != null) {
            _hashCode += getTestOnuByPortVisaResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(TestOnuByPortVisaResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://vnpt.vn/", ">TestOnuByPortVisaResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("testOnuByPortVisaResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "TestOnuByPortVisaResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://vnpt.vn/", ">>TestOnuByPortVisaResponse>TestOnuByPortVisaResult"));
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
