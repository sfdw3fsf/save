/**
 * OfferResouceObject.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vtn;

public class OfferResouceObject  implements java.io.Serializable {
    private String offer_id;

    private String offer_name;

    private Object[] vtExternal;

    public OfferResouceObject() {
    }

    public OfferResouceObject(
           String offer_id,
           String offer_name,
           Object[] vtExternal) {
           this.offer_id = offer_id;
           this.offer_name = offer_name;
           this.vtExternal = vtExternal;
    }


    /**
     * Gets the offer_id value for this OfferResouceObject.
     * 
     * @return offer_id
     */
    public String getOffer_id() {
        return offer_id;
    }


    /**
     * Sets the offer_id value for this OfferResouceObject.
     * 
     * @param offer_id
     */
    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }


    /**
     * Gets the offer_name value for this OfferResouceObject.
     * 
     * @return offer_name
     */
    public String getOffer_name() {
        return offer_name;
    }


    /**
     * Sets the offer_name value for this OfferResouceObject.
     * 
     * @param offer_name
     */
    public void setOffer_name(String offer_name) {
        this.offer_name = offer_name;
    }


    /**
     * Gets the vtExternal value for this OfferResouceObject.
     * 
     * @return vtExternal
     */
    public Object[] getVtExternal() {
        return vtExternal;
    }


    /**
     * Sets the vtExternal value for this OfferResouceObject.
     * 
     * @param vtExternal
     */
    public void setVtExternal(Object[] vtExternal) {
        this.vtExternal = vtExternal;
    }

    public Object getVtExternal(int i) {
        return this.vtExternal[i];
    }

    public void setVtExternal(int i, Object _value) {
        this.vtExternal[i] = _value;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof OfferResouceObject)) return false;
        OfferResouceObject other = (OfferResouceObject) obj;
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
            ((this.offer_name==null && other.getOffer_name()==null) || 
             (this.offer_name!=null &&
              this.offer_name.equals(other.getOffer_name()))) &&
            ((this.vtExternal==null && other.getVtExternal()==null) || 
             (this.vtExternal!=null &&
              java.util.Arrays.equals(this.vtExternal, other.getVtExternal())));
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
        if (getOffer_name() != null) {
            _hashCode += getOffer_name().hashCode();
        }
        if (getVtExternal() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVtExternal());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getVtExternal(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OfferResouceObject.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://soap.crm.telsoft/", "offerResouceObject"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("offer_id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "offer_id"));
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
        elemField.setFieldName("vtExternal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vtExternal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anyType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
