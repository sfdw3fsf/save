<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Tạo mã in ghép</div>
        <div @click="$emit('form-close', true)" class="close -ap icon-close" data-dismiss="modal">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="TaoMoi">
                    <span class="icon nc-icon-glyph ui-1_circle-add"></span>Tạo mới
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="tsbtnChonMIG_Click">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chọn
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="Xoa">
                    <span class="icon one-trash"></span> Xóa
                </a>
            </li>

        </ul>
    </div>
    <div class="popup-body">
        <div class="box-form">
            <div class="legend-title">
                <div class="pull-left">
                    Thông tin tạo mã tạo ghép
                </div>
                <div class="pull-right red">
                    Thanh toán: {{ma_tt}} - {{ten_tt}}
                </div>
                <div class="clearfix"></div>

            </div>
            <div class="row">
                <div class="col-sm-4 col-12">
                    <div class="info-row">
                        <div class="key w90">Mã in ghép</div>
                        <div class="value">
                            <input type="text" class="form-control highlight">
                        </div>
                    </div>
                </div>
                <div class="col-sm-8 col-12">
                    <div class="info-row">
                        <div class="key w90">Đơn vị quản lý</div>
                        <div class="value">
                            <div class="select-custom">
                                <select2 v-model="listbox.donviql.value" class="select-custom" :options="listbox.donviql.list.map(dd=>({id:dd.donvi_id,text:dd.ten_dv}))"></select2>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-4 col-12">
                    <div class="info-row">
                        <PopupTimKiemNhanVienThuCuoc :input="diachiIG" @acceptChange="getNVTCProcess($event)" ref="dlgTimKiemNVTC"></PopupTimKiemNhanVienThuCuoc>
                        <div class="key w90">NVTC/Tuyến</div>
                        <div class="value w100">
                            <div class="input-more-button">
                                <button class="btn" @click.prevent="popupTimNVThuCuoc">
                                    <span class="-ap icon-more_horiz"></span>
                                </button>
                                <input type="text" class="form-control highlight" v-model="ma_nvtc">
                            </div>
                        </div>
                        <div class="value">
                            <input type="text" class="form-control highlight"  v-model="matuyen">
                        </div>
                    </div>
                </div>
                <div class="col-sm-8 col-12">
                    <div class="info-row">
                        <div class="key w90">Địa chỉ IG</div>
                        <div class="value">
                            <div class="input-more-button">
                                <button class="btn" v-b-modal.popupDiaChiIG>
                                    <span class="-ap icon-more_horiz"></span>
                                </button>
                                <ModalChonDiaChi modalId="popupDiaChiIG" :data="diachiIG" @xacnhan="getDiaChiIG"/>

                                <bss-error-marker :has-error="$v.diachiIG.DIACHI.$error">
                                    <input type="text" v-model="$v.diachiIG.DIACHI.$model" class="form-control disabled" required title="Bạn chưa nhập địa chỉ" >
                                </bss-error-marker>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="box-form">
            <div class="legend-title">Danh sách mã in ghép</div>
            <div class="table-content">
                <DataGrid :columns="listbox.dgvDanhSach.cols" :dataSource="listbox.dgvDanhSach.list" :showFilter="showFilter" @selectedRowChanged="dgvDanhSach_selectedRowChanged"></DataGrid>
            </div>
        </div>
        <div class="box-form">
            <div class="legend-title">Danh sách mã thanh toán</div>
            <div class="table-content">
                <DataGrid :columns="listbox.dgvDanhSachTT.cols" :dataSource="listbox.dgvDanhSachTT.list" :showFilter="showFilter"></DataGrid>
            </div>
        </div>
    </div>
</div>
</template>
<style scoped>

