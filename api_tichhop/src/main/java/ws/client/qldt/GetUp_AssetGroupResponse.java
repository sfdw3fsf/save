/**
 * GetUp_AssetGroupResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.qldt;

public class GetUp_AssetGroupResponse  implements java.io.Serializable {
    private String getUp_AssetGroupResult;

    public GetUp_AssetGroupResponse() {
    }

    public GetUp_AssetGroupResponse(
           String getUp_AssetGroupResult) {
           this.getUp_AssetGroupResult = getUp_AssetGroupResult;
    }


    /**
     * Gets the getUp_AssetGroupResult value for this GetUp_AssetGroupResponse.
     * 
     * @return getUp_AssetGroupResult
     */
    public String getGetUp_AssetGroupResult() {
        return getUp_AssetGroupResult;
    }


    /**
     * Sets the getUp_AssetGroupResult value for this GetUp_AssetGroupResponse.
     * 
     * @param getUp_AssetGroupResult
     */
    public void setGetUp_AssetGroupResult(String getUp_AssetGroupResult) {
        this.getUp_AssetGroupResult = getUp_AssetGroupResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetUp_AssetGroupResponse)) return false;
        GetUp_AssetGroupResponse other = (GetUp_AssetGroupResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getUp_AssetGroupResult==null && other.getGetUp_AssetGroupResult()==null) || 
             (this.getUp_AssetGroupResult!=null &&
              this.getUp_AssetGroupResult.equals(other.getGetUp_AssetGroupResult())));
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
        if (getGetUp_AssetGroupResult() != null) {
            _hashCode += getGetUp_AssetGroupResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetUp_AssetGroupResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetUp_AssetGroupResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getUp_AssetGroupResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "GetUp_AssetGroupResult"));
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
