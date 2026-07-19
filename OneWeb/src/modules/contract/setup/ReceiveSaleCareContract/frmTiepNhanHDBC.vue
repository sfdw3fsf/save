<template>
    <div>
        <breadcrumb :header="header" />
        <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="javascript:;" @click="btnLayTT_Click">
                    <span class="icon one-search"></span>Tìm kiếm
                </a>
            </li>
            <li v-if="tsbtnTraPhieu.Visible">
                <a href="javascript:;" @click="tsbtnTraPhieu_Click">
                    <span class="icon one-back1"></span>{{ tsbtnTraPhieu.Text }}
                </a>
            </li>
            <li v-if="tsbtnLapHD.Visible">
                <a href="javascript:;" @click="tsbtnLapHD_Click">
                    <span class="icon one-save"></span>{{ tsbtnLapHD.Text }}
                </a>
            </li>
            <li v-if="tsbtnXoaHD.Visible">
                <a href="javascript:;" @click="tsbtnXoaHD_Click">
                    <span class="icon one-trash"></span>{{ tsbtnXoaHD.Text }}
                </a>
            </li>
            <li v-if="tsbtnThanhToan.Visible">
                <a href="javascript:;" @click="tsbtnThanhToan_Click">
                    <span class="icon one-wallet"></span>{{ tsbtnThanhToan.Text }}
                </a>
            </li>
        </ul>
        </div>
        <div class="page-content">
            <div class="box-form">
                <div class="legend-title">
                    <span>Danh sách hồ sơ</span>
                </div>
                <div class="row">
                <div class="col-sm-3 col-12">
                    <div class="info-row">
                    <div class="key w100">{{ cbbPhanLoaiHS.text }}</div>
                    <div class="value">
                        <div class="select-custom" ref="cbbPhanLoaiHS">
                        <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cbbPhanLoaiHS'] }"
                            v-model="cbbPhanLoaiHS.value" class="select2"
                            :options="cbbPhanLoaiHS.Items && cbbPhanLoaiHS.Items.length > 0 ? cbbPhanLoaiHS.Items.map(e=>({id:e.id,text:e.text})) : []">
                        </select2>
                        </div>
                    </div>                  
                    </div>
                </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w100">{{ cbbTrangThaiPhieu.text }}</div>
                            <div class="value">
                                <div class="select-custom" ref="cbbTrangThaiPhieu">
                                    <select2 :z-index="2000" :settings="{ dropdownParent: $refs['cbbTrangThaiPhieu'] }"
                                        v-model="cbbTrangThaiPhieu.value" class="select2" @change="cbbTrangThaiPhieu_EditValueChanged"
                                        :options="cbbTrangThaiPhieu.Items && cbbTrangThaiPhieu.Items.length > 0 ? cbbTrangThaiPhieu.Items.map(e=>({id:e.id, text:`${e.text}`})) : []">
                                    </select2>
                                </div>
                            </div>                  
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w100">{{ dtpTimTuNgay.text }}</div>
                            <div class="value">
                                <DatePicker :format="dateFormat" :value-type="'format'" v-model="dtpTimTuNgay.value" 
                                    @change="changeTuNgay" :disabled="false"/>
                            </div>                  
                        </div>
                    </div>      
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w100">{{ dtpTimDenNgay.text }}</div>
                            <div class="value">
                                <DatePicker :format="dateFormat" :value-type="'format'" v-model="dtpTimDenNgay.value" 
                                    @change="changeDenNgay" :disabled="false"/>
                            </div>                  
                        </div>
                    </div>         
                </div>        
            </div>
            <div class="box-form box-tab-horizontal">
                <div class="nav tabs tab-left">
                    <a href="#tabHDPL" @click="tabClick(1)" :class="{active: p_tab === 1}" data-toggle="tab" >Danh sách hồ sơ HĐ/PL</a>
                    <a href="#tabLoi" @click="tabClick(2)" :class="{active: p_tab === 2}" data-toggle="tab" >Danh sách hồ sơ HĐ/PL bị lỗi</a>
                </div>
                <div class="tab-content" style="height: 600px;">
                    <div id="tabHDPL" class="tab-pane" :class="{active: p_tab === 1}" >
                        <DataGrid class="table-result table-gachle" :columns="gridTiepNhan.header" :dataSource="gridTiepNhan.list"
                            :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true"
                            :enabledSelectFirstRow="true" ref="gridTiepNhan" panelDataHeight="300px" @selectedItemsChanged="gridTiepNhan_ItemsChanged"
                        />
                    </div>
                    <div id="tabLoi" class="tab-pane" :class="{active: p_tab === 2}" >
                        <DataGrid class="table-result table-gachle" :columns="grcThongTinLoi.header" :dataSource="grcThongTinLoi.list"
                            :enable-paging-server="false" :allowPaging="true" :showFilter="true" :showColumnCheckbox="true"
                            :enabledSelectFirstRow="true" ref="grcThongTinLoi" panelDataHeight="300px" />
                    </div>
                </div>
            </div>
        </div>
        <b-modal id="popupComponents" size="huge" :title="popupComponentHeader" :hide-header="popupComponentHeader?false:true"
        hide-footer modal-class="modal-custom" body-class="modal-body p-0">
            <component :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr"
                isPopup="true" v-on="popupComponentEvts"></component>
        </b-modal>
    </div>
</template>
<style src="./frmTiepNhanHDBC.scss"></style>

<script setup>

</script>
<script>
import breadcrumb from "@/components/breadcrumb";
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
// import DataGrid from "@/components/Controls/DataGrid";
import api from './API.js'
import { TRANGTHAI_DONGBO, LoaiHopDong, TrangThaiHD, DichVuVienThong, listDichVuCNTT, TRANGBI, KieuLapDat, KHOANMUC_TT, LoaiHinhTB, Loai_KM, NHOM_LNV, LOAI_DV } from '@/modules/admin/category/MultiServicePackage/enum.js'

