// các hàm đọc các giá trị thuộc tính của đối tượng trong store
// UpsPrint:1,
//   Datapost:0,
//   Barcode:0,
//   QRcode:0,
//   Pdf:0,
//   PaperOffset:'',
//   PBTotal:'',
//   RunSQL:0,
//   ShowOption:0,
//   AdvOpion:0,
//   InvNumber:'',
//   InvNumberOption:0 
export const getters = {
  //frmInvoiceConfiguration.cs
  //[UR1.2.006] - Tham số thông báo cước
  UpsPrint: (state, getters, rootState) => {
    return state.UpsPrint
  },
  Datapost: (state, getters, rootState) => {
    return state.Datapost
  },
  Barcode: (state, getters, rootState) => {
    return state.Barcode
  },
  QRcode: (state, getters, rootState) => {
    return state.QRcode
  },
  Pdf: (state, getters, rootState) => {
    return state.Pdf
  },
  PaperOffset: (state, getters, rootState) => {
    return state.PaperOffset
  },
  PBTotal: (state, getters, rootState) => {
    return state.PBTotal
  },
  RunSQL: (state, getters, rootState) => {
    return state.RunSQL
  },
  ShowOption: (state, getters, rootState) => {
    return state.ShowOption
  },
  AdvOpion: (state, getters, rootState) => {
    return state.AdvOpion
  },
  InvNumber: (state, getters, rootState) => {
    return state.InvNumber
  },
  InvNumberOption: (state, getters, rootState) => {
    return state.InvNumberOption
  },
  //
  getKyhoadon: (state, getters, rootState)=>{
    return state.kyhoadon
  },
  getChuKy: (state, getters, rootState)=>{
    return state.chuky
  },
}
