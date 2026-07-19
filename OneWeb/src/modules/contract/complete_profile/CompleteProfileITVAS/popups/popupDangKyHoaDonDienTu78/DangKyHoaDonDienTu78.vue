<style scoped>
.list-actions-top .list > li > div.button {
  display: block;
  padding: 2px 15px;
  color: #0176ff;
  border-left: 1px solid #bae7ff;
  text-decoration: none;
  font-weight: 600;
  cursor: pointer;
}
</style>

<template src="./DangKyHoaDonDienTu78.html"> </template>

<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment'
import api from './PopUpApi'
import CreateIssue from '../popupCreateIssue/CreateIssue.vue'
import ChonMauHDDT from './ChonMauHDDT.vue'
import Vue from 'vue'
import commonFn from '@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions'
import { LOAI_HOPDONG, DICHVU_VIENTHONG, TRANGTHAI_HOPDONG, LOAIHINH_THUEBAO, TRANGTHAI_DONGBO } from '@/constants'

export default {
  components: {
    moment,
    DatePicker,
    api,
    CreateIssue,
    ChonMauHDDT
  },
  name: 'DangKyHoaDonDienTu78',
  data() {
    return {
      Loading: false,
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      showTimePanel: false,
      tabIndex: 0,
      state: {
        popupTitle: 'Hóa đơn điện tử 32/78'
      },
      tt_nd: {
        nhanvien_id: this.$root.token.getNhanVienID() * 1,
        nguoidung_id: this.$root.token.getNguoiDungID() * 1,
        phanvung_id: this.$root.token.getPhanVungID() * 1,
        donvi_id: this.$root.token.getDonViID() * 1,
        may_cn: this.$root.token.getMachine(),
        ip_cn: this.$root.token.getIP(),
        ngay_cn: moment(new Date()).format('DD/MM/YYYY hh:mm A'),
        ngay_bg: moment(new Date().setSeconds(new Date().getSeconds() + 30)).format('DD/MM/YYYY hh:mm A'),
        ten_dv: '',
        nguoi_cn: this.$root.token.getUserName()
      },
      grcMauDaDK: {
        headers: [
          { fieldName: 'identification', headerText: 'ID Mẫu', allowFiltering: true },
          { fieldName: 'templatename', headerText: 'Tên mẫu', allowFiltering: true },
          {
            fieldName: '',
            headerText: '',
            allowFiltering: false,
            allowSorting: false,
            textAlign: 'center',
            width: 90,
            template: function() {
              return {
                template: Vue.component('btnXemMauHeThongTemplate', {
                  template: `<button class="btn btn-second" style="border-color: rgb(1, 118, 255);" @click="onXemMauHeThong">Xem mẫu</button>`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onXemMauHeThong(e) {
                      this.parent.btnXemMauHeThong_ButtonClick(this.data)
                    }
                  }
                })
              }
            }
          },
          {
            fieldName: '',
            headerText: '',
            allowFiltering: false,
            allowSorting: false,
            textAlign: 'center',
            width: 90,
            template: function() {
              return {
                template: Vue.component('btnXoaMauDaDKTemplate', {
                  template: `<button class="btn btn-second" style="border-color: red" @click="onXoaMauDaDK">Xóa mẫu</button>`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onXoaMauDaDK(e) {
                      this.parent.btnXoaMauDaDK_ButtonClick(this.data)
                    }
                  }
                })
              }
            }
          },
          {
            fieldName: '',
            headerText: '',
            allowFiltering: false,
            allowSorting: false,
            textAlign: 'center',
            width: 120,
            template: function() {
              return {
                template: Vue.component('btnDongBoDHSXTemplate', {
                  template: `<button class="btn btn-second" style="border-color: rgb(1, 118, 255);" @click="onDongBoDHSX">Đồng bộ ĐHSX</button>`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onDongBoDHSX(e) {
                      this.parent.btnDongBoDHSX_ButtonClick(this.data)
                    }
                  }
                })
              }
            }
          }
        ],
        list: []
      },
      grcTacVu: {
        list: [],
        headers: [
          { fieldName: 'tacvu', headerText: 'Tác vụ', allowFiltering: true },
          { fieldName: 'trangthai', headerText: 'Trạng thái', allowFiltering: true },
          //{fieldName: 'thaotac', headerText: 'Thao tác', allowFiltering: true},
          {
            fieldName: '',
            headerText: 'Thao tác',
            allowFiltering: false,
            allowSorting: false,
            visible: true,
            textAlign: 'center',
            width: 100,
            template: function() {
              return {
                template: Vue.component('ThucHienTemplate', {
                  template: `<button class="btn btn-second" style="border-color: rgb(1, 118, 255);" @click="onThucHien">Thực hiện</button>`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onThucHien(e) {
                      this.parent.btnThucHien_ButtonClick(this.data)
                    }
                  }
                })
              }
            }
          }
        ]
      },
      grcMauDangKyTrenHD: {
        headers: [
          { fieldName: 'templatename', headerText: 'Tên mẫu', allowFiltering: true },
          { fieldName: 'trangthai_dk', headerText: 'Trạng thái', allowFiltering: true },
          {
            fieldName: '',
            headerText: '',
            allowHtml: false,
            allowFiltering: false,
            allowSorting: false,
            textAlign: 'center',
            width: 90,
            template: function() {
              return {
                template: Vue.component('XemMauDaDKTemplate', {
                  template: `<button class="btn btn-second" style="border-color: rgb(1, 118, 255);" @click="onXemMauDaDK">Xem mẫu</button>`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onXemMauDaDK(e) {
                      this.parent.btnXemMauDaDK_ButtonClick(this.data)
                    }
                  }
                })
              }
            }
          },
          {
            fieldName: '',
            headerText: '',
            allowFiltering: false,
            allowSorting: false,
            textAlign: 'center',
            width: 90,
            template: function() {
              return {
                template: Vue.component('btnDangKyMauTemplate', {
                  template: `<button class="btn btn-second" style="border-color: rgb(1, 118, 255);" @click="onDangKyMau">Đăng ký</button>`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onDangKyMau(e) {
                      this.parent.btnDangKyMau_ButtonClick(this.data)
                    }
                  }
                })
              }
            }
          },
          {
            fieldName: '',
            headerText: '',
            allowFiltering: false,
            allowSorting: false,
            textAlign: 'center',
            width: 90,
            template: function() {
              return {
                template: Vue.component('btnXoaMauHDTemplate', {
                  template: `<button class="btn btn-second" style="border-color: red;" @click="onXoaMauHD">Xóa mẫu</button>`,
                  data() {
                    return { data: {} }
                  },
                  computed: {
                    parent() {
                      return this.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onXoaMauHD(e) {
                      this.parent.btnXoaMauHD_ButtonClick(this.data)
                    }
                  }
                })
              }
            }
          }
        ],
        list: []
      },
      cboToKen: {
        list: [],
        value: 0
      },
      controls: {},
      display_button: {
        themmau: true,
        chuyenphuctap: true,
        chuyendongian: true,
        guiycit360: true,
        dongbodulieu: true
      }
    }
  },
  created: async function() {},
  mounted: function() {},
  watch: {},
  methods: {
    dialogClose() {},
    openDialog(data) {
      if (!data) {
        return
      }

      this.state.kieuld_id = data.kieuld_id
      this.state.hdtb_id = data.hdtb_id
      this.state.phieu_id = data.phieu_id
      this.state.thuebao_id = data.thuebao_id
      this.state.ma_gd = data.ma_gd
      this.state.loaitb_id = data.loaitb_id
      this.state.ma_tb = data.ma_tb

      this.state.popupTitle = 'Đăng ký dịch vụ hóa đơn điện tử'
      if (this.state.loaitb_id == 2116) {
        this.state.popupTitle = 'Hóa đơn điện tử - Máy tính tiền'
      }
      if (this.state.loaitb_id == 319) {
        this.state.popupTitle = 'Dịch vụ vé điện tử'
      }
      if (this.state.loaitb_id == 175) {
        this.state.popupTitle = 'Biên lai điện tử (VNPT ERECEIPT)'
      }
      if (this.state.loaitb_id == 319 || this.state.loaitb_id == 175) {
        //this.state.popupTitle = 'Dịch vụ vé điện tử'
        this.display_button.chuyenphuctap = false
        this.display_button.chuyendongian = false
        this.display_button.guiycit360 = false
      }

      this.$refs.dialogDangKyHoaDonDienTu78.show()
      this.pageLoad()
    },
    async ResetgrcTacVu() {
      this.grcTacVu.list = []
      this.grcTacVu.list.push({ tacvu: 'Đăng ký khách hàng', thaotac: 'Thực thi', trangthai: this.controls.txtIdKH ? 'Đã tạo ID khách hàng' : 'Pending' })
      this.grcTacVu.list.push({ tacvu: 'Đăng ký demo', thaotac: 'Thực thi', trangthai: this.controls.status == 3 ? 'Đã tạo hệ thống Demo' : this.controls.status == 6 ? 'Đã tạo hệ thống Demo' : 'Pending' })
      this.grcTacVu.list.push({ tacvu: 'Đăng ký hệ thống thật', thaotac: 'Thực thi', trangthai: this.controls.status == 6 ? 'Đã tạo hệ thống thật' : 'Pending' })
    },
    async pageLoad() {
      this.tt_nd.may_cn = await this.$root.token.getMachine()
      this.tt_nd.ip_cn = await this.$root.token.getIP()

      await this.LoadMauDaDangKy()
      await this.LoadThongTinDaKyHD()

      //console.log('controls')
      //console.log(this.controls)

      this.grcTacVu.list = []
      this.grcTacVu.list.push({ tacvu: 'Đăng ký khách hàng', thaotac: 'Thực thi', trangthai: this.controls.txtIdKH ? 'Đã tạo ID khách hàng' : 'Pending' })
      this.grcTacVu.list.push({ tacvu: 'Đăng ký demo', thaotac: 'Thực thi', trangthai: this.controls.status == 3 ? 'Đã tạo hệ thống Demo' : this.controls.status == 6 ? 'Đã tạo hệ thống Demo' : 'Pending' })
      this.grcTacVu.list.push({ tacvu: 'Đăng ký hệ thống thật', thaotac: 'Thực thi', trangthai: this.controls.status == 6 ? 'Đã tạo hệ thống thật' : 'Pending' })

      this.cboToKen.list = [
        { id: 1, text: 'Ký token' },
        { id: 2, text: 'Ký HSM' }
      ]
      // this.cboToKen.value = this.cboToKen.list[0].id
    },
    async LoadMauDaDangKy() {
      let dt = this.GetData(await api.sp_loadmaudadangky_hdtb_cntt(this.axios, { vhdtb_id: this.state.hdtb_id }))
      if (!dt || dt.length <= 0) return
      this.state.cus_id = dt[0]['id_khachhang']
      if (!this.state.cus_id || this.state.cus_id === '') {
        return
      }

      this.state.taxcode = dt[0]['mst']
      await this.LayDanhSachMauDaDangKy(this.state.cus_id, this.state.taxcode)
    },
    async LayDanhSachMauDaDangKy(cus_id, taxcode) {
      try {
        console.log('LayDanhSachMauDaDangKy')
        this.loading(true)
        let req = {
          cus_id: cus_id,
          limit: 100,
          offset: 0,
          taxcode: taxcode
        }
        let ds = null
        if(this.state.loaitb_id == 175)
        {
          ds =  this.GetData(await api.LayDSMauHoaDonDaDangKyBienLaiDienTu(this.axios, req))
        }
        else{
          ds = this.GetData(await api.LayDSMauHoaDonDaDangKy(this.axios, req))
        }
        console.log(ds)
        this.grcMauDaDK.list = []
        if (ds) {
          console.log(this.grcMauDaDK.list)
          this.grcMauDaDK.list = this.LowerCasePropertyList(ds.datas)
        }
      } catch (ex) {
        console.error(ex)
        //commonFn.showException(this, ex);
      } finally {
        this.loading(false)
      }
    },
    async LoadThongTinDaKyHD() {
      let soluong_dk = this.GetData(await api.lay_soluong_hddt_dk(this.axios, { vhdtb_id: this.state.hdtb_id }))
      this.controls.txtSoLuongMau = soluong_dk
      let lst = this.GetData(await api.lay_thongtin_tao_hddt(this.axios, { vhdtb_id: this.state.hdtb_id }))
      let dsMauHDDK = this.GetData(await api.layds_mau_hddt_dangky(this.axios, { vhdtb_id: this.state.hdtb_id }))
      this.grcMauDangKyTrenHD.list = this.LowerCasePropertyList(dsMauHDDK)

      console.log('dsMauHDDK')
      console.log(dsMauHDDK)
      console.log(this.grcMauDaDK.list)
      console.log('this.cboToKen.value', this.cboToKen.value, lst[0].system_type)

      if (lst && lst.length > 0) {
        this.controls.txtNguoiDaiDien = lst[0].represent_person
        this.controls.txtTenCty = lst[0].company_name
        this.controls.txtDiaChi = lst[0].diachi_tb
        this.controls.txtDienThoai = lst[0].tel
        this.controls.txtEmail = lst[0].email
        this.controls.txtMST = lst[0].taxcode
        this.controls.txtTenTat = lst[0].short_name
        this.controls.txtTinhThanh = lst[0].org
        this.cboToKen.value = lst[0].system_type
        this.controls.txtIdKH = lst[0].cus_id
        this.controls.status = lst[0].status
      }
    },
    async btnThemMau_ItemClick() {
      let data = {
        _dsHDTB_CT: [],
        soluong: this.controls.txtSoLuongMau == '' ? 0 : this.controls.txtSoLuongMau
      }
      this.$refs.dialogChonMauHDDT.openDialog(data)
    },
    async onChapNhan(data) {
      //console.log('onChapNhan', data)
      try {
        this.loading(true)
        if (data && data.isChapNhan == true) {
          let ketqua_hdtb_ct2 = this.GetData(await api.them_hd_thuebao_ct_common(this.axios, { vhdtb_id: this.state.hdtb_id, vds_hdtb_ct: data._dsHDTB_CT }))
          if (ketqua_hdtb_ct2 == 'ok') {
            let dsMauHDDK = this.GetData(await api.layds_mau_hddt_dangky(this.axios, { vhdtb_id: this.state.hdtb_id }))
            this.grcMauDangKyTrenHD.list = this.LowerCasePropertyList(dsMauHDDK)
          }
        }
      } catch (ex) {
        commonFn.showException(this, ex)
      } finally {
        this.loading(false)
      }
    },
    async btnDangKyMau_ButtonClick(e) {
      this.loading(true)
      var row = e
      try {
        //console.log(row)
        let req = {
          cus_id: row.cus_id,
          inv_id: row.identification,
          taxcode: row.taxcode
        }
        row.trangthai = 'Đang xử lý'
        console.log(this.state.loaitb_id)
        if ((this.state.loaitb_id == 175)) {
          console.log('Vào btnDangKyMau_ButtonClick của api DangKyMauHoaDonBienLaiDienTu')
          this.GetData(await api.DangKyMauHoaDonBienLaiDienTu(this.axios, req))
        } else {
          console.log('Vào btnDangKyMau_ButtonClick của các dịch vụ khác')
          this.GetData(await api.DangKyMauHoaDon(this.axios, req))
        }
        row.trangthai = 'Thành công'
        await this.LoadMauDaDangKy()
        await this.LoadThongTinDaKyHD()
      } catch (ex) {
        row.trangthai = 'lỗi'
        commonFn.showException(this, ex)
      } finally {
        this.loading(false)
      }
    },
    async btnXoaMauHD_ButtonClick(e) {
      this.$bvModal
        .msgBoxConfirm('Bạn có chắc là sẽ xóa?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Thoát',
          centered: true
        })
        .then(async (v) => {
          if (!v) {
            return
          }

          try {
            this.loading(true)
            let row = e
            //console.log(row)
            let req = {
              cus_id: this.state.cus_id,
              inv_id: row.identification,
              taxcode: row.taxcode
            }
            row.trangthai = 'Đang xử lý'
            if ((this.state.loaitb_id = 175)) {
              console.log('Nhảy vào btnXoaMauHD_ButtonClick của api XoaMauHoaDonBienLaiDienTu')
              this.GetData(await api.XoaMauHoaDonBienLaiDienTu(this.axios, req))
            } else this.GetData(await api.XoaMauHoaDon(this.axios, req))
            row.trangthai = 'Thành công'
            await this.LoadMauDaDangKy()
            await this.LoadThongTinDaKyHD()
          } catch (ex) {
            commonFn.showException(this, ex)
          }
          this.loading(false)
        })
    },
    async btnXoaMauDaDK_ButtonClick(e) {
      this.$bvModal
        .msgBoxConfirm('Bạn có chắc là sẽ xóa?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Thoát',
          centered: true
        })
        .then(async (v) => {
          if (!v) {
            return
          }

          try {
            this.loading(true)
            let row = e
            //console.log(row)
            let req = {
              cus_id: this.state.cus_id,
              inv_id: row.identification,
              taxcode: this.state.taxcode
            }
            this.GetData(await api.XoaMauHoaDon(this.axios, req))
            await this.LoadMauDaDangKy()
            await this.LoadThongTinDaKyHD()
          } catch (ex) {
            commonFn.showException(this, ex)
          } finally {
            this.loading(false)
          }
        })
    },
    async btnGuiYCIT360_ItemClick() {
      var data = {
        hdtb_id: this.state.hdtb_id,
        phieu_id: this.state.phieu_id,
        loai: 1,
        vip_giamsat: false
      }
      this.$refs.dialogCreateIssue.openDialog(data)
    },
    async btnChuyenPhucTac_ItemClick() {
      try {
        this.loading(true)
        await api.sp_update_hd_thuebao_kieu_ld(this.axios, { vhdtb_id: this.state.hdtb_id, vkieuld_id: 13272 })
        this.$toast.success('Chuyển đổi sang phức tạp thành công!')
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      this.loading(false)
    },
    async btnChuyenDonGian_ItemClick() {
      try {
        this.loading(true)
        await api.sp_update_hd_thuebao_kieu_ld(this.axios, { vhdtb_id: this.state.hdtb_id, vkieuld_id: 13273 })
        this.$toast.success('Chuyển đổi sang đơn giản thành công!')
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      this.loading(false)
    },
    async btnDongBoDHSX_ButtonClick(e) {
      try {
        this.loading(true)
        let row = e
        //console.log(row)
        let req = {
          vhdtb_id: this.state.hdtb_id,
          vds_hdtb_ct: [
            {
              Identification: row.identification,
              Templatename: row.templatename,
              Invcateid: row.invcateid,
              Invcatename: row.invcatename,
              Pattern: row.pattern,
              XmlFile: row.xmlfile,
              XsltFile: row.xsltfile,
              SchemaFile: row.schemafile
            }
          ]
        }
        let kqxl = this.GetData(await api.capnhat_hddt_da_dk(this.axios, req))
        if (kqxl == 'ok') {
          this.$toast.success('Đồng bộ thành công!')
          this.loading(false)
          return
        }
        this.$toast.success('Đồng bộ lỗi: ' + kqxl)
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      this.loading(false)
    },
    async btnDongBoDuLieu_ItemClick() {
      if (!this.controls.txtMST || this.controls.txtMST === '') {
        this.$toast.warning('Bạn chưa nhập Mã số thuế')
        this.$refs.txtMST.focus()
        return
      }

      try {
        const check_use_api_dynamic = await api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
          p_nghiepvu: 'DONGBODULIEU_DANGKYHOADONDIENTU78',
          p_ds_para: '{"LOAITB_ID":' + this.state.loaitb_id + '}'
        })
        const result_check = check_use_api_dynamic.data.data[0].ketqua
        console.log(check_use_api_dynamic.data.data[0].ketqua)
        if (result_check == 'ok') {
          const result_call_dynamic = await api.cntt_dynamic_api(this.axios, { hdtb_id: this.state.hdtb_id, vdem: 0, vtype: 'DONGBODULIEU_DANGKYHOADONDIENTU78' })
          console.log(result_call_dynamic)
          const get_status = result_call_dynamic.data.data.status
          if (get_status == '00') {
            this.$toast.success(result_call_dynamic.data.data.message)
            await this.LoadThongTinDaKyHD()
            await this.ResetgrcTacVu()
          } else {
            this.$toast.error(result_call_dynamic.data.data.message)
          }
          this.loading(false)
          return
        } else {
          this.loading(true)

          let kq = this.GetData(await api.LayThongTinKHHDDT(this.axios, { params: { taxcode: this.controls.txtMST } }))
          this.loading(false)
          // console.log(kq)
          if (kq && kq.success === false) {
            throw kq.returnvalue
          }
          //kq.versions = kq.Versions
          kq.versions = kq.Versions.filter((item) => item.type.includes('TT78'))
          //console.log(kq)
          //debugger

          if (!kq || !kq.versions || kq.versions.length <= 0) {
            this.$toast.warning('Không lấy được thông tin khách hàng theo MST: ' + this.controls.txtMST)
            this.loading(false)
            return
          }

          this.state.versions = kq.versions
          //console.log(this.state.versions)
          // debugger
          if (kq.versions.length > 1) {
            this.$bvModal.show('modal-scoped')
          } else {
            this.loading(true)
            let kq2 = this.GetData(await api.capnhat_thongtin_kh_hddt(this.axios, { vhdtb_id: this.state.hdtb_id, vkhachhang_id: this.state.versions[0].cus_id }))
            if (kq2 == 'ok') {
              this.state.cus_id = this.state.versions[0].cus_id
              this.$toast.success('Đồng bộ thành công!')
              //Thêm đoạn này theo yêu cầu của anh Dương
              this.state.init_type = this.state.versions[0].init_type
              this.state.invoices_services = kq.invoices_services
              if (this.state.init_type === 6 && this.state.invoices_services.length == 0 && this.state.loaitb_id == 122) {
                let dt_hddt = this.GetData_HDDT(await api.lay_thongso_dkmua_them_hd(this.axios, { hdtb_id: this.state.hdtb_id }))
                dt_hddt = commonFn.LowerCasePropertyList(dt_hddt)
                let dkt = {}
                dkt.cus_id = dt_hddt[0]['cus_id']
                dkt.taxcode = dt_hddt[0]['taxcode']
                dkt.number_invoices = dt_hddt[0]['number_invoices'] * 1
                let kq = this.GetData_HDDT(await api.MuaThemHoaDon(this.axios, dkt))
                if (!kq) {
                  let res = this.GetData_HDDT(await api.update_hd_thuebao(this.axios, { hdtb_id: this.state.hdtb_id, status: 6 }))
                  if (res) {
                    this.$toast.success('Mua thêm hóa đơn thành công')
                  } else {
                    throw 'Lỗi cập nhật HĐ thuê bao!'
                  }
                } else {
                  throw 'Lỗi đăng ký! ' + kq
                }
              }
              const resStatus = this.GetData(await api.fn_get_status_hdtb(this.axios, this.state.hdtb_id))
              console.log(resStatus)
              console.log('HDTB_ID: ' + this.state.hdtb_id)
              if (resStatus != 6) {
                //End
                if ((this.state.versions[0].isProcessed == 4 || (this.state.versions[0].isProcessed == 3 && this.state.versions[0].status == 'Đã khởi tạo Domain thật' )) && this.state.loaitb_id == 122) {
                  await api.cap_nhat_status_hdtb(this.axios, { vhdtb_id: this.state.hdtb_id, vstatus: 6 })
                } else {
                  if (this.state.versions[0].isProcessed == 1 || this.state.versions[0].isProcessed == 2 || this.state.versions[0].isProcessed == 4 || this.state.versions[0].isProcessed == 3) {
                    await api.cap_nhat_status_hdtb(this.axios, { vhdtb_id: this.state.hdtb_id, vstatus: 3 })
                  } else await api.cap_nhat_status_hdtb(this.axios, { vhdtb_id: this.state.hdtb_id, vstatus: 1 })
                }
              }
              await this.LoadThongTinDaKyHD()
              await this.ResetgrcTacVu()
            } else {
              this.$toast.error(kq2)
            }
          }
        }
      } catch (ex) {
        commonFn.showException(this, ex)
      } finally {
        this.loading(false)
      }
    },
    async onDongBoDuLieu_Click(e) {
      //console.log(e)
      this.$bvModal.hide('modal-scoped')
      try {
        this.loading(true)
        if (e == 32) {
          let kq2 = this.GetData(await api.capnhat_thongtin_kh_hddt(this.axios, { vhdtb_id: this.state.hdtb_id, vkhachhang_id: this.state.versions[0].cus_id }))
          if (kq2 == 'ok') {
            this.state.cus_id = this.state.versions[0].cus_id
            this.$toast.success('Đồng bộ thành công!')
          } else {
            this.$toast.error(kq2)
          }
        }
        if (e == 78) {
          let kq2 = this.GetData(await api.capnhat_thongtin_kh_hddt(this.axios, { vhdtb_id: this.state.hdtb_id, vkhachhang_id: this.state.versions[1].cus_id }))
          if (kq2 == 'ok') {
            this.state.cus_id = this.state.versions[1].cus_id
            this.$toast.success('Đồng bộ thành công!')
          } else {
            this.$toast.error(kq2)
          }
        }
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      this.loading(false)
    },
    async btnXemMauHeThong_ButtonClick(e) {
      await this.btnXemMauDaDK_ButtonClick(e)
    },
    async btnXemMauDaDK_ButtonClick(e) {
      try {
        this.loading(true)
        let row = e
        //console.log(row)
        let xmlData = this.GetData(await api.HTMLMauHoaDon(this.axios, { params: { inv_id: row.identification } }))
        if (!xmlData) {
          this.$toast.warning('Không lấy được thông tin xem mẫu hóa đơn!')
          this.loading(false)
          return
        }
        var prettifyXml = function(sourceXml, sourcexslt) {
          var xmlDoc = new DOMParser().parseFromString(sourceXml, 'application/xml')
          var xsltDoc = new DOMParser().parseFromString(sourcexslt, 'application/xml')
          // console.info(xmlDoc,xsltDoc);
          var xsltProcessor = new XSLTProcessor()
          xsltProcessor.importStylesheet(xsltDoc)
          var resultDoc = xsltProcessor.transformToFragment(xmlDoc, document)
          // console.info(resultDoc);
          var resultXml = new XMLSerializer().serializeToString(resultDoc)
          // console.info(resultXml);

          return resultXml
        }
        var html = prettifyXml(xmlData.XmlFile, xmlData.XsltFile)
        html = html
          .replaceAll('&lt;', '<')
          .replaceAll('&gt;', '>')
          .replaceAll('&amp;', '&')
        const h = this.$createElement
        const titleVNode = h('div', { domProps: { innerHTML: html } })
        this.$bvModal.msgBoxOk([titleVNode])
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      this.loading(false)
    },
    async btnThucHien_ButtonClick(e) {
      //console.log('btnThucHien_ButtonClick', e)
      if (e.tacvu == 'Đăng ký khách hàng') {
        await this.KhoiTaoKH()
      }
      if (e.tacvu == 'Đăng ký demo') {
        await this.KhoiTaoDemo()
      }
      if (e.tacvu == 'Đăng ký hệ thống thật') {
        await this.TaoHeThongThat()
      }
    },
    async KhoiTaoKH() {
      try {
        this.loading(true)
        const check_use_api_dynamic = await api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
          p_nghiepvu: 'KHOITAOKH_DANGKYHOADONDIENTU78',
          p_ds_para: '{"LOAITB_ID":' + this.state.loaitb_id + '}'
        })
        const result_check = check_use_api_dynamic.data.data[0].ketqua
        console.log(check_use_api_dynamic.data.data[0].ketqua)
        if (result_check == 'ok') {
          const result_call_dynamic = await api.cntt_dynamic_api(this.axios, { hdtb_id: this.state.hdtb_id, vdem: 0, vtype: 'KHOITAOKH_DANGKYHOADONDIENTU78' })
          console.log(result_call_dynamic)
          const get_status = result_call_dynamic.data.data.status
          if (get_status == '00') {
            this.$toast.success(result_call_dynamic.data.data.message)
            await this.LoadThongTinDaKyHD()
            await this.ResetgrcTacVu()
          } else {
            this.$toast.error(result_call_dynamic.data.data.message)
          }
          this.loading(false)
          return
        } else {
          let lst = this.GetData(await api.lay_thongtin_tao_hddt(this.axios, { vhdtb_id: this.state.hdtb_id }))
          if (!lst || lst.length <= 0) {
            this.$toast.error('Không lấy được thông tin tạo HDDT')
            this.loading(false)
            return
          }
          lst = this.LowerCasePropertyList(lst)
          let kh_hddt = {}
          kh_hddt.address = lst[0].diachi_tb
          kh_hddt.company_name = lst[0].company_name
          kh_hddt.email = lst[0].email
          kh_hddt.is_sme = false
          kh_hddt.org = lst[0].org
          kh_hddt.represent_person = lst[0].represent_person
          kh_hddt.short_name = lst[0].short_name ? lst[0].short_name : this.controls.txtTenTat
          kh_hddt.system_type = lst[0].system_type
          kh_hddt.taxcode = lst[0].taxcode
          kh_hddt.taxauthority_code = lst[0].taxauthority_code
          kh_hddt.tel = lst[0].tel
          kh_hddt.taxauthority_code = lst[0].taxauthority_code
          kh_hddt.service_type = lst[0].service_type

          console.log(kh_hddt)

          let kq = this.GetData(await api.DangKyKhachHang(this.axios, kh_hddt))
          let reqLog = {
            vhdtb_id: this.state.hdtb_id,
            verror: '',
            vreq: JSON.stringify(kh_hddt),
            vres: JSON.stringify(kq),
            vma_tb: '',
            vghichu: 'KhoiTaoKH'
          }
          await api.sp_insert_log_cntt(this.axios, reqLog)
          console.log(kq)
          if (kq) {
            //console.log(kq)
            //console.log(this.state.version)
            let kq2 = this.GetData(await api.capnhat_thongtin_kh_hddt(this.axios, { vhdtb_id: this.state.hdtb_id, vkhachhang_id: kq }))
            if (kq2 == 'ok') {
              //gdvTacVu.SetCellValue_Async(0, "TRANGTHAI", "Đăng ký khách hàng thành công");
              this.$toast.success('Đăng ký khách hàng thành công')
              await this.LoadThongTinDaKyHD()
              await this.ResetgrcTacVu()
            } else {
              this.$toast.error(kq2)
            }
          }
        }
      } catch (ex) {
        let reqLog = {
          vhdtb_id: this.state.hdtb_id,
          verror: '',
          vreq: ex,
          vres: ex,
          vma_tb: '',
          vghichu: 'KhoiTaoKH - catch'
        }
        await api.sp_insert_log_cntt(this.axios, reqLog)
        commonFn.showException(this, ex)
      } finally {
        this.loading(false)
      }
    },
    async KhoiTaoDemo() {
      try {
        this.loading(true)
        if (this.state.kieuld_id == 13272) {
          let vkq_jr = this.GetData(await api.kiemtra_trangthai_jira_hths_v2(this.axios, { vhdtb_id: this.state.hdtb_id, vkhachhang_id: 0 }))
          if (vkq_jr != 'OK') {
            this.$toast.warning('Với loại hóa đơn điện tử phức tạp (Khách hàng yêu cầu mẫu) GDV cần gửi IT-360 để Trung tâm CNTT hỗ trợ, sau khi phiếu được resolve, GDV tiếp tục đăng ký demo và chính thức cho khách hàng')
            this.loading(false)
            return
          }
        }
        const check_use_api_dynamic = await api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
          p_nghiepvu: 'KHOITAODEMO_DANGKYHOADONDIENTU78',
          p_ds_para: '{"LOAITB_ID":' + this.state.loaitb_id + '}'
        })
        const result_check = check_use_api_dynamic.data.data[0].ketqua
        console.log(check_use_api_dynamic.data.data[0].ketqua)
        if (result_check == 'ok') {
          const result_call_dynamic = await api.cntt_dynamic_api(this.axios, { hdtb_id: this.state.hdtb_id, vdem: 0, vtype: 'KHOITAODEMO_DANGKYHOADONDIENTU78' })
          console.log(result_call_dynamic)
          const get_status = result_call_dynamic.data.data.status
          if (get_status == '00') {
            this.$toast.success(result_call_dynamic.data.data.message)
            await this.LoadThongTinDaKyHD()
            await this.ResetgrcTacVu()
          } else {
            this.$toast.error(result_call_dynamic.data.data.message)
          }
          this.loading(false)
          return
        } else {
          let dkmd = {}
          dkmd.cus_id = this.state.cus_id
          dkmd.taxcode = this.controls.txtMST
          dkmd.short_name = this.controls.txtTenTat
          let res = await api.KhoiTaoDemo(this.axios, dkmd)
          let kq = this.formatDataResponse(res)

          let reqLog = {
            vhdtb_id: this.state.hdtb_id,
            verror: '',
            vreq: JSON.stringify(dkmd),
            vres: JSON.stringify(kq),
            vma_tb: '',
            vghichu: 'KhoiTaoDemo'
          }
          console.log(reqLog)
          await api.sp_insert_log_cntt(this.axios, reqLog)
          if (!kq) {
            this.loading(false)
            this.$toast.error('Không khởi tạo được hệ thống Demo. Hãy kiểm tra lại')
            return
          }
          if (kq.errorCode == 0) {
            // câp nhat staus
            await api.cap_nhat_status_hdtb(this.axios, { vhdtb_id: this.state.hdtb_id, vstatus: 3 })
            this.$toast.success('Tạo hệ thống Demo thành công')
          } else {
            const resStatus = this.GetData(await api.fn_get_status_hdtb(this.axios, this.state.hdtb_id))
            if (resStatus != 3 && kq.faultCode === 'ERR:5.3') {
              await api.cap_nhat_status_hdtb(this.axios, { vhdtb_id: this.state.hdtb_id, vstatus: 3 })
              this.$toast.error(kq.faultString)
              this.loading(false)
            } else {
              this.$toast.error(kq.faultString)
              this.loading(false)
            }
          }
          await this.LoadThongTinDaKyHD()
          await this.ResetgrcTacVu()
        }
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      this.loading(false)
    },
    async TaoHeThongThat() {
      try {
        this.loading(true)
        const check_use_api_dynamic = await api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
          p_nghiepvu: 'TAOHETHONGTHAT_DANGKYHOADONDIENTU78',
          p_ds_para: '{"LOAITB_ID":' + this.state.loaitb_id + '}'
        })
        const result_check = check_use_api_dynamic.data.data[0].ketqua
        console.log(check_use_api_dynamic.data.data[0].ketqua)
        if (result_check == 'ok') {
          const result_call_dynamic = await api.cntt_dynamic_api(this.axios, { hdtb_id: this.state.hdtb_id, vdem: 0, vtype: 'TAOHETHONGTHAT_DANGKYHOADONDIENTU78' })
          console.log(result_call_dynamic)
          const get_status = result_call_dynamic.data.data.status
          if (get_status == '00') {
            this.$toast.success(result_call_dynamic.data.data.message)
            await this.LoadThongTinDaKyHD()
            await this.ResetgrcTacVu()
          } else {
            this.$toast.error(result_call_dynamic.data.data.message)
          }
          this.loading(false)
          return
        } else {
          if (this.state.loaitb_id == 2116 || this.state.loaitb_id == 319) {
            //HDDT - MTT, VNPT_ETICKET , VNPT_ERECEIPT
            let req = this.GetData(await api.tao_req_cntt(this.axios, { hdtb_id: this.state.hdtb_id, vhdtb_id: this.state.hdtb_id }))
            if (!req || req == '') {
              this.$toast.error('Không tạo được request. Hãy kiểm tra lại hàm tạo request')
              this.loading(false)
              return
            }
            const resKTHTT = await api.KhoiTaoHeThongThat(this.axios, JSON.parse(req))
            let kq = this.formatDataResponse(resKTHTT)
            console.log(kq)
            let reqLog = {
              vhdtb_id: this.state.hdtb_id,
              verror: '',
              vreq: JSON.stringify(req),
              vres: JSON.stringify(resKTHTT),
              vma_tb: '',
              vghichu: 'TaoHeThongThat'
            }
            console.log(req)
            console.log(resKTHTT)
            console.log(reqLog)
            await api.sp_insert_log_cntt(this.axios, reqLog)

            if (!kq) {
              this.loading(false)
              this.$toast.error('Không khởi tạo được hệ thống. Hãy kiểm tra lại')
              return
            }
            //neu ket qua thanh cong

            if (kq.errorCode == 0) {
              // câp nhat staus
              await api.cap_nhat_status_hdtb(this.axios, { vhdtb_id: this.state.hdtb_id, vstatus: 6 })
              this.$toast.success(kq.faultString)
            } else {
              //lay thonhg tin status let req = {"vhdtb_id": this.state.hdtb_id*1}
              const resStatus = this.GetData(await api.fn_get_status_hdtb(this.axios, this.state.hdtb_id))
              //lay status kiem tra co khac 6
              if (resStatus != 6 && kq.faultCode === 'ERR:5.1' && (this.state.loaitb_id == 2116 || this.state.loaitb_id == 319)) {
                let resRQ = this.GetData(await api.tao_req_cntt(this.axios, { hdtb_id: this.state.hdtb_id, vhdtb_id: this.state.hdtb_id }))

                if (!resRQ) {
                  this.$toast.error('Có lỗi xảy ra')
                  this.loading(false)
                }
                const resMTHD = this.formatDataResponse(await api.MuaThemHoaDon(this.axios, resRQ))
                const reqLog = {
                  vhdtb_id: this.state.hdtb_id,
                  verror: '',
                  vreq: JSON.stringify(resRQ),
                  vres: JSON.stringify(resMTHD),
                  vma_tb: '',
                  vghichu: 'Mua thêm hóa đơn tại nút tạo hệ thống thật'
                }
                await api.sp_insert_log_cntt(this.axios, reqLog)

                if (resMTHD && resMTHD.errorCode == 0) {
                  //cap nhat status
                  await api.cap_nhat_status_hdtb(this.axios, { vhdtb_id: this.state.hdtb_id, vstatus: 6 })
                  this.$toast.success('Thành công')
                } else {
                  this.$toast.error('Có lỗi xảy ra: ' + resMTHD.faultString)
                }
                await this.LoadThongTinDaKyHD()
                await this.ResetgrcTacVu()
                this.loading(false)
              } else {
                this.$toast.error('Có lỗi xảy ra: ' + kq.faultString)
                this.loading(false)
                await this.LoadThongTinDaKyHD()
                await this.ResetgrcTacVu()
                return
              }
            }
          } else {
            //HDDT
            let dkmct = {}
            dkmct.cus_id = this.state.cus_id
            dkmct.taxcode = this.controls.txtMST
            let soluong_hd = this.GetData(await api.lay_soluong_hddt_dk(this.axios, { vhdtb_id: this.state.hdtb_id }))
            dkmct.number_invoices = soluong_hd * 1

            let kq = this.formatDataResponse(await api.KhoiTaoHeThongThat(this.axios, dkmct))
            const reqLog = {
              vhdtb_id: this.state.hdtb_id,
              verror: '',
              vreq: JSON.stringify(dkmct),
              vres: JSON.stringify(kq),
              vma_tb: '',
              vghichu: 'TaoHeThongThat'
            }
            await api.sp_insert_log_cntt(this.axios, reqLog)
            if (kq.errorCode == 0) {
              // câp nhat staus
              await api.cap_nhat_status_hdtb(this.axios, { vhdtb_id: this.state.hdtb_id, vstatus: 6 })
              this.$toast.success('Thành công')
            } else {
              this.$toast.error('Đã có lỗi xảy ra: ' + kq.faultString)
            }
            await this.LoadThongTinDaKyHD()
            await this.ResetgrcTacVu()
          }
        }
      } catch (ex) {
        const reqLog = {
          vhdtb_id: this.state.hdtb_id,
          verror: '',
          vreq: ex,
          vres: ex,
          vma_tb: '',
          vghichu: 'TaoHeThongThat'
        }
        await api.sp_insert_log_cntt(this.axios, reqLog)
        commonFn.showException(this, ex)
      }
      this.loading(false)
    },
    GetData: function(response) {
      // {"errorCode":0,"faultCode":"","faultString":"Thao tác thành công!","data":null}
      if (response.data.error === 200 || response.data.error === '200' || response.data.errorCode == 0) {
        if (response.data.faultString && response.data.faultString !== '') {
          return response.data.faultString
        }
        return response.data.data
      } else {
        //console.log(response.data.error_code)
        //console.log(response.data.faultString)
        if (response.data && response.data.faultString) {
          if (response.data.errorCode == 1 && response.data.faultCode == 'ERR:4.1') {
            throw response.data.faultString + '. Hãy nhấn nút đồng bộ dữ liệu để đồng bộ thông tin về hệ thống OneBSS.'
          } else throw response.data.faultString
        }
      }
      return null
    },
    GetData_HDDT: function(response) {
      if (response.data.errorCode && response.data.errorCode != '' && response.data.errorCode != '0' && response.data.faultString && response.data.faultString != '') {
        throw response.data.faultString
      }
      if (response.data.error === 200 || response.data.error === '200' || response.data.errorCode === 0) {
        if (response.data.error_code === 'BSS-00000000') return response.data.data
        else if (response.data.errorCode === 0) return response.data.data
        else {
          commonFn.showException(this, response.data)
          return response.data.data
        }
      } else {
        console.log(response.data.error_code)
        commonFn.showException(this, response.data)
      }
      return null
    },

    ShowAlert: function(msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function(msg) {
      this.$toast.success(msg)
    },
    ShowError: function(msg) {
      this.$toast.error(msg)
    },
    LowerCasePropertyList(obj) {
      if (!obj) return []
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toLowerCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    UpperCasePropertyList(obj) {
      if (!obj) return []
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },

    //them func get format data
    formatDataResponse(response) {
      if (response.status === 200) {
        return response.data
      }

      return null
    }
  }
}
</script>

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
</style>
