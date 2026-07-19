<template>
<ejs-dialog
        :enableResize="true"
        :allowDragging="true"
        ref="frmKhaoSatKH_KLL"
        :header="'Khảo sát khách hàng Fiber không PS lưu lượng/MyTV không đăng nhập/GPON suy hao cao'"
        :animationSettings="animationSettings"
        showCloseIcon="true"
        width="90%"
        :visible='false'
        :target="target"
        :open="openDialog"
        :close="closeDialog"
        :isModal="true"
    >
    <div class="">
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="javascript: void(0)" @click="onGhiLaiClicked" data-toggle="tooltip" title="Ghi lại">
                        <span class="icon one-save"></span>Ghi lại
                    </a>
                </li>
                <!-- <li>
                    <a href="javascript: void(0)" @click="onDieuLaiClicked" data-toggle="tooltip" title="Điều lại">
                        <span
                            class="icon nc-icon-glyph arrows-1_curved-next"
                        ></span
                        >Điều lại
                    </a>
                    <confirmPopup :content="xacNhanDieuLai" idModal='popup-confirmDieuLai' @onOk="onDieuLaiConfirmed"/>
                </li> -->
                <li>
                    <a href="javascript: void(0)" @click="onDieuBaoDuongClicked" data-toggle="tooltip" title="Điều bảo dưỡng">
                        <span
                            class="icon nc-icon-glyph arrows-1_curved-next"
                        ></span
                        >Điều bảo dưỡng
                    </a>
                    <confirmPopup :content="xacNhanDieuBaoDuong" idModal='popup-confirmDieuBaoDuong' @onOk="onDieuBaoDuongConfirmed"/>
                    <confirmPopup :content="msgCanhBao" idModal='popup-confirmKhaoSat' @onCancel="closeDialog"/>
                </li>
                <li>
                    <a href="javascript: void(0)" data-toggle="tooltip" title="Lịch sử hỏng" @click="onLichSuClicked">
                        <span class="icon one-history"></span>Lịch sử hỏng
                    </a>
                </li>
                <!-- <li>
                    <a href="http://10.70.52.164:8081/FILE/hdsd/frmKhaoSatKH_KLL.html" target="_blank">
                        <span class="icon files_single-content-02 nc-icon-glyph" ></span>
                        Trợ giúp
                    </a>
                </li> -->
            </ul>
        </div>
        <div class="popup-body">
            <div class="row">
                <div class="col-sm-5 col-12">
                    <div class="box-form">
                        <div class="legend-title">
                            Thông tin khách hàng / Thuê bao
                        </div>
                        <div class="info-row">
                            <div class="key w140">Số máy/Acc</div>
                            <div class="value">
                                <input
                                    type="text"
                                    class="form-control"
                                    v-model="pTxtMaTB"
                                />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w140">Loại hình/tốc độ</div>
                            <div class="value">
                                <input
                                    type="text"
                                    class="form-control"
                                    v-model="pTxtLoaiHinhTB"
                                />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w140">Tên thuê bao</div>
                            <div class="value">
                                <input
                                    type="text"
                                    class="form-control"
                                    v-model="pTxtTenTB"
                                />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w140">Địa chỉ lắp đặt</div>
                            <div class="value">
                                <input
                                    type="text"
                                    class="form-control"
                                    v-model="pTxtDiaChiLD"
                                />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w140">Điện thoại liên hệ</div>
                            <div class="value">
                                <input
                                    type="text"
                                    class="form-control"
                                    v-model="pTxtDienThoaiLH"
                                />
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Thông tin cập nhật</div>
                        <div class="info-row">
                            <div class="key w140">Tên người trả lời</div>
                            <div class="value">
                                <input
                                    type="text"
                                    class="form-control"
                                    v-model="pTxtTenNguoiTL"
                                    ref='txtTenNguoiTL'
                                />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w140">Số liên hệ</div>
                            <div class="value">
                                <input
                                    type="text"
                                    class="form-control"
                                    v-model="pTxtSoLH"
                                    ref='txtSoLH'
                                />
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w140">
                                        <div class="check-action">
                                            <input
                                                type="checkbox"
                                                class="check"
                                                v-model="pCboNgayHen"
                                                :value="true"
                                                :unchecked-value="false"
                                            />
                                            <span class="name"
                                                >Hẹn sử dụng lại từ</span
                                            >
                                        </div>
                                    </div>
                                    <div class="value">
                                       <ejs-datepicker v-model="pDtpTuNgay" :format="'dd/MM/y'"
                                            :showClearButton="false" :allowEdit="true" :enabled="pCboNgayHen">
                                        </ejs-datepicker>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w40">Đến</div>
                                    <div class="value">
                                        <ejs-datepicker v-model="pDtpDenNgay" :format="'dd/MM/y'"
                                            :showClearButton="false" :allowEdit="true" :enabled="pCboNgayHen">
                                        </ejs-datepicker>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-7 col-12">
                    <div class="box-form">
                        <div class="legend-title">
                            <div class="pull-left">
                                Danh sách lần hỏi
                                <div class="check-action normal red marl10">
                                    <input type="checkbox" class="check" v-model="pChkKTL"/>
                                    <span class="name font-weight-bold" style="width:150px !important;">Không trả lời</span>
                                </div>
                            </div>
                            <div class="pull-right">
                                <div class="select-custom" style="width: 350px;">
                                    <b-form-select
                                        v-model="optLyDoKoTraLoiSelected"
                                        :disabled="!pChkKTL"
                                        :options="optLyDoKoTraLoi"
                                        value-field="LYDOKTL_ID"
                                        text-field="LYDO_KTL"
                                        class="form-control"
                                    >
                                    </b-form-select>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                            <DataGrid
                                v-bind:columns="[
                                    {
                                        fieldName: 'NGUOI_CN',
                                        headerText: 'Người hỏi',
                                        allowFiltering: true,
                                        allowSorting: false
                                    },
                                    {
                                        fieldName: 'NGUOI_TL',
                                        headerText: 'Người trả lời',
                                        allowFiltering: true
                                    },
                                    {
                                        fieldName: 'DIENTHOAI_LH',
                                        headerText: 'Điện thoại',
                                        allowFiltering: true
                                    },
                                    {
                                        fieldName: 'NGAY_HOI',
                                        headerText: 'Ngày hỏi',
                                        allowFiltering: true
                                    },
                                    {
                                        fieldName: 'TRANGTHAI_TXT',
                                        headerText: 'Trạng thái',
                                        allowFiltering: true
                                    },
                                    {
                                        fieldName: 'GHICHU',
                                        headerText: 'Ghi chú',
                                        allowFiltering: true
                                    }
                                ]"
                                v-bind:dataSource="this.dsLichSuChamSoc"
                                :showFilter="true"
                                :allowSorting="true"
                                :allowPaging="true"
                                :enablePagingServer="false"
                            ></DataGrid>
                    </div>
                </div>
            </div>
            <div class="info-row padl12">
                <div class="key w140">Ghi chú</div>
                <div class="value">
                    <input
                        type="text"
                        class="form-control"
                        v-model="pTxtGhiChu"
                        ref="txtGhiChu"
                        maxlength="500"
                    />
                </div>
            </div>
            <div class="box-form">
                <cauHoiKS
                    ref="cauHoiKS"
                    :hinhThuc="0"
                    :nhanVienId="25"
                    :tbtlId="pTxtTBTLId"
                />
            </div>
        </div>
        <footer class="footer">VNPT-IT &copy; 2021</footer>
        <PopUpMoMay />
    </div>
