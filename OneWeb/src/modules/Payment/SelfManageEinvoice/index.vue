<template src="./index.html"></template>
<style src="./style.scss"></style>

<script>
import breadcrumb from '@/components/breadcrumb'
import api from './api'
import { TextBox } from '@syncfusion/ej2-inputs'
import PopupHieuChinhHddt from './popups/PopupHieuChinhHddt'
import PopupEinvoiceDetail from './popups/PopupEinvoiceDetail'
import DataGridEdit from './components/DataGrid'
import PopupTraCuuHDDT from '@/modules/Payment/eInvoice/Popup_TraCuuHDDT.vue'
import moment from 'moment'
import { DocSo } from './DocSo'
import apiHelper from './apiHelper'
import { checkMST } from './invoices.utils.js'

let proNameObj, proNameElm, proUnitObj, proUnitElm, proQuantityObj, proQuantityElm, proPriceElm, proPriceObj, totalElm, totalObj,
  vatAmountElm, vatAmountObj, amountElm, amountObj, vatRateElm, vatRateObj

export default {
  name: 'SelfManageEinvoice',
  props: {
    i_ma_gd: {
      type: String,
      default: ''
    },
    linkForm: {
      type: Number,
      default: 0
    },
    isPopup: {
      type: Boolean,
      default: false
    }
  },
  components: { breadcrumb, PopupHieuChinhHddt, PopupEinvoiceDetail, DataGridEdit, PopupTraCuuHDDT },
  data () {
    return {
      header: {
        title: 'Phát hành hóa đơn điện tử tự quản',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.buttons' } }
        ]
      },
      actions: [
        { id: 'tsbtnNhapMoi', icon: 'one-file-plus', name: 'Nhập mới', visible: true, disabled: false },
        { id: 'tsbtnGhiLai', icon: 'one-reload1', name: 'Cập nhật', visible: true, disabled: false }
      ],
      traCuuFilter: {
        txtMaGD: '',
        mauSoId: null,
        kieuDieuChinhId: null,
        thaoTacId: null,
        kyHieuId: null,
        txtSoHoaDon: 0,
        lblThanhToan: 'Đã thanh toán'
      },
      cboPattern: [],
      cboDieuChinh: [],
      cboThaoTac: [],
      cboNganHang: [],
      cboSeri: [],
      isDelayCboSeri: false,
      dsThamSoMacDinh: [],
      hoaDonFilter: {
        txtDiaChiDV: '',
        txtKyHD: '',
        txtMaKH: '',
        txtMST: '',
        txtMauHoaDon: '',
        txtTenHD: '',
        txtTenKH: '',
        txtTenDV: '',
        loaiKHId: null,
        lblKyHieu: '',
        lblSoHoaDon: null,
        txtDiaChiKH: '',
        txtHTTT: 'Tiền mặt/Chuyển khoản',
        txtFilePath: '',
        filePathBytes: [],
        txtMSTKH: '',
        txtSoDT: '',
        txtEmail: '',
        txtGhiChu: '',
        nganHangId: null,
        txtSoTaiKhoan: '',
        txtTiLeThue: '10',
        txtTongTienChiuThue10: 0,
        txtTongTienChiuThue5: 0,
        txtTongTienChiuThue0: 0,
        txtTongTienThue10: 0,
        txtTongTienThue5: 0,
        txtTongTienKhongThue: 0,
        txtTongTienKhongChiuThue: 0,
        txtTongTien: 0,
        txtTongThue: 0,
        txtExtra: '',
        txtTongTra: 0,
        txtTienChu: '',
        txtTongTienChiuThue8: 0,
        txtTongTienThue8: 0
      },
      loaiKHOptions: [],
      danhSachHoaDon: {
        headers: [
          {
            fieldName: '',
            headerText: 'Thêm',
            allowFiltering: false,
            width: 100,
            template: this.getColumnCreate(this)
          },
          {
            fieldName: '',
            headerText: 'Xóa',
            allowFiltering: false,
            width: 70,
            template: this.getColumnDelete(this)
          },
          {
            fieldName: 'STT',
            headerText: 'STT',
            width: 100,
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            isPrimaryKey: true
          },
          {
            fieldName: 'ProdName',
            headerText: 'Dịch vụ sử dụng',
            type: 'string',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: true,
            visible: true,
            edit: {
              create: () => {
                proNameElm = document.createElement('input')
                return proNameElm
              },
              read: () => {
                return proNameObj.value
              },
              destroy: () => {
                proNameObj.destroy()
              },
              write: (args) => {
                const that = this
                proNameObj = new TextBox({
                  value: args.rowData[args.column.field],
                  input: function (args) {
                    const formEle = document.getElementById('grid')
                      .querySelector('form')
                      .ej2_instances[0]
                    const prodNameValue = formEle.getInputElement('ProdName').value
                    const stt = formEle.getInputElement('STT').value
                    const found = that.lstProduct.find(elm => +elm.STT === +stt)
                    if (found) found.ProdName = prodNameValue
                  }
                })
                proNameObj.appendTo(proNameElm)
              }
            }
          },
          {
            fieldName: 'ProdUnit',
            headerText: 'Đơn vị tính',
            type: 'string',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: true,
            visible: true,
            edit: {
              create: () => {
                proUnitElm = document.createElement('input')
                return proUnitElm
              },
              read: () => {
                return proUnitObj.value
              },
              destroy: () => {
                proUnitObj.destroy()
              },
              write: (args) => {
                const that = this
                proUnitObj = new TextBox({
                  value: args.rowData[args.column.field],
                  input: function (args) {
                    const formEle = document.getElementById('grid')
                      .querySelector('form')
                      .ej2_instances[0]
                    const prodUnitValue = formEle.getInputElement('ProdUnit').value
                    if (prodUnitValue) {
                      const stt = formEle.getInputElement('STT').value
                      const found = that.lstProduct.find(elm => +elm.STT === +stt)
                      if (found) found.ProdUnit = prodUnitValue
                    }
                  }
                })
                proUnitObj.appendTo(proUnitElm)
              }
            }
          },
          {
            fieldName: 'ProdQuantity',
            headerText: 'Số lượng',
            type: 'number',
            format: 'N0',
            allowFiltering: true,
            allowSorting: true,
            allowEditing: true,
            edit: {
              create: () => {
                proQuantityElm = document.createElement('input')
                return proQuantityElm
              },
              read: () => {
                return proQuantityObj.value
              },
              destroy: () => {
                proQuantityObj.destroy()
              },
              write: (args) => {
                const that = this
                proQuantityObj = new TextBox({
                  value: args.rowData[args.column.field],
                  input: function (args) {
                    const formEle = document.getElementById('grid')
                      .querySelector('form')
                      .ej2_instances[0]
                    const proQuantityValue = +formEle.getInputElement('ProdQuantity').value
                    if (proQuantityValue >= 0) {
                      const amountEle = formEle.getInputElement('Amount')
                      const totalEle = formEle.getInputElement('Total')
                      const vatAmountEle = formEle.getInputElement('VATAmount')
                      const proPriceValue = +formEle.getInputElement('ProdPrice').value
                      const vatRateValue = +formEle.getInputElement('VATRate').value
                      const total = Math.round(proQuantityValue * proPriceValue)
                      const vatAmount = Math.round(proQuantityValue * proPriceValue * (vatRateValue < 0 ? 0 : vatRateValue) / 100)
                      const amount = Math.round(proQuantityValue * proPriceValue + (proQuantityValue * proPriceValue * vatRateValue) / 100)
                      totalEle.value = total
                      vatAmountEle.value = vatAmount
                      amountEle.value = amount
                      const stt = formEle.getInputElement('STT').value
                      const found = that.lstProduct.find(elm => +elm.STT === +stt)
                      if (found) {
                        found.ProdQuantity = proQuantityValue
                        found.Total = total
                        found.VATAmount = vatAmount
                        found.Amount = amount
                      }
                      that.setCellChange()
                    }
                  }
                })
                proQuantityObj.appendTo(proQuantityElm)
              }
            },
            visible: true
          },
          {
            fieldName: 'ProdPrice',
            headerText: 'Đơn giá',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: true,
            type: 'number',
            format: 'N2',
            edit: {
              create: () => {
                proPriceElm = document.createElement('input')
                return proPriceElm
              },
              read: () => {
                return proPriceObj.value
              },
              destroy: () => {
                proPriceObj.destroy()
              },
              write: (args) => {
                const that = this
                proPriceObj = new TextBox({
                  value: args.rowData[args.column.field],
                  input: function (args) {
                    const formEle = document.getElementById('grid')
                      .querySelector('form')
                      .ej2_instances[0]
                    const proPriceValue = +formEle.getInputElement('ProdPrice').value
                    if (proPriceValue >= 0) {
                      const amountEle = formEle.getInputElement('Amount')
                      const totalEle = formEle.getInputElement('Total')
                      const vatAmountEle = formEle.getInputElement('VATAmount')
                      const proQuantityValue = +formEle.getInputElement('ProdQuantity').value
                      const vatRateValue = +formEle.getInputElement('VATRate').value
                      const total = Math.round(proPriceValue * proQuantityValue)
                      const vatAmount = Math.round(proPriceValue * proQuantityValue * (vatRateValue < 0 ? 0 : vatRateValue) / 100)
                      const amount = Math.round(proPriceValue * proQuantityValue + (proPriceValue * proQuantityValue * vatRateValue) / 100)
                      totalEle.value = total
                      vatAmountEle.value = vatAmount
                      amountEle.value = amount
                      const stt = formEle.getInputElement('STT').value
                      const found = that.lstProduct.find(elm => +elm.STT === +stt)
                      if (found) {
                        found.ProdPrice = proPriceValue
                        found.Total = total
                        found.VATAmount = vatAmount
                        found.Amount = amount
                      }
                      that.setCellChange()
                    }
                  }
                })
                proPriceObj.appendTo(proPriceElm)
              }
            },
            visible: true
          },
          {
            fieldName: 'Total',
            headerText: 'Thành tiền (trước thuế)',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: true,
            visible: true,
            type: 'number',
            format: 'N2',
            edit: {
              create: () => {
                totalElm = document.createElement('input')
                return totalElm
              },
              read: () => {
                return totalObj.value
              },
              destroy: () => {
                totalObj.destroy()
              },
              write: (args) => {
                const that = this
                totalObj = new TextBox({
                  value: args.rowData[args.column.field],
                  input: function (args) {
                    const formEle = document.getElementById('grid')
                      .querySelector('form')
                      .ej2_instances[0]
                    const stt = formEle.getInputElement('STT').value
                    const found = that.lstProduct.find(elm => +elm.STT === +stt)
                    if (found) found.Total = +formEle.getInputElement('Total').value || 0
                  }
                })
                totalObj.appendTo(totalElm)
              }
            }
          },
          {
            fieldName: 'VATRate',
            headerText: 'VAT',
            type: 'number',
            format: 'N0',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: true,
            visible: true,
            edit: {
              create: () => {
                vatRateElm = document.createElement('input')
                return vatRateElm
              },
              read: () => {
                return vatRateObj.value
              },
              destroy: () => {
                vatRateObj.destroy()
              },
              write: (args) => {
                const that = this
                vatRateObj = new TextBox({
                  value: args.rowData[args.column.field],
                  input: function (args) {
                    const formEle = document.getElementById('grid')
                      .querySelector('form')
                      .ej2_instances[0]
                    const vatAmountEle = formEle.getInputElement('VATAmount')
                    const totalValue = +formEle.getInputElement('Total').value
                    const vatRateValue = +formEle.getInputElement('VATRate').value
                    const vatAmount = Math.round(totalValue * (vatRateValue < 0 ? 0 : vatRateValue) / 100)
                    vatAmountEle.value = vatAmount
                    const amountEle = formEle.getInputElement('Amount')
                    const amount = totalValue + vatAmount
                    amountEle.value = amount
                    const stt = formEle.getInputElement('STT').value
                    const found = that.lstProduct.find(elm => +elm.STT === +stt)
                    if (found) {
                      found.VATRate = vatRateValue || 0
                      found.VATAmount = vatAmount || 0
                      found.Amount = amount || 0
                    }
                    that.setCellChange()
                    let pattern1 = true
                    that.isDelayCboSeri = true
                    if (that.lstProduct.length > 1) {
                      const vatRate = +that.lstProduct[0].VATRate
                      if (that.lstProduct.slice(1).some(elm => elm.ProdName && +elm.VATRate !== vatRate)) pattern1 = false
                    }
                    if (that.cboPattern.length > 1) {
                      that.traCuuFilter.mauSoId = pattern1
                        ? that.cboPattern[0].LOAIHOADON_ID
                        : that.cboPattern[1].LOAIHOADON_ID
                    } else if (that.cboPattern.length === 1) {
                      that.traCuuFilter.mauSoId = that.cboPattern[0].LOAIHOADON_ID
                    } else {
                      that.traCuuFilter.mauSoId = null
                    }
                  }
                })
                vatRateObj.appendTo(vatRateElm)
              }
            }
          },
          {
            fieldName: 'VATAmount',
            headerText: 'Tiền thuế',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: true,
            visible: true,
            type: 'number',
            format: 'N0',
            edit: {
              create: () => {
                vatAmountElm = document.createElement('input')
                return vatAmountElm
              },
              read: () => {
                return vatAmountObj.value
              },
              destroy: () => {
                vatAmountObj.destroy()
              },
              write: (args) => {
                const that = this
                vatAmountObj = new TextBox({
                  value: args.rowData[args.column.field],
                  input: function (args) {
                    const formEle = document.getElementById('grid')
                      .querySelector('form')
                      .ej2_instances[0]
                    const amountEle = formEle.getInputElement('Amount')
                    const totalValue = +formEle.getInputElement('Total').value
                    const vatAmountValue = +formEle.getInputElement('VATAmount').value
                    const amount = totalValue + vatAmountValue
                    amountEle.value = amount
                    const stt = formEle.getInputElement('STT').value
                    const found = that.lstProduct.find(elm => +elm.STT === +stt)
                    if (found) {
                      found.Amount = amount || 0
                      found.VATAmount = vatAmountValue || 0
                    }
                    that.setCellChange()
                  }
                })
                vatAmountObj.appendTo(vatAmountElm)
              }
            }
          },
          {
            fieldName: 'Amount',
            headerText: 'Thành tiền (sau thuế)',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: true,
            customAttributes: { class: 'tienSauThueText' },
            type: 'number',
            format: 'N0',
            visible: true,
            edit: {
              create: () => {
                amountElm = document.createElement('input')
                return amountElm
              },
              read: () => {
                return amountObj.value
              },
              destroy: () => {
                amountObj.destroy()
              },
              write: (args) => {
                const that = this
                amountObj = new TextBox({
                  value: args.rowData[args.column.field],
                  input: function (args) {
                    const formEle = document.getElementById('grid')
                      .querySelector('form')
                      .ej2_instances[0]
                    const stt = formEle.getInputElement('STT').value
                    const found = that.lstProduct.find(elm => +elm.STT === +stt)
                    if (found) found.Amount = +formEle.getInputElement('Amount').value || 0
                    that.setObjectHDDTEditCellChange()
                  }
                })
                amountObj.appendTo(amountElm)
              }
            }
          },
          {
            fieldName: 'TinhVat',
            headerText: 'Tính VAT',
            type: 'boolean',
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false,
            visible: false
          }
        ],
        editSettings: {
          allowEditing: true,
          allowAdding: true,
          allowDeleting: true,
          mode: 'Normal'
        },
        dataSources: [],
        visibleColumns: {
          total: false,
          amount: false,
          vatRate: false,
          vatAmount: false
        }
      },
      tagForm: null,
      hoi_capnhat: false,
      them_extra: false,
      xemtruoc_hddt: false,
      vxuat_hd_nhieumucthue: false,
      visibleTinhVATColumn: false,
      tt78_stk_nh: false,
      isDisabledCboDieuChinh: false,
      vma_kh: '',
      vnguoicn: '',
      bdhddt_cha_id: 0,
      vma_gd: '',
      t_tien_edited: 0,
      vten_nh: '',
      vdiachi_tt: '',
      vhdkh_id: 0,
      _pattern: '',
      _seri: '',
      _so_hd: null,
      Fkey: '',
      da_xuathd: false,
      objHoaDonDienTu: {
        ArisingDate: '',
        InvoiceName: '',
        InvoicePattern: '',
        SerialNo: '',
        InvoiceNo: '',
        Kind_of_Payment: '',
        ComName: '',
        ComTaxCode: '',
        ComAddress: '',
        ComPhone: '',
        ComBankNo: '',
        CusCode: '',
        MaKhachHang: '',
        CusName: '',
        CusTaxCode: '',
        CusPhone: '',
        CusAddress: '',
        CusBankName: '',
        CusBankNo: '',
        Total: 0,
        VAT_Amount: 0,
        Amount: 0,
        Amount_words: '',
        Buyer: '',
        KindOfService: '',
        VAT_Rate: 0,
        EmailDeliver: '',
        Extra: '',
        Products: [],
        MaThanhToan: ''
      },
      objHoaDonDienTuEdit: {
        ArisingDate: '',
        InvoiceName: '',
        InvoicePattern: '',
        SerialNo: '',
        InvoiceNo: '',
        Kind_of_Payment: '',
        ComName: '',
        ComTaxCode: '',
        ComAddress: '',
        ComPhone: '',
        ComBankNo: '',
        CusCode: '',
        MaKhachHang: '',
        CusName: '',
        CusTaxCode: '',
        CusPhone: '',
        CusAddress: '',
        CusBankName: '',
        CusBankNo: '',
        Total: 0,
        VAT_Amount: 0,
        Amount: 0,
        Amount_words: '',
        Buyer: '',
        KindOfService: '',
        VAT_Rate: 0,
        EmailDeliver: '',
        Extra: '',
        Products: [],
        MaThanhToan: ''
      },
      first: true,
      visibleBankAccount: true,
      lstProduct: [],
      invToken: '',
      vtoken_show: '',
      DS_HOPDONG_TB_HDDT: [],
      type: 0,
      tiengoc: 0,
      hienthitt: null,
      thongTinNguoiDung: null,
      BDHDDT_ID: 0,
      fkey_new: '',
      hieuChinhHDDTProp: '',
      position: { X: 'center', Y: 'top' },
      isHienThiTTHD: false,
      isVTTTraCuuHDDT: false,
      machine: '',
    }
  },
  computed: {
    phanVungId () {
      return +this.$root.token.getPhanVungID()
    },
    nhanVienId () {
      return +this.$root.token.getNhanVienID()
    },
    donViId () {
      return +this.$root.token.getDonViID()
    },
    nguoiDungId () {
      return +this.$root.token.getNguoiDungID()
    },
    maNguoiDung () {
      return this.thongTinNguoiDung ? this.thongTinNguoiDung.ma_nd : ''
    },
    maNhanVien () {
      return this.thongTinNguoiDung ? this.thongTinNguoiDung.ma_nv : ''
    },
    acPassHDDT () {
      return this.thongTinNguoiDung ? this.thongTinNguoiDung.pass_hddt_td : ''
    },
    accountHDDT () {
      return this.thongTinNguoiDung ? this.thongTinNguoiDung.user_hddt_td : ''
    }
  },
  async created () {
    this.loading(true)
    try {
      const [nguoiDungResponse, machineResponse] = await Promise.all([
        this.getThongTinNguoiDung2(),
        this.getMachine()
      ])
      this.thongTinNguoiDung = nguoiDungResponse
      this.machine = machineResponse
      this.$refs.maKHRef.focus()
      if (!this.isPopup) await this.frmXuat_HoaDon_DienTu_Dong_Load()
    } catch (e) {
    } finally {
      this.loading(false)
    }
  },
  methods: {
    async getThongTinNguoiDung2 () {
      try {
        const response = await api.getThongTinNguoiDung2(this.axios)
        const data = await response.data
        return data.error_code === 'BSS-00000000' ? data.data : null
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin người dùng')
        return null
      }
    },
    async getMachine () {
      try {
        const response = await api.getMachine(this.axios)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' && data.data ? data.data.mayCn : ''
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin machine')
        return ''
      }
    },
    async frmXuat_HoaDon_DienTu_Dong_Load () {
      this.getTag()
      try {
        const response = await api.fn_xuat_hddt_tq_load(
          this.axios,
          { ds_param: JSON.stringify({ tag_form: this.tagForm, nguoidung_id: this.nguoiDungId }) }
        )
        const result = await response.data
        if (result.error_code === 'BSS-00000000' && result.error === '200') {
          const data = JSON.parse(result.data.data)
          this.setComBoData({
            dsDieuChinh: data.DS_DIEUCHINH,
            dsLoaiHoaDon: data.DS_LOAI_HOADON,
            dsLoaiKH: data.DS_LOAI_KH,
            dsThamSoMD: data.DS_THAMSO_MD,
            dsThaoTac: data.DS_THAOTAC,
            dsNganHang: []
          })
          if (data.DS_LOAI_HOADON.length > 0) {
            this.traCuuFilter.mauSoId = data.DS_LOAI_HOADON[0].LOAIHOADON_ID
          }
          if (data.DS_THAOTAC.length > 0) {
            this.traCuuFilter.thaoTacId = data.DS_THAOTAC[0].THAOTAC_ID
          }
          this.traCuuFilter.kieuDieuChinhId = this.cboDieuChinh.length > 0 ? this.cboDieuChinh[0].DIEUCHINH_ID : null
          this.isDisabledCboDieuChinh = this.traCuuFilter.thaoTacId !== 2
          this.clean()
          if (data.DS_LOAI_KH.length > 0) this.hoaDonFilter.loaiKHId = data.DS_LOAI_KH[0].LOAIKH_ID
          if (this.tagForm === 2) {
            this.hoaDonFilter.txtHTTT = ''
          }

          if (this.linkForm === 1) {
            this.traCuuFilter.thaoTacId = this.cboThaoTac.length > 6 ? this.cboThaoTac[6].THAOTAC_ID : null
            this.traCuuFilter.txtMaGD = this.i_ma_gd
            await this.HienThi_ThongTin_HopDong()
          }
          if (this.dsThamSoMacDinh.length > 0) {
            const KIEMTRA_HUY_HDDT = this.getThamSoByMaThamSo('KIEMTRA_HUY_HDDT')
            if (KIEMTRA_HUY_HDDT && KIEMTRA_HUY_HDDT.TEN_TS.toString().trim() === '1') this.hoi_capnhat = true
            const THEM_HDDT_EXTRA = this.getThamSoByMaThamSo('THEM_HDDT_EXTRA')
            if (THEM_HDDT_EXTRA && THEM_HDDT_EXTRA.TEN_TS.toString().trim() === '1') this.them_extra = true
            const XEMTRUOC_HDDT = this.getThamSoByMaThamSo('XEMTRUOC_HDDT')
            if (XEMTRUOC_HDDT && XEMTRUOC_HDDT.TEN_TS.toString().trim() === '1') this.xemtruoc_hddt = true
            const XUAT_HD_NHIEUMUCTHUE = this.getThamSoByMaThamSo('XUAT_HD_NHIEUMUCTHUE')
            if (XUAT_HD_NHIEUMUCTHUE && XUAT_HD_NHIEUMUCTHUE.TEN_TS.toString().trim() === '1') {
              this.vxuat_hd_nhieumucthue = true
              this.visibleTinhVATColumn = true
            }
            const HDDT_THONGTU_78_STK_NH = this.getThamSoByMaThamSo('HDDT_THONGTU_78_STK_NH')
            if (HDDT_THONGTU_78_STK_NH && HDDT_THONGTU_78_STK_NH.TEN_TS.toString().trim() === '1') {
              this.tt78_stk_nh = true
              this.cboNganHang = data.DS_NGANHANG || []
            }
            this.HienThi_GiaoDien_TT78()
          }
        } else {
          this.setComBoData({
            dsDieuChinh: [],
            dsLoaiHoaDon: [],
            dsLoaiKH: [],
            dsThamSoMD: [],
            dsThaoTac: [],
            dsNganHang: []
          })
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hóa đơn')
        this.setComBoData({
          dsDieuChinh: [],
          dsLoaiHoaDon: [],
          dsLoaiKH: [],
          dsThamSoMD: [],
          dsThaoTac: [],
          dsNganHang: []
        })
      }
    },
    getTag () {
      try {
        const tag = this.$route.query.tag
        if (tag && tag.length > 0) {
          const tags = tag.split('+')
          if (tags.length > 0) this.tagForm = +tags[0]
        } else {
          this.tagForm = 0
        }
      } catch (e) {
        this.$root.$toast.error('Tham số tag không hợp lệ')
        this.tagForm = 0
      }
    },
    setComBoData ({ dsDieuChinh, dsLoaiHoaDon, dsLoaiKH, dsThamSoMD, dsThaoTac, dsNganHang }) {
      if (dsDieuChinh.length > 0) {
        const emptyDieuChinh = dsDieuChinh.find(elm => !elm.DIEUCHINH)
        if (emptyDieuChinh) emptyDieuChinh.DIEUCHINH = 'Chưa chọn'
      }
      this.cboDieuChinh = dsDieuChinh
      this.cboPattern = dsLoaiHoaDon
      this.loaiKHOptions = dsLoaiKH
      this.dsThamSoMacDinh = dsThamSoMD
      if (dsThaoTac.length > 0) {
        const emptyThaoTac = dsThaoTac.find(elm => !elm.THAOTAC)
        if (emptyThaoTac) emptyThaoTac.THAOTAC = 'Chưa chọn'
      }
      this.cboThaoTac = dsThaoTac
      this.cboNganHang = dsNganHang
    },
    async HienThi_ThongTin_HopDong () {
      const lsPro = []
      let tongtien = 0
      let tien = 0
      let vat = 0
      let counter = 1
      this.clean()
      this.resetObjHoaDonDienTu()
      this.resetObjHoaDonDienTuEdit()
      let thongTinNew = null
      const thongTin = await this.xuatHddtTQHienThiThongTinHopDong(
        {
          maGD: this.traCuuFilter.txtMaGD,
          loaihdId: 0,
          tthdId: 0,
          donviId: 0,
          nhanvienId: 0,
          donviDLId: 0,
          dichvuvtId: 0,
          tungayYc: '',
          denngayYc: '',
          hdkhId: 0,
          getServiceInfo: 0
        }
      )
      if (thongTin && thongTin.DS_HOPDONG_HDDT.length > 0) {
        this.DS_HOPDONG_TB_HDDT = thongTin.DS_HOPDONG_TB_HDDT
        const hopDong = thongTin.DS_HOPDONG_HDDT[0]
        this.da_xuathd = false
        this.vhdkh_id = hopDong.HDKH_ID
        if (+hopDong.KIEU === 1) {
          this.$root.$toast.warning('Hợp đồng đã xuất hóa đơn giấy. Bạn không được thực hiện thao tác HĐĐT')
          return false
        }
        this.traCuuFilter.lblThanhToan = ''
        if (hopDong.HDDT_FKEY) {
          this.bdhddt_cha_id = +thongTin.BDHDDT_CHA_ID || 0
          this.da_xuathd = true
          const kq = await this.listInvByCus(this.traCuuFilter.txtMaGD, '', '')
          if (!kq) return false
          if (kq.errorCode !== 0) {
            this.$root.$toast.error(kq.faultString)
            return false
          }
          const xmlDoc = this.parseXmlDoc(kq.data)
          if (xmlDoc.getElementsByTagName('Item').length > 0) {
            this.isHienThiTTHD = true
            this.$refs.popupTraCuuHDDTRef.showModal()
            return true
          }
          this.$root.$toast.warning('Không tìm thấy thông tin hóa đơn !')
          return false
        }
        this.hoaDonFilter.txtMaKH = hopDong.MA_KH
        thongTinNew = await this.xuatHddtTQHienThiThongTinHopDong(
          {
            maGD: this.traCuuFilter.txtMaGD,
            loaihdId: 0,
            tthdId: 0,
            donviId: 0,
            nhanvienId: 0,
            donviDLId: 0,
            dichvuvtId: 0,
            tungayYc: '',
            denngayYc: '',
            hdkhId: this.vhdkh_id,
            getServiceInfo: 1
          }
        )
        if (thongTinNew && thongTinNew.DS_HOPDONG_TT && thongTinNew.DS_HOPDONG_TT.length > 0) {
          this.hoaDonFilter.txtMSTKH = thongTinNew.DS_HOPDONG_TT[0].MST
          this.hoaDonFilter.txtDiaChiKH = thongTinNew.DS_HOPDONG_TT[0].DIACHI_CT
          this.hoaDonFilter.txtTenKH = thongTinNew.DS_HOPDONG_TT[0].TEN_TT
        }
        this.hoaDonFilter.txtHTTT = 'Tiền mặt/Chuyển khoản'
        if (hopDong.EMAIL_TT) {
          this.hoaDonFilter.txtEmail = hopDong.EMAIL_TT
        } else if (thongTinNew && thongTinNew.DS_DBKH && thongTinNew.DS_DBKH.length > 0) {
          this.hoaDonFilter.txtEmail = thongTinNew.DS_DBKH[0].EMAIL_TT
        } else this.hoaDonFilter.txtEmail = ''
        if (thongTinNew && thongTinNew.DS_HDTB && thongTinNew.DS_HDTB.length > 0) {
          this.hoaDonFilter.txtSoDT = thongTinNew.DS_HDTB[0].MA_TB
        } else this.hoaDonFilter.txtSoDT = ''
      } else {
        this.$root.$toast.warning('Không tìm thấy thông tin hợp đồng !')
        this.setButtonDisabled('tsbtnGhiLai', true)
        this.clean()
        return false
      }
      if (!thongTinNew) {
        thongTinNew = await this.xuatHddtTQHienThiThongTinHopDong(
          {
            maGD: this.traCuuFilter.txtMaGD,
            loaihdId: 0,
            tthdId: 0,
            donviId: 0,
            nhanvienId: 0,
            donviDLId: 0,
            dichvuvtId: 0,
            tungayYc: '',
            denngayYc: '',
            hdkhId: this.vhdkh_id,
            getServiceInfo: 1
          }
        )
      }
      if (thongTinNew && thongTinNew.DS_DB && thongTinNew.DS_DB.length > 0) {
        thongTinNew.DS_DB.forEach(tb => {
          lsPro.push({
            Code: '',
            Total: 0,
            ProdName: tb.PRODNAME,
            ProdUnit: tb.PRODUNIT,
            ProdQuantity: +tb.PRODQUANTITY,
            ProdPrice: +tb.PRODPRICE,
            Discount: 0,
            DiscountAmount: 0,
            VATRate: 0,
            VATAmount: 0,
            Amount: +tb.AMOUNT,
            TinhVat: false
          })
          tien += tb.TIEN
          vat += tb.VAT
          tongtien += tb.TONGTIEN
        })
      }
      for (let i = 0; i < 3; i++) {
        lsPro.push({
          Code: '',
          Total: 0,
          ProdName: '',
          ProdUnit: '',
          ProdQuantity: 0,
          ProdPrice: 0,
          Discount: 0,
          DiscountAmount: 0,
          VATRate: 0,
          VATAmount: 0,
          Amount: 0,
          TinhVat: false
        })
      }
      this.lstProduct = lsPro.map(product => ({
        STT: counter++,
        Code: product.Code,
        Total: product.Total,
        ProdName: product.ProdName,
        ProdUnit: product.ProdUnit,
        ProdQuantity: product.ProdQuantity,
        ProdPrice: product.ProdPrice,
        Discount: product.Discount,
        DiscountAmount: product.DiscountAmount,
        VATRate: product.VATRate,
        VATAmount: product.VATAmount,
        Amount: product.Amount,
        TinhVat: product.TinhVat
      }))
      this.danhSachHoaDon.dataSources = [...this.lstProduct]
      this.hoaDonFilter.txtTongTien = tien
      this.hoaDonFilter.txtTongThue = vat
      this.hoaDonFilter.txtTongTra = tongtien
      this.t_tien_edited = tongtien
      this.hoaDonFilter.txtTienChu = DocSo(Math.abs(this.hoaDonFilter.txtTongTra))
      this.setButtonDisabled('tsbtnGhiLai', false)
      this.setButtonDisabled('tsbtnNhapMoi', true)
    },
    parseXmlDoc (xml) {
      let xmlDoc
      if (window.DOMParser) {
        const parser = new DOMParser()
        xmlDoc = parser.parseFromString(xml.trim(), 'text/xml')
      } else {
        xmlDoc = new ActiveXObject('Microsoft.XMLDOM')
        xmlDoc.async = false
        xmlDoc.loadXML(xml.trim())
      }
      return xmlDoc
    },
    resetObjHoaDonDienTu () {
      this.objHoaDonDienTu = {
        ArisingDate: '',
        InvoiceName: '',
        InvoicePattern: '',
        SerialNo: '',
        InvoiceNo: '',
        Kind_of_Payment: '',
        ComName: '',
        ComTaxCode: '',
        ComAddress: '',
        ComPhone: '',
        ComBankNo: '',
        CusCode: '',
        MaKhachHang: '',
        CusName: '',
        CusTaxCode: '',
        CusPhone: '',
        CusAddress: '',
        CusBankName: '',
        CusBankNo: '',
        Total: 0,
        VAT_Amount: 0,
        Amount: 0,
        Amount_words: '',
        Buyer: '',
        KindOfService: '',
        VAT_Rate: 0,
        EmailDeliver: '',
        Extra: '',
        Products: [],
        MaThanhToan: ''
      }
    },
    resetObjHoaDonDienTuEdit () {
      this.objHoaDonDienTuEdit = {
        ArisingDate: '',
        InvoiceName: '',
        InvoicePattern: '',
        SerialNo: '',
        InvoiceNo: '',
        Kind_of_Payment: '',
        ComName: '',
        ComTaxCode: '',
        ComAddress: '',
        ComPhone: '',
        ComBankNo: '',
        CusCode: '',
        MaKhachHang: '',
        CusName: '',
        CusTaxCode: '',
        CusPhone: '',
        CusAddress: '',
        CusBankName: '',
        CusBankNo: '',
        Total: 0,
        VAT_Amount: 0,
        Amount: 0,
        Amount_words: '',
        Buyer: '',
        KindOfService: '',
        VAT_Rate: 0,
        EmailDeliver: '',
        Extra: '',
        Products: [],
        MaThanhToan: ''
      }
    },
    async xuatHddtTQHienThiThongTinHopDong (
      { maGD, loaihdId, tthdId, donviId, nhanvienId, donviDLId, dichvuvtId, tungayYc, denngayYc, hdkhId, getServiceInfo }
    ) {
      try {
        const response = await api.xuatHddtTQHienThiThongTinHopDong(
          this.axios,
          {
            ds_param: JSON.stringify({
              ma_gd: maGD,
              loaihd_id: loaihdId,
              tthd_id: tthdId,
              donvi_id: donviId,
              nhanvien_id: nhanvienId,
              donvi_dl_id: donviDLId,
              dichvuvt_id: dichvuvtId,
              tungay_yc: tungayYc,
              denngay_yc: denngayYc,
              hdkh_id: hdkhId,
              get_service_info: getServiceInfo
            })
          }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' && data.data.ret === '1'
          ? JSON.parse(data.data.data)
          : null
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hợp đồng')
        return null
      }
    },
    async txtMaGD_KeyPress () {
      if (this.traCuuFilter.txtMaGD) {
        this.loading(true)
        try {
          await this.HienThi_ThongTin()
          await this.KiemTra_DieuChinh_TT_HD()
          if (this.traCuuFilter.thaoTacId === 1 || this.traCuuFilter.thaoTacId === 6) {
            this.hoaDonFilter.txtDiaChiDV = ''
            this.hoaDonFilter.txtExtra = ''
            this.hoaDonFilter.txtFilePath = ''
            //this.hoaDonFilter.txtGhiChu = ''
            this.hoaDonFilter.txtHTTT = this.tagForm === 2 ? '' : 'Tiền mặt/Chuyển khoản'
            this.hoaDonFilter.txtKyHD = ''
            this.hoaDonFilter.txtMauHoaDon = ''
            this.hoaDonFilter.txtMST = ''
            this.hoaDonFilter.txtTenDV = ''
            this.hoaDonFilter.txtTenHD = ''
            this.hoaDonFilter.lblKyHieu = ''
            this.hoaDonFilter.lblSoHoaDon = null
          }
          this.lstProduct = []
          for (let i = 1; i < 16; i++) {
            this.lstProduct.push({
              STT: i,
              Code: '',
              Total: 0,
              ProdName: '',
              ProdUnit: '',
              ProdQuantity: 0,
              ProdPrice: 0,
              Discount: 0,
              DiscountAmount: 0,
              VATRate: 0,
              VATAmount: 0,
              Amount: 0,
              TinhVat: false
            })
          }
          this.danhSachHoaDon.dataSources = [...this.lstProduct]
          this.hoaDonFilter.txtTongTien = 0
          this.hoaDonFilter.txtTongThue = 0
          this.hoaDonFilter.txtTongTra = 0
          this.hoaDonFilter.txtTienChu = DocSo(Math.abs(this.hoaDonFilter.txtTongTra))
        } catch (e) {
        } finally {
          this.loading(false)
        }
      }
    },
    async HienThi_ThongTin () {
      this.clean()
      this.da_xuathd = false
      this.resetObjHoaDonDienTu()
      this.resetObjHoaDonDienTuEdit()
      this.vma_gd = this.traCuuFilter.txtMaGD
      const thongTin = await this.xuatHddtTQHienThiThongTin({
        maGD: this.traCuuFilter.txtMaGD,
        fkey: '',
        tagForm: this.tagForm,
        thaoTacId: this.traCuuFilter.thaoTacId,
        getServiceInfo: 0
      })

      if (thongTin && thongTin.DS_KH.length > 0) {
        this.hienthitt = thongTin.DS_KH[0]
        this.da_xuathd = true
        this.Fkey = ''
        const kq = await this.listInvByCus(this.traCuuFilter.txtMaGD, '', '')
        if (!kq) return false
        if (kq.errorCode !== 0) {
          this.$root.$toast.error(kq.faultString)
          return false
        }
        const xmlDoc = this.parseXmlDoc(kq.data)
        if (xmlDoc.getElementsByTagName('Item').length > 0) {
          this.isVTTTraCuuHDDT = this.tagForm === 2
          this.isHienThiTTHD = false
          await this.$refs.popupTraCuuHDDTRef.TraCuuTheoMaGD()
          this.$refs.popupTraCuuHDDTRef.showModal()
          return true
        }
        this.$root.$toast.warning('Không tìm thấy thông tin hóa đơn !')
        return false
      }
      if (this.traCuuFilter.thaoTacId !== 1 && this.traCuuFilter.thaoTacId !== 6) {
        this.$root.$toast.warning('Không tìm thấy thông tin hóa đơn đã phát hành!')
        return false
      }
      this.setButtonDisabled('tsbtnGhiLai', false)
      this.setButtonDisabled('tsbtnNhapMoi', true)
    },
    async xuatHddtTQHienThiThongTin ({ maGD, fkey, tagForm, thaoTacId, getServiceInfo}) {
      try {
        const response = await api.xuatHddtTQHienThiThongTin(
          this.axios,
          {
            ds_param: JSON.stringify({
              ma_gd: maGD,
              fkey: fkey,
              tag_form: tagForm,
              thaotac_id: thaoTacId,
              get_service_info: getServiceInfo
            })
          }
        )
        const data = apiHelper.getDataFromResponseApiReturnRaise(response)

        if(!data) return null;
        return  data.ret == '1' ? JSON.parse(data.data) : null

      } catch (e) {
        console.log(e);
        this.$root.$toast.error('Không thể lấy thông tin hợp đồng')
        return null
      }
    },
    async listInvByCus (cusCode, fromDate, toDate) {
      try {
        const response = await api.listInvByCus(this.axios, { cusCode, fromDate, toDate })
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hóa đơn')
        return null
      }
    },
    async onSuccessTC_HDDT (data) {
      this.loading(true)
      try {
        if (this.isHienThiTTHD) {
          if (data.vchapnhan) {
            const lsPro = []
            let tien = 0
            let vat = 0
            let tongtien = 0
            this.Fkey = data._fkey
            await this.TraCuu(data._pattern, data._seri, +data._so_hd)
            const thongTin = await this.xuatHddtTQHienThiThongTinHopDong(
              {
                maGD: this.traCuuFilter.txtMaGD,
                loaihdId: 0,
                tthdId: 0,
                donviId: 0,
                nhanvienId: 0,
                donviDLId: 0,
                dichvuvtId: 0,
                tungayYc: '',
                denngayYc: '',
                hdkhId: this.vhdkh_id,
                getServiceInfo: 1
              }
            )
            if (thongTin && thongTin.DS_HOPDONG_TB_HDDT_TMP && thongTin.DS_HOPDONG_TB_HDDT_TMP.length > 0) {
              for (const hopDong of thongTin.DS_HOPDONG_TB_HDDT_TMP) {
                const product = {
                  Code: '',
                  Total: Math.abs(+hopDong.TOTAL),
                  ProdName: hopDong.PRODNAME,
                  ProdUnit: hopDong.PRODUNIT,
                  ProdQuantity: Math.abs(+hopDong.PRODQUANTITY),
                  ProdPrice: Math.abs(+hopDong.PRODPRICE),
                  Discount: 0,
                  DiscountAmount: 0,
                  VATRate: 0,
                  VATAmount: Math.abs(+hopDong.VATAmount),
                  Amount: Math.abs(+hopDong.AMOUNT),
                  TinhVat: false
                }
                lsPro.push(product)
                tien += +hopDong.tien
                vat += +hopDong.vat
                tongtien += +hopDong.tongtien
              }
              for (let i = 0; i < 3; i++) {
                lsPro.push({
                  Code: '',
                  Total: 0,
                  ProdName: '',
                  ProdUnit: '',
                  ProdQuantity: 0,
                  ProdPrice: 0,
                  Discount: 0,
                  DiscountAmount: 0,
                  VATRate: 0,
                  VATAmount: 0,
                  Amount: 0,
                  TinhVat: false
                })
              }
              let counter = 1
              this.lstProduct = lsPro.map(product => ({
                STT: counter++,
                Code: product.Code,
                Total: product.Total,
                ProdName: product.ProdName,
                ProdUnit: product.ProdUnit,
                ProdQuantity: product.ProdQuantity,
                ProdPrice: product.ProdPrice,
                Discount: product.Discount,
                DiscountAmount: product.DiscountAmount,
                VATRate: product.VATRate,
                VATAmount: product.VATAmount,
                Amount: product.Amount,
                TinhVat: product.TinhVat
              }))
              this.danhSachHoaDon.dataSources = [...this.lstProduct]
              this.hoaDonFilter.txtTongTien = Math.abs(tien)
              this.hoaDonFilter.txtTongThue = Math.abs(vat)
              this.hoaDonFilter.txtTongTra = Math.abs(tongtien)
              this.t_tien_edited = tongtien
              this.hoaDonFilter.txtTienChu = DocSo(Math.abs(this.hoaDonFilter.txtTongTra))
            }
            this.setButtonDisabled('tsbtnGhiLai', false)
            return true
          }
          this.setButtonDisabled('tsbtnGhiLai', true)
          return false
        } else {
          if (data.vchapnhan) {
            this.vtoken_show = data.vtoken_show
            if (data.hasOwnProperty('vtype')) this.type = +data.vtype
            this.Fkey = data._fkey
            this._pattern = data._pattern
            this._seri = data._seri
            this._so_hd = +data._so_hd
            await this.TraCuu(this._pattern, this._seri, this._so_hd)

            const thongTin = await this.xuatHddtTQHienThiThongTin({
              maGD: this.traCuuFilter.txtMaGD,
              fkey: this.Fkey,
              tagForm: this.tagForm,
              thaoTacId: this.traCuuFilter.thaoTacId,
              getServiceInfo: 1
            })
           
            if (thongTin && thongTin.DS_KH.length > 0) {
              const tt = thongTin.DS_KH[0]
              this.bdhddt_cha_id = +tt.BDHDDT_CHA_ID || 0
              this.vnguoicn = tt.NGUOI_CN || ''
            } else {
              this.bdhddt_cha_id = 0
              this.vnguoicn = ''
            }
            this.setButtonDisabled('tsbtnGhiLai', false)
          } else {
            this.hoaDonFilter.txtTenKH = this.hienthitt.TEN_TT
            this.hoaDonFilter.txtDiaChiKH = this.hienthitt.DIACHI_TT
            this.hoaDonFilter.txtMSTKH = this.hienthitt.MST
            this.hoaDonFilter.txtSoDT = this.hienthitt.SDT
            this.hoaDonFilter.txtTiLeThue = '10'
            this.hoaDonFilter.txtEmail = this.hienthitt.EMAIL
            this.setButtonDisabled('tsbtnGhiLai', true)
            this.setButtonDisabled('tsbtnNhapMoi', false)
          }
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    getThamSoByMaThamSo (maTS) {
      return this.dsThamSoMacDinh.find(elm => elm.MA_TS === maTS)
    },
    HienThi_GiaoDien_TT78 () {
      this.setColumnHeaderName('Amount', 'Thành tiền (sau thuế)')
      this.setColumnVisible('Total', true)
      this.setColumnVisible('Amount', true)
      this.setColumnVisible('VATRate', true)
      this.setColumnVisible('VATAmount', true)
      if (!this.tt78_stk_nh) this.visibleBankAccount = false
    },
    setColumnVisible (fieldName, visible) {
      this.danhSachHoaDon.headers.find(elm => elm.fieldName === fieldName).visible = visible
    },
    setColumnHeaderName (fieldName, headerText) {
      this.danhSachHoaDon.headers.find(elm => elm.fieldName === fieldName).headerText = headerText
    },
    async cboPattern_EditValueChanged () {
      try {
        const response = await api.sp_lay_ds_seri_dientu_v2(
          this.axios,
          { kieu: 1, loaihd_id: this.traCuuFilter.mauSoId, phanvung_id: this.phanVungId }
        )
        const data = await response.data
        this.cboSeri = data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : []
        if (this.cboSeri.length > 0) this.traCuuFilter.kyHieuId = this.cboSeri[0].seri_id
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách seri')
        this.cboSeri = []
        this.traCuuFilter.kyHieuId = null
      }
    },
    async cboDieuChinh_EditValueChanged () {
      this.loading(true)
      try {
        await this.KiemTra_DieuChinh_TT_HD()
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async KiemTra_DieuChinh_TT_HD () {
      if (this.traCuuFilter.thaoTacId === 2) {
        if (this.traCuuFilter.kieuDieuChinhId === 3) {
          if (this.objHoaDonDienTuEdit &&
            this.objHoaDonDienTuEdit.hasOwnProperty('Products') &&
            Array.isArray(this.objHoaDonDienTuEdit.Products)
          ) {
            const lsPro = []
            this.objHoaDonDienTuEdit.Products.forEach(product => lsPro.push(product))
            if (this.objHoaDonDienTuEdit.Products.length < 15) {
              for (let i = 0; i < 15 - this.objHoaDonDienTuEdit.Products.length; i++) {
                lsPro.push({
                  Code: '',
                  Total: 0,
                  ProdName: '',
                  ProdUnit: '',
                  ProdQuantity: 0,
                  ProdPrice: 0,
                  Discount: 0,
                  DiscountAmount: 0,
                  VATRate: 0,
                  VATAmount: 0,
                  Amount: 0,
                  TinhVat: false
                })
              }
            }
            let counter = 1
            this.lstProduct = lsPro.map(product => ({
              STT: counter++,
              Code: product.Code,
              Total: 0,
              ProdName: product.ProdName,
              ProdUnit: product.ProdUnit,
              ProdQuantity: 0,
              ProdPrice: 0,
              Discount: 0,
              DiscountAmount: 0,
              VATRate: 0,
              VATAmount: 0,
              Amount: 0,
              TinhVat: product.TinhVat
            }))
            const lstProductFilter = []
            this.lstProduct.forEach(product => {
              if (product.ProdName) {
                lstProductFilter.push({
                  Code: product.Code,
                  Total: product.Total,
                  ProdName: product.ProdName,
                  ProdUnit: product.ProdUnit,
                  ProdQuantity: product.ProdQuantity,
                  ProdPrice: product.ProdPrice,
                  Discount: product.Discount,
                  DiscountAmount: product.DiscountAmount,
                  VATRate: product.VATRate,
                  VATAmount: product.VATAmount,
                  Amount: product.Amount
                })
              }
            })
            this.objHoaDonDienTuEdit.Products = lstProductFilter
            this.danhSachHoaDon.dataSources = [...this.lstProduct]
            this.objHoaDonDienTuEdit.Amount_words = 'Không đồng !'
            this.objHoaDonDienTuEdit.Total = 0
            this.objHoaDonDienTuEdit.VAT_Amount = 0
            this.objHoaDonDienTuEdit.VAT_Rate = 0
            this.objHoaDonDienTuEdit.Amount = 0
            this.hoaDonFilter.txtTongThue = 0
            this.hoaDonFilter.txtTongTien = 0
            this.hoaDonFilter.txtTongTra = 0
            this.hoaDonFilter.txtTiLeThue = '0'
            this.hoaDonFilter.txtTienChu = 'Không đồng !'

            this.hoaDonFilter.txtTongTienChiuThue10 = 0
            this.hoaDonFilter.txtTongTienChiuThue8 = 0
            this.hoaDonFilter.txtTongTienChiuThue5 = 0
            this.hoaDonFilter.txtTongTienChiuThue0 = 0
            this.hoaDonFilter.txtTongTienThue10 = 0
            this.hoaDonFilter.txtTongTienThue8 = 0
            this.hoaDonFilter.txtTongTienThue5 = 0
            this.hoaDonFilter.txtTongTienKhongThue = 0
            this.hoaDonFilter.txtTongTienKhongChiuThue = 0
          }
        } else {
          await this.TraCuu(this._pattern, this._seri, this._so_hd)
        }
      }
    },
    async TraCuu (_Pattern, _Seri, _sohoadon) {
      this.first = true
      this.hoaDonFilter.txtFilePath = ''
      //this.hoaDonFilter.txtGhiChu = ''
      const products = []
      if (_Pattern && _Seri && _sohoadon) this.invToken = `${_Pattern};${_Seri};${_sohoadon}`
      else {
        if (!this.traCuuFilter.mauSoId) {
          this.$refs.mauSoRef.focus()
          return false
        }
        if (!this.traCuuFilter.kyHieuId) {
          this.$refs.kyHieuRef.focus()
          return false
        }
        if (!this.traCuuFilter.txtSoHoaDon) {
          this.$refs.txtSoHoaDonRef.focus()
          return false
        }
        const pattern = this.cboPattern.find(elm => elm.LOAIHOADON_ID === this.traCuuFilter.mauSoId)
        const mauSo = pattern ? pattern.TEN_LOAIHD : ''
        const serial = this.cboSeri.find(elm => elm.seri_id === this.traCuuFilter.kyHieuId)
        const kyHieu = serial ? serial.seri : ''
        this.invToken = `${mauSo};${kyHieu};${this.traCuuFilter.txtSoHoaDon}`
      }
      const resultNoPay = await this.downloadInvNoPay(this.invToken)
      if (!resultNoPay) return false
      if (resultNoPay.errorCode !== 0) {
        this.$root.$toast.error(resultNoPay.faultString)
        return false
      }
      const xmlDoc = this.parseXmlDoc(resultNoPay.data)
      if (xmlDoc.getElementsByTagName('HHDVu').length === 0) {
        this.objHoaDonDienTu.Amount = +this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTTTBSo' })
        this.objHoaDonDienTu.Amount_words = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTTTBChu' })
        this.objHoaDonDienTu.ArisingDate = this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'NLap' })
        this.objHoaDonDienTu.Buyer = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'HVTNMHang' })
        this.objHoaDonDienTu.ComAddress = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'DChi' })
        this.objHoaDonDienTu.ComName = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'Ten' })
        this.objHoaDonDienTu.ComPhone = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'SDThoai' })
        this.objHoaDonDienTu.ComTaxCode = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'MST' })
        this.objHoaDonDienTu.CusAddress = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'DChi' })
        this.objHoaDonDienTu.CusBankName = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'TNHang' })
        this.objHoaDonDienTu.CusCode = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'MKHang' })
        this.objHoaDonDienTu.CusName = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'Ten' })
        this.objHoaDonDienTu.CusPhone = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'SDThoai' })
        this.objHoaDonDienTu.CusTaxCode = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'MST' })
        if (xmlDoc.getElementsByTagName('DLHDon')[0].getElementsByTagName('TTChung')[0].getElementsByTagName('TTKhac').length > 0) {
          this.objHoaDonDienTu.Extra = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'TTKhac', fourth: 'TTruong' })
        }
        this.objHoaDonDienTu.InvoiceName = this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'THDon' })
        this.objHoaDonDienTu.InvoiceNo = this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'SHDon' })
        this.objHoaDonDienTu.InvoicePattern = this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'KHMSHDon' })
        this.objHoaDonDienTu.Kind_of_Payment = this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'HTTToan' })
        this.objHoaDonDienTu.SerialNo = this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'KHHDon' })
        this.objHoaDonDienTu.Total = +this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTCThue' })
        this.objHoaDonDienTu.VAT_Amount = +this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTThue' })
        try {
          const tSuat = this.getFifthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'THTTLTSuat', fifth: 'TSuat' })
          if (tSuat === 'KKKNT') this.objHoaDonDienTu.VAT_Rate = -2
          else if (tSuat === 'KCT') this.objHoaDonDienTu.VAT_Rate = -1
          else if (tSuat.includes('KHAC:')) this.objHoaDonDienTu.VAT_Rate = 10
          else this.objHoaDonDienTu.VAT_Rate = +tSuat.substring(0, tSuat.length - 1)
        } catch (e) {
          this.objHoaDonDienTu.VAT_Rate = 0
        }
        for (const elm of xmlDoc.getElementsByTagName('DLHDon')[0].getElementsByTagName('NDHDon')[0].getElementsByTagName('DSHHDVu')) {
          const product = {
            Code: '',
            Total: +elm.getElementsByTagName('ThTien')[0].textContent,
            ProdName: elm.getElementsByTagName('THHDVu')[0].textContent,
            ProdUnit: elm.getElementsByTagName('DVTinh')[0].textContent,
            ProdQuantity: +elm.getElementsByTagName('SLuong')[0].textContent,
            ProdPrice: +elm.getElementsByTagName('DGia')[0].textContent,
            Discount: 0,
            DiscountAmount: 0,
            VATRate: 0,
            VATAmount: 0,
            Amount: +elm.getElementsByTagName('TTKhac')[0].textContent,
            TinhVat: false
          }
          if (elm.getElementsByTagName('MHHDVu').length > 0) {
            product.Code = elm.getElementsByTagName('MHHDVu')[0].textContent
          }
          if (elm.getElementsByTagName('TTKhac').length > 1) {
            product.VATAmount = +elm.getElementsByTagName('TTKhac')[1].getElementsByTagName('DLieu')[0].textContent
          }
          const tSuatProduct = elm.getElementsByTagName('TSuat')[0].textContent
          if (tSuatProduct === 'KKKNT') product.VATRate = -2
          else if (tSuatProduct === 'KCT') product.VATRate = -1
          else if (tSuatProduct.includes('KHAC:')) product.VATRate = 10
          else product.VATRate = +(tSuatProduct.substring(0, tSuatProduct.length - 1))
          products.push(product)
        }
        this.objHoaDonDienTu.Products = [...products]
      } else {
        this.objHoaDonDienTu.Amount = +this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTTTBSo' })
        this.objHoaDonDienTu.Amount_words = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTTTBChu' })
        this.objHoaDonDienTu.ArisingDate = this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'NLap' })
        this.objHoaDonDienTu.ComAddress = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'DChi' })
        this.objHoaDonDienTu.ComName = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'Ten' })
        this.objHoaDonDienTu.ComPhone = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'SDThoai' })
        this.objHoaDonDienTu.ComTaxCode = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'MST' })
        this.objHoaDonDienTu.CusAddress = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'DChi' })
        this.objHoaDonDienTu.CusBankName = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'TNHang' })
        this.objHoaDonDienTu.CusBankNo = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'STKNHang' })
        this.objHoaDonDienTu.CusCode = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'MKHang' })
        this.objHoaDonDienTu.CusName = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'Ten' })
        this.objHoaDonDienTu.CusPhone = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'SDThoai' })
        this.objHoaDonDienTu.CusTaxCode = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'MST' })
        this.objHoaDonDienTu.InvoiceName = this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'THDon' })
        this.objHoaDonDienTu.InvoiceNo = this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'SHDon' })
        this.objHoaDonDienTu.InvoicePattern = this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'KHMSHDon' })
        this.objHoaDonDienTu.Kind_of_Payment = this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'HTTToan' })
        this.objHoaDonDienTu.SerialNo = this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'KHHDon' })
        this.objHoaDonDienTu.Total = +this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTCThue' })
        this.objHoaDonDienTu.VAT_Amount = +this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTThue' })
        try {
          const tSuatHd2 = this.getSixthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'THTTLTSuat', fifth: 'LTSuat', sixth: 'TSuat' })
          if (tSuatHd2 === 'KKKNT' || tSuatHd2.includes('KHAC:2')) this.objHoaDonDienTu.VAT_Rate = -2
          else if (tSuatHd2 === 'KCT' || tSuatHd2.includes('KHAC:1')) this.objHoaDonDienTu.VAT_Rate = -1
          else this.objHoaDonDienTu.VAT_Rate = +tSuatHd2.substring(0, tSuatHd2.length - 1)
        } catch (e) {
          this.objHoaDonDienTu.VAT_Rate = 0
        }
        for (const elm of xmlDoc.getElementsByTagName('DLHDon')[0].getElementsByTagName('NDHDon')[0].getElementsByTagName('DSHHDVu')[0].getElementsByTagName('HHDVu')) {
          const product = {
            Code: '',
            Total: +this.getFirstLevelContentXml(elm, 'ThTien'),
            ProdName: this.getFirstLevelContentXml(elm, 'THHDVu'),
            ProdUnit: this.getFirstLevelContentXml(elm, 'DVTinh'),
            ProdQuantity: +this.getFirstLevelContentXml(elm, 'SLuong'),
            ProdPrice: +this.getFirstLevelContentXml(elm, 'DGia'),
            Discount: 0,
            DiscountAmount: 0,
            VATRate: 0,
            VATAmount: 0,
            Amount: +this.getThirdLevelContentXml(elm, { first: 'TTKhac', second: 'TTin', third: 'DLieu' }),
            TinhVat: false
          }
          if (elm.getElementsByTagName('TTKhac')[0].getElementsByTagName('TTin').length > 1) {
            product.VATAmount = +elm.getElementsByTagName('TTKhac')[0].getElementsByTagName('TTin')[1].getElementsByTagName('DLieu')[0].textContent
          }
          const tSuatProduct = this.getFirstLevelContentXml(elm, 'TSuat')
          if (tSuatProduct === 'KKKNT') product.VATRate = -2
          else if (tSuatProduct === 'KCT') product.VATRate = -1
          else if (tSuatProduct.includes('KHAC:')) product.VATRate = 10
          else product.VATRate = +(tSuatProduct.substring(0, tSuatProduct.length - 1))
          products.push(product)
        }
        this.objHoaDonDienTu.Products = products
      }
      this.objHoaDonDienTuEdit = { ...this.objHoaDonDienTu }
      let pattern1 = true
      if (products.length > 1) {
        const vatrate = +products[0].VATRate
        for (let i = 1; i < products.length; i++) {
          if (products[i].ProdName && vatrate !== +products[i].VATRate) {
            pattern1 = false
            break
          }
        }
      }
      if (this.cboPattern.length > 1) {
        this.traCuuFilter.mauSoId = pattern1 ? this.cboPattern[0].LOAIHOADON_ID : this.cboPattern[1].LOAIHOADON_ID
      } else if (this.cboPattern.length === 1) {
        this.traCuuFilter.mauSoId = this.cboPattern[0].LOAIHOADON_ID
      } else {
        this.traCuuFilter.mauSoId = null
      }
      this.hoaDonFilter.txtTenHD = this.objHoaDonDienTuEdit.InvoiceName
      this.hoaDonFilter.txtMauHoaDon = this.objHoaDonDienTuEdit.InvoicePattern
      this.hoaDonFilter.lblKyHieu = this.objHoaDonDienTuEdit.SerialNo
      this.hoaDonFilter.lblSoHoaDon = +this.objHoaDonDienTuEdit.InvoiceNo
      this.hoaDonFilter.txtMST = this.objHoaDonDienTuEdit.ComTaxCode
      this.hoaDonFilter.txtTenDV = this.objHoaDonDienTuEdit.ComName
      this.hoaDonFilter.txtDiaChiDV = this.objHoaDonDienTuEdit.ComAddress
      this.hoaDonFilter.txtTenKH = this.objHoaDonDienTuEdit.CusName
      this.hoaDonFilter.txtDiaChiKH = this.objHoaDonDienTuEdit.CusAddress
      this.hoaDonFilter.txtMSTKH = this.objHoaDonDienTuEdit.CusTaxCode
      this.hoaDonFilter.txtSoDT = this.objHoaDonDienTuEdit.CusPhone
      this.hoaDonFilter.txtMaKH = this.objHoaDonDienTuEdit.CusCode
      this.hoaDonFilter.txtSoTaiKhoan = this.objHoaDonDienTuEdit.CusBankNo
      const nganhangId = await this.getThongTinNganHang(this.objHoaDonDienTuEdit.CusBankName, 3)
      if (nganhangId !== '-1') this.hoaDonFilter.nganHangId = +nganhangId
      this.hoaDonFilter.txtHTTT = this.objHoaDonDienTuEdit.Kind_of_Payment
      this.hoaDonFilter.txtKyHD = this.objHoaDonDienTuEdit.KindOfService
      this.hoaDonFilter.txtTiLeThue = this.objHoaDonDienTuEdit.VAT_Rate.toString()
      this.hoaDonFilter.txtExtra = this.objHoaDonDienTuEdit.Extra
      const lsPro = []
      for (const product of this.objHoaDonDienTuEdit.Products) {
        lsPro.push(product)
      }
      if (lsPro.length < 15) {
        lsPro.push({
          Code: '',
          Total: 0,
          ProdName: '',
          ProdUnit: '',
          ProdQuantity: 0,
          ProdPrice: 0,
          Discount: 0,
          DiscountAmount: 0,
          VATRate: 0,
          VATAmount: 0,
          Amount: 0,
          TinhVat: false
        })
      }
      let counter = 1
      this.lstProduct = lsPro.map(product => ({
        STT: counter++,
        Code: product.Code,
        Total: product.Total,
        ProdName: product.ProdName,
        ProdUnit: product.ProdUnit,
        ProdQuantity: product.ProdQuantity,
        ProdPrice: product.ProdPrice,
        Discount: product.Discount,
        DiscountAmount: product.DiscountAmount,
        VATRate: product.VATRate,
        VATAmount: product.VATAmount,
        Amount: product.Amount,
        TinhVat: product.VATRate > 0
      }))
      this.danhSachHoaDon.dataSources = [...this.lstProduct]
      const total = this.lstProduct
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      const vatAmount = this.lstProduct
        .map(elm => elm.VATAmount)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTienKhongChiuThue = this.lstProduct
        .filter(elm => elm.VATRate === -1)
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTienKhongThue = this.lstProduct
        .filter(elm => elm.VATRate === -2)
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTienChiuThue0 = this.lstProduct
        .filter(elm => elm.VATRate === 0)
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTienChiuThue5 = this.lstProduct
        .filter(elm => elm.VATRate === 5)
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTienChiuThue8 = this.lstProduct
        .filter(elm => elm.VATRate === 8)
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTienChiuThue10 = this.lstProduct
        .filter(elm => elm.VATRate === 10)
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTienThue5 = this.lstProduct
        .filter(elm => elm.VATRate === 5)
        .map(elm => elm.VATAmount)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTienThue8 = this.lstProduct
        .filter(elm => elm.VATRate === 8)
        .map(elm => elm.VATAmount)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTienThue10 = this.lstProduct
        .filter(elm => elm.VATRate === 10)
        .map(elm => elm.VATAmount)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTien = total
      this.hoaDonFilter.txtTongThue = vatAmount
      this.hoaDonFilter.txtTongTra = this.objHoaDonDienTuEdit.Amount
      this.objHoaDonDienTuEdit.Total = total
      this.objHoaDonDienTuEdit.VAT_Amount = vatAmount
      this.objHoaDonDienTuEdit.Amount = total + vatAmount
      this.t_tien_edited = this.objHoaDonDienTuEdit.Amount
      this.hoaDonFilter.txtTienChu = DocSo(Math.abs(this.objHoaDonDienTuEdit.Amount))
      this.first = false
      return true
    },
    async getThongTinNganHang (param, type) {
      try {
        const response = await api.getThongTinNganHang(this.axios, { param, type })
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : ''
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin ngân hàng')
        return ''
      }
    },
    getFirstLevelContentXml (xmlDoc, first) {
      return xmlDoc.getElementsByTagName(first).length > 0
        ? xmlDoc.getElementsByTagName(first)[0].textContent
        : ''
    },
    getThirdLevelContentXml (xmlDoc, { first, second, third }) {
      return xmlDoc.getElementsByTagName(first)[0]
        .getElementsByTagName(second)[0]
        .getElementsByTagName(third)
        .length > 0
        ? xmlDoc.getElementsByTagName(first)[0]
          .getElementsByTagName(second)[0]
          .getElementsByTagName(third)[0].textContent
        : ''
    },
    getFourthLevelContentXml (xmlDoc, { first, second, third, fourth }) {
      return xmlDoc.getElementsByTagName(first)[0]
        .getElementsByTagName(second)[0]
        .getElementsByTagName(third)[0]
        .getElementsByTagName(fourth)
        .length > 0
        ? xmlDoc.getElementsByTagName(first)[0]
          .getElementsByTagName(second)[0]
          .getElementsByTagName(third)[0]
          .getElementsByTagName(fourth)[0].textContent
        : ''
    },
    getFifthLevelContentXml (xmlDoc, { first, second, third, fourth, fifth }) {
      return xmlDoc.getElementsByTagName(first)[0]
        .getElementsByTagName(second)[0]
        .getElementsByTagName(third)[0]
        .getElementsByTagName(fourth)[0]
        .getElementsByTagName(fifth)
        .length > 0
        ? xmlDoc.getElementsByTagName(first)[0]
          .getElementsByTagName(second)[0]
          .getElementsByTagName(third)[0]
          .getElementsByTagName(fourth)[0]
          .getElementsByTagName(fifth)[0]
          .textContent
        : ''
    },
    getSixthLevelContentXml (xmlDoc, { first, second, third, fourth, fifth, sixth }) {
      return xmlDoc.getElementsByTagName(first)[0]
        .getElementsByTagName(second)[0]
        .getElementsByTagName(third)[0]
        .getElementsByTagName(fourth)[0]
        .getElementsByTagName(fifth)[0]
        .getElementsByTagName(sixth)
        .length > 0
        ? xmlDoc.getElementsByTagName(first)[0]
          .getElementsByTagName(second)[0]
          .getElementsByTagName(third)[0]
          .getElementsByTagName(fourth)[0]
          .getElementsByTagName(fifth)[0]
          .getElementsByTagName(sixth)[0]
          .textContent
        : ''
    },
    async downloadInvNoPay (invToken) {
      try {
        const response = await api.downloadInvNoPay(this.axios, { invToken })
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hóa đơn')
        return null
      }
    },
    cboThaoTac_EditValueChanged () {
      this.traCuuFilter.kieuDieuChinhId = this.cboDieuChinh.length > 0 ? this.cboDieuChinh[0].DIEUCHINH_ID : null
      this.isDisabledCboDieuChinh = this.traCuuFilter.thaoTacId !== 2
      this.clean()
    },
    clean () {
      this.vma_kh = ''
      this.vnguoicn = ''
      this.bdhddt_cha_id = 0
      this.vma_gd = ''
      this.t_tien_edited = 0
      this.hoaDonFilter.txtEmail = ''
      this.vten_nh = ''
      this.vdiachi_tt = ''
      this.hoaDonFilter.txtDiaChiDV = ''
      this.hoaDonFilter.txtDiaChiKH = ''
      this.hoaDonFilter.txtExtra = ''
      this.hoaDonFilter.txtFilePath = ''
      //this.hoaDonFilter.txtGhiChu = ''
      this.hoaDonFilter.txtHTTT = this.tagForm === 2 ? '' : 'Tiền mặt/Chuyển khoản'
      this.hoaDonFilter.txtKyHD = ''
      this.hoaDonFilter.txtMaKH = ''
      this.hoaDonFilter.txtMauHoaDon = ''
      this.hoaDonFilter.txtMST = ''
      this.hoaDonFilter.txtMSTKH = ''
      this.hoaDonFilter.txtSoDT = ''
      this.traCuuFilter.txtSoHoaDon = 0
      this.hoaDonFilter.txtTenDV = ''
      this.hoaDonFilter.txtTenHD = ''
      this.hoaDonFilter.txtTenKH = ''
      this.hoaDonFilter.txtTienChu = ''
      this.hoaDonFilter.txtTiLeThue = '10'
      this.hoaDonFilter.txtTongThue = 0
      this.hoaDonFilter.txtTongTien = 0
      this.hoaDonFilter.txtTongTra = 0
      this.hoaDonFilter.txtTongTienChiuThue0 = 0
      this.hoaDonFilter.txtTongTienChiuThue5 = 0
      this.hoaDonFilter.txtTongTienChiuThue10 = 0
      this.hoaDonFilter.txtTongTienKhongChiuThue = 0
      this.hoaDonFilter.txtTongTienKhongThue = 0
      this.hoaDonFilter.txtTongTienThue10 = 0
      this.hoaDonFilter.txtTongTienThue5 = 0
      this.hoaDonFilter.txtTongTienThue8 = 0
      this.hoaDonFilter.txtTongTienChiuThue8 = 0
      this.hoaDonFilter.lblKyHieu = ''
      this.hoaDonFilter.lblSoHoaDon = null
      this.vhdkh_id = 0
      this._pattern = ''
      this._seri = ''
      this._so_hd = null
      this.hoaDonFilter.txtSoTaiKhoan = ''
      this.hoaDonFilter.nganHangId = null
    },
    async onClickAction (id) {
      if (id === 'tsbtnNhapMoi') this.nhapMoi()
      else await this.capNhat()
    },
    nhapMoi () {
      this.setButtonDisabled('tsbtnGhiLai', false)
      this.clean()
      const dataSources = []
      for (let i = 1; i < 16; i++) {
        dataSources.push({
          STT: i,
          Code: '',
          Total: 0,
          ProdName: '',
          ProdUnit: '',
          ProdQuantity: 0,
          ProdPrice: 0,
          Discount: 0,
          DiscountAmount: 0,
          VATRate: 0,
          VATAmount: 0,
          Amount: 0,
          TinhVat: false
        })
      }
      this.lstProduct = [...dataSources]
      this.danhSachHoaDon.dataSources = [...dataSources]
      this.hoaDonFilter.txtTongTien = 0
      this.hoaDonFilter.txtTongThue = 0
      this.hoaDonFilter.txtTongTra = 0
      this.hoaDonFilter.txtTienChu = DocSo(Math.abs(this.hoaDonFilter.txtTongTra))
    },
    setButtonDisabled (id, disabled) {
      this.actions.find(item => item.id === id).disabled = disabled
    },
    async capNhat () {
      this.loading(true)
      try {
        const thaoTacId = this.traCuuFilter.thaoTacId
        console.log('thaoTacId', thaoTacId)
        if (!thaoTacId) {
          this.$root.$toast.warning('Bạn chưa chọn thao tác !')
          return false
        }
        if (thaoTacId !== 1 && thaoTacId !== 6 && !this.da_xuathd) {
          this.$root.$toast.warning('Chưa xuất hóa đơn điện tử. Bạn không thể chọn thao tác này !')
          return false
        }
        if (thaoTacId === 1) {
          if (!(await this.Kiemtra_dulieu(thaoTacId))) return false
          debugger
          return false;
          if (this.xemtruoc_hddt) {
            const response = await this.xuatHddtTQCapNhat({
              thaoTac: thaoTacId,
              BD_HDDT: '',
              BD_HDDT_CT: '',
              maNd: this.maNguoiDung,
              bdhddtChaId: this.bdhddt_cha_id,
              tagForm: this.tagForm,
              linkForm: this.linkForm,
              maGD: this.traCuuFilter.txtMaGD
            })
            let html = ''
            if (response) {
              if (response.ERROR_CODE === 1) {
                const xml = await this.taoXmlHoaDon(thaoTacId, response.DS_HDDT_MAU[0].THONGTIN_CT)
                const xmlDoc = this.parseXmlDoc(xml)
                const xsl = this.parseXmlDoc(response.DS_HDDT_MAU[0].MAU_HD.join(''))
                const transformXml = this.transformXml(xmlDoc, xsl)
                html = this.convertXmlToString(transformXml)
              } else {
                this.$root.$toast.error(response.MESSAGE)
                html = ''
              }
            } else {
              this.$root.$toast.error('Không thể lấy thông tin thẻ html')
              html = ''
            }
            if (html) {
              await this.$refs.popupEinvoiceDetailRef.showModal({
                html,
                vthaotac: thaoTacId,
                viewFromText: true,
                isview: false,
                hoang_test: this.tagForm === 2 ? 3 : 1,
                vtthoadon: 1
              })
            } else {
              this.$confirm(
                'Không xem trước được hóa đơn. Bạn có muốn tiếp tục xuất hóa đơn?',
                'Xuất hóa đơn',
                { confirmButtonText: 'Có', cancelButtonText: 'Không' }
              ).then(async () => {
                await this.Xuathoadon()
              }).catch((e) => {})
            }
          } else {
            await this.Xuathoadon()
          }
        } else if (thaoTacId === 2) {
          if (!(await this.Kiemtra_dulieu(thaoTacId))) return false
          if (this.xemtruoc_hddt) {
            const response = await this.xuatHddtTQCapNhat({
              thaoTac: thaoTacId,
              BD_HDDT: '',
              BD_HDDT_CT: '',
              maNd: this.maNguoiDung,
              bdhddtChaId: this.bdhddt_cha_id,
              tagForm: this.tagForm,
              linkForm: this.linkForm,
              maGD: this.traCuuFilter.txtMaGD
            })
            let html = ''
            if (response) {
              if (response.ERROR_CODE === 1) {
                const xml = await this.taoXmlHoaDon(thaoTacId, response.DS_HDDT_MAU[0].THONGTIN_CT)
                const xmlDoc = this.parseXmlDoc(xml)
                const xsl = this.parseXmlDoc(response.DS_HDDT_MAU[0].MAU_HD.join(''))
                const transformXml = this.transformXml(xmlDoc, xsl)
                html = this.convertXmlToString(transformXml)
              } else {
                this.$root.$toast.error(response.MESSAGE)
                html = ''
              }
            } else {
              this.$root.$toast.error('Không thể lấy thông tin thẻ html')
              html = ''
            }
            if (html) {
              await this.$refs.popupEinvoiceDetailRef.showModal({
                html,
                vthaotac: thaoTacId,
                viewFromText: true,
                isview: false,
                hoang_test: this.tagForm === 2 ? 3 : 1,
                vtthoadon: 1
              })
            } else {
              this.$confirm(
                'Không xem trước được hóa đơn. Bạn có muốn tiếp tục điều chỉnh hóa đơn?',
                'Điều chỉnh hóa đơn',
                { confirmButtonText: 'Có', cancelButtonText: 'Không' }
              ).then(async () => {
                await this.DieuChinhHoaDon()
                return true
              }).catch((e) => {})
            }
          } else {
            await this.DieuChinhHoaDon()
          }
        } else if (thaoTacId === 3) {
          if (!(await this.Kiemtra_dulieu(thaoTacId))) return false
          if (this.xemtruoc_hddt) {
            const response = await this.xuatHddtTQCapNhat({
              thaoTac: thaoTacId,
              BD_HDDT: '',
              BD_HDDT_CT: '',
              maNd: this.maNguoiDung,
              bdhddtChaId: this.bdhddt_cha_id,
              tagForm: this.tagForm,
              linkForm: this.linkForm,
              maGD: this.traCuuFilter.txtMaGD
            })
            let html = ''
            if (response) {
              if (response.ERROR_CODE === 1) {
                const xml = await this.taoXmlHoaDon(thaoTacId, response.DS_HDDT_MAU[0].THONGTIN_CT)
                const xmlDoc = this.parseXmlDoc(xml)
                const xsl = this.parseXmlDoc(response.DS_HDDT_MAU[0].MAU_HD.join(''))
                const transformXml = this.transformXml(xmlDoc, xsl)
                html = this.convertXmlToString(transformXml)
              } else {
                this.$root.$toast.error(response.MESSAGE)
                html = ''
              }
            } else {
              this.$root.$toast.error('Không thể lấy thông tin thẻ html')
              html = ''
            }
            if (html) {
              await this.$refs.popupEinvoiceDetailRef.showModal({
                html,
                vthaotac: thaoTacId,
                viewFromText: true,
                isview: false,
                hoang_test: this.tagForm === 2 ? 3 : 1,
                vtthoadon: 1
              })
            } else {
              this.$confirm(
                'Không xem trước được hóa đơn. Bạn có muốn tiếp tục thay thế hóa đơn?',
                'Thay thế hóa đơn',
                { confirmButtonText: 'Có', cancelButtonText: 'Không' }
              ).then(async () => {
                await this.ThayTheHoaDon()
              }).catch((e) => {})
            }
          } else {
            await this.ThayTheHoaDon()
          }
        } else if (thaoTacId === 4) {
          if (!(await this.Kiemtra_dulieu(thaoTacId))) return false
          if (this.hoi_capnhat) {
            await this.$refs.popupEinvoiceDetailRef.showModal({
              vthaotac: thaoTacId,
              vtoken_show: this.vtoken_show,
              isview: false,
              hoang_test: this.tagForm === 2 ? 3 : 1,
              vtthoadon: 1
            })
          } else {
            await this.HuyHoaDon()
          }
        } else if (thaoTacId === 5) {
        
          if (!(await this.Kiemtra_dulieu(thaoTacId))) return false
          if (this.hoi_capnhat) {
            await this.$refs.popupEinvoiceDetailRef.showModal({
              vthaotac: thaoTacId,
              vtoken_show: this.vtoken_show,
              isview: false,
              hoang_test: this.tagForm === 2 ? 3 : 1,
              vtthoadon: 1
            })
          } else {
            await this.HuyThanhToan_HuyHoaDon()
          }
        } else if (thaoTacId === 6) {
          if (!(await this.Kiemtra_dulieu(thaoTacId))) return false
          if (this.xemtruoc_hddt) {
            const response = await this.xuatHddtTQCapNhat({
              thaoTac: thaoTacId,
              BD_HDDT: '',
              BD_HDDT_CT: '',
              maNd: this.maNguoiDung,
              bdhddtChaId: this.bdhddt_cha_id,
              tagForm: this.tagForm,
              linkForm: this.linkForm,
              maGD: this.traCuuFilter.txtMaGD
            })
            let html = ''
            if (response) {
              if (response.ERROR_CODE === 1) {
                const xml = await this.taoXmlHoaDon(thaoTacId, response.DS_HDDT_MAU[0].THONGTIN_CT)
                const xmlDoc = this.parseXmlDoc(xml)
                const xsl = this.parseXmlDoc(response.DS_HDDT_MAU[0].MAU_HD.join(''))
                const transformXml = this.transformXml(xmlDoc, xsl)
                html = this.convertXmlToString(transformXml)
              } else {
                this.$root.$toast.error(response.MESSAGE)
                html = ''
              }
            } else {
              this.$root.$toast.error('Không thể lấy thông tin thẻ html')
              html = ''
            }
            if (html) {
              await this.$refs.popupEinvoiceDetailRef.showModal({
                html,
                vthaotac: thaoTacId,
                viewFromText: true,
                isview: false,
                hoang_test: this.tagForm === 2 ? 3 : 1,
                vtthoadon: 1
              })
            } else {
              this.$confirm(
                'Không xem trước được hóa đơn. Bạn có muốn tiếp tục xuất hóa đơn?',
                'Thay thế hóa đơn',
                { confirmButtonText: 'Có', cancelButtonText: 'Không' }
              ).then(async () => {
                await this.Xuathoadon_GachNo()
              }).catch((e) => {})
            }
          } else {
            await this.Xuathoadon_GachNo()
          }
        } else if (thaoTacId === 7) {
          if (!(await this.Kiemtra_dulieu(thaoTacId))) return false
          await this.GachNo()
        } else if (thaoTacId === 8) {
          if (!(await this.Kiemtra_dulieu(thaoTacId))) return false
          await this.Huy_GachNo()
        } else if (thaoTacId === 9) {
          if (!(await this.Kiemtra_dulieu(thaoTacId))) return false
          await this.ChuyenDoi_XacMinh()
        } else if (thaoTacId === 10) {
          if (!(await this.Kiemtra_dulieu(thaoTacId))) return false
          await this.ChuyenDoi_LuuTru()
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async Kiemtra_dulieu (thaoTacId) {
      
      this.tiengoc = 0
      if (!this.hoaDonFilter.txtTenKH) {
        this.$root.$toast.warning('Tên khách hàng không được để trống !')
        this.$refs.tenKHRef.focus()
        return false
      }
      if (!this.hoaDonFilter.txtDiaChiKH) {
        this.$root.$toast.warning('Địa chỉ khách hàng không được để trống !')
        this.$refs.diaChiRef.focus()
        return false
      }
      if (!this.hoaDonFilter.txtHTTT) {
        this.$root.$toast.warning('Hình thức thanh toán không được để trống !')
        this.$refs.hinhThucThanhToanRef.focus()
        return false
      }
      if (!this.hoaDonFilter.txtTiLeThue) {
        this.$root.$toast.warning('Thuế GTGT không được để trống !')
        this.$refs.txtTiLeThueRef.focus()
        return false
      }
      if (this.danhSachHoaDon.dataSources.every(dataSource => !dataSource.ProdName)) {
        this.$root.$toast.warning('Kiểm tra lại dịch vụ sử dụng trên lưới !')
        return false
      }
      if (thaoTacId !== 1 && thaoTacId !== 6 && this.vnguoicn !== this.maNguoiDung) {

        this.$root.$toast.warning('Bạn không được điều chỉnh hóa đơn do người khác tạo !')
        return false
      }

      //update huy hoa don ko can kiem tra MST  
      if (thaoTacId !== 4 && thaoTacId !== 5 ) {
        if(this.hoaDonFilter.txtMSTKH){
            if(!checkMST(this.hoaDonFilter.txtMSTKH)){
              this.$root.$toast.warning('Mã số thuế không đúng định dạng')
              this.$refs.maSoThueRef.focus()
              return false
            }
        } 
      }
      if (!this.hoaDonFilter.txtTienChu) {
        this.$root.$toast.warning('Bạn hãy kiểm tra lại thông tin Tiền Bằng Chữ !!')
        this.$refs.txtTienChuRef.focus()
        return false
      } else if (thaoTacId === 2) {
        if (!this.traCuuFilter.kieuDieuChinhId) {
          this.$root.$toast.warning('Bạn chưa chọn kiểu điều chỉnh !')
          return false
        }
        if (!this.da_xuathd) {
          this.$root.$toast.warning('Hợp đồng chưa xuất hóa đơn. Bạn không được thực hiện thao tác này !')
          return false
        }
        const kq = await this.listInvByCus(this.objHoaDonDienTu.CusCode, '', '')
        if (!kq) return false
        if (kq.errorCode !== 0) {
          this.$root.$toast.error(kq.faultString)
          return false
        }
        const xmlDoc = this.parseXmlDoc(kq.data)
        const item = Array.from(xmlDoc.getElementsByTagName('Item'))
          .find(elm => elm.getElementsByTagName('invToken')[0].textContent === this.invToken)
        if (item) {
          this.Fkey = item.getElementsByTagName('fkey')[0].textContent
          const status = item.getElementsByTagName('status')[0].textContent
          if (status !== '1' && status !== '4') {
            this.$root.$toast.warning('Hóa đơn đã bị thay thế/hủy. Không thể điều chỉnh !')
            return false
          }
        } else {
          this.Fkey = ''
        }
        if (!this.objHoaDonDienTu.CusCode) {
          this.$root.$toast.warning('Không có thông tin khách hàng để điều chỉnh')
          return false
        }
        if (!this.objHoaDonDienTu.CusName) {
          this.$root.$toast.warning('Tên khách hàng không được để trống !')
          return false
        }
        if (!this.objHoaDonDienTu.CusAddress) {
          this.$root.$toast.warning('Địa chỉ khách hàng không được để trống !')
          return false
        }
        if ((this.traCuuFilter.kieuDieuChinhId === 1 || this.traCuuFilter.kieuDieuChinhId === 2) && Math.abs(this.t_tien_edited) === 0) {
          this.$root.$toast.warning('Tiền điều chỉnh phải khác 0')
          return false
        }
        if (this.traCuuFilter.kieuDieuChinhId === 3 && this.t_tien_edited !== 0) {
          this.$root.$toast.warning('Điều chỉnh thông tin. Khoản mục tiền phải = 0 !')
          return false
        }
      } else if (thaoTacId === 3) {
        if (this.type === 2 || this.type === 3 || this.type === 4) {
          this.$root.$toast.warning('Hóa đơn điều chỉnh. Bạn không thể Thay thế !')
          return false
        }
        const kq = await this.listInvByCus(this.objHoaDonDienTu.CusCode, '', '')
        if (!kq) return false
        if (kq.errorCode !== 0) {
          this.$root.$toast.error(kq.faultString)
          return false
        }
        const xmlDoc = this.parseXmlDoc(kq.data)
        const item = Array.from(xmlDoc.getElementsByTagName('Item'))
          .find(elm => elm.getElementsByTagName('invToken')[0].textContent === this.invToken)
        if (item) {
          this.Fkey = item.getElementsByTagName('fkey')[0].textContent
          if (item.getElementsByTagName('status')[0].textContent !== '1') {
            this.$root.$toast.warning('Hóa đơn đã bị thay thế/sửa đổi/hủy. Không thể thay thế !')
            return false
          }
        } else {
          this.Fkey = ''
        }
        if (!this.objHoaDonDienTu.CusCode) {
          this.$root.$toast.warning('Mã khách hàng không để để trống')
          return false
        }
        if (!this.objHoaDonDienTu.CusName) {
          this.$root.$toast.warning('Tên khách hàng không được để trống !')
          return false
        }
        if (!this.objHoaDonDienTu.CusAddress) {
          this.$root.$toast.warning('Địa chỉ khách hàng không được để trống !')
          return false
        }
      } else if (thaoTacId === 4) {
        if (this.type === 2 || this.type === 3 || this.type === 4) {
          this.$root.$toast.warning('Hóa đơn điều chỉnh. Bạn không thể hủy !')
          return false
        }
        if (this.type === 1) {
          this.$root.$toast.warning('Hợp đồng đã có hóa đơn bị thay thế. Bạn không thể hủy !')
          return false
        }
        const kq = await this.listInvByCus(this.objHoaDonDienTu.CusCode, '', '')
        if (!kq) return false
        if (kq.errorCode !== 0) {
          this.$root.$toast.error(kq.faultString)
          return false
        }
        const xmlDoc = this.parseXmlDoc(kq.data)
        const item = Array.from(xmlDoc.getElementsByTagName('Item'))
          .find(elm => elm.getElementsByTagName('invToken')[0].textContent === this.invToken)
        if (item) {
          this.Fkey = item.getElementsByTagName('fkey')[0].textContent
          if (item.getElementsByTagName('payment')[0].textContent === '1') {
            this.$root.$toast.warning('Hóa đơn đã được thanh toán. Không thể hủy !')
            return false
          }
          if (item.getElementsByTagName('status')[0].textContent !== '1') {
            this.$root.$toast.warning('Hóa đơn đã bị thay thế/sửa đổi/hủy. Không thể thay thế !')
            return false
          }
        } else {
          this.Fkey = ''
        }
      } else if (thaoTacId === 9 || thaoTacId === 10) {
        const kq = await this.listInvByCus(this.objHoaDonDienTu.CusCode, '', '')
        if (!kq) return false
        if (kq.errorCode !== 0) {
          this.$root.$toast.error(kq.faultString)
          return false
        }
        const xmlDoc = this.parseXmlDoc(kq.data)
        const item = Array.from(xmlDoc.getElementsByTagName('Item'))
          .find(elm => elm.getElementsByTagName('invToken')[0].textContent === this.invToken)
        if (item) {
          this.Fkey = item.getElementsByTagName('fkey')[0].textContent
          if (item.getElementsByTagName('status')[0].textContent !== '1') {
            this.$root.$toast.warning('Hóa đơn đã bị thay thế/sửa đổi/hủy. Không thể chuyển đổi !')
            return false
          }
        } else {
          this.Fkey = ''
        }
      } else if (thaoTacId === 7) {
        const kq = await this.listInvByCus(this.objHoaDonDienTu.CusCode, '', '')
        if (!kq) return false
        if (kq.errorCode !== 0) {
          this.$root.$toast.error(kq.faultString)
          return false
        }
        const xmlDoc = this.parseXmlDoc(kq.data)
        const item = Array.from(xmlDoc.getElementsByTagName('Item'))
          .find(elm => elm.getElementsByTagName('invToken')[0].textContent === this.invToken)
        if (item) {
          this.Fkey = item.getElementsByTagName('fkey')[0].textContent
          if (item.getElementsByTagName('payment')[0].textContent === '1') {
            this.$root.$toast.warning('Hóa đơn đã được thanh toán. Bạn không thể thực hiện thao tác này !')
            return false
          }
          if (item.getElementsByTagName('status')[0].textContent !== '1') {
            this.$root.$toast.warning('Hóa đơn đã được điều chỉnh hoặc bị hủy !')
            return false
          }
        } else {
          this.Fkey = ''
        }
      } else if (thaoTacId === 8) {
        const kq = await this.listInvByCus(this.objHoaDonDienTu.CusCode, '', '')
        if (!kq) return false
        if (kq.errorCode !== 0) {
          this.$root.$toast.error(kq.faultString)
          return false
        }
        const xmlDoc = this.parseXmlDoc(kq.data)
        const item = Array.from(xmlDoc.getElementsByTagName('Item'))
          .find(elm => elm.getElementsByTagName('invToken')[0].textContent === this.invToken)
        if (item) {
          this.Fkey = item.getElementsByTagName('fkey')[0].textContent
          if (item.getElementsByTagName('payment')[0].textContent === '0') {
            this.$root.$toast.warning('Hóa đơn chưa được thanh toán. Bạn không thể thực hiện thao tác này !')
            return false
          }
          if (item.getElementsByTagName('status')[0].textContent !== '1') {
            this.$root.$toast.warning('Hóa đơn đã được điều chỉnh hoặc bị hủy !')
            return false
          }
        } else {
          this.Fkey = ''
        }
      } else if (thaoTacId === 5) {
        if (this.type === 2 || this.type === 3 || this.type === 4) {
          this.$root.$toast.warning('Hóa đơn điều chỉnh. Bạn không thể hủy !')
          return false
        }
        if (this.type === 1) {
          this.$root.$toast.warning('Hợp đồng đã có hóa đơn bị thay thế. Bạn không thể hủy !')
          return false
        }
        const kq = await this.listInvByCus(this.objHoaDonDienTu.CusCode, '', '')
        if (!kq) return false
        if (kq.errorCode !== 0) {
          this.$root.$toast.error(kq.faultString)
          return false
        }
        const xmlDoc = this.parseXmlDoc(kq.data)
        const item = Array.from(xmlDoc.getElementsByTagName('Item'))
          .find(elm => elm.getElementsByTagName('invToken')[0].textContent === this.invToken)
        if (item) {
          this.Fkey = item.getElementsByTagName('fkey')[0].textContent
          if (item.getElementsByTagName('payment')[0].textContent === '0') {
            this.$root.$toast.warning('Hóa đơn chưa được thanh toán. Bạn không thể thực hiện thao tác này !')
            return false
          }
          if (item.getElementsByTagName('status')[0].textContent !== '1') {
            this.$root.$toast.warning('Hóa đơn đã được điều chỉnh, thay thế hoặc bị hủy. Không thể thực hiện thao tác này !')
            return false
          }
        } else {
          this.Fkey = ''
        }
      }
      return true
    },
    async xuatHddtTQCapNhat ({ thaoTac, BD_HDDT, BD_HDDT_CT, maNd, bdhddtChaId, tagForm, linkForm, maGD }) {
      try {
        const response = await api.xuatHddtTQCapNhat(
          this.axios,
          {
            ds_param: JSON.stringify({
              thaotac: thaoTac,
              js_bd_hddt: BD_HDDT,
              js_bd_hddt_ct: BD_HDDT_CT,
              ma_nd: maNd,
              bdhddt_cha_id: bdhddtChaId,
              tag_form: tagForm,
              link_form: linkForm,
              ma_gd: maGD
            })
          }
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? JSON.parse(data.data.data) : null
      } catch (e) {
        this.$root.$toast.error('Không thể thực hiện cập nhật')
        return null
      }
    },
    transformXml (xml, xsl) {
      let processor
      if (window.ActiveXObject) {
        processor = new ActiveXObject('MSXML2.DOMDocument')
        return xml.transformNodeToObject(xsl, processor)
      } else {
        processor = new XSLTProcessor()
        processor.importStylesheet(xsl)
        return processor.transformToFragment(xml, document)
      }
    },
    convertXmlToString (xml) {
      if (window.XMLSerializer) return (new XMLSerializer()).serializeToString(xml)
      if (xml.xml) return xml.xml
      return ''
    },
    async taoXmlHoaDon (thaoTacId, thongTinCty) {
      const anhXacThucKs = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAABqRJREFUeNrsWX1MU1cUP+17bV8LbfkQlLEZTDOmohOFGaMwE5gOxZlsc//41xIzExMMpkQCkSxZ4qKxMSEhcXFz2ZZt2ZzLNqdRo5CREfET3dgU50SFBcP4EEpp30f7XnfOK8WCpbR8zoSbnL737rv3vt/v3HPOPfdW4/f74VkuWnjGyxyBOQKTLCz9fNL43qwBOPHVF+GqF6EYUW5H6nv+iPS/nIF9FqvlfmJy0i28fz+qGfgfhdIKs9Wyf9u77wCj1cLxz777YKDfKWP9hxF9QFGUWZMR4C3mA5vffgO8IoDAK1CM91i3n95FJODHgWZLguDjzfEHNr21BVitDtxOQRWthgWqM8XHHcA29rFnwK/Mivg1qunujVPBFwPDIPhBEWRZVoXuWVYPRW9uBlOc6TC2LQlPQFZmXPyKH3769pu9COzQ61uLUNukeQTvlYfb0D3NhI7hYMOWjcBxXA3C3TXrPkBB4+fvj9sJ/AYEzzB6cA8END+6rc8ng8clgN5ggsItG8DAcUcQ8o5RBOQZEz+azukfTtiNaBIFxa8BS2YzICB435h9fD6fSoJDEmvX54NOpzuGsK3DYZSmayaKRqOBs6d+LDWajIcLigrQNAzgQc0ryvhhHHnAoJPMSws6vR68Xm8KVjtVAjK9nQHwF06fIvDVr25cDww6pxu1Gg34YBEFAZouXwKPx12Fj/dCZkAe46MAdefOLhxynI8Kiza1T2TNI/C1586UckauOq8wD3QsaT528DeuXgae56tCFzbVB4JhK1TI9hB8sVarbXs5d1kF2l0bPu+g+nDtxxJyxLpzZ0oMnKF6bcG6IYcV0AR8UY/h8XigKQz44RkYbUKk+Ybauq0I/mRufi48n5EGiSlJ0Fh36Vj9hfNMfmHhx9HMBGm+obaWwNesWb9GjfOeQTEmzUuiCM03mnBlfhp8yDogD4sfyTTU1W1lGObkqrWrwGpJgN5Hg6rDrc5/BR1IdxTf76RVNLTfaKH3DXW1JXqDvob60YLEu0TwoeYj9QsVATXf3HR9TPBPmxCCv1hfv03LaE+uWL0CLBYrDOJ0C7yEDicieA5y1+VgTNYfvVj/SwmZx1hmg+93EfjcvNwhhxVjMhsewf/x200QBKFq3GQuCP5KQwOBP5GN4OMtZhW8Vwp8lK4eFy3tBli5JptI1Fxu+LVktE/QM9bvwpk6ko3tGMptBgKajwX87eZmctzKSOCfEFB8cL2xcTuBX56bBUaTKeBoklddYIJCzxT6tFoWVqxerpK41thYqihDbfCKzzsJPL1nGVZt7/OOHCeS8LwH7tz6E0RRBX8wqi3lzStXt6PDfr0sZylwxrjAdEvhNUaa9AxK6KAsLM/JIhLVN65ctVNyhuPsZHXs0axVSwKJmUuKSfNo6/B3Sws5blTgh6MQxflFNhtoZB3wzuiiBI85isHIQNbKxXDr5p3Dv1+7ziD4Q0uzF6PDo8MOSDFFG68kwYPWeyBJUtTgQwl82tPTk6fXGwIxNJoiB3yHSCzNzoR7LfcPLXoxQ7V5jytG8GhibQ/uE4mYwA8TyFyy5PO7LS05GLpK0tLTo+9NJHxe0CMJ20s2NUXmY4zz5B/tbW10jRn8k4UMNWnLzNzdevcupbol89PSYhrE55pYLkVZZkd7O10nBH4EASoZNtvuh62tKol5qanqSjpdhcB3dnRMCnzYZG5hRsbu9ocPZVxJS5NTUqaFBIHv6uycNPgxc6H0hS/s6Wj/B5SurtKkeclTSkJG8N1dXXiVK/E7B+k7kycQJp1ekP7cns6OR9Db3V2akJQ0JSToO4+7e+hajuM7ZHny+5CI+4HUBfP3dHX+K/f19tqtCQmTIkFg+x/3qeBxXIciT80matwdWXLqvLLerh7o7+uzm62WCZEgsM5+J21dy3E8x1TuAKPaEycmJ5X19T6GgX6nPd5sjokEZaYu5wBdy3Ecx1Tvv9ngqcR4xZpoLXP2OTFDddlN8aaoSBB4t8utgsf+DmUa9t5s8EPRFLPVXOZyumQEtdcYZ4xIgsb0uD20OpdjP0e035gWEwotcfFx5e5Bt4zgKjiOC0tCTSkws8QFsRzbO6bz2IYNno3GUlD7lbybx/RXqNBz+hEk6NRN5EUVPLZzxDr2hAj4JzC9nNFQKfCiLAniPjpoIg4EXhK9Knh87/Ar039gFpMPjC56g66KAEuitI9lGfUck8Bj/bTZ/JT/Q6PTs1W4e/sLN+zq4Rc+fzmT//ho5v6pnyMwR2COwKyW/wQYAMgN/37otPaaAAAAAElFTkSuQmCC'
      let xml = ''
      switch (thaoTacId) {
        case 1:
        case 6:
          xml = this.GetInvoiceXml_View(thongTinCty, anhXacThucKs)
          break
        case 2:
          xml = this.GetInvoiceXml_DieuChinh_View(this.objHoaDonDienTuEdit, thongTinCty, anhXacThucKs)
          break
        case 3:
          xml = this.GetInvoiceXml_ThayThe_View(this.objHoaDonDienTuEdit, thongTinCty, anhXacThucKs)
          break
        default:
          xml = ''
          break
      }
      return xml
    },
    GetInvoiceXml_View (thongTinCty, anhXacThucKs) {
      let xml = '<HDon><DLHDon Id="DuLieuKy">'
      xml += '<TTChung>'
      xml += `<NLap>${moment(new Date()).format('YYYY-MM-DD')}</NLap>`
      xml += '<THDon>Hóa đơn giá trị gia tăng</THDon>'
      xml += `<KHMSHDon>${this.traCuuFilter.mauSoId}</KHMSHDon>`
      xml += `<KHHDon>${this.traCuuFilter.kyHieuId}</KHHDon>`
      xml += `<SHDon>${this.traCuuFilter.txtSoHoaDon}</SHDon>`
      xml += `<HTTToan>${this.hoaDonFilter.txtHTTT}</HTTToan>`
      if (this.them_extra) {
        xml += `<TTKhac><TTin><TTruong>${this.Fkey}</TTruong></TTin></TTKhac>`
      }
      xml += '</TTChung>'
      xml += '<NDHDon>'
      xml += '<NBan>'
      xml += thongTinCty.toString()
      xml += `<TTKhac><TTin><TTruong>Teller</TTruong><DLieu>${this.maNhanVien}</DLieu></TTin></TTKhac>`
      xml += '</NBan>'
      xml += '<NMua>'
      xml += `<MKHang>${this.vma_kh}</MKHang>`
      xml += `<Ten><![CDATA[${this.hoaDonFilter.txtTenKH}]]></Ten>`
      xml += `<DChi><![CDATA[${this.hoaDonFilter.txtDiaChiKH}]]></DChi>`
      xml += `<SDThoai><![CDATA[${this.hoaDonFilter.txtSoDT}]]></SDThoai>`
      xml += `<MST>${this.hoaDonFilter.txtMSTKH}</MST>`
      if (this.tt78_stk_nh) {
        xml += `<STKNHang><![CDATA[${this.hoaDonFilter.txtSoTaiKhoan}]]></STKNHang>`
        xml += `<TNHang>${this.hoaDonFilter.nganHangId}</TNHang>`
      }
      xml += '</NMua>'
      xml += '<TToan>'
      xml += `<TgTCThue>${Math.abs(this.hoaDonFilter.txtTongTien)}</TgTCThue>`
      xml += '<TTCKTMai></TTCKTMai>'
      xml += `<THTTLTSuat><LTSuat><TSuat>${this.hoaDonFilter.txtTiLeThue}</TSuat></LTSuat></THTTLTSuat>`
      xml += `<TgTThue>${Math.abs(this.hoaDonFilter.txtTongThue)}</TgTThue>`
      xml += `<TgTTTBSo>${Math.abs(this.hoaDonFilter.txtTongTra)}</TgTTTBSo>`
      xml += `<TgTTTBChu>${this.hoaDonFilter.txtTienChu}</TgTTTBChu>`
      xml += '</TToan>'
      xml += '<DSHHDVu>'
      for (const product of this.danhSachHoaDon.dataSources) {
        if (product.ProdName) {
          xml += '<HHDVu>'
          xml += `<THHDVu><![CDATA[${product.ProdName}]]></THHDVu>`
          xml += `<DVTinh>${product.ProdUnit}</DVTinh>`
          xml += `<SLuong>${product.ProdQuantity}</SLuong>`
          xml += `<DGia>${Math.abs(product.ProdPrice)}</DGia>`
          xml += `<ThTien>${Math.abs(product.Total)}</ThTien>`
          xml += '</HHDVu>'
        }
      }
      xml += '</DSHHDVu>'
      xml += '</NDHDon>'
      xml += '</DLHDon>'
      xml += `<image URI="${anhXacThucKs}">Signature Valid</image>`
      xml += `<Fkey>${this.Fkey}</Fkey>`
      xml += '</HDon>'
      return xml
    },
    GetInvoiceXml_DieuChinh_View (obj, thongTinCty, anhXacThucKs) {
      const kieuDieuChinhId = this.traCuuFilter.kieuDieuChinhId
      let type = ''
      if (kieuDieuChinhId === 1) type = '2'
      else if (kieuDieuChinhId === 2) type = '3'
      else type = '4'
      let xml = '<HDon><DLHDon Id="DuLieuKy">'
      xml += '<TTChung>'
      xml += `<NLap>${moment(new Date()).format('YYYY-MM-DD')}</NLap>`
      xml += '<THDon>Hóa đơn giá trị gia tăng</THDon>'
      xml += `<KHMSHDon>${this.traCuuFilter.mauSoId}</KHMSHDon>`
      xml += `<KHHDon>${this.traCuuFilter.kyHieuId}</KHHDon>`
      xml += `<SHDon>${this.traCuuFilter.txtSoHoaDon}</SHDon>`
      xml += `<HTTToan>${obj.Kind_of_Payment}</HTTToan>`
      if (this.them_extra) {
        xml += `<TTKhac><TTin><TTruong>${this.fkey_new}</TTruong></TTin></TTKhac>`
      }
      xml += '<TTHDLQuan>'
      xml += `<TCHDon>${type}</TCHDon>`
      xml += '</TTHDLQuan>'
      xml += '</TTChung>'
      xml += '<NDHDon>'
      xml += '<NBan>'
      xml += thongTinCty.toString()
      xml += `<TTKhac><TTin><TTruong>Teller</TTruong><DLieu>${this.maNhanVien}</DLieu></TTin></TTKhac>`
      xml += '</NBan>'
      xml += '<NMua>'
      xml += `<MKHang>${obj.CusCode}</MKHang>`
      xml += `<Ten><![CDATA[${obj.CusName}]]></Ten>`
      xml += `<DChi><![CDATA[${obj.CusAddress}]]></DChi>`
      xml += `<SDThoai><![CDATA[${obj.CusPhone}]]></SDThoai>`
      xml += `<MST>${obj.CusTaxCode}</MST>`
      if (this.tt78_stk_nh) {
        xml += `<STKNHang><![CDATA[${obj.CusBankNo}]]></STKNHang>`
        xml += `<TNHang>${obj.CusBankName}</TNHang>`
      }
      xml += '</NMua>'
      xml += '<TToan>'
      xml += `<TgTCThue>${kieuDieuChinhId === 2 ? -Math.abs(this.hoaDonFilter.txtTongTien) : Math.abs(this.hoaDonFilter.txtTongTien)}</TgTCThue>`
      xml += '<TTCKTMai></TTCKTMai>'
      xml += `<THTTLTSuat><LTSuat><TSuat>${this.hoaDonFilter.txtTiLeThue}</TSuat></LTSuat></THTTLTSuat>`
      xml += `<TgTThue>${kieuDieuChinhId === 2 ? -Math.abs(this.hoaDonFilter.txtTongThue) : Math.abs(this.hoaDonFilter.txtTongThue)}</TgTThue>`
      const tongTra = kieuDieuChinhId === 2 ? -Math.abs(this.hoaDonFilter.txtTongTra) : Math.abs(this.hoaDonFilter.txtTongTra)
      xml += `<TgTTTBSo>${tongTra}</TgTTTBSo>`
      const tienChu = DocSo(Math.abs(tongTra))
      xml += `<TgTTTBChu>${kieuDieuChinhId === 2 ? `Giảm ${tienChu}` : tienChu}</TgTTTBChu>`
      xml += '</TToan>'
      xml += '<DSHHDVu>'
      for (const product of this.danhSachHoaDon.dataSources) {
        if (product.ProdName) {
          xml += '<HHDVu>'
          xml += `<THHDVu><![CDATA[${product.ProdName}]]></THHDVu>`
          xml += `<DVTinh>${product.ProdUnit}</DVTinh>`
          xml += `<SLuong>${product.ProdQuantity}</SLuong>`
          xml += `<DGia>${Math.abs(product.ProdPrice)}</DGia>`
          xml += `<ThTien>${kieuDieuChinhId === 2 ? -Math.abs(product.Total) : Math.abs(product.Total)}</ThTien>`
          xml += '</HHDVu>'
        }
      }
      xml += '</DSHHDVu>'
      xml += '</NDHDon>'
      xml += '</DLHDon>'
      xml += `<image URI="${anhXacThucKs}">Signature Valid</image>`
      xml += `<Fkey>${this.Fkey}</Fkey>`
      xml += '<isAdjust>Hóa đơn điều chỉnh</isAdjust>'
      xml += '</HDon>'
      // if (this.tagForm === 2) xml += '<PaymentStatus>1</PaymentStatus>'
      return xml
    },
    GetInvoiceXml_ThayThe_View (obj, thongTinCty, anhXacThucKs) {
      let xml = '<HDon><DLHDon Id="DuLieuKy">'
      xml += '<TTChung>'
      xml += `<NLap>${moment(new Date()).format('YYYY-MM-DD')}</NLap>`
      xml += '<THDon>Hóa đơn giá trị gia tăng</THDon>'
      xml += `<KHMSHDon>${this.traCuuFilter.mauSoId}</KHMSHDon>`
      xml += `<KHHDon>${this.traCuuFilter.kyHieuId}</KHHDon>`
      xml += `<SHDon>${this.traCuuFilter.txtSoHoaDon}</SHDon>`
      xml += `<HTTToan>${obj.Kind_of_Payment}</HTTToan>`
      if (this.them_extra) {
        xml += `<TTKhac><TTin><TTruong>${this.fkey_new}</TTruong></TTin></TTKhac>`
      }
      xml += '</TTChung>'
      xml += '<NDHDon>'
      xml += '<NBan>'
      xml += thongTinCty.toString()
      xml += `<TTKhac><TTin><TTruong>Teller</TTruong><DLieu>${this.maNhanVien}</DLieu></TTin></TTKhac>`
      xml += '</NBan>'
      xml += '<NMua>'
      xml += `<MKHang>${obj.CusCode}</MKHang>`
      xml += `<Ten>${this.convertSpecialCharacter(obj.CusName)}</Ten>`
      xml += `<DChi>${this.convertSpecialCharacter(obj.CusAddress)}</DChi>`
      xml += `<SDThoai>${obj.CusPhone}</SDThoai>`
      xml += `<MST>${obj.CusTaxCode}</MST>`
      if (this.tt78_stk_nh) {
        xml += `<STKNHang><![CDATA[${obj.CusBankNo}]]></STKNHang>`
        xml += `<TNHang>${obj.CusBankName}</TNHang>`
      }
      xml += '</NMua>'
      xml += '<TToan>'
      xml += `<TgTCThue>${Math.abs(this.hoaDonFilter.txtTongTien)}</TgTCThue>`
      xml += '<TTCKTMai></TTCKTMai>'
      xml += `<THTTLTSuat><LTSuat><TSuat>${this.hoaDonFilter.txtTiLeThue}</TSuat></LTSuat></THTTLTSuat>`
      xml += `<TgTThue>${Math.abs(this.hoaDonFilter.txtTongThue)}</TgTThue>`
      xml += `<TgTTTBSo>${Math.abs(this.hoaDonFilter.txtTongTra)}</TgTTTBSo>`
      xml += `<TgTTTBChu>${this.hoaDonFilter.txtTienChu}</TgTTTBChu>`
      xml += '</TToan>'
      xml += '<DSHHDVu>'
      for (const product of this.danhSachHoaDon.dataSources) {
        if (product.ProdName) {
          xml += '<HHDVu>'
          xml += `<THHDVu><![CDATA[${product.ProdName}]]></THHDVu>`
          xml += `<DVTinh>${product.ProdUnit}</DVTinh>`
          xml += `<SLuong>${product.ProdQuantity}</SLuong>`
          xml += `<DGia>${Math.abs(product.ProdPrice)}</DGia>`
          xml += `<ThTien>${Math.abs(product.Total)}</ThTien>`
          xml += '</HHDVu>'
        }
      }
      xml += '</DSHHDVu>'
      xml += '</NDHDon>'
      xml += '</DLHDon>'
      xml += `<image URI="${anhXacThucKs}">Signature Valid</image>`
      xml += `<Fkey>${this.Fkey}</Fkey>`
      xml += '<isReplace>Hóa đơn thay thế</isReplace>'
      xml += '</HDon>'
      // if (this.tagForm === 2) xml += '<PaymentStatus>1</PaymentStatus>'
      return xml
    },
    async onSuccessEinvoiceDetail (data) {
      if (data.ok) {
        this.loading(true)
        try {
          switch (data.thaoTacId) {
            case 1:
              await this.Xuathoadon()
              break
            case 2:
              await this.DieuChinhHoaDon()
              break
            case 3:
              await this.ThayTheHoaDon()
              break
            case 4:
              await this.HuyHoaDon()
              break
            case 5:
              await this.HuyThanhToan_HuyHoaDon()
              break
            default:
              await this.Xuathoadon_GachNo()
              break
          }
        } catch (e) {
        } finally {
          this.loading(false)
          this.$refs.popupEinvoiceDetailRef.hideModal()
        }
      }
    },
    async Xuathoadon () {
      if (!this.traCuuFilter.mauSoId) {
        this.$root.$toast.warning('Bạn chưa chọn mẫu số !')
        return false
      }
      if (!this.traCuuFilter.kyHieuId) {
        this.$root.$toast.warning('Bạn chưa chọn ký hiệu !')
        return false
      }
      try {
        const xmlCusData = await this.GetInvoiceXml_Cus()
        const updateResult = await this.UpdateCus(0, xmlCusData)
        if (!updateResult) return false
        if (updateResult.errorCode !== 0) {
          this.$root.$toast.error(updateResult.faultString)
          return false
        }
        const xmlInvData = await this.GetInvoiceXml()
        const importPublishResult = await this.importAndPublishInv({
          acPass: this.acPassHDDT,
          account: this.accountHDDT,
          convert: 0,
          pattern: this.cboPattern.find(elm => elm.LOAIHOADON_ID === this.traCuuFilter.mauSoId).TEN_LOAIHD,
          serial: this.cboSeri.find(elm => elm.seri_id === this.traCuuFilter.kyHieuId).seri,
          xmlInvData
        })
        if (!importPublishResult) return false
        if (importPublishResult.errorCode !== 0) {
          this.$root.$toast.error(importPublishResult.faultString)
          return false
        }
        const result = importPublishResult.data.split(':')
        if (result[0] === 'OK') {
          const result1 = result[1].split(';')
          const InvoicePattern1 = result1[0]
          this.objHoaDonDienTuEdit.InvoicePattern = InvoicePattern1
          const result2 = result1[1].split('-')
          const SerialNo1 = result2[0]
          this.objHoaDonDienTuEdit.SerialNo = SerialNo1
          const result3 = result2[1].split('_')
          const InvoiceNo1 = result3[1]
          this.objHoaDonDienTuEdit.InvoiceNo = InvoiceNo1
          const pattern = this.cboPattern.find(elm => elm.TEN_LOAIHD === InvoicePattern1)
          if (pattern) this.traCuuFilter.mauSoId = pattern.LOAIHOADON_ID
          const seri = this.cboSeri.find(elm => elm.seri === SerialNo1)
          if (seri) this.traCuuFilter.kyHieuId = seri.seri_id
          this.traCuuFilter.txtSoHoaDon = +InvoiceNo1
          if (!(await this.TraCuu(InvoicePattern1, SerialNo1, +InvoiceNo1))) return false
          const bd = this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, this.traCuuFilter.kyHieuId)
          const bdct = this.TaoDuLieuBDCT()
          const response = await this.xuatHddtTQCapNhat({
            thaoTac: this.traCuuFilter.thaoTacId,
            BD_HDDT: bd,
            BD_HDDT_CT: bdct,
            maNd: this.maNguoiDung,
            bdhddtChaId: this.bdhddt_cha_id,
            tagForm: this.tagForm,
            linkForm: this.linkForm,
            maGD: this.traCuuFilter.txtMaGD
          })
          if (response) {
            if (response.ERROR_CODE === 1) {
              this.$root.$toast.success('Xuất hóa đơn thành công !')
              this.setButtonDisabled('tsbtnGhiLai', true)
              this.setButtonDisabled('tsbtnNhapMoi', false)
              this.invToken = ''
              this.Fkey = ''
              this.fkey_new = ''
              // this.clean();
              await this.HienThi_ThongTin()
            } else {
              this.$root.$toast.error(response.MESSAGE)
            }
          } else {
            this.$root.$toast.error('Không thể cập nhật thông tin hóa đơn vào hệ thống')
          }
        } else {
          this.$root.$toast.error(`Có lỗi trong quá trình xuất hóa đơn: ${importPublishResult.data}`)
        }
      } catch (e) {
      }
    },
    async GetInvoiceXml_Cus () {
      if (this.traCuuFilter.txtMaGD) this.vma_kh = this.traCuuFilter.txtMaGD
      else {
        this.vma_kh = await this.sinh_ma_kh_hddt(1)
        this.traCuuFilter.txtMaGD = this.vma_kh
      }
      let xml = '<Customers><Customer>'
      xml += `<Name><![CDATA[${this.hoaDonFilter.txtTenKH}]]></Name>`
      xml += `<Code>${this.vma_kh}</Code>`
      xml += `<TaxCode><![CDATA[${this.hoaDonFilter.txtMSTKH}]]></TaxCode>`
      xml += `<Address><![CDATA[${this.hoaDonFilter.txtDiaChiKH}]]></Address>`
      xml += '<BankAccountName></BankAccountName>'
      xml += '<BankName></BankName>'
      xml += '<BankNumber></BankNumber>'
      xml += `<Email><![CDATA[${this.hoaDonFilter.txtEmail}]]></Email>`
      xml += '<Fax></Fax>'
      xml += `<Phone><![CDATA[${this.hoaDonFilter.txtSoDT}]]></Phone>`
      xml += '<ContactPerson></ContactPerson>'
      xml += '<RepresentPerson></RepresentPerson>'
      xml += `<CusType>${this.hoaDonFilter.loaiKHId}</CusType>`
      xml += '</Customer></Customers>'
      return xml
    },
    async sinh_ma_kh_hddt (kieu) {
      try {
        const response = await api.sinh_ma_kh_hddt(this.axios, kieu)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : ''
      } catch (e) {
        this.$root.$toast.error('Không thể sinh mã khách hàng')
        return ''
      }
    },
    async UpdateCus (convert, xmlCusData) {
      try {
        const response = await api.UpdateCus(this.axios, { convert, xmlCusData })
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể cập nhật thông tin khách hàng')
        return null
      }
    },
    async GetInvoiceXml () {
      const kieu = this.tagForm === 2 ? 2 : 1
      this.Fkey = await this.sinh_fkey(kieu)
      let xmlInv = `<Invoices><Inv><key>${this.Fkey}</key><Invoice>`
      xmlInv += `<CusCode>${this.vma_kh}</CusCode>`
      xmlInv += `<CusName><![CDATA[${this.hoaDonFilter.txtTenKH}]]></CusName>`
      xmlInv += `<CusAddress><![CDATA[${this.hoaDonFilter.txtDiaChiKH}]]></CusAddress>`
      xmlInv += `<CusPhone><![CDATA[${this.hoaDonFilter.txtSoDT}]]></CusPhone>`
      xmlInv += `<CusTaxCode>${this.hoaDonFilter.txtMSTKH}</CusTaxCode>`
      if (this.tt78_stk_nh) {
        xmlInv += `<CusBankNo><![CDATA[${this.hoaDonFilter.txtSoTaiKhoan}]]></CusBankNo>`
        xmlInv += `<CusBankName>${this.hoaDonFilter.nganHangId
          ? this.cboNganHang.find(elm => elm.NGANHANG_ID === this.hoaDonFilter.nganHangId).TEN_NH
          : ''}</CusBankName>`
      }
      xmlInv += `<PaymentMethod>${this.hoaDonFilter.txtHTTT}</PaymentMethod>`
      xmlInv += '<KindOfService></KindOfService>'
      if (this.them_extra) xmlInv += `<Extra>${this.Fkey}</Extra>`
      xmlInv += '<Products>'
      for (const product of this.danhSachHoaDon.dataSources) {
        if (product.ProdName) {
          xmlInv += '<Product>'
          xmlInv += `<ProdName><![CDATA[${product.ProdName}]]></ProdName>`
          xmlInv += `<ProdUnit>${product.ProdUnit}</ProdUnit>`
          xmlInv += `<Amount>${product.Amount}</Amount>`
          xmlInv += `<ProdQuantity>${product.ProdQuantity === 0 ? '' : product.ProdQuantity}</ProdQuantity>`
          xmlInv += `<ProdPrice>${product.ProdPrice === 0 ? '' : product.ProdPrice}</ProdPrice>`
          xmlInv += `<VATRate>${product.VATRate}</VATRate>`
          xmlInv += `<VATAmount>${Math.abs(product.VATAmount)}</VATAmount>`
          xmlInv += `<Total>${Math.abs(product.Total)}</Total>`
          xmlInv += '</Product>'
        }
      }
      xmlInv += '</Products>'
      xmlInv += `<Total>${Math.abs(this.hoaDonFilter.txtTongTien)}</Total>`
      xmlInv += '<DiscountAmount></DiscountAmount>'
      xmlInv += `<VATRate>${this.hoaDonFilter.txtTiLeThue}</VATRate>`
      xmlInv += `<VATAmount>${Math.abs(this.hoaDonFilter.txtTongThue)}</VATAmount>`
      xmlInv += `<Amount>${Math.abs(this.hoaDonFilter.txtTongTra)}</Amount>`
      xmlInv += `<AmountInWords>${this.hoaDonFilter.txtTienChu}</AmountInWords>`
      xmlInv += `<GrossValue_NonTax>${Math.abs(this.hoaDonFilter.txtTongTienKhongThue)}</GrossValue_NonTax>`
      xmlInv += `<GrossValue>${Math.abs(this.hoaDonFilter.txtTongTienKhongChiuThue)}</GrossValue>`
      xmlInv += `<GrossValue0>${Math.abs(this.hoaDonFilter.txtTongTienChiuThue0)}</GrossValue0>`
      xmlInv += '<VatAmount0>0</VatAmount0>'
      xmlInv += `<GrossValue5>${Math.abs(this.hoaDonFilter.txtTongTienChiuThue5)}</GrossValue5>`
      xmlInv += `<VatAmount5>${Math.abs(this.hoaDonFilter.txtTongTienThue5)}</VatAmount5>`
      xmlInv += `<GrossValue8>${Math.abs(this.hoaDonFilter.txtTongTienChiuThue8)}</GrossValue8>`
      xmlInv += `<VatAmount8>${Math.abs(this.hoaDonFilter.txtTongTienThue8)}</VatAmount8>`
      xmlInv += `<GrossValue10>${Math.abs(this.hoaDonFilter.txtTongTienChiuThue10)}</GrossValue10>`
      xmlInv += `<VatAmount10>${Math.abs(this.hoaDonFilter.txtTongTienThue10)}</VatAmount10>`
      xmlInv += `<Teller>${this.maNhanVien}</Teller>`
      xmlInv += '</Invoice>'
      xmlInv += '</Inv>'
      xmlInv += '</Invoices>'
      return xmlInv
    },
    async sinh_fkey (kieu) {
      try {
        const response = await api.sinh_fkey(this.axios, kieu)
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? data.data : ''
      } catch (e) {
        this.$root.$toast.error('Không thể sinh fkey')
        return ''
      }
    },
    async importAndPublishInv ({ acPass, account, convert, pattern, serial, xmlInvData }) {
      try {
        const response = await api.importAndPublishInv(
          this.axios,
          { acPass, account, convert, pattern, serial, xmlInvData }
        )
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể publish invoice')
        return null
      }
    },
    TaoDuLieuBD (objOld, objNew, seriId) {
      console.log('TaoDuLieuBD', this.hoaDonFilter.txtGhiChu)
      const dsBDHDDT = []
      const row = {}
      row.MA_TT = objNew.CusCode
      row.NHANVIEN_ID = this.nhanVienId
      row.DONVI_ID = this.donViId
      row.KIEU_ID = this.tagForm === 2 ? 3 : 1
      row.TEN_TT = this.hoaDonFilter.txtTenKH
      row.DIACHI_TT = this.hoaDonFilter.txtDiaChiKH
      row.SDT = this.hoaDonFilter.txtSoDT
      row.MST = this.hoaDonFilter.txtMSTKH
      row.EMAIL = this.hoaDonFilter.txtEmail
      row.FKEY = this.Fkey
      row.HINHTHUC_TT = this.hoaDonFilter.txtHTTT
      row.TIEN = Math.abs(this.hoaDonFilter.txtTongTien)
      row.VAT = Math.abs(this.hoaDonFilter.txtTongThue)
      row.TYLE_VAT = Math.abs(+this.hoaDonFilter.txtTiLeThue)
      row.TIENBANGCHU = this.hoaDonFilter.txtTienChu
      if (this.traCuuFilter.thaoTacId === 1) {
        row.KIEU_BD = 4
        row.THONGTINHD_CU = ''
        row.THONGTINHD_MOI = this.GetXMLFromObject(objNew)
        row.TRANGTHAI = 1
      } else if (this.traCuuFilter.thaoTacId === 2) {
        row.THONGTINHD_CU = this.GetXMLFromObject(objOld)
        row.THONGTINHD_MOI = this.GetXMLFromObject(objNew)
        if (this.traCuuFilter.kieuDieuChinhId === 1) row.KIEU_BD = 8
        else if (this.traCuuFilter.kieuDieuChinhId === 2) row.KIEU_BD = 9
        else if (this.traCuuFilter.kieuDieuChinhId === 3) row.KIEU_BD = 10
        else row.KIEU_BD = 1
        row.TRANGTHAI = 1
        row.BDHDDT_CHA_ID = this.bdhddt_cha_id
        row.FKEY = this.fkey_new
      } else if (this.traCuuFilter.thaoTacId === 3) {
        row.KIEU_BD = 2
        row.THONGTINHD_CU = this.GetXMLFromObject(objOld)
        row.THONGTINHD_MOI = this.GetXMLFromObject(objNew)
        row.TRANGTHAI = 1
        row.BDHDDT_CHA_ID = this.bdhddt_cha_id
        row.FKEY = this.fkey_new
      } else if (this.traCuuFilter.thaoTacId === 4) {
        row.KIEU_BD = 3
        row.THONGTINHD_CU = this.GetXMLFromObject(objOld)
        row.THONGTINHD_MOI = this.GetXMLFromObject(objNew)
        row.TRANGTHAI = 4
        row.BDHDDT_CHA_ID = this.bdhddt_cha_id
      } else if (this.traCuuFilter.thaoTacId === 5) {
        row.KIEU_BD = 11
        row.THONGTINHD_CU = this.GetXMLFromObject(objOld)
        row.THONGTINHD_MOI = this.GetXMLFromObject(objNew)
        row.TRANGTHAI = 4
        row.BDHDDT_CHA_ID = this.bdhddt_cha_id
      } else if (this.traCuuFilter.thaoTacId === 6) {
        row.KIEU_BD = 5
        row.THONGTINHD_CU = this.GetXMLFromObject(objOld)
        row.THONGTINHD_MOI = this.GetXMLFromObject(objNew)
        row.TRANGTHAI = 1
      } else if (this.traCuuFilter.thaoTacId === 7) {
        row.KIEU_BD = 6
        row.THONGTINHD_CU = this.GetXMLFromObject(objOld)
        row.THONGTINHD_MOI = this.GetXMLFromObject(objNew)
        row.TRANGTHAI = 1
        row.BDHDDT_CHA_ID = this.bdhddt_cha_id
      } else if (this.traCuuFilter.thaoTacId === 8) {
        row.KIEU_BD = 7
        row.THONGTINHD_CU = this.GetXMLFromObject(objOld)
        row.THONGTINHD_MOI = this.GetXMLFromObject(objNew)
        row.TRANGTHAI = 1
        row.BDHDDT_CHA_ID = this.bdhddt_cha_id
      }
      row.LOAIHOADON_CU = objOld.InvoicePattern
      row.LOAIHOADON_MOI = objNew.InvoicePattern
      row.SERI_CU = objOld.SerialNo
      row.SERI_MOI = objNew.SerialNo
      row.SO_HD_CU = objOld.InvoiceNo
      row.SO_HD_MOI = objNew.InvoiceNo
      row.TONGTIEN = Math.abs(this.t_tien_edited)
      row.TIENGOC = this.tiengoc
      row.NGUOI_CN = this.maNguoiDung
      row.MAY_CN = this.machine
      row.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
      row.GHICHU = `(Chức năng Động) ${this.hoaDonFilter.txtGhiChu}`
      row.SERI_MOI_ID = seriId
      row.LOAIHOADON_MOI_ID = this.traCuuFilter.mauSoId
      if (this.hoaDonFilter.txtFilePath) row.FILE_DINHKEM = this.hoaDonFilter.filePathBytes
      dsBDHDDT.push(row)
      return dsBDHDDT
    },
    GetXMLFromObject (data) {
      let xml = '<Content>'
      for (const [key, value] of Object.entries(data)) {
        if (Array.isArray(value)) {
          xml += `<${key}>`
          for (const elm of value) {
            xml += '<Product>'
            for (const [subKey, subValue] of Object.entries(elm)) {
              xml += `<${subKey}>${subValue}</${subKey}>`
            }
            xml += '</Product>'
          }
          xml += `</${key}>`
        } else xml += `<${key}>${value}</${key}>`
      }
      xml += '</Content>'
      return xml
    },
    TaoDuLieuBDCT () {
      const bdHDDTCT = []
      for (const product of this.danhSachHoaDon.dataSources) {
        if (product.ProdName) {
          bdHDDTCT.push({
            BDHDDT_ID: this.BDHDDT_ID,
            DONGIA: product.ProdPrice,
            DONVITINH: product.ProdUnit,
            NOIDUNG: product.ProdName,
            SOLUONG: product.ProdQuantity,
            STT: product.STT,
            THANHTIEN: product.Amount
          })
        }
      }
      return bdHDDTCT
    },
    async DieuChinhHoaDon () {
      if (!this.traCuuFilter.mauSoId) {
        this.$root.$toast.warning('Bạn chưa chọn mẫu số !')
        return false
      }
      if (!this.traCuuFilter.kyHieuId) {
        this.$root.$toast.warning('Bạn chưa chọn ký hiệu !')
        return false
      }
      const lstProductFilter = []
      this.lstProduct.forEach(product => {
        if (product.ProdName) {
          lstProductFilter.push({
            Code: product.Code,
            Total: product.Total,
            ProdName: product.ProdName,
            ProdUnit: product.ProdUnit,
            ProdQuantity: product.ProdQuantity,
            ProdPrice: product.ProdPrice,
            Discount: product.Discount,
            DiscountAmount: product.DiscountAmount,
            VATRate: product.VATRate,
            VATAmount: product.VATAmount,
            Amount: product.Amount
          })
        }
      })
      this.objHoaDonDienTuEdit.Products = lstProductFilter
      try {
        const xmlInvData = await this.GetInvoiceXml_DieuChinh(this.objHoaDonDienTuEdit)
        const kq = this.tagForm === 2
          ? await this.AdjustInvoiceAction_VTT(xmlInvData)
          : await this.AdjustInvoiceAction_TT78(xmlInvData)
        if (kq.errorCode !== 0) {
          this.$root.$toast.error(kq.faultString)
          return false
        }
        const result = kq.data.split(':')
        if (result[0] === 'OK') {
          const result1 = result[1].split(';')
          this.objHoaDonDienTuEdit.InvoicePattern = result1[0]
          this.objHoaDonDienTuEdit.SerialNo = result1[1]
          const result2 = result1[2].split('_')
          this.objHoaDonDienTuEdit.InvoiceNo = result2[1]
          this.fkey_new = result2[0]
          const bd = this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, this.traCuuFilter.kyHieuId)
          const bdct = this.TaoDuLieuBDCT()
          const response = await this.xuatHddtTQCapNhat({
            thaoTac: this.traCuuFilter.thaoTacId,
            BD_HDDT: bd,
            BD_HDDT_CT: bdct,
            maNd: this.maNguoiDung,
            bdhddtChaId: this.bdhddt_cha_id,
            tagForm: this.tagForm,
            linkForm: this.linkForm,
            maGD: this.traCuuFilter.txtMaGD
          })
          if (response) {
            if (response.ERROR_CODE === 1) {
              const pattern = this.cboPattern.find(elm => elm.TEN_LOAIHD === result1[0])
              if (pattern) this.traCuuFilter.mauSoId = pattern.LOAIHOADON_ID
              const seri = this.cboSeri.find(elm => elm.seri === result1[1])
              if (seri) this.traCuuFilter.kyHieuId = seri.seri_id
              this.traCuuFilter.txtSoHoaDon = +result2[1]
              this.$root.$toast.success('Điều chỉnh hóa đơn thành công')
              this.setButtonDisabled('tsbtnGhiLai', true)
              this.invToken = ''
              this.Fkey = ''
              this.fkey_new = ''
              await this.HienThi_ThongTin()
            } else {
              this.$root.$toast.error(response.MESSAGE)
            }
          } else {
            this.$root.$toast.error('Không thể cập nhật thông tin hóa đơn vào hệ thống')
          }
        } else {
          this.$root.$toast.error(`Có lỗi trong quá trình điều chỉnh hóa đơn:  ${kq.data}`)
        }
      } catch (e) {
      }
    },
    async GetInvoiceXml_DieuChinh (obj) {
      const kieu = this.tagForm === 2 ? 2 : 1
      this.fkey_new = await this.sinh_fkey(kieu)
      let xml = '<AdjustInv>'
      xml += `<key>${this.fkey_new}</key>`
      xml += `<CusCode>${obj.CusCode}</CusCode>`
      xml += `<CusName><![CDATA[${obj.CusName}]]></CusName>`
      xml += `<CusAddress><![CDATA[${obj.CusAddress}]]></CusAddress>`
      xml += `<CusPhone><![CDATA[${obj.CusPhone}]]></CusPhone>`
      xml += `<CusTaxCode><![CDATA[${obj.CusTaxCode}]]></CusTaxCode>`
      if (this.tt78_stk_nh) {
        xml += `<CusBankNo><![CDATA[${obj.CusBankNo}]]></CusBankNo>`
        xml += `<CusBankName>${obj.CusBankName}</CusBankName>`
      }
      xml += `<PaymentMethod>${obj.Kind_of_Payment}</PaymentMethod>`
      xml += `<KindOfService>${obj.KindOfService}</KindOfService>`
      const type = this.traCuuFilter.kieuDieuChinhId === 1 ? '2' : (this.traCuuFilter.kieuDieuChinhId === 2 ? '3' : '4')
      xml += `<Type>${type}</Type>`
      if (this.them_extra) xml += `<Extra>${this.fkey_new}</Extra>`
      xml += '<Products>'
      for (const product of obj.Products) {
        xml += '<Product>'
        xml += `<ProdName>${this.convertSpecialCharacter(product.ProdName)}</ProdName>`
        xml += `<ProdUnit>${product.ProdUnit}</ProdUnit>`
        xml += `<ProdQuantity>${product.ProdQuantity}</ProdQuantity>`
        xml += `<ProdPrice>${product.ProdPrice.toString() ? Math.abs(product.ProdPrice) : product.ProdPrice}</ProdPrice>`
        xml += `<Amount>${product.Amount.toString() ? Math.abs(product.Amount) : ''}</Amount>`
        xml += `<VATRate>${product.VATRate}</VATRate>`
        xml += `<VATAmount>${Math.abs(product.VATAmount)}</VATAmount>`
        xml += `<Total>${Math.abs(product.Total)}</Total>`
        xml += '</Product>'
      }
      xml += '</Products>'
      xml += `<Total>${obj.Total.toString() ? Math.abs(obj.Total) : obj.Total}</Total>`
      xml += `<VATRate>${obj.VAT_Rate}</VATRate>`
      if (obj.VAT_Amount.toString()) {
        xml += `<VATAmount>${obj.VAT_Amount}</VATAmount>`
      } else if (obj.VAT_Amount === 0) {
        xml += `<VATAmount>0</VATAmount>`
      } else {
        xml += `<VATAmount></VATAmount>`
      }
      xml += `<Amount>${obj.Amount.toString() ? Math.abs(obj.Amount) : obj.Amount}</Amount>`
      xml += `<AmountInWords>${obj.Amount.toString() ? obj.Amount_words : '\\\\'}</AmountInWords>`
      xml += '<PaymentStatus>1</PaymentStatus>'
      xml += `<GrossValue_NonTax>${Math.abs(this.hoaDonFilter.txtTongTienKhongThue)}</GrossValue_NonTax>`
      xml += `<GrossValue>${Math.abs(this.hoaDonFilter.txtTongTienKhongChiuThue)}</GrossValue>`
      xml += `<GrossValue0>${Math.abs(this.hoaDonFilter.txtTongTienChiuThue0)}</GrossValue0>`
      xml += '<VatAmount0>0</VatAmount0>'
      xml += `<GrossValue5>${Math.abs(this.hoaDonFilter.txtTongTienChiuThue5)}</GrossValue5>`
      xml += `<VatAmount5>${Math.abs(this.hoaDonFilter.txtTongTienThue5)}</VatAmount5>`
      xml += `<GrossValue8>${Math.abs(this.hoaDonFilter.txtTongTienChiuThue8)}</GrossValue8>`
      xml += `<VatAmount8>${Math.abs(this.hoaDonFilter.txtTongTienThue8)}</VatAmount8>`
      xml += `<GrossValue10>${Math.abs(this.hoaDonFilter.txtTongTienChiuThue10)}</GrossValue10>`
      xml += `<VatAmount10>${Math.abs(this.hoaDonFilter.txtTongTienThue10)}</VatAmount10>`
      xml += `<Teller>${this.maNhanVien}</Teller>`
      xml += '</AdjustInv>'
      return xml
    },
    convertSpecialCharacter (xmlData) {
      let result = ''
      result = xmlData.replace('&', '&amp;')
      result = result.replace('<', '&lt;')
      result = result.replace('>', '&gt;')
      result = result.replace('[', '')
      result = result.replace(']', '')
      result = result.replace('!', '')
      return result
    },
    async AdjustInvoiceAction_VTT (xmlInvData) {
      try {
        const response = await api.AdjustInvoiceAction(
          this.axios,
          {
            acPass: this.acPassHDDT,
            account: this.accountHDDT,
            attachFile: '',
            convert: 0,
            fkey: this.Fkey,
            pattern: this.cboPattern.find(elm => elm.LOAIHOADON_ID === this.traCuuFilter.mauSoId).TEN_LOAIHD,
            serial: this.cboSeri.find(elm => elm.seri_id === this.traCuuFilter.kyHieuId).seri,
            xmlInvData
          }
        )
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hóa đơn')
        return null
      }
    },
    async AdjustInvoiceAction_TT78 (xmlInvData) {
      try {
        const response = await api.AdjustInvoiceAction(
          this.axios,
          {
            acPass: this.acPassHDDT,
            account: this.accountHDDT,
            attachFile: '11',
            convert: 0,
            fkey: this.Fkey,
            pattern: this.cboPattern.find(elm => elm.LOAIHOADON_ID === this.traCuuFilter.mauSoId).TEN_LOAIHD,
            serial: this.cboSeri.find(elm => elm.seri_id === this.traCuuFilter.kyHieuId).seri,
            xmlInvData
          }
        )
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hóa đơn')
        return null
      }
    },
    async ThayTheHoaDon () {
      if (!this.traCuuFilter.mauSoId) {
        this.$root.$toast.warning('Bạn chưa chọn mẫu số !')
        return false
      }
      if (!this.traCuuFilter.kyHieuId) {
        this.$root.$toast.warning('Bạn chưa chọn ký hiệu !')
        return false
      }
      const lstProductFilter = []
      this.lstProduct.forEach(product => {
        if (product.ProdName) {
          lstProductFilter.push({
            Code: product.Code,
            Total: product.Total,
            ProdName: product.ProdName,
            ProdUnit: product.ProdUnit,
            ProdQuantity: product.ProdQuantity,
            ProdPrice: product.ProdPrice,
            Discount: product.Discount,
            DiscountAmount: product.DiscountAmount,
            VATRate: product.VATRate,
            VATAmount: product.VATAmount,
            Amount: product.Amount
          })
        }
      })
      this.objHoaDonDienTuEdit.Products = lstProductFilter
      try {
        const xmlInvData = await this.GetInvoiceXml_ThayThe(this.objHoaDonDienTuEdit)
        const kq = await this.ReplaceInvoiceAction({
          attachFile: '11',
          pattern: this.cboPattern.find(elm => elm.LOAIHOADON_ID === this.traCuuFilter.mauSoId).TEN_LOAIHD,
          serial: this.cboSeri.find(elm => elm.seri_id === this.traCuuFilter.kyHieuId).seri,
          xmlInvData
        })
        if (kq.errorCode !== 0) {
          this.$root.$toast.error(kq.faultString)
          return false
        }
        const result = kq.data.split(':')
        if (result[0] === 'OK') {
          const result1 = result[1].split(';')
          this.objHoaDonDienTuEdit.InvoicePattern = result1[0]
          this.objHoaDonDienTuEdit.SerialNo = result1[1]
          const result2 = result1[2].split('_')
          this.objHoaDonDienTuEdit.InvoiceNo = result2[1]
          this.fkey_new = result2[0]
          const bd = this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, this.traCuuFilter.kyHieuId)
          const bdct = this.TaoDuLieuBDCT()
          const response = await this.xuatHddtTQCapNhat({
            thaoTac: this.traCuuFilter.thaoTacId,
            BD_HDDT: bd,
            BD_HDDT_CT: bdct,
            maNd: this.maNguoiDung,
            bdhddtChaId: this.bdhddt_cha_id,
            tagForm: this.tagForm,
            linkForm: this.linkForm,
            maGD: this.traCuuFilter.txtMaGD
          })
          if (response) {
            if (response.ERROR_CODE === 1) {
              this.$root.$toast.success('Thay thế hóa đơn thành công')
            } else {
              this.$root.$toast.error(response.MESSAGE)
            }
            const pattern = this.cboPattern.find(elm => elm.TEN_LOAIHD === result1[0])
            if (pattern) this.traCuuFilter.mauSoId = pattern.LOAIHOADON_ID
            const seri = this.cboSeri.find(elm => elm.seri === result1[1])
            if (seri) this.traCuuFilter.kyHieuId = seri.seri_id
            this.traCuuFilter.txtSoHoaDon = +result2[1]
            this.setButtonDisabled('tsbtnGhiLai', true)
            this.invToken = ''
            this.Fkey = ''
            this.fkey_new = ''
            await this.HienThi_ThongTin()
          } else {
            this.$root.$toast.error('Không thể cập nhật thông tin hóa đơn vào hệ thống')
          }
        } else {
          this.$root.$toast.error(`Có lỗi trong quá trình điều chỉnh hóa đơn: ${kq.data}`)
        }
      } catch (e) {
      }
    },
    async GetInvoiceXml_ThayThe (obj) {
      const kieu = this.tagForm === 2 ? 2 : 1
      this.fkey_new = await this.sinh_fkey(kieu)
      let xml = '<ReplaceInv>'
      xml += `<key>${this.fkey_new}</key>`
      xml += `<CusCode>${obj.CusCode}</CusCode>`
      xml += `<CusName>${this.convertSpecialCharacter(obj.CusName)}</CusName>`
      xml += `<CusAddress>${this.convertSpecialCharacter(obj.CusAddress)}</CusAddress>`
      xml += `<CusPhone>${obj.CusPhone}</CusPhone>`
      xml += `<CusTaxCode>${obj.CusTaxCode}</CusTaxCode>`
      if (this.tt78_stk_nh) {
        xml += `<CusBankNo><![CDATA[${obj.CusBankNo}]]></CusBankNo>`
        xml += `<CusBankName>${obj.CusBankName}</CusBankName>`
      }
      xml += `<PaymentMethod>${obj.Kind_of_Payment}</PaymentMethod>`
      xml += `<KindOfService>${obj.KindOfService}</KindOfService>`
      if (this.them_extra) xml += `<Extra>${this.fkey_new}</Extra>`
      xml += '<Products>'
      for (const product of obj.Products) {
        xml += '<Product>'
        xml += `<ProdName>${this.convertSpecialCharacter(product.ProdName)}</ProdName>`
        xml += `<ProdUnit>${product.ProdUnit}</ProdUnit>`
        xml += `<ProdQuantity>${product.ProdQuantity}</ProdQuantity>`
        xml += `<ProdPrice>${product.ProdPrice.toString() ? Math.abs(product.ProdPrice) : product.ProdPrice}</ProdPrice>`
        xml += `<Amount>${product.Amount.toString() ? Math.abs(product.Amount) : product.Amount}</Amount>`
        xml += `<VATRate>${product.VATRate}</VATRate>`
        xml += `<VATAmount>${Math.abs(product.VATAmount)}</VATAmount>`
        xml += `<Total>${Math.abs(product.Total)}</Total>`
        xml += '</Product>'
      }
      xml += '</Products>'
      xml += `<Total>${obj.Total.toString() ? Math.abs(obj.Total) : obj.Total}</Total>`
      xml += `<VATRate>${obj.VAT_Rate}</VATRate>`
      xml += `<VATAmount>${obj.VAT_Amount.toString() ? Math.abs(obj.VAT_Amount) : obj.VAT_Amount}</VATAmount>`
      xml += `<Amount>${obj.Amount.toString() ? Math.abs(obj.Amount) : obj.Amount}</Amount>`
      xml += `<AmountInWords>${obj.Amount_words}</AmountInWords>`
      xml += '<PaymentStatus>1</PaymentStatus>'
      xml += `<GrossValue_NonTax>${Math.abs(this.hoaDonFilter.txtTongTienKhongThue)}</GrossValue_NonTax>`
      xml += `<GrossValue>${Math.abs(this.hoaDonFilter.txtTongTienKhongChiuThue)}</GrossValue>`
      xml += `<GrossValue0>${Math.abs(this.hoaDonFilter.txtTongTienChiuThue0)}</GrossValue0>`
      xml += '<VatAmount0>0</VatAmount0>'
      xml += `<GrossValue5>${Math.abs(this.hoaDonFilter.txtTongTienChiuThue5)}</GrossValue5>`
      xml += `<VatAmount5>${Math.abs(this.hoaDonFilter.txtTongTienThue5)}</VatAmount5>`
      xml += `<GrossValue8>${Math.abs(this.hoaDonFilter.txtTongTienChiuThue8)}</GrossValue8>`
      xml += `<VatAmount8>${Math.abs(this.hoaDonFilter.txtTongTienThue8)}</VatAmount8>`
      xml += `<GrossValue10>${Math.abs(this.hoaDonFilter.txtTongTienChiuThue10)}</GrossValue10>`
      xml += `<VatAmount10>${Math.abs(this.hoaDonFilter.txtTongTienThue10)}</VatAmount10>`
      xml += `<Teller>${this.maNhanVien}</Teller>`
      xml += '</ReplaceInv>'
      return xml
    },
    async ReplaceInvoiceAction ({ attachFile, pattern, serial, xmlInvData }) {
      try {
        const response = await api.ReplaceInvoiceAction(
          this.axios,
          {
            acPass: this.acPassHDDT,
            account: this.accountHDDT,
            attachFile: attachFile,
            convert: 0,
            fkey: this.Fkey,
            pattern,
            serial,
            xmlInvData
          }
        )
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hóa đơn')
        return null
      }
    },
    async HuyHoaDon () {
      const kq = await this.cancelInv()
      if (kq.errorCode !== 0) {
        this.$root.$toast.error(kq.faultString)
        return false
      }
      const bd = this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, 0)
      const bdct = this.TaoDuLieuBDCT()
      const response = await this.xuatHddtTQCapNhat({
        thaoTac: this.traCuuFilter.thaoTacId,
        BD_HDDT: bd,
        BD_HDDT_CT: bdct,
        maNd: this.maNguoiDung,
        bdhddtChaId: this.bdhddt_cha_id,
        tagForm: this.tagForm,
        linkForm: this.linkForm,
        maGD: this.traCuuFilter.txtMaGD
      })
      if (response) {
        if (response.ERROR_CODE === 1) {
          this.$root.$toast.success('Hủy hóa đơn thành công !')
        } else {
          this.$root.$toast.error(response.MESSAGE)
        }
      } else {
        this.$root.$toast.error('Không thể cập nhật thông tin hóa đơn vào hệ thống')
      }
      await this.HienThi_ThongTin()
    },
    async cancelInv () {
      try {
        const response = await api.cancelInv(this.axios, {
          acPass: this.acPassHDDT,
          account: this.accountHDDT,
          fkey: this.Fkey
        })
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hóa đơn')
        return null
      }
    },
    async HuyThanhToan_HuyHoaDon () {
      const unConfirmPaymentFkeyResult = await this.UnConfirmPaymentFkey()
      if (!unConfirmPaymentFkeyResult) return false
      if (unConfirmPaymentFkeyResult.errorCode !== 0) {
        this.$root.$toast.error(unConfirmPaymentFkeyResult.faultString)
        return false
      }
      this.$root.$toast.success('Hủy thanh toán hóa đơn thành công !')
      const cancelInvResult = await this.cancelInv()
      if (!cancelInvResult) {
        await this.HienThi_ThongTin()
        return false
      }
      if (cancelInvResult.errorCode !== 0) {
        this.$root.$toast.error(cancelInvResult.faultString)
        await this.HienThi_ThongTin()
        return false
      }
      const bd = this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, 0)
      const bdct = this.TaoDuLieuBDCT()
      const response = await this.xuatHddtTQCapNhat({
        thaoTac: this.traCuuFilter.thaoTacId,
        BD_HDDT: bd,
        BD_HDDT_CT: bdct,
        maNd: this.maNguoiDung,
        bdhddtChaId: this.bdhddt_cha_id,
        tagForm: this.tagForm,
        linkForm: this.linkForm,
        maGD: this.traCuuFilter.txtMaGD
      })
      if (response) {
        if (response.ERROR_CODE === 1) {
          this.$root.$toast.success('Hủy hóa đơn thành công !')
        } else {
          this.$root.$toast.error(response.MESSAGE)
        }
      } else {
        this.$root.$toast.error('Không thể cập nhật thông tin hóa đơn vào hệ thống')
      }
      await this.HienThi_ThongTin()
    },
    async UnConfirmPaymentFkey () {
      try {
        const response = await api.UnConfirmPaymentFkey(this.axios, { lstFkey: this.Fkey })
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hóa đơn')
        return null
      }
    },
    async Xuathoadon_GachNo () {
      if (!this.traCuuFilter.mauSoId) {
        this.$root.$toast.warning('Bạn chưa chọn mẫu số !')
        return false
      }
      if (!this.traCuuFilter.kyHieuId) {
        this.$root.$toast.warning('Bạn chưa chọn ký hiệu !')
        return false
      }
      try {
        const xmlCusData = await this.GetInvoiceXml_Cus()
        const updateCusResult = await this.UpdateCus(0, xmlCusData)
        if (!updateCusResult) return false
        if (updateCusResult.errorCode !== 0) {
          this.$root.$toast.error(updateCusResult.faultString)
          return false
        }
        const xmlInvData = await this.GetInvoiceXml()
        const importPublishResult = await this.importAndPublishInv({
          acPass: this.acPassHDDT,
          account: this.accountHDDT,
          convert: 0,
          pattern: this.cboPattern.find(elm => elm.LOAIHOADON_ID === this.traCuuFilter.mauSoId).TEN_LOAIHD,
          serial: this.cboSeri.find(elm => elm.seri_id === this.traCuuFilter.kyHieuId).seri,
          xmlInvData
        })
        if (!importPublishResult) return false
        if (importPublishResult.errorCode !== 0) {
          this.$root.$toast.error(importPublishResult.faultString)
          return false
        }
        const result = importPublishResult.data.split(':')
        if (result[0] === 'OK') {
          const result1 = result[1].split(';')
          const InvoicePattern1 = result1[0]
          this.objHoaDonDienTuEdit.InvoicePattern = InvoicePattern1
          const result2 = result1[1].split('-')
          const SerialNo1 = result2[0]
          this.objHoaDonDienTuEdit.SerialNo = SerialNo1
          const result3 = result2[1].split('_')
          const InvoiceNo1 = result3[1]
          this.objHoaDonDienTuEdit.InvoiceNo = InvoiceNo1
          const pattern = this.cboPattern.find(elm => elm.TEN_LOAIHD === InvoicePattern1)
          if (pattern) this.traCuuFilter.mauSoId = pattern.LOAIHOADON_ID
          const seri = this.cboSeri.find(elm => elm.seri === SerialNo1)
          if (seri) this.traCuuFilter.kyHieuId = seri.seri_id
          this.traCuuFilter.txtSoHoaDon = +InvoiceNo1
          this.hoaDonFilter.txtMauHoaDon = InvoicePattern1
          this.hoaDonFilter.lblKyHieu = SerialNo1
          this.hoaDonFilter.lblSoHoaDon = +InvoiceNo1
          let loiThanhtoan = 0
          try {
            const confirmPaymentFkeyResult = await this.confirmPaymentFkey(this.Fkey, InvoicePattern1, SerialNo1)
            if (!confirmPaymentFkeyResult) loiThanhtoan = 1
            if (confirmPaymentFkeyResult.errorCode !== 0) {
              this.$root.$toast.error(confirmPaymentFkeyResult.faultString)
              loiThanhtoan = 1
            }
          } catch (e) {
            loiThanhtoan = 1
            this.$root.$toast.warning('Lỗi thanh toán HĐĐT !')
          }
          await this.TraCuu(InvoicePattern1, SerialNo1, +InvoiceNo1)
          const bd = this.TaoDuLieuBD_v2(SerialNo1, InvoiceNo1, this.traCuuFilter.kyHieuId, loiThanhtoan)
          console.log("TaoDuLieuBD_v2", bd);
          const bdct = this.TaoDuLieuBDCT()
          const response = await this.xuatHddtTQCapNhat({
            thaoTac: this.traCuuFilter.thaoTacId,
            BD_HDDT: bd,
            BD_HDDT_CT: bdct,
            maNd: this.maNguoiDung,
            bdhddtChaId: this.bdhddt_cha_id,
            tagForm: this.tagForm,
            linkForm: this.linkForm,
            maGD: this.traCuuFilter.txtMaGD
          })
          if (response) {
            if (response.ERROR_CODE === 1) {
              this.$root.$toast.success('Xuất hóa đơn và thanh toán thành công !')
              this.setButtonDisabled('tsbtnGhiLai', true)
              this.setButtonDisabled('tsbtnNhapMoi', false)
              this.invToken = ''
              this.Fkey = ''
              this.fkey_new = ''
              await this.HienThi_ThongTin()
              // this.clean();
            } else {
              this.$root.$toast.error(response.MESSAGE)
            }
          } else {
            this.$root.$toast.error('Không thể cập nhật thông tin hóa đơn vào hệ thống')
          }
        } else {
          this.$root.$toast.error(`Có lỗi trong quá trình xuất hóa đơn: ${importPublishResult.data}`)
          return false
        }
      } catch (e) {
      }
    },
    async confirmPaymentFkey (fKey, pattern, serial) {
      try {
        const response = await api.confirmPaymentFkey(this.axios, { fKey, pattern, serial })
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hóa đơn')
        return null
      }
    },
    TaoDuLieuBD_v2 (SerialNo1, InvoiceNo1, seriId, loi) {
      console.log('hoaDonFilter', this.hoaDonFilter)
      const dsBDHDDT = []
      const row = {}
      row.MA_TT = this.vma_kh
      row.NHANVIEN_ID = this.nhanVienId
      row.DONVI_ID = this.donViId
      row.KIEU_ID = this.tagForm === 2 ? 3 : 1
      row.TEN_TT = this.hoaDonFilter.txtTenKH
      row.DIACHI_TT = this.hoaDonFilter.txtDiaChiKH
      row.SDT = this.hoaDonFilter.txtSoDT
      row.MST = this.hoaDonFilter.txtMSTKH
      row.EMAIL = this.hoaDonFilter.txtEmail
      row.FKEY = this.Fkey
      row.HINHTHUC_TT = this.hoaDonFilter.txtHTTT
      row.TIEN = Math.abs(this.hoaDonFilter.txtTongTien)
      row.VAT = Math.abs(this.hoaDonFilter.txtTongThue)
      row.TYLE_VAT = Math.abs(+this.hoaDonFilter.txtTiLeThue)
      row.TIENBANGCHU = this.hoaDonFilter.txtTienChu
      if (loi !== 0) row.THANHTOAN_LOI = loi
      row.KIEU_BD = 5
      row.TRANGTHAI = 1
      const tenLoaiHD = this.cboPattern.find(elm => elm.LOAIHOADON_ID === this.traCuuFilter.mauSoId).TEN_LOAIHD
      row.LOAIHOADON_CU = tenLoaiHD
      row.LOAIHOADON_MOI = tenLoaiHD
      row.SERI_CU = SerialNo1
      row.SERI_MOI = SerialNo1
      row.SO_HD_CU = InvoiceNo1
      row.SO_HD_MOI = InvoiceNo1
      row.TONGTIEN = Math.abs(this.t_tien_edited)
      row.TIENGOC = this.tiengoc
      row.NGUOI_CN = this.maNguoiDung
      row.MAY_CN = this.machine
      row.NGAY_CN = moment(new Date()).format('DD/MM/YYYY')
      row.GHICHU = `(Chức năng Động) ${this.hoaDonFilter.txtGhiChu}`
      row.SERI_MOI_ID = seriId
      row.LOAIHOADON_MOI_ID = this.traCuuFilter.mauSoId
      if (this.hoaDonFilter.txtFilePath) row.FILE_DINHKEM = this.hoaDonFilter.filePathBytes
      dsBDHDDT.push(row)
      return dsBDHDDT
    },
    async GachNo () {
      const confirmPaymentFkeyResult = await this.confirmPaymentFkey(
        this.Fkey,
        this.cboPattern.find(elm => elm.LOAIHOADON_ID === this.traCuuFilter.mauSoId).TEN_LOAIHD,
        this.cboSeri.find(elm => elm.seri_id === this.traCuuFilter.kyHieuId).seri
      )
      if (!confirmPaymentFkeyResult) return false
      if (confirmPaymentFkeyResult.errorCode !== 0) {
        this.$root.$toast.error(confirmPaymentFkeyResult.faultString)
        return false
      }
      const bd = this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, 0)
      const bdct = this.TaoDuLieuBDCT()
      const response = await this.xuatHddtTQCapNhat({
        thaoTac: this.traCuuFilter.thaoTacId,
        BD_HDDT: bd,
        BD_HDDT_CT: bdct,
        maNd: this.maNguoiDung,
        bdhddtChaId: this.bdhddt_cha_id,
        tagForm: this.tagForm,
        linkForm: this.linkForm,
        maGD: this.traCuuFilter.txtMaGD
      })
      if (response) {
        if (response.ERROR_CODE === 1) {
          this.$root.$toast.success('Thanh toán hóa đơn thành công !')
          this.setButtonDisabled('tsbtnGhiLai', true)
          this.invToken = ''
          this.Fkey = ''
          this.fkey_new = ''
          await this.HienThi_ThongTin()
        } else {
          this.$root.$toast.error(response.MESSAGE)
        }
      } else {
        this.$root.$toast.error('Không thể cập nhật thông tin hóa đơn vào hệ thống')
      }
    },
    async Huy_GachNo () {
      const result = await this.UnConfirmPaymentFkey()
      if (!result) return false
      if (result.errorCode !== 0) {
        this.$root.$toast.error(result.faultString)
        return false
      }
      const bd = this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, 0)
      const bdct = this.TaoDuLieuBDCT()
      const response = await this.xuatHddtTQCapNhat({
        thaoTac: this.traCuuFilter.thaoTacId,
        BD_HDDT: bd,
        BD_HDDT_CT: bdct,
        maNd: this.maNguoiDung,
        bdhddtChaId: this.bdhddt_cha_id,
        tagForm: this.tagForm,
        linkForm: this.linkForm,
        maGD: this.traCuuFilter.txtMaGD
      })
      if (response) {
        if (response.ERROR_CODE === 1) {
          this.$root.$toast.success('Hủy thanh toán hóa đơn thành công !')
          this.setButtonDisabled('tsbtnGhiLai', true)
          this.invToken = ''
          this.Fkey = ''
          this.fkey_new = ''
          await this.HienThi_ThongTin()
        } else {
          this.$root.$toast.error(response.MESSAGE)
        }
      } else {
        this.$root.$toast.error('Không thể cập nhật thông tin hóa đơn vào hệ thống')
      }
    },
    async ChuyenDoi_XacMinh () {
      const result = await this.convertForVerifyFkey(this.Fkey)
      if (!result) return false
      if (result.errorCode !== 0) {
        this.$root.$toast.error(result.faultString)
        return false
      }
      this.hieuChinhHDDTProp = result.data
      this.$refs.popupHieuChinhHDDTRef.showModal()
    },
    async convertForVerifyFkey (fkey) {
      try {
        const response = await api.convertForVerifyFkey(this.axios, { fkey })
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hóa đơn')
        return null
      }
    },
    async ChuyenDoi_LuuTru () {
      const result = this.tagForm === 2 ? await this.convertForStoreFkey(this.Fkey) : await this.convertForStore(this.invToken)
      if (!result) return false
      if (result.errorCode !== 0) {
        this.$root.$toast.error(result.faultString)
        return false
      }
      this.hieuChinhHDDTProp = result.data
      this.$refs.popupHieuChinhHDDTRef.showModal()
    },
    async convertForStoreFkey (fkey) {
      try {
        const response = await api.convertForStoreFkey(this.axios, { fkey })
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hóa đơn')
        return null
      }
    },
    async convertForStore (invToken) {
      try {
        const response = await api.convertForStore(this.axios, { invToken })
        return await response.data
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin hóa đơn')
        return null
      }
    },
    btnFilePath_Click () {
      this.$refs.bienBanRef.click()
    },
    importFile (e) {
      const bytes = []
      const files = e.target.files
      if (files.length > 0) {
        this.hoaDonFilter.txtFilePath = files[0].name
        const fileReader = new FileReader()
        fileReader.readAsArrayBuffer(files[0])
        fileReader.onload = (ev) => {
          if (ev.target.readyState === FileReader.DONE) {
            const result = ev.target.result
            const array = new Uint8Array(result)
            for (let i = 0; i < array.length; i++) {
              bytes.push(array[i])
            }
            this.hoaDonFilter.filePathBytes = [...bytes]
          }
        }
      } else {
        this.hoaDonFilter.txtFilePath = ''
        this.hoaDonFilter.filePathBytes = []
      }
    },
    actionComplete (args) {
      if (args.requestType === 'save') {
        this.danhSachHoaDon.dataSources = [...this.lstProduct]
      }
    },
    setCellChange () {
      this.hoaDonFilter.txtTongTienChiuThue0 = this.lstProduct
        .filter(elm => elm.VATRate === 0)
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTienKhongChiuThue = this.lstProduct
        .filter(elm => elm.VATRate === -1)
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTienKhongThue = this.lstProduct
        .filter(elm => elm.VATRate === -2)
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTienChiuThue5 = this.lstProduct
        .filter(elm => elm.VATRate === 5)
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTienChiuThue8 = this.lstProduct
        .filter(elm => elm.VATRate === 8)
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTienChiuThue10 = this.lstProduct
        .filter(elm => elm.VATRate === 10)
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTienThue5 = this.lstProduct
        .filter(elm => elm.VATRate === 5)
        .map(elm => elm.VATAmount)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTienThue8 = this.lstProduct
        .filter(elm => elm.VATRate === 8)
        .map(elm => elm.VATAmount)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.hoaDonFilter.txtTongTienThue10 = this.lstProduct
        .filter(elm => elm.VATRate === 10)
        .map(elm => elm.VATAmount)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.setObjectHDDTEditCellChange()
    },
    setObjectHDDTEditCellChange () {
      this.objHoaDonDienTuEdit.Total = this.lstProduct
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.objHoaDonDienTuEdit.VAT_Amount = this.lstProduct
        .map(elm => elm.VATAmount)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.objHoaDonDienTuEdit.Amount = this.lstProduct
        .map(elm => elm.Amount)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.objHoaDonDienTuEdit.Amount_words = DocSo(Math.abs(this.objHoaDonDienTuEdit.Amount))
      this.hoaDonFilter.txtTienChu = this.objHoaDonDienTuEdit.Amount_words
      this.hoaDonFilter.txtTongThue = this.objHoaDonDienTuEdit.VAT_Amount
      this.hoaDonFilter.txtTongTien = this.objHoaDonDienTuEdit.Total
      this.hoaDonFilter.txtTongTra = this.objHoaDonDienTuEdit.Amount
      this.t_tien_edited = this.objHoaDonDienTuEdit.Amount
    },
    getColumnCreate (parent) {
      return function () {
        return {
          template: {
            template: `
              <div class="center">
              <span title="Thêm">
                  <button class="btn btn-sm btn-primary" @click="parent.onClickAddProduct(data)">
                    <span class="-ap icon-more_horiz"></span>
                  </button>
                </span>
              </div>
            `,
            data () {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    getColumnDelete (parent) {
      return function () {
        return {
          template: {
            template: `
              <div class="center">
              <span title="Xóa">
                  <button class="btn btn-sm btn-danger" @click="parent.onClickDeleteProduct(data)">
                    <span class="-ap icon-close"></span>
                  </button>
                </span>
              </div>
            `,
            data () {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    onClickAddProduct (data) {
      if (this.lstProduct.length >= 100) {
        this.$root.$toast.warning('Hóa đơn không được quá 100 sản phẩm !')
      } else {
        this.lstProduct.push({
          STT: this.lstProduct.length + 1,
          Code: '',
          Total: 0,
          ProdName: '',
          ProdUnit: '',
          ProdQuantity: 0,
          ProdPrice: 0,
          Discount: 0,
          DiscountAmount: 0,
          VATRate: 0,
          VATAmount: 0,
          Amount: 0,
          TinhVat: false
        })
        this.danhSachHoaDon.dataSources = [...this.lstProduct]
      }
    },
    onClickDeleteProduct (data) {
      if (this.danhSachHoaDon.dataSources.length === 1) return
      this.$confirm(
        'Bạn có chắc chắn muốn xóa sản phẩm này ?',
        'Xóa sản phẩm',
        {confirmButtonText: 'Có', cancelButtonText: 'Không'}
      ).then(() => {
        let counter = 1
        const productsFilter = this.lstProduct
          .filter(elm => +elm.STT !== +data.STT)
          .map(product => ({
            STT: counter++,
            Code: product.Code,
            Total: product.Total,
            ProdName: product.ProdName,
            ProdUnit: product.ProdUnit,
            ProdQuantity: product.ProdQuantity,
            ProdPrice: product.ProdPrice,
            Discount: product.Discount,
            DiscountAmount: product.DiscountAmount,
            VATRate: product.VATRate,
            VATAmount: product.VATAmount,
            Amount: product.Amount,
            TinhVat: product.TinhVat
          }))
        this.lstProduct = [...productsFilter]
        this.danhSachHoaDon.dataSources = [...productsFilter]
        this.objHoaDonDienTuEdit.Total = this.lstProduct
          .map(elm => elm.Amount)
          .reduce((prev, next) => (+prev) + (+next), 0)
        this.objHoaDonDienTuEdit.VAT_Amount = this.hoaDonFilter.txtTiLeThue === '-1'
          ? 0
          : Math.round(this.objHoaDonDienTuEdit.Total * (+this.hoaDonFilter.txtTiLeThue) / 100)
        this.objHoaDonDienTuEdit.Amount = this.objHoaDonDienTuEdit.Total + this.objHoaDonDienTuEdit.VAT_Amount
        this.objHoaDonDienTuEdit.Amount_words = DocSo(Math.abs(this.objHoaDonDienTuEdit.Amount))
        this.hoaDonFilter.txtTienChu = this.objHoaDonDienTuEdit.Amount_words
        this.hoaDonFilter.txtTongThue = this.objHoaDonDienTuEdit.VAT_Amount
        this.hoaDonFilter.txtTongTien = this.objHoaDonDienTuEdit.Total
        this.hoaDonFilter.txtTongTra = this.objHoaDonDienTuEdit.Amount
        this.t_tien_edited = this.objHoaDonDienTuEdit.Amount
      }).catch((e) => {
      })
    }
  },
  watch: {
    'hoaDonFilter.txtDiaChiKH': function (newVal, oldVal) {
      this.objHoaDonDienTuEdit.CusAddress = newVal
    },
    'hoaDonFilter.txtExtra': function (newVal, oldVal) {
      this.objHoaDonDienTuEdit.Extra = newVal
    },
    'hoaDonFilter.txtHTTT': function (newVal, oldVal) {
      this.objHoaDonDienTuEdit.Kind_of_Payment = newVal
    },
    'hoaDonFilter.txtMSTKH': function (newVal, oldVal) {
      this.objHoaDonDienTuEdit.CusTaxCode = newVal
    },
    'hoaDonFilter.txtSoDT': function (newVal, oldVal) {
      this.objHoaDonDienTuEdit.CusPhone = newVal
    },
    'hoaDonFilter.txtTenKH': function (newVal, oldVal) {
      this.objHoaDonDienTuEdit.CusName = newVal
    },
    'hoaDonFilter.txtTienChu': function (newVal, oldVal) {
      this.objHoaDonDienTuEdit.Amount_words = newVal
    },
    'hoaDonFilter.txtTiLeThue': function (newVal, oldVal) {
      const vatRate = +newVal
      this.objHoaDonDienTuEdit.VAT_Rate = vatRate
      this.objHoaDonDienTuEdit.VAT_Amount = newVal === '-1' ? 0 : Math.round(this.objHoaDonDienTuEdit.Total * vatRate / 100)
      this.objHoaDonDienTuEdit.Amount = this.objHoaDonDienTuEdit.VAT_Amount + this.objHoaDonDienTuEdit.Total
      this.objHoaDonDienTuEdit.Amount_words = DocSo(this.objHoaDonDienTuEdit.Amount)
      this.hoaDonFilter.txtTongTien = this.objHoaDonDienTuEdit.Total
      this.hoaDonFilter.txtTongThue = this.objHoaDonDienTuEdit.VAT_Amount
      this.hoaDonFilter.txtTongTra = this.objHoaDonDienTuEdit.Amount
      this.t_tien_edited = this.objHoaDonDienTuEdit.Amount
    },
    'hoaDonFilter.txtTongTien': function (newVal, oldVal) {
      if (!this.first) {
        this.objHoaDonDienTuEdit.Total = newVal
        this.objHoaDonDienTuEdit.Amount_words = DocSo(Math.abs(this.objHoaDonDienTuEdit.Amount))
        this.hoaDonFilter.txtTienChu = this.objHoaDonDienTuEdit.Amount_words
        this.hoaDonFilter.txtTongThue = this.objHoaDonDienTuEdit.VAT_Amount
        this.hoaDonFilter.txtTongTra = this.objHoaDonDienTuEdit.Amount
        this.t_tien_edited = this.objHoaDonDienTuEdit.Amount
      }
    },
    'hoaDonFilter.txtTongThue': function (newVal, oldVal) {
      this.objHoaDonDienTuEdit.VAT_Amount = newVal
      this.objHoaDonDienTuEdit.Amount = this.objHoaDonDienTuEdit.VAT_Amount + this.objHoaDonDienTuEdit.Total
      this.objHoaDonDienTuEdit.Amount_words = DocSo(Math.abs(this.objHoaDonDienTuEdit.Amount))
      this.hoaDonFilter.txtTienChu = this.objHoaDonDienTuEdit.Amount_words
      this.hoaDonFilter.txtTongTra = this.objHoaDonDienTuEdit.Amount
      this.t_tien_edited = this.objHoaDonDienTuEdit.Amount
    },
    'hoaDonFilter.txtTongTra': function (newVal, oldVal) {
      if (!this.first) {
        this.objHoaDonDienTuEdit.Amount = newVal
        this.t_tien_edited = this.objHoaDonDienTuEdit.Amount
        this.objHoaDonDienTuEdit.Amount_words = DocSo(Math.abs(this.objHoaDonDienTuEdit.Amount))
        this.hoaDonFilter.txtTienChu = this.objHoaDonDienTuEdit.Amount_words
      }
    },
    'traCuuFilter.mauSoId': async function (newVal, oldVal) {
      if (this.isDelayCboSeri) {
        await this.cboPattern_EditValueChanged()
      } else {
        this.loading(true)
        try {
          await this.cboPattern_EditValueChanged()
        } catch (e) {
        } finally {
          this.loading(false)
        }
      }
      this.isDelayCboSeri = false
    }
  }
}
</script>
