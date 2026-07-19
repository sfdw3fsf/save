/**
 * ChangePromotionsV2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class ChangePromotionsV2  implements java.io.Serializable {
    private String IPTVACCOUNT;

    private String sPromo_ID;

    private String option;

    public ChangePromotionsV2() {
    }

    public ChangePromotionsV2(
           String IPTVACCOUNT,
           String sPromo_ID,
           String option) {
           this.IPTVACCOUNT = IPTVACCOUNT;
           this.sPromo_ID = sPromo_ID;
           this.option = option;
    }


    /**
     * Gets the IPTVACCOUNT value for this ChangePromotionsV2.
     * 
     * @return IPTVACCOUNT
     */
    public String getIPTVACCOUNT() {
        return IPTVACCOUNT;
    }


    /**
     * Sets the IPTVACCOUNT value for this ChangePromotionsV2.
     * 
     * @param IPTVACCOUNT
     */
    public void setIPTVACCOUNT(String IPTVACCOUNT) {
        this.IPTVACCOUNT = IPTVACCOUNT;
    }


    /**
     * Gets the sPromo_ID value for this ChangePromotionsV2.
     * 
     * @return sPromo_ID
     */
    public String getSPromo_ID() {
        return sPromo_ID;
    }


    /**
     * Sets the sPromo_ID value for this ChangePromotionsV2.
     * 
     * @param sPromo_ID
     */
    public void setSPromo_ID(String sPromo_ID) {
        this.sPromo_ID = sPromo_ID;
    }


    /**
     * Gets the option value for this ChangePromotionsV2.
     * 
     * @return option
     */
    public String getOption() {
        return option;
    }


    /**
     * Sets the option value for this ChangePromotionsV2.
     * 
     * @param option
     */
    public void setOption(String option) {
        this.option = option;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangePromotionsV2)) return false;
        ChangePromotionsV2 other = (ChangePromotionsV2) obj;
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
            ((this.sPromo_ID==null && other.getSPromo_ID()==null) || 
             (this.sPromo_ID!=null &&
              this.sPromo_ID.equals(other.getSPromo_ID()))) &&
            ((this.option==null && other.getOption()==null) || 
             (this.option!=null &&
              this.option.equals(other.getOption())));
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
        if (getSPromo_ID() != null) {
            _hashCode += getSPromo_ID().hashCode();
        }
        if (getOption() != null) {
            _hashCode += getOption().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangePromotionsV2.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">ChangePromotionsV2"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IPTVACCOUNT");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IPTVACCOUNT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPromo_ID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sPromo_ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("option");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "option"));
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
