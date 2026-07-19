<template>
  <b-modal
    ref="popupAssignCollectDebtStaff"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">Gán nhân viên thu cước</div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <input ref="fileInput" id="file" type="file" @change="onChangeFile($event)" accept=".xls, .xlsx" style="display:none" class="form-control ">
      <div class="popup-body">
        <div class="box-form padt8 padb8">
          <div class="info-row marb0">
            <div class="key w100">Mã người dùng</div>
            <div class="value">
              <div class="input-more-button">
                <button class="btn" @click="chooseFile" data-toggle="tooltip" data-placement="bottom" title="Đọc file NVTC. Định dạng file: MA_ND, MA_NVTC">
                  <span class="-ap icon-more_horiz"></span>
                </button>
                <input type="text" ref="inputMaND" v-model="input_ma_nd" @keypress.enter="enterMaND()" class="form-control" />
              </div>
            </div>
          </div>
        </div>
        <div class="box-move-select-table">
          <div class="box-col box-form">
            <div class="legend-title">NVTC đã gán</div>
            <KTableV3 v-show="isReadFile"
              :columns="columns"
              :dataSources="dataSourcesGan"
              :allowFilter="true"
              :allowCheckBox="true"
              tableHeight="240px"/>
            <DataGrid 
              ref="gridGan"
              v-show="!isReadFile"
              :columns="columnsG" 
              :dataSource="dataSourcesGan"
              :selectionSettings="selectionOptions"
              :showColumnCheckbox="true"
              :enable-paging-server="false"
              :allowPaging="true"
              panelDataHeight='240px'
              :showFilter="true"
              :enabledSelectFirstRow="false"
              @rowSelected="gridGan_RowSelected"
              @dataBound="dataBoundGan"
              @checkBoxChange="checkBoxGanChange"
              @rowDeselected="dsGanRowDeselected"
              @selectedItemsChanged="onDataCheckedsGan"
            />
            
          </div>
          <div class="actions">
            <button class="btn" @click="boGan">
              <span class="fa fa-angle-right"></span>
            </button>
            <button class="btn" @click="boGanAll">
              <span class="fa fa-angle-double-right"></span>
            </button>
            <button class="btn" @click="gan">
              <span class="fa fa-angle-left"></span>
            </button>
            <button class="btn" @click="ganAll">
              <span class="fa fa-angle-double-left"></span>
            </button>
          </div>
          <div class="box-col box-form">
            <div class="legend-title">NVTC chưa gán</div>
                <DataGrid 
                  ref="gridChuaGan"
                  :columns="columnsG" 
                  :dataSource="dataSourcesChuaGan"
                  :selectionSettings="selectionOptions"
                  :showColumnCheckbox="true"
                  :enable-paging-server="false"
                  :allowPaging="true"
                  panelDataHeight='240px'
                  :showFilter="true"
                  :enabledSelectFirstRow="false"
                  @rowSelected="gridChuaGan_RowSelected"
                  @dataBound="dataBoundChuaGan"
                  @checkBoxChange="checkBoxChuaGanChange"
                  @rowDeselected="dsChuaGanRowDeselected"
                  @selectedItemsChanged="onDataCheckedsChuaGan"
                />
          </div>
        </div>
      </div>
      <DanhSachLoiModal :data="danhsachloi_docfile" ref="danhsachloi"/>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import AssignCollectDebtStaffAPI from './AssignCollectDebtStaffAPI'
