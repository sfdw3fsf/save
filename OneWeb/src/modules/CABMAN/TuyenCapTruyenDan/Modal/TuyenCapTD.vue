<template src="./TuyenCapTD.html"></template>
<script>
import {minLength,maxLength,numeric, required} from 'vuelidate/lib/validators'
import {Sort, Filter, ForeignKey, Resize, Freeze,Page  } from "@syncfusion/ej2-vue-grids"
import { L10n } from '@syncfusion/ej2-base'
L10n.load({
    'vi-VN': {
        grid: {
          EmptyRecord: 'Không có bản ghi nào để hiển thị'
        },
    }
})
export default {
  name: "TuyenCapTD",
  props: ["modalId"],
   provide: {
    grid: [Sort, Filter, ForeignKey, Resize, Freeze ,Page]
  },
  computed: {},
  destroyed() {},
  methods: {
   onActionComplete(args) {
        if (args.requestType == "filtering") {

          let element = document.getElementById(args.currentFilterObject.field)

          if (element.type == 'text') {
            element.click() 
            element.focus() 
          }
            
        }
    },
    //#region "Đổ dữ liệu"
    focusMyElement: async function () {
      try{
        this.$root.showLoading(true)
        this.getDsTuyenCap()
        this.getdsTTVT()
      }catch(er){}
      finally{
         this.$root.showLoading(false)
      }
    },
    hiddenModal:function(){
       this.dsTuyenCap=[]
      this.dsCapTrenTuyen=[]
      this.dsCapChuaGan=[]
      this.dsDonViQLCap=[]
      this.dsToKyThuat=[]
      this.dsTTVT=[]
      this.dsCapChuaGan_check=[]
      this.dsCapTrenTuyen_check=[]
      this.tuyenCap={
         tuyencap_id:null,
         ma_tc:"",
         tuyen_cap:"",
         donvi_id: null,
         ghichu:""
      }
    },
    getDsTuyenCap: async function(){
        try{
             var rs = await this.$root.context.get(
          "/web-cabman/tuyencap/dstuyencap"
        );       
        if(rs.data!=null && rs.data.length>0){
          this.dsTuyenCap=rs.data
          this.$refs['grid-tuyencap'].setCurrentSelectedRow(0)
          this.totalRow_dsTuyen = this.dsTuyenCap.length;
        }
      }catch(er){}
      finally{
         this.$root.showLoading(false)
      }
    },
    getChitietTuyenCap: async function(){
        try{
           var rs = await this.$root.context.get(
          "/web-cabman/tuyencap/chitiet/"+ this.tuyenCap.tuyencap_id
        );
        if(rs.data!=null){
         this.tuyenCap={
         tuyencap_id:this.tuyenCap.tuyencap_id,
         ma_tc:rs.data.MA_TC,
         tuyen_cap:rs.data.TUYEN_CAP,
         donvi_id: rs.data.DONVI_ID,
         ghichu:rs.data.GHICHU}
         this.action=1
      Object.assign(this.tuyencapTemp,this.tuyenCap)
       this.tuyen_id = this.tuyenCap.tuyencap_id
      //await this.getdsCapTrenTuyen_Paging()
      await this.getdsCapTrenTuyen()
      await this.getdsCapChuaGan()  
     // await this.getdsCapChuaGan_Paging()
      await this.getdsToKyThuat() 
      this.setDisableBtn(false,false,false,false) 
      }else
       this.setDisableBtn(false,true,true,true) 
      }catch(er){}
      finally{
         this.$root.showLoading(false)
      }
    },
    getdsTTVT: async function(){
     try{
      var rs = await this.$root.context.post(
          "/web-ecms/danhmuc/layDsTTVTTrongToken"
        );
        if(rs.data!=null&&rs.data.length>0){
          this.dsTTVT=rs.data
        }
      }catch(er){}
      finally{
         this.$root.showLoading(false)
      }
    },
    getdsToKyThuat: async function(){
     try{
      this.dsToKyThuat=[]
      var rs = await this.$root.context.get(
          "/web-cabman/tuyencap/dstokythuat/"+this.tuyenCap.donvi_id
        );
        if(rs.data!=null&&rs.data.length>0){
          for(let i=0;i<rs.data.length;i++){
            rs.data[i].CHON=false
          }
           this.dsToKyThuat=rs.data
        }
      }catch(er){
         console.log(er)
      }
      finally{
         this.$root.showLoading(false)
      }
    },
    getdsCapTrenTuyen: async function(){
      try{
             var rs = await this.$root.context.get(
          "/web-cabman/tuyencap/cap_tren_truyen/"+ this.tuyenCap.tuyencap_id
        );
        if(rs.data!=null&&rs.data.length>0){     
          this.dsCapTrenTuyen=rs.data   

        }else
        this.columnsCapTrenTuyen =0
        this.totalRow_captrentuyen  = this.dsCapTrenTuyen.length;
      }catch(er){}
      finally{
         this.$root.showLoading(false)
      }
    },
    getdsCapChuaGan: async function(){
      try{
             var rs = await this.$root.context.get(
          "/web-cabman/tuyencap/cap_chua_gan/"+ this.tuyenCap.tuyencap_id
        );
        if(rs.data!=null&&rs.data.length>0)
          this.dsCapChuaGan=rs.data 
        this.totalRow_capchuagan = this.dsCapChuaGan.length;                   
      }catch(er){
          console.log(er)
      }
      finally{
         this.$root.showLoading(false)
      }
    },
    getdsCapChuaGan_Paging: async function(pageNo=1,pageSize=10){
      try{
        var rs = await this.$root.context.get(
          "/web-cabman/tuyencap/cap_chua_gan_paging/"+ this.tuyenCap.tuyencap_id+
          '?pageNo='+pageNo+'&pageSize='+pageSize
        );
        if(rs.data!=null && rs.data.dsCap.length>0){
          this.totalRow_capchuagan =rs.data.paging[0].RECORD_COUNT 
          this.dsCapChuaGan=rs.data.dsCap     
        }else{
           this.totalRow_capchuagan =0 
          this.dsCapChuaGan=[] 
        }
      }catch(er){
          console.log(er)
      }
      finally{
         this.$root.showLoading(false)
      }
    },
    getdsCapTrenTuyen_Paging: async function(pageNo=1,pageSize=10){
      try{
        var rs = await this.$root.context.get(
          "/web-cabman/tuyencap/cap_tren_tuyen_paging/"+ this.tuyenCap.tuyencap_id+
          '?pageNo='+pageNo+'&pageSize='+pageSize
        );
        if(rs.data!=null && rs.data.dsCap.length>0){
          this.totalRow_captrentuyen =rs.data.paging[0].RECORD_COUNT 
          this.dsCapTrenTuyen=rs.data.dsCap 
        }
        else{
          this.totalRow_captrentuyen =0
          this.dsCapTrenTuyen=[]
        }

      }catch(er){
          console.log(er)
      }
      finally{
         this.$root.showLoading(false)
      }
    },
    getdsTimKiem:async function(){
      try{
           var rs = await this.$root.context.get(
          "/web-cabman/tuyencap/timkiemtuyencap", {name:this.chuoiTimKiem.trim()}
        );
        if(rs.data!=null){
          this.dsToKyThuat=[]
         this.dsTuyenCap=rs.data
         this.tuyenCap.tuyencap_id=this.dsTuyenCap[0].TUYENCAP_ID
         this.tuyen_id = this.tuyenCap.tuyencap_id
         await  this.getChitietTuyenCap()  
      }
      }catch(er){
        this.$root.toastError('Lỗi: ',err.response.data.message_detail)
      }
      finally{
         this.$root.showLoading(false)
      }
    },
    timkiem_keydown: async function(e){
      if(e.key=="Enter"){
        if(this.chuoiTimKiem.trim()!="")
         await this.getdsTimKiem()
         else
         await this.getDsTuyenCap()
      }
    },
    // grid_toKT_dataGridCellCreated:  function(e){
    //   console.log('e:',e)
    //   if(e.cellColumn.headerText==="Chọn"){

    //   e.cellAllowHtml = true;
    //   let id= e.cellDataItem.DONVI_ID
    //   if ($('#'+id).length > 0){
    //      return
    //    }else{
    //   $(e.cellElement).append('<input type="radio" id="'+id+'" name="tokt" class="radio-donvi check">')
    //   $('input[type=radio].check').on("change",(e) => {
    //          if($('#'+id).prop('checked')){
    //        this.tokythuat_id=id          
    //     }
    //     });
    //     }
    //   }
    // },
    setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa) {
      this.isDisableBtnNhapMoi = isNhapmoi;
      this.isDisableBtnGhiLai = isGhilai;
      this.isDisableBtnHuy = isHuy;
      this.isDisableBtnXoa = isXoa;
    },
    //#endregion

    //#region "code cũ"
    //#region "Grid Tuyến Cáp"
     onDataBoundTuyenCap() {
      this.$refs['grid-tuyencap'].selectRow(0)
        
    },
    onRowSelectedTuyenCap: async function(args) {
    
       this.$refs['grid-tuyencap'].$el.querySelectorAll('.col-mark-selected-row').forEach(item => {
         item.style.display = args.rowIndex == parseInt(item.id) ? 'block' : 'none'
      })
      if(args.data!=null){
          this.action =1
         this.tuyenCap.tuyencap_id=args.data.TUYENCAP_ID
          await  this.getChitietTuyenCap() 
      }
    },
     onCreateGridTuyenCap() { 
      this.$refs['grid-tuyencap'].$el.classList.add('table-result')
    },
    //#endregion

    //#region "Grid Cáp trên tuyến"
    onDataBoundCapTrenTuyen: function(){

    },
     onDataBoundCapTrenTuyen(refGrid) {
             return () => {
                let grid = this.$refs[refGrid]

                if (grid.getDataRows().length > 0) {

                    if (grid.isFrozenGrid()) {
                        let cells = grid.getMovableRows()[0].querySelectorAll('.e-rowcell')
                        cells.forEach(item => item.classList.add('e-active'))
                    }

                    let firstRow = grid.getRowByIndex(0)
                    let cells = firstRow.querySelectorAll('.e-rowcell')
                    cells.forEach(item => item.classList.add('e-active'))
                    this.onRowSelectedCapTrenTuyen(grid.getRowInfo(firstRow))
                } else if (grid.isFrozenGrid()) {
                    let visibleColumnCount = grid.getVisibleColumns().length
                    let emptyRows = grid.$el.querySelectorAll('.e-emptyrow')
                    let cell0 = emptyRows[0].cells[0]
                    let cell1 = emptyRows[1].cells[0]
                    cell0.style.color = 'white'
                    cell1.innerHTML = cell0.innerHTML
                    cell0.innerHTML = 'x'
                    cell1.setAttribute('colspan', visibleColumnCount - grid.getFrozenLeftCount())
                    cell1.style.setProperty('text-align', 'left', 'important')
                }
            }
     // this.$refs['grid-captrentuyen'].selectRow(0)
    },
    onRowSelectedCapTrenTuyen: async function(args) { 
       this.$refs['grid-captrentuyen'].$el.querySelectorAll('.col-mark-selected-row').forEach(item => {
         item.style.display = args.rowIndex == parseInt(item.id) ? 'block' : 'none'
      })
    },
     onCreateGridCapTrenTuyen() { 
      this.$refs['grid-captrentuyen'].$el.classList.add('table-result')
      this.$refs['grid-captrentuyen'].ej2Instances.getHeaderTable().querySelector(".e-filterbarcell div").style.display = "none"   
    },

    //#endregion

     //#region "Grid Cáp chưa gán"
     onDataBoundCapChuaGan(refGrid) {
        return () => {
                let grid = this.$refs[refGrid]

                if (grid.getDataRows().length > 0) {

                    if (grid.isFrozenGrid()) {
                        let cells = grid.getMovableRows()[0].querySelectorAll('.e-rowcell')
                        cells.forEach(item => item.classList.add('e-active'))
                    }

                    let firstRow = grid.getRowByIndex(0)
                    let cells = firstRow.querySelectorAll('.e-rowcell')
                    cells.forEach(item => item.classList.add('e-active'))
                    this.onRowSelectedCapChuaGan(grid.getRowInfo(firstRow))
                } else if (grid.isFrozenGrid()) {
                    let visibleColumnCount = grid.getVisibleColumns().length
                    let emptyRows = grid.$el.querySelectorAll('.e-emptyrow')
                    let cell0 = emptyRows[0].cells[0]
                    let cell1 = emptyRows[1].cells[0]
                    cell0.style.color = 'white'
                    cell1.innerHTML = cell0.innerHTML
                    cell0.innerHTML = 'x'
                    cell1.setAttribute('colspan', visibleColumnCount - grid.getFrozenLeftCount())
                    cell1.style.setProperty('text-align', 'left', 'important')
                }
            }
    //  this.$refs['grid-capchuagan'].selectRow(0)
    },
    onRowSelectedCapChuaGan: async function(args) {
    
       this.$refs['grid-capchuagan'].$el.querySelectorAll('.col-mark-selected-row').forEach(item => {
         item.style.display = args.rowIndex == parseInt(item.id) ? 'block' : 'none'
      })
    },
     onCreateGridCapChuaGan() { 
      this.$refs['grid-capchuagan'].$el.classList.add('table-result')
    },
    //#endregion

     //#endregion
   
   //#region "Grid Tổ kỹ thuật"
     onDataBoundToKyThuat() {
     // this.$refs['grid-tokythuat'].selectRow(0)
    },
    onRowSelectedToKyThuat: async function(args) {
        //console.log(args)
       this.$refs['grid-tokythuat'].$el.querySelectorAll('.col-mark-selected-row').forEach(item => {
         item.style.display = args.rowIndex == parseInt(item.id) ? 'block' : 'none'
      })
    },
     onCreateGridToKyThuat() { 
      this.$refs['grid-tokythuat'].$el.classList.add('table-result')
    },

    //#endregion
   
   //#region "code mới theo gird mới"
   tuyencap_selectedRowChanged: async function(args){
     if(args!=null){
     this.tuyenCap.tuyencap_id=args.TUYENCAP_ID
      await  this.getChitietTuyenCap() 
   }
   },
   captrentuyen_selectedCheckbox: function(data){
    if(data!=null)
     this.dsCapTrenTuyen_check=data
   },
   capchuagan_selectedCheckbox:function(data){
    if(data!=null)
      this.dsCapChuaGan_check=data
   },
   capchuagan_PageChanged:async function(args){
   // this.$root.showLoading(true);
      try {
     //   this.paging_chuagan.pNo= args.pageIndex +1
      //  this.paging_chuagan.pSize= args.pageSize 
      //  await this.getdsCapChuaGan_Paging(this.paging_chuagan.pNo, this.paging_chuagan.pSize);
        //  this.$refs['grid-capchuagan'].flagSelectedRowIndexes.push(-1)
       //   this.dsCapChuaGan_check =[]
      } finally {
        this.$root.showLoading(false);
      }
   },
   captrentuyen_PageChanged:async function(args){
   // this.$root.showLoading(true);
      try {
      //   this.paging_trentuyen.pNo= args.pageIndex +1
      //   this.paging_trentuyen.pSize= args.pageSize 
      //   await this.getdsCapTrenTuyen_Paging(this.paging_trentuyen.pNo, this.paging_trentuyen.pSize);
        // this.$refs['grid-captrentuyen'].flagSelectedRowIndexes.push(-1)
       //  this.dsCapTrenTuyen_check=[]
      } finally {
        this.$root.showLoading(false);
      }
   },
   toKT_selectedRowChanged: function(args){
   if(args!=null)
    this.tokythuat_id = args.DONVI_ID
   },
   //#endregion

    //#region CRUD Tuyến Cáp
    themMoiTuyenCap: async function(){
       try{
          this.$root.showLoading(true)
         if(!this.validateData()){
          var rs = await this.$root.context.post(
          "/web-cabman/tuyencap/themtuyencap",this.tuyenCap
           );
           if(rs.error_code=="BSS-00000000"){             
           this.$root.toastSuccess("Thêm mới tuyến cáp truyền dẫn thành công")
            this.getDsTuyenCap()
            this.$refs['grid-tuyencap'].selectRow(0)
            }
           else
             this.$root.toastError("Thêm mới tuyến cáp truyền dẫn lỗi")
          }
       }catch(err){
         this.$root.toastError(err.response.data.message_detail)
       }
       finally{
         this.$root.showLoading(false)
       }
    },
    capNhatTuyenCap: async function(){
      try{
          this.$root.showLoading(true)
           if(!this.validateData()){
          var rs = await this.$root.context.post(
          "/web-cabman/tuyencap/suatuyencap",this.tuyenCap
           );
           if(rs.error_code=="BSS-00000000"){
            this.$root.toastSuccess("Cập nhật tuyến cáp truyền dẫn thành công")
            this.getDsTuyenCap()
            }
           else
             this.$root.toastError("Cập nhật tuyến cáp truyền dẫn lỗi")
           }
       }catch(err){
         this.$root.toastError(err.response.data.message_detail)
       }
       finally{
         this.$root.showLoading(false)
       }
    },
    ghiLaiTuyenCap: async function(){
      if( this.tuyenCap.tuyencap_id==-1)
       this.action=0
       else
       this.action=1
       if(this.action==0)
        await this.themMoiTuyenCap()
        else if (this.action==1)
        await this.capNhatTuyenCap()
    },
    huyBo: async function(){
      //this.action=3
      if(this.action==0)
       this.clearForm()
      else if(this.action==1){
      Object.assign(this.tuyenCap,this.tuyencapTemp)
      this.setDisableBtn(false,false,false,false)
      }
    },
    xoaTuyenCap: async function(){
    this.action=2
    this.modalXoa=true   
    },
    xacNhanXoa: async function(){
     try{
          this.$root.showLoading(true)
          var rs = await this.$root.context.post(
          "/web-cabman/tuyencap/xoatuyencap/"+this.tuyenCap.tuyencap_id
           );
           if(rs.error_code=="BSS-00000000"){
            this.$root.toastSuccess("Xóa tuyến cáp truyền dẫn thành công")
            this.getDsTuyenCap()
            this.$refs['grid-tuyencap'].selectRow(0)}
           else
             this.$root.toastError("Xóa tuyến cáp truyền dẫn lỗi")
       }catch(err){
         if(err.response.data.message_detail =="Không thể cập nhật hoặc xóa do dữ liệu liên quan")
           this.$root.toastError(err.response.data.message_detail)
        else
          this.$root.toastError("Xóa tuyến cáp truyền dẫn lỗi")
       }
       finally{
         this.$root.showLoading(false)
       }
    },
    clearForm: async function(){
         this.tuyencapTemp=Object.assign({}, this.tuyenCap)
         this.tuyen_id = this.tuyenCap.tuyencap_id
         this.tuyenCap.tuyencap_id=-1
         this.tuyenCap.ma_tc=""
         this.tuyenCap.tuyen_cap=""
         this.tuyenCap.donvi_id= null
         this.tuyenCap.ghichu=""
         this.action=0
         this.setDisableBtn(true,false,false,true)
         this.$v.$reset()
    },
    validateData: function(){
     try{
    this.$v.$touch();
    let errorMessage = []
    if (this.$v.$invalid) {
    if(!this.$v.tuyenCap.ma_tc.required)
        errorMessage.push("Trường mã tuyến cáp bắt buộc có dữ liệu.")
    if(!this.$v.tuyenCap.ma_tc.maxLength)
        errorMessage.push("Trường mã tuyến cáp dữ liệu vượt quá độ dài cho phép.") 
    if(!this.$v.tuyenCap.tuyen_cap.maxLength)
        errorMessage.push("Trường tên tuyến dữ liệu vượt quá độ dài cho phép.")         
    if(!this.$v.tuyenCap.tuyen_cap.required)
        errorMessage.push("Trường tên tuyến bắt buộc có dữ liệu.")
     this.$root.toastError(errorMessage.join('\n'))
      return true   
      }
     return false
    }
    catch(e){
      console.log(e)
    }
     },
    //#endregion

    //#region "Gán/ Gỡ cáp" 
    ganVaoTuyen:async function(){
     if(this.tabIndex==1){
          try{            
          this.$root.showLoading(true)
          if(this.kiemtraGanCapVaoTuyen()){
          let cap_ids= this.dsCapChuaGan_check.join(',')// rows.map(x=>x.CAP_ID).join(',')
          let data={
           tuyencap_id: this.tuyen_id,
           dscap_id:cap_ids
          }
          var rs = await this.$root.context.post(
          "/web-cabman/tuyencap/gancap_vaotuyen",data
           );
           if(rs.error_code=="BSS-00000000"){             
            this.$root.toastSuccess("Gán cáp vào tuyến cáp thành công")
            //await this.getdsCapTrenTuyen_Paging(this.paging_trentuyen.pNo, this.paging_trentuyen.pSize)
           // await this.getdsCapChuaGan_Paging(this.paging_chuagan.pNo, this.paging_chuagan.pSize)  
            await this.getdsCapTrenTuyen()
            await this.getdsCapChuaGan()          
            }
           else
             this.$root.toastError("Gán cáp vào tuyến cáp lỗi")
            }
       }catch(err){
        this.$root.toastError("Gán cáp vào tuyến cáp lỗi")
       }
       finally{
         this.$root.showLoading(false)
       }
     }else{
       try{
          this.$root.showLoading(true)
          if(this.kiemtraGanDonVi()){
          let cap_ids= this.dsCapTrenTuyen_check.join(',')
          let data={            
              donvi_id:this.tokythuat_id,
              tuyencap_id:this.tuyen_id,
              dscap_id:cap_ids           
          }
          var rs = await this.$root.context.post(
          "/web-cabman/tuyencap/gandonvi_qltuyen",data
           );
           if(rs.error_code=="BSS-00000000"){             
            this.$root.toastSuccess("Gán đơn vị vào cáp  thành công")
              await this.getdsCapTrenTuyen()
              //await this.getdsCapTrenTuyen_Paging(this.paging_trentuyen.pNo, this.paging_trentuyen.pSize)  
            }
           else
             this.$root.toastError("Gán đơn vị vào cáp lỗi")
          }
       }catch(err){
        this.$root.toastError("Gán đơn vị vào cáp lỗi")
       }
       finally{
         this.$root.showLoading(false)
       }
     }
    } ,
    goKhoiTuyen:async function(){
     if(this.tabIndex==1){
      try{
          this.$root.showLoading(true)        
          if(this.kiemtraGoCapKhoiTuyen()){
          let cap_ids= this.dsCapTrenTuyen_check.join(',')
          let data={
           tuyencap_id: this.tuyen_id,
           dscap_id:cap_ids
          }
          let rs = await this.$root.context.post(
          "/web-cabman/tuyencap/gocap_khoituyen",data
           );
           if(rs.error_code=="BSS-00000000"){             
            this.$root.toastSuccess("Gỡ gán cáp thành công")
            //await this.getdsCapTrenTuyen_Paging(this.paging_trentuyen.pNo, this.paging_trentuyen.pSize)
            //await this.getdsCapChuaGan_Paging(this.paging_chuagan.pNo, this.paging_chuagan.pSize)   
              await this.getdsCapTrenTuyen()
              await this.getdsCapChuaGan()
            }
           else
             this.$root.toastError("Gỡ gán cáp lỗi")
          }
       }catch(err){
        this.$root.toastError("Gỡ gán cáp lỗi")
       }
       finally{
         this.$root.showLoading(false)
       }
     }else{
        try{
          this.$root.showLoading(true)
          if(this.kiemtraGoDonVi()){
          let cap_ids=this.dsCapTrenTuyen_check.join(',') 
          let data={
              tuyencap_id:this.tuyen_id,
              dscap_id:cap_ids
          }
          var rs = await this.$root.context.post(
          "/web-cabman/tuyencap/godonvi_qltuyen",data
           );
           if(rs.error_code=="BSS-00000000"){             
            this.$root.toastSuccess("Gỡ gán đơn vị quản lý cáp thành công")
            await this.getdsCapTrenTuyen()
            // await this.getdsCapTrenTuyen_Paging(this.paging_trentuyen.pNo, this.paging_trentuyen.pSize) 
            }
           else
             this.$root.toastError("Gỡ gán đơn vị quản lý cáp lỗi")
          }
       }catch(err){
        this.$root.toastError("Gỡ gán đơn vị quản lý cáp lỗi")
       }
       finally{
         this.$root.showLoading(false)
       }
     }
    },
    kiemtraGanCapVaoTuyen:function(){
       if(this.tuyen_id==-1){
         this.$root.toastError("Hãy chọn tuyến")
         return false
       }
      // let rows = this.dsCapChuaGan.filter(item=>item.CHON==true)
      // if(rows==null || rows.length==0){
       if(this.dsCapChuaGan_check.length==0){
          this.$root.toastError("Hãy chọn cáp để gán vào tuyến")
         return false
       }
       return true
    },
    kiemtraGoCapKhoiTuyen:function(){
     if(this.tuyen_id==-1){
         this.$root.toastError("Hãy chọn tuyến")
         return false
       }
      // let rows = this.dsCapTrenTuyen.filter(item=>item.CHON==true)
      // if(rows==null || rows.length==0){
      if(this.dsCapTrenTuyen_check.length==0){
          this.$root.toastError("Hãy chọn cáp để gỡ khỏi tuyến")
         return false
       }
       return true
    },
    kiemtraGanDonVi:function(){
       if(this.tuyen_id==-1){
         this.$root.toastError("Hãy chọn tuyến để gán gán đơn vị quản lý cáp")
         return false
       }
       if(this.tokythuat_id==-1){
         this.$root.toastError("Hãy chọn tổ kỹ thuật để gán")
         return false
       }
        if(this.dsCapTrenTuyen_check.length==0){
          this.$root.toastError("Hãy chọn cáp để gán đơn vị quản lý")
         return false
       }
       return true
    },
    kiemtraGoDonVi:function(){
      try{
      if(this.tuyen_id==-1){
         this.$root.toastError("Hãy chọn tuyến")
         return false
       }
     //  let grid= this.$refs['grid-captrentuyen'].$refs.grid
     //  let rec= grid.getSelectedRecords()
     //  this.dsCapTrenTuyen_check = this.$refs['grid-captrentuyen'].getSelectedValues(rec)
      if(this.dsCapTrenTuyen_check.length==0){
          this.$root.toastError("Hãy chọn cáp muốn bỏ đơn vị quản lý")
         return false
       }
       return true 
      }catch(e){
        console.log(e)
      }  
    },
    //#endregion
    nhapMoi: function(){
      this.clearForm();
      if(this.$root.token.getPhanVungID() == 21)
      this.getMaTC();
    },
    getMaTC:async function(){
    let ma_tc = "";
    let key = await  this.getKey("FOS_ID");
    if(key != null)
    ma_tc = "FOS"+ this.leftPad(key,9);
    else
    ma_tc = "FOS000000000";
    this.tuyenCap.ma_tc = ma_tc;
    },
    async getKey(key) {
      let rs = await this.$root.context.get("web-cabman/them-hoso-ptm/get-key", {key: key})
      return rs.data
    },
    leftPad(number, targetLength) {
    var output = number + '';
    while (output.length < targetLength) {
        output = '0' + output;
    }
    return output;
    },
    captrentuyen_PageChanged: function(data){}
 },
  data() {
    let vm=this
    return {
     dsTuyenCap:[],
     dsCapTrenTuyen:[],
     dsCapChuaGan:[],
     dsDonViQLCap:[],
     dsToKyThuat:[],
     dsTTVT:[],
     dsCapTrenTuyen_check:[],
     dsCapChuaGan_check:[],
     tokythuat_id:-1,
     tuyenCap:{
      tuyencap_id:null,
      ma_tc:"",
      tuyen_cap:"",
      donvi_id: null,
      ghichu:""
     },
    tuyen_id:-1,
    tuyencapTemp:Object,
    kiemtra:true  ,
    chuoiTimKiem:"",
    isDisableBtnNhapMoi: false,
    isDisableBtnGhiLai: false,
    isDisableBtnHuy: true,
    isDisableBtnXoa: false,
    modalXoa:false,
    tabIndex:1,
    cate:{
     dinhdanh:0,
     quanly:0
    },
    action:0,//0: Thêm mới; 1: Cập Nhật; 2: Xóa; 3: None
    //#region "tempalte"
      columnTemplate: function () {
          return { 
              template: {
                  template: '<span :id="data.index" :style="{display: display }" style="font-size: 12px;" class="col-mark-selected-row fa fa-play text-main"></span>',
                  data() {
                      return { 
                          data: { } 
                      }
                  },
                  computed: {
                    display() {
                      return parseInt(this.data.index) == this.$parent.getSelectedRowIndexes()[0] ? 'block' : 'none'
                    }
                  }
              }
          }
      },
      headerTemplate: function () {
          return { 
              template: {
                  template: '<span>Chọn</span>',
                  data() {
                      return { 
                          data: { } 
                      }
                  },                 
              }
          }
      },
      columnChkTemplate: function () {
          return { 
              template: {
                  template: '<input v-model="data.CHON" type="checkbox" class="check" style="height:16px;width:16px">',
                  data() {
                      return { 
                          data: { } 
                      }
                  },
                  mouted() {
                  },
                  watch:{
                    'data.CHON': function(val){ 
                      if(this.$parent.dataSource!=null && this.$parent.dataSource.length>0)
                      this.$parent.dataSource.filter(item => item.CAP_ID == this.data.CAP_ID)[0].CHON = val  
                    }
                  },
                  computed: {     
                  }
              }
          }
      },
      filterBarChkTemplate: {
            create: function (args) {
              let div = document.createElement('div')
              div.classList.add('check-action')
              let input = document.createElement('input')
              input.classList.add('check')
              input.type="checkbox"
              input.id = args.column.foreignKeyField
              let span = document.createElement('span')
              span.classList.add('name')
              div.style="text-align:center"
              div.append(input)
              div.append(span)
              return div
          },
           write: function (args) {

              args.element.querySelector('input').addEventListener('click', (args) => {
                  let value = args.target.checked
                  if (!value) {
                      this.parent.removeFilteredColsByField(args.target.id);
                  } else {
                      this.parent.filterByColumn(args.target.id, 'equal', value)
                  }
              })
          }
      },
      filterBarRadioTemplate: {
            create: function (args) {
              let div = document.createElement('div')
              div.classList.add('check-action')
              let input = document.createElement('input')
              input.classList.add('check')
              input.type="checkbox"
              input.id = args.column.foreignKeyField
              let span = document.createElement('span')
              span.classList.add('name')
              div.style="text-align:center"
              div.append(input)
              div.append(span)
              return div
          },
           write: function (args) {

              args.element.querySelector('input').addEventListener('click', (args) => {
                  let value = args.target.checked
                  if (!value) {
                      this.parent.removeFilteredColsByField(args.target.id);
                  } else {
                      this.parent.filterByColumn(args.target.id, 'equal', value)
                  }
              })
          }
      },
      
      columnRadioTemplate: function () {
          return { 
              template: {
                  template: '<input v-model="dataChon" :value="chon" name="radio_cap" type="radio" class="check" style="height:16px;width:16px">',
                  data() {
                      return { 
                          data: { },
                          dataChon:true
                      }
                  },
                  mouted() {
                  },
                  methods:{

                  },
                  watch:{
                    'dataChon': function(val){ 
                      if(this.$parent.dataSource!=null && this.$parent.dataSource.length>0){
                      this.$parent.dataSource.filter(item => item.DONVI_ID == this.data.DONVI_ID)[0].CHON = true 
                      this.$parent.dataSource.filter(item => {
                        if(item.DONVI_ID != this.data.DONVI_ID)
                           item.CHON=false})
                      }
                     
                    }
                  },
                  computed: {   
                    chon: function(){
                      let x = this.$parent.dataSource.filter(item => item.DONVI_ID == this.data.DONVI_ID)
                      if(x!=null && x.length>0)
                       return  x[0].CHON 
                       else
                      return false
                    }  
                  }
              }
          }
      },
      filterBarRadioTemplate_: {
            create: function (args) {
              let div = document.createElement('div')
              div.classList.add('check-action')
              let input = document.createElement('input')
              input.classList.add('check')
              input.type="radio"
              input.disabled='disabled'
              input.id = args.column.foreignKeyField
              let span = document.createElement('span')
              span.classList.add('name')
              div.style="text-align:center"
              div.append(input)
              div.append(span)
              return div
          },
           write: function (args) {

              // args.element.querySelector('input').addEventListener('click', (args) => {
              //     let value = args.target.checked
              //     if (!value) {
              //         this.parent.removeFilteredColsByField(args.target.id);
              //     } else {
              //         this.parent.filterByColumn(args.target.id, 'equal', value)
              //     }
              // })
          }
      },
      filterBarTemplate: {
            create: function (args) {

              let div = document.createElement('div')
              div.classList.add('input-icon-right')
              
              let input = document.createElement('input')
              input.classList.add('form-control')
              input.id = args.column.foreignKeyField

              let span = document.createElement('span')
              span.classList.add('icon', 'nc-icon-outline', 'ui-1_zoom')

              div.append(input)
              div.append(span)

              return div
          },
          write: function (args) {
              args.element.querySelector('input').addEventListener('input', (args) => {

                  let value = args.currentTarget.value

                  if (!value) {
                      this.parent.removeFilteredColsByField(args.currentTarget.id);
                  } else {
                      this.parent.filterByColumn(args.currentTarget.id, 'contains', value)
                  }
              })
          }
      },
      filterBarTemplate1: {
            create: function (args) {

              let div = document.createElement('div')
              div.classList.add('input-icon-right')
              
              let input = document.createElement('input')
              input.classList.add('form-control')
              input.id = args.column.foreignKeyField

              let span = document.createElement('span')
              span.classList.add('icon', 'nc-icon-outline', 'ui-1_zoom')

              div.append(input)
              div.append(span)

              return div
          },
          write: function (args) {
              args.element.querySelector('input').addEventListener('input', (args) => {

                  let value = args.currentTarget.value

                  if (!value) {
                      this.parent.removeFilteredColsByField(args.currentTarget.id);
                  } else {
                      this.parent.filterByColumn(args.currentTarget.id, 'contains', value)
                  }
              })
          }
      },
      beforeRender:async function (args) { 
        try{
          if(args.target.innerText!=""){
         if (args.target.closest("td")) {
           vm.$refs.tooltip.content = args.target.innerText;}
        }
        }catch(e){}
      } ,
      //#endregion
    totalRow_captrentuyen:0, 
    totalRow_capchuagan:0,
    totalRow_dsTuyen:0,
    paging_trentuyen:{
     pNo:0,
     pSize:10
    },
    paging_chuagan:{
     pNo:0,
     pSize:10
    }
    };
  },
   validations: {
     tuyenCap:{
        ma_tc:{required,
                maxLength:maxLength(100)},
        tuyen_cap:{required,
                maxLength:maxLength(100)}
        }
    },
};
</script>
<style>
.modal-fullscreen .modal-dialog {
    max-width: 100%;
    margin: 0;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    height: 100vh;
    display: flex;
    position: fixed;
    z-index: 100000;
  }
