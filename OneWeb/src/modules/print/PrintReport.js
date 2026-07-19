import RECEIPTTYPE from './RECEIPTTYPE'

export default class PrintReport {
  PaperSize = ''
  Landscape = true
  PaperHeight = 0
  PaperWidth = 0
  hoadon = 0
  type = 0
  data = null
  containerText = ''

  constructor (type, data) {
    this.type = type
    this.data = data
    switch (type) {
      case RECEIPTTYPE.InHoaDon_BNTTx4:
        this.containerText = this.renderContent()
        break
      case RECEIPTTYPE.InHoaDon_BNTTx2:
        this.containerText = this.renderContent()
        break
      case RECEIPTTYPE.InHoaDonVT01:
        this.containerText = this.renderContent()
        break
      case RECEIPTTYPE.InThongBaoNo:
        this.containerText = this.renderContent()
        break
      case RECEIPTTYPE.INHOADON_BD01:
        this.containerText = this.renderContent()
        break
      case RECEIPTTYPE.InPhieuThu:
        this.containerText = this.renderContent()
        break
      case RECEIPTTYPE.InPhieuNX:
        this.containerText = this.renderContent()
        break
    }
  }
  renderContent () {
    let el = ''
    this.data.forEach(
      (item) => {
        el += `<span style="white-space: pre;">${item.noidung}</span><br>`
      }
    )
    return el
  }
}
