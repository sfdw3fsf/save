/**
 * GetUrlQualityFileResponse.java
 * <p>
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.cts;

public class GetUrlQualityFileResponse implements java.io.Serializable {
    private java.lang.String getUrlQualityFileResult;

    public GetUrlQualityFileResponse() {
    }

    public GetUrlQualityFileResponse(
            java.lang.String getUrlQualityFileResult) {
        this.getUrlQualityFileResult = getUrlQualityFileResult;
    }


    /**
     * Gets the getUrlQualityFileResult value for this GetUrlQualityFileResponse.
     *
     * @return getUrlQualityFileResult
     */
    public java.lang.String getGetUrlQualityFileResult() {
        return getUrlQualityFileResult;
    }


    /**
     * Sets the getUrlQualityFileResult value for this GetUrlQualityFileResponse.
     *
     * @param getUrlQualityFileResult
     */
    public void setGetUrlQualityFileResult(java.lang.String getUrlQualityFileResult) {
        this.getUrlQualityFileResult = getUrlQualityFileResult;
    }

    private java.lang.Object __equalsCalc = null;

    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetUrlQualityFileResponse)) return false;
        GetUrlQualityFileResponse other = (GetUrlQualityFileResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
                ((this.getUrlQualityFileResult == null && other.getGetUrlQualityFileResult() == null) ||
                        (this.getUrlQualityFileResult != null &&
                                this.getUrlQualityFileResult.equals(other.getGetUrlQualityFileResult())));
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
        if (getGetUrlQualityFileResult() != null) {
            _hashCode += getGetUrlQualityFileResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
            new org.apache.axis.description.TypeDesc(GetUrlQualityFileResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://vnpt.vn/", ">GetUrlQualityFileResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getUrlQualityFileResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://vnpt.vn/", "GetUrlQualityFileResult"));
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
