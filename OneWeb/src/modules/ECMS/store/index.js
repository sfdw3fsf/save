import {loaiTu} from './LoaiTu'
import {quanLyPrefixIPCuaBras} from './QuanLyPrefixIPCuaBras'
import {quyHoachVLAN} from './QuyHoachVLAN'
import {quanLyModule} from './QuanLyModule'
import {rack} from '../Rack/Rack'
import {traCuuThueBaoTheoDSSplitter} from '../TraCuuThueBaoTheoDSSplitter/TraCuuThueBaoTheoDSSplitter'
import {hknNoiTinh} from './HuongKetNoiNoiTinh'
import {hknBras} from './HuongKetNoiBras'
import {quanLyBras} from "./QuanLyBras"
import {hknInternetTT} from './HuongKetNoiInternetTT'
import {quanlythietbibts} from './quanlythietbibts'

export const ecms = {
  namespaced: true,
  modules: {
    loaiTu,
    quanLyPrefixIPCuaBras,
    quyHoachVLAN,
    quanLyModule,
    rack,
    traCuuThueBaoTheoDSSplitter,
    hknNoiTinh,
    hknBras,
    hknInternetTT,
    quanLyBras,
    quanlythietbibts
  }
}
