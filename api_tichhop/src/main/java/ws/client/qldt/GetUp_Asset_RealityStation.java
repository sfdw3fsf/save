/**
 * GetUp_Asset_RealityStation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.qldt;

public class GetUp_Asset_RealityStation  implements java.io.Serializable {
    private String sUserID;

    private String sPass;

    private String sSys;

    private String sProjectcode;

    private String sContractNumber;

    private GetUp_Asset_RealityStationListAssetRealityStation listAssetRealityStation;

    public GetUp_Asset_RealityStation() {
    }

    public GetUp_Asset_RealityStation(
           String sUserID,
           String sPass,
           String sSys,
           String sProjectcode,
           String sContractNumber,
           GetUp_Asset_RealityStationListAssetRealityStation listAssetRealityStation) {
           this.sUserID = sUserID;
           this.sPass = sPass;
           this.sSys = sSys;
           this.sProjectcode = sProjectcode;
           this.sContractNumber = sContractNumber;
           this.listAssetRealityStation = listAssetRealityStation;
    }


    /**
     * Gets the sUserID value for this GetUp_Asset_RealityStation.
     * 
     * @return sUserID
     */
    public String getSUserID() {
        return sUserID;
    }


    /**
     * Sets the sUserID value for this GetUp_Asset_RealityStation.
     * 
     * @param sUserID
     */
    public void setSUserID(String sUserID) {
        this.sUserID = sUserID;
    }


    /**
     * Gets the sPass value for this GetUp_Asset_RealityStation.
     * 
     * @return sPass
     */
    public String getSPass() {
        return sPass;
    }


    /**
     * Sets the sPass value for this GetUp_Asset_RealityStation.
     * 
     * @param sPass
     */
    public void setSPass(String sPass) {
        this.sPass = sPass;
    }


    /**
     * Gets the sSys value for this GetUp_Asset_RealityStation.
     * 
     * @return sSys
     */
    public String getSSys() {
        return sSys;
    }


    /**
     * Sets the sSys value for this GetUp_Asset_RealityStation.
     * 
     * @param sSys
     */
    public void setSSys(String sSys) {
        this.sSys = sSys;
    }


    /**
     * Gets the sProjectcode value for this GetUp_Asset_RealityStation.
     * 
     * @return sProjectcode
     */
    public String getSProjectcode() {
        return sProjectcode;
    }


    /**
     * Sets the sProjectcode value for this GetUp_Asset_RealityStation.
     * 
     * @param sProjectcode
     */
    public void setSProjectcode(String sProjectcode) {
        this.sProjectcode = sProjectcode;
    }


    /**
     * Gets the sContractNumber value for this GetUp_Asset_RealityStation.
     * 
     * @return sContractNumber
     */
    public String getSContractNumber() {
        return sContractNumber;
    }


    /**
     * Sets the sContractNumber value for this GetUp_Asset_RealityStation.
     * 
     * @param sContractNumber
     */
    public void setSContractNumber(String sContractNumber) {
        this.sContractNumber = sContractNumber;
    }


    /**
     * Gets the listAssetRealityStation value for this GetUp_Asset_RealityStation.
     * 
     * @return listAssetRealityStation
     */
    public GetUp_Asset_RealityStationListAssetRealityStation getListAssetRealityStation() {
        return listAssetRealityStation;
    }


    /**
     * Sets the listAssetRealityStation value for this GetUp_Asset_RealityStation.
     * 
     * @param listAssetRealityStation
     */
    public void setListAssetRealityStation(GetUp_Asset_RealityStationListAssetRealityStation listAssetRealityStation) {
        this.listAssetRealityStation = listAssetRealityStation;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GetUp_Asset_RealityStation)) return false;
        GetUp_Asset_RealityStation other = (GetUp_Asset_RealityStation) obj;
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
            ((this.sSys==null && other.getSSys()==null) || 
             (this.sSys!=null &&
              this.sSys.equals(other.getSSys()))) &&
            ((this.sProjectcode==null && other.getSProjectcode()==null) || 
             (this.sProjectcode!=null &&
              this.sProjectcode.equals(other.getSProjectcode()))) &&
            ((this.sContractNumber==null && other.getSContractNumber()==null) || 
             (this.sContractNumber!=null &&
              this.sContractNumber.equals(other.getSContractNumber()))) &&
            ((this.listAssetRealityStation==null && other.getListAssetRealityStation()==null) || 
             (this.listAssetRealityStation!=null &&
              this.listAssetRealityStation.equals(other.getListAssetRealityStation())));
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
        if (getSSys() != null) {
            _hashCode += getSSys().hashCode();
        }
        if (getSProjectcode() != null) {
            _hashCode += getSProjectcode().hashCode();
        }
        if (getSContractNumber() != null) {
            _hashCode += getSContractNumber().hashCode();
        }
        if (getListAssetRealityStation() != null) {
            _hashCode += getListAssetRealityStation().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetUp_Asset_RealityStation.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">GetUp_Asset_RealityStation"));
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
        elemField.setFieldName("SSys");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sSys"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SProjectcode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sProjectcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SContractNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "sContractNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listAssetRealityStation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "ListAssetRealityStation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", ">>GetUp_Asset_RealityStation>ListAssetRealityStation"));
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
