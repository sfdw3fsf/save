<template>
  <div class="">
    <!-- action header -->
    <div class="list-action-top">
      <vue-bread-crumb :header="header" />
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnNhapMoi }"
              @click.prevent="nhapMoi"
            >
              <span class="icon one-file-plus"></span> Nhập mới (F5)
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnGhiLai }"
              @click.prevent="tsbtnGhiLai_Click"
            >
              <span class="icon one-save"></span>
              Ghi lại (F9)
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnHuy }"
              @click.prevent="huy"
            >
              <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnXoaHD }"
              @click.prevent="tsbtnXoa_Click"
            >
              <span class="icon one-trash"></span> Xóa HĐ (F8)
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnThanhToan }"
              @click.prevent="tsbtnThanhToan_Click"
            >
              <span class="icon nc-icon-glyph shopping_credit-card"></span>
              Thanh toán
            </a>
          </li>
          <li>
            <a href="#" :class="{ disabled: disabledBtnInPL }">
              <span class="icon one-print"></span> In PL
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnThemTB }"
              @click.prevent="tsbtnThemTB_Click"
            >
              <span class="icon one-mobile-plus"></span>Thêm TB
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnXoaTB }"
              @click.prevent="tsbtnXoaTB_Click"
            >
              <span class="icon one-phone-remove"></span>Xoá TB
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnHenLD }"
              @click.prevent="tsbtnHen_Click"
            >
              <span class="icon one-calendar-check"></span>Hẹn LĐ
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnLienHe }"
              @click.prevent="btn_Lien_He"
            >
              <span class="icon nc-icon-glyph business_contacts"></span>Liên hệ
            </a>
          </li>
          <li>
            <a
              href="#"
              :class="{ disabled: disabledBtnEmail }"
              @click.prevent="btn_Email"
            >
              <span class="icon one-email"></span>Email
            </a>
          </li>
        </ul>
      </div>
    </div>

    <!-- content page -->
    <div class="page-content">
      <!-- content 1 -->
      <div class="box-form">
        <div class="row">
          <div class="col-sm-6 col-12"></div>
          <div class="col-sm-2 col-12">
            <div class="info-row marb0">
              <div class="key nowrap w60">
                <div class="check-action">
                  <input
                    type="checkbox"
                    class="check"
                    v-model="checkBoxCTV"
                    @change="chkCTVChange"
                  />
                  <span class="name">Cộng tác viên</span>
                </div>
              </div>
              <div class="value">
                <input
                  type="text"
                  class="form-control"
                  v-model="inputCTV"
                  :disabled="true"
                />
              </div>
            </div>
          </div>
          <div class="col-sm-2 col-12">
            <div class="info-row marb0">
              <div class="key nowrap w60">
                <div class="check-action">
                  <input
                    type="checkbox"
                    class="check"
                    v-model="checkBoxNguoiGT"
                    @change="chkNguoiGTChange"
                  />
                  <span class="name">Người giới thiệu</span>
                </div>
              </div>
              <div class="value">
                <input
                  type="text"
                  class="form-control"
                  v-model="inputNguoiGT"
                  :disabled="true"
                />
              </div>
            </div>
          </div>
          <div class="col-sm-2 col-12">
            <div class="info-row marb0">
              <div class="key nowrap w60">
                Tổng tiền
              </div>
              <div class="value">
                <input
                  type="text"
                  class="form-control"
                  v-model="tongTien"
                  :disabled="true"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- content 2 -->
      <div class="box-form">
        <!-- Thông tin phiếu yêu cầu -->
        <div class="legend-title">
          Thông tin phiếu yêu cầu
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="row">
              <div class="col-sm-7 col-12">
                <div class="info-row">
                  <div class="key w100">Mã giao dịch</div>
                  <div class="value">
                    <div class="input-more-button">
                      <button class="btn" @click="showPopupSearchContract">
                        <span class="-ap icon-more_horiz"></span>
                      </button>
                      <input
                        type="text"
                        class="form-control highlight"
                        v-model="maGiaoDich"
                        v-on:keyup.enter="enterClickedMaGiaoDich"
                      />
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Mã hợp đồng</div>
                  <div class="value">
                    <input
                      type="text"
                      class="form-control"
                      v-model="maHopDong"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-5 col-12">
                <div class="info-row">
                  <div class="key w100">Ngày yêu cầu</div>
                  <div class="value">
                    <div class="input-icon-right">
                      <input
                        type="text"
                        class="form-control"
                        v-model="ngayYeuCau"
                      />
                      <span class="icon one-calendar"></span>
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Ngày lập HĐ</div>
                  <div class="value">
                    <div class="input-icon-right">
                      <input
                        type="text"
                        class="form-control"
                        v-model="ngayLapHD"
                      />
                      <span class="icon one-calendar"></span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="row">
              <div class="col-sm-7 col-12">
                <div class="info-row">
                  <div class="select-custom">
                    <vue-select
                      v-model="selectedDichVu"
                      :labelWidth="'110'"
                      :options="dichVuList"
                      labelField="TEN_DVVT"
                      valueField="DICHVUVT_ID"
                      label="Dịch vụ"
                      @input="cboDichVuVT_SelectedValueChanged"
                    ></vue-select>
                  </div>
                </div>
                <div class="info-row">
                  <div class="select-custom">
                    <vue-select
                      v-model="selectedKieuYeuCau"
                      :labelWidth="'110'"
                      :options="dataKieuYeuCauList"
                      labelField="ten_kieuld"
                      valueField="kieuld_id"
                      label="Kiểu yêu cầu"
                      @input="changeKieuYeuCau"
                    ></vue-select>
                  </div>
                </div>
              </div>
              <div class="col-sm-5 col-12">
                <div class="info-row">
                  <div class="select-custom">
                    <vue-select
                      v-model="selectedLoaiHinh"
                      :labelWidth="'140'"
                      :options="loaiHinhListByFilter"
                      labelField="LOAIHINH_TB"
                      valueField="LOAITB_ID"
                      label="Loại hình"
                      @input="changeLoaiHinh"
                    ></vue-select>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w140">Mã HĐ - TTKD</div>
                  <div class="value">
                    <input
                      type="text"
                      class="form-control"
                      v-model="maHDTTKD"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <!-- Thông tin thuê bao -->
          <div class="col-sm-6 col-12">
            <div class="legend-title mart20">Thông tin thuê bao</div>
            <div class="row">
              <div class="col-sm-7 col-12">
                <div class="info-row">
                  <div class="key w100">Số máy/Acc</div>
                  <div class="value">
                    <div class="input-more-button">
                      <button class="btn" @click="btnSearchAccountClick">
                        <span class="-ap icon-more_horiz"></span>
                      </button>

                      <input
                        type="text"
                        class="form-control highlight"
                        v-model="soMayAcc"
                        v-on:keyup.enter="txtMaTB_KeyPress"
                        ref="txtMaTB"
                      />
                    </div>
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Hướng kết nối</div>
                  <div class="value">
                    <input
                      type="text"
                      class="form-control"
                      v-model="huongKetNoi"
                    />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Tên thuê bao</div>
                  <div class="value">
                    <input
                      type="text"
                      class="form-control"
                      v-model="tenThueBao"
                    />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Địa chỉ TB</div>
                  <div class="value">
                    <input
                      type="text"
                      class="form-control"
                      v-model="diaChiTB"
                    />
                  </div>
                </div>
              </div>
              <div class="col-sm-5 col-12">
                <div class="info-row">
                  <div class="key w100">Số ảo</div>
                  <div class="value">
                    <input
                      type="text"
                      class="form-control highlight"
                      v-model="soAo"
                    />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Trạng thái TB</div>
                  <div class="value">
                    <input
                      type="text"
                      class="form-control"
                      v-model="trangThaiTB"
                    />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Mã ERP</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="maERP" />
                  </div>
                </div>
                <div class="info-row">
                  <div class="key w100">Mã BSS</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="maBSS" />
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Địa chỉ LĐ</div>
              <div class="value">
                <input type="text" class="form-control" v-model="diaChiLD" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Địa chỉ LĐ đầu</div>
              <div class="value">
                <input type="text" class="form-control" v-model="diaChiLDDau" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Địa chỉ LĐ cuối</div>
              <div class="value">
                <input
                  type="text"
                  class="form-control"
                  v-model="diaChiLDCuoi"
                />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Ghi chú</div>
              <div class="value">
                <input type="text" class="form-control" v-model="ghiChu" />
              </div>
            </div>
          </div>

          <!-- Thông tin khách hàng -->
          <div class="col-sm-6 col-12">
            <div class="legend-title mart20">Thông tin khách hàng</div>
            <div class="row">
              <div class="col-sm-7 col-12">
                <div class="info-row">
                  <div class="key">Mã khách hàng</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="maKH" />
                  </div>
                </div>
              </div>
              <div class="col-sm-5 col-12">
                <div class="info-row">
                  <div class="key w140">
                    <div class="check-action">
                      <input
                        type="checkbox"
                        class="check"
                        v-model="checkBoxNgayHetHan"
                      />
                      <span class="name">Ngày hết hạn TD</span>
                    </div>
                  </div>
                  <div class="value">
                    <div class="input-icon-right">
                      <input
                        type="text"
                        class="form-control"
                        v-model="ngayHetHan"
                      />
                      <span class="icon one-calendar"></span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key">Tên khách hàng</div>
              <div class="value">
                <input type="text" class="form-control" v-model="tenKH" />
              </div>
            </div>
            <div class="info-row">
              <div class="key">Địa chỉ KH</div>
              <div class="value">
                <input type="text" class="form-control" v-model="diaChiKH" />
              </div>
            </div>
            <div class="nav tabs tab-over mart20">
              <!-- <a href="#" class="active">Lý do huỷ</a>
              <a href="#">Dịch vụ khác</a> -->
            </div>
            <b-tabs content-class="mt-3">
              <b-tab title="Lý do hủy">
                <div class="tab-content">
                  <div class="input-more-button">
                    <button class="btn" @click="btnLyDoHuyClick">
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                    <ejs-listbox
                      :cssClass="'list-lydohuy'"
                      :dataSource="
                        dsDKHUY.map(r => ({ id: r.stt, text: r.lydohuy }))
                      "
                    ></ejs-listbox>
                  </div>
                </div>
              </b-tab>

              <b-tab title="Dịch vụ khác"
                ><div class="tab-content">
                  <div class="input-more-button">
                    <button class="btn" @click="btnDichVuKhacClick">
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                    <ejs-listbox
                      :cssClass="'list-dich-vu-khac'"
                      :dataSource="
                        dsDVKhac.map(r => ({ id: r.stt, text: r.noidung }))
                      "
                    ></ejs-listbox>
                  </div>
                </div>
              </b-tab>
            </b-tabs>

            <div class="legend-title mart20">Chi tiết tiền khoản mục</div>
            <div class="row">
              <div class="col-sm-7 col-12">
                <div class="info-row">
                  <div class="key">Tổng tiền</div>
                  <div class="value">
                    <div class="input-more-button">
                      <button class="btn" @click="btnTienKM_Click">
                        <span class="-ap icon-more_horiz"></span>
                      </button>
                      <input
                        type="text"
                        class="form-control tright red"
                        value="0"
                        v-model="tongTienKhoanMuc"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-5 col-12">
                <div class="info-row">
                  <div class="key w140">
                    Tổng VAT
                  </div>
                  <div class="value">
                    <input
                      type="text"
                      class="form-control tright red"
                      value="0"
                      v-model="tongVat"
                    />
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="box-form">
        <div class="row">
          <!-- DS thuê bao -->
          <div class="col-sm-2 col-12">
            <div class="legend-title">DS thuê bao</div>
            <vue-card>
              <vue-table
                :config="configDSThueBao"
                :options="dsThueBao"
                :loading="isLoadingDsThueBao"
                v-model="currentRecordThueBao"
              />
            </vue-card>
          </div>

          <!-- Dánh sách dịch vụ gia tăng -->
          <div class="col-sm-10 col-12">
            <danh-sach-dich-vu-tang
              @recordClick="recordClickDichVuTang"
              @dataEditCompleted="dataEditCompleted"
              @valueThuHoi="valueThuHoi"
            />
          </div>
        </div>
      </div>
    </div>

    <!-- dialog số máy acc -->
    <dialog-search-account
      ref="dlgSearchAccount"
      :isDialog="true"
      @onSelectCustomer="onSelectCustomer"
    />

    <!-- dialog mã giao dịch -->
    <search-contract-modal
      ref="popupSearchContract"
      @recordClick="recordClick"
      @accept="accept"
      @dataSelectMaGiaoDich="dataSelectMaGiaoDich"
    />

    <!-- dialog lý do hủy -->
    <dialog-ly-do-huy ref="dlgHuy" @onAccept="onDialogLyDoHuyAccepted" />

    <!-- dialog dịch vụ khác -->
    <DialogDichVuKhac
      ref="dlgDichVuKhac"
      @onAccept="onDialogDichVuKhacAccepted"
    />

    <!-- dialog tổng tiền -->
    <DialogTienKhoanMuc ref="dlgTienKhoanMuc" @onAccept="dlgTienKM_Close" />

    <!-- dialog người giới thiệu -->
    <DialogNguoiGioiThieu
      ref="dlgNguoiGioiThieu"
      @onAccept="dlgNguoiGioiThieu_Accept"
    />

    <!-- dialog cộng tác viên -->
    <DialogCongTacVien ref="dlgCongTacVien" @onAccept="dlgCongTacVien_Accept" />

    <!-- dialog hẹn ld -->
    <DialogThongTinHenKhachHang
      :statusPopupHenKH="statusPopupHenKH"
      @updateStatusPopup="updateStatusPopup"
    />

    <!-- dialog liên hệ -->
    <DialogThongTinLienHe ref="dlgLienHe" />

    <!-- dialog Email -->
    <DialogThongTinEmail ref="dlgEmail" />
  </div>
