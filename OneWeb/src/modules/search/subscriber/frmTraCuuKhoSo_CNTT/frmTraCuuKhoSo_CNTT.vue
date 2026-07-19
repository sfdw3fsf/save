<!-- <template>
    <div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a @click="tsbtnTimKiem_Click">
                        <span class="icon one-search"></span>Tìm kiếm
                    </a>
                </li>
                <li>
                    <a @click="btnChapNhan_Click" v-show="tsbtnChapNhan.Visible" :disabled="!tsbtnChapNhan.Enabled">
                        <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp nhận
                    </a>
                </li>

                <li>
                    <a @click="tsbtnInHD_Click">
                        <span class="icon nc-icon-glyph files_single-content-02"></span>Hồ sơ
                    </a>
                </li>
                <li>
                    <a @click="btnInPhieuTT_Click">
                        <span class="icon one-print"></span>In phiếu TT
                    </a>
                </li>
                <li>
                    <a v-show="btnInHopDong.Visible" :disabled="!btnInHopDong.Enabled">
                        <span class="icon one-print"></span>In HĐ theo mẫu
                    </a>
                </li>
                <li>
                    <a v-show="btnInPL.Visible" :disabled="!btnInPL.Enabled">
                        <span class="icon one-print"></span>In phụ lục
                    </a>
                </li>
                <li>
                    <a v-show="tsbtnInBB.Visible" :disabled="!tsbtnInBB.Enabled" @click="tsbtnInBB_Click">
                        <span class="icon one-print"></span>In BB
                    </a>
                </li>
                <li>
                    <a @click="tsbtnKhuyenMai_Click">
                        <span class="icon one-sale"></span>Khuyến mại
                    </a>
                </li>
                <li :style="!isShowTaoHDDT ? 'display: none' : ''">
                    <a @click="tsbtnTaoHDDT_Click">
                        <span class="icon one-print"></span>Tạo HĐĐT
                    </a>
                </li>
            </ul>
        </div>
        <div class="page-content">
            <div class="box-form">
                <div class="legend-title">
                    <span class="fa fa-angle-up icon"></span>Thông tin tìm kiếm
                </div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w110">
                                <div class="check-action">
                                    <input type="checkbox" v-model="chkLoaiHD.Checked" class="check">
                                    <span class="name">Loại HĐ/PL</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt v-model="cboLoaiHD.SelectedValue" :allowFiltering="true"
                                        :disabled="!chkLoaiHD.Checked" :dataSource="cboLoaiHD.list" dataValueField="ID"
                                        dataTextField="NAME">
                                    </SelectExt>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w140">
                                Tra cứu theo
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt v-model="cboDKTraCuu.SelectedValue" :allowFiltering="true"
                                        :disabled="!cboDKTraCuu.Enabled" :dataSource="cboDKTraCuu.list" dataValueField="ID"
                                        dataTextField="NAME">
                                    </SelectExt>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w110">
                                <div class="check-action">
                                    <input type="checkbox" v-model="chkDVVT.Checked" class="check">
                                    <span class="name">Dịch vụ</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt v-model="cboDichVuVT.SelectedValue" :allowFiltering="true"
                                        :disabled="!chkDVVT.Checked" :dataSource="cboDichVuVT.list" dataValueField="ID"
                                        dataTextField="NAME">
                                    </SelectExt>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w75">
                                Mã GD
                            </div>
                            <div class="value">
                                <input type="text" v-model="inputValue.ma_gd" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w140">
                                Thông tin tra cứu
                            </div>
                            <div class="value">
                                <input type="text" v-model="inputValue.tt_tracu" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w90">
                                Mã HĐ-TTKD
                            </div>
                            <div class="value">
                                <input type="text" v-model="inputValue.ma_hd_ttkd" class="form-control">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w110">
                                <div class="check-action">
                                    <input type="checkbox" v-model="chkNgayYC.Checked" class="check">
                                    <span class="name">Ngày YC</span>
                                </div>
                            </div>
                            <div>
                                <div class="input-icon-right">
                                    <vue-date :disable="!chkNgayYC.Checked" :format="'DD/MM/YYYY'" v-model="dtpTuNgay" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-3 col-12">
                        <div class="info-row">
                            <div class="key w70">
                                Đến ngày
                            </div>
                            <div class="value">
                                <div class="input-icon-right">
                                    <vue-date :disable="!chkNgayYC.Checked" :format="'DD/MM/YYYY'" v-model="dtpDenNgay" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">

                            <div class="key w140">
                                <div class="check-action">
                                    <input type="checkbox" v-model="chkTrangThai.Checked" class="check">
                                    <span class="name">Trạng thái HĐ/PL</span>
                                </div>

                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt v-model="cboTrangThai.SelectedValue" :allowFiltering="true"
                                        :disabled="!chkTrangThai.Checked" :dataSource="cboTrangThai.list"
                                        dataValueField="ID" dataTextField="NAME">
                                    </SelectExt>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <div class="box-form">
                <div class="legend-title"><span @click="clickShowHideHD" class="fa icon"
                        :class="{ 'fa-angle-up': expanded.hop_dong, 'fa-angle-down': !expanded.hop_dong, }"></span> Danh
                    sách
                    hợp đồng/phụ lục khách hàng</div>
                <div class="table-content">
                    <DataGrid v-bind:dataSource="dataSourcesHD.list" v-bind:columns="dataSourcesHD.cols" ref="dataSourcesHD"
                        :panelDataHeight="'150'" :showFilter="true" :enablePagingServer="false"
                        :enabledSelectFirstRow="true" :allowPaging="true" @rowSelected="rowSelected"
                        v-show="expanded.hop_dong" />
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title"><span @click="clickShowHideTT" class="fa icon"
                        :class="{ 'fa-angle-up': expanded.thanhtoan, 'fa-angle-down': !expanded.thanhtoan, }"></span> Danh
                    sách
                    thanh toán</div>
                <div class="table-content">
                    <DataGrid v-bind:dataSource="dataSourcesTT.list" v-bind:columns="dataSourcesTT.cols" ref="dataSourcesTT"
                        :panelDataHeight="'150'" :showFilter="true" :enablePagingServer="false"
                        :enabledSelectFirstRow="true" :allowPaging="true" @rowSelected="rowGridTTSelected"
                        v-show="expanded.thanhtoan" />
                </div>

            </div>
            <div class="box-form">
                <div class="legend-title"><span @click="clickShowHideTB" class="fa icon"
                        :class="{ 'fa-angle-up': expanded.thuebao, 'fa-angle-down': !expanded.thuebao, }"></span> Danh sách
                    thuê
                    bao</div>
                <div class="table-content">
                    <DataGrid v-bind:dataSource="dataSourcesTB.list" v-bind:columns="dataSourcesTB.cols" ref="dataSourcesTB"
                        :panelDataHeight="'150'" :showFilter="true" :enablePagingServer="false"
                        :enabledSelectFirstRow="true" :allowPaging="true" @rowSelected="rowGridTBSelected"
                        @recordDoubleClick="gridviewThueBao_DoubleClick" v-show="expanded.thuebao" />
                </div>

            </div>





        </div>
        <footer class="footer">
            VNPT-IT &copy; 2021
        </footer>
        <TraCuuThongSoKTModal ref="traCuuThongSoKTModal" :dichvuvt_id="dichvuvt_selected" :ma_tb="ma_tb" :kieu="1"
            :thuebao_id="thuebao_id" />
        <TraCuuKhuyenMaiModal ref="TraCuuKhuyenMaiModal" :hdtb_id="data_thuebao.hdtb_id" :vkieu="3" />
        <InBienBan ref="popupInBB" :params="modelIn" />
        <InHoSo ref="popupInHoSo" :params="popupInHoSo_Param" />
        <InPhieuThu ref="popupInPhieuThu" :ma_gd="inputValue.ma_gd" />

        <!-- udpate them tao hop dong dien tu -->
        <CreatEInvoiceModal ref="popupCreateEInvoice" :kieuld_id="this.kieuld_id" :loaitb_id="this.loaitb_id"
            v-show="isShowTaoHDDT" />
    </div>
