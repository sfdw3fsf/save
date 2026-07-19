<template src="./ListInvestmentContracts.html"></template>
<style scoped src="./ListInvestmentContracts.scss"></style>

<script>
    import '@/assets/vendor/jquery/split'
    import breadcrumb from "@/components/breadcrumb"
    import DataGrid from "@/components/Controls/DataGrid"
    import SelectExt from "@/components/Controls/SelectExt"
    import api from './ListInvestmentContractsAPI'
    import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
    import moment from 'moment';
    import VueNumeric from 'vue-numeric'
    import Auth from "@/plugins/auth"
    import Calculator from '@/components/Calculator'
    export default {
        components: {
            breadcrumb,
            DataGrid,
            SelectExt,
            api,
            KDatePicker,
            VueNumeric,
            Calculator,
        },
        name: 'ListInvestmentContracts',
        mounted () {
            Split(['#boxLeft', '#boxRight'], {
                sizes: [65, 35],
                gutterSize: 16,
                onDragEnd: function (sizes) {
                },
            });
        },
        data() {
            return {
                isCalculatorShow:false,
                isNhapMoi:false,
                isGhiLai:true,
                isHuy:true,
                isXoa:false,
                isChiTiet:false,
                isTienTrinh:false,
                hddtu_id: 0,
                ma_hd: '',
                ten_hd: '',
                so_hd_giay: '',
                ds_nganhang: [],
                ds_toanha:[],
                ds_nv_doisoat: [],
                ds_nv_quanly: [],
                ds_nhom_duan: [],
                url_downfile: '#',
                ds_chitiet: 0,
                ds_tientrinh: 0,
                selectHTDT: 0,
                selectHTPCDT: 0,
                selectHTHD: 0,
                selectTinhTrang: 1,
                nguoi_ky: '',
                chuc_vu: '',
                tien_do: '',
                txtNgayTT: '',
                soTaiKhoan: '',
                maSoThue: '',
                ten_upFile: '',
                ten_dowFile: '',
                isUploaded: false,
                ghi_chu: '',
                tongmuc_dt: 0,
                thiphan_tt: 0,
                apru: 0,
                giatri_ts: 0,
                tile_pcdt: 0,
                txtNgayKy: '',//moment(new Date()).subtract(5, 'days').format('DD/MM/YYYY'),
                txtThoiHan: '',
                txtTGT_HQDT: '',
                page_num: 1,
                page_sz: 100000000,
                totalRecords: 1,
                params: {
                    nganhang_id: -1,
                    toanha_id: -1,
                    nhanvien_ds_id: -1,
                    nhanvien_ql_id: -1,
                    nhom_da_id: -1,
                },
                ds_hopdong: {
                    list: [],
                    header: [
                        { fieldName: "ma_hddtu", headerText: "Mã HĐ", allowFiltering: true,},
                        { fieldName: "ten_hddtu", headerText: "Tên HĐ", allowFiltering: true,},
                        { fieldName: "so_hd", headerText: "Số HĐ", allowFiltering: true,},
                        { fieldName: "ngayky", headerText: "Ngày ký", allowFiltering: true, type: "Date", format: "dd/MM/yyyy",},
                        { fieldName: "thoihan", headerText: "Thời hạn", allowFiltering: true, type: "Date", format: "dd/MM/yyyy"},
                        { fieldName: "tiendo", headerText: "Tiến độ", allowFiltering: true,},
                        { fieldName: "ten_toanha", headerText: "Tòa nhà", allowFiltering: true,},
                        { fieldName: "ten_nhomda", headerText: "Nhóm dự án", allowFiltering: true,},
                        { fieldName: "tongmuc_dtu", headerText: "Tổng mức đtư", allowFiltering: true,},
                        { fieldName: "tg_hqdt", headerText: "TG tính HQĐT", allowFiltering: true, type: "Date", format: "dd/MM/yyyy"},
                        { fieldName: "thiphan_toithieu", headerText: "Thị phần", allowFiltering: true,},
                    ],
                    value: {},
                    isEnabled: true,
                    checked: [],
                },
                index: 0,
                popupTitle: "",
                popupComponent: null,
                popupComponentData: null,
                popupComponentName: '',
                popupComponentEvents: {
                    'form-close':'popupClosed',
                    'acceptChangeCTV': 'popupClosed',
                    'acceptChangeNGT': 'popupClosed'
                },
            };
        },
        created() {
            this.Loading = true;
            try {
                Promise.all([this.getDsNganHang(), this.getDsToaNha(), 
                             this.getDsNVDoiSoat(), this.getDsNVQuanLy(),
                             this.getDsNhomDuAn(),
                            ]);
                this.Loading = false;
            } catch (ex) {
                this.Loading = false;
            }
            this.Loading = false;
        },
        computed:{
            cp_params: {
                get() {
                    return this.params;
                },
                set(value) {
                    return (this.params = value);
                },
            },
        },
        methods:{
            getDsNganHang: async function () {
                var ds_nganhang = [];
                let data = this.GetData(await api.get_ds_nganhang(this.axios));
                if (data && data.length > 0) {
                    data.forEach((e) => {
                        var temp = {
                            id: e.NGANHANG_ID,
                            text: e.TEN_NH,
                        };
                        ds_nganhang.push(temp);
                    });
                } else{
                    // console.log("Không có dữ liệu DS Ngân hàng");
                }
                // console.log(ds_nganhang);
                this.ds_nganhang = ds_nganhang.sort(function(a, b) {
                                                const nameA = a.text.toUpperCase();
                                                const nameB = b.text.toUpperCase();
                                                    if (nameA < nameB) {
                                                        return -1;
                                                    }
                                                    if (nameA > nameB) {
                                                        return 1;
                                                    }
                                                    return 0;
                                                });
            },
            getDsToaNha: async function () {
                var ds_toanha = [];
                let data = this.GetData(await api.get_ds_toanha(this.axios));
                if (data && data.length > 0) {
                    data.forEach((e) => {
                        var temp = {
                            id: e.toanha_id,
                            text: e.ma_toanha +" - "+ e.ten_toanha,
                        };
                        ds_toanha.push(temp);
                    });
                } else{
                    // console.log("Không có dữ liệu DS Tòa nhà");
                }
                // console.log(ds_toanha);
                this.ds_toanha = ds_toanha.sort(function(a, b) {
                                                const nameA = a.text.toUpperCase();
                                                const nameB = b.text.toUpperCase();
                                                    if (nameA < nameB) {
                                                        return -1;
                                                    }
                                                    if (nameA > nameB) {
                                                        return 1;
                                                    }
                                                    return 0;
                                                });
            },
            getDsNVDoiSoat: async function () {
                var ds_nv_doisoat = [];
                var thamso = {"vkieu": 2};// 1 nhân viên quản lý, 2 nhân viên đối soát
                let data = this.GetData(await api.get_ds_nhanvien(this.axios, thamso));
                if (data && data.length > 0) {
                    data.forEach((e) => {
                        var temp = {
                            id: e.nhanvien_id,
                            text: e.ten_nv == null ? 'ABC' : e.ten_nv,
                        };
                        ds_nv_doisoat.push(temp);
                    });
                } else{
                    // console.log("Không có dữ liệu DS Nhân viên đối soát");
                }
                // console.log(ds_nv_doisoat);
                this.ds_nv_doisoat = ds_nv_doisoat.sort(function(a, b) {
                                                const nameA = a.text.toUpperCase();
                                                const nameB = b.text.toUpperCase();
                                                    if (nameA < nameB) {
                                                        return -1;
                                                    }
                                                    if (nameA > nameB) {
                                                        return 1;
                                                    }
                                                    return 0;
                                                });
            },
            getDsNVQuanLy: async function () {
                var ds_nv_quanly = [];
                var thamso = {"vkieu": 1};// 1 nhân viên quản lý, 2 nhân viên đối soát
                let data = this.GetData(await api.get_ds_nhanvien(this.axios, thamso));
                if (data && data.length > 0) {
                    data.forEach((e) => {
                        var temp = {
                            id: e.nhanvien_id,
                            text: e.ten_nv == null ? 'ABC' : e.ten_nv,
                        };
                        ds_nv_quanly.push(temp);
                    });
                } else{
                    // console.log("Không có dữ liệu DS Nhân viên quản lý");
                }
                // console.log(ds_nv_quanly);
                this.ds_nv_quanly = ds_nv_quanly.sort(function(a, b) {
                                                const nameA = a.text.toUpperCase();
                                                const nameB = b.text.toUpperCase();
                                                    if (nameA < nameB) {
                                                        return -1;
                                                    }
                                                    if (nameA > nameB) {
                                                        return 1;
                                                    }
                                                    return 0;
                                                });
            },
            getDsNhomDuAn: async function () {
                var ds_nhom_duan = [];
                let data = this.GetData(await api.get_ds_nhom_duan(this.axios));
                if (data && data.length > 0) {
                    data.forEach((e) => {
                        var temp = {
                            id: e.NHOMDA_ID,
                            text: e.TEN_NHOMDA,
                        };
                        ds_nhom_duan.push(temp);
                    });
                } else{
                    // console.log("Không có dữ liệu DS Nhóm dự án");
                }
                // console.log(ds_nhom_duan);
                this.ds_nhom_duan = ds_nhom_duan.sort(function(a, b) {
                                                const nameA = a.text.toUpperCase();
                                                const nameB = b.text.toUpperCase();
                                                    if (nameA < nameB) {
                                                        return -1;
                                                    }
                                                    if (nameA > nameB) {
                                                        return 1;
                                                    }
                                                    return 0;
                                                });
            },
            selectNganHang: function (value) {
                this.params.nganhang_id = value.id;
            },
            selectToaNha: function (value) {
                this.params.toanha_id = value.id;
            },
            selectNVDoiSoat: function (value) {
                this.params.nhanvien_ds_id = value.id;
            },
            selectNVQuanLy: function (value) {
                this.params.nhanvien_ql_id = value.id;
            },
            selectNhomDuAn: function (value) {
                this.params.nhom_da_id = value.id;
            },
            changeFileUpload() {
                this.ten_upFile = this.$refs.files.files[0].name;
            },
            btnLayTT: async function()
            {
                await this.LoadDSHopDong(this.page_num, this.page_sz, this.totalRecords);
                if (this.ds_hopdong.list.length>0)
                    this.SetButton(3);
                else
                    this.SetButton(1);
            },
            btnNhapMoi()
            {
                this.SetButton(1);
            },
            btnHuy(){
                this.SetButton(2);
            },
            btnXoa(){
                if (this.ds_hopdong.list.length>0)
                    this.SetButton(3);
                else
                    this.SetButton(1);
            },
            btnGhiLai: async function (){
                this.Loading = true;
                var checked = await this.validated();
                if (checked) {
                    if (this.ten_upFile != "")
                    {
                        await this.UploadFile();
                    }
                    var chk_new = 0;
                    if(!this.isNhapMoi)
                    {
                        this.hddtu_id = this.ds_hopdong.list.length+1;
                        this.ma_hd = this.hddtu_id.toString().padStart(6,'0') + 'HDDT';
                        chk_new = 1;
                    }
                    var input = {
                        "chk_new": chk_new,
                        "data": [
                            {
                                "HDDTU_ID": this.hddtu_id,
                                "MA_HDDTU": this.ma_hd,
                                "TEN_HDDTU": this.ten_hd,
                                "TOANHA_ID": this.params.toanha_id == "-1" ? "" : this.params.toanha_id,
                                "NHOM_DA": this.params.nhom_da_id == "-1" ? "" : this.params.nhom_da_id,
                                "SO_HD": this.so_hd_giay,
                                "NGAYKY": this.txtNgayKy,
                                "THOIHAN": this.txtThoiHan,
                                "TIENDO": this.tien_do,
                                "TRANGTHAI": "1",
                                "GHICHU": this.ghi_chu,
                                "DONVI_CN": "",
                                "NGAY_CN": moment(new Date()).format('DD/MM/YYYY'),
                                "MAY_CN": "",
                                "NGUOI_CN": "",
                                "IP_CN": "",
                                "TONGMUC_DTU": this.tongmuc_dt,
                                "TG_HQDT": this.txtTGT_HQDT,
                                "THIPHAN_TOITHIEU": this.thiphan_tt,
                                "APRU_TOITHIEU": this.apru,
                                "CHUCVU": this.chuc_vu,
                                "NGUOIKY": this.nguoi_ky,
                                "GIATRI_TAISAN": this.giatri_ts,
                                "TYLE_PCDT": parseFloat(this.tile_pcdt),
                                "FILE_HD": this.ten_dowFile,
                                "TINHTRANG_ID": this.selectTinhTrang,
                                "NGAY_TT": this.txtNgayTT == null ? "" : this.txtNgayTT,
                                "HT_HOADON_ID": this.selectHTHD,
                                "HT_PCDT_ID": this.selectHTPCDT,
                                "HT_DAUTU_ID": this.selectHTDT,
                                "NGANHANG_ID": this.params.nganhang_id == "-1" ? "" : this.params.nganhang_id,
                                "STK": this.soTaiKhoan,
                                "MST": this.maSoThue,
                                "NV_DOISOAT_ID": this.params.nhanvien_ds_id == "-1" ? "" : this.params.nhanvien_ds_id,
                                "NV_QUANLY_ID": this.params.nhanvien_ql_id == "-1" ? "" : this.params.nhanvien_ql_id
                            }
                        ]
                    };
                    // console.log(input);
                    await this.ins_upd_hddt(input);
                    await this.LoadDSHopDong(this.page_num, this.page_sz, this.totalRecords);
                    if (this.ds_hopdong.list.length>0)
                        this.SetButton(3);
                    else
                        this.SetButton(1);
                }
                this.Loading = false;
            },
            btnXoa: async function ()
            {
                await this.LoadDSChiTietHD(this.hddtu_id, this.page_num, this.page_sz, this.totalRecords);
                if(this.ds_chitiet != 0 || this.ds_chitiet != "0")
                {
                    this.ShowError("Bạn không thể xóa bản ghi này! Hợp đồng này đang tồn tại Chi tiết hợp đồng đầu tư");
                    return;
                }
                await this.LoadDSTienTrinh(this.hddtu_id, this.page_num, this.page_sz, this.totalRecords);
                if(this.ds_tientrinh != 0 || this.ds_tientrinh != "0")
                {
                    this.ShowError("Bạn không thể xóa bản ghi này! Hợp đồng này đang tồn tại Tiến trình đầu tư");
                    return;
                }
                this.$bvModal.msgBoxConfirm("Bạn chắc chắn muốn xóa?", {
                    title: "Thông báo",
                    size: "m",
                    okTitle: "Đồng ý",
                    cancelTitle: "Hủy",
                }).then(async v => {
                    if(!v) {
                        return;
                    }
                    this.Loading = true;
                    var input = {
                        "hddtu_id": this.hddtu_id
                    };
                    await this.del_upd_hddt(input);
                    await this.LoadDSHopDong(this.page_num, this.page_sz, this.totalRecords);
                    if (this.ds_hopdong.list.length>0)
                        this.SetButton(3);
                    else
                        this.SetButton(1);
                    this.Loading = false;
                });
            },
            Clear: function ()
            {
                this.ma_hd = "";
                this.ten_hd = "";
                this.so_hd_giay = "";
                this.tien_do = "";
                this.txtNgayKy = "";
                this.txtThoiHan = "";
                this.ghi_chu = "";
                this.nguoi_ky = "";
                this.chuc_vu = "";
                this.params.nhom_da_id = -1;
                this.params.toanha_id = -1;
                this.txtTGT_HQDT = "";
                this.tongmuc_dt = 0;
                this.thiphan_tt = 0;
                this.apru = 0;
                this.giatri_ts = 0;
                this.tile_pcdt = 0;
                this.txtNgayTT = "";
                this.selectHTHD = 0;
                this.selectHTPCDT = 0;
                this.selectHTDT = 0;
                this.params.nganhang_id = -1;
                this.selectTinhTrang = 1;
                this.maSoThue = "";
                this.soTaiKhoan = "";
                this.params.nhanvien_ds_id = -1;
                this.params.nhanvien_ql_id = -1;
                this.ten_upFile = "";
                this.ten_dowFile = "";
                this.$refs.files.value = null;
            },
            LoadDSHopDong: async function (page_num, page_sz, total_row)
            {
                var input = {
                    page_num: page_num,
                    page_sz: page_sz,
                    total_row: total_row,
                };
                this.loading(true);
                let data = this.GetData(await api.get_ds_hopdong(this.axios, input));
                this.ds_hopdong.list = data.out;
                if (this.ds_hopdong.list.length>0)
                {
                    this.$refs.grid.setCurrentSelectedRow(this.index);
                }
                this.loading(false);
            },
            LoadDSChiTietHD: async function ( hddtu_id , page_num, page_sz, total_row)
            {
                var input = {
                    hddtu_id: hddtu_id,
                    page_num: page_num,
                    page_sz: page_sz,
                    total_row: total_row,
                };
                this.loading(true);
                let data = this.GetData(await api.get_ds_chitiet(this.axios, input));
                this.ds_chitiet = data.out.length;
                this.loading(false);
            },
            LoadDSTienTrinh: async function ( hddtu_id , page_num, page_sz, total_row)
            {
                var input = {
                    hddtu_id: hddtu_id,
                    page_num: page_num,
                    page_sz: page_sz,
                    total_row: total_row,
                };
                this.loading(true);
                let data = this.GetData(await api.get_ds_tientrinh(this.axios, input));
                this.ds_tientrinh = data.out.length;
                this.loading(false);
            },
            rowClicked(i,obj)
            {
                if(!obj) { 
                    return; 
                }
                this.index = i;
            },
            gridChanged(obj)
            {
                if(!obj) { 
                    return; 
                }
                this.hddtu_id = obj.hddtu_id;
                this.ma_hd = obj.ma_hddtu;
                this.ten_hd = obj.ten_hddtu;
                this.params.toanha_id = obj.toanha_id;
                this.nguoi_ky = obj.nguoiky == null ? "" : obj.nguoiky;
                this.chuc_vu = obj.chucvu == null ? "" : obj.chucvu;
                this.params.nhom_da_id = obj.nhom_da;
                this.txtNgayKy = obj.ngayky == null ? "" : moment(obj.ngayky).format("DD/MM/YYYY");
                this.so_hd_giay = obj.so_hd == null ? "" : obj.so_hd;
                this.selectHTDT = obj.ht_dautu_id;
                this.txtThoiHan = obj.thoihan == null ? "" : moment(obj.thoihan).format("DD/MM/YYYY");
                this.tien_do = obj.tiendo == null ? "" : obj.tiendo;
                this.selectHTPCDT = obj.ht_pcdt_id;
                this.txtNgayTT = obj.ngay_tt == null ? "" : moment(obj.ngay_tt).format("DD/MM/YYYY");
                this.params.nganhang_id = obj.nganhang_id;
                this.selectHTHD = obj.ht_hoadon_id;
                this.soTaiKhoan = obj.stk;
                this.maSoThue = obj.mst == null ? "" : obj.mst;
                this.selectTinhTrang = obj.tinhtrang_id;
                this.params.nhanvien_ds_id = obj.nv_doisoat_id;
                this.params.nhanvien_ql_id = obj.nv_quanly_id;
                this.tongmuc_dt = obj.tongmuc_dtu == null ? 0 : obj.tongmuc_dtu;
                this.txtTGT_HQDT = obj.tg_hqdt == null ? "" : moment(obj.tg_hqdt).format("DD/MM/YYYY");
                this.thiphan_tt = obj.thiphan_toithieu == null ? 0 : obj.thiphan_toithieu;
                this.apru = obj.apru_toithieu == null ? 0 : obj.apru_toithieu;
                this.giatri_ts = obj.giatri_taisan == null ? 0 : obj.giatri_taisan;
                this.tile_pcdt = obj.tyle_pcdt == null ? 0 : parseFloat(obj.tyle_pcdt);
                this.ghi_chu = obj.ghichu == null ? "" : obj.ghichu;
                this.ten_dowFile = obj.file_hd;
                this.ten_upFile = "",
                this.$refs.files.value = null;
                this.DownloadFile();
                this.SetButton(3);
            },
            SetButton(kieu) {
                this.isNhapMoi = false;
                this.isGhiLai = false;
                this.isHuy = false;
                this.isXoa = false;
                this.isChiTiet = false;
                this.isTienTrinh = false;
                switch (kieu) {
                    case 1: //Them moi
                        this.Clear();
                        this.isGhiLai = true;
                        this.isHuy = true;
                        this.$refs.tenHDFocus.focus();
                        break;
                    case 2: //huy
                        this.isNhapMoi = true;
                        this.Clear();
                        break;
                    case 3: //edit (Gridview change data)
                        this.isNhapMoi = true;
                        this.isXoa = true;
                        this.isGhiLai = true;
                        this.isHuy = true;
                        this.isChiTiet = true;
                        this.isTienTrinh = true;
                        break;
                }
            },
            btnChiTiet(){
                this.popupComponent = ()=>import('./Popups/HopDongChiTiet.vue');
                this.popupComponentName = "HopDongChiTiet";
                this.popupTitle = "Chi tiết hợp đồng đầu tư";
                this.popupComponentData = { hddtu_id:this.hddtu_id, ma_hd: this.ma_hd, ten_hd: this.ten_hd}
                this.$bvModal.show('popupComponentsChiTiet'); 
            },
            btnTienTrinh(){
                this.popupComponent = ()=>import('./Popups/HopDongTienTrinh.vue');
                this.popupComponentName = "HopDongTienTrinh";
                this.popupTitle = "Tiến trình đầu tư hợp đồng";
                this.popupComponentData = { hddtu_id:this.hddtu_id, ma_hd: this.ma_hd, ten_hd: this.ten_hd}
                this.$bvModal.show('popupComponentsTienTrinh'); 
            },
            UploadFile: async function () {
                let formData = new FormData();
                for(var file of this.$refs.files.files) {
                    formData.append('files', file);
                }
                this.Loading = true;
                await api.upload_file(this.axios,formData,{ headers: { 'Content-Type': 'multipart/form-data' } }).then((response) => {
                    if(response.data.error_code === 'BSS-00000000') {
                        this.ten_dowFile = response.data.data[0];
                        this.isUploaded = true;
                    }
                    }).catch(function(){
                        // this.ShowError("Upload file thất bại.");
                    }).finally(()=>{ this.Loading = false; });
                if (!this.isUploaded)
                    this.ShowError("Upload file thất bại.");
                else
                {
                    this.$refs.files.value = null;
                    this.ten_upFile = "";
                }
            },
            DownloadFile: async function () {
                var input = {
                    "list_file": this.ten_dowFile
                };
                this.Loading = true;
                let response = await api.download_file(this.axios, input);
                if(response.data.data && response.data.data.length > 0)
                    this.url_downfile = response.data.data[0].file_url;
                else
                    this.url_downfile = "#"; 
                this.Loading = false;
            },  
            validated: async function () {
                if (this.ten_hd == "") {
                    this.ShowWarning("Hãy nhập Tên hợp đồng đầu tư");
                    this.$refs.tenHDFocus.focus();
                    return false;
                }
                if (this.ten_hd.trim().length > 500) {
                    this.ShowWarning("Tên hợp đồng không được lớn hơn 500 ký tự");
                    this.$refs.tenHDFocus.focus();
                    return false;
                }
                if (this.params.toanha_id == -1) {
                    this.ShowWarning("Hãy nhập Tòa nhà");
                    return false;
                }
                if (this.params.nhom_da_id == -1) {
                    this.ShowWarning("Hãy nhập Nhóm dự án");
                    return false;
                }
                if (this.tile_pcdt < 0 || this.tile_pcdt > 100)
                {
                    this.ShowWarning("Tỷ lệ phân chia doanh thu từ 0.00% -> 100.00%");
                    this.tile_pcdt = 0;
                    return false;
                }
                if (this.nguoi_ky.trim().length > 100) {
                    this.ShowWarning("Người ký không được lớn hơn 100 ký tự");
                    this.$refs.nguoikyFocus.focus();
                    return false;
                }
                if (this.chuc_vu.trim().length > 100) {
                    this.ShowWarning("Chức vụ không được lớn hơn 100 ký tự");
                    this.$refs.chucvuFocus.focus();
                    return false;
                }
                if (this.so_hd_giay.trim().length > 200) {
                    this.ShowWarning("Số hợp đồng không được lớn hơn 200 ký tự");
                    this.$refs.soHDGFocus.focus();
                    return false;
                }
                if (this.tien_do.trim().length > 200) {
                    this.ShowWarning("Tiến độ không được lớn hơn 200 ký tự");
                    this.$refs.tiendoFocus.focus();
                    return false;
                }
                if (this.ghi_chu.trim().length > 500) {
                    this.ShowWarning("Ghi chú không được lớn hơn 500 ký tự");
                    this.$refs.ghichuFocus.focus();
                    return false;
                }
                if(this.maSoThue.trim() != "" )
                {
                    var input = {
                        "mst": this.maSoThue.trim()
                    };                    
                    var kq_kt = "";
                    this.Loading = true;
                    await api.kt_mst(this.axios, input).then((response) => {
                        if(response.data.error_code === 'BSS-00000000') {
                            kq_kt = response.data.data;
                        }
                        }).catch(function(){
                            
                        }).finally(()=>{ this.Loading = false; });
                    if(kq_kt != "OK")
                    {
                        this.ShowWarning(kq_kt);
                        return false;
                    }
                }
                return true;
            },
            ins_upd_hddt: async function (input) {
                if (this.isUploaded || (!this.isUploaded && this.ten_upFile == ""))
                {
                    let response = await api.ins_upd_hddt(this.axios, input);
                    if (response.data.error === 200 || response.data.error === "200") {
                        if(input["chk_new"] == 1)
                            this.ShowSuccess("Nhập mới thành công");
                        else
                            this.ShowSuccess("Cập nhật thành công");
                    } else {
                        this.ShowError(response.data.message);
                    }
                }
            },
            del_upd_hddt: async function (input) {
                let response = await api.del_upd_hddt(this.axios, input);
                if (response.data.error === 200 || response.data.error === "200") {
                    this.ShowSuccess("Xóa dữ liệu thành công!");
                } else {
                    this.ShowError(response.data.message);
                }
            },
            ShowSuccess: function (msg) {
                this.$toast.success(msg);
            },
            ShowWarning: function (msg) {
                this.$toast.warning(msg);
            },
            ShowError: function (msg) {
                this.$toast.error(msg);
            },
            GetData: function (response) {
                if (
                    response.data.error === 200 ||
                    response.data.error === "200" ||
                    response.data.error === 204 ||
                    response.data.error === "204"
                ) {
                    return response.data.data;
                } else {
                    // console.log(response.data.message);
                    this.ShowError(response.data.message);
                }
                return [];
            },
        },
    }
</script>