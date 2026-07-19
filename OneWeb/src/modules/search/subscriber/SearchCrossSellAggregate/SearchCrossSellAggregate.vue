<template src="./SearchCrossSellAggregate.html"></template>
<style scoped src="./SearchCrossSellAggregate.scss"></style>

<script>
    import gridView from '@/components/Shared/gridview'
    import breadcrumb from "@/components/breadcrumb"
    import '@/assets/vendor/jquery/split'
    import api from './SearchCrossSellAggregateAPI'
    import moment from 'moment'
    import VueNumeric from 'vue-numeric'
    import DataGrid2 from '@/components/Controls/DataGrid2'
    import searchAccount from '@/modules/search/subscriber/SearchAccount'
    import popupNhanVienDiaBan from './popup/popupNhanVienDiaBan.vue'
    import ThueBao_ToaNha from './popup/ThueBao_ToaNha.vue'
    import SearchAccountModal from '../../../contract/setup/SurveyRequest/popups/SearchAccountModal.vue'
    // import ModalTraCuuVT from '@/modules/QLVT/components/TraCuuVatTuThueBao/TraCuuVatTuThueBao.vue'
    export default {
        components: {
            gridView,
            breadcrumb,
            api,
            VueNumeric,
            searchAccount,
            popupNhanVienDiaBan,
            ThueBao_ToaNha,
            SearchAccountModal
            // ModalTraCuuVT
        },
        name: 'SearchCrossSellAggregate',
        mounted () {
            Split(['#boxLeft', '#boxRight'], {
                sizes: [55,45],
                gutterSize: 16,
                onDragEnd: function (sizes) {
                },
            });
            this.$refs.txtmaKH.focus();
        },
        async created() {
            this.Loading = true;
            try {
                Promise.all([ await this.getDsTinhTC(), await this.getDsDVVT(),
                              await this.getDsDoiTuong(), await this.getDsHinhThucTT(),
                              await this.getDsLoaiHinhTB(),
                            ]);
                this.Loading = false;
            } catch (ex) {
                this.Loading = false;
            }
            this.Loading = false;
        },
        watch: {
            // 'selDVVT': function (){
            //     if(this.temp_Ds_LoaiHinhTB.length > 0)
            //     {
            //         this.ds_LoaiHinhTB = this.temp_Ds_LoaiHinhTB.filter(x=>x.DICHVUVT_ID == this.selDVVT);
            //         this.selLoaiHinhTB = this.ds_LoaiHinhTB[0].LOAITB_ID;
            //     }
            // },
            'selTinhTC': function(){
                this.getDsChuQuan();
            }
        },
        data() {
            return {
                chkAll: false,
                ds_TinhTC: [],
                selTinhTC: 0,
                ds_DVVT: [],
                selDVVT: 0,
                ds_DoiTuong: [],
                selDoiTuong: 0,
                ds_HinhThucTT: [],
                selHinhThucTT: 0,
                temp_Ds_LoaiHinhTB: [],
                ds_LoaiHinhTB: [],
                selLoaiHinhTB: 0,
                ds_ChuQuan: [],
                selChuQuan: 0,
                ds_ChuQuanLD: [],
                selChuQuanLD: 0,
                kieu: 0,
                keyMaKH: "",
                khId: 0,
                ThongBao: "",
                loaiKM: "",
                maKHCu: "",
                tenKH: "",
                plKH: "",
                dcKH: "",
                kh_tienTT: 0,
                kh_tienNo: 0,
                maTT: "",
                maDD: "",
                tenTT: "",
                diachiTT: "",
                diachiBC: "",
                maInGhep: "",
                tt_tienTT: 0,
                tt_tienNo: 0,
                soMayAcc: "",
                soAo: "",
                diachiTB: "",
                diachiLD: "",
                trangthaiTB: "",
                khuvuc: "",
                tb_tienTT: 0,
                tb_tienNo: 0,
                ds_KH_Theo_MaKH: [],
                trangthaitb_id: 0,
                thuebao_id: 0,
                thanhtoan_id: 0,
                dsBienDongChuaHoanThanh: {
                    list: [],
                    header: [
                        { fieldName: "ma_tb", headerText: "Số máy/Acc", allowFiltering: true,},
                        { fieldName: "ten_tb", headerText: "Tên thuê bao", allowFiltering: true,},
                        { fieldName: "ten_loaihd", headerText: "Loại HĐ/PL", allowFiltering: true,},
                        { fieldName: "ngay_yc", headerText: "Ngày YC", allowFiltering: true, type: "Date", format: "dd/MM/yyyy"},
                        { fieldName: "trangthai_hd", headerText: "Trạng thái", allowFiltering: true},
                        { fieldName: "ma_gd", headerText: "Mã giao dịch", allowFiltering: true, customAttributes:{class: "redcolor"},},
                    ],
                    value: {},
                    isEnabled: true,
                    checked: [],
                },
                dsDanhBaThueBao: {
                    list: [],
                    header: [
                        { fieldName: "ma_tt", headerText: "Mã TT", allowFiltering: true,},
                        { fieldName: "ma_tt_neo", headerText: "Mã TT Neo", allowFiltering: true,},
                        { fieldName: "ma_tb", headerText: "Số máy/Acc", allowFiltering: true,},
                        { fieldName: "ten_tb", headerText: "Tên TB", allowFiltering: true,},
                        { fieldName: "sochinh", headerText: "Số chính", allowFiltering: true,},
                        { fieldName: "soao", headerText: "Số ảo", allowFiltering: true, customAttributes:{class: "redcolor"},},
                        { fieldName: "tocdo", headerText: "Tốc độ", allowFiltering: true,},
                        { fieldName: "trangthai_tb", headerText: "Trạng thái", allowFiltering: true,},
                        { fieldName: "ngay_sd", headerText: "Ngày SD", allowFiltering: true, type: "Date", format: "dd/MM/yyyy"},
                        { fieldName: "ngay_cat", headerText: "Ngày cắt", allowFiltering: true, type: "Date", format: "dd/MM/yyyy"},
                        { fieldName: "ngay_td", headerText: "Ngày TD", allowFiltering: true, type: "Date", format: "dd/MM/yyyy"},
                        { fieldName: "ngay_kp", headerText: "Ngày KP", allowFiltering: true, type: "Date", format: "dd/MM/yyyy"},
                        { fieldName: "tg_thue_tu", headerText: "Thuê từ ngày", allowFiltering: true, type: "Date", format: "dd/MM/yyyy"},
                        { fieldName: "tg_thue_den", headerText: "Thuê đến ngày", allowFiltering: true, type: "Date", format: "dd/MM/yyyy"},
                        { fieldName: "congnghe", headerText: "Công nghệ", allowFiltering: true,},
                    ],
                    value: {},
                    isEnabled: true,
                    checked: [],
                },
                ds_dulieu: [],
                dsKhuyenMai: {
                    list: [],
                    header: [
                        { fieldName: "LOAI_KM", headerText: "Loại KM", allowFiltering: true,},
                        { fieldName: "TENCHITIET_KM", headerText: "Chi tiết KM", allowFiltering: true,},
                        { fieldName: "NGAY_DKDC", headerText: "Ngày ĐK", allowFiltering: true, type: "Date", format: "dd/MM/yyyy"},
                        { fieldName: "THANG_BD", headerText: "Tháng BĐ", allowFiltering: true},
                        { fieldName: "THANG_KT", headerText: "Tháng KT", allowFiltering: true},
                        { fieldName: "THANG_BDDC", headerText: "Tháng BĐĐC", allowFiltering: true},
                        { fieldName: "THANG_KTDC", headerText: "Tháng KTĐC", allowFiltering: true},
                        { fieldName: "THANG_THOAITRA", headerText: "Tháng thoái", allowFiltering: true},
                        { fieldName: "TIEN_THOAI", headerText: "Tiền thoái", allowFiltering: true,},
                        { fieldName: "HIEULUC", headerText: "Hiệu lực", allowFiltering: true,},
                        { fieldName: "THANG_HUY", headerText: "Tháng hủy", allowFiltering: true},
                        { fieldName: "TEN_KM", headerText: "Tên KM", allowFiltering: true},
                        { fieldName: "DATCOC_CSD", headerText: "Tiền đặt cọc", allowFiltering: true},
                        { fieldName: "TYLE_SD", headerText: "Tỷ lệ SD", allowFiltering: true,},
                        { fieldName: "TIEN_SD", headerText: "Tiền SD", allowFiltering: true,},
                        { fieldName: "CUOC_SD", headerText: "Giảm cước SD", allowFiltering: true},
                        { fieldName: "TIEN_TD", headerText: "Tiền TD", allowFiltering: true,},
                        { fieldName: "TYLE_TB", headerText: "Tỷ lệ TB", allowFiltering: true,},
                        { fieldName: "TIEN_TB", headerText: "Tiền TB", allowFiltering: true,},
                        { fieldName: "CUOC_TB", headerText: "Giảm cước TB", allowFiltering: true,},
                        { fieldName: "TYLE_TK", headerText: "Tỷ lệ TK", allowFiltering: true,},
                        { fieldName: "TYLE_TC", headerText: "Tỷ lệ TC", allowFiltering: true,},
                        { fieldName: "NOIDUNG", headerText: "Nội dung", allowFiltering: true,},
                    ],
                    value: {},
                    isEnabled: true,
                    checked: [],
                },
                dsNhomGoi: {
                    list: [],
                    header: [
                        { fieldName: "TEN_GOI", headerText: "Tên nhóm/gói", allowFiltering: true,},
                        { fieldName: "CHUNHOM", headerText: "Chủ nhóm", allowFiltering: true,},
                        { fieldName: "MA_TB", headerText: "Số máy/Acc", allowFiltering: true},
                        { fieldName: "NGAY_DK", headerText: "Ngày ĐK", allowFiltering: true, type: "Date", format: "dd/MM/yyyy"},
                        { fieldName: "THANG_BD", headerText: "Tháng BĐ", allowFiltering: true},
                        { fieldName: "THANG_KT", headerText: "Tháng KT", allowFiltering: true},
                    ],
                    value: {},
                    isEnabled: true,
                    checked: [],
                },
                index_DSTB: 0,
                position: { X: 'center', Y: 'top' },
                dataDiachi: {},
                dataPopupNVDB: [],
                diaban: "",
            };
        },
        methods:{
            maKHEnter: async function (){
                this.Loading = true;
                try{
                    if(this.selTinhTC == 0 || this.selTinhTC == null)
                    {
                            this.$toast.warning("Chưa chọn tỉnh tra cứu.");
                            return;
                    }
                    if(this.keyMaKH.trim() != "")
                    {
                        this.keyMaKH = this.keyMaKH.trim();
                        await this.getDbKhTheoMaKH(this.keyMaKH);
                        if(this.ds_KH_Theo_MaKH.length > 0)
                        {
                            await this.HienThiTT_DBKH(this.ds_KH_Theo_MaKH, true);
                        }
                        else
                        {
                            this.Clear();
                            this.$toast.warning("Không tìm thấy thông tin khách hàng của mã KH : " + this.keyMaKH);
                        }
                    }
                    this.Loading = false;
                }catch(ex){
                    this.$toast.error("" + ex);
                    this.Loading = false;
                }
                finally { this.Loading = false; }
            },
            maTBEnter: async function (){
                this.Loading = true;
                if(this.soMayAcc.trim() != "")
                {
                    var vma_tb = this.soMayAcc.trim();
                    var ds_khachhang = [];
                    await api.get_ds_kh_theo_ma_tb(this.axios,{
                        "tinhthicong_id": this.selTinhTC,
                        "vma_tb": vma_tb
                        }).then((response) => {
                                    if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                                    {
                                        ds_khachhang = response.data.data;
                                    }
                                }
                    );
                    if(ds_khachhang.length > 0)
                    {
                      await this.HienThiTT_DBKH(ds_khachhang, true);

                    }
                    else
                    {
                        this.Clear();
                        this.$toast.warning("Không tìm thấy thông tin khách hàng của thuê bao : " + vma_tb);
                    }

                }
                this.Loading = false;
            },
            maTTEnter: async function (){
                this.Loading = true;
                try{
                    if(this.maTT.trim() != "")
                    {
                        var vma_tt = this.maTT.trim();
                        var ds_khachhang = [];
                        
                        await api.get_ds_kh_theo_ma_tt(this.axios,{
                            "tinhthicong_id": this.selTinhTC,
                            "vma_tt": vma_tt
                            }).then((response) => {
                                        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                                        {
                                            ds_khachhang = response.data.data;
                                        }
                                    }
                        );
                        if(ds_khachhang.length <=0)
                        {
                            await api.get_ds_kh_theo_ma_tb(this.axios,{
                                "tinhthicong_id": this.selTinhTC,
                                "vma_tb": vma_tt
                                }).then((response) => {
                                            if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000')
                                            {
                                                ds_khachhang = response.data.data;
                                            }
                                        }
                            );
                        }
                        if(ds_khachhang.length > 0)
                        {
                            await this.HienThiTT_DBKH(ds_khachhang, true);

                        }
                        else
                        {
                            this.Clear();
                            this.$toast.warning("Không tìm thấy thông tin khách hàng của mã tt : " + vma_tt);
                        }
                    }
                    this.Loading = false;
                }catch(ex){
                    this.$toast.error("" + ex);
                    this.Loading = false;
                }
                finally { this.Loading = false; }
            },
            chkAll_KM_CheckedChanged: async function (){
                if(this.ds_dulieu && this.ds_dulieu[0].DS_THUEBAO.length > 0)
                {
                    var ds = {};
                    var pdata = {
                        "tinhthicong_id": this.selTinhTC,
                        "p_thuebao_id": this.thuebao_id,
                        "p_thanhtoan_id": this.thanhtoan_id,
                        "p_dichvuvt_id": this.selDVVT,
                        "p_is_chk_km": this.chkAll ? 1 : 0
                    };
                    console.log(pdata);
                    await api.get_ds_tt_db_theo_thuebao(this.axios, JSON.stringify(pdata)).then((response) => {
                        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                            ds = JSON.parse(response.data.data);
                            this.dsKhuyenMai.list = ds.DS_KM;
                        }
                        else
                            this.dsKhuyenMai.list = [];
                    });
                    this.loaiKM = "";
                    if(this.dsKhuyenMai.list.length > 0)
                    {
                        this.loaiKM = this.dsKhuyenMai.list[0].TEN_KM;
                    }
                }
            },
            getDsTinhTC: async function () {
                var ds_TinhTC = [];
                let data = this.GetData(await api.get_ds_tinhtc(this.axios, {}));
                if (data && data.length > 0) {
                    data.forEach((e) => {
                        var temp = {
                            id: e.tinh_id,
                            text: e.tentinh,
                        };
                        ds_TinhTC.push(temp);
                    });
                    this.ds_TinhTC = ds_TinhTC;
                    this.selTinhTC = ds_TinhTC[0].id;
                } else{
                    this.ds_TinhTC = ds_TinhTC;
                    this.selTinhTC = 0;   
                }
            },
            sel_TinhTC: function (value) {
                this.selTinhTC = value.id;
            },
            getDsDVVT: async function () {
                let data = this.GetData(await api.get_ds_dvvt(this.axios, {}));
                if (data && data.length > 0) {
                    this.ds_DVVT = data;
                    this.selDVVT = data[0].dichvuvt_id;
                } else{
                    this.ds_DVVT = data;
                    this.selDVVT = 0;   
                }
            },
            getDsDoiTuong: async function () {
                let data = this.GetData(await api.get_doituong(this.axios));
                if (data && data.length > 0) {
                    this.ds_DoiTuong = data;
                    this.selDoiTuong = data[0].doituong_id;
                } else{
                    this.ds_DoiTuong = data;
                    this.selDoiTuong = 0;   
                }
            },
            getDsHinhThucTT: async function () {
                let data = this.GetData(await api.get_hinhthuc_tt(this.axios));
                if (data && data.length > 0) {
                    this.ds_HinhThucTT = data;
                    this.selHinhThucTT = data[0].HTTT_ID;
                } else{
                    this.ds_HinhThucTT = data;
                    this.selHinhThucTT = 0;   
                }
            },
            getDsLoaiHinhTB: async function () {
                let data = this.GetData(await api.get_loaihinh_tb(this.axios));
                if (data && data.length > 0) {
                    this.temp_Ds_LoaiHinhTB = data;
                    this.ds_LoaiHinhTB = this.temp_Ds_LoaiHinhTB.filter(x=>x.DICHVUVT_ID == this.selDVVT);
                    this.selLoaiHinhTB = this.ds_LoaiHinhTB[0].LOAITB_ID;
                } else{
                    this.ds_LoaiHinhTB = data;
                    this.selLoaiHinhTB = 0;   
                }
            },
            getDsChuQuan: async function () {
                let data = this.GetData(await api.get_chuquan(this.axios, {"tinhthicong_id": this.selTinhTC, "vkieu": 1}));
                if (data && data.length > 0) {
                    this.ds_ChuQuan = data;
                    this.ds_ChuQuanLD = data;
                    this.selChuQuan = data[0].chuquan_id;
                    this.selChuQuanLD = data[0].chuquan_id;
                } else{
                    this.ds_ChuQuan = data;
                    this.ds_ChuQuanLD = data;
                    this.selChuQuan = 0;   
                    this.selChuQuanLD = 0;   
                }
            },
            getDbKhTheoMaKH: async function (vmaKh) {
                var input = {
                    "tinhthicong_id": this.selTinhTC,
                    "in_ma_tb": vmaKh
                };
                let data = this.GetData(await api.get_dbkh_theo_makh(this.axios, input));
                this.ds_KH_Theo_MaKH = data;
            },
            HienThiTT_DBKH:  async function (ds, htds) {
                try{
                    if(ds.length > 0)
                    {
                        if(htds)
                        {
                            this.keyMaKH = ds[0].ma_kh;
                            this.maKHCu = ds[0].makh_cu;
                            this.tenKH = ds[0].ten_kh;
                            this.khId = ds[0].khachhang_id;
                            this.dcKH = ds[0].diachi_kh;
                            var pdata = {
                                "khachhang_id": this.khId,
                                "phanloaikh_id": ds[0].phanloaikh_id == null ? 0 : ds[0].phanloaikh_id,
                                "tinhthicong_id": this.selTinhTC
                            };
                            await api.get_tt_db_theo_kh(this.axios, pdata).then((response) => {
                                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                                    this.plKH = response.data.data.out_phanloaikh;
                                    this.kh_tienNo = (response.data.data.out_tienno) * 1;
                                    this.kh_tienTT = (response.data.data.out_tienttno) * 1;
                                }
                            });
                            await this.LAY_DS_BIENDONG_CHUA_HOANTHANH();
                            await this.HienThiDanhSachDBTB();
                        }
                    }
                }
                catch(e){
                    this.ShowError("Đã có lỗi trong quá trình hiển thị dữ liệu");
                }
            },
            async LAY_DS_BIENDONG_CHUA_HOANTHANH(){
                await api.get_ds_biendong_chua_hoanthanh_kh(this.axios, {
                    "tinhthicong_id": this.selTinhTC,
                    "vkhachhang_id": this.khId
                }).then((response) => {
                    if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        this.dsBienDongChuaHoanThanh.list = response.data.data;
                    }
                    else
                        this.dsBienDongChuaHoanThanh.list = [];
                });
            },
            async HienThiDanhSachDBTB(){
                var ds = [];
                await api.get_ds_danhba_tb_v2(this.axios, {
                    "tinhthicong_id": this.selTinhTC,
                    "khachhang_id": this.khId
                }).then((response) => {
                    if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                        if(this.soMayAcc.trim() != "")
                        {
                            let dataSelect = response.data.data.filter((x) => x.ma_tb == this.soMayAcc.trim());
                            let dataNotSelect = response.data.data.filter((x) => x.ma_tb != this.soMayAcc.trim());
                            this.dsDanhBaThueBao.list = [...dataSelect, ...dataNotSelect];
                        }
                        else
                            this.dsDanhBaThueBao.list = response.data.data;
                    }
                    else
                        this.dsDanhBaThueBao.list = [];
                });// Số ảo chỉ user chính phụ mới có css.lay_ds_dbtb_theo_khid_v2
            },
            rowClicked(i,obj)
            {
                if(!obj) { 
                    return; 
                }
                this.index_DSTB = i;
            },
            async gridChanged(obj)
            {
                try{
                    if(!obj) { 
                        return; 
                    }
                    this.selDVVT = obj.dichvuvt_id;
                    this.thuebao_id = obj.thuebao_id *1;
                    this.thanhtoan_id = obj.thanhtoan_id *1;
                    var pdata = {
                        "tinhthicong_id": this.selTinhTC,
                        "p_thuebao_id": this.thuebao_id,
                        "p_thanhtoan_id": this.thanhtoan_id,
                        "p_dichvuvt_id": this.selDVVT,
                        "p_is_chk_km": this.chkAll ? 1 : 0
                    };
                    console.log(pdata);
                    await api.get_ds_tt_db_theo_thuebao(this.axios, JSON.stringify(pdata)).then((response) => {
                        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                            this.ds_dulieu = [];
                            this.ds_dulieu.push(Object.assign({}, JSON.parse(response.data.data)));
                        }
                        else
                            this.ds_dulieu = [];
                    });
                    // console.log(this.ds_dulieu);
                    if(this.ds_dulieu)
                    {
                        if(this.ds_dulieu[0].DS_THUEBAO.length > 0)
                        {
                            await this.HienThiTT_DBTB(this.ds_dulieu[0].DS_THUEBAO, false);
                            await this.HienThiTT_DBTT();
                            this.khuvuc = "";
                            if(this.ds_dulieu[0].DS_NV && this.ds_dulieu[0].DS_NV.length > 0)
                                this.khuvuc = this.ds_dulieu[0].DS_NV[0].TEN_KV;
                            await api.lay_ds_diachi_theo_dbtbid(this.axios, this.thuebao_id
                                ).then(response => {            
                                    if (response &&response.data &&response.data.error_code === "BSS-00000000"&&response.data.data&&response.data.data.length>0){
                                        this.dataDiachi = response.data.data[0]
                                    }
                            });
                            await this.lay_ds_nhanvien_dbtb_by_id(this.thuebao_id);
                        }
                    }
                }catch(ex){
                    this.$toast.error('' + ex);
                }

            },
            async HienThiTT_DBTB(ds, htds){
                try{
                    if(ds.length > 0)
                    {
                        this.trangthaitb_id = ds[0].TRANGTHAITB_ID;
                        this.soMayAcc = ds[0].MA_TB;
                        this.diachiTB = ds[0].DIACHI_TB;
                        this.diachiLD = ds[0].DIACHI_LD;
                        this.selDVVT = ds[0].DICHVUVT_ID;
                        this.selLoaiHinhTB = ds[0].LOAITB_ID;
                        this.ds_LoaiHinhTB = this.temp_Ds_LoaiHinhTB.filter(x=>x.LOAITB_ID == ds[0].LOAITB_ID);
                        this.selDoiTuong = ds[0].DOITUONG_ID;
                        this.trangthaiTB = ds[0].TRANGTHAI_TB;
                        this.tb_tienNo = this.ds_dulieu[0].TienNoTBao;
                        this.tb_tienTT = this.ds_dulieu[0].TienTTNoTBao;
                        this.selChuQuan = this.ds_dulieu[0].CHUQUAN[0].CHUQUAN_ID;
                        this.selChuQuanLD = this.ds_dulieu[0].CHUQUAN[0].CHUQUANLD_ID;
                        if(this.tb_tienNo > 0)
                            this.ThongBao = "Thuê bao đang trong tình trạng nợ cước.";
                        else
                            this.ThongBao = "";
                        await this.LAY_DS_KM();
                        await this.LAY_DS_GOI_DICHVU();
                    }
                }catch(ex){
                    this.$toast.error('' + ex);
                }
            },
            async HienThiTT_DBTT(){
                try{
                    if(this.ds_dulieu[0].DS_THANHTOAN.length > 0)
                    {
                        this.maTT = this.ds_dulieu[0].DS_THANHTOAN[0].MA_TT;
                        this.tenTT = this.ds_dulieu[0].DS_THANHTOAN[0].TEN_TT;
                        this.diachiTT = this.ds_dulieu[0].DS_THANHTOAN[0].DIACHI_TT; 
                        this.diachiBC = this.ds_dulieu[0].DS_THANHTOAN[0].DIACHI_BC; 
                        this.selHinhThucTT = this.ds_dulieu[0].DS_THANHTOAN[0].HTTT_ID; 
                        this.maDD = this.ds_dulieu[0].DS_THANHTOAN[0].MATB_DD; 
                        this.tt_tienNo = this.ds_dulieu[0].TienNoTT; 
                        this.tt_tienTT = this.ds_dulieu[0].TienTTNoTT; 
                        this.maInGhep = this.ds_dulieu[0].DS_THANHTOAN[0].MAIN_GHEP;
                    }
                }catch(ex){
                    this.$toast.error('' + ex);
                }
            },
            async LAY_DS_KM(){
                try{
                    if(this.ds_dulieu[0].DS_KM.length > 0)
                    {
                        this.loaiKM = this.ds_dulieu[0].DS_KM[0].TEN_KM;
                    }
                    this.dsKhuyenMai.list = this.ds_dulieu[0].DS_KM;
                }catch(ex){
                    this.$toast.warning('' + ex);
                }
            },
            async LAY_DS_GOI_DICHVU(){
                this.dsNhomGoi.list = this.ds_dulieu[0].DS_GOI_DICHVU;
            },
            Clear()
            { 
                // txtMaKH.Text = "";
                // txtTenKH.Text = "";
                // txtDiaChiKH.Text = "";
                // txtMaKHcu.Text = "";
                // txtTienNoKH.Text = "0";
                // txtTienTTNoKH.Text = "";
                // txtPhanLoaiKH.Text = "";
                this.keyMaKH = "";
                this.tenKH = "";
                this.dcKH = "";
                this.maKHCu = "";
                this.kh_tienNo = 0;
                this.kh_tienTT = 0;
                this.plKH = "";

                // txtMaTT.Text = "";
                // txtTenTT.Text = "";
                // txtDiaChiTT.Text = "";
                // txtTienTTNoTT.Text = "0";
                // txtTienNoTT.Text = "0";
                // txtDiaChiBC.Text = "";
                // cboHTTT.SelectedIndex = 0;
                // txtMaIG.Text = "";
                // txtMaTBDD.Text = "";
                this.maTT = "";
                this.tenTT = "";
                this.diachiTT = "";
                this.tt_tienTT = 0;
                this.tt_tienNo = 0;
                this.diachiBC = "";
                this.selHinhThucTT = this.ds_HinhThucTT[0].HTTT_ID;
                this.maInGhep = "";
                this.maDD = "";

                // txtMaTB.Text = "";
                // txtDiaChiTB.Text = "";
                // txtDiaChiLD.Text = "";
                // txtTrangThaiTB.Text = "";
                // gridThueBao.DataSource = null;
                // grvGoiSuDung.DataSource = null;
                // grvDSBD.DataSource = null;
                // txtKhuVuc.Text = "";
                // grvDSKM.DataSource = null;
                // txtMa_lt.Text = "";
                this.soMayAcc = "";
                this.diachiTB = "";
                this.diachiLD = "";
                this.trangthaiTB = "";
                this.tb_tienNo = 0;
                this.tb_tienTT = 0;
                this.dsDanhBaThueBao.list = [];
                this.dsNhomGoi.list = [];
                this.dsBienDongChuaHoanThanh.list = [];
                this.khuvuc = "";
                this.dsKhuyenMai.list = [];
                // ma lt chưa khảo sát
                this.loaiKM = "";
                this.ThongBao = "";
                this.dataPopupNVDB = [];
                this.diaban = "";

            },
            SearchDanhBa: function () {
                this.$refs.searchAccountDialog.showModal()
            },
            btnToaNha()
            {
                if (this.keyMaKH.trim() == "") {
                    this.$toast.warning("Bạn chưa nhập mã khách hàng!");
                    return
                }
                //Tham chiếu UR2.8.004 (chức năng của Tân)
                //this.$bvModal.show('pop_toanha')
                this.$refs.formDiachi.initData();
                this.$refs.pop_toanha.show();
            },
            searchAccountDialogClose(data) {
                if (data != null) {
                    if (data.ma_kh!=null && data.ma_kh!='') {
                        this.keyMaKH = data.ma_kh;
                    }    
                }
                this.$refs.searchAccountDialog.hide()
            },
            //lấy danh sách nhân viên địa bàn theo thuê bao id
            async lay_ds_nhanvien_dbtb_by_id(thuebao_id) {
                try {
                    this.dataPopupNVDB = [];
                    this.diaban = "";
                    await api.lay_ds_nhanvien_dbtb_by_id(this.axios,
                    {
                        "thuebaoId": thuebao_id,
                        "pageNum": 1,
                        "pageRec": 10,
                        "totalRow": 1
                    }).then(response => {
                        if (response && response.data && response.data.error_code === "BSS-00000000" && response.data.data && response.data.data.length>0){
                            this.dataPopupNVDB = response.data.data;
                        }
                    });
                    this.diaban = this.khuvuc;
                    } catch (error) {
                    console.log(error)        
                }
            },
            async btnKhuVuc(){
                if (this.thuebao_id==0) {
                    this.$toast.warning('Bạn chưa chọn thuê bao!');
                    return;
                }
                this.$bvModal.show('popupNVDB')
            },
            closePopupNVDB(){},
            // btnVatTu()
            // {
            //     if (this.thuebao_id=="" || this.thuebao_id==0) {
            //         this.$toast.warning("Chưa có thuê bao được chọn!");
            //         return;
            //     }
            //     //Tham chiếu UR13.1.037     
            //     this.$refs.formVattu.p_so_may=this.soMayAcc;
            //     this.$refs.formVattu.frmTraCuuVatTuTB(this.thuebao_id);
            //     this.$bvModal.show('popup-tracuuvattuthuebao');
            // },
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