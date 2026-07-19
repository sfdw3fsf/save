
<template src="./SalesChannel.html"></template>


<style scoped src="./SalesChannel.scss"></style>

<script>
import ComboboxGrid from "@/components/Controls/ComboboxGrid"
import {
    required,
    maxLength,
    minLength,
    numeric
} from "vuelidate/lib/validators"
import moment from 'moment'
import Chitietkenhban from "./popup/Chitietkenhban/Chitietkenhban.vue";
import Hopdong from "./popup/Hopdong/Hopdong.vue";
import GanquyenUser from "./popup/GanquyenUser/GanquyenUser.vue";
import Ganloaisanpham from "./popup/Ganloaisanpham/Ganloaisanpham.vue";
import CapnhatFile from "./popup/CapnhatFile/CapnhatFile.vue";
import ModalChonDiaChi from './popup/ChonDiaChi'
import api from "./api/index.js";
export default {
    components: {
        appCombobox: ComboboxGrid,
        Chitietkenhban,
        Hopdong,
        GanquyenUser,
        Ganloaisanpham,
        ModalChonDiaChi,
        CapnhatFile
    },
    name: 'SalesChannel',

    async created() {
        this.setButton(-1);
        await this.onLayTT(false);
        this.listGioitinh = [
            {
                id: 0,
                ten: "Nam",
            },
            {
                id: 1,
                ten: "Nữ",
            },
            {
                id: -1,
                ten: "Khác",
            }
        ];
         this.listLoaigiayto = [
            {
                id: 0,
                ten: "CCCD/CMT",
            },
            {
                id: 1,
                ten: "Hộ chiếu",
            },
            {
                id: 2,
                ten: "Căn cước quân đội",
            }
        ];
        this.listTrangthai= [
            {
                id_trangthai: 0,
                ten_trangthai: "Hoạt động",
            },
            {
                id_trangthai: 1,
                ten_trangthai: "Dừng",
            },
            {
                id_trangthai: 2,
                ten_trangthai: "Hủy",
            },
            {
                id_trangthai: 3,
                ten_trangthai: "Không thanh toán hoa hồng",
            }
        ];
        this.items.trangthai = this.listTrangthai[0].id_trangthai;
        this.items.gioitinh = this.listGioitinh[0].id;
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
                ngay_sn: moment().format('DD/MM/YYYY'),
                ngay_cap: moment().format('DD/MM/YYYY'),
            },
            header: {
                title: "Danh mục kênh bán",
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
            diachi: {},
            dsKenhban:[],
            dsHangkenh:[],
            dsHopdong:[],
            dsNSQL:[],
            dsLoaiGT: [],
            dsFileKenhBan:[],
            linkview_file: "",
            txtFile_Anhmattruoc:"",
            txtFile_Anhmatsau:"",
            txtFile_Anhchugiayto:"",
            txtFile_GiayPhepKD:"",
            file_anhmattruoc:"",
            file_anhmatsau:"",
            file_chandung:"",
            ma_nvql:"",
            xemFile:"",
            showTTHD:"",
            file_GPKD:"",
            anhmatsau_view:"",
            grid_Kenhban: {
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
            ma_nv: {
                required,
                maxLength: maxLength(300)
            }, 
            ma_nd: {
                required,
                maxLength: maxLength(100),
            },
            so_dt: {
                required,
                maxLength: maxLength(20),
            },
            diachi: {
                required,
                maxLength: maxLength(300),
            },
            mst: {
                required,
                maxLength: maxLength(20),
            },
            so_gp_kd: {
                required,
                maxLength: maxLength(50),
            },
            file_gp_kd: {
                required
            },
            anh_gt_mat_truoc: {
                required
            },
            anh_gt_mat_sau: {
                required
            },
            anh_chan_dung_cgt: {
                required
            },
        },
    },
    mounted() {
        this.getDanhsach_Kenhban();
        this.getDanhsach_Hangkenh();
        this.getDanhsach_LoaiGT();
        this.getDanhsach_HTHD();
        this.getDanhsach_TTKenhban();
    },
    methods: {
        //Lấy danh sách thành viên kênh
        async getDanhsach_Thanhvienkenh(){
            let input = {
                    loaidv_id:this.items.loaidv_id
                    }
            let data = this.GetDataList(await api.lay_ds_thanhvienkenh(this.axios,input));
            this.dsThanhvienkenh = data;
            if(this.dsThanhvienkenh.length > 0){
                this.items.donvi_id = data[0].donvi_id;
            }
            
        },
        //Lấy danh sách kênh bán
        async getDanhsach_Kenhban(){
            let data = this.GetDataList(await api.lay_ds_kenhban(this.axios));
            this.grid_Kenhban.items = data;
            if(this.grid_Kenhban.items.length == 0){
                 this.btn_Enabled.nhapMoi = true;
            }
        },
        //Lấy danh sách hạng kênh
        async getDanhsach_Hangkenh(){
            let data = this.GetData(await api.lay_ds_hangkenh(this.axios));
            this.dsHangkenh = data;
            this.items.loaidv_id = data[0].loaidv_id;
            this.getDanhsach_Thanhvienkenh();
        },
        //Lấy danh sách nhân viên quản lý kênh bán
        async getDanhsach_NVQL(){
            let input = {
                    tinh_id: this.items.tinh_id,
                    quan_id: this.items.quan_id,
                    phuong_id: this.items.phuong_id,
                    pho_id: this.items.pho_id,
                    ap_id: this.items.ap_id,
                    khu_id: this.items.khu_id,
                    dacdiem_id: this.items.dacdiem_id
                }
            let data = this.GetDataList(await api.lay_ds_nhanvienQL(this.axios,input));
            this.dsNSQL = data;
        },
        //Lấy danh sách loại giấy tờ
        async getDanhsach_LoaiGT(){
            let data = this.GetDataList(await api.lay_ds_loaiGT(this.axios));
            this.dsLoaiGT = data;
            this.items.loaigt_id = data[0].loaigt_id;
        },
         //Lấy danh sách trạng thái kênh bán
        async getDanhsach_TTKenhban(){
            let data = this.GetDataList(await api.lay_dsTTKenhBan(this.axios));
            this.dsTTKenhban= data;
            this.items.tag = data[0].value;
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
                    ma_kenh_ban: this.items.ma_nv
                }
            let data = this.GetDataList(await api.lay_dsFile_Kenhban(this.axios,input));
            this.dsFileKenhBan = data;
        },
        //Get key sinh mã kênh bán
        async getKey_Kenhban(){
            let data = this.GetDataList(await api.getKey_Kenhban(this.axios));
            this.items.ma_nv = data;
            console.log(this.items.ma_nv)
        },
        //Nhấn đúp vào grid để xem thông tin sản phẩm, quyền, hợp đồng
        Kenhban_DoubleClick(index, rowData) {
            this.$refs.chitietkenhbanModal.showModal();
        },
        //CLick để cập nhật hợp đồng
        Hopdong_Click(index, rowData) {
            this.$refs.nhapHopdongModal.showModal();
        },
        //Click để mở popup gán quyền cho kênh bán
        Ganquyen_Click(index, rowData) {
            this.$refs.ganquyenUserModal.showModal();
        },
        //Click để mở popup gán sản phẩm cho kênh bán
        Ganloaisanpham_Click(index, rowData) {
            this.$refs.ganloaisanphamModal.showModal();
        },
         //Click để mở popup cập nhật file cho kênh bán
        CapnhatFile_Click(index, rowData) {
            this.$refs.capnhatFileModal.showModal();
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
        ChonNVQL(){
            console.log(this.items.diachi)
            if(this.items.diachi == ""){
                this.$root.$toast.warning("Vui lòng chọn địa chỉ trước khi chọn nhân viên quản lý!")
                return;
            }
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
        NVQL_DoubleClick(index, rowData){
            this.items.nvql_id = rowData.nhanvien_id;
            this.ma_nvql = rowData.ma_nv;
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
            this.getDanhsach_NVQL()
        },
        onChangeHangkenh(){
            this.getDanhsach_Thanhvienkenh();
        },
        changeNgaysinh(date){
          this.items.ngay_sn =  moment(this.items.ngay_sn).format("DD/MM/YYYY");
        },
        changeNgaycap(date){
          this.items.ngay_cap =  moment(this.items.ngay_cap).format("DD/MM/YYYY");
        },
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
                                    ma_kenh_ban: this.items.ma_nv,
                                    files: fileArray
                            }
                            let data = this.GetDataList(await api.capNhatFileKB(this.axios,input));
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
        validateData() {
            this.$v.$touch();
            let errorMessage = [];
            let focusInput = [];

            if (!this.$v.items.ma_nv.required) {
                errorMessage.push("Mã kênh bán không được để trống!");
                focusInput.push('ma_nv');
            } else if (!this.$v.items.ma_nv.maxLength) {
                errorMessage.push("Tên đối tác không được lớn hơn 300 ký tự!");
                focusInput.push('ma_nv');
            }
            if (!this.$v.items.ma_nd.required) {
                errorMessage.push("Username không được để trống!");
                focusInput.push('user_name');
            } else if (!this.$v.items.ma_nd.maxLength) {
                errorMessage.push("Username không được lớn hơn 100 ký tự!");
                focusInput.push('user_name');
            }
            if (!this.$v.items.so_dt.required) {
                errorMessage.push("Số điện thoại không được để trống!");
                focusInput.push('phone');
            } else if (!this.$v.items.so_dt.maxLength) {
                errorMessage.push("Số điện thoại không được lớn hơn 20 ký tự!");
                focusInput.push('phone');
            }
            if (!this.$v.items.mst.required) {
                errorMessage.push("Mã số thuế không được để trống!");
                focusInput.push('mst');
            } else if (!this.$v.items.mst.maxLength) {
                errorMessage.push("Mã số thuế không được lớn hơn 50 ký tự!");
                focusInput.push('mst');
            }
             if (!this.$v.items.diachi.required) {
                errorMessage.push("Địa chỉ kênh bán không được để trống!");
                focusInput.push('phone');
            } else if (!this.$v.items.diachi.maxLength) {
                errorMessage.push("Địa chỉ kênh bán không được lớn hơn 300 ký tự!");
                focusInput.push('phone');
            }

            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join("\r\n"));
                this.$refs[focusInput[0]].focus();
                return false;
            } else return true;
        },

        async onLayTT(isMessage = true) {
            await this.getDanhsach_Kenhban();
            if (this.grid_Kenhban.items.length <= 0) {
                this.setButton(1);
                if (isMessage) this.$toast.error('Không có dữ liệu, vui lòng thử lại');
            }
        },
        //Nhấn nhập mới
        onNhapMoi() {
            this.getKey_Kenhban()
            this.setButton(1);
        },
        //Ghi lại thông tin kênh bán
        async onGhiLai() {
            if (this.validateData()) {
                if (!this.btn_Enabled.nhapMoi) {
                    let rs = await this.addKenhBan();
                    if (rs) this.$toast.success('Thêm mới thành công!');
                } else {
                    let rs = await this.updateKenhban();
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
                this.onKenhbanSelected({
                    data: this.grid_Kenhban.selected
                });
                this.setButton(2);
            }
        },
        //Click chọn kênh bán
        onKenhbanSelected(args) {
            if (args != null) {
                this.grid_Kenhban.selected = args.data;
                this.formData.ma_nv = args.data.ma_nv;
                this.items =  this.grid_Kenhban.selected;
                this.getDanhsach_Thanhvienkenh()
                this.ma_nvql = args.data.ma_nv_ql
                this.getDanhsach_NVQL();
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
            } else if (kieu === 2) { //Cap nhat
                this.btn_Enabled.nhapMoi = true;
                this.btn_Enabled.ghiLai = true;
                this.btn_Enabled.huy = true;
                this.btn_Enabled.xoa = true;
                this.btn_Enabled.ganquyen = true;
                this.btn_Enabled.ganloaisanpham = true;
                this.btn_Enabled.duyet = true;

            }
        },

        clear() {
            this.ma_nvql = "";
            this.dsNSQL = []
            this.dsFileKenhBan = []
            this.items = {diachi:"",ma_nv:"",ngay_cap:moment().format('DD/MM/YYYY'),ngay_sn:moment().format('DD/MM/YYYY'),ma_nvql:""}
            this.$v.$reset();
            this.$refs.ma_nv.focus();
        },
        async addKenhBan() {
            try {
                this.loading(true);
                await api.Themmoi_Kenhban(this.axios, this.items).then((res) => {
                    if(res.data.error_code =="BSS-00000000"){
                            this.$root.$toast.success("Thêm mới thành công !");
                            this.getDanhsach_Kenhban();
                    }else{
                        this.$toast.error(res.data.message);
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
        async updateKenhban() {
            try {
                this.loading(true);
                await api.Capnhat_Kenhban(this.axios, this.items).then((res) => {
                    if(res.data.error_code =="BSS-00000000"){
                            this.$root.$toast.success("Cập nhật thành công !");
                            this.getDanhsach_Kenhban();
                    }
                });
            } catch (error) {
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
