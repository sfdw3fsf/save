/**
 * GetUp_Asset_WarrantyResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.qldt;

public class GetUp_Asset_WarrantyResponse  implements java.io.Serializable {
    private String getUp_Asset_WarrantyResult;

    public GetUp_Asset_WarrantyResponse() {
    }

    public GetUp_Asset_WarrantyResponse(
           String getUp_Asset_WarrantyResult) {
           this.getUp_Asset_WarrantyResult = getUp_Asset_WarrantyResult;
    }


    /**
     * Gets the getUp_Asset_WarrantyResult value for this GetUp_Asset_WarrantyResponse.
     * 
     * @return getUp_Asset_WarrantyResult
     */
    public String getGetUp_Asset_WarrantyResult() {
        return getUp_Asset_WarrantyResult;
    }


    /**
     * Sets the getUp_Asset_WarrantyResult value for this GetUp_Asset_WarrantyResponse.
     * 
     * @param getUp_Asset_WarrantyResult
     */
    public void setGetUp_Asset_WarrantyResult(String getUp_Asset_WarrantyResult) {
        this.getUp_Asset_WarrantyResult = getUp_Asset_WarrantyResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetUp_Asset_WarrantyResponse)) return false;
        GetUp_Asset_WarrantyResponse other = (GetUp_Asset_WarrantyResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getUp_Asset_WarrantyResult==null && other.getGetUp_Asset_WarrantyResult()==null) || 
             (this.getUp_Asset_WarrantyResult!=null &&
              this.getUp_Asset_WarrantyResult.equals(other.getGetUp_Asset_WarrantyResult())));
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
        if (getGetUp_Asset_WarrantyResult() != null) {
            _hashCode += getGetUp_Asset_WarrantyResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetUp_Asset_WarrantyResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetUp_Asset_WarrantyResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getUp_Asset_WarrantyResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetUp_Asset_WarrantyResult"));
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
