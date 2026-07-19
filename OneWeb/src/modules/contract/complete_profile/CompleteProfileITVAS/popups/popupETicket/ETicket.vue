<template src='./Eticket.html'>

</template>
<script>
// import api from './api.js'
// import ComboboxGrid from '@/components/Controls/ComboboxGrid'
// import { isProxy, toRaw } from 'vue';
import commonFn from '@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions'
import api from '../../API/CompleteProfileAPI'
import Vue from 'vue'
import ChonMauHDDT from '../popupDangKyHoaDonDienTu78/ChonMauHDDT.vue'
// import { defineAsyncComponent } from '@vue/composition-api'

export default {
  props: {
    mst: {
      type: String,
      default: ""
    },

    state: {
      type: Object,
      default: {}
    },

    dataHDTB: {
      type: Array,
      default: []
    }
  },

  data() {
    return {
      controls: {
        txtSoLuongMau: '',
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
                      return this.$parent.$parent.$parent
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
                      return this.$parent.$parent.$parent
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
        ],
        list: []
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
                      return this.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onXemMauDaDK(e) {
                      console.log(this.parent)
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
                      return this.$parent.$parent.$parent
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
                      return this.$parent.$parent.$parent
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
      formData: {
        header: '',
        taxcode: '',
        company_name: '',
        Versions: [
          { status: 'Chưa khởi tạo khách hàng', org: '', isProcesssed: ''},
        ],

      },
      isShowBtn_ThemMau: true,
      isShowBtn_KhoiTaoThat: false,
      isShowBtn_KhoiTaoTest: false,
      isShowBtn_KhoiTaoKH: false,
      kh_hddt: {},
      khoiTao: true,
    }
  },

  components: {
    ChonMauHDDT,
    // ComboboxGrid
  },

  watch: {
    formData() {
      console.log('this.formData.Versions[0]')
      console.log(this.formData.Versions[0])
      if(this.formData.Versions[0].type === 'Hệ thống HDDT TT78') {
        console.log('Hệ thống hóa đơn điện tử thông tư 78')
        this.formData.header = 'Hệ thống hóa đơn điện tử thông tư 78';
        console.log(this.formData.header)
      }
      else if(this.formData.Versions[0].type === 'Hệ thống HDDT TT32') {
        this.formData.header = 'Hệ thống hóa đơn điện tử thông tư 32'
      }

      // check isProcesssed
      console.log(this.formData.Versions[0].isProcesssed)

      if (this.formData.Versions[0].isProcesssed === 4 ){
        this.isShowBtn_KhoiTaoThat = false
        this.isShowBtn_KhoiTaoTest = false
        this.isShowBtn_KhoiTaoKH = false
      }
      else if (this.formData.Versions[0].isProcesssed == 3){
        this.isShowBtn_KhoiTaoThat = true
      }
      else if (this.formData.Versions[0].isProcesssed == 1){
        this.isShowBtn_KhoiTaoTest = true
      }
      console.log('this.formData.Versions[0].isProcesssed')
      console.log(this.formData.Versions[0].isProcesssed)
    }
  },

  computed: {
  },

  async mounted() {
    console.log('this.state')
    console.log(this.state)
    this.$root.showLoading(true);

    await this.LayThongTinKHHDDT(this.mst)

    this.khoiTao = false;
    this.$root.showLoading(false);

  },


  methods: {
    check_isProcessed(isProcessed) {
      if(!isProcessed) {
        this.isShowBtn_ThemMau = true;
        this.isShowBtn_KhoiTaoThat = false;
        this.isShowBtn_KhoiTaoTest = true;
        this.isShowBtn_KhoiTaoKH = false;
      }
      else if(isProcessed == 2) { //demo
        this.isShowBtn_ThemMau = false;
        this.isShowBtn_KhoiTaoThat = true;
        this.isShowBtn_KhoiTaoTest = false;
        this.isShowBtn_KhoiTaoKH = false;
      }
      else if(isProcessed == 4) { //that
        this.isShowBtn_ThemMau = false;
        this.isShowBtn_KhoiTaoThat = false;
        this.isShowBtn_KhoiTaoTest = false;
        this.isShowBtn_KhoiTaoKH = false;
      }
    },
    async LayThongTinKHHDDT(mst) {
      try {
        let response = await api.LayThongTinKHHDDT(this.axios, { params: { taxcode: mst } })
        let kq = this.GetData(response)
        let data  = {};
        if(typeof kq !== 'object') {
          data = JSON.parse(kq)
        }
        console.log('kq')
        console.log(kq);

        //neu chua khoi tao kh
        let chuaDK = data.hasOwnProperty("success");
        if(chuaDK) {
          if(data.success == false) {
            await this.lay_thongtin_tao_hddt();
            //moi bat popup
            if(this.khoiTao) {
              this.isShowBtn_KhoiTaoKH = true;
              this.isShowBtn_ThemMau = false;
              this.isShowBtn_ThemMau = false;
              this.formData.company_name = this.kh_hddt.company_name
              this.formData.Versions[0].org = this.kh_hddt.org
              this.formData.Versions[0].type = this.kh_hddt.system_type
              return this.$toast.info(data.returnvalue + ' Vui lòng tạo mới khách hàng')
            }

          }
        }

        this.formData = kq;
        this.check_isProcessed(this.formData.Versions[0].isProcessed);
        //lay dds mau hd
        let dsMauHDDK = this.GetData(await api.layds_mau_hddt_dangky(this.axios, { vhdtb_id: this.state.hdtb_id }))
        this.grcMauDangKyTrenHD.list = this.LowerCasePropertyList(dsMauHDDK)

        //lay mau da dk
        await this.LayDanhSachMauDaDangKy(this.formData.Versions[0].cus_id, mst)
      }
      catch (e) {
        console.log(e)
      }
    },

    GetData: function(response) {
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

    async lay_thongtin_tao_hddt() {
      let lst = this.GetData(await api.lay_thongtin_tao_hddt(this.axios, { vhdtb_id: this.state.hdtb_id }))
      if (!lst || lst.length <= 0) {
        this.$toast.error('Không lấy được thông tin tạo HDDT')
        this.loading(false)
        return
      }
      lst = this.LowerCasePropertyList(lst)
      this.kh_hddt.address = lst[0].diachi_tb
      this.kh_hddt.company_name = lst[0].company_name
      this.kh_hddt.email = lst[0].email
      this.kh_hddt.is_sme = false
      this.kh_hddt.org = lst[0].org
      this.kh_hddt.represent_person = lst[0].represent_person
      // kh_hddt.short_name = lst[0].short_name ? lst[0].short_name : lst[0].taxcode
      this.kh_hddt.short_name =  lst[0].taxcode
      this.kh_hddt.system_type = lst[0].system_type
      this.kh_hddt.taxcode = lst[0].taxcode
      // this.kh_hddt.taxcode = '2222222222-117'
      this.kh_hddt.taxauthority_code = lst[0].taxauthority_code
      this.kh_hddt.tel = lst[0].tel
    },

    async KhoiTaoKH() {
      try {
        this.loading(true)

        await this.lay_thongtin_tao_hddt()

        let kq = await api.DangKyKhachHang(this.axios, this.kh_hddt)
        console.log('kq.data.faultCode')
        console.log(kq.data.faultCode)
        if(kq.data.faultCode == "ERR:4.1") {
          this.isShowBtn_KhoiTaoKH = false;
          this.isShowBtn_KhoiTaoTest = true;
          this.$toast.ìnfo('Hãy khởi tạo hệ thống Demo')
        }
        else if(kq.data.faultCode == "ERR:5.1") {
          this.isShowBtn_KhoiTaoTest = false;
          this.isShowBtn_KhoiTaoThat = true;
        }
        else if(kq.data.hasOwnProperty('errorCode') && kq.data.data) {
          //console.log(kq)
          //console.log(this.state.version)
          let kq2 = this.GetData(await api.capnhat_thongtin_kh_hddt(this.axios, { vhdtb_id: this.state.hdtb_id, vkhachhang_id: kq.data.data }))
          if (kq2 == 'ok') {
            //gdvTacVu.SetCellValue_Async(0, "TRANGTHAI", "Đăng ký khách hàng thành công");
            this.$toast.success('Đăng ký khách hàng thành công')
            this.isShowBtn_KhoiTaoTest = true
            this.isShowBtn_KhoiTaoKH = false
            await this.LayThongTinKHHDDT(this.mst)
          }
          else {
            this.$toast.error(kq2)
          }
        }
        else {
          this.$toast.error(kq.data.faultString)
        }
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      this.loading(false)
    },
    async KhoiTaoDemo() {
      try {
        console.log(this.formData)
        let dkmd = {}
            dkmd.cus_id = this.formData.Versions[0].cus_id;
            dkmd.taxcode = this.formData.taxcode;
            // dkmd.taxcode = '2222222222-116';
            // dkmd.short_name = this.formData.Versions[0].short_name;
        let res = await api.KhoiTaoDemo(this.axios, dkmd)
        if(res.data.faultCode) {
          return this.$toast.error(res.data.faultString);
        }

        this.$toast.success('Khởi tạo test thành công!');
        await this.LayThongTinKHHDDT(this.mst)
      }
      catch (e) {
        console.log(e)
      }
    },
    async TaoHeThongThat() {
      try {
        this.loading(true)
        if (this.state.loaitb_id == 2116) {
          //HDDT - MTT
          let req = this.GetData(await api.tao_req_cntt(this.axios, { hdtb_id: this.state.hdtb_id, vhdtb_id: this.state.hdtb_id }))
          if (!req || req == '') {
            this.$toast.error('Không tạo được request. Hãy kiểm tra lại hàm tạo request')
            this.loading(false)
            return
          }
          const resKTHTT = await api.KhoiTaoHeThongThat(this.axios, JSON.parse(req))
          let kq = this.formatDataResponse(resKTHTT)

          if (!kq) {
            this.loading(false)
            this.$toast.error('Không khởi tạo được hệ thống. Hãy kiểm tra lại')
            return
          }
          //neu ket qua thanh cong

          if (kq.errorCode == 0) {
            // câp nhat staus
            await api.cap_nhat_status_hdtb(this.axios, { vhdtb_id: this.state.hdtb_id, vstatus: 6 })
            this.$toast.success(kq)
          } else {
            //lay thonhg tin status let req = {"vhdtb_id": this.state.hdtb_id*1}
            const resStatus = this.GetData(await api.fn_get_status_hdtb(this.axios, this.state.hdtb_id))
            //lay status kiem tra co khac 6
            if (resStatus != 6 && kq.faultCode === 'ERR:5.1') {
              let resRQ = this.GetData(await api.tao_req_cntt(this.axios, { hdtb_id: this.state.hdtb_id, vhdtb_id: this.state.hdtb_id }))

              if (!resRQ) {
                this.$toast.error('Có lỗi xảy ra')
                this.loading(false)
              }
              const resMTHD = this.formatDataResponse(await api.MuaThemHoaDon(this.axios, resRQ))

              if (resMTHD && resMTHD.errorCode == 0) {
                //cap nhat status
                await api.cap_nhat_status_hdtb(this.axios, { vhdtb_id: this.state.hdtb_id, vstatus: 6 })
                this.$toast.success('Mua hoá đơn thành công!')
              } else {
                this.$toast.error('Có lỗi xảy ra')
              }
              this.loading(false)
            }
          }
        } else {
          //HDDT
          let dkmct = {}
          console.log('this.grcMauDaDK.list')
          console.log(this.formData.taxcode)
          dkmct.cus_id = this.formData.Versions[0].cus_id;
          dkmct.taxcode = this.formData.taxcode
          console.log(dkmct)
          let soluong_hd = this.GetData(await api.lay_soluong_hddt_dk(this.axios, { vhdtb_id: this.state.hdtb_id }))
          dkmct.number_invoices = soluong_hd * 1

          let kq = this.formatDataResponse(await api.KhoiTaoHeThongThat(this.axios, dkmct))
          if (kq.errorCode == 0) {
            // câp nhat staus
            await api.cap_nhat_status_hdtb(this.axios, { vhdtb_id: this.state.hdtb_id, vstatus: 6 })
            this.$toast.success('Khởi tạo thật thành công')
          } else {
            this.$toast.error('Đã có lỗi xảy ra: ' + kq.faultString)
          }
          await this.LoadThongTinDaKyHD()
          // await this.ResetgrcTacVu()
          await this.LayThongTinKHHDDT(this.mst)
        }
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      this.loading(false)
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
            console.log(this.grcMauDangKyTrenHD.list)
          }
        }
      } catch (ex) {
        commonFn.showException(this, ex)
      }
      this.loading(false)
    },

    LowerCasePropertyList(obj) {
      if(!obj) return [];
      return obj.map( function( item ){
        for(var key in item){
          var upper = key.toLowerCase();
          // check if it already wasn't uppercase
          if( upper !== key ){
            item[ upper ] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
    UpperCasePropertyList(obj) {
      if(!obj) return [];
      return obj.map( function( item ){
        for(var key in item){
          var upper = key.toUpperCase();
          // check if it already wasn't uppercase
          if( upper !== key ){
            item[ upper ] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },

    async btnThemMau_ItemClick() {
      let data = {
        _dsHDTB_CT: [],
        soluong: this.controls.txtSoLuongMau == '' ? 0 : this.controls.txtSoLuongMau
      }
      this.$refs.dialogChonMauHDDT.openDialog(data)
      // this.$parent.hide();
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
    async btnDangKyMau_ButtonClick(e) {
      this.loading(true)
      var row = e
      try {
        //console.log(row)
        //test
        let req = {
          cus_id: row.cus_id,
          inv_id: row.identification,
          taxcode: row.taxcode
          // taxcode: '2222222222-116'
        }
        row.trangthai = 'Đang xử lý'
        this.GetData(await api.DangKyMauHoaDon(this.axios, req))
        row.trangthai = 'Thành công'
        this.$toast.success(row.trangthai)
        await this.LoadMauDaDangKy()
        await this.LoadThongTinDaKyHD()
      } catch (ex) {
        row.trangthai = 'lỗi'
        commonFn.showException(this, ex)
      }
      this.loading(false)
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
              vhdtb_id: this.state.hdtb_id,
            }
            row.trangthai = 'Đang xử lý'
            this.GetData(await api.xoa_mau_hddt_chuadk(this.axios, req))
            row.trangthai = 'Thành công'
            this.$toast.success(row.trangthai)
            await this.LoadMauDaDangKy()
            await this.LoadThongTinDaKyHD()
          } catch (ex) {
            commonFn.showException(this, ex)
          }
          this.loading(false)
        })
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
    async LoadThongTinDaKyHD() {
      let soluong_dk = this.GetData(await api.lay_soluong_hddt_dk(this.axios, { vhdtb_id: this.state.hdtb_id }))
      this.controls.txtSoLuongMau = soluong_dk
      let lst = this.GetData(await api.lay_thongtin_tao_hddt(this.axios, { vhdtb_id: this.state.hdtb_id }))
      let dsMauHDDK = this.GetData(await api.layds_mau_hddt_dangky(this.axios, { vhdtb_id: this.state.hdtb_id }))
      this.grcMauDangKyTrenHD.list = this.LowerCasePropertyList(dsMauHDDK)

      //console.log('Thông tin lst')
      //console.log(lst)

      if (lst && lst.length > 0) {
        this.controls.txtNguoiDaiDien = lst[0].represent_person
        this.controls.txtTenCty = lst[0].company_name
        this.controls.txtDiaChi = lst[0].diachi_tb
        this.controls.txtDienThoai = lst[0].tel
        this.controls.txtEmail = lst[0].email
        this.controls.txtMST = lst[0].taxcode
        this.controls.txtTenTat = lst[0].short_name
        this.controls.txtTinhThanh = lst[0].org
        this.controls.cboToKen = lst[0].system_type
        this.controls.txtIdKH = lst[0].cus_id
        this.controls.status = lst[0].status
      }
    },
    async LayDanhSachMauDaDangKy(cus_id, taxcode) {
      try {
        this.loading(true)
        let req = {
          cus_id: cus_id,
          limit: 100,
          offset: 0,
          taxcode: taxcode
        }
        let ds = this.GetData(await api.LayDSMauHoaDonDaDangKy(this.axios, req))
        this.grcMauDaDK.list = []
        if (ds) {
          this.grcMauDaDK.list = this.LowerCasePropertyList(ds.datas)
        }
      } catch (ex) {
        console.error(ex)
        //commonFn.showException(this, ex);
      }
      this.loading(false)
    },

    async btnXemMauHeThong_ButtonClick(e) {
      await this.btnXemMauDaDK_ButtonClick(e)
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
            console.log(this.state)
            let req = {
              cus_id: this.formData.Versions[0].cus_id,
              inv_id: row.identification,
              taxcode: this.formData.taxcode
            }
            this.GetData(await api.XoaMauHoaDon(this.axios, req))
            await this.LoadMauDaDangKy()
            await this.LoadThongTinDaKyHD()
          } catch (ex) {
            commonFn.showException(this, ex)
          }
          this.loading(false)
        })
    },

    formatDataResponse(response) {
      if (response.status === 200) {
        return response.data
      }

      return null
    },
  }
}
</script>
<style scoped>

</style>
