// Khai bao store của module Thu Nợ
import { qltnCommon } from './commons'
import { tracuuhoadonchuyenkhoan } from './tracuuhoadonchuyenkhoan'
import { tracuutongnokhachhang } from './tracuutongnokhachhang'
import {tracuutinhhinhthuno} from './tracuutinhhinhthuno'

export const qltn = {
  modules: {
    qltnCommon,
    tracuuhoadonchuyenkhoan,
    tracuutongnokhachhang,
    tracuutinhhinhthuno
  }
}
