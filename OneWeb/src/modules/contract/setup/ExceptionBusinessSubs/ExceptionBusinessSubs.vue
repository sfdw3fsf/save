<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>
        <div class="page-content">
            <div class="info-row">
                <div class="key">Chọn file:</div>
                <div class="input-more-button">
                    <button class="btn" @click="chooseFile">
                        <span class="-ap icon-more_horiz"></span>
                    </button>
                    <input ref="fileInput" id="file" type="file" @change="onChangeFile($event)" accept=".xls, .xlsx" style="display:none" class="form-control ">
                    <input type="text" v-model="file_full_path" class="form-control" readonly>
                </div>
            </div>
            <div class="list-checks mart10 marb10">
                <div class="item">
                    <button @click="NapLai" class="btn btn-main marr5 nocorner">
                        <span class="one-file-refresh"></span> Nạp lại
                    </button>
                    <button @click="ThucThi" :disabled="disabledThucThi" class="btn btn-main marr5 nocorner">
                        <span class="nc-icon-outline media-1_button-play"></span> Thực thi
                    </button>
                    <button class="btn btn-main marr5 nocorner" :disabled="dataSourcesDs.length==0">
                        <download-excel name="danh_sach_du_lieu_tb_ngoaile_tinh_luong" :fields="json_fields" :data="dataSourcesDs" >
                            <span class="one-xlsx-import"></span>Xuất excel
                        </download-excel>
                    </button>
                </div>
                <div class="item text-desc center vtop">
                    <!-- *Ghi chú: - Chương trình chỉ đọc dữ liệu từ sheet có tên ‘’dsthuebao’’<br/>
                    - File excel chỉ có 2 cột ‘’ma_tb’’ và ‘’loaitb_id’’ -->
                </div>
            </div>
            <div class="grid-stack-ver" style="height: 600px;">
                <div class="box-form marb0" >
                    <div class="legend-title">Dữ liệu Excel</div>
                    <KTableV3 :columns="columnsExcel"
                        :dataSources="danhsach_excel"
                        :allowFilter="true" />
                </div>
                <div style="height: 10px;"></div>
                <div class="box-form" >
                    <div class="legend-title">Danh sách thuê bao</div>
                    <KTableV3 :columns="columnsDs"
                        :dataSources="dataSourcesDs"
                        :allowFilter="true"
                        id="id"
                        :rowSelectedId="rowSelectedId"
                        @onSelectedRow="onSelectedRow"/>
                </div>
            </div>
        </div>
        <Footer/>
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import XLSX from 'xlsx'
import ExceptionBusinessSubsAPI from './ExceptionBusinessSubsAPI'
import moment from 'moment'
export default {
    name:'ExceptionBusinessSubs',
    components:{
        breadcrumb,
        Footer,
        KTableV3,
        ActionTop
    },
    data(){
        return {
            header: {
                title: "Quản lý thuê bao ngoại lệ kênh tính lương",
                list: [
                    { name: "Lập hợp đồng", 
                    link: { name: "Ui.cards" },
                    active: false
                    },
                    {
                        name: "Quản lý thuê bao ngoại lệ KHDN",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ],
            },
            actions:[
                {
                    id:'xoa_tb',
                    name:'Xoá TB',
                    active: true,
                    icon_class:'one-trash'
                },
                {
                    id:'file_mau',
                    name:'File mẫu',
                    active: true,
                    icon_class:'icon one-excel'
                }
            ],
            columnsExcel:[
                {
                    field: "kq",
                    label: "Kết quả",
                    allowFilter: true
                },
                {
                    field: "MA_TB",
                    label: "Mã TB",
                    allowFilter: true
                },
                {
                    field: "LOAITB_ID",
                    label: "Loại TB ID",
                    allowFilter: true
                },
                {
                    field: "loaihinh_tb",
                    label: "Loại TB",
                    allowFilter: true
                },
                {
                    field: "ten_dvvt",
                    label: "Dịch vụ",
                    allowFilter: true
                }
            ],
            danhsach_excel:[],
            columnsDs:[
                {
                    field: "ma_tb",
                    label: "Mã TB",
                    allowFilter: true
                },
                {
                    field: "loaihinh_tb",
                    label: "Loại TB",
                    allowFilter: true
                },
                {
                    field: "thang",
                    label: "Tháng",
                    allowFilter: true
                },
                {
                    field: "ten_nv",
                    label: "Nhân viên",
                    allowFilter: true
                },
                {
                    field: "nguoi_cn",
                    label: "Người CN",
                    allowFilter: true
                },
                {
                    field: "ngay_cn",
                    label: "Ngày CN",
                    allowFilter: true
                },
            ],
            dataSourcesDs:[],
            disabledThucThi:true,
            file:null,
            file_full_path:'',
            ts_cho_them_ngoai_le_tinh_luong:false,
            danhsach_loaihinh_tb:[],
            danhsach_dichvu_vt:[],
            thuebao_selected:null,
            danhsach_thucthi:[],
            json_fields:{
                'Mã TB': 'ma_tb',
                'Loại TB':'loaihinh_tb',
                'Tháng': 'thang',
                'Nhân viên': 'ten_nv',
                'Người CN':'nguoi_cn',
                'Ngày CN':'ngay_cn'
            },
            rowSelectedId:-1
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='xoa_tb'){
                this.XoaTB()
            }else if(action.id=='file_mau'){
                this.taiFileMau()
            }
        },
        taiFileMau(){
            this.$confirm('Bạn có muốn tải file mẫu về không ?','Thông báo',{
                confirmButtonText: 'Đồng ý',
                cancelButtonText: 'Huỷ',
                type: 'info'
            }).then(() => {
                //dùng tạm
                window.open("/static/files/MauFile_Import_Ngoai_Le_Kenh_Tinh_Luong.xlsx")
            })
        },
        XoaTB(){
            if(this.thuebao_selected==null){
                this.$toast.error('Hãy chọn thuê bao để xoá')
                return
            }
            this.$confirm('Xóa thuê bao khỏi danh sách thuê bao ngoại lệ ?',
                'Thông báo',
                {
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Huỷ'
                }).then(() => {
                    this.xoa_thuebao_ngoaile_thang(this.thuebao_selected.thuebao_id, this.thuebao_selected.thang)
            }).catch((e) => {})
        },
        NapLai(){
            this.readFileExcel()
        },
        ThucThi(){
            if(!this.ts_cho_them_ngoai_le_tinh_luong){
                this.$toast.error('Bạn không có quyền thực hiện chức năng này.')
                return
            }
            if(this.danhsach_excel.length==0){
                return
            }
            this.insert_json_kenh_tinh_luong_ngoai_le(this.danhsach_excel.map(x=>{
                return {
                    LOAITB_ID:x.LOAITB_ID,
                    MA_TB:x.MA_TB
                }
            }))
        },
        async sp_lay_ct_tsmd(){
            try{
                this.loading(true)
                let response=await ExceptionBusinessSubsAPI.sp_lay_ct_tsmd(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    //user test nguoidung_id=999999 không có dữ liệu, test lại sau
                    if(response.data.data.length>0){
                        this.ts_cho_them_ngoai_le_tinh_luong=true
                    }else{
                        this.ts_cho_them_ngoai_le_tinh_luong=false
                    }
                }else{
                    this.ts_cho_them_ngoai_le_tinh_luong=false
                }
            }catch(e){
                this.loading(false)
                this.ts_cho_them_ngoai_le_tinh_luong=false
                this.$toast.error('Không lấy được tham số ngoại lệ tính lương')
            }
        },
        async lay_du_lieu_tb_ngoaile_tinh_luong(){
            try{
                this.loading(true)
                this.dataSourcesDs=[]
                let response=await ExceptionBusinessSubsAPI.lay_du_lieu_tb_ngoaile_tinh_luong(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSourcesDs=response.data.data.map((x, index)=>Object.assign(x,{
                        id:index,
                        ngay_cn:x.ngay_cn?moment(x.ngay_cn, 'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY HH:mm:ss'):''
                    }))
                    if(this.dataSourcesDs.length>0){
                        this.rowSelectedId=this.dataSourcesDs[0].id
                        this.onSelectedRow(this.dataSourcesDs[0])
                    }
                }else{
                    this.dataSourcesDs=[]
                }
            }catch(e){
                this.loading(false)
                this.$toast.error('Không lấy được tham số ngoại lệ tính lương')
            }
        },
        async lay_danhsach_loaihinh_tb(){
            try{
                this.loading(true)
                let response=await ExceptionBusinessSubsAPI.lay_danhsach_loaihinh_tb(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_loaihinh_tb=response.data.data
                }else{
                    this.danhsach_loaihinh_tb=[]
                }
            }catch(e){
                this.loading(false)
                this.$toast.error('Không load được danh sách loại hình thuê bao')
            }
        },
        async lay_danhsach_dich_vu_vt(){
            try{
                this.loading(true)
                let response=await ExceptionBusinessSubsAPI.lay_danhsach_dich_vu_vt(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_dichvu_vt=response.data.data
                }else{
                    this.danhsach_dichvu_vt=[]
                }
            }catch(e){
                this.loading(false)
                this.$toast.error('Không load được danh sách dịch vụ viễn thông')
            }
        },
        async xoa_thuebao_ngoaile_thang(thuebao_id, thang){
            try{
                this.loading(true)
                let response=await ExceptionBusinessSubsAPI.xoa_thuebao_ngoaile_thang(this.axios, thuebao_id, thang)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data.toString()=='OK'){
                        this.$toast.success('Xóa dữ liệu thành công!')
                        this.lay_du_lieu_tb_ngoaile_tinh_luong()
                    }else{
                        this.$toast.error(response.data.data)
                    }
                }else if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00003013' && response.data.data){
                    this.$toast.error(response.data.data)
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Có lỗi khi xóa thuê bao')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Có lỗi khi xóa thuê bao')
                }
            }
        },
        async insert_json_kenh_tinh_luong_ngoai_le(danhsach){
            try{
                this.loading(true)
                let response = await ExceptionBusinessSubsAPI.insert_json_kenh_tinh_luong_ngoai_le(this.axios,danhsach)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    //handle result
                    //                 [{
                    //   "json_k_tontai": "[{\"ma_tb\":\"231dsffew\"},{\"ma_tb\":\"231dsffew\"}]",
                    //   "json_trung": "[{\"ma_tb\":\"859866968\"}]",
                    //   "json_dbtb_nhanvien_cs": null,
                    //   "json_khac": null
                    // }]    
                    let result=response.data.data[0]
                    if(result.json_k_tontai&&result.json_k_tontai!=""){
                        //array
                        var dt_temps=JSON.parse(result.json_k_tontai)
                        dt_temps.forEach((item)=>{
                            this.danhsach_excel.forEach((i)=>{
                                if(item.ma_tb==i.MA_TB){
                                    i.kq='Không tìm thấy thuê bao trong danh bạ ứng với mã và loại hình này.'
                                }
                            })
                        })
                    }
                    if(result.json_trung&&result.json_trung!=""){
                        var dt_temps=JSON.parse(result.json_trung)
                        dt_temps.forEach((item)=>{
                            this.danhsach_excel.forEach((i)=>{
                                if(item.ma_tb==i.MA_TB){
                                    i.kq='Thuê bao đã được gán ngoại lệ với tháng này.'
                                }
                            })
                        })
                    }
                    if(result.json_dbtb_nhanvien_cs&&result.json_dbtb_nhanvien_cs!=""){
                        var dt_temps=JSON.parse(result.json_dbtb_nhanvien_cs)
                        dt_temps.forEach((item)=>{
                            this.danhsach_excel.forEach((i)=>{
                                if(item.ma_tb==i.MA_TB){
                                    i.kq='Thuê bao chưa được gán vào danh sách tính lương.'
                                }
                            })
                        })
                    }

                    if(result.json_khac&&result.json_khac!=""){
                        var dt_temps=JSON.parse(result.json_khac)
                        dt_temps.forEach((item)=>{
                            this.danhsach_excel.forEach((i)=>{
                                if(item.ma_tb==i.MA_TB){
                                    i.kq='Lý do khác.'
                                }
                            })
                        })
                    }

                    //cập nhật các row thành công
                    this.danhsach_excel.forEach((item)=>{
                        if(item.kq=='Sẵn sàng!'){
                            item.kq='Thành công!'
                        }
                    })
                    //refresh table excel
                    var excel_temp=this.danhsach_excel.slice()
                    this.danhsach_excel=[]
                    this.$nextTick(() => {
                        this.danhsach_excel=excel_temp.slice()
                    })
                    
                    //refresh get data thue bao
                    this.lay_du_lieu_tb_ngoaile_tinh_luong()
                }else{
                    this.$toast.error(response.data.message)
                }
            }catch(e){
                this.loading(false)
                this.$toast.error('Có lỗi khi cập nhật dữ liệu')
            }
        },
        
        onSelectedRow(item){
            this.thuebao_selected=item
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
        readFileExcel(){
            this.danhsach_excel=[]
            let columns
            this.disabledThucThi=true
            try {
                this.loading(true)
                if (this.checkfile()) {
                    const reader = new FileReader()
                    reader.onload = (e) => {
                        /* Parse data */
                        const bstr = e.target.result
                        const wb = XLSX.read(bstr, { type: "binary" })
                        /* Get first worksheet */
                        var wsname=''
                        wb.SheetNames.every((name)=>{
                            if(name=='dsthuebao'){
                                wsname=name
                                return false
                            }
                            return true
                        })
                        if(wsname=='dsthuebao'){
                            // check ngoại lệ  comment lại để test do user test nguoidung_id=999999 không có dữ liệu
                            if(!this.ts_cho_them_ngoai_le_tinh_luong){
                                this.$toast.error('Bạn không có quyền thực hiện chức năng này.')
                                return
                            }
                            const ws = wb.Sheets[wsname]
                            /* Convert array of arrays */
                            const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
                            console.log('data: ', data)
                            columns=data[0]
                            if(this.kiemTraDuLieu(columns)){
                                
                                for(var i=1;i<data.length;i++){
                                    this.danhsach_excel.push({
                                        MA_TB:data[i][0],
                                        LOAITB_ID:data[i][1]
                                    })
                                    
                                }
                                var isValid=this.danhsach_excel.length>0
                                this.danhsach_excel.forEach((item)=>{
                                    
                                    item.MA_TB=item.MA_TB.toString()
                                    item.LOAITB_ID=item.LOAITB_ID.toString()
                                    if(item.MA_TB==''||item.LOAITB_ID==''){
                                        item.kq='Thiếu thông tin.'
                                         isValid=false
                                    }else if(item.MA_TB.indexOf(' ')>-1||item.LOAITB_ID.indexOf(' ')>-1){
                                        item.kq='Chứa ký tự cách'
                                        isValid=false
                                    }else{
                                        item.kq='Sẵn sàng!'
                                    }
                                      
                                    if(this.isNumber(item.LOAITB_ID)){
                                        let id=parseInt(item.LOAITB_ID)
                                        const loai_tb=this.danhsach_loaihinh_tb.find(x=>x.LOAITB_ID==id)
                                        if(loai_tb!=undefined){
                                            item.loaihinh_tb=loai_tb.LOAIHINH_TB
                                            const dvvt=this.danhsach_dichvu_vt.find(x=>x.DICHVUVT_ID==loai_tb.DICHVUVT_ID)
                                            if(dvvt!=undefined){
                                                item.ten_dvvt=dvvt.TEN_DVVT
                                            }else{
                                                item.ten_dvvt=''
                                            }
                                        }else{
                                            item.loaihinh_tb='',
                                            item.ten_dvvt=''
                                        }
                                    }else{
                                        item.kq='Loại thuê bao ID phải là số'
                                        isValid=false
                                    }  
                                })

                                if(isValid){
                                    this.disabledThucThi=false
                                }else{
                                    this.disabledThucThi=true
                                }


                            }

                        }else{
                            this.disabledThucThi=true
                            this.$toast.error('Không tìm thấy sheet dữ liệu yêu cầu. Sheet dữ liệu danh sách thuê bao phải đặt tên là dsthuebao')
                            return
                        }
                        
                    }
                    reader.readAsBinaryString(this.file)
                }
            } catch (error) {
                console.log(error)
                this.disabledThucThi=true
                this.$toast.error('Có lỗi xảy ra')
            } finally {
                this.loading(false)
            }
        },
        isNumber(n) {
            return !isNaN(parseInt(n)) && isFinite(n);
        },
        checkfile: function () {
            if (this.file == null) {
                this.$toast.error('Chưa chọn file excel')
                return false
            }
            if (this.file.type != "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"){
                this.$toast.error('File không đúng định dạng cho phép (xls, xlsx) hoặc không đúng biểu mẫu vui lòng thử lại')
                return false
            }
            return true
        },
        kiemTraDuLieu: function (columns) {
            if (columns == null) {
                this.$toast.error('File có cấu trúc không hợp lệ!')
                return false
            }
            if (!(columns[0]!=undefined&&columns[0].toString().trim().toLowerCase() == "ma_tb" && columns[1]!=undefined&&columns[1].toString().trim().toLowerCase() == "loaitb_id")) {
                this.$toast.error('File có cấu trúc không hợp lệ!')
                return false
            }
            return true
        },
        async initDuLieu(){
            await this.sp_lay_ct_tsmd()
            await this.lay_du_lieu_tb_ngoaile_tinh_luong()
            await this.lay_danhsach_loaihinh_tb()
            await this.lay_danhsach_dich_vu_vt()
        }
    },
    mounted(){
        $(".table-content").css('height', 'calc(100% - 80px)')
        this.actions[this.actions.findIndex(x=>x.id=='xoa_tb')].active=false
        setTimeout(()=>{
            this.initDuLieu()
        },500)
    },
    watch:{
        dataSourcesDs(val){
            if(val.length>0){
                this.actions[this.actions.findIndex(x=>x.id=='xoa_tb')].active=true
            }else{
                this.actions[this.actions.findIndex(x=>x.id=='xoa_tb')].active=false
            }
        }
    }

}
</script>
<style scoped>
.btn-main{
    background: #0176FF;
    color: #fff;
}
.btn-main:hover{
    background: #035FCC;
}
.btn {
    padding: 5px 10px;
    border-radius: 4px;
}
</style>