</template>
<script>
import { mapActions, mapState, mapGetters } from "vuex";
import moment from "moment";
import SearchContractModal from "./popup/SearchContract/SearchContractModal.vue";
import DialogSearchAccount from "./popup/SoMayAcc/DialogSearchAccount.vue";
import DialogLyDoHuy from "./popup/LyDoHuy/DialogLyDoHuy.vue";
import DialogDichVuKhac from "./popup/DichVuKhac/DialogLyDichVuKhac.vue";
import DialogTienKhoanMuc from "../RestoreLiquidationSubs/components/popupTienKM.vue";
import DialogNguoiGioiThieu from "./popup/NguoiGioiThieu/dialogNguoiGioiThieu.vue";
import DialogCongTacVien from "./popup/CongTacVien/DialogCongTacVien.vue";
import DialogThongTinHenKhachHang from "./ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue";
import DialogThongTinLienHe from "./popup/LienHe/DialogLienHe.vue";
import DialogThongTinEmail from "./popup/Email/DialogEmail.vue";

import {
  LOAI_HOPDONG,
  TRANGTHAI_HOPDONG,
  LOAI_DONVI,
  DUNG_THU,
  DICHVU_VIENTHONG,
  KHOANMUC_TT,
  LOAIHINH_THUEBAO,
  TRANGTHAI_TB,
  TRANGTHAI_DONGBO,
  KieuCaiDatDVGT,
  KIEU_LAPDAT
} from "../../../../constants";
import { DichVuGiaTang } from "./constants";
import { TrangThaiTB } from "../../../../const/enums";
import { formatDate, formatCurrencyNoVND } from "@/utils/format";

