<template>
    <b-modal
        id="popup-frmKhaoSatKH"
        size="xl"
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0"
        
        @show="onShow"
    >
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span> KHẢO SÁT KHÁCH HÀNG
                    BÁO HỎNG
                </div>
                <div class="close -ap icon-close" data-dismiss="modal"
                    @click="this.$bvModal.show('popup-frmKhaoSatKH')"></div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="javascript: void(0)" @click="onGhiLaiClicked">
                            <span class="icon one-save"></span> Ghi lại
                        </a>
                    </li>
                    <li>
                        <a href="javascript: void(0)" :class="{ disabled: !pBtnDieuLaiEnabled }">
                            <span
                                class="icon arrows-1_curved-next nc-icon-glyph"
                            ></span>
                            Điều lại
                        </a>
                    </li>
                    <li>
                        <a href="javascript: void(0)">
                            <span
                                class="icon files_single-paragraph nc-icon-glyph"
                            ></span>
                            Lịch sử KS
                        </a>
                    </li>
                    <li>
                        <a href="javascript: void(0)" v-b-modal.popup-frmLichSuBaoHong>
                            <span
                                class="icon files_single-copy-04 nc-icon-glyph"
                            ></span>
                            Lịch sử BH
                        </a>
                        <frmLichSuBaoHong :thueBaoId="pThueBaoId" />
                    </li>
                    <li>
                        <a href="javascript: void(0)" v-b-modal.popup-frmLichSuHenBaoHong>
                            <span
                                class="icon business_notes nc-icon-glyph"
                            ></span>
                            Lịch sử hẹn
                        </a>
                        <frmLichSuHenBaoHong :baoHongId="pBaoHongId" :kieu="2" />
                    </li>
                    <li>
                        <a href="javascript: void(0)">
                            <span
                                class="icon files_single-content-02 nc-icon-glyph"
                            ></span>
                            Trợ giúp
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="box-form">
                            <div class="legend-title mart0">
                                Thông tin khách hàng / Thuê bao
                                <span class="pull-right red" v-show="pIsShowCKCL">Thuê bao có CKCL, y/c chọn Câu hỏi 5</span>
                            </div>
                            <div class="info-row">
                                <div class="key w110">Số máy/Acc</div>
                                <div class="value">
                                    <input
                                        type="text"
                                        class="form-control highlight"
                                        v-model="pTxtMaTB"
                                    />
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w110">Loại hình/tốc độ</div>
                                <div class="value">
                                    <input
                                        type="text"
                                        class="form-control"
                                        v-model="pTxtLoaiHinh"
                                    />
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w110">TB cùng đôi cáp</div>
                                <div class="value">
                                    <input
                                        type="text"
                                        class="form-control"
                                        v-model="pTxtTBCungCap"
                                    />
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w110">Tên thuê bao</div>
                                <div class="value">
                                    <input
                                        type="text"
                                        class="form-control"
                                        v-model="pTxtTenTB"
                                    />
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w110">Địa chỉ lắp đặt</div>
                                <div class="value">
                                    <input
                                        type="text"
                                        class="form-control"
                                        v-model="pTxtDiaChiLD"
                                    />
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w110">Điện thoại liên hệ</div>
                                <div class="value">
                                    <input
                                        type="text"
                                        class="form-control"
                                        v-model="pTxtDienThoaiLH"
                                    />
                                </div>
                            </div>
                        </div>
                        <div class="box-form" style="height: 300px">
                            <div class="legend-title mart0">
                                Thông tin cập nhật
                            </div>
                            <div class="info-row">
                                <div class="key w110">Tên người trả lời</div>
                                <div class="value">
                                    <input
                                        type="text"
                                        class="form-control"
                                        ref="txtTenNguoiTL"
                                        v-model="pTxtTenNguoiTL"
                                    />
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w110">Số liên hệ</div>
                                <div class="value">
                                    <input
                                        type="text"
                                        class="form-control"
                                        v-model="pTxtSoLH"
                                        ref='txtSoLH'
                                    />
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w110">Tb cùng đôi cáp</div>
                                <div class="value">
                                    <textarea
                                        class="form-control"
                                        style="height: 80px;resize: vertical;"
                                        :value="pTxtGhiChu"
                                    ></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="box-form">
                            <div class="legend-title mart0">
                                <div class="title pull-left mart7">
                                    Thông báo hỏng
                                </div>
                                <div class="pull-right">
                                    <div class="desc fw6 padt7 inline vtop">
                                        Lần hỏng trong 30 ngày:
                                        <span class="red">{{pSoLanHongTrong30Ngay}}</span>
                                    </div>
                                    <div class="inline marl10 vtop">
                                        <div class="info-row normal">
                                            <div class="key w90">
                                                Thời gian VP
                                            </div>
                                            <div class="value w90">
                                                <input
                                                    type="text"
                                                    class="form-control red"
                                                    v-model="pTxtVPXL"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w100">
                                            Ngày báo hỏng
                                        </div>
                                        <div class="value">
                                            <div class="input-icon-right">
                                                <input
                                                    type="text"
                                                    class="form-control"
                                                    v-model="pTxtNgayBH"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w80">Ngày HT</div>
                                        <div class="value">
                                            <div class="input-icon-right">
                                                <input
                                                    type="text"
                                                    class="form-control"
                                                    v-model="pTxtNgayNT"
                                                />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w100">Người báo</div>
                                        <div class="value">
                                            <input
                                                type="text"
                                                class="form-control"
                                                v-model="pTxtNguoiBH"
                                            />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w80">Số ĐT</div>
                                        <div class="value">
                                            <input
                                                type="text"
                                                class="form-control"
                                                v-model="pTxtSoDTBH"
                                            />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w100">Tình trạng</div>
                                <div class="value">
                                    <input
                                        type="text"
                                        class="form-control"
                                        v-model="pTxtTinhTrang"
                                    />
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w100">Nguyên nhân</div>
                                <div class="value">
                                    <input
                                        type="text"
                                        class="form-control"
                                        v-model="pTxtNguyenNhan"
                                    />
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w100">Ghi chú NT</div>
                                <div class="value">
                                    <textarea
                                        name=""
                                        id=""
                                        cols="30"
                                        rows="10"
                                        class="form-control"
                                        style="height: 48px;resize: vertical;"
                                        :value="pTxtGhiChuNT"
                                    ></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="box-form">
                            <div class="legend-title mart0">
                                <div class="title pull-left">
                                    Danh sách lần hỏi
                                </div>
                                <div class="pull-right">
                                    <div class="info-row">
                                        <div class="key">
                                            <div
                                                class="check-action red normal"
                                            >
                                                <input
                                                    type="checkbox"
                                                    class="check"
                                                    v-model="pChkKTL"
                                                />
                                                <span class="name"
                                                    >Không trả lời</span
                                                >
                                            </div>
                                        </div>
                                        <div class="value">
                                            <div class="select-custom">
                                                <b-form-select
                                                    v-model="pCboLyDoKTL"
                                                    :options="pDsLyDoKTL"
                                                    value-field="LYDOKTL_ID"
                                                    text-field="LYDO_KTL"
                                                    class="form-control"
                                                >
                                                </b-form-select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w80">
                                            <div class="check-action">
                                                <input
                                                    type="checkbox"
                                                    class="check"
                                                    v-model="pchkHenKS_Tu"
                                                />
                                                <span class="name">Hẹn từ</span>
                                            </div>
                                        </div>
                                        <div class="value">
                                            <ejs-datepicker
                                                v-model="pDtpHenKS_Tu"
                                                :value="today"
                                                :format="'dd/MM/y'"
                                                :showClearButton="false"
                                                :enabled="pchkHenKS_Tu"
                                            >
                                            </ejs-datepicker>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w80">
                                            <div class="check-action">
                                                <input
                                                    type="checkbox"
                                                    class="check"
                                                    v-model="pchkHenKS_Den"
                                                />
                                                <span class="name">Đến</span>
                                            </div>
                                        </div>
                                        <div class="value">
                                            <ejs-datepicker
                                                v-model="pDtpHenKS_Den"
                                                :value="today"
                                                :format="'dd/MM/y'"
                                                :showClearButton="false"
                                                :enabled="pchkHenKS_Den"
                                            >
                                            </ejs-datepicker>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div
                                class="table-content mart10"
                                style="height: 140px;"
                            >
                                <table-c
                                    :columns="[
                                        {
                                            fieldName: 'NGUOI_HOI',
                                            headerText: 'Người hỏi',
                                            allowFiltering: true,
                                            allowSorting: false
                                        },
                                        {
                                            fieldName: 'NGUOI_TL',
                                            headerText: 'Người trả lời',
                                            allowFiltering: true,
                                            allowSorting: false
                                        },
                                        {
                                            fieldName: 'DIENTHOAI_LH',
                                            headerText: 'Điện thoại',
                                            allowFiltering: true,
                                            allowSorting: false
                                        },
                                        {
                                            fieldName: 'NGAY_HOI',
                                            headerText: 'Ngày hỏi',
                                            allowFiltering: true,
                                            allowSorting: false
                                        },
                                        {
                                            fieldName: 'TRANGTHAI',
                                            headerText: 'Trạng thái',
                                            allowFiltering: true,
                                            allowSorting: false
                                        },
                                        {
                                            fieldName: 'LYDO_KTL',
                                            headerText: 'Lý do KTL',
                                            allowFiltering: true,
                                            allowSorting: false
                                        },
                                        {
                                            fieldName: 'GHICHU',
                                            headerText: 'Ghi chú',
                                            allowFiltering: true,
                                            allowSorting: false
                                        }
                                    ]"
                                    :dataSource="pDsLanGoiChuaTraLoi"
                                    ref="gridLanGoiChuaTraLoi"
                                    @selectedRowChanged="
                                        gridLanGoiChuaTraLoi_selectedRowChanged
                                    "
                                >
                                </table-c>
                            </div>
                            <div class="tc-pagination">
                                <a
                                    href="#"
                                    class="btn  one-arrow-left-forward"
                                ></a>
                                <a
                                    href="#"
                                    class="btn fa fa-angle-double-left"
                                ></a>
                                <a href="#" class="btn fa fa-angle-left"></a>
                                <span class="text">Bản ghi</span>
                                <div class="dropdown inline">
                                    <div
                                        class="fw6 input-number"
                                        data-toggle="dropdown"
                                    >
                                        <span class="text-main">10</span>/1033
                                    </div>
                                    <div class="dropdown-menu">
                                        <a href="#" class="dropdown-item">10</a>
                                        <a href="#" class="dropdown-item">20</a>
                                    </div>
                                </div>
                                <a href="#" class="btn fa fa-angle-right"></a>
                                <a
                                    href="#"
                                    class="btn fa fa-angle-double-right"
                                ></a>
                                <a
                                    href="#"
                                    class="btn one-arrow-right-forward"
                                ></a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="box-form">
                    <div class="legend-title">
                        Danh sách câu hỏi
                        <span class="pull-right red" v-if="pIsShowThongBaoCoHen"
                            >Khách hàng có hẹn, ấn nút Lịch sử hẹn để xem chi
                            tiết</span
                        >
                    </div>
                    <div class="box-quest marb20" v-for="(cauHoi, index) in pKhaoSat.dsCauHoiChinh" :key="index" >
                        <div class="title-bg-main">
                            <span class="title underline" >Câu hỏi {{ index + 1 }}</span >
                        </div>
                        <div class="desc marb10">
                            {{ cauHoi.TENCAUHOI }}
                        </div>
                        <div class="list-aw">
                            <div class="item padt5 padb5" v-for="(cauHoiPhu, indexPhu) in cauHoi.cauHoiPhu" :key="cauHoiPhu.RCH_ID1" >
                                <div class="check-action">
                                    <input
                                        :type=" cauHoi.LOAICH_ID == 1 ? 'radio' : 'checkbox' "
                                        class="check"
                                        :name="cauHoi.CAUHOI_ID"
                                        :checked="cauHoiPhu.Checked"
                                        @change=" onTraLoiChecked(index, indexPhu, cauHoi.LOAICH_ID == 1) "
                                    />
                                    <span class="name">{{ cauHoiPhu.NOIDUNG_TL }}</span>
                                </div>
                                <input
                                    type="text"
                                    class="form-control"
                                    v-if="cauHoiPhu.Checked && cauHoiPhu.LOAI == 1 "
                                    v-model="cauHoiPhu.Text"
                                />
                            </div>
                        </div>
                        <template v-for="(cauHoiPhu, indexPhu) in cauHoi.cauHoiPhu">
                            <div
                                class="box-quest box-form marb20 marl20"
                                style="background:#FFF9EB" v-show="cauHoiPhu.Checked && cauHoiPhu.dsCauHoiChau && cauHoiPhu.dsCauHoiChau.length > 0"
                                :key="cauHoiPhu.RCH_ID1"
                            >
                                <div class="title-bg-main">
                                    <span class="title underline" >Câu hỏi phụ</span >
                                </div>
                                <div class="desc marb10">
                                    <p>{{cauHoiPhu.NOIDUNG_TL}}</p>
                                </div>
                                <div class="list-aw">
                                    <div class="item padt5 padb5"  v-for="(cauHoiChau, indexChau) in cauHoiPhu.dsCauHoiChau" :key="cauHoiChau.TRALOI_ID">
                                        <div class="check-action">
                                            <input type="radio" class="check" :name="cauHoiPhu.RCH_ID1"
                                                :checked="cauHoiChau.Checked"
                                                @change=" onTraLoiCauHoiPhuChecked(index, indexPhu, indexChau) "
                                                />
                                            <span class="name" >{{cauHoiChau.NOIDUNG_TL}}</span >
                                        </div>
                                        <input
                                            type="text"
                                            class="form-control"
                                            v-if="cauHoiChau.Checked && cauHoiChau.LOAI == 1 "
                                            v-model="cauHoiChau.Text"
                                        />
                                    </div>
                                </div>
                            </div>
                        </template>
                        
                    </div>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import API from "../../api/TraCuuThueBaoKhaoSatApi";
