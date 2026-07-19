<template>
    <b-modal ref="popupTocDoMucCuocADSL" size="xl" hide-header hide-header-close hide-footer body-class="p-0"
        no-close-on-backdrop @show="handleShowModal">
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span> Chọn tốc độ mức cước theo đối tượng
                </div>
                <div class="close -ap icon-close" @click="hideModal"></div>
            </div>

            <ActionTop :actions="actions" @onActionClick="onActionClick" />

            <div class="popup-body">

                <div class="grid-stack-box">
                    <div class="box-col box-form" style="width: calc(50% - 8px);">
                        <div class="legend-title">Thông tin dịch vụ tỉnh Bán</div>
                        <div class="info-row">
                            <div class="key w120"></div>
                            <div class="value"
                                style="height: 32px !important; padding: 0.375rem 0.75rem; font-size: 1rem;">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w120"></div>
                            <div class="value"
                                style="height: 32px !important; padding: 0.375rem 0.75rem; font-size: 1rem;">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Tốc độ </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_tocdo_ban">
                                    <SelectExt v-model="thongso_bancheo.tocdo_id" :allowFiltering="true"
                                        :dataSource="ds_tocdo_ban" @change="changeTocDoBan"
                                        :disabled="!cboTocDoBan_Enabled" dataValueField="TOCDO_ID"
                                        dataTextField="THUONGHIEU" />
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Tốc độ thực </div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt v-model="thongso_bancheo.tocdothuc_id" :allowFiltering="true"
                                        :dataSource="ds_tocdothuc_ban" :disabled="!cboTocDoThucBan_Enabled"
                                        dataValueField="tocdo_id" dataTextField="thuonghieu" />
                                </div>
                            </div>
                        </div>
                        <div class="info-row" v-if="pnlMucCuocBan_Visible">
                            <div class="key w100">Mức cước </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_mucuoc_ban">
                                    <select2 :settings="{ dropdownParent: $refs['ds_mucuoc_ban'] }"
                                        v-model="thongso_bancheo.muccuoc_id" class="select2"
                                        :disabled="!cboMucCuocBan_Enabled" @change="changeMucCuocBan"
                                        :options="ds_muccuoc.map(e => ({ id: e.MUCCUOC_ID, text: e.TENMUC }))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row" v-if="pnlCongNgheBan_Visible">
                            <div class="key w100">Công nghệ</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select class="form-control" :disabled="!cboCongNgheBan_Enabled">
                                        <option></option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="info-row" v-if="pnlKieuTraBan_Visible">
                            <div class="key w100">Kiểu trả</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_kieutra_ban">
                                    <select2 :settings="{ dropdownParent: $refs['ds_kieutra_ban'] }"
                                        v-model="thongso_bancheo.kieutra_id" class="select2"
                                        :disabled="!cboKieuTraBan_Enabled" @change="changeKieuTraBan"
                                        :options="ds_kieutra.map(e => ({ id: e.KIEUTRA_ID, text: e.TENKIEU }))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Đối tượng</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_doituong_ban">
                                    <select2 :settings="{ dropdownParent: $refs['ds_doituong_ban'] }"
                                        ref="cboDoiTuongBan" v-model="thongso_bancheo.doituong_id" class="select2"
                                        :disabled="!cboDoiTuongBan_Enabled" @change="changeDoiTuongBan"
                                        :options="ds_doituong_ban.map(e => ({ id: e.DOITUONG_ID, text: e.TEN_DT }))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Trang bị</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_trangbi_ban">
                                    <select2 :settings="{ dropdownParent: $refs['ds_trangbi_ban'] }" ref="cboTrangBiBan"
                                        v-model="thongso_bancheo.trangbi_id" class="select2" @change="changeTrangBiBan"
                                        :options="ds_trangbi_ban.map(e => ({ id: e.TRANGBI_ID, text: e.TENTRANGBI }))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Thiết bị</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_thietbi_ban">
                                    <select2 :settings="{ dropdownParent: $refs['ds_thietbi_ban'] }"
                                        v-model="thongso_bancheo.thietbidc_id" class="select2"
                                        :disabled="!cboThietBiDCBan_Enabled"
                                        :options="ds_thietbi_ban.map(e => ({ id: e.thietbidc_id, text: e.ten_tbi }))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Mức cước TB</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_muccuoctb_ban">
                                    <select2 :settings="{ dropdownParent: $refs['ds_muccuoctb_ban'] }"
                                        ref="cboMucCuocTBBan" v-model="thongso_bancheo.muccuoctb_id" class="select2"
                                        :options="ds_muccuoctb_ban.map(e => ({ id: e.muccuoctb_id, text: e.tenmuccuoc }))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Chủ quản</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_chuquan_ban">
                                    <select2 :settings="{ dropdownParent: $refs['ds_chuquan_ban'] }"
                                        v-model="thongso_bancheo.chuquan_id" class="select2"
                                        :disabled="!chuquan_ban_enabled"
                                        :options="ds_chuquan_ban.map(e => ({ id: e.CHUQUAN_ID, text: e.TENCHUQUAN }))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Đơn vị TC</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_chuquanld_ban">
                                    <select2 :settings="{ dropdownParent: $refs['ds_chuquanld_ban'] }"
                                        v-model="thongso_bancheo.chuquanld_id" class="select2"
                                        :options="ds_chuquan_ban.map(e => ({ id: e.CHUQUAN_ID, text: e.TENCHUQUAN }))">
                                    </select2>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="gutter gutter-horizontal" style="width: 16px;"></div>
                    <div class="box-col box-form" style="width: calc(50% - 8px);">
                        <div class="legend-title">Thông tin dịch vụ tỉnh Thi công</div>
                        <div class="info-row">
                            <div class="key w100">Kiểu LD</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_kieuld">
                                    <select2 :settings="{ dropdownParent: $refs['ds_kieuld'] }" ref="cboKieuLD"
                                        v-model="thongso_thicong.kieuld_id" class="select2"
                                        :options="ds_kieuld.map(e => ({ id: e.kieuld_id, text: e.ten_kieuld }))"
                                        @change="changeKieuLD">
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Mã TN</div>
                            <div class="value">
                                <input type="text" class="form-control tright bold" :value="input_matn"
                                    @change="e => input_matn = e.target.value" readonly />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Tốc độ</div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt v-model="thongso_thicong.tocdo_id" :allowFiltering="true"
                                        :dataSource="ds_tocdo_tc" @change="changeTocDoTC"
                                        :disabled="!cboTocDoTC_Enabled" dataValueField="tocdo_id"
                                        dataTextField="thuonghieu" />
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Tốc độ thực</div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt v-model="thongso_thicong.tocdothuc_id" :allowFiltering="true"
                                        :dataSource="ds_tocdothuc_tc" :disabled="!cboTocDoThucTC_Enabled"
                                        dataValueField="tocdo_id" dataTextField="thuonghieu" />
                                </div>
                            </div>
                        </div>
                        <div class="info-row" v-if="pnlMucCuocTC_Visible">
                            <div class="key w100">Mức cước</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_mucuoc_tc">
                                    <select2 :settings="{ dropdownParent: $refs['ds_mucuoc_tc'] }"
                                        v-model="thongso_thicong.muccuoc_id" class="select2"
                                        :disabled="!cboMucCuocTC_Enabled"
                                        :options="ds_muccuoc.map(e => ({ id: e.MUCCUOC_ID, text: e.TENMUC }))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row" v-if="pnlCongNgheTC_Visible">
                            <div class="key w100">Công nghệ</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select class="form-control" :disabled="!cboCongNgheTC_Enabled">
                                        <option></option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="info-row" v-if="pnlKieuTraTC_Visible">
                            <div class="key w100">Kiểu trả</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_kieutra_tc">
                                    <select2 :settings="{ dropdownParent: $refs['ds_kieutra_tc'] }"
                                        v-model="thongso_thicong.kieutra_id" class="select2"
                                        :disabled="!cboKieuTraTC_Enabled"
                                        :options="ds_kieutra.map(e => ({ id: e.KIEUTRA_ID, text: e.TENKIEU }))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Đối tượng</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_doituong_tc">
                                    <select2 :settings="{ dropdownParent: $refs['ds_doituong_tc'] }" ref="cboDoiTuongTC"
                                        v-model="thongso_thicong.doituong_id" class="select2"
                                        :disabled="!cboDoiTuongTC_Enabled" @change="changeDoiTuongTC"
                                        :options="ds_doituong_tc.map(e => ({ id: e.doituong_id, text: e.ten_dt }))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Trang bị</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_trangbi_tc">
                                    <select2 :settings="{ dropdownParent: $refs['ds_trangbi_tc'] }" ref="cboTrangBiTC"
                                        v-model="thongso_thicong.trangbi_id" class="select2"
                                        :disabled="!cboTrangBiTC_Enabled" @change="changeTrangBiTC"
                                        :options="ds_trangbi_tc.map(e => ({ id: e.trangbi_id, text: e.tentrangbi }))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Thiết bị</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_thietbi_tc">
                                    <select2 :settings="{ dropdownParent: $refs['ds_thietbi_tc'] }"
                                        v-model="thongso_thicong.thietbidc_id" class="select2"
                                        :disabled="!cboThietBiDCTC_Enabled"
                                        :options="ds_thietbi_tc.map(e => ({ id: e.thietbidc_id, text: e.ten_tbi }))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Mức cước TB</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click="chonMucCuocTB">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <div class="select-custom" ref="ds_muccuoctb_tc">
                                        <select2 ref="cboMucCuocTBTC" v-model="thongso_thicong.muccuoctb_id"
                                            class="select2"
                                            :options="ds_muccuoctb_tc.map(e => ({ id: e.muccuoctb_id, text: e.tenmuccuoc }))">
                                        </select2>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Chủ quản</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_chuquan_tc">
                                    <select2 :settings="{ dropdownParent: $refs['ds_chuquan_tc'] }"
                                        v-model="thongso_thicong.chuquan_id" class="select2"
                                        :disabled="!chuquan_tc_enabled"
                                        :options="ds_chuquan_tc.map(e => ({ id: e.CHUQUAN_ID, text: e.TENCHUQUAN }))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Đơn vị TC</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_chuquanld_tc">
                                    <select2 :settings="{ dropdownParent: $refs['ds_chuquanld_tc'] }"
                                        v-model="thongso_thicong.chuquanld_id" class="select2"
                                        :options="ds_chuquan_tc.map(e => ({ id: e.CHUQUAN_ID, text: e.TENCHUQUAN }))">
                                    </select2>
                                </div>
                            </div>
                        </div>


                    </div>
                </div>
            </div>
            <!-- Modal -->
            <DSTBCungDoiCapBCModal ref="dSTBCungDoiCapBCModal" :khachhang_id="Number(khachhang_id)"
                :tinh_tc="Number(tinh_tc)" :kieuld_id="kieuld_selected" @accept="acceptDSTBCungDoiCapBC" />
            <DSTBLapKemBCModal ref="dSTBLapKemBCModal" :hdkh_id="Number(hdkh_id)" :tinh_tc="Number(tinh_tc)"
                :kieuld_id="kieuld_selected" @accept="acceptDSTBLapKemBC" />
            <MucCuocTBTCModal ref="mucCuocTBTCModal" :tinh_tc="Number(tinh_tc)"
                :tocdo_tc="Number(thongso_thicong.tocdo_id)" @accept="acceptMucCuocTBTC" />
        </div>
    </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import { KieuLapDat, LoaiHinhTB, KieuTra, MUCCUOC, DichVuVienThong } from '../ThamSo'