export default {
  name: "ThayDoiDichVu",
  components: {
    DanhSachDichVuTang: () => import("./part/DanhSachDichVuTang.vue"),
    SearchContractModal,
    DialogSearchAccount,
    DialogLyDoHuy,
    DialogDichVuKhac,
    DialogTienKhoanMuc,
    DialogNguoiGioiThieu,
    DialogCongTacVien,
    DialogThongTinHenKhachHang,
    DialogThongTinLienHe,
    DialogThongTinEmail,
    DanhSachThueBao: () => import("./part/DanhSachThueBao.vue")
  },

  data() {
    return {
      /* data header action */
      header: {
        title: "THAY ĐỔI DỊCH VỤ",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Lắp đặt mới",
            link: { name: "Ui.buttons" }
          }
        ]
      },

      isShowDialogMaGiaoDich: false,

      // data form
      selectedDichVu: null,
      selectedLoaiHinh: null,
      selectedKieuYeuCau: null,

      checkBoxCTV: false,
      checkBoxNguoiGT: false,
      checkBoxNgayHetHan: false,

      inputCTV: "",
      inputNguoiGT: "",
      tongTien: 0,
      maGiaoDich: "",
      maHopDong: "",
      ngayYeuCau: "",
      ngayLapHD: "",
      maHDTTKD: "",
      soMayAcc: "",
      huongKetNoi: "",
      tenThueBao: "",
      diaChiTB: "",
      soAo: "",
      trangThaiTB: "",
      maERP: "",
      maBSS: "",
      diaChiLD: "",
      diaChiLDDau: "",
      diaChiLDCuoi: "",
      ghiChu: "",
      maKH: "",
      ngayHetHan: "",
      tenKH: "",
      diaChiKH: "",
      tongTienKhoanMuc: 0,
      tongVat: 0,

      // status disable
      disabledBtnNhapMoi: true,
      disabledBtnGhiLai: false,
      disabledBtnHuy: true,
      disabledBtnXoaHD: true,
      disabledBtnThanhToan: true,
      disabledBtnInPL: false,
      disabledBtnThemTB: true,
      disabledBtnXoaTB: true,
      disabledBtnHenLD: false,
      disabledBtnLienHe: false,
      disabledBtnEmail: false,

      tagForm: 1,
      loaiHinhListByFilter: [],
      kieuYeuCauListFilterByLoaiHdId: [],

      dataSearchMaGiaoDichById: null,
      dataSearchMaGiaoDichByRow: null,

      dataSearchSoMayAccById: null,
      dataSearchSoMayAccByRow: null,

      currentRecordThueBao: null,
      configDSThueBao: [
        {
          label: "Số máy/Acc",
          filterAble: true,
          key: "ma_tb"
        }
      ],
      isLoadingDsThueBao: false,
      current_time: "",
      hdtbId: 0,

      dsDKHUY: [],
      dsDVKhac: [],
      ds_tien_khoanmuc: [],

      dataInforMaGiaoDDich: {},
      dataInforSoMayAcc: {},

      dataKieuYeuCauList: [],

      isActiveClickMaGiaoDich: false,
      isActiveClickSoMayAcc: false,

      dataDangKy: {},
      arrayItemDaDangKy: [],
      statusPopupHenKH: false,
      dataGhiLai: {},
      valueThuHoiThietBi: false,

      themMoi: 1,
      hdkh_id_themMoi: "",
      hdtb_id_themMoi: "",
      maGD_themMoi: ""
    };
  },

  async created() {
    let current_time = moment()
      .format("DD-MM-YYYY")
      .toString();

    this.current_time = current_time;
    this.ngayYeuCau = this.current_time;
    this.ngayLapHD = this.current_time;

    await this.resetDataDefault();
    await this.fetchDanhSachThueBao();

    this.$route.name === "changeService"
      ? (this.tagForm = "1")
      : (this.tagForm = "");

    if (this.$route.query && this.$route.query.hdkh_cha_id) {
      this.loadTuHopDongTuVan(this.$route.query.hdkh_cha_id);
    }
  },

  computed: {
    ...mapGetters("mapChangeService", [
      "dichVuList",
      "loaiHinhList",
      "kieuYeuCauList",
      "dsThueBao",
      "dsSoMayAcc",
      "dsMaGiaoDich",
      "dsTongTienKhoanMuc",
      "kieuYeuCauListLD_BC",
      "dsDichVuGiaTang"
    ])
  },
  methods: {
    ...mapActions("mapChangeService", [
      "fetchDichVuList",
      "fetchLoaiHinhList",
      "fetchYeuCauList",
      "fetchDanhSachThueBao",
      "Lay_ds_tb_dvgt_theo_tt_ims_v2",
      "LAY_DS_THUEBAO_DICHVU_IMS_V2",
      "LAY_DS_THUEBAO_DICHVU_LINHVUC_V2",
      "LAY_DS_THUEBAO_DICHVU_V2",
      "lay_ds_thuebao_dichvu_v2",
      "clickSoMaAcc",
      "clickMaGiaoDich",
      "getTongTienKhoanMucList",
      "deleteHD",
      "fetchKieuYeuCauListLD_BC",
      "xoaHopDongTB",
      "ghiLaiHopDongTB",
      "mapIDDuLieuHDKH",
      "LAY_DS_DB_MEGAWAN_THEO_TBID",
      "LAY_DS_DB_TSL_THEO_TBID",
      "LAY_DS_HDTB_DV",
      "LAY_DS_HDTB_DV",
      "LAY_DS_DB_ADSL_THEO_TBID",
      "LAY_DS_DB_IMS_THEO_TBID",
      "LAY_DS_DB_GP_THEO_TBID",
      "LAY_DS_DB_CNTT_THEO_TBID",
      "LAY_DS_DB_LYDOHUY_THEO_TBID",
      "MAP_ID",
      "Kiemtra_no_thanhly_yc",
      "Ktra_DK_SD_GreenOnline",
      "LayHuongGiao_TiepNhan",
      "themMoiTB",
      "LAY_DANHBA_THEO_MATB",
      "getKeys",
      "Lay_Ma_GD_V2",
      "LayHuongGiao_TiepNhan"
    ]),
    async loadTuHopDongTuVan(hdkh_cha_id) {
      // Lấy thông tin từ phiếu tư vấn
      // var d = bangts.GetDataSql(
      //   "select a.*, b.ma_tb, b.thuebao_id from " +
      //     DatabaseConstants.DB2 +
      //     ".hd_khachhang a, " +
      //     DatabaseConstants.DB2 +
      //     ".hd_thuebao b where a.hdkh_id = b.hdkh_id and a.hdkh_id =" +
      //     phdkh_cha_id
      // );
      // const dt = await api.lay_dulieu_theo_dieukien(
      //   "hd_thuebao",
      //   "ma_tb",
      //   "dichvuvt_id",
      //   "hdkh_id = " + hdkh_cha_id,
      //   ""
      // );
      // if (dt.length > 0) {
      //   this.requestFormInfo.dichVu = dt[0][1];
      //   this.subscriptionFormInfo.soMayAcc = dt[0][0];
      //   this.onMaTBKeyPress({ charCode: 13 });
      // } else {
      //   this.$toast.error("Khách hàng không có thuê bao yêu cầu thanh lý");
      // }
    },

    // click checkbox CTV
    chkCTVChange() {
      if (this.checkBoxCTV) {
        this.$refs.dlgCongTacVien.show(true, "ctv");
      } else {
        this.inputCTV = "";
      }
    },

    // click checkbox  Người giới thiệu
    chkNguoiGTChange() {
      if (this.checkBoxNguoiGT) {
        this.$refs.dlgNguoiGioiThieu.show(true, "gt");
      } else {
        this.inputNguoiGT = "";
      }
    },

    // click thu hồi
    valueThuHoi(val) {
      this.valueThuHoiThietBi = val;
    },

    // click nút chấp nhận trong dialog người giới thiệu
    dlgNguoiGioiThieu_Accept(data) {
      this.inputNguoiGT = data.data.ten_ht;
    },

    // click nút chấp nhận trong dialog CTV
    dlgCongTacVien_Accept(data) {
      this.inputCTV = data.data.ten_ht;
    },

    // click enter mã giao dịch
    async enterClickedMaGiaoDich() {
      this.isActiveClickMaGiaoDich = true;
      this.$root.showLoading(true);
      const variables = {
        in_donvi_dl_id: 0,
        in_donvi_id: 0,
        in_loaihd_id: 7,
        in_ma_gd: this.maGiaoDich, //"HPG-BD/00999232",
        in_nhanvien_id: 0,
        in_tthd_id: 0
      };

      try {
        await this.clickMaGiaoDich(variables);
        if (this.dsMaGiaoDich.length) {
          this.fillDataSearchMaGiaoDich(this.dsMaGiaoDich[0], null);
          await this.napThongTinDichVuThueBao(this.dsMaGiaoDich[0]);
          this.dataInforMaGiaoDDich = this.dsMaGiaoDich[0];
        } else {
          this.$toast.error(
            `Không tìm thấy thông tin về hợp đồng có mã giao dịch ${this.maGiaoDich} đối với dịch vụ cố định`
          );
        }
      } catch (error) {
        this.$toast.error(
          `Không tìm thấy thông tin về hợp đồng có mã giao dịch ${this.maGiaoDich} đối với dịch vụ cố định`
        );
      }

      // this.dataInforMaGiaoDDich
      //   ? (this.disabledBtnXoaHD = false)
      //   : (this.disabledBtnXoaHD = true);

      this.setButton(4);

      this.$root.showLoading(false);
    },

    /* lý do là ko đủ thông tin cần thiết nên cần lấy gộ 2 data lại */
    // gắn data vào biến dataSearchMaGiaoDichById khi chọn dòng trong dialog mã giao dịch (chỉ chọn chưa bấm chấp nhận)
    dataSelectMaGiaoDich(data) {
      this.dataSearchMaGiaoDichById = data;
    },

    // gắn data vào biến dataSearchMaGiaoDichByRow khi nhấn chấp nhân trong dialog mã giao dịch
    async accept(data) {
      this.isActiveClickMaGiaoDich = true;
      this.dataInforMaGiaoDDich = this.dataSearchMaGiaoDichById;

      this.$root.showLoading(true);
      this.dataSearchMaGiaoDichByRow = data;

      this.fillDataSearchMaGiaoDich(
        this.dataSearchMaGiaoDichById,
        this.dataSearchMaGiaoDichByRow
      );

      this.dataInforMaGiaoDDich
        ? (this.disabledBtnXoaHD = false)
        : (this.disabledBtnXoaHD = true);

      this.$root.showLoading(false);
      this.setButton(4);
    },

    // show dialog search mã giao dịch
    showPopupSearchContract() {
      this.$refs.popupSearchContract.showModal();
    },

    // đổ data vào form khi search mã giao dich
    // (bao data khi click chọn row trong dialog và data khi click nút chấp nhận trong dialog)
    fillDataSearchMaGiaoDich(
      dataSearchMaGiaoDichById,
      dataSearchMaGiaoDichByRow
    ) {
      this.hdtbId = dataSearchMaGiaoDichById.hdtb_id
        ? dataSearchMaGiaoDichById.hdtb_id
        : null;
      this.maGiaoDich = dataSearchMaGiaoDichById.ma_gd;
      this.maHopDong = dataSearchMaGiaoDichByRow.ma_hd;
      this.ngayYeuCau = dataSearchMaGiaoDichByRow.ngay_yc;
      this.tongTien = dataSearchMaGiaoDichById.tongtien;

      this.ngayLapHD = dataSearchMaGiaoDichById.ngaylap_hd;
      this.selectedDichVu = dataSearchMaGiaoDichById.dichvuvt_id;
      this.selectedKieuYeuCau = dataSearchMaGiaoDichById.kieuld_id;
      this.selectedLoaiHinh = dataSearchMaGiaoDichById.loaitb_id;
      this.soMayAcc = dataSearchMaGiaoDichById.ma_tb;

      this.tenThueBao = dataSearchMaGiaoDichById.ten_tb;
      this.diaChiTB = dataSearchMaGiaoDichById.diachi_tb;
      this.trangThaiTB = dataSearchMaGiaoDichById.trangthai_hd;
      this.diaChiLD = dataSearchMaGiaoDichById.diachi_ld;
      this.ghiChu = dataSearchMaGiaoDichById.ghichu;
      this.maKH = dataSearchMaGiaoDichByRow
        ? dataSearchMaGiaoDichByRow.ma_kh
        : dataSearchMaGiaoDichById.ma_kh;
      this.ngayHetHan = dataSearchMaGiaoDichById.ngayhethan_gt;
      this.tenKH = dataSearchMaGiaoDichByRow
        ? dataSearchMaGiaoDichByRow.ten_kh
        : dataSearchMaGiaoDichById.ten_kh;
      this.diaChiKH = dataSearchMaGiaoDichByRow
        ? dataSearchMaGiaoDichByRow.diachi_kh
        : dataSearchMaGiaoDichById.diachi_kh;
    },

    // button click dialog search số máy Acc
    btnSearchAccountClick() {
      this.$refs.dlgSearchAccount.show(true);
    },

    // click enter số máy Acc
    async txtMaTB_KeyPress() {
      // this.disabledBtnGhiLai = false;
      // const variables = {
      //   dichVuVtId: this.selectedDichVu ? this.selectedDichVu : 0,
      //   donViDlId: 0,
      //   loaiHdId: 7,
      //   loaiTbId: this.selectedLoaiHinh ? this.selectedLoaiHinh : 0,
      //   maTb: this.soMayAcc,
      //   ttHdId: 1
      // };

      // if (this.soMayAcc) {
      //   try {
      //     await this.clickSoMaAcc(variables);

      //     if (this.dsSoMayAcc.length === 0) {
      //       this.dataInforSoMayAcc = null;
      //       this.HienThiTT_DanhBa();
      //     } else {
      //       this.disabledBtnThemTB = false;
      //       this.dataInforSoMayAcc = this.dsSoMayAcc[0];
      //       this.dataInforMaGiaoDDich = this.dsSoMayAcc[0];
      //       this.fillDataSearchSoMayAcc(this.dsSoMayAcc[0]);
      //       await this.napThongTinDichVuThueBao(this.dsSoMayAcc[0]);
      //       this.setButton(3);
      //     }
      //   } catch (error) {
      //     this.$toast.error(error);
      //   }
      // } else {
      //   this.$toast.error("Bạn chưa nhập mã thuê bao!");
      // }

      // this.maGiaoDich = this.maGD_themMoi;

      this.setButton(4);
    },

    async HienThiTT_DanhBa() {
      try {
        const variables = {
          maTb: this.soMayAcc,
          dichVuVtId: this.selectedDichVu,
          donViDlId: null
        };
        const res = await this.LAY_DANHBA_THEO_MATB(variables);
        this.dataInforMaGiaoDDich = res;
        if (res && Object.keys(res).length > 0) {
          this.maHopDong = res.ma_hd;
          this.ngayLapHD = res.ngaylap_hd ? res.ngaylap_hd : this.current_time;
          this.trangThaiTB = res.trangthai_tb;
          this.tenThueBao = res.ten_tb;
          this.diaChiTB = res.diachi_tb;
          this.diaChiLD = res.diachi_ld;
          this.ghiChu = res.ghichu_tb;
          this.maKH = res.ma_kh;
          this.tenKH = res.ten_kh;
          this.ngayHetHan = res.ngayhethan_gt;
          this.diaChiKH = res.diachi_kh;

          if (res.ky_hd === 0) {
            this.$toast.error(
              `Khách hàng có mã thuê bao: ${this.soMayAcc} chưa ký lại hợp đồng gốc. Đề nghị ký lại hợp đồng gốc`
            );
          }

          if (res.trangthaitb_id === TRANGTHAI_TB.THANHLY) {
            this.$toast.error(
              `Thuê bao ${this.soMayAcc} đang ở trạng thái thanh lý Không thể thực hiện thay đổi dịch vụ cho thuê bao này!`
            );
          }

          if (res.trangthaitb_id === TRANGTHAI_TB.THANHLY_NO) {
            this.$toast.error(
              `Thuê bao ${this.soMayAcc} đang ở trạng thái thanh lý nợ Không thể thực hiện thay đổi dịch vụ cho thuê bao này!`
            );
          }

          if (res.trangthaitb_id === TRANGTHAI_TB.DOISO) {
            this.$toast.error(
              `Thuê bao ${this.soMayAcc} đang ở trạng thái đổi số Không thể thực hiện thay đổi dịch vụ cho thuê bao này!`
            );
          }

          if (
            res.trangthaitb_id === TRANGTHAI_TB.KHOA_HAICHIEU_CUONGBUC &&
            this.selectedDichVu === DICHVU_VIENTHONG.ADSL
          ) {
            this.$toast.error(
              `Thuê bao ${this.soMayAcc} đang ở trạng thái khóa do nợ cước. Không thể thực hiện thay đổi dịch vụ cho thuê bao này!`
            );
          }
        } else {
          const objDV = this.dichVuList.find(
            item => item.DICHVUVT_ID === this.selectedDichVu
          );
          this.$toast.error(
            `Không tìm thấy thông tin thuê bao : ${this.soMayAcc} đối với dịch vụ ${objDV.TEN_DVVT}`
          );
        }
      } catch (error) {
        this.$toast.error(error);
      }
    },

    // đổ data vào form khi nhập/chấp nhận từ dialog so may acc
    async fillDataSearchSoMayAcc(data) {
      this.maGiaoDich = data.ma_gd;
      this.maHopDong = data.ma_hd;
      this.ngayYeuCau = data.ngay_yc;
      this.ngayLapHD = data.ngaylap_hd ? data.ngaylap_hd : this.current_time;
      this.selectedDichVu = data.dichvuvt_id;
      this.selectedKieuYeuCau = data.kieuld_id;
      this.selectedLoaiHinh = data.loaitb_id;

      this.tongTien = data.tongtien;
      this.soMayAcc = data.ma_tb;
      this.tenThueBao = data.ten_tb;
      this.diaChiTB = data.diachi_tb;
      this.diaChiLD = data.diachi_ld;
      this.ghiChu = data.ghichu;
      this.trangThaiTB = data.trangthai_hd;

      this.maKH = data.ma_kh;
      this.tenKH = data.ten_kh;
      this.diaChiKH = data.diachi_kh;
      this.ngayHetHan = data.ngayhethan_gt;

      this.setButton(4);
    },

    // click btn hiện dialog tổng tiền
    async btnTienKM_Click() {
      const variables = {
        loaihd_id: 7
      };
      await this.getTongTienKhoanMucList(variables);
      this.ds_tien_khoanmuc = this.dsTongTienKhoanMuc;
      this.$refs.dlgTienKhoanMuc.show(true, {
        ds: this.ds_tien_khoanmuc,
        cols: [
          {
            fieldName: "khoanmuctt_id",
            headerText: "Id",
            isPrimaryKey: true,
            visible: false
          },
          {
            fieldName: "tien",
            headerText: "Tiền",
            allowEditing: true,
            editType: "numericedit"
          },
          {
            fieldName: "vat",
            headerText: "VAT",
            allowEditing: true,
            editType: "numericedit"
          }
        ],
        tieude: "Chi tiết tiền khoản mục",
        tieudebang: "Chi tiết khoản mục thanh toán"
      });
    },

    // click nút chấp nhận trong dialog tổng tiền
    dlgTienKM_Close(data) {
      this.ds_tien_khoanmuc = data.map(r => ({
        ...r,
        khoanmuctt_id: parseInt(r.khoanmuctt_id)
      }));
      if (this.ds_tien_khoanmuc.length > 0) {
        for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          if (
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"] ==
            KHOANMUC_TT.KMTT_KHOIPHUCSD
          ) {
            this.dtienld = parseFloat(this.ds_tien_khoanmuc[i]["tien"]);
            this.dvatld = parseFloat(this.ds_tien_khoanmuc[i]["vat"]);
          }

          if (
            this.ds_tien_khoanmuc[i]["khoanmuctt_id"] == KHOANMUC_TT.KMTT_DICHVU
          ) {
            this.dtien_dv = parseFloat(this.ds_tien_khoanmuc[i]["tien"]);
            this.dvat_dv = parseFloat(this.ds_tien_khoanmuc[i]["vat"]);
          }
        }
        this.LayTongTien_HDTB();
      }
    },

    // lấy tổng tiền
    LayTongTien_HDTB() {
      let dtongtien = 0;
      let dtongvat = 0;
      try {
        for (let i = 0; i < this.ds_tien_khoanmuc.length; i++) {
          dtongtien += parseInt(this.ds_tien_khoanmuc[i].tien);
          dtongvat += parseInt(this.ds_tien_khoanmuc[i].vat);
        }
        this.tongTienKhoanMuc = dtongtien;
        this.tongVat = dtongvat;
      } catch (err) {
        this.$toast.error(err);
      }
    },

    // click để mở dialog Lý do hủy
    btnLyDoHuyClick() {
      this.$refs.dlgHuy.show(true);
    },

    // click để mở dialog Dịch vụ khác
    btnDichVuKhacClick() {
      this.$refs.dlgDichVuKhac.show(true);
    },

    // click nút liên hệ
    btn_Lien_He() {
      this.$refs.dlgLienHe.show(true);
    },

    //click nút email
    btn_Email() {
      this.$refs.dlgEmail.show(true);
    },

    // khi nhấn chấp nhận trong dialog lý do hủy
    onDialogLyDoHuyAccepted(data) {
      this.dsDKHUY = data;
      let lsTmp = [];
      for (const item of data) {
        if (
          !this.dsDKHUY.find(
            i => i.lydohuy === item.lydohuy && i.noidung === item.noidung
          )
        ) {
          lsTmp.push(item);
        }
      }
      this.dsDKHUY = [...this.dsDKHUY, ...lsTmp];
    },

    // khi nhấn chấp nhận trong dialog dịch vụ khác
    onDialogDichVuKhacAccepted(data) {
      let lsTmp = [];
      for (const item of data) {
        if (
          !this.dsDVKhac.find(
            i => i.lydohuy === item.lydohuy && i.noidung === item.noidung
          )
        ) {
          lsTmp.push(item);
        }
      }
      this.dsDVKhac = [...this.dsDVKhac, ...lsTmp];
    },

    // hàm nạp thông tin (để load lại danh sách dịch vụ gia tăng)
    async napThongTinDichVuThueBao(data) {
      if (data !== null) {
        if (this.selectedDichVu === DICHVU_VIENTHONG.IMS) {
          if (this.tagForm === 1) {
            const variables = {
              hdtb_id: data.hdtb_id,
              thuebao_id: data.thuebao_id,
              loaitb_id: this.selectedLoaiHinh,
              kieu_id: data.kieutn_id,
              trangthaitb_id: data.tthd_id,
              kieuld_id: this.selectedKieuYeuCau
            };
            await this.Lay_ds_tb_dvgt_theo_tt_ims_v2(variables);
          } else {
            const variables = {
              hdtb_id: data.hdtb_id,
              thuebao_id: data.thuebao_id,
              loaitb_id: this.selectedLoaiHinh,
              kieu_id: data.kieutn_id ? data.kieutn_id : 0,
              kieuld_id: this.selectedKieuYeuCau
            };
            await this.LAY_DS_THUEBAO_DICHVU_IMS_V2(variables);
          }
        } else if (
          this.selectedDichVu === DICHVU_VIENTHONG.TRUNGTAM_DULIEU ||
          this.selectedDichVu === DICHVU_VIENTHONG.ANTOAN_BAOMAT_TT ||
          this.selectedDichVu === DICHVU_VIENTHONG.DICHVU_CNTT ||
          this.selectedDichVu === DICHVU_VIENTHONG.HOINGHI_TRUYENHINH
        ) {
          if (this.selectedLoaiHinh === LOAIHINH_THUEBAO.BRANDNAME) {
            const variables = {
              hdtb_id: data.hdtb_id,
              thuebao_id: data.thuebao_id,
              loaitb_id: this.selectedLoaiHinh,
              kieu_id: data.kieutn_id ? data.kieutn_id : 0,
              kieuld_id: this.selectedKieuYeuCau,
              linhvuc_id: 0
            };
            await this.LAY_DS_THUEBAO_DICHVU_LINHVUC_V2(variables);
          } else {
            const variables = {
              hdtb_id: data.hdtb_id,
              thuebao_id: data.thuebao_id,
              loaitb_id: this.selectedLoaiHinh ? this.selectedLoaiHinh : 0,
              kieu_id: data.kieutn_id ? data.kieutn_id : 0,
              kieuld_id: this.selectedKieuYeuCau
            };
            await this.LAY_DS_THUEBAO_DICHVU_V2(variables);
          }
        } else {
          if (this.tagForm === 1) {
            const variables = {
              hdtb_id: data.hdtb_id ? data.hdtb_id : 0,
              thuebao_id: data.thuebao_id ? data.thuebao_id : 0,
              loaitb_id: this.selectedLoaiHinh ? this.selectedLoaiHinh : 0,
              kieu_id: data.kieutn_id ? data.kieutn_id : 0,
              kieuld_id: this.selectedKieuYeuCau
            };
            await this.lay_ds_thuebao_dichvu_v2(variables);
          } else {
            const variables = {
              hdtb_id: data.hdtb_id ? data.hdtb_id : 0,
              thuebao_id: data.thuebao_id ? data.thuebao_id : 0,
              loaitb_id: this.selectedLoaiHinh ? this.selectedLoaiHinh : 0,
              kieu_id: data.kieutn_id ? data.kieutn_id : 0,
              kieuld_id: this.selectedKieuYeuCau
            };
            await this.LAY_DS_THUEBAO_DICHVU_V2(variables);
          }
        }
      }

      if (this.soMayAcc && data === null) {
        const variables = {
          hdtb_id: 0,
          thuebao_id: 0,
          loaitb_id: this.selectedLoaiHinh ? this.selectedLoaiHinh : 0,
          kieu_id: 0,
          kieuld_id: this.selectedKieuYeuCau ? this.selectedKieuYeuCau : 0
        };
        await this.LAY_DS_THUEBAO_DICHVU_V2(variables);
        this.$root.showLoading(false);
        return;
      }

      if (!this.soMayAcc && data === null) {
        try {
          const variables = {
            hdtb_id: 0,
            thuebao_id: 0,
            loaitb_id: this.selectedLoaiHinh ? this.selectedLoaiHinh : 0,
            kieu_id: 0,
            kieuld_id: this.selectedKieuYeuCau ? this.selectedKieuYeuCau : 0
          };
          await this.LAY_DS_THUEBAO_DICHVU_V2(variables);
        } catch (error) {
          throw error;
        }
      }
    },

    // mapping data kiểu yêu cầu của api_35 (khi ko có loại tb id)
    mappingDataKieuYeuCau(data) {
      const res = data.map(item => {
        const list = {
          ten_kieuld: item.TEN_KIEULD,
          kieuld_id: item.KIEULD_ID
        };
        return list;
      });
      return res;
    },

    // filter load loại hinh khi mới load form
    async filterLoaiHinhList() {
      this.loaiHinhListByFilter = this.loaiHinhList.filter(
        item => item.DICHVUVT_ID === this.selectedDichVu
      );
      if (this.loaiHinhListByFilter.length) {
        const status = this.checkDichVuId(this.selectedDichVu);

        if (status) {
          this.filterLoaiHinhByDichVuID(this.selectedDichVu);
          const variables = {
            vkieu: 0,
            vloaihd_id: 7,
            vloaitb_id: this.selectedLoaiHinh
          };
          await this.fetchKieuYeuCauListLD_BC(variables);
          this.dataKieuYeuCauList = this.kieuYeuCauListLD_BC;
          if (this.dataKieuYeuCauList.length) {
            this.selectedKieuYeuCau = this.dataKieuYeuCauList[0].kieuld_id;
          } else {
            this.selectedKieuYeuCau = null;
          }
        } else {
          this.selectedLoaiHinh = this.loaiHinhListByFilter[0].LOAITB_ID;
        }
      } else {
        this.selectedLoaiHinh = null;
      }
    },

    // khi loaitb_id = 0 - api_035
    filterKieuYeuCauList() {
      this.kieuYeuCauListFilterByLoaiHdId = this.kieuYeuCauList.filter(
        item => item.LOAIHD_ID === LOAI_HOPDONG.THAY_DOI_DV
      );
      this.selectedKieuYeuCau = 23;
    },

    // chọn hiển thị loại hình theo các dịch vụ tương ứng
    filterLoaiHinhByDichVuID(dichVuId) {
      if (dichVuId === DICHVU_VIENTHONG.CO_DINH) {
        this.selectedLoaiHinh = LOAIHINH_THUEBAO.DIENTHOAI_CD;
        return;
      }
      if (dichVuId === DICHVU_VIENTHONG.ADSL) {
        this.selectedLoaiHinh = LOAIHINH_THUEBAO.MEGA;
        return;
      }

      if (dichVuId === DICHVU_VIENTHONG.TSL) {
        this.selectedLoaiHinh = LOAIHINH_THUEBAO.LEASEDLINE;
        return;
      }
      if (dichVuId === DICHVU_VIENTHONG.MEGAWAN) {
        this.selectedLoaiHinh = LOAIHINH_THUEBAO.MEGAWAN_ADSL;
        return;
      }
      if (dichVuId === DICHVU_VIENTHONG.DI_DONG) {
        this.selectedLoaiHinh = LOAIHINH_THUEBAO.DIDONGTRASAU;
        return;
      }
      if (dichVuId === DICHVU_VIENTHONG.MEGA_EYES) {
        this.selectedLoaiHinh = LOAIHINH_THUEBAO.MEGAEYES_CLOUD_CAMERA;
        return;
      }
    },

    // check xem dịch vụ id có nằm trong các list này ko để chọn loại hình id mặc định hiển thị đầu tương ứng.
    checkDichVuId(id) {
      const listDichVuId = [
        DICHVU_VIENTHONG.CO_DINH,
        DICHVU_VIENTHONG.ADSL,
        DICHVU_VIENTHONG.TSL,
        DICHVU_VIENTHONG.MEGAWAN,
        DICHVU_VIENTHONG.DI_DONG,
        DICHVU_VIENTHONG.MEGA_EYES
      ];
      const check = listDichVuId.includes(id);
      return check;
    },

    //  hàm load lại data khi thay đổi dịch vụ (vì thay đổi cả loại hình và kiểu yêu cầu) hoặc khi thay đổi loại hình, hoặc thay đổi kiểu yêu cầu
    async loadDataGridWhenChange() {
      this.$root.showLoading(true);
      try {
        // khi enter số máy
        if (
          this.soMayAcc &&
          this.dataInforSoMayAcc &&
          Object.keys(this.dataInforSoMayAcc).length > 0
        ) {
          await this.napThongTinDichVuThueBao(this.dataInforSoMayAcc);
        }

        // khi enter mã giao dịch
        if (
          this.dataInforMaGiaoDDich &&
          Object.keys(this.dataInforMaGiaoDDich).length > 0
        ) {
          await this.napThongTinDichVuThueBao(this.dataInforMaGiaoDDich);
        }
        // }
      } catch (error) {
        throw error;
      }
      this.$root.showLoading(false);
    },

    // event khi change dịch vụ
    async cboDichVuVT_SelectedValueChanged() {
      await this.loadDataGridWhenChange();
    },

    //event khi change loai hinh
    async changeLoaiHinh() {
      await this.loadDataGridWhenChange();
    },

    // event thay đổi kiểu yêu cầu
    async changeKieuYeuCau() {
      await this.loadDataGridWhenChange();
    },

    // data khi edit trên dòng xong => cộng tiền và thuế vat vs những row có đăng ký => cộng dồn in ra 2 ô tổng tiền và tổng vat
    dataEditCompleted(data) {
      this.dataDangKy = data;
      this.dataGhiLai = data;
      this.tongTienKhoanMuc = 0;
      this.tongVat = 0;

      if (data !== null && data !== undefined) {
        // xem data có thuộc mảng đki chưa

        const itemData =
          this.arrayItemDaDangKy.length !== 0
            ? this.arrayItemDaDangKy.find(
                item => item.ten_dvgt === data.ten_dvgt
              )
            : null;

        // nếu thuộc rồi
        if (itemData !== null && itemData !== undefined) {
          // nếu thuộc rồi mà hủy đăng kí
          if (!data.dangky) {
            this.arrayItemDaDangKy = this.arrayItemDaDangKy.filter(
              item => item.ten_dvgt != data.ten_dvgt
            );
          }

          // nếu thuộc rồi mà có đăng kí rồi chỉ update thông tin (tiền, vat, nội dung,...)
          if (data.dangky) {
            const itemIndex = this.arrayItemDaDangKy.findIndex(
              item => item.ten_dvgt === data.ten_dvgt
            );
            if (itemIndex !== -1) {
              this.arrayItemDaDangKy[itemIndex] = data;
            }
          }
        }
        // nếu chưa  thuộc
        else {
          // đăng ký push vào mảng
          if (data.dangky) {
            this.arrayItemDaDangKy.push(data);
          }
        }
      }

      // if (this.arrayItemDaDangKy.length) {
      //   for (let index = 0; index < this.arrayItemDaDangKy.length; index++) {
      //     const element = this.arrayItemDaDangKy[index];

      //     this.tongTienKhoanMuc += parseInt(element.tien.replace(/,/g, ""));
      //     this.tongVat += parseInt(element.vat_ld.replace(/,/g, ""));
      //   }
      // }
    },

    // action nút nhập mới
    async nhapMoi() {
      // ds_tien_khoanmuc
      const variables = {
        loaihd_id: 1
      };
      await this.getTongTienKhoanMucList(variables);
      this.ds_tien_khoanmuc = this.dsTongTienKhoanMuc;
      this.themMoi = 1;

      this.setButton(1);
    },

    // action nút hủy
    async huy() {
      await this.clearData();
      this.setButton(2);
    },

    // Hàm convert date thành dd/mm/yyyy

    // action nút thêm tb
    async tsbtnThemTB_Click() {
      if (this.themMoi === 0) {
        this.hdtb_id_themMoi = await this.getKeys({
          keyname: "HD_THUEBAO"
        });
      }

      let kt_chon_DVGT = false;
      console.log("dataGhiLai:", this.dataGhiLai);
      if (!this.dataGhiLai) {
        this.$toast.error(
          "Bạn chưa đăng ký/hủy dịch vụ gia tăng nào. Không thể cập nhật thông tin"
        );
        return;
      }

      if (this.dataGhiLai.dangky || this.dataGhiLai.huy) {
        kt_chon_DVGT = true;
      }

      if (kt_chon_DVGT === false) {
        this.$toast.error(
          "Bạn chưa đăng ký/hủy dịch vụ gia tăng nào. Không thể cập nhật thông tin"
        );
        return;
      }

      if (this.dsDichVuGiaTang.length === 0 || !this.dataGhiLai) return;
      let kt = false;

      const inputHuongTiepNhan = {
        loaiHdId: 7,
        loaiTbId: 1,
        tocDoId: 0,
        mucCuocId: 0,
        kieuLdId: this.selectedKieuYeuCau
      };
      const dsHuongTiepNhan = await this.LayHuongGiao_TiepNhan(
        inputHuongTiepNhan
      );

      const ds_isdn = this.TaoDuLieu_HDTB_ISDN(dsHuongTiepNhan);
      const dgvDVGT = this.TaoDuLieu_DVGT();
      const HDKH = await this.TaoDuLieu_HDKH();
      const HDTB = this.TaoDuLieu_HDTB(dsHuongTiepNhan);
      const HDTB_DV = await this.TaoDuLieu_HDTBDV();
      const dangky_dvgt = this.TaoDuLieu_HDTDDV();
      const CTTHD = await this.TaoDuLieu_CT_TIENHD();
      const HDTB_CD = await this.TaoDuLieu_HDTB_CD();
      const HDTB_ADSL = await this.TaoDuLieu_HDTB_ADSL();
      const HDTB_IMS = await this.TaoDuLieu_HDTB_IMS();
      const HDTB_GP = await this.TaoDuLieu_HDTB_GP();
      const HDTB_MGWAN = await this.TaoDuLieu_HDTB_MGWAN();
      const HDTB_TSL = await this.TaoDuLieu_HDTB_TSL();
      const HDTB_DD = await this.TaoDuLieu_HDTB_DD();
      const HDTB_CNTT = await this.TaoDuLieu_HDTB_CNTT();
      const BD_LYDOHUY = await this.TaoDuLieu_BD_LYDOHUY();

      const inputData = {
        kt: 0,
        thuebao_id: parseInt(this.dataInforMaGiaoDDich.thuebao_id)
          ? parseInt(this.dataInforMaGiaoDDich.thuebao_id)
          : parseInt(this.soMayAcc),
        loaitb_id: parseInt(this.selectedLoaiHinh),
        ma_tb: this.dataInforMaGiaoDDich.ma_tb,
        js_dgvDVGT: [dgvDVGT],
        loaihd_id: 7,
        dichvuvt_id: this.selectedDichVu,
        themmoi: this.themMoi,
        tocdo_id: 0,
        hdtb_id:
          this.themMoi === 0
            ? this.dataInforMaGiaoDDich.hdtb_id
            : this.hdtb_id_themMoi,
        kieuld_id: this.selectedKieuYeuCau,
        muccuoc_id: 0,
        hdkh_id:
          this.themMoi === 0
            ? this.dataInforMaGiaoDDich.hdkh_id
            : this.hdkh_id_themMoi,

        khachhang_id: this.dataInforMaGiaoDDich.khachhang_id,

        js_ds_isdn: ds_isdn,
        js_HDKH: HDKH,
        js_HDTB: HDTB,
        js_HDTB_DV: HDTB_DV,
        js_dangky_dvgt: dangky_dvgt,
        js_TTTT: [], // empty   // TaoDuLieu_THONGTIN_TT
        js_CTTHD: CTTHD,
        js_HDTB_CD: HDTB_CD,
        js_HDTB_ADSL: HDTB_ADSL,
        js_HDTB_IMS: HDTB_IMS,
        js_HDTB_GP: HDTB_GP,
        js_HDTB_MGW: HDTB_MGWAN,
        js_HDTB_TSL: HDTB_TSL,
        js_HDTB_DD: HDTB_DD,
        js_HDTB_CNTT: HDTB_CNTT,
        js_dangky_huy: this.dsDKHUY,
        js_dangky_dvk: this.dsDVKhac,
        thuhoi: this.valueThuHoiThietBi,
        nhanvien_id: this.$root.token.getNhanVienID(),
        js_BD_LYDOHUY: BD_LYDOHUY,
        ma_gd:
          this.themMoi === 0
            ? this.dataInforMaGiaoDDich.ma_gd
            : this.maGiaoDich,
        loaihinhtb: this.selectedLoaiHinh
      };

      console.log("input:", inputData);

      const inputData1 = JSON.stringify({
        kt: kt === true ? 1 : 0,
        thuebao_id: parseInt(this.dataInforMaGiaoDDich.thuebao_id)
          ? parseInt(this.dataInforMaGiaoDDich.thuebao_id)
          : parseInt(this.soMayAcc),
        loaitb_id: parseInt(this.selectedLoaiHinh),
        ma_tb: this.dataInforMaGiaoDDich.ma_tb,
        js_dgvDVGT: JSON.stringify([dgvDVGT]),
        loaihd_id: 7,
        dichvuvt_id: this.selectedDichVu,
        themmoi: this.themMoi,
        tocdo_id: 0,
        hdtb_id:
          this.themMoi === 0
            ? this.dataInforMaGiaoDDich.hdtb_id
            : this.hdtb_id_themMoi,
        kieuld_id: this.selectedKieuYeuCau,
        muccuoc_id: 0,
        hdkh_id:
          this.themMoi === 0
            ? this.dataInforMaGiaoDDich.hdkh_id
            : this.hdkh_id_themMoi,
        khachhang_id: this.dataInforMaGiaoDDich.khachhang_id,

        js_ds_isdn: JSON.stringify(ds_isdn),
        js_HDKH: JSON.stringify(HDKH),
        js_HDTB: JSON.stringify(HDTB),
        js_HDTB_DV: JSON.stringify(HDTB_DV),
        js_dangky_dvgt: JSON.stringify(dangky_dvgt),
        js_TTTT: JSON.stringify([]), // TaoDuLieu_THONGTIN_TT
        js_CTTHD: JSON.stringify(CTTHD),
        js_HDTB_CD: JSON.stringify(HDTB_CD),
        js_HDTB_ADSL: JSON.stringify(HDTB_ADSL),
        js_HDTB_IMS: JSON.stringify(HDTB_IMS),
        js_HDTB_GP: JSON.stringify(HDTB_GP),
        js_HDTB_MGW: JSON.stringify(HDTB_MGWAN),
        js_HDTB_TSL: JSON.stringify(HDTB_TSL),
        js_HDTB_DD: JSON.stringify(HDTB_DD),
        js_HDTB_CNTT: JSON.stringify(HDTB_CNTT),
        js_dangky_huy: JSON.stringify(this.dsDKHUY),
        js_dangky_dvk: JSON.stringify(this.dsDVKhac),
        thuhoi: this.valueThuHoiThietBi,
        nhanvien_id: this.$root.token.getNhanVienID(),
        js_BD_LYDOHUY: JSON.stringify(BD_LYDOHUY),
        ma_gd:
          this.themMoi === 0
            ? this.dataInforMaGiaoDDich.ma_gd
            : this.maGiaoDich,
        loaihinhtb: this.selectedLoaiHinh
      });

      this.$root.showLoading(true);
      try {
        const para = { ds_para: inputData };
        const res = await this.themMoiTB(para);
        const status = JSON.parse(res);
        if (status.MESSAGE === "Thành công") {
          this.$root.toastSuccess("Thêm mới thuê bao thành công!");
        } else {
          this.$toast.error(`${status.MESSAGE}`);
        }

        await this.napThongTinDichVuThueBao(this.dataInforMaGiaoDDich);
      } catch (error) {
        this.$toast.error(error);
      }

      this.$root.showLoading(false);
    },

    // action nút ghi lại  - chưa xong
    async tsbtnGhiLai_Click() {
      let kt = false;

      if (this.themMoi === 1) {
        this.hdkh_id_themMoi = await this.getKeys({
          keyname: "HD_KHACHHANG"
        });

        this.hdtb_id_themMoi = await this.getKeys({
          keyname: "HD_THUEBAO"
        });

        this.maGD_themMoi = await this.Lay_Ma_GD_V2({
          vdonvi_id: 0,
          vloaihd_id: 7
        });
      }

      if (this.dataGhiLai) {
        if (parseInt(this.dataGhiLai.trangthaitb_id) === TrangThaiTB.TAMDUNG) {
          kt = true;
        }

        if (kt) {
          const vthanhtoan_id = await this.MAP_ID({
            in_id_neo: "khlon_id",
            in_table: "css.db_khachhang",
            in_dk: `where khachhang_id = ${this.dataInforMaGiaoDDich.khachhang_id}`
          });

          if (vthanhtoan_id != "-1") {
            const ktra = await this.Kiemtra_no_thanhly_yc({
              thanhtoan_id: vthanhtoan_id,
              thuebao_id: this.dataInforMaGiaoDDich.thuebao_id
            });

            if (ktra !== "ok") {
              this.$root.toastSuccess("Lỗi!");
              return;
            }
          }

          if (this.selectedLoaiHinh === LOAIHINH_THUEBAO.INTERNET_MYTV) {
          }
        }
        const inputHuongTiepNhan = {
          loaiHdId: 7,
          loaiTbId: 1,
          tocDoId: 0,
          mucCuocId: 0,
          kieuLdId: this.selectedKieuYeuCau
        };
        const dsHuongTiepNhan = await this.LayHuongGiao_TiepNhan(
          inputHuongTiepNhan
        );

        const ds_isdn = this.TaoDuLieu_HDTB_ISDN(dsHuongTiepNhan);
        const dgvDVGT = this.TaoDuLieu_DVGT();
        const HDKH = await this.TaoDuLieu_HDKH();
        const HDTB = this.TaoDuLieu_HDTB(dsHuongTiepNhan);
        const HDTB_DV = await this.TaoDuLieu_HDTBDV();
        const dangky_dvgt = this.TaoDuLieu_HDTDDV();
        const CTTHD = await this.TaoDuLieu_CT_TIENHD();
        const HDTB_CD = await this.TaoDuLieu_HDTB_CD();
        const HDTB_ADSL = await this.TaoDuLieu_HDTB_ADSL();
        const HDTB_IMS = await this.TaoDuLieu_HDTB_IMS();
        const HDTB_GP = await this.TaoDuLieu_HDTB_GP();
        const HDTB_MGWAN = await this.TaoDuLieu_HDTB_MGWAN();
        const HDTB_TSL = await this.TaoDuLieu_HDTB_TSL();
        const HDTB_DD = await this.TaoDuLieu_HDTB_DD();
        const HDTB_CNTT = await this.TaoDuLieu_HDTB_CNTT();
        const BD_LYDOHUY = await this.TaoDuLieu_BD_LYDOHUY();

        const inputData = {
          kt: kt === true ? 1 : 0,
          thuebao_id: parseInt(this.dataInforMaGiaoDDich.thuebao_id)
            ? parseInt(this.dataInforMaGiaoDDich.thuebao_id)
            : parseInt(this.soMayAcc),
          loaitb_id: parseInt(this.selectedLoaiHinh),
          ma_nd: this.$auth.getUserName(),
          may_cn: this.dataInforMaGiaoDDich.may_cn
            ? this.dataInforMaGiaoDDich.may_cn
            : null,
          ip_cn: this.dataInforMaGiaoDDich.ip_cn
            ? this.dataInforMaGiaoDDich.ip_cn
            : null,
          ma_tb: this.dataInforMaGiaoDDich.ma_tb,
          js_dgvDVGT: [dgvDVGT],
          loaihd_id: 7,
          dichvuvt_id: this.selectedDichVu,
          themmoi: this.themMoi,
          tocdo_id: 0,
          hdtb_id:
            this.themMoi === 0
              ? this.dataInforMaGiaoDDich.hdtb_id
              : this.hdtb_id_themMoi,
          // hdtb_id: "535174619",
          kieuld_id: this.selectedKieuYeuCau,
          isdn_sochinh: 0,
          muccuoc_id: 0,
          hdkh_id:
            this.themMoi === 0
              ? this.dataInforMaGiaoDDich.hdkh_id
              : this.hdkh_id_themMoi,

          // hdkh_id: "5689981",
          khachhang_id: this.dataInforMaGiaoDDich.khachhang_id,

          js_ds_isdn: ds_isdn,
          js_HDKH: HDKH,
          js_HDTB: HDTB,
          js_HDTB_DV: HDTB_DV,
          js_dangky_dvgt: dangky_dvgt,
          js_TTTT: [], // empty   // TaoDuLieu_THONGTIN_TT
          js_CTTHD: CTTHD,
          js_HDTB_CD: HDTB_CD,
          js_HDTB_ADSL: HDTB_ADSL,
          js_HDTB_IMS: HDTB_IMS,
          js_HDTB_GP: HDTB_GP,
          js_HDTB_MGW: HDTB_MGWAN,
          js_HDTB_TSL: HDTB_TSL,
          js_HDTB_DD: HDTB_DD,
          js_HDTB_CNTT: HDTB_CNTT,
          js_dangky_huy: this.dsDKHUY,
          js_dangky_dvk: this.dsDVKhac,
          thuhoi: this.valueThuHoiThietBi,
          nhanvien_id: this.$root.token.getNhanVienID(),
          js_BD_LYDOHUY: BD_LYDOHUY,
          ma_gd:
            this.themMoi === 0
              ? this.dataInforMaGiaoDDich.ma_gd
              : this.maGiaoDich,
          // ma_gd: "HPG-DV/00721238",
          loaihinhtb: this.selectedLoaiHinh
        };

        console.log("input:", inputData);

        const inputData1 = JSON.stringify({
          kt: kt === true ? 1 : 0,
          thuebao_id: parseInt(this.dataInforMaGiaoDDich.thuebao_id)
            ? parseInt(this.dataInforMaGiaoDDich.thuebao_id)
            : parseInt(this.soMayAcc),
          loaitb_id: parseInt(this.selectedLoaiHinh),
          ma_nd: this.$auth.getUserName(),
          may_cn: this.dataInforMaGiaoDDich.may_cn
            ? this.dataInforMaGiaoDDich.may_cn
            : null,
          ip_cn: this.dataInforMaGiaoDDich.ip_cn
            ? this.dataInforMaGiaoDDich.ip_cn
            : null,
          ma_tb: this.dataInforMaGiaoDDich.ma_tb,
          js_dgvDVGT: JSON.stringify([dgvDVGT]),
          loaihd_id: 7,
          dichvuvt_id: this.selectedDichVu,
          themmoi: this.themMoi,
          tocdo_id: 0,
          hdtb_id:
            this.themMoi === 0
              ? this.dataInforMaGiaoDDich.hdtb_id
              : this.hdtb_id_themMoi,
          // hdtb_id: "535174619",
          kieuld_id: this.selectedKieuYeuCau,
          isdn_sochinh: 0,
          muccuoc_id: 0,
          hdkh_id:
            this.themMoi === 0
              ? this.dataInforMaGiaoDDich.hdkh_id
              : this.hdkh_id_themMoi,
          // hdkh_id: "5689981",
          khachhang_id: this.dataInforMaGiaoDDich.khachhang_id,

          js_ds_isdn: JSON.stringify(ds_isdn),
          js_HDKH: JSON.stringify(HDKH),
          js_HDTB: JSON.stringify(HDTB),
          js_HDTB_DV: JSON.stringify(HDTB_DV),
          js_dangky_dvgt: JSON.stringify(dangky_dvgt),
          js_TTTT: JSON.stringify([]), // TaoDuLieu_THONGTIN_TT
          js_CTTHD: JSON.stringify(CTTHD),
          js_HDTB_CD: JSON.stringify(HDTB_CD),
          js_HDTB_ADSL: JSON.stringify(HDTB_ADSL),
          js_HDTB_IMS: JSON.stringify(HDTB_IMS),
          js_HDTB_GP: JSON.stringify(HDTB_GP),
          js_HDTB_MGW: JSON.stringify(HDTB_MGWAN),
          js_HDTB_TSL: JSON.stringify(HDTB_TSL),
          js_HDTB_DD: JSON.stringify(HDTB_DD),
          js_HDTB_CNTT: JSON.stringify(HDTB_CNTT),
          js_dangky_huy: JSON.stringify(this.dsDKHUY),
          js_dangky_dvk: JSON.stringify(this.dsDVKhac),
          thuhoi: this.valueThuHoiThietBi,
          nhanvien_id: this.$root.token.getNhanVienID(),
          js_BD_LYDOHUY: JSON.stringify(BD_LYDOHUY),
          ma_gd:
            this.themMoi === 0
              ? this.dataInforMaGiaoDDich.ma_gd
              : this.maGiaoDich,
          // ma_gd: "HPG-DV/00721238",
          loaihinhtb: this.selectedLoaiHinh
        });

        this.$root.showLoading(true);
        try {
          const para = { ds_para: inputData1 };
          const res = await this.ghiLaiHopDongTB(para);
          const status = JSON.parse(res);
          if (status.MESSAGE === "Thành công") {
            this.$root.toastSuccess("Ghi lại thành công!");
            this.disabledBtnThanhToan = false;
            // this.disabledBtnGhiLai = true;
            // this.disabledBtnThemTB = false;
          } else {
            this.$toast.error(`${status.MESSAGE}`);
          }

          await this.napThongTinDichVuThueBao(this.dataInforMaGiaoDDich);
        } catch (error) {
          throw error;
        }
      } else {
        this.$root.toastSuccess("Ghi lại thành công!");
      }

      this.$root.showLoading(false);
    },

    TaoDuLieu_DVGT() {
      return [
        {
          dichvugt_id: parseInt(this.dataGhiLai.dichvugt_id),
          ma_dvgt: this.dataGhiLai.ma_dvgt,
          ten_dvgt: this.dataGhiLai.ten_dvgt,
          tien: parseInt(this.dataGhiLai.tien.replace(/,/g, "")),
          trangthaitb_id: this.dataGhiLai.trangthaitb_id,
          dangky: this.dataGhiLai.dangky === true ? 1 : 0,
          huy: this.dataGhiLai.huy === true ? 1 : 0,
          sua: this.dataGhiLai.sua === true ? 1 : 0,
          noidung: this.dataGhiLai.noidung,
          cuoc_sd: parseInt(this.dataGhiLai.cuoc_sd.replace(/,/g, "")),
          vat_ld: parseInt(this.dataGhiLai.vat_ld.replace(/,/g, "")),
          ghichu: this.dataGhiLai.ghichu,
          sl_cha: parseInt(this.dataGhiLai.sl_cha.replace(/,/g, "")),
          block_tiep: parseInt(this.dataGhiLai.block_tiep.replace(/,/g, "")),
          gia_block_tiep: parseInt(
            this.dataGhiLai.gia_block_tiep.replace(/,/g, "")
          ),
          vat_block_tiep: parseInt(
            this.dataGhiLai.vat_block_tiep.replace(/,/g, "")
          )
        }
      ];
    },

    // Tao du lieu cho bang HD_KHACHHANG
    async TaoDuLieu_HDKH() {
      let obj = {
        HDKH_ID:
          this.themMoi === 0
            ? this.dataInforMaGiaoDDich.hdkh_id
            : this.hdkh_id_themMoi,
        MA_GD:
          this.themMoi === 0
            ? this.dataInforMaGiaoDDich.ma_gd
            : this.maGiaoDich,
        MA_HD: this.maHopDong,
        MA_KH: this.maKH,
        KHACHHANG_ID: this.dataInforMaGiaoDDich.khachhang_id,
        TEN_KH: this.tenKH,
        DIACHI_KH: this.diaChiKH,
        NGAYLAP_HD: this.ngayLapHD
          ? moment(this.ngayLapHD).format("DD-MMM-YYYY")
          : this.current_time,
        NGAY_YC: this.ngayYeuCau,
        DONVI_ID: this.dataInforMaGiaoDDich.donvi_id,
        NHANVIEN_ID: this.$root.token.getNhanVienID(),
        LOAIHD_ID: LOAI_HOPDONG.THAY_DOI_DV,
        NGUOI_CN:
          this.themMoi === 0
            ? this.dataInforMaGiaoDDich.nguoi_cn
            : this.$root.token.getUserName(),
        NGAY_CN: this.$auth.getNgayCapNhat(),
        MAY_CN: this.dataInforMaGiaoDDich.may_cn
          ? this.dataInforMaGiaoDDich.may_cn
          : "OneBSS",
        HD_KHACHHANG: this.dataInforMaGiaoDDich.hdkh_id
          ? this.dataInforMaGiaoDDich.hdkh_id
          : null
      };

      if (this.dataInforMaGiaoDDich.hdtb_cha_id > 0) {
        obj = { HDKH_CHA_ID: this.dataInforMaGiaoDDich.hdtb_cha_id, ...obj };
      }

      if (this.dataInforMaGiaoDDich.hdkh_id > 0) {
        const variables = {
          in_id_neo: "khlon_id",
          in_table: "css.db_khachhang",
          in_dk: `where khachhang_id = ${
            this.themMoi === 0
              ? this.dataInforMaGiaoDDich.hdkh_id
              : this.hdkh_id_themMoi
          }`
        };

        const khl_id = await this.mapIDDuLieuHDKH(variables);
        if (khl_id != "" && khl_id != "-1") {
          obj = { KHLON_ID: parseInt(khl_id), ...obj };
        } else {
          obj = { KHLON_ID: 1, ...obj };
        }
      } else {
        obj = { KHLON_ID: 1, ...obj };
      }

      if (this.checkBoxCTV) {
        obj = { CTV_ID: this.inputCTV, ...obj };
      }

      if (this.checkBoxNguoiGT) {
        obj = { NHANVIENGT_ID: this.inputNguoiGT, ...obj };
      }

      return [obj];
    },

    // Tao du lieu cho bang HD_THUEBAO
    TaoDuLieu_HDTB(dsHuongTiepNhan) {
      let obj = {
        HDTB_ID:
          this.themMoi === 0
            ? this.dataInforMaGiaoDDich.hdtb_id
            : this.hdtb_id_themMoi,
        // HDTB_ID: "535174619",
        HDKH_ID:
          this.themMoi === 0
            ? this.dataInforMaGiaoDDich.hdkh_id
            : this.hdkh_id_themMoi,
        // HDKH_ID: "5689981",
        MA_TB: this.dataInforMaGiaoDDich.ma_tb,
        TEN_TB: this.dataInforMaGiaoDDich.ten_tb,
        DIACHI_TB: this.dataInforMaGiaoDDich.diachi_tb,
        DIACHI_LD: this.dataInforMaGiaoDDich.diachi_ld,
        GHICHU: this.dataInforMaGiaoDDich.ghichu,
        KIEULD_ID: this.selectedKieuYeuCau,
        DOITUONG_ID: this.dataInforMaGiaoDDich.doituong_id,
        TTHD_ID: TRANGTHAI_HOPDONG.MOI,
        LOAITB_ID: this.dataInforMaGiaoDDich.loaitb_id,
        DICHVUVT_ID: this.dataInforMaGiaoDDich.dichvuvt_id,
        DONVI_ID: this.dataInforMaGiaoDDich.donvi_id,
        QUYTRINH_ID: dsHuongTiepNhan.quytrinh_id
          ? dsHuongTiepNhan.quytrinh_id
          : 0,
        NGUOI_CN:
          this.themMoi === 0
            ? this.dataInforMaGiaoDDich.nguoi_cn
            : this.$root.token.getUserName(),
        // NGUOI_CN: this.$root.token.getUserName(),
        MAY_CN: this.dataInforMaGiaoDDich.may_cn
          ? this.dataInforMaGiaoDDich.may_cn
          : "OneBSS",
        HD_THUEBAO: this.dataInforMaGiaoDDich.hdtb_id
          ? this.dataInforMaGiaoDDich.hdtb_id
          : null
      };
      if (this.dataInforMaGiaoDDich.thuebao_id > 0) {
        obj = { THUEBAO_ID: this.dataInforMaGiaoDDich.thuebao_id, ...obj };
      }

      if (this.dataInforMaGiaoDDich.dichvuvt_id === DICHVU_VIENTHONG.DI_DONG) {
        obj = { NGAY_HT: this.ngayYeuCau, ...obj };
      } else {
        obj = { NGAY_CN: this.ngayYeuCau, ...obj };
      }

      if (
        this.dataInforMaGiaoDDich.dichvuvt_id == DICHVU_VIENTHONG.ADSL ||
        this.dataInforMaGiaoDDich.dichvuvt_id == DICHVU_VIENTHONG.IMS ||
        this.dataInforMaGiaoDDich.dichvuvt_id == DICHVU_VIENTHONG.CO_DINH ||
        this.dataInforMaGiaoDDich.dichvuvt_id == DICHVU_VIENTHONG.DI_DONG ||
        this.dataInforMaGiaoDDich.dichvuvt_id == DICHVU_VIENTHONG.GPHONE
      )
        obj = { STATUS: TRANGTHAI_DONGBO.CHUADONGBO, ...obj };

      if (this.checkBoxNgayHetHan) {
        obj = { STATUS: this.ngayHetHan, ...obj };
      }

      if (this.dataInforMaGiaoDDich.loaitb_id === LOAIHINH_THUEBAO.PABX) {
        if (this.dataInforMaGiaoDDich.hdtb_id_cha > 0)
          obj = { HDTB_CHA_ID: this.dataInforMaGiaoDDich.hdtb_id_cha, ...obj };
      }

      return [obj];
    },

    // Tao du lieu cho bang HD_THUEBAO_ISDN
    TaoDuLieu_HDTB_ISDN(dsHuongTiepNhan) {
      let obj = {
        HDTB_ID:
          this.themMoi === 0
            ? this.dataInforMaGiaoDDich.hdtb_id
            : this.hdtb_id_themMoi,
        // HDTB_ID: "535174619",
        HDKH_ID:
          this.themMoi === 0
            ? this.dataInforMaGiaoDDich.hdkh_id
            : this.hdkh_id_themMoi,
        // HDKH_ID: "5689981",

        MA_TB: this.dataInforMaGiaoDDich.ma_tb,
        TEN_TB: this.dataInforMaGiaoDDich.ten_tb,
        DIACHI_TB: this.dataInforMaGiaoDDich.diachi_tb,
        DIACHI_LD: this.dataInforMaGiaoDDich.diachi_ld,
        GHICHU: this.dataInforMaGiaoDDich.ghichu,
        KIEULD_ID: this.selectedKieuYeuCau,
        DOITUONG_ID: this.dataInforMaGiaoDDich.doituong_id,
        TTHD_ID: TRANGTHAI_HOPDONG.MOI,
        LOAITB_ID: this.dataInforMaGiaoDDich.loaitb_id,
        DICHVUVT_ID: this.dataInforMaGiaoDDich.dichvuvt_id,
        DONVI_ID: this.dataInforMaGiaoDDich.donvi_id,
        QUYTRINH_ID: dsHuongTiepNhan.quytrinh_id
          ? dsHuongTiepNhan.quytrinh_id
          : 0,
        NGUOI_CN: this.themMoi
          ? this.dataInforMaGiaoDDich.nguoi_cn
          : this.$root.token.getUserName(),
        // NGUOI_CN: this.$root.token.getUserName(),
        NGAY_CN: this.$auth.getNgayCapNhat(),
        MAY_CN: this.dataInforMaGiaoDDich.may_cn
          ? this.dataInforMaGiaoDDich.may_cn
          : "OneBSS",
        HD_THUEBAO: this.dataInforMaGiaoDDich.hdtb_id
          ? this.dataInforMaGiaoDDich.hdtb_id
          : null
      };
      if (this.dataInforMaGiaoDDich.thuebao_id > 0) {
        obj = { THUEBAO_ID: this.dataInforMaGiaoDDich.thuebao_id, ...obj };
      }
      if (this.dataInforMaGiaoDDich.thuebao_cha_id > 0) {
        obj = { THUEBAO_ID: this.dataInforMaGiaoDDich.thuebao_cha_id, ...obj };
      }

      if (
        this.dataInforMaGiaoDDich.dichvuvt_id === DICHVU_VIENTHONG.ADSL ||
        this.dataInforMaGiaoDDich.dichvuvt_id === DICHVU_VIENTHONG.IMS
      )
        obj = { STATUS: TRANGTHAI_DONGBO.CHUADONGBO, ...obj };

      return [obj];
    },

    // TaoDuLieu_HDTBDV
    async TaoDuLieu_HDTBDV() {
      try {
        const dtTemp = await this.LAY_DS_HDTB_DV({
          thuebao_id: this.dataInforMaGiaoDDich.thuebao_id
        });

        if (dtTemp.length) {
          let list = dtTemp.map(item => {
            const res = {
              LOAIDV_ID: item.loaidv_id,
              DONVI_ID: item.donvi_id,
              KIEUDV_ID: item.kieudv_id,
              HDTB_ID:
                this.themMoi === 0
                  ? this.dataInforMaGiaoDDich.hdtb_id
                  : this.hdtb_id_themMoi
              // HDTB_ID: "535174619",
            };

            return res;
          });

          return list;
        } else {
          return [];
        }
      } catch (error) {
        throw error;
      }
    },

    // Tao du lieu cho bang DANGKY_DVGT
    TaoDuLieu_HDTDDV() {
      let obj = {
        HDTB_ID:
          this.themMoi === 0
            ? this.dataInforMaGiaoDDich.hdtb_id
            : this.hdtb_id_themMoi,
        // HDTB_ID: "3589114",
        NGAY_YC: this.ngayYeuCau,
        DICHVUGT_ID: parseInt(this.dataGhiLai.dichvugt_id),
        NOIDUNG: this.dataGhiLai.noidung,
        TTTH: 1,
        CUOC_SD: parseInt(this.dataGhiLai.cuoc_sd),
        VAT_SD: this.dataGhiLai.vat_sd
          ? parseInt(this.dataGhiLai.vat_sd.replace(/,/g, ""))
          : 0,
        TIEN: this.dataGhiLai.tien
          ? parseInt(this.dataGhiLai.tien.replace(/,/g, ""))
          : 0
      };

      let kieu_dk_dvgt = "";
      if (this.dataGhiLai.dangky) {
        kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_THEM_MOI_DVGT;
      } else if (this.dataGhiLai.huy)
        kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_HUY_DVGT;
      else if (this.dataGhiLai.sua)
        kieu_dk_dvgt = KieuCaiDatDVGT.KIEU_CD_SUA_DVGT;

      obj = { KIEU_YC: kieu_dk_dvgt, ...obj };

      if (
        parseInt(this.dataGhiLai.sl_cha) !== 0 &&
        (this.selectedLoaiHinh === LOAIHINH_THUEBAO.IOFFICE ||
          this.selectedLoaiHinh === LOAIHINH_THUEBAO.HNTH_NGN ||
          this.selectedLoaiHinh === LOAIHINH_THUEBAO.VNPT_MEETING ||
          this.selectedLoaiHinh === LOAIHINH_THUEBAO.VNPT_PORTAL)
      ) {
        if (parseInt(this.dataGhiLai.block_tiep) !== 0) {
          obj = { SL_CHA: parseInt(this.dataGhiLai.sl_cha), ...obj };
        } else {
          obj = { SL_CHA: 0, ...obj };
        }

        obj = { BLOCK_DAU: parseInt(this.dataGhiLai.block_dau), ...obj };
        obj = { BLOCK_TIEP: parseInt(this.dataGhiLai.block_tiep), ...obj };
        obj = {
          GIA_BLOCK_TIEP: parseInt(this.dataGhiLai.gia_block_tiep),
          ...obj
        };
        obj = {
          VAT_BLOCK_TIEP: parseInt(this.dataGhiLai.vat_block_tiep),
          ...obj
        };
        obj = { HE_SO: parseInt(this.dataGhiLai.he_so), ...obj };
      }

      return [obj];
    },

    // Tạo dữ liệu cho bảng CT_TIENHD
    async TaoDuLieu_CT_TIENHD() {
      let obj = {};
      const id = await this.getKeys({
        keyname: "CT_TIENHD"
      });
      const item = this.ds_tien_khoanmuc.find(
        x => parseInt(x.khoanmuctt_id) === KHOANMUC_TT.KMTT_DICHVU
      );
      if (item && Object.keys(item).length === 0) {
        if (this.dataGhiLai.dangky && !this.dataGhiLai.huy) {
          obj = {
            HDTB_ID:
              this.themMoi === 0
                ? this.dataInforMaGiaoDDich.hdtb_id
                : this.hdtb_id_themMoi,
            CT_TIENHD_ID: this.themMoi === 1 ? id : null,
            KHOANMUCTT_ID: KHOANMUC_TT.KMTT_DICHVU,
            TIEN: this.dataGhiLai.cuoc_ld
              ? parseInt(this.dataGhiLai.cuoc_ld.replace(/,/g, ""))
              : 0,
            VAT: this.dataGhiLai.vat_ld
              ? parseInt(this.dataGhiLai.vat_ld.replace(/,/g, ""))
              : 0,

            ID: this.selectedDichVu,
            LOAI_ID: 2,
            ...obj
          };
        }
      } else {
        obj = {
          HDTB_ID:
            this.themMoi === 0
              ? this.dataInforMaGiaoDDich.hdtb_id
              : this.hdtb_id_themMoi,
          CT_TIENHD_ID: this.themMoi === 1 ? id : null,
          KHOANMUCTT_ID: KHOANMUC_TT.KMTT_DICHVU,
          TIEN: this.dataGhiLai.tien
            ? parseInt(this.dataGhiLai.tien.replace(/,/g, ""))
            : 0,
          VAT: this.dataGhiLai.vat
            ? parseInt(this.dataGhiLai.vat.replace(/,/g, ""))
            : 0,
          ID: this.selectedDichVu,
          LOAI_ID: 1,
          ...obj
        };
      }

      if (Object.keys(obj).length === 0) {
        return [];
      } else {
        return [obj];
      }
    },

    //Tao du lieu cho bang HDTB_CD
    async TaoDuLieu_HDTB_CD() {
      try {
        const res = await this.LAY_DS_HDTB_DV({
          in_thuebao_id: this.dataInforMaGiaoDDich.thuebao_id
        });
        if (res.length) {
          const list = res.map(item => {
            const arr = {
              PHANVUNG_ID: item.phanvung_id,
              HDTB_ID:
                this.themMoi === 0
                  ? this.dataInforMaGiaoDDich.hdtb_id
                  : this.hdtb_id_themMoi,
              // HDTB_ID: "535174619",
              NE: item.ne,
              CULY: item.culy,
              GHICHU: item.ghichu,
              MATB_TN: item.matb_tn,
              MADOICAP: item.madoicap,
              MA_LT: item.ma_lt,
              TRANGBI_ID: item.trangbi_id,
              NE_ID: item.ne_id,
              TRAMTB_ID: item.tramtb_id,
              NAP_ID: item.nap_id,
              TUYENCAP: item.tuyencap,
              LIENTU: item.lientu,
              DOICAP_P: item.doicap_p,
              HOPCAP_P: item.hopcap_p,
              TUCAP_P: item.tucap_p,
              LOAICAP_ID: item.loaicap_id,
              CAP_GOC: item.cap_goc,
              CAP_P: item.cap_p,
              DOICAP_GOC: item.doicap_goc,
              TUCAP_GOC: item.tucap_goc,
              TG_THUESO: item.tg_thueso,
              SOTRUOT: item.sotruot,
              NOIDUNG_TB: item.noidung_tb,
              CAP_ID: item.cap_id,
              KETCUOI_ID: item.ketcuoi_id,
              DOICAP: item.doicap,
              VITRI: item.vitri,
              DOICAP_2: item.doicap_2,
              VITRI_2: item.vitri_2,
              CHUQUAN_ID: item.chuquan_id,
              CONGNGHE_ID: item.congnghe_id,
              THOIHAN_ID: item.thoihan_id,
              TOANHA_ID: item.toanha_id,
              SO_TAT: item.so_tat,
              CHUQUANLD_ID: item.chuquan_id,
              CUOC_DOITAC: item.cuoc_doitac,
              DOITAC_ID: item.doitac_id,
              TINHKHAC: item.tinhkhac,
              MA_VNPT: item.ma_vnpt
            };
            return arr;
          });
          return list;
        } else {
          return [];
        }
      } catch (error) {
        throw error;
      }
    },

    // Tao du lieu cho bang HDTB_ADSL
    async TaoDuLieu_HDTB_ADSL() {
      try {
        const res = await this.LAY_DS_DB_ADSL_THEO_TBID(
          this.dataInforMaGiaoDDich.thuebao_id
        );
        if (res.length) {
          const list = res.map(item => {
            const obj = Object.keys(item).reduce(
              (a, b) => {
                a[b.toUpperCase()] = item[b];
                return a;
              },
              {
                HDTB_ID:
                  this.themMoi === 0
                    ? this.dataInforMaGiaoDDich.hdtb_id
                    : this.hdtb_id_themMoi
                // HDTB_ID: "535174619",
              }
            );

            if (Object.keys(obj).length === 0) {
              return [];
            } else {
              return [obj];
            }
          });
          return list;
        } else {
          return [];
        }
      } catch (error) {
        throw error;
      }
    },

    // Tao du lieu cho bang TaoDuLieu_HDTB_IMS
    async TaoDuLieu_HDTB_IMS() {
      try {
        const res = await this.LAY_DS_DB_IMS_THEO_TBID(
          this.dataInforMaGiaoDDich.thuebao_id
        );
        if (res.length) {
          const list = res.map(item => {
            const obj = Object.keys(item).reduce(
              (a, b) => {
                a[b.toUpperCase()] = item[b];
                return a;
              },
              {
                HDTB_ID:
                  this.themMoi === 0
                    ? this.dataInforMaGiaoDDich.hdtb_id
                    : this.hdtb_id_themMoi
                // HDTB_ID: "535174619",
              }
            );
            return obj;
          });
          return list;
        } else {
          return [];
        }
      } catch (error) {
        throw error;
      }
    },

    // Tao du lieu cho bang
    async TaoDuLieu_HDTB_GP() {
      try {
        const res = await this.LAY_DS_DB_GP_THEO_TBID(
          this.dataInforMaGiaoDDich.thuebao_id
        );
        if (res.length) {
          const list = res.map(item => {
            const obj = Object.keys(item).reduce(
              (a, b) => {
                a[b.toUpperCase()] = item[b];
                return a;
              },
              {
                HDTB_ID:
                  this.themMoi === 0
                    ? this.dataInforMaGiaoDDich.hdtb_id
                    : this.hdtb_id_themMoi
                // HDTB_ID: "535174619",
              }
            );
            return obj;
          });
          return list;
        } else {
          return [];
        }
      } catch (error) {
        throw error;
      }
    },

    // Tao du lieu cho bang HDTB_MGWAN
    async TaoDuLieu_HDTB_MGWAN() {
      try {
        const res = await this.LAY_DS_DB_GP_THEO_TBID(
          this.dataInforMaGiaoDDich.thuebao_id
        );
        if (res.length) {
          const list = res.map(item => {
            const obj = Object.keys(item).reduce(
              (a, b) => {
                a[b.toUpperCase()] = item[b];
                return a;
              },
              {
                HDTB_ID:
                  this.themMoi === 0
                    ? this.dataInforMaGiaoDDich.hdtb_id
                    : this.hdtb_id_themMoi
                // HDTB_ID: "535174619",
              }
            );
            return obj;
          });
          return list;
        } else {
          return [];
        }
      } catch (error) {
        throw error;
      }
    },

    //TaoDuLieu_HDTB_TSL
    async TaoDuLieu_HDTB_TSL() {
      let obj = {
        HDTB_ID:
          this.themMoi === 0
            ? this.dataInforMaGiaoDDich.hdtb_id
            : this.hdtb_id_themMoi
      };
      const res = await this.LAY_DS_DB_TSL_THEO_TBID(
        this.themMoi === 0
          ? this.dataInforMaGiaoDDich.hdtb_id
          : this.hdtb_id_themMoi
      );

      if (res.length) {
        obj = {
          DAUCUOI_ID: res[0].daucuoi_id,
          MA_TB_SUB: res[0].ma_tb_sub,
          DIACHI: res[0].diachi,
          CHUQUAN_ID: res[0].chuquan_id,
          TRANGBI_ID: res[0].trangbi_id,
          TOCDO_ID: res[0].tocdo_id,
          LOAIKENH_ID: res[0].loaikenh_id,
          MA_PL: res[0].ma_pl,
          MA_LT: res[0].ma_lt,
          MA_BSS: res[0].ma_bss,
          ID_ERP: res[0].id_erp,
          ...obj
        };
      }

      return [obj];
    },

    //TaoDuLieu_HDTB_DD - chưa có api
    async TaoDuLieu_HDTB_DD() {
      return {
        HDTB_ID:
          this.themMoi === 0
            ? this.dataInforMaGiaoDDich.hdtb_id
            : this.hdtb_id_themMoi
        // HDTB_ID: "535174619",
      };
    },

    // TaoDuLieu_HDTB_CNTT
    async TaoDuLieu_HDTB_CNTT() {
      try {
        const res = await this.LAY_DS_DB_CNTT_THEO_TBID(
          this.dataInforMaGiaoDDich.thuebao_id
        );

        if (res.length) {
          const list = res.map(item => {
            const obj = Object.keys(item).reduce(
              (a, b) => {
                a[b.toUpperCase()] = item[b];
                return a;
              },
              {
                HDTB_ID:
                  this.themMoi === 0
                    ? this.dataInforMaGiaoDDich.hdtb_id
                    : this.hdtb_id_themMoi
                // HDTB_ID: "535174619",
              }
            );
            return obj;
          });
          return list;
        } else {
          return [];
        }
      } catch (error) {
        throw error;
      }
    },

    // TaoDuLieu_BD_LYDOHUY
    async TaoDuLieu_BD_LYDOHUY() {
      try {
        const res = await this.LAY_DS_DB_LYDOHUY_THEO_TBID(
          this.dataInforMaGiaoDDich.thuebao_id
        );

        if (res.length) {
          const list = res.map(item => {
            const obj = Object.keys(item).reduce((a, b) => {
              a[b.toUpperCase()] = item[b];
              return a;
            }, {});
            return obj;
          });
          return list;
        } else {
          return [];
        }
      } catch (error) {
        throw error;
      }
    },

    // action nút thanh toán
    tsbtnThanhToan_Click() {
      this.$router.push({
        name: "Payment",
        params: { magd: this.maGiaoDich }
      });
    },

    //action nút xóa Hợp đồng
    async tsbtnXoa_Click() {
      if (
        this.soMayAcc &&
        this.dataInforSoMayAcc &&
        Object.keys(this.dataInforSoMayAcc).length > 0
      ) {
        const variables = {
          hdkh_id: this.dataInforSoMayAcc.hdkh_id,
          loaihd_id: 7
        };
        this.$root.showLoading(true);
        try {
          const status = await this.deleteHD(variables);
          if (status === "Success")
            this.$toast.success("Xóa hợp đồng thành công!");
          this.setButton(2);
        } catch (error) {
          this.$toast.error("Xóa hợp đồng bị lỗi!");
        }
        this.$root.showLoading(false);
      } else if (
        this.maGiaoDich &&
        this.dataInforMaGiaoDDich &&
        Object.keys(this.dataInforMaGiaoDDich).length > 0
      ) {
        const variables = {
          hdkh_id: this.dataInforMaGiaoDDich.hdkh_id,
          loaihd_id: 7
        };
        this.$root.showLoading(true);
        try {
          const status = await this.deleteHD(variables);
          if (status === "Success")
            this.$toast.success("Xóa hợp đồng thành công!");
          this.setButton(2);
        } catch (error) {
          this.$toast.error("Xóa hợp đồng bị lỗi!");
        }
        this.$root.showLoading(false);
      } else {
        this.$toast.error(
          "Vui lòng tìm hợp đồng bằng số máy thuê bao hoặc mã giao dịch!"
        );
      }
    },

    // xóa thuê bao
    async tsbtnXoaTB_Click() {
      const id =
        Object.keys(this.dataInforSoMayAcc).length > 0
          ? this.dataInforSoMayAcc.hdtb_id
          : this.dataInforMaGiaoDDich.hdtb_id;
      if (this.soMayAcc) {
        if (!confirm("Bạn thật sự muốn xóa dữ liệu không ?")) return;
        this.$root.showLoading(true);
        const res = await this.xoaHopDongTB({
          hdtb_id: id
        });

        if (res === "Success")
          this.$toast.success("Xóa hợp đồng thuê bao thành công!");

        this.$root.showLoading(false);
        this.setButton(1);
        try {
        } catch (err) {
          this.$toast.error(err);
        }
      }
    },

    // hẹn thông tin kh
    tsbtnHen_Click() {
      this.statusPopupHenKH = true;
    },

    updateStatusPopup(data) {
      this.statusPopupHenKH = data;
    },

    // original data
    async resetDataDefault() {
      await this.fetchDichVuList();
      this.selectedDichVu = 1;

      await this.fetchLoaiHinhList();
      await this.filterLoaiHinhList();

      await this.napThongTinDichVuThueBao(null);
    },

    async setButton(num) {
      // nhập mới
      if (num === 1) {
        this.themMoi = 1;
        // this.disabledBtnGhiLai = true;
        this.disabledBtnHuy = false;
        this.checkBoxCTV = false;
        this.checkBoxNguoiGT = false;
        this.inputCTV = "";
        this.inputNguoiGT = "";

        this.hdkh_id_themMoi = "";
        this.hdtb_id_themMoi = "";
        this.maGD_themMoi = "";
        this.maGiaoDich = "";

        await this.clearData();
      }

      if (num === 2) {
        this.themMoi = 0;
        // this.disabledBtnGhiLai = true;
        this.disabledBtnXoaTB = true;
        this.disabledBtnXoaHD = true;
        this.disabledBtnThanhToan = true;
        this.hdkh_id_themMoi = "";
        this.hdtb_id_themMoi = "";
        this.maGD_themMoi = "";
        this.maGiaoDich = "";
        await this.clearData();
      }

      if (num === 3) {
        //Edit
        // this.disabledBtnGhiLai = false;
        this.disabledBtnXoaTB = false;
        this.disabledBtnXoaHD = false;
        this.disabledBtnThanhToan = false;
        this.disabledBtnHuy = false;
      }

      // khi tồn tại mã giao dịch or số máy acc
      // if (
      //   num === 4 &&
      //   ((this.soMayAcc &&
      //     this.dataInforSoMayAcc &&
      //     Object.keys(this.dataInforSoMayAcc).length > 0) ||
      //     (this.maGiaoDich &&
      //       this.dataInforMaGiaoDDich &&
      //       Object.keys(this.dataInforMaGiaoDDich).length > 0))
      // ) {
      //   // this.disabledBtnXoaTB = false;
      //   // this.disabledBtnThanhToan = false;
      //   // this.disabledBtnGhiLai = false;
      // }
    },

    // clear data
    async clearData() {
      let current_time = moment()
        .format("DD-MM-YYYY")
        .toString();

      this.hdtbId = 0;
      this.maHopDong = "";
      this.ngayYeuCau = current_time;
      this.ngayLapHD = current_time;
      this.selectedDichVu = 1;
      this.selectedKieuYeuCau = 23;
      this.selectedLoaiHinh = 1;
      this.maHDTTKD = "";
      this.soMayAcc = "";
      this.huongKetNoi = "";
      this.tenThueBao = "";
      this.diaChiTB = "";
      this.soAo = "";
      this.trangThaiTB = "";
      this.maERP = "";
      this.maBSS = "";
      this.diaChiLD = "";
      this.diaChiLDDau = "";
      this.diaChiLDCuoi = "";
      this.ghiChu = "";
      this.maKH = "";
      this.checkBoxNgayHetHan = false;
      this.ngayHetHan = current_time;
      this.tenKH = "";
      this.diaChiKH = "";
      this.tongTienKhoanMuc = 0;
      this.dsDKHUY = [];
      this.dsDVKhac = [];
      await this.napThongTinDichVuThueBao(null);
    },

    // data khi click 1 dòng trong danh sách dịch vụ gia tăng
    recordClickDichVuTang(data) {},

    // data nhận từ emit khi tìm kiếm số máy acc
    onSelectCustomer(data) {
      this.soMayAcc = data.ma_tb;

      this.txtMaTB_KeyPress();
    },

    /* chọn data từ danh sách thuê bao */
    recordClick(data) {}
  },
  watch: {
    // thay đổi dịch vụ => thay đổi cả loại hình tb
    async selectedDichVu(val) {
      this.loaiHinhListByFilter = [];
      const list = this.loaiHinhList.filter(
        item => item.DICHVUVT_ID === this.selectedDichVu
      );
      if (list.length) {
        const status = this.checkDichVuId(this.selectedDichVu);
        if (status) {
          this.filterLoaiHinhByDichVuID(this.selectedDichVu);
        } else {
          this.selectedLoaiHinh = list[0].LOAITB_ID;
        }
      } else {
        this.selectedLoaiHinh = null;
      }
      this.loaiHinhListByFilter = list;
    },

    // thay đổi loại hình => thay đổi kiểu yêu cầu
    async selectedLoaiHinh(val) {
      if (val) {
        const variables = {
          vkieu: 0,
          vloaihd_id: 7,
          vloaitb_id: val
        };
        await this.fetchKieuYeuCauListLD_BC(variables);
        this.dataKieuYeuCauList = this.kieuYeuCauListLD_BC;

        if (this.dataKieuYeuCauList.length) {
          this.selectedKieuYeuCau = this.dataKieuYeuCauList[0].kieuld_id;
        } else {
          this.selectedKieuYeuCau = 0;
        }
      } else {
        await this.fetchYeuCauList();
        this.dataKieuYeuCauList = this.mappingDataKieuYeuCau(
          this.kieuYeuCauList
        );
        this.selectedKieuYeuCau = this.dataKieuYeuCauList[0].kieuld_id;
      }
      await this.loadDataGridWhenChange();
    },

    // thay đổi kiểu  yêu cầu => gọi hàm napThongTinDichVuThueBao để load lại data & gọi api đổ data vào thông tin form
    async selectedKieuYeuCau(val) {
      await this.loadDataGridWhenChange();
    }
  }
};
</script>
<style scoped>
/deep/ .page-content .tabs .tab-content {
  padding: 0 !important;
  border: none !important;
  box-shadow: none !important;
}
</style>
