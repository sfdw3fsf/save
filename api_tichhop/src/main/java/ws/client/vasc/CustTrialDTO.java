/**
 * CustTrialDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.vasc;

public class CustTrialDTO  implements java.io.Serializable {
    private String USERNAME;

    private String METHOD_FLG;

    private String NOMONTH;

    private String TRIAL_CODE;

    private String TRIAL_GROUP;

    private int TRIAL_NOMONTH;

    public CustTrialDTO() {
    }

    public CustTrialDTO(
           String USERNAME,
           String METHOD_FLG,
           String NOMONTH,
           String TRIAL_CODE,
           String TRIAL_GROUP,
           int TRIAL_NOMONTH) {
           this.USERNAME = USERNAME;
           this.METHOD_FLG = METHOD_FLG;
           this.NOMONTH = NOMONTH;
           this.TRIAL_CODE = TRIAL_CODE;
           this.TRIAL_GROUP = TRIAL_GROUP;
           this.TRIAL_NOMONTH = TRIAL_NOMONTH;
    }


    /**
     * Gets the USERNAME value for this CustTrialDTO.
     * 
     * @return USERNAME
     */
    public String getUSERNAME() {
        return USERNAME;
    }


    /**
     * Sets the USERNAME value for this CustTrialDTO.
     * 
     * @param USERNAME
     */
    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }


    /**
     * Gets the METHOD_FLG value for this CustTrialDTO.
     * 
     * @return METHOD_FLG
     */
    public String getMETHOD_FLG() {
        return METHOD_FLG;
    }


    /**
     * Sets the METHOD_FLG value for this CustTrialDTO.
     * 
     * @param METHOD_FLG
     */
    public void setMETHOD_FLG(String METHOD_FLG) {
        this.METHOD_FLG = METHOD_FLG;
    }


    /**
     * Gets the NOMONTH value for this CustTrialDTO.
     * 
     * @return NOMONTH
     */
    public String getNOMONTH() {
        return NOMONTH;
    }


    /**
     * Sets the NOMONTH value for this CustTrialDTO.
     * 
     * @param NOMONTH
     */
    public void setNOMONTH(String NOMONTH) {
        this.NOMONTH = NOMONTH;
    }


    /**
     * Gets the TRIAL_CODE value for this CustTrialDTO.
     * 
     * @return TRIAL_CODE
     */
    public String getTRIAL_CODE() {
        return TRIAL_CODE;
    }


    /**
     * Sets the TRIAL_CODE value for this CustTrialDTO.
     * 
     * @param TRIAL_CODE
     */
    public void setTRIAL_CODE(String TRIAL_CODE) {
        this.TRIAL_CODE = TRIAL_CODE;
    }


    /**
     * Gets the TRIAL_GROUP value for this CustTrialDTO.
     * 
     * @return TRIAL_GROUP
     */
    public String getTRIAL_GROUP() {
        return TRIAL_GROUP;
    }


    /**
     * Sets the TRIAL_GROUP value for this CustTrialDTO.
     * 
     * @param TRIAL_GROUP
     */
    public void setTRIAL_GROUP(String TRIAL_GROUP) {
        this.TRIAL_GROUP = TRIAL_GROUP;
    }


    /**
     * Gets the TRIAL_NOMONTH value for this CustTrialDTO.
     * 
     * @return TRIAL_NOMONTH
     */
    public int getTRIAL_NOMONTH() {
        return TRIAL_NOMONTH;
    }


    /**
     * Sets the TRIAL_NOMONTH value for this CustTrialDTO.
     * 
     * @param TRIAL_NOMONTH
     */
    public void setTRIAL_NOMONTH(int TRIAL_NOMONTH) {
        this.TRIAL_NOMONTH = TRIAL_NOMONTH;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof CustTrialDTO)) return false;
        CustTrialDTO other = (CustTrialDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.USERNAME==null && other.getUSERNAME()==null) || 
             (this.USERNAME!=null &&
              this.USERNAME.equals(other.getUSERNAME()))) &&
            ((this.METHOD_FLG==null && other.getMETHOD_FLG()==null) || 
             (this.METHOD_FLG!=null &&
              this.METHOD_FLG.equals(other.getMETHOD_FLG()))) &&
            ((this.NOMONTH==null && other.getNOMONTH()==null) || 
             (this.NOMONTH!=null &&
              this.NOMONTH.equals(other.getNOMONTH()))) &&
            ((this.TRIAL_CODE==null && other.getTRIAL_CODE()==null) || 
             (this.TRIAL_CODE!=null &&
              this.TRIAL_CODE.equals(other.getTRIAL_CODE()))) &&
            ((this.TRIAL_GROUP==null && other.getTRIAL_GROUP()==null) || 
             (this.TRIAL_GROUP!=null &&
              this.TRIAL_GROUP.equals(other.getTRIAL_GROUP()))) &&
            this.TRIAL_NOMONTH == other.getTRIAL_NOMONTH();
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
        if (getUSERNAME() != null) {
            _hashCode += getUSERNAME().hashCode();
        }
        if (getMETHOD_FLG() != null) {
            _hashCode += getMETHOD_FLG().hashCode();
        }
        if (getNOMONTH() != null) {
            _hashCode += getNOMONTH().hashCode();
        }
        if (getTRIAL_CODE() != null) {
            _hashCode += getTRIAL_CODE().hashCode();
        }
        if (getTRIAL_GROUP() != null) {
            _hashCode += getTRIAL_GROUP().hashCode();
        }
        _hashCode += getTRIAL_NOMONTH();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CustTrialDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/", "CustTrialDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("USERNAME");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "USERNAME"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("METHOD_FLG");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "METHOD_FLG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NOMONTH");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "NOMONTH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TRIAL_CODE");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "TRIAL_CODE"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TRIAL_GROUP");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "TRIAL_GROUP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TRIAL_NOMONTH");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/", "TRIAL_NOMONTH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