.e-fltrTemp{
  text-align: center !important;
}
input[type=radio]{
  text-align: center !important;
}
 .e-filterbarcell {
      padding: 5px 7px !important;
  }

  th.e-headercell, th.e-filterbarcell {
      background-color: #BAE7FF !important;
      border-color: white !important;
  }

  th.e-filterbarcell {
      border-width: 1px 0 0 1px !important;
  }

  .e-grid {
      font-family: "Roboto", Arial !important;
  }

  .e-grid td.e-active {
      background: #F9E1A9 !important;
  }

  .e-grid.e-gridhover tr[role='row']:not(.e-editedrow):hover .e-rowcell:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
  .e-grid.e-gridhover tr[role='row']:hover .e-detailrowcollapse:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
  .e-grid.e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
  .e-grid.e-rtl .e-gridhover tr[role='row']:hover .e-rowdragdrop:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell),
  .e-grid.e-gridhover tr[role='row']:hover .e-detailrowexpand:not(.e-cellselectionbackground):not(.e-active):not(.e-updatedtd):not(.e-indentcell) {
    background-color: #FFF9EB !important;
  }

  .e-grid td.e-rowcell {
      line-height: 20px !important;
      padding: 5px 7px !important;
  }

  .e-grid .e-filterdiv.e-fltrinputdiv .e-input-group.e-control-wrapper {
      border: 0 !important;
  }

  .e-grid .e-filtertext.e-input.e-disable {
      display: none !important;
  }

  .e-grid .cell-error-custom {
    color: white;
    background-color: red;
    width: 20px;
    height: 20px;
    border-radius: 10px;
    text-align: center;
    float: left;
  }
  .e-grid tr.e-emptyrow td {
    text-align: center !important;
  }
div#popupTCTTTB___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>