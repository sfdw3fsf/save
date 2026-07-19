// các hàm dùng để gọi API, gọi hàm khác trong mutations để thay đổi state của của đối tượng trong store
export const actions = {
  //frmInvoiceConfiguration.cs
  //[UR1.2.006] - Tham số thông báo cước
  setUpsPrint({ commit, state }, upsPrint) {
    commit('setUpsPrint', upsPrint)
  },
  setDatapost({ commit, state }, datapost) {
    commit('setDatapost', datapost)
  },
  setBarcode({ commit, state }, barcode) {
    commit('setBarcode', barcode)
  },
  setQRcode({ commit, state }, QRcode) {
    commit('setQRcode', QRcode)
  },
  setPdf({ commit, state }, Pdf) {
    commit('setPdf', Pdf)
  },
  setPaperOffset({ commit, state }, paperOffset) {
    commit('setPaperOffset', paperOffset)
  },
  setPBTotal({ commit, state }, PBTotal) {
    commit('setPBTotal', PBTotal)
  },
  setRunSQL({ commit, state }, RunSQL) {
    commit('setRunSQL', RunSQL)
  },
  setShowOption({ commit, state }, showOption) {
    commit('setShowOption', showOption)
  },
  setAdvOpion({ commit, state }, advOpion) {
    commit('setAdvOpion', advOpion)
  },
  setInvNumber({ commit, state }, invNumber) {
    commit('setInvNumber', invNumber)
  },
  setInvNumberOption({ commit, state }, invNumberOption) {
    commit('setInvNumberOption', invNumberOption)
  },
  //
  setKyhoadon({ commit, state }, kyhoadon){
    commit('setKyhoadon', kyhoadon)
  },
  setChuKy({ commit, state }, chuky){
    commit('setChuKy', chuky)
  }
}
