package vn.vnptit.sapi.oneapp.models

class CREATE_QRCODE_REQ {
    String phanvung_id
    String amount
    String billNumber
    String purpose
    String expDate
    String qrCodeType
    String addInfo
    String merchantData
    String mcc
    String tipAndFee
    String countryCode
    String ccy
    String txnId
    String packageCode
    List paymentDetail
    String provinceCode
    String consumerId
}

class REFUND_TRANS_REQ {
    String phanvung_id
    String billNumber
    String transactionId
    String refundRequestId
    String refundType
    String amount
    String description
    String createDate
}