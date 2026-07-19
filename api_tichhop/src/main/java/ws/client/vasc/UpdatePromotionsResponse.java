/**
 * UpdatePromotionsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class UpdatePromotionsResponse  implements java.io.Serializable {
    private UpdatePromoVO updatePromotionsResult;

    public UpdatePromotionsResponse() {
    }

    public UpdatePromotionsResponse(
           UpdatePromoVO updatePromotionsResult) {
           this.updatePromotionsResult = updatePromotionsResult;
    }


    /**
     * Gets the updatePromotionsResult value for this UpdatePromotionsResponse.
     * 
     * @return updatePromotionsResult
     */
    public UpdatePromoVO getUpdatePromotionsResult() {
        return updatePromotionsResult;
    }


    /**
     * Sets the updatePromotionsResult value for this UpdatePromotionsResponse.
     * 
     * @param updatePromotionsResult
     */
    public void setUpdatePromotionsResult(UpdatePromoVO updatePromotionsResult) {
        this.updatePromotionsResult = updatePromotionsResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UpdatePromotionsResponse)) return false;
        UpdatePromotionsResponse other = (UpdatePromotionsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.updatePromotionsResult==null && other.getUpdatePromotionsResult()==null) || 
             (this.updatePromotionsResult!=null &&
              this.updatePromotionsResult.equals(other.getUpdatePromotionsResult())));
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
        if (getUpdatePromotionsResult() != null) {
            _hashCode += getUpdatePromotionsResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdatePromotionsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">UpdatePromotionsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updatePromotionsResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "UpdatePromotionsResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "UpdatePromoVO"));
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