import { LoaiHopDong } from '../../../../../utils/Enum'
import moment from 'moment'
import DSTBCungDoiCapBCModal from './DSTBCungDoiCapBCModal.vue'
import DSTBLapKemBCModal from './DSTBLapKemBCModal.vue'
import MucCuocTBTCModal from './MucCuocTBTCModal.vue'
//WinUI.WinUIBanCheo.frmTocDoMucCuoc
//link: https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1906551485&range=C6340
export default {
    name: 'TocDoMucCuocADSLModal',
    components: {
        ActionTop,
        DSTBCungDoiCapBCModal,
        DSTBLapKemBCModal,
        MucCuocTBTCModal
    },
    props: {
        //tham số
        khachhang_id: {
            type: Number,
            default: 0
        },
        hdkh_id: {
            type: Number,
            default: 0
        },
        thongso_ban: {
            type: Object,
            default: () => { }
        },
        thongso_tc: {
            type: Object,
            default: () => { }
        }

    },
    data() {
        return {
            input_matn: '',
            actions: [
                {
                    id: 'capnhat',
                    name: 'Cập nhật',
                    active: true,
                    icon_class: 'one-save'
                },
            ],
            tinh_tc: 0,
            ploaitb_ld: 0,
            ploaitb_td: 0,
            thuebao_id_ban: 0,
            thuebao_id_thicong: 0,
            loaihd_id: 0,
            thongso_thicong: {},
            thongso_bancheo: {},
            ds_kieuld: [],
            tinhchuquan_id: -1,
            ds_tocdo_ban: [],
            ds_tocdo_tc: [],
            ds_kieutra: [],
            ds_muccuoc: [],
            ds_chuquan_ban: [],
            ds_chuquan_tc: [],
            ds_doituong_ban: [],
            diachi_ban: null,
            diachi_thicong: null,
            ds_trangbi_ban: [],
            ds_trangbi_tc: [],
            ds_doituong_tc: [],
            ds_thietbi_ban: [],
            ds_thietbi_tc: [],
            cboTocDoThucBan_Enabled: true,
            cboTocDoThucTC_Enabled: true,
            cboKieuTraTC_Enabled: true,
            cboMucCuocTC_Enabled: true,
            cboKieuTraBan_Enabled: true,
            pnlCongNgheBan_Visible: true,
            pnlCongNgheTC_Visible: true,
            cboTrangBiTC_Enabled: true,
            pnlMucCuocBan_Visible: true,
            pnlMucCuocTC_Visible: true,
            pnlKieuTraBan_Visible: true,
            pnlKieuTraTC_Visible: true,
            cboCongNgheBan_Enabled: true,
            cboCongNgheTC_Enabled: true,

            cboTocDoBan_Enabled: true,
            cboTocDoTC_Enabled: true,
            cboMucCuocBan_Enabled: true,
            cboThietBiDCTC_Enabled: true,
            cboThietBiDCBan_Enabled: true,
            cboDoiTuongBan_Enabled: true,
            cboDoiTuongTC_Enabled: true,
            cboMucCuocTBTC_Enabled: true,
            cboMucCuocTBBan_Enabled: true,
            ds_muccuoctb_ban: [],
            ds_muccuoctb_tc: [],
            ds_tocdothuc_ban: [],
            ds_tocdothuc_tc: [],
            chkTocDoTC_Checked: true,

            chuquan_ban_enabled: true,
            chuquan_tc_enabled: true,
            kieuld_selected: null,
        }
    },
    methods: {
        showModal(tinh_tc, ploaitb_ld, ploaitb_td, thuebao_id_ban, thuebao_id_thicong, loaihd_id) {
            this.tinh_tc = tinh_tc
            this.ploaitb_ld = ploaitb_ld
            this.ploaitb_td = ploaitb_td
            this.thuebao_id_ban = thuebao_id_ban
            this.thuebao_id_thicong = thuebao_id_thicong
            this.loaihd_id = loaihd_id
            this.$refs["popupTocDoMucCuocADSL"].show()
        },
        hideModal() {
            this.$refs["popupTocDoMucCuocADSL"].hide()
        },
        onActionClick(action) {
            if (action.id == 'capnhat') {
                this.CapNhat()
            }
        },
        async CapNhat() {
            if (!this.thongso_bancheo.trangbi_id || !this.thongso_thicong.trangbi_id || this.ds_trangbi_ban.length == 0 || this.ds_trangbi_tc.length == 0) {
                this.$toast.error('Bạn phải chọn trang bị cho 2 đơn vị')
                return
            }
            if (!this.thongso_bancheo.thietbidc_id || !this.thongso_thicong.thietbidc_id || this.ds_thietbi_ban.length == 0 || this.ds_thietbi_tc.length == 0) {
                this.$toast.error('Bạn phải chọn thiết bị cho 2 đơn vị')
                return
            }
            // if(!this.thongso_bancheo.muccuoc_id||!this.thongso_thicong.muccuoc_id||this.ds_muccuoc.length==0){
            //     this.$toast.error('Bạn phải chọn mức cước thuê bao cho 2 đơn vị')
            //     return
            // }
            if (!this.thongso_bancheo.doituong_id || !this.thongso_thicong.doituong_id || this.ds_doituong_ban.length == 0 || this.ds_doituong_tc.length == 0) {
                this.$toast.error('Bạn phải chọn đối tượng cho 2 đơn vị')
                return
            }
            if (!this.thongso_bancheo.tocdo_id || !this.thongso_thicong.tocdo_id || this.ds_tocdo_ban.length == 0 || this.ds_tocdo_tc.length == 0) {
                this.$toast.error('Bạn phải chọn tốc độ cho 2 đơn vị')
                return
            }
            if (!this.thongso_bancheo.muccuoctb_id || !this.thongso_thicong.muccuoctb_id || this.ds_muccuoctb_ban.length == 0 || this.ds_muccuoctb_tc.length == 0) {
                this.$toast.error('Bạn phải chọn mức cước thuê bao cho 2 đơn vị')
                return
            }

            if (this.input_matn.trim() == '') {
                let kieu = Number(await this.fn_map_kieu_ld(this.tinh_tc, this.thongso_thicong.kieuld_id, 1, '0'))
                if (kieu == 2) {
                    this.$toast.error('Chưa chọn mã truy nhập lắp trên đường có sẵn !')
                } else if (kieu == 1) {
                    this.$toast.error('Chưa chọn mã truy nhập lắp kèm !')
                    return
                }
            }
            this.$emit('accept', {
                thongso_ban: this.thongso_bancheo,
                thongso_tc: this.thongso_thicong
            })
            this.hideModal()

        },
        handleShowModal() {
            this.thongso_thicong = {}
            this.thongso_bancheo = {}
            this.ds_kieuld = []
            this.tinhchuquan_id = -1
            this.ds_kieutra = []
            this.ds_tocdo_ban = []
            this.ds_tocdo_tc = []
            this.ds_muccuoc = []
            this.ds_chuquan_ban = []
            this.ds_chuquan_tc = []
            this.ds_doituong_ban = []
            this.diachi_ban = null
            this.diachi_thicong = null
            this.ds_trangbi_ban = []
            this.ds_trangbi_tc = []
            this.ds_doituong_tc = []
            this.ds_thietbi_ban = []
            this.ds_thietbi_tc = []
            this.ds_muccuoctb_ban = []
            this.ds_muccuoctb_tc = []
            this.cboTocDoThucBan_Enabled = true
            this.cboTocDoThucTC_Enabled = true

            this.cboKieuTraTC_Enabled = true
            this.cboMucCuocTC_Enabled = true
            this.cboKieuTraBan_Enabled = true
            this.pnlCongNgheBan_Visible = true
            this.pnlCongNgheTC_Visible = true
            this.cboTrangBiTC_Enabled = true

            this.pnlMucCuocBan_Visible = true
            this.pnlMucCuocTC_Visible = true
            this.pnlKieuTraBan_Visible = true
            this.pnlKieuTraTC_Visible = true
            this.cboCongNgheBan_Enabled = true
            this.cboCongNgheTC_Enabled = true

            this.cboTocDoBan_Enabled = true
            this.cboTocDoTC_Enabled = true
            this.cboMucCuocBan_Enabled = true
            this.cboThietBiDCTC_Enabled = true
            this.cboThietBiDCBan_Enabled = true
            this.cboDoiTuongBan_Enabled = true
            this.cboDoiTuongTC_Enabled = true
            this.cboMucCuocTBTC_Enabled = true
            this.cboMucCuocTBBan_Enabled = true
            this.chkTocDoTC_Checked = true



            setTimeout(() => {
                this.initDuLieu()
            }, 500)
        },
        async initDuLieu() {
            Object.assign(this.thongso_thicong, this.thongso_tc)
            Object.assign(this.thongso_bancheo, this.thongso_ban)
            console.log('loaihd_id', this.loaihd_id)
            console.log('thongso_thicong', this.thongso_thicong)
            console.log('thongso_ban', this.thongso_ban)
            this.input_matn = ''

            //   string dsLoaiHinhLocTocDo = ",11,";
            //     if (dsLoaiHinhLocTocDo.IndexOf("," + this._ploaitb_td.ToString() + ",") > 1)
            //         this.chkTocDoTC.Visible = true;
            //     ;


            //Kiểu LD
            var triggerKieuLD = false
            //FN_LAY_DULIEU_DANHMUC
            let result_dl_danhmuc = await this.fn_lay_dulieu_danhmuc(JSON.stringify({
                LOAIHD_ID: this.loaihd_id,
                LOAITB_ID: this.ploaitb_td,
                TINHTHICONG_ID: this.tinh_tc
            }))
            if(result_dl_danhmuc && result_dl_danhmuc.ERROR_CODE == 1) {
                
                // this.ds_kieuld = await this.ht_kieu_ld_combobox({
                //     tinhthicong_id: this.tinh_tc,
                //     loaitb_id: this.ploaitb_ld,
                //     loaihd_id: this.loaihd_id
                // }) 
                this.ds_kieuld = result_dl_danhmuc.RESULT.CBO_KIEULD
                if (this.thongso_thicong.kieuld_id == 0) {
                    this.thongso_thicong.kieuld_id = this.ds_kieuld.length > 0 ? this.ds_kieuld[0].kieuld_id : 0
                    triggerKieuLD = true
                    this.kieuld_selected = this.thongso_thicong.kieuld_id
                }
    
                this.cboTocDoThucBan_Enabled = false
                this.cboTocDoThucTC_Enabled = false
    
                this.cboKieuTraTC_Enabled = false
                this.cboMucCuocTC_Enabled = false
                this.cboKieuTraBan_Enabled = true
                this.pnlCongNgheBan_Visible = false
                this.pnlCongNgheTC_Visible = false
    
                this.chuquan_ban_enabled = false
                this.chuquan_tc_enabled = false
    
                //Bổ sung theo yêu cầu AnhNT
                // if(this.loaihd_id==1){
                //     this.chuquan_ban_enabled=true
                //     this.chuquan_tc_enabled=true
                // }else{
                //     this.chuquan_ban_enabled=false
                //     this.chuquan_tc_enabled=false
                // }
    
                //formload_frmtocdomuccuoc
                let result = await this.formload_frmtocdomuccuoc(JSON.stringify({
                    LOAIHD_ID: this.loaihd_id,
                    LOAITB_ID: this.ploaitb_td,
                    TTND_NV_ID: this.$root.token.getNhanVienID(),
                    TTND_DONVI_ID: this.$root.token.getDonViID(),
                    TTND_NGUOIDUNG_ID: this.$root.token.getNguoiDungID()
                }))
                if (result && result.ERROR_CODE == 1) {
                    
                    //Tốc độ bán
                    this.ds_tocdo_ban = result.RESULT.CBO_TOCDO_BAN
                    console.log('ds_tocdo_ban', this.ds_tocdo_ban.length)
                    if (this.thongso_bancheo.tocdo_id == 0) {
                        this.thongso_bancheo.tocdo_id = this.ds_tocdo_ban.length > 0 ? this.ds_tocdo_ban[0].TOCDO_ID : 0
                    }
    
                    if (this.loaihd_id == LoaiHopDong.DAT_MOI) {
                        // this.ds_tocdo_tc = await this.load_dm_tocdo_adsl(this.ploaitb_td, this.tinh_tc)
                        this.ds_tocdo_tc = result_dl_danhmuc.RESULT.CBO_TOCDO_ADSL
                    } else {
                        // this.ds_tocdo_tc = await this.load_dm_tocdo_adsl_dc(this.ploaitb_td, this.tinh_tc)
                        this.ds_tocdo_tc = result_dl_danhmuc.RESULT.CBO_TOCDO_ADSL_TC
                    }
                    console.info(this.ds_tocdo_tc);
                    //kiểu trả
                    this.ds_kieutra = result.RESULT.DS_KIEUTRA
                    if (this.thongso_bancheo.kieutra_id == 0) {
                        this.thongso_bancheo.kieutra_id = this.ds_kieutra.length > 0 ? this.ds_kieutra[0].KIEUTRA_ID : 0
                    }
                    if (this.thongso_thicong.kieutra_id == 0) {
                        this.thongso_thicong.kieutra_id = this.ds_kieutra.length > 0 ? this.ds_kieutra[0].KIEUTRA_ID : 0
                    }
                    //Mức cước
                    this.ds_muccuoc = result.RESULT.CBO_MUCCUOC_BAN
                    if (this.thongso_bancheo.muccuoc_id == 0) {
                        this.thongso_bancheo.muccuoc_id = this.ds_muccuoc.length > 0 ? this.ds_muccuoc[0].MUCCUOC_ID : 0
                    }
                    if (this.thongso_thicong.muccuoc_id == 0) {
                        this.thongso_thicong.muccuoc_id = this.ds_muccuoc.length > 0 ? this.ds_muccuoc[0].MUCCUOC_ID : 0
                    }
                    //
                    // this.ds_thietbi_ban = await this.ht_loai_tb_lhtb_combobox({
                    //     tinhthicong_id: this.tinh_tc,
                    //     loaitb_id: this.ploaitb_td,
                    //     kieucb: 1
                    // })

                    this.ds_thietbi_ban = result_dl_danhmuc.RESULT.CBO_THIETBI_BAN
                    if (this.thongso_bancheo.thietbidc_id == 0) {
                        this.thongso_bancheo.thietbidc_id = this.ds_thietbi_ban.length > 0 ? this.ds_thietbi_ban[0].thietbidc_id : 0
                    }
                    //ht_loai_tb_lhtb_combobox
                    //Chủ quản bán
                    this.tinhchuquan_id = Number(result.RESULT.TINHCHU_QUAN_ID)
                    let ds_cq_ban = result.RESULT.DS_CQ_BAN
                    this.ds_chuquan_ban = ds_cq_ban
                    var detBan = []
                    if (this.tinhchuquan_id == -1) {
                        detBan = ds_cq_ban.filter(x => x.TINH_ID && x.TINH_ID == this.$root.token.getPhanVungID() && x.HIEULUC == 1)
                    } else {
                        detBan = ds_cq_ban.filter(x => x.TINH_ID && x.TINH_ID == this.tinhchuquan_id && x.HIEULUC == 1)
                    }
                    if (detBan.length > 0) {
                        this.thongso_bancheo.chuquan_id = detBan[0].CHUQUAN_ID
                    }
                    console.log('detBan', detBan)
                    detBan = ds_cq_ban.filter(x => x.TINH_ID && x.TINH_ID == this.tinh_tc && x.HIEULUC == 1)
    
                    if (detBan.length > 0) {
                        this.thongso_bancheo.chuquanld_id = detBan[0].CHUQUAN_ID
                    }
    
                    //Chủ quản TC
                    let detTC = []
                    let ds_cq_tc = result.RESULT.DS_CQ_TC
                    this.ds_chuquan_tc = ds_cq_tc
                    if (this.tinhchuquan_id == -1) {
                        detTC = ds_cq_tc.filter(x => x.TINH_ID && x.TINH_ID == this.$root.token.getPhanVungID() && x.HIEULUC == 1)
                    } else {
                        detTC = ds_cq_tc.filter(x => x.TINH_ID && x.TINH_ID == this.tinhchuquan_id && x.HIEULUC == 1)
                    }
                    if (detTC.length > 0) {
                        this.thongso_thicong.chuquan_id = detTC[0].CHUQUAN_ID
                    }
                    detTC = ds_cq_tc.filter(x => x.TINH_ID && x.TINH_ID == this.tinh_tc && x.HIEULUC == 1)
                    if (detTC.length > 0) {
                        this.thongso_thicong.chuquanld_id = detTC[0].CHUQUAN_ID
                    }
    
                    this.ds_doituong_ban = result.RESULT.CBO_DOITUONG_BAN
                    if (this.thongso_bancheo.doituong_id == 0) {
                        this.thongso_bancheo.doituong_id = this.ds_doituong_ban.length > 0 ? this.ds_doituong_ban[0].DOITUONG_ID : 0
                    }
    
                    // this.ds_doituong_tc = await this.sp_lay_ds_doituong_theo_thicong(this.tinh_tc)

                    this.ds_doituong_tc = result_dl_danhmuc.RESULT.CBO_DOITUONG_TC
                    if (this.ds_doituong_tc.length > 0) {
                        if (this.thongso_thicong.doituong_id == 0) {
                            this.thongso_thicong.doituong_id = this.ds_doituong_tc[0].doituong_id
                        }
                    }
    
                    //không có
                    // if (thongso_ban.congnghe_id > 0)
                    //     cboCongNgheBan.SelectedValue = thongso_ban.congnghe_id;
    
                    // if (thongso_tc.congnghe_id > 0)
                    //     cboCongNgheTC.SelectedValue = thongso_tc.congnghe_id;
    
                    this.diachi_ban = this.resetDiaChi()
                    this.ds_trangbi_ban = result.RESULT.CBO_TRANGBI_BAN
                    if(this.ds_trangbi_ban.length > 0){
                        if (this.thongso_bancheo.trangbi_id == 0) {
                            this.thongso_bancheo.trangbi_id = this.ds_trangbi_ban[0].TRANGBI_ID
                        }
                    }
    
                    // this.ds_trangbi_tc = await this.sp_lay_ds_trangbi_tc(this.tinh_tc)
                    this.ds_trangbi_tc = result_dl_danhmuc.RESULT.CBO_TRANGBI
                    if (this.ds_trangbi_tc.length > 0) {
                        if (this.thongso_thicong.trangbi_id == 0) {
                            this.thongso_thicong.trangbi_id = this.ds_trangbi_tc[0].trangbi_id
                        }
                    }
    
                    // this.ds_thietbi_tc = await this.ht_loai_tb_lhtb_combobox({
                    //     tinhthicong_id: this.tinh_tc,
                    //     loaitb_id: this.ploaitb_td,
                    //     kieucb: 2
                    // })

                    this.ds_thietbi_tc = result_dl_danhmuc.RESULT.CBO_THIETBI_TC
                    if (this.thongso_thicong.thietbidc_id == 0) {
                        this.thongso_thicong.thietbidc_id = this.ds_thietbi_tc.length > 0 ? this.ds_thietbi_tc[0].thietbidc_id : 0
                    }
    
                    this.cboTrangBiTC_Enabled = true
    
                    console.log('thuebao_id_thicong', this.thuebao_id_thicong)
                    if (this.thuebao_id_thicong != 0) {
                        let object = await this.lay_diachi_dbtb_bancheo(this.thuebao_id_thicong, this.tinh_tc, 2)
                        if (object) {
                            this.diachi_thicong = this.resetDiaChi()
                            this.diachi_thicong.diachi_id = object.diachi_id ? object.diachi_id : 0
                            this.diachi_thicong.diachi = object.diachi ? object.diachi : ''
                            this.diachi_thicong.quan_id = object.quan_id ? object.quan_id : 0
                            this.diachi_thicong.phuong_id = object.phuong_id ? object.phuong_id : 0
                            this.diachi_thicong.pho_id = object.pho_id ? object.pho_id : 0
                            this.diachi_thicong.ap_id = object.ap_id ? object.ap_id : 0
                            this.diachi_thicong.khu_id = object.khu_id ? object.khu_id : 0
                            this.diachi_thicong.dacdiem_id = object.dacdiem_id ? object.dacdiem_id : 0
                        } else {
                            this.diachi_thicong = this.resetDiaChi()
                        }
                    } else {
                        this.diachi_thicong = this.resetDiaChi()
                    }
    
                    if (this.thongso_ban.kieuld_id == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || this.thongso_ban.kieuld_id == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON) {
                        this.pnlCongNgheBan_Visible = true
                        this.pnlCongNgheTC_Visible = true
                        this.pnlMucCuocBan_Visible = false
                        this.pnlMucCuocTC_Visible = false
                        this.pnlKieuTraBan_Visible = false
                        this.pnlKieuTraTC_Visible = false
    
                        this.cboKieuTraBan_Enabled = false
                        this.cboCongNgheBan_Enabled = true
                        this.cboCongNgheTC_Enabled = true
    
                        this.cboTocDoBan_Enabled = false
                        this.cboTocDoTC_Enabled = false
                        this.cboMucCuocBan_Enabled = false
                    } else {
                        if (this.ploaitb_td == LoaiHinhTB.INTERNET_MYTV) {
                            this.pnlMucCuocBan_Visible = false
                            this.pnlMucCuocTC_Visible = false
                            this.pnlCongNgheBan_Visible = false
                            this.pnlCongNgheTC_Visible = false
                            this.pnlKieuTraBan_Visible = true
                            this.pnlKieuTraTC_Visible = true
                        } else {
                            this.pnlCongNgheBan_Visible = false
                            this.pnlCongNgheTC_Visible = false
                            this.pnlMucCuocBan_Visible = true
                            this.pnlMucCuocTC_Visible = true
                            this.pnlKieuTraBan_Visible = false
                            this.pnlKieuTraTC_Visible = false
                        }
                    }
                    if (this.thongso_tc.kieuld_id == KieuLapDat.THAYDOI_MUCCUOC_ADSL) {
                        this.cboTocDoBan_Enabled = false
                        this.cboTocDoTC_Enabled = false
                        // cboTocDoBan.EditValue = thongso_ban.tocdo_id;
                        // cboTocDoTC.SelectedValue = thongso_tc.tocdo_id;
                    }
                    if (this.thongso_tc.kieuld_id == KieuLapDat.THAYDOI_KIEUTRA_MYTV) {
                        this.cboTocDoBan_Enabled = false
                        this.cboTocDoTC_Enabled = false
                        this.cboCongNgheBan_Enabled = false
                        this.cboCongNgheTC_Enabled = false
                        if (this.thongso_ban.kieutra_id == KieuTra.TRASAU) {
                            this.thongso_bancheo.kieutra_id = KieuTra.TRATRUOC
                        } else {
                            this.thongso_bancheo.kieutra_id = KieuTra.TRASAU
                        }
                    }
                    if (this.thongso_ban.kieutra_id == 0) {
                        this.thongso_bancheo.kieutra_id = 2
                    } else {
                        this.thongso_bancheo.kieutra_id = this.thongso_ban.kieutra_id
                    }
    
    
                    if (this.thongso_ban.kieuld_id == KieuLapDat.THAYDOI_TOCDO_ADSL || this.thongso_ban.kieuld_id == KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL ||
                        this.thongso_ban.kieuld_id == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_AON_GPON || this.thongso_ban.kieuld_id == KieuLapDat.THAYDOI_CONGNGHE_TOCDO_GPON_AON) {
                        this.cboTocDoBan_Enabled = true
                        this.cboTocDoTC_Enabled = true
                        this.cboMucCuocBan_Enabled = false
                        this.cboKieuTraBan_Enabled = false
                    }
                    if (this.thongso_ban.kieuld_id == KieuLapDat.THAYDOI_MUCCUOC_ADSL || this.thongso_ban.kieuld_id == KieuLapDat.THAYDOI_TOCDO_MUCCUOC_ADSL) {
    
                        if (this.thongso_ban.muccuoc_id == MUCCUOC.LUU_LUONG) {
                            this.thongso_bancheo.muccuoc_id = MUCCUOC.TRON_GOI
                        }
                        this.thongso_thicong.muccuoc_id = MUCCUOC.TRON_GOI
                        if (this.thongso_ban.muccuoc_id == MUCCUOC.TRON_GOI) {
                            this.thongso_bancheo.muccuoc_id = MUCCUOC.LUU_LUONG
                        }
                        this.thongso_thicong.muccuoc_id = MUCCUOC.LUU_LUONG
                    }
    
                    if (this.thongso_ban.kieuld_id == KieuLapDat.THAYDOI_TOCDOTHUC_ADSL) {
                        this.cboTocDoBan_Enabled = false
                        this.cboTocDoTC_Enabled = false
                        this.cboMucCuocBan_Enabled = false
    
                        this.cboMucCuocTBBan_Enabled = false
                        this.cboMucCuocTBTC_Enabled = false
                        this.cboCongNgheBan_Enabled = false
                        this.cboCongNgheTC_Enabled = false
                        this.cboTocDoThucBan_Enabled = true
                        this.cboTocDoThucTC_Enabled = true
                        this.thongso_bancheo.tocdothuc_id = 0
                        this.thongso_thicong.tocdothuc_id = 0
                        this.cboKieuTraBan_Enabled = false
                    }
                    if (this.thongso_ban.kieuld_id == KieuLapDat.DOIGOI_FSECURE) {
                        this.cboMucCuocBan_Enabled = false
                        this.cboCongNgheBan_Enabled = false
                        this.cboCongNgheTC_Enabled = false
                        this.cboKieuTraBan_Enabled = false
                        this.thongso_bancheo.muccuoc_id = this.thongso_ban.muccuoc_id
                    }
                    if (this.thongso_ban.tocdo_id != 0) {
                        if (this.ploaitb_td == LoaiHinhTB.INTERNET_MYTV && this.thongso_ban.kieuld_id != KieuLapDat.THAYDOI_KIEUTRA_MYTV) {
                            // /ds_tocdo_tc
                            this.thongso_bancheo.tocdo_id = this.ds_tocdo_ban.length > 0 ? this.ds_tocdo_ban[0].TOCDO_ID : 0
                            this.thongso_thicong.tocdo_id = this.ds_tocdo_tc.length > 0 ? this.ds_tocdo_tc[0].tocdo_id : 0
                        } else {
                            this.thongso_bancheo.tocdo_id = this.thongso_ban.tocdo_id
                            this.thongso_thicong.tocdo_id = this.thongso_tc.tocdo_id
                        }
                    }
                    if(this.thongso_ban.trangbi_id != 0){
                        await this.Lay_DS_MucCuoc_TB_Ban(0, 0, 0, 0, this.ploaitb_td, this.thongso_ban.doituong_id, moment(new Date()).format('DD/MM/YYYY'), this.thongso_ban.trangbi_id)
                    }
 
                    if(this.thongso_tc.trangbi_id != 0){
                        await this.Lay_DS_MucCuoc_TB_TC(0, 0, 0, 0, this.ploaitb_td, this.thongso_tc.doituong_id, moment(new Date()).format('DD/MM/YYYY'), this.thongso_tc.trangbi_id)
                    } 
    
                    if (this.thongso_ban.muccuoctb_id > 0) {
                        this.thongso_bancheo.muccuoctb_id = this.thongso_ban.muccuoctb_id
                    }
                    if (this.thongso_tc.muccuoctb_id > 0) {
                        this.thongso_thicong.muccuoctb_id = this.thongso_tc.muccuoctb_id
                    }
                    // #endregion
                    let dichvuvt_id = Number(await this.fn_map_loaihinh_tb(this.ploaitb_td, 2, '-1'))
                    if (this.loaihd_id == LoaiHopDong.KHOIPHUC_SD) {
                        await this.thongso_khoiphuc(dichvuvt_id)
                    }
    
                    if (this.loaihd_id == LoaiHopDong.CHUYEN_QUYEN || this.loaihd_id == LoaiHopDong.DI_CHUYEN) {
                        this.cboTocDoBan_Enabled = false
                        this.cboTocDoThucBan_Enabled = false
                        this.cboThietBiDCBan_Enabled = false
                        this.cboCongNgheBan_Enabled = false
                        this.cboDoiTuongBan_Enabled = false
                        this.cboKieuTraBan_Enabled = false
                        this.cboMucCuocBan_Enabled = false
                        this.cboMucCuocTBBan_Enabled = false
    
                        this.cboTocDoTC_Enabled = false
                        this.cboTocDoThucTC_Enabled = false
                        this.cboCongNgheTC_Enabled = false
                        this.cboDoiTuongTC_Enabled = false
                        this.cboKieuTraTC_Enabled = false
                        this.cboMucCuocTC_Enabled = false
                        this.cboMucCuocTBTC_Enabled = false
                        this.cboThietBiDCTC_Enabled = true
                    }
                    //
                    await this.changeTocDoBan()
                    if(this.thongso_ban.tocdo_id == 0){
                        await this.changeTocDoTC() 
                    }
    
                    // 
                    if (triggerKieuLD) {
                        this.changeKieuLD()
                    }
                    this.input_matn = this.thongso_thicong.ma_tn ? this.thongso_thicong.ma_tn.toString() : ''
                } else if (result) {
                    this.$toast.error(result.MESSAGE)
                }
            }
            else if(result_dl_danhmuc){
                this.$toast.error(result_dl_danhmuc.MESSAGE)
            }
        },
        async Lay_DS_MucCuoc_TB_Ban(vkhuld_id, vapld_id, vphold_id, vphuongld_id, vloaitb_id, vdoituong_id, ngay, vtrangbi_id) {
            //ploaitb_td
            // Lấy danh sách mức cước mới.
            var vtocdo_id = 0
            var vmuccuoc_id = 0 //2 Lưu lượng
            if (this.ploaitb_td == LoaiHinhTB.INTERNET_FTTH ||
                this.ploaitb_td == LoaiHinhTB.WIFI_FIBER ||
                this.ploaitb_td == LoaiHinhTB.INTERNET_ADSL ||
                this.ploaitb_td == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC ||
                this.ploaitb_td == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC ||
                this.ploaitb_td == LoaiHinhTB.WIFI_MESH) {

                vtocdo_id = this.thongso_bancheo.tocdo_id ? this.thongso_bancheo.tocdo_id : 0
                vmuccuoc_id = this.thongso_bancheo.muccuoc_id ? this.thongso_bancheo.muccuoc_id : 0
            } else if (this.ploaitb_td == LoaiHinhTB.INTERNET_MYTV || this.ploaitb_td == LoaiHinhTB.FSECURE) {
                vtocdo_id = this.thongso_bancheo.tocdo_id ? this.thongso_bancheo.tocdo_id : 0
                vmuccuoc_id = 0
            }
            var ds = []
            this.ds_muccuoctb_ban = []
 
            if (this.loaihd_id == LoaiHopDong.DAT_MOI || this.loaihd_id == LoaiHopDong.THAY_DOI_TOCDO_ADSL || this.loaihd_id == LoaiHopDong.CHUYENDOI_LH) {
                ds = await this.lay_ds_mucuoc_tb_v2_ban({
                    khuld_id: 0,
                    apld_id: 0,
                    phold_id: 0,
                    phuongld_id: 0,
                    loaitb_id: this.ploaitb_td,
                    doituong_id: vdoituong_id,
                    tocdo_id: vtocdo_id,
                    muccuoc_id: vmuccuoc_id,
                    ngay: ngay,
                    trangbi_id: vtrangbi_id
                })
            } else {
                //     string str = @"Select mucuoctb_id, a.muccuoc||' - '||Decode(a.muccuoc_id,1, a.cuoc_tg, a.cuoc_tb)||' VND' tenmuccuoc 
                //                      from {?DB2}.muccuoc_tb a ";
                //     if (vmuccuoc_id != 0)
                //         str = str + "where tocdo_id  =  " + cboTocDoBan.EditValue.ToString();

                //     ds = new BanCheoBase().GET_DATA_SQL_DS(str);
            }
            if (ds.length > 0) {
                this.ds_muccuoctb_ban = ds
            } else {
                this.ds_muccuoctb_ban = []
                this.thongso_bancheo.muccuoctb_id = 0
            }
        },
        async Lay_DS_MucCuoc_TB_TC(vkhuld_id, vapld_id, vphold_id, vphuongld_id, vloaitb_id, vdoituong_id, ngay, vtrangbi_id) {
            var vtocdo_id = 0
            var vmuccuoc_id = 0 //2 Lưu lượng
            if (this.ploaitb_td == LoaiHinhTB.INTERNET_FTTH ||
                this.ploaitb_td == LoaiHinhTB.WIFI_FIBER ||
                this.ploaitb_td == LoaiHinhTB.INTERNET_ADSL ||
                this.ploaitb_td == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC ||
                this.ploaitb_td == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC ||
                this.ploaitb_td == LoaiHinhTB.WIFI_MESH) {

                vtocdo_id = this.thongso_thicong.tocdo_id ? this.thongso_thicong.tocdo_id : 0
                vmuccuoc_id = this.thongso_thicong.muccuoc_id ? this.thongso_thicong.muccuoc_id : 0
            } else if (this.ploaitb_td == LoaiHinhTB.INTERNET_MYTV || this.ploaitb_td == LoaiHinhTB.FSECURE) {
                vtocdo_id = this.thongso_thicong.tocdo_id ? this.thongso_thicong.tocdo_id : 0
                vmuccuoc_id = 0
            }
            var ds = []
            this.ds_muccuoctb_tc = []
            if (this.loaihd_id == LoaiHopDong.DAT_MOI || this.loaihd_id == LoaiHopDong.THAY_DOI_TOCDO_ADSL || this.loaihd_id == LoaiHopDong.CHUYENDOI_LH) {
                ds = await this.lay_ds_mucuoc_tb_v2_tc({
                    tinhthicong_id: this.tinh_tc,
                    khuld_id: 0,
                    apld_id: 0,
                    phold_id: 0,
                    phuongld_id: 0,
                    loaitb_id: vloaitb_id,
                    doituong_id: vdoituong_id,
                    tocdo_id: vtocdo_id,
                    muccuoc_id: vmuccuoc_id,
                    ngay: ngay,
                    trangbi_id: vtrangbi_id
                })
            } else {
                // string str = @"Select mucuoctb_id, a.muccuoc||' - '||Decode(a.muccuoc_id,1, a.cuoc_tg, a.cuoc_tb)||' VND' tenmuccuoc 
                //                  from {?DB2}.muccuoc_tb a ";
                // if (vmuccuoc_id != 0)
                //     str = str + " where tocdo_id =  " + cboTocDoTC.SelectedValue;


                // ds = new BanCheoBase().GET_DATA_SQL_DS(tinhtc, str);
            }
            if (ds.length > 0) {
                this.ds_muccuoctb_tc = ds
            } else {
                this.ds_muccuoctb_tc = []
                this.thongso_thicong.muccuoctb_id = 0
            }

        },
        async thongso_khoiphuc(dichvu_vt) {
            if (dichvu_vt == DichVuVienThong.CO_DINH) {
                this.cboTocDoBan_Enabled = false
                this.cboTocDoThucBan_Enabled = false
                this.pnlCongNgheBan_Visible = false
                this.pnlKieuTraBan_Visible = false
                this.pnlMucCuocBan_Visible = true
                this.cboMucCuocBan_Enabled = false

                this.cboTocDoTC_Enabled = false
                this.cboTocDoThucTC_Enabled = false
                this.pnlCongNgheTC_Visible = false
                this.pnlKieuTraTC_Visible = false
                this.pnlMucCuocTC_Visible = true
                this.cboMucCuocTC_Enabled = false
            } else if (dichvu_vt == DichVuVienThong.ADSL) {
                this.cboTocDoBan_Enabled = true
                this.cboTocDoThucBan_Enabled = false
                this.pnlCongNgheBan_Visible = false
                this.pnlKieuTraBan_Visible = false
                this.pnlMucCuocBan_Visible = true
                this.cboMucCuocBan_Enabled = true

                this.cboTocDoTC_Enabled = true
                this.cboTocDoThucTC_Enabled = false
                this.pnlCongNgheTC_Visible = false
                this.pnlKieuTraTC_Visible = false
                this.pnlMucCuocTC_Visible = true
                this.cboMucCuocTC_Enabled = true
            }

        },
        changeKieuLD() {
            console.log('changeKieuLD thongso_thicong', this.thongso_thicong)
            console.log('changeKieuLD thongso_thicong.kieuld_id', this.thongso_thicong.kieuld_id)
            this.kieuld_selected = this.thongso_thicong.kieuld_id
            this.layMaTN()
        },
        async changeTocDoBan() {
            this.ds_tocdo_tc = []
            this.thongso_thicong.tocdo_id = 0
            let tinh_id = this.$root.token.getPhanVungID()
            this.ds_tocdothuc_ban = await this.ht_tocdo_thuc_adsl_theo_tocdo_id(this.thongso_bancheo.tocdo_id, tinh_id)
            this.thongso_bancheo.tocdothuc_id = this.thongso_bancheo.tocdo_id
            await this.Lay_DS_MucCuoc_TB_Ban(0, 0, 0, 0, this.ploaitb_td, this.thongso_bancheo.doituong_id, moment(new Date()).format('DD/MM/YYYY'), 3)
            //ds_tocdo_ban
            const tocdo = this.ds_tocdo_ban.find(x => x.TOCDO_ID == this.thongso_bancheo.tocdo_id)
            if (!tocdo) {
                return
            }
            let maTocDo = tocdo.MATOCDO
            console.log('maTocDo', maTocDo)
            if (this.chkTocDoTC_Checked) {
                var result = []
                if (this.loaihd_id == LoaiHopDong.CHUYEN_QUYEN || this.loaihd_id == LoaiHopDong.DI_CHUYEN || this.loaihd_id == LoaiHopDong.KHOIPHUC_SD) {
                    result = await this.laydmtocdotinhtctheomatocdo_dc({
                        tinhthicong_id: this.tinh_tc,
                        loaitb_id: this.ploaitb_td,
                        maTocDo: maTocDo
                    })
                } else {
                    result = await this.laydmtocdotinhtctheomatocdo({
                        tinhthicong_id: this.tinh_tc,
                        loaitb_id: this.ploaitb_td,
                        maTocDo: maTocDo
                    })
                }
                console.log('result', result)
                this.ds_tocdo_tc = result
                //
                if (this.ds_tocdo_tc.length == 0) {
                    this.$toast.error('Đơn vị thi công không có tốc độ này. Vui lòng chọn tốc độ khác.')
                }
                if (this.thongso_tc.tocdo_id == 0) {
                    this.thongso_thicong.tocdo_id = result.length > 0 ? this.ds_tocdo_tc[0].tocdo_id : 0
                } else {
                    this.thongso_thicong.tocdo_id = this.thongso_tc.tocdo_id
                }

                await this.changeTocDoTC()
            }

        },
        async changeTocDoTC() {
            this.ds_tocdothuc_tc = await this.ht_tocdo_thuc_adsl_theo_tocdo_id(this.thongso_thicong.tocdo_id, this.tinh_tc)
            this.thongso_thicong.tocdothuc_id = this.thongso_thicong.tocdo_id
            // await this.Lay_DS_MucCuoc_TB_TC(0, 0, 0, 0, this.ploaitb_td, this.thongso_thicong.doituong_id, moment(new Date()).format('DD/MM/YYYY'), 3)
            await this.change_LayDSMucCuoc_TB_TC() 
        },
        // Mức cước
        changeMucCuocBan() {
            this.thongso_thicong.muccuoc_id = this.thongso_bancheo.muccuoc_id
        },
        //Kiểu trả
        changeKieuTraBan() {
            this.thongso_thicong.kieutra_id = this.thongso_bancheo.kieutra_id
        },
        //Đối tượng
        async changeDoiTuongBan() {
            await this.Lay_DS_MucCuoc_TB_Ban(0, 0, 0, 0, this.ploaitb_td, this.thongso_bancheo.doituong_id, moment(new Date()).format('DD/MM/YYYY'), this.thongso_bancheo.trangbi_id)
        },
        async changeDoiTuongTC() {
            // await this.Lay_DS_MucCuoc_TB_TC(0, 0, 0, 0, this.ploaitb_td, this.thongso_thicong.doituong_id, moment(new Date()).format('DD/MM/YYYY'), this.thongso_thicong.trangbi_id)
            await this.change_LayDSMucCuoc_TB_TC()
        },
        async changeTrangBiBan() {
            await this.Lay_DS_MucCuoc_TB_Ban(0, 0, 0, 0, this.ploaitb_td, this.thongso_bancheo.doituong_id, moment(new Date()).format('DD/MM/YYYY'), this.thongso_bancheo.trangbi_id)
            this.thongso_thicong.trangbi_id = this.thongso_bancheo.trangbi_id
            await this.changeTrangBiTC()

        },
        async changeTrangBiTC() {
            // await this.Lay_DS_MucCuoc_TB_TC(0, 0, 0, 0, this.ploaitb_td, this.thongso_thicong.doituong_id, moment(new Date()).format('DD/MM/YYYY'), this.thongso_thicong.trangbi_id)
            await this.change_LayDSMucCuoc_TB_TC()
        },

        async change_LayDSMucCuoc_TB_TC(){
            setTimeout(() => {
                this.Lay_DS_MucCuoc_TB_TC(0, 0, 0, 0, this.ploaitb_td, this.thongso_thicong.doituong_id, moment(new Date()).format('DD/MM/YYYY'), this.thongso_thicong.trangbi_id)
            },300)
        },
        async layMaTN() {
            let kieu = Number(await this.fn_map_kieu_ld(this.tinh_tc, this.thongso_thicong.kieuld_id, 1, '0'))
            if (kieu == 2) {
                ////Lắp mới trên đường có sẵn
                if (this.khachhang_id > 0) {
                    //WinUI.WinUIBanCheo.frmDSTBCungDoiCapBC
                    this.$refs.dSTBCungDoiCapBCModal.showModal()
                } else {
                    this.$toast.error('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !')
                    return
                }
            } else if (kieu == 1) {
                //Lắp kèm
                //WinUI.WinUIBanCheo.frmDSTBLapKemBC
                if (this.hdkh_id > 0) {
                    this.$refs.dSTBLapKemBCModal.showModal()
                } else {
                    this.$toast.error('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp kèm !')
                }
            }
        },
        chonMucCuocTB() {
            this.$refs.mucCuocTBTCModal.showModal()
        },
        acceptDSTBCungDoiCapBC(data) {
            console.log('acceptDSTBCungDoiCapBC', data)
            this.thongso_thicong.ma_tn = data.ma_tn
            this.thongso_thicong.ma_doicap = data.ma_doicap ? data.ma_doicap : data.madoicap ? data.madoicap : ''
            this.input_matn = data.ma_tn ? data.ma_tn.toString() : ''
        },
        acceptDSTBLapKemBC(data) {
            console.log('acceptDSTBLapKemBC', data)
            this.thongso_thicong.ma_tn = data.ma_tn
            this.thongso_thicong.ma_doicap = data.ma_doicap ? data.ma_doicap : data.madoicap ? data.madoicap : ''
            this.input_matn = data.ma_tn ? data.ma_tn.toString() : ''
        },
        async acceptMucCuocTBTC(data) {
            console.log('acceptMucCuocTBTC', data)
            this.thongso_thicong.trangbi_id = data.trangbi_id
            this.thongso_thicong.doituong_id = data.doituong_id
            await this.Lay_DS_MucCuoc_TB_TC(0, 0, 0, 0, this.ploaitb_td, this.thongso_thicong.doituong_id, moment(new Date()).format('DD/MM/YYYY'), this.thongso_thicong.trangbi_id)
        },
        resetDiaChi() {
            return {
                diachi_id: 0,
                diachi: '',
                quan_id: 0,
                phuong_id: 0,
                pho_id: 0,
                ap_id: 0,
                khu_id: 0,
                dacdiem_id: 0
            }
        },
        async fn_map_loaihinh_tb(loaitb_id, type, defaultValue) {
            try {
                this.loading(true)
                let response = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_loaihinh_tb', {
                    param: loaitb_id,
                    type: type
                })
                this.loading(false)
                if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                } else {
                    return defaultValue
                }
            } catch (e) {
                this.loading(true)
                return defaultValue
            }
        },
        async ht_kieu_ld_combobox(data) {
            try {
                this.loading(true)
                let response = await this.axios.post('/web-bancheo/danhmuc/ht_kieu_ld_combobox', data)
                this.loading(false)
                if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                } else {
                    return []
                }
            } catch (e) {
                this.loading(false)
                return []
            }
        },
        async fn_map_kieu_ld(tinh_tc, kieuld_id, type, defaultValue) {
            try {
                this.loading(true)
                let response = await this.axios.post('/web-bancheo/tracuu/fn_tt_kieu_ld', {
                    type: type,
                    param: kieuld_id,
                    tinhthicong_id: tinh_tc
                })
                this.loading(false)
                if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                } else {
                    return defaultValue
                }
            } catch (e) {
                this.loading(false)
                return defaultValue
            }
        },
        async formload_frmtocdomuccuoc(ds_para) {
            try {
                this.loading(true)
                let response = await this.axios.post('/web-hopdong/TocDoMucCuoc/formload_frmtocdomuccuoc', {
                    ds_para: ds_para
                })
                this.loading(false)
                if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                } else {
                    if (response && response.data && response.data.message) {
                        this.$toast.error(response.data.message)
                    } else {
                        this.$toast.error('Đã xảy ra lỗi khi khởi tạo dữ liệu form')
                    }
                    return null
                }
            } catch (e) {
                this.loading(false)
                if (e.data && e.data.message) {
                    this.$toast.error(e.data.message)
                } else if (e.response && e.response.data && e.response.data.message) {
                    this.$toast.error(e.response.data.message)
                } else {
                    this.$toast.error('Đã xảy ra lỗi khi khởi tạo dữ liệu form')
                }
                return null
            }
        },
        async fn_lay_dulieu_danhmuc(ds_para){
            try {
                this.loading(true)
                let response = await this.axios.post('/web-thicong/capnhatthongtin/fn_lay_dulieu_danhmuc', {
                    p_ds_para: ds_para
                })
                this.loading(false)
                if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                } else {
                    if (response && response.data && response.data.message) {
                        this.$toast.error(response.data.message)
                    } else {
                        this.$toast.error('Đã xảy ra lỗi khi khởi tạo dữ liệu form')
                    }
                    return null
                }
            } catch (e) {
                this.loading(false)
                if (e.data && e.data.message) {
                    this.$toast.error(e.data.message)
                } else if (e.response && e.response.data && e.response.data.message) {
                    this.$toast.error(e.response.data.message)
                } else {
                    this.$toast.error('Đã xảy ra lỗi khi khởi tạo dữ liệu form')
                }
                return null
            }
        },
        async sp_lay_ds_trangbi_tc(tinh_tc) {
            try {
                this.loading(true)
                let response = await this.axios.post('/web-bancheo/danhmuc/load_dm_trang_bi', {
                    tinhthicong_id: tinh_tc
                })
                this.loading(false)
                if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.sort((a, b) => a.trangbi_id - b.trangbi_id)
                } else {
                    return []
                }
            } catch (e) {
                this.loading(false)
                return []
            }
        },
        async sp_lay_ds_doituong_theo_thicong(tinh_tc) {
            try {
                this.loading(true)
                let response = await this.axios.post('/web-bancheo/danhmuc/load_dm_doi_tuong', {
                    tinhthicong_id: tinh_tc
                })
                this.loading(false)
                if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                } else {
                    return []
                }
            } catch (e) {
                this.loading(false)
                return []
            }
        },
        async ht_loai_tb_lhtb_combobox(data) {
            try {
                this.loading(true)
                let response = await this.axios.post('/web-bancheo/danhmuc/ht_loai_tb_lhtb_combobox', data)
                this.loading(false)
                if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                } else {
                    return []
                }
            } catch (e) {
                this.loading(false)
                return []
            }
        },
        //https://docs.google.com/spreadsheets/d/1-L0ojT6OkWz4CTJR5sUeHlbzChRzO5i_tE9G_RrZ9Vc/edit#gid=1906551485&range=E6343
        async lay_diachi_dbtb_bancheo(thuebao_id, tinhthicong_id, kieu_tinh) {
            try {
                this.loading(true)
                let response = await this.axios.post('/web-bancheo/danhmuc/lay_diachi_dbtb_bancheo', {
                    tinhthicong_id: tinhthicong_id,
                    kieu_tinh: kieu_tinh,
                    thuebao_id: thuebao_id
                })
                this.loading(false)
                if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                } else {
                    return null
                }
            } catch (e) {
                this.loading(false)
                return null
            }
        },
        async load_dm_tocdo_adsl(loaitb_id, tinh_tc) {
            try {
                this.loading(true)
                let response = await this.axios.post('/web-bancheo/danhmuc/load_dm_tocdo_adsl', {
                    tinhthicong_id: tinh_tc,
                    loaitb_id: loaitb_id
                })
                this.loading(false)
                if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                } else {
                    return []
                }
            } catch (e) {
                this.loading(false)
                return []
            }
        },
        async load_dm_tocdo_adsl_dc(loaitb_id, tinh_tc) {
            try {
                this.loading(true)
                let response = await this.axios.post('/web-bancheo/danhmuc/load_dm_tocdo_adsl_dc', {
                    tinhthicong_id: tinh_tc,
                    loaitb_id: loaitb_id
                })
                this.loading(false)
                if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                } else {
                    return []
                }
            } catch (e) {
                this.loading(false)
                return []
            }
        },
        async lay_ds_mucuoc_tb_v2_ban(data) {
            try {
                this.loading(true)
                let response = await this.axios.post('/web-hopdong/TocDoMucCuoc/lay_ds_mucuoc_tb_v2', data)
                this.loading(false)
                if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                } else {
                    return []
                }
            } catch (e) {
                this.loading(false)
                return []
            }
        },
        async lay_ds_mucuoc_tb_v2_tc(data) {
            try {
                this.loading(true)
                let response = await this.axios.post('/web-bancheo/tracuu/lay_ds_mucuoc_tb_v2', data)
                this.loading(false)
                if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                } else {
                    return []
                }
            } catch (e) {
                this.loading(false)
                return []
            }
        },
        async ht_tocdo_thuc_adsl_theo_tocdo_id(tocdo_id, tinh_tc) {
            try {
                this.loading(true)
                let response = await this.axios.post('/web-bancheo/danhmuc/ht_tocdo_thuc_adsl_theo_tocdo_id', {
                    tinhthicong_id: tinh_tc,
                    tocdo_id: tocdo_id
                })
                this.loading(false)
                if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                } else {
                    return []
                }
            } catch (e) {
                this.loading(false)
                return []
            }
        },
        async laydmtocdotinhtctheomatocdo(data) {
            try {
                this.loading(true)
                let response = await this.axios.post('/web-bancheo/danhmuc/laydmtocdotinhtctheomatocdo', data)
                this.loading(false)
                if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                } else {
                    return []
                }
            } catch (e) {
                this.loading(false)
                return []
            }
        },
        async laydmtocdotinhtctheomatocdo_dc(data) {
            try {
                this.loading(true)
                let response = await this.axios.post('/web-bancheo/danhmuc/laydmtocdotinhtctheomatocdo_dc', data)
                this.loading(false)
                if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                } else {
                    return []
                }
            } catch (e) {
                this.loading(false)
                return []
            }
        },
    }
}
</script>
<style>
.muccuoctb-body .page-content {
    position: unset !important;
}

.select2-selection {
    overflow: hidden !important;
}

.select2-container .select2-selection--single .select2-selection__rendered {
    white-space: normal !important;
    word-break: break-all !important;
}
</style>