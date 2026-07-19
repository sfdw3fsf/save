<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>
        <div class="page-content">
            <div class="box-form">
                <div class="legend-title">
                    <div class="pull-left">Thông tin file</div>
                    <div class="clearfix"></div>
                </div>
                <div class="inline note marb10 fw6"> <span class="one-alert text-warning f20 inline vcenter"></span> File excel gồm 3 trường: MA_TOANHA, LOAIHINHKD_ID, QUYMO_LAPDAY</div> <br>
                <p style="padding-left: 20px; font-style: italic;">
                    <span>- LOAIHINHKD_ID: 1: Tiềm năng chưa khai thác; 2: Đang khai thác; 3: Bão hòa (0: Bỏ qua không cập nhật)</span> <br>
                    <span>- QUYMO_LAPDAY: Kiểu số (Number), (0: Bỏ qua không cập nhật)</span> <br>
                </p>
                <div class="info-row">
                    <div class="key">Đường dẫn:</div>
                    <div class="input-more-button">
                        <button class="btn" @click="chooseFile">
                            <span class="-ap icon-more_horiz"></span>
                        </button>
                        <input ref="fileInput" id="file" type="file" @change="onChangeFile($event)" accept=".xls, .xlsx" style="display:none" class="form-control ">
                        <input type="text" v-model="file_full_path" class="form-control" readonly>
                    </div>
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">File Upload</div>
                <KDataGrid
                    :columns="columns"
                    :dataSource="dataSources"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="false"/>
            </div>

        </div>
    </div>
