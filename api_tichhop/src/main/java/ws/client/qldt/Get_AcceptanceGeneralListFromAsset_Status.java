/**
 * Get_AcceptanceGeneralListFromAsset_Status.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.qldt;

public class Get_AcceptanceGeneralListFromAsset_Status  implements java.io.Serializable {
    private String sUserID;

    private String sPass;

    private String sSOPHIEU;

    public Get_AcceptanceGeneralListFromAsset_Status() {
    }

    public Get_AcceptanceGeneralListFromAsset_Status(
           String sUserID,
           String sPass,
           String sSOPHIEU) {
           this.sUserID = sUserID;
           this.sPass = sPass;
           this.sSOPHIEU = sSOPHIEU;
    }


    /**
     * Gets the sUserID value for this Get_AcceptanceGeneralListFromAsset_Status.
     * 
     * @return sUserID
     */
    public String getSUserID() {
        return sUserID;
    }


    /**
     * Sets the sUserID value for this Get_AcceptanceGeneralListFromAsset_Status.
     * 
     * @param sUserID
     */
    public void setSUserID(String sUserID) {
        this.sUserID = sUserID;
    }


    /**
     * Gets the sPass value for this Get_AcceptanceGeneralListFromAsset_Status.
     * 
     * @return sPass
     */
    public String getSPass() {
        return sPass;
    }


    /**
     * Sets the sPass value for this Get_AcceptanceGeneralListFromAsset_Status.
     * 
     * @param sPass
     */
    public void setSPass(String sPass) {
        this.sPass = sPass;
    }


    /**
     * Gets the sSOPHIEU value for this Get_AcceptanceGeneralListFromAsset_Status.
     * 
     * @return sSOPHIEU
     */
    public String getSSOPHIEU() {
        return sSOPHIEU;
    }


    /**
     * Sets the sSOPHIEU value for this Get_AcceptanceGeneralListFromAsset_Status.
     * 
     * @param sSOPHIEU
     */
    public void setSSOPHIEU(String sSOPHIEU) {
        this.sSOPHIEU = sSOPHIEU;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Get_AcceptanceGeneralListFromAsset_Status)) return false;
        Get_AcceptanceGeneralListFromAsset_Status other = (Get_AcceptanceGeneralListFromAsset_Status) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sUserID==null && other.getSUserID()==null) || 
             (this.sUserID!=null &&
              this.sUserID.equals(other.getSUserID()))) &&
            ((this.sPass==null && other.getSPass()==null) || 
             (this.sPass!=null &&
              this.sPass.equals(other.getSPass()))) &&
            ((this.sSOPHIEU==null && other.getSSOPHIEU()==null) || 
             (this.sSOPHIEU!=null &&
              this.sSOPHIEU.equals(other.getSSOPHIEU())));
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
        if (getSUserID() != null) {
            _hashCode += getSUserID().hashCode();
        }
        if (getSPass() != null) {
            _hashCode += getSPass().hashCode();
        }
        if (getSSOPHIEU() != null) {
            _hashCode += getSSOPHIEU().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Get_AcceptanceGeneralListFromAsset_Status.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">Get_AcceptanceGeneralListFromAsset_Status"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SUserID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sUserID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SPass");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sPass"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SSOPHIEU");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sSOPHIEU"));
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
