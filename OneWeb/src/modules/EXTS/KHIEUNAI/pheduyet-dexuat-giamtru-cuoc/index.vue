<template src="./index.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import API from '../api/DeXuatGiamTruCuocAPI'
import frmHoSoKhieuNai from '@/modules/complaint/SearchComplainDetail/popups/frmHoSoKhieuNai.vue'
import frmTraCuuTBKhieuNai from '@/modules/complaint/SearchComplainDetail/popups/frmTraCuuTBKhieuNai.vue'
import GiaoPhieuGTC from '@/modules/EXTS/KHIEUNAI/giaophieu-dexuat-gtc/index.vue'
import moment from 'moment'
import { data } from 'vue-barcode'
export default {
  name: 'PheDuyetDeXuatGiamTruCuocKN',
  data() {
    return {
      loaiHinh: {
        dataSource: [],
        ID: null,
        text: ''
      },
      cboDVGiao: [],
      cboYKien: [],
      ykien_pheduyet_id: null,
      xuLyDuLieu: {
        dataSource: [
          {
            id: 1,
            text: 'Truy thu'
          },
          {
            id: 2,
            text: 'Giảm trừ'
          },
          {
            id: 3,
            text: 'Bù trừ'
          }
        ],
        ID: null,
        text: ''
      },
      dsDichVuVT: [],
      ghichu_TD: '',
      maKN: '',
      khieuNaiID: null,
      phieuKNID: null,
      chuDeKN: '',
      noiDungKN: '',
      ngayKN: null,
      maKhachHang: '',
      maThueBao: '',
      tenKhachHang: '',
      maThanhToan: '',
      maHopDong: '',
      ngayLapHD: null,
      tenThanhToan: '',
      maSoThue: '',
      giaTriHD: 0,
      soHopDong: '',
      dsTienTrinh: [],
      dsThongTinGiamTru: [],
      dsPhieuMoi: [],
      dsPhieuDaGui: [],
      ketQuaXacMinh: '',
      TTHopDong: '',
      TTHoaDon: '',
      TTCuoc: '',
      noiDungDeXuat: '',
      Enabled: {
        NhapMoi: true
      },
      lyDoTra: '',
      PhieuMoiSelected: {},
      ChenNo: { ma_tt: null, kycuoc: null, ma_tb: null },
      dtLienHe: null,
      nguoiKN: null,
      ghiChu_KN: null,
      tenThueBao: '',
      NguoiGiao: '',
      ngayGiao: null,
      ghiChu_pheDuyet: null,
      showTabs: {
        XM: true,
        GP: true
      },
      dsKetQua: [],
      xacMinhKhac: '',
      optXuLy: '1',
      optXuLy_DG: '1',
      activetab: 1,
      paramsPhieuMoi: {
        TTKN_ID: 17,
        HUONGGIAO_ID: 0,
        TTPH_ID: 1,
        DA_GIAO: 0
      },
      paramsDaGui: {
        TTKN_ID: 18,
        HUONGGIAO_ID: 0,
        TTPH_ID: 1,
        DA_GIAO: 1
      },
      buttons: {
        btnSave: {
          enabled: false
        },
        btnEdit: {
          enabled: true
        },
        btnCancel: {
          enabled: true
        },
        btnGiaoPhieu: {
          enabled: true
        },
        btnHoSo: {
          enabled: true
        }
      },
      cboChucNang: [],
      nguoiXuLy: null,
      ngayXuLy: null,
      ykien_TD: null,
      dichVuVTID: 0,
      donviID: 0,
      chucNangID: null
    }
  },
  components: {
    frmHoSoKhieuNai,
    frmTraCuuTBKhieuNai,
    GiaoPhieuGTC
  },
  computed: {
    grdItemsChuaGiao: function() {
      return this.$refs.grdItemsChuaGiao
    },
    grdItemsDaGiao: function() {
      return this.$refs.grdItemsDaGiao
    }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      // Load danh sách dịch vụ
      this.dsDichVuVT = await this.getDsDichVu()

      // Khởi tạo danh sách ý kiến
      this.cboYKien = [
        { id: 1, text: 'Đồng ý' },
        { id: 2, text: 'Không đồng ý' }
      ]

      // Khởi tạo danh sách chức năng theo tab
      this.cboChucNang =
        this.activetab === 1
          ? [
              { id: 18, text: 'Thẩm định phiếu đề xuất giảm trừ cước' },
              { id: 19, text: 'Phê duyệt kết quả thẩm định phiếu đề xuất giảm trừ cước' }
            ]
          : [
              { id: 17, text: 'Phê duyệt giảm trừ cước' },
              { id: 18, text: 'Thẩm định phiếu đề xuất giảm trừ cước' },
              { id: 19, text: 'Phê duyệt kết quả thẩm định phiếu đề xuất giảm trừ cước' }
            ]
      this.LayDuLieu()
    } finally {
      this.$root.showLoading(false)
    }
  },
  // mounted: async function() {
  //   let ma_kn = this.$route.query.MaKN != null ? this.$route.query.MaKN : ''
  //   this.dieuChinhHD.dataSource = await this.getDSLoaiPAGC()
  //   this.xuLyDuLieu.dataSource = await this.getDSKieuPAGC()
  //   // console.log(ma_kn, ' = ma_kn')

  //   if (ma_kn != '') {
  //     this.maKN = ma_kn
  //     this.maKN_keyPress()
  //   } else {
  //     // this.paramsPhieuMoi.TTPH_ID = Number(this.optXuLy)
  //     // this.paramsDaGui.TTPH_ID = Number(this.optXuLy)
  //     // this.dsPhieuMoi = await this.getDSKhieuNaiTheoTrangThai(this.paramsPhieuMoi)
  //     // this.dsPhieuDaGui = await this.getDSKhieuNaiTheoTrangThai(this.paramsDaGui)
  //   }
  // },

  methods: {
    async GhiLai() {
      try {
        this.loading(true)
        // if (this.ykien_pheduyet_id == null) {
        //   this.$toast.error('Bạn chưa chọn ý kiến phê duyệt!')
        //   return
        // }
        if (this.ghiChu_pheDuyet == null || this.ghiChu_pheDuyet.trim() == '') {
          this.$toast.error('Bạn chưa nhập ghi chú phê duyệt!')
          return
        }
        let ketQua = await this.ghiLaiPhieuGiamTruCuoc({
          PHIEUKN_ID: this.phieuKNID,
          NOIDUNG_PHEDUYET: this.ghiChu_pheDuyet
        })
        if (ketQua == 'BSS-00000000') {
          this.$toast.success('Lưu thông tin thành công')
          await this.LayDuLieu()
        } else this.$toast.error(ketQua.data.message_detail)
      } catch (e) {
        this.$toast.error(e.data.message_detail)
      } finally {
        this.loading(false)
      }
    },
    async CapNhat() {
      this.buttons.btnEdit.enabled = false
      this.buttons.btnSave.enabled = true
    },
    async TraCuuKhieuNai() {
      try {
        this.loading(true)

        // this.$refs.frmHoSoKhieuNai.khieunai_id = this.phieuKNID;
        this.$refs.frmTraCuuTBKhieuNai.frmTraCuuTBKhieuNai(0, 0, 0, 0, '0', 1, 0, 0)
        this.$refs.frmTraCuuTBKhieuNai.show()
        this.$refs.maKN.focus()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async HoSo() {
      try {
        this.loading(true)
        if (this.khieuNaiID === null) return
        this.$refs.frmHoSoKhieuNai.khieunai_id = this.khieuNaiID
        this.$refs.frmHoSoKhieuNai.show()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async Huy() {
      this.Enabled.NhapMoi = true
      this.clearAll()
      await this.LayDuLieu()
    },
    async maKN_keyPress() {
      // await this.LayKetQuaXacMinh({
      //   khieuNaiID: 10411,
      //   phieuKNID: 1,
      // })
      await this.getThongTinTheoMaKN(this.maKN)
    },
    ChangedPhieu: async function(item) {
      this.clearAll()
      if (item !== null && item !== undefined) {
        this.PhieuMoiSelected = item
        this.phieuKNID = item.PHIEUKN_ID
        this.maKN = item.MA_KN
        this.ngayGiao = item.NGAYGIAO
        this.NguoiGiao = item.NGUOI_GIAO
        this.donviID = item.DONVI_GIAO
        this.buttons.btnEdit.enabled = true
        this.buttons.btnSave.enabled = false
      }
      await this.getThongTinTheoMaKN(item.MA_KN)
      await this.LayKetQuaXacMinh({
        khieuNaiID: item.KHIEUNAI_ID,
        phieuKNID: item.PHIEUKN_ID
      })
      this.ketQuaXacMinh = item.ND_XACMINH
      this.noiDungDeXuat = item.ND_GIAO
    },
    async LayDuLieu() {
      try {
        this.$root.showLoading(true)
        this.paramsPhieuMoi.TTPH_ID = Number(this.optXuLy)
        this.paramsDaGui.TTPH_ID = Number(this.optXuLy_DG)
        this.dsPhieuMoi = await this.getDSKhieuNaiTheoTrangThai(this.paramsPhieuMoi)
        if (this.dsPhieuMoi.length == 0) {
          this.clearAll()
        }
        this.dsPhieuDaGui = await this.getDSKhieuNaiTheoTrangThai(this.paramsDaGui)
      } catch (e) {
        this.$toast.error(e.data.message_detail)
      } finally {
        this.$root.showLoading(false)
      }
    },
    f_StringToDateTime(value, format = 'DD/MM/YYYY HH:mm:ss') {
      return moment(value, format).toDate()
    },
    clearKetQuaXacMinh() {
      this.ketQuaXacMinh = ''
      this.TTHopDong = ''
      this.TTHoaDon = ''
      this.TTCuoc = ''
      this.noiDungDeXuat = ''
    },
    clearThongTinKhieuNai() {
      this.loaiHinh.text = ''
      this.chuDeKN = ''
      this.noiDungKN = ''
      this.ngayKN = null
    },
    clearThongTinKhachHang() {
      this.maKhachHang = ''
      this.maThueBao = ''
      this.tenKhachHang = ''
      this.maThanhToan = ''
      this.maHopDong = ''
      this.ngayLapHD = null
      this.tenThanhToan = ''
      this.maSoThue = ''
    },
    clearThongTinPheDuyet() {
      this.ykien_pheduyet_id = null
      this.ghiChu_pheDuyet = ''
    },
    clearAll() {
      this.clearKetQuaXacMinh()
      this.clearThongTinKhieuNai()
      this.clearThongTinKhachHang()
      this.clearThongTinPheDuyet()
      // this.dsTienTrinh = []
      // this.dsThongTinGiamTru = []
      // this.dsPhieuMoi = []
      // this.dsPhieuDaGui = []
    },
    async getThongTinTheoMaKN(maKN) {
      try {
        this.loading(true)
        // this.clearAll()
        let dataKN = await this.getDSKhieuNaiTheoMaKN(maKN)
        if (dataKN.length == 0) {
          this.$toast.error('Không tìm thấy mã khiếu nại!')
          this.clearThongTinKhieuNai()
          return
        }
        this.khieuNaiID = dataKN[0].KHIEUNAI_ID
        this.dichVuVTID = dataKN[0].DICHVUVT_ID
        this.loaiHinh.text = dataKN[0].LOAIHINH_TB
        this.chuDeKN = dataKN[0].TENCHUDE
        this.noiDungKN = dataKN[0].NOI_DUNG
        this.ngayKN = this.f_StringToDateTime(dataKN[0].NGAY_TN, 'YYYY-MM-DD HH:mm:ss')
        this.nguoiKN = dataKN[0].NGUOI_KN
        this.dtLienHe = dataKN[0].DIENTHOAI_LH
        this.ghiChu_KN = dataKN[0].GHICHU_KN
        this.noiDungDeXuat = dataKN[0].ND_XULY
        if (this.ykien_pheduyet_id == 1 && this.ghiChu_pheDuyet != null && this.ghiChu_pheDuyet.trim() != '') {
          this.buttons.btnCancel.enabled = true
        } else {
          this.buttons.btnCancel.enabled = false
        }
        this.getThongTinKhachHang(dataKN[0].MA_TB)
        // this.getTienTrinhKhieuNai(dataKN[0].KHIEUNAI_ID)
        this.getGiamCuocKhieuNai(dataKN[0].KHIEUNAI_ID)
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },

    async getThongTinKhachHang(pMaTB) {
      let data = await this.getThongTinKhachHangTHeoMaTB(pMaTB)
      if (data.length == 0) {
        this.$toast.error('Không tìm dữ liệu khách hàng!')
        return
      }

      this.maKhachHang = data[0].MA_KH
      this.maThueBao = data[0].MA_TB
      this.tenKhachHang = data[0].TEN_KH
      this.maThanhToan = data[0].MA_TT
      // this.maHopDong = data[0].MA_HD
      this.ngayLapHD = data[0].NGAYLAP_HD
      this.tenThanhToan = data[0].TEN_TT
      this.maSoThue = data[0].MST_TT
      this.tenThueBao = data[0].TEN_TB
      this.giaTriHD = data[0].CUOC_TB
      this.soHopDong = data[0].MA_HD
    },
    getDsDichVu: async function() {
      const rs = await API.getDSDichVuVT(this.axios)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },
    getDSKhieuNaiTheoMaKN: async function(maKN) {
      const rs = await API.getDSKhieuNaiTheoMaKN(this.axios, maKN)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },

    async getThongTinKhachHangTHeoMaTB(pMaTB) {
      const rs = await API.getDanhBa(this.axios, pMaTB)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },

    getDsLoaiHinhTB: async function(dichvuvt_id) {
      const rs = await API.getDSLoaiHinhTB(this.axios, dichvuvt_id)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },

    async getGiamCuocKhieuNai(khieuNaiID) {
      this.dsThongTinGiamTru = []
      let data = await API.getGiamCuocKhieuNai(this.axios, khieuNaiID)
      if (data.data.data.length != 0) {
        this.dsThongTinGiamTru = data.data.data
      }
    },
    async getDSKhieuNaiTheoTrangThai(params) {
      const data = await API.getDSKhieuNaiTheoTrangThaiv2(this.axios, params)
      let rs = []
      console.log(rs, ' = rs')

      if (data.data.data.length > 0) {
        rs = data.data.data
      }
      return rs
    },
    async giaoPhieuGiamTruCuoc(datagd) {
      const data = await API.giaoPhieuGiamTruCuoc(this.axios, datagd)
      let rs = []
      if (data.data.data.length != 0) {
        rs = data.data.data
      }
      return rs
    },

    async ghiLaiPhieuGiamTruCuoc(data) {
      const rs = await API.ghilai_pheduyet(this.axios, data)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      return result
    },

    async LayKetQuaXacMinh(data) {
      this.clearKetQuaXacMinh()
      const rs = await API.getThongTinKetQua(this.axios, data)
      let result = []
      if (rs.data.data.length != 0) result = rs.data.data
      console.log(result, ' = result')
      if (result.length > 0) {
        this.ketQuaXacMinh = result[0].KETQUA
        // this.TTHopDong = result[0].TT_HOPDONG
        // this.TTHoaDon = result[0].TT_HOADON
        // this.TTCuoc = result[0].TT_CUOC
        // this.dieuChinhHD.ID = parseInt(result[0].DCHD_ID)
        // this.xuLyDuLieu.ID = parseInt(result[0].XLDL_ID)
      }
    },
    // getColumnTemplateChucNang(parent) {
    //   return function() {
    //     return {
    //       template: {
    //         template: `
    //                                 <div style="text-align: center">
    //                                   <button class="btn btn-main pad4 lh14"  @click="parent.onEdit(data)" :disabled="parent.activetab === 2">
    //                                     <span class="icon fa fa-pencil"></span>
    //                                   </button>
    //                                   <button class="btn btn-main pad4 lh14"  @click="parent.onDelete(data)" :disabled="parent.activetab === 2">
    //                                     <span class="icon one-trash"></span>
    //                                   </button>
    //                                 </div>
    //                             `,
    //         data() {
    //           return {
    //             parent: parent,
    //             data: {}
    //           }
    //         }
    //       }
    //     }
    //   }
    // },
    onTuChoi: async function() {
      try {
        this.$root.showLoading(true)
        if (this.ghiChu_pheDuyet == null || this.ghiChu_pheDuyet == undefined || this.ghiChu_pheDuyet == '') {
          this.$toast.error('Bạn Chưa nhập nội dung ghi chú')
          return
        }

        const data = {
          khieuNaiID: this.khieuNaiID,
          phieuKNID: this.phieuKNID,
          trangTKNID: parseInt(this.pTTKNID),
          noiDung: this.ghiChu_pheDuyet
        }
        console.log(data)
        const rs = await API.postTraPhieuGiamCuocKhieuNai(this.axios, data)
        if (rs.data.error_code == 'BSS-00000000') {
          this.$toast.success('Từ chối ' + this.uiForm.textButton + ' thành công')
          await this.LayDuLieu()
        } else this.$toast.error(rs.data.message_detail)
      } catch (e) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    onclickTab: function(currentActivetab) {
      try {
        this.loading(true)
        this.activetab = currentActivetab
        this.optXuLy = '1'
        this.optXuLy_DG = '1'
        if (this.activetab == 1) {
          let items = this.grdItemsChuaGiao.getSelectedRecords()
          if (items.length > 0) {
            this.PhieuMoiSelected = items[0]
            this.ChangedPhieu(this.PhieuMoiSelected)
          }
          this.buttons.btnEdit.enabled = true
          this.buttons.btnGiaoPhieu.enabled = true
          this.buttons.btnCancel.enabled = true
        } else if (this.activetab == 2) {
          let items = this.grdItemsDaGiao.getSelectedRecords()
          if (items.length > 0) {
            this.PhieuMoiSelected = items[0]
            this.ChangedPhieu(this.PhieuMoiSelected)
          }
          this.buttons.btnEdit.enabled = false
          this.buttons.btnSave.enabled = false
          this.buttons.btnGiaoPhieu.enabled = false
          this.buttons.btnCancel.enabled = false
        }
      } catch (error) {
        this.$root.toastError(' ' + error)
      } finally {
        this.loading(false)
      }
    },
    changeDSPhieu() {
      this.buttons.btnEdit.enabled = false
      this.buttons.btnSave.enabled = false
      this.LayDuLieu()
    },
    async giaoPieu() {
      try {
        this.loading(true)
        console.log('khieuNaiID', this.khieuNaiID)
        if (this.khieuNaiID === null) return
        this.$refs.frmGiaoPhieuGTC.maKN = this.maKN
        this.$refs.frmGiaoPhieuGTC.show()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    }
  },
}
</script>
