
<template src="./DeliveryHistory.html"></template>


<style scoped src="./DeliveryHistory.scss"></style>

<script>
import ComboboxGrid from "@/components/Controls/ComboboxGrid"
import {
    required,
    maxLength,
    minLength,
    numeric
} from "vuelidate/lib/validators"
import moment from 'moment'
import Giaophieu from "./popup/Giaophieu/Giaophieu.vue";
import HosoFile from "./popup/HosoFile/HosoFile.vue";
import CapnhatFile from "./popup/CapnhatFile/CapnhatFile.vue";
//import ModalChonDiaChi from './popup/ChonDiaChi'
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import api from "./api/index.js";
export default {
    components: {
        appCombobox: ComboboxGrid,
        Giaophieu,
        HosoFile,
        ModalChonDiaChi,
        CapnhatFile
    },
    name: 'DeliveryHistory', 

    async created() {
        this.setButton(-1);
        await this.onLayTT(false);
    },
    data() {
        return {
            cbo_NganHang: {
                items: [], 
                selected: null
            },
            dateFormat: 'dd/MM/yyyy',
            formData: {
                thuebao_id: 0,
                dichvuvt_id: 0,
                ma_tb: ''
            },
            gioitinh: "",
            items: {
                diachi: "",
                ma_nvql:"",
                dt_ban_id: "",
                donvi_id: "",
                ma_kenh: "",
                ten_kenh: "", 
                nhanvien_id: "",
                kenhban_id: "",
                loaidv_id:"",
                fax: "",
                nguoi_dd: "",
                ngaysinh: moment().format('DD/MM/YYYY'),
                ngay_cap: moment().format('DD/MM/YYYY'),
                gioitinh: 0
            },
            header: {
                title: "Tra cứu đề xuất kênh bán",
                list: [{
                        name: "Nghiệm vụ quản lý",
                        link: {
                            name: "Ui.cards"
                        }
                    },
                    {
                        name: "Kênh bán",
                        link: {
                            name: "Ui.buttons"
                        },
                    },
                ],
            },
            btn_Enabled: {
                nhapMoi: false,
                ghiLai: false,
                xoa: false,
                huy: false,
                ganquyen: false,
                ganloaisanpham: false,
                duyet: false
            },
            listGioitinh: [],
            listTrangthai:[],
            listLoaigiayto:[],
            dsTinh: [],
            dsQuan: [],
            dsThanhvienkenh:[],
            dsPhuong: [],
            dsPho: [],
            dsAp: [],
            dsKhu: [],
            dsLoaiHD: [],
            isPho: false,
            isAp: false,
            isKhu: false,
            isDacDiem: false,
            dsTTKenhban: [],
            tentinh: '',
            tenquan: '',
            tenphuong: '',
            anhmattruoc:"",
            tenpho: '',
            tenap: '',
            tenkhu: '',
            ma_kenh_cu:"",
            ten_hang_kenh:"",
            diachi: {},
            dsKenhban:[],
            dsHangkenh:[],
            dsHopdong:[],
            dsNguoidexuat:[],
            dsLoaiGT: [],
            DsLichsu:[],
            dsFileKenhBan:[],
            dsDoituongban:[],
            dsDVDexuat:[],
            dsKenhban:[],
            ma_nv_dexuat:"",
            linkview_file: "",
            txtFile_Anhmattruoc:"",
            txtFile_Anhmatsau:"",
            txtFile_Anhchugiayto:"",
            txtFile_GiayPhepKD:"",
            file_anhmattruoc:"",
            file_anhmatsau:"",
            hangkenhCTV: false,
            file_chandung:"",
            ma_nvql:"",
            xemFile:"",
            showTTHD:"",
            file_GPKD:"",
            anhmatsau_view:"",
            grid_DeXuatKenhban: {
                items: [],
                selected: {}
            },
            grid_File: {
                items: [],
                selected: {}
            },
        }
    },
    validations: {
        ma_nv_dexuat: {
                required,
                maxLength: maxLength(100),
        },
        items: {
            ma_kenh: {
                required,
                maxLength: maxLength(100),
            },
            ten_kenh: {
                required,
                maxLength: maxLength(300),
            },
            so_dt: {
                maxLength: maxLength(20),
            },
            diachi: {
                required,
                maxLength: maxLength(300),
            },
            mst_cccd: {
                required,
                maxLength: maxLength(50),
            },
        },
    },
    mounted() {
        this.getDanhsach_Doituongban();
        this.getDanhsach_DVDexuat();
        this.getDanhsach_DexuatHDKenh();
        this.getDanhsach_Hangkenh();
        this.getDanhsach_Kenhban()
        
    },
    methods: {
        //Lấy danh sách đối tượng bán
        async getDanhsach_Doituongban(){
            let data = this.GetDataList(await api.dsDoiTuongBan(this.axios,{}));
            this.dsDoituongban = data;
            
        },
        async lay_lichsu_phieugiao(){
                  this.loading(true)
                  let data = this.GetDataList(await api.lichSugiaoPhieu(this.axios,{"hdkhenh_id":this.items.hdkenh_id}));
                  this.DsLichsu = data
                  console.log(this.DsLichsu)
                   this.loading(false)
        },
        //Lấy danh sách hạng kênh
        async getDanhsach_Hangkenh(){
            let input = {
                hdkenh_id: this.items.hdkenh_id != 0?this.items.hdkenh_id:0
            }
            let data = this.GetData(await api.dsHangKenh(this.axios,input));
            this.dsHangkenh = data;
        },
        //Lấy danh sách đơn vị đề xuất
        async getDanhsach_DVDexuat(){
            let data = this.GetData(await api.dsDonViDeXuat(this.axios));
            this.dsDVDexuat = data;
            
        },
        txtMaPhieu_KeyPress(){
            this.getDanhsach_DexuatHDKenh()
        },
        //Lấy danh sách kênh bán
        async getDanhsach_DexuatHDKenh(){
             this.loading(true);
            let input = {
                "maphieu": this.items.ma_yc,
                "makenh": "",
                "doituongban": this.items.dt_ban_id,
                "nhanvien_id":this.items.nhanvien_id,
                "donvi_id":this.items.donvi_id,
                "kenhban_id": this.items.kenhban_id,
                "hangkenh_id":this.items.loaidv_id
            }
            let data = this.GetDataList(await api.lichSuDxHopDong(this.axios,input));
            this.grid_DeXuatKenhban.items = data;
            if(this.grid_DeXuatKenhban.items.length == 0){
                this.DsLichsu = []
                this.btn_Enabled.nhapMoi = true;
            }
             this.loading(false)
        },
        async getDanhsach_LoaiGT(){
            let data = this.GetDataList(await api.lay_ds_loaiGT(this.axios));
            this.dsLoaiGT = data;
            this.items.loaigt_id = data[0].loaigt_id;
        },
        async getDanhsach_Kenhban(){
            let data = this.GetDataList(await api.dsKenhBan(this.axios));
            this.dsKenhban = data;
        },
        //Lấy danh sách nhân viên quản lý kênh bán
        async getDanhsach_NguoiDexuat(){
            let input = {"donvi_id":this.items.donvi_id}
            let data = this.GetDataList(await api.dsNhanVienDeXuat(this.axios,input));
            this.dsNguoidexuat = data;
        },
      
         //Lấy danh sách loại hợp đồng
        async getDanhsach_HTHD(){
             let input = {}
            let data = this.GetDataList(await api.lay_dsHTHD(this.axios,input));
            this.dsLoaiHD = data;
            this.items.hthd_id = data[0].hthd_id;
        },
         //Lấy danh sách File kênh bán
        async getDanhsach_FileKenhBan(){
              let input = {
                    hdkenh_id: this.items.hdkenh_id
                }
            let data = this.GetDataList(await api.dsFileHDK(this.axios,input));
            this.dsFileKenhBan = data;
        },
        //Get key sinh mã kênh bán
        async getSinhMaPhieu(){
            let data = this.GetDataList(await api.sinhMaPhieu(this.axios));
            this.items.ma_yc = data;
        },
        async getSinhMaKenh(ten_hang_kenh){
            let input = {
                    ten_hangkenh: ten_hang_kenh,
                    makenh_cu: this.ma_kenh_cu
            }   
            let data = this.GetDataList(await api.sinhMaKenh(this.axios,input));
            this.items.ma_kenh = data;
            this.ma_kenh_cu  = this.items.ma_kenh
        },
        changeNgaycap(date){
          this.items.ngay_cap =  moment(this.items.ngay_cap).format("DD/MM/YYYY");
        },
        onChangeKenhban(){

        },      
        //CLick để cập nhật hợp đồng
        Hopdong_Click(index, rowData) {
            this.$refs.nhapHopdongModal.showModal();
        },
         //Click để mở popup cập nhật file cho kênh bán
        CapnhatFile_Click(index, rowData) {
            this.$refs.capnhatFileModal.showModal();
        },
        //Click để mở popup giao phiếu
        Giaophieu_Click(index, rowData) {
            this.$refs.GiaophieuModal.showModal();
        },
        HosoFile_Click(index, rowData) {
            this.$refs.HosoFileModal.showModal();
        },
        ghilai(){
            this.$refs.GiaophieuModal.hideModal();
            this.getDanhsach_DexuatHDKenh()
        },
        //Click để mở popup chọn ấp, phường, xã và địa chỉ
        Diachi_Click(index, rowData) {
            this.diachi.diachi = this.items.diachi
            this.diachi.tinh_id = this.items.tinh_id
            this.diachi.quan_id = this.items.quan_id
            this.diachi.phuong_id = this.items.phuong_id
            this.diachi.pho_id = this.items.pho_id
            this.diachi.ap_id = this.items.ap_id
            this.diachi.khu_id = this.items.khu_id
            this.diachi.dac_diem_id = this.items.dacdiem_id
            this.diachi.so_nha = this.items.sonha;
            this.$bvModal.show('popupDiaChi_LD')
        },
         //Kiểm tra trước khi view popup Thay thế hợp đồng, báo hỏng
        ChonNguoiDeXuat(){
            this.showTTHD = "dropdown";
            var container = $("#move");
            container.show();
           
        },
        //Đóng dropdown
        CloseDropDown(){
            var container = $("#move");
            container.hide();
        },
        //Click đúp để lấy mã nhân viên
        Nguoidexuat_DoubleClick(index, rowData){
            this.items.nhanvien_id = rowData.nhanvien_id;
            this.ma_nv_dexuat = rowData.ten_nv;
            this.CloseDropDown()
        },
        //Lấy địa chỉ
        getDiaChi(data) {
            this.items.diachi = data.diachimoi;
            this.items.tinh_id = data.tinh_id;
            this.items.quan_id = data.quan_id;
            this.items.phuong_id = data.phuong_id;
            this.items.pho_id = data.pho_id;
            this.items.ap_id = data.ap_id;
            this.items.khu_id = data.khu_id;
            this.items.dacdiem_id = data.dacdiem_id;
        },
        onChangeHangkenh(value){ 
            this.items.loaidv_id = value;
            let ten_hang_kenh = this.dsHangkenh.find(item => item.loaidv_id == value).ten_loaidv
            this.getSinhMaKenh(ten_hang_kenh);
            if(ten_hang_kenh == 'Cộng tác viên'){
                this.hangkenhCTV = true;
                
            }else{
                this.hangkenhCTV = false;
            }
        },
        onDVDexuat(){
            this.getDanhsach_NguoiDexuat() 
        },
         onXoa() { 
                this.$bvModal.msgBoxConfirm("Bạn có muốn đề xuất ký hợp đồng này không?", {
                        title: '',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Không đồng ý',
                    }).then(async v => {
                        if(v) {
                            let input = {
                                    hdkenh_id: this.items.hdkenh_id
                            }
                             this.loading(true)
                            let data = this.GetDataList(await api.xoaDeXuatHopDong(this.axios,input));
                            if(data === 'ok'){
                                    this.$toast.success('Xóa đè xuất ký hợp đồng thành công!');
                                    this.getDanhsach_DexuatHDKenh()
                            }else{
                                this.$toast.error(data);
                            }
                             this.loading(false)
                        }
                    });
         },
        changeNgaysinh(date){
          this.items.ngaysinh =  moment(this.items.ngaysinh).format("DD/MM/YYYY");
        },
        validateData() {
            this.$v.$touch();
            let errorMessage = [];
            let focusInput = [];

            if (!this.$v.items.ma_kenh.required) {
                errorMessage.push("Mã đại lý không được để trống!");
                focusInput.push('ma_kenh');
            } else if (!this.$v.items.ma_kenh.maxLength) {
                errorMessage.push("Mã đại lý không được lớn hơn 300 ký tự!");
                focusInput.push('ma_kenh');
            }
            if (!this.$v.ma_nv_dexuat.required) {
                errorMessage.push("Nhân viên đề xuất không được để trống!");
                focusInput.push('ma_nv_dexuat');
            } else if (!this.$v.ma_nv_dexuat.maxLength) {
                errorMessage.push("Nhân viên đề xuất không được lớn hơn 300 ký tự!");
                focusInput.push('ma_nv_dexuat');
            }
            if (!this.$v.items.ten_kenh.required) {
                errorMessage.push("Tên đại lý không được để trống!");
                focusInput.push('ten_kenh');
            } else if (!this.$v.items.ten_kenh.maxLength) {
                errorMessage.push("Tên đại lý không được lớn hơn 300 ký tự!");
                focusInput.push('ten_kenh');
            }

            if (!/^\d{10,20}$/.test(this.$v.items.so_dt.$model)) {
                errorMessage.push("Số điện thoại phải là chuỗi chứa từ 10 đến 20 chữ số!");
                focusInput.push('so_dt');
            }
            if (!this.$v.items.mst_cccd.required) {
                errorMessage.push("Mã số thuế không được để trống!");
                focusInput.push('mst_cccd');
            } else if (!this.$v.items.mst_cccd.maxLength) {
                errorMessage.push("Mã số thuế không được lớn hơn 50 ký tự!");
                focusInput.push('mst_cccd');
            }
             if (!this.$v.items.diachi.required) {
                errorMessage.push("Địa chỉ không được để trống!");
                focusInput.push('diachi');
            } else if (!this.$v.items.diachi.maxLength) {
                errorMessage.push("Địa chỉ không được lớn hơn 300 ký tự!");
                focusInput.push('diachi');
            }

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join("\r\n"));
                this.$refs[focusInput[0]].focus();
                return false;
            } else return true;
        },

        async onLayTT(isMessage = true) {
            await this.getDanhsach_DexuatHDKenh();
            
            if (this.grid_DeXuatKenhban.items.length <= 0) {
                this.$toast.error('Không có dữ liệu, vui lòng thử lại');
            }else{
                this.$root.$toast.success("Đã tìm thấy "+this.grid_DeXuatKenhban.items.length+" đề xuất ký hợp đồng kênh bán !");
            }
        },
        //Nhấn nhập mới
        async onNhapMoi() {
            this.setButton(1);
            await this.getSinhMaPhieu();
            await this.getSinhMaKenh(this.ten_hang_kenh);
            
        },
        //Ghi lại thông tin kênh bán
        async onGhiLai() {
            if (this.validateData()) {
                if (!this.btn_Enabled.nhapMoi) {
                    let rs = await this.themMoiDeXuatHopDong();
                } else {
                    let rs = await this.capNhatDeXuatHopDong();
                }

                await this.onLayTT(false);
            }
        },
        //Hủy thực hiện
        onHuy() {
             this.setButton(1);
        },
        //Click chọn kênh bán
        onDeXuatSelected(args) {
            if (args != null) {
                this.items.hdkenh_id =  args.data.hdkenh_id;
               this.lay_lichsu_phieugiao();
            }
        },
        GetDataList: function (response) {
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data
            } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
                
            } else {
                 return response.data.message
            }
            return []
        },
        GetData: function (response) {
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data
            } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
                return null
            } else {
                this.$root.$toast.error('Lấy dữ liệu không thành công')
            }
            return null
        },
        setButton(kieu) {
            this.btn_Enabled = {
                nhapMoi: false,
                ghiLai: false,
                xoa: false,
                huy: false,
                ganquyen: false,
                ganloaisanpham: false,
                duyet: false

            };
            if (kieu === -1) { //Bat dau  
                this.btn_Enabled.ghiLai = true;
                this.btn_Enabled.huy = true;
                this.btn_Enabled.ganquyen = true;
                this.btn_Enabled.ganloaisanpham = true;
                this.btn_Enabled.duyet = true;
            } else if (kieu === 1) { //Them moi
                this.clear();
                this.btn_Enabled.ghiLai = true;
                this.btn_Enabled.huy = true;
                this.btn_Enabled.nhapMoi = false;
            } else if (kieu === 2) { //Cap nhat
                this.btn_Enabled.nhapMoi = true;
                this.btn_Enabled.ghiLai = true;
                this.btn_Enabled.huy = true;
                this.btn_Enabled.xoa = true;
                this.btn_Enabled.duyet = true;

            }
        },

        clear() {
            this.ma_nv_dexuat = "";
            this.getDanhsach_Doituongban()
            this.getDanhsach_Hangkenh()
            this.getDanhsach_DVDexuat()
            this.dsFileKenhBan = []
            this.items = {diachi:"",ma_nv:"",ma_yc: "",ma_kenh: "",gioitinh: 0,ngay_cap:moment().format('DD/MM/YYYY'),ngaysinh:moment().format('DD/MM/YYYY')}
            this.$v.$reset();
            this.$refs.ten_kenh.focus();
        },
        async themMoiDeXuatHopDong() {
            try {
                this.loading(true);
                await api.themMoiDeXuatHopDong(this.axios, this.items).then((res) => {
                    if(res.data.data === "ok"){
                            this.$toast.success("Thêm mới thành công !");
                            this.getDanhsach_DexuatHDKenh();
                    }else{
                        this.$toast.error(res.data.data);
                    }
                });
            } catch (error) {
                this.$toast.error('Thêm mới không thành công!');
                return false;
            } finally {
                this.loading(false);
            }
        },
        onDuyet(){
            console.log("Tạm thời bỏ qua chưa làm")
        },
        async capNhatDeXuatHopDong() {
            try {
                this.loading(true);
                await api.capNhatDeXuatHopDong(this.axios, this.items).then((res) => {
                    if(res.data.data =="ok"){
                            this.$root.$toast.success("Cập nhật thành công !");
                            this.getDanhsach_DexuatHDKenh();
                    }else{
                        this.$toast.error(res.data.data);
                    }
                });
            } catch (error) {
                console.log(error)
                this.$toast.error('Cập nhật không thành công!');
                return false;
            } finally {
                this.loading(false);
            }
        },
    }
}
</script>
<style>
.info-row .value{
    padding-left: 0px !important;
}
.pd-l0{
    padding-left: 0px !important;
}
.select2-container .select2-selection--single{
    border: 1px solid #ced4da !important
}
.bd-cl0{
    border-color: gray !important
}
.bd-0{
    border-color: gray !important
}
</style>
