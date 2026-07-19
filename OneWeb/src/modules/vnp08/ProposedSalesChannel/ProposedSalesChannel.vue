
<template src="./ProposedSalesChannel.html"></template>
<style scoped src="./ProposedSalesChannel.scss"></style>
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
//import ModalChonDiaChi from './popup/ChonDiaChi/ModalChonDiaChi.vue' 
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import api from "./api/index.js"; 
import Lichsuphieugiao from "./popup/Lichsuphieugiao/Lichsuphieugiao.vue";
export default {
    components: {
        appCombobox: ComboboxGrid,
        Giaophieu,
        HosoFile,
        ModalChonDiaChi,
        CapnhatFile,
        Lichsuphieugiao
    },
    name: 'ProposedSalesChannel', 
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
                ma_tb: '',
                ma_nv: ''
            },
            gioitinh: "",
            thamsomacdinh: {},
            items: {
                diachi: "",
                ma_nvql:"",
                dt_ban_id: 0,
                donvi_id: null,  
                ma_kenh: "",
                ten_kenh: "",  
                fax: "",
                loaigt_id: 0,
                loaidv_id: 0,
                nguoi_dd: "", 
                ngaysinh: moment().format('DD/MM/YYYY'), 
                ngay_cap: null, 
                gioitinh: 0
            },
            header: {
                title: "Đề xuất ký hợp đồng kênh bán",
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
                nhapMoi: true,
                ghiLai: false,
                xoa: false,
                huy: false,
                giaophieu: false,
                filehoso: false,
                duyet: false,
                lsgiaophieu: false
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
            isthemmoi: false,
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
                pattern: /^[0-9]+$/,
                maxLength: maxLength(13),
                minLength: minLength(10),
        
            },
        },
    },
     mounted() {
         this.getDanhsach_Doituongban();
         this.getDanhsach_DexuatHDKenh();
         this.getDanhsach_DVDexuat();
         this.getDanhsach_Hangkenh();
         this.getDanhsach_HTHD();
         this.getDanhsach_LoaiGT()
         this.getDanhsach_NguoiDexuat();
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
            this.items.loaidv_id = data[0].loaidv_id;
            this.ten_hang_kenh = data[0].ten_loaidv;
        },
        //Lấy danh sách đơn vị đề xuất
        async getDanhsach_DVDexuat(){
            let data = this.GetData(await api.dsDonViDeXuat(this.axios));
            this.dsDVDexuat = this.list2treeNew(this.LowerCasePropertyList(data));
            if(this.grid_DeXuatKenhban.items.length == 0){
                this.items.donvi_id = data[0].donvi_id;
                await this.getDanhsach_NguoiDexuat()
            } 
        },
        //Lấy danh sách kênh bán
        async getDanhsach_DexuatHDKenh(){
             this.loading(true)
            let data = this.GetDataList(await api.dsDeXuatHopDongKenh(this.axios));
            this.grid_DeXuatKenhban.items = data;
            if(this.grid_DeXuatKenhban.items.length == 0){
                 this.setButton(0);
            }
             this.loading(false)
        }, 
        async getDanhsach_LoaiGT(){
            let data = this.GetDataList(await api.lay_ds_loaiGT(this.axios));
            this.dsLoaiGT = data;
            this.items.loaigt_id = data[0].loaigt_id;
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
        list2treeNew(list) {
            let rootList = list.filter(x => (x.donvi_cha_id == null));
            let openList = [];
            rootList.forEach(item => {
                item.id = item.donvi_id;
                item.label = item.ten_dv ? item.ten_dv : "Null";
                item.level = 0;
                openList.push(item);
                let children = list.filter(x => x.donvi_cha_id == item.donvi_id);
                if (children.length > 0) {
                    item.children = children;
                    children.forEach(childItem => {
                        childItem.level = item.level + 1;
                        childItem.id = childItem.donvi_id;
                        childItem.label = childItem.ten_dv ? childItem.ten_dv : "Null";
                        openList.push(childItem);
                        let grandchildren = list.filter(x => x.donvi_cha_id == childItem.donvi_id);
                        if (grandchildren.length > 0) {
                            childItem.children = grandchildren;
                            grandchildren.forEach(grandchildItem => {
                                grandchildItem.level = childItem.level + 1;
                                grandchildItem.id = grandchildItem.donvi_id;
                                grandchildItem.label = grandchildItem.ten_dv ? grandchildItem.ten_dv : "Null";
                                openList.push(grandchildItem);
                                let greatGrandchildren = list.filter(x => x.donvi_cha_id == grandchildItem.donvi_id);
                                if (greatGrandchildren.length > 0) {
                                    grandchildItem.children = greatGrandchildren;
                                    greatGrandchildren.forEach(greatGrandchildItem => {
                                        greatGrandchildItem.level = grandchildItem.level + 1;
                                        greatGrandchildItem.id = greatGrandchildItem.donvi_id;
                                        greatGrandchildItem.label = greatGrandchildItem.ten_dv ? greatGrandchildItem.ten_dv : "Null";
                                        openList.push(greatGrandchildItem);
                                    });
                                }
                            });
                        }
                    });
                }
            });
            let rootList_new = openList.filter(x => (x.donvi_cha_id == null && x.level < 4));
            return rootList_new;
        },

        // list2treeNew(list) {
        //     let rootList = list.filter(x => (x.donvi_cha_id == null));
        //     let openList  = [];
        //     rootList.forEach(item => {
        //         item.id = item.donvi_id;
        //         item.label = item.ten_dv ? item.ten_dv : "Null";
        //         item.level = 0;
        //         openList.push(item);
        //         let children = list.filter(x => x.donvi_cha_id == item.donvi_id);
        //         if(children.length > 0){
        //             item.children = children;
        //             children.forEach(childItem => {
        //                 childItem.level = item.level + 1;
        //                 childItem.id = childItem.donvi_id
        //                 childItem.label = childItem.ten_dv ? childItem.ten_dv : "Null"
        //                 openList.push(childItem);
        //             });

        //         }
        //     });
        //     let rootList_new = openList.filter(x => (x.donvi_cha_id == null));
        //     return rootList_new;
        // },
        LowerCasePropertyList(obj) {
            if(!obj) return [];
            return obj.map( function( item ){
                for(var key in item){
                    var upper = key.toLowerCase();
                    // check if it already wasn't uppercase
                    if( upper !== key ){
                        item[ upper ] = item[key];
                        delete item[key];
                    }
                }
                return item;
            });
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
            this.ma_nv_dexuat = rowData.ten_nv ;
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
            //this.items.loaidv_id = value;
            //let ten_hang_kenh = this.dsHangkenh.find(item => item.loaidv_id == value).ten_loaidv
            this.items.hangkenh_id = value;
            let ten_hang_kenh = this.dsHangkenh.find(item => item.hangkenh_id == value).ten_loaidv
            this.getDanhsach_LoaiGT();
            this.getSinhMaKenh(ten_hang_kenh);
            if(ten_hang_kenh == 'Cộng tác viên'){
                this.hangkenhCTV = true;
                
            }else{
                this.hangkenhCTV = false;
            }
        },
        changeNgaycap(event){

            if(!event.value){
                this.items.ngay_cap=null
                retur
            }
            this.items.ngay_cap=moment(event.value).format('DD/MM/YYYY')
        },
        onDVDexuat(item){
            this.ma_nv_dexuat=''
            this.items.nhanvien_id = 0
            this.items.donvi_id = item.donvi_id
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
    // async KiemTraMST() {
    //   if (this.items.mst_cccd && this.items.mst_cccd.trim() != '') {
    //     var api6 = api.post_lay_thamso_md(this.axios, { kieu_id: 0 }).then((response) => {
    //     if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
    //         this.thamsomacdinh = response.data.data.reduce(function(result, item, index, array) {
    //             result[item['ma_ts']] = item['ten_ts'] //a, b, c
    //             return result
    //         }, {}) //.map(x => ({ [x['ma_ts']]:x['ten_ts'] }));
    //         }
    //     });
    //     if (this.thamsomacdinh['CHAN_TRUNG_SGT'] && this.thamsomacdinh['CHAN_TRUNG_SGT'] == 1) {
    //       let checkMST = await api.post_kiemtra_khachhang_theo_mst(this.axios, {
    //         vmasothue: this.items.mst_cccd,
    //         vhdkh_id: this.khachhang.hdkh_id,
    //         vloaihd_id: LoaiHopDong.DAT_MOI,
    //         vma_kh: this.khachhang.makh
    //       }).then((response) => {
    //         if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
    //           return response.data.data
    //         } else return ''
    //       })
    //       if (checkMST && checkMST != 1) {
    //         this.$toast.error(checkMST)
    //         this.items.mst_cccd = ''
    //         return
    //       }
    //     }
    //     this.Laythongtin_kh_tiemnang();
    //   }
    // },
    // async Laythongtin_kh_tiemnang() {
    //   //https://cntt.vnpt.vn/browse/VNPSR-10681
    //   //Với KHDN, lấy ra thông tin khách hàng trên hệ thống KHDN để auto điền vào form
    //   try
    //   {
    //       //Nếu là KHDN thì mới xử lý
    //       if (!this.items.mst_cccd || this.items.mst_cccd.trim()=="") return;
    //       //Nếu là KH doanh nghiệp thì sẽ lấy thông tin từ hệ thống KHDN
    //       if (this.items.dt_ban_id != 2) return; //Khách hàng doanh nghiệp
    //       // var client = new RestClient();
    //       api.post_search_khtn(this.axios,{mst:this.items.mst_cccd}).then((response) => {
    //         if (response && response.data && response.data.data && response.data.errorCode && response.data.errorCode === '0') {
    //           var rss = JSON.parse(response.data.data);
    //           this.items.ten_kenh = rss["result"][0]["FULL_NAME"];
    //           this.items.diachi = rss["result"][0]["ADDR"];
    //           this.items.nguoi_dd = rss["result"][0]["DD_NAME"];
    //           this.items.so_dt = rss["result"][0]["PHONE"];
    //         } else if (response.data.faultString) this.$toast.error(response.data.faultString);
    //       })
    //   }
    //   catch (ex)
    //   {
    //       this.$toast.error("Không lấy được thông tin từ hệ thống Khách hàng tiềm năng !");
    //   }
    // },
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
            } else if (!/^\d{10,13}$/.test(this.$v.items.mst_cccd.$model)) {
                errorMessage.push("Mã số thuế phải từ 10 đến 13 ký tự số!");
                focusInput.push('mst_cccd');
            }
            else if (!/^[0-9]+$/.test(this.$v.items.mst_cccd.$model)) {
                errorMessage.push("Chỉ được nhập số cho mã số thuế!");
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
             this.isthemmoi = true;
            await this.getSinhMaPhieu();
            await this.getSinhMaKenh(this.ten_hang_kenh);
            
        },
        Lichsu_Click(index, rowData) {
            this.$refs.lichsugiaophieuModal.showModal();
        },
        //Ghi lại thông tin kênh bán
        async onGhiLai() {
            //console.log(this.items.ngay_cap)
            //let formattedDate = this.items.ngay_cap==null?"":moment(this.items.ngay_cap).format("DD/MM/YYYY");
            
            //this.items.ngay_cap = formattedDate;
            if (this.validateData()) {
                if (this.isthemmoi) {
                    let rs = await this.themMoiDeXuatHopDong();
                } else {
                    let rs = await this.capNhatDeXuatHopDong();
                }
            }
        },
        //Hủy thực hiện
        onHuy() {
             this.setButton(1);
        }, 
        //Click chọn kênh bán
       async onDeXuatSelected(args) {  
         try {
                this.ma_nv_dexuat=''
                this.items = args.data;
                this.grid_DeXuatKenhban.selected = args.data;
                this.formData.ma_nv = args.data.ma_nv;
                //await  this.getDanhsach_LoaiGT();
                await  this.getDanhsach_Hangkenh();
                // await this.getDanhsach_DVDexuat();
                 
                this.items = args.data;
                await this.getDanhsach_NguoiDexuat()
                //let ten_nv_dx = this.dsNguoidexuat.find(item => item.ma_nv == args.data.ma_nv).ten_nv
                //this.ma_nv_dexuat =  ten_nv_dx
                var check_manv = this.dsNguoidexuat.find(item => item.ma_nv == args.data.ma_nv)                
                if (!check_manv) {
                    this.$toast.error(`Mã nhân viên ${args.data.ma_nv} không nằm trong danh sách nhân viên đề xuất của đơn vị, bạn hãy liên hệ admin để xử lý!`)
                } else {
                    let ten_nv_dx = check_manv.ten_nv
                    this.ma_nv_dexuat =  ten_nv_dx
                }         
                this.getDanhsach_FileKenhBan();
                 this.items.ngay_cap = args.data.ngay_cap?args.data.ngay_cap:''
                this.setButton(2);
                console.log(this.items.hdkenh_id)
                //this.btn_Enabled.nhapMoi = false  
                if(args.data.ten_hangkenh == 'Cộng tác viên'){
                    this.hangkenhCTV = true; 
                }else{ 
                    this.hangkenhCTV = false;
                }
            } catch (e) {
	
                this.$toast.error('Lỗi ' + JSON.stringify(e))

                console.log('onDeXuatSelected', e)
	
            } finally {}
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
                filehoso: false,
                giaophieu: false,
                duyet: false,
                lsgiaophieu: false
            };
            if (kieu === -1) { //Bat dau  
                this.btn_Enabled.ghiLai = true;
                this.btn_Enabled.huy = true;
                this.btn_Enabled.nhapMoi = true;
                this.btn_Enabled.xoa = true;
                this.btn_Enabled.giaophieu = true;
                this.btn_Enabled.filehoso = true;
                this.btn_Enabled.lsgiaophieu = true
            } else if (kieu === 0) { //Không có bản ghi 
                this.clear(); 
                this.btn_Enabled.ghiLai = false; 
                this.btn_Enabled.huy = true; 
                this.btn_Enabled.nhapMoi = true; 
                this.btn_Enabled.giaophieu = false;
                this.btn_Enabled.filehoso = false;
                this.btn_Enabled.lsgiaophieu = false
                this.btn_Enabled.xoa = false;
            } else if (kieu === 1) { //Them moi 
                this.clear();
                this.btn_Enabled.ghiLai = true; 
                this.btn_Enabled.huy = true; 
                this.btn_Enabled.nhapMoi = false; 
                this.btn_Enabled.giaophieu = false;
                this.btn_Enabled.filehoso = false;
                this.btn_Enabled.lsgiaophieu = false
                this.btn_Enabled.xoa = true;
            } else if (kieu === 2) { //Cap nhat
                this.isthemmoi = false;
                this.btn_Enabled.nhapMoi = true;
                this.btn_Enabled.ghiLai = true;
                this.btn_Enabled.huy = true;
                this.btn_Enabled.xoa = true;
                this.btn_Enabled.filehoso = true;
                this.btn_Enabled.giaophieu = true;
                this.btn_Enabled.lsgiaophieu = true
                this.btn_Enabled.xoa = true;
            }
        },
        clear() {
            this.ma_nv_dexuat = "";
            this.dsFileKenhBan = []
            this.diachi = {}
            this.items = {
                hdkenh_id: 0,diachi:"",ma_nv:"",ma_yc: "",ma_kenh: "",
                gioitinh: 0,ngay_cap:null,ngaysinh:moment().format('DD/MM/YYYY'),
                diachi: "",phuong_id: "",quan_id:"",ap_id: "",pho_id:"",diachimoi: "",tinh_id:""
            }
            this.getDanhsach_Doituongban()
            this.getDanhsach_DVDexuat()
            this.getDanhsach_LoaiGT()
            this.getDanhsach_Hangkenh()
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
                        return;
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
                        return;
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