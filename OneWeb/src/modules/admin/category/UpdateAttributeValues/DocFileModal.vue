<template>
    <b-modal
    ref="popupDocFile"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal">
     <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title"><span class="icon one-notepad"></span>Đọc file giá trị thuộc tính</div>
                <div class="close -ap icon-close" @click="hideModal"></div>
            </div>
            <ActionTop :actions="actions" @onActionClick="onActionClick"/>
            <div class="popup-body">
                <div class="box-form">
                    <div class="info-row">
                        <div class="key w80">Đường dẫn</div>
                        <div class="value">
                            <div class="input-more-button">
                                <button class="btn" @click="chooseFile">
                                    <span class="-ap icon-more_horiz"></span>
                                </button>
                                <input id="file" ref="fileInput" type="file" @change="onChangeFile($event)" accept=".xls, .xlsx" style="display:none" class="form-control ">
                                <input type="text" v-model="file_full_path" class="form-control" readonly>
                            </div>
                        </div>
                        <div class="value w80 nowrap padt7">
                            <a class="link underline" href="/static/files/Bieumau_Giatri_Thuoctinh.xlsx">Tải biểu mẫu</a>
                        </div>
                    </div>
                    <div class="legend-title mart20">
                            Danh sách giá trị thuộc tính
                    </div>
                    <div class="nav tabs tab-over">
                        <a @change="changeTab(1)" href="#tabDs" :class="{active: tab_selected==1}" data-toggle="tab">
                            <span class="icon f20 nc-icon-glyph design_bullet-list-67"></span> Danh sách
                        </a>
                        <a @change="changeTab(1)" href="#tabDsLoi" :class="{active: tab_selected==2}" data-toggle="tab">
                            <span class="icon f20 one-alert text-warning"></span> Lỗi
                        </a>
                    </div>
                    <div class="tab-content">
                        <div id="tabDs" class="tab-pane" :class="{active: tab_selected==1}" >
                            <KTableV3 :columns="columns" :dataSources="dataSources"/>
                        </div>
                        <div id="tabDsLoi" class="tab-pane" :class="{active: tab_selected==2}" >
                            <KTableV3 :columns="columnsLoi" :dataSources="dataSourcesError"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>   
    </b-modal>
