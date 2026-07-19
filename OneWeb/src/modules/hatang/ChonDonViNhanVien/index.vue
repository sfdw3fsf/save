<template src="./index.html">
</template>

<script>
export default {
    props: {
       p_phanVungSDID: Number, 
       p_tenDonVi: String,
       p_chonNhanVien: Boolean
    },
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            formTitle: "CHỌN ĐƠN VỊ",
            isChonNhanVien: false,
            donViId: null,           
            tenDonVi: "",
            tenNhanVien: "",
            nhanVienId: null,
            DSDonVi: [],
            DSNhanVien: [],
            DSTinhThanh: {
                dataSource: [],
                ID: null,
            },
        }
    },
    methods: {
        clearAll(){
            this.donViId = null          
            this.tenDonVi = ""
            this.tenNhanVien = ""
            this.nhanVienId = null
            this.DSDonVi = []
            this.DSNhanVien = []
            this.DSTinhThanh = {
                dataSource: [],
                ID: null
            }
        },
        async dialogOpen() {
            try{
                this.loading(true)
                this.clearAll()
                await this.InitForm()
                this.$refs.dialogChonDVNV.show();   
            }catch(e){
                this.$toast.error(e.data.message)
            }finally{
                this.loading(false)
            }                    
        },
        setDataProps(){           
            this.isChonNhanVien = this.p_chonNhanVien
            this.DSTinhThanh.ID = this.p_phanVungSDID ?  this.p_phanVungSDID : null           
            this.tenDonVi = this.p_tenDonVi ? this.p_tenDonVi : ""              
        },
        closeDialog(){
            this.clearAll()
            this.$refs.dialogChonDVNV.hide();
        },
        async InitForm(){                    
            this.DSTinhThanh.dataSource = await this.getDSTinhThanh();  
            this.setDataProps()   
            this.formTitle = this.isChonNhanVien ? "CHỌN NHÂN VIÊN VÀ ĐƠN VỊ" :  "CHỌN ĐƠN VỊ"       
            this.DSDonVi = await this.getDSDonVi({
                thamSo1: this.DSTinhThanh.ID,
                thamSo2: this.tenDonVi
            });            
        },
        async TimKiemDonVi(){
            this.DSDonVi = await this.getDSDonVi({
                thamSo1: this.DSTinhThanh.ID,
                thamSo2: this.tenDonVi
            });
        },
        rowSelected_DonVi: async function(args) {
            try{
                this.donViId = args.data.DONVI_ID;
                this.tenDonVi = args.data.TEN_DV;
                if(this.isChonNhanVien){
                    this.tenNhanVien = ""
                    this.nhanVienId = null
                    this.DSNhanVien = await this.getDSNhanVien(this.donViId);  
                }                
            }catch(e){
                this.$toast.error(e.data.message)
            }finally{
                this.loading(false)
            }               
        },
        rowSelected_NhanVien: async function(args) {
            this.nhanVienId = args.data.NHANVIEN_ID;
            this.tenNhanVien = args.data.TEN_NV;
        },
        getDSDonVi: async function(param) {
            let result = [];
            try {
                let rs = await this.$root.context.post("/web-ecms/danhmuc/find-don-vi", param);
                result = rs.data;
            } catch(e) {
                this.$toast.error(e.response.data.message_detail)
            }
            return result;
        },
        getDSNhanVien: async function(id) {
            let result = [];
            try {
                let rs = await this.$root.context.get(`/web-ecms/danhmuc/nhan-vien-don-vi?donViId=${id}`)
                result = rs.data;
            } catch(e) {
                this.$toast.error(e.response.data.message_detail)
            }
            return result;
        },
        XacNhan(){
            if(this.donViId == null){
                this.$toast.error("Chưa chọn đơn vị")
                return
            }            
            let data = {
                DONVI_ID : this.donViId,
                TEN_DV: this.tenDonVi,                
            }
            if(this.isChonNhanVien){
                if(this.nhanVienId == null){
                    this.$toast.error("Chưa chọn nhân viên")
                    return
                }
                data.TEN_NV = this.tenNhanVien,
                data.NHANVIEN_ID = this.nhanVienId
            }
            this.$emit('xacNhanChon', data);
            this.$refs.dialogChonDVNV.hide();
        },
        getDSTinhThanh: async function () {
            let result = [];
            try {
                let rs = await this.$root.context.get("/web-ecms/danhmuc/tinh-thanh");
                result = rs.data;
            } catch(e) {
                this.$toast.error(e.response.data.message_detail)
            }
            return result;
            },
    },
    created: async function () {
        // this.$refs.dialogChonDVNV.show();
    },
}
</script>
