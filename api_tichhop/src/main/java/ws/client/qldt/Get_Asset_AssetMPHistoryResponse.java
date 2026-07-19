/**
 * Get_Asset_AssetMPHistoryResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.qldt;

public class Get_Asset_AssetMPHistoryResponse  implements java.io.Serializable {
    private String get_Asset_AssetMPHistoryResult;

    public Get_Asset_AssetMPHistoryResponse() {
    }

    public Get_Asset_AssetMPHistoryResponse(
           String get_Asset_AssetMPHistoryResult) {
           this.get_Asset_AssetMPHistoryResult = get_Asset_AssetMPHistoryResult;
    }


    /**
     * Gets the get_Asset_AssetMPHistoryResult value for this Get_Asset_AssetMPHistoryResponse.
     * 
     * @return get_Asset_AssetMPHistoryResult
     */
    public String getGet_Asset_AssetMPHistoryResult() {
        return get_Asset_AssetMPHistoryResult;
    }


    /**
     * Sets the get_Asset_AssetMPHistoryResult value for this Get_Asset_AssetMPHistoryResponse.
     * 
     * @param get_Asset_AssetMPHistoryResult
     */
    public void setGet_Asset_AssetMPHistoryResult(String get_Asset_AssetMPHistoryResult) {
        this.get_Asset_AssetMPHistoryResult = get_Asset_AssetMPHistoryResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Get_Asset_AssetMPHistoryResponse)) return false;
        Get_Asset_AssetMPHistoryResponse other = (Get_Asset_AssetMPHistoryResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.get_Asset_AssetMPHistoryResult==null && other.getGet_Asset_AssetMPHistoryResult()==null) || 
             (this.get_Asset_AssetMPHistoryResult!=null &&
              this.get_Asset_AssetMPHistoryResult.equals(other.getGet_Asset_AssetMPHistoryResult())));
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
        if (getGet_Asset_AssetMPHistoryResult() != null) {
            _hashCode += getGet_Asset_AssetMPHistoryResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Get_Asset_AssetMPHistoryResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Get_Asset_AssetMPHistoryResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("get_Asset_AssetMPHistoryResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "Get_Asset_AssetMPHistoryResult"));
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
