<template src="./index.html">
</template>
<script>
import Enum from './Enum'
import FormThongTinKhachHang from './components/ThongTinKhachHang'
import FormThongTinThueBao from './components/ThongTinThueBao'
import FormChangeCustomerInfor from './components/ChangeCustomerInfor'

import ThongTinHenKhachHangModal from '@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import CapVatTuTB from '@/modules/contract/complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB.vue'
import frmTC_DBTB_KH from '@/modules/contract/setup/SeparationSubscriber/popups/frmTC_DBTB_KH.vue'
import InHoSo from '@/modules/print/InHopDong/InHopDong'
import DateTimeLib from '@/utils/DateTimeLib'
import PopupCapNhatDV from '../../../contract/setup/Liquidate/components/popupCapNhatDV.vue'

export default {
  name: 'DichChuyenV2',
  components: {
    FormThongTinKhachHang,
    FormThongTinThueBao,
    FormChangeCustomerInfor,
    ThongTinHenKhachHangModal,
    CapVatTuTB,
    frmTC_DBTB_KH,
    PopupCapNhatDV,
    InHoSo
  },
  data () {
    return {
      thamso_md: null,
      glbDanhMuc_Chung: null,
      ThongTinKhachHang: null,
      ThongTinThueBao: null,
      KIEU_HD: {
        TAI_GD: 1,
        TAI_NHA: 2
      },
      activetab: 0,
      tsbtnNhapMoi: true,
      tsbtnGhiLai: true,
      tsbtnHuyBo: true,
      tsbtnXoa: true,
      tsbtnThanhToan: true,
      tsbtnInPL: true,
      tsbtnThemTB: true,
      tsbtnXoaTB: true,
      tsbtnThemTB_DS: true,
      tsbtnThongTinLH: true,
      tsbtnEmail: true,
      tsbtnHen: true,
      tsbtnVatTuMoi: true,
      tsbtnThemTT: false,
      tsbtnXoaTT: false,
      tsbtnInhopdong: false,
      modelCbo: {
        cboKieu_HD: -1,
        vcboKieu_HD: []
      },
      modelCkeckBox: {
        chkCTV: false,
        chkNguoiGT: false
      },
      modelInput: {
        txtCTV: '',
        txtNguoiGT: '',
        txtTongTienHD: ''
      },
      ctv_id: null,
      nguoigt_id: null,
      modelVatTu: null,
      hdkh_id: 0,
      khachhang_id: 0,
      popupInHoSo_Param: {
        hdkh_id: 0,
        hdtb_id: 0,
        ma_gd: '',
        ngaylap_hd: null,
        ngay_yc: null
      },
      popupComponentTitle: null,
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: '',
      popupComponentAttr: null,
      popupComponentEvents: {
        'form-close': 'popupClosed',
        'acceptChangeCTV': 'popupClosed',
        'acceptChangeNGT': 'popupClosed'
      }
    }
  },
  methods: {
    onclickTab: function (currentActivetab) {
      try {
        this.loading(true)
        this.activetab = currentActivetab
        console.log('🚀 ~ file: index.vue ~ line 48 ~ this.activetab', this.activetab)
      } catch (error) {
        this.$root.toastError(' ' + error)
      } finally {
        this.loading(false)
      }
    },
    chkCTV_CheckedChanged () {
      if (this.modelCkeckBox.chkCTV) {
        this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien.vue')
        this.popupComponentName = 'popupCongTacVien'
        this.popupComponentTitle = 'Cộng tác viên'
        this.$bvModal.show('popupComponentsFormDC')
      }
    },
    chkNguoiGT_CheckedChanged () {
      if (this.modelCkeckBox.chkNguoiGT) {
        this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu.vue')
        this.popupComponentName = 'popupNguoiGioiThieu'
        this.popupComponentTitle = 'Người giới thiệu'
        this.$bvModal.show('popupComponentsFormDC')
      }
    },
    tsbtnNhapMoi_Click () {
      this.NhapMoi()
    },
    tsbtnGhiLai_Click: async function () {
      try {
        // console.log(this)
        if (this.thamso_md === null || this.thamso_md === undefined || this.thamso_md.length === 0) {
          this.$root.toastError('Chương trình hiện không load được các tham số thiết lập mặc định cho đơn vị. Đề nghị thực hiện lại!')
          return
        }
        this.loading(true)
        let check = await this.ThongTinKhachHang.KiemTraDL_KhachHang()
        if (!check) {
          return
        }
        check = await this.ThongTinKhachHang.KiemTraDL_ThanhToan()
        if (!check) {
          return
        }
        check = await this.ThongTinThueBao.KiemTraDL_ThueBao()
        if (!check) {
          return
        }

        let checkTongNo = await this.checkTongNoTB()

        if (checkTongNo && Number(checkTongNo) > 0) {
          if ((await this.$confirm(`Mã thuê bao ${this.ThongTinThueBao.modelInput.txtMaTB} đang còn nợ ${this.$options.filters.numeral(checkTongNo)} đồng`, 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(async () => {
            return 'Yes'
          })) != 'Yes') {
            return false
          }            
        }


        if (this.tsbtnNhapMoi == false) {
          await this.ThongTinThueBao.CapNhat(true)
        } else {
          await this.ThongTinThueBao.CapNhat(false)
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    tsbtnHuyBo_Click () {
      this.SetButton(0)
    },
    tsbtnXoa_Click () {
      this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa hợp đồng không ?', {
        size: 'sm',
        okTitle: 'Đồng ý',
        cancelTitle: 'Hủy'
      }).then(async v => {
        if (v) {
          await this.ThongTinKhachHang.XoaHDKH()
        }
      }).catch(error => {
        // this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Xóa thất bại');
      })
    },
    async tsbtnThanhToan_Click () {
      let rs = await this.$root.context.post(`/web-hopdong/chuyendich/kiemtra_dk_goidadv_v2?hdkh_id=${this.ThongTinKhachHang.hdkh_id}`)
      if (rs.data != 1) {
        this.$root.toastError(rs.data)
      } else {
        this.popupComponent = () => import('@/modules/contract/setup/Payment/Payment.vue')
        this.popupComponentAttr = { magiaodich: this.ThongTinThueBao.modelInput.txtMaGD, tag: 1 }
        this.popupComponentName = 'popupPayment'
        this.popupComponentTitle = 'THANH TOÁN HỢP ĐỒNG'
        this.$refs.thanhtoanDialog.show(true)
      }
    },
    ycThayDoiThongTinClick: async function () {
      if (this.hasHDKH) {
        // this.$router.push({ path: '/contract/ChangeCustomerInfor', query: { tag: 0, hdkh_id: this.ThongTinKhachHang.hdkh_id } })
        this.$refs.refFormChangeCustomerInfor.setData({hdkh_id: this.ThongTinKhachHang.hdkh_id, tag: 0})
        this.$refs.refFormChangeCustomerInfor.show()
      }
    },
    pdThayDoiThongTinClick: async function () {
      if (this.hasHDKH) {
        // this.$router.push({ path: '/contract/ChangeCustomerInfor', query: { tag: 1, hdkh_id: this.ThongTinKhachHang.hdkh_id } })
        this.$refs.refFormChangeCustomerInfor.setData({hdkh_id: this.ThongTinKhachHang.hdkh_id, tag: 1})
        this.$refs.refFormChangeCustomerInfor.show()
      }
    },
    tsbtnInPL_Click () {
      if (this.ThongTinKhachHang.hdkh_id > 0) {
        this.$refs.popupInHoSo.hdkh_id = this.ThongTinKhachHang.hdkh_id
        this.$refs.popupInHoSo.ma_gd = this.ThongTinThueBao.modelInput.txtMaGD
        this.$refs.popupInHoSo.ngaylap_hd = DateTimeLib.toDateDisplay(this.ThongTinThueBao.modelDateTime.dtpNgayLapHD)
        this.$refs.popupInHoSo.ngay_yc = DateTimeLib.toDateDisplay(this.ThongTinThueBao.modelDateTime.dtpNgayYC)
        this.$refs.popupInHoSo.showModal()
      } else {
        this.$root.toastError('Chưa có thông tin hợp đồng!')
      }
    },
    async tsbtnThemTB_Click () {
      await this.ThongTinThueBao.ThemHDThueBao()
    },
    tsbtnXoaTB_Click () {
      try {
        if (this.ThongTinThueBao.modelInput.lvwThueBao.length == 0) {
          this.$root.toastError('Không có dữ liệu để xóa!')
        } else {
          this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa thuê bao không ?', {
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          }).then(async v => {
            if (v) {
              await this.ThongTinThueBao.XoaHDTB()
            }
          }).catch(error => {
            this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Xóa thất bại')
          })
        }
      } catch (error) {

      }
    },
    tsbtnThemTB_DS_Click () {
      if (this.hdkh_id != 0) {
        this.$refs.frmTC_DBTB_KH.showModal(this.khachhang_id, 0, this.ThongTinThueBao.inputValue.loaitb_id, this.hdkh_id, 0)
      }
    },
    setDBTB: function () {

    },
    tsbtnThongTinLH_Click () {
      if (this.ThongTinKhachHang.hdkh_id && this.ThongTinKhachHang.hdkh_id != 0) {
        this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue')
        this.popupComponentAttr = { hdkh_id: this.hdkh_id, khachhang_id: this.khachhang_id, ten_kh: this.ThongTinKhachHang.txtTenKH, diachi: this.ThongTinKhachHang.txtDiaChiKH }
        this.popupComponentName = 'popupThongTinLienHe'
        this.popupComponentTitle = 'Thông tin liên hệ'
        this.$bvModal.show('popupComponentsFormDC')
      }
    },
    tsbtnEmail_Click () {
      if (this.ThongTinKhachHang.hdkh_id && this.ThongTinKhachHang.hdkh_id != 0) {
        this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/ThongTinEmail.vue')
        this.popupComponentAttr = { hdkh_id: this.hdkh_id, khachhang_id: this.khachhang_id }
        this.popupComponentName = 'popupThongTinEmail'
        this.popupComponentTitle = 'Thông tin Email'
        this.$bvModal.show('popupComponentsFormDC')
      }
    },
    tsbtnHen_Click () {
      if (this.ThongTinKhachHang.hdkh_id != 0) {
        this.$refs.thongTinHenKhachHangModal.showModal()
      }
    },
    async tsbtnVatTuMoi_Click () {
      let phieu_id = 0
      let rs = await this.$root.context.get(`/web-hopdong/chuyendich/fn_map_custom?loai=1&id=${this.ThongTinThueBao.inputValue.hdtb_id}`)
      if (rs.data != '' && rs.data != -1) {
        phieu_id = rs.data
      }
      if (phieu_id == 0) {
        this.$root.toastError('Bạn phải ghi lại hợp đồng trước khi chọn vật tư')
        return
      }
      this.modelVatTu = {
        phieu_id: phieu_id,
        loaihd_id: this.ThongTinThueBao.inputValue.loaihd_id,
        hdtb_id: this.ThongTinThueBao.inputValue.hdtb_id,
        thuebao_id: this.ThongTinThueBao.inputValue.thuebao_id,
        loaitb_id: this.ThongTinThueBao.inputValue.loaitb_id,
        baohong_id: 0,
        kieu_f: 10
      }
      this.$refs.popupVatTuTB.openDialog()
    },
    tsbtnThemTT_Click () {
      this.ThongTinKhachHang.ThemHDThanhToan()
    },
    async tsbtnXoaTT_Click () {
      try {
        let rs = await this.$root.context.get(`/web-hopdong/kiemtra_hdtb_theo_hdtt?hdttId=${this.ThongTinKhachHang.hdtt_id}`)
        if (rs.data > 0) {
          this.$root.toastError(`Thanh toán ${this.ThongTinThueBao.txtMaTT} đã có thuê bao! Hãy xóa thuê bao trước.`)
        } else {
          this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa thanh toán không ?', {
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          }).then(async v => {
            if (v) {
              await this.ThongTinKhachHang.XoaHDTT()
            }
          }).catch(error => {
            this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : 'Xóa thất bại')
          })
        }
      } catch (error) {

      }
    },
    btnInHopDong_Click () {},
    NhapMoi () {
      this.activetab = 0
      this.SetButton(1)
    },
    SetButton (kieu) {
      try {
        this.tsbtnNhapMoi = false
        this.tsbtnGhiLai = false
        this.tsbtnXoa = false
        this.tsbtnHuyBo = false

        this.tsbtnThemTB = false
        this.tsbtnThemTB_DS = false
        this.tsbtnThemTT = false

        this.tsbtnInhopdong = false
        this.tsbtnThanhToan = false
        this.tsbtnXoaTB = false
        if (kieu == -1) {
          this.tsbtnGhiLai = true
          this.tsbtnHuyBo = true
        } else if (kieu == 0) { // Bắt đầu
          this.tsbtnNhapMoi = true
          this.ThongTinKhachHang.Clear()
          this.ThongTinThueBao.Clear()
        } else if (kieu == 1) { // Thêm mới
          this.tsbtnGhiLai = true
          this.tsbtnHuyBo = true
          this.ThongTinKhachHang.Clear()
          this.ThongTinThueBao.Clear()
        } else if (kieu == 2) { // Hủy
          this.tsbtnNhapMoi = true
          this.tsbtnXoa = true
          this.ThongTinKhachHang.Clear()
          this.ThongTinThueBao.Clear()
        } else if (kieu == 3) { // Edit
          this.tsbtnNhapMoi = true
          this.tsbtnXoa = true
          this.tsbtnGhiLai = true
          this.tsbtnHuyBo = true

          this.tsbtnThemTB = true
          this.tsbtnThemTB_DS = true
          this.tsbtnThemTT = true

          this.tsbtnInhopdong = true
          this.tsbtnThanhToan = true

          if (this.ThongTinKhachHang.lvwThanhToan.length > 1) {
            this.tsbtnXoaTT = true
          }
          if (this.ThongTinThueBao.modelInput.lvwThueBao.length > 1) {
            this.tsbtnXoaTB = true
          }
        }
      } catch (error) {
        console.log(error)
      }
    },
    frmChuyenDich_Load: async function () {
      try {
        let rs = {}
        rs.data = this.glbDanhMuc_Chung
        await this.ThongTinThueBao.frmChuyenDich_Load()
        if (rs.data != null) {
          this.ThongTinKhachHang.lay_dulieu_combobox(rs)
          this.modelCbo.vcboKieu_HD = rs.data.kieu_hd
          this.modelCbo.cboKieu_HD = this.modelCbo.vcboKieu_HD.length ? this.modelCbo.vcboKieu_HD[0].kieuhd_id : null
        }
        this.thamso_md = await this.LAY_DS_THAMSO_MD(0)
        if (this.thamso_md !== null) {
          await this.ThongTinThueBao.get_thamso_md(this.thamso_md)
        }
      } catch (error) {
        console.log('🚀 ~ file: index.vue ~ line 375 ~ error', error)
      }
    },
    txtMaTB_KeyPress: async function (args) {
      try {
        if (args.loai == 1) {
          let data = args.data
          this.ThongTinKhachHang.HienThiTTHopDongKH(data)
          if (data != null && data.length) {
            let item = data[0]
            if (item.kieuhd_id) {
              this.modelCbo.cboKieu_HD = item.kieuhd_id
            } else {
              this.modelCbo.cboKieu_HD = this.KIEU_HD.TAI_GD
            }
            if (item.ctv_id) {
              this.modelCkeckBox.chkCTV = true
              this.ctv_id = item.ctv_id
              let rs = await this.$root.context.get('/web-hopdong/chuyendich/map_id_nhanvien_id_neo', {nhanvien_id: this.ctv_id})
              this.modelInput.txtCTV = rs.data ? rs.data.ten_nv : null
            } else {
              this.modelInput.txtCTV = ''
              this.modelCkeckBox.chkCTV = false
            }
            if (item.nhanviengt_id) {
              this.modelCkeckBox.chkNguoiGT = true
              this.nguoigt_id = item.nhanviengt_id
              let rs = await this.$root.context.get('/web-hopdong/chuyendich/map_id_nhanvien_id_neo', {nhanvien_id: this.nguoigt_id})
              this.modelInput.txtNguoiGT = rs.data ? rs.data.ten_nv : null
            } else {
              this.modelInput.txtNguoiGT = ''
              this.modelCkeckBox.chkNguoiGT = false
            }
          }
        } else {
          this.ThongTinKhachHang.HienThiTT_HopDong_ThueBao(args.data)
        }
      } catch (err) {
        console.log(err)
      }
    },
    onEnterMaGD: function (ds) {
      try {
        if (ds.length > 0) {
          this.ThongTinKhachHang.HienThiTTHopDongKH(ds)
        }
      } catch (error) {

      }
    },
    HienThiTT_DanhBa_KH_NhanCQ: async function (ds) {
      try {
        await this.ThongTinKhachHang.HienThiTT_DanhBa_KH_NhanCQ(ds)
      } catch (error) {
        console.log(error)
      }
    },
    change_hdkh_id: function (data) {
      this.hdkh_id = data
    },
    change_khachhang_id: function (data) {
      this.khachhang_id = data
    },
    PopUpCongTacVien_Thoat: function () {
      this.$bvModal.hide('popupComponentsFormDC')
    },
    acceptChangeCTV: function (data) {
      if (data.rowData != 0) {
        this.modelInput.txtCTV = data.rowData.ten_nv
        this.ctv_id = data.nhanvien_id
      }
      this.$bvModal.hide('popupComponentsFormDC')
    },
    PopUpNguoiGioiThieu_Thoat: function () {
      this.$bvModal.hide('popupComponentsFormDC')
    },
    acceptChangeNGT: function (data) {
      if (data.rowData != 0) {
        this.modelInput.txtNguoiGT = data.rowData.ten_nv
        this.nguoigt_id = data.nhanvien_id
      }
      this.$bvModal.hide('popupComponentsFormDC')
    },
    btnCapNhatDV_Click: function () {
      if (this.ThongTinThueBao.inputValue.hdtb_id == 0) {
        this.$root.$toast.error('Chưa cập nhật Hợp đồng!')
        return
      }
      let dichvuvt_id = this.ThongTinThueBao.modelCbo.cboDichVuVT
      if (dichvuvt_id == Enum.DichVuVienThong.DICHVU_CNTT || dichvuvt_id == Enum.DichVuVienThong.TRUNGTAM_DULIEU ||
            dichvuvt_id == Enum.DichVuVienThong.ANTOAN_BAOMAT_TT || dichvuvt_id == Enum.DichVuVienThong.HOINGHI_TRUYENHINH) {
        // mở frmCapNhatHDTB_DV
        let dulieu = {
          hdtb_id: this.ThongTinThueBao.inputValue.hdtb_id
        };
        this.$refs.popupCapNhatDV_cq.openDialog(dulieu)

      } else {
        this.$root.$toast.error('Chức năng chỉ cập nhật cho HĐ nhóm CNTT, TTDL, HNTH, ATBMTT')
      }
    },
    async LAY_DS_THAMSO_MD (vkieu) {
      try {
        let rs = await this.$root.context.get('/web-hopdong/chuyendich/lay_danhsach_thamso?kieu=' + vkieu)
        console.log('🚀 ~ file: index.vue ~ line 476 ~ LAY_DS_THAMSO_MD ~ rs', rs)
        return rs.data
      } catch (error) {
        return []
      }
    },
    async checkTongNoTB() {
      try {
        let rs = await this.$root.context.get(
          '/web-hopdong/hopdong/tracuu_no_thuebao?thueBaoId=' + this.ThongTinThueBao.inputValue.thuebao_id
        )
        if (rs !== undefined && rs.error_code === 'BSS-00000000' && rs.data !== null) return rs.data
        else return false
      } catch (er) {
        return false
      }
    }
  },
  mounted: async function () {
    try {
      this.loading(true)
      let args = 'DICHVU_VT|LOAIHINH_TB|DANTOC|QUOCTICH|NGANHNGHE|TIEUNGANH_C1|TIEUNGANH_C2|TIEUNGANH_C3|TRANG_BI|THOIHAN|CHUQUAN|LOAIKENH|DOITUONG|HINHTHUC_TT|HINHTHUC_TC|KIEU_HD'
      let rs = await this.$root.context.post('/web-hopdong/chuyendich/lay_danhmuc_chung', {vchuoi: args})
      if (rs.data != null) {
        this.glbDanhMuc_Chung = rs.data
      }
      this.ThongTinKhachHang = this.$refs.ThongTinKhachHang
      this.ThongTinThueBao = this.$refs.ThongTinThueBao
      await this.frmChuyenDich_Load()
      this.SetButton(-1)
      if (Object.keys(this.$route.query).length > 0) {
        let query = this.$route.query
        if (query.ma_tb && query.dichvu_id) {
          this.ThongTinThueBao.modelInput.txtMaTB = query.ma_tb
          this.ThongTinThueBao.modelCbo.cboDichVuVT = query.dichvu_id
          await this.ThongTinThueBao.txtMaTB_KeyPress()
        }
        try {
          if (query.tag) {
            this.ThongTinThueBao.inputValue.iKenhTN = Number(query.tag)
          }
        } catch (error) {
          this.ThongTinThueBao.inputValue.iKenhTN = 1
        }
      }
    } catch (error) {
      console.log('🚀 ~ file: index.vue ~ line 522 ~ created ~ error', error)
    } finally {
      this.loading(false)
    }
  },
  computed: {
    hasHDKH: function () {
      if (this.ThongTinKhachHang === null) return false
      console.log('🚀 ~ file: index.vue:519 ~ this.ThongTinKhachHang.hdkh_id:', this.ThongTinKhachHang.hdkh_id)
      return !(this.ThongTinKhachHang.hdkh_id == null || this.ThongTinKhachHang.hdkh_id == undefined || this.ThongTinKhachHang.hdkh_id == 0)
    }
  },
  watch: {

  }
}
</script>
<style>
a.disable-btn-chuyendich {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
.modal-ThanhToan-ChuyenDich .main-wrapper {
  position: unset !important;
}

.modal-ThanhToan-ChuyenDich .page-content {
  position: unset !important;
}
</style>
