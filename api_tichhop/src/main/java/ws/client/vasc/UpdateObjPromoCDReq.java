/**
 * UpdateObjPromoCDReq.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class UpdateObjPromoCDReq  implements java.io.Serializable {
    private String IPTVAcount;

    private String objPromoCD;

    private String objCustPromoCD;

    public UpdateObjPromoCDReq() {
    }

    public UpdateObjPromoCDReq(
           String IPTVAcount,
           String objPromoCD,
           String objCustPromoCD) {
           this.IPTVAcount = IPTVAcount;
           this.objPromoCD = objPromoCD;
           this.objCustPromoCD = objCustPromoCD;
    }


    /**
     * Gets the IPTVAcount value for this UpdateObjPromoCDReq.
     * 
     * @return IPTVAcount
     */
    public String getIPTVAcount() {
        return IPTVAcount;
    }


    /**
     * Sets the IPTVAcount value for this UpdateObjPromoCDReq.
     * 
     * @param IPTVAcount
     */
    public void setIPTVAcount(String IPTVAcount) {
        this.IPTVAcount = IPTVAcount;
    }


    /**
     * Gets the objPromoCD value for this UpdateObjPromoCDReq.
     * 
     * @return objPromoCD
     */
    public String getObjPromoCD() {
        return objPromoCD;
    }


    /**
     * Sets the objPromoCD value for this UpdateObjPromoCDReq.
     * 
     * @param objPromoCD
     */
    public void setObjPromoCD(String objPromoCD) {
        this.objPromoCD = objPromoCD;
    }


    /**
     * Gets the objCustPromoCD value for this UpdateObjPromoCDReq.
     * 
     * @return objCustPromoCD
     */
    public String getObjCustPromoCD() {
        return objCustPromoCD;
    }


    /**
     * Sets the objCustPromoCD value for this UpdateObjPromoCDReq.
     * 
     * @param objCustPromoCD
     */
    public void setObjCustPromoCD(String objCustPromoCD) {
        this.objCustPromoCD = objCustPromoCD;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof UpdateObjPromoCDReq)) return false;
        UpdateObjPromoCDReq other = (UpdateObjPromoCDReq) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.IPTVAcount==null && other.getIPTVAcount()==null) || 
             (this.IPTVAcount!=null &&
              this.IPTVAcount.equals(other.getIPTVAcount()))) &&
            ((this.objPromoCD==null && other.getObjPromoCD()==null) || 
             (this.objPromoCD!=null &&
              this.objPromoCD.equals(other.getObjPromoCD()))) &&
            ((this.objCustPromoCD==null && other.getObjCustPromoCD()==null) || 
             (this.objCustPromoCD!=null &&
              this.objCustPromoCD.equals(other.getObjCustPromoCD())));
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
        if (getIPTVAcount() != null) {
            _hashCode += getIPTVAcount().hashCode();
        }
        if (getObjPromoCD() != null) {
            _hashCode += getObjPromoCD().hashCode();
        }
        if (getObjCustPromoCD() != null) {
            _hashCode += getObjCustPromoCD().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UpdateObjPromoCDReq.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "UpdateObjPromoCDReq"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IPTVAcount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "IPTVAcount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("objPromoCD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ObjPromoCD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("objCustPromoCD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ObjCustPromoCD"));
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