import DanhSachLoiModal from './DanhSachLoiModal.vue'
import XLSX from 'xlsx'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
export default {
    name:'AssignCollectDebtStaffModal',
    props:{
      p_nguoidung_id:{
        type:Number,
        default:0
      },
      p_ma_nd:{
        type:String,
        default:''
      }
    },
    components:{
        ActionTop,
        DanhSachLoiModal,
        KTableV3
    },
    data(){
        return{
            actions:[
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'file_mau',
                    name:'File mẫu',
                    active: true,
                    icon_class:'icon one-excel'
                }
            ],
            columns:[
              {
                  field: "ma_nd",
                  label: "Mã ND",
                  allowFilter: true,
                  allowSorting:false
              },
              {
                  field: "ma_nv",
                  label: "Mã NVTC",
                  allowFilter: true,
                  allowSorting:false
              },
              {
                  field: "ten_nv",
                  label: "Tên NVTC",
                  allowFilter: true,
                  allowSorting:false
              }
            ],
            columnsG:[
              {
                fieldName: "header_donvi",
                headerText: "",
                allowFiltering: true,
                allowSorting:false,
                isGroupedColumn: true
              },
              {
                fieldName: "ma_nv",
                headerText: "Mã NVTC",
                allowFiltering: true,
                allowSorting:false,
                width:120
              },
              {
                fieldName: "ten_nv",
                headerText: "Tên NVTC",
                allowFiltering: true,
                allowSorting:false
              }
            ],
            dataSourcesGan:[],
            dataSourcesChuaGan:[],
            nguoidung_id:-1,
            dataCheckGan:[],
            dataCheckChuaGan:[],
            input_ma_nd:'',
            file:null,
            danhsach_excel:[],
            isReadFile:false,
            danhsachloi_docfile:[],
            danhsach_thoaman:[],
            danhsach_excel:[],
            selectionOptions: {
              type: 'Multiple',
              checkBoxOnly: true
            },
        }
    },
    computed:{
      getColumns(){
        return this.isReadFile?this.columns:this.columnsG
      }
    },
    methods:{
        onActionClick(action){
            if(action.id=='ghilai'){
              this.ghiDuLieu()
            }else if(action.id=='file_mau'){
              this.taiFileMau()
            }
        },
        taiFileMau(){
          window.open("/static/files/MauFile_GanNhanVienThuCuoc.xlsx")
            // this.$confirm('Bạn có muốn tải file mẫu về không ?','Thông báo',{
            //     confirmButtonText: 'Đồng ý',
            //     cancelButtonText: 'Huỷ',
            //     type: 'info'
            // }).then(() => {
            //     //dùng tạm
            //     window.open("/static/files/MauFile_GanNhanVienThuCuoc.xlsx")
            // })
        },
        showModal() {
            this.$refs["popupAssignCollectDebtStaff"].show()
        },
        hideModal() {
            this.$refs["popupAssignCollectDebtStaff"].hide()
        },
        handleShowModal(){
          this.dataSourcesGan=[]
          this.dataSourcesChuaGan=[]
          this.dataCheckGan=[]
          this.dataCheckChuaGan=[]
          this.file=null,
          this.danhsach_excel=[]
          this.isReadFile=false
          this.input_ma_nd=''
          this.nguoidung_id=this.p_nguoidung_id

          this.danhsachloi_docfile=[]
          this.danhsach_thoaman=[]
          this.danhsach_excel=[]
          //this.nguoidung_id=this.$root.token.getNguoiDungID()
          //load từ form ngoài
          if(this.nguoidung_id!=-1&&this.nguoidung_id!=0){
            //load danh sách theo người dùng
            this.input_ma_nd=this.p_ma_nd
            this.hienthi_nvtc_theo_nguoidung_id(this.nguoidung_id)
          }
          setTimeout(()=>{
            this.$refs.inputMaND.focus()
          },1000)
        },
        async ghiDuLieu(){
          if(!this.isReadFile&&this.nguoidung_id==-1){
            this.$toast.error('Hãy tìm người dùng để gán!')
            return
          }
          // if(!this.isReadFile){
          //   //tạo dư liệu
          //   let data=this.taoDulieu()
          //   this.sp_nvtc_update(data)
          // }
          
          if(!this.isReadFile){
            let data=this.taoDulieu()
            await this.sp_nvtc_update(this.nguoidung_id, JSON.stringify(data))
          }else{
            if(this.dataSourcesGan.length<=0){
              return
            }
            var results = this.dataSourcesGan.reduce(function(results, org) {
                (results[org.nguoidung_id] = results[org.nguoidung_id] || []).push(org);
                return results;
            }, {})
            console.log('results', results)
            let ds_nguoidung_id=Object.keys(results)
            console.log('ds_nguoidung_id', ds_nguoidung_id)
            for(let i=0;i<ds_nguoidung_id.length;i++){
              let data=this.taoDulieuDocFile(Number(ds_nguoidung_id[i]), results[ds_nguoidung_id[i].toString()])
              console.log('data', data)
              await this.sp_nvtc_update_docfile(Number(ds_nguoidung_id[i]), JSON.stringify(data))
            }
            this.$toast.success('Cập nhật dữ liệu thành công!')
            this.$refs.inputMaND.focus()
          }
          
        },
        taoDulieuDocFile(nguoidung_id, ds){
          return ds.map(x=>{
            return {
              NGUOIDUNG_ID:nguoidung_id,
              DAILY_ID:x.nhanvien_id
            }
          })
          // let nhanvien_daily="["
          //   ds.forEach((item, index)=>{
          //     var row="{'NGUOIDUNG_ID': #1, 'DAILY_ID': #2}"
          //     //row=row.replace("#1", this.isReadFile?item.nguoidung_id:this.nguoidung_id)
          //     row=row.replace("#1", nguoidung_id)
          //     row=row.replace("#2", item.nhanvien_id)
          //     if(index==ds.length-1){
          //         nhanvien_daily=nhanvien_daily.concat(row)
          //     }else{
          //         nhanvien_daily=nhanvien_daily.concat(row).concat(', ')
          //     } 
          // })
          // nhanvien_daily=nhanvien_daily.concat("]")
          // return nhanvien_daily
        },
        taoDulieu(){
          return this.dataSourcesGan.map(x=>{
            return {
              NGUOIDUNG_ID:this.nguoidung_id,
              DAILY_ID:x.nhanvien_id
            }
          })
        },
        getDSImport(){
          return this.danhsach_thoaman.map(x=>{
            return {
              MA_ND:x.MA_ND,
              MA_NVTC:x.MA_NVTC
            }
          })
        },
        gridChuaGan_RowSelected(args){
          if (!args.isHeaderCheckboxClicked && args.isInteracted) {
            // this.updateDSKhoaSelected(args.data)
            console.log('gridChuaGan_RowSelected', args)
            if(Array.isArray(args.data)){
              for(let i=0;i<args.data.length;i++){
                const index = this.dataSourcesChuaGan.findIndex(x => x.nhanvien_id == args.data[i].nhanvien_id)
                this.dataSourcesChuaGan[index].ischeck='1'
              }
            }else {
              const index =  this.dataSourcesChuaGan.findIndex(x => x.nhanvien_id == args.data.nhanvien_id)
              this.dataSourcesChuaGan[index].ischeck='1'
            }
          }
        },
        dataBoundChuaGan(args){
          console.log('dataBoundChuaGan', args)
          let obj = this.$refs.gridChuaGan.$refs.grid.ej2Instances
          console.log('dataBoundChuaGan obj', obj)
          if (obj.currentViewData.records&&obj.currentViewData.records.length > 0) {
            let data = obj.currentViewData.records
            let selectedCurrentPage = []
            data.forEach((value, index) => {
              if (value.ischeck=='1') {
                selectedCurrentPage.push(index)
              }
            })
            obj.selectRows(selectedCurrentPage)
          }
        },
        checkBoxChuaGanChange(args){
          if (args.target.className=='e-checkselectall') { // click check/uncheck all
            console.log('checkBoxChuaGanChange', args)
            let dsFilter = this.$refs.gridChuaGan.filteredDataSource || []

            console.log('checkBoxChuaGanChange dsFilter', dsFilter)

            for(let i=0;i<dsFilter.length;i++){
              const index=this.dataSourcesChuaGan.findIndex(x=>x.nhanvien_id==dsFilter[i].nhanvien_id)
              if(index>-1){
                if(args.checked){
                  this.dataSourcesChuaGan[index].ischeck='1'
                }else{
                  this.dataSourcesChuaGan[index].ischeck='0'
                }
                
              }else{
                this.dataSourcesChuaGan[index].ischeck='0'
              }
            }
            console.log('checkBoxChuaGanChange',  this.dataSourcesChuaGan)
          }
        },
        dsChuaGanRowDeselected(args){
          console.log('dsChuaGanRowDeselected', args)
          if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header & paging
            // this.updateDSKhoaSelected(args.data)
            console.log('dsChuaGanRowDeselected', args)
            const index = this.dataSourcesChuaGan.findIndex(x => x.nhanvien_id == args.data.nhanvien_id)
            this.dataSourcesChuaGan[index].ischeck='0'
          }
        },

        
        gridGan_RowSelected(args){
          if (!args.isHeaderCheckboxClicked && args.isInteracted) {
            // this.updateDSKhoaSelected(args.data)
            console.log('gridGan_RowSelected', args)
            if(Array.isArray(args.data)){
              for(let i=0;i<args.data.length;i++){
                const index = this.dataSourcesGan.findIndex(x => x.nhanvien_id == args.data[i].nhanvien_id)
                this.dataSourcesGan[index].ischeck='1'
              }
            }else {
              const index =  this.dataSourcesGan.findIndex(x => x.nhanvien_id == args.data.nhanvien_id)
              this.dataSourcesGan[index].ischeck='1'
            }
          }
        },
        dataBoundGan(args){
          console.log('dataBoundGan', args)
          let obj = this.$refs.gridGan.$refs.grid.ej2Instances
          console.log('dataBoundGan obj', obj)
          if (obj.currentViewData.records&&obj.currentViewData.records.length > 0) {
            let data = obj.currentViewData.records
            let selectedCurrentPage = []
            data.forEach((value, index) => {
              if (value.ischeck=='1') {
                selectedCurrentPage.push(index)
              }
            })
            obj.selectRows(selectedCurrentPage)
          }
        },
        checkBoxGanChange(args){
          if (args.target.className=='e-checkselectall') { // click check/uncheck all
            console.log('checkBoxGanChange', args)
            let dsFilter = this.$refs.gridGan.filteredDataSource || []

            console.log('checkBoxGanChange dsFilter', dsFilter)

            for(let i=0;i<dsFilter.length;i++){
              const index=this.dataSourcesGan.findIndex(x=>x.nhanvien_id==dsFilter[i].nhanvien_id)
              if(index>-1){
                if(args.checked){
                  this.dataSourcesGan[index].ischeck='1'
                }else{
                  this.dataSourcesGan[index].ischeck='0'
                }
                
              }else{
                this.dataSourcesGan[index].ischeck='0'
              }
            }
            console.log('checkBoxGanChange',  this.dataSourcesGan)
          }
        },
        dsGanRowDeselected(args){
          console.log('dsGanRowDeselected', args)
          if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header & paging
            // this.updateDSKhoaSelected(args.data)
            console.log('dsGanRowDeselected', args)
            const index = this.dataSourcesGan.findIndex(x => x.nhanvien_id == args.data.nhanvien_id)
            this.dataSourcesGan[index].ischeck='0'
          }
        },


        async hienthi_nvtc_theo_nguoidung_id(nguoidung_id){
          this.dataSourcesGan=[]
          this.dataSourcesGan=await this.sp_nvtc_theo_nguoidung(1, nguoidung_id)
          this.dataSourcesChuaGan=[]
          this.dataSourcesChuaGan=await this.sp_nvtc_theo_nguoidung(0, nguoidung_id)
        },
        async sp_nvtc_theo_nguoidung(type, nguoidung_id){
            try{
                this.loading(true)
                let response = await AssignCollectDebtStaffAPI.sp_nvtc_theo_nguoidung(this.axios, type, nguoidung_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.map(x=>Object.assign(x,{
                      header_donvi:'Đơn vị: '+x.ten_dv,
                      ischeck:'0'
                    }))
                }else{
                    return []
                }
            }catch(e){
              this.loading(false)
              return []
            }
        },
        async sp_nvtc_nguoidung(ma_nd){
            try{
                this.loading(true)
                let response = await AssignCollectDebtStaffAPI.sp_nvtc_nguoidung(this.axios, ma_nd)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data

                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return
            }
        },
        async sp_nvtc_update(nguoidung_id, data){
            try{
                this.loading(true)
                let response=await AssignCollectDebtStaffAPI.sp_nvtc_update(this.axios, nguoidung_id, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Cập nhật dữ liệu thành công!')
                    this.$refs.inputMaND.focus()
                }else{
                    if(response && response.data&&response.data.message){
                      this.$toast.error(response.data.message)
                    }else{
                      this.$toast.error('Đã xảy ra lỗi, cập nhật dữ liệu không thành công!')
                    }
                    
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, cập nhật dữ liệu không thành công!')
                }
            }
        },
        //sài tạm
        async sp_nvtc_update_docfile(nguoidung_id, data){
            try{
                this.loading(true)
                await AssignCollectDebtStaffAPI.sp_nvtc_update(this.axios, nguoidung_id, data)
                this.loading(false)
            }catch(e){
                this.loading(false)
            }
        },
        async sp_nvtc_file_import(data){
            try{
                this.loading(true)
                this.dataSourcesGan=[]
                this.dataSourcesChuaGan=[]
                let response=await AssignCollectDebtStaffAPI.sp_nvtc_file_import(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    //danh sách lỗi
                    if(response.data.data.ds_error&&response.data.data.ds_error.length>0){
                      let resultError=response.data.data.ds_error[0]
                      if(resultError.js_err_ma_nd){

                        let err_ma_nd=JSON.parse(resultError.js_err_ma_nd)
                        err_ma_nd.forEach((item)=>{
                          this.danhsachloi_docfile.push({
                            MA_XLN:item.MA_ND,
                            LYDO:'Mã ND trống hoặc Mã ND không đúng, không tìm được ID'
                          })
                        })

                      }
                      if(resultError.js_err_ma_nvtc){

                        let err_ma_nvtc=JSON.parse(resultError.js_err_ma_nvtc)
                        err_ma_nvtc.forEach((item)=>{
                          this.danhsachloi_docfile.push({
                            MA_XLN:item.MA_NVTC,
                            LYDO:'Mã NVTC trống, không đúng hoặc không phải NVTC, không tìm được ID'
                          })
                        })

                      }
                      
                      //show modal lỗi
                      if(this.danhsachloi_docfile.length>0){
                        this.$refs.danhsachloi.showModal()
                      }

                    }
                    //danh sách thoả mãn
                    if(response.data.data.import_success){
                      this.dataSourcesGan=response.data.data.import_success
                    }

                }else{
                    if(response && response.data&&response.data.message){
                      this.$toast.error(response.data.message)
                    }else{
                      this.$toast.error('Đã xảy ra lỗi, upload dữ liệu không thành công!')
                    }
                    
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                  this.$toast.error('Đã xảy ra lỗi, upload dữ liệu không thành công!')
                }
            }
        },
        onDataCheckedsGan(data){
          //this.dataCheckGan=data
        },
        onDataCheckedsChuaGan(data){
          //this.dataCheckChuaGan=data
        },
        boGan(){
          this.dataCheckGan=this.dataSourcesGan.filter(x=>x.ischeck=='1')
          if(this.dataCheckGan.length==0){
            return
          }
          this.dataCheckGan.forEach((item)=>{
            this.dataSourcesChuaGan.push(item)
            const index = this.dataSourcesGan.findIndex(x=>x.nhanvien_id==item.nhanvien_id)
            if(index>-1){
              this.dataSourcesGan.splice(index,1)
            }
          })
          this.dataCheckGan=[]

        },
        boGanAll(){
          if(this.dataSourcesGan.length==0){
            return 
          }
          this.dataSourcesChuaGan=this.dataSourcesChuaGan.concat(this.dataSourcesGan)
          this.dataSourcesGan=[]
        },
        gan(){
          this.dataCheckChuaGan=this.dataSourcesChuaGan.filter(x=>x.ischeck=='1')
          if(this.dataCheckChuaGan.length==0){
            return
          }
          this.dataCheckChuaGan.forEach((item)=>{
            this.dataSourcesGan.push(item)
            const index = this.dataSourcesChuaGan.findIndex(x=>x.nhanvien_id==item.nhanvien_id)
            if(index>-1){
              this.dataSourcesChuaGan.splice(index,1)
            }
          })
          this.dataCheckChuaGan=[]
        },
        ganAll(){
          if(this.dataSourcesChuaGan.length==0){
            return 
          }
          this.dataSourcesGan=this.dataSourcesGan.concat(this.dataSourcesChuaGan)
          this.dataSourcesChuaGan=[]
        },
        async enterMaND(){
          if(this.input_ma_nd.trim()!=''){
            let result =await this.sp_nvtc_nguoidung(this.input_ma_nd.trim().toLowerCase())
            if(result.length>0){
              this.isReadFile=false
              this.nguoidung_id=result[0].nguoidung_id
              await this.hienthi_nvtc_theo_nguoidung_id(this.nguoidung_id)
            }else{
              this.$toast.error('Không tìm thấy người dùng có tên đăng nhập này!')
            }
          }
        },
        chooseFile(){
          this.$refs.fileInput.value=null
          document.getElementById("file").click()
        },
        onChangeFile(event){
          this.input_ma_nd=event.target.value.split('\\').pop()
          this.file=event.target.files[0]
          this.readFileExcel()
        },
        readFileExcel(){
            this.danhsach_excel=[]
            this.danhsachloi_docfile=[]
            this.danhsach_thoaman=[]
            let columns
            try {
                this.loading(true)
                if (this.checkfile()) {
                    const reader = new FileReader()
                    reader.onload = (e) => {
                        /* Parse data */
                        const bstr = e.target.result
                        const wb = XLSX.read(bstr, { type: "binary" })
                        /* Get first worksheet */
                        const wsname = wb.SheetNames[0];
                        const ws = wb.Sheets[wsname];
                        /* Convert array of arrays */
                        const data = XLSX.utils.sheet_to_json(ws, { header: 1 });
                        columns=data[0]
                        if(this.kiemTraDuLieu(columns)){
                          for(var i=1;i<data.length;i++){
                              this.danhsach_excel.push({
                                  MA_ND:data[i][0]!=undefined?data[i][0]:null,
                                  MA_NVTC:data[i][1]!=undefined?data[i][1]:null,
                              })
                          }
                          //Check dữ liệu
                          console.log('danhsach_excel',this.danhsach_excel)
                          //Check rổng rổng remove không lấy vào ds
                          if(this.danhsach_excel.length>0){
                            this.danhsach_excel.forEach((item)=>{
                              if(item.MA_ND!=null&&item.MA_NVTC!=null){
                                this.danhsach_thoaman.push(item)
                              }
                            })
                          }
                          console.log('danhsach_thoaman',this.danhsach_thoaman)
                          //chạy api check lấy dữ liệu thoã và ds lỗi
                          if(this.danhsach_thoaman.length>0){
                            this.isReadFile=true
                            let data=this.getDSImport()
                            this.sp_nvtc_file_import(JSON.stringify(data))
                          }else{
                            this.$toast.error('Dữ liệu file rỗng hoặc các giá trị không thoả mãn!')
                          }
                        }
                        
                    }
                    reader.readAsBinaryString(this.file)
                }
            } catch (error) {
                console.log(error)
                this.$toast.error('Có lỗi xảy ra khi đọc file')
            } finally {
                this.loading(false)
            }
        },
        checkfile() {
            if (this.file == null) {
                this.$toast.error('Chưa chọn file excel')
                return false
            }
            if (this.file.type != "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"){
                this.$toast.error('File import không đúng định dạng xlsx!')
                return false
            }
            return true
        },
        kiemTraDuLieu(columns) {
            if (columns == null) {
                this.$toast.error('File import có cấu trúc không hợp lệ!')
                return false
            }
            if (!(columns[0]!=undefined&&columns[0].toString().trim().toUpperCase() == "MA_ND" && columns[1]!=undefined&&columns[1].toString().trim().toUpperCase() == "MA_NVTC")) {
                this.$toast.error('File import cần có chính xác 2 cột MA_ND, MA_NVTC')
                return false
            }
            return true
        },

    }
}
</script>