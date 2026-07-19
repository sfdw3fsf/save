/**
 * AddonPayTVInfoResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class AddonPayTVInfoResponse  implements java.io.Serializable {
    private String addonPayTVInfoResult;

    public AddonPayTVInfoResponse() {
    }

    public AddonPayTVInfoResponse(
           String addonPayTVInfoResult) {
           this.addonPayTVInfoResult = addonPayTVInfoResult;
    }


    /**
     * Gets the addonPayTVInfoResult value for this AddonPayTVInfoResponse.
     * 
     * @return addonPayTVInfoResult
     */
    public String getAddonPayTVInfoResult() {
        return addonPayTVInfoResult;
    }


    /**
     * Sets the addonPayTVInfoResult value for this AddonPayTVInfoResponse.
     * 
     * @param addonPayTVInfoResult
     */
    public void setAddonPayTVInfoResult(String addonPayTVInfoResult) {
        this.addonPayTVInfoResult = addonPayTVInfoResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof AddonPayTVInfoResponse)) return false;
        AddonPayTVInfoResponse other = (AddonPayTVInfoResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.addonPayTVInfoResult==null && other.getAddonPayTVInfoResult()==null) || 
             (this.addonPayTVInfoResult!=null &&
              this.addonPayTVInfoResult.equals(other.getAddonPayTVInfoResult())));
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
        if (getAddonPayTVInfoResult() != null) {
            _hashCode += getAddonPayTVInfoResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AddonPayTVInfoResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">AddonPayTVInfoResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addonPayTVInfoResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "AddonPayTVInfoResult"));
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
