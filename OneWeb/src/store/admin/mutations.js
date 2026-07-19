// các hàm xử lý các thuộc tính của đối tượng trong store 
export const mutations = {
  //frmInvoiceConfiguration.cs
  //[UR1.2.006] - Tham số thông báo cước
  setUpsPrint(state, upsPrint) {
    state.UpsPrint = upsPrint
  },
  setDatapost(state, datapost) {
    state.Datapost = datapost
  },
  setBarcode(state, barcode) {
    state.Barcode = barcode
  },
  setQRcode(state, QRcode) {
    state.QRcode = QRcode
  },
  setPdf(state, Pdf) {
    state.Pdf = Pdf
  },
  setPaperOffset(state, paperOffset) {
    state.PaperOffset = paperOffset
  },
  setPBTotal(state, PBTotal) {
    state.PBTotal = PBTotal
  },
  setRunSQL(state, RunSQL) {
    state.RunSQL = RunSQL
  },
  setShowOption(state, showOption) {
    state.ShowOption = showOption
  },
  setAdvOpion(state, advOpion) {
    state.AdvOpion = advOpion
  },
  setInvNumber(state, invNumber) {
    state.InvNumber = invNumber
  },
  setInvNumberOption(state, invNumberOption) {
    state.InvNumberOption = invNumberOption
  },
  //
  setKyhoadon(state, kyhoadon){
    state.kyhoadon = kyhoadon
  },
  setChuKy(state, chuky){
    state.chuky = chuky
  }
}
