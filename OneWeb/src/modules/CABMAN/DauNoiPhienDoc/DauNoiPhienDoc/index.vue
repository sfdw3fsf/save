<template src="./index.html"></template>
<script>
import { L10n } from '@syncfusion/ej2-base'
import { DropDownTreePlugin } from "@syncfusion/ej2-vue-dropdowns";
import QuanLyCap from "../../QLCap/QLCap"
import QuanLyDayNhay from "../../QLCap/QLCapTD"
L10n.load({
  'vi-VN': {
    grid: {
      EmptyRecord: 'Không có bản ghi nào để hiển thị'
    }
  }
})
var columnTemplateChonNhieuPortDV = function () {
  return {
    template: {
      template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.DVChecked"
                                    :value="data.CONG_VAO"/>
                                <span class="name"></span>
                            </div>
                        `,
      data() {
        return {
          data: { }
        }
      },
      computed: {
        parent() {
          return this.$parent.$parent.$parent.$parent.$parent
        },
      }
    }
  }
}
var columnTemplateChonNhieuPortDR = function () {
  return {
    template: {
      template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.DRChecked"
                                    :value="data.CONG_RA"/>
                                <span class="name"></span>
                            </div>
                        `,
      data() {
        return {
          data: { }
        }
      },
      computed: {
        parent() {
          return this.$parent.$parent.$parent.$parent.$parent
        },
      }
    }
  }
}
var ghiChuMaKetNoitemplate= function () {
  return {
    template: {
      template: `
      <div style="display: inline-flex;align-items: center;justify-content: space-around;">
      <div class="pull-left" style="margin-right: 3px" @click="onClickXoaMaKetNoi">
        <button class="btn btn-second lh12 pad2" >
          <span class="-ap icon-close"></span>
        </button>
      </div>
          <input type="text"  class="form-control" v-model="data.MA_KN" :title="data.MA_KN" style="text-overflow: ellipsis;">
                                                <div style="margin-left: 1px; margin-left: 3px" class="pull-right" @click="onClickCapNhatMatKetNoi">
                                                    <button class="btn btn-second lh12 pad2" >
                                                        <span class="-ap icon-check3"></span>
                                                    </button>
                                                </div>
                        </div>`,
      data() {
        return {
          data: { }
        }
      },
      computed: {
        parent() {
          return this.$parent.$parent.$parent.$parent.$parent
        },
      },methods:{
        onClickXoaMaKetNoi() {
          this.parent.UpdateGhiChuMaKetNoi(this.data.CONG_VAO,null)
        },
        onClickCapNhatMatKetNoi(){
          let ma_kn = this.data.MA_KN.trim("-")
          console.log(ma_kn)
          this.parent.UpdateGhiChuMaKetNoi(this.data.CONG_VAO,ma_kn)
        }
      }
    }
  }
}
var capvaoTemplate = function () {
  return {
    template: {
      template: `
      <div>
            <div class="pull-left" style="width: calc(100% - 25px);display: block;overflow: hidden;text-overflow: ellipsis;" :title="data.CAP_VAO ? data.CAP_VAO : ''"> {{data.CAP_VAO ? data.CAP_VAO : ""}}</div>
                                                <div class="pull-right" @click="onClick">
                                                    <button class="btn btn-second lh12 pad2" >
                                                        <span class="-ap icon-more_horiz"></span>
                                                    </button>
                                                </div>
                        </div>`,
      data() {
        return {
          data: { }
        }
      },
      computed: {
        parent() {
          return this.$parent.$parent.$parent.$parent.$parent
        },
        id(){
          return `${this.data.CONG_VAO}`
        }
      },
      methods:{
        onClick() {
          this.parent.xemPopupQuanLyCap(this.data.CAPVAO_ID)
        }
      }
    }
  }
}
var capraTemplate = function () {
  return {
    template: {
      template: `
      <div>
            <div class="pull-left" style="width: calc(100% - 25px);display: block;overflow: hidden;text-overflow: ellipsis;" :title="data.CAP_RA ? data.CAP_RA : ''"> {{data.CAP_RA?data.CAP_RA:""}}</div>
                                                <div class="pull-right" @click="onClick">
                                                    <button class="btn btn-second lh12 pad2" >
                                                        <span class="-ap icon-more_horiz"></span>
                                                    </button>
                                                </div>
                        </div>`,
      data() {
        return {
          data: { }
        }
      },
      computed: {
        parent() {
          return this.$parent.$parent.$parent.$parent.$parent
        },
        id(){
          return `${this.data.CONG_RA}`
        }
      },methods:{
        onClick() {
          this.parent.xemPopupQuanLyCap(this.data.CAP_ID)
        }
      }
    }
  }
}
export default {
  components:{ QuanLyCap, QuanLyDayNhay,DropDownTreePlugin},
  name: "DauNoiPhienDoc",
  props:{
    phiendocId:Number,
    id:String
  },
  data:function (){
    return {
      isShown: false,
      showGrid:false,
      kyHieuDayNhay: '', // popupDauNhay
      thongTinPhienDoc:{},
      dsDauNoi: [],
      //Tab đầu vào-ra active: Mã kết nối :MKN, Dây nhảy:DN, Thiết bị:TB, Cáp gốc: CG
      tab_dv_active:'MKN',
      tab_dr_active:'MKN',

      //Xem thông tin cáp grid ở popup Quản lý cáp
      capObj:{},
      //Xem thông tin quản lý dây nhảy
      editable:false, // true: XemTT tab Dây nhảy, false: xem ở grid

      cellIndex:null, // column index

      // Đấu nối check
      DVChecked:[],
      DRChecked:[],
      //Soi vao-ra
      lblCongSoiVao:'Mã kết nối',
      lblCongSoiRa:'Mã kết nối',
      fieldsThietBiDv3: {},
      fieldsThietBiDr3: {},

      DAUNOI_DV: {
        MAKETNOI:{
          MKNDvCheck:[],
          cboLoaiTbiDv1:null,
          cboThietBiDv1:null,

          dsLoaiTbiDv1:[],
          dsThietbiDv1:[],
          dsMaKetNoiDv:[],
        },
        DAYNHAY:{
          DNDvCheck:[],

          cboDayNhayDv:null,

          dsDayNhayDv:[],
          dsSoiDayNhayDv:[],
        },
        THIETBI:{
          TBDvCheck: [],
          cboLoaiTbiDv2:null,
          cboThietBiDv2:null,
          cboThietBiDv3:null,

          dsLoaiTbiDv2:[],
          dsThietbiDv2:[],
          dsThietbiDv3:[],
          dsCongThietBiDv:[],
        }
      },
      DAUNOI_DR: {
        MAKETNOI:{
          MKNDrCheck:[],
          cboLoaiTbiDr1:null,
          cboThietBiDr1:null,

          dsLoaiTbiDr1:[],
          dsThietbiDr1:[],
          dsMaKetNoiDr:[],
        },
        DAYNHAY:{
          DNDrCheck:[],
          dsDayNhayDr:[],

          cboDayNhayDr: null,
          dsSoiDayNhayDr:[],
        },
        THIETBI:{
          TBDrCheck:[],
          cboLoaiTbiDr2: null,
          cboThietBiDr2:null,
          cboThietBiDr3:null,

          dsLoaiTbiDr2:[],
          dsThietbiDr2:[],
          dsThietbiDr3:[],
          dsCongThietBiDr:[],
        },
        CAPGOC:{
          CGDrCheck:[],
          cboCapGocDr: null,

          dsCapGocDr:[],
          dsSoiCapGocDr:[],
        }
      },

      DVColumns:[
        {
          customAttributes:{hidden:true},
          columns:[
            {
              field: 'CAP_VAO',
              headerText: 'Cáp/Dây nhảy vào',
              template:capvaoTemplate,
              width: '15%',
              textAlign: 'Center',
              minWidth: 10
            },
            {
              field: 'SOI_VAO',
              headerText: 'Sợi',
              width: '10%',
              textAlign: 'Center',
              minWidth: 10
            },
          ],
        },
      ],
      phiendocColumns:[
        {
          headerText: 'Mặt trước',
          textAlign: 'Center',
          columns:[
            {
              field: 'CHON_VAO',
              headerText: 'Chọn',
              template: columnTemplateChonNhieuPortDV,
              width: '10%',
              textAlign: 'Center',
              minWidth: 10
            },
            {
              field: 'CONG_VAO',
              headerText: 'Vị trí vào',
              width: '10%',
              textAlign: 'Center',
              minWidth: 10
            },
          ]
        },
        {
          headerText: 'Thông tin cổng',
          textAlign: 'Center',
          columns:[
            {
              field: 'MA_KN',
              headerText: 'Ghi chú mã kết nối',
              template:ghiChuMaKetNoitemplate,
              width: '15%',
              textAlign: 'Center',
              minWidth: 10
            },
          ]
        },
        {
          headerText: 'Mặt sau',
          textAlign: 'Center',
          columns:[
            {
              field: 'CONG_RA',
              headerText: 'Vị trí ra',
              width: '10%',
              textAlign: 'Center',
              minWidth: 10
            },
            {
              field: 'CHON_RA',
              headerText: 'Chọn',
              template: columnTemplateChonNhieuPortDR,
              width: '10%',
              textAlign: 'Center',
              minWidth: 10
            },
          ]
        }
      ],
      DRColumns:[
        {
          customAttributes:{hidden:true},
          columns:[
            {
              field: 'CAP_RA',
              headerText: 'Cáp/Dây nhảy ra',
              template:capraTemplate,
              width: '15%',
              textAlign: 'Center',
              minWidth: 10
            },
            {
              field: 'SOI_RA',
              headerText: 'Sợi',
              width: '10%',
              textAlign: 'Center',
              minWidth: 10
            },
          ]
        }
      ],
    }
  },
  watch:{
    'DAUNOI_DV.MAKETNOI.cboLoaiTbiDv1': async function (value){
      if (!this.isShown) return // ignore close model
      try {
        this.loading(true)
        await this.getDsThietbiDv1()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    'DAUNOI_DV.MAKETNOI.cboThietBiDv1': async function (value){
      if (!this.isShown) return // ignore close model
      try {
        this.loading(true)
        await this.getDsMaKetNoiDv()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    'DAUNOI_DV.DAYNHAY.cboDayNhayDv': async function (value){
      if (!this.isShown) return // ignore close model
      try {
        this.loading(true)
        await this.getDsSoiDayNhayDv()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    'DAUNOI_DV.THIETBI.cboLoaiTbiDv2': async function (value){
      if (!this.isShown) return // ignore close model
      try {
        this.loading(true)
        await this.getDsThietbiDv2()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    'DAUNOI_DV.THIETBI.cboThietBiDv2': async function (value){
      if (!this.isShown) return // ignore close model
      try {
        this.loading(true)
        await this.getDsThietbiDv3()
        this.DAUNOI_DV.THIETBI.cboThietBiDv3 = null
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    'DAUNOI_DV.THIETBI.cboThietBiDv3': async function (value){
      if (!this.isShown) return // ignore close model
      try {
        this.loading(true)
        await this.getDsCongThietbiDv()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    'DAUNOI_DR.MAKETNOI.cboLoaiTbiDr1': async function (value){
      if (!this.isShown) return // ignore close model
      try {
        this.loading(true)
        await this.getDsThietbiDr1()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    'DAUNOI_DR.MAKETNOI.cboThietBiDr1': async function (value){
      if (!this.isShown) return // ignore close model
      try {
        this.loading(true)
        await this.getDsMaKetNoiDr()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    'DAUNOI_DR.DAYNHAY.cboDayNhayDr': async function (value){
      if (!this.isShown) return // ignore close model
      try {
        this.loading(true)
        await this.getDsSoiDayNhayDr()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    'DAUNOI_DR.THIETBI.cboLoaiTbiDr2': async function (value){
      if (!this.isShown) return // ignore close model
      try {
        this.loading(true)
        await this.getDsThietbiDr2()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    'DAUNOI_DR.THIETBI.cboThietBiDr2': async function (value){
      if (!this.isShown) return // ignore close model
      try {
        this.loading(true)
        await this.getDsThietbiDr3()
        this.DAUNOI_DR.THIETBI.cboThietBiDr3 = null
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    'DAUNOI_DR.THIETBI.cboThietBiDr3': async function (value){
      if (!this.isShown) return // ignore close model
      try {
        this.loading(true)
        await this.getDsCongThietbiDr()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    'DAUNOI_DR.CAPGOC.cboCapGocDr': async function (value){
      if (!this.isShown) return // ignore close model
      try {
        this.loading(true)
        await this.getDsSoiCapGocDr()
      }catch (e){}
      finally {
        this.loading(false)
      }
    }
  },
  destroyed() {
    this.clearForm()
  },
  methods: {
    onHiddenModal() {
      this.isShown = false
      this.showGrid = false
      this.clearForm()
    },
    async onShownModal() {
      this.isShown = true
      this.showGrid = true

      try {
        // this.loading(true)
        this.$root.showLoading(true)
        await this.getThongTinPhienDoc()
        if (this.thongTinPhienDoc === null && this.thongTinPhienDoc === {}){
          this.$root.toastError("Không tìm thấy thông tin phiến dọc")
          this.onHiddenModal()
        }
        if (this.$root.token.getMaTinh() !== 'HPG'){
          //hidden tag Mã kết nối
          //default chọn tag DV: Thiết bị, DR:Cáp gốc
          await this.onClickTabDv("TB")
          await this.onClickTabDr("CG")
        }else {
          //default chọn tag Mã kết nối
          await this.onClickTabDv("MKN")
          await this.onClickTabDr("MKN")
        }

        await this.getDsDauNoi()


      } catch (e) {
      }
      finally {
        this.$root.showLoading(false)
      }
    },
    async onClickTabDv(tab){
      this.lblCongSoiVao = ''
      this.DAUNOI_DV.MAKETNOI.MKNDvCheck = []
      this.DAUNOI_DV.DAYNHAY.DNDvCheck = []
      this.DAUNOI_DV.THIETBI.TBDvCheck = []
      this.tab_dv_active = tab
      try {
        this.loading(true)
        switch (this.tab_dv_active){
          case "MKN":
            this.lblCongSoiVao = "Mã kết nối"
            await this.getDsLoaiTbiDv1()
            await this.getDsThietbiDv1()
            await this.getDsMaKetNoiDv()
            break
          case "DN":
            this.lblCongSoiVao = "Đôi cáp"
            await this.getDsDayNhayDv()
            await this.getDsSoiDayNhayDv()
            break
          case "TB":
            this.lblCongSoiVao = "Cổng"
            await this.getDsLoaiTbiDv2()
            await this.getDsThietbiDv2()
            await this.getDsThietbiDv3()
            await this.getDsCongThietbiDv()
        }
      }catch (e) {

      }finally {
        this.loading(false)
      }
    },
    async onClickTabDr(tab){
      this.lblCongSoiRa = ''
      this.DAUNOI_DR.MAKETNOI.MKNDrCheck = []
      this.DAUNOI_DR.DAYNHAY.DNDrCheck = []
      this.DAUNOI_DR.THIETBI.TBDrCheck = []
      this.tab_dr_active = tab
      try {
        this.loading(true)
        switch (this.tab_dr_active){
          case "MKN":
            this.lblCongSoiRa = "Mã kết nối"
            await this.getDsLoaiTbiDr1()
            await this.getDsThietbiDr1()
            await this.getDsMaKetNoiDr()
            break
          case "DN":
            this.lblCongSoiRa = "Đôi cáp"
            await this.getDsDayNhayDr()
            await this.getDsSoiDayNhayDr()
            break
          case "TB":
            this.lblCongSoiRa = "Cổng"
            await this.getDsLoaiTbiDr2()
            await this.getDsThietbiDr2()
            await this.getDsThietbiDr3()
            await this.getDsCongThietbiDr()
            break
          case "CG":
            this.lblCongSoiRa = "Đôi Cáp"
            await this.getDsCapGocDr()
            await this.getDsSoiCapGocDr()
        }
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    // ĐẤU NỐI TỰ ĐỘNG - HỖ TRỢ ĐẤU NỐI
    btnDauNoi(){
      if (!this.cellIndex){
        return
      }
      let min
      // columns index : <4 DAUVAO, >4 DAURA
      if (this.cellIndex <= 4){ // in CAP_VAO, SOI_VAO, CONG_VAO, CHON_VAO
        this.DVChecked = []
        switch (this.tab_dv_active) {
          case "MKN":
            min = Math.min(this.DAUNOI_DV.MAKETNOI.dsMaKetNoiDv.length,this.dsDauNoi.length)
            this.DAUNOI_DV.MAKETNOI.MKNDvCheck = this.DAUNOI_DV.MAKETNOI.dsMaKetNoiDv.filter((item,index) => index < min).map(item => item.PORTVL_ID)
            break
          case "DN":
            min = Math.min(this.DAUNOI_DV.DAYNHAY.dsDayNhayDv.length, this.dsDauNoi.length)
            this.DAUNOI_DV.DAYNHAY.DNDvCheck = this.DAUNOI_DV.DAYNHAY.dsSoiDayNhayDv.filter((item,index) => index < min).map(item => item.SOICAP)
            break
          case "TB":
            min = Math.min(this.DAUNOI_DV.THIETBI.dsCongThietBiDv.length,this.dsDauNoi.length)
            this.DAUNOI_DV.THIETBI.TBDvCheck = this.DAUNOI_DV.THIETBI.dsCongThietBiDv.filter((item,index) => index < min).map(item => item.VITRI)
        }
        for (let i = 0, j = 0; i < this.dsDauNoi.length, j < min; i++){
          if (this.dsDauNoi[i].SOI_VAO === null){
            this.DVChecked.push(this.dsDauNoi[i].CONG_VAO)
            j++
          }
        }
      }else {
        this.DRChecked = []
        switch (this.tab_dr_active) {
          case "MKN":
            min = Math.min(this.DAUNOI_DR.MAKETNOI.dsMaKetNoiDr.length,this.dsDauNoi.length)
            this.DAUNOI_DR.MAKETNOI.MKNDrCheck = this.DAUNOI_DR.MAKETNOI.dsMaKetNoiDr.filter((item,index) => index < min).map(item => item.PORTVL_ID)
            break
          case "DN":
            min = Math.min(this.DAUNOI_DR.DAYNHAY.dsDayNhayDr.length, this.dsDauNoi.length)
            this.DAUNOI_DR.DAYNHAY.DNDrCheck = this.DAUNOI_DR.DAYNHAY.dsSoiDayNhayDr.filter((item,index) => index < min).map(item => item.SOICAP)
            break
          case "TB":
            min = Math.min(this.DAUNOI_DR.THIETBI.dsCongThietBiDr.length,this.dsDauNoi.length)
            this.DAUNOI_DR.THIETBI.TBDrCheck = this.DAUNOI_DR.THIETBI.dsCongThietBiDr.filter((item,index) => index < min).map(item => item.VITRI)
            break
          case "CG":
            min = Math.min(this.DAUNOI_DR.CAPGOC.dsSoiCapGocDr.length,this.dsDauNoi.length)
            this.DAUNOI_DR.CAPGOC.CGDrCheck = this.DAUNOI_DR.CAPGOC.dsSoiCapGocDr.filter((item,index) => index < min).map(item => item.SOICAP)
        }
        for (let i = 0, j = 0; i < this.dsDauNoi.length, j < min; i++){
          if (this.dsDauNoi[i].SOI_RA === null){
            this.DRChecked.push(this.dsDauNoi[i].CONG_RA)
            j++
          }
        }
      }
    },
    // ĐẤU DÂY NHẢY
    btnDauDayNhay(){
      // KIEMTRA_DAU_DAYNHAY
      if (this.DVChecked.length !== 2){
        this.$root.toastError("Hãy chọn 2 vị trí bất kì trên mặt trước để đấu dây nhảy")
        return
      }
      // DAU NHAY
      this.$bvModal.show('popupDauNhay')
    },
    async DAU_DAYNHAY_PHIENDOC(){
      if(!this.kyHieuDayNhay){
        this.$root.toastError('Bạn phải nhập Ký hiệu dây nhảy.');
        return
      }
      try {
        this.loading(true)
        let object = new Object()
        object.phiendoc_id = this.phiendocId
        object.kyhieu = this.kyHieuDayNhay
        object.vitri1 = this.DVChecked[0]
        object.vitri2 = this.DVChecked[1]
        await this.$root.context.post("web-cabman/mang-truyen-dan/daunoi-phiendoc/dau-daynhay-phiendoc", object)
        .then(res =>{
          if (res.error === "200" && res.error_code === "BSS-00000000" && res.data.result === true){
            this.$root.toastSuccess("Đấu nối dây nhảy thành công")
            this.$bvModal.hide('popupDauNhay')
          }
        }).catch(e => {
            let msg = e.response.data.message_detail.includes("CABMAN.UK_KYHIEU_CAP_TD") ? "Ký hiệu đã tồn tại" : e.response.data.message_detail
            this.$root.toastError("Có lỗi xảy ra khi đấu dây nhảy: \r\n" + msg)
          })
        this.DVChecked = []
        await this.getDsDauNoi()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    resetPopupDauNhay(){
      this.kyHieuDayNhay = ''
    },
    KIEMTRA_GAN_DAUVAO(soivao, vitrivao){
      if (soivao.length === 0){
        this.$root.toastError("Vui lòng chọn " + this.lblCongSoiVao + " để gán")
        return false
      }
      if (vitrivao.length === 0){
        this.$root.toastError("Vui lòng chọn vị trí vào để gán")
        return false
      }
      return true
    },
    KIEMTRA_GO_DAUVAO(vitrivao){
      if (vitrivao.length === 0){
        this.$root.toastError("Vui lòng chọn vị trí vào để gỡ")
        return false
      }
      return true
    },
    KIEMTRA_GAN_DAURA(soira, vitrira){
      if (soira.length === 0){
        this.$root.toastError("Vui lòng chọn " + this.lblCongSoiRa + " để gán")
        return false
      }
      if (vitrira.length === 0){
        this.$root.toastError("Vui lòng chọn vị trí ra để gán")
        return false
      }
      if (soira.length !== vitrira.length){
        this.$root.toastError("Số "+ this.lblCongSoiRa + " và số vị trí ra không tương đồng")
        return false
      }
      return true
    },
    KIEMTRA_GO_DAURA(vitrira){
      if (vitrira.length === 0){
        this.$root.toastError("Vui lòng chọn vị trí ra để gỡ")
        return false
      }
      return true
    },
    /**
     * ĐẤU NỐI ĐẦU VÀO
     */
    async btnGanDauVao(){
      try {
        let soivao = []
        let vitrivao = this.DVChecked
        switch (this.tab_dv_active){
          case 'MKN':
            soivao = this.DAUNOI_DV.MAKETNOI.MKNDvCheck
            break
          case "DN":
            soivao = this.DAUNOI_DV.DAYNHAY.DNDvCheck
            break
          case "TB":
            soivao = this.DAUNOI_DV.THIETBI.TBDvCheck
        }
        if (this.KIEMTRA_GAN_DAUVAO(soivao, vitrivao)){
          await this.GAN_DAUVAO(soivao, vitrivao)
        }
      }catch (e){}

    },
    async btnGoDauVao(){
      try {
        this.loading(true)
        let vitrivao = this.DVChecked
        if (this.KIEMTRA_GO_DAUVAO(vitrivao)){
          await this.GO_DAUVAO(vitrivao)
        }
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    async GAN_DAUVAO(soivao, vitrivao){
      let object = new Object()
      object.ds_soivao = soivao.toString()
      object.ds_vitrivao = vitrivao.toString()
      object.tab = this.tab_dv_active
      switch (this.tab_dv_active){
        case "MKN":
          // Mã kết nối
          let ma_kn = this.DAUNOI_DV.MAKETNOI.dsMaKetNoiDv.filter(item => soivao.includes(item.PORTVL_ID)).map(item => "C-" + item.MA_KN)
          object.ds_ma_kn = ma_kn.toString()
          object.donvi_id = this.thongTinPhienDoc.DONVI_ID
          object.phiendoc_id = this.phiendocId
          break
        case "DN":
          // Dây nhảy
          object.daynhay_id_dv = this.DAUNOI_DV.DAYNHAY.cboDayNhayDv ? parseInt(this.DAUNOI_DV.DAYNHAY.cboDayNhayDv,10) : 0
          object.phiendoc_id = this.phiendocId
          break
        case "TB":
          // Thiết bị
          object.donvi_id = this.thongTinPhienDoc.DONVI_ID
          object.phiendoc_id = this.phiendocId
          object.loaitbi_id_dv_2 = this.DAUNOI_DV.THIETBI.cboLoaiTbiDv2 ? parseInt(this.DAUNOI_DV.THIETBI.cboLoaiTbiDv2,10) : 0
          object.thietbi_id_dv_2 = this.DAUNOI_DV.THIETBI.cboThietBiDv2 ? parseInt(this.DAUNOI_DV.THIETBI.cboThietBiDv2,10) : 0
          object.loaitbi_id_dv_3 = parseInt((this.DAUNOI_DV.THIETBI.cboThietBiDv3 ? this.DAUNOI_DV.THIETBI.cboThietBiDv3 : "0#0").toString().split("#")[0],10)
          object.thietbi_id_dv_3 = parseInt((this.DAUNOI_DV.THIETBI.cboThietBiDv3 ? this.DAUNOI_DV.THIETBI.cboThietBiDv3 : "0#0").toString().split("#")[1],10)
      }
      try {
        this.loading(true)
        await this.$root.context.post("web-cabman/mang-truyen-dan/daunoi-phiendoc/gan-dauvao", object)
          .then(res =>{
            if (res.error === "200" && res.error_code === "BSS-00000000" && res.data.result === true){
              this.$root.toastSuccess("Gán đầu vào thành công")
            }
          }).catch(e =>{
            console.log(e)
            this.$root.toastError("Có lỗi xảy ra khi gán đầu vào")
          })
        // reload data
        switch (this.tab_dv_active){
          case "MKN":
            this.DAUNOI_DV.MAKETNOI.MKNDvCheck = []
            this.DVChecked = []
            await this.getDsMaKetNoiDv()
            break
          case "DN":
            this.DAUNOI_DV.DAYNHAY.DNDvCheck = []
            this.DVChecked = []
            await this.getDsSoiDayNhayDv()
            break
          case "TB":
            this.DAUNOI_DV.THIETBI.TBDvCheck = []
            this.DVChecked = []
            await this.getDsCongThietbiDv()
        }
        await this.getDsDauNoi()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    async GO_DAUVAO(vitrivao){
      let object = new Object()
      object.ds_vitrivao = vitrivao.toString()
      object.phiendoc_id = this.phiendocId
      try {
        this.loading(true)
        await this.$root.context.post("web-cabman/mang-truyen-dan/daunoi-phiendoc/go-dauvao", object)
          .then(res =>{
            if (res.error === "200" && res.error_code === "BSS-00000000" && res.data.result === true){
              this.$root.toastSuccess("Gỡ đầu vào thành công")
            }
          }).catch(e =>{
            console.log(e)
            this.$root.toastError("Có lỗi xảy ra khi gỡ đầu vào")
          })
        await this.getDsDauNoi()
        // reset checked
        this.DVChecked = []
        switch (this.tab_dv_active){
          case "MKN":
            await this.getDsMaKetNoiDv()
            break
          case "DN":
            await this.getDsSoiDayNhayDv()
            break
          case "TB":
            await this.getDsCongThietbiDv()
        }
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    /** END - ĐẤU NỐI ĐẦU VÀO*/

    /**
     * ĐẤU NỐI ĐẦU RA
     * */
    async btnGanDauRa(){
      try {
        let soira = []
        let vitrira = this.DRChecked
        switch (this.tab_dr_active){
          case 'MKN':
            soira = this.DAUNOI_DR.MAKETNOI.MKNDrCheck
            break
          case "DN":
            // DISABLE
            soira = this.DAUNOI_DR.DAYNHAY.DNDrCheck
            break
          case "TB":
            // DISABLE
            soira = this.DAUNOI_DR.THIETBI.TBDrCheck
            break
          case "CG":
            soira = this.DAUNOI_DR.CAPGOC.CGDrCheck
        }
        if (this.KIEMTRA_GAN_DAURA(soira, vitrira)){
          await this.GAN_DAURA(soira, vitrira)
        }
      }catch (e){}

    },
    async btnGoDauRa(){
      try {
        let vitrira = this.DRChecked
        if (this.KIEMTRA_GO_DAURA(vitrira)){
          await this.GO_DAURA(vitrira)
        }
      }catch (e){}
    },
    async GAN_DAURA(soira, vitrira){
      let object = new Object()
      object.ds_soira = soira.toString()
      object.ds_vitrira = vitrira.toString()
      object.tab = this.tab_dr_active
      switch (this.tab_dr_active){
        case "MKN":
          // Mã kết nối
          let ma_kn = this.DAUNOI_DR.MAKETNOI.dsMaKetNoiDr.filter(item => soira.includes(item.PORTVL_ID)).map(item => "C-" + item.MA_KN)
          object.ds_ma_kn = ma_kn.toString()
          object.donvi_id = this.thongTinPhienDoc.DONVI_ID
          object.phiendoc_id = this.phiendocId
          break
        case "DN":
          //DISABLE
          // Dây nhảy
          object.daynhay_id_dr = this.DAUNOI_DR.DAYNHAY.cboDayNhayDr ? this.DAUNOI_DR.DAYNHAY.cboDayNhayDr : 0
          object.phiendoc_id = this.phiendocId
          break
        case "TB":
          //DISABLE
          // Thiết bị
          object.donvi_id = this.thongTinPhienDoc.DONVI_ID
          object.phiendoc_id = this.phiendocId
          object.loaitbi_id_dr_2 = this.DAUNOI_DR.THIETBI.cboLoaiTbiDr2 ? this.DAUNOI_DR.THIETBI.cboLoaiTbiDr2 : 0
          object.thietbi_id_dr_2 = this.DAUNOI_DR.THIETBI.cboThietBiDr2 ? this.DAUNOI_DR.THIETBI.cboThietBiDr2 : 0
          object.loaitbi_id_dr_3 = parseInt((this.DAUNOI_DR.THIETBI.cboThietBiDr3 ? this.DAUNOI_DR.THIETBI.cboThietBiDr3 : "0#0").toString().split("#")[0],10)
          object.thietbi_id_dr_3 = parseInt((this.DAUNOI_DR.THIETBI.cboThietBiDr3 ? this.DAUNOI_DR.THIETBI.cboThietBiDr3 : "0#0").toString().split("#")[1],10)
          break
        case "CG":
          object.capgoc_id = this.DAUNOI_DR.CAPGOC.cboCapGocDr
          object.phiendoc_id = this.phiendocId
      }
      try {
        this.loading(true)
        await this.$root.context.post("web-cabman/mang-truyen-dan/daunoi-phiendoc/gan-daura", object)
          .then(res =>{
            if (res.error === "200" && res.error_code === "BSS-00000000" && res.data.result === true){
              this.$root.toastSuccess("Gán đầu ra thành công")
            }
          }).catch(e =>{
            console.log(e)
            this.$root.toastError("Có lỗi xảy ra khi gán đầu ra")
          })
        // reload data
        this.DRChecked = []
        switch (this.tab_dr_active){
          case "MKN":
            this.DAUNOI_DR.MAKETNOI.MKNDrCheck = []
            await this.getDsMaKetNoiDr()
            break
          case "DN":
            //DISABLE
            this.DAUNOI_DR.DAYNHAY.DNDrCheck = []
            await this.getDsSoiDayNhayDr()
            break
          case "TB":
            //DISABLE
            this.DAUNOI_DR.THIETBI.TBDrCheck = []
            await this.getDsCongThietbiDr()
            break
          case "CG":
            this.DAUNOI_DR.CAPGOC.CGDrCheck = []
            await this.getDsSoiCapGocDr()
        }
        await this.getDsDauNoi()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    async GO_DAURA(vitrira){
      let object = new Object()
      object.ds_vitrira = vitrira.toString()
      object.phiendoc_id = this.phiendocId
      try {
        this.loading(true)
        await this.$root.context.post("web-cabman/mang-truyen-dan/daunoi-phiendoc/go-daura", object)
          .then(res =>{
            if (res.error === "200" && res.error_code === "BSS-00000000" && res.data.result === true){
              this.$root.toastSuccess("Gỡ đầu ra thành công")
            }
          }).catch(e =>{
            console.log(e)
            this.$root.toastError("Có lỗi xảy ra khi gỡ đầu ra")
          })
        await this.getDsDauNoi()
        // reset checked
        this.DRChecked = []
        switch (this.tab_dr_active){
          case "MKN":
            await this.getDsMaKetNoiDr()
            break
          case "DN":
            await this.getDsSoiDayNhayDr()
            break
          case "TB":
            await this.getDsCongThietbiDr()
            break
          case "CG":
            await this.getDsSoiCapGocDr()
        }
      }catch (e){}
      finally {
        this.loading(false)
      }
    },

    // Tạo tree thiết bị 3
    createTreeThietBi(dsThietBi){
      let tree = []
      let pkeyArray = dsThietBi.map(item => (item['PKEY']))
      let keyArray = dsThietBi.map(item => (item['KEY']))
      dsThietBi.forEach(item => {
        //check item la parent node
        if(pkeyArray.includes(item['KEY'])){
          //check PKEY khong co trong ds KEY thi xoa PKEY
          if(!keyArray.includes(item['PKEY'])){
            delete item.PKEY
          }
          tree.push( {...item, image:'/static/icons/icon16x16/'+item.MA_LTB + '.png', hasChild:true, expanded: true})
        }else{
          tree.push({...item,image:'/static/icons/icon16x16/'+item.MA_LTB + '.png'})
        }
      })
      if (tree.length === 1){
        //only parent node
        tree[0].hasChild = true
        tree[0].expanded = true
        delete tree[0].PKEY
      }
      return tree
    },
    onchangeThietBiDv3(){
      let defaultObj = this.$refs.thietbiDv3Tree.ej2Instances;
      this.DAUNOI_DV.THIETBI.cboThietBiDv3 = defaultObj.value.toString()
    },
    // Xem thông tin dây nhảy
    onClickXemTTDv(){
      this.capObj.CAPTD_ID = this.DAUNOI_DV.DAYNHAY.cboDayNhayDv
      this.editable = true
      this.$bvModal.show("popupQuanLyDayNhay1")
      // this.$root.toastError("Mở form Quản lý Cáp TD")
    },
    onClickXoaDNDv(){
      if (!this.DAUNOI_DV.DAYNHAY.cboDayNhayDv){
        this.$root.toastError("Chưa chọn dây nhảy")
        return
      }
      let dnText = this.DAUNOI_DV.DAYNHAY.dsDayNhayDv.filter(item => item.CAPTD_ID.toString() === this.DAUNOI_DV.DAYNHAY.cboDayNhayDv.toString())[0].KYHIEU
      this.$bvModal.msgBoxConfirm("Bạn có thực sự muốn xóa dây nhảy ["+ dnText +"] không?\r\nLưu ý: Mọi thông tin đấu nối trên dây nhảy cũng sẽ bị xóa theo khi thực hiện thao tác này.",
        {
          title: "Xác nhận hành động",
          centered: true,
          size: "sm"
        }).then((value) => {
        if (value){
          this.XOA_DAYNHAY(this.DAUNOI_DV.DAYNHAY.cboDayNhayDv)
        }
        else{
          return
        }
      })
    },
    async XOA_DAYNHAY(vdaynhay_id_dv){
      try {
        this.loading(true)
        await this.$root.context.post(`web-cabman/mang-truyen-dan/daunoi-phiendoc/xoa-daynhay/${vdaynhay_id_dv}`)
          .then(res =>{
            if (res.error === "200" && res.error_code === "BSS-00000000" && res.data.result === true){
              this.$root.toastSuccess("Xóa dây nhảy thành công")
            }
          })
          .catch(e =>{
            console.log(e)
            this.$root.toastError("Có lỗi xảy ra khi xóa dây nhảy")
          })
        await this.getDsDayNhayDv()
        await this.getDsDauNoi()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    async UpdateGhiChuMaKetNoi(jumper_pd, ma_kn){
      try {
        this.loading(true)
        let object = new Object()
        object.jumper_pd = jumper_pd
        object.ma_kn = ma_kn
        object.phiendoc_id = this.phiendocId
        let msgSuccess = 'Cập nhật ghi chú mã kết nối thành công'
        let msgError = 'Có lỗi xảy ra trong quá trình cập nhật ghi chú mã kết nối'
        if (!ma_kn) {
          msgSuccess = 'Xóa ghi chú mã kết nối thành công'
          msgError = 'Có lỗi xảy ra trong quá trình xóa ghi chú mã kết nối'
        }
        await this.$root.context.post("web-cabman/mang-truyen-dan/daunoi-phiendoc/capnhat-maketnoi", object)
        .then(res =>{
          if (res.error === "200" && res.error_code === "BSS-00000000" && res.data.result === true){
            this.$root.toastSuccess(msgSuccess)
          }
        }).catch(e => {
          console.log(e)
            this.$root.toastError(msgError)
          })
        await this.getDsDauNoi()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    xemPopupQuanLyCap(CAP_ID){
      if (CAP_ID != null){
        let cap_id = parseInt(CAP_ID,10)
        if (cap_id < 0){
          this.capObj = { CAPTD_ID: -cap_id}
          // this.$root.toastError("mở frmQLCapTD")
          this.editable = false
          this.$bvModal.show('popupQuanLyDayNhay1')
        }else {
          this.capObj = { CAP_ID: cap_id}
          this.$bvModal.show('popupQuanLyCap1')
        }
      }
    },
    cellSelected(args){
      this.cellIndex = args.cellIndex.cellIndex
    },
    /**
     * API
     * ĐẤU NỐI PHIẾN DỌC
     */
    async getThongTinPhienDoc(){
      let phiendocId = this.phiendocId
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-thongtin-phiendoc", {phiendocId:phiendocId})
      .then(res =>{
        if (res.data) this.thongTinPhienDoc = res.data
        else this.thongTinPhienDoc = {}
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải thông tin phiến dọc"))
    },
    async getDsDauNoi(){
      let phiendocId = this.phiendocId
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-daunoi", {phiendocId:phiendocId})
      .then(res =>{
        this.dsDauNoi = res.data
      }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách đấu nối"))
    },
    /**
     * API
     * ĐẤU NỐI ĐẦU VÀO - Mã kết nối
     */
    async getDsLoaiTbiDv1(){
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-loaitbi-dv-1")
        .then(res =>{
          this.DAUNOI_DV.MAKETNOI.dsLoaiTbiDv1 = res.data
          if (res.data.length > 0) this.DAUNOI_DV.MAKETNOI.cboLoaiTbiDv1 = res.data[0].LOAITBI_ID
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách loại thiết bị - Mã kết nối - Đầu vào"))
    },
    async getDsThietbiDv1(){
      let loaitbi_id_dv_1 = this.DAUNOI_DV.MAKETNOI.cboLoaiTbiDv1 ? this.DAUNOI_DV.MAKETNOI.cboLoaiTbiDv1 : 0
      let donviId = this.thongTinPhienDoc.DONVI_ID
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-thietbi-dv-1",{loaitbi_id_dv_1:loaitbi_id_dv_1, donviId:donviId})
        .then(res =>{
          this.DAUNOI_DV.MAKETNOI.dsThietbiDv1 = res.data
          if (res.data.length > 0) this.DAUNOI_DV.MAKETNOI.cboThietBiDv1 = res.data[0].THIETBI_ID
          else this.DAUNOI_DV.MAKETNOI.cboThietBiDv1 = null
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách thiết bị - Mã kết nối - Đầu vào"))
    },
    async getDsMaKetNoiDv(){
      let loaitbi_id_dv_1 = this.DAUNOI_DV.MAKETNOI.cboLoaiTbiDv1 ? this.DAUNOI_DV.MAKETNOI.cboLoaiTbiDv1 : 0
      let thietbi_id_dv_1 = this.DAUNOI_DV.MAKETNOI.cboThietBiDv1 ? this.DAUNOI_DV.MAKETNOI.cboThietBiDv1 : 0
      let donviId = this.thongTinPhienDoc.DONVI_ID
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-maketnoi-dv",{loaitbi_id_dv_1:loaitbi_id_dv_1, thietbi_id_dv_1:thietbi_id_dv_1, donviId:donviId})
        .then(res =>{
          this.DAUNOI_DV.MAKETNOI.dsMaKetNoiDv = res.data
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách mã kết nối - Đầu vào"))
    },
    /**
     * API
     * ĐẤU NỐI ĐẦU VÀO - Dây nhảy
     */
    async getDsDayNhayDv(){
      let phiendocId = this.phiendocId
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-daynhay-dv", {phiendocId:phiendocId})
        .then(res =>{
          this.DAUNOI_DV.DAYNHAY.dsDayNhayDv = res.data
          if (res.data.length > 0) this.DAUNOI_DV.DAYNHAY.cboDayNhayDv = res.data[0].CAPTD_ID
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách dây nhảy - Đầu vào"))
    },
    async getDsSoiDayNhayDv(){
      let daynhay_id_dv = this.DAUNOI_DV.DAYNHAY.cboDayNhayDv ? this.DAUNOI_DV.DAYNHAY.cboDayNhayDv : 0
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-soi-daynhay-dv", {daynhay_id_dv:daynhay_id_dv})
        .then(res =>{
          this.DAUNOI_DV.DAYNHAY.dsSoiDayNhayDv = res.data
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách sợi dây nhảy - Đầu vào"))
    },
    /**
     * API
     * ĐẤU NỐI ĐẦU VÀO - Thiết bị
     */
    async getDsLoaiTbiDv2(){
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-loaitbi-dv-2")
        .then(res =>{
          this.DAUNOI_DV.THIETBI.dsLoaiTbiDv2 = res.data
          if (res.data.length > 0) this.DAUNOI_DV.THIETBI.cboLoaiTbiDv2 = res.data[0].LOAITBI_ID
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách loại thiết bị - Thiết bị - Đầu vào"))
    },
    async getDsThietbiDv2(){
      let loaitbi_id_dv_2 = this.DAUNOI_DV.THIETBI.cboLoaiTbiDv2 ? this.DAUNOI_DV.THIETBI.cboLoaiTbiDv2 : 0
      let donviId = this.thongTinPhienDoc.DONVI_ID
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-thietbi-dv-2",{loaitbi_id_dv_2:loaitbi_id_dv_2, donviId:donviId})
        .then(res =>{
          this.DAUNOI_DV.THIETBI.dsThietbiDv2 = res.data
          if (res.data.length > 0) this.DAUNOI_DV.THIETBI.cboThietBiDv2 = res.data[0].THIETBI_ID
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách thiết bị - Thiết bị - Đầu vào"))
    },
    async getDsThietbiDv3(){
      let thietbi_id_dv_2 = this.DAUNOI_DV.THIETBI.cboThietBiDv2 ? this.DAUNOI_DV.THIETBI.cboThietBiDv2 : 0
      let loaitbi_id_dv_2 = this.DAUNOI_DV.THIETBI.cboLoaiTbiDv2 ? this.DAUNOI_DV.THIETBI.cboLoaiTbiDv2 : 0
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-thietbi-dv-3",{thietbi_id_dv_2:thietbi_id_dv_2, loaitbi_id_dv_2:loaitbi_id_dv_2})
        .then(res =>{
          this.DAUNOI_DV.THIETBI.dsThietbiDv3 = res.data
          let treeThietBi = this.createTreeThietBi(this.DAUNOI_DV.THIETBI.dsThietbiDv3)
          this.fieldsThietBiDv3 = { dataSource: treeThietBi, value: 'KEY', text: 'TEN_TB', parentValue: 'PKEY', hasChildren: 'hasChild', imageUrl: 'image'}
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách thiết bị 3 - Thiết bị - Đầu vào"))
    },
    async getDsCongThietbiDv(){
      let thietbi_id_dv_3 = parseInt((this.DAUNOI_DV.THIETBI.cboThietBiDv3 ? this.DAUNOI_DV.THIETBI.cboThietBiDv3 : "0#0").toString().split("#")[1],10)
      let loaitbi_id_dv_3 = parseInt((this.DAUNOI_DV.THIETBI.cboThietBiDv3 ? this.DAUNOI_DV.THIETBI.cboThietBiDv3 : "0#0").toString().split("#")[0],10)
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-cong-thietbi-dv",{thietbi_id_dv_3:thietbi_id_dv_3, loaitbi_id_dv_3:loaitbi_id_dv_3})
        .then(res =>{
          this.DAUNOI_DV.THIETBI.dsCongThietBiDv = res.data
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách cổng thiết bị - Thiết bị - Đầu vào"))
    },
    /**
     * API
     * ĐẤU NỐI ĐẦU RA - Mã kết nối
     */
    async getDsLoaiTbiDr1(){
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-loaitbi-dr-1")
        .then(res =>{
          this.DAUNOI_DR.MAKETNOI.dsLoaiTbiDr1 = res.data
          if (res.data.length > 0) this.DAUNOI_DR.MAKETNOI.cboLoaiTbiDr1 = res.data[0].LOAITBI_ID
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách loại thiết bị - Mã kết nối - Đầu ra"))
    },
    async getDsThietbiDr1(){
      let loaitbi_id_dr_1 = this.DAUNOI_DR.MAKETNOI.cboLoaiTbiDr1 ? this.DAUNOI_DR.MAKETNOI.cboLoaiTbiDr1 : 0
      let donviId = this.thongTinPhienDoc.DONVI_ID
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-thietbi-dr-1",{loaitbi_id_dr_1:loaitbi_id_dr_1, donviId:donviId})
        .then(res =>{
          this.DAUNOI_DR.MAKETNOI.dsThietbiDr1 = res.data
          if (res.data.length > 0) this.DAUNOI_DR.MAKETNOI.cboThietBiDr1 = res.data[0].THIETBI_ID
          else this.DAUNOI_DR.MAKETNOI.cboThietBiDr1 = null
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách thiết bị - Mã kết nối - Đầu ra"))
    },
    async getDsMaKetNoiDr(){
      let loaitbi_id_dr_1 = this.DAUNOI_DR.MAKETNOI.cboLoaiTbiDr1 ? this.DAUNOI_DR.MAKETNOI.cboLoaiTbiDr1 : 0
      let thietbi_id_dr_1 = this.DAUNOI_DR.MAKETNOI.cboThietBiDr1 ? this.DAUNOI_DR.MAKETNOI.cboThietBiDr1 : 0
      let donviId = this.thongTinPhienDoc.DONVI_ID
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-maketnoi-dr",{loaitbi_id_dr_1:loaitbi_id_dr_1, thietbi_id_dr_1:thietbi_id_dr_1, donviId:donviId})
        .then(res =>{
          this.DAUNOI_DR.MAKETNOI.dsMaKetNoiDr = res.data
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách mã kết nối - Đầu ra"))
    },
    /**
     * API
     * ĐẤU NỐI ĐẦU RA - Dây nhảy
     */
    async getDsDayNhayDr(){
      let phiendocId = this.phiendocId
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-daynhay-dr", {phiendocId:phiendocId})
        .then(res =>{
          this.DAUNOI_DR.DAYNHAY.dsDayNhayDr = res.data
          if (res.data.length > 0) this.DAUNOI_DR.DAYNHAY.cboDayNhayDr = res.data[0].CAPTD_ID
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách dây nhảy - Đầu ra"))
    },
    async getDsSoiDayNhayDr(){
      let daynhay_id_dr = this.DAUNOI_DR.DAYNHAY.cboDayNhayDr ? this.DAUNOI_DR.DAYNHAY.cboDayNhayDr : 0
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-soi-daynhay-dr", {daynhay_id_dr:daynhay_id_dr})
        .then(res =>{
          this.DAUNOI_DR.DAYNHAY.dsSoiDayNhayDr = res.data
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách sợi dây nhảy - Đầu ra"))
    },
    /**
     * API
     * ĐẤU NỐI ĐẦU RA - Thiết bị
     */
    async getDsLoaiTbiDr2(){
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-loaitbi-dr-2")
        .then(res =>{
          this.DAUNOI_DR.THIETBI.dsLoaiTbiDr2 = res.data
          if (res.data.length > 0) this.DAUNOI_DR.THIETBI.cboLoaiTbiDr2 = res.data[0].LOAITBI_ID
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách loại thiết bị - Thiết bị - Đầu ra"))
    },
    async getDsThietbiDr2(){
      let loaitbi_id_dr_2 = this.DAUNOI_DR.THIETBI.cboLoaiTbiDr2 ? this.DAUNOI_DR.THIETBI.cboLoaiTbiDr2 : 0
      let donviId = this.thongTinPhienDoc.DONVI_ID
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-thietbi-dr-2",{loaitbi_id_dr_2:loaitbi_id_dr_2, donviId:donviId})
        .then(res =>{
          this.DAUNOI_DR.THIETBI.dsThietbiDr2 = res.data
          if (res.data.length > 0) this.DAUNOI_DR.THIETBI.cboThietBiDr2 = res.data[0].THIETBI_ID
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách thiết bị - Thiết bị - Đầu ra"))
    },
    async getDsThietbiDr3(){
      let thietbi_id_dr_2 = this.DAUNOI_DR.THIETBI.cboThietBiDr2 ? this.DAUNOI_DR.THIETBI.cboThietBiDr2 : 0
      let loaitbi_id_dr_2 = this.DAUNOI_DR.THIETBI.cboLoaiTbiDr2 ? this.DAUNOI_DR.THIETBI.cboLoaiTbiDr2 : 0
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-thietbi-dr-3",{thietbi_id_dr_2:thietbi_id_dr_2, loaitbi_id_dr_2:loaitbi_id_dr_2})
        .then(res =>{
          this.DAUNOI_DR.THIETBI.dsThietbiDr3 = res.data
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách thiết bị 3 - Thiết bị - Đầu ra"))
    },
    async getDsCongThietbiDr(){
      let thietbi_id_dr_3 = parseInt((this.DAUNOI_DR.THIETBI.cboThietBiDr3 ? this.DAUNOI_DR.THIETBI.cboThietBiDr3 : "0#0").toString().split("#")[1],10)
      let loaitbi_id_dr_3 = parseInt((this.DAUNOI_DR.THIETBI.cboThietBiDr3 ? this.DAUNOI_DR.THIETBI.cboThietBiDr3 : "0#0").toString().split("#")[0],10)
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-cong-thietbi-dr",{thietbi_id_dr_3:thietbi_id_dr_3, loaitbi_id_dr_3:loaitbi_id_dr_3})
        .then(res =>{
          this.DAUNOI_DR.THIETBI.dsCongThietBiDr = res.data
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách cổng thiết bị - Thiết bị - Đầu ra"))
    },
    /**
     * API
     * ĐẤU NỐI ĐẦU RA - Cáp gốc
     */
    async getDsCapGocDr(){
      let donviId = this.thongTinPhienDoc.DONVI_ID
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-capgoc-dr", {donviId:donviId})
        .then(res =>{
          this.DAUNOI_DR.CAPGOC.dsCapGocDr = res.data
          if (res.data.length > 0) this.DAUNOI_DR.CAPGOC.cboCapGocDr = res.data[0].CAP_ID
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách cáp gốc - Cáp gốc - Đầu ra"))
    },
    async getDsSoiCapGocDr(){
      let capgoc_id_dr = this.DAUNOI_DR.CAPGOC.cboCapGocDr ? this.DAUNOI_DR.CAPGOC.cboCapGocDr : 0
      await this.$root.context.get("web-cabman/mang-truyen-dan/daunoi-phiendoc/lay-ds-soi-capgoc-dr", {capgoc_id_dr:capgoc_id_dr})
        .then(res =>{
          this.DAUNOI_DR.CAPGOC.dsSoiCapGocDr = res.data
        }).catch(e => this.$root.toastError("Có lỗi xảy ra khi tải danh sách sợi cáp gốc - Cáp gốc - Đầu ra"))
    },
    clearForm(){
      this.thongTinPhienDoc = {}
      this.dsDauNoi = []
      // Đấu nối check
      this.DVChecked = []
      this.DRChecked = []

      this.DAUNOI_DV = {
        MAKETNOI:{
          MKNDvCheck:[],
            cboLoaiTbiDv1:null,
            cboThietBiDv1:null,

            dsLoaiTbiDv1:[],
            dsThietbiDv1:[],
            dsMaKetNoiDv:[],
        },
        DAYNHAY:{
          dsDayNhayDv:[],
            dsSoiDayNhayDv:[],
        },
        THIETBI:{

          cboLoaiTbiDv3:null,
            cboThietBiDv3:null,

            dsLoaiTbiDv2:[],
            dsThietbiDv2:[],
            dsThietbiDv3:[],
            dsCongThietBiDv:[],
        }
      }
      this.DAUNOI_DR = {
        MAKETNOI:{
          MKNDrCheck:[],
            cboLoaiTbiDr1:null,
            cboThietBiDr1:null,

            dsLoaiTbiDr1:[],
            dsThietbiDr1:[],
            dsMaKetNoiDr:[],
        },
        DAYNHAY:{
          dsDayNhayDr:[],
            dsSoiDayNhayDr:[],
        },
        THIETBI:{
          dsLoaiTbiDr2:[],
            dsThietbiDr2:[],
            dsThietbiDr3:[],
            dsCongThietBiDr:[],
        },
        CAPGOC:{
          dsCapGocDr:[],
            dsSoiCapGocDr:[],
        }
      }
    }
  }
}
</script>

<style >
.daunoi-phiendoc .e-grid.e-default.e-bothlines .e-headercell, .e-grid.e-default.e-bothlines .e-detailheadercell {
  border-width: 1px 1px 0px 0px !important;
}
</style>
