/**
 * ChangePromotionsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangePromotionsResponse  implements java.io.Serializable {
    private ChangePromotionsVO changePromotionsResult;

    public ChangePromotionsResponse() {
    }

    public ChangePromotionsResponse(
           ChangePromotionsVO changePromotionsResult) {
           this.changePromotionsResult = changePromotionsResult;
    }


    /**
     * Gets the changePromotionsResult value for this ChangePromotionsResponse.
     * 
     * @return changePromotionsResult
     */
    public ChangePromotionsVO getChangePromotionsResult() {
        return changePromotionsResult;
    }


    /**
     * Sets the changePromotionsResult value for this ChangePromotionsResponse.
     * 
     * @param changePromotionsResult
     */
    public void setChangePromotionsResult(ChangePromotionsVO changePromotionsResult) {
        this.changePromotionsResult = changePromotionsResult;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangePromotionsResponse)) return false;
        ChangePromotionsResponse other = (ChangePromotionsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.changePromotionsResult==null && other.getChangePromotionsResult()==null) || 
             (this.changePromotionsResult!=null &&
              this.changePromotionsResult.equals(other.getChangePromotionsResult())));
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
        if (getChangePromotionsResult() != null) {
            _hashCode += getChangePromotionsResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangePromotionsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ChangePromotionsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changePromotionsResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ChangePromotionsResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "ChangePromotionsVO"));
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