</ejs-dialog>
</template>
<style scope>
.e-grid .e-gridheader
{
    position: -webkit-sticky;
    position: sticky;
    top: 0px;
    z-index: 1;
}
.legend-title {
    font-weight: 600 !important;
}
</style>
<script>
import API from "../api/DanhSachKSTB5NgayKLLApi";
import YesNoModal from "../utils/YesNoModal.vue";
import cauHoiKS from "./frmCauHoiKS.vue";
import moment from "moment"
import PopUpMoMay from "../TraCuuLichSuBaoHong";

export default {
    props: {
        ctSuyHaoId: {
            type: Number,
            default: 0
        },
        hinhThuc: {
            type: Number,
            default: 3
        }
    },
    components: {
        confirmPopup: YesNoModal,
        cauHoiKS,
        PopUpMoMay
    },
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },

            optLyDoKoTraLoiSelected: 0,
            optLyDoKoTraLoi: [],
            dsLichSuChamSoc: [],
            dsCauHoi: [],
            pTxtMaTB: "",
            pTxtLoaiHinhTB: "",
            pTxtTenTB: "",
            pTxtDiaChiLD: "",
            pTxtDienThoaiLH: "",
            pTxtThueBaoId: "",
            pTxtTBTLId: 0,
            pTxtTenNguoiTL: "",
            pTxtSoLH: "",
            pTxtGhiChu: "",
            pCboTrangThai: 0,
            pCboNgayHen: false,
            pDtpTuNgay: new Date(),
            pDtpDenNgay: new Date(),
            pRdoCauHoiChaId: 0,
            dsCauHoi: [],
            dsCauHoiPhu: [],
            pKhaoSat: {
                dsCauHoiChinh: [],
                dsCauHoiPhu: [],
                dsCauTraLoi: []
            },
            fakeData: {
                cauHoi: [],
                traLoi: []
            },
            pIsNew: false,
            pChkKTL: false,
            today: new Date(),

            msgCanhBao: '',
            khaoSatXong: false,
            khaoSatMoi: false,
            khaoSatMoiDone: false,
            row: -1,
        };

    },
    computed: {
        cauHoiPhuContent() {
            let cauHoiPhu = this.dsCauHoi.find(
                x => x.CAUHOI_ID == this.pRdoCauHoiChaId
            );
            return cauHoiPhu ? cauHoiPhu.TENCAUHOI : "";
        },
        kieuCauHoiCha() {
            let cauHoi = this.dsCauHoi.find(
                x => x.CAUHOI_ID == this.pRdoCauHoiChaId
            );
            return cauHoi ? cauHoi.KIEU : 0;
        },
        getKetQua() {
            return this.$refs["cauHoiKS"].ketQuaKS
        },
        xacNhanDieuLai() {
            return "Bạn có chắc chắn muốn điều hỏng thuê bao " + this.pTxtMaTB + " ?"
        },
        xacNhanDieuBaoDuong() {
            return "Bạn có chắc chắn muốn điều bảo dưỡng thuê bao " + this.pTxtMaTB + " ?"
        }
    },
    async created() {
    },
    async mounted() {

    },
    methods: {
        async openDialog() {
            this.$forceUpdate()
            this.$refs.frmKhaoSatKH_KLL.show()
            this.khaoSatXong = false
            this.khaoSatMoiDone = false
            try {
                this.loading(true);
                this.initData()
                this.pKhaoSat.dsCauHoiChinh = [];
                await Promise.all([
                    this.getDsLyDoKoTraLoi(),
                    this.getLichSuGoiChamSoc(),
                    this.getDsCauHoiTheoNV(),
                    this.layThongTinTBKoLuuLuong(),
                    this.layThongTinTBTraLoi()
                ]);
                await this.$refs.cauHoiKS.loadData()
                //this.processFakeData();
            }
            catch (e) {

            }
            finally {
                this.loading(false);
            }
        },

        initData() {
            this.clearData()
            this.pCboNgayHen = false
            this.pDtpTuNgay = new Date()
            this.pDtpDenNgay = new Date()
            this.pChkKTL = false
            this.optLyDoKoTraLoiSelected = 0
        },

        closeDialog() {
            if (this.khaoSatMoiDone) {
                this.$emit("onKhaoSatXong")
            }
            this.$emit("onHideKhaoSat");
            this.$refs.frmKhaoSatKH_KLL.hide()
        },
        async getThongTinTB() {
            var rs = await this.$root.context.post(
                "/web-cskh/api/don-vi/nhan-vien",
                data
            );
        },
        async getDsLyDoKoTraLoi() {
            this.optLyDoKoTraLoi = [];
            try {
                let response = await API.getDsLyDoKoTraLoi(this.axios);
                if (response.data.error_code === "BSS-00000000") {
                    this.optLyDoKoTraLoi = response.data.data || [];
                }
            } catch (e) {
                console.log(e);
            }
        },
        async getLichSuGoiChamSoc() {
            this.dsLichSuChamSoc = [];
            try {
                let response = await API.getLichSuGoiChamSoc(this.axios, {
                    id: this.ctSuyHaoId,
                    pageNo: 1,
                    pageSize: 1000
                });
                if (response.data.error_code === "BSS-00000000") {
                    this.dsLichSuChamSoc = response.data.data.data || [];
                    this.dsLichSuChamSoc.forEach(e => {
                        e.TRANGTHAI_TXT = e.TRANGTHAI == 0 ? "Chưa trả lời" : "Đã trả lời"
                    })
                    if (this.dsLichSuChamSoc.length > 0) {
                        let chamSoc = this.dsLichSuChamSoc[0]
                        if (chamSoc.TRANGTHAI == 0) {
                            this.msgCanhBao = `Nhân viên ${chamSoc.NGUOI_CN} đã gọi điện cho khách hàng vào lúc ${chamSoc.NGAY_HOI} nhưng chưa liên hệ được!\r\nBạn có muốn tiếp tục?`
                        }
                        else {
                            if (chamSoc.NHANVIEN_ID == this.$auth.getNhanVienID()) {
                                this.msgCanhBao = `Bạn đã khảo sát khách hàng này lúc ${chamSoc.NGAY_HOI}.\r\nBạn có chắc muốn khảo sát lại?`
                            }
                            else {
                                this.msgCanhBao = `Trường hợp này đã được khảo sát lúc ${chamSoc.NGAY_HOI} bởi nhân viên ${chamSoc.NGUOI_CN}.\r\nBạn có chắc muốn khảo sát lại?`
                            }

                        }
                        this.$bvModal.show('popup-confirmKhaoSat')
                    }
                }
            } catch (e) {
                console.log(e);
            }
        },
        async layThongTinTBKoLuuLuong() {
            try {
                this.clearData();
                let response = await API.layThongTinTBKoLuuLuong(this.axios, {
                    ctSuyHaoId: this.ctSuyHaoId
                });
                if (response.data.error_code === "BSS-00000000") {
                    let dsThongTin = response.data.data.data || [];
                    if (dsThongTin.length > 0) {
                        this.pTxtThueBaoId = dsThongTin[0].THUEBAO_ID;
                        this.pTxtMaTB = dsThongTin[0].MA_TB;
                        this.pTxtLoaiHinhTB = dsThongTin[0].LOAIHINH_TB;
                        this.pTxtTenTB = dsThongTin[0].TEN_TB;
                        this.pTxtDiaChiLD = dsThongTin[0].DIACHI_LD;
                        this.pTxtDienThoaiLH = dsThongTin[0].DIENTHOAI_LH;
                    }
                }
            } catch (e) {
                console.log(e);
            } finally {
            }
        },
        async layDsLanGoiChuaTL(ctSuyHaoId) {
            try {
                let rs = await this.$root.context.get(
                    "/web-cskh/api/xac-nhan-phan-hoi-chat-luong/ds-lan-goi-chua-tra-loi",
                    {
                        id: ctSuyHaoId,
                        loai: 3,
                        pageNo: 1,
                        pageSize: 10000
                    }
                );
                if (rs.error_code === this.API_SUCCESS) {
                    return rs.data.data || [];
                }
            } catch (e) {
                console.log(e);
            }
            return [];
        },
        async layThongTinTBTraLoi() {
            try {
                this.clearData();
                let response = await this.$root.context.get("/web-cskh/api/xac-nhan-phan-hoi-chat-luong/ds-lan-goi-chua-tra-loi", {
                    id: this.ctSuyHaoId,
                    loai: 3,
                    pageNo: 1,
                    pageSize: 10000
                });
                if (response.error_code === "BSS-00000000") {
                    let dsThongTin = response.data.data || [];
                    if (dsThongTin.length > 0) {
                        this.pIsNew = false
                        this.pTxtTBTLId = dsThongTin[0].TBTL_ID;
                        this.pTxtTenNguoiTL = dsThongTin[0].NGUOI_TL;
                        this.pTxtSoLH = dsThongTin[0].DIENTHOAI_LH;
                        this.pTxtGhiChu = dsThongTin[0].GHICHU;
                        this.pCboTrangThai = dsThongTin[0].TRANGTHAI;
                        this.pTxtSoLH = dsThongTin[0].DIENTHOAI_LH;
                        this.pChkKTL = dsThongTin[0].TRANGTHAI_ID == 0
                        // console.log('NGAYHEN_TU', dsThongTin[0].NGAYHEN_TU)
                        // console.log('NGAYHEN_TU', moment(dsThongTin[0].NGAYHEN_TU, 'dd/MM/yyyy HH:mm', true).toDate())
                        // console.log('NGAYHEN_DEN', dsThongTin[0].NGAYHEN_DEN)
                        // console.log('NGAYHEN_DEN', moment(dsThongTin[0].NGAYHEN_DEN).toDate())
                        // console.log('NGAY_HOI', moment(dsThongTin[0].NGAY_HOI, 'DD/MM/YYYY HH:mm', true).toDate())
                        if (dsThongTin[0].NGAYHEN_DEN) {
                            this.pCboNgayHen = true;
                            this.pDtpTuNgay = moment(dsThongTin[0].NGAYHEN_TU, 'DD/MM/YYYY HH:mm', true).toDate()
                            this.pDtpDenNgay = moment(dsThongTin[0].NGAYHEN_DEN, 'DD/MM/YYYY HH:mm', true).toDate()
                        }
                        else
                        {
                            this.pCboNgayHen = false
                        }
                        if (this.pChkKTL) {
                            this.optLyDoKoTraLoiSelected = dsThongTin[0].LYDOKTL_ID
                            this.pIsNew = true
                            this.pTxtTBTLId = 0
                        }
                    }
                    else {
                        this.pIsNew = true
                        this.pTxtTBTLId = 0
                    }
                }
            } catch (e) {
                console.log(e);
            } finally {
            }
        },
        async getDsCauHoiTheoNV() {
            try {
                this.dsCauHoi = [];
                let response = await API.getDsCauHoiTheoNV(this.axios);
                if (response.data.error_code === "BSS-00000000") {
                    this.fakeData.cauHoi = response.data.data || [];
                    this.fakeData.cauHoi.forEach((element, index) => element.STT = index)
                }
            } catch (e) {
            } finally {
            }
        },
        async getDsCauHoiPhu() {
            try {
                this.loading(true);
                this.dsCauHoiPhu = [];
                let response = await API.getDsCauHoiPhu(this.axios, {
                    cauHoiId: this.pRdoCauHoiChaId
                });
                if (response.data.error_code === "BSS-00000000") {
                    this.dsCauHoiPhu = response.data.data || [];
                }
            } catch (e) {
            } finally {
                this.loading(false);
            }
        },
        onTraLoiChecked(index, indexPhu, isRadio) {
            let ch = this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu[indexPhu];
            if (isRadio) {
                for (
                    let i = 0;
                    i < this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu.length;
                    i++
                ) {
                    let tmp = this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu[i];
                    if (i != indexPhu) {
                        tmp.Checked = false;
                        this.$set(
                            this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu,
                            i,
                            tmp
                        );
                    }
                }
            }

            ch.Checked = !ch.Checked;
            this.$set(
                this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu,
                indexPhu,
                ch
            );
        },
        onTraLoiCauHoiPhuChecked(index, indexPhu, indexChau) {
            let chp = this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu[indexPhu];
            if (chp.LOAICH_ID == 1) {
                for (let i = 0; i < chp.dsCauHoiChau.length; i++) {
                    if (i != indexChau) {
                        chp.dsCauHoiChau[i].Checked = false;
                    } else {
                        chp.dsCauHoiChau[i].Checked = !chp.dsCauHoiChau[i]
                            .Checked;
                    }
                }
            } else {
                chp.dsCauHoiChau[i].Checked = !chp.dsCauHoiChau[i].Checked;
            }

            this.$set(
                this.pKhaoSat.dsCauHoiChinh[index].cauHoiPhu,
                indexPhu,
                chp
            );
        },
        focusInput(name) {
            this.$nextTick(() => this.$refs[name].select())
        },
        async kiemTraDuLieu(isThemMoi) {
            if (this.pTxtGhiChu && this.pTxtGhiChu.trim().length > 500) {
                this.$root.toastError("Trường ghi chú vượt quá 500 ký tự!");
                this.focusInput('txtGhiChu')
                return false;
            }
            if (this.pTxtTenNguoiTL && this.pTxtTenNguoiTL.trim().length > 150) {
                this.$root.toastError("Tên người trả lời vượt quá 150 ký tự!");
                this.focusInput('txtTenNguoiTL')
                return false;
            }
            if (this.pTxtSoLH && this.pTxtSoLH.trim().length > 100) {
                this.$root.toastError("Điện thoại liên hệ vượt quá 100 ký tự!");
                this.focusInput('txtSoLH')
                return false;
            }
            if (!this.pTxtTenNguoiTL && !this.pChkKTL) {
                this.$root.toastError("Chưa nhập tên người trả lời !");
                this.focusInput('txtTenNguoiTL')
                return false;
            }
            if (!this.pTxtSoLH) {
                this.$root.toastError("Chưa nhập điện thoại liên hệ !");
                this.focusInput('txtSoLH')
                return false;
            }

            if (!this.isNumeric(this.pTxtSoLH)) {
                this.$root.toastError("Số điện thoại liên hệ của khách hàng phải là kiểu số!");
                this.focusInput("txtSoLH");
                return false;
            }


            if (this.pChkKTL && this.optLyDoKoTraLoiSelected == 0) {
                this.$root.toastError("Chưa chọn lý do không trả lời!");
                return false;
            }

            if (moment(this.pDtpTuNgay).isAfter(moment(this.pDtpDenNgay))) {
                this.$root.toastError("Hẹn sử dụng lại từ ngày không được nhập lớn hơn Đến ngày")
                return false;
            }

            let BATBUOC_CHON_TRALOI_KLL = -1
            let dsTs = await this.getThamSoMacDinh('BATBUOC_CHON_TRALOI_KLL')
            if (dsTs && dsTs.length > 0) {
                BATBUOC_CHON_TRALOI_KLL = dsTs[0].TEN_TS
            }

            if (BATBUOC_CHON_TRALOI_KLL == 1 && !this.pChkKTL && this.getKetQua.length <= 0) {
                this.$root.toastError("Bạn phải chọn câu trả lời!");
                return false;
            }

            if ( this.getKetQua.length > 0 && this.pChkKTL ) {
                this.$root.toastError(
                    'Bạn đang chọn "Không trả lời" và đồng thời tích chọn trả lời trong Danh sách câu hỏi!'
                );
                return false;
            }

            // for (let i = 0; i < this.getKetQua.length; i++) {
            //     console.log('this.getKetQua[i].NOIDUNG', this.getKetQua[i].NOIDUNG)
            //     if (this.getKetQua[i].NOIDUNG && this.getKetQua[i].NOIDUNG.trim().length > 500) {
            //         this.$root.toastError("Câu trả lời vượt quá 500 ký tự!");
            //         return false;
            //     }
            // }

            // this.getKetQua.forEach(kq => { if (kq.NOIDUNG && kq.NOIDUNG.trim().length > 500) {
            //         this.$root.toastError("Câu trả lời vượt quá 500 ký tự!");
            //         return false;
            //     }
            // })

            //kiem tra cau hoi bat buoc tra loi
            dsTs = await this.getThamSoMacDinh('KHAOSAT_BATBUOC_KLL')
            if (dsTs && dsTs.length > 0) {
                let strBatBuoc = dsTs[0].TEN_TS
                if (strBatBuoc && this.pChkKTL) {
                    let tmp = strBatBuoc.split('|')
                    if (tmp[0] == '1') {
                        let dsBatBuoc = tmp[1].split(',')
                        let dsCauHoi = this.getKetQua.filter(x => dsBatBuoc.includes(x.CAUHOI_ID))
                        if (dsCauHoi.length != dsBatBuoc.length) {
                            let str = ''
                            dsBatBuoc.forEach(e => {
                                let ch = this.fakeData.cauHoi.find(x => x.CAUHOI_ID == e)
                                if (ch) {
                                    str += ' - ' + ch.NOIDUNG
                                }
                            })
                            this.$root.toastError("Bạn phải chọn trả lời các câu hỏi: " + str)
                            this.focusInput('txtTenNguoiTL')
                            return false
                        }
                    }
                    else {
                        let dsBatBuoc = tmp[1].split(',')
                        let dsCauHoi = this.getKetQua.filter(x => dsBatBuoc.includes(x.CAUHOI_ID))
                        if (dsCauHoi.length == 0) {
                            let str = ''
                            dsBatBuoc.forEach(e => {
                                let ch = this.fakeData.cauHoi.find(x => x.CAUHOI_ID == e)
                                if (ch) {
                                    str += ' - ' + ch.NOIDUNG
                                }
                            })
                            this.$root.toastError("Bạn phải chọn trả lời một trong các câu hỏi: " + str)
                            this.focusInput('txtTenNguoiTL')
                            return false
                        }
                    }
                }
            }

            //kiem tra cau hoi phu co textbox
            return true
        },
        async getThamSoMacDinh() {
            try {
            } catch (e) {}
        },
        clearData() {
            this.pTxtMaTB = "";
            this.pTxtLoaiHinhTB = "";
            this.pTxtTenTB = "";
            this.pTxtDiaChiLD = "";
            this.pTxtDienThoaiLH = "";
            this.pTxtThueBaoId = 0;
            this.pTxtTBTLId = 0;
            this.pTxtTenNguoiTL = "";
            this.pTxtSoLH = "";
            this.pTxtGhiChu = "";
            this.pCboTrangThai = 0;
        },
        async onSaveKhaoSat(data) {
            let rs = await this.$root.context.post('/web-cskh/api/khao-sat-kh-kll/ghi-lai', data)
            if (rs.error_code === "BSS-00000000")
            {
                if (rs.data.ketQua != 1)
                {
                    this.$root.toastError(rs.data.msgError)
                }
                else
                {
                    this.khaoSatMoiDone = true
                    this.$root.toastSuccess('Cập nhật thành công!')
                    if (rs.data.msgError)
                    {
                        let listMessages = rs.data.msgError.split("#")
                        listMessages.forEach(message => {
                            let pattern = /.*\d/g;
                            let result = message.match(pattern)
                            if (result && result.length > 0 && result[0].includes('1'))
                            {
                                this.$root.toastSuccess(message.replace(result[0], ""))
                            }
                            else
                            {
                                this.$root.toastError(message.replace(result[0], ""))
                            }
                        });
                    }
                    this.closeDialog()
                }
            }
            else {
                this.$root.toastError('Cập nhật thất bại')
            }
        },
        async onGhiLaiClicked() {
            try {
              this.$root.showLoading(true)
                if (this.pIsNew) {
                    if (await this.kiemTraDuLieu(true)) {
                        let data = this.taoDuLieu(true, false)
                        await this.onSaveKhaoSat(data)
                    }
                } else {
                    if (await this.kiemTraDuLieu(false)) {
                      let data = this.taoDuLieu(false, false)
                      await this.onSaveKhaoSat(data)
                    }
                }
            } catch (e) {
              console.log(e)
              this.$root.toastError(e.response.data.message)
            }
            finally {
              this.$root.showLoading(false)
            }
        },
        async ghiLaiKhaoSat(data) {
            try {
            } catch (e) {
                console.log(e);
            }
        },
        taoDuLieu(isThemMoi, isXuatLai) {
          let kq = this.getKetQua
            let data = {
                hinhThuc: this.hinhThuc,
                ctSuyHaoId: this.ctSuyHaoId,
                tbtlId: isThemMoi ? 0 : this.pTxtTBTLId,
                thueBaoId: this.pTxtThueBaoId,
                nguoiTl: this.pTxtTenNguoiTL ? this.pTxtTenNguoiTL.trim() : "",
                dienThoaiLh: this.pTxtSoLH ? this.pTxtSoLH.trim() : "",
                ghiChu: this.pTxtGhiChu ? this.pTxtGhiChu.trim() : "",
                lyDoKtlId: this.pChkKTL ? this.optLyDoKoTraLoiSelected : 0,
                gioHenTu: this.pCboNgayHen ? moment(this.pDtpTuNgay).format('DD/MM/YYYY') : '',
                gioHenDen: this.pCboNgayHen ? moment(this.pDtpDenNgay).format('DD/MM/YYYY') : '',
                dsCauHoiId: kq.map(x => x.CAUHOI_ID).join('#'),
                dsTraLoiId: kq.map(x => x.TRALOI_ID).join('#'),
                dsNoiDung: JSON.stringify(kq.map(x => `${x.NOIDUNG}`)),
                isCnSoLienHe: 0,
                nguoiDungId: this.$root.token.getNguoiDungID()
            };
            return data
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
                            let dapan = this.fakeData.traLoi.find(
                                tl =>
                                    tl.CAUHOI_ID == e.CAUHOI_ID &&
                                    tl.TRALOI_ID == e.TRALOI_ID
                            );
                            if (dapan) {
                                e.Checked = true;
                                e.Text = dapan.NOIDUNG;
                            } else {
                                e.Checked = false;
                                e.Text = "";
                            }
                        });
                        chp.dsCauHoiChau = dsCauHoiChau;

                        let dapan = this.fakeData.traLoi.find(
                            tl =>
                                tl.CAUHOI_ID == chp.CAUHOI_ID &&
                                tl.TRALOI_ID == chp.TRALOI_ID
                        );
                        if (dapan) {
                            chp.Checked = true;
                            chp.Text = dapan.NOIDUNG;
                        } else {
                            chp.Checked = false;
                            chp.Text = "";
                        }
                    });

                    dsCauHoiPhu = dsCauHoiPhu.sort((a, b) =>
                        a.STT > b.STT ? 1 : -1
                    );

                    this.pKhaoSat.dsCauHoiChinh.push({
                        CAUHOI_ID: x.CAUHOI_ID,
                        TENCAUHOI: x.NOIDUNG,
                        KIEU: x.KIEU,
                        HINHTHUC: x.HINHTHUC,
                        LOAICH_ID: x.LOAICH_ID,
                        THUTU: x.THUTU,
                        TRALOI_ID: x.TRALOI_ID,
                        cauHoiPhu: dsCauHoiPhu
                    });
                }
            });
            this.pKhaoSat.dsCauHoiChinh = this.pKhaoSat.dsCauHoiChinh.sort(
                (a, b) => (a.STT > b.STT ? 1 : -1)
            );
        },
        onDieuLaiClicked() {
            if (this.pIsNew) {
                this.$bvModal.show('popup-confirmDieuLai')
            }
            else {
                this.$root.toastError("Phiếu đã khảo sát không thực hiện được chức năng này!")
                return
            }
        },
         onDieuBaoDuongClicked() {
            if (this.pIsNew) {
                this.$bvModal.show('popup-confirmDieuBaoDuong')
            }
            else {
                this.$root.toastError("Phiếu đã khảo sát không thực hiện được chức năng này!")
                return
            }
        },
        async getThamSoMacDinh(ts) {
            try {
                let response = await this.$root.context.get('/web-cskh/api/danh-muc/tham-so-mac-dinh', {
                    dsMaTs: ts
                })
                if (response.error_code === "BSS-00000000")
                    return response.data || []
                return []
            }
            catch (e) {
                return []
            }
        },
        async onDieuLaiConfirmed() {
            try {
                this.$root.showLoading(true)
                if (await this.kiemTraDuLieu(true)) {
                    let data = this.taoDuLieu(true, true)
                    let rs = await this.$root.context.post('/web-cskh/api/khao-sat-kh-kll/dieu-lai', data)
                    if (rs.error_code === "BSS-00000000")
                    {
                        if (rs.data.ketQua != 1)
                        {
                            this.$root.toastError(rs.data.msgError)
                        }
                        else
                        {
                            this.khaoSatMoiDone = true
                            this.$root.toastSuccess('Điều lại thành công!')
                            this.closeDialog()
                        }
                    }
                    else {
                        this.$root.toastError('Điều lại thất bại!')
                    }
                }
            } catch (e) {
              console.log(e)
            }
            finally {
              this.$root.showLoading(false)
            }
        },
        async onDieuBaoDuongConfirmed() {
            try {
                this.$root.showLoading(true)
                if (await this.kiemTraDuLieu(true)) {
                    let data = this.taoDuLieu(true, true)
                    let rs = await this.$root.context.post('/web-cskh/api/khao-sat-kh-kll/dieu-bao-duong', data)
                    if (rs.error_code === "BSS-00000000")
                    {
                        if (rs.data.ketQua != 1)
                        {
                            this.$root.toastError('Có lỗi!' + rs.data.msgError)
                        }
                        else
                        {
                            this.khaoSatMoiDone = true
                            this.$root.toastSuccess('Điều bảo dưỡng thành công!')
                            this.closeDialog()
                        }
                    }
                    else {
                        this.$root.toastError('Điều bảo dưỡng thất bại ' + rs.message)
                    }
                }
            } catch (e) {
              console.log(e)
               this.$root.toastError('Điều bảo dưỡng thất bại ' + e.data.message)
            }
            finally {
              this.$root.showLoading(false)
            }
        },
        isNumeric(input) {
            return /^\d+$/.test(input);
        },
        onLichSuClicked(){
           this.$bvModal.show("modal-tracuu-baohong");
        },
    },
    watch: {
        pRdoCauHoiChaId: {
            handler: async function(newValue) {
                await this.getDsCauHoiPhu();
            }
        }
    }
};
</script>
