/**
 * GetUp_Asset_RealityStationResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.qldt;

public class GetUp_Asset_RealityStationResponse  implements java.io.Serializable {
    private String getUp_Asset_RealityStationResult;

    public GetUp_Asset_RealityStationResponse() {
    }

    public GetUp_Asset_RealityStationResponse(
           String getUp_Asset_RealityStationResult) {
           this.getUp_Asset_RealityStationResult = getUp_Asset_RealityStationResult;
    }


    /**
     * Gets the getUp_Asset_RealityStationResult value for this GetUp_Asset_RealityStationResponse.
     * 
     * @return getUp_Asset_RealityStationResult
     */
    public String getGetUp_Asset_RealityStationResult() {
        return getUp_Asset_RealityStationResult;
    }


    /**
     * Sets the getUp_Asset_RealityStationResult value for this GetUp_Asset_RealityStationResponse.
     * 
     * @param getUp_Asset_RealityStationResult
     */
    public void setGetUp_Asset_RealityStationResult(String getUp_Asset_RealityStationResult) {
        this.getUp_Asset_RealityStationResult = getUp_Asset_RealityStationResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetUp_Asset_RealityStationResponse)) return false;
        GetUp_Asset_RealityStationResponse other = (GetUp_Asset_RealityStationResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getUp_Asset_RealityStationResult==null && other.getGetUp_Asset_RealityStationResult()==null) || 
             (this.getUp_Asset_RealityStationResult!=null &&
              this.getUp_Asset_RealityStationResult.equals(other.getGetUp_Asset_RealityStationResult())));
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
        if (getGetUp_Asset_RealityStationResult() != null) {
            _hashCode += getGetUp_Asset_RealityStationResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetUp_Asset_RealityStationResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetUp_Asset_RealityStationResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getUp_Asset_RealityStationResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetUp_Asset_RealityStationResult"));
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
