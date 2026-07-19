
<template src='./WorkAssignment.html'></template>

<style scoped src='./WorkAssignment.scss'></style>

<script>
import api from "./api/index.js";
import state from "./define.js";
import { currency } from "@/filters/currency";
import { TRANGTHAI_DONGBO } from "@/constants.js"

import DatePicker from 'vue2-datepicker' 
import 'vue2-datepicker/locale/vi.js' 
import 'vue2-datepicker/index.css'
import moment from 'moment'
import {
    required,
    minLength
} from 'vuelidate/lib/validators'
export default {
    components: {        
    },
    data() {
        return {
            dateconfig: {
                altFormat: 'd/m/Y H:i:S',
                altInput: true,
                dateFormat: 'd/m/Y H:i:S',
                allowInput: true,
            },
            dateFormat: "DD/MM/YYYY",
            ...state,
            header: {
                title: "Quản lý phân công công việc",
                list: [{
                        name: "Lập hợp đồng",
                        link: {
                            name: "Ui.cards"
                        }
                    },
                    {
                        name: "Lắp đặt mới",
                        link: {
                            name: "Ui.buttons"
                        },
                    },
                ],
            },
            Fn: {
                NhapMoi: false,
                GhiLai: false,
                HuyBo: false,
                Xoa: false,
            },
            nhanvienId: this.$root.token.getNhanVienID(),
            donviId: this.$root.token.getDonViID(),
            dataSelected: {
              
            },
            options: {
             
            },
        };
    },
    mounted() {
        this.init();
    },
    computed: {

    },
    validations: {

    },
    methods: {
        async init() {
            try {
              await this.NAP_DS_COMBO()
              await this.TIMKIEM()
              this.may_cn = await this.$root.token.getMachine()
              this.ip_cn = await this.$root.token.getIP()
              
            } catch (e) {
                console.log(e);
            }
        },
        async NAP_DS_COMBO(){
            try
            {                
                let data = {
                    "p_donvi_id": this.$root.token.getDonViID(),
                    "p_phanvung_id": this.$root.token.getPhanVungID(), 
                }
                await api.sp_lay_ds_nhanvien_theo_donvi_id(this.axios, data).then((response) => {                    
                    if (response &&response.data &&response.data.data &&response.data.data.rs1 &&response.data.error_code &&response.data.error_code === "BSS-00000000"&& response.data.data.rs1.length>0) {            
                        console.log(response.data.data.rs1)
                        this.options.cbo_nhanviennhan = response.data.data.rs1                                                 
                    }
                });
            }
            catch (ex)
            {                
                this.$toast.error("Có lỗi khi nạp danh sách combo "+ex)
            }
        },
        async NAP_DS_NHANVIEN(kieu){
            try
            {
                let data = {
                    "p_donvi_id": parseInt(this.$root.token.getDonViID()), 
                    "p_kieu": kieu, 
                    "p_nhanvien_id": parseInt(this.$root.token.getNhanVienID()), 
                    "p_phanvung_id": parseInt(this.$root.token.getPhanVungID()), 
                    "p_qlcv_id": this.formTT.qlcv_id
                }
                
                await api.sp_nap_ds_nhanvien_ql_congviec(this.axios, data).then((response) => {
                    console.log(response)
                    if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
                        if (kieu==0) {
                            this.gvDSNVNghi.tableData=[]
                            this.gvDSNVChuaGan.tableData=[]
                            this.gvDSNVNghi.tableData = response.data.data.rs1
                            this.gvDSNVChuaGan.tableData = response.data.data.rs2
                        }else if (kieu==1) {
                            this.gvDSNVChuaGan.tableData=[]
                            this.gvDSNVChuaGan.tableData = response.data.data.rs2

                        } else if (kieu==2){
                            this.gvDSNVNghi.tableData=[]
                            this.gvDSNVNghi.tableData = response.data.data.rs1
                        }
                            
                    }
                });
                
            }
            catch (ex)
            {
                this.$toast.error("Có lỗi khi nạp danh sách chưa gán " + ex)
            }

        },
        async NAP_DS_CHITIET(item)
        {
            try
            {
                console.log('NAP_DS_CHITIET')
                console.log(item)
                this.formTT.qlcv_id = item.qlcv_id;

                await this.NAP_DS_NHANVIEN(0)

                this.formTT.ghichu = item.ghichu;
                this.formTT.nhanviennhan_id = item.nhanvien_id
                this.formTT.ngaytao = moment(item.ngaytao,'yyyy/MM/DD HH:mm:ss').format('DD/MM/yyyy')
                this.formTT.tungay = moment(item.tu_ngay,'yyyy/MM/DD HH:mm:ss').format('DD/MM/yyyy HH:mm')
                this.formTT.denngay = moment(item.den_ngay,'yyyy/MM/DD HH:mm:ss').format('DD/MM/yyyy HH:mm')
               
                this.KHOAMO_FORM(true)
                
                this.setVisibleButton('huybo',true)
                this.setVisibleButton('capnhat',true)
                this.setVisibleButton('xoa',true)    
                this.setActiveButton('xoa',true)    
                this.setActiveButton('nhapmoi',true) 

            }
            catch (ex)
            {
               this.$toast.error("Có lỗi khi lấy danh sách thông tin " + ex)               
            }
        },
        async NAP_DS_LICHSU(nhanvien_id){
            try
            {
                let data = { "p_nhanvien_id": nhanvien_id}
                this.gvLichSuNVNghi.tableData = []
                await api.sp_lichsu_nhanvien_nghi(this.axios, data).then((response) => {                    
                    if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
                        console.log(response.data.data)
                        this.gvLichSuNVNghi.tableData = response.data.data.rs1                                                 
                    }
                });
            }
            catch (ex)
            {                
                this.$toast.error("Có lỗi khi nạp danh sách nghỉ "+ex)
            }
        },
        async TIMKIEM(){
            try
            {
                let data ={
                    "p_ckbngay": (this.searchDate.ckb_ngay_tn?1:0), 
                    "p_dl_denngay": moment(this.searchDate.den_ngay,'DD/MM/yyyy').format('DD/MM/yyyy'), 
                    "p_dl_tungay": moment(this.searchDate.ngay_tn,'DD/MM/yyyy').format('DD/MM/yyyy'), 
                    "p_ma_nd": this.$root.token.getUserName()
                }
                this.gvDSCV.tableData = []
                await api.lay_danhsach_cv_phancong(this.axios, data).then((response) => {                    
                    if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&& response.data.data.rs1&& response.data.data.rs1.length>0) {            
                        console.log(response.data.data)
                        this.gvDSCV.tableData = response.data.data.rs1                                                 
                    }
                    else {
                        this.$toast.error("Không tìm thấy thông tin!")
                    }
                });
               
            }
            catch (ex)
            {                
                this.$toast.error("Có lỗi tìm thông tin " + ex)                
            }


        },
        async selectItem(row){
            if (!row || !row.data) return
            this.NAP_DS_CHITIET(row.data)
        },
        async selectItem_gvDSNVChuaGan(row){
            console.log('selectItem_gvDSNVChuaGan')
            console.log(row)
            if (!row || !row.data) return
            this.NAP_DS_LICHSU(row.data.nhanvien_id)
        },
        selectItemsChanged_gvDSNVChuaGan(records) {      
            this.gvDSNVChuaGan_selectItems=records //todo
            console.log(records)
        },
        selectItemsChanged_gvDSNVNghi(records) {      
            this.gvDSNVNghi_selectItems=records //todo
            console.log(records)
        },
        async TraCuu(){
            console.log('Click Tra cứu')
            this.TIMKIEM()
        },
        async KIEMTRA_DULIEU(){            
            if (this.formTT.nhanviennhan_id==0)
            {                
                this.$toast.error("Bạn chưa chọn nhân viên nhận việc")
                return false
            }
            if (this.formTT.tungay == null || this.formTT.tungay == '')
            {                
                this.$toast.error("Bạn chưa nhập từ ngày")
                return false
            }            
            if (this.formTT.denngay == null || this.formTT.denngay == '')
            {                
                this.$toast.error("Bạn chưa nhập đến ngày")
                return false
            }

            let index = this.actions.findIndex(item => item.id === 'nhapmoi')
            let status_nhapmoi=true
            if (index >= 0) {
                status_nhapmoi = this.actions[index].active
            }
            if (status_nhapmoi && this.formTT.qlcv_id == 0)
            {                
                this.$toast.error("Bạn chưa chọn nhóm để cập nhật")
                return false
            }
            //todo kiểm tra trên DB
            // var kiemtra = tdan.GET_VALUE_FUNC("{?DB7}.KIEMTRA_PC_CONGVIEC", "vqlcv_id", vqlcv_id, "vtu_ngay", vtungay,
            //     "vden_ngay", vdenngay, "vnhanvien_nhan_id",vnhanvien_nhan_id);
            // if (kiemtra.ToString() != "1")
            // {
            //     Message_Box.ShowWarning(kiemtra.ToString());
            //     return false;
            // }
            let data = {
                "vden_ngay": moment(this.formTT.denngay,'DD/MM/yyyy').format('DD/MM/yyyy'), 
                "vnhanvien_nhan_id": this.formTT.nhanviennhan_id,
                "vqlcv_id": this.formTT.qlcv_id,
                "vtu_ngay": moment(this.formTT.tungay,'DD/MM/yyyy').format('DD/MM/yyyy')
            }
            let kt= true
            await api.kiemtra_pc_congviec(this.axios, data).then((response) => {                    
                console.log(response)
                if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
                    if(response.data.data.results!="1") {
                        this.$toast.error(response.data.data.results)
                        kt = false
                    }
                    //this.gvLichSuNVNghi.tableData = response.data.data.rs1                                                 
                }
            });
            if (kt==true) return true
            else return false
        },
        async CAPNHAT(){
            try
            {
                let kt = await this.KIEMTRA_DULIEU()
                if (!kt) return;

                let isnew = (this.formTT.qlcv_id==0?1:2)
                if (this.formTT.qlcv_id!=0){
                    const cf = await this.$bvModal.msgBoxConfirm("Bạn muốn thay đổi thông tin công việc không?",
                    {
                      okTitle: "Có",
                      cancelTitle: "Không"
                    }
                    );
                    if (!cf) return;
                    // if (!window.confirm("Bạn muốn thay đổi thông tin công việc không?")) {
                    //     return
                    // }
                }
                let data = {                    
                    "vghichu": this.formTT.ghichu, 
                    "vip_cn": this.ip_cn, 
                    "vmay_cn": this.may_cn, 
                    "vnguoi_cn": this.$root.token.getUserName(), 
                    "vnhanvien_giao_id": this.$root.token.getNhanVienID(), 
                    "vnhanvien_id": this.formTT.nhanviennhan_id, 
                    "vnhapmoi": isnew, 
                    "vphanvung_id": this.$root.token.getPhanVungID(), 
                    "vqlcv_id": this.formTT.qlcv_id, 
                    "vtu_ngay": moment(this.formTT.tungay,'DD/MM/yyyy HH:mm').format('DD/MM/yyyy HH:mm:ss'),
                    "vden_ngay": moment(this.formTT.denngay,'DD/MM/yyyy HH:mm').format('DD/MM/yyyy HH:mm:ss')
                }
                await api.sp_capnhat_ql_congviec(this.axios, data).then((response) => {     
                    console.log(response )              
                    if (response &&response.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
                        console.log(response.data)
                        this.$toast.success("Cập nhật dữ liệu thành công.")       
                        this.TIMKIEM()                                                            
                    } else {
                        this.$toast.error("Cập nhật dữ liệu thất bại.")
                    }
                });                
            }
            catch (ex)
            {                
                this.$toast.error("Có lỗi khi cập nhật thông tin " + ex)
            }
        },
        async GAN_GO_NHANVIEN(kieu)
        {
            try
            {
                if (this.formTT.qlcv_id == 0)
                {
                    this.$toast.error("Chưa có quản lý công việc để gán")                    
                    return
                }
                
                if (kieu == 1) // Gan
                {
                    if (this.gvDSNVChuaGan.tableData.length==0){
                        this.$toast.error("Không có danh sách nhân viên để gán")
                        return
                    }

                    if (this.gvDSNVChuaGan_selectItems.length == 0)
                    {                        
                        this.$toast.error("Bạn chưa chọn nhân viên để thực hiện")
                        return
                    }
                    
                    let docx = '<ds>'    
                    for (let item of this.gvDSNVChuaGan_selectItems) {
                        docx+='<vt nhanvien_id="'+item.nhanvien_id+'"/>'                        
                    }
                    docx+='</ds>'
                    
                    let data = {                        
                        "p_danhsach": docx,//p_danhsach.join(','),
                        "p_kieu": 1,
                        "p_phanvung_id": this.$root.token.getPhanVungID(),
                        "p_qlcv_id": this.formTT.qlcv_id
                    }
                    //todo
                    //gọi hàm gán
                    await api.sp_gan_go_nhanvien_ql_congviec(this.axios, data).then((response) => {     
                        console.log(response )              
                        if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
                            console.log(response.data)
                            this.$toast.success("Gán nhân viên thành công.")  
                            this.NAP_DS_NHANVIEN(0)                                                                 
                        } else {
                            this.$toast.error("Gán dữ liệu thất bại:" + response.data.message)
                        }
                    });  
                    //nạp lại danh sách
                }
                else if (kieu == 2) //Go
                {
                    if (this.gvDSNVNghi.tableData.length==0){
                        this.$toast.error("Không có danh sách nhân viên để gỡ")
                        return
                    }
                    console.log(this.gvDSNVNghi_selectItems)

                    if (this.gvDSNVNghi_selectItems.length == 0)
                    {                        
                        this.$toast.error("Bạn chưa chọn nhân viên để thực hiện")
                        return
                    }
                    
                    let docx = '<ds>'
                    for (let item of this.gvDSNVNghi_selectItems) {
                        docx+='<vt nhanvien_id="'+item.nhanvien_id+'"/>'                        
                    }
                    docx+='</ds>'

                    let data = {                        
                        "p_danhsach": docx,
                        "p_kieu": 2,
                        "p_phanvung_id": this.$root.token.getPhanVungID(),
                        "p_qlcv_id": this.formTT.qlcv_id
                    }

                    
                    //gọi hàm gở
                    //nạp lại danh sách
                    await api.sp_gan_go_nhanvien_ql_congviec(this.axios, data).then((response) => {     
                        console.log(response )              
                        if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
                            console.log(response.data)
                            this.$toast.success("Đã xóa nhân viên thành công.")  
                            this.NAP_DS_NHANVIEN(0)                                                                 
                        } else {
                            this.$toast.error("Gỡ dữ liệu thất bại:" + response.data.message)
                        }
                    });
                }
            }
            catch (ex)
            {
                console.log(ex)
                this.$toast.error("Có lỗi xảy ra trong quá trình thực hiện " + ex)                
            }
        },
        async XOA(){
            if (this.formTT.qlcv_id == 0)
            {
                this.$toast.error("Không lấy được thông tin quản lý công việc")                    
                return
            }

            if (!window.confirm("Bạn có chắn chắn muốn xóa thông tin công việc không?")) {
                return
            }


            try
            {
                let data = { 
                    "p_phanvung_id": this.$root.token.getPhanVungID(), 
                    "p_qlcv_id": this.formTT.qlcv_id
                } 
                await api.sp_xoa_ql_congviec(this.axios, data).then((response) => {     
                        console.log(response )              
                        if (response &&response.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {            
                            console.log(response.data)
                            this.$toast.success("Xóa công việc được giao thành công")  
                            this.TIMKIEM()   
                            this.NAP_DS_NHANVIEN(0)                                                                  
                        } else {
                            this.$toast.error("Có lỗi khi xóa thông tin quản lý công việc: " + response.data.message)
                        }
                });  
                

            }
            catch (ex)
            {                
                this.$toast.error("Có lỗi khi xóa thông tin quản lý công việc: "+ex)   
            }
        },
        async btnCapNhat_Click(){
            console.log('Click btnCapNhat_Click')
            this.CAPNHAT()
        },
        async btnNhapmoi_Click(){
            console.log('Click btnNhapmoi_Click')
            this.THAOTAC_NUT(1)
        },
        async btnHuy_Click(){
            console.log('Click btnHuy_Click')
            this.THAOTAC_NUT(3)
        },
        async btnXoa_Click(){
            console.log('Click btnXoa_Click')
            this.XOA()
        },
        async btnGan_Click(){
            console.log('Click btnGan_Click')
            this.GAN_GO_NHANVIEN(1)
        },
        async btnGo_Click(){
            console.log('Click btnGo_Click')
            this.GAN_GO_NHANVIEN(2)
        },
        async onActionClick(action){
            if (action === 'laytt') {                
                this.TraCuu()
            } else if (action === 'capnhat') {
                this.btnCapNhat_Click()
            } else if (action === 'nhapmoi') {
                this.btnNhapmoi_Click()
            } else if (action === 'huybo') {
                this.btnHuy_Click()
            } else if (action === 'xoa') {
                this.btnXoa_Click()
            }
            
        },
        setActiveButton (id, status) {
            let index = this.actions.findIndex(item => item.id === id)
            if (index >= 0) {
                this.actions[index].active = status
            }            
        },
        setVisibleButton (id, status) {
            let index = this.actions.findIndex(item => item.id === id)
            if (index >= 0) {
                this.actions[index].visible = status
            }            
        },
        KHOAMO_FORM(mo)
        {
            this.isDisableForm = mo
        },
        THAOTAC_NUT(kieu){
            if (kieu == 1)
            {
                
                this.XOA_FORM();
                this.KHOAMO_FORM(true);
                // Cập nhật nút
                this.setVisibleButton('huybo',true)
                this.setVisibleButton('capnhat',true)
                this.setActiveButton('nhapmoi',false)                
                //todo
                // Chọn control nhập
               //cboNhanVienThay.Focus();
               $('#select_nhanviennhan select').focus()
            }
            else if (kieu == 3)
            {
                
                // Xóa form
                this.XOA_FORM();
                // Cập nhật nút
                this.setVisibleButton('huybo',false)
                this.setVisibleButton('capnhat',false)
                this.setVisibleButton('xoa',false)    
                this.setActiveButton('nhapmoi',true)    

                
            }

        },
        XOA_FORM(){            
            // Xóa key
            this.formTT.qlcv_id=0
            // Xóa form
            this.CLEAR();
            this.KHOAMO_FORM(false);

            // Cập nhật nút
            this.setVisibleButton('huybo',false)
            this.setVisibleButton('capnhat',false)
            this.setActiveButton('xoa',false)                
        },
        CLEAR(){
            this.gvDSNVNghi.tableData=[]
            this.gvDSNVChuaGan.tableData=[]
            this.gvDSNVChuaGan_selectItems = []
            this.gvDSNVNghi_selectItems = []
            this.formTT.ghichu=''
            this.formTT.nhanviennhan_id=0
            this.formTT.ngaytao = moment(new Date()).format('DD/MM/yyyy')
            this.formTT.tungay=''
            this.formTT.denngay=''
        },
        customiseCell: function (args) {
            if (args.column.field === 'nvthay') {            
                args.cell.setAttribute('style', 'font-weight: bold;color:blue;')                
            }
            if (args.column.field === 'ten_nv') {            
                args.cell.setAttribute('style', 'font-weight: bold;color:red;')                
            }
            if (args.column.field === 'ma_nv') {            
                args.cell.setAttribute('style', 'font-weight: bold;color:blue;')                
            }
            
            if (args.column.field === 'den_ngay'||args.column.field === 'tu_ngay') {            
                args.cell.setAttribute('style', 'font-weight: bold;text-align:right;color:red;')
                
            }
            if (args.column.field === 'ngaytao') {            
                args.cell.setAttribute('style', 'font-weight: bold;text-align:right;')                
            }
            
        },

    },
    watch: {        
        
    }
};
</script>
<style scoped>
 .btn-cuoc {
     padding:0;
 }
 .table-dv-content {
     max-height: 400px;
     overflow: scroll;
 }
 .btn:not(:disabled):not(.disabled) {
    border: 1px solid gray;
    margin-bottom: 10px;
}
</style>