
<template src="./ApproveSalesChannel.html"></template>
<style scoped src="./ApproveSalesChannel.scss"></style>
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
import Tralaiphieugiao from "./popup/Tralaiphieu/Tralaiphieu.vue";
import Lichsuphieugiao from "./popup/Lichsuphieugiao/Lichsuphieugiao.vue";
import CapnhatFile from "./popup/CapnhatFile/CapnhatFile.vue";
import ModalChonDiaChi from './popup/ChonDiaChi'
import HosoFile from "./popup/HosoFile/HosoFile.vue";
import api from "./api/index.js";
export default {
    components: {
        appCombobox: ComboboxGrid,
        Giaophieu,
        Lichsuphieugiao,
        Tralaiphieugiao,
        ModalChonDiaChi,
        CapnhatFile,
        HosoFile
    },
    name: 'ApproveSalesChannel', 
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
            isDisabled: true,
            items: {
                diachi: "",
                ma_nvql:"",
                dt_ban_id: 0,
                donvi_id: 0,
                ma_kenh: "",
                ten_kenh: "", 
                fax: "",
                loaidv_id: 0,
                hangkenh_id: 0,
                loaigt_id: 0,
                hdkenh_id:0,
                nguoi_dd: "",
                ngaysinh: moment().format('DD/MM/YYYY'),
                ngay_cap: moment().format('DD/MM/YYYY'),
                gioitinh: 0
            },
            header: {
                title: "Lãnh đạo đơn vị phê duyệt đề xuất ký hợp đồng kênh bán",
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
            noidung: "",
            lydo: "",
            ten_hang_kenh:"",
            huonggiao: 0,
            dsHuonggiao: [],
            dsDonvigiao: [],
            dsNhanviengiao: [],
            dsNhanviennhan:[],
            dsDonvinhan: [],
            donvi_giao_id: 0,
            donvi_nhan_id: 0,
            nhanvien_giao_id: 0,
            nhanvien_nhan_id: 0,
            items: {},
            ngaygiao: moment().format('DD/MM/YYYY HH:mm:ss'),
            ngayduyet: moment().format('DD/MM/YYYY'),
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
            nguoiduyet: "",
            showTTHD:"",
            file_GPKD:"",
            data_phieugiao: [],
            dsDonviduyet: [],
            donvi_duyet_id: 0,
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
                maxLength: maxLength(50),
            },
        },
    },
     mounted() {
        if (this.$route.query.tag && this.$route.query.tag !== '') {
            this.items.tthd_id = this.$route.query.tag.replaceAll(' ', '+')
        }
        console.log(this.$route.query.tag.replaceAll(' ', '+'))
         this.getDanhsach_LoaiGT()
         this.getDanhsach_Doituongban();
         this.getDanhsach_DexuatHDKenh();
         this.getDanhsach_DVDexuat();
         this.getDanhsach_Hangkenh();
         this.getDanhsach_HTHD();
         this.getDsHuonggiao();
         this.getDsDonvigiao();
         this.getDsDonvinhan();
         this.getDsDonviDuyet();

        this.nguoiduyet = this.$root.context.user.getTenNhanVien()
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
            if(this.grid_DeXuatKenhban.items.length == 0){
                this.items.donvi_id = data[0].donvi_id;
                this.getDanhsach_NguoiDexuat()
            }
        },
        async getDanhsach_LoaiGT(){
            let data = this.GetDataList(await api.lay_ds_loaiGT(this.axios));
            this.dsLoaiGT = data;
            this.items.loaigt_id = data[0].loaigt_id;
        },
        //Lấy danh sách đề xuất
        async getDanhsach_DexuatHDKenh(){
            let data = this.GetDataList(await api.dsDeXuatHopDongKenh(this.axios,{tthd_id: 1}));
            this.grid_DeXuatKenhban.items = data;
            if(this.grid_DeXuatKenhban.items.length == 0){
                 this.btn_Enabled.nhapMoi = true;
                 this.clear();
            }
        },
        async dsPhieuGiao(){
            let data = this.GetDataList(await api.dsPhieuGiao(this.axios,{hdkenh_id: this.items.hdkenh_id}));
            this.data_phieugiao = data[0]
            console.log(this.data_phieugiao)
            this.huonggiao = this.data_phieugiao.huonggiao_id;
            this.donvi_giao_id = this.data_phieugiao.donvi_giao_id;
            this.getDsNhanviengiao();
            this.nhanvien_giao_id = this.data_phieugiao.nhanvien_giao_id;
            this.donvi_nhan_id = this.data_phieugiao.donvi_nhan_id;
            this.getDsNhanviennhan()
            this.nhanvien_nhan_id = this.data_phieugiao.nhanvien_nhan_id;
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
        //Lấy danh sách huonggiao
        async getDsHuonggiao() { 
            let input = {
                tthd_id: this.items.tthd_id
            }
            let data = this.GetDataList(await api.dsAllHuongGiao(this.axios, input));
            this.dsHuonggiao = data
            this.huonggiao =  data[0].huonggiao_id
        },
        //Lấy danh sách đơn vị giao
        async getDsDonvigiao() { 
            let data = this.GetDataList(await api.dsDonViGiao(this.axios,{}));
            this.dsDonvigiao = data
            this.donvi_giao_id = data[0].donvi_id
            
        },
        //Lấy danh sách đơn vị nhận
        async getDsDonvinhan() { 
            let data = this.GetDataList(await api.dsDonViNhan(this.axios,{}));
            this.dsDonvinhan = data
            this.donvi_nhan_id = data[0].donvi_id
           
        },
        //Lấy danh sách đơn vị giao
        async getDsNhanviengiao() { 
            let input = {
                donvi_id: this.donvi_giao_id
            }
            let data = this.GetDataList(await api.dsNhanVienGiao(this.axios,input));
            this.dsNhanviengiao = data
            //this.nhanvien_giao_id = data[0].nhanvien_id
        },
        //Lấy danh sách đơn vị nhận
        async getDsNhanviennhan() { 
            let input = {
                donvi_id: this.donvi_nhan_id
            }
            let data = this.GetDataList(await api.dsNhanVienNhan(this.axios,input));
            this.dsNhanviennhan = data
            //this.nhanvien_nhan_id = data[0].nhanvien_id
        },
        //Lấy danh sách đơn vị duyệt
        async getDsDonviDuyet() {  
            let data = this.GetDataList(await api.dsDonViDuyet(this.axios,{}));
            this.dsDonviduyet = data
            this.donvi_duyet_id = data[0].donvi_id
        },
        HosoFile_Click(index, rowData) {
            this.$refs.HosoFileModal.showModal();
        },
        changeNgaycap(date){
          this.items.ngay_cap =  moment(this.items.ngay_cap).format("DD/MM/YYYY");
        },
         async Pheduyet_click() {
            try {
                this.loading(true);
                 this.$bvModal.msgBoxConfirm("Bạn muốn phê duyệt đề xuất ký hợp đồng này không?", {
                        title: '',
                        size: 'sm',
                        okTitle: 'Đồng ý',
                        cancelTitle: 'Không đồng ý',
                    }).then(async v => {
                        if(v) {
                                let input = {
                                    "hdkenh_id":this.items.hdkenh_id,
                                    "phieu_id":this.data_phieugiao.phieu_id,
                                    "ttph_id": 4,
                                    "tthd_id":this.items.tthd_id,
                                    "noidung_thuchien":this.noidung,
                                    "nguoi_thuchien":this.$auth.getNhanVienID(),
                                    "donvi_thuchien":this.donvi_duyet_id,
                                    "ngay_thuchien":this.ngayduyet,
                                }
                                await api.duyetPhieuGiao(this.axios,input).then((res) => {
                                    if(res.data.data =="ok"){
                                            this.$root.$toast.success("Duyệt đề xuất thành công !");
                                            this.onLayTT(false)
                                    }else{
                                        this.$toast.error(res.data.data);
                                    }
                                });
                             }
                    });
            } catch (error) {
                console.log(error)
                this.$toast.error('Duyệt đề xuất không thành công!');
                return false;
            } finally {
                this.loading(false);
            }
        },
        Tuchoi_click(index, rowData) {
           this.$refs.TralaiModal.showModal();
        },
        Tralai(){
            this.$refs.TralaiModal.hideModal();
            this.getDanhsach_DexuatHDKenh();
        },
        onChangeDonvigiao(){
            this.getDsNhanviengiao()
        },
        onChangeDonvinhan(){
            this.getDsNhanviennhan()
        },
        onChangeNhanviengiao(){
        },
        onChangeNhanviennhan(){
        },
        changeNgaygiao(date){
          this.ngaygiao =  moment(this.ngaygiao).format("DD/MM/YYYY HH:mm:ss");
        },
        changeNgayduyet(date){
          this.ngayduyet =  moment(this.ngayduyet).format("DD/MM/YYYY");
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
        Lichsu_Click(index, rowData) {
            this.$refs.lichsugiaophieuModal.showModal();
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
            let ten_hang_kenh = this.dsHangkenh.find(item => item.loaidv_id == this.items.loaidv_id).ten_loaidv
            if(ten_hang_kenh == 'Cộng tác viên'){
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
        async onDeXuatSelected(args) {
            // if (args != null) {
            //     this.grid_DeXuatKenhban.selected = args.data;
            //     this.formData.ma_nv = args.data.ma_nv;
            //     this.items =  this.grid_DeXuatKenhban.selected;
            //     this.dsPhieuGiao()
            //     await this.getDanhsach_DVDexuat();
            //     let ten_nv_dx = this.dsNguoidexuat.find(item => item.ma_nv == args.data.ma_nv).ten_nv
            //     this.ma_nv_dexuat =  ten_nv_dx
            //     this.getDanhsach_FileKenhBan();
            //      this.items.ngay_cap = args.data.ngay_cap?args.data.ngay_cap:''
            //     this.setButton(2);
            //     if(args.data.ten_hangkenh === 'Cộng tác viên'){
            //         this.hangkenhCTV = true;
                    
            //     }else{
            //         this.hangkenhCTV = false;
            //     }
            // }
             if (args != null) {
                console.log('args.data', args.data)
                this.grid_DeXuatKenhban.selected = args.data;
                this.formData.ma_nv = args.data.ma_nv;
                this.items =  this.grid_DeXuatKenhban.selected;
                await this.dsPhieuGiao();
                await this.getDanhsach_Hangkenh();
                //await this.getDanhsach_DVDexuat();
                this.items.donvi_id=args.data.donvi_id
                await this.getDanhsach_NguoiDexuat()
                let ten_nv_dx_item = this.dsNguoidexuat.find(item => item.ma_nv == args.data.ma_nv)
                this.ma_nv_dexuat =ten_nv_dx_item?ten_nv_dx_item.ten_nv:''
                await this.getDanhsach_FileKenhBan();
                 this.items.ngay_cap = args.data.ngay_cap?args.data.ngay_cap:''
                this.setButton(2);
                if(args.data.ten_hangkenh === 'Cộng tác viên'){
                    this.hangkenhCTV = true;
                    
                }else{
                    this.hangkenhCTV = false;
                }
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
            this.items = {diachi:"",ma_nv:"",ma_yc: "",ma_kenh: "",gioitinh: 0,ngay_cap:moment().format('DD/MM/YYYY'),ngaysinh:moment().format('DD/MM/YYYY')}
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
.select2-container .select2-selection--single{
    border: 1px solid #ced4da !important
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