<template src="./index.html"></template>
<script>
import Vue from "vue";
import {Page, Filter, Sort, CommandColumn} from "@syncfusion/ej2-vue-grids";
import { DropDownTreePlugin } from "@syncfusion/ej2-vue-dropdowns";
import TraCuuDanDoCap from './TraCuuDanDoCap'
Vue.use(DropDownTreePlugin);
export default {
  components:{TraCuuDanDoCap},
  provide:{
    grid:[Page, Filter, Sort, CommandColumn],
  },
  name: "DauNoiCap",
  props: {
    capObject: {
      type: Object,
      required: false
    },
    id: String
  },
  data: function (){
    return {
      key: 1,
      isShown: false,
      cap:null,
      show:false,

      selectionOptions:{ persistSelection: true },
      pageSettings: { pageSizes: ['10','20','50','100','All'], pageCount: 3, pageSize: 10 },
      filterOptions: { type: 'FilterBar', hierarchyMode: 'Parent', mode: 'Immediate', operators: '%', immediateModeDelay: 100, showFilterBarStatus:false},
      thietbiNguonColumns:[
        {
          field: 'NGUON',
          headerText: 'Tên thiết bị',
          width: '20%',
          headerAlign:'Center',
          textAlign: 'Center',
          minWidth: 10
        },
        {
          field: 'VITRI_N',
          headerText: 'Vị trí',
          width: '10%',
          headerAlign:'Center',
          textAlign: 'Center',
          minWidth: 10
        },
      ],
      thietbiDichColumns:[
        {
          field: 'VITRI_D',
          headerText: 'Vị trí',
          width: '10%',
          headerAlign:'Center',
          textAlign: 'Center',
          minWidth: 10
        },
        {
          field: 'DICH',
          headerText: 'Tên thiết bị',
          width: '20%',
          headerAlign:'Center',
          textAlign: 'Center',
          minWidth: 10
        },
      ],
      capColumns:[
        {
          // field: 'CHON',
          type:'checkbox',
          headerAlign:'Center',
          textAlign:'Center',
          width: '10%',
          minWidth: 10,
        },
        {
          field: 'SOI',
          headerText: 'Đôi/Sợi cáp',
          width: '15%',
          headerAlign:'Center',
          textAlign: 'Center',
          minWidth: 10,
          isPrimaryKey: true
        },
      ],
      commandChucNang:[
        {
          type: "Tra Dẫn Độ",
          buttonOption: {
            cssClass: "-ap icon-more_horiz",
          },
        },
        {
          type: "Gỡ đấu nối",
          buttonOption: {
            cssClass: "-ap icon-close",
          },
        },
        {
          type: "Thêm chức năng",
          buttonOption: {
            cssClass: "-ap icon-chevron-thin-down",
          },
        },
      ],

      dsThietBiNguon:[],
      dsThietBiNguontest:[],
      dsThietBiDich:[],
      dsNguonDn:[],
      dsDichDn:[],
      dsMidspan:[],
      dsCap:[],
      dsDauNoiCap:[],
      dsViTriNguon:[],
      dsViTriDich:[],

      cboCap:null,
      cboMidspan:null,
      cboNguonDn:null,
      cboDichDn:null,

      filterPlaceholder:'Tìm kiếm',
      // fieldsThietBiN:{},
      // fieldsThietBiD:{},
      cboThietBiNguon: null,
      cboThietBiDich: null,

      lblNguon:'',
      lblDich:'',
      lblThietBiNguon:'',
      lblThietBiDich:'',
      lblViTriNguon:'Vị trí nguồn',
      lblViTriDich:'Vị trí đích',
      loaiN:'',
      loaiD:'',

      vitriNChecked: [],
      vitriDChecked: [],

      txtTuViTriNguon:0,
      txtDenViTriNguon:0,
      txtTuSoiCapNguon:0,
      txtDenSoiCapNguon:0,

      txtTuViTriDich:0,
      txtDenViTriDich:0,
      txtTuSoiCapDich:0,
      txtDenSoiCapDich:0,

      layout1:"",
      layout2:"active",
      layout3:"",
      isShowLayout1: true,
      isShowLayout3: true,
      split1Size:20,
      split2Size:60,
      split3Size:20,

      capTraCuuObject:{
        kyhieu:'',
        soi:null,
      }
    }
  },
  methods:{
    onHiddenModal() {
      this.beforeDestroy()
      this.isShown = false
      this.show = false
      this.key++
    },
    async onShownModal() {
      this.isShown = true
      this.show = true
      try {
        this.loading(true)
        this.cap = this.capObject
        this.cboMidspan = this.capObject.MIDSPAN_ID
        if (this.cap.CAP_ID){
          await this.getDsCap(this.cap.CAP_ID)
          await this.getDsMidspan(this.cap.CAP_ID)
          if (this.dsCap.length > 0 && this.dsMidspan.length > 0){
            await this.getDsNguonDn(this.cboCap, this.cboMidspan)
            await this.getDsDichDn(this.cboCap, this.cboMidspan)
            if (this.cboDichDn){
              await this.getDsDauNoiCap(this.cboCap, this.cboMidspan,this.cboDichDn)
              // this.$refs.grid.setCurrentSelectedRow(-1);
            }
          }
        }
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    commandTTClick: async function(args) {
      //kiem tra nguoi dung co quyen thao tac nay
      try {
        switch (args.commandColumn.type){
          case "Tra Dẫn Độ":
            // mo form tra cuu dan do
            this.capTraCuuObject.kyhieu = this.dsCap[0].MOTA
            this.capTraCuuObject.soi = args.rowData.SOI
            this.$bvModal.show('popupTraCuuDanDoCap')
            break
          case "Gỡ đấu nối":
            if (args.rowData.READONLY) return
            // go dau noi
            this.$bvModal.msgBoxConfirm("Bạn thật sự muốn gỡ đấu nối sợi "+ args.rowData.SOI + " cáp " + this.dsCap[0].MOTA +" không?",
              {
                title: "Xác nhận hành động",
                centered: true,
                size: "sm"
              }).then((value) => {
              if (value){
                this.loading(true)
                this.$root.context.post("/web-cabman/daunoi-cap/go-daunoi-cap",{cap_id:this.cboCap, ds_soi:args.rowData.SOI})
                  .then(res=>{
                    if (res.error === "200" && res.data.result === true){
                      //load lại ds đấu nối cáp
                      this.getDsDauNoiCap(this.cboCap,this.cboMidspan,this.cboDichDn)
                      //load lại ds vị trí nguồn, đích
                      //load lại ds vị trí nguồn, đích
                      if (this.cboThietBiNguon) this.getDsViTriNguon(this.cboThietBiNguon.split("#"))
                      if (this.cboThietBiDich)this.getDsViTriDich(this.cboThietBiDich.split("#"))
                      this.$root.toastSuccess("Gỡ đấu nối cáp thành công")
                    }
                  }).catch(e=>this.$root.toastError("Có lỗi xảy ra khi thực hiện gỡ đấu nối cáp"))
                    . finally(()=>this.loading(false))
              }
              else{
                return
              }
            })
            break
          case "Thêm chức năng":
            if (args.rowData.READONLY) return
            this.$root.toastError("Chức năng đang được xây dựng")
        }
      } catch (e) {
      }
    },
    async btnNapLai(){
      try {
        this.loading(true)
        await this.getDsNguonDn(this.cboCap, this.cboMidspan)
        await this.getDsDichDn(this.cboCap, this.cboMidspan)
        this.txtTuViTriNguon = 0
        this.txtDenViTriNguon = 0
        this.txtTuSoiCapNguon = 0
        this.txtDenSoiCapNguon = 0

        this.txtTuViTriDich = 0
        this.txtDenViTriDich = 0
        this.txtTuSoiCapDich = 0
        this.txtDenSoiCapDich = 0
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    btnViTriNDauNoi(){
      this.dauNoiCap(true,false)
    },
    btnViTriDDauNoi(){
      this.dauNoiCap(false,true)
    },
    btnDauNoi(){
      this.dauNoiCap(true,true)
    },
    btnViTriNGoDauNoi(){
      let cap = this.dsDauNoiCap.filter(item => item.CHON === 1).map(item => item.SOI)
      if (cap.length === 0){
        this.$root.toastError("Phải chọn ít nhất 1 sợi cáp để gỡ đấu nối")
        return
      }
      this.xoaDauNoiCap(true,false)
    },
    btnViTriDGoDauNoi(){
      let cap = this.dsDauNoiCap.filter(item => item.CHON === 1).map(item => item.SOI)
      if (cap.length === 0){
        this.$root.toastError("Phải chọn ít nhất 1 sợi cáp để gỡ đấu nối")
        return
      }
      this.xoaDauNoiCap(false, true)
    },
    btnGoDauNoi(){
      // let cap = this.$refs.grid.getSelectedRecords()
      let cap = this.dsDauNoiCap.filter(item => item.CHON === 1).map(item => item.SOI)
      if (cap.length === 0){
        this.$root.toastError("Phải chọn ít nhất 1 sợi cáp để gỡ đấu nối")
        return
      }
      this.$bvModal.msgBoxConfirm("Bạn thật sự muốn gỡ đấu nối các sợi đang chọn không?",
        {
          title: "Xác nhận hành động",
          centered: true,
          size: "sm"
        }).then((value) => {
        if (value){
          // this.xoaDauNoiCap(true,true)
          let object = new Object()
          object.cap_id = this.cboCap
          object.ds_soi = cap.toString()
          this.$root.context.post("/web-cabman/daunoi-cap/go-daunoi-caps",object)
            .then(res=>{
              if (res.error === "200" && res.data.result === true){
                //load lại ds đấu nối cáp
                this.getDsDauNoiCap(this.cboCap,this.cboMidspan,this.cboDichDn)
                //load lại ds vị trí nguồn, đích
                //load lại ds vị trí nguồn, đích
                if (this.cboThietBiNguon) this.getDsViTriNguon(this.cboThietBiNguon.split("#"))
                if (this.cboThietBiDich)this.getDsViTriDich(this.cboThietBiDich.split("#"))
                this.$root.toastSuccess("Gỡ đấu nối cáp thành công")
              }
            }).catch(e=>this.$root.toastError("Có lỗi xảy ra khi thực hiện gỡ đấu nối"))
        }
        else{
          return
        }
      })
    },
    btnDauNoiNhanhNguon(){
      let isHoanThanhHoTro = this.hoTroDauNoi(0)
      if (isHoanThanhHoTro){
        this.dauNoiCap(true, false)
      }
    },
    btnDauNoiNhanhDich(){
      let isHoanThanhHoTro = this.hoTroDauNoi(1)
      if (isHoanThanhHoTro){
        this.dauNoiCap(false, true)
      }
    },
    // 0: nguon, 1: dich
    hoTroDauNoi(mode){
      let lbViTri = this.dsViTriNguon

      let txtTuViTri = this.txtTuViTriNguon
      let txtDenViTri = this.txtDenViTriNguon
      let txtTuSoi = this.txtTuSoiCapNguon
      let txtDenSoi = this.txtDenSoiCapNguon
      if (mode === 1)
      {
        lbViTri = this.dsViTriDich

        txtTuViTri = this.txtTuViTriDich
        txtDenViTri = this.txtDenViTriDich
        txtTuSoi = this.txtTuSoiCapDich
        txtDenSoi = this.txtDenSoiCapDich
      }
      if (txtTuViTri === 0)
      {
        this.$root.toastError("Bạn chưa nhập vị trí đầu")
        return false
      }

      if (txtDenViTri === 0)
      {
        this.$root.toastError("Bạn chưa nhập vị trí cuối")
        return false
      }

      if (txtTuSoi === 0)
      {
        this.$root.toastError("Bạn chưa nhập đôi/sợi đầu")
        return false
      }

      if (txtDenSoi === 0)
      {
        this.$root.toastError("Bạn chưa nhập đôi/sợi cuối")
        return false
      }
      if (txtTuViTri > txtDenViTri){
        //swap
        txtTuViTri = txtTuViTri + txtDenViTri
        txtDenViTri = txtTuViTri - txtDenViTri
        txtTuViTri = txtTuViTri - txtDenViTri
      }
      if (txtTuSoi > txtDenSoi){
        //swap
        txtTuSoi = txtTuSoi + txtDenSoi
        txtDenSoi = txtTuSoi - txtDenSoi
        txtTuSoi = txtTuSoi - txtDenSoi
      }
      this.dsDauNoiCap.forEach(item =>{
        item.CHON = 0
        if (item.READ_ONLY === 1) {}
        else {
          if (item.SOI >= txtTuSoi && item.SOI <= txtDenSoi)
            item.CHON = 1
        }
      })
      this.dsDauNoiCap = [...this.dsDauNoiCap]
      //reset check vi tri
      this.vitriDChecked = []
      this.vitriNChecked = []
      lbViTri.forEach(item =>{
        if (item.ID >= txtTuViTri && item.ID <= txtDenViTri){
          if (mode === 1) this.vitriDChecked.push(item.ID)
          else this.vitriNChecked.push(item.ID)
        }
      })
      return true
    },
    async dauNoiCap(isHasSrc, isHasDst){
      try {
        this.loading(true)
        // lay du lieu
        let cap = this.dsDauNoiCap.filter(item => item.CHON === 1).map(item => item.SOI)
        let nguon = [...this.vitriNChecked]
        let dich = [...this.vitriDChecked]
        // kiem tra
        if (cap.length === 0){
          this.$root.toastError("Phải chọn ít nhất 1 sợi cáp để đấu nối")
          return
        }
        if (nguon.length ===0 && isHasSrc){
          this.$root.toastError("Phải chọn ít nhất 1 vị trí nguồn để đấu nối")
          return
        }
        if (dich.length ===0 && isHasDst){
          this.$root.toastError("Phải chọn ít nhất 1 vị trí đích để đấu nối")
          return
        }
        if (isHasSrc && isHasDst){
          if (cap.length !== nguon.length || nguon.length !== dich.length || cap.length !== dich.length)
          {
            this.$root.toastError("Số vị trí nguồn, vị trí đích và số sợi cáp phải tương đồng")
            return
          }
        }
        if (isHasSrc && cap.length !== nguon.length)
        {
          this.$root.toastError("Số vị trí nguồn và số sợi cáp phải tương đồng")
          return
        }

        if (isHasDst && cap.length !== dich.length)
        {
          this.$root.toastError("Số vị trí đích và số sợi cáp phải tương đồng")
          return
        }
        // Sap xep du lieu
        cap.sort(function(a, b) {
          return a - b;
        })
        nguon.sort(function(a, b) {
          return a - b;
        });
        dich.sort(function(a, b) {
          return a - b;
        });

        // chuyen doi du lieu
        if (!isHasSrc) dich.forEach(item =>nguon.push(null))
        if (!isHasDst) nguon.forEach(item => dich.push(null))
        let src = isHasSrc ? this.cboThietBiNguon.split("#") : null
        let dst = isHasDst ? this.cboThietBiDich.split("#") : null
        let object = this.calculateObjectDauNoi(cap.toString(), nguon.toString(), dich.toString(), src, dst)

        //thuc hien dau noi
        await this.$root.context.post("/web-cabman/daunoi-cap/daunoi-cap",object)
          .then(res=>{
            if (res.error === "200" && res.data.result === true){
              //reset vitriNChecked, vitriDChecked
              this.vitriNChecked = []
              this.vitriDChecked = []
              //load lại ds đấu nối cáp
              this.getDsDauNoiCap(this.cboCap,this.cboMidspan,this.cboDichDn)
              //load lại ds vị trí nguồn, đích
              if (this.cboThietBiNguon) this.getDsViTriNguon(this.cboThietBiNguon.split("#"))
              if (this.cboThietBiDich)this.getDsViTriDich(this.cboThietBiDich.split("#"))
              this.$root.toastSuccess("Đấu nối cáp thành công")
            }
          })
          .catch(e => {
            if (e && e.response && e.response.data && e.response.data.message_detail) {
              this.$root.toastError(e.response.data.message_detail)
            } else this.$root.toastError("Có lỗi xảy ra trong quá trình thực hiện đấu nối cáp")
          })
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    async xoaDauNoiCap(isRemoveSrc, isRemoveDst){
      try {
        this.loading(true)
        // lấy dữ liệu
        let cap = this.$refs.grid.getSelectedRecords()
        cap = cap.map(item => item.SOI)
        let src = null
        let dst = null
        // Gỡ nguồn
        if (isRemoveSrc){
          src = []
          src.push("MAT")
          src.push(null)
          src.push(null)
        }
        // Gỡ đích
        if (isRemoveDst)
        {
          dst = []
          dst.push("MAT")
          dst.push(null)
          dst.push(null)
        }
        // Chuyển đổi dữ liệu
        let nguon = []
        let dich = []
        if (cap.length > 0){
          cap.forEach(item => {
            nguon.push(null)
            dich.push(null)
          })
        }
        let object = this.calculateObjectDauNoi(cap.toString(), nguon.toString(), dich.toString(), src, dst)
        // thực hiện gỡ đấu nối cáp
        await this.$root.context.post("/web-cabman/daunoi-cap/daunoi-cap",object)
          .then(res=>{
            if (res.error === "200" && res.data.result === true){
              //load lại ds đấu nối cáp
              this.getDsDauNoiCap(this.cboCap,this.cboMidspan,this.cboDichDn)
              //load lại ds vị trí nguồn, đích
              if (this.cboThietBiNguon) this.getDsViTriNguon(this.cboThietBiNguon.split("#"))
              if (this.cboThietBiDich)this.getDsViTriDich(this.cboThietBiDich.split("#"))
              this.$root.toastSuccess("Gỡ đấu nối cáp thành công")
            }
          })
          .catch(e => this.$root.toastError("Có lỗi xảy ra trong quá trình thực hiện gỡ đấu nối cáp"))
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    // onchangeThietBiN(){
    //   let defaultObj = this.$refs.thietbiNTree.ej2Instances;
    //   this.cboThietBiNguon = [defaultObj.value.toString()]
    // },
    // onchangeThietBiD(){
    //   let defaultObj = this.$refs.thietbiDTree.ej2Instances;
    //   this.cboThietBiDich = [defaultObj.value.toString()]
    // },
    // createTreeThietBi(dsThietBi){
    //   let tree = []
    //   let pkeyArray = dsThietBi.map(item => (item['PKEY']))
    //   let keyArray = dsThietBi.map(item => (item['KEY']))
    //   dsThietBi.forEach(item => {
    //     // case: all item is parents
    //       //check PKEY khong co trong ds KEY thi xoa PKEY
    //     if(!keyArray.includes(item['PKEY'])){
    //       delete item.PKEY
    //     }
    //     //check item la parent node
    //     if(pkeyArray.includes(item['KEY'])){
    //       tree.push( {...item, image:'/static/icons/icon16x16/'+item.MA_LTB + '.png', hasChild:true, expanded: true})
    //     }else{
    //       tree.push({...item,image:'/static/icons/icon16x16/'+item.MA_LTB + '.png'})
    //     }
    //   })
    //   if (tree.length === 1){
    //     //only parent node
    //     tree[0].hasChild = true
    //     tree[0].expanded = true
    //     delete tree[0].PKEY
    //   }
    //   return tree
    // },
    calculateObjectDauNoi(cap, nguon, dich, src, dst){
      let object = new Object()
      object.cap_id = this.cboCap
      object.midspan_id = this.cboMidspan
      object.cap = cap
      object.nguon = nguon
      object.dich = dich

      object.daucapvao = dst === null ? 0: 1
      object.daucapra = src === null ? 0: 1
      object.ktTruocKhiDauNoi = 0
      if (src !== null){
        switch (src[0]){
          case "MAT":
            object.loaitbi_n_id = 25
            object.thietbi_n_id = src[1]
            object.mat_n = src[2]
            break
          case "PHIEN_DOC":
            object.loaitbi_n_id = 23
            object.thietbi_n_id = src[1]
            object.mat_n = 2
            break
          case "ODF":
            object.loaitbi_n_id = 18
            object.thietbi_n_id = src[1]
            object.mat_n = 2
            break
          case "CARD_DSL":
            object.loaitbi_n_id = 6
            object.thietbi_n_id = src[1]
            object.mat_n = 2
            break
          case "CARD_GPON":
            object.loaitbi_n_id = 10
            object.thietbi_n_id = src[1]
            object.mat_n = 2
            break
          case "SWITCH":
            object.loaitbi_n_id = 2
            object.thietbi_n_id = src[1]
            object.mat_n = 2
            break
        }
      }else {
        object.loaitbi_n_id = null
        object.thietbi_n_id = null
        object.mat_n = null
      }
      if (dst !== null){
        switch (dst[0]) {
          case "MAT":
            object.loaitbi_d_id = 25
            object.thietbi_d_id = dst[1]
            object.mat_d = dst[2]
            break
          case "PHIEN_DOC":
            object.loaitbi_d_id = 23
            object.thietbi_d_id = dst[1]
            object.mat_d = 2
            break
          case "ODF":
            object.loaitbi_d_id = 18
            object.thietbi_d_id = dst[1]
            object.mat_d = 2
            break
        }
      }else {
        object.loaitbi_d_id = null
        object.thietbi_d_id = null
        object.mat_d = null
      }
      return object
    },
    assignLabel(item_n, item_d){
      if (item_n){
        switch (item_n){
          case "TRAM":
            this.lblNguon = "Trạm nguồn"
            this.lblThietBiNguon = "Thiết bị nguồn"
            break
          case "KETCUOI":
            this.lblNguon = "Kết cuối nguồn"
            this.lblThietBiNguon = "Mặt"
            break
          default:
            this.$root.toastError("Kiểu thiết bị " + item_n + " chưa được hỗ trợ")
            return
        }
      }
      if (item_d){
        switch (item_d){
          case "TRAM":
            this.lblDich = "Trạm đích"
            this.lblThietBiDich = "Thiết bị đích"
            break
          case "KETCUOI":
            this.lblDich = "Kết cuối đích"
            this.lblThietBiDich = "Mặt"
            break
          default:
            this.$root.toastError("Kiểu thiết bị " + item_d + " chưa được hỗ trợ")
            return
        }
      }
    },
    eventTxtTuViTriNguon(args){
      if (args.target.value === ""){
        this.txtTuViTriNguon = 0
      }
    },
    eventTxtDenViTriNguon(args){
      if (args.target.value === ""){
        this.txtDenViTriNguon = 0
      }
    },
    eventTxtTuSoiCapNguon(args){
      if (args.target.value === ""){
        this.txtTuSoiCapNguon = 0
      }
    },
    eventTxtDenSoiCapNguon(args){
      if (args.target.value === ""){
        this.txtDenSoiCapNguon = 0
      }
    },
    eventTxtTuViTriDich(args){
      if (args.target.value === ""){
        this.txtTuViTriDich = 0
      }
    },
    eventTxtDenViTriDich(args){
      if (args.target.value === ""){
        this.txtDenViTriDich = 0
      }
    },
    eventTxtTuSoiCapDich(args){
      if (args.target.value === ""){
        this.txtTuSoiCapDich = 0
      }
    },
    eventTxtDenSoiCapDich(args){
      if (args.target.value === ""){
        this.txtDenSoiCapDich = 0
      }
    },
    cboCap_EditValueChanged(item) {
      this.NAP_DS_MIDSPAN()
    },
    cboMidSpan_EditValueChanged(item) {
      this.$refs.grid.grid.clearSelection();
      this.NAP_DS_THIETBI()

    },
    async NAP_DS_THIETBI() {
      try {
        this.loading(true)
        await this.getDsNguonDn(this.cboCap, this.cboMidspan)
        await this.getDsDichDn(this.cboCap, this.cboMidspan)
      } finally {
        this.loading(false)
      }
    },
    async NAP_DS_MIDSPAN() {
      // Lấy mid-span
      await this.getDsMidspan(this.cboCap)
      await this.NAP_DS_THIETBI()
    },

    async getDsViTriNguon(nguon){
      let object = new Object()
      switch (nguon[0]){
        case "MAT":
          object.ma_loaitbi = nguon[0]
          object.port_plug = nguon.length > 4 ? nguon[3] : "0"
          object.ketcuoi_id = nguon[1]
          object.mat = nguon[2]
          break
        default:
          object.ma_loaitbi = nguon[0]
          object.ketcuoi_id = nguon[1]
      }
      await this.$root.context.post(
        "/web-cabman/daunoi-cap/lay-ds-vitri-nguon", object
      ).then(res =>{
        this.dsViTriNguon = res.data
      }).catch(e=> {
        this.$root.toastError("Có lỗi xảy ra khi tải danh sách vị trí nguồn")
      })
    },
    async getDsViTriDich(dich){
      let object = new Object()
      switch (dich[0]){
        case "MAT":
          object.ma_loaitbi = dich[0]
          object.ketcuoi_id = dich[1]
          object.mat = dich[2]
          break
        default:
          object.ma_loaitbi = dich[0]
          object.ketcuoi_id = dich[1]
      }
      await this.$root.context.post(
        "/web-cabman/daunoi-cap/lay-ds-vitri-dich", object
      ).then(res =>{
        this.dsViTriDich = res.data
      }).catch(e=> {
        this.$root.toastError("Có lỗi xảy ra khi tải danh sách vị trí đích")
      })
    },
    async getDsThietBiNguon(object){
      object.dsloaitbi_id = "18,23"
      await this.$root.context.post(
        "/web-cabman/daunoi-cap/lay-ds-cay-thietbi-tongdai", object
      ).then(res =>{
        this.dsThietBiNguon = res.data.map(i => ({
          ...i,
          id: i.KEY,
          pid: 0,
          label: i.TEN_TB,
          img: '/static/icons/icon16x16/'+i.MA_LTB + '.png'
        }))
        // let treeThietBi = this.createTreeThietBi(this.dsThietBiNguon)
        // this.fieldsThietBiN = { dataSource: treeThietBi, value: 'KEY', text: 'TEN_TB', parentValue: 'PKEY', hasChildren: 'hasChild', imageUrl: 'image'}
      }).catch(e=> {
        this.$root.toastError("Có lỗi xảy ra khi tải danh sách thiết bị nguồn")
      })
    },
    async getDsThietBiDich(object){
      object.dsloaitbi_id = "18,23,43"
      await this.$root.context.post(
        "/web-cabman/daunoi-cap/lay-ds-cay-thietbi-tongdai",object
      ).then(res =>{
        this.dsThietBiDich = res.data.map(i => ({
          ...i,
          id: i.KEY,
          pid: 0,
          label: i.TEN_TB,
          img: '/static/icons/icon16x16/'+i.MA_LTB + '.png'
        }))
        // let treeThietBi = this.createTreeThietBi(this.dsThietBiDich)
        // this.fieldsThietBiD = { dataSource: treeThietBi, value: 'KEY', text: 'TEN_TB', parentValue: 'PKEY', hasChildren: 'hasChild', imageUrl: 'image'}
      }).catch(e=> {
        this.$root.toastError("Có lỗi xảy ra khi tải danh sách thiết bị đích")
      })
    },
    async getDsNguonDn(capId, midspanId){
      await this.$root.context.get(
        "/web-cabman/daunoi-cap/lay-ds-nguon-dn",{capId:capId,midspanId:midspanId}
      ).then(res =>{
        this.dsNguonDn = res.data
        // this.cboNguonDn = this.dsMidspan[0].NGUON_ID
        let midSpan = this.dsMidspan.find(x => x.ID == midspanId)
        if (midSpan) {
          this.cboNguonDn = midSpan.NGUON_ID
        }

      }).catch(e=> {
        this.$root.toastError("Có lỗi xảy ra khi tải danh sách kết cuối nguồn")
      })
    },
    async getDsDichDn(capId, midspanId){
      await this.$root.context.get(
        "/web-cabman/daunoi-cap/lay-ds-dich-dn",{capId:capId,midspanId:midspanId}
      ).then(res =>{
        this.dsDichDn = res.data
        // this.cboDichDn = this.dsMidspan[0].DICH_ID
        let midSpan = this.dsMidspan.find(x => x.ID == midspanId)
        if (midSpan) {
          this.cboDichDn = midSpan.DICH_ID
        }
      }).catch(e=> {
        this.$root.toastError("Có lỗi xảy ra khi tải danh sách kết cuối đích")
      })
    },
    async getDsMidspan(capId){
      await this.$root.context.get(
        "/web-cabman/daunoi-cap/lay-ds-midspan",{capId:capId}
      ).then(res =>{
        this.dsMidspan = res.data
        if (res.data.length > 0){
          if (this.cboMidspan == null)
            this.cboMidspan = this.dsMidspan[0].ID
        }
      }).catch(e=> {
        this.$root.toastError("Có lỗi xảy ra khi tải danh sách mid-span")
      })
    },
    async getDsCap(capId){
      await this.$root.context.get(
        "/web-cabman/daunoi-cap/lay-ds-cap",{capId:capId}
      ).then(res =>{
        this.dsCap = res.data
        if (res.data.length > 0) this.cboCap = res.data[0].ID
      }).catch(e=> {
        this.$root.toastError("Có lỗi xảy ra khi tải danh sách cáp")
      })
    },
    async getDsDauNoiCap(capId, midspanId, dichId){
      await this.$root.context.get(
        "/web-cabman/daunoi-cap/lay-ds-daunoi-cap",{capId:capId, midspanId:midspanId, dichId:dichId}
      ).then(res =>{
        this.dsDauNoiCap = res.data
      }).catch(e=> {
        this.$root.toastError("Có lỗi xảy ra khi tải danh sách đấu nối cáp")
      })
    },
    beforeDestroy: function () {
      this.dsThietBiNguon = []
      this.dsThietBiDich = []
      this.dsNguonDn = []
      this.dsDichDn = []
      this.dsMidspan = []
      this.dsCap = []
      this.dsDauNoiCap = []
      this.dsViTriNguon = []
      this.dsViTriDich = []

      this.cboCap = null
      this.cboMidspan = null
      this.cboNguonDn = null
      this.cboDichDn = null
      this.cboThietBiNguon = null
      this.cboThietBiDich = null

      this.lblNguon = ''
      this.lblDich = ''
      this.lblThietBiNguon = ''
      this.lblThietBiDich = ''
      this.lblViTriNguon = 'Vị trí nguồn'
      this.lblViTriDich = 'Vị trí đích'
      this.loaiN = ''
      this.loaiD = ''

      this.vitriNChecked = []
      this.vitriDChecked = []

      this.txtTuViTriNguon = 0
        this.txtDenViTriNguon =0
        this.txtTuSoiCapNguon = 0
        this.txtDenSoiCapNguon = 0

        this.txtTuViTriDich = 0
        this.txtDenViTriDich = 0
        this.txtTuSoiCapDich = 0
        this.txtDenSoiCapDich = 0
    },
    changeClassLayout(layout1, layout2, layout3){
      this.layout1 = layout1
      this.layout2 = layout2
      this.layout3 = layout3
    },
    dauNoiCapRowSelected(args){
      if (!args.data.READONLY) {
        args.rowIndexes.forEach(idx => {
          this.dsDauNoiCap[idx].CHON = 1
        })
      }
    },
    dauNoiCapRowSelecting(args) {
      if (args.data.READONLY) {
        args.cancel = true;
      }
      if (args.rowIndexes.length > 1) {
        let index = [];
        args.rowIndexes.filter((rowIndex) => {
          if (this.$refs.grid.grid.getRowByIndex(rowIndex).classList.contains('e-disabled')) {
            index.push(rowIndex);
          }
        });
        index.forEach((p) =>
          args.rowIndexes.splice(args.rowIndexes.indexOf(p), 1)
        );
      }
    },
    dauNoiCapRowDeselected(args){
      if (args && args.rowIndexes && !args.data.READONLY) {
        args.rowIndexes.forEach(idx => {
          this.dsDauNoiCap[idx].CHON = 0
        })
      }
    },
    dauNoiCapRowDataBound: function (args) {
      if (args.data.READONLY) {
        args.row.classList.add('e-disabled')
      }
    },
    btnLayout1(){
      this.isShowLayout1 = false
      this.isShowLayout3 = true
      this.split1Size = 0
      this.split2Size = 85
      this.split3Size = 15

      this.changeClassLayout("active", "","")
    },
    btnLayout2(){
      this.isShowLayout1 = true
      this.isShowLayout3 = true
      this.split1Size = 15
      this.split2Size = 70
      this.split3Size = 15

      this.changeClassLayout("", "active","")
    },
    btnLayout3(){
      this.isShowLayout1 = true
      this.isShowLayout3 = false
      this.split1Size = 15
      this.split2Size = 85
      this.split3Size = 0

      this.changeClassLayout("", "","active")
    },
  },
  watch:{
    "cboNguonDn": async function (value){
      try {
        // this.loading(true)
        if (value){
          let object = new Object()
          let nguon_id = this.cboNguonDn.split("_")
          this.loaiN = nguon_id[0]
          this.assignLabel(nguon_id[0], null)
          object.ma_loaitbi_cha = nguon_id[0]
          object.thietbi_cha_id = nguon_id[1]
          await this.getDsThietBiNguon(object)
          if (this.dsThietBiNguon.length > 0) {
            if (nguon_id[0] === "KETCUOI")
              this.cboThietBiNguon = this.dsThietBiNguon[1].KEY
            else if (nguon_id[0] === "TRAM") {
              for (let i = 0; i < this.dsThietBiNguon.length;i++){
                if (this.dsThietBiNguon[i].CO_PORT === 1){
                  this.cboThietBiNguon = this.dsThietBiNguon[i].KEY
                  break
                }
              }
            }
          }
        }else {
          this.lblThietBiNguon = ''
          this.cboThietBiNguon = null
          this.dsThietBiNguon = []
          this.dsViTriNguon = []
        }
      } catch (e){}
      finally {
        // this.loading(false)
      }
    },
    "cboDichDn": async function (value){
      try {
        // this.loading(true)
        if (value){
          this.$refs.grid.grid.clearSelection();
          await this.getDsDauNoiCap(this.cboCap, this.cboMidspan,this.cboDichDn)
          let object = new Object()
          let dich_id = this.cboDichDn.split("_")
          this.loaiD = dich_id[0]
          this.assignLabel(null, dich_id[0])
          object.ma_loaitbi_cha = dich_id[0]
          object.thietbi_cha_id = dich_id[1]
          await this.getDsThietBiDich(object)
          if (this.dsThietBiDich.length > 0) {
            if (dich_id[0] === "KETCUOI")
              this.cboThietBiDich = this.dsThietBiDich[0].KEY
            else if (dich_id[0] === "TRAM") {
              for (let i = 0; i < this.dsThietBiDich.length;i++){
                if (this.dsThietBiDich[i].CO_PORT === 1){
                  this.cboThietBiDich = this.dsThietBiDich[i].KEY
                  break
                }
              }
            }
          }
        }else {
          this.lblThietBiDich = ''
          this.cboThietBiDich = null
          this.dsThietBiDich = []
          this.dsViTriDich = []
        }
      } catch (e){}
      finally {
        // this.loading(false)
      }
    },
    "cboThietBiNguon":async function (value){
      try {
        // this.loading(true)
        this.dsViTriNguon = []
        // if (value.length === 0) return
        let nguon_id = value
        if (nguon_id === null || nguon_id === "-1" || nguon_id === "0"){
          this.lblViTriNguon = ''
          this.dsViTriNguon = []
        }else {
          await this.getDsViTriNguon(nguon_id.split("#"))
        }
      }catch (e){}
      finally {
        // this.loading(false)
      }
    },
    "cboThietBiDich":async function (value){
      try {
        // this.loading(true)
        this.dsViTriDich = []
        // if (value.length === 0) return
        let dich_id = value
        if (dich_id === null || dich_id === "-1" || dich_id === "0"){
          this.lblViTriDich = ''
          this.dsViTriDich = []
        }else {
          await this.getDsViTriDich(dich_id.split("#"))
        }
      }catch (e){}
      finally {
        // this.loading(false)
      }
    },
  },
  async mounted()  {
    try {
      this.$store.dispatch('setHeader', {
        title: 'Đấu nối cáp'
      })
    } catch (err) {
      console.log(err)
    }
  }
}
</script>
<style>
.e-grid *{
  font-size:13px !important;
}
.e-grid.e-default.e-bothlines .e-headercell, .e-grid.e-default.e-bothlines .e-detailheadercell {
  border-width: 1px 1px 0px 0px !important;
}
@media (min-width: 1200px){
  .modal-xl {
    max-width: 80% !important;
  }
}
</style>
