<template src="./index.html"></template>
<style scoped src="./style.scss"></style>
<script>
import moment from 'moment'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import TTLienHe from './components/modal-tt-lien-he'
import Email from './components/email'
import TenTT from './components/ten-tt'
import DSThueBao from './components/modal-thue-bao'
import MaInGhep from './components/ma_in_ghep'
import TTLHFile from './components/ttlh-file'
import DiaChiPhatFile from './components/diachi-phat-file'
import DiaChiCTFile from './components/diachi-vat-mst-file'
import ChonDiaChi from './components/ChonDiaChi'
import CapNhatTheoFile from './components/CapNhatTheoFile'
import CapNhatInHDTheoFile from './components/CapNhatInHDTheoFile'
import PopupTimKiemNhanVienThuCuoc from '../GachNo/components/TimNhanVienThuCuoc'
import { Query } from '@syncfusion/ej2-data'

import {
  KTRA_MATT
} from '@/const/enums'

export default {
  components: {
    BssRequiredMarker,
    'thong-tin-lien-he': TTLienHe,
    email: Email,
    tentt: TenTT,
    'ds-thuebao': DSThueBao,
    MaInGhep,
    TTLHFile,
    DiaChiPhatFile,
    DiaChiCTFile,
    ChonDiaChi,
    PopupTimKiemNhanVienThuCuoc,
    CapNhatTheoFile,
    CapNhatInHDTheoFile
  },
  data: function () {
    return {
      header: {
        title: 'THAY ĐỔI THÔNG TIN KHÁCH HÀNG',
        list: [
          { name: 'Quản lý đại lý', link: { name: 'Ui.cards' } },
          {
            name: 'Thay đổi thông tin khách hàng',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      config: {
        f_diachiphat_visible: false,
        f_inhoadon_visible: false,
        f_lienhe_visible: false,
        f_mst_visible: false,
        f_sdt_visible: false,
        f_mig_visible: false,
        f_diachict_visible: false,
        fieldsNganHang: { text: 'TEN_NH', value: 'NGANHANG_ID' },
      },
      diachi: {
      },
      opt_diachi: -1,
      kieuId: 1,
      form: {
        danhBa: '1',
        hieuLuc: '1',
        idHTTT: '',
        bankCkb: false,
        daiDienId: '',
        dienThoaiKH: '',
        khachHangId: '',
        thanhToanId: '',
        thueBaoId: '',
        // dsDvKhac:[]
        maTT: '',
        maTbDaiDien: '',
        tenTT: '',
        diaChiTT: '',
        diaChiCt: '',
        diaChiBc: '',
        soDt: '',
        email: '',
        ghiChu: '',
        maIG: '',
        main_ghep: '',
        idDVQL: '',
        maNvTC: '',
        maTuyen: '',
        tuyenThuId: null,
        stk: '',
        mst: '',
        bankId: '',
        nvql: '',
        diemTN: ''
      },
      dsDVQL: [],
      dsHTTT: [],
      dsBank: [],
      dsMaDaiDien: [],
      thongTinTT: {},
      dsDichVuKhac: [],
      QLDL_DIACHITT_DUNGHUONG: -1,
      checkTTAM: false,
      dsDBModal: [],
      dsDvMaster: [],
      dsDvKhacChose: [],
      dsDKChecked: [],
      dsHuyChecked: [],
      modalTitle: 'Danh sách thanh toán',
      kyHoaDon: `${moment(new Date()).subtract(1, 'months').endOf('month').format('YYYYMM')}01`,
      objDiaChiTT: null,
      objDiaChiCT: null,
      objDiaChiBC: null
    }
  },
  validations: {},
  watch: {},
  created: async function () {
    this.SetDocfileButton(true)
    this.initForm()
    this.checkKH_AM()
  },
  async mounted() {
    this.$refs.maTT.focus()
    $(document).on('change', '.table-dichvu .check', (evt) => {
      const id = evt.currentTarget.id
      var remember = document.getElementById(id)
      const type = id.split('_')[0]
      if (type == 'DK') {
        if (remember.checked) {
          this.checkDangKy(id)
        } else {
          this.unCheckDangKy(id)
        }
      } else {
        if (remember.checked) {
          this.checkHuy(id)
        } else {
          this.unCheckHuy(id)
        }
      }
    })
  },
  methods: {
    SetDocfileButton(f) {
      this.config.f_inhoadon_visible = f
      this.config.f_mst_visible = f
      this.config.f_sdt_visible = f
      this.config.f_mig_visible = f
      this.config.f_lienhe_visible = f
      this.config.f_diachiphat_visible = f
      this.config.f_diachict_visible = f
    },
    initForm() {
      this.getDsDVQL()
      this.getDsHTTT()
      this.getDsBank()
      this.QLDL_DIACHITT_DUNGHUONG = this.getTSMD('QLDL_DIACHITT_DUNGHUONG')
    },
    checkDangKy(id) {
      const dvkid = id.split('_')[1]
      this.dsDKChecked.push(dvkid)
      this.dsDvMaster = this.dsDvMaster.map(item => ({
        ...item,
        CHECKED_DK: dvkid == item.DVKHAC_ID || item.CHECKED_DK
      }))
      this.formatData(this.dsDvMaster)
    },
    unCheckDangKy(id) {
      const dvkid = id.split('_')[1]
      const index = this.dsDKChecked.indexOf(dvkid)
      if (index > -1) {
        this.dsDKChecked.splice(index, 1)
      }
      this.dsDvMaster = this.dsDvMaster.map(item => ({
        ...item,
        CHECKED_DK: dvkid == item.DVKHAC_ID ? false : item.CHECKED_DK
      }))
      this.formatData(this.dsDvMaster)
    },
    checkHuy(id) {
      const dvkid = id.split('_')[1]
      this.dsHuyChecked.push(dvkid)
      this.dsDvMaster = this.dsDvMaster.map(item => ({
        ...item,
        CHECKED_H: dvkid == item.DVKHAC_ID || item.CHECKED_H
      }))
      this.formatData(this.dsDvMaster)
    },
    unCheckHuy(id) {
      const dvkid = id.split('_')[1]
      const index = this.dsHuyChecked.indexOf(dvkid)
      if (index > -1) {
        this.dsHuyChecked.splice(index, 1)
      }
      this.dsDvMaster = this.dsDvMaster.map(item => ({
        ...item,
        CHECKED_H: dvkid == item.DVKHAC_ID ? false : item.CHECKED_H
      }))
      this.formatData(this.dsDvMaster)
    },
    checkKH_AM() {
      if (this.$route.query && this.$route.query.AM == '1') {
        this.checkTTAM = true
        this.header.title = 'THAY ĐỔI THÔNG TIN KHÁCH HÀNG - AM'
        this.header.list[1].name = 'Thay đổi thông tin khách hàng - AM'
      }
    },
    onEnterMaTT: async function () {
      this.form.maTT = this.form.maTT.trim()
      if (!this.form.maTT) {
        this.$toast.error('Hãy nhập mã thanh toán và ấn Enter để tìm kiếm!')
        return false
      }
      if (this.form.maTT) {
        this.loading(true)
        const param = {
          pOPT: 1,
          pMa: this.form.maTT
        }
        const db = await this.getDanhBa(param)
        if (db.data.length > 0) {
          // this.thongTinTT = db.data[0];
          // this.renderTT();
          if (db.data.length === 1) {
            this.thongTinTT = db.data[0]
            this.renderTT()
          } else if (db.data.length > 1) {
            this.dsDBModal = db.data
            this.modalTitle = 'Danh sách thanh toán'
            this.$bvModal.show('bv-modal-example')
          } else {
            this.$toast.error('Không tìm thấy thông tin thanh toán')
            this.thongTinTT = {}
            this.mapperData({})
          }
        } else {
          const param = {
            pOPT: 2,
            pMa: this.form.maTT
          }
          const db = await this.getDanhBa(param)
          if (db.data.length == 1) {
            this.thongTinTT = db.data[0]
            this.renderTT()
          } else if (db.data.length > 1) {
            this.dsDBModal = db.data
            this.modalTitle = 'Danh sách thuê bao'
            this.$bvModal.show('bv-modal-example')
          } else {
            this.$toast.error('Không tìm thấy thông tin thanh toán')
            this.thongTinTT = {}
            this.mapperData({})
          }
        }
      }
      this.loading(false)
    },
    onConfirmThueBao(value) {
      this.$bvModal.hide('bv-modal-example')
      if (value) {
        this.thongTinTT = value
        this.renderTT()
        this.form.maTT = value.MA_TT
      }
    },
    onCloseThueBao() {
      this.$bvModal.hide('bv-modal-example')
    },
    async renderTT() {
      this.mapperData(this.thongTinTT)
      await Promise.all([
        this.getDsMaDaiDien(),
        this.getDBByKHId(),
        this.getDsDichVu(),
        this.getThueBaoIdByThanhToanId(),
        this.getDiemTN(),
        this.getNVQL_AM_DB_HD(),
        this.getObjDiaChi(1),
        this.getObjDiaChi(2)
      ])
    },
    async getObjDiaChi(type) {
      const idDiaChi = type === 1 ? this.thongTinTT.DIACHITT_ID : this.thongTinTT.DIACHIBC_ID
      if (!idDiaChi) return
      try {
        const rs = await this.$root.context.get(
          '/web-quantri/quan-ly-danh-ba/thongtin-diachi/' + idDiaChi
        )
        if (rs.data.length > 0) {
          const result = rs.data[0]
          let objData = {}
          objData.tinh_id = result.TINH_ID
          objData.quan_id = result.QUAN_ID
          objData.phuong_id = result.PHUONG_ID
          objData.pho_id = result.PHO_ID
          objData.ap_id = result.AP_ID
          objData.khu_id = result.KHU_ID
          objData.dacdiem_id = result.DACDIEM_ID
          objData.so_nha = result.SONHA
          objData.diachimoi = result.DIACHI
          objData.daydiachi = result.DIACHI
          objData.diachi_id = result.DIACHI_ID
          objData.diachi = result.DIACHI

          if (type === 1) {
            this.objDiaChiTT = objData
            this.diachi = { ...this.objDiaChiTT }
            //this.form.diaChiTT = objData.diachi
          } else {
            this.objDiaChiBC = objData
            // this.diachi = {...this.objDiaChiBC}
            this.form.diaChiBc = objData.diachi
          }
        }
      } catch (error) { }
    },
    openDiaChiTT() {
      this.opt_diachi = 0
      this.diachi = { ...this.objDiaChiTT }
      this.$refs.refChonDiaChi.openDialog()
    },
    openDiaChiCT() {
      if (!this.objDiaChiCT) {
        this.objDiaChiCT = { ...this.objDiaChiCT }
      }
      this.opt_diachi = 1
      this.diachi = { ...this.objDiaChiCT }
      this.$refs.refChonDiaChi.openDialog()
    },
    openDiaChiBC() {
      if (!this.objDiaChiBC) {
        this.objDiaChiBC = { ...this.objDiaChiBC }
      }
      this.opt_diachi = 2
      this.diachi = { ...this.objDiaChiBC }
      this.$refs.refChonDiaChi.openDialog()
    },
    onClickBtnDvKhac() {
      this.dsDKChecked = []
      this.dsDKChecked = this.dsDichVuKhac.map(item => item.DVKHAC_ID.toString())
      this.dsHuyChecked = []
      this.getDsDVKhac()
      this.$bvModal.show('modal-inPT')
    },
    onClickHideModal() {
      this.$bvModal.hide('modal-inPT')
    },
    onClickLienHe() {
      this.$bvModal.show('modal-ttlh')
    },
    onClickHideModalTTLH() {
      this.$bvModal.hide('modal-ttlh')
    },
    onClickEmail() {
      this.$bvModal.show('modal-email')
    },
    onClickHideModalEmail() {
      this.$bvModal.hide('modal-email')
    },
    onClickTenTT() {
      this.$bvModal.show('modal-tentt')
    },
    onClickHideModalTenTT() {
      this.$bvModal.hide('modal-tentt')
    },
    onClickDangKyInHD() {
      this.$refs.refDangKyInHDTheoFile.openDialog()
    },
    onClickMaSoThue() {
      this.kieuId = KTRA_MATT.MST
      this.$refs.refCapNhatTheoFile.openDialog()
    },
    onClickCapNhatSDT() {
      this.kieuId = KTRA_MATT.SDT
      this.$refs.refCapNhatTheoFile.openDialog()
    },
    onClickGoMIG() {
      if (this.form.thanhToanId === null || this.form.thanhToanId === '' || this.form.maTT === '') {
        this.$toast.error('Chưa có mã thanh toán để thực hiện. Hãy tìm kiếm trước khi gỡ MIG!')
        return
      }
      this.$bvModal
        .msgBoxConfirm(`Bạn có muốn gỡ mã in ghép không?`, {
          title: 'Thông báo',
          size: 'sm',
          buttonSize: 'md',
          okVariant: 'primary',
          headerClass: 'p-2 pb-0 border-bottom-0',
          footerClass: 'p-2 pt-0 border-top-0 justify-content-center',
          centered: true,
          noCloseOnBackdrop: true,
          okTitle: 'Đồng ý',
          cancelTitle: 'Không đồng ý'
        })
        .then((value) => {
          value && this.goMaInGhep()
        })
    },
    async goMaInGhep() {
      try {
        this.loading(true)
        const postData = {
          thanhToanId: this.form.thanhToanId,
          tucThi: this.form.hieuLuc
        }
        await this.$root.context.post(
          '/web-thuno/api/thu-no/quan-ly-dai-ly/cap-nhat-go-mig',
          postData
        )
        this.$toast.success('Gỡ mã in ghép thành công!')
        this.onEnterMaTT()
      } catch (error) {
        this.$toast.error('Đã phát sinh lỗi khi gỡ MIG.')
        console.log(error)
      } finally {
        this.loading(false)
      }
    },
    xacnhanCapNhatHandler(value) {
      console.log(value)
    },
    onAccept() {
      this.dsDvKhacChose = []
      const dvDK = this.dsDvMaster.filter(item => this.dsDKChecked.includes(item.DVKHAC_ID.toString())).map(item => ({
        DVKHAC_ID: item.DVKHAC_ID,
        TEN_DVKHAC: `${item.TEN_DVKHAC}(DK)`,
        kieu_yc: 1
      }))
      const dvH = this.dsDvMaster.filter(item => this.dsHuyChecked.includes(item.DVKHAC_ID.toString())).map(item => ({
        DVKHAC_ID: item.DVKHAC_ID,
        TEN_DVKHAC: `${item.TEN_DVKHAC}(Huỷ)`,
        kieu_yc: 0
      }))
      this.dsDvKhacChose = [...dvDK, ...dvH]
      console.log(this.dsDvKhacChose)
      this.$bvModal.hide('modal-inPT')
      this.dsDichVuKhac = []
      this.dsDvKhacChose.forEach(item => {
        this.dsDichVuKhac.push({
          DVKHAC_ID: item.DVKHAC_ID,
          TEN_DVKHAC: item.TEN_DVKHAC,
          kieu_yc: item.kieu_yc
        })
      })
      // console.log( this.dsDichVuKhac);
    },
    onClickNhapMoi() {
      this.mapperData({})
      this.initForm()
      this.form.maTT = ''
      this.form.diemTN = ''
      this.$refs.maTT.focus()
    },
    onClickCapNhat() {
      if (this.validateCapNhat()) {
        if (this.form.maIG !== null && this.form.maIG !== '' && (this.form.diaChiTT || this.form.diaChiCt)) {
          this.$bvModal
            .msgBoxConfirm(`Thay đổi địa chỉ BC, địa chỉ TT sẽ thay đổi địa chỉ của MIG. Bạn có đồng ý thay đổi?`, {
              title: 'Thông báo',
              size: 'sm',
              buttonSize: 'md',
              okVariant: 'primary',
              headerClass: 'p-2 pb-0 border-bottom-0',
              footerClass: 'p-2 pt-0 border-top-0 justify-content-center',
              centered: true,
              noCloseOnBackdrop: true,
              okTitle: 'Đồng ý',
              cancelTitle: 'Không đồng ý'
            })
            .then((value) => {
              value && this.capNhat()
            })
        } else {
          this.capNhat()
        }
      }
    },
    validateCapNhat() {
      if (!this.form.maTT) {
        this.$toast.error('Hãy nhập mã thanh toán và ấn Enter để tìm kiếm!')
        return false
      }

      if (!this.form.maTbDaiDien) {
        this.$toast.error('Hãy nhập mã đại diện!')
        return false
      }
      if (!this.form.idHTTT) {
        this.$toast.error('Hãy chọn hình thức thanh toán!')
        return false
      }

      if (!this.form.maNvTC) {
        this.$toast.error('Hãy chọn mã nhân viên thu cước!')
        return false
      }

      if (!this.form.maTuyen) {
        this.$toast.error('Hãy chọn mã tuyến thu!')
        return false
      }

      if (this.form.idHTTT == 3) {
        if (!this.form.stk) {
          this.$toast.error('Hãy nhập số tài khoản!')
          return false
        }
        if (this.form.bankCkb && !this.form.bankId) {
          this.$toast.error('Hãy chọn ngân hàng!')
          return false
        }
      }
      if ((!/(0[1-9])+(\d{8})/.test(this.form.soDt) || this.form.soDt.length > 11) && this.form.soDt.length > 0) {
        this.$toast.error('Số điện thoại không hợp lệ!')
        return false
      }
      let mailformat = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/
      // if((!/ ^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z0-9\-]+\.)+[a-zA-Z]{2,}))$/.test(this.form.email)) && this.form.email.length > 0) {
      if (!this.form.email.match(mailformat) && this.form.email.length > 0) {
        this.$toast.error('Email không hợp lệ!')
        return false
      }
      return true
    },
    getDsDVQL: async function () {
      try {
        const rs = await this.$root.context.get(
          '/web-thuno/api/thu-no/lay-danh-sach-don-vi-quan-ly-theo-don-vi-dl-id'
        )
        this.dsDVQL = rs.data
        if (rs.data.length > 0) {
          this.form.idDVQL = rs.data[0].DONVI_ID
        }
      } catch (error) { }
    },
    getDsHTTT: async function () {
      try {
        //const rs = await this.$root.context.get("/web-thuno/api/thu-no/common/hinh-thuc-thanh-toan");
        const rs = await this.$root.context.get(
          '/web-thuno/api/thu-no/common/hinh-thuc-thanh-toan-css'
        )
        this.dsHTTT = rs.data
        if (rs.data.length > 0) {
          this.form.idHTTT = rs.data[0].HTTT_ID
        }
      } catch (error) { }
    },
    getDsBank: async function () {
      try {
        const rs = await this.$root.context.get(
          '/web-thuno/api/thu-no/common/ngan-hang'
        )
        this.dsBank = rs.data
        // if (rs.data.length > 0) {
        //   this.form.bankId = rs.data[0].NGANHANG_ID
        // }
      } catch (error) { }
    },
    getDanhBa: async function (params) {
      try {
        const rs = await this.$root.context.get(
          '/web-thuno/api/thu-no/thong-tin-khach-hang/lay-thong-tin-thanh-toan',
          params
        )
        console.log('TTKH', rs)
        return rs
      } catch (error) { }
    },
    mapperData(srcData) {
      this.form.khachHangId = srcData.KHACHHANG_ID || ''
      this.form.thanhToanId = srcData.THANHTOAN_ID || ''
      this.form.maTbDaiDien = srcData.MATB_DD || ''
      this.form.tenTT = srcData.TEN_TT || ''
      this.form.diaChiTT = srcData.DIACHI_TT || ''
      this.form.diaChiCt = srcData.DIACHI_CT || ''
      this.form.diaChiBc = srcData.DIACHI_BC || ''
      this.form.soDt = srcData.SO_DT || ''
      this.form.email = srcData.EMAIL || ''
      this.form.ghiChu = srcData.GHICHU || ''
      this.form.maIG = srcData.MIG_ID || ''
      this.form.main_ghep = srcData.MAIN_GHEP || ''
      this.form.idDVQL = srcData.DONVI_ID || ''
      this.form.idHTTT = srcData.HTTT_ID || ''
      this.form.maNvTC = srcData.MANV_TC || ''
      this.form.maTuyen = srcData.MA_TUYEN || ''
      this.form.tuyenThuId = srcData.TUYENTHU_ID || ''
      this.form.stk = srcData.STK || ''
      this.form.mst = srcData.MST || ''
      this.form.bankId = srcData.NGANHANG_ID || ''

      if (srcData.NGANHANG_ID !== null) {
        this.form.bankCkb = true
      }
    },
    getNVQL_AM_DB_HD: async function () {
      const param = {
        pKieu: 1, // Kieu = 1 : danh ba, kieu = 2 : hop dong
        pThanhToanId: this.thongTinTT.THANHTOAN_ID
      }
      try {
        const rs = await this.$root.context.get(
          '/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-quan-ly',
          param
        )
        // this.form.nvql = rs.data[0].TEN_NV
        this.form.nvql = rs.data[0].MA_NV
      } catch (error) { }
    },
    getDsMaDaiDien: async function () {
      const param = {
        pThanhToanId: this.thongTinTT.THANHTOAN_ID
      }
      try {
        const rs = await this.$root.context.get(
          '/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-ma-dai-dien',
          param
        )
        this.dsMaDaiDien = rs.data
      } catch (error) { }
    },
    getDBByKHId: async function () {
      const param = {
        pKhachHangId: this.thongTinTT.KHACHHANG_ID
      }
      try {
        const rs = await this.$root.context.get(
          '/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-danh-ba-khach-hang-theo-khach-hang',
          param
        )
        if (rs.data.length > 0) this.form.dienThoaiKH = rs.data[0].SO_DT
      } catch (error) { }
    },
    getDsDichVu: async function () {
      const param = {
        pDanhBaId: this.thongTinTT.THANHTOAN_ID,
        pLoaiId: 2
      }
      try {
        const rs = await this.$root.context.get(
          '/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-dich-vu-khac-theo-thanh-toan',
          param
        )
        console.log('DVK', rs)
        this.dsDichVuKhac = rs.data
      } catch (error) { }
    },
    getThueBaoIdByThanhToanId: async function () {
      try {
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/quan-ly-dai-ly/thue-bao-id/thanh-toan/${this.form.thanhToanId}`
        )
        if (rs.data.length > 0) {
          this.form.thueBaoId = rs.data[0].THUEBAO_ID
        }
      } catch (error) { }
    },
    getTSMD: async function (tenThamSo) {
      try {
        var rs = await this.$root.context.get(
          '/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-tham-so-mac-dinh',
          {
            pMaThamSo: tenThamSo
          }
        )
        return rs.data.length > 0 ? rs.data[0].TEN_TS : -1
      } catch (error) {
      } finally {
      }
    },
    getDiemTN: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-thuno/api/thu-no/khoa-may-no-cuoc-theo-thanh-toan/lay-diem-tinh-nhiem',
          {
            pKhachHangId: this.form.khachHangId,
            pKyCuoc: this.kyHoaDon//"20210301"
          }
        )
        if (rs.data.length > 0) {
          this.form.diemTN = rs.data[0].DIEMTINNHIEM
        }
      } catch (error) { }
    },
    capNhat: async function () {
      const dsDVK = this.dsDvKhacChose.map(item => ({
        kieu_yc: item.kieu_yc,
        dvkhac_id: item.DVKHAC_ID
      }))
      const body = {
        pDBHienTai: this.form.danhBa,
        pThanhToanId: this.form.thanhToanId,
        pTenTT: this.form.tenTT,
        pHTTTId: this.form.idHTTT,
        pSoTaiKhoan: this.form.stk,
        pNganHangId: this.form.bankCkb ? this.form.bankId : "",
        // pTuyenThuId: this.thongTinTT.TUYENTHU_ID,
        // pTuyenThuId: this.form.maTuyen,
        pTuyenThuId: this.form.tuyenThuId,
        pMaTBDaiDien: this.form.maTbDaiDien,
        pMST: this.form.mst,
        pDiaChiTT: {
          diachi_id: this.objDiaChiTT ? this.objDiaChiTT.diachi_id : 0,
          diachi: this.form.diaChiTT || '',
          tinh_id: this.objDiaChiTT ? this.objDiaChiTT.tinh_id : 0,
          quan_id: this.objDiaChiTT ? this.objDiaChiTT.quan_id : 0,
          phuong_id: this.objDiaChiTT ? this.objDiaChiTT.phuong_id : 0,
          pho_id: this.objDiaChiTT ? this.objDiaChiTT.pho_id : 0,
          ap_id: this.objDiaChiTT ? this.objDiaChiTT.ap_id : 0,
          khu_id: this.objDiaChiTT ? this.objDiaChiTT.khu_id : 0,
          sonha: this.objDiaChiTT ? this.objDiaChiTT.so_nha : '',
          dacdiem_id: this.objDiaChiTT ? this.objDiaChiTT.dacdiem_id : 0
        },
        pDiaChiChungTu: {
          diachi_id: this.objDiaChiCT ? this.objDiaChiCT.diachi_id : 0,
          diachi: this.form.diaChiCt || '',
          tinh_id: this.objDiaChiCT ? this.objDiaChiCT.tinh_id : 0,
          quan_id: this.objDiaChiCT ? this.objDiaChiCT.quan_id : 0,
          phuong_id: this.objDiaChiCT ? this.objDiaChiCT.phuong_id : 0,
          pho_id: this.objDiaChiCT ? this.objDiaChiCT.pho_id : 0,
          ap_id: this.objDiaChiCT ? this.objDiaChiCT.ap_id : 0,
          khu_id: this.objDiaChiCT ? this.objDiaChiCT.khu_id : 0,
          sonha: this.objDiaChiCT ? this.objDiaChiCT.so_nha : '',
          dacdiem_id: this.objDiaChiCT ? this.objDiaChiCT.dacdiem_id : 0
        },
        pDiaChiBaoCuoc: {
          diachi_id: this.objDiaChiBC ? this.objDiaChiBC.diachi_id : 0,
          diachi: this.form.diaChiBc || '',
          tinh_id: this.objDiaChiBC ? this.objDiaChiBC.tinh_id : 0,
          quan_id: this.objDiaChiBC ? this.objDiaChiBC.quan_id : 0,
          phuong_id: this.objDiaChiBC ? this.objDiaChiBC.phuong_id : 0,
          pho_id: this.objDiaChiBC ? this.objDiaChiBC.pho_id : 0,
          ap_id: this.objDiaChiBC ? this.objDiaChiBC.ap_id : 0,
          khu_id: this.objDiaChiBC ? this.objDiaChiBC.khu_id : 0,
          sonha: this.objDiaChiBC ? this.objDiaChiBC.so_nha : '',
          dacdiem_id: this.objDiaChiBC ? this.objDiaChiBC.dacdiem_id : 0
        },
        pKieuApDung: this.form.hieuLuc,
        pDienThoai: this.form.soDt,
        pEmail: this.form.email,
        pDonViQLId: this.form.idDVQL,
        pGhiChu: this.form.ghiChu,
        pNhanVienQLAMId: this.checkTTAM ? this.form.nvql : null,
        pDSDKDichVuKhac: dsDVK
      }
      // console.log(body)
      try {
        this.loading(true)
        const { data } = await this.axios.put(
          '/web-thuno/api/thu-no/thong-tin-khach-hang/cap-nhat-khach-hang',
          body
        )
        this.loading(false)
        if (data && data.error === '200') {
          this.$toast.success('Cập nhật thông tin thành công!')
          await this.onEnterMaTT()
        } else {
          this.$toast.success('Cập nhật thông tin thành công!')
        }
      } catch (error) {
        // this.$toast.error(error.data.message)
        // this.$toast.error("Cập nhật thông tin thất bại!");
        if (error.data.message_detail !== undefined) {
          this.$toast.error(error.data.message_detail)
        } else {
          this.$toast.error('Đã phát sinh lỗi khi cập nhật thông tin khách hàng.')
          console.log(error)
        }
      } finally {
        this.loading(false)
      }
    },
    getDsDVKhac: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-dich-vu-khac-dbid',
          {
            pDBId: this.form.thanhToanId || 0,//1453312,
            pLoaiId: 2//1,
            // pLoaiTBId: 0,//58,
            // pKieuLDId: 0//39,
          }
        )
        this.formatData(rs.data)
      } catch (error) {
      }
    },
    formatData(data) {
      const dvUsing = this.dsDichVuKhac.map(item => item.DVKHAC_ID)
      this.dsDvMaster = data.map(item => {
        let htmlDangKy = `<div class="check-action">
            <input type="checkbox" id="DK_${item.DVKHAC_ID}" class="check"`
        if (dvUsing.includes(item.DVKHAC_ID)) htmlDangKy += `disabled`
        if (item.CHECKED_DK) htmlDangKy += `checked`
        htmlDangKy += `><span class="name"></span></div>`

        let htmlHuy = `<div class="check-action">
            <input type="checkbox" id="H_${item.DVKHAC_ID}" class="check" `
        if (!dvUsing.includes(item.DVKHAC_ID)) htmlHuy += `disabled `
        if (item.CHECKED_H) htmlHuy += `checked`
        htmlHuy += `><span class="name"></span> </div>`

        return {
          ...item,
          TRANGTHAI: dvUsing.includes(item.DVKHAC_ID) ? 'Su dung' : '',
          HTML_DANGKY: htmlDangKy,
          HTML_HUY: htmlHuy
        }
      })
    },
    getDiaChiTT(data) {
      // console.log(data)
      if (data !== undefined && data !== null) {
        try {
          if (data.quan_id !== null) {
            data.quan_id = Number(data.quan_id)
            if (data.quan_id < 0) data.quan_id = 0
          }
          if (data.phuong_id !== null) {
            data.phuong_id = Number(data.phuong_id)
            if (data.phuong_id < 0) data.phuong_id = 0
          }
          if (data.ap_id !== null) {
            data.ap_id = Number(data.ap_id)
            if (data.ap_id < 0) data.ap_id = 0
          }
          if (data.pho_id !== null) {
            data.pho_id = Number(data.pho_id)
            if (data.pho_id < 0) data.pho_id = 0
          }
          this.objDiaChiTT = data
          this.form.diaChiTT = data.diachimoi || ''
        } catch (error) {

        }
      }
    },
    getDiaChiCT(data) {
      if (data !== undefined && data !== null) {
        try {
          if (data.quan_id !== null) {
            data.quan_id = Number(data.quan_id)
            if (data.quan_id < 0) data.quan_id = 0
          }
          if (data.phuong_id !== null) {
            data.phuong_id = Number(data.phuong_id)
            if (data.phuong_id < 0) data.phuong_id = 0
          }
          if (data.ap_id !== null) {
            data.ap_id = Number(data.ap_id)
            if (data.ap_id < 0) data.ap_id = 0
          }
          if (data.pho_id !== null) {
            data.pho_id = Number(data.pho_id)
            if (data.pho_id < 0) data.pho_id = 0
          }
          this.objDiaChiCT = data
          this.form.diaChiCt = data.diachimoi || ''
        } catch (error) {

        }
      }
    },
    getDiaChiBC(data) {
      if (data !== undefined && data !== null) {
        try {
          if (data.quan_id !== null) {
            data.quan_id = Number(data.quan_id)
            if (data.quan_id < 0) data.quan_id = 0
          }
          if (data.phuong_id !== null) {
            data.phuong_id = Number(data.phuong_id)
            if (data.phuong_id < 0) data.phuong_id = 0
          }
          if (data.ap_id !== null) {
            data.ap_id = Number(data.ap_id)
            if (data.ap_id < 0) data.ap_id = 0
          }
          if (data.pho_id !== null) {
            data.pho_id = Number(data.pho_id)
            if (data.pho_id < 0) data.pho_id = 0
          }
          this.objDiaChiBC = data
          this.form.diaChiBc = data.diachimoi || ''
        } catch (error) {

        }
      }
    },
    getNVTCProcess(value) {
      this.form.maNvTC = value.maNVTC || ''
      this.form.maTuyen = value.maTuyen || ''

      this.thongTinTT.TUYENTHU_ID = value.tuyenThuId || this.thongTinTT.TUYENTHU_ID
    },
    chonNVTCHandler(value) {
      console.log(value)
      this.form.maNvTC = value.MANV_TC || ''
      this.form.maTuyen = value.MA_TUYENTHU || ''
      this.form.tuyenThuId = value.TUYENTHU_ID
      this.thongTinTT.TUYENTHU_ID = value.TUYENTHU_ID || this.thongTinTT.TUYENTHU_ID
    },
    openFormMaInGhep() {
      this.$refs.dlgMaIG.openDialog()
    },
    closeFormMaIG(data) {
      this.form.maIG = data
      this.$refs.dlgMaIG.hideDialog()
    },
    getMaIG(data) {
      // this.form.maIG = data
      this.form.maIG = data.mig_id
      this.form.main_ghep = data.main_ghep
    },
    onClickLienHeFile() {
      this.$bvModal.show('modal-ttlh-file')
    },
    onClickDiaChiPhat() {
      this.$bvModal.show('modal-diachiphat-file')
    },
    onClickDiaChiChungTu() {
      this.$bvModal.show('modal-diachichungtu-file')
    },
    async xacNhanDCHandler(args) {
      console.log(args)
      if (this.opt_diachi === 0) {
        this.objDiaChiTT = args
        this.diachi = { ...this.objDiaChiTT }
        this.form.diaChiTT = args.diachimoi || ''
      } else if (this.opt_diachi === 1) {
        this.objDiaChiCT = args
        this.form.diaChiCt = args.diachimoi || ''
      } else if (this.opt_diachi === 2) {
        this.objDiaChiBC = args
        this.diachi = { ...this.objDiaChiBC }
        this.form.diaChiBc = args.diachimoi || ''

        await this.getNVTCTheoDC()
      }
    },
    async xacnhanDangKyHandler(args) {
      console.log(args)
    },
    async getNVTCTheoDC() {
      const postData = {
        pPhuongId: this.objDiaChiBC.phuong_id,
        pPhoId: this.objDiaChiBC.pho_id,
        pApId: this.objDiaChiBC.ap_id,
        pKhuId: this.objDiaChiBC.khu_id,
        pDonViQLId: this.form.idDVQL,
        pDacDiemId: this.objDiaChiBC.dacdiem_id,
        pHTTTId: this.form.idHTTT
      }
      try {
        let rs = await this.$root.context.get('/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-nhan-vien-thu-cuoc-theo-dia-chi-va-httt', postData)
        // console.log(rs)
        if (rs.data.length > 0) {
          if (rs.data.length > 1) {
            this.$refs.dlgTimKiemNVTC.openDialog()
          } else if (rs.data.length === 1) {
            this.form.maNvTC = rs.data[0].MANV_TC
            this.form.maTuyen = rs.data[0].MA_TUYEN
            this.form.tuyenThuId = rs.data[0].TUYENTHU_ID
          } else {
            this.form.maNvTC = null
            this.form.maTuyen = null
            this.form.tuyenThuId = null
          }
        } else {
          this.form.maNvTC = null
          this.form.maTuyen = null
          this.form.tuyenThuId = null
        }
      } catch (error) { }
    },
    onFilteringNganHang(e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_NH', 'contains', e.text, true) : query
      e.updateData(this.dsBank, query)
    }
  }
}
</script>
