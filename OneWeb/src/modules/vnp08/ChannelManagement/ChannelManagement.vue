
<template src="./ChannelManagement.html"></template>


<style scoped src="./ChannelManagement.scss"></style>

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
import CapnhatFile from "./popup/CapnhatFile/CapnhatFile.vue";
import ModalChonDiaChi from './popup/ChonDiaChi'
import api from "./api/index.js";
export default {
    components: {
        appCombobox: ComboboxGrid,
        Giaophieu,
        ModalChonDiaChi,
        CapnhatFile
    },
    name: 'ChannelManagement', 

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
                dt_ban_id: 0,
                donvi_id: 0,
                ma_kenh: "",
                ten_kenh: "", 
                fax: "",
                nguoi_dd: "",
                ngaysinh: moment().format('DD/MM/YYYY'),
                gioitinh: 0
            },
            header: {
                title: "AM/AS/QLĐB đề xuất ký hợp đồng kênh bán",
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
            dsFileKenhBan:[],
            dsDoituongban:[],
            dsDVDexuat:[],
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
                maxLength: maxLength(13),
                minLength: maxLength(10),
            },
        },
    },
    mounted() {
        this.getDanhsach_Doituongban();
        this.getDanhsach_DVDexuat();
        this.getDanhsach_DexuatHDKenh();
        this.getDanhsach_Hangkenh();
        this.getDanhsach_HTHD();
    },
    methods: {
        //Lấy danh sách đối tượng bán
        async getDanhsach_Doituongban(){
            let data = this.GetDataList(await api.dsDoiTuongBan(this.axios,{}));
            this.dsDoituongban = data;
            if(this.dsDoituongban.length > 0){
                this.items.dt_ban_id = data[0].dt_ban_id;
            }
            
        },
        //Lấy danh sách hạng kênh
        async getDanhsach_Hangkenh(){
            let data = this.GetData(await api.dsHangKenh(this.axios));
            this.dsHangkenh = data;
            this.items.loaidv_id = data[0].loaidv_id;
            this.ten_hang_kenh = data[0].ten_loaidv;
        },
        //Lấy danh sách đơn vị đề xuất
        async getDanhsach_DVDexuat(){
            let data = this.GetData(await api.dsDonViDeXuat(this.axios));
            this.dsDVDexuat = data;
            this.items.donvi_id = data[0].donvi_id;
            this.getDanhsach_NguoiDexuat()
        },
        //Lấy danh sách kênh bán
        async getDanhsach_DexuatHDKenh(){
            let data = this.GetDataList(await api.dsDeXuatHopDongKenh(this.axios));
            this.grid_DeXuatKenhban.items = data;
            if(this.grid_DeXuatKenhban.items.length == 0){
                 this.btn_Enabled.nhapMoi = true;
            }
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
        async getSinhMaKenh(){
            let input = {
                    ten_hangkenh: this.ten_hang_kenh,
                    makenh_cu: this.ma_kenh_cu
            }   
            let data = this.GetDataList(await api.sinhMaKenh(this.axios,input));
            this.items.ma_kenh = data;
            this.ma_kenh_cu  = this.items.ma_kenh
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
            this.ma_nv_dexuat = rowData.ma_nv;
            this.CloseDropDown()
        },
        async viewFile(url){
            let input ={
                list_file: url
            }
            let data = this.GetDataList(await api.view_file(this.axios,input));
            this.linkview_file = data[0].file_url;
        },
        onClickXemAnh() {
            this.$bvModal.show("PopUp");
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
        onChangeHangkenh(){
            this.getSinhMaKenh();
            if(this.items.loaidv_id == 1006){
                this.hangkenhCTV = true;
                
            }else{
                this.hangkenhCTV = false;
            }
        },
        onDVDexuat(){},
        gridDsFileCommand(args,data_file) {
            if(args == 'Xóa'){
                this.$bvModal.msgBoxConfirm("Bạn có muốn xóa file "+data_file.loai_file+" này không?", {
                        title: '',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Không đồng ý',
                    }).then(async v => {
                        if(v) {
                            let fileArray = this.dsFileKenhBan.filter(item => item.file_id !== data_file.file_id);                          
                            let input = {
                                    hdkenh_id: this.items.hdkenh_id,
                                    files: fileArray
                            }
                            let data = this.GetDataList(await api.capNhatFileHDK(this.axios,input));
                            if(data=="OK"){
                                    this.$toast.success('Xóa file thành công!');
                                    this.getDanhsach_FileKenhBan()
                            }
                        }
                    });
            }else{
                this.checkFileType(data_file.url)
            }

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
                            let data = this.GetDataList(await api.xoaDeXuatHopDong(this.axios,input));
                            if(data=="ok"){
                                    this.$toast.success('Xóa đè xuất ký hợp đồng thành công!');
                                    this.getDanhsach_DexuatHDKenh()
                            }
                        }
                    });
         },
         downloadFile(url) {
            const link = document.createElement('a');
            link.href = url;
            link.target = "_blank"
            link.click();
         },
         async checkFileType(event) {
            let file = event
            if (file.endsWith('.jpg') || file.endsWith('.jpeg') || file.endsWith('.png')) {
                this.viewFile(file);
                this.onClickXemAnh();
            } else {
                await this.viewFile(file);
                this.downloadFile(this.linkview_file)
            }
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
            if (!this.$v.items.ten_kenh.required) {
                errorMessage.push("Tên đại lý không được để trống!");
                focusInput.push('ten_kenh');
            } else if (!this.$v.items.ten_kenh.maxLength) {
                errorMessage.push("Tên đại lý không được lớn hơn 300 ký tự!");
                focusInput.push('ten_kenh');
            }

            if (!this.$v.items.so_dt.maxLength) {
                errorMessage.push("Số điện thoại không được lớn hơn 20 ký tự!");
                focusInput.push('so_dt');
            }
            if (!this.$v.items.mst_cccd.required) {
                errorMessage.push("Mã số thuế không được để trống!");
                focusInput.push('mst_cccd');
            } else if (!this.$v.items.mst_cccd.maxLength) {
                errorMessage.push("Mã số thuế phải từ 10 đến 13 ký tự!");
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
                this.setButton(1);
                if (isMessage) this.$toast.error('Không có dữ liệu, vui lòng thử lại');
            }
        },
        //Nhấn nhập mới
        async onNhapMoi() {
            this.setButton(1);
            await this.getSinhMaPhieu();
            await this.getSinhMaKenh();
            
        },
        //Ghi lại thông tin kênh bán
        async onGhiLai() {
            if (this.validateData()) {
                if (this.btn_Enabled.nhapMoi) {
                    let rs = await this.themMoiDeXuatHopDong();
                    if (rs) this.$toast.success('Thêm mới thành công!');
                } else {
                    let rs = await this.capNhatDeXuatHopDong();
                    if (rs) this.$toast.success('Cập nhật thành công!');
                }

                await this.onLayTT(false);
            }
        },
        //Hủy thực hiện
        onHuy() {
            if (!this.btn_Enabled.nhapMoi) // Hủy nhập mới
                this.onNhapMoi();
            else { 

                this.onDeXuatSelected({
                    data: this.grid_DeXuatKenhban.selected
                });
            }
        },
        //Click chọn kênh bán
        onDeXuatSelected(args) {
            if (args != null) {
                this.grid_DeXuatKenhban.selected = args.data;
                this.formData.ma_nv = args.data.ma_nv;
                this.items =  this.grid_DeXuatKenhban.selected;
                this.getDanhsach_DVDexuat();
                this.getDanhsach_Hangkenh();
                this.ma_nv_dexuat = args.data.ma_nv
                this.getDanhsach_NguoiDexuat();
                this.getDanhsach_FileKenhBan();
                this.setButton(2);
            }
        },
        GetDataList: function (response) {
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data
            } else if (response && response.data && response.data.error_code === 'BSS-00000204') {} else {
                this.$root.$toast.error('Lấy dữ liệu không thành công')
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
                this.btn_Enabled.nhapMoi = true;
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
            this.items = {diachi:"",ma_nv:"",ma_yc: "",ma_kenh: "",gioitinh: 0,ngaysinh:moment().format('DD/MM/YYYY')}
            this.$v.$reset();
            this.$refs.ten_kenh.focus();
        },
        async themMoiDeXuatHopDong() {
            try {
                this.loading(true);
                await api.themMoiDeXuatHopDong(this.axios, this.items).then((res) => {
                    if(res.data.data =="ok"){
                            this.$root.$toast.success("Thêm mới thành công !");
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
.bd-cl0{
    border-color: gray !important
}
.bd-0{
    border-color: gray !important
}
</style>
