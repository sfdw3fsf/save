<template src="./index.html"></template>
<style scoped src="./index.scss"></style>
<script>
import ComboboxGrid from '../../../../components/Controls/ComboboxGrid'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import TienTrinhDuAn from './modal/tientrinh-duan'
import ModalChonDiaChi from '../../HOPDONG/ChuanHoaDLKhachHang/popupChonDiaChi/ModalChonDiaChi'
import TraCuuTBToaNha from '../TraCuuTBToaNha/index.vue'
import TraCuuKCToaNha from '../TraCuuKCToaNha/index.vue'
import frmChonTinhDuAn from './modal/chon-tinh/frmChonTinh.vue'
import moment from 'moment'
import { required, maxLength } from 'vuelidate/lib/validators'

export default {
  components: {
    appCombobox: ComboboxGrid,
    BssRequiredMarker,
    tienTrinhDA: TienTrinhDuAn,
    ModalChonDiaChi,
    TraCuuTBToaNha,
    TraCuuKCToaNha,
    frmChonTinhDuAn
  },
  data() {
    return {
      isShowChonTinh: false,
      tinh: null,
      listTinh: [],
      tinhHienTai: null,
      isViewOnly: false,
      cbo_ChuDauTu: {
        item: [],
        selected: null
      },
      cbo_trangThaiDA: {
        item: [],
        selected: null
      },
      cbo_htHopTac: {
        item: [],
        selected: null
      },
      cbo_TTVT: {
        item: [],
        selected: null
      },
      cbo_LoaiHinhKD: {
        item: [],
        selected: null
      },
      tt_duAn: [],
      duAn: {
        duan_id: 0,
        chudtu_id: 0,
        phuong_id: 0,
        quan_id: 0,
        ma_duan: '',
        ttda_id: 1,
        ten_duan: '',
        nguoi_cctt: '',
        ttvt_id: -1,
        diachi: '',
        htht_id: 0,
        ghichu: '',
        ngay_ht: null,
        ngay_tk: null,
        ngay_cn: new Date(),
        diachi_id: 0
      },
      diaChi: {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        pho_id: 0,
        phuong_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: '',
        diachi: '',
        kinhdo: 0,
        vido: 0
      },
      dataDiaChi: null,
      btnEnable: {
        nhapMoi: false,
        ghiLai: false,
        xoa: false,
        huy: false,
        maDA: false
      },
      themmoi: false,
      capnhat_mada: false,
      txtMaDA: false,
      btnEditMaDA: {
        ok: false,
        cancel: false,
        edit: false
      },
      txtMaDAcu: ''
    }
  },
  validations: {
    duAn: {
      ten_duan: {
        required,
        maxLength: maxLength(500)
      },
      chudtu_id: {
        required
      },
      ttvt_id: {
        required
      },
      diachi: {
        required
      },
      ghichu: {
        maxLength: maxLength(500)
      },
      ngay_ht: {
        required
      },
      ngay_tk: {
        required
      }
    }
  },
  async created() {
    this.setButton(-1)
    await this.loadForm()
    await this.onLoad()
    this.tinhHienTai = this.$auth.getPhanVungID()
  },
  methods: {
    cboTinh_EditValueChanged: async function(args) {
      if (args == null) {
        this.tinh = {}
        return
      }
      this.tinh = args
      if (this.tinh !== this.tinhHienTai) {
        this.isViewOnly = true
      } else {
        this.isViewOnly = false
      }
      this.cleanForm()
      this.setButton(-1)
      await this.loadForm()
    },
    async onLoad() {
      this.axios.post('web-hopdong/bss_group1/lay_ds_tinh_thicong_combobox', {}).then((rs) => {
        this.listTinh = rs.data.data
        if (this.listTinh.length > 0) {
          this.tinh = this.$auth.getPhanVungID()
        }
      })
    },
    checkShowModal: async function() {
      this.$root.showLoading(true)
      let data = await this.$root.context.post('web-toanha/capnhat-doanhthu-bc/fn_kiemtra_quyen_capnhat_doanhthu_bc', {
        kieu: 1
      })
      if (data.data == 1) {
        this.isShowChonTinh = true
        this.$root.showLoading(false)
        await this.$refs.ref_frmChonTinhDuAn.showModal()
      } else {
        this.$root.showLoading(false)
      }
    },
    onChapNhanModalChonTinh: async function(data) {
      this.tinh = data
      this.cleanForm()
      this.setButton(-1)
      await this.loadForm()
      if (this.tinh !== this.tinhHienTai) {
        this.isViewOnly = true
      } else {
        this.isViewOnly = false
      }
    },
    async getDSTrangThaiDA() {
      try {
        this.loading(true)
        let response = await this.$root.context.get(`web-toanha/danhmuc-duan/ds_ttda`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.cbo_trangThaiDA.item = response.data
        }
      } catch (err) {
        this.cbo_trangThaiDA.item = []
      } finally {
        this.loading(false)
      }
    },
    async getDSHTHopTac() {
      try {
        this.loading(true)
        let response = await this.$root.context.get(`web-toanha/danhmuc-duan/ds_htht`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.cbo_htHopTac.item = response.data
        }
      } catch (err) {
        this.cbo_htHopTac.item = []
      } finally {
        this.loading(false)
      }
    },
    async getDSTrungTamVT() {
      try {
        this.loading(true)
        let response = await this.$root.context.post(`web-toanha/danhmuc-duan/ds_ttvt_v2`, {
          phanvung_id: this.tinh ?? 0
        })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.cbo_TTVT.item = response.data
        }
      } catch (err) {
        this.cbo_TTVT.item = []
      } finally {
        this.loading(false)
      }
    },
    async getDSThongTinDA() {
      try {
        this.loading(true)
        let response = await this.$root.context.post(`web-toanha/danhmuc-duan/tt_duan_v2`, {
          phanvung_id: this.tinh ?? 0
        })
        if (response.error_code === 'BSS-00000000' && response.data != null) {
          this.tt_duAn = response.data
        } else {
          this.tt_duAn = []
        }
      } catch (err) {
        this.tt_duAn = []
      } finally {
        this.loading(false)
      }
    },
    async getMaDA(vttvt_id) {
      try {
        this.loading(true)
        let response = await this.$root.context.get(`web-toanha/danhmuc-duan/lay_ma_duan/${vttvt_id}`)
        if (response.error_code === 'BSS-00000000' && response.data != null) {
          return response.data
        }
      } catch (err) {
      } finally {
        this.loading(false)
      }
    },
    async getDSChuDauTu() {
      try {
        this.loading(true)
        let response = await this.$root.context.post(`web-toanha/danhmuc-duan/ds_chudautu_v2`, {
          phanvung_id: this.tinh ?? 0
        })
        if (response.error_code === 'BSS-00000000' && response.data !== null) {
          this.cbo_ChuDauTu.item = response.data
          this.cbo_ChuDauTu.item.push({
            chudtu_id: -1,
            ma_chudtu: '',
            ten_chudtu: ''
          })
        }
      } catch (err) {
        this.cbo_ChuDauTu.item = []
      } finally {
        this.loading(false)
      }
    },
    async updateDiaChi(kieu) {
      try {
        this.loading(true)
        if (kieu === 2 && this.diaChi.diaChi_id === 0) {
          if (this.diaChi.quan_id === 0) return true
          else kieu = 1
        }
        let apiBody = {
          vkieu: kieu,
          vap_id: this.diaChi.ap_id,
          vdacdiem_id: this.diaChi.dacDiem_id,
          vdiachi: this.diaChi.diaChi,
          vdiachi_id: this.diaChi.diaChi_id,
          vkhu_id: this.diaChi.khu_id,
          vkinhdo: this.diaChi.kinhDo,
          vpho_id: this.diaChi.pho_id,
          vphuong_id: this.diaChi.phuong_id,
          vquan_id: this.diaChi.quan_id,
          vsonha: this.diaChi.soNha,
          vtinh_id: this.diaChi.tinh_id,
          vvido: this.diaChi.viDo
        }
        console.log(apiBody)
        let url = `web-toanha/danhmuc-duan/capnhat_diachi`
        let response = await this.$root.context.post(url, apiBody)
        if (response.error_code === 'BSS-00000000' && response.data !== undefined && response.data.includes('OK')) {
          if (kieu === 1) this.diaChi.diaChi_id = Number(response.data.split('-')[1])
          return true
        } else return false
      } catch (error) {
        // this.$toast.error(error.message)
        return false
      } finally {
        this.loading(false)
      }
    },
    async capNhatDuAn(kieu) {
      try {
        this.loading(true)
        let vds = {
          DUAN_ID: this.duAn.duan_id,
          MA_DUAN: this.duAn.ma_duan,
          TEN_DUAN: this.duAn.ten_duan,
          TEN_THUONGMAI: this.duAn.ten_thuongmai,
          DIACHI: this.duAn.diachi,
          CHUDTU_ID: this.cbo_ChuDauTu.selected,
          PHUONG_ID: this.duAn.phuong_id,
          QUAN_ID: this.duAn.quan_id,
          GHICHU: this.duAn.ghichu,
          NGAY_CN:
            this.duAn.ngay_cn &&
            moment(this.duAn.ngay_cn)
              .format('DD/MM/yyyy HH:mm:ss')
              .toString(),
          TTDA_ID: this.cbo_trangThaiDA.selected,
          NGAY_TK:
            this.duAn.ngay_tk &&
            moment(this.duAn.ngay_tk)
              .format('DD/MM/yyyy HH:mm:ss')
              .toString(),
          NGAY_HT:
            this.duAn.ngay_ht &&
            moment(this.duAn.ngay_ht)
              .format('DD/MM/yyyy HH:mm:ss')
              .toString(),
          TTVT_ID: this.cbo_TTVT.selected,
          NGUOI_CCTT: this.duAn.nguoi_cctt,
          HTHT_ID: this.cbo_htHopTac.selected,
          LOAIHINHKD_ID: this.cbo_LoaiHinhKD.selected,
          DIACHI_ID: this.duAn.diachi_id
        }
        let vds_diachi = {
          DIACHI_ID: this.diaChi.diachi_id,
          TINH_ID: this.diaChi.tinh_id,
          QUAN_ID: this.diaChi.quan_id,
          PHUONG_ID: this.diaChi.phuong_id,
          PHO_ID: this.diaChi.pho_id,
          AP_ID: this.diaChi.ap_id,
          KHU_ID: this.diaChi.khu_id,
          SONHA: this.diaChi.sonha,
          DIACHI: this.diaChi.diachi,
          DACDIEM_ID: this.diaChi.dacdiem_id,
          VIDO: this.diaChi.vido,
          KINHDO: this.diaChi.kinhdo
        }
        let apiBody = {
          vkieu: kieu,
          vds: vds,
          vds_diachi: vds_diachi
        }
        let url = `web-toanha/danhmuc-duan/capnhat_duan`
        let response = await this.$root.context.post(url, apiBody)
        if (response.error_code === 'BSS-00000000' && response.data !== undefined && response.data.includes('OK')) {
          if (kieu === 1) this.duAnID = Number(response.data.split('-')[1])
          return true
        } else {
          return false
        }
      } catch (err) {
        this.$toast.error(err.message)
        return false
      } finally {
        this.loading(false)
      }
    },
    async ktQuyenToaNha(kieu) {
      try {
        let apiBody = {
          vkieu: kieu,
          vid: this.duAn.duan_id,
          vttvt_id: this.duAn.ttvt_id
        }
        let url = `web-toanha/danhmuc-duan/kt_quyen_toanha`
        let response = await this.$root.context.post(url, apiBody)
        if (response.data == 'OK') return true
        else {
          this.$toast.error(response.data)
          return false
        }
      } catch (err) {
        return false
      } finally {
      }
    },
    async lay_ds_thamso_md(kieu) {
      try {
        let url = `web-toanha/danhmuc-duan/lay_ds_thamso_md?kieu=${kieu}`
        let response = await this.$root.context.get(url)
        if (response.error_code === 'BSS-00000000' && response.data != null) {
          console.log(response.data)
          let ds_thamso = response.data[0]
          let lst = ds_thamso.ten_ts.split(',')
          console.log(lst)
          let nhanvien_id = this.$root.token.getNhanVienID()
          if (lst.includes(nhanvien_id)) {
            this.capnhat_mada = true
          }
        }
      } catch (err) {
        this.capnhat_mada = false
      } finally {
      }
    },
    async capnhat_ma_duan() {
      try {
        this.loading(true)
        let apiBody = {
          duan_id: this.duAn.duan_id,
          ma_duan: this.duAn.ma_duan
        }
        let url = `web-toanha/danhmuc-duan/capnhat_ma_duan`
        let response = await this.$root.context.post(url, apiBody)
        if (response.error_code === 'BSS-00000000') {
          return response.data
        }
      } catch (err) {
      } finally {
        this.loading(false)
      }
    },
    viewEdit(hienThi) {
      if (hienThi) {
        this.txtMaDA = true
        this.btnEditMaDA.ok = false
        this.btnEditMaDA.cancel = false
        this.btnEditMaDA.edit = true
      } else {
        this.txtMaDA = false
        this.btnEditMaDA.ok = true
        this.btnEditMaDA.cancel = true
        this.btnEditMaDA.edit = false
      }
    },
    onClickEdit() {
      this.viewEdit(false)
    },
    onClickCancel() {
      this.duAn.ma_duan = this.txtMaDAcu
      this.viewEdit(true)
    },
    async capnhat_ma_da() {
      await this.$bvModal
        .msgBoxConfirm('Bạn thật sự muốn cập nhật lại mã dự án?', {
          title: 'Thông báo',
          size: 'md',
          buttonSize: 'md',
          okVariant: 'primary',
          okTitle: 'Đồng ý',
          cancelTitle: 'Không đồng ý',
          hideHeaderClose: false,
          centered: true,
          'modal-class': ['f18', 'text-center']
        })
        .then(async (result) => {
          if (result) {
            this.viewEdit(true)
            let rs = await this.capnhat_ma_duan()
            if (rs == null) {
              this.$toast.error('Không nhận được thông tin phản hồi khi cập nhật dữ liệu')
            } else if (rs == '1') {
              await this.getDSThongTinDA()
              this.$toast.success('Cập nhật mã dự án thành công')
            } else {
              this.$toast.error(rs)
            }
          } else {
            this.viewEdit(true)
          }
        })
    },
    async loadForm() {
      await this.getDSTrangThaiDA()
      await this.getDSHTHopTac()
      await this.getDSTrungTamVT()
      await this.getDSThongTinDA()
      await this.getDSChuDauTu()
      this.viewEdit(true)
      await this.lay_ds_thamso_md(0)
      this.cbo_LoaiHinhKD.item = [
        {
          id: 0,
          text: ' '
        },
        {
          id: 1,
          text: '1'
        },
        {
          id: 2,
          text: '2'
        },
        {
          id: 3,
          text: '3'
        }
      ]
    },
    cleanForm() {
      this.duAn.duan_id = 0
      this.cbo_ChuDauTu.selected = -1
      // this.duAn.phuong_id = 0
      // this.duAn.quan_id = 0
      this.duAn.ma_duan = ''
      this.cbo_trangThaiDA.selected = 1
      this.duAn.ten_duan = ''
      this.duAn.ten_thuongmai = ''
      this.duAn.nguoi_cctt = ''
      this.cbo_TTVT.selected = -1
      this.duAn.diachi = ''
      this.cbo_htHopTac.selected = 1
      this.duAn.ghichu = ''
      this.duAn.ngay_ht = null
      this.duAn.ngay_tk = null
      this.selectCboRow()
      // this.duAn.diachi_id = 0
    },
    setButton(kieu) {
      this.btnEnable.nhapMoi = false
      this.btnEnable.ghiLai = false
      this.btnEnable.xoa = false
      this.btnEnable.huy = false
      // this.btnEnable.maDA = false
      //Bắt đầu
      if (kieu == -1) {
        this.btnEnable.ghiLai = true
        this.btnEnable.huy = true
        this.themmoi = true
      }
      //Bắt đầu
      if (kieu == 0) {
        this.btnEnable.nhapMoi = true
        this.cleanForm()
        this.themmoi = true
      }
      //Thêm mới
      if (kieu == 1) {
        this.cleanForm()
        this.btnEnable.ghiLai = true
        this.btnEnable.huy = true
        this.themmoi = true
        this.btnEnable.maDA = true
      }
      //Huỷ
      if (kieu == 2) {
        this.btnEnable.nhapMoi = true
        // this.cleanForm()
        this.themmoi = true
      }
      //Edit
      if (kieu == 3) {
        this.btnEnable.nhapMoi = true
        this.btnEnable.xoa = true
        this.btnEnable.ghiLai = true
        this.btnEnable.huy = true
        this.themmoi = false
      }
    },
    trangThaiDASelected(args) {
      if (args != null) {
        this.cbo_trangThaiDA.selected = args.ID
      }
    },
    hinhThucHTSelected(args) {
      if (args != null) {
        this.cbo_htHopTac.selected = args.htht_id
      }
    },
    TTVTSelected(args) {
      if (args != null) {
        this.$refs.cboTTVT.$refs.comboboxGridInput.classList.remove('is-invalid')
        this.cbo_TTVT.selected = args.donvi_id
      }
    },
    chuDauTuSelected(args) {
      if (args != null) {
        this.$refs.cboChuDauTu.$refs.comboboxGridInput.classList.remove('is-invalid')
        this.cbo_ChuDauTu.selected = args.chudtu_id
      }
    },
    selectCboRow() {
      let idx1 = this.cbo_trangThaiDA.item.findIndex((e) => e.ID == this.cbo_trangThaiDA.selected)
      let idx2 = this.cbo_htHopTac.item.findIndex((e) => e.htht_id == this.cbo_htHopTac.selected)
      let idx3 = this.cbo_TTVT.item.findIndex((e) => e.donvi_id == this.cbo_TTVT.selected)
      let idx4 = this.cbo_ChuDauTu.item.findIndex((e) => e.chudtu_id == this.cbo_ChuDauTu.selected)

      if (idx1 !== -1) this.$refs.cboTrangThaiDA.$refs.grid.setCurrentSelectedRow(idx1)
      // if (idx2 !== -1)
      //     this.$refs.cboHTHopTac.$refs.grid.setCurrentSelectedRow(idx2);
      if (idx3 !== -1) this.$refs.cboTTVT.$refs.grid.setCurrentSelectedRow(idx3)
      if (idx4 !== -1) this.$refs.cboChuDauTu.$refs.grid.setCurrentSelectedRow(idx4)
    },
    validateData() {
      this.$v.$touch()
      let errorMessage = []
      let focusInput = []

      if (!this.$v.duAn.ten_duan.required) {
        errorMessage.push('Bạn chưa nhập Tên dự án')
        focusInput.push('ten_duan')
      } else if (!this.$v.duAn.ten_duan.maxLength) {
        errorMessage.push('Tên dự án không được lớn hơn 500 ký tự')
        focusInput.push('ten_duan')
      }

      if (!this.$v.duAn.chudtu_id.required || this.cbo_ChuDauTu.selected == -1) {
        errorMessage.push('Bạn chưa chọn Chủ đầu tư')
        // focusInput.push('cboChuDauTu');
        this.$refs.cboChuDauTu.$refs.comboboxGridInput.classList.add('is-invalid')
      }

      if (this.cbo_TTVT.selected == -1) {
        errorMessage.push('Bạn chưa chọn TTVT')
        // focusInput.push('cboTTVT')
        this.$refs.cboTTVT.$refs.comboboxGridInput.classList.add('is-invalid')
      }

      if (!this.$v.duAn.diachi.required) {
        errorMessage.push('Bạn chưa cập nhật thông tin Địa chỉ')
        focusInput.push('diachi')
      }

      if (!this.$v.duAn.ghichu.maxLength) {
        errorMessage.push('Ghi chú không được lớn hơn 500 ký tự')
        focusInput.push('ghichu')
      }

      if (this.duAn.ngay_ht != null && this.duAn.ngay_tk != null && this.duAn.ngay_tk > this.duAn.ngay_ht) {
        errorMessage.push('Ngày hoàn thành không được nhỏ hơn ngày triển khai!')
      }

      if (this.themmoi) {
        if (!this.$v.duAn.ngay_ht.required) {
          errorMessage.push('Bạn chưa nhập ngày hoàn thành')
          focusInput.push('ngay_ht')
        }

        if (!this.$v.duAn.ngay_tk.required) {
          errorMessage.push('Bạn chưa nhập ngày triển khai')
          focusInput.push('ngay_tk')
        }
      }

      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\r\n'))
        if (focusInput.length > 0) this.$refs[focusInput[0]].focus()
        return false
      } else return true
    },
    onNhapMoi() {
      this.setButton(1)
    },
    async onLayTT() {
      await this.getDSThongTinDA()
      if (this.tt_duAn.length <= 0) {
        this.setButton(1)
      }
    },
    onHuy() {
      this.setButton(2)
      let val = this.tt_duAn[0]
      this.onSelected(val)
      this.$refs.danhMucDAGrid.setCurrentSelectedRow(0)
    },
    async onGhiLai() {
      let kieu = this.btnEnable.nhapMoi ? 2 : 1
      let rs = await this.ktQuyenToaNha(kieu)
      if (!rs) {
        return
      }
      if (this.validateData()) {
        if (!this.btnEnable.nhapMoi) {
          let response = await this.updateDiaChi(1)
          if (!response) {
            this.$toast.error('Có lỗi khi cập nhật địa chỉ!')
            return
          }
          //Thêm mới dự án
          let result = await this.capNhatDuAn(1)
          if (result) {
            await this.getDSThongTinDA()
            this.$toast.success('Thêm mới thành công!')
          }
        } else {
          let response = await this.updateDiaChi(2)
          if (!response) {
            this.$toast.error('Có lỗi khi cập nhật địa chỉ!')
            return
          }
          //Cập nhật dự án
          let result = await this.capNhatDuAn(2)
          if (result) {
            await this.getDSThongTinDA()
            this.$toast.success('Cập nhật thành công!')
          }
        }
      }
    },
    async onXoa() {
      let rs = await this.ktQuyenToaNha(3)
      if (!rs) {
        return
      }
      try {
        await this.$bvModal
          .msgBoxConfirm('Bạn chắc chắn muốn xóa?', {
            title: 'Thông báo',
            size: 'md',
            buttonSize: 'md',
            okVariant: 'primary',
            okTitle: 'Đồng ý',
            cancelTitle: 'Không đồng ý',
            hideHeaderClose: false,
            centered: true,
            'modal-class': ['f18', 'text-center']
          })
          .then(async (result) => {
            if (result) {
              this.loading(true)
              let response = await this.updateDiaChi(3)
              if (!response) {
                this.$toast.error('Có lỗi khi xóa địa chỉ!')
                return
              }
              let rs = await this.capNhatDuAn(3)
              if (rs) {
                await this.getDSThongTinDA()
                this.$toast.success('Xóa dữ liệu thành công!')
              }
            }
          })
      } catch (err) {
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
      } finally {
        this.loading(false)
      }
    },
    onDiaChi() {
      if (this.isViewOnly) {
        return
      } else {
        this.dataDiaChi = {}
        this.dataDiaChi.tinh_id = this.$auth.getPhanVungID()
        this.dataDiaChi.quan_id = this.btnEnable.nhapMoi ? this.diaChi.quan_id : 0
        this.dataDiaChi.phuong_id = this.btnEnable.nhapMoi ? this.diaChi.phuong_id : 0
        this.dataDiaChi.pho_id = this.btnEnable.nhapMoi ? this.diaChi.pho_id : 0
        this.dataDiaChi.ap_id = this.btnEnable.nhapMoi ? this.diaChi.ap_id : 0
        this.dataDiaChi.khu_id = this.btnEnable.nhapMoi ? this.diaChi.khu_id : 0
        this.dataDiaChi.dacdiem_id = this.btnEnable.nhapMoi ? this.diaChi.dacdiem_id : 0
        this.dataDiaChi.so_nha = this.btnEnable.nhapMoi ? this.diaChi.sonha : ''
        this.dataDiaChi.diachi = this.btnEnable.nhapMoi ? this.diaChi.diachi : ''
        this.$bvModal.show('popupDiaChi')
      }
    },
    onDiaChiConfirm(data) {
      this.diaChi.diachi = data.diachimoi
      this.diaChi.tinh_id = data.tinh_id
      this.diaChi.quan_id = data.quan_id
      this.diaChi.phuong_id = data.phuong_id
      this.diaChi.pho_id = data.pho_id
      this.diaChi.ap_id = data.ap_id
      this.diaChi.khu_id = data.khu_id
      this.diaChi.dacdiem_id = data.dacdiem_id
      this.diaChi.sonha = data.so_nha

      this.duAn.phuong_id = data.phuong_id
      this.duAn.quan_id = data.quan_id
      this.duAn.diachi = data.diachimoi
    },
    onSelected(val) {
      this.duAn.duan_id = val.duan_id
      this.duAn.chudtu_id = val.chudtu_id
      this.duAn.phuong_id = val.phuong_id
      this.duAn.quan_id = val.quan_id
      this.duAn.ma_duan = val.ma_duan
      this.duAn.ttda_id = val.ttda_id
      this.duAn.ten_duan = val.ten_duan
      this.duAn.ten_thuongmai = val.ten_thuongmai
      this.duAn.nguoi_cctt = val.nguoi_cctt
      this.duAn.ttvt_id = val.ttvt_id
      this.duAn.diachi = val.diachi
      this.duAn.htht_id = val.htht_id
      this.duAn.ghichu = val.ghichu
      this.duAn.ngay_ht = val.ngay_ht
      this.duAn.ngay_tk = val.ngay_tk
      this.duAn.diachi_id = val.diachi_id

      this.cbo_LoaiHinhKD.selected = val.loaihinhkd_id
      this.cbo_trangThaiDA.selected = val.ttda_id
      this.cbo_ChuDauTu.selected = val.chudtu_id
      this.cbo_htHopTac.selected = val.htht_id
      this.cbo_TTVT.selected = val.ttvt_id
      this.selectCboRow()

      this.diaChi.diachi_id = val.diachi_id != null ? val.diachi_id : 0
      this.diaChi.tinh_id = val.tinh_id != null ? val.tinh_id : this.$auth.getPhanVungID()
      this.diaChi.quan_id = val.quan_id != null ? val.quan_id : 0
      this.diaChi.phuong_id = val.phuong_id != null ? val.phuong_id : 0
      this.diaChi.pho_id = val.pho_id != null ? val.pho_id : 0
      this.diaChi.ap_id = val.ap_id != null ? val.ap_id : 0
      this.diaChi.khu_id = val.khu_id != null ? val.khu_id : 0
      this.diaChi.dacdiem_id = val.dacdiem_id != null ? val.dacdiem_id : 0
      this.diaChi.sonha = val.sonha
      this.diaChi.diachi = val.diachi
      this.diaChi.kinhdo = val.kinhdo
      this.diaChi.vido = val.vido

      this.txtMaDAcu = val.ma_duan
      if (this.capnhat_mada) {
        this.viewEdit(true)
      }
    },
    onSelectedRowChanged: function(val, index) {
      this.onSelected(val)
      this.setButton(3)
    },
    gridViewControll_ExcelQueryCellInfo(args) {
      if (args.column.field === 'ngay_cn') {
        args.cell.innerText = moment(args.data.ngay_cn).format('DD/MM/YYYY')
      }
      if (args.column.field === 'ngay_tk') {
        args.cell.innerText = args.data.ngay_tk && moment(args.data.ngay_tk).format('DD/MM/YYYY')
      }
      if (args.column.field === 'ngay_ht') {
        args.cell.innerText = args.data.ngay_ht && moment(args.data.ngay_ht).format('DD/MM/YYYY')
      }
      if (args.column.field === 'loaihinhkd_id' && args.data.loaihinhkd_id == 0) {
        args.cell.innerText = null
      }
    },
    gridViewControll_QueryCellInfo(args) {
      if (args.column.field === 'ngay_cn') {
        args.cell.innerText = moment(args.data.ngay_cn).format('DD/MM/YYYY')
      }
      if (args.column.field === 'ngay_tk') {
        args.cell.innerText = args.data.ngay_tk && moment(args.data.ngay_tk).format('DD/MM/YYYY')
      }
      if (args.column.field === 'ngay_ht') {
        args.cell.innerText = args.data.ngay_ht && moment(args.data.ngay_ht).format('DD/MM/YYYY')
      }
      if (args.column.field === 'loaihinhkd_id' && args.data.loaihinhkd_id == 0) {
        args.cell.innerText = null
      }
    },
    async onTienTrinh() {
      this.$refs.tienTrinhDA.duan_id = this.duAn.duan_id
      this.$refs.tienTrinhDA.ten_duan = this.duAn.ma_duan + ' -- ' + this.duAn.ten_duan
      this.$refs.tienTrinhDA.showModal()
    },
    onXuatExcel() {
      if (this.tt_duAn.length <= 0) {
        this.$toast.error('Chưa có danh sách để xuất excel!')
      } else {
        let excelExportProperties = {
          fileName: 'Danh-muc-du-an.xlsx',
          dataSource: this.tt_duAn
        }
        this.$refs.danhMucDAGrid.excelExport(excelExportProperties)
      }
    },
    openTBToaNha() {
      this.$refs.tbToaNha.open()
      this.$refs.tbToaNha.duan_id = this.duAn.duan_id
    },
    openKCToaNha() {
      this.$refs.kcToaNha.open()
      this.$refs.kcToaNha.duan_id = this.duAn.duan_id
    }
  },
  async mounted() {
    this.checkShowModal()
  },
  watch: {
    isViewOnly: async function(val) {
      if (val) {
        //config to view only
      } else {
        //config to normal mode
      }
    }
  }
}
</script>
