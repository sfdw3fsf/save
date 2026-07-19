/* eslint-disable */
import { BanDoMangCap } from './../BanDoMangCap/store'
import { BanDoRanh } from './../BanDoRanh/store'
import { danhsachCotCap } from './../DanhSachCotCap/store'
import { daunoiThuebao } from './../DauNoiThueBao/store'
import { GiamSatMangNgoaiVi } from './../GiamSatMangNgoaiVi/store'
import { QuanLyMangTruyenDan } from './../QuanLyMangTruyenDan/store'
import { QuanLyRanh } from './../QuanLyRanh/store'
import { capvattuptmtt } from './CapVatTuPTMTT'
import { quanLyTaiSan } from './QuanLyTaiSan'
import { tuyenCot } from './TuyenCot'

export const cabman = {
  namespaced: true,
  modules: {
    tuyenCot,
    quanLyTaiSan,
    danhsachCotCap,
    daunoiThuebao,
    BanDoMangCap,
    BanDoRanh,
    QuanLyMangTruyenDan,
    capvattuptmtt,
    GiamSatMangNgoaiVi,
    QuanLyRanh
  }
}
