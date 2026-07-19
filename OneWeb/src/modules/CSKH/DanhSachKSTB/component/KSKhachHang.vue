<template>
<ejs-dialog
        :enableResize="true"
        :allowDragging="true"
        ref="popupKSKhachHang"
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
                    <a href="javascript: void(0)" @click="onGhiLaiClicked">
                        <span class="icon one-save"></span>Ghi lại
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)" @click="onDieuLaiClicked">
                        <span
                            class="icon nc-icon-glyph arrows-1_curved-next"
                        ></span
                        >Điều lại
                    </a>
                    <confirmPopup :content="xacNhanDieuLai" idModal='popup-confirmDieuLai' @onOk="onDieuLaiConfirmed"/>
                </li>
                <li>
                    <a href="javascript: void(0)">
                        <span class="icon one-history"></span>Lịch sử hỏng
                    </a>
                </li>
                <li>
                    <a href="javascript: void(0)">
                        <span class="icon one-circle-question"></span>Trợ giúp
                    </a>
                </li>
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
                                       <ejs-datepicker v-model="pDtpTuNgay" :value="today" :max="today" :format="'dd/MM/y'" :allowEdit="true" :enabled="pCboNgayHen">
                                        </ejs-datepicker>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w40">Đến</div>
                                    <div class="value">
                                        <ejs-datepicker v-model="pDtpDenNgay" :value="today" :max="today" :format="'dd/MM/y'" :allowEdit="true" :enabled="pCboNgayHen">
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
                                    <span class="name">Không trả lời</span>
                                </div>
                            </div>
                            <div class="pull-right">
                                <div class="select-custom">
                                    <b-form-select
                                        v-model="optLyDoKoTraLoiSelected"
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
                        <div class="table-content" style="height: 326px">
                            <table-c
                                v-bind:columns="[
                                    {
                                        fieldName: 'NGUOI_HOI',
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
                                        fieldName: 'TRANGTHAI',
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
                            ></table-c>
                        </div>
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
                    />
                </div>
            </div>
            <div class="box-form">
                <div class="legend-title">Danh sách câu hỏi</div>
                <div
                    class="box-quest marb20"
                    v-for="(cauHoi, index) in pKhaoSat.dsCauHoiChinh"
                    :key="index"
                >
                    <div class="title-bg-main">
                        <span class="title underline"
                            >Câu hỏi {{ index + 1 }}</span
                        >
                    </div>
                    <div class="desc marb10">
                        {{ cauHoi.TENCAUHOI }}
                    </div>
                    <div class="list-aw">
                        <div
                            class="item padt5 padb5"
                            v-for="(cauHoiPhu, indexPhu) in cauHoi.cauHoiPhu"
                            :key="cauHoiPhu.RCH_ID1"
                        >
                            <div class="check-action">
                                <input
                                    :type="
                                        cauHoiPhu.LOAICH_ID == 1
                                            ? 'radio'
                                            : 'checkbox'
                                    "
                                    class="check"
                                    :name="cauHoi.CAUHOI_ID"
                                    :checked="cauHoiPhu.Checked"
                                    @change="
                                        onTraLoiChecked(
                                            index,
                                            indexPhu,
                                            cauHoi.LOAICH_ID == 1
                                        )
                                    "
                                />
                                <span class="name">{{
                                    cauHoiPhu.NOIDUNG_TL
                                }}</span>
                            </div>
                            <input
                                type="text"
                                class="form-control"
                                v-if="cauHoiPhu.Checked && cauHoiPhu.LOAI == 1"
                                v-model="cauHoiPhu.Text"
                            />
                        </div>
                    </div>
                    <template v-for="(cauHoiPhu, indexPhu) in cauHoi.cauHoiPhu">
                        <div
                            class="box-quest box-form marb20 marl20"
                            style="background: #fff9eb"
                            v-show="
                                cauHoiPhu.Checked &&
                                    cauHoiPhu.dsCauHoiChau &&
                                    cauHoiPhu.dsCauHoiChau.length > 0
                            "
                            :key="cauHoiPhu.RCH_ID1"
                        >
                            <div class="title-bg-main">
                                <span class="title underline">Câu hỏi phụ</span>
                            </div>
                            <div class="desc marb10">
                                <p>{{ cauHoiPhu.NOIDUNG_TL }}</p>
                            </div>
                            <div class="list-aw">
                                <div
                                    class="item padt5 padb5"
                                    v-for="(cauHoiChau,
                                    indexChau) in cauHoiPhu.dsCauHoiChau"
                                    :key="cauHoiChau.TRALOI_ID"
                                >
                                    <div class="check-action">
                                        <input
                                            :type="
                                                cauHoiChau.LOAICH_ID == 1
                                                    ? 'radio'
                                                    : 'checkbox'
                                            "
                                            class="check"
                                            :name="cauHoiPhu.RCH_ID1"
                                            :checked="cauHoiChau.Checked"
                                            @change="
                                                onTraLoiCauHoiPhuChecked(
                                                    index,
                                                    indexPhu,
                                                    indexChau
                                                )
                                            "
                                        />
                                        <span class="name">{{
                                            cauHoiChau.NOIDUNG_TL
                                        }}</span>
                                    </div>
                                    <input
                                        type="text"
                                        class="form-control"
                                        v-if="
                                            cauHoiChau.Checked &&
                                                cauHoiChau.LOAI == 1
                                        "
                                        v-model="cauHoiChau.Text"
                                    />
                                </div>
                            </div>
                        </div>
                    </template>
                </div>
                <!-- <div>
          <div class="title-bg-main">
            <span class="title">Câu hỏi 1:</span>
          </div>
          <div class="desc marb10">Lý do không phát sinh lưu lượng</div>
          <div class="list-checks-v">
            <template>
              <div
                class="item marb10"
                v-for="(cauHoi, index) in dsCauHoi"
                :key="cauHoi.CAUHOI_ID"
              >
                <div class="check-action">
                  <input
                    type="radio"
                    class="check"
                    name="cauHoiCha"
                    v-model="pRdoCauHoiChaId"
                    :value="cauHoi.CAUHOI_ID"
                  />
                  <span class="name">{{ cauHoi.TENCAUHOI }}</span>
                </div>
              </div>
            </template>
          </div>
        </div>
        <template>
          <div class="box-form" v-if="pRdoCauHoiChaId!=0">
            <div class="title-bg-main">
              <span class="title">Câu hỏi phụ:</span>
            </div>
            <div class="desc marb10">{{cauHoiPhuContent}}</div>
            <div class="list-checks-v">
              <div class="item marb10" v-for="(cauHoiPhu, index) in dsCauHoiPhu" :key="cauHoiPhu.RCH_ID">
                <div class="check-action">
                  <input :type="kieuCauHoiCha == 1 ? 'radio' : 'checkbox'" class="check" :name='pRdoCauHoiChaId'/>
                  <span class="name">{{cauHoiPhu.NOIDUNG_TL}}</span>
                </div>
              </div>
              
            </div>
          </div>
        </template> -->
            </div>
        </div>
        <footer class="footer">VNPT-IT &copy; 2021</footer>
    </div>
