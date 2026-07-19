/**
 * ChangePromotionsV2Response.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangePromotionsV2Response  implements java.io.Serializable {
    private ChangePromotionsVO changePromotionsV2Result;

    public ChangePromotionsV2Response() {
    }

    public ChangePromotionsV2Response(
           ChangePromotionsVO changePromotionsV2Result) {
           this.changePromotionsV2Result = changePromotionsV2Result;
    }


    /**
     * Gets the changePromotionsV2Result value for this ChangePromotionsV2Response.
     * 
     * @return changePromotionsV2Result
     */
    public ChangePromotionsVO getChangePromotionsV2Result() {
        return changePromotionsV2Result;
    }


    /**
     * Sets the changePromotionsV2Result value for this ChangePromotionsV2Response.
     * 
     * @param changePromotionsV2Result
     */
    public void setChangePromotionsV2Result(ChangePromotionsVO changePromotionsV2Result) {
        this.changePromotionsV2Result = changePromotionsV2Result;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangePromotionsV2Response)) return false;
        ChangePromotionsV2Response other = (ChangePromotionsV2Response) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.changePromotionsV2Result==null && other.getChangePromotionsV2Result()==null) || 
             (this.changePromotionsV2Result!=null &&
              this.changePromotionsV2Result.equals(other.getChangePromotionsV2Result())));
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
        if (getChangePromotionsV2Result() != null) {
            _hashCode += getChangePromotionsV2Result().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangePromotionsV2Response.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ChangePromotionsV2Response"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("changePromotionsV2Result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ChangePromotionsV2Result"));
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