</style>
<script>
import ModalChonDiaChi from './popupChonDiaChi'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required, minValue, maxValue } from 'vuelidate/lib/validators'
import PopupTimKiemNhanVienThuCuoc from './PopupTimKiemNhanVienThuCuoc'
export default {
    name: 'popupChonMaInGhep',
    components: { ModalChonDiaChi,BssErrorMarker, BssRequiredMarker,PopupTimKiemNhanVienThuCuoc },
    validations: {
        diachiIG:
        {
            DIACHI: {
                required
            },
        },
    },
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            // ds: {
            //     list: [],
            //     cols: [],
            //     keycol: "",
            //     value: {},
            // },
            // btnThoat: false,
            // tieude: "popup",
            // tieudebang: "datapopup",
            hdkh_id: 0,
            khachhang_id: 0,
            hdtt_id: 0,
            showFilter: true,
            thanhtoan_id: 0,
            fmig_id: 0,
            fhdmig_id: 0,
            ma_tt: 0,
            ten_tt: "",
            main_ghep: "",
            tuyenthu_id: 0,
            ma_nvtc: "",
            matuyen: "",
            listbox: {
                madaidien : {
                    list: [],
                    value: 0,
                },
                donviql: {
                    list: [],
                    value: 0,
                },
                dgvDanhSach: {
                    list: [],
                    value: {},
                    cols: [
                        {fieldName: 'main_ghep', headerText: 'Mã IG', allowFiltering: true,cellCssClass: 'text-left',},
                        {fieldName: 'diachi_ig', headerText: 'Địa chỉ IG', allowFiltering: true, cellCssClass: 'text-left',},
                        {fieldName: 'ma_nv', headerText: 'Mã NVTC', allowFiltering: true, cellCssClass: 'text-left',},
                        {fieldName: 'trangthai', headerText: 'Trạng thái', allowFiltering: true, cellCssClass: 'text-left',},
                        {fieldName: 'tuyenthu_id', headerText: 'tuyenthu_id', allowFiltering: true,allowHtml : true,visible:false,},
                        {fieldName: 'hdmig_id', headerText: 'vhdmig_id', allowFiltering: true,allowHtml : true,visible:false,},
                        {fieldName: 'mig_id', headerText: 'vmig_id', allowFiltering: true,allowHtml : true,visible:false,},
                        {fieldName: 'hdtt_id', headerText: 'vhdtt_id', allowFiltering: true,allowHtml : true,visible:false,},
                    ],
                },
                dgvDanhSachTT: {
                    list: [],
                    value: {},
                    cols: [
                        {fieldName: 'ma_tt', headerText: 'Mã TT', allowFiltering: true,cellCssClass: 'text-left',},
                        {fieldName: 'gdiachi_tt', headerText: 'Địa chỉ TT', allowFiltering: true, cellCssClass: 'text-left',},
                    ],
                }
            },
            diachiIG: {
                DIACHI: "",
                DIACHI_ID: 0,
                TINH_ID:0,
                QUAN_ID:0,
                PHUONG_ID:0,
                PHO_ID:0,
                AP_ID:0,
                KHU_ID:0,
                DACDIEM_ID:0,
                SO_NHA:null,
                Lng: 0,
                Lat: 0,
                BLOCK: null,
                TANG: null,
                PHONG: null,
                MOTA: null,
            },
            dsHDMIG: [],
            dsDiaChi: [],
            dsDiaChi_HDMIG: [],
        }
    },
    async mounted() {
        if(this.dulieu.hdkh_id) this.hdkh_id = this.dulieu.hdkh_id;
        if(this.dulieu.khachhang_id) this.khachhang_id = this.dulieu.khachhang_id;
        if(this.dulieu.hdtt_id) this.hdtt_id = this.dulieu.hdtt_id;
        if(this.dulieu.thanhtoan_id) this.thanhtoan_id = this.dulieu.thanhtoan_id;
        if(this.dulieu.fmig_id) this.fmig_id = this.dulieu.fmig_id;
        if(this.dulieu.fhdmig_id) this.fhdmig_id = this.dulieu.fhdmig_id;
        if(this.dulieu.ma_tt) this.ma_tt = this.dulieu.ma_tt;
        if(this.dulieu.ten_tt) this.ten_tt = this.dulieu.ten_tt;

        var rs = await this.$root.context.post("/web-thicong/hoinghi_truyenhinh/ht_donvi_loaidv_combobox", { "loaidv_id": 2 });
        if(rs.data && rs.data.length>0) this.listbox.donviql.list = rs.data;
        this.HienThiDS();
    },
    computed: {
    },
    methods: {
        popupTimNVThuCuoc() {
            this.$refs.dlgTimKiemNVTC.openDialog();
        },
        closeForm() {
            this.$emit('form-close',{hdmig_id:this.fhdmig_id, main_ghep:this.main_ghep});
        },
        terminateForm() {
            this.$emit('form-close',null);
        },
        rowChanged(idx,item) {
            this.ds.value = item;
        },
        async HienThiDS() {
            try {
                var rs = await this.$root.context.post("/web-hopdong/thongtin_maghep/lay_ds_mig_hddb_kh", { "vhdkh_id": this.hdkh_id,"vkhachhang_id":this.khachhang_id,"vhdtt_id":0 });
                if(rs.data && rs.data.length>0) {
                    this.listbox.dgvDanhSach.list = rs.data;
                    var list = [];
                    if(this.fhdmig_id==0) {
                        list = this.listbox.dgvDanhSach.list.filter(x=>x['mig_id']==this.fmig_id);
                    } else {
                        list = this.listbox.dgvDanhSach.list.filter(x=>x['hdmig_id']==this.fhdmig_id);
                    }
                    if(list && list.length>0) this.listbox.dgvDanhSach.value = list[0];
                }
                else {
                    this.listbox.dgvDanhSach.list = [];
                    this.listbox.dgvDanhSach.value = {};
                }
            } catch(ex) { this.$toast.error('Có lỗi: '+ex); }
        },
        async dgvDanhSach_selectedRowChanged(item) {
            if(!item) return;
            this.listbox.dgvDanhSach.value = item;
            console.log(item, "item")
            try {
                this.fhdmig_id = 0;
                this.fmig_id = 0;
                if(item['hdmig_id']) this.fhdmig_id = item['hdmig_id'];
                if(item['mig_id']) this.fmig_id = item['mig_id'];
                this.main_ghep = item['main_ghep'];
                if(item['tuyenthu_id']) {
                    this.tuyenthu_id = item['tuyenthu_id'];
                }
                var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/fn_lay_tt_luoi_hd_inghep", { "ds_para": JSON.stringify({"TUYENTHU_ID":this.tuyenthu_id,"HDMIG_ID":this.fhdmig_id,"MIG_ID":this.fmig_id}) });
                if(rs.data) {
                    var apidata = JSON.parse(rs.data);
                    if(apidata['ERROR_CODE']!=1) {
                        this.$toast.error(apidata['MESSAGE']);
                        return;
                    }
                    this.ma_nvtc = apidata['RESULT']['MA_NV'];
                    this.HienThiTTDiaChi(apidata['RESULT']['DIACHI']);
                    this.HT_DanhSachTT(apidata['RESULT']['DS_TT']);
                }

            }catch(ex) { this.$toast.error('Có lỗi: '+ex); }
        },
        HienThiTTDiaChi(ds) {
            if(ds && ds.length>0) {
                if(ds[0]['diachi_id']) this.diachiIG.DIACHI_ID = ds[0]['diachi_id'];
                if(ds[0]['tinh_id']) this.diachiIG.TINH_ID = ds[0]['tinh_id'];
                if(ds[0]['quan_id']) this.diachiIG.QUAN_ID = ds[0]['quan_id'];
                if(ds[0]['phuong_id']) this.diachiIG.PHUONG_ID = ds[0]['phuong_id'];
                if(ds[0]['dacdiem_id']) this.diachiIG.DACDIEM_ID = ds[0]['dacdiem_id'];
                if(ds[0]['pho_id']) this.diachiIG.PHO_ID = ds[0]['pho_id'];
                if(ds[0]['ap_id']) this.diachiIG.AP_ID = ds[0]['ap_id'];
                if(ds[0]['khu_id']) this.diachiIG.KHU_ID = ds[0]['khu_id'];
                if(ds[0]['sonha']) this.diachiIG.SO_NHA = ds[0]['sonha'];
            }
        },
        getDiaChiIG(data) {
            this['diachiIG'].DIACHI = data.diachimoi
            this['diachiIG'].TINH_ID = data.tinh_id
            this['diachiIG'].QUAN_ID = data.quan_id
            this['diachiIG'].PHUONG_ID = data.phuong_id
            this['diachiIG'].PHO_ID = data.pho_id
            this['diachiIG'].AP_ID = data.ap_id
            this['diachiIG'].KHU_ID = data.khu_id
            this['diachiIG'].DACDIEM_ID = data.dacdiem_id
            this['diachiIG'].SO_NHA = data.so_nha
            this['diachiIG'].Lat = data.lat
            this['diachiIG'].Lng = data.lng
        },
        HT_DanhSachTT(ds) {
            if(ds && ds.length>0) {
                this.listbox.dgvDanhSachTT.list = rs.data;
                if(this.listbox.dgvDanhSachTT.list && this.listbox.dgvDanhSachTT.list.length>0) this.listbox.dgvDanhSachTT.value = this.listbox.dgvDanhSachTT.list[0];
            }
        },
        async TaoMoi() {
            try {
                if (this.diachiIG.PHUONG_ID == 0)
                {
                    this.$toast.error("Bạn chưa chọn địa chỉ in ghép");
                    return;
                }
                if (this.ma_nvtc == "")
                {
                    this.$toast.error("Bạn chưa chọn nhân viên thu cước");
                    return;
                }
                this.$bvModal.msgBoxConfirm('Bạn có muốn sinh mã in ghép không ?', {
                    title: 'Xác nhận hành động',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    if(v) {
                        var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/fn_laytt_taodl_maig", { "ds_para": JSON.stringify({"THEMMOI":true,"PHUONGIG_ID":this.diachiIG.PHUONG_ID,"MA_NVTC":this.ma_nvtc,"MIG_ID":0,"HDTT_ID":this.hdtt_id}) });
                        if(rs.data) {
                            var apidata = JSON.parse(rs.data);
                            if(apidata['ERROR_CODE']!=1) {
                                this.$toast.error(apidata['MESSAGE']);
                                return;
                            }
                            if(apidata['RESULT']['MA_INGHEP']) this.main_ghep = apidata['RESULT']['MA_INGHEP'];
                            if(apidata['RESULT']['HDMIG_ID']) this.fhdmig_id = apidata['RESULT']['HDMIG_ID'];
                            if(apidata['RESULT']['DIACHIIG_ID']) this.diachiIG.DIACHI_ID = apidata['RESULT']['DIACHIIG_ID'];
                            this.TaoDuLieu_HDMIG(0);
                            this.TaoDuLieu_DiaChi();
                            var rs1 = await this.$root.context.post("web-hopdong/lapdatmoi/fn_ins_upd_hopdong_inghep", { "ds_para": JSON.stringify(
                                {"THEMMOI":true,"HD_MIG":this.dsHDMIG,"DIACHI":this.dsDiaChi,"DIACHI_HDMIG":this.dsDiaChi_HDMIG,"HDTT_ID":this.hdtt_id}) });
                            if(rs1.data) {
                                var apidata1 = JSON.parse(rs1.data);
                                console.log(apidata1, "apidata1")
                                if(apidata1['ERROR_CODE']!=1) {
                                    this.$toast.error(apidata1['MESSAGE']);
                                    return;
                                } else {
                                    this.Clear();
                                    this.HienThiDS();
                                    this.$toast.success("Thêm mới thành công!");
                                }
                            } else this.$toast.error(rs1.message +': ' + (rs1.message_detail?rs1.message_detail:""));
                            //datmoi.ThemMoi_HopDong_InGhep(dsHDMIG, dsDiaChi, dsDiaChi_HDMIG);
                        } else this.$toast.error(rs.message +': ' + (rs.message_detail?rs.message_detail:""));
                    }
                });
            } catch(ex) { this.$toast.error(''+ex); }
        },
        Clear(){
            try
            {
                this.diachiIG = {
                    DIACHI: "",
                    DIACHI_ID: 0,
                    TINH_ID:0,
                    QUAN_ID:0,
                    PHUONG_ID:0,
                    PHO_ID:0,
                    AP_ID:0,
                    KHU_ID:0,
                    DACDIEM_ID:0,
                    SO_NHA:null,
                    Lng: 0,
                    Lat: 0,
                    BLOCK: null,
                    TANG: null,
                    PHONG: null,
                    MOTA: null,
                };
                this.main_ghep = "";
                this.tuyenthu_id = 0;
                this.ma_nvtc = "";
                this.matuyen = "";

            }
            catch (ex)
            {
                this.$toast.error("Có lỗi: " + ex);
            }
        },
        TaoDuLieu_HDMIG(mig_id) {
            this.dsHDMIG = [];
            var rowHDMIG = {};
            rowHDMIG.HDMIG_ID = this.fhdmig_id;
            if (mig_id != 0)
            {
                rowHDMIG.MIG_ID = mig_id;
            }
            rowHDMIG.HDKH_ID = this.hdkh_id;
            rowHDMIG.MAIN_GHEP = this.main_ghep;
            rowHDMIG.DIACHI_IG = this.diachiIG.DIACHI;
            rowHDMIG.TUYENTHU_ID = this.tuyenthu_id;
            this.dsHDMIG.push(rowHDMIG);
        },
        TaoDuLieu_DiaChi() {
            this.dsDiaChi = [];
            this.dsDiaChi_HDMIG = [];
            var rowDiaChi = {};
            rowDiaChi.DIACHI_ID = this.diachiIG['DIACHI_ID'];
            rowDiaChi.TINH_ID = this.diachiIG['TINH_ID'];
            rowDiaChi.QUAN_ID = this.diachiIG['QUAN_ID'];
            rowDiaChi.PHUONG_ID = this.diachiIG['PHUONG_ID'];
            if(this.diachiIG['PHO_ID'] && this.diachiIG['PHO_ID']!=0) rowDiaChi.PHO_ID = this.diachiIG['PHO_ID'];
            if(this.diachiIG['AP_ID'] && this.diachiIG['AP_ID']!=0) rowDiaChi.AP_ID = this.diachiIG['AP_ID'];
            if(this.diachiIG['KHU_ID'] && this.diachiIG['KHU_ID']!=0) rowDiaChi.KHU_ID = this.diachiIG['KHU_ID'];
            rowDiaChi.DACDIEM_ID = this.diachiIG['DACDIEM_ID'];
            rowDiaChi.SONHA = this.diachiIG['SONHA'];
            rowDiaChi.DIACHI = this.diachiIG['DIACHI'];
            this.dsDiaChi.push(rowDiaChi);

            var rowDiaChi_HDMIG = {
                DIACHI_ID: this.diachiIG['DIACHI_ID'],
                HDMIG_ID: this.fhdmig_id,
            };
            this.dsDiaChi_HDMIG.push(rowDiaChi_HDMIG);
        },
        getNVTCProcess(value) {
            // this.nhanvientc_id = value.rowData.NHANVIEN_ID;
            this.ma_nvtc = value.maNVTC;
            this.tuyenthu_id = value.rowData.TUYENTHU_ID;
            this.matuyen = value.rowData.MA_TUYENTHU;
        },
        async Xoa() {
            if (!this.listbox.dgvDanhSach.list || this.listbox.dgvDanhSach.list.length <= 0)
            {
                this.$toast.error("Chưa có thông tin mã in ghép để Xóa!");
                return;
            }
            this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xóa hợp đồng mã in ghép không ?', {
                title: 'Xác nhận hành động',
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy',
            }).then(async v => {
                if(v) {
                    var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/xoa_hdmig", { "vhdmig_id": this.fhdmig_id });
                    if(rs) {
                        var apidata = rs;
                        if(apidata['error_code']!= 'BSS-00000000') {
                            this.$toast.error(apidata['message']);
                            return;
                        } else {
                            this.$toast.success("Xóa dữ liệu thành công!");
                            this.HienThiDS();
                        }
                    }
                    else this.$toast.error("Có lỗi xảy ra");
                }
            });
        },
        async tsbtnChonMIG_Click() {
            if (!this.listbox.dgvDanhSach.list || this.listbox.dgvDanhSach.list.length <= 0)
            {
                this.$toast.error("Chưa có thông tin mã in ghép để bạn chọn!");
                return;
            }
            // if(!this.fmig_id) {
            //     this.TaoDuLieu_HDMIG(this.fmig_id);
            //     this.TaoDuLieu_DiaChi();
            //     var rs1 = await this.$root.context.post("web-hopdong/lapdatmoi/fn_ins_upd_hopdong_inghep", { "ds_para": JSON.stringify(
            //                     {"THEMMOI":true,"HD_MIG":this.dsHDMIG,"DIACHI":this.dsDiaChi,"DIACHI_HDMIG":this.dsDiaChi_HDMIG,"HDTT_ID":this.hdtt_id}) });
            //     if(rs1.data) {
            //         var apidata1 = JSON.parse(rs1.data);
            //         if(apidata1['ERROR_CODE']!=1) {
            //             this.$toast.error(apidata1['MESSAGE']);
            //             return;
            //         }
            //     } else {
            //         this.$toast.error(rs1.message +': ' + (rs1.message_detail?rs1.message_detail:""));
            //         return;
            //     }
            // }
            this.closeForm();
        }
    }
}
</script>
