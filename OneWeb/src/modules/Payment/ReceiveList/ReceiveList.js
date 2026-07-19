import api from "./api";
import Loai_NV from "../../../const/enums/Loai_NV";
import LOAI_DV from "../../../const/enums/LOAI_DV";
import DSBangKe from "./components/DSBangKe.vue";
import DSHopDongDaGan from "./components/DSHopDongDaGan.vue";
import DSThueBao from "./components/DSThueBao.vue";
import { DocSo } from "./DocSo";
import moment from "moment";
import { Query,Predicate } from "@syncfusion/ej2-data";
import PickUpDate from "./components/PickUpDate.vue"
import DatePicker from 'vue2-datepicker' 
import 'vue2-datepicker/locale/vi.js' 
import 'vue2-datepicker/index.css'

export default {
    components: {DSBangKe, DSHopDongDaGan, DSThueBao, PickUpDate, DatePicker},
    data() {
        return {
            TrungTamVTList: [],
            ToVTList: [],
            TrangThaiBKList: [],
            NguoiNopList: [],
            NhomNhanList: [],
            NoiNhanList: [],
            NguoiNhanList: [],
            TrungTamVT: null,
            ToVT: null,
            TrangThaiBK: null,
            NguoiNop: null,
            NhomNhan: null,
            NoiNhan: null,
            NguoiNhan: null,
            NgayTao: moment(new Date()).format("DD/MM/YYYY"),
            NgayNhan: moment(new Date()).format("DD/MM/YYYY"),
            DSBangKe: [],
            DSHopDong: [],
            DSThueBao: [],
            TongCong: null,
            BangChu: null,
            denngay: moment(new Date()).format("DD/MM/YYYY"),
            tungay: moment(new Date()).format("DD/MM/YYYY"),
            MaBK: null,
            vdsbangke_id: null
        }
    },
    created() {
        this.initialization();
    },
    computed: {
        fieldDropDownNV() {
            return { text: 'ten_nv', value: 'nhanvien_id' };
        },
        fieldDropDownDV() {
            return { text: 'ten_dv', value: 'donvi_id' };
        },
        fieldDropDownLoaiDV() {
            return { text: 'TEN_LOAIDV', value: 'LOAIDV_ID' };
        },
        fieldDropDownTrangThai() {
            return { text: 'TRANGTHAI', value: 'TTBK_ID' };
        },
        fieldDropDown_DV_upper() {
            return { text: 'TEN_DV', value: 'DONVI_ID' };
        }
    },
    watch: {
        TongCong() {
            this.BangChu = DocSo(this.TongCong);
        }
    },
    methods: {
        async initialization() {
            try {
                const respone = await api.loadPage(this.$root.axios);
                const data = JSON.parse(respone.data.data);
                console.log(data);
                this.TrungTamVTList = data.DS_DONVI;
                this.NhomNhanList = data.DS_LOAIDV;
                this.TrangThaiBKList = data.DS_TRANGTHAI_BANGKE;
                this.$nextTick(() => {
                    this.TrangThaiBK = 1;
                    this.TrungTamVTList.length && (this.TrungTamVT = this.TrungTamVTList[0].DONVI_ID);
                    this.NhomNhanList.length && (this.NhomNhan = this.NhomNhanList[0].LOAIDV_ID);
                });
            } catch (error) {
                this.$toast.error(`Có lỗi !`);
            }
        },
        async onChangeTrungTamVT() {
            this.$root.showLoading(true);
            try {
                if (!this.TrungTamVT) return;
                this.ToVT = this.NoiNhan = null;
                const respone = await api.getToVT(this.$root.axios, { donvi_id: this.TrungTamVT, loaidv_id: LOAI_DV.TRAM_VT });
                this.ToVTList = respone.data.data;
                this.ToVTList.length && (this.ToVT = this.ToVTList[0].donvi_id);
                this.$root.showLoading(false);
            } catch (error) {
                this.$root.showLoading(false);
                this.$toast.error(`Có lỗi !`);
            }
        },
        async onChangeToVT() {
            this.$root.showLoading(true);
            try {
                if (!this.ToVT) return;
                const respone = await api.getNguoiNop(this.$root.axios, { loainv_id: Loai_NV.THICONG_DAYMAY, donvi_id: this.TrungTamVT });
                this.NguoiNopList = respone.data.data;
                this.NguoiNopList.length && (this.NguoiNop = this.NguoiNopList[0].nhanvien_id);
                this.$root.showLoading(false);
            } catch (error) {
                this.$root.showLoading(false);
                this.$toast.error(`Có lỗi !`);
            }
        },
        async onChangeNhomNhan() {
            this.$root.showLoading(true);
            try {
                if (!this.NhomNhan) return;
                this.NoiNhan = null;
                // const respone = await api.getNoiNhan(this.$root.axios, { donvi_id: 220, loaidv_id: 5 });
                const respone = await api.getNoiNhan(this.$root.axios, { donvi_id: +this.$root.token.getDonViID(), loaidv_id: this.NhomNhan });
                this.NoiNhanList = respone.data.data;
                this.NoiNhanList.length && (this.NoiNhan = this.NoiNhanList[0].donvi_id);
                this.$root.showLoading(false);
            } catch (error) {
                this.$root.showLoading(false);
                this.$toast.error(`Có lỗi !`);
            }
        },
        async onChangeNoiNhan() {
            this.$root.showLoading(true);
            try {
                if (!this.NoiNhan) return;
                const respone = await api.getNguoiNhan(this.$root.axios, { loainv_id: Loai_NV.NHAN_BANGKE, donvi_id: this.NhomNhan });
                this.NguoiNhanList = respone.data.data;
                this.NguoiNhanList.length && (this.NguoiNhan = this.NguoiNhanList[0].donvi_id);
                this.$root.showLoading(false);
            } catch (error) {
                this.$root.showLoading(false);
                this.$toast.error(`Có lỗi !`);
            }
        },
        async HienThiBangKe() {
            this.$root.showLoading(true);
            try {
                const params = { 
                    vma_bk: "0", 
                    vnhanvien_id: this.NguoiNop,
                    vdenngay: this.denngay,
                    vttbk_id: this.TrangThaiBK,
                    vtungay: this.tungay
                };
                const respone = await api.getBangKe(this.$root.axios, params);
                this.DSBangKe = respone.data.data;
                this.$root.showLoading(false);
            } catch (error) {
                this.$root.showLoading(false);
            }
        },
        async Timkiem() {
            await this.HienThiBangKe();
            if (!this.DSBangKe.length) {
                this.$toast.error('Không tìm thấy thông tin bảng kê !');
            }
        },
        async keypressMaBK() {
            if (this.MaBK) {
                let ma_bk = "0";
                if (this.MaBK.trim() != "") {
                    ma_bk = this.MaBK.trim();
                }
                await this.HienThiBangKe();
                if (this.DSBangKe.length) {
                    const resKeyPress = await api.keyPressMaBK(this.$root.axios,{ "ds_para": JSON.stringify({ ma_bk }) });
                    const DS_BANGKE = JSON.parse(resKeyPress.data.data).DS_BANGKE;
                    
                    if (DS_BANGKE.length) {
                        for (const bangke of DS_BANGKE) {
                            if (bangke.MA_BK == ma_bk) {
                                this.DSBangKe = this.DSBangKe.filter(x => x.ma_bk);
                                return;
                            }
                        }
                    } 
                } else {
                    this.MaBK = "";
                    this.TongCong = 0;
                    this.$toast.error("Không tìm thấy bảng kê : " + this.MaBK);
                }
            } 
            
        },
        onchangeRowDSBK(items) {
            if (!items.length) {
                return;
            }
            this.$root.showLoading(true);
            this.DSThueBao.length = 0;
            this.vdsbangke_id = "";
            let tongtien = 0;
            for (const row of items) {
                this.vdsbangke_id += row.bangke_id + ",";
                tongtien += +row.tongtien;
            }
            this.TongCong = tongtien;
            this.vdsbangke_id = this.vdsbangke_id.substring(0, this.vdsbangke_id.length - 1);
            api.get_DSHopDong_DaGan(this.$root.axios, {
                vdsbangke_id: this.vdsbangke_id
            }).then(res => {
                this.DSHopDong = res.data.data;
            }).catch(() => {
                this.DSHopDong.length = 0;
            });
            this.$root.showLoading(false);
        },
        onchangeRowDSHopDong(items) {
            if (!items.length) {
                return;
            }
            this.$root.showLoading(true);
            this.DSThueBao.length = 0;
            let vdshdkh_id = ""
            for (const row of items) {
                vdshdkh_id += row.ma_gd + ",";                
            }
            if (vdshdkh_id == "")
                vdshdkh_id = "-1";
            else
                vdshdkh_id = vdshdkh_id.substring(0, vdshdkh_id.length - 1);

            api.get_DSThueBao(this.$root.axios, {
                vdshdkh_id,
                vdsbangke_id: this.vdsbangke_id
            }).then(res => {
                this.DSThueBao = res.data.data;
            }).catch(() => {
                this.DSThueBao.length = 0;
            });
            this.$root.showLoading(false);
        },
        Nhan() {
            if (!this.NguoiNhan) {
                this.$toast.error("Bạn chưa chọn người nhận !");
                return;
            }
            if (this.DSBangKe.length) {
                if (!this.vdsbangke_id) {
                    this.$toast.error("Bạn chưa chọn bảng kê để nhận !");
                    return;
                }
                this.$root.showLoading(true);
                this.vdsbangke_id = this.vdsbangke_id.substring(0, vdsbangke_id.length - 1);
                api.Btn_ckick_Nhan(this.$root.axios, {
                    vbangke_id: this.vbangke_id,
                    vnguoinhan_id: this.NguoiNhan,
                    vttbk_id: this.TrangThaiBK,
                    vngay_nhan: this.NgayNhan
                }).then(() => {
                    this.$toast.success("Nhận bảng kê thành công !");
                }).catch(() => {
                    this.$toast.error("Có lỗi !");
                });
                this.$root.showLoading(false);
            } else {
                this.$toast.error("Không có bảng kê. Bạn không thể nhận !");
            }
            
        },
        onFilteringTTVT(e) {
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('TEN_DV', 'contains', e.text.trim(), true);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.TrungTamVTList, query);
        },
        onFilteringToVT(e) {
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('ten_dv', 'contains', e.text.trim(), true);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.ToVTList, query);
        },
        onFilteringTTBK(e) {
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('TRANGTHAI', 'contains', e.text.trim(), true);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.TrangThaiBKList, query);
        },
        onFilteringNguoiNop(e) {
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('ten_nv', 'contains', e.text.trim(), true);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.NguoiNopList, query);
        },
        onFilteringNhomNhan(e) {
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('TEN_LOAIDV', 'contains', e.text.trim(), true);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.NhomNhanList, query);
        },
        onFilteringNoiNhan(e) {
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('ten_dv', 'contains', e.text.trim(), true);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.NoiNhanList, query);
        },
        onFilteringNguoiNhan(e) {
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('ten_nv', 'contains', e.text.trim(), true);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.NguoiNhanList, query);
        },
        pickUpDate() {
            this.$refs.dialog.show();
        },
        chapNhanPopUpChonNgay(item) {
            this.denngay = item.vdenngay;
            this.tungay = item.vtungay;
        }
    }
}