import XacNhanNVKTAPI from "../../api/XacNhanPhanHoiNVKTApi";
import frmLichSuBaoHong from "./frmLichSuBaoHong.vue"
import frmLichSuHenBaoHong from "./frmLichSuHenBaoHong.vue"
import moment from "moment";
const LOAI_LAN_GOI = {
    HOP_DONG: 1,
    BAO_HONG: 2
};
export default {
    name: "frmKhaoSatKH",
    components: {
        frmLichSuBaoHong,
        frmLichSuHenBaoHong
    },
    props: {
        baoHongId: {
            type: Number,
            default: 0
        },
        tbtlId: {
            type: Number,
            default: 0
        },
        thueBaoId: {
            type: Number,
            default: 0
        },
        hdkhId: {
            type: Number,
            default: 0
        },
        giaoPhieuKsId: {
            type: Number,
            default: 0
        }
    },
    data() {
        return {
            pCboLyDoKTL: "",
            pThamSoMacDinh: {
                BATBUOC_TRALOI_ALL_CAUHOI: false,
                KIEMTRA_CAUHOI_KHL: false,
                FIMS_DIEU_XN_DONVI: false,
                OUTBOUND_KHONG_DIEULAI: false,
                OB_KHONGTL_KTC1: false,
                KHONGTL_CHUYEN_OB_TUDONG: false,
                OB_CHON_NHIEU_LYDO_KTL: false,
                CHOPHEP_SUA_OB_KHONG_TL: false,
                LAN_KS_TOIDA: false,
                KT_DAMBAO_CKCL_KH: false
            },
            pTxtMaTB: "",
            pTxtLoaiHinh: "",
            pTxtTBCungCap: "",
            pTxtTenTB: "",
            pTxtDiaChiLD: "",
            pTxtDienThoaiLH: "",
            pTxtTenNguoiTL: "",
            pTxtSoLH: "",
            pTxtGhiChu: "",

            pTxtVPXL: "",
            pTxtNgayBH: "",
            pTxtNgayNT: "",
            pTxtNguoiBH: "",
            pTxtSoDTBH: "",
            pTxtTinhTrang: "",
            pTxtNguyenNhan: "",
            pTxtGhiChuNT: "",

            pChkKTL: false,
            pchkHenKS_Tu: false,
            pchkHenKS_Den: false,
            today: new Date(),
            pDtpHenKS_Tu: new Date(),
            pDtpHenKS_Den: new Date(),

            pDsThongTinBaoHong: [],
            pIsSuaPhieu: false,
            pIsShowThongBaoCoHen: true,
            pBaoHongId: 0,
            pThueBaoId: 0,

            pNgayKS: new Date(),
            pIsNew: false,
            pIsShowCKCL: false,
            pbaohong_ckcl: false,
            pSoLanHongTrong30Ngay: 0,

            pDsLanGoiChuaTraLoi: [],
            pDsCauHoi: [],

            pBtnDieuLaiEnabled: false,
            pKhaoSat: {
                dsCauHoiChinh: [],
                dsCauHoiPhu: [],
                dsCauTraLoi: []
            },
            pChiTietTraLoi: [],
            pDsLyDoKTL: [],
            fakeData: {
                cauHoi: [],
                traLoi: []
            },

            vDaCapNhap: false,
            vMoPhieu: 0,
        };
    },
    computed: {
        getKetQua() {
            let dtKQ = []
            this.pKhaoSat.dsCauHoiChinh.forEach((ch, indexChinh) => {
                ch.cauHoiPhu.forEach((chp, indexPhu) => {
                    if (chp.Checked) {
                        dtKQ.push({
                            CAUHOI_ID: chp.CAUHOI_ID,
                            TRALOI_ID: chp.TRALOI_ID,
                            NOIDUNG: chp.Text,
                            CAUHOI: ch.TENCAUHOI,
                            TRALOI: chp.NOIDUNG_TL,
                            DIEULAI: chp.DIEULAI,
                            RCH_ID: chp.RCH_ID1,
                            DIEULAI_XN: chp.DIEULAI_XN
                        })
                    }
                    
                    if (chp.dsCauHoiChau && chp.dsCauHoiChau.length > 0) {
                        //có câu hỏi cháu
                        chp.dsCauHoiChau.forEach((chc, indexChau) => {
                            if (chc.Checked) {
                                dtKQ.push({
                                    CAUHOI_ID: chc.CAUHOI_ID,
                                    TRALOI_ID: chc.TRALOI_ID,
                                    NOIDUNG: chc.Text,
                                    CAUHOI: ch.TENCAUHOI,
                                    TRALOI: chc.NOIDUNG_TL,
                                    DIEULAI: chc.DIEULAI,
                                    RCH_ID: chc.RCH_ID,
                                    DIEULAI_XN: chc.DIEULAI_XN
                                })
                            }
                        })
                    }
                })
            })
            return dtKQ
        },
    },
    methods: {
        ...mapActions("cskhCommon", [
            "getThamSoMacDinh",
            "getListThamSoMacDinh"
        ]),
        async onShow() {
            try {
                this.$root.showLoading(true);
                this.clearData();
                this.pDsLyDoKTL = await this.getDsLyDoKTL();

                if (this.pDsLyDoKTL.length > 0) {
                    this.pCboLyDoKTL = this.pDsLyDoKTL[0].LYDOKTL_ID;
                }
                this.$nextTick(() => this.$refs.txtTenNguoiTL.select());

                await this.getThamSoMacDinh();
                await this.hienThiThongTinTB();
                await this.hienThiCH();
                // this.processFakeData();

                this.pBtnDieuLaiEnabled = this.pIsNew;
                if (this.pThamSoMacDinh.KT_DAMBAO_CKCL_KH) {
                    let ktCKCL = await this.getKiemTraPhieuBaoHongCKCL()
                    this.pbaohong_ckcl = ktCKCL == 1
                    this.pIsShowCKCL = ktCKCL == 1
                }
            } catch (e) {
                console.log(e);
            } finally {
                this.$root.showLoading(false);
            }
        },
        async getThamSoMacDinh() {
            try {
                let dsThamSoMacDinh = await this.getListThamSoMacDinh({
                    dsMaTs: Object.getOwnPropertyNames(
                        this.pThamSoMacDinh
                    ).join()
                });
                if (dsThamSoMacDinh && dsThamSoMacDinh.length > 0) {
                    let BATBUOC_TRALOI_ALL_CAUHOI = dsThamSoMacDinh.find(
                        x => x.MA_TS === "BATBUOC_TRALOI_ALL_CAUHOI"
                    );
                    this.pThamSoMacDinh.BATBUOC_TRALOI_ALL_CAUHOI =
                        (BATBUOC_TRALOI_ALL_CAUHOI ? true : false) &&
                        BATBUOC_TRALOI_ALL_CAUHOI.TEN_TS == "1";

                    let KIEMTRA_CAUHOI_KHL = dsThamSoMacDinh.find(
                        x => x.MA_TS === "KIEMTRA_CAUHOI_KHL"
                    );
                    this.pThamSoMacDinh.KIEMTRA_CAUHOI_KHL =
                        (KIEMTRA_CAUHOI_KHL ? true : false) &&
                        KIEMTRA_CAUHOI_KHL.TEN_TS == "1";

                    let FIMS_DIEU_XN_DONVI = dsThamSoMacDinh.find(
                        x => x.MA_TS === "FIMS_DIEU_XN_DONVI"
                    );
                    this.pThamSoMacDinh.FIMS_DIEU_XN_DONVI =
                        (FIMS_DIEU_XN_DONVI ? true : false) &&
                        FIMS_DIEU_XN_DONVI.TEN_TS == "1";

                    let OUTBOUND_KHONG_DIEULAI = dsThamSoMacDinh.find(
                        x => x.MA_TS === "OUTBOUND_KHONG_DIEULAI"
                    );
                    this.pThamSoMacDinh.OUTBOUND_KHONG_DIEULAI =
                        (OUTBOUND_KHONG_DIEULAI ? true : false) &&
                        OUTBOUND_KHONG_DIEULAI.TEN_TS == "1";

                    let OB_KHONGTL_KTC1 = dsThamSoMacDinh.find(
                        x => x.MA_TS === "OB_KHONGTL_KTC1"
                    );
                    this.pThamSoMacDinh.OB_KHONGTL_KTC1 =
                        (OB_KHONGTL_KTC1 ? true : false) &&
                        OB_KHONGTL_KTC1.TEN_TS == "1";

                    let KHONGTL_CHUYEN_OB_TUDONG = dsThamSoMacDinh.find(
                        x => x.MA_TS === "KHONGTL_CHUYEN_OB_TUDONG"
                    );
                    this.pThamSoMacDinh.KHONGTL_CHUYEN_OB_TUDONG =
                        (KHONGTL_CHUYEN_OB_TUDONG ? true : false) &&
                        KHONGTL_CHUYEN_OB_TUDONG.TEN_TS == "1";

                    let OB_CHON_NHIEU_LYDO_KTL = dsThamSoMacDinh.find(
                        x => x.MA_TS === "OB_CHON_NHIEU_LYDO_KTL"
                    );
                    this.pThamSoMacDinh.OB_CHON_NHIEU_LYDO_KTL =
                        (OB_CHON_NHIEU_LYDO_KTL ? true : false) &&
                        OB_CHON_NHIEU_LYDO_KTL.TEN_TS == "1";

                    let CHOPHEP_SUA_OB_KHONG_TL = dsThamSoMacDinh.find(
                        x => x.MA_TS === "CHOPHEP_SUA_OB_KHONG_TL"
                    );
                    this.pThamSoMacDinh.CHOPHEP_SUA_OB_KHONG_TL =
                        (CHOPHEP_SUA_OB_KHONG_TL ? true : false) &&
                        CHOPHEP_SUA_OB_KHONG_TL.TEN_TS == "1";

                    let LAN_KS_TOIDA = dsThamSoMacDinh.find(
                        x => x.MA_TS === "LAN_KS_TOIDA"
                    );
                    this.pThamSoMacDinh.LAN_KS_TOIDA =
                        (LAN_KS_TOIDA ? true : false) &&
                        LAN_KS_TOIDA.TEN_TS == "1";

                    let KT_DAMBAO_CKCL_KH = dsThamSoMacDinh.find(
                        x => x.MA_TS === "KT_DAMBAO_CKCL_KH"
                    );
                    this.pThamSoMacDinh.KT_DAMBAO_CKCL_KH =
                        (KT_DAMBAO_CKCL_KH ? true : false) &&
                        KT_DAMBAO_CKCL_KH.TEN_TS == "1";
                }
            } catch (e) {
            } finally {
            }
        },
        async hienThiThongTinTB() {
            let data = {
                baoHongId: this.baoHongId
            };
            this.pDsThongTinBaoHong = [];
            try {
                let response = await API.getThongTinBaoHong(this.axios, data);
                if (response.data.error_code === "BSS-00000000") {
                    this.pDsThongTinBaoHong = response.data.data.data || [];
                    if (
                        this.pDsThongTinBaoHong &&
                        this.pDsThongTinBaoHong.length > 0
                    ) {
                        let record = this.pDsThongTinBaoHong[0];
                        this.pBaoHongId = record.BAOHONG_ID;
                        this.pThueBaoId = record.THUEBAO_ID;

                        this.pTxtMaTB = record.MA_TB;
                        this.pTxtTenTB = record.TEN_TB;
                        this.pTxtDiaChiLD = record.DIACHI_LD;
                        this.pTxtDienThoaiLH = record.SO_DT_KHF;
                        if (this.$root.token.getPhanVungId != 26) {
                            this.pTxtSoLH = record.DIENTHOAI_LH;
                            this.pTxtTenNguoiTL = record.TEN_TB;
                        }
                        // Lấy tốc độ loại hình
                        this.pTxtLoaiHinh = record.DICHVUVT_ID;
                        // End thông tin thuê bao
                        // Thông tin báo hỏng
                        this.pTxtNgayBH = moment(record.NGAY_BH).format(
                            "DD/MM/YYYY HH:MM"
                        );
                        this.pTxtNguoiBH = record.NGUOI_BH;
                        this.pTxtSoDTBH = record.DIENTHOAI_LH;
                        this.pTxtNgayNT = moment(record.NGAY_HT).format(
                            "DD/MM/YYYY HH:MM"
                        );
                        this.pTxtTinhTrang = record.TINHTRANG;
                        this.pTxtNguyenNhan = record.NGUYENNHAN;

                        this.pTxtGhiChuNT = record.GHICHU_XL;
                        this.pTxtVPXL = record.VPXL;

                        this.pSoLanHongTrong30Ngay = await this.getSoLanHongTrong30Ngay()
                        let lsBaoHong = await this.getLichSuBaoHong();

                        this.pIsShowThongBaoCoHen =
                            lsBaoHong && lsBaoHong.length > 0;

                        this.pDsLanGoiChuaTraLoi = await this.getLanGoiChuaTraLoi(
                            LOAI_LAN_GOI.BAO_HONG
                        );
                        let dsThongTinTBChuaTraLoi = null;
                        if (this.pIsSuaPhieu)
                            dsThongTinTBChuaTraLoi = await this.getThongTinTBTraLoi(
                                LOAI_LAN_GOI.BAO_HONG
                            );
                        else
                            dsThongTinTBChuaTraLoi = await this.getThongTinTBTraLoi(
                                LOAI_LAN_GOI.HOP_DONG
                            );

                        if (
                            dsThongTinTBChuaTraLoi &&
                            dsThongTinTBChuaTraLoi.length > 0
                        ) {
                            if (this.tbtlId == 0)
                                this.tbtlId = dsThongTinTBChuaTraLoi[0].TBTL_ID;
                            this.pTxtTenNguoiTL =
                                dsThongTinTBChuaTraLoi[0].NGUOI_TL;
                            this.pTxtSoLH =
                                dsThongTinTBChuaTraLoi[0].DIENTHOAI_LH;
                            this.pTxtGhiChu = dsThongTinTBChuaTraLoi[0].GHICHU;
                            this.pChkKTL =
                                dsThongTinTBChuaTraLoi[0].TRANGTHAI == 0;
                            this.pNgayKS = dsThongTinTBChuaTraLoi[0].NGAY_HOI;

                            if (this.pChkKTL) {
                                this.pIsNew = true;
                                if (this.CHOPHEP_SUA_OB_KHONG_TL == -1)
                                    this.tbtlId = 0;

                                if (dsThongTinTBChuaTraLoi[0].LYDOKTL_ID) {
                                    this.pCboLyDoKTL =
                                        dsThongTinTBChuaTraLoi[0].LYDOKTL_ID;
                                }
                            }

                            // if (dsThongTinTBChuaTraLoi[0].)
                        } else {
                            this.pIsNew = true;
                            this.tbtlId = 0;
                        }
                    }
                }
            } catch (e) {
                console.log(e);
            }
        },
        async hienThiCH() {
            // await this.getDsCauHoi();
            this.fakeData.cauHoi = await this.getCauHoiChinh();
            this.fakeData.traLoi = await this.getDsChiTietTraLoi();
            this.processFakeData()
        },
        async getDsCauHoi() {
            try {
                this.pChiTietTraLoi = await this.getDsChiTietTraLoi();
                this.pKhaoSat.dsCauHoiChinh = await this.getCauHoiChinh();
                this.pKhaoSat.dsCauHoiChinh.forEach(async (cauhoi, index) => {
                    try {
                        let cauhoi2 = cauhoi;
                        cauhoi2.cauHoiPhu = await this.getCauHoiPhu(
                            cauhoi2.CAUHOI_ID
                        );
                        cauhoi2.cauHoiPhu.forEach(element => {
                            let tl = this.pChiTietTraLoi.filter(
                                x =>
                                    x.CAUHOI_ID == cauhoi2.CAUHOI_ID &&
                                    x.TRALOI_ID == element.TRALOI_ID
                            );
                            if (tl && tl.length > 0) {
                                element.Checked = true;
                                element.TxtNoiDung = tl.NOIDUNG;
                            } else {
                                element.Checked = false;
                                element.TxtNoiDung = "";
                            }
                        });
                        this.$set(this.pKhaoSat.dsCauHoiChinh, index, cauhoi2);
                    } catch (e) {
                        console.log(e);
                    }
                });
            } catch (e) {
                console.log(e);
            }
        },
        async getKiemTraPhieuBaoHongCKCL() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-kh-bao-hong/kiem-tra-phieu-bao-hong-ckcl",
                    {
                        baoHongId: this.baoHongId,
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data || 0;
                }
                return 0;
            } catch (e) {
                return 0;
            }
        },
        async getSoLanHongTrong30Ngay() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-kh-bao-hong/so-lan-bao-hong-30-ngay",
                    {
                        thueBaoId: this.thueBaoId,
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data || 0;
                }
                return 0;
            } catch (e) {
                return 0;
            }
        },
        async getLichSuBaoHong() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-thue-bao/lich-su-hen-bao-hong",
                    {
                        baoHongId: this.baoHongId,
                        pageNo: 1,
                        pageSize: 10000
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data.data || [];
                }
                return [];
            } catch (e) {
                return [];
            }
        },
        async getDsLyDoKTL() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/danh-muc/ly-do-ktl",
                    {}
                );
                if (response.data.error_code === "BSS-00000000") {
                    return response.data || [];
                }
                return [];
            } catch (e) {
                return [];
            }
        },
        async getLanGoiChuaTraLoi(loai) {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/xac-nhan-phan-hoi-chat-luong/ds-lan-goi-chua-tra-loi",
                    {
                        id: this.baoHongId,
                        loai: loai,
                        pageNo: 1,
                        pageSize: 10000
                    }
                );
                if (response.data.error_code === "BSS-00000000") {
                    return response.data.data.data || [];
                }
                return [];
            } catch (e) {
                return [];
            }
        },
        async getThongTinTBTraLoi(loai) {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/xac-nhan-phan-hoi-chat-luong/ds-thue-bao-tra-loi",
                    {
                        id: this.baoHongId,
                        loai: loai,
                        pageNo: 1,
                        pageSize: 10000
                    }
                );
                if (response.data.error_code === "BSS-00000000") {
                    return response.data.data || [];
                }
                return [];
            } catch (e) {
                return [];
            }
        },
        async getCauHoiChinh() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-thue-bao/cau-hoi",
                    {
                        nhanVienId: 6,
                        hinhThuc: 2
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data || [];
                }
                return [];
            } catch (e) {
                return [];
            }
        },
        async getCauHoiPhu(cauHoiId) {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-thue-bao/tra-loi",
                    {
                        cauHoiId: cauHoiId
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data || [];
                }
                return [];
            } catch (e) {
                return [];
            }
        },
        async getDsChiTietTraLoi() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/xac-nhan-phan-hoi-chat-luong/chi-tiet-tbtl",
                    {
                        tbtlId: this.tbtlId
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data || [];
                }
                return [];
            } catch (e) {
                return [];
            }
        },
        processFakeData() {
            this.fakeData.cauHoi.forEach(x => {
                var i = this.pKhaoSat.dsCauHoiChinh.findIndex(
                    cauhoi => x.CAUHOI_ID == cauhoi.CAUHOI_ID
                );
                if (i <= -1 && x.KIEU != 2) {
                    let dsCauHoiPhu = this.fakeData.cauHoi.filter(
                        y => x.CAUHOI_ID == y.CAUHOI_ID && y.RCH_ID == 0
                    );

                    dsCauHoiPhu.forEach(chp => {
                        let dsCauHoiChau = this.fakeData.cauHoi.filter(
                            y => y.RCH_ID == chp.RCH_ID1
                        );
                        dsCauHoiChau.forEach(e => {
                            let dapan = this.fakeData.traLoi.find(tl => tl.CAUHOI_ID == e.CAUHOI_ID && tl.TRALOI_ID == e.TRALOI_ID)
                            if (dapan) {
                                e.Checked = true
                                e.Text = dapan.NOIDUNG
                            }
                            else {
                                e.Checked = false
                                e.Text = ''
                            }
                            
                        })
                        chp.dsCauHoiChau = dsCauHoiChau;

                        let dapan = this.fakeData.traLoi.find(tl => tl.CAUHOI_ID == chp.CAUHOI_ID && tl.TRALOI_ID == chp.TRALOI_ID)
                        if (dapan) {
                            chp.Checked = true
                            chp.Text = dapan.NOIDUNG
                        }
                        else {
                            chp.Checked = false
                            chp.Text = ''
                        } 
                    });

                    dsCauHoiPhu = dsCauHoiPhu.sort(
                        (a, b) => (a.THUTU_TL > b.THUTU_TL ? 1 : -1)
                    );

                    this.pKhaoSat.dsCauHoiChinh.push({
                        CAUHOI_ID: x.CAUHOI_ID,
                        TENCAUHOI: x.NOIDUNG,
                        KIEU: x.KIEU,
                        HINHTHUC: x.HINHTHUC,
                        LOAICH_ID: x.LOAI,
                        THUTU: x.THUTU,
                        cauHoiPhu: dsCauHoiPhu
                    });
                }
            });
            this.pKhaoSat.dsCauHoiChinh = this.pKhaoSat.dsCauHoiChinh.sort(
                (a, b) => (a.THUTU > b.THUTU ? 1 : -1)
            );
        },
        gridLanGoiChuaTraLoi_selectedRowChanged(dataItem) {},
        clearData() {
            this.pTxtMaTB = "";
            this.pTxtLoaiHinh = "";
            this.pTxtTBCungCap = "";
            this.pTxtTenTB = "";
            this.pTxtDiaChiLD = "";
            this.pTxtDienThoaiLH = "";
            this.pTxtTenNguoiTL = "";
            this.pTxtSoLH = "";
            this.pTxtGhiChu = "";
            this.pTxtVPXL = "";
            this.pTxtNgayBH = "";
            this.pTxtNgayNT = "";
            this.pTxtNguoiBH = "";
            this.pTxtSoDTBH = "";
            this.pTxtTinhTrang = "";
            this.pTxtNguyenNhan = "";
            this.pTxtGhiChuNT = "";

            this.pIsSuaPhieu = false;
            this.pDsThongTinBaoHong = [];
            this.pBaoHongId = 0;
            this.pThueBaoId = 0;

            this.pKhaoSat.dsCauHoiChinh = []
            this.fakeData.cauHoi = []
            this.fakeData.traLoi = []
        },
        onTraLoiChecked(index, indexPhu, isRadio) {
            let ch = this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu[indexPhu];
            if (isRadio) {
                for (let i = 0; i < this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu.length; i++) {
                    let tmp = this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu[i]
                    if (i != indexPhu) {
                        tmp.Checked = false
                        this.$set(this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu, i, tmp);
                    }
                }
            }

            ch.Checked = !ch.Checked
            this.$set(this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu, indexPhu, ch);
        },
        onTraLoiCauHoiPhuChecked(index, indexPhu, indexChau) {
            let chp = this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu[indexPhu]
            for (let i = 0; i < chp.dsCauHoiChau.length; i++) {
                if (i != indexChau) {
                    chp.dsCauHoiChau[i].Checked = false
                }
                else {
                    chp.dsCauHoiChau[i].Checked = !chp.dsCauHoiChau[i].Checked
                }
            }
            this.$set(this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu, indexPhu, chp);
        },
        
        onGhiLaiClicked() {
            try {
                
            }
            catch (e) {
                console.log(e)
                this.$root.toastError('Có lỗi ' + e)
            }
        },
        focusInput(name) {
            this.$nextTick(() => this.$refs[name].select())
        },
        async kiemTraDuLieu(isThemMoi) {
            if (!this.pTxtTenNguoiTL && this.pChkKTL == true) {
                this.$root.toastError('Chưa nhập tên người trả lời !')
                this.focusInput('txtTenNguoiTL')
                return false
            }
            if (!this.pTxtSoLH) {
                this.$root.toastError('Chưa nhập điện thoại liên hệ !')
                this.focusInput('txtSoLH')
                return false
            }
            if (this.pTxtSoLH.isNaN()) {
                this.$root.toastError('Số điện thoại liên hệ của khách hàng phải là kiểu số!')
                this.focusInput('txtSoLH')
                return false
            }
            if (this.pChkKTL && this.pCboLyDoKTL == 20) //FIMS_LYDO_KTL.KHACHHANG_HEN
            {
                if (this.pchkHenKS_Tu && this.pchkHenKS_Den) {
                    this.$root.toastError('Hãy chọn Ngày hẹn')
                    return false
                }
                if (this.pchkHenKS_Tu && !this.pDtpHenKS_Tu) {
                    this.$root.toastError('Hãy chọn Ngày hẹn khảo sát từ')
                    return false
                }
                if (this.pchkHenKS_Tu && this.pDtpHenKS_Tu < this.today) {
                    this.$root.toastError('Ngày hẹn từ không thể nhỏ hơn ngày hiện tại')
                    return false
                }   
                if (this.pchkHenKS_Den && !this.pDtpHenKS_Den) {
                    this.$root.toastError('Hãy chọn Ngày hẹn khảo sát đến')
                    return false
                }
                if (this.pchkHenKS_Den && this.pDtpHenKS_Den < this.today) {
                    this.$root.toastError('Ngày hẹn đến không thể nhỏ hơn ngày hiện tại')
                    return false
                }   
                if (this.pchkHenKS_Tu && this.pDsThongTinBaoHong[0].NGAY_HT && moment(this.pDsThongTinBaoHong[0].NGAY_HT).add(3, 'days') < this.pDtpHenKS_Tu) {
                    this.$root.toastError('Không thể hẹn quá 72h tính từ thời điểm nghiệm thu: ' + this.pDsThongTinBaoHong[0].NGAY_HT)
                    return false
                }
                if (this.pchkHenKS_Den && this.pDsThongTinBaoHong[0].NGAY_HT && moment(this.pDsThongTinBaoHong[0].NGAY_HT).add(3, 'days') < this.pDtpHenKS_Den) {
                    this.$root.toastError('Không thể hẹn quá 72h tính từ thời điểm nghiệm thu: ' + this.pDsThongTinBaoHong[0].NGAY_HT)
                    return false
                }
            }


        }
    }
};
</script>