</ejs-dialog>
</template>
<script>
import API from "../../api/DanhSachKSTB5NgayKLLApi";
import YesNoModal from "../../utils/YesNoModal.vue";
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
        confirmPopup: YesNoModal
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
            today: new Date()
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
        xacNhanDieuLai() {
            return "Bạn có chắc chắn muốn điều hỏng thuê bao " + this.pTxtMaTB + " ?"
        }
    },
    async created() {
    },
    async mounted() {
        
    },
    methods: {
        async openDialog() {
            this.$refs.popupKSKhachHang.show()
            try {
                this.loading(true);
                this.pKhaoSat.dsCauHoiChinh = [];
                await Promise.all([
                    this.getDsLyDoKoTraLoi(),
                    this.getLichSuGoiChamSoc(),
                    this.getDsCauHoiTheoNV(),
                    this.layThongTinTBKoLuuLuong(),
                    this.layThongTinTBTraLoi()
                ]);
                this.processFakeData();
            }
            catch (e) {
                
            }
            finally {
                this.loading(false);
            }
        },

        closeDialog() {
            this.$refs.popupKSKhachHang.hide()
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
        async layThongTinTBTraLoi() {
            try {
                this.clearData();
                let response = await API.layThongTinTBTraLoi(this.axios, {
                    id: this.ctSuyHaoId,
                    loai: 5
                });
                if (response.data.error_code === "BSS-00000000") {
                    let dsThongTin = response.data.data.data || [];
                    if (dsThongTin.length > 0) {
                        this.pIsNew = false
                        this.pTxtTBTLId = dsThongTin[0].THUEBAO_ID;
                        this.pTxtTenNguoiTL = dsThongTin[0].NGUOI_TL;
                        this.pTxtSoLH = dsThongTin[0].DIENTHOAI_LH;
                        this.pTxtGhiChu = dsThongTin[0].GHICHU;
                        this.pCboTrangThai = dsThongTin[0].TRANGTHAI;
                        this.pTxtDienThoaiLH = dsThongTin[0].DIENTHOAI_LH;
                        this.pChkKTL = dsThongTin[0].TRANGTHAI == 0
                        if (dsThongTin[0].NGAYHEN_DEN) {
                            this.pCboNgayHen = true;
                            this.pDtpTuNgay = moment(dsThongTin[0].NGAYHEN_TU)
                            this.pDtpDenNgay = moment(dsThongTin[0].NGAYHEN_DEN)
                        }
                        else
                            this.pCboNgayHen = false
                        if (this.pChkKTL) {
                            this.pIsNew = true
                            this.pTxtTBTLId = 0
                        }
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
            if (!this.pTxtTenNguoiTL && this.optLyDoKoTraLoiSelected) {
                this.$root.toastError("Chưa nhập tên người trả lời !");
                this.focusInput('txtTenNguoiTL')
                return false;
            }
            if (!this.pTxtSoLH) {
                this.$root.toastError("Chưa nhập điện thoại liên hệ !");
                this.focusInput('txtSoLH')
                return false;
            }

            let BATBUOC_CHON_TRALOI_KLL = -1
            let dsTs = await this.getThamSoMacDinh('BATBUOC_CHON_TRALOI_KLL')
            if (dsTs && dsTs.length > 0) {
                BATBUOC_CHON_TRALOI_KLL = dsTs[0].TEN_TS
            }

            if (BATBUOC_CHON_TRALOI_KLL == 1 && this.getKetQua.length <= 0) {
                this.$root.toastError("Bạn phải chọn câu trả lời!");
                return false;
            }

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
                                    str += '<br/>' + ch.NOIDUNG
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
                                    str += '<br/>' + ch.NOIDUNG
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
        async onGhiLaiClicked() {
            try {
              this.$root.showLoading(false)
                if (this.pIsNew) {
                } else {
                    if (await this.kiemTraDuLieu(false)) {
                      console.log('aaaaa')
                      let data = this.taoDuLieu(false, false)
                      console.log(data)
                      let rs = await this.$root.context.post('/web-cskh/api/khao-sat-kh-kll/ghi-lai', data)
                      console.log(rs)
                      if (rs.error_code === "BSS-00000000")
                      {
                        if (rs.data.msgError)
                        {
                            this.$root.toastSuccess('Có lỗi!' + rs.data.msgError)
                        }
                        else
                            this.$root.toastSuccess('Cập nhật thành công!')
                      }
                      else {
                        this.$root.toastError('Cập nhật thất bại')
                      }
                    }
                }
            } catch (e) {
              console.log(e)
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
          console.log(kq)
            let data = {
                hinhThuc: this.hinhThuc,
                ctSuyHaoId: this.ctSuyHaoId,
                tbtlId: this.pTxtTBTLId,
                thueBaoId: 0,
                nguoiTl: this.pTxtTenNguoiTL,
                dienThoaiLh: this.pTxtSoLH,
                ghiChu: this.pTxtGhiChu,
                lyDoKtlId: this.optLyDoKoTraLoiSelected,
                gioHenTu: this.pCboNgayHen ? moment(this.pDtpTuNgay).format('dd/MM/yyyy') : '',
                gioHenDen: this.pCboNgayHen ? moment(this.pDtpDenNgay).format('dd/MM/yyyy') : '',
                dsCauHoiId: kq.map(x => x.CAUHOI_ID).join('#'),
                dsTraLoiId: kq.map(x => x.TRALOI_ID).join('#'),
                dsNoiDung: kq.map(x => x.pTxtGhiChu).join('#'),
                isCnSoLienHe: 1,
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
                this.$root.showLoading(false)
                if (await this.kiemTraDuLieu(true)) {
                    console.log('aaaaa')
                    let data = this.taoDuLieu(true, true)
                    console.log(data)
                    let rs = await this.$root.context.post('/web-cskh/api/khao-sat-kh-kll/dieu-lai', data)
                    console.log(rs)
                    if (rs.error_code === "BSS-00000000")
                    {
                    if (rs.data.msgError)
                    {
                        this.$root.toastSuccess('Có lỗi!' + rs.data.msgError)
                    }
                    else
                        this.$root.toastSuccess('Cập nhật thành công!')
                    }
                    else {
                        this.$root.toastError('Cập nhật thất bại')
                    }
                }
            } catch (e) {
              console.log(e)
            }
            finally {
              this.$root.showLoading(false)
            }
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
