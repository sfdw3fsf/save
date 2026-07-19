/**
 * OfferObject.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vtn;

public class OfferObject  implements java.io.Serializable {
    private String offer_id;

    private String offer_code;

    private String offer_name;

    private String offer_price;

    public OfferObject() {
    }

    public OfferObject(
           String offer_id,
           String offer_code,
           String offer_name,
           String offer_price) {
           this.offer_id = offer_id;
           this.offer_code = offer_code;
           this.offer_name = offer_name;
           this.offer_price = offer_price;
    }


    /**
     * Gets the offer_id value for this OfferObject.
     * 
     * @return offer_id
     */
    public String getOffer_id() {
        return offer_id;
    }


    /**
     * Sets the offer_id value for this OfferObject.
     * 
     * @param offer_id
     */
    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }


    /**
     * Gets the offer_code value for this OfferObject.
     * 
     * @return offer_code
     */
    public String getOffer_code() {
        return offer_code;
    }


    /**
     * Sets the offer_code value for this OfferObject.
     * 
     * @param offer_code
     */
    public void setOffer_code(String offer_code) {
        this.offer_code = offer_code;
    }


    /**
     * Gets the offer_name value for this OfferObject.
     * 
     * @return offer_name
     */
    public String getOffer_name() {
        return offer_name;
    }


    /**
     * Sets the offer_name value for this OfferObject.
     * 
     * @param offer_name
     */
    public void setOffer_name(String offer_name) {
        this.offer_name = offer_name;
    }


    /**
     * Gets the offer_price value for this OfferObject.
     * 
     * @return offer_price
     */
    public String getOffer_price() {
        return offer_price;
    }


    /**
     * Sets the offer_price value for this OfferObject.
     * 
     * @param offer_price
     */
    public void setOffer_price(String offer_price) {
        this.offer_price = offer_price;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof OfferObject)) return false;
        OfferObject other = (OfferObject) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.offer_id==null && other.getOffer_id()==null) || 
             (this.offer_id!=null &&
              this.offer_id.equals(other.getOffer_id()))) &&
            ((this.offer_code==null && other.getOffer_code()==null) || 
             (this.offer_code!=null &&
              this.offer_code.equals(other.getOffer_code()))) &&
            ((this.offer_name==null && other.getOffer_name()==null) || 
             (this.offer_name!=null &&
              this.offer_name.equals(other.getOffer_name()))) &&
            ((this.offer_price==null && other.getOffer_price()==null) || 
             (this.offer_price!=null &&
              this.offer_price.equals(other.getOffer_price())));
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
        if (getOffer_id() != null) {
            _hashCode += getOffer_id().hashCode();
        }
        if (getOffer_code() != null) {
            _hashCode += getOffer_code().hashCode();
        }
        if (getOffer_name() != null) {
            _hashCode += getOffer_name().hashCode();
        }
        if (getOffer_price() != null) {
            _hashCode += getOffer_price().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OfferObject.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.crm.telsoft/", "offerObject"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offer_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "offer_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offer_code");
        elemField.setXmlName(new javax.xml.namespace.QName("", "offer_code"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offer_name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "offer_name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offer_price");
        elemField.setXmlName(new javax.xml.namespace.QName("", "offer_price"));
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
