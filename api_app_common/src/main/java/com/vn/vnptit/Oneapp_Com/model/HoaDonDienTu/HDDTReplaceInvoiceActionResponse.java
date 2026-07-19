
package com.vn.vnptit.Oneapp_Com.model.HoaDonDienTu;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Danhnq
 *
 */
public class HDDTReplaceInvoiceActionResponse {
  @JsonProperty("ReplaceInvoiceActionResult")
  private String ReplaceInvoiceActionResult;

  public String getReplaceInvoiceActionResult() {
    return ReplaceInvoiceActionResult;
  }

  public void setReplaceInvoiceActionResult(String replaceInvoiceActionResult) {
    ReplaceInvoiceActionResult = replaceInvoiceActionResult;
  }

 
}
