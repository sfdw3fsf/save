// khai báo 1 store
import { actions } from './actions'
import { getters } from './getters'
import { mutations } from './mutations'

const state = {
    //frmInvoiceConfiguration.cs
    //[UR1.2.006] - Tham số thông báo cước
    UpsPrint:1,
    Datapost:0,
    Barcode:0,
    QRcode:0,
    Pdf:0,
    PaperOffset:'',
    PBTotal:'',
    RunSQL:0,
    ShowOption:0,
    AdvOpion:0,
    InvNumber:'0',
    InvNumberOption:0,
    //
    kyhoadon:'',
    chuky:''   
}

const namespaced = true

export const admin = {
  namespaced,
  state,
  getters,
  actions,
  mutations
}
