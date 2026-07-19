/**
 * GrowReportModel.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.application;

public class GrowReportModel  implements java.io.Serializable {
    private String areaCode;

    private long nvh;

    private long nvm;

    private long nvtt;

    private long parentId;

    private long sum1;

    private long sum2;

    private long sumall;

    private long tbh;

    private long tbm;

    private long tbtt;

    private long tkht;

    private String userId;

    public GrowReportModel() {
    }

    public GrowReportModel(
           String areaCode,
           long nvh,
           long nvm,
           long nvtt,
           long parentId,
           long sum1,
           long sum2,
           long sumall,
           long tbh,
           long tbm,
           long tbtt,
           long tkht,
           String userId) {
           this.areaCode = areaCode;
           this.nvh = nvh;
           this.nvm = nvm;
           this.nvtt = nvtt;
           this.parentId = parentId;
           this.sum1 = sum1;
           this.sum2 = sum2;
           this.sumall = sumall;
           this.tbh = tbh;
           this.tbm = tbm;
           this.tbtt = tbtt;
           this.tkht = tkht;
           this.userId = userId;
    }


    /**
     * Gets the areaCode value for this GrowReportModel.
     * 
     * @return areaCode
     */
    public String getAreaCode() {
        return areaCode;
    }


    /**
     * Sets the areaCode value for this GrowReportModel.
     * 
     * @param areaCode
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }


    /**
     * Gets the nvh value for this GrowReportModel.
     * 
     * @return nvh
     */
    public long getNvh() {
        return nvh;
    }


    /**
     * Sets the nvh value for this GrowReportModel.
     * 
     * @param nvh
     */
    public void setNvh(long nvh) {
        this.nvh = nvh;
    }


    /**
     * Gets the nvm value for this GrowReportModel.
     * 
     * @return nvm
     */
    public long getNvm() {
        return nvm;
    }


    /**
     * Sets the nvm value for this GrowReportModel.
     * 
     * @param nvm
     */
    public void setNvm(long nvm) {
        this.nvm = nvm;
    }


    /**
     * Gets the nvtt value for this GrowReportModel.
     * 
     * @return nvtt
     */
    public long getNvtt() {
        return nvtt;
    }


    /**
     * Sets the nvtt value for this GrowReportModel.
     * 
     * @param nvtt
     */
    public void setNvtt(long nvtt) {
        this.nvtt = nvtt;
    }


    /**
     * Gets the parentId value for this GrowReportModel.
     * 
     * @return parentId
     */
    public long getParentId() {
        return parentId;
    }


    /**
     * Sets the parentId value for this GrowReportModel.
     * 
     * @param parentId
     */
    public void setParentId(long parentId) {
        this.parentId = parentId;
    }


    /**
     * Gets the sum1 value for this GrowReportModel.
     * 
     * @return sum1
     */
    public long getSum1() {
        return sum1;
    }


    /**
     * Sets the sum1 value for this GrowReportModel.
     * 
     * @param sum1
     */
    public void setSum1(long sum1) {
        this.sum1 = sum1;
    }


    /**
     * Gets the sum2 value for this GrowReportModel.
     * 
     * @return sum2
     */
    public long getSum2() {
        return sum2;
    }


    /**
     * Sets the sum2 value for this GrowReportModel.
     * 
     * @param sum2
     */
    public void setSum2(long sum2) {
        this.sum2 = sum2;
    }


    /**
     * Gets the sumall value for this GrowReportModel.
     * 
     * @return sumall
     */
    public long getSumall() {
        return sumall;
    }


    /**
     * Sets the sumall value for this GrowReportModel.
     * 
     * @param sumall
     */
    public void setSumall(long sumall) {
        this.sumall = sumall;
    }


    /**
     * Gets the tbh value for this GrowReportModel.
     * 
     * @return tbh
     */
    public long getTbh() {
        return tbh;
    }


    /**
     * Sets the tbh value for this GrowReportModel.
     * 
     * @param tbh
     */
    public void setTbh(long tbh) {
        this.tbh = tbh;
    }


    /**
     * Gets the tbm value for this GrowReportModel.
     * 
     * @return tbm
     */
    public long getTbm() {
        return tbm;
    }


    /**
     * Sets the tbm value for this GrowReportModel.
     * 
     * @param tbm
     */
    public void setTbm(long tbm) {
        this.tbm = tbm;
    }


    /**
     * Gets the tbtt value for this GrowReportModel.
     * 
     * @return tbtt
     */
    public long getTbtt() {
        return tbtt;
    }


    /**
     * Sets the tbtt value for this GrowReportModel.
     * 
     * @param tbtt
     */
    public void setTbtt(long tbtt) {
        this.tbtt = tbtt;
    }


    /**
     * Gets the tkht value for this GrowReportModel.
     * 
     * @return tkht
     */
    public long getTkht() {
        return tkht;
    }


    /**
     * Sets the tkht value for this GrowReportModel.
     * 
     * @param tkht
     */
    public void setTkht(long tkht) {
        this.tkht = tkht;
    }


    /**
     * Gets the userId value for this GrowReportModel.
     * 
     * @return userId
     */
    public String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this GrowReportModel.
     * 
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GrowReportModel)) return false;
        GrowReportModel other = (GrowReportModel) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.areaCode==null && other.getAreaCode()==null) || 
             (this.areaCode!=null &&
              this.areaCode.equals(other.getAreaCode()))) &&
            this.nvh == other.getNvh() &&
            this.nvm == other.getNvm() &&
            this.nvtt == other.getNvtt() &&
            this.parentId == other.getParentId() &&
            this.sum1 == other.getSum1() &&
            this.sum2 == other.getSum2() &&
            this.sumall == other.getSumall() &&
            this.tbh == other.getTbh() &&
            this.tbm == other.getTbm() &&
            this.tbtt == other.getTbtt() &&
            this.tkht == other.getTkht() &&
            ((this.userId==null && other.getUserId()==null) || 
             (this.userId!=null &&
              this.userId.equals(other.getUserId())));
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
        if (getAreaCode() != null) {
            _hashCode += getAreaCode().hashCode();
        }
        _hashCode += new Long(getNvh()).hashCode();
        _hashCode += new Long(getNvm()).hashCode();
        _hashCode += new Long(getNvtt()).hashCode();
        _hashCode += new Long(getParentId()).hashCode();
        _hashCode += new Long(getSum1()).hashCode();
        _hashCode += new Long(getSum2()).hashCode();
        _hashCode += new Long(getSumall()).hashCode();
        _hashCode += new Long(getTbh()).hashCode();
        _hashCode += new Long(getTbm()).hashCode();
        _hashCode += new Long(getTbtt()).hashCode();
        _hashCode += new Long(getTkht()).hashCode();
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GrowReportModel.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:report.model.hyframe.hyperlogy", "GrowReportModel"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("areaCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "areaCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nvh");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nvh"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nvm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nvm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nvtt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nvtt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("parentId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "parentId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sum1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sum1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sum2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sum2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sumall");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sumall"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tbh");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tbh"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tbm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tbm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tbtt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tbtt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tkht");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tkht"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
