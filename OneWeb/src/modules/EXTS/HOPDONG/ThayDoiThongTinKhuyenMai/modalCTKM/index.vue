<template src="./index.html"></template>

<script>
import moment from 'moment'
import DateTimeLib from '@/utils/DateTimeLib';
import { async } from 'q';

let dangKyTemplate = function () {
    return {
        template: {
            template: `
                    <div class="">
                        <input type="checkbox" class="check" v-model="check" :checked="check" :value="check"  @change="dangKy">
                    </div>
                `,
            data() {
                return {
                    check: 0
                }
            },
            created: function () {
                this.check = this.data.dangky;
            },
            computed: {
                parent() {
                    return this.$parent.$parent.$parent.$parent.$parent;
                },
                value() {
                    return this.data.chitietkm_id;
                },
                isCheck() {
                    return this.data.dangky;
                }
            },
            watch: {
                // 'check': function(data) {
                //     console.log(this.value, data);
                //     this.parent.dangKy(this.value, data)
                // }
            },
            methods: {
                dangKy: function (val) {
                    this.parent.dangKy(this.value, this.check)
                }
            }
        },
    }
}
export default {
    props: {
        idModal: String,
        dsKM_HDTB: {
            type: Array,
            default: function () {
                return [];
            }
        },
        dsDC_HDTB: {
            type: Array,
            default: function () {
                return [];
            }
        },
        tocdo_id: Number,
        muccuoc_id: Number,
        loaitb_id: Number
    },
    data: function () {
        return {
            selectOptions: {
                type: "Single",
                persistSelection: true,
                checkboxMode: "ResetOnRowClick",
            },
            title: "Đăng ký chi tiết khuyến mại",
            tableDetail: "Thông tin chi tiết khuyến mại",
            content: "Nội dung khuyến mại",

            dt: [],
            dsChiTietKM: [],
            dsTB_DV: [],
            // dsKM_HDTB: [],
            // dsDC_HDTB: [],
            txtDoiTuongKM: null,

            chapnhan: false,
            khuyenmai_id: 0,
            dichvuvt_id: 0,
            chuyenmang: 0,
            khuyenmai_dc: 0,
            hdtb_id: 0,
            kieu_km: 0, // 0 khuyến mãi, 1 trả trước
            tien_kmld: 0, //Tiền khuyến mại lắp đặt
            tyle_kmld: 0, //Tỷ lệ khuyến mại lắp đặt
            vat_kmld: 0, //Vat khuyến mại lắp đặt
            tien_datcoc: 0,
            tien_kmld_tt: 0,
            tyle_kmld_tt: 0,
            vat_kmld_tt: 0,
            tien_datcoc_tt: 0,
            vat_datcoc_tt: 0,
            i_tien_td: 0,
            i_tien_sd: 0,
            i_tien_tb: 0,
            i_cuoc_sd: 0,
            i_cuoc_tb: 0,
            i_tyle_sd: 0,
            i_tyle_tb: 0,

            ts_kiemtra_dinhmuc: 0,
            ts_loc_ct_tratruoc_dvgt: 0,
            ts_cho_chon_1_ctkm: 0,
            is_tinhcuoc_ngay: false,//BSS-88020
            isGiaHan: false,
            ht_kmtt: false,

            template: dangKyTemplate,
            dsCTKMId: [],
            m_dsTienCTKM: [],
            allowEdit: {
                thangBDKM: false,
                tyleSD: true,
                tyleTB: true,
                tienSD: true,
                tienTB: true,
                cuocSD: true,
                cuocTB: true,
                tyleKM: false,
                datcocCSD: false,
                thangBDTG: false,
                thangBDDC: false
            },

            DichVuVienThong: {
                CO_DINH: 1,
                DI_DONG: 2,
                ADSL: 4,
                DD_TRATRUOC: 5,
                EMAIL: 6,
                METRONET: 7,
                MEGAWAN: 8,
                TSL: 9,
                GPHONE: 10,
                IMS: 11,
                MEGA_EYES: 12,
                HOINGHI_TRUYENHINH: 13,
                ANTOAN_BAOMAT_TT: 14,
                DICHVU_CNTT: 15,
                TRUNGTAM_DULIEU: 16,
                HATANG_VIENTHONG: 19,
                IMS_DOITAC: 21,
                BRCD_DOITAC: 22,
                MGW_DOITAC: 23,
                TSL_DOITAC: 24,
            },
            dateEdit: { params: { format: 'dd/MM/yyyy' } }
        }
    },
    methods: {
        showModal: async function () {
            try {
                this.loading(true);
                this.txtDoiTuongKM = null;
                if (this.khuyenmai_id > 0) {
                    let rsThamSo = await this.$root.context.post('/web-hopdong/thaydoiloaihinhtbcodinhims/sp_lay_ds_thamso_md_theo_kieu', {
                        kieu_id: 0
                    });
                    if (rsThamSo.data.length > 0) {
                        let dsThamSo = rsThamSo.data;
                        let str_temp = 'DINHMUC_KM';
                        let obj = dsThamSo.find(v => v.ma_ts == str_temp);
                        if (obj && obj.ten_ts == '1') {
                            this.ts_kiemtra_dinhmuc = 1;
                        }

                        str_temp = 'LOC_CT_TRATRUOC_DVGT';
                        obj = dsThamSo.find(v => v.ma_ts == str_temp);
                        if (obj && obj.ten_ts == '1') {
                            this.ts_loc_ct_tratruoc_dvgt = 1;
                        }

                        str_temp = 'CHON_TUNG_CTKM';
                        obj = dsThamSo.find(v => v.ma_ts == str_temp);
                        if (obj && obj.ten_ts == '1') {
                            this.ts_cho_chon_1_ctkm = 1;
                        }
                        //begin tinh cuoc ngay BSS-88020
                        str_temp = 'TINHCUOC_NGAY';
                        obj = dsThamSo.find(v => v.ma_ts == str_temp);
                        if (obj && obj.ten_ts == '1') {
                            this.is_tinhcuoc_ngay = true;
                        }
                        //end tinh cuoc ngay BSS-88020
                    }
                    let rs;
                    if (this.ts_loc_ct_tratruoc_dvgt == 1 && this.dsTB_DV.length > 0) {
                        let rows = []
                        if (this.isGiaHan) {
                            rows = this.dsTB_DV.filter(v => v.dangky == 1 || (v.sudung == 1))
                        }
                        else {
                            rows = this.dsTB_DV.filter(v => v.dangky == 1)
                        }

                        let ds_dvgt = '-1';
                        if (rows.length > 0) {
                            let arr = rows.map(v => v.dichvugt_id);
                            ds_dvgt = arr.join(',');
                        }
                        rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_chitiet_km_hdtb_dvgt', {
                            khuyenmai_id: this.khuyenmai_id,
                            tocdo_id: this.tocdo_id,
                            muccuoc_id: this.muccuoc_id,
                            loaitb_id: this.loaitb_id,
                            chuyenmang: this.chuyenmang,
                            hdtb_id: this.hdtb_id,
                            ds_dvgt_id: ds_dvgt
                        })
                    }
                    else {
                        if (this.khuyenmai_dc == 1) {
                            rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_chitiet_km_hdtb_v4', {
                                khuyenmai_id: this.khuyenmai_id,
                                tocdo_id: this.tocdo_id,
                                muccuoc_id: this.muccuoc_id,
                                loaitb_id: this.loaitb_id,
                                chuyenmang: this.chuyenmang,
                                hdtb_id: this.hdtb_id
                            })
                        }
                        else {
                            rs = await this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_chitiet_km_hdtb', {
                                khuyenmai_id: this.khuyenmai_id,
                                tocdo_id: this.tocdo_id,
                                muccuoc_id: this.muccuoc_id,
                                loaitb_id: this.loaitb_id,
                                hdtb_id: this.hdtb_id,
                                chuyenmang: this.chuyenmang,
                            })
                        }
                    }
                    this.dt = rs.data;
                    this.dsChiTietKM = rs.data;
                    // this.tomau();

                    if (this.kieu_km == 0) {
                        if (this.dsKM_HDTB.length == 0) {
                            this.dsChiTietKM.forEach((element, index) => {
                                if (element.hienthi_md == 1) {
                                    this.dsChiTietKM[index].dangky = 1;
                                }
                                else {
                                    this.dsChiTietKM[index].dangky = 0;
                                }
                            });
                        }
                        else {
                            this.dsChiTietKM.forEach((ctkm, index1) => {
                                this.dsKM_HDTB.forEach((hdtb, index2) => {
                                    if (ctkm.chitietkm_id == hdtb.chitietkm_id) {
                                        this.dsChiTietKM[index1].dangky = 1;
                                        this.dsChiTietKM[index1].thang_bdkm = this.dsKM_HDTB[index2].thang_bd;
                                        this.dsChiTietKM[index1].thang_bddc = this.dsKM_HDTB[index2].thang_bddc;
                                        this.dsChiTietKM[index1].thang_bdtg = this.dsKM_HDTB[index2].thang_bdtg;
                                        this.dsChiTietKM[index1].tyle_km = this.dsKM_HDTB[index2].tyle_kmld;
                                        this.dsChiTietKM[index1].tyle_sd = this.dsKM_HDTB[index2].tyle_sd;
                                        this.dsChiTietKM[index1].tyle_tb = this.dsKM_HDTB[index2].tyle_tb;
                                        this.dsChiTietKM[index1].tien_sd = this.dsKM_HDTB[index2].tien_sd;
                                        this.dsChiTietKM[index1].tien_tb = this.dsKM_HDTB[index2].tien_tb;
                                        this.dsChiTietKM[index1].cuoc_sd = this.dsKM_HDTB[index2].cuoc_sd;
                                        this.dsChiTietKM[index1].cuoc_tb = this.dsKM_HDTB[index2].cuoc_tb;
                                        this.dsChiTietKM[index1].datcoc_csd = this.dsKM_HDTB[index2].datcoc_csd;
                                        this.dsChiTietKM[index1].tien_td = this.dsKM_HDTB[index2].tien_td;
                                    }
                                });
                            });
                        }
                    }

                    if (this.kieu_km == 1) {
                        this.title = 'Đăng ký chi tiết trả trước';
                        this.tableDetail = 'Thông tin chi tiết trả trước';
                        this.content = 'Nội dung trả trước';

                        if (this.dsDC_HDTB.length <= 0) {
                            for (let i = 0; i < dt.length; i++) {
                                if (dt[i].hienthi_md == 1) {
                                    this.dsChiTietKM[i].dangky = 1;
                                }
                                else {
                                    this.dsChiTietKM[i].dangky = 0;
                                }
                            }
                        }
                        else {
                            this.dsChiTietKM.forEach((ctkm, index1) => {
                                this.dsDC_HDTB.forEach((hdtb, index2) => {
                                    if (ctkm.chitietkm_id == hdtb.chitietkm_id) {
                                        this.dsChiTietKM[index1].dangky = 1;
                                        this.dsChiTietKM[index1].thang_bdkm = this.dsDC_HDTB[index2].thang_bd;
                                        this.dsChiTietKM[index1].thang_bddc = this.dsDC_HDTB[index2].thang_bddc;
                                        this.dsChiTietKM[index1].thang_bdtg = this.dsDC_HDTB[index2].thang_bdtg;
                                        this.dsChiTietKM[index1].tyle_km = this.dsDC_HDTB[index2].tyle_kmld;
                                        this.dsChiTietKM[index1].tyle_sd = this.dsDC_HDTB[index2].tyle_sd;
                                        this.dsChiTietKM[index1].tyle_tb = this.dsDC_HDTB[index2].tyle_tb;
                                        this.dsChiTietKM[index1].tien_sd = this.dsDC_HDTB[index2].tien_sd;
                                        this.dsChiTietKM[index1].tien_tb = this.dsDC_HDTB[index2].tien_tb;
                                        this.dsChiTietKM[index1].cuoc_sd = this.dsDC_HDTB[index2].cuoc_sd;
                                        this.dsChiTietKM[index1].cuoc_tb = this.dsDC_HDTB[index2].cuoc_tb;
                                        this.dsChiTietKM[index1].datcoc_csd = this.dsDC_HDTB[index2].datcoc_csd;
                                        this.dsChiTietKM[index1].tien_td = this.dsDC_HDTB[index2].tien_td;
                                    }
                                });
                            });
                        }
                    }
                }
                else {
                    this.$root.toastError('Hãy chọn đợt khuyến mãi!');
                }

                if (this.dsChiTietKM.length > 0) {
                    this.dsChiTietKM.forEach((element, index) => {
                        this.kiemTra(index);
                    });
                }
                this.loadEvent();
            } catch (error) {
                console.log(error);
            } finally {
                {
                    this.loading(false);
                }
            }
        },
        kiemTra: async function (index) {
            let chk = true;
            let chitietkm_id = parseInt(this.dsChiTietKM[index].chitietkm_id);
            let rs = await this.$root.context.get(`/web-hopdong/thaydoithongtinkhuyenmai/lay_ctkm_dvgt/${chitietkm_id}`);
            let dtl = rs.data;
            if (dtl.length > 0) {
                for (const element of dtl) {
                    let kq = await this.kiemTra_dvgt(element.dichvugt_id);
                    if (!kq) {
                        chk = false;
                    }
                    else {
                        chk = true;
                    }
                };
                this.dsChiTietKM[index].dangky = 0;
            }
            return chk;
        },
        kiemTra_dvgt: function (dichvu_id) {
            if (this.dsTB_DV.length > 0) {
                let rows = [];
                if (this.isGiaHan) {
                    rows = this.dsTB_DV.filter(v => v.dangky == 1 || (v.sudung == 1));
                }
                else {
                    rows = this.dsTB_DV.filter(v => v.dangky == 1);
                }
                rows.forEach(element => {
                    if (dichvu_id == element.dichvugt_id) {
                        return true;
                    }
                });
            }
            return false;
        },
        gridChiTietKM_rowSelected: function (data) {
            if (data) {
                this.txtDoiTuongKM = data[0].noidung;
            }
            else {
                this.txtDoiTuongKM = null;
            }
        },
        chapNhan: async function () {
            this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.endEdit()
            this.chapnhan = true;
            this.tien_kmld = 0;
            this.tyle_kmld = 0;
            this.vat_kmld = 0;
            this.tien_datcoc = 0;
            this.tien_kmld_tt = 0;
            this.tyle_kmld_tt = 0;
            this.vat_kmld_tt = 0;
            this.tien_datcoc_tt = 0;
            this.vat_datcoc_tt = 0;
            this.i_tien_td = 0;
            let ithang_huongdc = 0;
            let ithang_huongkm = 0;
            let ithang_tg = 0;
            let dtiendatcoc_csd = 0;
            let dtien_td = 0;
            let check = true;
            let loai_bddc = 0;
            let sothang_bddc = 0;
            try {
                let dsKM_HDTB_New = [];
                let ngayCn = await this.lay_ngay_cn();
                if (this.kieu_km == 0) {
                    for (let index = 0; index < this.dsChiTietKM.length; index++) {
                        if (this.dsChiTietKM[index].dangky == 1) {
                            if (this.khuyenmai_dc == 1 && this.dsChiTietKM[index].datcoc_csd > 0) {
                                this.$root.toastError('Thay đổi khuyến mại Đặt cọc. Bạn không được nhập tiền đặt cọc !');
                                check = false;
                                return;
                            }
                            if (this.dsChiTietKM[index].huong_dc != undefined)
                                ithang_huongdc = this.dsChiTietKM[index].huong_dc;
                            if (this.dsChiTietKM[index].huong_km != undefined)
                                ithang_huongkm = this.dsChiTietKM[index].huong_km;
                            if (this.dsChiTietKM[index].sothang_tg != undefined)
                                ithang_tg = this.dsChiTietKM[index].sothang_tg;
                            dtiendatcoc_csd = this.dsChiTietKM[index].datcoc_csd;
                            this.i_tien_sd = this.dsChiTietKM[index].tien_sd;
                            this.i_tien_tb = this.dsChiTietKM[index].tien_tb;
                            this.i_cuoc_sd = this.dsChiTietKM[index].cuoc_sd;
                            this.i_cuoc_tb = this.dsChiTietKM[index].cuoc_tb;
                            this.i_tyle_sd = this.dsChiTietKM[index].tyle_sd;
                            this.i_tyle_tb = this.dsChiTietKM[index].tyle_tb;
                            dtien_td = ithang_huongdc > 0 ? Math.round(dtiendatcoc_csd / ithang_huongdc) : 0;
                            if (this.dsChiTietKM[index].kieu_bddc != undefined)
                                loai_bddc = this.dsChiTietKM[index].kieu_bddc;
                            if (this.dsChiTietKM[index].sothang_bddc != undefined)
                                sothang_bddc = this.dsChiTietKM[index].sothang_bddc;
                            let obj = {
                                khuyenmai_id: this.khuyenmai_id,
                                chitietkm_id: this.dsChiTietKM[index].chitietkm_id,
                                kieu_yc: 1,
                                thang_huongdc: ithang_huongdc,
                                thang_huongkm: ithang_huongkm,
                                sothang_tg: ithang_tg,
                                datcoc_csd: dtiendatcoc_csd,
                                tien_td: dtien_td,
                                tien_sd: this.i_tien_sd,
                                tien_tb: this.i_tien_tb,
                                cuoc_sd: this.i_cuoc_sd,
                                cuoc_tb: this.i_cuoc_tb,
                                tyle_sd: this.i_tyle_sd,
                                tyle_tb: this.i_tyle_tb,
                                ngay_bd: DateTimeLib.toDateDisplay(this.dsChiTietKM[index].ngay_bd),
                                ngay_kt: DateTimeLib.toDateDisplay(this.dsChiTietKM[index].ngay_kt),
                                ngay_bddc: DateTimeLib.toDateDisplay(this.dsChiTietKM[index].ngay_bddc),
                                ngay_ktdc: DateTimeLib.toDateDisplay(this.dsChiTietKM[index].ngay_ktdc),
                                thang_kt: 0,
                                thang_bd: 0,
                                thang_bddc: 0,
                                thang_ktdc: 0,
                                thang_bdtg: 0,
                                thang_kttg: 0
                            }
                            if (this.dsChiTietKM[index].loai_bdkm == 3) { // kieu_bdkm = loai_bdkm
                                let ngayHT = DateTimeLib.toDateObjectWithFormat(ngayCn, "YYYY-MM-DD HH:mm:ss");
                                if (this.dsChiTietKM[index].ngay_bddc != null
                                    && this.dsChiTietKM[index].ngay_bddc.toString() != "") {
                                    this.dsChiTietKM[index].thang_bdkm = this.yyyymm(this.dsChiTietKM[index].ngay_bddc);
                                }
                                else {
                                    // if (this.is_tinhcuoc_ngay) {
                                    //     this.$root.toastError('Bạn phải chọn Ngày BĐĐC');
                                    //     check = false;
                                    //     return;
                                    // }
                                }
                                if (this.dsChiTietKM[index].thang_bdkm == null) {
                                    this.$root.toastError('Bạn phải nhập tháng bắt đầu khuyến mại');
                                    check = false;
                                    return;
                                }
                                let utc = moment(this.dsChiTietKM[index].thang_bdkm, 'YYYYMM', true);
                                if (!utc.isValid()) {
                                    this.$root.toastError('Bạn phải nhập tháng bắt đầu khuyến mại theo định dạng yyyyMM');
                                    check = false;
                                    return;
                                }
                                if (parseInt(this.dsChiTietKM[index].thang_bdkm) < parseInt(moment(ngayHT).format('YYYYMM'))) {
                                    this.$root.toastError('Tháng bắt đầu khuyến mại phải lớn hơn hoặc bằng với tháng hiện tại.');
                                    check = false;
                                    return;
                                }
                                if (loai_bddc == 1) {
                                    ngayHT.setMonth(ngayHT.getMonth() + sothang_bddc);
                                    obj.thang_bd = this.yyyymm(ngayHT);
                                    ngayHT.setMonth(ngayHT.getMonth() + ithang_huongdc - 1);
                                    obj.thang_kt = this.yyyymm(ngayHT);
                                }
                                else if (loai_bddc == 3) {
                                    obj.thang_bd = this.dsChiTietKM[index].thang_bdkm;
                                    ngayHT.setMonth(ngayHT.getMonth() + ithang_huongkm - 1);
                                    obj.thang_kt = this.yyyymm(ngayHT);
                                }
                                else {
                                    obj.thang_bd = this.dsChiTietKM[index].thang_bdkm;
                                    ngayHT.setMonth(ngayHT.getMonth() + ithang_huongkm - 1);
                                    obj.thang_kt = this.yyyymm(ngayHT);
                                }
                            }

                            if (this.dsChiTietKM[index].kieu_bdtg == 3) {
                                let ngayHT = DateTimeLib.toDateObjectWithFormat(ngayCn, "YYYY-MM-DD HH:mm:ss");
                                if (this.dsChiTietKM[index].thang_bdtg == null) {
                                    this.$root.toastError('Bạn phải nhập tháng bắt đầu trả góp');
                                    check = false;
                                    return;
                                }
                                let utc = moment(this.dsChiTietKM[index].thang_bdtg, 'YYYYMM', true);
                                if (!utc.isValid()) {
                                    this.$root.toastError('Bạn phải nhập tháng bắt đầu trả góp theo định dạng yyyyMM');
                                    check = false;
                                    return;
                                }
                                obj.thang_bdtg = this.dsChiTietKM[index].thang_bdtg;
                                ngayHT.setMonth(ngayHT.getMonth() + ithang_tg - 1);
                                obj.thang_kttg = this.yyyymm(ngayHT);
                            }

                            if (this.dsChiTietKM[index].loai_bddc == 3) { // kieu_bddc = loai_bddc
                                let ngayHT = DateTimeLib.toDateObjectWithFormat(ngayCn, "YYYY-MM-DD HH:mm:ss");
                                if (this.dsChiTietKM[index].ngay_bddc != null
                                    && this.dsChiTietKM[index].ngay_bddc.toString() != "") {
                                    this.dsChiTietKM[index].thang_bddc = this.yyyymm(this.dsChiTietKM[index].ngay_bddc);
                                }
                                else {
                                    if (this.is_tinhcuoc_ngay) {
                                        this.$root.toastError('Bạn phải chọn Ngày BĐĐC');
                                        check = false;
                                        return;
                                    }
                                }
                                if (this.dsChiTietKM[index].thang_bddc == null) {
                                    this.$root.toastError('Bạn phải nhập tháng bắt đầu trả trước');
                                    check = false;
                                    return;
                                }
                                let utc = moment(this.dsChiTietKM[index].thang_bddc, 'YYYYMM', true);
                                if (!utc.isValid()) {
                                    this.$root.toastError('Bạn phải nhập tháng bắt đầu đặt cọc theo định dạng yyyyMM');
                                    check = false;
                                    return;
                                }
                                if (parseInt(this.dsChiTietKM[index].thang_bddc) < parseInt(moment(ngayHT).format('YYYYMM'))) {
                                    this.$root.toastError('Tháng bắt đầu đặt cọc phải lớn hơn hoặc bằng với tháng hiện tại.');
                                    check = false;
                                    return;
                                }
                                obj.thang_bddc = this.dsChiTietKM[index].thang_bddc;
                                ngayHT.setMonth(ngayHT.getMonth() + ithang_huongdc - 1);
                                obj.thang_ktdc = this.yyyymm(ngayHT);

                                obj.thang_bd = obj.thang_bddc;
                                obj.thang_kt = obj.thang_ktdc;

                            }
                            if (loai_bddc == 1) {
                                let ngayHT = DateTimeLib.toDateObjectWithFormat(ngayCn, "YYYY-MM-DD HH:mm:ss");
                                if (this.dsChiTietKM[index].ngay_bddc != null
                                    && this.dsChiTietKM[index].ngay_bddc.toString() != "") {
                                    this.dsChiTietKM[index].thang_bddc = this.yyyymm(this.dsChiTietKM[index].ngay_bddc);
                                }
                                else {
                                    if (this.is_tinhcuoc_ngay) {
                                        this.$root.toastError('Bạn phải chọn Ngày BĐĐC');
                                        check = false;
                                        return;
                                    }
                                }
                                ngayHT.setMonth(ngayHT.getMonth() + sothang_bddc);
                                obj.thang_bddc = this.yyyymm(ngayHT);
                                ngayHT.setMonth(ngayHT.getMonth() + ithang_huongdc - 1);
                                obj.thang_ktdc = this.yyyymm(ngayHT);

                                obj.thang_bd = obj.thang_bddc;
                                obj.thang_kt = obj.thang_ktdc;
                            }

                            if (this.dsChiTietKM[index].tyle_sd === "") {
                                this.$root.toastError('Tỷ lệ sử dụng không được để trống!');
                                check = false;
                                return;
                            }
                            if (this.dsChiTietKM[index].tyle_tb === "") {
                                this.$root.toastError('Tỷ lệ thuê bao không được để trống!');
                                check = false;
                                return;
                            }
                            if (this.dsChiTietKM[index].tyle_kmld === "") {
                                this.$root.toastError('Tỷ lệ khuyến mại lắp đặt không được để trống!');
                                check = false;
                                return;
                            }

                            //Với khuyến mại cho sửa khuyến mại cước
                            if (this.dsChiTietKM[index].kieu_km == 1) {
                                if ((this.dichvuvt_id == this.DichVuVienThong.ANTOAN_BAOMAT_TT
                                    || this.dichvuvt_id == this.DichVuVienThong.DICHVU_CNTT
                                    || this.dichvuvt_id == this.DichVuVienThong.TRUNGTAM_DULIEU
                                    || this.dichvuvt_id == this.DichVuVienThong.HOINGHI_TRUYENHINH)
                                    && this.dsChiTietKM[index].nhom_km != "") {
                                    // LongDX - Hoangpkn : 19/11/2019
                                    //Đối với riêng các dịch vụ CNTT của chị An
                                    //Và đối thì các khuyến mại tự nhập mà có nhóm
                                    //Em sửa lại cho phép để =0 hết nhé
                                }
                                else {
                                    if (this.i_tien_sd + this.i_tien_tb + this.i_cuoc_sd + this.i_cuoc_tb + this.i_tyle_sd + this.i_tyle_tb == 0) {
                                        this.$root.toastError("Bạn chưa nhập thông tin khuyến mại!");
                                        return;
                                    }
                                }
                            }

                            if (this.i_tyle_sd > 100 || this.i_tyle_tb > 100) {
                                this.$root.toastError('Tỷ lệ không được vượt quá 100!');
                                check = false;
                                return;
                            }
                            if ((this.i_tien_sd + this.i_tien_tb > 0 && this.i_cuoc_sd + this.i_cuoc_tb > 0) ||
                                (this.i_tien_sd + this.i_tien_tb > 0 && this.i_tyle_sd + this.i_tyle_tb > 0) ||
                                (this.i_cuoc_sd + this.i_cuoc_tb > 0 && this.i_tyle_sd + this.i_tyle_tb > 0)) {
                                this.$root.toastError('Bạn không được nhập đồng thời nhiều hình thức khuyến mại!');
                                check = false;
                                return;
                            }

                            //Với khuyến mại cho sửa khuyến mại cước
                            if (this.dsChiTietKM[index].kieu_km == 1) {
                                if (this.ts_kiemtra_dinhmuc == 1) {
                                    let kt = await this.Kiemtra_DinhMuc(this.dsChiTietKM[index].chitietkm_id, this.i_tien_sd, this.i_tien_tb, this.i_cuoc_sd, this.i_cuoc_tb, this.i_tyle_sd, this.i_tyle_tb);
                                    if (!kt) {
                                        return;
                                    }
                                }
                            }

                            obj.tyle_kmld = this.dsChiTietKM[index].tyle_km;

                            dsKM_HDTB_New.push(obj);
                            //Lấy tiền khuyến mại
                            if (this.tien_kmld < this.dsChiTietKM[index].tien) {
                                this.tien_kmld = this.dsChiTietKM[index].tien;
                            }
                            if (this.vat_kmld < this.dsChiTietKM[index].vat) {
                                this.vat_kmld = this.dsChiTietKM[index].vat;
                            }
                            if (this.tyle_kmld < this.dsChiTietKM[index].tyle_km) {
                                this.tyle_kmld = this.dsChiTietKM[index].tyle_km;
                            }
                            this.tien_datcoc += parseFloat(this.dsChiTietKM[index].datcoc_csd);
                        }
                    };
                }
                else {
                    let dsDC_HDTB_new = [];
                    this.dsChiTietKM.forEach(element, index => {
                        if (element.dangky == 1) {
                            if (this.dsChiTietKM[index].thang_huongdc != undefined)
                                ithang_huongdc = this.dsChiTietKM[index].thang_huongdc;
                            if (this.dsChiTietKM[index].thang_huongkm != undefined)
                                ithang_huongkm = this.dsChiTietKM[index].thang_huongkm;
                            dtiendatcoc_csd = this.dsChiTietKM[index].datcoc_csd;
                            this.i_tien_sd = this.dsChiTietKM[index].tien_sd;
                            this.i_tien_tb = this.dsChiTietKM[index].tien_tb;
                            this.i_cuoc_sd = this.dsChiTietKM[index].cuoc_sd;
                            this.i_cuoc_tb = this.dsChiTietKM[index].cuoc_tb;
                            this.i_tyle_sd = this.dsChiTietKM[index].tyle_sd;
                            this.i_tyle_tb = this.dsChiTietKM[index].tyle_tb;
                            dtien_td = ithang_huongdc > 0 ? Math.round(dtiendatcoc_csd / ithang_huongdc) : 0;
                            if (this.dsChiTietKM[index].kieu_bddc != undefined)
                                loai_bddc = this.dsChiTietKM[index].kieu_bddc;
                            if (this.dsChiTietKM[index].sothang_bddc != undefined)
                                sothang_bddc = this.dsChiTietKM[index].sothang_bddc;
                            let obj = {
                                khuyenmai_id: this.khuyenmai_id,
                                chitietkm_id: this.dsChiTietKM[index].chitietkm_id,
                                kieu_yc: 1,
                                thang_huongdc: ithang_huongdc,
                                thang_huongkm: ithang_huongkm,
                                sothang_tg: ithang_tg,
                                datcoc_csd: dtiendatcoc_csd,
                                tien_td: dtien_td,
                                tien_sd: this.i_tien_sd,
                                tien_tb: this.i_tien_tb,
                                cuoc_sd: this.i_cuoc_sd,
                                cuoc_tb: this.i_cuoc_tb,
                                tyle_sd: this.i_tyle_sd,
                                tyle_tb: this.i_tyle_tb,
                                ngay_bd: DateTimeLib.toDateDisplay(this.dsChiTietKM[index].ngay_bd),
                                ngay_kt: DateTimeLib.toDateDisplay(this.dsChiTietKM[index].ngay_kt),
                                ngay_bddc: DateTimeLib.toDateDisplay(this.dsChiTietKM[index].ngay_bddc),
                                ngay_ktdc: DateTimeLib.toDateDisplay(this.dsChiTietKM[index].ngay_ktdc),
                                thang_kt: 0,
                                thang_bd: 0,
                                thang_bddc: 0,
                                thang_ktdc: 0,
                                thang_bdtg: 0,
                                thang_kttg: 0
                            }
                            if (this.dsChiTietKM[index].loai_bdkm == 3) { // kieu_bdkm = loai_bdkm
                                let ngayHT = DateTimeLib.toDateObjectWithFormat(ngayCn, "YYYY-MM-DD HH:mm:ss");
                                if (this.dsChiTietKM[index].ngay_bddc != null
                                    && this.dsChiTietKM[index].ngay_bddc.toString() != "") {
                                    this.dsChiTietKM[index].thang_bdkm = this.yyyymm(this.dsChiTietKM[index].ngay_bddc);
                                }
                                if (this.dsChiTietKM[index].thang_bdkm == null) {
                                    this.$root.toastError('Bạn phải nhập tháng bắt đầu khuyến mại');
                                    check = false;
                                    return;
                                }
                                let utc = moment(this.dsChiTietKM[index].thang_bdkm, 'YYYYMM', true);
                                if (!utc.isValid()) {
                                    this.$root.toastError('Bạn phải nhập tháng bắt đầu khuyến mại theo định dạng yyyyMM');
                                    check = false;
                                    return;
                                }
                                if (parseInt(this.dsChiTietKM[index].thang_bdkm) < parseInt(moment(ngayHT).format('YYYYMM'))) {
                                    this.$root.toastError('Tháng bắt đầu khuyến mại phải lớn hơn hoặc bằng với tháng hiện tại.');
                                    check = false;
                                    return;
                                }
                                obj.thang_bd = this.dsChiTietKM[index].thang_bdkm;
                                ngayHT.setMonth(ngayHT.getMonth() + ithang_huongkm - 1);
                                obj.thang_kt = this.yyyymm(ngayHT);
                            }

                            if (this.dsChiTietKM[index].kieu_bdtg == 3) {
                                let ngayHT = DateTimeLib.toDateObjectWithFormat(ngayCn, "YYYY-MM-DD HH:mm:ss");
                                if (this.dsChiTietKM[index].thang_bdtg == null) {
                                    this.$root.toastError('Bạn phải nhập tháng bắt đầu trả góp');
                                    check = false;
                                    return;
                                }
                                let utc = moment(this.dsChiTietKM[index].thang_bdtg, 'YYYYMM', true);
                                if (!utc.isValid()) {
                                    this.$root.toastError('Bạn phải nhập tháng bắt đầu trả góp theo định dạng yyyyMM');
                                    check = false;
                                    return;
                                }
                                obj.thang_bdtg = this.dsChiTietKM[index].thang_bdtg;
                                ngayHT.setMonth(ngayHT.getMonth() + ithang_tg - 1);
                                obj.thang_kttg = this.yyyymm(ngayHT);
                            }

                            if (this.dsChiTietKM[index].kieu_bddc == 3) { // kieu_bddc = loai_bddc
                                let ngayHT = DateTimeLib.toDateObjectWithFormat(ngayCn, "YYYY-MM-DD HH:mm:ss");
                                if (this.dsChiTietKM[index].ngay_bddc != null
                                    && this.dsChiTietKM[index].ngay_bddc.toString() != "") {
                                    this.dsChiTietKM[index].thang_bddc = this.yyyymm(this.dsChiTietKM[index].ngay_bddc);
                                }
                                else {
                                    if (this.is_tinhcuoc_ngay) {
                                        this.$root.toastError('Bạn phải chọn Ngày BĐĐC');
                                        check = false;
                                        return;
                                    }
                                }
                                if (this.dsChiTietKM[index].thang_bddc == null) {
                                    this.$root.toastError('Bạn phải nhập tháng bắt đầu trả trước');
                                    check = false;
                                    return;
                                }
                                let utc = moment(this.dsChiTietKM[index].thang_bddc, 'YYYYMM', true);
                                if (!utc.isValid()) {
                                    this.$root.toastError('Bạn phải nhập tháng bắt đầu đặt cọc theo định dạng yyyyMM');
                                    check = false;
                                    return;
                                }
                                if (parseInt(this.dsChiTietKM[index].thang_bddc) < parseInt(moment(new Date()).format('YYYYMM'))) {
                                    this.$root.toastError('Tháng bắt đầu đặt cọc phải lớn hơn hoặc bằng với tháng hiện tại.');
                                    check = false;
                                    return;
                                }
                                obj.thang_bddc = this.dsChiTietKM[index].thang_bddc;
                                ngayHT.setMonth(ngayHT.getMonth() + ithang_huongdc - 1);
                                obj.thang_ktdc = this.yyyymm(ngayHT);
                                obj.thang_bd = obj.thang_bddc;
                                obj.thang_kt = obj.thang_ktdc;
                            }
                            if (loai_bddc == 1) {
                                let ngayHT = DateTimeLib.toDateObjectWithFormat(ngayCn, "YYYY-MM-DD HH:mm:ss");
                                if (this.dsChiTietKM[index].ngay_bddc != null
                                    && this.dsChiTietKM[index].ngay_bddc.toString() != "") {
                                    this.dsChiTietKM[index].thang_bddc = this.yyyymm(this.dsChiTietKM[index].ngay_bddc);
                                }
                                else {
                                    if (this.is_tinhcuoc_ngay) {
                                        this.$root.toastError('Bạn phải chọn Ngày BĐĐC');
                                        check = false;
                                        return;
                                    }
                                }
                                ngayHT.setMonth(ngayHT.getMonth() + sothang_bddc);
                                obj.thang_bddc = this.yyyymm(ngayHT);
                                ngayHT.setMonth(ngayHT.getMonth() + ithang_huongdc - 1);
                                obj.thang_ktdc = this.yyyymm(ngayHT);

                                obj.thang_bd = obj.thang_bddc;
                                obj.thang_kt = obj.thang_ktdc;
                            }

                            if (this.dsChiTietKM[index].tyle_sd === "") {
                                this.$root.toastError('Tỷ lệ sử dụng không được để trống!');
                                check = false;
                                return;
                            }
                            if (this.dsChiTietKM[index].tyle_tb === "") {
                                this.$root.toastError('Tỷ lệ thuê bao không được để trống!');
                                check = false;
                                return;
                            }
                            if (this.i_tyle_sd > 100 || this.i_tyle_tb > 100) {
                                this.$root.toastError('Tỷ lệ không được vượt quá 100!');
                                check = false;
                                return;
                            }
                            if (this.dsChiTietKM[index].tyle_kmld === "") {
                                this.$root.toastError('Tỷ lệ khuyến mại lắp đặt không được để trống!');
                                check = false;
                                return;
                            }
                            if (this.dsChiTietKM[index].ngay_bd != null &&
                                this.dsChiTietKM[index].ngay_kt != null &&
                                this.dsChiTietKM[index].ngay_bd.toString() != "" &&
                                this.dsChiTietKM[index].ngay_kt.toString() != "") {
                                if (this.dsChiTietKM[index].ngay_kt
                                    < this.dsChiTietKM[index].ngay_bd) {
                                    this.$root.toastError('Ngày KT không thể nhỏ hơn Ngày BĐ!');
                                    check = false;
                                    return;
                                }
                            }

                            if (this.is_tinhcuoc_ngay && (loai_bddc == 1 || loai_bddc == 3)) {
                                if (this.dsChiTietKM[index].ngay_bddc != null) {
                                    if (obj.thang_bddc != null) {
                                        if (this.yyyymm(this.dsChiTietKM[index].ngay_bddc).ToString("yyyyMM")
                                            != obj.thang_bddc.toString()) {
                                            this.$root.toastError('Ngày BĐĐC phải nằm trong tháng BĐĐC!');
                                            check = false;
                                            return;
                                        }
                                    }
                                    else {
                                        this.$root.toastError('Bạn chưa chọn tháng BĐĐC');
                                        check = false;
                                        return;
                                    }

                                }
                            }

                            //Với khuyến mại cho sửa khuyến mại cước
                            if (this.dsChiTietKM[index].kieu_km == 1) {
                                if ((this.dichvuvt_id == this.DichVuVienThong.ANTOAN_BAOMAT_TT
                                    || this.dichvuvt_id == this.DichVuVienThong.DICHVU_CNTT
                                    || this.dichvuvt_id == this.DichVuVienThong.TRUNGTAM_DULIEU
                                    || this.dichvuvt_id == this.DichVuVienThong.HOINGHI_TRUYENHINH)
                                    && this.dsChiTietKM[index].nhom_km != "") {
                                    // LongDX - Hoangpkn : 19/11/2019
                                    //Đối với riêng các dịch vụ CNTT của chị An
                                    //Và đối thì các khuyến mại tự nhập mà có nhóm
                                    //Em sửa lại cho phép để =0 hết nhé
                                }
                                else {
                                    if (this.i_tien_sd + this.i_tien_tb + this.i_cuoc_sd + this.i_cuoc_tb + this.i_tyle_sd + this.i_tyle_tb == 0) {
                                        this.$root.toastError("Bạn chưa nhập thông tin khuyến mại!");
                                        return;
                                    }
                                }
                                if ((this.i_tien_sd + this.i_tien_tb > 0 && this.i_cuoc_sd + this.i_cuoc_tb > 0) ||
                                    (this.i_tien_sd + this.i_tien_tb > 0 && this.i_tyle_sd + this.i_tyle_tb > 0) ||
                                    (this.i_cuoc_sd + this.i_cuoc_tb > 0 && this.i_tyle_sd + this.i_tyle_tb > 0)) {
                                    this.$root.toastError('Bạn không được nhập đồng thời nhiều hình thức khuyến mại!');
                                    check = false;
                                    return;
                                }
                            }

                            obj.tyle_kmld = this.dsChiTietKM[index].tyle_km;
                            if (this.is_tinhcuoc_ngay) {
                                if (this.dsChiTietKM[index].ngay_bddc != null && this.dsChiTietKM[index].ngay_bddc.toString() != "") {
                                    obj.ngay_bddc = DateTimeLib.toDateDisplay(this.dsChiTietKM[index].ngay_bddc);
                                }
                                let a = new Date(this.dsChiTietKM[index].ngay_bddc.getYear(), this.dsChiTietKM[index].ngay_bddc.getMonth(), this.dsChiTietKM[index].ngay_bddc.getDate());
                                a.setMonth(a.getMonth() + ithang_huongdc);
                                a.setDate(a.getDate() - 1);
                                obj.ngay_ktdc = a;
                                obj.ngay_bd = obj.ngay_bddc;
                                obj.ngay_kt = obj.ngay_ktdc
                            }
                            dsDC_HDTB_new.push(obj);
                            //Lấy tiền khuyến mại
                            if (this.tien_kmld_tt < this.dsChiTietKM[index].tien) {
                                this.tien_kmld_tt = this.dsChiTietKM[index].tien;
                            }
                            if (this.vat_kmld_tt < this.dsChiTietKM[index].vat) {
                                this.vat_kmld_tt = this.dsChiTietKM[index].vat;
                            }
                            if (this.tyle_kmld_tt < this.dsChiTietKM[index].tyle_km) {
                                this.tyle_kmld_tt = this.dsChiTietKM[index].tyle_km;
                            }

                            if (this.ht_kmtt) {
                                let tongtien = parseInt(this.dsChiTietKM[index].datcoc_csd);
                                let tyle_vat = parseInt(this.dsChiTietKM[index].tyle_vat);
                                let tien = Math.round(tongtien * 100 / (100 + tyle_vat));
                                let vat = Math.random(tien * tyle_vat / 100);
                                if (tongtien != tien + vat) {
                                    tien = tongtien - vat;
                                }
                                this.tien_datcoc_tt += tien;
                                this.vat_datcoc_tt += vat;
                                this.m_dsTienCTKM.push({
                                    khoanmuctt_id: 11,
                                    tien: tien,
                                    vat: vat,
                                    danhdau: 0,
                                    id: this.dsChiTietKM[index].chitietkm_id,
                                    tyle_vat: tyle_vat,
                                    tyle_vat_id: this.dsChiTietKM[index].tyle_vat_id,
                                    loai_id: 1,
                                    ten_ctkm: this.dsChiTietKM[index].ten_ctkm,
                                    kieu: 1
                                })
                            }
                            else {
                                this.tien_datcoc_tt += parseFloat(this.dsChiTietKM[index].datcoc_csd);
                            }
                        }
                    });
                }
                if (check) {
                    this.$emit('chapNhan', dsKM_HDTB_New);
                    this.$bvModal.hide(this.idModal);
                }
            } catch (error) {
                console.log(error);
            }
        },
        dangKy: function (id, check) {
            this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.endEdit();
            let index = this.dsChiTietKM.findIndex(v => v.chitietkm_id == id);
            if (index >= 0) {
                this.dsChiTietKM[index].dangky = check ? 1 : 0;
            }
        },
        tomau: function () {
            let length = this.dsChiTietKM.length;
            for (var i = 0; i < length; i++) {
                if (this.dsChiTietKM[i].loai_bdkm != 3) { // kieu_bdkm = loai_bdkm
                    this.allowEdit.thangBDKM = false; // ReadOnly = true;
                }
                else {
                    this.allowEdit.thangBDKM = true;
                }

                if (this.dsChiTietKM[i].kieu_kmld != 1) {
                    this.allowEdit.tyleKM = false;
                }
                else {
                    this.allowEdit.tyleKM = true;
                }

                if (this.dsChiTietKM[i].kieu_tratruoc != 1) {
                    this.allowEdit.datcocCSD = false;
                }
                else {
                    this.allowEdit.datcocCSD = true;
                }

                if (this.dsChiTietKM[i].kieu_bdtg != 3) {
                    this.allowEdit.thangBDTG = false;
                }
                else {
                    this.allowEdit.thangBDTG = true;
                }

                if (this.dsChiTietKM[i].loai_bddc != 3) { // kieu_bddc = loai_bddc
                    this.allowEdit.thangBDDC = false;
                }
                else {
                    this.allowEdit.thangBDDC = true;
                }
            }
        },
        onCellSavedCTKM: function (args) {
            console.log(args);
            let ChiTietKMGrid = this.$refs['gridDsChiTietKM']
            let changes = ChiTietKMGrid.getBatchChanges()
            ChiTietKMGrid.batchUpdate(changes);
        },
        khuyenmaiChanged: function (data) {
            console.log(this.dsChiTietKM);
            // console.log(data.rowData);
            // console.log(this.dsChiTietKM);
        },
        actionComplete: function (args) {
            // console.log(args);
            if (args.requestType === "beginEdit") {
                if (args.rowData['loai_bdkm'] != 3) {
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'thang_bdkm'].readOnly = true;
                }
                else {
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'thang_bdkm'].readOnly = false;
                }

                if (args.rowData['kieu_kmld'] != 1) {
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'tyle_kmld'].readOnly = true;
                }
                else {
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'tyle_kmld'].readOnly = false;
                }

                if (args.rowData['kieu_tratruoc'] != 1) {
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'datcoc_csd'].readOnly = true;
                }
                else {
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'datcoc_csd'].readOnly = false;
                }

                if (args.rowData['kieu_bdtg'] != 3) {
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'thang_bdtg'].readOnly = true;
                }
                else {
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'thang_bdtg'].readOnly = false;
                }

                if (args.rowData['loai_bddc'] != 3) {
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'thang_bddc'].readOnly = true;
                }
                else {
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'thang_bddc'].readOnly = false;
                }

                if (args.rowData['kieu_km'] != 1) {
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'tyle_sd'].readOnly = true;
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'tyle_tb'].readOnly = true;
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'tien_sd'].readOnly = true;
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'tien_tb'].readOnly = true;
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'cuoc_sd'].readOnly = true;
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'cuoc_tb'].readOnly = true;
                }
                else {
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'tyle_sd'].readOnly = false;
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'tyle_tb'].readOnly = false;
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'tien_sd'].readOnly = false;
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'tien_tb'].readOnly = false;
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'cuoc_sd'].readOnly = false;
                    args.form.elements[this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.getAttribute("id") + 'cuoc_tb'].readOnly = false;
                }
            }
        },
        formatPrice(value) {
            let val = (value / 1).toFixed(0).replace('.', ',')
            return val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".")
        },
        async Kiemtra_DinhMuc(kt_ctkm_id, kt_tien_sd, kt_tien_tb, kt_cuoc_sd, kt_cuoc_tb, kt_tyle_sd, kt_tyle_tb) {
            //TODO line 1393
            //ds_kt = tchopdong.LAY_CTKM_DINHMUC(kt_ctkm_id);
            var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/lay_ctkm_dinhmuc", { "chitiet_km_id": kt_ctkm_id });
            var ds_kt = rs.data;
            if (ds_kt && ds_kt.length > 0) {
                var _tien_sd_min = ds_kt[0]["tien_sd_min"];
                var _tien_sd_max = ds_kt[0]["tien_sd_max"];
                var _cuoc_sd_min = ds_kt[0]["cuoc_sd_min"];
                var _cuoc_sd_max = ds_kt[0]["cuoc_sd_max"];
                var _tyle_sd_min = ds_kt[0]["tyle_sd_min"];
                var _tyle_sd_max = ds_kt[0]["tyle_sd_max"];
                var _tien_tb_min = ds_kt[0]["tien_tb_min"];
                var _tien_tb_max = ds_kt[0]["tien_tb_max"];
                var _cuoc_tb_min = ds_kt[0]["cuoc_tb_min"];
                var _cuoc_tb_max = ds_kt[0]["cuoc_tb_max"];
                var _tyle_tb_min = ds_kt[0]["tyle_tb_min"];
                var _tyle_tb_max = ds_kt[0]["tyle_tb_max"];
                // Kiểm tra tiền sử dụng ( tiền áp )
                if (kt_tien_sd > 0 && (_tien_sd_min >= 0 || _tien_sd_max >= 0)) {
                    if (kt_tien_sd < _tien_sd_min || kt_tien_sd > _tien_sd_max) {
                        this.$root.toastError("Tiền SD không đúng định mức cho phép : " + this.formatPrice(_tien_sd_min) + " -> " + this.formatPrice(_tien_sd_max) + " !");
                        return false;
                    }
                }

                // Kiểm tra tiền thuê bao ( tiền áp )
                if (kt_tien_tb > 0 && (_tien_tb_min >= 0 || _tien_tb_max >= 0)) {
                    if (kt_tien_tb < _tien_tb_min || kt_tien_tb > _tien_tb_max) {
                        this.$root.toastError("Tiền TB không đúng định mức cho phép : " + this.formatPrice(_tien_tb_min) + " -> " + this.formatPrice(_tien_tb_max) + " !");
                        return false;
                    }
                }

                // Kiểm tra cước sử dụng (tiền giảm cho khách hàng)
                if (kt_cuoc_sd > 0 && (_cuoc_sd_min >= 0 || _cuoc_sd_max >= 0)) {
                    if (kt_cuoc_sd < _cuoc_sd_min || kt_cuoc_sd > _cuoc_sd_max) {
                        this.$root.toastError("Giảm cước SD không đúng định mức cho phép : " + this.formatPrice(_cuoc_sd_min) + " -> " + this.formatPrice(_cuoc_sd_max) + " !");
                        return false;
                    }
                }

                // Kiểm tra cước thuê bao ( tiền áp )
                if (kt_cuoc_tb > 0 && (_cuoc_tb_min >= 0 || _cuoc_tb_max >= 0)) {
                    if (kt_cuoc_tb < _cuoc_tb_min || kt_cuoc_tb > _cuoc_tb_max) {
                        this.$root.toastError("Giảm cước TB không đúng định mức cho phép : " + this.formatPrice(_cuoc_tb_min) + " -> " + this.formatPrice(_cuoc_tb_max) + " !");
                        return false;
                    }
                }

                // Kiểm tra tỷ lệ sử dụng ( tỷ lệ giảm )
                if (kt_tyle_sd > 0 && (_tyle_sd_min >= 0 || _tyle_sd_max >= 0)) {
                    if (kt_tyle_sd < _tyle_sd_min || kt_tyle_sd > _tyle_sd_max) {
                        this.$root.toastError("Tỷ lệ SD không đúng định mức cho phép : " + _tyle_sd_min + " -> " + _tyle_sd_max + " !");
                        return false;
                    }
                }

                // Kiểm tra tỷ lệ thuê bao ( tỷ lệ áp )
                if (kt_tyle_tb > 0 && (_tyle_tb_min >= 0 || _tyle_tb_max >= 0)) {
                    if (kt_tyle_tb < _tyle_tb_min || kt_tyle_tb > _tyle_tb_max) {
                        this.$root.toastError("Tỷ lệ TB không đúng định mức cho phép : " + _tyle_sd_min + " -> " + _tyle_sd_max + " !");
                        return false;
                    }
                }
                return true;
            }
            else
                return true;
        },
        getDateElements(date) {

            date = new Date(date)

            let day = date.getDate()
            day = day > 9 ? day : '0' + day

            let month = date.getMonth() + 1
            month = month > 9 ? month : '0' + month

            let hour = date.getHours()
            hour = hour > 9 ? hour : '0' + hour

            let min = date.getMinutes()
            min = min > 9 ? min : '0' + min

            let second = date.getSeconds()
            second = second > 9 ? second : '0' + second

            return {
                y: date.getFullYear(),
                M: month,
                d: day,
                h: hour,
                m: min,
                s: second
            }
        },
        yyyymm(date) {

            if (!date) {
                return ''
            }

            date = this.getDateElements(date)
            return `${date.y}${date.M}`
        },
        async lay_ngay_cn() {
            try {
                let response = await this.$root.context.get(`/web-hopdong/ThayDoiDatCocHuy/lay_ngay_cn`)
                return !response.data ? null : response.data.ngay_cn
            } catch (err) {
                return null
            }
        },
        loadEvent: function () {
            console.log(123, this.$refs);
            try {
                this.$refs.gridDsChiTietKM.$refs.grid.ej2Instances.element.addEventListener('mouseup', function (e) {
                    // console.log(e);
                    var instance = this.ej2_instances[0];
                    if (e.target.classList.contains("e-rowcell")) {
                        if (instance.isEdit)
                            instance.endEdit();
                        let index = parseInt(e.target.getAttribute("Index"));
                        instance.selectRow(index);
                        instance.startEdit();
                    };
                });
            } catch (error) {
                console.log(error);
            }
        }
    },
    watch: {
        dsCTKMId: function (newVal, oldVal) {
        }
    }
}
</script>