<!-- </template> -->

<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component'
import API from './api'

import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'

export default {

    components: { breadcrumb, VueFlatPickr, API, DataGridCustomm },
    name: 'frmTraCuuKhoSo_CNTT',
    props: {
        //inMaTB: String
    },
    async mounted() {
        await this.LoadCombobox()
        await this.DichVuChange(false)
        if (this.$route.query.ma_tb != null && this.$route.params.ma_tb != '' && this.$route.query.ma_tb.length > 0) {
            this.MaTB = this.$route.query.ma_tb
            this.onMaTBEnter()
        }
        if (this.inMaTB) {
            this.MaTB = this.inMaTB
            this.hideBreadcrumb = 'hide'
            this.onMaTBEnter()
        }
        //await this.HienThiTT_DBKH();
    },
    data: () => ({
       
        dateconfig: {
            altFormat: 'd/m/Y H:i:S',
            altInput: true,
            dateFormat: 'd/m/Y H:i:S',
            allowInput: true
        },
        header: {
            title: 'TRA CỨU DANH BẠ',
            list: [
                { name: 'Tra cứu', link: { name: 'Ui.cards' } },
                {
                    name: 'Tra cứu tổng hợp',
                    link: { name: 'Ui.buttons' }
                }
            ]
        },
        toptarget: '.SearchGeneral',
        tsbtnHuyGiao: true,
        tsbtnTimKiem: true,
        tsbtnGiaoPhieu: true,
        ctmsGiaoPhieu: true,
        ctmsHuyPhieu: true,
        isTimTheoQuyTrinh: false,
        isTimTheoDichVu: false,
        isTimTheoLoaiHD: false,
        hideBreadcrumb: 'show',

        listbox: {
            ds_nhomgoi: {
                list: [],
                header: [
                    { fieldName: 'ten_goi', headerText: 'Tên nhóm/gói', allowFiltering: true, width: 250 },
                    { fieldName: 'chunhom', headerText: 'Chủ nhóm', allowFiltering: true },
                    { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, allowHtml: true },
                    { fieldName: 'ngay_dk', format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, textAlign: 'Right', type: 'date', headerText: 'Ngày ĐK', allowFiltering: true, allowHtml: true },
                    { fieldName: 'thang_bd', headerText: 'Tháng BĐ', textAlign: 'Right', allowFiltering: true, allowHtml: true },
                    { fieldName: 'thang_kt', headerText: 'Tháng KT', textAlign: 'Right', allowFiltering: true, allowHtml: true }
                ],
                value: {},
                isEnabled: true,
                checked: []
            },
            ds_khuyenmai: {
                list: [],
                header: [
                    { fieldName: 'loai_km', headerText: 'Loại KM', allowFiltering: true, width: 150 },
                    { fieldName: 'ten_chitietkm', headerText: 'Chi tiết KM	', allowFiltering: true, width: 200 },
                    { fieldName: 'ngay_dkdc', headerText: 'Ngày ĐK', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'ngay_bd', headerText: 'Ngày BĐ', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'ngay_kt', headerText: 'Ngày KT', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'ngay_bddc', headerText: 'Ngày BĐĐC', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'ngay_ktdc', headerText: 'Ngày KTĐC', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'thang_bd', headerText: 'Tháng BĐ', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'thang_kt', headerText: 'Tháng KT', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'thang_bddc', headerText: 'Tháng BĐĐC', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'thang_ktdc', headerText: 'Tháng KTĐC', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'ngay_thoai', headerText: 'Ngày thoái', format: { type: 'date', format: 'dd/MM/yyyy' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'thang_thoaitra', headerText: 'Tháng thoái', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'tien_thoai', headerText: 'Tiền thoái', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 100 },
                    { fieldName: 'hieuluc', headerText: 'Hiệu lực', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'thang_huy', headerText: 'Tháng hủy', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'datcoc_csd', headerText: 'Tiền đặt cọc', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 100 },
                    { fieldName: 'tyle_sd', headerText: 'Tỷ lệ SD', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'tien_sd', headerText: 'Tiền SD', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 100 },
                    { fieldName: 'cuoc_sd', headerText: 'Giảm cước SD', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'tien_td', headerText: 'Tiền TD', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 100 },
                    { fieldName: 'tyle_tb', headerText: 'Tỷ lệ TB', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'tien_tb', headerText: 'Tiền TB', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 100 },
                    { fieldName: 'cuoc_tb', headerText: 'Giảm cước TB', format: 'N0', type: 'Number', textAlign: 'Right', allowFiltering: true, width: 100 },
                    { fieldName: 'tyle_tk', headerText: 'Tỷ lệ TK', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'tyle_tc', headerText: 'Tỷ lệ TC', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 100 },
                    { fieldName: 'noidung', headerText: 'Nội dung', allowFiltering: true, allowHtml: true, width: 100 }
                ],
                value: {},
                isEnabled: true,
                checked: []
            },
            ds_bdchuahoanthanh: {
                list: [],
                header: [
                    { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, width: 150 },
                    { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true, width: 150 },
                    { fieldName: 'ten_loaihd', headerText: 'Loại HĐ/PL', allowFiltering: true, allowHtml: true, width: 150 },
                    { fieldName: 'ngay_yc', textAlign: 'Right', headerText: 'Ngày YC', allowFiltering: true, allowHtml: true, width: 150 },
                    { fieldName: 'trangthai_hd', headerText: 'Trạng thái', allowFiltering: true, allowHtml: true, width: 150 },
                    { fieldName: 'ma_gd', headerText: 'Mã giao dịch', allowFiltering: true, allowHtml: true, width: 150 }
                ],
                value: {},
                isEnabled: true,
                checked: []
            },
            ds_thuebao: {
                list: [],
                header: [
                    { fieldName: 'ma_tt', headerText: 'Mã TT', allowFiltering: true, width: 150 },
                    { fieldName: 'ma_tt_neo', headerText: 'Mã TT Neo', allowFiltering: true, width: 150 },
                    { fieldName: 'ma_tb', headerText: 'Số máy/Acc', allowFiltering: true, allowHtml: true, width: 150 },
                    { fieldName: 'ten_tb', headerText: 'Tên TB', allowFiltering: true, allowHtml: true, width: 150 },
                    { fieldName: 'sochinh', headerText: 'Số chính', allowFiltering: true, allowHtml: true, width: 150 },
                    { fieldName: 'ma_lt', headerText: 'Số ảo', allowFiltering: true, allowHtml: true, width: 150 },
                    { fieldName: 'tocdo', headerText: 'Tốc độ', allowFiltering: true, allowHtml: true, width: 150 },
                    { fieldName: 'trangthai_tb', headerText: 'Trạng thái', allowFiltering: true, allowHtml: true },
                    { fieldName: 'ngay_sd', headerText: 'Ngày SD', format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 150 },
                    { fieldName: 'ngay_cat', headerText: 'Ngày cắt', format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 150 },
                    { fieldName: 'ngay_td', headerText: 'Ngày TD', format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 150 },
                    { fieldName: 'ngay_kp', headerText: 'Ngày KP', format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 150 },
                    { fieldName: 'tg_thue_tu', headerText: 'Thuê từ ngày', format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 150 },
                    { fieldName: 'tg_thue_den', headerText: 'Thuê đến ngày', format: { type: 'date', format: 'dd/MM/yyyy HH:mm:ss' }, type: 'date', textAlign: 'Right', allowFiltering: true, allowHtml: true, width: 150 },
                    { fieldName: 'congnghe', headerText: 'Công nghệ', allowFiltering: true, allowHtml: true, width: 150 }
                ],
                value: {},
                isEnabled: true,
                checked: []
            },

            dichvu: {
                list: [],
                value: {
                    DICHVUVT_ID: 0
                }
            },
            loaihinh: {
                list: [],
                value: {
                    LOAITB_ID: 0
                }
            },
            loaihinh_full: {
                list: [],
                value: {}
            },
            doituong: {
                list: [],
                value: {}
            },
            hinhthuctt: {
                list: [],
                value: {}
            }
        },
        totalRecords: 0,
        isClick: false,
        dateConfig: {
            altInput: true,
            dateFormat: 'd/m/Y',
            altFormat: 'd/m/Y'
        },
        ngaygiao_tk: new Date(),
        ngaygiao: new Date(),

        success_code: 'BSS-00000000',
        NHANTIN_NHANVIEN_PHOIHOP: -1,
        SEND_SMS_NHANTIN_NV: -1,
        NHANTIN_THEO_HG: -1,
        ts_kt_tthdkhdn: 0, //Kiểm tra cho phép thêm thông tin khách hàng DN  đối với PTTB KHDN mới xài
        kt_phieutra_gdv: false,
        _pSendSMS: false,
        _Giaoviec_td: false,
        _Giaoviec_nvkd: false,

        searchTerm: '',

        MaKH: '',
        MaKHCu: '',
        TenKH: '',
        PLKH: '',
        DiaChiKH: '',
        TienTT: '0',
        ConNo: '0',
        khachhang_id: '',
        thuebao_id: '',

        chkAll_KM: false,
        txtKhuyenMai: '',

        MaTB: '',
        SoAo: '',
        DiaChiTB: '',
        DiaChiLD: '',
        TrangThaiTB: '',
        KhuVuc: '',
        TienTT_Tbao: '0',
        ConNo_Tbao: '0',
        MaGD: '',

        MaTT: '',
        MaDD: '',
        TenTT: '',
        DiaChiTT: '',
        DiaChiBC: '',
        MaInGhep: '',
        TienTT_TT: '0',
        ConNo_TT: '0',

        dataDiachi: {},
        //Popup
        popupComponent: null,
        popupComponentData: null,
        popupComponentName: '',
        map: {
            lat: null,
            lng: null
        },
        map_info: {
            dia_chi: '',
            title: ''
        },
        popupDetailSubData: [],
        dataPopupNVDB: [],
        thongbaoLoi: ['Không tìm thấy thông tin khách hàng của mã KH : ', 'Không tìm thấy thông tin khách hàng của mã TT : ', 'Không tìm thấy thông tin khách hàng của mã TB : '],
        dl_db_cntt: ''

    }),
    computed: {
        output() {
            return marked(this.input)
        }
    },
    methods: {
        update: debounce(function (e) {
            this.input = e.target.value
        }, 100)
    }
}
</script> 