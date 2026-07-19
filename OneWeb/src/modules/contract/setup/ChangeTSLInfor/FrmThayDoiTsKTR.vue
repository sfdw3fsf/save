
<template src="./FrmThayDoiTsKTR.html"></template>
<style src="./FrmThayDoiTsKTR.scss"></style>
<script>
import service from "./service/service"
import serviceTest from "./service/serviceTest"
import endpoint from "./components/endpoint.vue"
// import chonport from "./popup/chonport/chonport.vue"
import chonport from '@/modules/EXTS/QUANTRI/ThayDoiThongSoInternet/chon-port'
import moment from "moment"
import breadcrumb from "@/components/kylq_components/breadcrumb.vue"
import {DichVuVienThong, LoaiHopDong} from "@/const/enums/index"
import {KIEULAP} from "./Enum"
export default {
  name: "FrmThayDoiTsKTR",
  components: {endpoint, breadcrumb, chonport},
   props:{
        ma_tb:{
            type:String,
            default:''
        },
        propDichvuvt_id:{
            type:Number,
            default:0
        },
        isPopup:{
          type:Boolean,
          default:false
        },
        row_id:{
          type:String,
          default:''
        }
  },
  created: async function () {
    // Fix css breaking layout
    this.removeCssRule("/static/css/custom.css", ".row")
    this.removeCssRule("/static/css/custom.css", ".custom-select")
    this.removeCssRule("/static/css/custom.css", ".custom-control")
    this.removeCssRule("/static/css/style.css", ".input-group")
    this.removeCssRule("/static/css/style.css", ".form-control")
    console.log("created")
    await this.init()
  },
  data: function () {
    return {
      header: {
        title: "Thay đổi thông số kỹ thuật KTR",
        list: [{
          name: "Phát triển thuê bao",
          link: "/pttb"
        }, {
          name: "Thay đổi thông số kỹ thuật TSL",
          link: "/pttb/tdtstsl"
        } ]
      },
      isEnableCapNhatButton: false,
      isEnableHuyButton: false,
      radioTrangThaiDuLieu: 1,
      hdtbId: 0,
      thuebaoId: 0,
      tocDoKenhId: 0,
      dsTocDoKenh: [],
      dichVuVienThongId: 0,
      dsDichVuVT: [],
      loaiHinhThueBaoId: 0,
      dsLoaiHinhTB: [],
      maTb: "",
      tenThueBao: "",
      soPLHDCu: "",
      trangThaiThueBao: "",
      soPLHDMoi: "",
      tramTbDau: 0,
      tramTbCuoi: 0,
      maDoiCap: 0,
      isSuaDiemDau: false,
      isSuaDiemCuoi: false,
      trangThaiDuLieu: {
        isDanhBa: true,
        isHopDong: false
      },
      thongSoCu: {
        diemDau: {
          soAo: "",
          thietBiId: 0,
          serial: "",
          loaiCapId: 0,
          portId: 0,
          sVlan: 0,
          cvLan: 0,
          capGoc: "",
          capNgon: "",
          adslCap: "",
          adslPort: "",
          tuCapGoc: "",
          tuCapNgon: "",
          nasPortId: "",
          vrfVfiId: "",
          doiCapGoc: 0,
          doiCapNgon: 0,
          tbiTruyenDan: "",
          ipWan: "",
          lienTu: "",
          hopCapNgon: "",
          timeSlot: "",
          link: "",
          cuLy: 0,
          tuyenCap: "",
          diaChi: "",
          vci_vpi_id: 0,
          donViId: 0,
          dsThieBi: [],
          dsLoaiCap: [],
          dsDonVi: []
        },
        diemCuoi: {
          soAo: "",
          thietBiId: 0,
          serial: "",
          loaiCapId: 0,
          portId: 0,
          sVlan: 0,
          cvLan: 0,
          capGoc: "",
          capNgon: "",
          adslCap: "",
          adslPort: "",
          tuCapGoc: "",
          tuCapNgon: "",
          nasPortId: "",
          vrfVfiId: "",
          doiCapGoc: 0,
          doiCapNgon: 0,
          tbiTruyenDan: "",
          ipWan: "",
          lienTu: "",
          hopCapNgon: "",
          timeSlot: "",
          link: "",
          cuLy: 0,
          tuyenCap: "",
          diaChi: "",
          vci_vpi_id: null,
          donViId: 0,
          dsThieBi: [],
          dsLoaiCap: [],
          dsDonVi: []
        }
      },
      thongSoMoi: {
        diemDau: {
          soAo: "",
          thietBiId: 0,
          serial: "",
          loaiCapId: 0,
          portId: 0,
          sVlan: 0,
          cvLan: 0,
          capGoc: "",
          capNgon: "",
          adslCap: "",
          adslPort: "",
          tuCapGoc: "",
          tuCapNgon: "",
          nasPortId: "",
          vrfVfiId: "",
          doiCapGoc: 0,
          doiCapNgon: 0,
          tbiTruyenDan: "",
          ipWan: "",
          lienTu: "",
          hopCapNgon: "",
          timeSlot: "",
          link: "",
          cuLy: 0,
          tuyenCap: "",
          diaChi: "",
          vci_vpi_id: null,
          donViId: 0,
          dsThieBi: [],
          dsLoaiCap: [],
          dsDonVi: []
        },
        diemCuoi: {
          soAo: "",
          thietBiId: 0,
          serial: "",
          loaiCapId: 0,
          portId: 0,
          sVlan: 0,
          cvLan: 0,
          capGoc: "",
          capNgon: "",
          adslCap: "",
          adslPort: "",
          tuCapGoc: "",
          tuCapNgon: "",
          nasPortId: "",
          vrfVfiId: "",
          doiCapGoc: 0,
          doiCapNgon: 0,
          tbiTruyenDan: "",
          ipWan: "",
          lienTu: "",
          hopCapNgon: "",
          timeSlot: "",
          link: "",
          cuLy: 0,
          tuyenCap: "",
          diaChi: "",
          vci_vpi_id: null,
          donViId: 0,
          dsThieBi: [],
          dsLoaiCap: [],
          dsDonVi: []
        }
      }
    }
  },
  watch: {
    "radioTrangThaiDuLieu": function (newValue, oldValue) {
      this.trangThaiDuLieu.isDanhBa = this.radioTrangThaiDuLieu == 1
      this.trangThaiDuLieu.isHopDong = !this.trangThaiDuLieu.isDanhBa
      this.Clear()
      this.maTb = ""
    }
  },
  methods: {
    async Xacnhan()
      {
        this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xác nhận không thay đổi thông tin không ?', {
                    title: '',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then( async  v => {
                    if(v) {
                         let rs = await this.$root.context.post('/web-thicong/capnhatthongtin/sp_doisoat_capnhat_trangthai', {
                          p_id_row: this.row_id,
                          p_loai_tt : 0,
                        })
                        if (rs&&rs.data&&rs.data.error == 200&&rs.data.error_code == 'BSS-00000000') {
                          this.$root.toastError('Xác nhận không thay đổi thông tin thành công!')          
                        }
                        
                    }
                });
      },
    removeCssRule: function (file, rule) {
      try {
        var s = document.querySelector(`link[href = "${file}"]`)
        if (s.sheet) {
          s = s.sheet
          var i = [].slice.call(s.cssRules).findIndex((v) => v.selectorText == rule)
          if (i > -1) s.deleteRule(i)
        }
      } catch (e) {
        console.error(e)
      }
    },
    async init () {
      try {
        this.loading(true)
        var task1 = this.get_ds_tocdo_kenh()
        var task2 = this.get_ds_dichvu_vt()
        var task3 = this.get_ds_loaihinh_thuebao()
        var task4 = this.get_ds_loaicap()
        var task5 = this.get_ds_thiebit()
        var task6 = this.get_ds_donvi();
        
        await Promise.all([task1, task2, task3, task4, task5, task6])
        await this.khoiTaoDauVao();
        this.SetButton(0)
      } catch (e) {
        this.$toast.error(e.message)
        console.error(e)
      } finally {
        this.loading(false)
      }
    },
    async khoiTaoDauVao(){
      this.maTb=this.ma_tb;
      this.dichVuVienThongId = this.propDichvuvt_id
      console.log('khoiTaoDauVao')
      console.log(this.maTb);
      if(this.maTb.trim()!=''){
        await this.onMaTbEnter()
      }
    },
    async get_ds_tocdo_kenh () {
      var response = await service.css_tocdo_kenh()
      if (response.error_msg) {
        this.$toast.error(response.error_msg)
        this.dsTocDoKenh = []
      } else {
        this.dsTocDoKenh = response.data
      }
    },
    async get_ds_dichvu_vt () {
      var response = await service.css_dichvu_vt()
      if (response.error_msg) {
        this.$toast.error(response.error_msg)
        this.dsDichVuVT = []
      } else {
        this.dsDichVuVT = response.data
      }
    },
    async get_ds_loaihinh_thuebao () {
      var response = await service.css_loaihinh_tb()
      if (response.error_msg) {
        this.$toast.error(response.error_msg)
        this.dsLoaiHinhTB = []
      } else {
        this.dsLoaiHinhTB = response.data.filter(item => item.DICHVUVT_ID == 9)
      }
    },
    async get_ds_thiebit () {
      var response = await service.sp_lay_ds_loai_tbi(9)
      if (response.error_msg) {
        this.$toast.error(response.error_msg)
      } else {
        this.thongSoCu.diemDau.dsThieBi = response.data
        this.thongSoCu.diemCuoi.dsThieBi = structuredClone(response.data)
        this.thongSoMoi.diemDau.dsThieBi = structuredClone(response.data)
        this.thongSoMoi.diemCuoi.dsThieBi = structuredClone(response.data)
      }
    },
    async get_ds_loaicap () {
      var response = await service.cabman_loai_cap()
      if (response.error_msg) {
        this.$toast.error(response.error_msg)
      } else {
        this.thongSoCu.diemDau.dsLoaiCap = response.data
        this.thongSoCu.diemCuoi.dsLoaiCap = structuredClone(response.data)
        this.thongSoMoi.diemDau.dsLoaiCap = structuredClone(response.data)
        this.thongSoMoi.diemCuoi.dsLoaiCap = structuredClone(response.data)
      }
    },
    async get_ds_donvi () {
      var response = await service.sp_ht_tatca_vetinh_combobox1()
      if (response.error_msg) {
        this.$toast.error(response.error_msg)
      } else {
        this.thongSoCu.diemDau.dsDonVi = response.data
        this.thongSoCu.diemCuoi.dsDonVi = structuredClone(response.data)
        this.thongSoMoi.diemDau.dsDonVi = structuredClone(response.data)
        this.thongSoMoi.diemCuoi.dsDonVi = structuredClone(response.data)
      }
    },
    async onMaTbEnter () {
      console.log("MaTbEnter")
      try {
        this.Clear()
        this.loading(true)
        if (this.trangThaiDuLieu.isDanhBa) {
          await this.HienThiThongTinDanhBa()
        } else if (this.trangThaiDuLieu.isHopDong) {
          await this.HienThiThongTinHopDong()
        }
      } catch (e) {
        console.log(e)
        this.$toast.error("Co loi xay ra " + e.message)
      } finally {
        this.loading(false)
      }
    },
    SetButton (kieu) {
      this.isEnableCapNhatButton = false
      this.isEnableHuyButton = false
      if (kieu == 0) {
        this.Clear()
      } else if (kieu == 1) {
        this.isEnableCapNhatButton = false
        this.isEnableHuyButton = false
        this.Clear()
      } else if (kieu == 2) {
        this.isEnableHuyButton = true
        this.isEnableCapNhatButton = true
      } else if (kieu == 3) {
        this.isEnableHuyButton = true
        this.isEnableCapNhatButton = true
      }
    },
    Clear () {
      this.hdtbId = 0
      this.thuebaoId = 0
      this.tocDoKenhId = 0
      // this.maTb = ""
      this.tenThueBao = ""
      this.soPLHDCu = ""
      this.trangThaiThueBao = ""
      this.soPLHDMoi = ""
      this.isSuaDiemDau = false
      this.isSuaDiemCuoi = false
      this.thongSoCu.diemDau.soAo = ""
      this.thongSoCu.diemDau.thietBiId = 0
      this.thongSoCu.diemDau.serial = ""
      this.thongSoCu.diemDau.loaiCapId = 0
      this.thongSoCu.diemDau.sVlan = 0
      this.thongSoCu.diemDau.cvLan = 0
      this.thongSoCu.diemDau.capGoc = ""
      this.thongSoCu.diemDau.capNgon = ""
      this.thongSoCu.diemDau.adslCap = ""
      this.thongSoCu.diemDau.adslPort = ""
      this.thongSoCu.diemDau.tuCapGoc = ""
      this.thongSoCu.diemDau.tuCapNgon = ""
      this.thongSoCu.diemDau.nasPortId = ""
      this.thongSoCu.diemDau.vrfVfiId = ""
      this.thongSoCu.diemDau.doiCapGoc = 0
      this.thongSoCu.diemDau.doiCapNgon = 0
      this.thongSoCu.diemDau.tbiTruyenDan = ""
      this.thongSoCu.diemDau.ipWan = ""
      this.thongSoCu.diemDau.lienTu = ""
      this.thongSoCu.diemDau.hopCapNgon = ""
      this.thongSoCu.diemDau.timeSlot = ""
      this.thongSoCu.diemDau.link = ""
      this.thongSoCu.diemDau.cuLy = 0
      this.thongSoCu.diemDau.tuyenCap = ""
      this.thongSoCu.diemDau.diaChi = ""
      this.thongSoCu.diemDau.vci_vpi_id = 0
      this.thongSoCu.diemDau.donViId = 0
      this.thongSoCu.diemCuoi = structuredClone(this.thongSoCu.diemDau)
      this.thongSoMoi.diemDau = structuredClone(this.thongSoCu.diemDau)
      this.thongSoMoi.diemCuoi = structuredClone(this.thongSoCu.diemDau)
      console.log('step1')
      console.log('this.thongSoCu.diemDau', this.thongSoCu.diemDau)
      this.$refs.diemDauCu.HienThiThongSo(this.thongSoCu.diemDau)
      console.log('step2')
      this.$refs.diemCuoiCu.HienThiThongSo(this.thongSoCu.diemCuoi)
      console.log('step3')
      this.$refs.diemDauMoi.HienThiThongSo(this.thongSoMoi.diemDau)
      console.log('step4')
      this.$refs.diemCuoiMoi.HienThiThongSo(this.thongSoMoi.diemCuoi)
      console.log('step5')
    },
    async HienThiThongTinDanhBa () {
      try {
        var danhBaResult = await service.lay_danhba_theo_matb(this.maTb)
        if (danhBaResult.error_msg) {
          this.$toast.error("Co loi lay danh ba " + danhBaResult.error_msg)
          return
        }
        var ds = danhBaResult.data
        var dsTsl = []
        if (ds) {
          this.dichVuVienThongId = ds.dichvuvt_id
          this.thuebaoId = ds.thuebao_id
          this.tenThueBao = ds.ten_tb
          this.loaiHinhThueBaoId = ds.loaitb_id
          this.trangThaiThueBao = ds.trangthai_tb
          console.log("DichVuVienThong.TSL=" + DichVuVienThong.TSL)
          if (this.dichVuVienThongId == DichVuVienThong.TSL) {
            dsTsl = await service.lay_ds_db_con_theo_tbid(this.thuebaoId)
          }
          // this.thongSoCu.diemDau.
          if (dsTsl.length > 0) {
            this.tocDoKenhId = dsTsl[0].tocdo_id
            this.soPLHDCu = dsTsl[0].ma_pl
            this.soPLHDMoi = dsTsl[0].ma_pl
            this.maDoiCap = dsTsl[0].madoicap
            for (var i = 0; i < dsTsl.length; i++) {
              var thongso = dsTsl[i]
              if (thongso.daucuoi_id == 1) {
                await this.HienThiThongTinDanhBaDiemDau(thongso)
              } else {
                await this.HienThiThongTinDanhBaDiemCuoi(thongso)
              }
            }
            this.SetButton(2)
          }
        } else {
          this.$toast.warning("Không tìm thấy thông tin về thuê bao " +
          this.maTb + " \n Hãy kiểm tra lại số máy, dịch vụ vt")
        }
      } catch (e) {
        console.error(e)
        this.$toast.error("Co loi lay danh ba " + e.message)
      }
    },
    async HienThiThongTinDanhBaDiemDau (thongso) {
      // diem dau la du lieu tong dai
      this.tramTbDau = thongso.tram_id
      this.thongSoCu.diemDau.portId = thongso.port_id
      this.thongSoCu.diemDau.vci_vpi_id = thongso.vci_vpi_id
      this.thongSoCu.diemDau.sVlan = this.convertToNumber(thongso.vpi)
      this.thongSoCu.diemDau.cvLan = this.convertToNumber(thongso.vci)
      this.thongSoCu.diemDau.adslCap = thongso.port_mdf
      this.thongSoCu.diemDau.adslPort = thongso.adsl_port
      if (thongso.selfid01 && thongso.selfid02 && thongso.selfid03 && thongso.selfid04) {
        this.thongSoCu.diemDau.nasPortId = `${thongso.system}/${thongso.selfid01}/${thongso.selfid02}/${thongso.selfid03}/${thongso.selfid04}`
      } else {
        this.thongSoCu.diemDau.nasPortId = thongso.system
      }
      this.thongSoCu.diemDau.timeSlot = thongso.timeslot
      this.thongSoCu.diemDau.tbiTruyenDan = thongso.ten_tbi
      this.thongSoCu.diemDau.soAo = thongso.ma_lt
      this.thongSoCu.diemDau.link = thongso.link
      this.thongSoCu.diemDau.serial = thongso.serial
      this.thongSoCu.diemDau.vrfVfiId = thongso.vrf
      this.thongSoCu.diemDau.ipWan = thongso.ip_wan
      // end du lieu dong dai
      // du lieu cap
      this.thongSoCu.diemDau.tuyenCap = thongso.tuyencap
      this.thongSoCu.diemDau.lienTu = thongso.lientu
      this.thongSoCu.diemDau.tuCapNgon = thongso.tucap_p
      this.thongSoCu.diemDau.doiCapNgon = this.convertToNumber(thongso.doicap_p)
      this.thongSoCu.diemDau.hopCapNgon = thongso.hopcap_p
      this.thongSoCu.diemDau.loaiCapId = thongso.loaicap_id
      this.thongSoCu.diemDau.capGoc = thongso.cap_goc
      this.thongSoCu.diemDau.capNgon = thongso.cap_p
      this.thongSoCu.diemDau.doiCapGoc = this.convertToNumber(thongso.doicap_goc)
      this.thongSoCu.diemDau.tuCapGoc = thongso.tucap_goc
      this.thongSoCu.diemDau.cuLy = thongso.culy
      // end du lieu cap
      this.thongSoCu.diemDau.thietBiId = thongso.thietbidc_id
      this.thongSoCu.diemDau.diaChi = thongso.diachi
      var donviTmp = await service.sp_lay_ds_tramtc_thuebao(1, this.thuebaoId)
      if (donviTmp) {
        this.thongSoCu.diemDau.donViId = donviTmp.donvi_id
      }
      this.thongSoMoi.diemDau = structuredClone(this.thongSoCu.diemDau)
      this.$refs.diemDauMoi.HienThiThongSo(this.thongSoMoi.diemDau)
      this.$refs.diemDauCu.HienThiThongSo(this.thongSoCu.diemDau)
    },
    async HienThiThongTinDanhBaDiemCuoi (thongso) {
      // diem dau la du lieu tong dai
      this.tramTbCuoi = thongso.tram_id
      this.thongSoCu.diemCuoi.portId = thongso.port_id
      this.thongSoCu.diemCuoi.vci_vpi_id = thongso.vci_vpi_id
      this.thongSoCu.diemCuoi.sVlan = this.convertToNumber(thongso.vpi)
      this.thongSoCu.diemCuoi.cvLan = this.convertToNumber(thongso.vci)
      this.thongSoCu.diemCuoi.adslCap = thongso.port_mdf
      this.thongSoCu.diemCuoi.adslPort = thongso.adsl_port
      if (thongso.selfid01 && thongso.selfid02 && thongso.selfid03 && thongso.selfid04) {
        this.thongSoCu.diemCuoi.nasPortId = `${thongso.system}/${thongso.selfid01}/${thongso.selfid02}/${thongso.selfid03}/${thongso.selfid04}`
      } else {
        this.thongSoCu.diemCuoi.nasPortId = thongso.system
      }
      this.thongSoCu.diemCuoi.timeSlot = thongso.timeslot
      this.thongSoCu.diemCuoi.tbiTruyenDan = thongso.ten_tbi
      this.thongSoCu.diemCuoi.soAo = thongso.ma_lt
      this.thongSoCu.diemCuoi.link = thongso.link
      this.thongSoCu.diemCuoi.serial = thongso.serial
      this.thongSoCu.diemCuoi.vrfVfiId = thongso.vrf
      this.thongSoCu.diemCuoi.ipWan = thongso.ip_wan
      // end du lieu dong dai
      // du lieu cap
      this.thongSoCu.diemCuoi.tuyenCap = thongso.tuyencap
      this.thongSoCu.diemCuoi.lienTu = thongso.lientu
      this.thongSoCu.diemCuoi.tuCapNgon = thongso.tucap_p
      this.thongSoCu.diemCuoi.doiCapNgon = this.convertToNumber(thongso.doicap_p)
      this.thongSoCu.diemCuoi.hopCapNgon = thongso.hopcap_p
      this.thongSoCu.diemCuoi.loaiCapId = thongso.loaicap_id
      this.thongSoCu.diemCuoi.capGoc = thongso.cap_goc
      this.thongSoCu.diemCuoi.capNgon = thongso.cap_p
      this.thongSoCu.diemCuoi.doiCapGoc = this.convertToNumber(thongso.doicap_goc)
      this.thongSoCu.diemCuoi.tuCapGoc = thongso.tucap_goc
      this.thongSoCu.diemCuoi.cuLy = thongso.culy
      // end du lieu cap
      this.thongSoCu.diemCuoi.thietBiId = thongso.thietbidc_id
      this.thongSoCu.diemCuoi.diaChi = thongso.diachi
      var donviTmp = await service.sp_lay_ds_tramtc_thuebao(1, this.thuebaoId)
      if (donviTmp) {
        this.thongSoCu.diemCuoi.donViId = donviTmp.donvi_id
      }
      this.thongSoMoi.diemCuoi = structuredClone(this.thongSoCu.diemCuoi)
      this.$refs.diemCuoiCu.HienThiThongSo(this.thongSoCu.diemCuoi)
      this.$refs.diemCuoiMoi.HienThiThongSo(this.thongSoMoi.diemCuoi)
    },
    async HienThiThongTinHopDong () {
      var hopdongResult = await service.lay_ds_hopdong_theo_matb(
        {
          "maTb": this.maTb, "loaiHdId": 0, "ttHdId": 0, "donViDlId": 0, "dichVuVtId": this.dichVuVienThongId, "loaiTbId": 0
        }
      )
      if (hopdongResult.error_msg) {
        this.$toast.warning("Có lỗi khi lấy thông tin hợp đồng " + hopdongResult.error_msg)
        return
      }
      var ds = hopdongResult.data[0]
      console.log("ds hopdong result: ", ds)
      if (ds) {
        if (ds.tthd_id == 6) {
          this.$toast.warning("Hợp đồng đã hoàn thành. Bạn không được thay đổi thông số kỹ thuật !")
          return
        } else if (ds.tthd_id == 7) {
          this.$toast.warning("Hợp đồng đã thoái trả. Bạn không được thay đổi thông số kỹ thuật !")
          return
        }
        if (ds.loaihd_id == LoaiHopDong.DAT_MOI || ds.loaihd_id == LoaiHopDong.DI_CHUYEN || ds.loaihd_id == LoaiHopDong.CHUYENDOI_LH || ds.loaihd_id == LoaiHopDong.KHOIPHUC_SD) {
          this.maTb = ds.ma_tb
          this.tenThueBao = ds.ten_tb
          this.hdtbId = ds.hdtb_id
          this.dichVuVienThongId = ds.dichvuvt_id
          this.loaiHinhThueBaoId = ds.loaitb_id
        } else {
          this.$toast.warning("Bạn chỉ được đổi thông số kỹ thuật của thuê bao với loại HĐ/PL : \nLắp đặt mới, Di chuyển, Chuyển đổi loại hình và Khôi phục thuê bao thanh lý !")
          return
        }
      } else {
        this.$toast.warning("Không tìm thấy thông tin hợp đồng của thuê bao : " + this.maTb)
        return
      }
      var hopdongTslResult = await service.lay_ds_hopdong_thuebao_tls(this.hdtbId)
      if (hopdongTslResult.data && hopdongTslResult.data.length > 0) {
        var dsTsl = hopdongTslResult.data
        this.tocDoKenhId = dsTsl[0].tocdo_id
        this.soPLHDCu = dsTsl[0].ma_pl
        this.soPLHDMoi = dsTsl[0].ma_pl
        this.maDoiCap = dsTsl[0].madoicap
        for (var i = 0; i < dsTsl.length; i++) {
          var thongso = dsTsl[i]
          if (thongso.daucuoi_id == 1) {
            await this.HienThiThongTinHopDongDiemDau(thongso)
          } else {
            await this.HienThiThongTinHopDongDiemCuoi(thongso)
          }
        }
        this.SetButton(2)
      } else {
        this.$toast.warning("Không tìm thấy thông tin về thuê bao: " + this.maTb)
      }
    },
    async HienThiThongTinHopDongDiemDau (thongso) {
      // diem dau la du lieu tong dai
      this.tramTbDau = thongso.tram_id
      this.thongSoCu.diemDau.portId = thongso.port_id
      this.thongSoCu.diemDau.vci_vpi_id = thongso.vci_vpi_id
      this.thongSoCu.diemDau.sVlan = this.convertToNumber(thongso.vpi)
      this.thongSoCu.diemDau.cvLan = this.convertToNumber(thongso.vci)
      this.thongSoCu.diemDau.adslCap = thongso.port_mdf
      this.thongSoCu.diemDau.adslPort = thongso.adsl_port
      if (thongso.selfid01 && thongso.selfid02 && thongso.selfid03 && thongso.selfid04) {
        this.thongSoCu.diemDau.nasPortId = `${thongso.system}/${thongso.selfid01}/${thongso.selfid02}/${thongso.selfid03}/${thongso.selfid04}`
      } else {
        this.thongSoCu.diemDau.nasPortId = thongso.system
      }
      this.thongSoCu.diemDau.timeSlot = thongso.timeslot
      this.thongSoCu.diemDau.tbiTruyenDan = thongso.ten_tbi
      this.thongSoCu.diemDau.soAo = thongso.ma_lt
      this.thongSoCu.diemDau.link = thongso.link
      this.thongSoCu.diemDau.serial = thongso.serial
      this.thongSoCu.diemDau.vrfVfiId = thongso.vrf
      this.thongSoCu.diemDau.ipWan = thongso.ip_wan
      // end du lieu dong dai
      // du lieu cap
      this.thongSoCu.diemDau.tuyenCap = thongso.tuyencap
      this.thongSoCu.diemDau.lienTu = thongso.lientu
      this.thongSoCu.diemDau.tuCapNgon = thongso.tucap_p
      this.thongSoCu.diemDau.doiCapNgon = this.convertToNumber(thongso.doicap_p)
      this.thongSoCu.diemDau.hopCapNgon = thongso.hopcap_p
      this.thongSoCu.diemDau.loaiCapId = thongso.loaicap_id
      this.thongSoCu.diemDau.capGoc = thongso.cap_goc
      this.thongSoCu.diemDau.capNgon = thongso.cap_p
      this.thongSoCu.diemDau.doiCapGoc = this.convertToNumber(thongso.doicap_goc)
      this.thongSoCu.diemDau.tuCapGoc = thongso.tucap_goc
      this.thongSoCu.diemDau.cuLy = this.convertToNumber(thongso.culy)
      // end du lieu cap
      this.thongSoCu.diemDau.thietBiId = thongso.thietbidc_id
      this.thongSoCu.diemDau.diaChi = thongso.diachi
      var donviTmp = await service.sp_lay_ds_tramtc_thuebao(1, this.thuebaoId)
      if (donviTmp) {
        this.thongSoCu.diemDau.donViId = donviTmp.donvi_id
      }
      this.thongSoMoi.diemDau = structuredClone(this.thongSoCu.diemDau)
      this.$refs.diemDauMoi.HienThiThongSo(this.thongSoMoi.diemDau)
      this.$refs.diemDauCu.HienThiThongSo(this.thongSoCu.diemDau)
    },
    convertToNumber (str) {
      var result = parseInt(str)
      return result || 0
    },
    async HienThiThongTinHopDongDiemCuoi (thongso) {
      // diem dau la du lieu tong dai
      this.tramTbCuoi = thongso.tram_id
      this.thongSoCu.diemCuoi.portId = thongso.port_id
      this.thongSoCu.diemCuoi.vci_vpi_id = thongso.vci_vpi_id
      this.thongSoCu.diemCuoi.sVlan = this.convertToNumber(thongso.vpi)
      this.thongSoCu.diemCuoi.cvLan = this.convertToNumber(thongso.vci)
      this.thongSoCu.diemCuoi.adslCap = thongso.port_mdf
      this.thongSoCu.diemCuoi.adslPort = thongso.adsl_port
      if (thongso.selfid01 && thongso.selfid02 && thongso.selfid03 && thongso.selfid04) {
        this.thongSoCu.diemCuoi.nasPortId = `${thongso.system}/${thongso.selfid01}/${thongso.selfid02}/${thongso.selfid03}/${thongso.selfid04}`
      } else {
        this.thongSoCu.diemCuoi.nasPortId = thongso.system
      }
      this.thongSoCu.diemCuoi.timeSlot = thongso.timeslot
      this.thongSoCu.diemCuoi.tbiTruyenDan = thongso.ten_tbi
      this.thongSoCu.diemCuoi.soAo = thongso.ma_lt
      this.thongSoCu.diemCuoi.link = thongso.link
      this.thongSoCu.diemCuoi.serial = thongso.serial
      this.thongSoCu.diemCuoi.vrfVfiId = thongso.vrf
      this.thongSoCu.diemCuoi.ipWan = thongso.ip_wan
      // end du lieu dong dai
      // du lieu cap
      this.thongSoCu.diemCuoi.tuyenCap = thongso.tuyencap
      this.thongSoCu.diemCuoi.lienTu = thongso.lientu
      this.thongSoCu.diemCuoi.tuCapNgon = thongso.tucap_p
      this.thongSoCu.diemCuoi.doiCapNgon = this.convertToNumber(thongso.doicap_p)
      this.thongSoCu.diemCuoi.hopCapNgon = thongso.hopcap_p
      this.thongSoCu.diemCuoi.loaiCapId = thongso.loaicap_id
      this.thongSoCu.diemCuoi.capGoc = thongso.cap_goc
      this.thongSoCu.diemCuoi.capNgon = thongso.cap_p
      this.thongSoCu.diemCuoi.doiCapGoc = this.convertToNumber(thongso.doicap_goc)
      this.thongSoCu.diemCuoi.tuCapGoc = thongso.tucap_goc
      this.thongSoCu.diemCuoi.cuLy = this.convertToNumber(thongso.culy)
      // end du lieu cap
      this.thongSoCu.diemCuoi.thietBiId = thongso.thietbidc_id
      this.thongSoCu.diemCuoi.diaChi = thongso.diachi
      var donviTmp = await service.sp_lay_ds_tramtc_thuebao(1, this.thuebaoId)
      if (donviTmp) {
        this.thongSoCu.diemCuoi.donViId = donviTmp.donvi_id
      }
      this.thongSoMoi.diemCuoi = structuredClone(this.thongSoCu.diemCuoi)
      this.$refs.diemCuoiCu.HienThiThongSo(this.thongSoCu.diemCuoi)
      this.$refs.diemCuoiMoi.HienThiThongSo(this.thongSoMoi.diemCuoi)
    },
    async chonPortDau () {
      try {
        if (!this.thongSoMoi.diemDau.donViId) {
          this.$toast.warning("Chưa có thông tin về trạm viễn thông")
          return
        }
        var dslamId = await service.map_dslam_id(this.thongSoMoi.diemDau.portId)
        var thamso = await service.map_thamso(dslamId)
        var diaChiCuoiId = -1
        if (this.trangThaiDuLieu.isDanhBa) {
          diaChiCuoiId = await service.map_diachi_cuoi_id_danhba(this.thuebaoId)
        } else if (this.trangThaiDuLieu.isHopDong) {
          diaChiCuoiId = await service.map_diachi_cuoi_id_hopdong(this.hdtbId)
          if (diaChiCuoiId == -1) {
            var vthuebaoId = await service.map_thuebaoid(this.hdtbId)
            diaChiCuoiId = await service.map_diachi_cuoi_id_danhba(vthuebaoId)
          }
        }
        this.$refs.frmChonPortDau.frmChonPort(this.thongSoMoi.diemDau.donViId, this.tramTbDau, this.thongSoMoi.diemDau.portId, KIEULAP.THAYDOITSO, this.dichVuVienThongId, this.loaiHinhThueBaoId, diaChiCuoiId, thamso, this.hdtbId, this.thuebaoId, this.maDoiCap, this.tramTbDau)
        this.$refs.frmChonPortDau.show()
      } catch (e) {
        console.error(e)
      }
    },
    async onSelectPortDau (port) {
      this.thongSoMoi.diemDau.adslCap = port.port_mdf
      this.thongSoMoi.diemDau.adslPort = port.vadsl_port
      this.thongSoMoi.diemDau.sVlan = port.vpi
      this.thongSoMoi.diemDau.cvLan = port.vci
      if (port.selfid01 && port.selfid02 && port.selfid03 && port.selfid04) {
        this.thongSoMoi.diemDau.nasPortId = port.system + "/" +
                                port.selfid04 + "/" +
                                port.selfid03 + "/" +
                                port.selfid02 + "/" +
                                port.selfid01
      } else this.thongSoMoi.diemDau.nasPortId = port.system
      this.thongSoMoi.diemDau.portId = port.port_id
      this.thongSoMoi.diemDau.vci_vpi_id = port.vci_vpi_id ? port.vci_vpi_id : 0
      this.tramTbDau = port.tramtb_id ? port.tramtb_id : 0
      this.$refs.diemDauMoi.HienThiThongSo(this.thongSoMoi.diemDau)
    },
    async chonPortCuoi () {
      try {
        if (!this.thongSoMoi.diemCuoi.donViId) {
          this.$toast.warning("Chưa có thông tin về trạm viễn thông")
          return
        }
        var dslamId = await service.map_dslam_id(this.thongSoMoi.diemCuoi.portId)
        var thamso = await service.map_thamso(dslamId)
        var diaChiDauId = -1
        if (this.trangThaiDuLieu.isDanhBa) {
          diaChiDauId = await service.map_diachi_dau_id_danhba(this.thuebaoId)
        } else if (this.trangThaiDuLieu.isHopDong) {
          diaChiDauId = await service.map_diachi_dau_id_hopdong(this.hdtbId)
          if (diaChiDauId == -1) {
            var vthuebaoId = await service.map_thuebaoid(this.hdtbId)
            diaChiDauId = await service.map_diachi_dau_id_danhba(vthuebaoId)
          }
        }
        this.$refs.frmChonPortCuoi.frmChonPort(this.thongSoMoi.diemCuoi.donViId, this.tramTbCuoi, this.thongSoMoi.diemCuoi.portId, KIEULAP.THAYDOITSO, this.dichVuVienThongId, this.loaiHinhThueBaoId, diaChiDauId, thamso, this.hdtbId, this.thuebaoId, this.maDoiCap, this.tramTbCuoi)
        this.$refs.frmChonPortCuoi.show()
      } catch (e) {
        console.error(e)
      }
    },
    async onSelectPortCuoi (port) {
      this.thongSoMoi.diemCuoi.adslCap = port.port_mdf
      this.thongSoMoi.diemCuoi.adslPort = port.vadsl_port
      this.thongSoMoi.diemCuoi.sVlan = port.vpi
      this.thongSoMoi.diemCuoi.cvLan = port.vci
      if (port.selfid01 && port.selfid02 && port.selfid03 && port.selfid04) {
        this.thongSoMoi.diemCuoi.nasPortId = port.system + "/" +
                                port.selfid04 + "/" +
                                port.selfid03 + "/" +
                                port.selfid02 + "/" +
                                port.selfid01
      } else this.thongSoMoi.diemCuoi.nasPortId = port.system
      this.thongSoMoi.diemCuoi.portId = port.port_id
      this.thongSoMoi.diemCuoi.vci_vpi_id = port.vci_vpi_id ? port.vci_vpi_id : 0
      this.tramTbCuoi = port.tramtb_id ? port.tramtb_id : 0
      this.$refs.diemCuoiMoi.HienThiThongSo(this.thongSoMoi.diemCuoi)
    },
    async CapNhat () {
      try {
        this.loading(true)
        if (!this.isSuaDiemDau && !this.isSuaDiemCuoi) {
          this.$toast.warning("Bạn chưa chọn điểm đầu hoặc cuối để thay đổi !")
          return
        }
        var isValid1 = true
        var isValid2 = true
        isValid1 = this.$refs.diemDauMoi.validInput(false, "Điểm đầu: ")
        var isFocused = this.isSuaDiemDau && !isValid1
        isValid2 = this.$refs.diemCuoiMoi.validInput(isFocused, "Điểm cuối: ")
        if (!isValid1 || !isValid2) return
        var result = null
        result = await this.InsertThayDoiTT()
        if (result.error_msg) {
          this.$toast("Có lỗi xảy ra khi insert lên hệ thống " + result.error_msg)
          return
        }
        result = await this.CapNhat_Doi_ThongSoKt_TSL()
        if (result.error_msg) {
          this.$toast("Có lỗi xảy ra khi cập nhật thay đổi thông số lên hệ thống " + result.error_msg)
          return
        }
        this.$toast.success("Cập nhật thông số trên dữ liệu viễn thông tỉnh thành công")
        await this.onMaTbEnter()
      } catch (e) {
        this.$toast.error("Có lỗi xảy ra " + e.message)
      } finally {
        this.loading(false)
      }
    },
    async InsertThayDoiTT () {
      try {
        var result = {error_msg: null, data: null}
        var dulieuTsl = await this.TaoDuLieu_TSL()
        if (dulieuTsl.dau) {
          result = await service.sp_insert_tdtt_db_tsl(
            {"p_ds_para": JSON.stringify(dulieuTsl.dau)})
          if (result.error_msg) {
            return result
          }
        }
        if (dulieuTsl.cuoi) {
          result = await service.sp_insert_tdtt_db_tsl(
            {"p_ds_para": JSON.stringify(dulieuTsl.cuoi)})
        }
        return result
      } catch (e) {
        console.log(e)
        throw e
      }
    },
    async TaoDuLieu_TSL () {
      var tdtt_db_id = await service.get_keys("TDTT_DB_ID")
      var currentDate = moment(new Date()).format("DD/MM/YYYY HH:mm:ss")
      var rowDau = {}
      var rowCuoi = {}
      var dulieuTSL = {
        dau: null,
        cuoi: null
      }
      if (this.isSuaDiemDau) {
        var diemDauMoi = this.$refs.diemDauMoi
        rowDau.THUEBAO_ID = this.thuebaoId
        rowDau.TDTT_DB_ID = tdtt_db_id
        rowDau.MA_TB = this.maTb
        if (this.trangThaiDuLieu.isDanhBa) { rowDau.TRANGTHAI_ID = 1 } else { rowDau.TRANGTHAI_ID = 2 }
        rowDau.NHANVIEN_ID = this.$root.token.getNhanVienID()
        rowDau.DONVI_ID = this.$root.token.getDonViID()
        rowDau.NGAY_DK = currentDate
        rowDau.NGAY_HT = currentDate
        rowDau.GHICHU = ""
        rowDau.MAY_CN = this.$root.token.getNhanVienID()
        rowDau.NGAY_CN = currentDate
        rowDau.NGUOI_CN = this.$root.token.getUserName()
        rowDau.TEN_TBI = diemDauMoi.tbiTruyenDan
        rowDau.MA_PL = this.soPLHDCu
        rowDau.MA_PL_CU = this.soPLHDMoi
        rowDau.TBIDC_DAU_ID = diemDauMoi.thietBiId
        rowDau.TBIDC_DAU_ID_CU = this.thongSoCu.diemDau.thietBiId
        rowDau.BRAS_ID = ""
	      rowDau.DSLAM_ID = ""
        rowDau.PORT_ID = diemDauMoi.portId
        rowDau.VCI_VPI_ID = diemDauMoi.vci_vpi_id
        rowDau.SLOT = ""
        rowDau.PORT = ""
        rowDau.TIMESLOT = diemDauMoi.timeSlot
        rowDau.LINK = diemDauMoi.link
        rowDau.IP_WAN = diemDauMoi.ipWan
        rowDau.VRF = diemDauMoi.sVlan
        rowDau.TUYENCAP = diemDauMoi.tuyenCap
        rowDau.LIENTU = diemDauMoi.lienTu
        rowDau.LOAICAP_ID = diemDauMoi.loaiCapId
        rowDau.TUCAP_GOC = diemDauMoi.tuCapGoc
        rowDau.DOICAP_GOC = diemDauMoi.doiCapGoc ? diemDauMoi.doiCapGoc : -1
        rowDau.CAP_GOC = diemDauMoi.capGoc
        rowDau.TUCAP_P = diemDauMoi.tuCapGoc
        rowDau.HOPCAP_P = diemDauMoi.hopCapNgon
        rowDau.DOICAP_P = diemDauMoi.doiCapNgon ? diemDauMoi.doiCapNgon : -1
        rowDau.CAP_P = diemDauMoi.capNgon
        rowDau.CAP_ID = ""
        rowDau.DOICAP = ""
        rowDau.SERIAL = diemDauMoi.serial
        rowDau.CULY = diemDauMoi.cuLy ? diemDauMoi.cuLy : 0
        rowDau.TEN_TBI_CU = this.thongSoCu.diemDau.tbiTruyenDan
        rowDau.PORT_ID_CU = this.thongSoCu.diemDau.portId
        rowDau.VCI_VPI_ID_CU = this.thongSoCu.diemDau.vci_vpi_id
        rowDau.TIMESLOT_CU = this.thongSoCu.diemDau.timeSlot
        rowDau.LINK_CU = this.thongSoCu.diemDau.link
        rowDau.IP_WAN_CU = this.thongSoCu.diemDau.ipWan
        rowDau.VRF_CU = this.thongSoCu.diemDau.sVlan
        rowDau.TUYENCAP_CU = this.thongSoCu.diemDau.tuyenCap
        rowDau.LIENTU_CU = this.thongSoCu.diemDau.lienTu
        rowDau.LOAICAP_ID_CU = this.thongSoCu.diemDau.loaiCapId
        rowDau.TUCAP_GOC_CU = this.thongSoCu.diemDau.tuCapGoc
        rowDau.DOICAP_GOC_CU = this.thongSoCu.diemDau.doiCapGoc ? this.thongSoCu.diemDau.doiCapGoc : -1
        rowDau.CAP_GOC_CU = this.thongSoCu.diemDau.capGoc
        rowDau.TUCAP_P_CU = this.thongSoCu.diemDau.tuCapNgon
        rowDau.HOPCAP_P_CU = this.thongSoCu.diemDau.hopCapNgon
        rowDau.DOICAP_P_CU = this.thongSoCu.diemDau.doiCapNgon ? this.thongSoCu.diemDau.doiCapNgon : -1
        rowDau.CAP_P_CU = this.thongSoCu.diemDau.capNgon
        rowDau.SERIAL_CU = this.thongSoCu.diemDau.serial
        rowDau.CULY_CU = this.thongSoCu.diemDau.cuLy ? this.thongSoCu.diemDau.cuLy : 0
        rowDau.DAUCUOI_ID = 1
        dulieuTSL.dau = rowDau
      }
      if (this.isSuaDiemCuoi) {
        var diemCuoiMoi = this.$refs.diemCuoiMoi
        rowCuoi.THUEBAO_ID = this.thuebaoId
        rowCuoi.TDTT_DB_ID = tdtt_db_id
        rowCuoi.MA_TB = this.maTb
        if (this.trangThaiDuLieu.isDanhBa) { rowCuoi.TRANGTHAI_ID = 1 } else { rowCuoi.TRANGTHAI_ID = 2 }
        rowCuoi.NHANVIEN_ID = this.$root.token.getNhanVienID()
        rowCuoi.DONVI_ID = this.$root.token.getDonViID()
        rowCuoi.NGAY_DK = currentDate
        rowCuoi.NGAY_HT = currentDate
        rowCuoi.MAY_CN = this.$root.token.getNhanVienID()
        rowCuoi.NGAY_CN = currentDate
        rowCuoi.NGUOI_CN = this.$root.token.getUserName()
        rowCuoi.MA_PL = this.soPLHDCu
        rowCuoi.MA_PL_CU = this.soPLHDMoi
        rowCuoi.TBIDC_DAU_ID = diemCuoiMoi.thietBiId
        rowCuoi.TBIDC_DAU_ID_CU = this.thongSoCu.diemCuoi.thietBiId
        rowCuoi.TEN_TBI = diemCuoiMoi.tbiTruyenDan
        rowCuoi.PORT_ID = diemCuoiMoi.portId
        rowCuoi.VCI_VPI_ID = this.thongSoCu.diemCuoi.vci_vpi_id
        rowCuoi.TIMESLOT = diemCuoiMoi.timeSlot
        rowCuoi.LINK = diemCuoiMoi.link
        rowCuoi.IP_WAN = diemCuoiMoi.ipWan
        rowCuoi.VRF = diemCuoiMoi.sVlan
        rowCuoi.TUYENCAP = diemCuoiMoi.tuyenCap
        rowCuoi.LIENTU = diemCuoiMoi.lienTu
        rowCuoi.LOAICAP_ID = diemCuoiMoi.loaiCapId
        rowCuoi.TUCAP_GOC = diemCuoiMoi.tuCapGoc
        rowCuoi.DOICAP_GOC = diemCuoiMoi.doiCapGoc ? diemCuoiMoi.doiCapGoc : -1
        rowCuoi.CAP_GOC = diemCuoiMoi.capGoc
        rowCuoi.TUCAP_P = diemCuoiMoi.tuCapGoc
        rowCuoi.HOPCAP_P = diemCuoiMoi.hopCapNgon
        rowCuoi.DOICAP_P = diemCuoiMoi.doiCapNgon ? diemCuoiMoi.doiCapNgon : -1
        rowCuoi.CAP_P = diemCuoiMoi.capNgon
        rowCuoi.SERIAL = diemCuoiMoi.serial
        rowCuoi.CULY = diemCuoiMoi.cuLy ? diemCuoiMoi.cuLy : 0
        rowCuoi.TEN_TBI_CU = this.thongSoCu.diemCuoi.tbiTruyenDan
        rowCuoi.PORT_ID_CU = this.thongSoCu.diemCuoi.portId
        rowCuoi.VCI_VPI_ID_CU = this.thongSoCu.diemCuoi.vci_vpi_id
        rowCuoi.TIMESLOT_CU = this.thongSoCu.diemCuoi.timeSlot
        rowCuoi.LINK_CU = this.thongSoCu.diemCuoi.link
        rowCuoi.IP_WAN_CU = this.thongSoCu.diemCuoi.ipWan
        rowCuoi.VRF_CU = this.thongSoCu.diemCuoi.sVlan
        rowCuoi.TUYENCAP_CU = this.thongSoCu.diemCuoi.tuyenCap
        rowCuoi.LIENTU_CU = this.thongSoCu.diemCuoi.lienTu
        rowCuoi.LOAICAP_ID_CU = this.thongSoCu.diemCuoi.loaiCapId
        rowCuoi.TUCAP_GOC_CU = this.thongSoCu.diemCuoi.tuCapGoc
        rowCuoi.DOICAP_GOC_CU = this.thongSoCu.diemCuoi.doiCapGoc ? this.thongSoCu.diemCuoi.doiCapGoc : -1
        rowCuoi.CAP_GOC_CU = this.thongSoCu.diemCuoi.capGoc
        rowCuoi.TUCAP_P_CU = this.thongSoCu.diemCuoi.tuCapNgon
        rowCuoi.HOPCAP_P_CU = this.thongSoCu.diemCuoi.hopCapNgon
        rowCuoi.DOICAP_P_CU = this.thongSoCu.diemCuoi.doiCapNgon ? this.thongSoCu.diemCuoi.doiCapNgon : -1
        rowCuoi.CAP_P_CU = this.thongSoCu.diemCuoi.capNgon
        rowCuoi.SERIAL_CU = this.thongSoCu.diemCuoi.serial
        rowCuoi.CULY_CU = this.thongSoCu.diemCuoi.cuLy ? this.thongSoCu.diemCuoi.cuLy : 0
        rowCuoi.DAUCUOI_ID = 2
        dulieuTSL.cuoi = rowCuoi
      }
      return dulieuTSL
    },
    async CapNhat_Doi_ThongSoKt_TSL () {
      try {
        var diemDauMoi = this.$refs.diemDauMoi
        var diemCuoiMoi = this.$refs.diemCuoiMoi
        var body = {
          "HDTB_ID": this.hdtbId,
          "THUEBAO_ID": this.thuebaoId,
          "TRAMTB_DAU_ID": this.tramTbDau,
          "PORT_DAU_ID": diemDauMoi.portId,
          "VCI_VPI_DAU_ID": diemDauMoi.vci_vpi_id,
          "TXTTBI_DAU_MOI": diemDauMoi.tbiTruyenDan,
          "TXTTIMESLOT_DAU_MOI": diemDauMoi.timeSlot,
          "TXTLINK_DAU_MOI": diemDauMoi.link,
          "TXTIPWAN_DAU_MOI": diemDauMoi.ipWan,
          "TXTVRF_DAU_MOI": diemDauMoi.vrfVfiId,
          "TXTTUYENCAP_DAU_MOI": diemDauMoi.tuyenCap,
          "TXTLIENTU_DAU_MOI": diemDauMoi.lienTu,
          "CBOLOAICAP_DAU_MOI": diemDauMoi.loaiCapId,
          "TXTTUCAPGOC_DAU_MOI": diemDauMoi.tuCapGoc,
          "TXTCAPGOC_DAU_MOI": diemDauMoi.capGoc,
          "TXTTUCAPNGON_DAU_MOI": diemDauMoi.tuCapNgon,
          "TXTHOPCAPNGON_DAU_MOI": diemDauMoi.hopCapNgon,
          "TXTCAPNGON_DAU_MOI": diemDauMoi.capNgon,
          "TXTSERIAL_DAU_MOI": diemDauMoi.serial,
          "TRANGTHAI_DL": this.trangThaiDuLieu.isDanhBa ? 1 : 0,
          "CBOTHIETBIDAU_MOI": diemDauMoi.thietBiId,
          "TXTSOPL_MOI": this.soPLHDMoi,
          "TRAMTB_CUOI_ID": this.tramTbCuoi,
          "PORT_CUOI_ID": diemCuoiMoi.portId,
          "VCI_VPI_CUOI_ID": diemCuoiMoi.vci_vpi_id,
          "TXTTBI_CUOI_MOI": diemCuoiMoi.tbiTruyenDan,
          "TXTTIMESLOT_CUOI_MOI": diemCuoiMoi.timeSlot,
          "TXTLINK_CUOI_MOI": diemCuoiMoi.link,
          "TXTIPWAN_CUOI_MOI": diemCuoiMoi.ipWan,
          "TXTVRF_CUOI_MOI": diemCuoiMoi.vrfVfiId,
          "TXTTUYENCAP_CUOI_MOI": diemCuoiMoi.tuyenCap,
          "TXTLIENTU_CUOI_MOI": diemCuoiMoi.lienTu,
          "CBOLOAICAP_CUOI_MOI": diemCuoiMoi.loaiCapId,
          "TXTTUCAPGOC_CUOI_MOI": diemCuoiMoi.tuCapGoc,
          "TXTCAPGOC_CUOI_MOI": diemCuoiMoi.capGoc,
          "TXTTUCAPNGON_CUOI_MOI": diemCuoiMoi.tuCapNgon,
          "TXTHOPCAPNGON_CUOI_MOI": diemCuoiMoi.hopCapNgon,
          "TXTCAPNGON_CUOI_MOI": diemCuoiMoi.capNgon,
          "TXTSERIAL_CUOI_MOI": diemCuoiMoi.serial,
          "CBOTHIETBICUOI_MOI": diemCuoiMoi.thietBiId,
          "TXTDOICAPNGON_DAU_MOI": diemDauMoi.doiCapNgon,
          "TXTDOICAPGOC_DAU_MOI": diemDauMoi.doiCapGoc,
          "TXTSOMETDAY_DAU_MOI": diemDauMoi.cuLy,
          "TXTSOMETDAY_CUOI_MOI": diemCuoiMoi.cuLy,
          "TXTDOICAPNGON_CUOI_MOI": diemCuoiMoi.doiCapNgon,
          "TXTDOICAPGOC_CUOI_MOI": diemCuoiMoi.doiCapGoc,
          "TXTMATB": this.maTb,
          "CHKDIEMDAU": this.isSuaDiemDau ? 1 : 0,
          "PORT_DAU_ID_CU": this.thongSoCu.diemDau.portId,
          "VCI_VPI_DAU_ID_CU": this.thongSoCu.diemDau.vci_vpi_id,
          "CHKDIEMCUOI": this.isSuaDiemCuoi ? 1 : 0,
          "PORT_CUOI_ID_CU": this.thongSoCu.diemCuoi.portId,
          "VCI_VPI_CUOI_ID_CU": this.thongSoCu.diemCuoi.vci_vpi_id,
          "NGUOI_CN": this.$root.token.getNhanVienID(),
          "MAY_CN": this.$root.token.getNhanVienID()
        }
        console.log("fn_capnhat_port_thaydoi_tskt_tsl json", body)
        var result
        if(!this.isPopup){
          result = await service.fn_capnhat_port_thaydoi_tskt_tsl(
            {p_ds_para: JSON.stringify(body)}
          )
        }else{
          body.ID_ROW=this.row_id
          result = await service.fn_capnhat_port_thaydoi_tskt_tsl_v2(
            {p_ds_para: JSON.stringify(body)}
          )
        }
        
        return result
      } catch (e) {
        console.log(e)
        throw e
      }
    },
    Huy () {
      this.SetButton(1)
      this.maTb = ""
    }
  }
}
</script>