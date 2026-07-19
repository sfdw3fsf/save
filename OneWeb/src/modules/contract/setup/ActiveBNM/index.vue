<template src="./index.html"></template>

<script>
import define from "./define/index.js";
import api from "./api/index.js";
import * as moment from 'moment';
import { L10n, setCulture } from '@syncfusion/ej2-base';
import XLSX from 'xlsx'

import { TreeGridPlugin, Filter, Page, Freeze, Edit, ExcelExport   } from "@syncfusion/ej2-vue-treegrid";
import t from './components/ThongTinTimKiem.vue';
import Vue from "vue";
setCulture('vi');
import { TooltipComponent } from "@syncfusion/ej2-vue-popups";

L10n.load({
    'vi': {
        // 'grids': {
        //     'Item': 'Artikel',
        //     'Items': 'Artikel'
        // },
        'pager': {
            'currentPageInfo': '',
            'totalItemsInfo': 'Bản ghi từ {1} đến số {2} trong tổng số {0}',
            'pagerDropDown': 'Bản ghi trên trang'
        }
    }
});
Vue.use(TreeGridPlugin);
export default {
    provide: {
        treegrid: [Edit, Filter, Page, Freeze, ExcelExport ]
    },
    components: {
        api,
        t,
        "ejs-tooltip": TooltipComponent,
    },
    props: ['tag'],
    created() {
        // Lay gia trị tag truyen tu url
            if (this.$route.query.tag != null && 
            this.$route.params.tag != '' && 
            this.$route.query.tag.length > 0) {
                this.Tag = this.$route.query.tag
            }
            
            // Gan vao theo tham so cua form
            if (this.Tag != null && this.Tag.length > 0) {
                let arr = this.Tag.split('+')
                if (arr.length >= 1) this.ThHdId = parseInt(arr[0])
                if (arr.length >= 2) this.initLoaiHdArr = arr[1].split(',')
                if (arr.length >= 3) this.form.dich_vu_id = arr[2].split(';')
            }
        this.initData();
        // this.handleTag();
        this.getDanhMucChung();
        // this.LayDsBNM();
    },
    data() {
        return {
            ...define,
            checkboxTemplate: function(){
                return {
                    template: t
                }
            },
            filterBarMode: { filterBarMode: "Immediate" },
            currentRows: [],
            selectionOptions: {type: 'Multiple', checkboxMode: 'ResetOnRowClick'},
            formatOptions: {type:'date', format:'dd/MM/yyyy'},
            index: 0,
            editSettings: { allowEditing: true },
            columns: [ 
                { fieldName: 'id' ,headerText: 'ID' ,width: 100, allowFiltering: true, allowResizing: true, freeze:"Left" },
                { fieldName: 'ma_tb' ,headerText: 'Mã TB' ,width: 150 ,allowFiltering: true, freeze: "Left"  },
                { fieldName: 'ma_lt' ,headerText: 'Số ảo' ,width: 150 ,allowFiltering: true, freeze: "Left"  },
                { fieldName: 'loaihinh_tb' ,headerText: 'Loại hình' ,width: 150 ,allowFiltering: true, freeze: "Left"  },
                { fieldName: 'serial_num' ,headerText: 'SERIAL_NUM' ,width: 150 ,allowFiltering: true },
                { fieldName: 'auto_active' ,headerText: 'Tự động kích hoạt' ,width: 150  ,
                    template: function(){
                        return{
                            template: t
                        }
                    } ,
                    allowFiltering: true 
                },
                { fieldName: 'trangthai_tb' ,headerText: 'Trạng thái thuê bao' ,width: 150 ,allowFiltering: true },
                { fieldName: 'loai_td' ,headerText: 'Loại hợp đồng' ,width: 150 ,allowFiltering: true },
                { fieldName: 'loaithaotac' ,headerText: 'Loại thao tác' ,width: 150 ,allowFiltering: true },
                { fieldName: 'thaotac', headerText: 'Thao tác', width: 150, allowFiltering: true },
                { fieldName: 'lenh', headerText: 'Lệnh EWSD', width: 150, allowFiltering: true },
                { fieldName: 'nguoi_kh', headerText: 'Người yêu cầu', width: 150, allowFiltering: true },
                { fieldName: 'ngay_kh', headerText: 'Ngày yêu cầu', width: 150, allowFiltering: true },
                { fieldName: 'ngay_td', headerText: 'Ngày xác nhận tự động', width: 150, allowFiltering: true },
                { fieldName: 'ngay_kh_nc', headerText: 'Ngày xác nhận nhân công', width: 150, allowFiltering: true },
                { fieldName: 'nguoi_kh_nc', headerText: 'Nhân viên xác nhận nhân công', width: 150, allowFiltering: true },
                { fieldName: 'lenh_kh', headerText: 'Lệnh KH', width: 150, allowFiltering: true },
                { fieldName: 'ketqua_kh', headerText: 'Kết quả KH', width: 150, allowFiltering: true },
                { fieldName: 'trangthai_kh', headerText: 'Trạng thái', width: 150, allowFiltering: true },
                { fieldName: 'kieu_kh', headerText: 'Kiểu kích hoạt', width: 150, allowFiltering: true },
                { fieldName: 'thuonghieu', headerText: 'Thương hiệu', width: 150, allowFiltering: true },
                { fieldName: 'loai_tbi', headerText: 'Chủng loại TBI', width: 150, allowFiltering: true },
                { fieldName: 'port', headerText: 'Port', width: 150, allowFiltering: true },
                { fieldName: 'vci', headerText: 'VCI', width: 150, allowFiltering: true },
                { fieldName: 'vpi', headerText: 'VPI', width: 150, allowFiltering: true },
                { fieldName: 'systemid', headerText: 'System', width: 150, allowFiltering: true },
                { fieldName: 'rack', headerText: 'Rack', width: 150, allowFiltering: true },
                { fieldName: 'slot', headerText: 'Slot', width: 150, allowFiltering: true },
                { fieldName: 'ont_id', headerText: 'ONT ID', width: 150, allowFiltering: true },
                { fieldName: 'ip', headerText: 'IP', width: 150, allowFiltering: true },
                { fieldName: 'matkhau', headerText: 'Mật khẩu', width: 150, allowFiltering: true },
                { fieldName: 'frame', headerText: 'Frame', width: 150, allowFiltering: true },
                { fieldName: 'dongmo', headerText: 'Đóng/mở', width: 150, allowFiltering: true },
                { fieldName: 'profiledsl_id', headerText: 'ProfileDSL ID', width: 150, allowFiltering: true },
                { fieldName: 'profilename', headerText: 'Tên Profile', width: 150, allowFiltering: true },
                { fieldName: 'portindex', headerText: 'Port Index', width: 150, allowFiltering: true },
                { fieldName: 'ponid', headerText: 'Poinid', width: 150, allowFiltering: true },
                { fieldName: 'community', headerText: 'Community Switch', width: 150, allowFiltering: true },
                { fieldName: 'brasname', headerText: 'Tên Bras', width: 150, allowFiltering: true },
                { fieldName: 'profile_id', headerText: 'Profile ID', width: 150, allowFiltering: true },
                { fieldName: 'sone', headerText: 'Ne', width: 150, allowFiltering: true },
                { fieldName: 'port', headerText: 'Port mdf', width: 150, allowFiltering: true },
                { fieldName: 'so_con', headerText: 'Số con', width: 150, allowFiltering: true },
                { fieldName: 'ten_dvgt', headerText: 'Dịch vụ GT', width: 150, allowFiltering: true },
                { fieldName: 'ippc', headerText: 'IPPC', width: 150, allowFiltering: true },
                { fieldName: 'host', headerText: 'Host', width: 150, allowFiltering: true },
                { fieldName: 'huongmo', headerText: 'Hướng mở', width: 150, allowFiltering: true },
            ],
            EXCEL_TYPE: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8',
            EXCEL_EXTENSION: '.xlsx',
        }
    },
    methods: {
        beforeRender(args) {
            if (args.target.closest("td")) {
                this.$refs.tooltip.ej2Instances.content = args.target.innerText;
            }
        },
        initData(){
            try {
                this.form.TuNgayDenNgay = moment(new Date()).format("DD/MM/YYYY");
                this.form.DenNgay = moment(new Date()).format("DD/MM/YYYY");
                this.form.Tinh = this.$root.token.getMaTinh();
                this.nhanvien_id = this.$root.token.getNhanVienID();
            } catch (error) {
                this.informError(error);
            }
        },
        changeSelect(name){
            try {
                if(name == 'trang_thai'){
                    // this.form.chkTrangThaiKH = true;
                    this.lueTrangThaiKH_EditValueChanged();
                }
                if(name == 'tinh'){
                    this.form.chkTinh = true;
                    // this.cboTinh_EditValueChanged();
                }
                if(name == 'dich_vu_vt'){
                    this.lueDichVuVT_EditValueChanged();
                }
                if(name == 'loaitbi'){
                    this.form.chkLoaiTBI = true;
                }
                if(name == 'loai_hd'){
                    this.form.chkLoaiHD = true;
                }
                if(name == 'ma_tb'){
                    this.form.MaTB ? this.form.chkMaTB = true : this.form.chkMaTB = false;
                    
                }
                if(name == 'host'){
                    this.form.chkHost = true;
                }
            } catch (error) {
                this.informError(error);
            }
        },
        clickButton(key) {
            if (!this.button[key]) {
                return false;
            }
            if (key == "layTT") {
                this.tsbtnLayTT_Click();
            }
            if (key == "kich_hoat") {
                this.tsbtnKichHoat_Click();
            }
            if (key == "gui_lai_lenh") {
                this.tsbtnGuiLaiLenh_Click();
            }
            if (key == "xuat_file") {
                this.tsbtnXuatFile_Click();
            }
            if (key == "gui_yc_kich_hoat") {
                this.btnGuiYC_Click();
            }
            // if (key == "cap_nhat_serial") {
            //     this.tsbtnCapNhatSerial_Click();
            // }
            // if (key == "cap_nhat_NE") {
            //     this.tsbtnCapNhatNE_Click();
            // }
            if (key == "khoa_cong") {
                this.tsbtnKhoaCong_Click();
            }
            if (key == "mo_cong") {
                this.tsbtnMoCong_Click();
            }
        },
        async getInputKhoaMo(p_ma_tb){
            try {
                if(p_ma_tb){
                    let res = await this.axios.get('/web-ccdv/kichhoat-bnm/sp_khoamoi_may_pon_input', {params: {p_ma_tb: p_ma_tb}});
                    return res.data.data[0];
                    
                } else {
                    this.$toast.warning("Nhập mã thuê bao cần khóa/mở máy");
                }
            } catch (error) {
                
            }
        },
        async tsbtnKhoaCong_Click(){
            try {
                this.$root.showLoading(true);;
                console.log(this.$refs.grid.getSelectedRecords());
                let ds = this.$refs.grid.getSelectedRecords();
                if(ds.length > 0){
                    for(let i = 0; i < ds.length; i++){
                        if(ds[i].ma_tb){
                            let body = await this.getInputKhoaMo(ds[i].ma_tb);
                            // call api khóa Cổng
                            this.axios.post('/tichhop/bnm/khoaMay_PON', body);
                        }
                    }
                    this.$root.showLoading(false);;
                    this.$toast.success('Cập nhật thành công.')
                } else {
                    this.$root.showLoading(false);;
                    this.$toast.warning('Bạn chưa chọn dữ liệu kích hoạt')
                }
                
            } catch (error) {
                this.$root.showLoading(false);;
                this.informError(error);
            }
        },
        async tsbtnMoCong_Click(){
            try {
                console.log(this.$refs.grid.getSelectedRecords());
                let ds = this.$refs.grid.getSelectedRecords();
                if(ds.length > 0){
                    for(let i = 0; i < ds.length; i++){
                        if(ds[i].ma_tb){
                            let body = await this.getInputKhoaMo(ds[i].ma_tb);
                            // call api khóa Cổng
                            this.axios.post('/tichhop/bnm/moMay_PON', body);
                        }
                    }
                    this.$toast.success('Cập nhật thành công.')
                } else {
                    this.$toast.warning('Bạn chưa chọn dữ liệu kích hoạt')
                }
            } catch (error) {
                this.informError(error);
            }
        },
        async tsbtnKichHoat_Click(){
            try {
                let ls_id = [], ls_loai = [];
                let ds = this.$refs.grid.getSelectedRecords();
                if(ds.length == 0){
                    this.$toast.warning("Chọn dữ liệu kích hoạt nhân công");
                    return;
                }
                ds.forEach(e => {
                    ls_id.push(e.id);
                    ls_loai.push(e.br);
                });
                // console.log(ls_id.join(';'), ls_loai.join(';'));
                // call api kich kich_hoat
                await this.axios.post('/web-ccdv/kichhoat-bnm/capnhat_kh_nc', {
                    vphanvung_id: this.phanvung_id,
                    vid: ls_id.join(';'),
                    vloai: ls_loai.join(';'),
                    vnhanvien_id: this.nhanvien_id
                });
                this.$toast.success("Đã xác nhận kích hoạt nhân công")
                // call api load ds 
                await this.tsbtnLayTT_Click();
            } catch (error) {
                this.informError(error);
            }
        },
        async tsbtnGuiLaiLenh_Click(){
            try {
                let ls_id = [], ls_loai = [];
                let ds = this.$refs.grid.getSelectedRecords();
                if(ds.length == 0){
                    this.$toast.warning("Chọn dữ liệu gửi lệnh");
                    return;
                }
                ls_id = ds.map(e => e.id).join(";");
                ls_loai = ds.map(e => e.br).join(";")
                console.log(ds, ls_id, ls_loai);
                let res = await this.axios.post('/web-ccdv/kichhoat-bnm/sp_gui_lai_lenh_bnm', {
                    vphanvung_id: this.phanvung_id,
                    vid: ls_id,
                    vloai: ls_loai,
                    vnhanvien_id: this.nhanvien_id
                });
                if(res.data.error == '200' && res.data.error_code == 'BSS-00000000'){
                    this.$toast.success("Đã xác nhận kích hoạt nhân công")
                }else{
                    this.$toast.warning("Bạn chưa chọn bản ghi cần gửi lại lệnh BNM hoặc các bản ghi được chọn đã kích hoạt hoặc chưa kích hoạt.")
                }
                // call api load ds
                await this.tsbtnLayTT_Click();
            } catch (error) {
                this.informError(error);
            }
        },
        tsbtnXuatFile_Click(){
            try {
                if (!this.options.hopdong.length || !this.currentRows.length) {
                    this.$root.toastError("Không có dữ liệu để xuất Excel!")
                    return
                }
                var myFile = "myFile.xlsx";
                var myWorkSheet = XLSX.utils.json_to_sheet(this.currentRows);
                var myWorkBook = XLSX.utils.book_new();
                XLSX.utils.book_append_sheet(myWorkBook, myWorkSheet, "myWorkSheet");
                XLSX.writeFile(myWorkBook, myFile);
                // this.$toast.warning("Chức năng đang phát triển sẽ bổ sung sau !!");
            } catch (error) {
                this.informError(error);
            }
        },
        async tsbtnLayTT_Click(){
            try {
                this.$root.showLoading(true);
                let vma_tb = null, vtungay = null, vdenngay = null, vma_tinh = "";
                let vlenhxn = 0, vtrangthai_kh = -2, vloaihd_id = 0, vdichvuvt_id = 0, vhost_id = 0, vloaitbi_id = 0;
                if (this.form.chkLenhXN)
                {
                    vlenhxn = this.form.radioBtn
                }
                if (this.form.chkTrangThaiKH)
                {
                    // alert(this.form.TrangThaiKH)
                    vtrangthai_kh = Number(this.form.TrangThaiKH);
                }

                if (this.form.chkLoaiHD)
                {
                    vloaihd_id = Number(this.form.LoaiHD);
                }

                if (this.form.chkTuNgayDenNgay)
                {
                    vtungay = this.form.TuNgayDenNgay;
                }

                if (this.form.chkDenNgay)
                {
                    vdenngay = this.form.DenNgay;
                }

                if (this.form.chkMaTB)
                {
                    vma_tb = this.form.MaTB;
                }

                // if(this.form.chkTinh){
                    vma_tinh = this.form.Tinh;
                // }

                if(this.form.chkDichVuVT){
                    vdichvuvt_id = Number(this.form.DichVuVT)
                }

                if (this.form.chkHost)
                {
                    vhost_id = Number(this.form.Host);
                }

                if (this.form.chkLoaiTBI)
                {
                    vloaitbi_id = Number(this.form.LoaiTBI);
                }
                await this.LayDsBNM(vlenhxn, vtrangthai_kh, vloaihd_id, 
                        vtungay, vdenngay, vma_tb, vdichvuvt_id, vhost_id, 
                        vloaitbi_id, vma_tinh);
                this.$root.showLoading(false); 
            } catch (error) {
                this.$root.showLoading(false); 
                this.informError(error);
            }
        },

        btnGuiYC_Click(){
            try {
                this.$root.showLoading(true); 
                console.log(this.$refs.grid.getSelectedRecords());
                let ds = this.$refs.grid.getSelectedRecords();
                if(ds.length > 0){
                    for(let i = 0; i < ds.length; i++){
                        this.axios.post('/web-ccdv/kichhoat-bnm/GUI_YC_KH_BNM', {
                            vnguoi_kh_nc: this.nhanvien_id,
                            vid: ds[i].id
                        });
                    }
                    this.$toast.success('Cập nhật thành công.');
                    this.tsbtnLayTT_Click();
                    this.$root.showLoading(false); 
                } else {
                    this.$root.showLoading(false); 
                    this.$toast.warning('Bạn chưa chọn dữ liệu kích hoạt')
                }
            } catch (error) {
                this.$root.showLoading(false); 
                this.informError(error);
            }
        },
        handleTag(){
            // Lay gia trị tag truyen tu url
            if (this.$route.query.tag != null && 
            this.$route.params.tag != '' && 
            this.$route.query.tag.length > 0) {
                this.Tag = this.$route.query.tag
            }
            
            // Gan vao theo tham so cua form
            if (this.Tag != null && this.Tag.length > 0) {
                let arr = this.Tag.split('+')
                if (arr.length >= 1) this.ThHdId = parseInt(arr[0])
                if (arr.length >= 2) this.initLoaiHdArr = arr[1].split(',')
                if (arr.length >= 3) this.form.dich_vu_id = arr[2].split(';')
            }
        },
        async getDanhMucChung(){
            try {
                this.$root.showLoading(true);
                let res = await this.axios.post('/web-ccdv/kichhoat-bnm/SP_LAY_DS_DANHMUC_CHUNG_KH_BNM');
                this.cbTrangThai = [...res.data.data['p_trangthai_kh']];
                this.cbTinh = [...res.data.data['p_ds_tinh']];
                this.cbDichVu = [...res.data.data['p_ds_dichvu_vt']];
                this.cbLoaiTBI = [...res.data.data['p_ds_ds_loai_tbi']];
                this.cbLoaiHD = [...res.data.data['p_loai_hd']];
                this.cbHost = [...res.data.data['p_ds_ds_host']];
                this.$root.showLoading(false);
            } catch (error) {
                this.$root.showLoading(false); 
                this.informError(error);
            }
        },
        async LayDsBNM(vlenhxn, vtrangthai_kh, vloaihd_id, 
                        vtungay, vdenngay, vma_tb, vdichvuvt_id, vhost_id, 
                        vloaitbi_id, vma_tinh){
            try {
                this.$root.showLoading(true);;
                let res = await this.axios.post('/web-ccdv/kichhoat-bnm/sp_lay_ds_bnm', {
                    vphanvung_id: this.phanvung_id,
                    vlenhxn: vlenhxn,
                    vtrangthai_kh: vtrangthai_kh,
                    vloaihd_id: vloaihd_id,
                    vtungay: vtungay,
                    vdenngay: vdenngay,
                    vma_tb: vma_tb,
                    vdichvuvt_id: vdichvuvt_id,
                    vhost_id: vhost_id,
                    vloaitbi_id: vloaitbi_id,
                    vma_tinh : vma_tinh
                    
                    // vphanvung_id: 26,
                    // vlenhxn: 0,
                    // vtrangthai_kh: 0,
                    // vloaihd_id: null,
                    // vtungay: null,
                    // vdenngay: null,
                    // vma_tb: null,
                    // vdichvuvt_id: 4,
                    // vhost_id: null,
                    // vloaitbi_id: null,
                    // vma_tinh : 'HPG'
                });
                this.options.hopdong = [...res.data.data];
                // [
                //     {
                //         "lenh": null,
                //         "ngay_td": "2022-10-24 11:03:08",
                //         "systemid": "LCU.TDG.ALU1",
                //         "kh_nc": 0,
                //         "kieu_kh": "Kích hoạt tự động",
                //         "kh_td": 1,
                //         "brasname": null,
                //         "slot": "01",
                //         "ketqua_kh": "Đã gửi lênh. Đang chờ kết quả. Da_Gui_Lenh_Thanh_CongBegin",
                //         "br": 1,
                //         "nguoi_kh": "Vũ Đức Long",
                //         "dongmo": null,
                //         "matkhau": "lcu123",
                //         "ngay_kh": "2022-10-24 11:32:51",
                //         "ma_tb": "vnptnthoa92_f_td",
                //         "loai_td": "Lắp đặt mới",
                //         "profilename": null,
                //         "nguoi_kh_nc": null,
                //         "host": null,
                //         "so_con": null,
                //         "thuonghieu": "Home 1 - Ip Động_2021",
                //         "loaithaotac": null,
                //         "id": 126721,
                //         "ten_dvgt": null,
                //         "vci": "11",
                //         "selected": 0,
                //         "thaotac": null,
                //         "rack": "1",
                //         "trangthai": "Đã kích hoạt",
                //         "huongmo": null,
                //         "ngay_kh_nc": null,
                //         "sone": null,
                //         "ip": null,
                //         "loai_tbi": "Olt Gpon",
                //         "profiledsl_id": null,
                //         "trangthai_tb": "Đã giao thi công",
                //         "ippc": null,
                //         "community": null,
                //         "vpi": "9",
                //         "shelf": "1",
                //         "ponid": null,
                //         "port": "11",
                //         "profile_id": 500000110,
                //         "loaihinh_tb": "Fiber",
                //         "portindex": null,
                //         "trangthai_kh": 3,
                //         "ma_lt": "131051205623",
                //         "frame": null,
                //         "taskData": {
                //             "lenh": null,
                //             "ngay_td": "2022-10-24 11:03:08",
                //             "systemid": "LCU.TDG.ALU1",
                //             "kh_nc": 0,
                //             "kieu_kh": "Kích hoạt tự động",
                //             "kh_td": 1,
                //             "brasname": null,
                //             "slot": "01",
                //             "ketqua_kh": "Đã gửi lênh. Đang chờ kết quả. Da_Gui_Lenh_Thanh_CongBegin",
                //             "br": 1,
                //             "nguoi_kh": "Vũ Đức Long",
                //             "dongmo": null,
                //             "matkhau": "lcu123",
                //             "ngay_kh": "2022-10-24 11:32:51",
                //             "ma_tb": "vnptnthoa92_f_td",
                //             "loai_td": "Lắp đặt mới",
                //             "profilename": null,
                //             "nguoi_kh_nc": null,
                //             "host": null,
                //             "so_con": null,
                //             "thuonghieu": "Home 1 - Ip Động_2021",
                //             "loaithaotac": null,
                //             "id": 126721,
                //             "ten_dvgt": null,
                //             "vci": "11",
                //             "selected": 0,
                //             "thaotac": null,
                //             "rack": "1",
                //             "trangthai": "Đã kích hoạt",
                //             "huongmo": null,
                //             "ngay_kh_nc": null,
                //             "sone": null,
                //             "ip": null,
                //             "loai_tbi": "Olt Gpon",
                //             "profiledsl_id": null,
                //             "trangthai_tb": "Đã giao thi công",
                //             "ippc": null,
                //             "community": null,
                //             "vpi": "9",
                //             "shelf": "1",
                //             "ponid": null,
                //             "port": "11",
                //             "profile_id": 500000110,
                //             "loaihinh_tb": "Fiber",
                //             "portindex": null,
                //             "trangthai_kh": 3,
                //             "ma_lt": "131051205623",
                //             "frame": null
                //         },
                //         "index": 0,
                //         "uniqueID": "_data_47",
                //         "level": 0,
                //         "checkboxState": "uncheck"
                //     }
                // ]
                //[...res.data.data];
                this.$root.showLoading(false);;
            } catch (error) {
                this.informError(error);
                this.$root.showLoading(false);;
            }
        },
        chkLenhXN_CheckedChanged(){
            try {
                if (this.form.TrangThaiKH != null && 
                (this.form.TrangThaiKH == -1 || this.form.TrangThaiKH == 0))
                {
                    this.form.chkLenhXN = false;
                    return;
                }
                if (!this.form.chkLenhXN)
                {
                    // rbNhanCong.Checked = false;
                    // rbTuDong.Checked = false;
                    this.form.radioBtn = null;
                }
            } catch (error) {
                this.informError(error)
            }
        },
        rb_CheckedChanged(){
            try {
                // alert(this.form.radioBtnTD)
                if (this.form.TrangThaiKH != null && 
                (this.form.TrangThaiKH == -1 || this.form.TrangThaiKH == 0))
                {
                    this.form.radioBtn = 0;
                    return;
                }
                if (this.form.radioBtn)
                    this.form.chkLenhXN = true;
            } catch (error) {
                this.informError(error)
            }
        },
        lueTrangThaiKH_EditValueChanged(){
            try {
                // alert(this.form.TrangThaiKH)
                this.form.chkTrangThaiKH = true;
                if (this.form.TrangThaiKH != null && 
                (this.form.TrangThaiKH == -1 || this.form.TrangThaiKH == 0))
                {
                    this.form.chkLenhXN = false;
                    this.form.radioBtnTD = false;
                    this.form.radioBtnNC = false;
                }
            } catch (error) {
                this.informError(error)
            }
        },
        lueDichVuVT_EditValueChanged(){
            try {
                this.form.chkDichVuVT = true;
                    // neu dich vu la CO DINH HOAC IMS thi disable loai thiet bi
                    if(this.form.DichVuVT == 1 || this.form.DichVuVT == 11){
                        // set host
                        this.form.chkHost = false;
                        this.form.disHost = true;

                        // set loai tbi
                        this.form.chkLoaiTBI = false;
                        this.form.disLoaiTBI = false;
                        this.form.LoaiTBI = null;
                    }

                    // neu dich vu la BANG RONG CO DINH THI DISABLE HOST
                    if(this.form.DichVuVT == 4){
                        // set host
                        this.form.chkHost = false;
                        this.form.disHost = false;
                        this.form.Host = null;

                        // set loai tbi
                        this.form.chkLoaiTBI = false;
                        this.form.disLoaiTBI = true;
                    }
            } catch (error) {
                this.informError(error)
            }
        },
        async cboTinh_EditValueChanged(){
            try {
                // await this.LayDsBNM();
                // this.vma_tinh = await this.MAP_ID('tentat', 'css.tinh', "where tinh_id = " + this.$root.token.getPhanVungID());
            } catch (error) {
                this.informError(error);
            }
        },
        
        getCurrentRow(e){
            try {
                let ds = [];
                console.log(e);
                if(e && e.length){
                    this.currentRows = [...e];
                }
                // if(e.isHeaderCheckboxClicked){
                //     let ds = [...e.data];
                //     console.log(e);
                // }else{
                //     ds.push()
                // }
            } catch (error) {
                this.informError(error);
            }
        },
        
        informError(error){
            this.$toast.error("Đã có lỗi xảy ra !!");
            console.log(error);
        }
    },
    mounted() {
        // console.log(this.$root.token.getMaTinh());
    }
}
</script>

<style>
.list a {
    cursor: pointer;
}
.e-treegrid td.e-selectionbackground {
    background-color: #F9E1A9!important;
}
.e-treegrid tr:hover {
    background-color: #FFF9EB !important;
}
.e-checkbox-wrapper .e-check::before, .e-css.e-checkbox-wrapper .e-check::before {
    content: "\e718";
    background-color: transparent;
    line-height: 16px;
}
.e-checkbox-wrapper .e-stop::before, .e-css.e-checkbox-wrapper .e-stop::before {
    content:  "\e7a5" !important
}
.e-treegrid .e-altrow {
    background-color: #5a1e1e;
}
</style>