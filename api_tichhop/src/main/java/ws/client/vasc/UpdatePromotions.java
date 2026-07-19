/**
 * UpdatePromotions.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class UpdatePromotions  implements java.io.Serializable {
    private String IPTVACCOUNT;

    private String promoCD;

    public UpdatePromotions() {
    }

    public UpdatePromotions(
           String IPTVACCOUNT,
           String promoCD) {
           this.IPTVACCOUNT = IPTVACCOUNT;
           this.promoCD = promoCD;
    }


    /**
     * Gets the IPTVACCOUNT value for this UpdatePromotions.
     * 
     * @return IPTVACCOUNT
     */
    public String getIPTVACCOUNT() {
        return IPTVACCOUNT;
    }


    /**
     * Sets the IPTVACCOUNT value for this UpdatePromotions.
     * 
     * @param IPTVACCOUNT
     */
    public void setIPTVACCOUNT(String IPTVACCOUNT) {
        this.IPTVACCOUNT = IPTVACCOUNT;
    }


    /**
     * Gets the promoCD value for this UpdatePromotions.
     * 
     * @return promoCD
     */
    public String getPromoCD() {
        return promoCD;
    }


    /**
     * Sets the promoCD value for this UpdatePromotions.
     * 
     * @param promoCD
     */
    public void setPromoCD(String promoCD) {
        this.promoCD = promoCD;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UpdatePromotions)) return false;
        UpdatePromotions other = (UpdatePromotions) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.IPTVACCOUNT==null && other.getIPTVACCOUNT()==null) || 
             (this.IPTVACCOUNT!=null &&
              this.IPTVACCOUNT.equals(other.getIPTVACCOUNT()))) &&
            ((this.promoCD==null && other.getPromoCD()==null) || 
             (this.promoCD!=null &&
              this.promoCD.equals(other.getPromoCD())));
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
        if (getIPTVACCOUNT() != null) {
            _hashCode += getIPTVACCOUNT().hashCode();
        }
        if (getPromoCD() != null) {
            _hashCode += getPromoCD().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdatePromotions.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">UpdatePromotions"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IPTVACCOUNT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IPTVACCOUNT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("promoCD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "PromoCD"));
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
