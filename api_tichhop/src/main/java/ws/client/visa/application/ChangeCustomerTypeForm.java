/**
 * ChangeCustomerTypeForm.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client.visa.application;

public class ChangeCustomerTypeForm  extends ActionForm  implements java.io.Serializable {
    private String accountName;

    private int cusType;

    private String note;

    private String userId;

    public ChangeCustomerTypeForm() {
    }

    public ChangeCustomerTypeForm(
           Object multipartRequestHandler,
           Object servlet,
           Object servletWrapper,
           String accountName,
           int cusType,
           String note,
           String userId) {
        super(
            multipartRequestHandler,
            servlet,
            servletWrapper);
        this.accountName = accountName;
        this.cusType = cusType;
        this.note = note;
        this.userId = userId;
    }


    /**
     * Gets the accountName value for this ChangeCustomerTypeForm.
     * 
     * @return accountName
     */
    public String getAccountName() {
        return accountName;
    }


    /**
     * Sets the accountName value for this ChangeCustomerTypeForm.
     * 
     * @param accountName
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }


    /**
     * Gets the cusType value for this ChangeCustomerTypeForm.
     * 
     * @return cusType
     */
    public int getCusType() {
        return cusType;
    }


    /**
     * Sets the cusType value for this ChangeCustomerTypeForm.
     * 
     * @param cusType
     */
    public void setCusType(int cusType) {
        this.cusType = cusType;
    }


    /**
     * Gets the note value for this ChangeCustomerTypeForm.
     * 
     * @return note
     */
    public String getNote() {
        return note;
    }


    /**
     * Sets the note value for this ChangeCustomerTypeForm.
     * 
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }


    /**
     * Gets the userId value for this ChangeCustomerTypeForm.
     * 
     * @return userId
     */
    public String getUserId() {
        return userId;
    }


    /**
     * Sets the userId value for this ChangeCustomerTypeForm.
     * 
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof ChangeCustomerTypeForm)) return false;
        ChangeCustomerTypeForm other = (ChangeCustomerTypeForm) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.accountName==null && other.getAccountName()==null) || 
             (this.accountName!=null &&
              this.accountName.equals(other.getAccountName()))) &&
            this.cusType == other.getCusType() &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
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
        int _hashCode = super.hashCode();
        if (getAccountName() != null) {
            _hashCode += getAccountName().hashCode();
        }
        _hashCode += getCusType();
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getUserId() != null) {
            _hashCode += getUserId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ChangeCustomerTypeForm.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://change.formbean.webapp.service.hyframe.hyperlogy", "ChangeCustomerTypeForm"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accountName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cusType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cusType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("note");
        elemField.setXmlName(new javax.xml.namespace.QName("", "note"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
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
