/**
 * EditDistrict.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class EditDistrict  implements java.io.Serializable {
    private String districtCD;

    private String districtName;

    private String districtNote;

    public EditDistrict() {
    }

    public EditDistrict(
           String districtCD,
           String districtName,
           String districtNote) {
           this.districtCD = districtCD;
           this.districtName = districtName;
           this.districtNote = districtNote;
    }


    /**
     * Gets the districtCD value for this EditDistrict.
     * 
     * @return districtCD
     */
    public String getDistrictCD() {
        return districtCD;
    }


    /**
     * Sets the districtCD value for this EditDistrict.
     * 
     * @param districtCD
     */
    public void setDistrictCD(String districtCD) {
        this.districtCD = districtCD;
    }


    /**
     * Gets the districtName value for this EditDistrict.
     * 
     * @return districtName
     */
    public String getDistrictName() {
        return districtName;
    }


    /**
     * Sets the districtName value for this EditDistrict.
     * 
     * @param districtName
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }


    /**
     * Gets the districtNote value for this EditDistrict.
     * 
     * @return districtNote
     */
    public String getDistrictNote() {
        return districtNote;
    }


    /**
     * Sets the districtNote value for this EditDistrict.
     * 
     * @param districtNote
     */
    public void setDistrictNote(String districtNote) {
        this.districtNote = districtNote;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof EditDistrict)) return false;
        EditDistrict other = (EditDistrict) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.districtCD==null && other.getDistrictCD()==null) || 
             (this.districtCD!=null &&
              this.districtCD.equals(other.getDistrictCD()))) &&
            ((this.districtName==null && other.getDistrictName()==null) || 
             (this.districtName!=null &&
              this.districtName.equals(other.getDistrictName()))) &&
            ((this.districtNote==null && other.getDistrictNote()==null) || 
             (this.districtNote!=null &&
              this.districtNote.equals(other.getDistrictNote())));
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
        if (getDistrictCD() != null) {
            _hashCode += getDistrictCD().hashCode();
        }
        if (getDistrictName() != null) {
            _hashCode += getDistrictName().hashCode();
        }
        if (getDistrictNote() != null) {
            _hashCode += getDistrictNote().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EditDistrict.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">EditDistrict"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("districtCD");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DistrictCD"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("districtName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DistrictName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("districtNote");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "DistrictNote"));
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