</template>
<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import XLSX from 'xlsx'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import API from './API'
export default {
    name:'CapNhatTTToaNhaFile',
    components:{
        breadcrumb,
        ActionTop,
        KDataGrid
    },
    data(){
        return {
            header: {
                title: "Cập nhật thông tin dự án tòa nhà theo file",
                list: [
                    { name: "Quản lý tòa nhà", 
                    link: { name: "Ui.cards" },
                    active: false
                    },
                    {
                        name: "Cập nhật thông tin dự án tòa nhà theo file",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ],
            },
            actions:[
                {
                    id: 'capnhat',
                    name: 'Cập nhật',
                    active: true,
                    icon_class: 'one-reload1'
                },
                {
                    id: 'huy',
                    name: 'Huỷ',
                    active: true,
                    icon_class: 'nc-icon-glyph ui-1_circle-remove',
                    tooltip: ''
                },
                {
                    id: 'file_mau',
                    name: 'File Excel mẫu',
                    active: true,
                    icon_class: 'one-excel',
                    tooltip: ''
                },
                {
                    id: 'xuat_excel',
                    name: 'Xuất Excel',
                    active: true,
                    icon_class: 'one-excel',
                    tooltip: ''
                },

            ],
            file:null,
            file_full_path:'',
            columns:[
                {
                    fieldName: "trangthai",
                    headerText: "Trạng thái",
                    allowFiltering: true,
                    width:"100",
                    template: this.columnTemplate(this)
                },
                {
                    fieldName: "ma_toanha",
                    headerText: "Mã tòa nhà",
                    allowFiltering: true
                },
                {
                    fieldName: "loaihinhkd_id",
                    headerText: "LOAIHINHKD_ID",
                    allowFiltering: true
                },
                {
                    fieldName: "quymo_lapday",
                    headerText: "QUYMO_LAPDAY",
                    allowFiltering: true
                },
                {
                    fieldName: "ghichu",
                    headerText: "Ghi chú",
                    allowFiltering: true
                },
            ],
            dataSources:[],
            danhsach_excel:[]
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='capnhat'){
                this.CapNhat()
            }else if(action.id=='huy'){
                this.actions[this.actions.findIndex(x=>x.id=='capnhat')].active=false
                this.dataSources=[]
                this.file_full_path=''
            }else if(action.id=='file_mau'){
                window.open("/static/files/CAPNHAT_TT_TOANHA_TEMP.xlsx")
            }else if(action.id=='xuat_excel'){
                //MA_TOANHA, LOAIHINHKD_ID, QUYMO_LAPDAY
                let danhsach=this.dataSources.map(x=>{
                    return {
                        MA_TOANHA:x.ma_toanha,
                        LOAIHINHKD_ID:x.loaihinhkd_id,
                        QUYMO_LAPDAY:x.quymo_lapday
                    }
                })
                let data = XLSX.utils.json_to_sheet(danhsach);
                let wb = XLSX.utils.book_new(); // make Workbook of Excel
                XLSX.utils.book_append_sheet(wb, data, this.sheetAName); // sheetAName is name of Worksheet
                // export Excel file
                let fileFullName='CAPNHAT_TT_TOANHA.xlsx'
                XLSX.writeFile(wb, fileFullName);    
            }
        },
        chooseFile(){
            this.$refs.fileInput.value=null
            document.getElementById("file").click()
        },
        onChangeFile(event){
            this.file_full_path = event.target.value.split('\\').pop()
            this.file=event.target.files[0]
            console.log('file', this.file)
            //this.file_full_path=(window.URL || window.webkitURL).createObjectURL(this.file)
           this.readFileExcel()
        },
        async CapNhat(){
            //fn_capnhat_tt_duan_toanha
            let rs=await this.fn_capnhat_tt_duan_toanha(JSON.stringify(this.dataSources.map(x=>{
                return {
                    DUAN_ID:x.duan_id,
                    TOANHA_ID:x.toanha_id,
                    LOAIHINHKD_ID:x.loaihinhkd_id,
                    QUYMO_LAPDAY:x.quymo_lapday
                }
            })))
            if(rs=='OK'){
                this.$toast.success('Cập nhật dữ liệu thành công!')
                this.dataSources=[].concat(this.dataSources.map(x=>{
                    x.trangthai='1'
                    x.ghichu='OK'
                    return x
                }))

                this.actions[this.actions.findIndex(x=>x.id=='capnhat')].active=false

            }else{
                this.$toast.error(rs)
                this.dataSources=[].concat(this.dataSources.map(x=>{
                    x.trangthai='0'
                    x.ghichu='ERROR'
                    return x
                }))
            }
        },
        readFileExcel(){
            this.danhsach_excel=[]
            this.actions[this.actions.findIndex(x=>x.id=='capnhat')].active=false
            let columns
            try {
                this.loading(true)
                if (this.checkfile()) {
                    const reader = new FileReader()
                    reader.onload = async (e)  => {
                        /* Parse data */
                        const bstr = e.target.result
                        const wb = XLSX.read(bstr, { type: "binary" })
                        /* Get first worksheet */
                        const wsname = wb.SheetNames[0];
                        const ws = wb.Sheets[wsname];
                        /* Convert array of arrays */
                        const data = XLSX.utils.sheet_to_json(ws, { header: 1 });
                        columns=data[0]
                        //MA_TOANHA, LOAIHINHKD_ID, QUYMO_LAPDAY
                        if(this.kiemTraDuLieu(columns)){
                            for(var i=1;i<data.length;i++){
                                this.danhsach_excel.push({
                                    MA_TOANHA:data[i][0]?data[i][0]:null,
                                    LOAIHINHKD_ID:data[i][1]?data[i][1]:null,
                                    QUYMO_LAPDAY:data[i][2]?data[i][2]:null
                                })
                            }
                            if(this.danhsach_excel.length==0){
                                this.$toast.error('Danh sách trống!')
                                this.actions[this.actions.findIndex(x=>x.id=='capnhat')].active=false
                                return
                            }

                            console.log('danhsach_excel', this.danhsach_excel)
                            let dt=await this.kt_capnhat_tt_duan_toanha(JSON.stringify(this.danhsach_excel))
                            console.log('dt', dt)
                            this.dataSources=dt
                            var dtChuanHoa = dt.filter(x=>x.trangthai=='0')
                            if (dtChuanHoa.length > 0){
                                this.$toast.error('Vui lòng kiểm tra lại dữ liệu')
                                return
                            }
                            this.actions[this.actions.findIndex(x=>x.id=='capnhat')].active=true
                        }
                        
                    }
                    reader.readAsBinaryString(this.file)
                }
            } catch (error) {
                this.$toast.error('Có lỗi xảy ra khi đọc file')
                this.actions[this.actions.findIndex(x=>x.id=='capnhat')].active=false
            } finally {
                this.loading(false)
            }
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
            if(columns.length<=1){
                this.$toast.error('File excel gồm 3 trường: MA_TOANHA, LOAIHINHKD_ID, QUYMO_LAPDAY!')
                return false
            }
            if(!columns[0]||columns[0].toString().toUpperCase() != "MA_TOANHA"){
                this.$toast.error('Tên cột đầu tiên trong file không đúng định dạng : MA_TOANHA')
                return false
            }
            if(!columns[1]||columns[1].toString().toUpperCase() != "LOAIHINHKD_ID"){
                this.$toast.error('Tên cột thứ 2 trong file không đúng định dạng : LOAIHINHKD_ID')
                return false
            }
            if(!columns[2]||columns[2].toString().toUpperCase() != "QUYMO_LAPDAY"){
                this.$toast.error('Tên cột thứ 3 trong file không đúng định dạng : QUYMO_LAPDAY')
                return false
            }
            return true
        },
        columnTemplate(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                          <div class="center">
                            <span v-if="data.trangthai=='0'" class="one-alert text-warning f20 inline vcenter"></span>
                            <span v-if="data.trangthai=='-1'" class="one-check text-success f20 inline vcenter" style="color: gray!important;" ></span>
                            <span v-if="data.trangthai=='1'" class="one-check text-success f20 inline vcenter"></span>
                          </div>
                        `,
                        data() {
                            return {
                              data: {},
                              parent:parent
                            }
                        },
                    }
                }
            }
        },
        async kt_capnhat_tt_duan_toanha(vds){
            try{
                this.loading(true)
                let response = await API.kt_capnhat_tt_duan_toanha(this.axios, vds)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async fn_capnhat_tt_duan_toanha(vds){
            try{
                this.loading(true)
                let response = await API.fn_capnhat_tt_duan_toanha(this.axios, vds)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    if(response&&response.data&&response.data.message){
                        return response.data.message
                    }else{
                        return 'Đã xảy ra lỗi'
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    return e.data.message
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return e.response.data.message
                }else{
                    return 'Đã xảy ra lỗi'
                }
            }
        },
        async initDuLieu(){
            this.actions[this.actions.findIndex(x=>x.id=='capnhat')].active=false
        }
    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        }, 1000)
    }
}
</script>