export default {
name: 'frmTiepNhanHDBC',
components: {
    breadcrumb,
    DatePicker,
    // DataGrid
},
props: ["dulieu"],
data: () => ({
    header: {
        title: "Tiếp nhận hợp đồng bán chăm",
        list: [
            {
            name: 'Tiếp nhận hợp đồng bán chăm',
            link: { 
                name: 'Ui.buttons',
                active: true 
            }
            }
        ],
    },
    popupComponentName: '',
    popupComponentHeader: '',
    popupComponent: null,
    popupComponentData: null,
    popupComponentAttr: {},
    popupComponentEvts: {
       
    },
    tt_nd: {
        phanvung_id: 0, //this.$root.token.getPhanVungID(),
        donvi_id: 0, //this.$root.token.getDonviID(),
        nhanvien_id: 0, //this.$root.token.getNhanVienID(),
        ma_nd: '',
        ip_cn: "",
        may_cn: "",
        nguoi_cn: "",
        ngay_cn: moment(new Date()).format('DD/MM/YYYY')
    },
    tsbtnTraPhieu: {Text: 'Trả phiếu', Visible: true},
    tsbtnLapHD: {Text: 'Lập HĐ', Visible: true},
    tsbtnXoaHD: {Text: 'Xóa HĐ', Visible: true},
    tsbtnThanhToan: {Text: 'Thanh toán', Visible: true},
    p_tab: 1,
    quytrinh_id: 0,
    huonggiao_tn: 0,
    hdtb_id_cd: 0,
    hdkh_cd_id: 0,
    hdtb_dc_id: 0,
    hdkh_id_dc: 0,
    thuebao_dc_id: 0,
    rkm_id: 0,
    tongtien: 0,
    tongvat: 0,
    tongall: 0,
    dateFormat: 'DD/MM/YYYY',
    vkieu: 0,
    noidung: '',
    cbbPhanLoaiHS: {text: 'Phân loại', value: 0, Items: []},
    cbbTrangThaiPhieu: {text: 'Trạng thái', value: 0, Items: []},
    dtpTimTuNgay: {text: 'Từ ngày', value: moment(new Date()).format('DD/MM/YYYY')},
    dtpTimDenNgay: {text: 'Đến ngày', value: moment(new Date()).format('DD/MM/YYYY')},
    ds_tien_khoanmuc: [],
    ds_temp: [],
    dsDBTB: [],
    dsHDKH: [],
    dsHDTB: [],
    dsHDTBDV: [],
    dsTIEN_HDTB: [],
    dsKM_HDTB: [],
    dsDC_HDTB: [],
    dsCTTHD: [],
    dsTTTT: [],
    dsTDTD_ADSL: [],
    dsHDTB_CNTT: [],
    hdtb_dc: [],
    gridTiepNhan: {
        list: [],
        selectedItems: [],
        header: [
            {
                fieldName: "loai_hdbc",
                headerText: "Loại HĐ",
                allowFiltering: true,
            },
            {
                fieldName: "ngaygiao",
                headerText: "Ngày giao",
                allowFiltering: true,
            },
            {
                fieldName: "giogiao",
                headerText: "Giờ giao",
                allowFiltering: true,
            },
            {
                fieldName: "ma_tb",
                headerText: "Số máy/Account",
                allowFiltering: true,
            },
            {
                fieldName: "nd_giao",
                headerText: "ND giao",
                allowFiltering: true,
            },
            {
                fieldName: "tocdocu",
                headerText: "Tốc độ hiện tại",
                allowFiltering: true,
            },
            {
                fieldName: "tocdomoi",
                headerText: "Tốc độ mới",
                allowFiltering: true,
            },
            {
                fieldName: "tenmuc",
                headerText: "Mức cước",
                allowFiltering: true,
            },
            {
                fieldName: "muccuoc",
                headerText: "Mức cước TB",
                allowFiltering: true,
            },
            {
                fieldName: "ten_km",
                headerText: "Khuyến mại",
                allowFiltering: true,
            },
            {
                fieldName: "ten_cctkm",
                headerText: "Chi tiết KM",
                allowFiltering: true,
            },
            {
                fieldName: "datcoc_csd",
                headerText: "Tiền đặt cọc",
                allowFiltering: true,
            },
            {
                fieldName: "tientt_km",
                headerText: "Tiền trả trước",
                allowFiltering: true,
            },
            {
                fieldName: "thang_bd_km",
                headerText: "Tháng BĐ",
                allowFiltering: true,
            },
            {
                fieldName: "ma_gd",
                headerText: "Mã GD",
                allowFiltering: true,
            },
            {
                fieldName: "ma_kh",
                headerText: "Mã KH",
                allowFiltering: true,
            },
            {
                fieldName: "ghichu",
                headerText: "Ghi chú",
                allowFiltering: true,
            },
            {
                fieldName: "ngaytra",
                headerText: "Ngày trả",
                allowFiltering: true,
            },
            {
                fieldName: "nd_tra",
                headerText: "Nội dung trả",
                allowFiltering: true,
            },
            {
                fieldName: "phieu_id",
                headerText: "Số phiếu",
                allowFiltering: true,
            },
            {
                fieldName: "iserror",
                headerText: "Lỗi",
                allowFiltering: true,
            },
            {
                fieldName: "tt_hd",
                headerText: "Trạng thái HĐ",
                allowFiltering: true,
            },
            
        ],
        value: {},
        isEnabled: true,
        checked: [],
    },
    grcThongTinLoi: {
        list: [],
        selectedItems: [],
        header: [
            {
            fieldName: "phieu_id",
            headerText: "Phiếu ID",
            allowFiltering: true,
            },
            {
            fieldName: "ma_tb",
            headerText: "Mã TB",
            allowFiltering: true,
            },
            {
            fieldName: "ngaygiao",
            headerText: "Ngày giao",
            allowFiltering: true,
            },
            {
            fieldName: "giogiao",
            headerText: "Giờ giao",
            allowFiltering: true,
            },
            {
            fieldName: "nd_loi",
            headerText: "Nội dung lỗi",
            allowFiltering: true,
            },
            
        ],
        value: {},
        isEnabled: true,
        checked: [],
    },
}),
created: async function () {
},
watch: {},
computed: {},
mounted () {
    this.frmTiepNhanHDBC_Load()
},
methods: {
    tabClick (tab) {
        this.p_tab = tab
    },
    SETTING_BUTTON() {        
        this.tsbtnLapHD.Visible = false;
        this.tsbtnThanhToan.Visible = false;
        this.tsbtnTraPhieu.Visible = false;
        this.tsbtnXoaHD.Visible = false;
        // this.colNgayTra.Visible = false;
        // this.colNDTra.Visible = false;
        // TTPhieu_ID
        switch (Number(this.cbbTrangThaiPhieu.value))
        {
            case 0:                
                this.tsbtnLapHD.Visible = false;
                this.tsbtnThanhToan.Visible = false;
                this.tsbtnTraPhieu.Visible = false;
                this.tsbtnXoaHD.Visible = false;
                break;
            case 1:
                this.tsbtnTraPhieu.Visible = true;
                this.tsbtnLapHD.Visible = true;
                break;
            case 2:
                this.tsbtnXoaHD.Text = "Xóa HĐ";
                this.tsbtnThanhToan.Visible = true;
                this.tsbtnXoaHD.Visible = true;
                break;
            case 4:
                this.tsbtnXoaHD.Text = "Xóa TT";
                this.tsbtnXoaHD.Visible = true;
                break;
            case 3:
                // this.colNgayTra.Visible = true;
                // this.colNDTra.Visible = true;
                break;
            case 6:
                break;
        }

    },
    frmTiepNhanHDBC_Load: async function () {
        this.tt_nd = {
            phanvung_id: Number(await this.$root.token.getPhanVungID()),
            donvi_id: Number(await this.$root.token.getDonViID()),
            nhanvien_id: Number(await this.$root.token.getNhanVienID()),
            ma_nd: (await this.$root.token.getThongTinNguoiDung())['ma_nd'],
            ip_cn: await this.$root.token.getIP(),
            may_cn: await this.$root.token.getMachine(),
            nguoi_cn: await this.$root.token.getUserName(),
            ngay_cn: moment(new Date()).format('DD/MM/YYYY')
        };        
        this.getDSPhanLoaiHS();
        this.getDSTrangThaiPhieu();     

        if (this.dulieu) {
            this.cbbTrangThaiPhieu.value = this.dulieu['ttph_id']
            this.cbbPhanLoaiHS.value = this.dulieu['loaihs_id']
        } else {
            this.cbbTrangThaiPhieu.value = this.cbbTrangThaiPhieu.Items[0]['id']
            this.cbbPhanLoaiHS.value = this.cbbPhanLoaiHS.Items[0]['id']
        }
        this.SETTING_BUTTON();
        this.NAP_DS_DL_PHIEU_BANCHAM();
        // this.NAP_DS_LOI_TEMP()
        this.ds_tien_khoanmuc = await this.Lay_tienkhoanmuc_loaihd(LoaiHopDong.THAY_DOI_TOCDO_ADSL);
    },
    getDSPhanLoaiHS: function () {
        this.cbbPhanLoaiHS.Items = [
            { id: "1", text: "HĐ bán chăm"},
        ]
    },
    getDSTrangThaiPhieu: function () {
        this.cbbTrangThaiPhieu.Items = [
            { id: 1, text: "Phiếu mới"},
            { id: 2, text: "Phiếu đã lập HĐ/PL"},
            { id: 4, text: "Phiếu đã thanh toán"},
            { id: 6, text: "Phiếu đã hoàn thành"},
            { id: 3, text: "Phiếu đã trả"},
        ]
    },
    Lay_tienkhoanmuc_loaihd: async function (loaihd_id) {
        try {
            let result = [];
            try {
                const params = { loaihd_id: loaihd_id };
                let response = await api.sp_lay_tienkhoanmuc_loaihd(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e);
            }
            return result;
        } catch (ex) {
            console.log(ex);
            this.ShowError("Lỗi lấy danh sách tiền khoản mục: " + JSON.stringify(ex));
        }
    },
    CLEAR_LOI() {
        if (this.ds_temp && this.ds_temp.length > 0) this.grcThongTinLoi.list = this.LowerCasePropertyList(this.ds_temp)
    },
    NAP_DS_LOI_TEMP: async function () {
        this.ds_temp = []
        let response = await api.sp_lay_ngaygio_hethong(this.axios);
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.ds_temp = response.data.data;
        }
    },
    NAP_DS_LOI: async function () {
        try {
            this.grcThongTinLoi.list = []
            
            // grcThongTinLoi.DataSource = null;

            // var ds_tn = gridTiepNhan.DataSource as DataTable;
            // if (ds_tn == null || grvTiepNhan.DataRowCount == 0)
            // {
            //     return;
            // }

            // if (ds_loi == null || ds_loi.Rows.Count == 0)
            //     return;
            // ds_loi.AcceptChanges();
            // for (var i = 0; i < ds_tn.Rows.Count; i++)
            // {
            //     var length = ds_loi.Select("PHIEU_ID=" + ds_tn.Rows[i]["PHIEU_ID"]);
            //     if (length.Length > 0)
            //     {
            //         ds_tn.Rows[i]["ISERROR"] = "1";
            //     }
            // }
            // ds_loi.AcceptChanges();

            // grcThongTinLoi.DataSource = ds_loi;
        }
        catch(ex)
        {
            this.ShowError("Có lỗi khi nạp danh sách lỗi"+ex);
        }
    },
    NAP_DS_DL_PHIEU_BANCHAM: async function () {
        try {
            this.gridTiepNhan.list = []
            var input = { 
                vtungay: this.dtpTimTuNgay.value, 
                vdenngay: this.dtpTimDenNgay.value, 
                vttph_id: this.cbbTrangThaiPhieu.value, 
                vma_nd: this.tt_nd.ma_nd
            };
            let response = await api.sp_lay_ds_phieu_bancham(this.axios, input);
            if (
                response &&
                response.data &&
                response.data.error_code == "BSS-00000000"
            ) {
                this.gridTiepNhan.list = response.data.data;
            } else {
                // this.gridTiepNhan.list = [];
            }
        } catch (ex) {
            console.log(ex);
            this.ShowError("Lỗi lấy danh sách phiếu bán chăm: " + JSON.stringify(ex));
        }
    },
    sp_thaotac_phieu: async function () {
        var input = { 
            vkieu: this.vkieu, 
            vdanhsach: this.gridTiepNhan.selectedItems, 
            vnd:  this.noidung
        }
        var thongbao = ''
        if (this.vkieu == 3) {
            thongbao = 'Trả phiếu thành công'
            this.NAP_DS_DL_PHIEU_BANCHAM()
        }
        // let response = await api.sp_thaotac_phieu_bancham(this.axios, input);
        // if (
        //     response &&
        //     response.data &&
        //     response.data.error_code == "BSS-00000000"
        // ) {
        //     this.gridTiepNhan.list = response.data.data;
        // } else {
        //     // this.gridTiepNhan.list = [];
        // }
    },
    THAOTAC_PHIEU_BANCHAM: async function (vkieu) {
        try {            
            this.vkieu = vkieu
            switch (vkieu)
            {
                case 3:
                    this.loading(true)
                    await this.TRAPHIEU();    
                    this.loading(false)                
                    break;
                case 2:
                    this.loading(true)
                    await this.LAY_DS_LAPHD();
                    this.loading(false)      
                    break;
                case 1:
                    this.loading(true)
                    await this.XOA_DS_HDPL();
                    this.loading(false)      
                    break;
                case 4:
                    this.loading(true)
                    await this.THANHTOAN_HD();
                    this.loading(false)      
                    return;
                case 5:
                    this.loading(true)
                    await this.XOA_THANHTOAN_HD();
                    this.loading(false)      
                    return;
            }
           
        } catch (e) { 
            console.log('THAOTAC_PHIEU_BANCHAM', e) 
            this.loading(false)      
        }
    },
    TRAPHIEU() {
        try {
            if (!this.gridTiepNhan.list || this.gridTiepNhan.list.length == 0) {
                this.$toast.error("Không có dữ liệu phiếu để trả lại");
                return;
            }

            if (!this.gridTiepNhan.selectedItems || this.gridTiepNhan.selectedItems.length == 0) {
                this.$toast.error("Bạn chưa chọn phiếu để tiến hành trả lại");
                return;
            }
            
            this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn trả lại danh sách các phiếu đã chọn không ?", {
                title: "Thông báo",
                size: "sm",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
            }).then(async (v) => {
                if (v) {
                    this.loading(true);
                    // await this.CAPNHAT();
                    this.popupNhanText_open()
                    this.loading(false);
                }
            }).catch((e) => {
                console.log(e);
                this.ShowError("Lỗi trả phiếu " + JSON.stringify(e));
                this.loading(false);
            });
        }
        catch (ex)
        {
            this.$toast.error("Có lỗi khi trả phiếu " + ex);
        }

    },
    LAY_DS_LAPHD: async function () {
        try {
            this.CLEAR_LOI();
            
            if (!this.gridTiepNhan.list || this.gridTiepNhan.list.length == 0) {
                this.$toast.error("Không có dữ liệu phiếu để lập HĐ");
                return;
            }

            if (!this.gridTiepNhan.selectedItems || this.gridTiepNhan.selectedItems.length == 0) {
                this.$toast.error("Bạn chưa chọn phiếu để lập HĐ");
                return;
            }
            
            this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn lập HĐ với danh sách đã chọn không ?", {
                title: "Thông báo",
                size: "sm",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
            }).then(async (v) => {
                if (v) {
                    this.loading(true);
                    await this.SP_LAY_DS_LAPHD();
                    await this.NAP_DS_DL_PHIEU_BANCHAM();
                    await this.NAP_DS_LOI();
                    this.loading(false);
                }
            }).catch((e) => {
                console.log(e);
                this.ShowError("Lỗi lập HĐ " + JSON.stringify(e));
                this.loading(false);
            });
        }
        catch (ex)
        {
            this.$toast.error("Có lỗi khi lập HĐ " + ex);
        }

    },
    SP_LAY_DS_LAPHD: async function () {
        try {
            // var kq_dt = (DataTable)parameters[1];
            var tong = 0;
            var thucthi = 0;
            if (this.gridTiepNhan.selectedItems && this.gridTiepNhan.selectedItems.length > 0) {
                tong = this.gridTiepNhan.selectedItems.length
                var kq_dt = this.UpperCasePropertyList(this.gridTiepNhan.selectedItems)
                for (var i = 0; i < kq_dt.length; i++) {
                    if (kq_dt[i]["TOCDO_ID"]){
                        console.log('TAO_HD', kq_dt[i])
                        var q = await this.TAO_HD(kq_dt[i]);
                        if (q == "1")
                        {
                            thucthi += 1;
                        }
                        else
                        {
                            var row_loi = {
                                phieu_id: kq_dt[i]["PHIEU_ID"],
                                ma_tb: kq_dt[i]["MA_TB"],
                                giogiao: kq_dt[i]["GIOGIAO"],
                                ngaygiao: kq_dt[i]["NGAYGIAO"],
                                nd_loi: q
                            }
                            this.grcThongTinLoi.list.push(row_loi)
                        }
                    }
                    else
                    {
                        console.log('TAO_HD_DATCOC', kq_dt[i])
                        var q = await this.TAO_HD_DATCOC(kq_dt[i]);
                        if (q == "1")
                        {
                            thucthi += 1;
                        }
                        else
                        {
                            var row_loi = {
                                phieu_id: kq_dt[i]["PHIEU_ID"],
                                ma_tb: kq_dt[i]["MA_TB"],
                                giogiao: kq_dt[i]["GIOGIAO"],
                                ngaygiao: kq_dt[i]["NGAYGIAO"],
                                nd_loi: q
                            }
                            this.grcThongTinLoi.list.push(row_loi)
                        }
                    }
                }
            }
        } catch (e) { console.log(e) }
    },
    TAO_HD_DATCOC: async function (dr) {
        try
        {            
            var phieu_id = !dr["PHIEU_ID"] ? 0 : Number(dr["PHIEU_ID"]);
            var ma_tb = !dr["MA_TB"] ? "" : dr["MA_TB"];
            var dichvuvt_id = !dr["DICHVUVT_ID"] ? 0 : Number(dr["DICHVUVT_ID"]);
            var ma_kh = !dr["MA_TB"] ? "" : dr["MA_KH"];
            var ten_kh = !dr["TEN_KH"] ? "" : dr["TEN_KH"];
            var ghichu = !dr["GHICHU"] ? "" : dr["GHICHU"];
            var khachhang_id = !dr["KHACHHANG_ID"] ? 0 : Number(dr["KHACHHANG_ID"]);
            var thuebao_id = !dr["THUEBAO_ID"] ? 0 : Number(dr["THUEBAO_ID"]);
            var ma_gd = "";
            var loaihinh_tb = !dr["LOAITB_ID"] ? 0 : Number(dr["LOAITB_ID"]);
            var muccuoctb_id = !dr["MUCCUOCTB_ID"] ? -1 : Number(dr["MUCCUOCTB_ID"]);
            var tocdo_id = !dr["TOCDO_ID"] ? 0 : Number(dr["TOCDO_ID"]);
            var kieuld_id = !dr["KIEULD_ID"] ? 0 : Number(dr["KIEULD_ID"]);

            var input = { maTb: ma_tb, dichVuVtId: dichvuvt_id, donViDlId: 0 }
            var ds = await this.LAY_DANHBA_THEO_MATB(input); //Lấy ra bất kỳ thuê bao nào
            console.log('LAY_DANHBA_THEO_MATB', ds)
            if (!ds || ds.length <= 0) {
                this.$toast.error('Không lấy được danh bạ của mã thuê bao ' + ma_tb)
                return false
            }
            //Thông tin khách hàng            
            var ngay_ldh = '';
            if (ds[0]["ngaylap_hd"])
                ngay_ldh = ds[0]["ngaylap_hd"];
            else
                ngay_ldh = this.tt_nd.ngay_cn;
            var diachi_kh = ds[0]["diachi_kh"];

            //Thông tin thuê bao
            var diachi_ld = ds[0]["diachi_ld"];

            var ten_tb = ds[0]["ten_tb"];
            var loaitb_id = Number(ds[0]["loaitb_id"]);

            var donvi_id = Number(ds[0]["donviql_id"]);
            var doituong_id = Number(ds[0]["doituong_id"]);
            var khlon_id = Number(ds[0]["khlon_id"]);
            var thangbd_km = !dr["THANG_BD_KM"] ? "" : dr["THANG_BD_KM"];
            var thangkt_km = !dr["THANG_KT_KM"] ? "" : dr["THANG_KT_KM"];
            var thangbd_mg = !dr["THANG_BD_MG"] ? "" : dr["THANG_BD_MG"];
            var sothang_mg = !dr["SOTHANG_MG"] ? "" : dr["SOTHANG_MG"];
            var sothang_km = !dr["SOTHANG_KM"] ? "" : dr["SOTHANG_KM"];
            var ctkhuyenmai_id = !dr["CHITIETKM_ID"] ? -1 : Number(dr["CHITIETKM_ID"]);
            var khuyenmai_id = !dr["KHUYENMAI_ID"] ? -1 : Number(dr["KHUYENMAI_ID"]);
            var cuoc_dc = !dr["DATCOC_CSD"] ? "" : dr["DATCOC_CSD"];
            var tien_td = !dr["TIEN_TD"] ? "" : dr["TIEN_TD"];
            console.log('Thông tin thuê bao load done')
            var check_laphd_khac = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(ma_tb, "", dichvuvt_id, LoaiHopDong.THAYDOI_DATCOC)
            console.log('KIEMTRA_MA_TB_LAP_HOPDONG_KHAC', check_laphd_khac)
            if (check_laphd_khac) return false;
            this.hdtb_dc_id = 0;
            var check_dulieu_datcoc = await this.KIEMTRA_DULIEU_DATCOC(true, thuebao_id, ma_tb, dichvuvt_id, loaihinh_tb, ctkhuyenmai_id,
                                        khuyenmai_id, sothang_km, cuoc_dc, tien_td, thangbd_km)
            console.log('KIEMTRA_DULIEU_DATCOC', check_dulieu_datcoc)
            if (!check_dulieu_datcoc) return false;
            var tao_dulieu_dc = await this.TAODULIEU_DATCOC(true, ma_kh, khachhang_id, khlon_id, ten_kh, diachi_kh, ghichu, thuebao_id, ma_tb, ten_tb, diachi_ld, 
                doituong_id, loaitb_id, dichvuvt_id, tocdo_id, donvi_id, thangbd_km, thangkt_km, thangbd_mg, sothang_mg, ctkhuyenmai_id, cuoc_dc, tien_td);
            
            if (!tao_dulieu_dc) {
                this.ShowError('Có lỗi tạo dữ liệu đặt cọc!')
                return false
            }
            var params1 = {
                p_js_hd_khachhang: this.dsHDKH && this.dsHDKH.length > 0 ? JSON.stringify(this.dsHDKH) : '', 
                p_js_hd_thuebao: this.dsHDTB && this.dsHDTB.length > 0 ? JSON.stringify(this.dsHDTB) : '', 
                p_js_hdtb_dc: this.hdtb_dc && this.hdtb_dc.length > 0 ? JSON.stringify(this.hdtb_dc) : '', 
                p_js_ct_tienhd: this.dsCTTHD && this.dsCTTHD.length > 0 ? JSON.stringify(this.dsCTTHD) : '', 
                p_huonggiao_id: this.huonggiao_tn, 
            }
            console.log('ThemMoi_HopDong_DatCoc', params1)
            var laphd1 = await this.ThemMoi_HopDong_DatCoc(params1);
            if (!laphd1) return false
            if (this.hdtb_dc_id <= 0) {
                err = "Không lấy đc ID của HĐ"
                this.ShowError(err)
                return err;
            }

            var giaophieu = await this.giaophieu_hs(phieu_id, this.hdtb_dc_id, this.hdkh_id_dc)
            if (giaophieu) return "1"
            else return "Lỗi giao phiếu HS"
        }
        catch (ex)
        {
            return "Có lỗi trong quá trình lập HĐ đặt cọc " + ex + "";
        }
    },
    TAODULIEU_DATCOC: async function (themmoi, ma_kh, khachhang_id, khlon_id, ten_kh,
        diachi, ghichu, thuebao_id, ma_tb, ten_tb, diachi_ld, doituong_id,
        loaitb_id, dichvuvt_id, tocdo_id, donvi_id,
        thangbd_km, thangkt_km, thangbd_mg, vsothang_mg,
        chitietkm_id, cuoc_dc, tien_td)
    {
        try {
            var result = true
            result = await this.TaoDuLieu_HDKH_DATCOC(themmoi, ma_kh, khachhang_id, khlon_id, ten_kh, diachi, ghichu);
            result = await this.TaoDuLieu_HDTB_CSS(themmoi, thuebao_id, ma_tb, ten_tb, diachi_ld, doituong_id,
                                loaitb_id, dichvuvt_id, tocdo_id, donvi_id);
            result = await this.TaoDuLieu_HDTB_DATCOC(themmoi, ghichu, thangbd_km, thangkt_km, thangbd_mg, vsothang_mg, chitietkm_id,
                                    cuoc_dc, tien_td);
            result = await this.TaoDuLieu_CT_TIENHD(themmoi, cuoc_dc);

            return result
        } catch (e) {
            console.log('TAODULIEU_DATCOC', e)
            return false
        }
    },
    TaoDuLieu_HDKH_DATCOC: async function (themmoi, ma_kh, khachhang_id, khlon_id, ten_kh, diachi_kh, ghichu)
    {
        try {
            var nhomtb_id = 0;
            //b.hdkh_id, b.ma_gd, b.ma_kh, b.khachhang_id, b.ten_kh, b.diachi_kh, b.ngaylap_hd, b.ghichu
            this.dsHDKH = [];
            this.hdkh_id_dc = 0
            if (themmoi)
            {
                this.hdkh_id_dc = await this.GET_KEYS("HD_KHACHHANG");
                //nhomtb_id = getkey.GetKey("NHOMTB");
            }
            var rowHDKH = {};
            rowHDKH.HDKH_ID = this.hdkh_id_dc;
            if (themmoi)
            {
                var ma_gd = await this.Lay_Ma_GD(LoaiHopDong.DATCOC_MOI);
                rowHDKH.MA_GD = ma_gd;
            }
            rowHDKH.MA_KH = ma_kh;
            if (khachhang_id > 0)
                rowHDKH.KHACHHANG_ID = khachhang_id;
            rowHDKH.KHLON_ID = khlon_id;
            rowHDKH.TEN_KH = ten_kh;
            rowHDKH.DIACHI_KH = diachi_kh;
            rowHDKH.NGAYLAP_HD = this.tt_nd.ngay_cn;
            rowHDKH.NGAY_YC = this.tt_nd.ngay_cn;
            rowHDKH.GHICHU = ghichu;
            rowHDKH.DONVI_ID = this.tt_nd.donvi_id;
            rowHDKH.NHANVIEN_ID = this.tt_nd.nhanvien_id;
            rowHDKH.LOAIHD_ID = LoaiHopDong.DATCOC_MOI;
            rowHDKH.NGUOI_CN = this.tt_nd.ma_nd;
            rowHDKH.MAY_CN = this.tt_nd.may_cn;
            rowHDKH.NGAY_CN = this.tt_nd.ngay_cn;

            this.dsHDKH.push(rowHDKH);
            return true
        } catch (e) {
            this.$toast.error('Lỗi TaoDuLieu_HDKH_DATCOC ' + JSON.stringify(e))
            return false
        }
    },
    TaoDuLieu_HDTB_CSS: async function (themmoi, thuebao_id, ma_tb, ten_tb, diachi_ld,
        doituong_id, loaitb_id, dichvuvt_id, tocdo_id, donvi_id)
    {
        try {
            //select a.hdtb_id, a.thuebao_id, a.ma_tb,a.ten_tb, a.diachi_tb, a.hdkh_id, a.kieuld_id, 
            //a.tthd_id, a.loaitb_id, a.doituong_id, a.dichvuvt_id, a.donvi_id, a.ngay_ht, a.goi_da_dv_id, a.nhomtb_id , a.chunhom,
            this.dsHDTB = [];
            if (themmoi )
                this.hdtb_dc_id = await this.GET_KEYS("HD_THUEBAO");
            var rowHDTB = {};
            //GOI_DADV_HDTB_DATA.GOI_DADV_HDTBRow rowGOI = dsGOIDV_HDTB.GOI_DADV_HDTB.NewGOI_DADV_HDTBRow();
            rowHDTB.HDTB_ID = this.hdtb_dc_id;
            rowHDTB.HDKH_ID = this.hdkh_id_dc;
            if (thuebao_id > 0)
                rowHDTB.THUEBAO_ID = thuebao_id;
            rowHDTB.MA_TB = ma_tb;
            rowHDTB.TEN_TB = ten_tb;
            rowHDTB.DIACHI_LD = diachi_ld;
            rowHDTB.DIACHI_TB = diachi_ld;

            rowHDTB.KIEULD_ID = KieuLapDat.DATCOC_MOI; // Kiểu đặt cọc mới
            rowHDTB.DOITUONG_ID = doituong_id;
            rowHDTB.TTHD_ID = TrangThaiHD.MOI;
            rowHDTB.LOAITB_ID = loaitb_id;
            rowHDTB.DICHVUVT_ID = dichvuvt_id;
            rowHDTB.DONVI_ID = donvi_id;
            rowHDTB.NGUOI_CN = this.tt_nd.ma_nd;
            rowHDTB.NGAY_CN = this.tt_nd.ngay_cn;
            rowHDTB.MAY_CN = this.tt_nd.may_cn;
            // vinhpv lay huonggiao_id
            // DataSet dset = bangts.LayHuongGiao_TiepNhan(LoaiHopDong.DATCOC_MOI, KieuLapDat.DATCOC_MOI); // Kieu
            var dshg = await this.LayHuongGiao_TiepNhan(LoaiHopDong.DATCOC_MOI, loaitb_id, tocdo_id, 0, KieuLapDat.DATCOC_MOI);
            this.quytrinh_id = 0;
            this.huonggiao_tn = 0;
            if (!dshg || dshg.length <= 0)
            {
                this.$toast.error("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!")
                return false
            }
            this.huonggiao_tn = Number(dshg["huonggiao_id"]);
            this.quytrinh_id = Number(dshg["quytrinh_id"]);

            rowHDTB.QUYTRINH_ID = this.quytrinh_id;
            this.dsHDTB.push(rowHDTB);
            return true
        } catch (e) {
            console.log('Lỗi TaoDuLieu_HDTB_CSS ', e)
            this.$toast.error('Lỗi TaoDuLieu_HDTB_CSS ' + JSON.stringify(e))
            return false
        }
    },
    TaoDuLieu_HDTB_DATCOC: async function (themmoi, ghichu, thang_bd_km, thang_kt_km,
        vthang_bd_mg, vsothang_mg, chitietkm_id, cuocdc, tien_td)
    {
        try {
            this.hdtb_dc = [];
            var rowGOI = {};
            this.thuebao_dc_id = 0
            this.rkm_id = 0
            if (themmoi)
            {
                this.thuebao_dc_id = await this.GET_KEYS("THUEBAO_DC_ID");
                this.rkm_id = await this.GET_KEYS("RKM_ID");
            }
            rowGOI.RKM_ID = this.rkm_id;
            rowGOI.THUEBAO_DC_ID = this.thuebao_dc_id;
            rowGOI.HDTB_ID = this.hdtb_dc_id;
            rowGOI.NHOM_DATCOC_ID = 1;


            rowGOI.THANG_BD = Number(thang_bd_km);
            // Tính số tháng: 
            var thang_kt = Number(thang_kt_km);

            rowGOI.THANG_KT = thang_kt;
            //----------
            var tmp = new Date(moment(vthang_bd_mg, 'YYYYMM'))
            var thang_bd_mg = Number(vthang_bd_mg);
            var thang_kt_mg = Number(moment(tmp.setMonth(tmp.getMonth() + vsothang_mg - 1)).format('YYYYMM'))
            
            if (!vsothang_mg || vsothang_mg == "0")
                thang_bd_mg = thang_kt_mg = 0;
            rowGOI.THANG_BD_MG = thang_bd_mg;
            rowGOI.THANG_KT_MG = thang_kt_mg;

            rowGOI.NGAY_DK = this.tt_nd.ngay_cn;
            rowGOI.CHITIETKM_ID = chitietkm_id;
            rowGOI.GHICHU = ghichu;
            rowGOI.CUOC_DC = cuocdc;
            rowGOI.TIEN_TD = tien_td;

            this.hdtb_dc.push(rowGOI);
            return true
        } catch (e) {
            console.log('Lỗi TaoDuLieu_HDTB_DATCOC ', e)
            this.$toast.error('Lỗi TaoDuLieu_HDTB_DATCOC ' + JSON.stringify(e))
            return false
        }
    },
    TAO_HD: async function (dr) {
        try {
            var err = ''
            var phieu_id = !dr["PHIEU_ID"] ? 0 : Number(dr["PHIEU_ID"]);
            var ma_tb = !dr["MA_TB"] ? "" : dr["MA_TB"];
            var dichvuvt_id = !dr["DICHVUVT_ID"] ? 0 : Number(dr["DICHVUVT_ID"]);
            var ma_kh = !dr["MA_KH"] ? "" : dr["MA_KH"];
            var ten_kh = !dr["TEN_KH"] ? "" : dr["TEN_KH"];
            var khachhang_id = !dr["KHACHHANG_ID"] ? 0 : Number(dr["KHACHHANG_ID"]);
            var thuebao_id = !dr["THUEBAO_ID"] ? 0 : Number(dr["THUEBAO_ID"]);
            var ma_gd = "";
            var loaihinh_tb = !dr["LOAITB_ID"] ? 0 : Number(dr["LOAITB_ID"]);
            var muccuoctb_id = !dr["MUCCUOCTB_ID"] ? -1 : Number(dr["MUCCUOCTB_ID"]);
            var tocdo_id = !dr["TOCDO_ID"] ? -1 : Number(dr["TOCDO_ID"]);
            var tocdo_cu_id = !dr["TOCDO_CU_ID"] ? -1 : Number(dr["TOCDO_CU_ID"]);
            var kieuld_id = !dr["KIEULD_ID"] ? 0 : Number(dr["KIEULD_ID"]);
            var muccuoc_tb = !dr["MUCCUOC_ID"] ? 0 : Number(dr["MUCCUOC_ID"]);
            var khuyenmai = !dr["LOAI_KM"] ? -1 : Number(dr["LOAI_KM"]);
            var tratruoc = !dr["LKMTT"] ? -1 : Number(dr["LKMTT"]);
            var km_id = !dr["KHUYENMAI_ID"] ? -1 : Number(dr["KHUYENMAI_ID"]);

            var ttkm_id = !dr["KHUYENMAITT_ID"] ? -1 : Number(dr["KHUYENMAITT_ID"]);
            var sothang_sdck = !dr["SOTHANG_CK"] ? 0 : Number(dr["SOTHANG_CK"]);

            var input = {
                ma_tb: ma_tb, 
                dichvuvt_id: dichvuvt_id,
                loaihd_id: LoaiHopDong.THAY_DOI_TOCDO_ADSL
            }
            var Kiemtra_hopdong_thaydoi_dt = await this.Kiemtra_hopdong_thaydoi_dt(input)

            if (Kiemtra_hopdong_thaydoi_dt != 0) {
                err = '"Thuê bao này đang được lập phụ lục khác có thay đổi đối tượng chưa hoàn thành.\nBạn không thể tiếp tục thêm phụ lục mới!"'
                this.$toast.error(err)
                return err
            }

            input = { maTb: ma_tb, dichVuVtId: dichvuvt_id, donViDlId: 0 }
            this.dsDBTB = await this.LAY_DANHBA_THEO_MATB(input);

            var ma_hd = this.dsDBTB[0]["ma_hd"];
            var ten_tb = this.dsDBTB[0]["ten_tb"]
            var donvi_ql_id = Number(this.dsDBTB[0]["donviql_id"]);
            var kieutracu = "";
            var kieutramoi = "";
            var thangtra = -1;
            var ngaytra = "";
            var ngay_sn = this.dsDBTB[0]["ngay_sn"];

            var ds = await this.LAY_DS_DB_ADSL_THEO_TBID(thuebao_id);
            var Bras_id = 0;
            var ip = "";
            if (ds && ds.length > 0) {
                ip = ds[0]["ip"];
                if (ds[0]["kieutra_id"] != "" && loaihinh_tb == LoaiHinhTB.INTERNET_MYTV) {
                    kieutracu = ds[0]["kieutra_id"];
                    kieutramoi = ds[0]["kieutra_id"];
                    if (Number(ds[0]["kieutra_id"]) == KieuTra.TRATRUOC) {
                        thangtra = Number(ds[0]["thang_id"]);
                        ngaytra = moment(ds[0]["ngaytra"]).format("dd/MM/yyyy");
                    }
                } else {
                    kieutracu = "";
                    kieutramoi = "";
                }

                if (ds[0]["bras_id"]) {
                    Bras_id = Number(ds[0]["bras_id"]);
                }
            }

            if (!(await this.KIEMTRADL_KHACHHANG(ma_kh, ten_kh, khachhang_id, ma_gd))) return false;
            if (!this.KIEMTRADL_THUEBAO(loaihinh_tb, ma_hd, khachhang_id, ten_tb, tocdo_id, tocdo_cu_id, kieuld_id)) return false;

            input = { maTb: ma_tb, khachHangId: khachhang_id, thueBaoId: thuebao_id, dichVuVtId: dichvuvt_id}
            if (!(await this.KIEMTRA_DB_KHI_THEMMOI(input))) {
                err = "Thuê bao không tồn tại trong danh bạ, không cập nhật được "
                this.$toast.error(err)
                return err
            }

            // dsKM_HDTB = new KHUYENMAI_HDTB_DATA();
            var dtiendatcoc_km = 0;
            var dvatdatcoc_km = 0;
            var sothang_sd = 0;
            // #region Lấy số tháng sử dụng
            if (khuyenmai == -1 && tratruoc == -1) {
                sothang_sd = sothang_sdck;
            } else if (khuyenmai == -1 && tratruoc != -1) {
                var khuyenmai_id_tt = ttkm_id;
                var strStkm = this.GetData(await api.fn_tt_db_khachhang({ type: 2, param: khuyenmai_id_tt}))
                var stkm = strStkm != "-1" ? Number(strStkm) : 0;
                sothang_sd = stkm;
                
            } else if (khuyenmai != 1 && tratruoc == -1) {
                var khuyenmai_id_tt = km_id;
                var strStkm = this.GetData(await api.fn_tt_db_khachhang({ type: 2, param: khuyenmai_id_tt}))
                var stkm = strStkm != "-1" ? Number(strStkm) : 0;
                sothang_sd = stkm;

            } else if (khuyenmai != 1 && tratruoc != 1) {
                var khuyenmai_id_tt = ttkm_id;
                var strSttt = this.GetData(await api.fn_tt_db_khachhang({ type: 2, param: khuyenmai_id_tt}))
                var st_tt = strSttt != "-1" ? Number(strSttt) : 0;

                var khuyenmai_id = km_id;
                var strStkm = this.GetData(await api.fn_tt_db_khachhang({ type: 2, param: khuyenmai_id}))
                var st_km = strStkm != "-1" ? Number(strStkm) : 0;

                sothang_sd = 0;
                if (st_tt > st_km)
                    sothang_sd = st_tt;
                else
                    sothang_sd = st_km;
            }
            // #endregion

            var mcuoc_id = 0;
            if (loaihinh_tb != LoaiHinhTB.INTERNET_MYTV &&
                loaihinh_tb != LoaiHinhTB.INTERNET_SGTV)
                mcuoc_id = muccuoc_tb;

            var dshg = await this.LayHuongGiao_TiepNhan(LoaiHopDong.THAY_DOI_TOCDO_ADSL, loaihinh_tb, tocdo_id, mcuoc_id, kieuld_id);
            this.quytrinh_id = 0;
            this.huonggiao_tn = 0;
            if (!dshg || dshg.length <= 0)
            {
                err = "Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!"
                this.$toast.error(err)
                return err
            }
            this.huonggiao_tn = Number(dshg["huonggiao_id"]);
            this.quytrinh_id = Number(dshg["quytrinh_id"]);
    
            var chk_km = !dr["CHK_KM"] ? "0" : dr["CHK_KM"];
            if (chk_km == "0") {
                err = "Không tìm thấy khuyến mại trong Khuyến mai- Đối tượng"
                this.$toast.error(err)
                return err;
            }
                
            var check_laphd_khac = await this.KIEMTRA_MA_TB_LAP_HOPDONG_KHAC(ma_tb, "", dichvuvt_id, LoaiHopDong.THAY_DOI_TOCDO_ADSL)
            if (check_laphd_khac) return false;
            if (kieuld_id == KieuLapDat.THAYDOI_KIEUTRA_MYTV) {
                return false;
            }
            var taodulieu = await this.TaoDuLieu(true, dr, donvi_ql_id, kieutracu, kieutramoi, thangtra, ngaytra, ip, Bras_id, ngay_sn, sothang_sd);
            if (!taodulieu) {
                this.ShowError('Có lỗi tạo dữ liệu hợp đồng!')
                return;
            }
            var params = {
                HD_KHACHHANG: this.dsHDKH, 
                HD_THUEBAO: this.dsHDTB, 
                HDTB_ADSL: this.dsTDTD_ADSL, 
                THONGTIN_TT: this.dsTTTT, 
                CT_TIENHD: this.dsCTTHD, 
                HDTB_DV: this.dsHDTBDV,
                KHUYENMAI_HDTB: this.dsKM_HDTB, 
                DATCOC_HDTB: this.dsDC_HDTB, 
                HDTB_CNTT: this.dsHDTB_CNTT, 
                HUONGGIAO_ID: this.huonggiao_tn
            }
            var laphd = await this.ThemMoi_HopDong_TDTD_V1(params);
            if (!laphd) return false
            if (!this.hdtb_id_cd || this.hdtb_id_cd <= 0) {
                this.$toast.error("Không lấy đc ID của HĐ")
                return false ;
            }
                
            var giaophieu = await this.giaophieu_hs(phieu_id, hdtb_id, hdkh_id)
            if (giaophieu) return "1"
            else return "Lỗi giao phiếu HS"
        }
        catch (ex)
        {
            // return "Có lỗi trong quá trình lập HĐ " + ex;
        }
    },
    ThemMoi_HopDong_TDTD_V1: async function (params) {
        try {
            var input = {ds_para: JSON.stringify(params)}
            let response = await api.sp_themmoi_hopdong_tdtd_v1(this.axios, input);
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
                this.$toast.success('Thêm mới hợp đồng TDTD thành công !')
                return true
            } else {
                this.$toast.error('Thêm mới hợp đồng TDTD thất bại ' + response.data.message_detail)
                return false
            }
        } catch (e) { 
            this.$toast.error('Lỗi Thêm mới hợp đồng TDTD ' + JSON.stringify(e)) 
            return false
        }
    },
    ThemMoi_HopDong_DatCoc: async function (params) {
        try {
            var input = params
            let response = await api.sp_themmoi_hopdong_datcoc(this.axios, input);
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
                this.$toast.success('Thêm mới hợp đồng thành công !')
                return true
            } else {
                this.$toast.error('Thêm mới hợp đồng thất bại ' + response.data.message_detail)
                return false
            }
        } catch (e) { 
            this.$toast.error('Lỗi Thêm mới hợp đồng ' + JSON.stringify(e)) 
            return false
        }
    },
    giaophieu_hs: async function (phieu_id, hdtb_id, hdkh_id) {
        try {
            var input = { phieu_id: phieu_id, hdtb_id: hdtb_id, hdkh_id: hdkh_id }
            let response = await api.sp_capnhat_giaophieu_hs_v2(this.axios, input);
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
                this.$toast.success('Cập nhật giao phiếu HS thành công !')
                return true
            } else {
                this.$toast.error('Cập nhật giao phiếu HS thất bại ' + response.data.message_detail)
                return false
            }
        } catch (e) { 
            this.$toast.error('Lỗi Cập nhật giao phiếu HS ' + JSON.stringify(e)) 
            return false
        }
    },
    TaoDuLieu: async function (themmoi, dr, donvi_id, kieutracu, kieutramoi, thangtra, ngaytra, ip, Bras_id, ngay_sn, sothang_sd) {
        try {
            var result = true
            var ma_tb = !dr["MA_TB"] ? "" : dr["MA_TB"];
            var dichvuvt_id = !dr["DICHVUVT_ID"] ? 0 : Number(dr["DICHVUVT_ID"]);
            var ma_kh = !dr["MA_TB"] ? "" : dr["MA_KH"];
            var ten_kh = !dr["TEN_KH"] ? "" : dr["TEN_KH"];
            var diachi_kh = !dr["DIACHI_KH"] ? "" : dr["DIACHI_KH"];
            
            var ghichu = !dr["GHICHU"] ? "" : dr["GHICHU"];
            var khachhang_id = !dr["KHACHHANG_ID"] ? 0 : Number(dr["KHACHHANG_ID"]);
            var thuebao_id = !dr["THUEBAO_ID"] ? 0 : Number(dr["THUEBAO_ID"]);
            var ma_gd = "";
            var loaihinh_tb = !dr["LOAITB_ID"] ? 0 : Number(dr["LOAITB_ID"]);
            var muccuoctb_id = !dr["MUCCUOCTB_ID"] ? 0 : Number(dr["MUCCUOCTB_ID"]);
            var muccuoc_id = !dr["MUCCUOC_ID"] ? 0 : Number(dr["MUCCUOC_ID"]);
            var tocdo_id = !dr["TOCDO_ID"] ? 0 : Number(dr["TOCDO_ID"]);
            var kieuld_id = !dr["KIEULD_ID"] ? 0 : Number(dr["KIEULD_ID"]);

            var tucthi = !dr["TUCTHI"] ? 0 : Number(dr["TUCTHI"]);
            var chitiet_km = !dr["CHITIETKM_ID"] ? 0 : Number(dr["CHITIETKM_ID"]);
            var khuyenmai_id = !dr["KHUYENMAI_ID"] ? 0 : Number(dr["KHUYENMAI_ID"]);
            var thanghuong_dc = !dr["THANGHUONG_DC"] ? "" : dr["THANGHUONG_DC"];
            var thanghuong_km = !dr["THANGHUONG_KM"] ? "" : dr["THANGHUONG_KM"];
                    
            var sothang_tgkm = !dr["SOTHANG_TGKM"] ? "" : dr["SOTHANG_TGKM"];

            var  datcoc_csd = !dr["DATCOC_CSD"] ? 0 : dr["DATCOC_CSD"];
            var loai_bdkm = !dr["LOAI_BDKM"] ? "" : dr["LOAI_BDKM"];
            var thang_bkdm = !dr["THANG_BDKM"] ? -1 : Number(dr["THANG_BDKM"]);
            var kieu_bdtg = !dr["KIEU_BDTG"] ? 0 : dr["KIEU_BDTG"];
            var thang_bdtg = !dr["THANG_BDTG"] ? "" : dr["THANG_BDTG"];
            var kieu_bddc = !dr["KIEU_BDDC"] ? 0 : dr["KIEU_BDDC"];

            var thang_bddc = !dr["THANG_BDDC"] ? "" : dr["THANG_BDDC"];
            var tyle_km = !dr["TYLE_KM"] ? "" : dr["TYLE_KM"];
            var tyle_tb = !dr["TYLE_TB"] ? "" : dr["TYLE_TB"];
            var tyle_sd = !dr["TYLE_SD"] ? "" : dr["TYLE_SD"];
            var doituong = !dr["DOITUONG_ID"] ? "" : dr["DOITUONG_ID"];
            var chk_km = !dr["CHK_KM"] ? "" : dr["CHK_KM"];
            
            var chitiet_kmtt = !dr["CHITIETKMTT_ID"] ? 0 : Number(dr["CHITIETKMTT_ID"]);
            var khuyenmai_ttid = !dr["KHUYENMAITT_ID"] ? 0 : Number(dr["KHUYENMAITT_ID"]);



            var tien_km_sd = !dr["TIENSD_KM"] ? 0 : Number(dr["TIENSD_KM"]);

            var tien_kmtt_sd = !dr["TIENSD_KMTT"] ? 0 : Number(dr["TIENSD_KMTT"]);

            result = await this.TaoDuLieu_HDKH(themmoi, ma_kh, khachhang_id, ten_kh, diachi_kh, ngay_sn);
            result = await this.TaoDuLieu_HDTB(themmoi, thuebao_id, kieuld_id, ghichu, muccuoctb_id, donvi_id, sothang_sd, doituong);
            
            result = await this.TaoDuLieu_HDTBDV(themmoi,thuebao_id);
            result = this.TaoDuLieu_TIEN_HDTB_TC(true, thuebao_id, 0);

            if (chitiet_km) {
                result = this.TAODULIEU_HDKM(0, khuyenmai_id, chitiet_km, thanghuong_dc, thanghuong_km, sothang_tgkm, datcoc_csd,
                                loai_bdkm, thang_bkdm, kieu_bdtg, thang_bdtg,
                                kieu_bddc, thang_bddc, tyle_km, tyle_tb, tyle_sd, 0, 0, 0, 0, tien_km_sd);
            }
            if (chitiet_kmtt)
            {
                thanghuong_dc = !dr["THANGHUONGTT_DC"] ? "" : dr["THANGHUONGTT_DC"];
                thanghuong_km = !dr["THANGHUONGTT_KM"] ? "" : dr["THANGHUONGTT_KM"];
                    
                sothang_tgkm = !dr["SOTHANG_TGKM_TT"] ? "" : dr["SOTHANG_TGKM_TT"];

                datcoc_csd = !dr["TIENTT_KM"] ? 0 : dr["TIENTT_KM"];
                loai_bdkm = !dr["LOAI_BDKM_TT"] ? "" : dr["LOAI_BDKM_TT"];
                thang_bkdm = !dr["THANG_BDTT"] ? -1 : Number(dr["THANG_BDTT"]);
                kieu_bdtg = !dr["KIEU_BDTG_TT"] ? 0 : dr["KIEU_BDTG_TT"];
                thang_bdtg = !dr["THANG_BDTG"] ? "" : dr["THANG_BDTG"];
                kieu_bddc = !dr["KIEU_BDDC_TT"] ? 0 : dr["KIEU_BDDC_TT"];

                thang_bddc = !dr["THANG_BDDC"] ? "" : dr["THANG_BDDC"];
                tyle_km = !dr["TYLE_KM_TT"] ? "" : dr["TYLE_KM_TT"];
                tyle_tb = !dr["TYLE_TB_TT"] ? "" : dr["TYLE_TB_TT"];
                tyle_sd = !dr["TYLE_SD_TT"] ? "" : dr["TYLE_SD_TT"];
                result = this.TAODULIEU_HDKM(1, khuyenmai_ttid, chitiet_kmtt, thanghuong_dc, thanghuong_km, sothang_tgkm, datcoc_csd,
                                    loai_bdkm, thang_bkdm, kieu_bdtg, thang_bdtg,
                                    kieu_bddc, thang_bddc, tyle_km, tyle_tb, tyle_sd, 0, 0, 0, 0, tien_kmtt_sd);
                
            }
            result = await this.TaoDuLieu_CT_TIENHD(true, datcoc_csd);
            
            switch (dichvuvt_id)
            {
                case DichVuVienThong.ADSL:
                    result = await this.TaoDuLieu_TDTD_ADSL(themmoi, thuebao_id, tocdo_id, loaihinh_tb, muccuoc_id, 0, ip, Bras_id,
                                        kieuld_id, tucthi);
                    break;
                case DichVuVienThong.ANTOAN_BAOMAT_TT:
                case DichVuVienThong.TRUNGTAM_DULIEU:
                case DichVuVienThong.DICHVU_CNTT:
                case DichVuVienThong.HOINGHI_TRUYENHINH:
                    result = await this.Tao_HDTB_CNTT(themmoi, thuebao_id, tocdo_id);
                    break;
                default:
                    result = await this.Tao_HDTB_CNTT(themmoi, thuebao_id, tocdo_id);
                    break;
            }
            return result
        } catch (e) { this.$toast.error('Lỗi TaoDuLieu ' + JSON.stringify(e)) 
            return false
        }
    },
    TaoDuLieu_HDKH: async function (themmoi, ma_kh, khachhang_id, ten_kh, diachi_kh, ngay_sn) {
        try {
            this.dsHDKH = [];
            this.hdkh_cd_id = 0
            if (themmoi)
                this.hdkh_cd_id = await this.GET_KEYS("HD_KHACHHANG");
            var rowHDKH = {};
            rowHDKH.HDKH_ID = this.hdkh_cd_id;
            if (themmoi) {
                //Hiếu - 19.10.2010: truyền thêm biến loại hợp đồng vào hàm lấy mã giao dich 
                var ma_gd = await this.Lay_Ma_GD(LoaiHopDong.THAY_DOI_TOCDO_ADSL);
                rowHDKH.MA_GD = ma_gd;
                rowHDKH.MA_HD = ma_gd;
            }

            rowHDKH.MA_KH = ma_kh;
            if (khachhang_id > 0)
                rowHDKH.KHACHHANG_ID = khachhang_id;
            rowHDKH.TEN_KH = ten_kh;
            rowHDKH.DIACHI_KH = diachi_kh;
            rowHDKH.NGAYLAP_HD = this.tt_nd.ngay_cn;
            //Hiếu bổ sung một số thông tin HDKH 24/08/2010
            rowHDKH.NGAY_YC = this.tt_nd.ngay_cn;
            if (ngay_sn) {
                rowHDKH.NGAY_SN = ngay_sn;
            }
            rowHDKH.DONVI_ID = this.tt_nd.donvi_id;
            rowHDKH.NHANVIEN_ID = this.tt_nd.nhanvien_id;

            if (khachhang_id > 0)
            {
                var khl_id = this.GetData(await api.fn_tt_db_khachhang({ type: 1, param: khachhang_id }))
                if (khl_id && khl_id != "" && khl_id != "-1")
                    rowHDKH.KHLON_ID = Number(khl_id);
                else
                    rowHDKH.KHLON_ID = 1;
            }
            else
                rowHDKH.KHLON_ID = 1;

            rowHDKH.LOAIHD_ID = LoaiHopDong.THAY_DOI_TOCDO_ADSL;
            rowHDKH.NGUOI_CN = this.tt_nd.ma_nd;
            rowHDKH.NGAY_CN = this.tt_nd.ngay_cn;
            rowHDKH.MAY_CN = this.tt_nd.may_cn;
            rowHDKH.IP_CN = this.tt_nd.ip;
            //
            this.dsHDKH.push(rowHDKH);
            return true
        } catch (e) { 
            this.$toast.error('Lỗi TaoDuLieu_HDKH ' + JSON.stringify(e)) 
            return false
        }
    },
    TaoDuLieu_HDTB: async function (themmoi, thuebao_id, kieu_ld, ghichu, muccuoc_id, donvi_id, sothang_sd, doituong) {
        try {
            this.hdtb_id_cd = 0;
            this.dsHDTB = [];

            if (themmoi)
                this.hdtb_id_cd = await this.GET_KEYS("HD_THUEBAO");
            var rowHDTB = {};

            rowHDTB.HDTB_ID = this.hdtb_id_cd;
            rowHDTB.HDKH_ID = this.hdkh_cd_id;
            if (thuebao_id > 0)
                rowHDTB.THUEBAO_ID = thuebao_id;

            //rowHDTB.MA_TB = txtMaTB.Text;
            //rowHDTB.TEN_TB = CommonFunction.StringtoString(txtTenTB.Text);
            //rowHDTB.DIACHI_TB = CommonFunction.StringtoString(diachi_tb);
            //rowHDTB.DIACHI_LD = CommonFunction.StringtoString(diachi_ld);
            rowHDTB.KIEULD_ID = kieu_ld;
            rowHDTB.DOITUONG_ID = Number(doituong);
            rowHDTB.TTHD_ID = TrangThaiHD.MOI;
            //rowHDTB.LOAITB_ID = loaitb_id;
            //rowHDTB.DICHVUVT_ID = dichvuvt_id;
            rowHDTB.DONVI_ID = donvi_id; //chua biet lay don vi o dau
            rowHDTB.NGUOI_CN = this.tt_nd.ma_nd;
            rowHDTB.NGAY_CN = this.tt_nd.ngay_cn;
            rowHDTB.MAY_CN = this.tt_nd.may_cn;
            rowHDTB.IP_CN = this.tt_nd.ip;
            rowHDTB.GHICHU = ghichu;
            rowHDTB.QUYTRINH_ID = this.quytrinh_id;
            if (muccuoc_id != -1)
            {
                rowHDTB.MUCUOCTB_ID = muccuoc_id;
                rowHDTB.MUCUOC_TB = this.GetData(await api.fn_tt_muccuoc_tb({ type: 1, param: muccuoc_id}))
            }
            rowHDTB.STATUS = TRANGTHAI_DONGBO.CHUADONGBO;
            //if (Convert.ToInt32(cboLoaihinhTB.SelectedValue) == LoaiHinhTB.INTERNET_FTTH)
            //{
            //    if (matocdo == "")
            //        rowHDTB.STATUS = TRANGTHAI_DONGBO.KHONGPHAI_DB;   //-1
            //    else
            //        rowHDTB.STATUS = TRANGTHAI_DONGBO.CHUADONGBO;  //=0
            //}
            //else
            //    rowHDTB.STATUS = TRANGTHAI_DONGBO.CHUADONGBO;

            //Hiếu bổ sung trường tiền đặt cọc cước sử dụng = 0 - 20.10.2010
            //rowHDTB.DATCOC_CSD = 0;

            if (sothang_sd)
                rowHDTB.SOTHANG_CK = Number(sothang_sd);

            this.dsHDTB.push(rowHDTB);
            //longdx: bo sung thong tin hdtb_id vao bang khuyenmai, dat coc
            if (this.dsDC_HDTB && this.dsDC_HDTB.length > 0) {
                for (var row of this.dsDC_HDTB)
                {
                    row.HDTB_ID = this.hdtb_id_cd;

                }
            }
            if (this.dsKM_HDTB && this.dsKM_HDTB.length > 0) {
                for (var row of this.dsKM_HDTB)
                {
                    row.HDTB_ID = this.hdtb_id_cd;
                }
            }
            return true
        } catch (e) { 
            this.$toast.error('Lỗi TaoDuLieu_HDTB ' + JSON.stringify(e)) 
            return false
        }
    },
    TaoDuLieu_HDTBDV: async function (themmoi, thuebao_id) {
        try {
            this.dsHDTBDV = [];
            var dtTemp = await this.LAY_DS_HDTB_DV(0, thuebao_id)
                // bangts.GetDataSql("select donvi_id, loaidv_id, kieudv_id from " + DatabaseConstants.DB2 +
                //                     ".dbtb_dv where thuebao_id =" + thuebao_id);
            if (!dtTemp || dtTemp.length <= 0) return false
            for (var i = 0; i < dtTemp.length; i++)
            {
                var row = {};
                row.HDTB_ID = this.hdtb_id_cd;
                row.LOAIDV_ID = Number(dtTemp[i]["loaidv_id"]);
                row.DONVI_ID = Number(dtTemp[i]["donvi_id"]);
                row.KIEUDV_ID = Number(dtTemp[i]["kieudv_id"]);
                this.dsHDTBDV.push(row);
            }
            return true
        } catch (e) { 
            this.$toast.error('Lỗi TaoDuLieu_HDTBDV ' + JSON.stringify(e)) 
            return false
        }
    },
    TaoDuLieu_TIEN_HDTB_TC(themmoi, thuebao_id, tientra) {
        try {
            this.dsTIEN_HDTB = [];
            var rowTIEN_HDTB = {};
            rowTIEN_HDTB.HDTB_ID = this.hdtb_id_cd;
            rowTIEN_HDTB.THUEBAO_ID = thuebao_id;
            rowTIEN_HDTB.LOAITIEN_ID = 3;
            rowTIEN_HDTB.GIATRI = Number(tientra);
            this.dsTIEN_HDTB.push(rowTIEN_HDTB);
            return true
        } catch (e) { 
            this.$toast.error('Lỗi TaoDuLieu_TIEN_HDTB_TC ' + JSON.stringify(e)) 
            return false
        }
    },
    TAODULIEU_HDKM(kieu_km, khuyenmai_id, chitietkm_id, thanghuong_dc, thanghuong_km, 
        sothang_tgkm, tiendatcoc, loai_bdkm, thang_bdkm, kieu_bdtg, 
        thang_bdtg, kieu_bddc, thang_bddc, tyle_km, tyle_tb, 
        tyle_sd, vdtiendatcoc_km, vdvatdatcoc_km, vdtiendatcoc_tt, vdvatdatcoc_tt, tien_sd)
    {
        try {
            var tien_kmld = 0;//Tiềm khuyến mại lắp đặt
            var vat_kmld = 0;//Tiềm khuyến mại lắp đặt
            var tyle_kmld = 0;//Tỷ lệ khuyến mại lắp đặt
            var tien_datcoc = 0;
            var i_tien_td = 0;
            var i_th_datcoc = 0;
            //longdx: bien dung cho tra truoc
            var tien_kmld_tt = 0;//Tiềm khuyến mại lắp đặt
            var vat_kmld_tt = 0;//Tiềm khuyến mại lắp đặt
            var tyle_kmld_tt = 0;//Tỷ lệ khuyến mại lắp đặt
            var tien_datcoc_tt = 0;
            var vchuyenmang = 0;

            var _loaitb_id = 0;
            var _muccuoc_id = 0;
            var _tocdo_id = 0;
            
            var tmp;
            var ithang_huongdc = 0, ithang_huongkm = 0, ithang_tg = 0;
            var dtiendatcoc_csd = 0, dtien_td = 0;  
               
            if (kieu_km == 0) {
                this.dsKM_HDTB = [];
                    
                dtiendatcoc_csd = 0;
                dtien_td = 0;
                ithang_huongdc = 0;
                ithang_huongkm = 0;
                ithang_tg = 0;
                var dr = {};
                dr.KHUYENMAI_ID = khuyenmai_id;
                dr.CHITIETKM_ID = chitietkm_id;
                dr.KIEU_YC = 1;
                ithang_huongdc = thanghuong_dc;
                ithang_huongkm = thanghuong_km;
                ithang_tg = sothang_tgkm;
                dtiendatcoc_csd = tiendatcoc;
                if (ithang_huongdc > 0) dtien_td = Math.Round(dtiendatcoc_csd / ithang_huongdc);
                i_tien_td = dtien_td;
                dr.THANG_HUONGDC = ithang_huongdc;
                dr.THANG_HUONGKM = ithang_huongkm;
                dr.SOTHANG_TG = ithang_tg;
                dr.DATCOC_CSD = dtiendatcoc_csd;
                dr.TIEN_TD = dtien_td;
                dr.TIEN_SD = tien_sd;
                if (loai_bdkm && loai_bdkm == "3")
                {
                    if (!thang_bdkm || thang_bdkm == -1)
                    {
                        this.$toast.error("Bạn phải nhập tháng bắt đầu khuyến mại");
                        return false;
                    }

                    if (!moment(thang_bdkm, 'YYYYMM').isValid())
                    {
                        this.$toast.error("Bạn phải nhập tháng bắt đầu khuyến mại theo định dạng yyyyMM");
                        return false;
                    }
                    var tmp = new Date(moment(thang_bdkm, 'YYYYMM'))
                    dr.THANG_BD = Number(thang_bdkm);
                    dr.THANG_KT = Number(moment(tmp.setMonth(tmp.getMonth() + ithang_huongkm - 1)).format('YYYYMM'))
                }
                if (kieu_bdtg && kieu_bdtg == "3")
                {
                    if (!thang_bdtg || thang_bdtg == "")
                    {
                        this.$toast.error("Bạn phải nhập tháng bắt đầu trả góp");
                        return false;
                    }
                    if (!moment(thang_bdtg, 'YYYYMM').isValid())
                    {
                        this.$toast.error("Bạn phải nhập tháng bắt đầu trả góp theo định dạng yyyyMM");
                        return false;
                    }
                    
                    var tmp = new Date(moment(thang_bdtg, 'YYYYMM'))
                    dr.THANG_BDTG = Number(thang_bdtg);
                    dr.THANG_KTTG = Number(moment(tmp.setMonth(tmp.getMonth() + ithang_tg - 1)).format('YYYYMM'));
                }

                if (kieu_bddc && kieu_bddc == "3")
                {
                    if (!thang_bddc || thang_bddc == "")
                    {
                        this.$toast.error("Bạn phải nhập tháng bắt đầu trả trước");
                        return false;
                    }
                    if (!moment(thang_bddc, 'YYYYMM').isValid())
                    {
                        this.$toast.error("Bạn phải nhập tháng bắt đầu đặt cọc theo định dạng yyyyMM");
                        return false;
                    }
                    var tmp = new Date(moment(thang_bddc, 'YYYYMM'))
                    dr.THANG_BDDC = Number(thang_bddc);
                    dr.THANG_KTDC = Number(moment(tmp.setMonth(tmp.getMonth() + ithang_huongdc - 1)).format('YYYYMM'));
                }
                //longdx: bổ sung phần cập nhật các tỷ lệ

                dr.TYLE_KMLD = Number(tyle_km);
                dr.TYLE_SD = Number(tyle_sd);
                dr.TYLE_TB = Number(tyle_tb);
                dr.HDTB_ID = this.hdtb_id_cd;
                //fix riêng cho HCM
                if (this.tt_nd.phanvung_id && Number(this.tt_nd.phanvung_id) == 28)
                {
                    if (dr.TYLE_SD > 50 && kieu_km == 1)
                    {
                        this.$toast.error("Tỷ lệ sử dụng không được lớn hơn 50%!");
                        return false;
                    }
                }
                this.dsKM_HDTB.push(dr);

                //longdx: cong don tien dat coc
                tien_datcoc += Number(tiendatcoc);

            }
            else
            {
                this.dsDC_HDTB = [];
                var dr = {};
                dr.KHUYENMAI_ID = khuyenmai_id;
                dr.CHITIETKM_ID = Number(chitietkm_id);
                dr.KIEU_YC = 1;
                ithang_huongdc = thanghuong_dc
                ithang_huongkm = thanghuong_km
                dtiendatcoc_csd = tiendatcoc
        
                if (ithang_huongdc > 0) dtien_td = Math.round(dtiendatcoc_csd / ithang_huongdc);
                i_tien_td = dtien_td;
                i_th_datcoc = ithang_huongdc;
                dr.THANG_HUONGDC = ithang_huongdc;
                dr.THANG_HUONGKM = ithang_huongkm;
                dr.SOTHANG_TG = ithang_tg;
                dr.DATCOC_CSD = dtiendatcoc_csd;
                dr.TIEN_TD = dtien_td;
                dr.TIEN_SD = tien_sd;
                if (loai_bdkm && loai_bdkm == "3")
                {
                    if (!thang_bdkm || thang_bdkm == -1)
                    {
                        this.$toast.error("Bạn phải nhập tháng bắt đầu khuyến mại");
                        return;
                    }
                    if (!moment(thang_bdkm, 'YYYYMM').isValid())
                    {
                        this.$toast.error("Bạn phải nhập tháng bắt đầu khuyến mại theo định dạng yyyyMM");
                        return;
                    }
                    var tmp = new Date(moment(thang_bdkm, 'YYYYMM'))
                    dr.THANG_BD = Number(thang_bdkm);
                    dr.THANG_KT = Number(moment(tmp.setMonth(tmp.getMonth() + ithang_huongkm - 1)).format('YYYYMM'))
                }
                if (kieu_bdtg && kieu_bdtg == "3")
                {
                    if (!thang_bdtg || thang_bdtg == "")
                    {
                        this.$toast.error("Bạn phải nhập tháng bắt đầu trả góp");
                        return;
                    }
                    if (!moment(thang_bdtg, 'YYYYMM').isValid())
                    {
                        this.$toast.error("Bạn phải nhập tháng bắt đầu trả góp theo định dạng yyyyMM");
                        return;
                    }
                    var tmp = new Date(moment(thang_bdtg, 'YYYYMM'))
                    dr.THANG_BDTG = Number(thang_bdtg);
                    dr.THANG_KTTG = Number(moment(tmp.setMonth(tmp.getMonth() + ithang_tg - 1)).format('YYYYMM'))
                }
                if (kieu_bddc && kieu_bddc == "3")
                {
                    if (!thang_bddc || thang_bddc == "")
                    {
                        this.$toast.error("Bạn phải nhập tháng bắt đầu trả trước");
                        return false;
                    }
                    if (!moment(thang_bddc, 'YYYYMM').isValid())
                    {
                        this.$toast.error("Bạn phải nhập tháng bắt đầu đặt cọc theo định dạng yyyyMM");
                        return false;
                    }
                    //nhapt them 26/09/2016
                    //Kiem tra tháng đặt cọc >= tháng hiện tại
                    if (Number(thang_bddc) < Number(moment(new Date())).format('YYYYMM'))
                    {
                        this.$toast.error("Tháng bắt đầu đặt cọc phải lớn hơn hoặc bằng với tháng hiện tại.");
                        return false;
                    }
                    var tmp = new Date(moment(thang_bddc, 'YYYYMM'))
                    dr.THANG_BDDC = Number(thang_bddc);
                    dr.THANG_KTDC = Number(moment(tmp.setMonth(tmp.getMonth() + ithang_huongdc - 1)).format('YYYYMM'));
                }
                //longdx: bổ sung phần cập nhật các tỷ lệ
                dr.TYLE_KMLD = Number(tyle_km);
                dr.TYLE_SD = Number(tyle_sd);
                dr.TYLE_TB = Number(tyle_tb);
                dr.HDTB_ID = this.hdtb_id_cd;
                this.dsDC_HDTB.push(dr);

                //Lấy tiền khuyến mại 
                //longdx: cong don tien dat coc
                tien_datcoc_tt = Number(tiendatcoc);
            }


            var vdtiendatcoc_km_local = Math.round(tien_datcoc * 10 / 11);
            var vdvatdatcoc_km_local = tien_datcoc - vdtiendatcoc_km_local;

            var vdtiendatcoc_tt_local = Math.round(tien_datcoc_tt * 10 / 11);
            var vdvatdatcoc_tt_local = tien_datcoc_tt - vdtiendatcoc_tt_local;
            for (var j = 0; j < this.ds_tien_khoanmuc.length; j++)
            {
                if (this.ds_tien_khoanmuc[j]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_DATCOC_KM)
                {
                    this.ds_tien_khoanmuc[j]["tien"] = vdtiendatcoc_km_local;
                    this.ds_tien_khoanmuc[j]["vat"] = vdvatdatcoc_km_local;
                }
                if (this.ds_tien_khoanmuc[j]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_DATCOC)
                {
                    this.ds_tien_khoanmuc[j]["tien"] = vdtiendatcoc_tt_local;
                    this.ds_tien_khoanmuc[j]["vat"] = vdvatdatcoc_tt_local;
                }
            }

            var dtongtien = 0;
            var dtongvat = 0;
            for (var i = 0; i < this.ds_tien_khoanmuc.length; i++)
            {

                dtongtien += Number(this.ds_tien_khoanmuc[i]["tien"]);
                dtongvat += Number(this.ds_tien_khoanmuc[i]["vat"]);

            }
            this.tongtien = "";
            this.tongvat = "";
            this.tongall = "";
            this.tongtien = dtongtien;
            this.tongvat = dtongvat;
            this.tongall = dtongtien + dtongvat;
            return true
        } catch (e) { 
            this.$toast.error('Lỗi TaoDuLieu_TIEN_HDTB_TC ' + JSON.stringify(e)) 
            return false
        }
    },
    TaoDuLieu_CT_TIENHD: async function (themmoi, cuocdc) {
        try {
            this.dsCTTHD = [];
            var row = {};
            row.HDTB_ID = this.hdtb_dc_id;
            var CT_TIENHD_ID = 0
            if (themmoi)
            {
                CT_TIENHD_ID = await this.GET_KEYS("CT_TIENHD");
            }
            row.CT_TIENHD_ID = CT_TIENHD_ID;
            row.KHOANMUCTT_ID = KHOANMUC_TT.KMTT_DATCOC;
            var tien = 0;
            var vat = 0;
            tien = Math.round(Number(cuocdc)/ 1.1);
            vat = Number(cuocdc) - tien;
            row.TIEN = tien;
            row.VAT = vat;
            row.ID = KHOANMUC_TT.KMTT_DATCOC;
            row.LOAI_ID = 1;
            this.dsCTTHD.push(row);
            return true
        } catch (e) { 
            console.log('Lỗi TaoDuLieu_CT_TIENHD ', e)
            this.$toast.error('Lỗi TaoDuLieu_CT_TIENHD ' + JSON.stringify(e)) 
            return false
        }
    },
    TaoDuLieu_TDTD_ADSL: async function (themmoi, thuebao_id, tocdo_id, loaihinh_tb, muccuoc_id, 
        kieutramoi, ip, Bras_id, kieu_ld, tucthi)
    {
        try {
            this.dsTDTD_ADSL = [];
            var rowTDTD_ADSL = {};
            var tmp = await this.LAY_DS_DB_ADSL_THEO_TBID(thuebao_id)
            if (tmp && tmp.length > 0) {
                tmp = this. UpperCasePropertyList(tmp)
            } else {
                return false
            }
        
            rowTDTD_ADSL = tmp[0]

            rowTDTD_ADSL.HDTB_ID = this.hdtb_id_cd;
            rowTDTD_ADSL.TOCDO_ID = tocdo_id;
            var profiledsl_id = this.GetData(await api.fn_tt_db_khachhang({ type: 1, param: tocdo_id }))
            if (profiledsl_id != -1)
            {
                rowTDTD_ADSL.PROFILEDSL_ID = profiledsl_id;
            }
            else
                rowTDTD_ADSL.PROFILEDSL_ID = 0;
            rowTDTD_ADSL.MUCCUOC_ID = muccuoc_id;

            rowTDTD_ADSL.IP = ip;
            if (Bras_id != 0)
                rowTDTD_ADSL.BRAS_ID = Bras_id;
            rowTDTD_ADSL.THONGTIN_TC = "";

            if (loaihinh_tb == LoaiHinhTB.INTERNET_MYTV
                || loaihinh_tb == LoaiHinhTB.INTERNET_SGTV)
                rowTDTD_ADSL.TUCTHI = 2; // Với mytv, saigontv thì thay đổi tức thì luôn.
            else
            {
                if (tucthi == 0)
                    rowTDTD_ADSL.TUCTHI = 0;
                if (tucthi == 1 && kieu_ld == KieuLapDat.THAYDOI_MUCCUOC_ADSL)
                    rowTDTD_ADSL.TUCTHI = 1;
                if (tucthi == 1 && kieu_ld == KieuLapDat.THAYDOI_TOCDO_ADSL)
                    rowTDTD_ADSL.TUCTHI = 2;
                if (tucthi == 1 && kieu_ld == KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL)
                    rowTDTD_ADSL.TUCTHI = 3;
                if (tucthi == 1 &&
                    (loaihinh_tb == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC
                        || loaihinh_tb == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC))
                    rowTDTD_ADSL.TUCTHI = 4;
            }
            this.dsTDTD_ADSL.push(rowTDTD_ADSL);
            return true
        } catch (e) { 
            this.$toast.error('Lỗi TaoDuLieu_TDTD_ADSL ' + JSON.stringify(e)) 
            return false
        }
    },
    Tao_HDTB_CNTT: async function (themmoi, thuebao_id, tocdo_id)
    {
        try {
            var dt = await this.LAY_DS_DB_CNTT_THEO_TBID(thuebao_id);
            var ItemList = {}
            if (dt && dt.length > 0) {
                ItemList = dt[0]
                ItemList[2].sValue = tocdo_id;
            }           
            
            this.dsHDTB_CNTT = ItemList;
            return true
        } catch (e) { 
            this.$toast.error('Lỗi Tao_HDTB_CNTT ' + JSON.stringify(e)) 
            return false
        }
    },
    GET_KEYS: async function(key) {
      var input = {
        keyname: key
      }
      let response = await api.getKey(this.axios, input)
      if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
        return Number(response.data.data)
      } else {
        this.$toast.error('Có lỗi trong quá trình tạo key mới')
        return
      }
    },
    async Lay_Ma_GD_V2(loaihd_id) {
      var magd = "";
      await api.post_sinh_magd_v2(this.axios, {
        donvi_id: this.$root.token.getDonViID(),
        loaihd_id: loaihd_id,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          magd = response.data.data;
        }
      });
      return magd;
    },
    async Lay_Ma_GD(loaihd_id) {
      var magd = "";
      await api.post_sinh_magd(this.axios, {
        loaihd_id: loaihd_id,
      }).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000"
        ) {
          magd = response.data.data;
        }
      });
      return magd;
    },
    LAY_DS_HDTB_DV: async function (hd_id, tb_id) {
      var input = {
        hd_id: hd_id,
        tb_id: tb_id,
        phanvung_id: this.tt_nd.phanvung_id
      }
      let response = await api.sp_load_hdtb_dv(this.axios, input)
      if (response && response.data && response.data.error_code == 'BSS-00000000') {
        return response.data.data
      } else if (response.data.error_code == 'BSS-00000204') {
        // this.ShowError("Không có TT đơn vị của HDTB");
        return []
      } else {
        this.ShowError('Lỗi khi lấy TT đơn vị của HDTB  ' + response.data.message_detail)
        return []
      }
    },
    LAY_DANHBA_THEO_MATB: async function (input) {
        try {
            let response = await api.sp_lay_danhba_theo_matb(this.axios, input);
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
                var data = response.data.data
                if (data instanceof Array) return response.data.data
                if (data instanceof Object) return [response.data.data]
            } else return []
        } catch (e) { 
            console.log('LAY_DANHBA_THEO_MATB', e)
            return [] 
        }
    },
    LAY_DS_DB_ADSL_THEO_TBID: async function (input) {
        try {
            let response = await api.sp_lay_ds_danhba_adsl(this.axios, input);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data;
            } else return []
        } catch (e) { return [] }
    },
    LAY_DS_DB_CNTT_THEO_TBID: async function (input) {
        try {
            let response = await api.sp_lay_ds_db_cntt_theo_tbid(this.axios, input);
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                return response.data.data;
            } else return []
        } catch (e) { return [] }
    },
    async LayHuongGiao_TiepNhan(loaiHdId, loaiTbId, tocDoId, mucCuocId, kieuLdId) {
        let result;
        try {
            const params = { 
                loaiHdId: loaiHdId,
                loaiTbId: loaiTbId,
                tocDoId: tocDoId,
                mucCuocId: mucCuocId,
                kieuLdId: kieuLdId
            };
            let response = await api.sp_lay_huonggiao_tiepnhan(this.axios, params);
            
            if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                result = response.data.data;                
            }
        } catch(e) {
            console.log(e);           
        }
        return result;
    },
    Kiemtra_hopdong_thaydoi_dt: async function (input) {
        try {
            let response = await api.sp_kiemtra_hopdong_thaydoi_dt(this.axios, input);
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
                return true;
            } else return false
        } catch (e) { return false }
    },
    KIEMTRADL_KHACHHANG: async function (ma_kh, ten_kh, khachhang_id, ma_gd) {
        var err = "";
        if (!ten_kh || ten_kh == "") {
            err ="Hãy nhập tên khách hàng !";
            this.$toast.error(err)
            return false;
        }

        if (!ma_kh && khachhang_id != 0) {
            err = "Kiểm tra lại mã khách hàng !";
            this.$toast.error(err)
            return false;
        }
        //Hieutc: 11.10.2011: Kiểm tra mã khách hàng đã theo đúng chuẩn chưa
        if (ma_kh != "") {
            var input = { ma_kh: ma_kh, kieu: 1, loaiHdId: LoaiHopDong.THAY_DOI_TOCDO_ADSL}
            var check = await this.Kiemtra_makh_mtt(input)
            if (check == 0) {
                err = "Mã khách hàng không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!";
                this.$toast.error(err)
                return false;
            }
        }
        if (ma_gd != "") {
            var input = { ma_kh: ma_gd, kieu: 3, loaiHdId: LoaiHopDong.THAY_DOI_TOCDO_ADSL}
            var check = await this.Kiemtra_makh_mtt(input)
            if (check == 0) {
                err = "Mã giao dịch không đúng chuẩn quy định của Viễn thông tỉnh.\nBạn hãy kiểm tra lại!";
                this.$toast.error(err)
                return false;
            }
        }
        
        return true;
    },
    Kiemtra_makh_mtt: async function (input) {
        try {
            let response = await api.sp_kiemtra_makh_matt_magd(this.axios, input);
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
                return true;
            } else return false
        } catch (e) { return false }
    },
    KIEMTRADL_THUEBAO(loaihinh_tb, ma_hd, khachhang_id, ten_tb, tocdo_id, tocdo_cu_id, kieuld_id) {
        var err = "";
        if (!ma_hd && khachhang_id != 0) {
            err= "Bạn chưa nhập mã hợp đồng !";
            this.$toast.error(err)
            return false;
        }

        if (!ten_tb || ten_tb == "") {
            err=  "Bạn chưa nhập tên thuê bao !";
            this.$toast.error(err)
            return false;
        }

        if (kieuld_id == -1) {
            err=  "Hãy chọn kiểu yêu cầu cho thuê bao này !";
            this.$toast.error(err)
            return false;
        }


        if (kieuld_id == KieuLapDat.THAYDOI_TOCDO_ADSL || kieuld_id == KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL) {
            if (tocdo_id == -1) {
                err=  "Hãy nhập tốc độ mới !";
                this.$toast.error(err)
                return false;
            }

            if (tocdo_cu_id == tocdo_id) {
                err= "Dữ liệu về tốc độ mới và tốc độ cũ không được giống nhau !";
                this.$toast.error(err)
                return false;
            }
        }
        return true;
    },
    KIEMTRA_DB_KHI_THEMMOI: async function (input) {
        try {
            let response = await api.sp_kiemtra_danhba_khi_themmoi(this.axios, input);
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
                return true;
            } else return false
        } catch (e) { return false }
    },
    KIEMTRA_DULIEU_DATCOC: async function (themmoi, thuebao_id, ma_tb, dichvuvt_id, loaihinhtb_id, 
        ctkhuyenmai_id, khuyenmai_id, sothang_mg, cuoc_dc, tien_td, thangbd_km) {
        try {
            var err = ''
            if (themmoi)
            {                
                var kt = await this.lay_ten_loaihd_theo_thuebao_id(thuebao_id);
                if (kt && kt.length > 0)
                {
                    err ="Thuê bao đang có hợp đồng/ phụ lục " + kt[0][0] + " chưa hoàn thiện!";
                    this.$toast.error(err)
                    return false;
                }
                var tienno = await this.TraCuu_No_MaTB(thuebao_id);
                if (tienno > 0)
                {
                    err = "Thuê bao: " + ma_tb + " còn nợ: " + tienno + " vnđ.\nPhải thanh toán trước khi thực hiện!";
                    this.$toast.error(err)
                    return false;
                }
                if (tienno == -1)
                {
                    err = "Không tìm thấy thông tin về thuê bao!";
                    this.$toast.error(err)
                    return false;
                }
            }
            if (thuebao_id <= 0)
            {
                err = "Không có thông tin thuê bao!";
                this.$toast.error(err)
                return false;
            }
            if (khuyenmai_id == -1)
            {
                err = "Chưa có chi tiết khuyến mại !";
                this.$toast.error(err)
                return false;
            }
            if (ctkhuyenmai_id == -1)
            {
                err ="Bạn hãy chọn trả trước !";
                this.$toast.error(err)
                return false;
            }
            if (!ma_tb || ma_tb == "")
            {
                err ="Bạn chưa nhập số máy/account !";
                this.$toast.error(err)
                return false;
            }
            /* if (chkThangMG.Checked)
             {
                 if (txtSoThangMG.Text.Trim() == "")
                 {
                     Message_Box.ShowWarning("Hãy nhập số tháng miễn giảm");
                     txtSoThangMG.Focus();
                     return false;
                 }
               
             }*/
            if (!sothang_mg || sothang_mg == "")
            {
                err ="Bạn chưa nhập số tháng phải đặt cọc";
                this.$toast.error(err)
                return false;
            }

            if (!cuoc_dc || cuoc_dc == "")
            {
                err ="Bạn chưa nhập cước đặt cọc !";
                this.$toast.error(err)
                return false;
            }

            if (!cuoc_dc || cuoc_dc <= 0)
            {
                err ="Tiền đặt cọc phải lớn hơn 0";
                this.$toast.error(err)
                return false;
            }

            if (!tien_td || tien_td == "")
            {
                err ="Hãy kiểm tra lại số tiền trừ dần /tháng!";
                this.$toast.error(err)
                return false;
            }



            if (themmoi)
            {
                //Nếu là thêm mới, tháng bắt đầu không được nhỏ hơn tháng hiện tại
                // int thang_bd = Convert.ToInt32(this.tt_nd.ngay_cn.ToString("yyyyMM"));
                // if (thang_bd < Convert.ToInt32(this.tt_nd.ngay_cn.ToString("yyyyMM")))
                // {
                //     Message_Box.ShowWarning("Khi thêm mới, tháng bắt đầu không được nhỏ hơn tháng hiện tại!");
                //     return false;
                // }

                if (thuebao_id != 0) {
                    var input = {
                        p_thuebao_id: thuebao_id, 
                        p_ma_tb: ma_tb, 
                        p_dichvuvt_id: Number(dichvuvt_id), 
                        p_loaitb_id: Number(loaihinhtb_id)
                    }
                    var Kiemtra_loaitb_id_db_thuebao = this.GetData(await api.sp_kiemtra_loaitb_id_db_thuebao(this.axios, input))
                    if (!Kiemtra_loaitb_id_db_thuebao)
                    {
                        this.ShowError("Hãy kiểm tra lại loại hình/dịch vụ/trạng thái của thuê bao. Thuê bao phải ở trạng thái hoạt động bình thường. " + ma_tb);
                        return false;
                    }
                }

            }

            var thangbdmoi = 0;
            var thangktmoi = 0;
            var tmp = new Date(moment(thangbd_km, 'YYYYMM'))            
            thangbdmoi = Number(thangbd_km);
            thangktmoi = Number(moment(tmp.setMonth(tmp.getMonth() + Number(sothang_mg) - 1)).format('YYYYMM'))
            if (themmoi)
            {
                var input = { 
                    p_thuebao_dc_id: this.thuebao_dc_id, 
                    p_ma_tb: ma_tb, 
                    p_dichvuvt_id: dichvuvt_id, 
                    p_loaitb_id: loaihinhtb_id, 
                    p_thang_bd: thangbdmoi, 
                    p_thang_kt: thangktmoi, 
                    p_nhom_datcoc_id: 1
                }
                var kt = await this.Kiemtra_tb_datcoc(input)
                if (!kt)
                {
                    this.ShowError("Dữ liệu bị trùng khoảng thời gian đặt cọc. Bạn hãy kiểm tra lại!");
                    return false;
                }
                //nhapt 12042017: Thực hiện kiểm tra; nếu có khuyến mại 100% trùng thời gian đặt cọc thì thông báo
                var kt1 = await this.Kiemtra_datcoc_tyle_KM(this.thuebao_dc_id, ma_tb, dichvuvt_id, thangbdmoi, thangktmoi)
                if (kt1)
                {
                    this.ShowError("Dữ liệu bị trùng khoảng thời gian hưởng khuyến mại. Bạn hãy kiểm tra lại!");
                    return false;
                }

                var kt2 = await this.lay_ten_loaihd_theo_thuebao_id(thuebao_id);
                if (kt2 && kt2.length > 0)
                {
                    this.$toast.error("Thuê bao đang có hợp đồng/ phụ lục " + kt2[0][0] + " chưa hoàn thiện!")
                    return false;
                }
            }

            return true;
            
        } catch (e) { 
            console.log('KIEMTRA_DULIEU_DATCOC', e)
            return false 
        }
    },
    lay_ten_loaihd_theo_thuebao_id: async function(thuebao_id) {
        try {
            var input = { 
                thuebao_id: thuebao_id
            }
            let response = await api.sp_lay_ten_loaihd_theo_thuebao_id(this.axios, input);
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            } else return []
        } catch (e) { return [] }
    },
    TraCuu_No_MaTB: async function(thuebao_id) {
        try {
            var input = { 
                thuebao_id: thuebao_id
            }
            let response = await api.sp_tracuu_no_thuebao(this.axios, input);
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
                return response.data.data
            } else return -1
        } catch (e) { return -1 }
    },
    Kiemtra_tb_datcoc: async function(input) {
        try {
            let response = await api.fn_kiemtra_tb_datcoc(this.axios, input);
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
                return true;
            } else return false
        } catch (e) { return false }
    },
    Kiemtra_datcoc_tyle_KM: async function(thuebao_dc_id, ma_tb, dichvuvt_id, thangbdmoi, thangktmoi) {
        try {
            var input = { 
                vthuebao_dc_id: thuebao_dc_id, 
                vma_tb: ma_tb, 
                vdichvuvt_id: dichvuvt_id, 
                vthang_bd: thangbdmoi, 
                vthang_kt: thangktmoi 
            }
            let response = await api.fn_kiemtra_datcoc_tyle_km(this.axios, input);
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
                return response.data.data;
            } else return '1'
        } catch (e) { return '1' }
    },
    async KIEMTRA_MA_TB_LAP_HOPDONG_KHAC (maTb, maGd, dichVuVtId, loaiHdId) {
      let result = false
      await api.sp_kiemtra_thuebao_lap_hopdong_khac(this.axios, { 
            maTb: maTb, 
            maGd: maGd, 
            dichVuVtId: dichVuVtId, 
            loaiHdId: loaiHdId 
        }).then(response => {
            if (response && response.data && response.data.error_code == '0' && response.data.data) {
                if (response.data.data.length > 0) {
                    result = true
                    let tenLoaiHd = response.data.data[0].ten_loaihd
                    let maGd = response.data.data[0].ma_gd
                    let nguoiCn = response.data.data[0].nguoi_cn
                    let tenDV = response.data.data[0].ten_dv
                    let ngayYc = response.data.data[0].ngay_yc
                    this.$root.$toast.error(`Số máy/Acc: ${matb} đang được lập bởi hợp đồng (phụ lục) ${tenLoaiHd} có mã giao dịch ${maGd} do User ${nguoiCn} thuộc đơn vị ${tenDV} thực hiện vào ngày ${ngayYc}`)
                }
            }
      })
      return result
    },
    XOA_DS_HDPL: async function () {
        try {
            this.CLEAR_LOI();

            if (!this.gridTiepNhan.list || this.gridTiepNhan.list.length == 0) {
                this.$toast.error("Không có dữ liệu phiếu để xóa HĐ");
                return;
            }

            if (!this.gridTiepNhan.selectedItems || this.gridTiepNhan.selectedItems.length == 0) {
                this.$toast.error("Bạn chưa chọn phiếu để xóa HĐ");
                return;
            }
            
            this.$bvModal.msgBoxConfirm("Bạn có chắc chắn muốn xóa danh sách các HĐ/PL này không ?", {
                title: "Thông báo",
                size: "sm",
                okTitle: "Đồng ý",
                cancelTitle: "Hủy",
            }).then(async (v) => {
                if (v) {
                    this.loading(true);
                    await this.SP_XOA_DS_HDPL();
                    await this.NAP_DS_DL_PHIEU_BANCHAM();
                    await this.NAP_DS_LOI();
                    this.loading(false);
                }
            }).catch((e) => {
                console.log(e);
                this.ShowError("Lỗi lập HĐ " + JSON.stringify(e));
                this.loading(false);
            });
        }
        catch (ex)
        {
            this.$toast.error("Có lỗi khi xóa phiếu " + ex);
        }
    },
    SP_XOA_DS_HDPL: async function ()
    {
        // #region Xoa DS HDPL

        var dt = this.LowerCasePropertyList(this.gridTiepNhan.selectedItems)
        var tong = dt.length;
        var thucthi = 0;
        for (var i = 0; i < dt.length; i++)
        {
            var vphieu_id = !dt[i]["phieu_id"] ? 0 : Number(dt[i]["phieu_id"]);
            var vhdtb_id = !dt[i]["hdtb_id"] ? 0 : Number(dt[i]["hdtb_id"]);

            var vhdkh_id = !dt[i]["hdkh_id"] ? 0 : Number(dt[i]["hdkh_id"]);
            if (vphieu_id == 0 || vhdtb_id == 0 || vhdkh_id == 0)
                continue;

            if (dt[i]["tocdo_id"])
            {   
                var kq = await this.xoa_hdpl_bancham(1, vphieu_id, vhdtb_id, vhdkh_id, LoaiHopDong.THAY_DOI_TOCDO_ADSL);
                if (kq == "1")
                {
                    thucthi += 1;

                }
                else
                {
                    var row_loi = {
                        phieu_id: dt[i]["phieu_id"],
                        ma_tb: dt[i]["ma_tb"],
                        giogiao: dt[i]["giogiao"],
                        ngaygiao: dt[i]["ngaygiao"],
                        nd_loi: kq
                    }
                    this.grcThongTinLoi.list.push(row_loi)
                }
            }
            else
            {   
                var kq = await this.xoa_hdpl_bancham(1, vphieu_id, vhdtb_id, vhdkh_id, LoaiHopDong.DATCOC_MOI);
                if (kq == "1")
                {
                    thucthi += 1;

                }
                else
                {
                    var row_loi = {
                        phieu_id: dt[i]["phieu_id"],
                        ma_tb: dt[i]["ma_tb"],
                        giogiao: dt[i]["giogiao"],
                        ngaygiao: dt[i]["ngaygiao"],
                        nd_loi: kq
                    }
                    this.grcThongTinLoi.list.push(row_loi)
                }
            }
        }
    },
    xoa_hdpl_bancham: async function(vkieu, vphieu_id, vhdtb_id, vhdkh_id, vloaihd_id) {
        try {
            var input = { 
                vkieu: vkieu, 
                vphieu_id: vphieu_id, 
                vhdtb_id: vhdtb_id, 
                vhdkh_id: vhdkh_id, 
                vloaihd_id: vloaihd_id 
            }
            let response = await api.sp_xoa_hdpl_bancham(this.axios, input);
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
                return "1";
            } else return false
        } catch (e) { return false }
    },
    THANHTOAN_HD: async function () {
        try {
            if (!this.gridTiepNhan.list || this.gridTiepNhan.list.length == 0) {
                this.$toast.error("Không có dữ liệu phiếu để thanh toán");
                return;
            }

            if (!this.gridTiepNhan.selectedItems || this.gridTiepNhan.selectedItems.length == 0) {
                this.$toast.error("Bạn chưa chọn phiếu để thanh toán");
                return;
            }

            var vma_gd = this.gridTiepNhan.selectedItems[0]["MA_GD"]
            var tocdo_id = this.gridTiepNhan.selectedItems[0]["TOCDO_ID"]
            var tthd_id = this.gridTiepNhan.selectedItems[0]["TTHD_ID"]
            var tt_hd = this.gridTiepNhan.selectedItems[0]["TT_HD"]

            if (tocdo_id == 0) {
                if (tthd_id > 1)
                {
                    this.ShowError("Hợp đồng " + tt_hd + " !");
                    return;
                }

                var ds = this.GetData(await this.sp_lay_ds_thamso_md_mats({ma_ts: "KIEMTRA_GOI"}));
                if (ds && ds.length > 0) {
                    if (ds[0]["ten_ts"] && ds[0]["ten_ts"].toString().trim() == "1")
                    {
                        var _dtDK_Goi = this.GetData(await this.sp_kiemtra_dk_goidadv({vhdkh_id : vhdkh_id}));
                        if (_dtDK_Goi[0][0] != "1")
                        {
                            this.ShowError(_dtDK_Goi[0][0]);
                            return;
                        }
                    }
                }
                    
            } else {
                if (tthd_id > 1)
                {
                    this.ShowError("Hợp đồng " + tt_hd + " !");
                    return;
                }
            }


            this.$router.push({
                name: 'Payment',
                params: {
                    magd: vma_gd
                }
            })
        }
        catch(ex)
        {
            this.ShowError("Có lỗi khi thanh toán hợp đồng " + ex);
        }
    },
    XOA_THANHTOAN_HD() {
        try {
            if (!this.gridTiepNhan.list || this.gridTiepNhan.list.length == 0) {
                this.$toast.error("Không có dữ liệu phiếu để thanh toán");
                return;
            }

            if (!this.gridTiepNhan.selectedItems || this.gridTiepNhan.selectedItems.length == 0) {
                this.$toast.error("Bạn chưa chọn phiếu để thanh toán");
                return;
            }

            this.popupComponent = () => import('@/modules/Payment/ListOfPaymentList')
            this.popupComponentHeader = ''
            this.popupComponentName = 'frmDanhSachPhieuTT'
            this.popupComponentData = {
                title: 'Danh sách phiếu thanh toán'
            }
            this.popupComponentEvts = {
                'form-close': this.popupClosed
            }

            this.Popup('popupComponents')
            // var ds_tn = gridTiepNhan.DataSource as DataTable;
            // if (ds_tn == null || grvTiepNhan.DataRowCount == 0)
            // {
            //     Message_Box.ShowWarning("Không có dữ liệu phiếu để xóa thanh toán");
            //     return;
            // }
            // var index = grvTiepNhan.FocusedRowHandle;
            // if (index < 0)
            // {
            //     Message_Box.ShowWarning("Bạn chưa chọn phiếu để xóa thanh toán");
            //     return;
            // }
            // long phieu_id = grvTiepNhan.GetRowCellValue(index, "PHIEU_ID") == null || grvTiepNhan.GetRowCellValue(index, "PHIEU_ID") == DBNull.Value
            //             ? 0
            //                         : Number(grvTiepNhan.GetRowCellValue(index, "PHIEU_ID"));

            // long tocdo_id = grvTiepNhan.GetRowCellValue(index, "TOCDO_ID") == null || grvTiepNhan.GetRowCellValue(index, "TOCDO_ID") == DBNull.Value
            //         ? 0
            //                         : Number(grvTiepNhan.GetRowCellValue(index, "TOCDO_ID"));

            // long vhdkh_id = grvTiepNhan.GetRowCellValue(index, "HDKH_ID") == null || grvTiepNhan.GetRowCellValue(index, "HDKH_ID") == DBNull.Value
            //         ? 0
            //                         : Number(grvTiepNhan.GetRowCellValue(index, "HDKH_ID"));
            // string vma_gd = grvTiepNhan.GetRowCellValue(index, "MA_GD") == null || grvTiepNhan.GetRowCellValue(index, "MA_GD") == DBNull.Value
            //         ? ""
            //                         : grvTiepNhan.GetRowCellValue(index, "MA_GD");

            // int tthd_id = grvTiepNhan.GetRowCellValue(index, "TTHD_ID") == null || grvTiepNhan.GetRowCellValue(index, "TTHD_ID") == DBNull.Value
            //         ? 0
            //                         : Number(grvTiepNhan.GetRowCellValue(index, "TTHD_ID"));

            // string tt_hd = grvTiepNhan.GetRowCellValue(index, "TT_HD") == null || grvTiepNhan.GetRowCellValue(index, "TT_HD") == DBNull.Value
            //     ? ""
            //                     : grvTiepNhan.GetRowCellValue(index, "TTHD_ID");
            // if (phieu_id == 0)
            // {
            //     Message_Box.ShowWarning("Bạn chưa chọn phiếu để xóa thanh toán");
            //     return;
            // }
            
            //     var f = new frmDanhSachPhieuTT();
            //     f.MdiParent = MdiParent;
            //     f.kieu = 1;
            //     f.phieu_id = phieu_id;
            //     f.Show();
            //     f.DanhSach();
            //     f.Closed += (sn, en) =>
            //                 { 
            //                     NAP_DS_DL_PHIEU_BANCHAM();
            //                     NAP_DS_LOI();

            //                 }; 
        }
        catch (ex)
        {
            this.ShowError("Có lỗi khi xóa thanh toán hợp đồng " + ex);
        }
    },
    gridTiepNhan_RowSelected: function (args) {
        // var item = {}
        // if (args.data instanceof Array) item = args.data[0]
        // else if ((args.data instanceof Object)) item = args.data
        // console.log('gridTiepNhan_RowSelected', item)
    },
    gridTiepNhan_ItemsChanged: function (selectedItems) {
        if (selectedItems && selectedItems.length > 0)
            this.gridTiepNhan.selectedItems = selectedItems
        else this.gridTiepNhan.selectedItems = []
    },
    cbbTrangThaiPhieu_EditValueChanged: async function() {
        try {
            this.loading(true)
            await this.SETTING_BUTTON();
            await this.NAP_DS_DL_PHIEU_BANCHAM();
            await this.CLEAR_LOI();
            this.loading(false)
        } catch (e) {this.loading(false)}
    },
    changeTuNgay: function(value_) {
      if (!moment(value_, 'DD/MM/YYYY').isValid()) {
        this.dtpTimTuNgay.value = '01/01/2000'
      } else {
        this.dtpTimTuNgay.value = value_
      }
    },
    changeDenNgay: function(value_) {
      if (!moment(value_, 'DD/MM/YYYY').isValid()) {
        this.dtpTimDenNgay.value = '01/01/2000'
      } else {
        this.dtpTimDenNgay.value = value_
      }
    },
    clearForm: function () {
        this.cbbPhanLoaiHS.value = 0;
        this.cbbTrangThaiPhieu.value = 0;
        this.dtpTimTuNgay.value = moment(new Date()).format('DD/MM/YYYY');
        this.dtpTimDenNgay.value = moment(new Date()).format('DD/MM/YYYY');
    },
    LowerCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toLowerCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    UpperCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    GetData: function (response) {
      if (
        response &&
        response.data &&
        response.data.error_code == "BSS-00000000"
      ) {
        return response.data.data;
      } else {
        // console.log(response.data.message);
        // this.ShowError(response.data.message);
        return [];
      }
    },
    ShowSuccess: function (msg) {
        this.$toast.success(msg);
    },
    ShowError: function (msg) {
        this.$toast.error(msg);
    },
    Popup(modalId) {
        this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
        this.$bvModal.hide(modalId);
        this.popupComponent = null;
    },
    popupNhanText_open() {
        this.popupComponent = () => import('./popupNhapText')
        this.popupComponentHeader = ''
        this.popupComponentName = 'popupNhapText'
        this.popupComponentData = {
            title: 'Nhập nội dung trả phiếu'
        }
        this.popupComponentEvts = {
            'form-close': this.popupClosed
        }

        this.Popup('popupComponents')
    },
    popupClosed: async function (val) {
        try {
            if (!val) {
                this.ClosePopup("popupComponents");
                return;
            }
            this.loading(true);
        
            switch (this.popupComponentName) {
                case "popupNhapText":                 
                    this.noidung = val
                    await this.sp_thaotac_phieu()
                    break;
                case "frmDanhSachPhieuTT":    
                    await this.NAP_DS_DL_PHIEU_BANCHAM();
                    await this.NAP_DS_LOI();
                    break;
                    
            }
        } catch (ex) {
            if (ex.data && ex.data.message_detail) {
            this.$toast.error("" + ex.data.message_detail);
            } else {
            this.$toast.error("" + ex);
            }
            
            if (this.debug) console.error(ex);
        } finally {
            this.ClosePopup("popupComponents");
            this.loading(false);
        }
    },
    btnChitiet_click: function (item) {
        this.popupComponent = () =>
            import(
            "@/modules/admin/bnm/BNMScriptRequests/BNMScriptRequests.vue"
            );
        this.popupComponentName = "popup_DSYeuCauTaoScript";
        this.popupComponentHeader = 'Danh sách yêu cầu tạo scripts từ BNM';
        this.popupComponentData = {
            isPopup: true,
            ...item  
        }; 
        this.Popup("popupComponents");
    },
    btnLayTT_Click: async function (e) {
        this.loading(true)
        await this.NAP_DS_DL_PHIEU_BANCHAM();
        await this.CLEAR_LOI();
        this.loading(false)
    },
    tsbtnTraPhieu_Click: async function  (e) {
        this.THAOTAC_PHIEU_BANCHAM(3);
    },
    tsbtnLapHD_Click(e) {
        this.THAOTAC_PHIEU_BANCHAM(2);
    },    
    tsbtnXoaHD_Click(e) {
        // TTPhieu_ID
        if(this.cbbTrangThaiPhieu.value == 2)
            this.THAOTAC_PHIEU_BANCHAM(1);
        if(this.cbbTrangThaiPhieu.value == 4)
            this.THAOTAC_PHIEU_BANCHAM(5);
    },
    tsbtnThanhToan_Click(e) {
        this.THAOTAC_PHIEU_BANCHAM(4);
    },
    
    
    }
}
</script>  