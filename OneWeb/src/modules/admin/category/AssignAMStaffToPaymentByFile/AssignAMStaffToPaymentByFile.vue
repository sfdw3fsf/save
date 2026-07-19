<template>
    <div class="main-wrapper">
        <KBreadCrumb :title="title" />
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>
        <div class="page-content">
            <div class="box-form">
                <div class="note text-main marb10 fw6">
                    <span class="one-alert text-warning f20 inline vcenter"></span> File
                    excel gồm 2 trường: MA_TT, MA_AM. Viết hoa, không dấu, bỏ dòng tiêu
                    đề
                </div>
                <div class="info-row">
                    <div class="key w90">Đường dẫn</div>
                    <div class="value">
                        <div class="input-more-button">
                            <button class="btn" @click="chooseFile">
                                <span class="-ap icon-more_horiz"></span>
                            </button>
                            <input id="file" ref="fileInput" type="file" @change="onChangeFile($event)" accept=".xls, .xlsx" style="display:none" class="form-control ">
                            <input type="text" v-model="file_full_path" class="form-control" readonly>
                        </div>
                    </div>
                </div>
                <div class="legend-title mart20">
                    <div class="pull-left">Danh sách thuê bao khu vực</div>
                    <div class="clearfix"></div>
                </div>
                 <KDataGrid
                    :columns="columns"
                    :dataSource="dataSources"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="false"/>
                
            </div>
        </div>
        <Footer/>
        <!-- Modal -->
        <DanhSachLoiModal :columns="columnsError" :dataSources="dataSourcesError" ref="danhsachloi"/>
        <ExportDataModal ref="exportDataModal" :data="dataSources"  file_name="danh-sach"/>
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import KBreadCrumb from '@/components/kylq_components/mirana/KBreadCrumb'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import DanhSachLoiModal from './DanhSachLoiModal.vue'
import XLSX from 'xlsx'
import AssignAMStaffToPaymentByFileAPI from './AssignAMStaffToPaymentByFileAPI'
import moment from 'moment'
import ExportDataModal from '../../../contract/profile/ExportData/ExportDataModal.vue'
export default {
    name:'AssignAMStaffToPaymentByFile',
    components:{
        KBreadCrumb,
        Footer,
        ActionTop,
        KDataGrid,
        DanhSachLoiModal,
        ExportDataModal
    },
    data(){
        return{
            title: "Gán nhân viên AM quản lý thanh toán theo file",
            actions:[
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'xuatfile',
                    name:'Xuất file',
                    active: true,
                    icon_class:'one-download'
                }
            ],
            columns:[
                {
                    fieldName: "MA_TT",
                    headerText: "Mã TT",
                    allowFiltering: true
                },
                {
                    fieldName: "MA_AM",
                    headerText: "Mã AM",
                    allowFiltering: true
                },
            ],
            dataSources:[],
            columnsError:[],
            columnsError1:[
                {
                    fieldName: "STT",
                    headerText: "STT",
                    allowFiltering: true,
                    width:100
                },
                {
                    fieldName: "MA_TT",
                    headerText: "Mã TT",
                    allowFiltering: true
                },
                {
                    fieldName: "MA_AM",
                    headerText: "Mã AM",
                    allowFiltering: true
                },
                {
                    fieldName: "LOI",
                    headerText: "Lỗi",
                    allowFiltering: true
                }
            ],
            columnsError2:[
                {
                    fieldName: "STT",
                    headerText: "STT",
                    allowFiltering: true,
                    width:100
                },
                {
                    fieldName: "MA_TB",
                    headerText: "Mã TT",
                    allowFiltering: true
                },
                {
                    fieldName: "MA_KV",
                    headerText: "Mã AM",
                    allowFiltering: true
                },
                {
                    fieldName: "LYDO_LOI",
                    headerText: "Lỗi",
                    allowFiltering: true
                }
            ],
            dataSourcesError:[],
            file:null,
            file_full_path:'',
            danhsach_excel:[]
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='ghilai'){
                if(this.dataSources.length==0){
                    this.$toast.error('Chưa có thông tin dữ liệu từ file để ghi')
                    return
                }
                let ngay_cn=moment(new Date()).format('DD/MM/YYYY')
                this.sp_capnhat_dbtb_kv_file_v3(ngay_cn)

            }else if(action.id=='xuatfile'){
                if(this.dataSources.length==0){
                    this.$toast.error('Chưa có thông tin dữ liệu để xuất file')
                    return
                }
                this.$refs.exportDataModal.showModal()
            }
        },
        chooseFile(){
            this.$refs.fileInput.value=null
            document.getElementById("file").click()
        },
        onChangeFile(event){
            this.file_full_path = event.target.value.split('\\').pop()
            this.file=event.target.files[0]
            this.readFileExcel()
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
                                    MA_AM:data[i][1]
                                })  
                            }
                            //check lỗi null
                            this.danhsach_excel.forEach((item)=>{
                                if(item.MA_TT==undefined||item.MA_TT==null||item.MA_TT.toString().trim()==''){
                                    this.dataSourcesError.push({
                                        MA_TT:'',
                                        MA_AM:item.MA_AM==undefined?'':item.MA_AM,
                                        LOI:'Mã TT null'
                                    })
                                }else if(item.MA_AM==undefined||item.MA_AM==null||item.MA_AM.toString().trim()==''){
                                    this.dataSourcesError.push({
                                        MA_TT:item.MA_TT==undefined?'':item.MA_TT,
                                        MA_AM:'',
                                        LOI:'Mã AM null'
                                    })
                                }
                            })

                            //showModal lỗi null
                            if(this.dataSourcesError.length>0){
                                this.columnsError=this.columnsError1
                                this.dataSourcesError.forEach((item, index)=>{
                                    item.STT=index+1
                                })
                                this.$refs.danhsachloi.showModal()
                                return
                            }

                            //check lỗi trùng
                            //let ds_trung=this.findDuplicates(this.danhsach_excel)
                            let ds_trung=this.findDuplicates(this.danhsach_excel)
                            console.log('ds_trung',ds_trung)
                            if(ds_trung.length>0){
                                this.columnsError=this.columnsError1
                                ds_trung.forEach((item, index)=>{
                                    this.dataSourcesError.push({
                                        STT:index+1,
                                        MA_TT:item.MA_TT,
                                        MA_AM:item.MA_AM,
                                        LOI:'Cặp MA_TT và MA_AM bị lặp lại'
                                    })
                                })
                                this.$refs.danhsachloi.showModal()
                                return
                            }

                            //FN_INS_KIEMTRA_MA_TT_TMP
                            let nguoidung_id=this.$root.token.getNguoiDungID()
                            let ngay_cn=moment(new Date()).format('DD/MM/YYYY')
                            var danhsach=this.danhsach_excel.map(item=>{
                                return {
                                    MA_TT:item.MA_TT,
                                    GIATRI:item.MA_AM,
                                    KIEU_ID:16,
                                    NGUOIDUNG_ID:nguoidung_id,
                                    GHICHU:''
                                }
                            })
                            this.sp_upload_file_gan_tt_am(danhsach, ngay_cn)
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
            if (!(columns[0]!=undefined&&columns[0].toString().trim().toUpperCase() == "MA_TT" && columns[1]!=undefined&&columns[1].toString().trim().toUpperCase() == "MA_AM")) {
                this.$toast.error('File có cấu trúc không hợp lệ!')
                return false
            }
            return true
        },
        findDuplicates(array){
            var result=[]
            array.forEach((item, index)=>{
                if(array.findIndex(x=>x.MA_TT.toString().trim()==item.MA_TT.toString().trim()&&x.MA_AM.toString().trim()==item.MA_AM.toString().trim()) != index){
                    const index = result.findIndex(r=>r.MA_TT.toString().trim()==item.MA_TT.toString().trim()&&r.MA_AM.toString().trim()==item.MA_AM.toString().trim())
                    if(index==-1){
                        result.push(item)
                    }
                }
            })
            return result
        },
        async sp_upload_file_gan_tt_am(data, ngay_cn){
            
            try{
                this.loading(true)
                let response=await AssignAMStaffToPaymentByFileAPI.sp_upload_file_gan_tt_am(this.axios, data, ngay_cn)
                this.loading(false)
                console.log('sp_upload_file_gan_tt_am', response)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data.out=="0"){
                        if(response.data.data.out_err&&response.data.data.out_err.length>0){
                            this.columnsError=this.columnsError2
                            this.dataSourcesError=response.data.data.out_err.map((x, index)=>Object.assign(x,{STT:index+1}))
                            setTimeout(()=>{
                                this.$refs.danhsachloi.showModal()
                            },300)
                        }else{
                            if(response.data.data.out_ds){
                                this.dataSources=response.data.data.out_ds
                            }
                        }
                    }else{
                        this.$toast.error(response.data.data.out)
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, upload kiểm tra file không thành công!')
                    }
                }
            }catch(e){
                console.log(e)
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, upload kiểm tra file không thành công!')
                }
            }
        },
        async sp_capnhat_dbtb_kv_file_v3(ngay_cn){
            try{
                this.loading(true)  
                let response=await AssignAMStaffToPaymentByFileAPI.sp_capnhat_dbtb_kv_file_v3(this.axios, ngay_cn)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data=='0'){
                        this.$toast.success('Gán thanh toán – AM thành công !')
                    }else{
                        this.$toast.error(response.data.data)
                        
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã có lỗi xảy ra không thể ghi dữ liệu')
                    } 
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã có lỗi xảy ra không thể ghi dữ liệu')
                }
            }
        }

    }
}
</script>
