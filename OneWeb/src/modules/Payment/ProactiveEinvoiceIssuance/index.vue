<template src="./index.html">
</template>
<script>
import breadcrumb from "@/components/breadcrumb";
import api from "./api/index.js";
import { TextBox } from '@syncfusion/ej2-inputs'
import PopupHieuChinhHddt from '@/modules/Payment/SelfManageEinvoice/popups/PopupHieuChinhHddt'
import PopupEinvoiceDetail from '@/modules/Payment/SelfManageEinvoice/popups/PopupEinvoiceDetail'
import DataGridEdit from '@/modules/Payment/SelfManageEinvoice/components/DataGrid'
import PopupTraCuuHDDT from '@/modules/Payment/eInvoice/Popup_TraCuuHDDT.vue';
import moment from 'moment';

let proNameObj, proNameElm, proUnitObj, proUnitElm, proQuantityObj, proQuantityElm, proPriceElm, proPriceObj, totalElm, totalObj,
  vatAmountElm, vatAmountObj, amountElm, amountObj, vatRateElm, vatRateObj

let anhXacThucKs = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAABqRJREFUeNrsWX1MU1cUP+17bV8LbfkQlLEZTDOmohOFGaMwE5gOxZlsc//41xIzExMMpkQCkSxZ4qKxMSEhcXFz2ZZt2ZzLNqdRo5CREfET3dgU50SFBcP4EEpp30f7XnfOK8WCpbR8zoSbnL737rv3vt/v3HPOPfdW4/f74VkuWnjGyxyBOQKTLCz9fNL43qwBOPHVF+GqF6EYUW5H6nv+iPS/nIF9FqvlfmJy0i28fz+qGfgfhdIKs9Wyf9u77wCj1cLxz777YKDfKWP9hxF9QFGUWZMR4C3mA5vffgO8IoDAK1CM91i3n95FJODHgWZLguDjzfEHNr21BVitDtxOQRWthgWqM8XHHcA29rFnwK/Mivg1qunujVPBFwPDIPhBEWRZVoXuWVYPRW9uBlOc6TC2LQlPQFZmXPyKH3769pu9COzQ61uLUNukeQTvlYfb0D3NhI7hYMOWjcBxXA3C3TXrPkBB4+fvj9sJ/AYEzzB6cA8END+6rc8ng8clgN5ggsItG8DAcUcQ8o5RBOQZEz+azukfTtiNaBIFxa8BS2YzICB435h9fD6fSoJDEmvX54NOpzuGsK3DYZSmayaKRqOBs6d+LDWajIcLigrQNAzgQc0ryvhhHHnAoJPMSws6vR68Xm8KVjtVAjK9nQHwF06fIvDVr25cDww6pxu1Gg34YBEFAZouXwKPx12Fj/dCZkAe46MAdefOLhxynI8Kiza1T2TNI/C1586UckauOq8wD3QsaT528DeuXgae56tCFzbVB4JhK1TI9hB8sVarbXs5d1kF2l0bPu+g+nDtxxJyxLpzZ0oMnKF6bcG6IYcV0AR8UY/h8XigKQz44RkYbUKk+Ybauq0I/mRufi48n5EGiSlJ0Fh36Vj9hfNMfmHhx9HMBGm+obaWwNesWb9GjfOeQTEmzUuiCM03mnBlfhp8yDogD4sfyTTU1W1lGObkqrWrwGpJgN5Hg6rDrc5/BR1IdxTf76RVNLTfaKH3DXW1JXqDvob60YLEu0TwoeYj9QsVATXf3HR9TPBPmxCCv1hfv03LaE+uWL0CLBYrDOJ0C7yEDicieA5y1+VgTNYfvVj/SwmZx1hmg+93EfjcvNwhhxVjMhsewf/x200QBKFq3GQuCP5KQwOBP5GN4OMtZhW8Vwp8lK4eFy3tBli5JptI1Fxu+LVktE/QM9bvwpk6ko3tGMptBgKajwX87eZmctzKSOCfEFB8cL2xcTuBX56bBUaTKeBoklddYIJCzxT6tFoWVqxerpK41thYqihDbfCKzzsJPL1nGVZt7/OOHCeS8LwH7tz6E0RRBX8wqi3lzStXt6PDfr0sZylwxrjAdEvhNUaa9AxK6KAsLM/JIhLVN65ctVNyhuPsZHXs0axVSwKJmUuKSfNo6/B3Sws5blTgh6MQxflFNhtoZB3wzuiiBI85isHIQNbKxXDr5p3Dv1+7ziD4Q0uzF6PDo8MOSDFFG68kwYPWeyBJUtTgQwl82tPTk6fXGwIxNJoiB3yHSCzNzoR7LfcPLXoxQ7V5jytG8GhibQ/uE4mYwA8TyFyy5PO7LS05GLpK0tLTo+9NJHxe0CMJ20s2NUXmY4zz5B/tbW10jRn8k4UMNWnLzNzdevcupbol89PSYhrE55pYLkVZZkd7O10nBH4EASoZNtvuh62tKol5qanqSjpdhcB3dnRMCnzYZG5hRsbu9ocPZVxJS5NTUqaFBIHv6uycNPgxc6H0hS/s6Wj/B5SurtKkeclTSkJG8N1dXXiVK/E7B+k7kycQJp1ekP7cns6OR9Db3V2akJQ0JSToO4+7e+hajuM7ZHny+5CI+4HUBfP3dHX+K/f19tqtCQmTIkFg+x/3qeBxXIciT80matwdWXLqvLLerh7o7+uzm62WCZEgsM5+J21dy3E8x1TuAKPaEycmJ5X19T6GgX6nPd5sjokEZaYu5wBdy3Ecx1Tvv9ngqcR4xZpoLXP2OTFDddlN8aaoSBB4t8utgsf+DmUa9t5s8EPRFLPVXOZyumQEtdcYZ4xIgsb0uD20OpdjP0e035gWEwotcfFx5e5Bt4zgKjiOC0tCTSkws8QFsRzbO6bz2IYNno3GUlD7lbybx/RXqNBz+hEk6NRN5EUVPLZzxDr2hAj4JzC9nNFQKfCiLAniPjpoIg4EXhK9Knh87/Ar039gFpMPjC56g66KAEuitI9lGfUck8Bj/bTZ/JT/Q6PTs1W4e/sLN+zq4Rc+fzmT//ho5v6pnyMwR2COwKyW/wQYAMgN/37otPaaAAAAAElFTkSuQmCC"
export default {
  components: { breadcrumb, PopupHieuChinhHddt, PopupEinvoiceDetail, DataGridEdit, TextBox, PopupTraCuuHDDT },
  name: "",
  data() {
    return {
      ds: {
        list: []
      },
      changed: 0,
      header: {
        title: "XUẤT HOÁ ĐƠN GIÁ TRỊ GIA TĂNG",
        list: [
          { name: "Thanh toán", link: { name: "Ui.cards" } },
          {
            name: "Xuất hóa đơn",
            link: { name: "Ui.buttons" },
          },
        ],
      },
      fields: { text: 'text', value: 'value' },
      dropdownValue: 'Top',
      editSettings: { allowEditing: true, allowAdding: true, allowDeleting: true},
      toolbar: ['Add','Delete', 'Cancel'],
      orderidrules: { required: true, number: true },
      customeridrules: { required: true },
      requiredrules: {required: true},
      freightrules:  { required: true },
      editparams: { params: { popupHeight: '300px' }},
      pageSettings: {pageCount: 5},
      customAttributes:{class: 'tiensauthue'},
      data : [],
      listProduct: [],
      lstProduct: [],
      objProduct : {
          STT: "",
          Code: "",
          Total: "",
          ProdName: "",
          ProdUnit: "",
          ProdQuantity: "",
          ProdPrice: "",
          Discount: '',
          DiscountAmount: "",
          VATRate: "",
          VATAmount: '',
          Amount: ""
      },
      gvProduct: {},
      objKetQua: {},
      disableNhapMoi: false,
      disableGhiLai: false,
      disableKDC: true,
      objHoaDonDienTu: {},
      objHoaDonDienTuEdit: {
        // CusAddress: "",
        // Extra: "",
        // Kind_of_Payment: "",
        // CusTaxCode: "",
        // CusPhone: "",
        // CusName: "",
        // Amount_words: "",
        // VAT_Rate: "",
        // VAT_Amount: "",
        // Amount: "",
        // Total: "",
      },
      dsBDHDDT: [],
      dsBDHDDTCT: [],
      tien: 0,
      vat: 0,
      tongtien: 0,

      tt_nd: {},
      vma_kh: "",
      vnguoicn: "",
      vhdkh_id: 0,
      vtoken_show: "",
      vphieutt_id: "",
      vloaihd_id: "",
      t_tien_load: 0,
      t_tien_edited: 0,
      t_tien_tt: 0,
      t_tien_thoaitra: 0,
      hoi_capnhat: false,
      them_extra: false,
      xemtruoc_hddt: false,

      invToken: "",
      fkey_new: "",
      Fkey: "",
      first: true,

      DS_HOPDONG_TB_HDDT: [],
      vthaotac: 0,
      vskt: "",
      vdiachi_tt: "",
      vpattern_id: 0,
      vseri_id: 0,
      vsoseri: 0,
      vfkey: "",
      da_xuathd: false,
      bdhddt_cha_id: 0,
      _dieuchinh: 0,
      _thaythe: 0,
      vma_gd: "",
      t_tien_load: 0,
      t_tien_edited: 0,
      t_tien_tt: 0,
      vnhanvien_id: 0,
      type: 0,
      khoa: 1,
      vkhongguimail_td: 0,
      vchophep_gdv_dc_thongtin: 0,
      i_thoaitra_tmp: 0,
      _pattern: "",
      _seri: "",
      _so_hd: "",
      vfax: "",
      vnguoi_dd: "",
      link_form: 0,
      tt78: true,
      tiengoc: 0,
      huonggiaotn_id: 0,
      quytrinh_id_2: 0,
      thongTinNguoiDung: {},
      dsDaiSo: [],
      hieuChinhHDDTProp: "",
      timeout: null,
      formThongTinTraCuu: {
        maKhachHang: "",
        mauSo: [],
        mauSoSelected: "",
        kieuDieuChinh: [],
        kieuDieuChinhSelected: "",
        thaoTac: [],
        thaoTacSelected: "",
        kyHieu: [],
        kyHieuSelected: "",
        soHoaDon: "",
      },
      thongTinHoaDon: {
        tenHoaDon: "",
        mauSo: "",
        maSoThue: "",
        kyHieu: "",
        tenDonVi: "",
        so: "",
        diaChiDV: "",
        tenKhachHang: "",
        loaiKhachHang: [],
        loaiKhachHangSelected: "",
        maSoThueKH: "",
        diaChiKH: "",
        soDienThoai: "",
        hinhThucTT: "",
        maSo: "",
        thangHoaDon: "",
        bienBanDinhKem: "",
        fileDinhKem: "",
        email: "",
        ghiChuHoaDon: "",
        thueGTGT:'10',
        tienChiuThue10: 0,
        tienChiuThue5: 0,
        tienChiuThue0: 0,
        tienThue10: 0,
        tienThue5: 0,
        tienThue0: 0,
        tienKhongChiuThue: 0,
        congTienDichVu: 0,
        tienThueGTGT: 0,
        theMoRong: "",
        tongCongTienThanhToan: 0,
        soTienVietChu: "Không đồng",
      },
      position: { X: 'center', Y: 'top' },
      soThueBaoSelected: "",
      tableThueBaoConfig: {
        tableCollumns: [
          {
            fieldName: 'THEM',
            headerText: 'Thêm',
            allowFiltering: false,
            width: 70,
            template: this.getColumnCreate(this)
          },
          {
            fieldName: 'XOA',
            headerText: 'Xóa',
            allowFiltering: false,
            width: 70,
            template: this.getColumnDelete(this)
          },
          {
            fieldName: 'STT',
            headerText: 'STT',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            isPrimaryKey: true
          },
          {
            fieldName: 'ProdName',
            headerText: 'Dịch vụ sử dụng',
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
                  blur: function (args) {
                    const formEle = document.getElementById('grid')
                      .querySelector('form')
                      .ej2_instances[0]
                    const prodNameValue = formEle.getInputElement('ProdName').value
                    if (prodNameValue) {
                      const stt = formEle.getInputElement('STT').value
                      const found = that.lstProduct.find(elm => +elm.STT === +stt)
                      if (found) found.ProdName = prodNameValue
                    }
                  }
                })
                proNameObj.appendTo(proNameElm)
              }
            }
          },
          {
            fieldName: 'ProdUnit',
            headerText: 'Đơn vị tính',
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
                      // const proUnitObjValue = formEle.getInputElement('ProdUnit').value
                      // const stt = formEle.getInputElement('STT').value
                      // const found = that.lstProduct.find(elm => +elm.STT === +stt)
                      // found.ProdUnit = proUnitObj.value
                      const proUnitObjValue = formEle.getInputElement('ProdUnit').value
                      const stt = formEle.getInputElement('STT').value
                      const found = that.lstProduct.find(elm => +elm.STT === +stt)
                      if (found) found.ProdUnit = proUnitObjValue

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
            format:'N',
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
                      const amount = proQuantityValue * proPriceValue + Math.round(proQuantityValue * proPriceValue * vatRateValue) / 100
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
            format:'N2',
            type:'number',
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
                    if (+proPriceObj.value >= 0) {
                      const formEle = document.getElementById('grid')
                        .querySelector('form')
                        .ej2_instances[0]
                      const amountEle = formEle.getInputElement('Amount')
                      const proQuantityEle = formEle.getInputElement('ProdQuantity')
                      const stt = formEle.getInputElement('STT').value
                      const found = that.lstProduct.find(elm => +elm.STT === +stt)
                      //const proQuantityValue = +proQuantityEle.value
                      const proQuantityValue = +formEle.getInputElement('ProdQuantity').value
                      //found.ProdPrice = +proPriceObj.value
                      found.ProdPrice = +formEle.getInputElement('ProdPrice').value
                      const totalEle = formEle.getInputElement('Total')
                      const vatAmountEle = formEle.getInputElement('VATAmount')
                      const vatRateEle = formEle.getInputElement('VATRate')
                      totalEle.value = Math.round(+found.ProdPrice * proQuantityValue)
                      vatAmountEle.value = Math.round(+formEle.getInputElement('ProdPrice').value * proQuantityValue * ((+vatRateEle.value) < 0 ? 0 : (+vatRateEle.value)) / 100)
                      console.log('tiền thuế');
                      console.log(vatAmountEle.value);
                      amountEle.value = +found.ProdPrice * proQuantityValue + Math.round(+found.ProdPrice * proQuantityValue * (+vatRateEle.value)) / 100
                      found.Total = +totalEle.value
                      found.VATAmount = +vatAmountEle.value
                      found.Amount = +amountEle.value
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
            format: 'N',
            type: 'number',
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
                    const totalObjValue = formEle.getInputElement('Total').value
                    found.Total = +totalObjValue
                  }
                })
                totalObj.appendTo(totalElm)
              }
            }
          },
          {
            fieldName: 'VATRate',
            headerText: 'VAT',
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
                    const totalEle = formEle.getInputElement('Total')
                    vatAmountEle.value = Math.round(+totalEle.value * (+formEle.getInputElement('VATRate').value < 0 ? 0 : +formEle.getInputElement('VATRate').value) / 100)
                    const amountEle = formEle.getInputElement('Amount')
                    amountEle.value = +totalEle.value + (+vatAmountEle.value)
                    const stt = formEle.getInputElement('STT').value
                    const found = that.lstProduct.find(elm => +elm.STT === +stt)
                    const vatRateObjValue = formEle.getInputElement('VATRate').value
                    found.VATRate = +vatRateObjValue
                    found.VATAmount = +vatAmountEle.value
                    found.Amount = +amountEle.value
                    that.setCellChange()
                    if (that.lstProduct.length > 1) {
                      const vatRate = that.lstProduct[0].VATRate
                      if (that.lstProduct.slice(1).some(elm => elm.ProdName && elm.VATRate !== vatRate)) {
                        that.thongTinHoaDon.mauSoSelected = 0
                      } else {
                        that.thongTinHoaDon.mauSoSelected = 1
                      }
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
            format: 'N',
            type: 'number',
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
                    const stt = formEle.getInputElement('STT').value
                    const amountEle = formEle.getInputElement('Amount')
                    const vatAmountObjValue = formEle.getInputElement('VATAmount').value
                    amountEle.value = +amountEle.value
                    const found = that.lstProduct.find(elm => +elm.STT === +stt)
                    found.VATAmount = +vatAmountObjValue
                    found.Amount = +amountEle.value
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
            format: 'N',
            type: 'number',
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
                    const amountObjValue = formEle.getInputElement('Amount').value
                    found.Amount = +amountObjValue
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
            allowFiltering: true,
            allowSorting: false,
            allowEditing: false,
            visible: false,
            format:'N',
          }
        ],
        tableData: [
        ],
        editSettings: {
          allowEditing: true,
          allowAdding: false,
          allowDeleting: false,
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
      vtt: false,
    };
  },
  async created() {
    this.loading(true)
    let nguoidung_id = this.$root.token.getNguoiDungID();
    console.log(this.$root.token.getUserName())
    let obj1 = {
      "tag_form": 2,
      "nguoidung_id" : nguoidung_id
    }
    let dataSend = {
      ds_param: JSON.stringify(obj1),
    };
    let res = await api.layThongTinNguoiDung(this.axios, {})
    this.tt_nd = res.data.data ? res.data.data : {};
    // load danh muc

    let res4 = await api.layMauSo(this.axios)
    if (res4.data.data) {
      this.formThongTinTraCuu.mauSo = res4.data.data.filter((item) => item.KIEU == 5)
      // this.formThongTinTraCuu.mauSoSelected = this.formThongTinTraCuu.mauSo[0].LOAIHOADON_ID
    }

    let res5 = await api.layThaoTac(this.axios, { kieu: 2})
    if(res5.data.data) {
      this.formThongTinTraCuu.thaoTac = res5.data.data
    }  

    let res6 = await api.layDieuChinh(this.axios, { kieu: 2 })
    if (res6.data.data) {
      this.formThongTinTraCuu.kieuDieuChinh = res6.data.data
    }

    let res7 = await api.layLoaiKhachHang(this.axios, {});
    if (res7.data) {
      this.formThongTinTraCuu.loaiKhachHang = res7.data.data
      console.log(res7.data.data)
    }

    // let res1 = await api.loadForm(this.axios, dataSend);
    // let obj = JSON.parse(res1.data.data.data);
    // console.log(obj);

    // if (res1.data.data.data) {
      // this.formThongTinTraCuu.thaoTac = obj.DS_THAOTAC;
      // const found = this.formThongTinTraCuu.thaoTac.find(x => !x.THAOTAC)
      // if (found) found.THAOTAC = 'Chưa chọn'
      // this.formThongTinTraCuu.kieuDieuChinh = obj.DS_DIEUCHINH;
      // const found1 = this.formThongTinTraCuu.kieuDieuChinh.find(x => !x.DIEUCHINH)
      // if (found1) found1.DIEUCHINH = 'Chưa chọn'
      // this.formThongTinTraCuu.mauSo = obj.DS_LOAI_HOADON;
      // this.formThongTinTraCuu.loaiKhachHang = obj.DS_LOAI_KH;
      // this.thongTinHoaDon.loaiKhachHangSelected = obj.DS_LOAI_KH ? obj.DS_LOAI_KH[0].LOAIKH_ID : ""
    // }

    if(this.link_form == 1){
      this.formThongTinTraCuu.thaoTacSelected = "6";
      this.formThongTinTraCuu.maKhachHang = this.i_ma_gd;
      await this.HienThi_ThongTin_HopDong();
    }

    

    // lấy tham số mặc định
    let res2 = await api.layThamSoMacDinh(this.axios, {"kieu_id":-1})
    console.log(res2)
    if(res2.data && res2.data.error_code == "BSS-00000000"){
        let str_vtemp = "";
        let det;
        if(Array.isArray(res2.data.data) && res2.data.data.length > 0){
            str_vtemp = "KIEMTRA_HUY_HDDT";
            det = res2.data.data.filter(x => (x.ma_ts == str_vtemp && x.ten_ts == "1"));
            if(Array.isArray(det) && det.length > 0){
                this.hoi_capnhat = true;
            }

            str_vtemp = "THEM_HDDT_EXTRA";
            det = res2.data.data.filter(x => (x.ma_ts == str_vtemp && x.ten_ts == "1"));
            if(Array.isArray(det) && det.length > 0){
                this.them_extra = true;
            }

            str_vtemp = "XEMTRUOC_HDDT";
            det = res2.data.data.filter(x => (x.ma_ts == str_vtemp && x.ten_ts == "1"));
            if(Array.isArray(det) && det.length > 0){
                this.xemtruoc_hddt = true;
            }

            str_vtemp = "HDDT_THONGTU_78";
            det = res2.data.data.filter(x => (x.ma_ts == str_vtemp && x.ten_ts == "1"));
            if(Array.isArray(det) && det.length > 0){
                this.tt78 = true;
            }

            if(this.tt78){
                this.View_TT78()
            }else{
                this.View()
            }
        }
    }
    this.loading(false);


  },

   watch: {
    "formThongTinTraCuu.mauSoSelected": function (newVal, oldVal) {
      let dataSend = {
        kieu: 1,
        loaihd_id: newVal, //newVal
        phanvung_id: this.$root.token.getPhanVungID,
      };
      api.laySeri(this.axios, dataSend).then((res) => {
        console.log(res.data.data);
        this.formThongTinTraCuu.kyHieu = res.data.data ? res.data.data : [];
        this.formThongTinTraCuu.kyHieuSelected = res.data.data ? res.data.data[0].seri_id : "";
      });
    },
    "thongTinHoaDon.tenKhachHang": function (val){
      this.objHoaDonDienTuEdit.CusName = val;
    },
    "thongTinHoaDon.diaChiKH": function (val){
      this.objHoaDonDienTuEdit.CusAddress = val;
    },
    "thongTinHoaDon.theMoRong": function (val){
      this.objHoaDonDienTuEdit.Extra = val;
    },
    "thongTinHoaDon.hinhThucTT": function (val){
      this.objHoaDonDienTuEdit.Kind_of_Payment = val;
    },
    "thongTinHoaDon.maSoThueKH": function (val){
      this.objHoaDonDienTuEdit.CusTaxCode = val;
    },
    "thongTinHoaDon.soDienThoai": function (val){
      this.objHoaDonDienTuEdit.CusPhone = val;
    },
    "thongTinHoaDon.soTienVietChu": function (val){
      this.objHoaDonDienTuEdit.Amount_words = val;
    },
    "thongTinHoaDon.email": function (val){
      this.objHoaDonDienTuEdit.EmailDeliver = val;
    },

    "formThongTinTraCuu.thaoTacSelected" : function(val){
        if(val == "2"){
          this.disableKDC = false
          this.formThongTinTraCuu.kieuDieuChinhSelected = "1"
        }else{
          this.disableKDC = true
          this.formThongTinTraCuu.kieuDieuChinhSelected = ""
        }
    },

  },

  computed: {
     dulieu() {
       return JSON.parse(JSON.stringify(this.ds.list));
      }
  },

  methods: {
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
        this.tableThueBaoConfig.dataSources = [...this.lstProduct]
      }
    },
    onClickDeleteProduct (data) {
      this.$confirm(
        'Bạn có chắc chắn muốn xóa sản phẩm này ?',
        'Xóa sản phẩm',
        {confirmButtonText: 'Có', cancelButtonText: 'Không'}
      ).then(async () => {
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
        this.tableThueBaoConfig.dataSources = [...productsFilter]
        this.objHoaDonDienTuEdit.Total = this.lstProduct
          .map(elm => elm.Amount)
          .reduce((prev, next) => (+prev) + (+next), 0)
        this.objHoaDonDienTuEdit.VAT_Amount = this.thongTinHoaDon.thueGTGT === '-1'
          ? 0
          : Math.round(this.objHoaDonDienTuEdit.Total * (+this.thongTinHoaDon.thueGTGT) / 100)
        this.objHoaDonDienTuEdit.Amount = this.objHoaDonDienTuEdit.Total + this.objHoaDonDienTuEdit.VAT_Amount
        let tempDocTien = await api.docTienChu(this.axios, {money: this.objHoaDonDienTuEdit.Amount})
        this.objHoaDonDienTuEdit.Amount_words = await tempDocTien.data.data
        this.thongTinHoaDon.soTienVietChu = this.objHoaDonDienTuEdit.Amount_words
        this.thongTinHoaDon.tienThueGTGT = this.objHoaDonDienTuEdit.VAT_Amount
        this.thongTinHoaDon.congTienDichVu = this.objHoaDonDienTuEdit.Total
        this.thongTinHoaDon.tongCongTienThanhToan = this.objHoaDonDienTuEdit.Amount
        this.t_tien_edited = this.objHoaDonDienTuEdit.Amount
      }).catch((e) => {
      })
    },
    async actionComplete (args) {
      if (args.requestType === 'save') {
        if (this.timeout) clearTimeout(this.timeout)
        this.timeout = setTimeout(() => {
          this.tableThueBaoConfig.dataSources = [...this.lstProduct]
        }, 500)
      }
    },

    setCellChange () {
      this.thongTinHoaDon.tienChiuThue0 = this.lstProduct
        .filter(elm => elm.VATRate === 0)
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.thongTinHoaDon.tienKhongChiuThue = this.lstProduct
        .filter(elm => elm.VATRate === -1)
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.thongTinHoaDon.tienThue0 = this.lstProduct
        .filter(elm => elm.VATRate === -2)
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.thongTinHoaDon.tienChiuThue5 = this.lstProduct
        .filter(elm => elm.VATRate === 5)
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.thongTinHoaDon.tienChiuThue10 = this.lstProduct
        .filter(elm => elm.VATRate === 10)
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.thongTinHoaDon.tienThue5 = this.lstProduct
        .filter(elm => elm.VATRate === 5)
        .map(elm => elm.VATAmount)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.thongTinHoaDon.tienThue10 = this.lstProduct
        .filter(elm => elm.VATRate === 10)
        .map(elm => elm.VATAmount)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.setObjectHDDTEditCellChange()
    },

    setObjectHDDTEditCellChange () {
      console.log(this.lstProduct);
      this.objHoaDonDienTuEdit.Total = this.lstProduct
        .map(elm => elm.Total)
        .reduce((prev, next) => (+prev) + (+next), 0)
      this.objHoaDonDienTuEdit.VAT_Amount = this.lstProduct
        .map(elm => elm.VATAmount)
        .reduce((prev, next) => (+prev) + (+next), 0)
      console.log(this.objHoaDonDienTuEdit.VAT_Amount);
      this.objHoaDonDienTuEdit.Amount = this.lstProduct
        .map(elm => elm.Amount)
        .reduce((prev, next) => (+prev) + (+next), 0)
      console.log(this.objHoaDonDienTuEdit.Amount)
      if (this.timeout) clearTimeout(this.timeout)
      this.timeout = setTimeout(async () => {
        let tempDocTien = await api.docTienChu(this.axios , { money : this.objHoaDonDienTuEdit.Amount})
        this.objHoaDonDienTuEdit.Amount_words = tempDocTien.data.data
        this.thongTinHoaDon.soTienVietChu = this.objHoaDonDienTuEdit.Amount_words
      }, 100)
      this.thongTinHoaDon.tienThueGTGT = this.objHoaDonDienTuEdit.VAT_Amount
      this.thongTinHoaDon.congTienDichVu = this.objHoaDonDienTuEdit.Total
      this.thongTinHoaDon.tongCongTienThanhToan = this.objHoaDonDienTuEdit.Amount


      this.t_tien_edited = this.objHoaDonDienTuEdit.Amount
      this.objHoaDonDienTuEdit.Products = this.lstProduct
    },


    View_TT78 () {

    },
    View() {

    },

    uploadFile(){
      this.$refs.inputFile.click();
    },

    async importFile(e) {
      const files = e.target.files;
      let formData = new FormData();
      console.log(files[0]);
      formData.append("files", files[0]);
      console.log(formData);
      let resFileUpload = await api
        .upload_file(this.axios, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
      console.log(resFileUpload) ;
      if(resFileUpload.data.error_code == "BSS-00000000"){
        this.$toast.success("upload thành công!")
        this.thongTinHoaDon.bienBanDinhKem = files[0].name;
        this.thongTinHoaDon.fileDinhKem = resFileUpload.data.data[0]
      }
    },

    async onSuccessTCHopDong (data) {
      const isHopDong = true
      if (isHopDong) {
        if (data) {
          const lsPro = []
          let tien = 0
          let vat = 0
          let tongtien = 0
          this.vtoken_show = data.vtoken_show
          this.type = data.vtype
          this.Fkey = data._fkey
          this._pattern = data._pattern
          this._seri = data._seri
          this._so_hd = data._so_hd
          await this.TraCuu(data._pattern, data._seri, data._so_hd)
          if (this.DS_HOPDONG_TB_HDDT.length > 0) {
            for (const hopDong of this.DS_HOPDONG_TB_HDDT) {
              const product = {
                Code: '',
                Total: 0,
                ProdName: hopDong.PRODNAME.toString(),
                ProdUnit: hopDong.PRODUNIT.toString(),
                ProdQuantity: Math.abs(+hopDong.PRODQUANTITY),
                ProdPrice: Math.abs(+hopDong.PRODPRICE),
                Discount: 0,
                DiscountAmount: 0,
                VATRate: 0,
                VATAmount: 0,
                Amount: Math.abs(+hopDong.AMOUNT),
                TinhVat: false
              }
              if (this.tt78) {
                product.Total = Math.abs(+hopDong.TOTAL)
                product.VATAmount = Math.abs(+hopDong.VATAmount)
              }
              lsPro.push(product)

              tien += +hopDong.TIEN
              vat += +hopDong.VAT
              tongtien += +hopDong.TONGTIEN
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
            this.tableThueBaoConfig.dataSources = [...this.lstProduct]
            this.thongTinHoaDon.congTienDichVu = Math.abs(tien)
            this.thongTinHoaDon.tienThueGTGT = Math.abs(vat)
            this.thongTinHoaDon.tongCongTienThanhToan = Math.abs(tongtien)
            this.t_tien_edited = tongtien
            let tempDocTien = await api.docTienChu(this.axios, {money: tongtien})
            this.thongTinHoaDon.soTienVietChu = tempDocTien.data.data
          }
          //this.setButtonDisabled('tsbtnGhiLai', false)
          this.disableGhiLai = false
        } else {
          //this.setButtonDisabled('tsbtnGhiLai', true)
          this.disableNhapMoi = true;
        }
      } else {
        if (data) {
          this.vtoken_show = data.vtoken_show
          this.type = data.vtype
          this.Fkey = data._fkey
          this._pattern = data._pattern
          this._seri = data._seri
          this._so_hd = data._so_hd
          await this.TraCuu(this._pattern, this._seri, this._so_hd)
          // const thongTin = await this.xuatHddtTQHienThiThongTin({
          //   maGD: this.formThongTinTraCuu.maKhachHang,
          //   fkey: this.Fkey,
          //   tagForm: this.tagForm,
          //   thaoTacId: this.vthaotac,
          //   getServiceInfo: 1
          // })

          // let data = {
          //   "ma_tt": "this.formThongTinTraCuu.maKhachHang" ,
          // };

          let dataSend = {
            "ma_tt": "this.formThongTinTraCuu.maKhachHang" ,
          }
          let tempHTTT = await api.hienThiThongTin(this.axios, dataSend);
          let thongTin = tempHTTT.data.data;

          if (thongTin && thongTin.DS_BD_CHA && thongTin.DS_BD_CHA.length > 0) {
            this.bdhddt_cha_id = +thongTin.DS_BD_CHA[0].BDHDDT_CHA_ID
            this.vnguoicn = thongTin.DS_BD_CHA[0].NGUOI_CN
          } else {
            this.bdhddt_cha_id = 0
            this.vnguoicn = ''
          }
          //this.setButtonDisabled('tsbtnGhiLai', false)
          this.disableGhiLai = false

        } else if (this.hienthitt) {
          this.thongTinHoaDon.tenKhachHang = this.hienthitt.TEN_TT
          this.thongTinHoaDon.diaChiKH = this.hienthitt.DIACHI_TT
          this.thongTinHoaDon.maSoThueKH = this.hienthitt.MST
          this.thongTinHoaDon.soDienThoai = this.hienthitt.SDT
          this.thongTinHoaDon.thueGTGT = '10'
          this.thongTinHoaDon.email = this.hienthitt.EMAIL
          //this.setButtonDisabled('tsbtnGhiLai', true)
          this.disableGhiLai = true;
          //this.setButtonDisabled('tsbtnNhapMoi', false)
          this.disableNhapMoi = false

        }
      }
    },

    async onSuccessEinvoiceDetail (data) {
      console.log(data);
      if (data.ok) {
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
      }
      this.$refs.popupEinvoiceDetailRef.hideModal()
    },

    async ChuyenDoi_XacMinh(){
      let kq = "";
      let dataSend = {
        fkey: this.Fkey
      }

      kq = await api.convertForVerify_VTT(this.axios, dataSend);

      if(kq.data.errorCode.toString() != "3" && kq.data.data){
          this.hieuChinhHDDTProp = kq.data.data
          this.$refs.popupHieuChinhHDDTRef.showModal()
      }else{
        this.$toast.error("Có lỗi xẩy ra: " + kq.data.faultString)
      }

    },
    async ChuyenDoi_LuuTru(){
      let kq = "";
      let dataSend = {
        fkey: this.Fkey
      }

      kq = await api.convertForStore_VTT(this.axios, dataSend);

      if(kq.data.errorCode.toString() != "3" && kq.data.data){
          this.hieuChinhHDDTProp = kq.data.data
          this.$refs.popupHieuChinhHDDTRef.showModal()
      }else{
        this.$toast.error("Có lỗi xẩy ra: " + kq.data.faultString)
      }

    },
    async Huy_GachNo(){
      //vthaotac = 8
      let kq = "";
      let dataSend = {
        lstFkey: this.Fkey
      }
      kq = await api.UnConfirmPaymentFkey_VTT(this.axios, dataSend);

      let bd = this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, 0);
      let bdct = this.TaoDuLieuBDCT()
      let temp = {
        "thaotac":this.vthaotac,
        "js_bd_hddt":JSON.stringify(bd),
        "js_bd_hddt_ct":JSON.stringify(bdct),
        "ma_nd": this.tt_nd.ma_nd,
        "bdhddt_cha_id": this.bdhddt_cha_id
      }
      let dataSendUpdate = {
            "ds_para": JSON.stringify(temp),
            "phanvung_id": 0
      };

      await api.capNhatHDDTVTT(this.axios, dataSendUpdate);

      this.$toast.success("Hủy thanh toán hóa đơn thành công !")
      this.disableGhiLai = true;
      this.invToken = "";
      this.Fkey = "";
      this.fkey_new = "";
      this.HienThi_ThongTin();
    },

    async GachNo(){ //thanh toán
      //vthaotac = 7
      let pattern = "";
      let seri = "";
      let sohoadon = "";
      let patternSelect =  this.formThongTinTraCuu.mauSo.filter(x => (x.LOAIHOADON_ID == this.formThongTinTraCuu.mauSoSelected));
      pattern = patternSelect[0].TEN_LOAIHD;
      let seriSelect = this.formThongTinTraCuu.kyHieu.filter(x => (x.seri_id == this.formThongTinTraCuu.kyHieuSelected));
      seri = seriSelect[0].seri;

      let kq = "";
      let dataSend = {
        "fKey": this.Fkey,
        "pattern": pattern,
        "serial": seri
      }
      kq = await api.confirmPaymentFkey_VTT(this.axios, dataSend);


      let bd = this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, 0);
      console.log(bd);
      let bdct = this.TaoDuLieuBDCT()
      let temp = {
        "thaotac":this.vthaotac,
        "js_bd_hddt":JSON.stringify(bd),
        "js_bd_hddt_ct":JSON.stringify(bdct),
        "ma_nd": this.tt_nd.ma_nd,
        "bdhddt_cha_id": this.bdhddt_cha_id
      }
      let dataSendUpdate = {
            "ds_para": JSON.stringify(temp),
            "phanvung_id": this.$root.token.getPhanVungID()
      };

      let result = await api.capNhatHDDTVTT(this.axios, dataSendUpdate);
      if(result.data.data.js_returnds){
        let kq = JSON.parse(result.data.data.js_returnds);
        if(kq.ERROR_CODE == "1"){
          this.$toast.success("Thanh toán hóa đơn thành công !")
          this.disableGhiLai = true;
          this.invToken = "";
          this.Fkey = "";
          this.fkey_new = "";
          this.HienThi_ThongTin();
        }else{
          this.$toast.success("Có lỗi xẩy ra !");
          return false;
        }

      }else{
        this.$toast.success("Có lỗi xẩy ra !");
        return false;
      }

    },

    async Xuathoadon(){
      //vthaotac = 1
      let pattern = "";
      let seri = "";
      let sohoadon = "";

      if(this.formThongTinTraCuu.mauSoSelected == "" || this.formThongTinTraCuu.mauSoSelected == "0"){
        this.$toast.warning("Bạn chưa chọn mẫu số !");
      }

      if(this.formThongTinTraCuu.kyHieuSelected == "" || this.formThongTinTraCuu.kyHieuSelected == "0"){
        this.$toast.warning("Bạn chưa chọn ký hiệu !");
      }

      let patternSelect =  this.formThongTinTraCuu.mauSo.filter(x => (x.LOAIHOADON_ID == this.formThongTinTraCuu.mauSoSelected));
      pattern = patternSelect[0].TEN_LOAIHD;
      let seriSelect = this.formThongTinTraCuu.kyHieu.filter(x => (x.seri_id == this.formThongTinTraCuu.kyHieuSelected));
      seri = seriSelect[0].seri;

      let xmlCusData = await this.GetInvoiceXml_Cus()
      let dataSend = {
        "convert": 0,
        "xmlCusData": xmlCusData
      }
      let kq1 = "";
      //kq1 = invoice.UpdateCus_VTT(GetInvoiceXml_Cus(), tt_nd.user_hddt, tt_nd.pass_hddt, 0);

      let res =  await api.UpdateCus_VTT(this.axios, dataSend);
      if(!res.data.data) return false;

      let xmlInvData = "";
      if(this.tt78){
        xmlInvData = await this.GetInvoiceXml_tt78()
      }else{
        xmlInvData = await this.GetInvoiceXml() // bỏ những luồng không phải tt78
      }
      let kq = "";
      let dataImport = {
        acPass: this.tt_nd.pass_hddt_td,
        account: this.tt_nd.user_hddt_td,
        convert: 0,
        pattern: pattern,
        serial: seri,
        xmlInvData: xmlInvData
      }

      kq = await api.ImportAndPublishInv_VTT(this.axios, dataImport)
      if(!kq.data.data) return false;
      let result = kq.data.data.split(":")
      if(result[0] == "OK"){
        let bd = this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, parseInt(this.formThongTinTraCuu.kyHieuSelected));
        let bdct = this.TaoDuLieuBDCT()
        let temp = {
          "thaotac":this.vthaotac,
          "js_bd_hddt":JSON.stringify(bd),
          "js_bd_hddt_ct":JSON.stringify(bdct),
          "ma_nd": this.tt_nd.ma_nd,
          "bdhddt_cha_id": this.bdhddt_cha_id
        }
        let dataSendUpdate = {
              "ds_para": JSON.stringify(temp),
              "phanvung_id": 0
        };

        await api.capNhatHDDTVTT(this.axios, dataSendUpdate);

        this.$toast.success("Xuất hóa đơn thành công !")
        this.disableGhiLai = true;
        this.disableNhapMoi = false;
        this.invToken = "";
        this.Fkey = "";
        this.fkey_new = "";
      }else{
        this.$toast.error(" Có lỗi trong quá trình xuất hóa đơn !")
      }

    },

    async Xuathoadon_GachNo(){
      console.log('Xuathoadon_GachNo');
      console.log(this.tableThueBaoConfig.dataSources);
      let pattern = "";
      let seri = "";
      let sohoadon = "";

      if(this.formThongTinTraCuu.mauSoSelected == "" || this.formThongTinTraCuu.mauSoSelected == "0"){
        this.$toast.warning("Bạn chưa chọn mẫu số !");
        return false;
      }

      if(this.formThongTinTraCuu.kyHieuSelected == "" || this.formThongTinTraCuu.kyHieuSelected == "0"){
        this.$toast.warning("Bạn chưa chọn ký hiệu !");
        return false;
      }

      let patternSelect =  this.formThongTinTraCuu.mauSo.filter(x => (x.LOAIHOADON_ID == this.formThongTinTraCuu.mauSoSelected));
      pattern = patternSelect[0].TEN_LOAIHD;
      let seriSelect = this.formThongTinTraCuu.kyHieu.filter(x => (x.seri_id == this.formThongTinTraCuu.kyHieuSelected));
      seri = seriSelect[0].seri;

      let xmlCusData = await this.GetInvoiceXml_Cus();
      let dataSend = {
        convert: 0,
        xmlCusData: xmlCusData
      }

      let resUpdateCus_VTT = await api.UpdateCus_VTT(this.axios, dataSend);
      let kq1  = resUpdateCus_VTT.data.data;
      if(!kq1) {
          this.$toast.error("không lấy được thông tin update UpdateCus_VTT!");
          return false;
      }
      let xmlInvData = ""
      xmlInvData = await this.GetInvoiceXml_tt78()
      let dataSend78 = {
        "acPass": this.tt_nd.pass_hddt_td,
        "account": this.tt_nd.user_hddt_td,
        "convert": 0,
        "pattern": pattern,
        "serial": seri,
        "xmlInvData": xmlInvData
      }
      let kq = "";
      let temp1 = "";
      temp1 = await api.ImportAndPublishInv_VTT(this.axios, dataSend78);
      console.log("temp1");
      console.log(temp1);
      if(!temp1.data.data){
        this.$toast.error("ImportAndPublishInv_VTT " + temp1.data.faultString);
        return false;
      }
      kq = temp1.data.data.split(':')

      if(kq[0] == "OK"){
          let InvoicePattern1 = "";
          let SerialNo1 = "";
          let InvoiceNo1 = "";
          let result1 = kq[1].split(';')
          InvoicePattern1 = result1[0];
          this.objHoaDonDienTuEdit.InvoicePattern = InvoicePattern1;
          let result2 = result1[1].split('-')
          SerialNo1 = result2[0]
          this.objHoaDonDienTuEdit.SerialNo = SerialNo1
          let result3 = result2[1].split('_')
          InvoiceNo1 = result3[1]
          this.objHoaDonDienTuEdit.InvoiceNo = InvoiceNo1
          console.log(InvoicePattern1 + "và " + SerialNo1)
          let patternSelect =  this.formThongTinTraCuu.mauSo.filter(x => (x.TEN_LOAIHD == InvoicePattern1));
          this.formThongTinTraCuu.mauSoSelected  = patternSelect[0].LOAIHOADON_ID
          let seriSelect = this.formThongTinTraCuu.kyHieu.filter(x => (x.seri == SerialNo1));
          console.log(seriSelect);
          this.formThongTinTraCuu.kyHieuSelected = seriSelect[0].seri;

          this.thongTinHoaDon.mauSo = InvoicePattern1;
          this.thongTinHoaDon.kyHieu =  SerialNo1;
          this.thongTinHoaDon.so =  InvoiceNo1;
          let kq2 = "";
          let temp2 = ""
          let loi_thanhtoan = 0;
          try {
            if(this.Fkey){
                let dataTT78 = {
                  fKey: this.Fkey,
                  pattern: pattern,
                  serial: seri
                }
                temp2 = await api.confirmPaymentFkey_VTT(this.axios, dataTT78)
                kq2 = temp2.data
                if(kq2.errorCode != 0){
                    loi_thanhtoan = 1;
                    this.$toast.warning("Lỗi thanh toán HĐĐT, " + kq2.faultString);
                }
            }

            try {
              await this.TraCuu(InvoicePattern1, SerialNo1, InvoiceNo1);
            } catch (error) {
            }

          } catch (error) {
            loi_thanhtoan = 1;
            this.$toast.error("Lỗi thanh toán HĐĐT ! " + error.toString())
          }

          try {

            await this.TraCuu(InvoicePattern1, SerialNo1, InvoiceNo1);

          } catch (error) {
          }

          let bd = this.TaoDuLieuBD_v2(SerialNo1, InvoiceNo1, this.formThongTinTraCuu.kyHieuSelected, loi_thanhtoan)
          let bdct = this.TaoDuLieuBDCT()
          let temp = {
            "thaotac":this.vthaotac,
            "js_bd_hddt":JSON.stringify(bd),
            "js_bd_hddt_ct":JSON.stringify(bdct),
            "ma_nd": this.tt_nd.ma_nd,
            "bdhddt_cha_id": this.bdhddt_cha_id
          }
          let dataSendUpdate = {
                "ds_para": JSON.stringify(temp),
                "phanvung_id": 0
          };

          let resUpdate = await api.capNhatHDDTVTT(this.axios, dataSendUpdate);
          if(resUpdate.data.error_code == "BSS-00000000"){
            this.$toast.success("Xuất hóa đơn và thanh toán thành công !");
            this.$refs.popupEinvoiceDetailRef.hideModal()
            this.disableGhiLai = true;
            this.disableNhapMoi = false;
            this.invToken = "";
            this.Fkey = "";
            this.fkey_new = "";
            await this.HienThi_ThongTin();
          }else{
            this.$toast.error("Có lỗi xẩy ra !");
          }
      }

    },

    async DieuChinhHoaDon(){
      let pattern = "";
      let seri = "";
      let sohoadon = "";

      if (!this.formThongTinTraCuu.mauSoSelected) {
        this.$root.$toast.warning('Bạn chưa chọn mẫu số !')
        return false
      }
      if (!this.formThongTinTraCuu.kyHieuSelected) {
        this.$root.$toast.warning('Bạn chưa chọn ký hiệu !')
        return false
      }
      pattern = this.formThongTinTraCuu.mauSo.find((elm) => elm.LOAIHOADON_ID == this.formThongTinTraCuu.mauSoSelected).TEN_LOAIHD
      seri = this.formThongTinTraCuu.kyHieu.find((elm) => elm.seri_id == this.formThongTinTraCuu.kyHieuSelected).seri

      let lstProductFilter = []
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
        let kq = "";
        let xmlInvData ;
        xmlInvData = await this.GetInvoiceXml_DieuChinh_tt78(this.objHoaDonDienTuEdit)
        console.log("this.Fkey3 = " + this.Fkey)
        console.log(xmlInvData);
        let dataSendAdjustInv = {
          acPass: this.tt_nd.pass_hddt_td,
          account: this.tt_nd.user_hddt_td,
          attachFile: this.thongTinHoaDon.bienBanDinhKem,
          convert: 0,
          fkey: this.Fkey,
          pattern: pattern,
          serial: seri,
          xmlInvData: xmlInvData
        }

        kq = await api.AdjustInvoiceAction_VTT(this.axios, dataSendAdjustInv)

        if (kq.data.errorCode != 0) {
          this.$root.$toast.error("Có lỗi trong quá trình điều chỉnh hóa đơn: " + kq.data.faultString)
          return false
        }
        let result = kq.data.data.split(':')
        if (result[0] === 'OK') {
          let result1 = result[1].split(';')
          this.objHoaDonDienTuEdit.InvoicePattern = result1[0]
          this.objHoaDonDienTuEdit.SerialNo = result1[1]
          let result2 = result1[2].split('_')
          this.objHoaDonDienTuEdit.InvoiceNo = result2[1]
          this.fkey_new = result2[0]
          let bd = this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, this.formThongTinTraCuu.kyHieuSelected)
          let bdct = this.TaoDuLieuBDCT()
          let temp = {
            "thaotac":this.vthaotac,
            "js_bd_hddt":JSON.stringify(bd),
            "js_bd_hddt_ct":JSON.stringify(bdct),
            "ma_nd": this.tt_nd.ma_nd,
            "bdhddt_cha_id": this.bdhddt_cha_id
          }
          let dataSendUpdate = {
                "ds_para": JSON.stringify(temp),
                "phanvung_id": 0
          };

          await api.capNhatHDDTVTT(this.axios, dataSendUpdate);
          this.formThongTinTraCuu.mauSoSelected = this.formThongTinTraCuu.mauSo.find(elm => elm.TEN_LOAIHD === result1[0]).LOAIHOADON_ID
          this.formThongTinTraCuu.kyHieuSelected = this.formThongTinTraCuu.kyHieu.find(elm => elm.seri === result1[1]).seri_id
          this.formThongTinTraCuu.soHoaDon = result2[1]
          this.$root.$toast.success('Điều chỉnh hóa đơn thành công')
          this.disableGhiLai = true
          this.invToken = ''
          this.Fkey = ''
          this.fkey_new = ''
        } else {
          this.$root.$toast.error(`Có lỗi trong quá trình điều chỉnh hóa đơn:  ${kq.data.data}`)
        }
      } catch (e) {
      } finally {
        await this.HienThi_ThongTin()
      }

    },

    async ThayTheHoaDon(){
      let pattern = "";
      let seri = "";
      let sohoadon = "";

      if (!this.formThongTinTraCuu.mauSoSelected) {
        this.$root.$toast.warning('Bạn chưa chọn mẫu số !')
        return false
      }
      if (!this.formThongTinTraCuu.kyHieuSelected) {
        this.$root.$toast.warning('Bạn chưa chọn ký hiệu !')
        return false
      }
      pattern = this.formThongTinTraCuu.mauSo.find((elm) => elm.LOAIHOADON_ID == this.formThongTinTraCuu.mauSoSelected).TEN_LOAIHD
      seri = this.formThongTinTraCuu.kyHieu.find((elm) => elm.seri_id == this.formThongTinTraCuu.kyHieuSelected).seri

      let lstProductFilter = []
      console.log(this.lstProduct)
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
        let kq = "";
        let xmlInvData = "" ;

        xmlInvData = await this.GetInvoiceXml_ThayThe_tt78(this.objHoaDonDienTuEdit)
        let dataSendReplaceInv = {
          acPass: this.tt_nd.pass_hddt_td,
          account: this.tt_nd.user_hddt_td,
          attachFile: this.thongTinHoaDon.bienBanDinhKem,
          convert: 0,
          fkey: this.Fkey,
          pattern: pattern,
          serial: seri,
          xmlInvData: xmlInvData
        }

        kq = await api.ReplaceInvoiceAction_VTT(this.axios, dataSendReplaceInv)
        console.log('thay the hoa don')
        console.log(kq.data);
        if (kq.data.errorCode != 0) {
          this.$root.$toast.error("Có lỗi trong quá trình thay thế hóa đơn: " + kq.data.faultString)
          return false
        }
        let result = kq.data.data.split(':')
        if (result[0] == 'OK') {
          let result1 = result[1].split(';')
          this.objHoaDonDienTuEdit.InvoicePattern = result1[0]
          this.objHoaDonDienTuEdit.SerialNo = result1[1]
          let result2 = result1[2].split('_')
          this.objHoaDonDienTuEdit.InvoiceNo = result2[1]
          this.fkey_new = result2[0]
          let bd = this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, this.formThongTinTraCuu.kyHieuSelected)
          let bdct = this.TaoDuLieuBDCT()
          let temp = {
            "thaotac":this.vthaotac,
            "js_bd_hddt":JSON.stringify(bd),
            "js_bd_hddt_ct":JSON.stringify(bdct),
            "ma_nd": this.tt_nd.ma_nd,
            "bdhddt_cha_id": this.bdhddt_cha_id
          }
          let dataSendUpdate = {
                "ds_para": JSON.stringify(temp),
                "phanvung_id": 0
          };

          await api.capNhatHDDTVTT(this.axios, dataSendUpdate);
          this.formThongTinTraCuu.mauSoSelected = this.formThongTinTraCuu.mauSo.find(elm => elm.TEN_LOAIHD === result1[0]).LOAIHOADON_ID
          this.formThongTinTraCuu.kyHieuSelected = this.formThongTinTraCuu.kyHieu.find(elm => elm.seri === result1[1]).seri_id
          this.formThongTinTraCuu.soHoaDon = result2[1]
          this.$root.$toast.success('Thay thế hóa đơn thành công!')
          this.disableGhiLai = true
          this.invToken = ''
          this.Fkey = ''
          this.fkey_new = ''
        } else {
          this.$toast.error(`Có lỗi trong quá trình thay thế hóa đơn:  ${kq.data.data}`)
        }
      } catch (e) {
      } finally {
        //await this.HienThi_ThongTin()
      }

    },

    // keyPressed (args) {
    //   if (args.key === 'Enter') args.cancel = true
    // },

    async HuyHoaDon(){
        try {
            let dataCancel = {
              acPass: this.tt_nd.pass_hddt_td,
              account: this.tt_nd.user_hddt_td,
              fkey: this.Fkey
            }

            let res = api.cancelInv_VTT(this.axios, dataCancel);
            let result = res.data.data.split(':')
            if (result[0] == 'OK') {
               let bd = this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, 0);
               let bdct = this.TaoDuLieuBDCT();

              let temp = {
                "thaotac":this.vthaotac,
                "js_bd_hddt":JSON.stringify(bd),
                "js_bd_hddt_ct":JSON.stringify(bdct),
                "ma_nd": this.tt_nd.ma_nd,
                "bdhddt_cha_id": this.bdhddt_cha_id
              }
              let dataSendUpdate = {
                    "ds_para": JSON.stringify(temp),
                    "phanvung_id": 0
              };
              await api.capNhatHDDTVTT(this.axios, dataSendUpdate);


              this.$toast.success('Hủy hóa đơn thành công !');
              this.HienThi_ThongTin()

            }else{
              this.$toast.error(`Có lỗi trong quá trình hủy hóa đơn: ${res.data.data}`)
            }

        } catch (error) {
        }

    },

    async HuyThanhToan_HuyHoaDon(){
      console.log("HuyThanhToan_HuyHoaDon");
      let dataHuy = {
          "lstFkey": this.Fkey
      }
      let res = await api.UnConfirmPaymentFkey_VTT(this.axios, dataHuy);
      if(res.data.errorCode != 0){
        this.$root.$toast.error("Lỗi 101: " + res.data.faultString)
        return false
      }
      this.$toast.success('Hủy thanh toán hóa đơn thành công !')
      let dataCancel = {
          acPass: this.tt_nd.pass_hddt_td,
          account: this.tt_nd.user_hddt_td,
          fkey: this.Fkey
      }
      let cancelInvResult = await api.cancelInv_VTT(this.axios, dataCancel);
      if (cancelInvResult.data.errorCode != 0) {
        this.$root.$toast.error(cancelInvResult.data.faultString)
        return false
      }
      const bd = this.TaoDuLieuBD(this.objHoaDonDienTu, this.objHoaDonDienTuEdit, 0)
      const bdct = this.TaoDuLieuBDCT()
      let temp = {
        "thaotac":this.vthaotac,
        "js_bd_hddt":JSON.stringify(bd),
        "js_bd_hddt_ct":JSON.stringify(bdct),
        "ma_nd": this.tt_nd.ma_nd,
        "bdhddt_cha_id": this.bdhddt_cha_id
      }
      let dataSendUpdate = {
            "ds_para": JSON.stringify(temp),
            "phanvung_id": 0
      };
      await api.capNhatHDDTVTT(this.axios, dataSendUpdate);
      this.$toast.success('Hủy hóa đơn thành công !')
      await this.HienThi_ThongTin()

    },

    TaoDuLieuBD(objOld, objNew, _seri_id){
      let dsBDHDDT = [];
      const row = {}
      row.MA_TT = this.formThongTinTraCuu.maKhachHang
      row.NHANVIEN_ID = this.$root.token.getNhanVienID()
      row.DONVI_ID = this.$root.token.getDonViID()
      row.KIEU_ID = 3
      row.TEN_TT = this.thongTinHoaDon.tenKhachHang
      row.DIACHI_TT = this.thongTinHoaDon.diaChiKH,
      row.SDT = this.thongTinHoaDon.soDienThoai,
      row.MST = this.thongTinHoaDon.maSoThueKH,
      row.EMAIL = this.thongTinHoaDon.email ? this.thongTinHoaDon.email : ""
      row.FKEY = this.Fkey
      row.HINHTHUC_TT = this.thongTinHoaDon.hinhThucTT
      row.TIEN = Math.abs(this.thongTinHoaDon.tongCongTienThanhToan)
      row.VAT = Math.abs(this.thongTinHoaDon.tienThueGTGT)
      row.TYLE_VAT = Math.abs(this.thongTinHoaDon.thueGTGT)
      row.TIENBANGCHU = this.thongTinHoaDon.soTienVietChu
      switch(this.thongTinHoaDon.thaoTacSelected  ) {
        case "1":
          // code block
          row.KIEU_BD = 4;
          row.THONGTINHD_CU = "";
          row.THONGTINHD_MOI = this.GetXMLFromObject(objNew);
          row.TRANGTHAI = 1
          break;
        case "2":
          // code block
          row.THONGTINHD_CU = this.GetXMLFromObject(objOld);
          row.THONGTINHD_MOI = this.GetXMLFromObject(objNew);
          if (this.thongTinHoaDon.kieuDieuChinh.toString() == "1") // Điều chỉnh tăng
              row.KIEU_BD = 8;
          else if (this.thongTinHoaDon.kieuDieuChinh.toString() == "2") // Điều chỉnh giảm
              row.KIEU_BD = 9;
          else if (this.thongTinHoaDon.kieuDieuChinh.toString() == "3") // Điều chỉnh thông tin
              row.KIEU_BD = 10;
          else
          row.KIEU_BD = 1;
          row.TRANGTHAI = 1; // Đang sử dụng
          row.BDHDDT_CHA_ID = this.bdhddt_cha_id;
          row.FKEY = this.fkey_new;
          break;
        case "3":
          row.KIEU_BD = 2;
          row.THONGTINHD_CU = this.GetXMLFromObject(objOld);
          row.THONGTINHD_MOI = this.GetXMLFromObject(objNew);
          row.TRANGTHAI = 1; // Đang sử dụng
          row.BDHDDT_CHA_ID = this.bdhddt_cha_id;
          row.FKEY = this.fkey_new;
          break;
        case "4":
          row.KIEU_BD = 3;
          row.THONGTINHD_CU = this.GetXMLFromObject(objOld);
          row.THONGTINHD_MOI = this.GetXMLFromObject(objNew);
          row.TRANGTHAI = 4; // Hủy hóa đơn
          row.BDHDDT_CHA_ID = this.bdhddt_cha_id;
          break;
        case "5":
          row.KIEU_BD = 11;
          row.THONGTINHD_CU = this.GetXMLFromObject(objOld);
          row.THONGTINHD_MOI = this.GetXMLFromObject(objNew);
          row.TRANGTHAI = 4; // Đang sử dụng
          row.BDHDDT_CHA_ID = this.bdhddt_cha_id;
          break;
        case "6":
          row.KIEU_BD = 5;
          row.THONGTINHD_CU = this.GetXMLFromObject(objOld);
          row.THONGTINHD_MOI = this.GetXMLFromObject(objNew);
          row.TRANGTHAI = 1; // Đang sử dụng
          break;
        case "7":
          row.KIEU_BD = 6;
          row.THONGTINHD_CU = this.GetXMLFromObject(objOld);
          row.THONGTINHD_MOI = this.GetXMLFromObject(objNew);
          row.TRANGTHAI = 1; // Đang sử dụng
          row.BDHDDT_CHA_ID = this.bdhddt_cha_id;
          break;
        case "7":
          row.KIEU_BD = 7;
          row.THONGTINHD_CU = this.GetXMLFromObject(objOld);
          row.THONGTINHD_MOI = this.GetXMLFromObject(objNew);
          row.TRANGTHAI = 1; // Đang sử dụng
          row.BDHDDT_CHA_ID = this.bdhddt_cha_id;
          break;
        default:
          // code block
      }
      row.LOAIHOADON_CU = objOld.InvoicePattern;
      row.LOAIHOADON_MOI = objNew.InvoicePattern;
      row.SERI_CU = objOld.SerialNo;
      row.SERI_MOI = objNew.SerialNo;
      row.SO_HD_CU = objOld.InvoiceNo;
      row.SO_HD_MOI = objNew.InvoiceNo;
      row.TONGTIEN = Math.abs(this.t_tien_edited)
      row.TIENGOC = this.tiengoc
      row.NGUOI_CN = this.thongTinNguoiDung.ma_nd ? this.thongTinNguoiDung.ma_nd : ""
      row.MAY_CN = this.thongTinNguoiDung.may_cn ? this.thongTinNguoiDung.may_cn : ""
      row.NGAY_CN = this.thongTinNguoiDung.ngay_cn ? this.thongTinNguoiDung.ngay_cn : ""
      row.GHICHU = '(Chức năng Động) ' + this.thongTinNguoiDung.ghiChuHoaDon
      row.SERI_MOI_ID = _seri_id
      row.LOAIHOADON_MOI_ID = this.formThongTinTraCuu.mauSoSelected
      // if (!this.hoaDonFilter.txtFilePath) row.FILE_DINHKEM = ReadFile(txtFilePath.Text);
      this.dsBDHDDT.push(row)
      dsBDHDDT.push(row);
      return dsBDHDDT;
    },

    GetXMLFromObject (data) {
      let xml = ''
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
      return xml
    },


    TaoDuLieuBD_v2(SerialNo1, InvoiceNo1, _seri_id, loi){
        let dsBDHDDT = []
        let patternSelect =  this.formThongTinTraCuu.mauSo.filter(x => (x.LOAIHOADON_ID == this.formThongTinTraCuu.mauSoSelected));
        let pattern = patternSelect[0].TEN_LOAIHD;
        let obj = {
            BDHDDT_ID: "",
            MA_TT: this.vma_kh,
            NHANVIEN_ID: this.tt_nd.nhanvien_id,
            DONVI_ID: this.tt_nd.donvi_id,
            KIEU_ID: 3,
            TEN_TT: this.thongTinHoaDon.tenKhachHang,
            DIACHI_TT: this.thongTinHoaDon.diaChiKH,
            SDT: this.thongTinHoaDon.soDienThoai,
            MST: this.thongTinHoaDon.maSoThueKH,
            EMAIL: this.thongTinHoaDon.email,
            FKEY: this.Fkey,
            HINHTHUC_TT:this.thongTinHoaDon.hinhThucTT,
            TIEN: this.thongTinHoaDon.tongCongTienThanhToan,
            VAT: this.thongTinHoaDon.tienThueGTGT,
            TYLE_VAT: this.thongTinHoaDon.thueGTGT ,
            TIENBANGCHU:this.thongTinHoaDon.soTienVietChu,
            THANHTOAN_LOI: loi,
            KIEU_BD: 5,
            TRANGTHAI:1,
            LOAIHOADON_CU: pattern,
            LOAIHOADON_MOI: pattern,
            SERI_CU: SerialNo1,
            SERI_MOI: SerialNo1,
            SO_HD_CU: InvoiceNo1,
            SO_HD_MOI: InvoiceNo1,
            TONGTIEN: this.objHoaDonDienTuEdit.Amount,
            TIENGOC: this.tiengoc,
            NGUOI_CN:this.tt_nd.ma_nd,
            MAY_CN: this.tt_nd.may_cn,
            NGAY_CN: this.tt_nd.ngay_cn,
            GHICHU: "(Chức năng Động)" + this.thongTinHoaDon.ghiChuHoaDon,
            SERI_MOI_ID: _seri_id,
            LOAIHOADON_MOI_ID:this.thongTinHoaDon.mauSo,
            FILE_DINHKEM: this.thongTinHoaDon.bienBanDinhKem
        }
        this.dsBDHDDT.push(obj)
        dsBDHDDT.push(obj);
        return dsBDHDDT
    },


    TaoDuLieuBDCT(){
      let dsBDHDDTCT = [];
      for (let i = 0; i < this.ds.list.length; i++){
        if(this.ds.list[i].ProdName != ""){
          let row = {};
          row.BDHDDT_ID = "";
          row.DONGIA = this.ds.list[i].ProdPrice;
          row.DONVITINH = this.ds.list[i].ProdUnit;
          row.NOIDUNG = this.ds.list[i].ProdName;
          row.SOLUONG = this.ds.list[i].ProdQuantity;
          row.STT = this.ds.list[i].STT;
          row.THANHTIEN = this.ds.list[i].Total;
          this.dsBDHDDTCT.push(row);
          dsBDHDDTCT.push(row)
        }
      }
      return dsBDHDDTCT;
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

    async TraCuu(_Pattern, _Seri, _sohoadon) {
      console.log(_Pattern, _Seri, _sohoadon)
      this.first = true
      this.thongTinHoaDon.bienBanDinhKem = "";
      this.thongTinHoaDon.ghiChuHoaDon = "";
      const products = []
      if (_Pattern && _Seri && _sohoadon) this.invToken = `${_Pattern};${_Seri};${_sohoadon}`
      else {
        if (!this.thongTinHoaDon.mauSoSelected) {
          // this.$refs.mauSoRef.focus()
          return false
        }
        if (!this.thongTinHoaDon.kyHieuSelected) {
          this.$refs.kyHieuRef.focus()
          return false
        }
        if (!this.thongTinHoaDon.soHoaDon) {
          this.$refs.soHoaDonRef.focus()
          return false
        }
        const mauSo = this.thongTinHoaDon.mauSo.find(elm => elm.LOAIHOADON_ID === this.thongTinHoaDon.mauSoSelected).TEN_LOAIHD
        const kyHieu = this.thongTinNguoiDung.kyHieu.find(elm => elm.seri_id === this.thongTinNguoiDung.kyHieuSelected).seri
        this.invToken = `${mauSo};${kyHieu};${this.thongTinHoaDon.soHoaDon}`
      }

      // fill dữ liệu
      let datadownloadInvNoPay = {
        invToken: this.invToken //"01GTKT0/002;AC/20E;16181" //this.invToken
      }

      let result = await api.downloadInvNoPay_VTT(this.axios, datadownloadInvNoPay)
      let resultInvNoPay = result.data.data;
      const xmlDoc = this.parseXmlDoc(resultInvNoPay)
      console.log(xmlDoc);
      if (this.tt78) {
        console.log(xmlDoc.getElementsByTagName('HHDVu').length)
        if (xmlDoc.getElementsByTagName('HHDVu').length === 0) {
          this.objHoaDonDienTu.Amount = +this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTTTBSo' })
          this.objHoaDonDienTu.Amount_words = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTTTBChu' })
          this.objHoaDonDienTu.ArisingDate = this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'NLap' })
          this.objHoaDonDienTu.Buyer = this.objHoaDonDienTu.ArisingDate = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'HVTNMHang' })
          this.objHoaDonDienTu.ComAddress = this.objHoaDonDienTu.ArisingDate = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'DChi' })
          this.objHoaDonDienTu.ComName = this.objHoaDonDienTu.ArisingDate = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'Ten' })
          this.objHoaDonDienTu.ComPhone = this.objHoaDonDienTu.ArisingDate = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'SDThoai' })
          this.objHoaDonDienTu.ComTaxCode = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'MST' })
          this.objHoaDonDienTu.CusAddress = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'DChi' })
          this.objHoaDonDienTu.CusBankName = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'TNHang' })
          this.objHoaDonDienTu.CusCode = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'MKHang' })
          this.objHoaDonDienTu.CusName = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'Ten' })
          this.objHoaDonDienTu.CusPhone = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'SDThoai' })
          this.objHoaDonDienTu.CusTaxCode = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'MST' })
          if( this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'TTKhac' }) ) {
           this.objHoaDonDienTu.Extra = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'TTKhac', fourth: 'TTruong' })
          }
          this.objHoaDonDienTu.InvoiceName = this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'THDon' })
          this.objHoaDonDienTu.InvoiceNo = this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'SHDon' })
          this.objHoaDonDienTu.InvoicePattern = this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'KHMSHDon' })
          console.log('InvoicePattern == ' + this.objHoaDonDienTu.InvoicePattern)
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
              ProdUnit: (elm.getElementsByTagName('DVTinh')[0] != undefined) ? elm.getElementsByTagName('DVTinh')[0].textContent : "",
              ProdQuantity: +elm.getElementsByTagName('SLuong')[0].textContent,
              ProdPrice: +elm.getElementsByTagName('DGia')[0].textContent,
              Discount: 0,
              DiscountAmount: 0,
              VATRate: 0,
              VATAmount: 0,
              Amount: 0,
              TinhVat: false
            }
            console.log(product);
            // let tSuatnew = elm.getElementsByTagName('TSuat')[0].textContent;
            // console.log(tSuatnew);
            // if(tSuatnew == "KKKNT"){
            //   product.VATRate = -2
            // }else{
            //   if(tSuatnew == "KCT") {
            //     product.VATRate = -1
            //   }else{
            //     product.VATRate = +tSuat.substring(0, tSuatnew.length - 1)
            //   }
            // }
            const tSuatProduct = elm.getElementsByTagName('TSuat')[0].textContent;
            if (tSuatProduct == 'KKKNT') product.VATRate = -2
            else if (tSuatProduct == 'KCT') product.VATRate = -1
            else if (tSuatProduct.includes('KHAC:')) product.VATRate = 10
            else product.VATRate = +tSuatProduct.substring(0, tSuatProduct.length - 1)
            if (elm.getElementsByTagName('MHHDVu')[0] != undefined) {
              product.Code = elm.getElementsByTagName('MHHDVu')[0].textContent
            }
            console.log(elm.getElementsByTagName('TTKhac'))
            if (elm.getElementsByTagName('TTKhac').length > 0) {
              product.Amount = +elm.getElementsByTagName('TTKhac')[0].getElementsByTagName('DLieu')[0].textContent
              product.VATAmount = +elm.getElementsByTagName('TTKhac')[0].getElementsByTagName('DLieu')[1].textContent
            }else{
              if(product.VATRate <= 0){
                  product.VATAmount = 0;
                  product.Amount = product.Total
              }else{
                  product.VATAmount = parseFloat((product.ProdQuantity * product.ProdPrice * product.VATRate) / 100).toFixed(2);
                  product.Amount = product.VATAmount +  product.Total
              }
            }

            products.push(product)
          }
          this.objHoaDonDienTu.Products = [...products]
        } else {
          this.objHoaDonDienTu.Amount = +this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTTTBSo' })
          this.objHoaDonDienTu.Amount_words = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'TToan', fourth: 'TgTTTBChu' })
          this.objHoaDonDienTu.ArisingDate = this.getThirdLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'TTChung', third: 'NLap' })
          this.objHoaDonDienTu.ComAddress = this.objHoaDonDienTu.ArisingDate = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'DChi' })
          this.objHoaDonDienTu.ComName = this.objHoaDonDienTu.ArisingDate = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'Ten' })
          this.objHoaDonDienTu.ComPhone = this.objHoaDonDienTu.ArisingDate = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'SDThoai' })
          this.objHoaDonDienTu.ComTaxCode = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NBan', fourth: 'MST' })
          console.log(this.objHoaDonDienTu.ComTaxCode)
          this.objHoaDonDienTu.CusAddress = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'DChi' })
          this.objHoaDonDienTu.CusBankName = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'TNHang' })
          this.objHoaDonDienTu.CusBankNo = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'STKNHang' })
          this.objHoaDonDienTu.CusCode = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'MKHang' })
          this.objHoaDonDienTu.CusName = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'Ten' })
          this.objHoaDonDienTu.CusPhone = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'SDThoai' })
          this.objHoaDonDienTu.CusTaxCode = this.getFourthLevelContentXml(xmlDoc, { first: 'DLHDon', second: 'NDHDon', third: 'NMua', fourth: 'MST' })
          console.log(this.objHoaDonDienTu.CusTaxCode)
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
              Total: +elm.getElementsByTagName('ThTien')[0].textContent,
              ProdName: elm.getElementsByTagName('THHDVu')[0].textContent,
              ProdUnit: (elm.getElementsByTagName('DVTinh')[0] != undefined) ? elm.getElementsByTagName('DVTinh')[0].textContent : "",
              ProdQuantity: +elm.getElementsByTagName('SLuong')[0].textContent,
              ProdPrice: +elm.getElementsByTagName('DGia')[0].textContent,
              Discount: 0,
              DiscountAmount: 0,
              VATRate: 0,
              VATAmount: 0,
              Amount: +elm.getElementsByTagName('TTKhac')[0].getElementsByTagName('TTin')[0].getElementsByTagName('DLieu')[0].textContent,
              TinhVat: false
            }
            if (elm.getElementsByTagName('TTKhac')[0].getElementsByTagName('TTin').length > 1) {
              product.VATAmount = +elm.getElementsByTagName('TTKhac')[0].getElementsByTagName('TTin')[1].getElementsByTagName('DLieu')[0].textContent
            }
            const tSuatProduct = elm.getElementsByTagName('TSuat')[0].textContent
            if (tSuatProduct == 'KKKNT') product.VATRate = -2
            else if (tSuatProduct == 'KCT') product.VATRate = -1
            else if (tSuatProduct.includes('KHAC:')) product.VATRate = 10
            else product.VATRate = +tSuatProduct.substring(0, tSuatProduct.length - 1)
            this.objHoaDonDienTu.VAT_Rate = product.VATRate
            console.log("product.VAT_Rate " + this.objHoaDonDienTu.VAT_Rate)
            products.push(product)
          }
          this.objHoaDonDienTu.Products = products
        }
        this.objHoaDonDienTuEdit = { ...this.objHoaDonDienTu }
        console.log(this.objHoaDonDienTu);
        console.log(this.objHoaDonDienTuEdit);
        let pattern1 = true
        if (products.length > 1) {
          const vatrate = products[0].VATRate
          for (const product of products) {
            if (product.ProdName && vatrate !== product.VATRate) {
              pattern1 = false
              break
            }
          }
        }
        this.thongTinHoaDon.mauSoSelected = pattern1 ? 0 : 1
      }
      this.thongTinHoaDon.tenHoaDon = this.objHoaDonDienTuEdit.InvoiceName
      this.thongTinHoaDon.mauSo = this.objHoaDonDienTuEdit.InvoicePattern
      this.thongTinHoaDon.kyHieu = this.objHoaDonDienTuEdit.SerialNo
      this.formThongTinTraCuu.mauSo.forEach((ele, index) => {
          let dataSend = {
            kieu: 0,
            loaihd_id: ele.LOAIHOADON_ID, //newVal
            phanvung_id: this.$root.token.getPhanVungID,
          };
          api.laySeri(this.axios, dataSend).then(res => {
              console.log(res.data.data);
              let hieuSoArr = res.data.data
              for(let i = 0; i < hieuSoArr.length; i++){
                if(hieuSoArr[i].seri == this.objHoaDonDienTuEdit.SerialNo){
                  this.formThongTinTraCuu.mauSoSelected = ele.LOAIHOADON_ID
                }
              }
          })


      })

      this.thongTinHoaDon.so = this.objHoaDonDienTuEdit.InvoiceNo
      this.thongTinHoaDon.maSoThue = this.objHoaDonDienTuEdit.ComTaxCode
      this.thongTinHoaDon.tenDonVi = this.objHoaDonDienTuEdit.ComName
      this.thongTinHoaDon.diaChiDV = this.objHoaDonDienTuEdit.ComAddress
      this.thongTinHoaDon.tenKhachHang = this.objHoaDonDienTuEdit.CusName
      this.thongTinHoaDon.diaChiKH = this.objHoaDonDienTuEdit.CusAddress
      this.thongTinHoaDon.maSoThueKH = this.objHoaDonDienTuEdit.CusTaxCode
      this.thongTinHoaDon.soDienThoai = this.objHoaDonDienTuEdit.CusPhone
      this.thongTinHoaDon.hinhThucTT = this.objHoaDonDienTuEdit.Kind_of_Payment
      this.thongTinHoaDon.thangHoaDon = this.objHoaDonDienTuEdit.KindOfService
      this.thongTinHoaDon.thueGTGT = this.objHoaDonDienTuEdit.VAT_Rate
      this.thongTinHoaDon.theMoRong = this.objHoaDonDienTuEdit.Extra
      this.thongTinHoaDon.email = this.objHoaDonDienTuEdit.EmailDeliver
      const lsPro = []
      for (const product of this.objHoaDonDienTuEdit.Products) {
        lsPro.push(product)
      }
      // if (lsPro.length < 15) {
      //   lsPro.push({
      //     Code: '',
      //     Total: 0,
      //     ProdName: '',
      //     ProdUnit: '',
      //     ProdQuantity: 0,
      //     ProdPrice: 0,
      //     Discount: 0,
      //     DiscountAmount: 0,
      //     VATRate: 0,
      //     VATAmount: 0,
      //     Amount: 0,
      //     TinhVat: false
      //   })
      // }
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
      this.ds.list = [...this.lstProduct]
      this.tableThueBaoConfig.dataSources = [...this.lstProduct]
      if (this.tt78) {
        const total = this.lstProduct
          .map(elm => elm.Total)
          .reduce((prev, next) => prev + next)
        const vatAmount = this.lstProduct
          .map(elm => elm.VATAmount)
          .reduce((prev, next) => prev + next)
        console.log(vatAmount)
        this.thongTinHoaDon.tienChiuThue0 = this.lstProduct
          .filter(elm => elm.VATRate === 0)
          .map(elm => elm.Total)
          .reduce((prev, next) => (+prev) + (+next), 0)
        this.thongTinHoaDon.tienKhongChiuThue = this.lstProduct
          .filter(elm => elm.VATRate === -1)
          .map(elm => elm.Total)
          .reduce((prev, next) => (+prev) + (+next), 0)
        this.thongTinHoaDon.tienThue0 = this.lstProduct
          .filter(elm => elm.VATRate === -2)
          .map(elm => elm.Total)
          .reduce((prev, next) => (+prev) + (+next), 0)
        this.thongTinHoaDon.tienChiuThue5 = this.lstProduct
          .filter(elm => elm.VATRate === 5)
          .map(elm => elm.Total)
          .reduce((prev, next) => (+prev) + (+next), 0)
        this.thongTinHoaDon.tienChiuThue10 = this.lstProduct
          .filter(elm => elm.VATRate === 10)
          .map(elm => elm.Total)
          .reduce((prev, next) => (+prev) + (+next), 0)
        this.thongTinHoaDon.tienThue5 = this.lstProduct
          .filter(elm => elm.VATRate === 5)
          .map(elm => elm.VATAmount)
          .reduce((prev, next) => (+prev) + (+next), 0)
        this.thongTinHoaDon.tienThue10 = this.lstProduct
          .filter(elm => elm.VATRate === 10)
          .map(elm => elm.VATAmount)
          .reduce((prev, next) => (+prev) + (+next), 0)
        this.thongTinHoaDon.congTienDichVu = total
        this.thongTinHoaDon.tienThueGTGT = vatAmount
        this.thongTinHoaDon.tongCongTienThanhToan = total + vatAmount
        this.objHoaDonDienTuEdit.Total = total
        this.objHoaDonDienTuEdit.VAT_Amount = vatAmount
        this.objHoaDonDienTuEdit.Amount = total + vatAmount
      } else {
        this.thongTinHoaDon.congTienDichVu = this.objHoaDonDienTuEdit.Total
        this.thongTinHoaDon.tienThueGTGT = this.objHoaDonDienTuEdit.VAT_Amount
        this.thongTinHoaDon.tongCongTienThanhToan = this.objHoaDonDienTuEdit.Amount
      }
      this.t_tien_edited = this.objHoaDonDienTuEdit.Amount
      let docTienTemp = await api.docTienChu(this.axios, {money: this.objHoaDonDienTuEdit.Amount})
      this.thongTinHoaDon.soTienVietChu = docTienTemp.data.data
      this.first = false
      return true
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


    ReadFile(){

    },
    async txtMaGD_KeyPress(){
        if(this.formThongTinTraCuu.maKhachHang != ""){
           await this.HienThi_ThongTin();
           await this.KiemTra_DieuChinh_TT_HD();
           if(this.formThongTinTraCuu.thaoTac == "1" || this.formThongTinTraCuu.thaoTac == "6"){
              this.thongTinHoaDon.diaChiDV = "";
              this.thongTinHoaDon.diaChiKH = "";
              this.thongTinHoaDon.theMoRong = "";
              this.thongTinHoaDon.bienBanDinhKem = "";
              this.thongTinHoaDon.hinhThucTT = "";
              this.thongTinHoaDon.ghiChuHoaDon = "";
              this.ds.list = [];
              this.tableThueBaoConfig.dataSources = [{
                  STT: 1,
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
              }
              ]
              this.thongTinHoaDon.congTienDichVu = 0;
              this.thongTinHoaDon.tienThueGTGT = 0
              this.thongTinHoaDon.tongCongTienThanhToan = 0
              this.thongTinHoaDon.soTienVietChu = "Không đồng"
           }
        }else{
          this.$toast.warning("Bạn chưa nhập thông tin mã khách hàng!");
          return false;
        }
    },


    KiemTra_DieuChinh_TT_HD(){
        if (this.thongTinNguoiDung.thaoTacSelected == "2") {
        if (this.thongTinNguoiDung.kieuDieuChinhSelected == "3") {
          if (this.objHoaDonDienTuEdit &&
            this.objHoaDonDienTuEdit.hasOwnProperty('Products') &&
            this.objHoaDonDienTuEdit.Products &&
            this._pattern
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
              Total: product.Total,
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
                  ProdQuantity: 0,
                  ProdPrice: 0,
                  Discount: 0,
                  DiscountAmount: 0,
                  VATRate: 0,
                  VATAmount: 0,
                  Amount: 0
                })
              }
            })
            this.objHoaDonDienTuEdit.Products = lstProductFilter
            this.tableThueBaoConfig.dataSources = [...this.lstProduct]
            this.objHoaDonDienTuEdit.Amount_words = 'Không đồng !'
            this.objHoaDonDienTuEdit.Total = 0
            this.objHoaDonDienTuEdit.VAT_Amount = 0
            this.objHoaDonDienTuEdit.VAT_Rate = 0
            this.objHoaDonDienTuEdit.Amount = 0
            this.thongTinHoaDon.thueGTGT = 0
            this.thongTinHoaDon.congTienDichVu = 0
            this.thongTinHoaDon.tongCongTienThanhToan = 0
            this.thongTinHoaDon.tienThueGTGT = 0
            this.thongTinHoaDon.soTienVietChu = 'Không đồng'
          }
        } else {
          this.TraCuu(this._pattern, this._seri, this._so_hd)
        }
      }

    },

    tsbtnGhiLai_Click(){
      this.capNhat();
    },

    Clear() {
      this.vma_kh = "";
      this.maKhachHang = ""
      this.vnguoicn = "";
      this.bdhddt_cha_id = 0;
      this._dieuchinh = 0;
      this._thaythe = 0;
      this.vma_gd = "";
      this.t_tien_load = 0;
      this.t_tien_edited = 0;
      this.t_tien_tt = 0;
      this.vnhanvien_id = 0;
      this.vfax = "";
      this.vnguoi_dd = "";
      this.vloai_kh = "";
      this.thongTinHoaDon.tenKhachHang = "";
      this.thongTinHoaDon.email = "";
      this.thongTinHoaDon.diaChiDV = "";
      this.thongTinHoaDon.diaChiKH = "";
      this.thongTinHoaDon.theMoRong = "";
      this.thongTinHoaDon.bienBanDinhKem = "";
      this.thongTinHoaDon.hinhThucTT = "";
      this.thongTinHoaDon.thangHoaDon = ""; //txtKyHD
      this.thongTinHoaDon.maSoThue = "";
      this.thongTinHoaDon.maSoThueKH = "";
      this.thongTinHoaDon.soDienThoai = "";
      this.vsodt_kh = "";
      this.formThongTinTraCuu.soHoaDon = "";

      this.thongTinHoaDon.soTienVietChu = "Không đồng";
      this.thongTinHoaDon.thueGTGT = 10;
      this.thongTinHoaDon.tienThueGTGT = 0;
      this.thongTinHoaDon.congTienDichVu = 0;
      this.thongTinHoaDon.tienChiuThue10 = 0;
      this.thongTinHoaDon.tienChiuThue5 = 0;
      this.thongTinHoaDon.tienChiuThue0 = 0;
      this.thongTinHoaDon.tienThue10 = 0;
      this.thongTinHoaDon.tienThue5 = 0;
      this.thongTinHoaDon.tienThue0 = 0;
      this.thongTinHoaDon.tienKhongChiuThue = 0;
      this.thongTinHoaDon.kyHieu = "0";
      this.ds.list = [];
      this.tableThueBaoConfig.dataSources = [
        {
            STT: 1,
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
        }
      ]

      this.vhdkh_id = 0;
      this.vphieutt_id = "";
      this.khoa = 1;

      this._pattern = "";
      this._seri = "";
      this._so_hd = "";

    },


    async Kiemtra_dulieu(thaotac) {
      let tiengoc = 0;
      // handle kiểm tra dữ liệu
      if (
        this.tt_nd.user_hddt_td == undefined ||
        this.tt_nd.user_hddt_td == ""
      ) {
        this.$toast.warning(
          "Người dùng chưa được cấp tài khoản xuất hóa đơn !"
        );
        return false;
      }

      if (this.thongTinHoaDon.tenKhachHang == "") {
        this.$toast.warning("Tên khách hàng không được để trống !");
        this.$refs.ten_kh.focus();
        return false;
      }

      if (this.thongTinHoaDon.diaChi == "") {
        this.$toast.warning("Địa chỉ khách hàng không được để trống !");
        this.$refs.dia_chi.focus();
        return false;
      }

      if (this.thongTinHoaDon.hinhThucTT == "") {
        this.$toast.warning("Hình thức thanh toán không được để trống !");
        this.$refs.httt.focus();
        return false;
      }

      let kt_km = true;
      console.log(this.tableThueBaoConfig.dataSources);
      for (let i = 0; i < this.tableThueBaoConfig.dataSources.length ; i++ )
      {
          if (this.tableThueBaoConfig.dataSources[i].ProdName)
          {
              kt_km = false;
              break;
          }
      }

      if (kt_km) {
        this.$toast.warning("Kiểm tra lại dịch vụ sử dụng trên lưới !");
        return false;
      }


      // if (thaotac != 1 || thaotac != 6) {
      //   if (this.vnguoicn != this.tt_nd.ma_nd) {
      //     this.$toast.warning(
      //       "Bạn không được điều chỉnh hóa đơn do người khác tạo !"
      //     );
      //     return false;
      //   }
      // }

      if (thaotac == 1 || thaotac == 6) {
        // xuất hóa đơn
        if (this.thongTinHoaDon.soTienVietChu == "") {
          this.$toast.warning("Bạn hãy kiểm tra lại thông tin Tiền Bằng Chữ !");
          this.$refs.tien_vc.focus();
          return false;
        }
      } else if (thaotac == 2) {
        // điều chỉnh hóa đơn
        if (this.formThongTinTraCuu.kieuDieuChinhSelected == "") {
          this.$toast.warning("Bạn chưa chọn kiểu điều chỉnh !");
          return false;
        }

        if (this.da_xuathd == false) {
          this.$toast.warning(
            "Hợp đồng chưa xuất hóa đơn. Bạn không được thực hiện thao tác này !"
          );
          return false;
        }

        //Kiểm tra xem khách hàng đã xem hóa đơn hay chưa
        let kq = "";
        //kq = invoice.listInvByCus_VTT(objHoaDonDienTu.CusCode, "", "", tt_nd.user_hddt, tt_nd.pass_hddt, objHoaDonDienTu.InvoicePattern);
        //if (kq.Substring(0, 3) == "ERR")
        let dataSendCus_VTT = {
            cusCode: this.formThongTinTraCuu.maKhachHang, //"BNHCT00225681", // this.formThongTinTraCuu.maKhachHang,
            fromDate: "",
            toDate: ""
        }
        kq =  await api.listInvByCus_VTT(this.axios, dataSendCus_VTT)
        if(!kq.data) return false
        let xmlDoc = this.parseXmlDoc(kq.data.data)
        let item = Array.from(xmlDoc.getElementsByTagName('Item')).find(elm => elm.getElementsByTagName('invToken')[0].textContent === this.invToken)
        console.log("item -1")
        console.log(item)
        if (item) {
          this.Fkey = item.getElementsByTagName('fkey')[0].textContent

          let status = item.getElementsByTagName('status')[0].textContent
          if (status !== '1' && status !== '4') {
            this.$root.$toast.warning('Hóa đơn đã bị thay thế/hủy. Không thể điều chỉnh !')
            return false
          }
        }

        if (
          this.objHoaDonDienTu.CusCode == undefined ||
          this.objHoaDonDienTu.CusCode == ""
        ) {
          this.$toast.warning("Không có thông tin khách hàng để điều chỉnh");
          return false;
        }

        if (
          this.objHoaDonDienTu.CusName == undefined ||
          this.objHoaDonDienTu.CusName == ""
        ) {
          this.$toast.warning("Tên khách hàng không được để trống !");
          return false;
        }

        if (
          this.objHoaDonDienTu.CusAddress == undefined ||
          this.objHoaDonDienTu.CusAddress == ""
        ) {
          this.$toast.warning("Địa chỉ khách hàng không được để trống !");
          return false;
        }

        if (
          this.formThongTinTraCuu.kieuDieuChinhSelected == "1" ||
          this.formThongTinTraCuu.kieuDieuChinhSelected == "2"
        ) {
          if (Math.abs(this.t_tien_edited) == 0) {
            this.$toast.warning("Tiền điều chỉnh phải khác 0");
            return false;
          }
        }

        if (this.formThongTinTraCuu.kieuDieuChinhSelected == "3") {
          if (this.t_tien_edited != 0) {
            this.$toast.warning(
              "Điều chỉnh thông tin. Khoản mục tiền phải = 0 !"
            );
            return false;
          }
        }
      } else if (thaotac == 3) {
        // thay thế hóa đơn
        if (this.type == 2 || this.type == 3 || this.type == 4) {
          this.$toast.warning("Hóa đơn điều chỉnh. Bạn không thể Thay thế !");
          return false;
        }
        // Kiểm tra xem khách hàng đã xem hóa đơn hay chưa
        let kq = "";
        let dataSendCus_VTT = {
            cusCode: this.formThongTinTraCuu.maKhachHang,
            fromDate: "",
            toDate: ""
        }
        kq =  await api.listInvByCus_VTT(this.axios, dataSendCus_VTT)
        if(!kq.data) return false
        let xmlDoc = this.parseXmlDoc(kq.data.data)
        let item = Array.from(xmlDoc.getElementsByTagName('Item')).find(elm => elm.getElementsByTagName('invToken')[0].textContent === this.invToken)

        if (item) {
          this.Fkey = item.getElementsByTagName('fkey')[0].textContent
          let status = item.getElementsByTagName('status')[0].textContent
          if (status != '1') {
            this.$root.$toast.warning('Hóa đơn đã bị thay thế/sửa đổi/hủy. Không thể thay thế !')
            return false
          }
        }

        if (
          this.objHoaDonDienTu.CusCode == undefined ||
          this.objHoaDonDienTu.CusCode == ""
        ) {
          this.$toast.warning("Mã khách hàng không để để trống");
          return false;
        }

        if (
          this.objHoaDonDienTu.CusName == undefined ||
          this.objHoaDonDienTu.CusName == ""
        ) {
          this.$toast.warning("Tên khách hàng không được để trống !");
          return false;
        }

        if (
          this.objHoaDonDienTu.CusAddress == undefined ||
          this.objHoaDonDienTu.CusAddress == ""
        ) {
          this.$toast.warning("Địa chỉ khách hàng không được để trống !");
          return false;
        }
      } else if (thaotac == 4){
        if (this.type == 2 || this.type == 3 || this.type == 4) {
          this.$toast.warning("Hóa đơn điều chỉnh. Bạn không thể hủy !");
          return false;
        }

        if(this.type == 1){
          this.$toast.warning("Hợp đồng đã có hóa đơn bị thay thế. Bạn không thể hủy !");
          return false;
        }

        //Kiểm tra xem khách hàng đã xem hóa đơn hay chưa
        let kq = "";
        let dataSendCus_VTT = {
            cusCode: this.formThongTinTraCuu.maKhachHang,
            fromDate: "",
            toDate: ""
        }
        kq =  await api.listInvByCus_VTT(this.axios, dataSendCus_VTT)
        if(!kq.data) return false
        let xmlDoc = this.parseXmlDoc(kq.data.data)
        let item = Array.from(xmlDoc.getElementsByTagName('Item')).find(elm => elm.getElementsByTagName('invToken')[0].textContent === this.invToken)

        if (item) {
          this.Fkey = item.getElementsByTagName('fkey')[0].textContent
          let status = item.getElementsByTagName('status')[0].textContent
          if (status != '1') {
            this.$root.$toast.warning('Hóa đơn đã được Điều chỉnh/Thay thế/Hủy. Bạn không thể hủy !')
            return false
          }

          let payment = item.getElementsByTagName('payment')[0].textContent
          if(payment == '1'){
            this.$root.$toast.warning('Hóa đơn đã được thanh toán. Không thể hủy !');
            return false;
          }
        }

      }else if(thaotac == 9 || thaotac == 10){ // Chuyển đổi hóa đơn
        //Kiểm tra xem khách hàng đã xem hóa đơn hay chưa
        let kq = "";
        let dataSendCus_VTT = {
            cusCode: this.formThongTinTraCuu.maKhachHang,
            fromDate: "",
            toDate: ""
        }
        kq =  await api.listInvByCus_VTT(this.axios, dataSendCus_VTT)
        if(!kq.data) return false
        let xmlDoc = this.parseXmlDoc(kq.data.data)
        let item = Array.from(xmlDoc.getElementsByTagName('Item')).find(elm => elm.getElementsByTagName('invToken')[0].textContent === this.invToken)

        if (item) {
          this.Fkey = item.getElementsByTagName('fkey')[0].textContent
          let status = item.getElementsByTagName('status')[0].textContent
          if (status != '1') {
            this.$root.$toast.warning('Hóa đơn đã được Điều chỉnh/Thay thế/Hủy. Bạn không thể hủy !')
            return false
          }
        }
      } else if(thaotac == 7){ //thanh toán hóa đơn
        //Kiểm tra xem khách hàng đã xem hóa đơn hay chưa
        let kq = "";
        let dataSendCus_VTT = {
            cusCode: "BNHCT00225681" , // this.formThongTinTraCuu.maKhachHang,
            fromDate: "",
            toDate: ""
        }
        kq =  await api.listInvByCus_VTT(this.axios, dataSendCus_VTT)
        if(!kq.data) return false
        let xmlDoc = this.parseXmlDoc(kq.data.data)
        console.log(xmlDoc);
        console.log(this.invToken);
        let item = Array.from(xmlDoc.getElementsByTagName('Item')).find(elm => elm.getElementsByTagName('invToken')[0].textContent === this.invToken)
        console.log(item);
        if (item) {
          this.Fkey = item.getElementsByTagName('fkey')[0].textContent
          let status = item.getElementsByTagName('status')[0].textContent
          if (status != '1') {
            this.$root.$toast.warning('Hóa đơn đã được Điều chỉnh/Thay thế/Hủy. Bạn không thể hủy !')
            return false
          }

          let payment = item.getElementsByTagName('payment')[0].textContent
          if(payment == '1'){
            this.$root.$toast.warning('Hóa đơn đã được thanh toán. Không thể hủy !');
            return false;
          }
        }
      } else if(thaotac == 8){ // hủy thanh toán hóa đơn
        //Kiểm tra xem khách hàng đã xem hóa đơn hay chưa
        let kq = "";
        let dataSendCus_VTT = {
            cusCode: this.formThongTinTraCuu.maKhachHang,
            fromDate: "",
            toDate: ""
        }
        kq =  await api.listInvByCus_VTT(this.axios, dataSendCus_VTT)
        if(!kq.data) return false
        let xmlDoc = this.parseXmlDoc(kq.data.data)
        let item = Array.from(xmlDoc.getElementsByTagName('Item')).find(elm => elm.getElementsByTagName('invToken')[0].textContent === this.invToken)

        if (item) {
          this.Fkey = item.getElementsByTagName('fkey')[0].textContent
          let status = item.getElementsByTagName('status')[0].textContent
          if (status != '1') {
            this.$root.$toast.warning('Hóa đơn đã được Điều chỉnh/Thay thế/Hủy. Bạn không thể hủy !')
            return false
          }

          let payment = item.getElementsByTagName('payment')[0].textContent
          if(payment == '1'){
            this.$root.$toast.warning('Hóa đơn đã được thanh toán. Không thể hủy !');
            return false;
          }
        }

      } else if(thaotac == 5){ // hủy hóa đơn
        if (this.type == 2 || this.type == 3 || this.type == 4) {
            this.$toast.warning("Hóa đơn điều chỉnh. Bạn không thể hủy !");
            return false;
        }

        if(this.type == 1){
          this.$toast.warning("Hợp đồng đã có hóa đơn bị thay thế. Bạn không thể hủy !");
          return false;
        }

        //Kiểm tra xem khách hàng đã xem hóa đơn hay chưa
        let kq = "";
        let dataSendCus_VTT = {
            cusCode: this.formThongTinTraCuu.maKhachHang,
            fromDate: "",
            toDate: ""
        }
        kq =  await api.listInvByCus_VTT(this.axios, dataSendCus_VTT)
        if(!kq.data) return false
        let xmlDoc = this.parseXmlDoc(kq.data.data)
        let item = Array.from(xmlDoc.getElementsByTagName('Item')).find(elm => elm.getElementsByTagName('invToken')[0].textContent === this.invToken)

        if (item) {
          this.Fkey = item.getElementsByTagName('fkey')[0].textContent
          let status = item.getElementsByTagName('status')[0].textContent
          if (status != '1') {
            this.$root.$toast.warning('Hóa đơn đã được Điều chỉnh/Thay thế/Hủy. Bạn không thể hủy !')
            return false
          }

          let payment = item.getElementsByTagName('payment')[0].textContent
          if(payment == '1'){
            this.$root.$toast.warning('Hóa đơn đã được thanh toán. Không thể hủy !');
            return false;
          }
        }

      }
      console.log("this.Fkey " + this.Fkey);
      return true;
    },

    async GetInvoiceXml(){
        // Fkey = new LapHopDongFacade2().SINH_FKEY(2);
        let resKey = await api.sinhKey(this.axios, 2);
        this.Fkey = resKey.data.data
        let str = ""
        str += "<ReplaceInv>"
        str += "<Inv>"
        str += "<key>" + this.Fkey + "</key>"
        str += "<Invoice>"
        str += "<CusCode>" + obj.CusCode + "</CusCode>"
        str += "<CusName><![CDATA[" + obj.CusName + "]]></CusName>"
        str += "<CusAddress><![CDATA[" + obj.CusAddress + "]]></CusAddress>"
        str += "<CusPhone><![CDATA[" + obj.CusPhone + "]]></CusPhone>"
        str += "<CusTaxCode>" + obj.CusTaxCode + "</CusTaxCode>"
        str += "<PaymentMethod>" + obj.Kind_of_Payment + "</PaymentMethod>"
        str += "<KindOfService>" + obj.KindOfService + "</KindOfService>"
        if(this.them_extra){
          str += "<Extra>" + this.Fkey + "</Extra>"
        }``
        str += "<Products>"
        let Products = this.tableThueBaoConfig.dataSources
        Products.forEach((pro) => {
          if(pro.ProdName){
              str += "<Products>"
              str += "<ProdName><![CDATA[" + pro.ProdName + "]]></ProdName>"
              str += "<ProdUnit>" + pro.ProdUnit + "</ProdUnit>"
              str += "<ProdQuantity>" + pro.ProdQuantity + "</ProdQuantity>"
              str += "<ProdPrice>" + Math.abs(parseFloat(pro.ProdPrice).toFixed(2)) + "</ProdPrice>"
              str += "<Total>" + Math.abs(parseFloat(pro.Total).toFixed(2)) + "</Total>"
              str += "</Products>"
          }
        });
        str += "<Products>"
        str += "<Total>" + Math.abs(parseFloat(this.thongTinHoaDon.congTienDichVu).toFixed(2)) + "</Total>"
        str += "<DiscountAmount></DiscountAmount>"
        str += "<VATRate>" + this.thongTinHoaDon.thueGTGT + "</VATRate>"
        str += "<VATAmount>" + Math.abs(parseFloat(this.thongTinHoaDon.tienThueGTGT).toFixed(2)) + "</VATAmount>"
        str += "<Amount>" + Math.abs(parseFloat(this.thongTinHoaDon.tongCongTienThanhToan).toFixed(2)) + "</Amount>"
        str += "<AmountInWords>" + this.thongTinHoaDon.tongCongTienThanhToan + "</AmountInWords>"
        str += "<EmailDeliver><![CDATA[" + this.thongTinHoaDon.email.toString() + "]]></EmailDeliver>"
        str += "</Invoice>"
        str += "</Inv>"
        str += "</Invoices>"

        return str.toString();

    },

    async GetXml_tt78(thongTinCty){
        //let fkey_new = ""; new LapHopDongFacade2().SINH_FKEY(2);
        // let resKey = await api.sinhKey(this.axios, 2);
        // this.Fkey = resKey.data.data
        let mauSo = this.formThongTinTraCuu.mauSo.find((ele) => ele.LOAIHOADON_ID == this.formThongTinTraCuu.mauSoSelected).TEN_LOAIHD
        let kyHieu = this.formThongTinTraCuu.kyHieu.find((ele) => ele.seri_id == this.formThongTinTraCuu.kyHieuSelected).seri
        let str = ""
        str += "<HDon>"
        str +=  "<DLHDon Id='DuLieuKy'>"
        str +=  "<TTChung>"
        str +=  "<PBan>2.0.0</PBan>"
        str +=  "<THDon>Hóa đơn GTGT</THDon>"
        str +=  "<KHMSHDon>" + mauSo +"</KHMSHDon>"
        str +=  "<KHHDon>" + kyHieu + "</KHHDon>"
        str +=  "<SHDon></SHDon>"
        str +=  "<MSTTCGP></MSTTCGP>"
        str +=  "<NLap>" + moment(new Date()).format("YYYY-MM-DD") + "</NLap>"
        str +=  "<MHSo>MHS1</MHSo>"
        str +=  "<SBKe>sbk111</SBKe>"
        str +=  "<NBKe></NBKe>"
        str +=  "<MSTDVNUNLHDon></MSTDVNUNLHDon>"
        str +=  "<TDVNUNLHDon></TDVNUNLHDon>"
        str +=  "<DCDVNUNLHDon></DCDVNUNLHDon>"
        str +=  "<DVTTe>VND</DVTTe>"
        str +=  "<TGia></TGia>"
        str +=  "<HTTToan>" + this.thongTinHoaDon.hinhThucTT + "</HTTToan>"

        str +=  "</TTChung>"
        str +=      "<NDHDon>"
        str +=        "<NBan>"
        // str +=          "<Ten>VIỄN THÔNG HẢI PHÒNG</Ten>"
        // str +=           "<MST>0200287977</MST>"
        // str +=           "<DChi>Lô C6 Khu Trung tâm hành chính quận Hải An, đường Lê Hồng Phong, Phường Đằng Hải, Quận Hải An, Thành phố Hải Phòng, Việt Nam</DChi>"
        // str +=           "<SDThoai>02253523999</SDThoai>"
        // str +=           "<DCTDTu></DCTDTu>"
        // str +=           "<STKNHang>0031003898888</STKNHang> "
        // str +=           "<TNHang>Ngân hàng VCB chi nhánh Hải Phòng</TNHang> "
        // str +=          "<Fax></Fax>"
        // str +=           "<Website></Website>"
        str +=        thongTinCty
        str +=        "</NBan>"
        str +=        "<NMua>"
        str +=          "<Ten>" + this.thongTinHoaDon.tenKhachHang + "</Ten>"
        str +=          "<MST>" + this.thongTinHoaDon.maSoThueKH + "</MST>"
        str +=          "<DChi>" + this.thongTinHoaDon.diaChiKH + "</DChi>"
        str +=          "<MKHang></MKHang>"
        str +=          "<SDThoai>" + this.thongTinHoaDon.soDienThoai + "</SDThoai>"
        str +=          "<DCTDTu></DCTDTu>"
        str +=          "<HVTNMHang></HVTNMHang>"
        str +=          "<STKNHang></STKNHang>"
        str +=          "<TNHang></TNHang>"
        str +=        "</NMua>"
        str +=        "<DSHHDVu>"
        let Products = this.tableThueBaoConfig.dataSources
        Products.forEach((pro, index) => {
              if(pro.ProdName){
                  str +=          "<HHDVu>"
                  str +=              "<TChat></TChat>"
                  str +=              "<STT>" + (index + 1) + "</STT>"
                  str +=              "<MHHDVu></MHHDVu>"
                  str +=              "<THHDVu>" + pro.ProdName + "</THHDVu>"
                  str +=              "<DVTinh>" + pro.ProdUnit + "</DVTinh>"
                  str +=              "<SLuong>" + pro.ProdQuantity + "</SLuong>"
                  str +=              "<DGia>" + Math.abs(parseFloat(pro.ProdPrice).toFixed(2)) + "</DGia>"
                  str +=              "<TLCKhau></TLCKhau>"
                  str +=              "<STCKhau></STCKhau>"
                  str +=              "<TSuat>" + Math.abs(parseFloat(pro.VATRate).toFixed(2)) + "</TSuat>"
                  str +=              "<ThTien>" + Math.abs(parseFloat(pro.Total).toFixed(2)) + "</ThTien>"
                  str +=              "<TTKhac>"
                  str +=                "<TTin>"
                  str +=                  "<TTruong>Amount</TTruong>"
                  str +=                  "<KDLieu>numeric</KDLieu>"
                  str +=                  "<DLieu>" + Math.abs(parseFloat(pro.VATRate).toFixed(2)) +"</DLieu>"
                  str +=                "</TTin>"
                  str +=                "<TTin>"
                  str +=                  "<TTruong>VATAmount</TTruong>"
                  str +=                  "<KDLieu>numeric</KDLieu>"
                  str +=                  "<DLieu>" + Math.abs(parseFloat(pro.VATAmount).toFixed(2)) +"</DLieu>"
                  str +=                "</TTin>"
                  str +=             "</TTKhac>"
                  str +=          "</HHDVu>"
              }
        });
        str +=        "</DSHHDVu>"
        str +=        "<TToan>"
        str +=          "<THTTLTSuat>"
        str +=              "<LTSuat>"
        str +=                "<TSuat>" + this.tableThueBaoConfig.dataSources[0].VATRate + " %" + "</TSuat>"
        str +=                "<TThue>" + this.thongTinHoaDon.tienThueGTGT + "</TThue>"
        str +=                "<ThTien>" + this.thongTinHoaDon.congTienDichVu + "</ThTien>"
        str +=              "</LTSuat>"
        str +=          "</THTTLTSuat>"
        str +=          "<TgTCThue>" + this.thongTinHoaDon.congTienDichVu + "</TgTCThue>"
        str +=          "<TgTThue>" + this.thongTinHoaDon.tienThueGTGT + "</TgTThue>"
        str +=          "<TTCKTMai></TTCKTMai>"
        str +=          "<TgTTTBSo>" + this.thongTinHoaDon.tongCongTienThanhToan + "</TgTTTBSo>"
        str +=          "<TgTTTBChu>" + this.thongTinHoaDon.soTienVietChu + "</TgTTTBChu>"
        str +=        "</TToan>"
        str +=      "</NDHDon>"
        str +=  "</DLHDon>"
        str += "<image URI=\"" + anhXacThucKs + "\">Signature Valid</image>"
        str += "<Fkey>" + this.Fkey + "</Fkey>"
        str += "</HDon>"
        return str ;
    },

    async GetInvoiceXml_tt78(){
        //let fkey_new = ""; new LapHopDongFacade2().SINH_FKEY(2);
        let resKey = await api.sinhKey(this.axios, 2);
        this.Fkey = resKey.data.data
        let str = ""
        str += "<Invoices>"
        str += "<Inv>"
        str += "<key>" + this.Fkey + "</key>"
        str += "<Invoice>"
        str += "<CusCode>" + this.formThongTinTraCuu.maKhachHang + "</CusCode>"
        str += "<CusName><![CDATA[" + this.thongTinHoaDon.tenKhachHang + "]]></CusName>"
        str += "<CusAddress><![CDATA[" + this.thongTinHoaDon.diaChiKH + "]]></CusAddress>"
        str += "<CusPhone><![CDATA[" + this.thongTinHoaDon.soDienThoai + "]]></CusPhone>"
        str += "<CusTaxCode><![CDATA[" + this.thongTinHoaDon.maSoThueKH + "]]></CusTaxCode>"
        str += "<PaymentMethod>" + this.thongTinHoaDon.hinhThucTT + "</PaymentMethod>"
        str += "<KindOfService></KindOfService>"
        if(this.them_extra){
          str += "<Extra>" + this.Fkey + "</Extra>"
        }
        str += "<Products>"
        let Products = this.tableThueBaoConfig.dataSources
        Products.forEach((pro) => {
              if(pro.ProdName){
                str += "<Product>"
                str += "<ProdName><![CDATA[" + pro.ProdName + "]]></ProdName>"
                str += "<ProdUnit>" + pro.ProdUnit + "</ProdUnit>"
                str += "<ProdQuantity>" + pro.ProdQuantity + "</ProdQuantity>"
                str += "<ProdPrice>" + Math.abs(parseFloat(pro.ProdPrice).toFixed(2)) + "</ProdPrice>"
                str += "<Amount>" + Math.abs(parseFloat(pro.Amount).toFixed(2)) + "</Amount>"
                str += "<Total>" + Math.abs(parseFloat(pro.Total).toFixed(2)) + "</Total>"
                str += "<VATRate>" + Math.abs(parseFloat(pro.VATRate).toFixed(2)) + "</VATRate>"
                str += "<VATAmount>" + Math.abs(parseFloat(pro.VATAmount).toFixed(2)) + "</VATAmount>"
                str += "</Product>"
              }
        });
        str += "</Products>"
        str += "<Total>" + Math.abs(parseFloat(this.thongTinHoaDon.congTienDichVu).toFixed(2)) + "</Total>"
        str += "<VATRate>" + this.thongTinHoaDon.thueGTGT + "</VATRate>"
        str += "<VATAmount>" + Math.abs(parseFloat(this.thongTinHoaDon.tienThueGTGT).toFixed(2)) + "</VATAmount>"
        str += "<Amount>" + Math.abs(parseFloat(this.thongTinHoaDon.tongCongTienThanhToan).toFixed(2)) + "</Amount>"
        str += "<AmountInWords>" + this.thongTinHoaDon.soTienVietChu + "</AmountInWords>"
        str += "<EmailDeliver><![CDATA[]]></EmailDeliver>"
        str += "<GrossValue_NonTax>" + Math.abs(parseFloat(this.thongTinHoaDon.tienKhongChiuThue).toFixed(2))  + "</GrossValue_NonTax>"
        str += "<GrossValue>" + Math.abs(parseFloat(this.thongTinHoaDon.tienThue0).toFixed(2)) + "</GrossValue>"
        str += "<GrossValue0>" + Math.abs(parseFloat(this.thongTinHoaDon.tienChiuThue0).toFixed(2)) + "</GrossValue0>"
        str += "<VatAmount0>0</VatAmount0>"
        str += "<GrossValue5>" + Math.abs(parseFloat(this.thongTinHoaDon.tienChiuThue5).toFixed(2)) + "</GrossValue5>"
        str += "<VatAmount5>" + Math.abs(parseFloat(this.thongTinHoaDon.tienThue5).toFixed(2)) + "</VatAmount5>"
        str += "<GrossValue10>" + Math.abs(parseFloat(this.thongTinHoaDon.tienChiuThue10).toFixed(2)) + "</GrossValue10>"
        str += "<VatAmount10>" + Math.abs(parseFloat(this.thongTinHoaDon.tienThue10).toFixed(2)) + "</VatAmount10>"
        str += "</Invoice>"
        str += "</Inv>"
        str += "</Invoices>"
        console.log(str);
        return str ;


    },

    async GetInvoiceXml_DieuChinh(obj){
        // let fkey_new = ""; //new LapHopDongFacade2().SINH_FKEY(2);
        let resKey = await api.sinhKey(this.axios, 2);
        this.fkey_new = resKey.data.data
        let str = ""
        str += "<ReplaceInv>"
        str += "<key>" + this.fkey_new + "</key>"
        str += "<CusCode>" + obj.CusCode + "</CusCode>"
        str += "<CusName><![CDATA[" + obj.CusName + "]]></CusName>"
        str += "<CusAddress><![CDATA[" + obj.CusAddress + "]]></CusAddress>"
        str += "<CusPhone><![CDATA[" + obj.CusPhone + "]]></CusPhone>"
        str += "<CusTaxCode><![CDATA[" + obj.CusTaxCode + "]]></CusTaxCode>"
        str += "<PaymentMethod>" + obj.Kind_of_Payment + "</PaymentMethod>"
        str += "<KindOfService>" + obj.KindOfService + "</KindOfService>"
        let idieuchinh =  this.formThongTinTraCuu.kieuDieuChinhSelected
        let type = idieuchinh == 1 ? "2" : idieuchinh == 2 ? "3" : "4";
        str += "<Type>" + type + "</Type>"
        if(this.them_extra){
          str += "<Extra>" + this.fkey_new + "</Extra>"
        }
        str += "<Products>"
        obj.Products.forEach((pro) => {
              if(pro.ProdName){
                str += "<Product>"
                str += "<ProdName>" + pro.ProdName + "</ProdName>"
                str += "<ProdUnit>" + pro.ProdUnit + "</ProdUnit>"
                str += "<ProdQuantity>" + pro.ProdQuantity + "</ProdQuantity>"

                if(pro.ProdPrice.toString() != ""){
                  str += "<ProdPrice>" + Math.abs(parseFloat(pro.ProdPrice).toFixed(2)) + "</ProdPrice>"
                }else{
                  str += "<ProdPrice>" + (pro.ProdPrice) + "</ProdPrice>"
                }

                if(pro.Total.toString() != ""){
                  str += "<Total>" + Math.abs(parseFloat(pro.Total).toFixed(2)) + "</Total>"
                }else{
                  str += "<Total>" + pro.Total + "</Total>"
                }

                str += "</Product>"
              }
        });
        str += "</Products>"
        if(obj.Total.toString() != ""){
            str += "<Total>" + Math.abs(parseFloat(obj.Total).toFixed(2)) + "</Total>"
        }else{
          str += "<Total>" + obj.Total + "</Total>"
        }

        str += "<VATRate>" + obj.VAT_Rate + "</VATRate>"

        if(obj.VAT_Amount.toString() != ""){
            str += "<VATAmount>" + Math.abs(parseFloat(obj.VAT_Amount).toFixed(2)) + "</VATAmount>"
        }else{
          str += "<VATAmount>" + obj.VAT_Amount + "</VATAmount>"
        }

        if(obj.Amount.toString() != ""){
            str += "<Amount>" + Math.abs(parseFloat(obj.Amount).toFixed(2)) + "</Amount>"
        }else{
          str += "<Amount>" + obj.Amount + "</Amount>"
        }

        str += "<AmountInWords>" + obj.Amount_words + "</AmountInWords>"
        str += "<PaymentStatus>1</PaymentStatus>"
        str += "</AdjustInv>"

        return str.toString

    },


    async GetInvoiceXml_DieuChinh_tt78(obj){
        //let fkey_new = ""; //new LapHopDongFacade2().SINH_FKEY(2);
        let resKey = await api.sinhKey(this.axios, 2);
        this.fkey_new = resKey.data.data
        let str = ""
        str += "<AdjustInv>"
        str += "<key>" + this.fkey_new + "</key>"
        str += "<CusCode>" + obj.CusCode + "</CusCode>"
        str += "<CusName><![CDATA[" + obj.CusName + "]]></CusName>"
        str += "<CusAddress><![CDATA[" + obj.CusAddress + "]]></CusAddress>"
        str += "<CusPhone><![CDATA[" + obj.CusPhone + "]]></CusPhone>"
        str += "<CusTaxCode><![CDATA[" + this.thongTinHoaDon.maSoThueKH + "]]></CusTaxCode>"
        str += "<PaymentMethod>" + obj.Kind_of_Payment + "</PaymentMethod>"
        str += "<KindOfService>" + (obj.KindOfService ? obj.KindOfService : "") + "</KindOfService>"
        let idieuchinh =  this.formThongTinTraCuu.kieuDieuChinhSelected
        let type = idieuchinh == 1 ? "2" : idieuchinh == 2 ? "3" : "4";
        str += "<Type>" + type + "</Type>"
        if(this.them_extra){
          str += "<Extra>" + this.fkey_new + "</Extra>"
        }
        str += "<Products>"
        obj.Products.forEach((pro) => {
              if(pro.ProdName){
                  str += "<Product>"
                  str += "<ProdName>" + pro.ProdName + "</ProdName>"
                  str += "<ProdUnit>" + pro.ProdUnit + "</ProdUnit>"
                  str += "<ProdQuantity>" + pro.ProdQuantity + "</ProdQuantity>"
                  if(pro.ProdPrice.toString() != ""){
                    str += "<ProdPrice>" + Math.abs(parseFloat(pro.ProdPrice).toFixed(2)) + "</ProdPrice>"
                  }else{
                    str += "<ProdPrice>" + (pro.ProdPrice) + "</ProdPrice>"
                  }
                  if(pro.Amount.toString() != ""){
                    str += "<Amount>" + Math.abs(parseFloat(pro.Amount).toFixed(2)) + "</Amount>"
                  }else{
                    str += "<Amount>" + pro.Amount + "</Amount>"
                  }

                  if(pro.Total.toString() != ""){
                    str += "<Total>" + Math.abs(parseFloat(pro.Total).toFixed(2)) + "</Total>"
                  }else{
                    str += "<Total>" + pro.Total + "</Total>"
                  }

                  if(pro.VATRate.toString() != ""){
                    str += "<VATRate>" + Math.abs(parseFloat(pro.VATRate).toFixed(2)) + "</VATRate>"
                  }else{
                    str += "<VATRate>" + pro.VATRate + "</VATRate>"
                  }

                  if(pro.VATAmount.toString() != ""){
                    str += "<VATAmount>" + Math.abs(parseFloat(pro.VATAmount).toFixed(2)) + "</VATAmount>"
                  }else{
                    str += "<VATAmount>" + pro.Amount + "</VATAmount>"
                  }
                  str += "</Product>"
              }

        });
        str += "</Products>"
        if(obj.Total.toString() != ""){
            str += "<Total>" + Math.abs(parseFloat(obj.Total).toFixed(2)) + "</Total>"
        }else{
          str += "<Total>" + obj.Total + "</Total>"
        }


        str += "<VATRate>" + obj.VAT_Rate + "</VATRate>"


        if(obj.VAT_Amount.toString() != ""){
            str += "<VATAmount>" + Math.abs(parseFloat(obj.VAT_Amount).toFixed(2)) + "</VATAmount>"
        }else{
          str += "<VATAmount>" + obj.VAT_Amount + "</VATAmount>"
        }

        if(obj.Amount.toString() != ""){
            str += "<Amount>" + Math.abs(parseFloat(obj.Amount).toFixed(2)) + "</Amount>"
        }else{
          str += "<Amount>" + obj.Amount + "</Amount>"
        }


        str += "<AmountInWords>" + obj.Amount_words + "</AmountInWords>"
        str += "<PaymentStatus>1</PaymentStatus>"
        str += "<GrossValue_NonTax>" + Math.abs(parseFloat(this.thongTinHoaDon.tienKhongChiuThue).toFixed(2))  + "</GrossValue_NonTax>"
        str += "<GrossValue>" + Math.abs(parseFloat(this.thongTinHoaDon.tienThue0).toFixed(2)) + "</GrossValue>"
        str += "<GrossValue0>" + Math.abs(parseFloat(this.thongTinHoaDon.tienChiuThue0).toFixed(2)) + "</GrossValue0>"
        str += "<VatAmount0>0</VatAmount0>"
        str += "<GrossValue5>" + Math.abs(parseFloat(this.thongTinHoaDon.tienChiuThue5).toFixed(2)) + "</GrossValue5>"
        str += "<VatAmount5>" + Math.abs(parseFloat(this.thongTinHoaDon.tienThue5).toFixed(2)) + "</VatAmount5>"
        str += "<GrossValue10>" + Math.abs(parseFloat(this.thongTinHoaDon.tienChiuThue10).toFixed(2)) + "</GrossValue10>"
        str += "<VatAmount10>" + Math.abs(parseFloat(this.thongTinHoaDon.tienThue10).toFixed(2)) + "</VatAmount10>"
        str += "</AdjustInv>"
        return str.toString();

    },

    async GetInvoiceXml_ThayThe(obj){

        //let fkey_new = ""; //new LapHopDongFacade2().SINH_FKEY(2);
        let resKey = await api.sinhKey(this.axios, 2);
        this.fkey_new = resKey.data.data
        let str = "";
        str += "<ReplaceInv>"
        str += "<CusCode>" + obj.CusCode + "</CusCode>"
        str += "<CusName>" + obj.CusName + "</CusName>"
        str += "<CusAddress>" + obj.CusAddress + "</CusAddress>"
        str += "<CusPhone>" + obj.CusPhone + "</CusPhone>"
        str += "<CusTaxCode>" + this.thongTinHoaDon.maSoThueKH + "</CusTaxCode>"
        str += "<PaymentMethod>" + obj.Kind_of_Payment + "</PaymentMethod>"
        str += "<KindOfService>" + obj.KindOfService + "</KindOfService>"
        if(this.them_extra){
           str += "<Extra>" + this.fkey_new + "</Extra>"
        }
        str += "<Products>"
        obj.Products.forEach((pro) => {
              if(pro.ProdName){
                str += "<Product>"
                str += "<ProdName>" + pro.ProdName + "</ProdName>"
                str += "<ProdUnit>" + pro.ProdUnit + "</ProdUnit>"
                str += "<ProdQuantity>" + pro.ProdQuantity + "</ProdQuantity>"
                if(pro.ProdPrice.toString() != ""){
                  str += "<ProdPrice>" + Math.abs(pro.ProdPrice) + "</ProdPrice>"
                }else{
                  str += "<ProdPrice>" + (pro.ProdPrice) + "</ProdPrice>"
                }
                if(pro.Total.toString() != ""){
                  str += "<Total>" + Math.abs(pro.Total) + "</Total>"
                }else{
                  str += "<Total>" + pro.Total + "</Total>"
                }

                if(pro.Total.toString() != ""){
                  str += "<Amount>" + Math.abs(pro.Amount) + "</Amount>"
                }else{
                  str += "<Amount>" + pro.Amount + "</Amount>"
                }
                str += "</Product>"
              }

        });
        str += "</Products>"
        if(obj.Total.toString != ""){
          str += "<Total>" + Math.abs(obj.Total) + "</Total>"
        }else{
          str += "<Total>" + obj.Total + "</Total>"
        }
        str += "<VATRate>" + obj.VAT_Rate + "</VATRate>"

        if(obj.VAT_Amount.toString != ""){
          str += "<VATAmount>" + Math.abs(obj.VAT_Amount) + "</VATAmount>"
        }else{
          str += "<VATAmount>" + obj.VAT_Amount + "</VATAmount>"
        }

        if(obj.Amount.toString != ""){
          str += "<Amount>" + Math.abs(obj.Amount) + "</Amount>"
        }else{
          str += "<Amount>" + obj.Amount + "</Amount>"
        }

        str += "<GrossValue_NonTax>" + Math.abs(parseFloat(this.thongTinHoaDon.tienKhongChiuThue).toFixed(2)) + "</GrossValue_NonTax>"
        str += "<GrossValue>" + Math.abs(parseFloat(this.thongTinHoaDon.tienChiuThue0).toFixed(2)) + "</GrossValue>"
        str += "<GrossValue0>" + Math.abs(parseFloat(this.thongTinHoaDon.tienThue0).toFixed(2)) + "</GrossValue0>"
        str += "<VatAmount0>0</VatAmount0>"
        str += "<GrossValue5>" + Math.abs(parseFloat(this.thongTinHoaDon.tienChiuThue5).toFixed(2)) + "</GrossValue5>"
        str += "<VatAmount5>" + Math.abs(parseFloat(this.thongTinHoaDon.tienThue5).toFixed(2)) + "</VatAmount5>"
        str += "<GrossValue10>" + Math.abs(parseFloat(this.thongTinHoaDon.tienChiuThue10).toFixed(2)) + "</GrossValue10>"
        str += "<VatAmount10>" + Math.abs(parseFloat(this.thongTinHoaDon.tienChiuThue10).toFixed(2)) + "</VatAmount10>"
        str += "</ReplaceInv>"
        return str;

    },


    async GetInvoiceXml_ThayThe_tt78(obj){
        //let fkey_new = ""; //new LapHopDongFacade2().SINH_FKEY(2);
        console.log(obj)
        let resKey = await api.sinhKey(this.axios, 2);
        this.fkey_new = await resKey.data.data
        let str = "";
        str += "<ReplaceInv>"
        str += "<key>" + this.fkey_new + "</key>"
        str += "<CusCode>" + obj.CusCode + "</CusCode>"
        str += "<CusName>" + obj.CusName + "</CusName>"
        str += "<CusAddress>" + obj.CusAddress + "</CusAddress>"
        str += "<CusPhone>" + obj.CusPhone + "</CusPhone>"
        str += "<CusTaxCode>" + this.thongTinHoaDon.maSoThueKH + "</CusTaxCode>"
        str += "<PaymentMethod>" + obj.Kind_of_Payment + "</PaymentMethod>"
        str += "<KindOfService></KindOfService>"

        if(this.them_extra){
           str += "<Extra>" + this.fkey_new + "</Extra>"
        }
        str += "<Products>"
        console.log(obj.Products)
        obj.Products.forEach((pro) => {
              console.log(pro)
              if(pro.ProdName){
                  str += "<Product>"
                  str += "<ProdName>" + pro.ProdName + "</ProdName>"
                  str += "<ProdUnit>" + pro.ProdUnit + "</ProdUnit>"
                  str += "<ProdQuantity>" + pro.ProdQuantity + "</ProdQuantity>"
                  if(pro.ProdPrice.toString() != ""){
                    str += "<ProdPrice>" + Math.abs(parseFloat(pro.ProdPrice).toFixed(2)) + "</ProdPrice>"
                  }else{
                    str += "<ProdPrice>" + (pro.ProdPrice) + "</ProdPrice>"
                  }
                  if(pro.Total.toString() != ""){
                    str += "<Total>" + Math.abs(parseFloat(pro.Total).toFixed(2)) + "</Total>"
                  }else{
                    str += "<Total>" + pro.Total + "</Total>"
                  }

                  if(pro.Amount.toString() != ""){
                    str += "<Amount>" + Math.abs(parseFloat(pro.Amount).toFixed(2)) + "</Amount>"
                  }else{
                    str += "<Amount>" + pro.Amount + "</Amount>"
                  }

                  if(pro.VATRate.toString() != ""){
                    str += "<VATRate>" + Math.abs(parseFloat(pro.VATRate).toFixed(2)) + "</VATRate>"
                  }else{
                    str += "<VATRate>" + pro.VATRate + "</VATRate>"
                  }

                  if(pro.VATAmount.toString() != ""){
                    str += "<VATAmount>" + Math.abs(parseFloat(pro.VATAmount).toFixed(2)) + "</VATAmount>"
                  }else{
                    str += "<VATAmount>" + pro.VATAmount + "</VATAmount>"
                  }

                  str += "</Product>"
              }
        });
        str += "</Products>"
        if(obj.Total.toString != ""){
          str += "<Total>" + Math.abs(obj.Total) + "</Total>"
        }else{
          str += "<Total>" + obj.Total + "</Total>"
        }
        str += "<VATRate>" + obj.VAT_Rate + "</VATRate>"

        if(obj.VAT_Amount.toString != ""){
          str += "<VATAmount>" + Math.abs(obj.VAT_Amount) + "</VATAmount>"
        }else{
          str += "<VATAmount>" + obj.VAT_Amount + "</VATAmount>"
        }

        if(obj.Amount.toString != ""){
          str += "<Amount>" + Math.abs(obj.Amount) + "</Amount>"
        }else{
          str += "<Amount>" + obj.Amount + "</Amount>"
        }

        str += "<AmountInWords>" + obj.Amount_words + "</AmountInWords>"
        str += "<PaymentStatus>1</PaymentStatus>"
        str += "<GrossValue_NonTax>" + Math.abs(parseFloat(this.thongTinHoaDon.tienKhongChiuThue).toFixed(2)) + "</GrossValue_NonTax>"
        str += "<GrossValue>" + Math.abs(parseFloat(this.thongTinHoaDon.tienChiuThue0).toFixed(2)) + "</GrossValue>"
        str += "<GrossValue0>" + Math.abs(parseFloat(this.thongTinHoaDon.tienThue0).toFixed(2)) + "</GrossValue0>"
        str += "<VatAmount0>0</VatAmount0>"
        str += "<GrossValue5>" + Math.abs(parseFloat(this.thongTinHoaDon.tienChiuThue5).toFixed(2)) + "</GrossValue5>"
        str += "<VatAmount5>" + Math.abs(parseFloat(this.thongTinHoaDon.tienThue5).toFixed(2)) + "</VatAmount5>"
        str += "<GrossValue10>" + Math.abs(parseFloat(this.thongTinHoaDon.tienChiuThue10).toFixed(2)) + "</GrossValue10>"
        str += "<VatAmount10>" + Math.abs(parseFloat(this.thongTinHoaDon.tienChiuThue10).toFixed(2)) + "</VatAmount10>"
        str += "</ReplaceInv>"
        return str;
    },

    async GetInvoiceXml_Cus(){
        this.vma_kh = "";
        if(this.formThongTinTraCuu.maKhachHang != ""){
          this.vma_kh = this.formThongTinTraCuu.maKhachHang.trim()
        }else{
          let res = await api.SINH_MA_KH_HDDT(this.axios, 1);
          // vma_kh = LapHopDongFacade2().SINH_MA_KH_HDDT(1);
          this.vma_kh = res.data.data
          this.formThongTinTraCuu.maKhachHang = this.vma_kh;
        }

        let str = "";
        str += "<Customers>"
        str += "<Customer>"
        str += "<Name><![CDATA[" + this.thongTinHoaDon.tenKhachHang + "]]></Name>"
        str += "<Code>" + this.vma_kh + "</Code>"
        str += "<TaxCode><![CDATA[" + this.thongTinHoaDon.maSoThueKH + "]]></TaxCode>"
        str += "<Address><![CDATA[" + this.thongTinHoaDon.diaChiKH + "]]></Address>"
        str += "<BankAccountName></BankAccountName>"
        str += "<BankName></BankName>"
        str += "<BankNumber></BankNumber>"
        str += "<Email><![CDATA[" + (this.thongTinHoaDon.email ? this.thongTinHoaDon.email : "") + "]]></Email>"
        str += "<Fax></Fax>"
        str += "<Phone><![CDATA[" + this.thongTinHoaDon.soDienThoai + "]]></Phone>"
        str += "<ContactPerson></ContactPerson>"
        str += "<RepresentPerson></RepresentPerson>"
        str += "<CusType>" + this.thongTinHoaDon.loaiKhachHangSelected + "</CusType>"
        str += "</Customer>"
        str += "</Customers>"

        return str;
    },

    GetInvoiceXml_View(thongtin_cty, anhXacThucKs){

        let str = "";
        str += "<Invoice>";
        str += "<Content Id=\'SigningData\'>"
        str += "<Fkey>" + this.Fkey + "</Fkey>"
        str += "<ArisingDate>" + this.tt_nd.ngay_cn + "</ArisingDate>"
        str += "<InvoiceName>Hóa đơn giá trị gia tăng</InvoiceName>"
        str += "<InvoicePattern>" + this.formThongTinTraCuu.mauSoSelected + "</InvoicePattern>"
        str += "<SerialNo>" + this.formThongTinTraCuu.kyHieuSelected + "</SerialNo>"
        str += "<InvoiceNo>" + this.formThongTinTraCuu.soHoaDon + "</InvoiceNo>"

        str += thongtin_cty.toString()

        str += "<CusCode>" + this.vma_kh + "</CusCode>"
        str += "<CusName><![CDATA[" + this.thongTinHoaDon.tenKhachHang + "]]></CusName>"
        str += "<CusAddress><![CDATA[" + this.thongTinHoaDon.diaChiKH + "]]></CusAddress>"
        str += "<CusPhone><![CDATA[" + this.thongTinHoaDon.soDienThoai + "]]></CusPhone>"
        str += "<CusTaxCode>" + this.thongTinHoaDon.maSoThueKH + "</CusTaxCode>"
        str += "<Kind_of_Payment>" + this.thongTinHoaDon.hinhThucTT + "</Kind_of_Payment>"
        str += "<KindOfService></KindOfService>"
        if(this.them_extra){
          str += "<Extra>" + this.fkey_new + "</Extra>"

        }
        str += "<Products>"
        let gvProduct = this.tableThueBaoConfig.dataSources
        gvProduct.forEach((pro) => {
              if( pro.ProdName){
                  str += "<Product>"
                  str += "<ProdName><![CDATA[" + pro.ProdName + "]]></ProdName>"
                  str += "<ProdUnit>" + pro.ProdUnit + "</ProdUnit>"
                  str += "<ProdQuantity>" + pro.ProdQuantity + "</ProdQuantity>"
                  if(pro.ProdPrice.toString() != ""){
                    str += "<ProdPrice>" + Math.abs(pro.ProdPrice) + "</ProdPrice>"
                  }else{
                    str += "<ProdPrice>" + pro.ProdPrice + "</ProdPrice>"
                  }

                  if(pro.Amount.toString() != ""){
                    str += "<Amount>" + Math.abs(pro.Amount) + "</Amount>"
                  }else{
                    str += "<Amount>" + pro.Amount + "</Amount>"
                  }

                  str += "</Product>"
              }

        });
        str += "</Products>"
        str += "<Total>" + this.thongTinHoaDon.congTienDichVu + "</Total>"
        str += "<DiscountAmount></DiscountAmount>"
        str += "<VAT_Rate>" + this.thongTinHoaDon.thueGTGT + "</VAT_Rate>"
        str += "<VAT_Amount>" + this.thongTinHoaDon.tienThueGTGT + "</VAT_Amount>"
        str += "<Amount>" + this.thongTinHoaDon.tongCongTienThanhToan + "</Amount>"
        str += "<AmountInWords>" + this.thongTinHoaDon.soTienVietChu + "</AmountInWords>"
        str += "<Teller>" + this.tt_nd.ma_nv + "</Teller>"
        str += "</Content>"
        str += "<image URI=\"" + anhXacThucKs + "\">Signature Valid</image>"
        str += "</Invoice>"

        return str.toString();
    },

    GetInvoiceXml_DieuChinh_View(obj, thongtin_cty, anhXacThucKs){
        let str = "";
        str += "<Invoice>";
        str += "<Content Id=\'SigningData\'>"
        str += "<Fkey>" + this.Fkey + "</Fkey>"
        str += "<ArisingDate>" + this.tt_nd.ngay_cn + "</ArisingDate>"
        str += "<InvoiceName>Hóa đơn giá trị gia tăng</InvoiceName>"
        str += "<InvoicePattern>" + this.formThongTinTraCuu.mauSoSelected + "</InvoicePattern>"
        str += "<SerialNo>" + this.formThongTinTraCuu.kyHieuSelected + "</SerialNo>"
        str += "<InvoiceNo>" + this.formThongTinTraCuu.soHoaDon + "</InvoiceNo>"

        str += thongtin_cty.toString()

        str += "<CusCode>" + obj.CusCode + "</CusCode>"
        str += "<CusName><![CDATA[" + obj.CusName + "]]></CusName>"
        str += "<CusAddress><![CDATA[" + obj.CusAddress + "]]></CusAddress>"
        str += "<CusPhone><![CDATA[" + obj.CusPhone + "]]></CusPhone>"
        str += "<CusTaxCode><![CDATA[" + obj.CusTaxCode + "]]></CusTaxCode>"
        str += "<Kind_of_Payment>" + obj.Kind_of_Payment + "</Kind_of_Payment>"
        str += "<KindOfService>" + obj.KindOfService + "</KindOfService>"
        let idieuchinh = this.formThongTinTraCuu.kieuDieuChinhSelected
        let type = idieuchinh == 1 ? "2" : idieuchinh == 2 ? "3" : "4";
        str += "<Type>" + type + "</Type>"
        if(this.them_extra){
          str += "<Extra>" + this.fkey_new + "</Extra>"
        }
        str += "<Products>"

        obj.Products.forEach((pro) => {
              if(pro.ProdName){
                  str += "<Product>"
                  str += "<ProdName>" + pro.ProdName + "</ProdName>"
                  str += "<ProdUnit>" + pro.ProdUnit + "</ProdUnit>"
                  str += "<ProdQuantity>" + pro.ProdQuantity + "</ProdQuantity>"
                  if(pro.ProdPrice.toString() != ""){
                    str += "<ProdPrice>" + Math.abs(pro.ProdPrice) + "</ProdPrice>"
                  }else{
                    str += "<ProdPrice>" + (pro.ProdPrice) + "</ProdPrice>"
                  }

                   if(pro.Amount.toString() != ""){
                    str += "<Amount>" + Math.abs(pro.Amount) + "</Amount>"
                  }else{
                    str += "<Amount>" + pro.Amount + "</Amount>"
                  }

                  str += "</Product>"
              }

        });
        str += "</Products>"
        if(obj.Total.toString() != ""){
                 str += "<Total>" + Math.abs(obj.Total) + "</Total>"
        }else{
          str += "<Total>" + obj.Total + "</Total>"
        }

        str += "<VAT_Rate>" + obj.VAT_Rate + "</VAT_Rate>"

        if(obj.VAT_Amount.toString() != ""){
                 str += "<VAT_Amount>" + Math.abs(obj.VAT_Amount) + "</VAT_Amount>"
        }else{
          str += "<VAT_Amount>" + obj.VAT_Amount + "</VAT_Amount>"
        }

        if(obj.Amount.toString() != ""){
                 str += "<Amount>" + Math.abs(obj.Amount) + "</Amount>"
        }else{
          str += "<Amount>" + obj.Amount + "</Amount>"
        }

        str += "<AmountInWords>" + obj.Amount_words + "</AmountInWords>"
        str += "<PaymentStatus>1</PaymentStatus>"
        str += "<Teller>" + this.tt_nd.ma_nv + "</Teller>"
        str += "<isAdjust>Hóa đơn điều chỉnh</isAdjust>"
        str += "</Content>"
        str += "<image URI=\"" + anhXacThucKs + "\">Signature Valid</image>"
        str += "</Invoice>"
        return str.toString();
    },

    GetInvoiceXml_ThayThe_View(obj, thongtin_cty, anhXacThucKs){
        let str = "";
        str += "<Invoice>";
        str += "<Content Id=\'SigningData\'>"
        str += "<Fkey>" + this.Fkey + "</Fkey>"
        str += "<ArisingDate>" + this.tt_nd.ngay_cn + "</ArisingDate>"
        str += "<InvoiceName>Hóa đơn giá trị gia tăng</InvoiceName>"
        str += "<InvoicePattern>" + this.formThongTinTraCuu.mauSoSelected + "</InvoicePattern>"
        str += "<SerialNo>" + this.formThongTinTraCuu.kyHieuSelected + "</SerialNo>"
        str += "<InvoiceNo>" + this.formThongTinTraCuu.soHoaDon + "</InvoiceNo>"
        str += thongtin_cty.toString()
        str += "<CusCode>" + obj.CusCode + "</CusCode>"
        str += "<CusName>" + obj.CusName + "</CusName>"
        str += "<CusAddress>" + obj.CusAddress + "</CusAddress>"
        str += "<CusPhone>" + obj.CusPhone + "</CusPhone>"
        str += "<CusTaxCode>" + obj.CusTaxCode + "</CusTaxCode>"
        str += "<Kind_of_Payment>" + obj.Kind_of_Payment + "</Kind_of_Payment>"
        str += "<KindOfService>" + obj.KindOfService + "</KindOfService>"
        if(this.them_extra){
          str += "<Extra>" + this.fkey_new + "</Extra>"
        }
        str += "<Products>"

        obj.Products.forEach((pro) => {
              if(pro.ProdName){
                  str += "<Product>"
                  str += "<ProdName>" + pro.ProdName + "</ProdName>"
                  str += "<ProdUnit>" + pro.ProdUnit + "</ProdUnit>"
                  str += "<ProdQuantity>" + pro.ProdQuantity + "</ProdQuantity>"
                  if(pro.ProdPrice.toString() != ""){
                    str += "<ProdPrice>" + Math.abs(pro.ProdPrice) + "</ProdPrice>"
                  }else{
                    str += "<ProdPrice>" + Math.abs(pro.ProdPrice) + "</ProdPrice>"
                  }

                   if(pro.Amount.toString() != ""){
                    str += "<Amount>" + Math.abs(pro.Amount) + "</Amount>"
                  }else{
                    str += "<Amount>" + pro.Amount + "</Amount>"
                  }

                  str += "</Product>"
              }
        });
        str += "</Products>"
        if(obj.Total.toString() != ""){
                 str += "<Total>" + Math.abs(obj.Total) + "</Total>"
        }else{
          str += "<Total>" + obj.Total + "</Total>"
        }

        str += "<VAT_Rate>" + obj.VAT_Rate + "</VAT_Rate>"

        if(obj.VAT_Amount.toString() != ""){
                 str += "<VAT_Amount>" + Math.abs(obj.VAT_Amount) + "</VAT_Amount>"
        }else{
          str += "<VAT_Amount>" + obj.VAT_Amount + "</VAT_Amount>"
        }

        if(obj.Amount.toString() != ""){
                 str += "<Amount>" + Math.abs(obj.Amount) + "</Amount>"
        }else{
          str += "<Amount>" + obj.Amount + "</Amount>"
        }

        str += "<AmountInWords>" + obj.Amount_words + "</AmountInWords>"
        str += "<PaymentStatus>1</PaymentStatus>"
        str += "<Teller>" + this.tt_nd.ma_nv + "</Teller>"
        str += "</Content>"
        str += "<image URI=\"" + anhXacThucKs + "\">Signature Valid</image>"
        str += "</Invoice>"

        return str.toString();
    },

    async TaoHtml(thaotac){
        try {
          let anhXacThucKs = "";
          let xml = "";
          let xsltData = "";
          let thongTinCty = "";
          // DataTable dt = bangts.GetDataSql("SELECT a.mau_hd, a.thongtin_ct FROM " + DatabaseConstants.DB3 + ".hddt_mau a where a.hdmau_id = 1");
          let res = "";
          if (res.data.error_code == "BSS-00000000") {
            obj = res.data.data
             xsltData = obj.MAU_HD;
             thongTinCty = obj.THONGTIN_CT;
          }
          switch (thaotac){
              case 1:
              case 6:
                  xml = this.GetInvoiceXml_View(thongTinCty, anhXacThucKs);
                  break;
              case 2 :
                  xml = this.GetInvoiceXml_DieuChinh_View(objHoaDonDienTuEdit, thongTinCty, anhXacThucKs);
                  break;

              case 2 :
                  xml = this.GetInvoiceXml_ThayThe_View(objHoaDonDienTuEdit, thongTinCty, anhXacThucKs);
                  break;
              default :
                    xml = "";
                    break;
          }

          if(xlm == ""){
            return "";
          }

          let  output = "";
        } catch (error) {

        }
    },

    tsbtnNhapMoi_Click() {
        this.disableNhapMoi = true;
        this.Clear();
        let counter = 1;
        const dataSources = []
        for (let i = 1; i < 11; i++) {
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
        this.tableThueBaoConfig.dataSources = [...dataSources]
        // set lại giá trị
        this.thongTinHoaDon.thueGTGT = "";
        this.thongTinHoaDon.congTienDichVu = 0;
        this.thongTinHoaDon.tienThueGTGT = 0;
        this.thongTinHoaDon.tongCongTienThanhToan = 0;
    },

    async capNhat() {
      let vthaotac = 0;
      vthaotac = parseInt(this.formThongTinTraCuu.thaoTacSelected);
      this.vthaotac = parseInt(this.formThongTinTraCuu.thaoTacSelected);
      if (vthaotac == 0) {
        this.$toast.warning("Bạn chưa chọn thao tác!");
        return;
      }
      this.disableGhiLai = true;
      if (vthaotac == 1) {
        // xuất hóa đơn
        if (!(await this.Kiemtra_dulieu(vthaotac))) return false
        if(this.xemtruoc_hddt){
            let temp = {
              "thaotac":this.vthaotac,
              "js_bd_hddt":"",
              "js_bd_hddt_ct":"",
              "ma_nd": this.tt_nd.ma_nd,
              "bdhddt_cha_id": this.bdhddt_cha_id
            }
            let dataSendUpdate = {
                  "ds_para": JSON.stringify(temp),
                  "phanvung_id": this.$root.token.getPhanVungID()
            };


            let response = await this.capNhatHDDTVTT(dataSendUpdate);
            let html = ""
            if (response.data) {
              if (response.ERROR_CODE == 1) {
                const xml = this.parseXmlDoc(response.DS_HDDT_MAU[0].THONGTIN_CT)
                const xsl = this.parseXmlDoc(response.DS_HDDT_MAU[0].MAU_HD.join(''))
                const transformXml = this.transformXml(xml, xsl)
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
                vthaotac: vthaotac,
                viewFromText: true,
                isview: false,
                hoang_test:  1,
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
        }else{
          await this.DieuChinhHoaDon();
        }

        return;
      }

      if (vthaotac == 2) {
        // Điều chỉnh hóa đơn
        if (!(await this.Kiemtra_dulieu(vthaotac))) return false
        if(this.xemtruoc_hddt){
           let temp = {
              "thaotac":this.vthaotac,
              "js_bd_hddt":"",
              "js_bd_hddt_ct":"",
              "ma_nd": this.tt_nd.ma_nd,
              "bdhddt_cha_id": this.bdhddt_cha_id
            }
            let dataSendUpdate = {
                  "ds_para": JSON.stringify(temp),
                  "phanvung_id": this.$root.token.getPhanVungID()
            };
            let html = ""
            let response = await this.capNhatHDDTVTT(dataSendUpdate);
            console.log(response)
            if (response) {
              if (response.ERROR_CODE === 1) {
                const thongTinCty = response.DS_HDDT_MAU[0].THONGTIN_CT
                let temp = await this.GetXml_tt78(thongTinCty)
                // let xml = this.parseXmlDoc(this.GetInvoiceXml_DieuChinh_View(this.objHoaDonDienTuEdit, thongTinCty, anhXacThucKs));
                let xml = this.parseXmlDoc(temp);
                const xsl = this.parseXmlDoc(response.DS_HDDT_MAU[0].MAU_HD.join(''))
                const transformXml = this.transformXml(xml, xsl)
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
                vthaotac: vthaotac,
                viewFromText: true,
                isview: false,
                hoang_test:  1,
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
        }else{
         await this.DieuChinhHoaDon();
        }

        return;
      }

      if (vthaotac == 3) {
        // Xuất hóa đơn thay thế
        if (!(await this.Kiemtra_dulieu(vthaotac))) return false
        if(this.xemtruoc_hddt){
           let temp = {
              "thaotac":this.vthaotac,
              "js_bd_hddt":"",
              "js_bd_hddt_ct":"",
              "ma_nd": this.tt_nd.ma_nd,
              "bdhddt_cha_id": this.bdhddt_cha_id
            }
            let dataSendUpdate = {
                  "ds_para": JSON.stringify(temp),
                  "phanvung_id": this.$root.token.getPhanVungID()
            };
            let html = ""
            let response = await this.capNhatHDDTVTT(dataSendUpdate);
            if (response) {
              if (response.ERROR_CODE === 1) {
                const thongTinCty = response.DS_HDDT_MAU[0].THONGTIN_CT
                console.log(thongTinCty);
                let temp = await this.GetXml_tt78(thongTinCty)
                // let xml = this.parseXmlDoc(this.GetInvoiceXml_ThayThe_View(this.objHoaDonDienTuEdit, thongTinCty, anhXacThucKs));
                let xml = this.parseXmlDoc(temp);
                console.log(xml);
                const xsl = this.parseXmlDoc(response.DS_HDDT_MAU[0].MAU_HD.join(''))
                console.log(xsl);
                const transformXml = this.transformXml(xml, xsl)
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
                vthaotac: vthaotac,
                viewFromText: true,
                isview: false,
                hoang_test:  1,
                vtthoadon: 1
              })
            } else {
              this.$confirm(
              'Không xem trước được hóa đơn. Bạn có muốn tiếp tục thay thế hóa đơn?',
              'Thay thế hóa đơn',
              { confirmButtonText: 'Có', cancelButtonText: 'Không' }
              ).then(async () => {
                await this.ThayTheHoaDon()
                return true
              }).catch((e) => {})
            }
        }else{
         await this.ThayTheHoaDon();
        }
        return;
      }

      if (vthaotac == 4) {
        // API moi dang co ID = 5 Hủy hóa đơn, else if (vthaotac == 4) // Hủy hóa đơn
        if (!(await this.Kiemtra_dulieu(vthaotac))) return false
        if (this.hoi_capnhat) {
          await this.$refs.popupEinvoiceDetailRef.showModal({
            vtoken_show: this.vtoken_show,
            isview: false,
            hoang_test:  1,
            vtthoadon: 1
          })
        } else {
          await this.HuyHoaDon()
        }
        return;
      }

      if (vthaotac == 5) {
        // API moi dang co ID = 5 Hủy hóa đơn, else if (vthaotac == 4) // Hủy hóa đơn
        if (!(await this.Kiemtra_dulieu(vthaotac))) return false
        if (this.hoi_capnhat) {
          await this.$refs.popupEinvoiceDetailRef.showModal({
            vthaotac: vthaotac,
            vtoken_show: this.vtoken_show,
            isview: false,
            hoang_test:  1,
            vtthoadon: 1
          })
        } else {
          await this.HuyThanhToan_HuyHoaDon()
        }
        return;
      }

      if (vthaotac == 6) {
        // API moi dang co ID = 5 Hủy hóa đơn, else if (vthaotac == 4) // Hủy hóa đơn
        if (!(await this.Kiemtra_dulieu(vthaotac))) return false
        if(this.xemtruoc_hddt){
           let temp = {
              "thaotac":this.vthaotac,
              "js_bd_hddt":"",
              "js_bd_hddt_ct":"",
              "ma_nd": this.tt_nd.ma_nd,
              "bdhddt_cha_id": this.bdhddt_cha_id
            }
            let dataSendUpdate = {
                  "ds_para": JSON.stringify(temp),
                  "phanvung_id": this.$root.token.getPhanVungID()
            };
            let html = "";
            let response = await this.capNhatHDDTVTT(dataSendUpdate);
            console.log(response);
            if (response) {
              if (response.ERROR_CODE === 1) {
                const thongTinCty = response.DS_HDDT_MAU[0].THONGTIN_CT
                console.log(thongTinCty);
                // let xml = this.parseXmlDoc(this.GetInvoiceXml_View(thongTinCty, anhXacThucKs));
                let temp = await this.GetXml_tt78(thongTinCty)
                let xml = this.parseXmlDoc(temp);
                console.log(xml);
                const xsl = this.parseXmlDoc(response.DS_HDDT_MAU[0].MAU_HD.join(''))
                console.log(xsl);
                const transformXml = this.transformXml(xml, xsl)
                html = this.convertXmlToString(transformXml)
                console.log(html);
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
                vthaotac: vthaotac,
                viewFromText: true,
                isview: false,
                hoang_test:  1,
                vtthoadon: 1
              })
            } else {
              this.$confirm(
              'Không xem trước được hóa đơn. Bạn có muốn tiếp tục thay thế hóa đơn?',
              'Thay thế hóa đơn',
              { confirmButtonText: 'Có', cancelButtonText: 'Không' }
              ).then(async () => {
                await this.Xuathoadon_GachNo()
                return true
              }).catch((e) => {})
            }
        }else{
         await this.Xuathoadon_GachNo();
        }
        return;
      }

      if (vthaotac == 7) {
        // Thanh toán - Gạch nợ
        if (!(await this.Kiemtra_dulieu(vthaotac))) return false
        await this.GachNo()
        return;
      }
      if (vthaotac == 8) {
        // Thanh toán - Gạch nợ
        if (!(await this.Kiemtra_dulieu(vthaotac))) return false
        await this.Huy_GachNo()
        return;
      }
      if (vthaotac == 9) {
        // Chuyển đổi chứng minh nguồn gốc
        if (!(await this.Kiemtra_dulieu(vthaotac))) return false
        await this.ChuyenDoi_XacMinh()
        return;
      }
      if (vthaotac == 10) {
         if (!(await this.Kiemtra_dulieu(vthaotac))) return false
        await this.ChuyenDoi_LuuTru()
        // Chuyển đổi chứng minh để lưu trữ
        return;
      }
    },

    async capNhatHDDTVTT(dataSend){
      try {
        const response = await api.capNhatHDDTVTT(
          this.axios,
          dataSend
        )
        const data = await response.data
        return data.error_code === 'BSS-00000000' && data.error === '200' ? JSON.parse(data.data.js_returnds) : null
      } catch (e) {
        this.$root.$toast.error('Không thể thực hiện cập nhật')
        return null
      } finally {
        this.loading(false)
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

    async HienThi_ThongTin() {
      try {
        this.Clear();
        this.da_xuathd = false;
        let tongtien = 0;
        let tien = 0;
        let vat = 0;
        let counter = 1;
        this.vma_gd = this.formThongTinTraCuu.maKhachHang.trim();
        let thaotac_id = parseInt(this.formThongTinTraCuu.thaoTacSelected);
        let dataSend = {
          "ma_tt": this.formThongTinTraCuu.maKhachHang
          // "fkey": this.Fkey,
          // "kieu_id": 3
        };

        let res = await api.hienThiThongTin(this.axios, dataSend);
        // let result_ds_kh = JSON.parse(res.data.data.data);
        console.log("result_ds_kh");
        // console.log(result_ds_kh);
        let ds_kh = res.data.data
        console.log(ds_kh);
        if(ds_kh){
          this.hienthitt = ds_kh
          this.vnguoicn = ds_kh.NGUOI_CN ? ds_kh.NGUOI_CN : "";
          console.log('vnguoicn');
          console.log(this.vnguoicn);
          this.da_xuathd = true
          this.Fkey = "";
          let fromDate = "";
          let toDate = "";
          let dataSendCus_VTT = {
            cusCode: this.formThongTinTraCuu.maKhachHang.trim(), 
            fromDate: fromDate,
            toDate: toDate
          }

          let kq = "" ;
          kq =  await api.listInvByCus_VTT(this.axios, dataSendCus_VTT);
          console.log(kq.data);
          if(!kq.data) return false;
          if (kq.data.errorCode !== 0) {
            this.$root.$toast.error("Có lỗi xẩy ra!")
            return false
          }

          const xmlDoc = this.parseXmlDoc(kq.data.data)
          if (xmlDoc.getElementsByTagName('Item').length > 0) {
            // show popup tra cứu
            this.vtt = true
            console.log('popup_TraCuuHDDT');
            console.log(this.formThongTinTraCuu.maKhachHang);
            this.$nextTick(() => {
              this.$refs.popup_TraCuuHDDT.TraCuuTheoMaGD()
              this.$refs.popup_TraCuuHDDT.showModal();
            })
            return true
          }else{
            this.$toast.error("Không tìm thấy thông tin hóa đơn !");
            return;
          }

        }else{
          if( this.formThongTinTraCuu.thaoTacSelected != "1" && this.formThongTinTraCuu.thaoTacSelected != "6" ){
            this.$toast.error("Không tìm thấy thông tin hóa đơn đã phát hành!");
            return false;
          }
        }

        this.disableGhiLai = false;
        this.disableNhapMoi = true;

      } catch (error) {
        this.$toast.error(error);
      }
    },

    async accep_TraCuutHDDT(item){
        console.log(item);
        if(item.vchapnhan){
            this._dieuchinh = item.vdieuchinh;
            this._thaythe = item.vthaythe;
            this.Fkey = item._fkey;
            console.log(this.Fkey)
            this._pattern = item._pattern;
            this._seri = item._seri;
            this._so_hd = item._so_hd;
            this.disableGhiLai = false
            this.vtoken_show = item.vtoken_show
            await this.TraCuu(this._pattern, this._seri, this._so_hd)
        }
    },

    async HienThi_ThongTin_HopDong(){
      this.Clear();
      let i_thoaitra_tmp = 0;
      let tongtien = 0;
      let tien = 0;
      let vat = 0;
      let counter = 0;
      let vma_gd = this.formThongTinTraCuu.maKhachHang.trim();
      let params = {
        ma_gd: vma_gd, // "HPG-LD/00717942"
        loaihd_id: 0,
        tthd_id: 0,
        donvi_id: 0,
        nhanvien_id: 0,
        donvi_dl_id: 0,
        dichvuvt_id: 0,
        tungay_yc: "",
        denngay_yc: "",
        get_service_info: 1
      }
      let dataSend = {
        ds_param: JSON.stringify(params)
      }

      let res = await api.hienThiThongTinHopDong(this.axios, dataSend);
      console.log(res.data)
      if(res.data.error_code == "BSS-00000000"){
        let result = JSON.parse(res.data.data.data);
        if(result.DS_HOPDONG_HDDT && result.DS_HOPDONG_HDDT.length > 0){
            let ds_kh = result.DS_HOPDONG_HDDT[0]
            let vnhanvien_id = 0;
            let vpattern_id = 0;
            let vseri_id = 0;
            let vsoseri = 0;
            this.vfkey = 0;
            this.Fkey = 0;
            this.da_xuathd = false;
            this.vphieutt_id = ds_kh.phieutt_id;
            this.vhdkh_id  = ds_kh.hdkh_id;
            if(ds_kh.trangthai_hddt == "2"){
                this.khoa = 2;
            }else{
              this.khoa = 1;
            }
            this.vnhanvien_id = parseInt(ds_kh.nhanvien_hd_id);
            if(ds_kh.kieu == "1") {
              this.$toast.warning("Hợp đồng đã xuất hóa đơn giấy. Bạn không được thực hiện thao tác HĐĐT");
              return false;
            }
            if(ds_kh.hddt_fkey != ""){
              let params = {
                ma_gd: this.formThongTinTraCuu.maKhachHang , // "HPG-LD/00717942", //vma_gd
                loaihd_id: 0,
                tthd_id: 0,
                donvi_id: 0,
                nhanvien_id: 0,
                donvi_dl_id: 0,
                dichvuvt_id: 0,
                tungay_yc: "",
                denngay_yc: "",
                get_service_info: 0
              }
              let dataSend = {
                ds_param: JSON.stringify(params)
              }
              let res1 = await api.hienThiThongTinHopDong(this.axios, dataSend);
              let result1 = JSON.parse(res1.data.data.data);
              console.log(result1);
              this.bdhddt_cha_id = result1.BDHDDT_CHA_ID
              this.da_xuathd = true;
              let fromDate = "";
              let toDate = "";
              let kq = "" // invoice.listInvByCus_V2(txtMaGD.Text, fromDate, toDate, tt_nd.user_hddt, tt_nd.pass_hddt, "");
              let dataSendCus = {
                cusCode: this.formThongTinTraCuu.maKhachHang,
                fromDate: fromDate,
                toDate: toDate
              }
              let resCus_v2 = await api.listInvByCus_VTT(this.axios, dataSendCus);
              if(!resCus_v2.data) return false
              if(resCus_v2.data.errorCode != 0) return false
              const xmlDoc = this.parseXmlDoc(resCus_v2.data.data)
              if (xmlDoc.getElementsByTagName('Item').length > 0) {
                // frmTC_HDDT frm = new frmTC_HDDT(this.hoaDonFilter.txtMaGD);
                // frm.ShowDialog();
                // show popup và hiển thị thông tin ở đây
                return true
              }



            }

            //this.formThongTinTraCuu.maKhachHang = ds_kh.ma_kh
            if(result.DS_HOPDONG_TT.length > 0){
                let ds_hdtt  = result.DS_HOPDONG_TT[0];
                this.vskt = ds_hdtt.stk
                this.thongTinHoaDon.maSoThue = ds_hdtt.mst
                this.thongTinHoaDon.diaChiKH = ds_hdtt.diachi_ct
                this.thongTinHoaDon.tenKhachHang = ds_hdtt.ten_tt
            }

            this.thongTinHoaDon.hinhThucTT = "Tiền mặt/Chuyển khoản";
            let db = false;

            let ds_dbkh = result.DS_DBKH[0];
            if(result.DS_DBKH.length > 0){
              db = true;
            }

            this.vdiachi_tt = "";
            this.vdiachi_tt = ds_kh.diachi_kh ? ds_kh.diachi_kh : ((db) ? ds_dbkh.diachi_kh : "");
            this.thongTinHoaDon.email = ds_kh.email_tt ? ds_kh.email_tt : ((db) ? ds_dbkh.email_tt : "");
            this.vfax = ds_kh.so_fax ? ds_kh.so_fax : ((db) ? ds_dbkh.so_fax : "");
            this.vnguoi_dd = ds_kh.nguoi_dd ? ds_kh.nguoi_dd : ((db) ? ds_dbkh.nguoi_dd : "");
            this.vloai_kh = ds_kh.loaikh_id ? ds_kh.loaikh_id : ((db) ? ds_dbkh.loaikh_id : "");
            this.vsodt_kh = ds_kh.so_dt ? ds_kh.so_dt : ((db) ? ds_dbkh.so_dt : "");

            this.vnguoi_dd = ds_kh.nguoi_dd ? ds_kh.nguoi_dd : ((db) ? ds_dbkh.nguoi_dd : "");

            if(result.DS_HDTB.length > 0){
                let ds_tb_hd = result.DS_HDTB[0];
                this.thongTinHoaDon.soDienThoai = ds_tb_hd.ma_tb;
            }else{
                this.thongTinHoaDon.soDienThoai = "";
            }

            if(ds_kh.loaihd_id == "1"){
              this.vloaihd_id  = "0" + ds_kh.loaihd_id.toString();
            }else if(ds_kh.loaihd_id == "2"){
              this.vloaihd_id  = ds_kh.loaihd_id.toString();
            }else {
              this.vloaihd_id = "00";
            }
        }else{
            this.$toast.warning("Không tìm thấy thông tin hợp đồng !");
            this.disableGhiLai = true;
            this.Clear();
            return false;
        }
        this.ds.list = []
        let ds_tb = result.DS_HOPDONG_TB_HDDT;
        this.DS_HOPDONG_TB_HDDT = result.DS_HOPDONG_TB_HDDT
        console.log(ds_tb);
        this.tien = 0;
        this.vat = 0;
        this.tongtien = 0;
        for(let i = 0; i < ds_tb.length; i++){
            let itemPro = {
              STT: "",
              Code: "",
              Total: "",
              ProdName: "",
              ProdUnit: "",
              ProdQuantity: "",
              ProdPrice: "",
              Discount: '',
              DiscountAmount: "",
              VATRate: "",
              VATAmount: '',
              Amount: ""
            }
            itemPro.STT = i;
            itemPro.Amount = ds_tb[i].AMOUNT
            itemPro.ProdName = ds_tb[i].PRODNAME
            itemPro.ProdUnit = ds_tb[i].PRODUNIT
            itemPro.ProdQuantity = ds_tb[i].PRODQUANTITY
            itemPro.ProdPrice = ds_tb[i].PRODPRICE
            this.tien += parseInt(ds_tb[i].TIEN)
            this.vat += parseInt(ds_tb[i].VAT)
            console.log(ds_tb[i].VAT);
            this.tongtien += parseInt(ds_tb[i].TONGTIEN)
            this.ds.list.push(itemPro)
        }
        this.thongTinHoaDon.congTienDichVu = this.tien;
        this.thongTinHoaDon.tienThueGTGT = this.vat;
        this.thongTinHoaDon.tongCongTienThanhToan = this.tongtien;
        let resDocTien = await api.docTienChu(this.axios, {money: this.tongtien});
        this.thongTinHoaDon.soTienVietChu  = resDocTien.data.data;
        this.disableGhiLai = false;
        this.disableNhapMoi = true;
      }

    },

  },
};
</script>

<style>
.disabled {
  /* pointer-events: none; */
  cursor: no-drop;
  opacity: 0.7;
}

.disabled-color {
  color: gray !important;
}
</style>