</template>
<script>
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import XLSX from 'xlsx'
import UpdateAttributeValuesAPI from './UpdateAttributeValuesAPI'
export default {
    name:'DocFileModal',
    props:{
        thuoctinh:{
            type: Array,
            default: ()=>[]
        }
    },
    components:{
        KTableV3,
        ActionTop
    },
    data(){
        return{
            actions:[
                {
                    id:'chapnhan',
                    name:'Chấp nhận',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_check-circle-08'
                }
            ],
            columns:[
                {
                    field: "MA_TT",
                    label: "Mã thuộc tính",
                    allowFilter: true
                },
                {
                    field: "GIATRI",
                    label: "Giá trị",
                    allowFilter: true
                },
                {
                    field: "GHICHU",
                    label: "Ghi chú",
                    allowFilter: true
                }
            ],
            dataSources:[
            ],
            columnsLoi:[
                {
                    field: "MA_TT",
                    label: "Mã thuộc tính",
                    allowFilter: true
                },
                {
                    field: "GIATRI",
                    label: "Giá trị",
                    allowFilter: true
                },
                {
                    field: "GHICHU",
                    label: "Ghi chú",
                    allowFilter: true
                },
                {
                    field: "LOI",
                    label: "Ghi chú",
                    allowFilter: true
                }
            ],
            dataSourcesError:[],
            danhsach_thuoctinh:this.thuoctinh,
            tab_selected:1,
            file_full_path:'',
            file:null,
            giatri_tt:[]

        }
    },
    methods:{
        showModal() {
            this.$refs["popupDocFile"].show()
        },
        hideModal() {
            this.$refs["popupDocFile"].hide()
        },
        changeTab(value){
            this.tab_selected=value
        },
        onActionClick(action){
            if(action.id=='chapnhan'){
                if(this.dataSources.length>0){
                    let data=this.lay_danhsach_giatri()
                    
                    this.sp_upd_giatritt(JSON.stringify(data))
                }else{
                    this.$toast.error('Không có dữ liệu phù hợp để thêm mới!')
                }
            }
        },
        handleShowModal(){
            this.tab_selected=1
            this.dataSources=[]
            this.dataSourcesError=[]
            this.file_full_path=''
            this.file=null
            this.giatri_tt=[]

            //init get all mảng giá trị
            this.sp_grv_thuoctinh(0)

        },
        async resetAfterImport(){
            this.dataSources=[]
            this.dataSourcesError=[]
            this.danhsach_excel=[]
            await this.sp_grv_thuoctinh(0)
            await this.readFileExcel()
        },
        onChangeFile(event){
            this.file_full_path = event.target.value
            this.file=event.target.files[0]
            this.readFileExcel()

        },
        chooseFile(){
            this.$refs.fileInput.value=null
            document.getElementById("file").click()
        },
        tai_bieu_mau(){
            
        },
        lay_thuoctinh_id_theo_ma_tt(ma_tt){
            return this.danhsach_thuoctinh[this.danhsach_thuoctinh.findIndex(x=>x.ma_tt==ma_tt)].thuoctinh_id
        },
        lay_danhsach_giatri(){
            return this.dataSources.map(item=>{
                return {
                    GTTT_ID:0,
                    THUOCTINH_ID:this.lay_thuoctinh_id_theo_ma_tt(this.toString(item.MA_TT)),
                    GIATRI:this.toString(item.GIATRI),
                    GHICHU: item.GHICHU?this.toString(item.GHICHU):''
                }
            })
            // let giatri="["
            // this.dataSources.forEach((item, index)=>{
            //     var row="{'GTTT_ID':#1,'THUOCTINH_ID':#2,'GIATRI':'#3','GHICHU':'#4'}"
            //     row=row.replace("#1", 0)
            //     row=row.replace("#2", this.lay_thuoctinh_id_theo_ma_tt(this.toString(item.MA_TT)))
            //     row=row.replace("#3", this.toString(item.GIATRI))
            //     row=row.replace("#4", item.GHICHU!=undefined?this.toString(item.GHICHU):'')
            //     if(index==this.dataSources.length-1){
            //         giatri=giatri.concat(row)
            //     }else{
            //         giatri=giatri.concat(row).concat(', ')
            //     }
                
            // })
            // giatri=giatri.concat("]")
            // return giatri
        },
        tontai_thuoctinh(item){
            const index=this.danhsach_thuoctinh.findIndex(x=>x.ma_tt==this.toString(item.MA_TT))
            if(index>-1){
                return true
            }
            return false
        },
        tontai_giatri(item){
            
            const index=this.danhsach_thuoctinh.findIndex(x=>x.ma_tt==this.toString(item.MA_TT))
            if(index>-1){
                const indexGT=this.giatri_tt.findIndex(x=>x.thuoctinh_id==this.danhsach_thuoctinh[index].thuoctinh_id&&x.giatri==this.toString(item.GIATRI))
                if(indexGT>-1){
                    return true
                }
                return false
            }
            return false
        },
        readFileExcel(){
            this.dataSources=[]
            this.dataSourcesError=[]
            this.danhsach_excel=[]
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
                                    MA_TT:data[i][0],
                                    GIATRI:data[i][1],
                                    GHICHU:data[i][2]
                                })  
                            }
                            if(this.danhsach_excel.length==0){
                                return
                            }
                           
                            // Lọc data đúng chuẩn
                            // Kiễm tra giá trị đã tồn tại thì không push
                            this.danhsach_excel.forEach((item)=>{
                                if(item.MA_TT!=undefined&&this.toString(item.MA_TT).trim()!=''&&item.GIATRI!=undefined&&this.toString(item.GIATRI).trim()!=''&&this.tontai_thuoctinh(item)&&!this.tontai_giatri(item)){
                                    this.dataSources.push(item)
                                }
                            })
                            // Lọc danh sách lỗi
                            this.danhsach_excel.forEach((item)=>{
                                if(item.MA_TT==undefined||item.GIATRI==undefined||this.toString(item.MA_TT).trim()==''||this.toString(item.GIATRI).trim()==''){
                                    this.dataSourcesError.push(Object.assign(item,{
                                        LOI:'Giá trị rỗng'
                                    }))
                                    return
                                }
                                if(!this.tontai_thuoctinh(item)){
                                    this.dataSourcesError.push(Object.assign(item,{
                                        LOI:'Mã thuộc tính không tồn tại'
                                    }))
                                    return
                                }
                                if(this.tontai_giatri(item)){
                                    this.dataSourcesError.push(Object.assign(item,{
                                        LOI:'Các giá trị theo thuộc tính đã tồn tại'
                                    }))
                                }
                            })     
                        }
                        
                    }
                    reader.readAsBinaryString(this.file)
                }
            } catch (error) {
                this.$toast.error('Có lỗi xảy ra')
            } finally {
                this.loading(false)
            }
        },
        toString(value){
            return value+""
        },
        checkfile: function () {
            if (this.file == null) {
                this.$toast.error('Chưa chọn file excel')
                return false
            }
            if (this.file.type != "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"){
                this.$toast.error('File upload không đúng định dạng')
                return false
            }
            return true
        },
        kiemTraDuLieu: function (columns) {
            if (columns == null) {
                this.$toast.error('File có cấu trúc không hợp lệ!')
                return false
            }
            if (!(columns[0]!=undefined&&columns[0].toString().trim().toUpperCase() == "MA_TT" &&
                columns[1]!=undefined&&columns[1].toString().trim().toUpperCase() == "GIATRI"&&
                columns[2]!=undefined&&columns[2].toString().trim().toUpperCase() == "GHICHU")) {
                this.$toast.error('File có cấu trúc không hợp lệ!')
                return false
            }
            return true
        },
        async sp_grv_thuoctinh(thuoctinh_id){
            try{
                this.loading(true)
                this.giatri_tt=[]
                let response = await UpdateAttributeValuesAPI.sp_grv_thuoctinh(this.axios, thuoctinh_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.giatri_tt=response.data.data
                    
                }else{
                    this.giatri_tt=[]
                }
            }catch(e){
                this.$toast.error('Có lỗi khi lấy danh sách giá trị thuộc tính')
            }finally{
                this.loading(false)
            }
        },
        async sp_upd_giatritt(data){
            try{
                this.loading(true)
                let response=await UpdateAttributeValuesAPI.sp_upd_giatritt(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.$toast.success('Thêm mới giá trị thuộc tính thành công!')
                    this.resetAfterImport()
                }else {
                    if(response && response.data && response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Thêm mới giá trị thuộc tính không thành công!')
                    }
                    
                }
            }catch(error){
                this.loading(false)
                if(error.data&&error.data.message){
                    this.$toast.error(error.data.message)
                }else if(error.response&&error.response.data&&error.response.data.message){
                    this.$toast.error(error.response.data.message)
                }else{
                    this.$toast.error('Thêm mới giá trị thuộc tính không thành công!')
                }
            }
        }  
    },
    watch:{
        thuoctinh(val){
            this.danhsach_thuoctinh=val
        }
    }
}
</script>