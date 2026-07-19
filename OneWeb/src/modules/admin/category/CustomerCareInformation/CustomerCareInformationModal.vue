<template>
  <b-modal
    ref="popupCustomerCareInformationModal"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-esc
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="img"
            ><span class="icon one-notepad"></span> Cập nhật thông tin chăm sóc
            khách hàng</span
          >
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="hideModal"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a
              href="javascript:;"
              @click="handleBtnNhapMoi"
              :class="{ disabled: button.nhapmoi }"
            >
              <span class="icon one-file-plus"></span>Nhập mới
            </a>
          </li>
          <li>
            <a
              href="javascript:;"
              @click="handleBtnCapNhat"
              :class="{ disabled: button.capnhat }"
            >
              <span class="icon one-reload1"></span>Cập nhật
            </a>
          </li>
          <li>
            <a
              href="javascript:;"
              @click="handleBtnThemCS"
              :class="{ disabled: button.themCS }"
            >
              <span class="icon one-mobile-plus"></span>Thêm CS
            </a>
          </li>
          <li>
            <a
              href="javascript:;"
              @click="handleBtnXoa"
              :class="{ disabled: button.xoa }"
            >
              <span class="icon one-trash"></span>Xóa CS
            </a>
          </li>
          <li>
            <a
              href="javascript:;"
              @click="showLookUpModal"
              :class="{ disabled: button.tracuu }"
            >
              <span class="icon one-file-search"></span>Tra cứu
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin tìm kiếm</div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key">Số máy/Acc</div>
                <div class="value">
                  <input
                    type="text"
                    v-model="dbtb.ma_tb"
                    class="form-control highlight"
                    @keyup.enter="onMaTBKeyPress"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-9 col-12">
              <div class="info-row">
                <div class="key">Tên KH</div>
                <div class="value">
                  <input
                    disabled
                    type="text"
                    v-model="dbtb.ten_kh"
                    class="form-control"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key">Mã khách hàng</div>
                <div class="value">
                  <input
                    disabled
                    type="text"
                    v-model="dbtb.ma_kh"
                    ref="txtMaKH"
                    class="form-control"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-9 col-12">
              <div class="info-row">
                <div class="key">Địa chỉ</div>
                <div class="value">
                  <input
                    disabled
                    type="text"
                    v-model="dbtb.diachi_kh"
                    class="form-control"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Thông tin chăm sóc</div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w120">Loại đối tượng</div>
                <div class="value">
                  <div class="input-more-button">
                    <button class="btn" v-b-modal.frmDoiTuongCSKH>
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                    <div class="select-custom" ref="doituongcs">
                      <select2
                        :z-index="2000"
                        :settings="{ dropdownParent: $refs['doituongcs'] }"
                        v-model="ttkh.doituongcs_id"
                        class="select2"
                        :options="
                          ds_doituongcs.map((th) => ({
                            id: th.doituongcs_id,
                            text: th.tendoituongcs,
                          }))
                        "
                      ></select2>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-9 col-12">
              <div class="info-row">
                <div class="key">Tên ĐT (*)</div>
                <div class="value">
                  <input
                    type="text"
                    v-model="ttkh.tendoituong"
                    ref="txtTenDT"
                    class="form-control"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w120">Địa chỉ (*)</div>
            <div class="value">
              <!-- <input type="text" v-model="ttkh.diachi" class="form-control" /> -->
              <div class="input-more-button">
                <button class="btn" v-b-modal.popupdiachiDT>
                  <span class="-ap icon-more_horiz"></span>
                </button>
                <ModalChonDiaChi
                  modalId="popupdiachiDT"
                  :data="diachiDT"
                  @xacnhan="getdiachiDT"
                />
                <bss-error-marker :has-error="$v.diachiDT.DIACHI.$error">
                  <input
                    type="text"
                    v-model="$v.diachiDT.DIACHI.$model"
                    ref="txtDiaChiDT"
                    class="form-control disabled"
                    required
                    title="Bạn chưa nhập địa chỉ"
                  />
                </bss-error-marker>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-7 col-12">
              <div class="row">
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key w120">Ngày sinh (*)</div>
                    <div class="value">
                      <ejs-datepicker
                        v-model="ttkh.ngaysinh"
                        :value="today"
                        :max="today"
                        :format="'dd/MM/y'"
                        :allowEdit="true"
                        ref="txtNgaySN"
                        placeholder="__/__/____"
                      >
                      </ejs-datepicker>
                    </div>
                  </div>
                </div>
                <div class="col-sm-7 col-12">
                  <div class="list-checks mart5">
                    <div class="item">
                      <div class="check-action">
                        <input
                          type="radio"
                          class="check"
                          name="gioiTinh"
                          :value="1"
                          v-model="ttkh.gioitinh_id"
                        />
                        <span class="name">Nam</span>
                      </div>
                    </div>
                    <div class="item">
                      <div class="check-action">
                        <input
                          type="radio"
                          class="check"
                          name="gioiTinh"
                          :value="0"
                          v-model="ttkh.gioitinh_id"
                        />
                        <span class="name">Nữ</span>
                      </div>
                    </div>
                    <div class="item">
                      <div class="check-action">
                        <input
                          type="radio"
                          class="check"
                          name="kethon"
                          :value="0"
                          v-model="ttkh.kethon"
                        />
                        <span class="name">Chưa kết hơn</span>
                      </div>
                    </div>
                    <div class="item">
                      <div class="check-action">
                        <input
                          type="radio"
                          class="check"
                          name="kethon"
                          :value="1"
                          v-model="ttkh.kethon"
                        />
                        <span class="name">Đã kết hôn</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key w120">Quốc tịch</div>
                    <div class="value">
                      <div class="select-custom" ref="quoctich">
                        <select2
                          :z-index="2000"
                          :settings="{ dropdownParent: $refs['quoctich'] }"
                          v-model="cp_quoctich_id"
                          @change="onChangeQuocTich"
                          class="select2"
                          :options="
                            ds_quoctich.map((th) => ({
                              id: th.QUOCTICH_ID,
                              text: th.TENQUOCTICH,
                            }))
                          "
                        ></select2>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key w100">Dân tộc</div>
                    <div class="value">
                      <div class="select-custom" ref="dantoc">
                        <select2
                          :z-index="2000"
                          :settings="{ dropdownParent: $refs['dantoc'] }"
                          v-model="ttkh.dantoc_id"
                          class="select2"
                          :options="
                            ds_dantoc.map((th) => ({
                              id: th.DANTOC_ID,
                              text: th.TENDANTOC,
                            }))
                          "
                        ></select2>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key w120">Điện thoại</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        v-model="ttkh.dienthoai"
                      />
                    </div>
                  </div>
                </div>
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key w100">Fax</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        v-model="ttkh.fax"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-5 col-12">
                  <div class="info-row">
                    <div class="key w120">Email</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        v-model="ttkh.email"
                      />
                    </div>
                  </div>
                </div>
                <div class="col-sm-7 col-12">
                  <div class="info-row">
                    <div class="key w100">Ngành nghề</div>
                    <div class="value">
                      <div class="select-custom" ref="nganhnghe">
                        <select2
                          :z-index="2000"
                          :settings="{ dropdownParent: $refs['nganhnghe'] }"
                          v-model="ttkh.nganhnghe_id"
                          class="select2"
                          :options="
                            ds_nganhnghe.map((th) => ({
                              id: th.NGANHNGHE_ID,
                              text: th.TENNGANHNGHE,
                            }))
                          "
                        ></select2>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">Trình độ</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    v-model="ttkh.trinhdo"
                  />
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">Bộ phậm công tác</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    v-model="ttkh.bophancongtac"
                  />
                </div>
              </div>
              <div class="info-row">
                <div class="key w120">Ghi chú</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    v-model="ttkh.ghichu"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-5 col-12">
              <div
                class="box-form"
                style="border-radius: 0px; box-shadow: none"
              >
                <div class="legend-title">
                  <span class="title pull-left"> Sở thích </span>
                  <div class="pull-right">
                    <button
                      class="btn btn-second padt2 padb2"
                      v-b-modal.frmSoThichNV
                    >
                      <span class="-ap icon-plus3 normal"></span> Thêm sở thích
                    </button>
                  </div>
                  <div class="clearfix"></div>
                </div>
                <div
                  class="list-checks-box mart10"
                  style="height: 170px; overflow-y: scroll"
                >
                  <div
                    class="value"
                    v-for="item in ds_sothich"
                    :key="item.sothich_id"
                  >
                    <div class="item">
                      <div class="check-action">
                        <input
                          type="checkbox"
                          class="check"
                          name="sothich"
                          v-model="item.isChecked"
                          @change="changeCheckBox($event, item)"
                        />
                        <span class="name">{{ item.tensothich }}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách đối tượng chăm sóc</div>
          <DataGrid
            class="table-result table-gachle"
            :columns="ds_cskh.header"
            :dataSource="ds_cskh.list"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            :showColumnCheckbox="false"
            :enabledSelectFirstRow="true"
            ref="dscskh"
            @selectedItemsChanged="selectedDoiTuongCS_click"
          />
        </div>
      </div>

      <ModalLookUpCustomerCareInformation
        ref="ModalLookUpCustomerCareInformation"
      />
      <frmSoThichNV @onSoThichHide="getDSSoThich" />
      <frmDoiTuongCSKH @onDoiTuongHide="getDSDoiTuongCS" />
    </div>
  </b-modal>
</template>

<script>
import moment from "moment";
import api from "./API.js";
import DataGrid from "@/components/Controls/DataGrid";
import SelectExt from "@/components/Controls/SelectExt";
import BssErrorMarker from "@/components/BssErrorMarker";
import BssRequiredMarker from "@/components/BssRequiredMarker";
import { required, minValue, maxValue } from "vuelidate/lib/validators";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";
import ModalChonDiaChi from "@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi";
import ModalLookUpCustomerCareInformation from "./ModalLookUpCustomerCareInformation.vue";
import frmSoThichNV from "@/modules/CSKH/CapNhapThongTinCSKH/frmSoThichNV";
import frmDoiTuongCSKH from "@/modules/CSKH/CapNhapThongTinCSKH/frmDoiTuongCSKH";

export default {
  name: "CustomerCareInformationModal",
  components: {
    SelectExt,
    DataGrid,
    BssErrorMarker,
    BssRequiredMarker,
    DatePickerPlugin,
    ModalChonDiaChi,
    ModalLookUpCustomerCareInformation,
    frmSoThichNV,
    frmDoiTuongCSKH,
  },
  validations: {
    diachiDT: {
      DIACHI: {
        required,
      },
    },
  },
  data() {
    return {
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },
      button: {
        nhapmoi: true,
        capnhat: true,
        themCS: true,
        xoa: true,
        tracuu: true,
      },
      today: new Date(),
      ds_nganhnghe: [],
      ds_quoctich: [],
      ds_dantoc: [],
      ds_dantoc_all: [],
      ds_doituongcs: [],
      ds_sothich: [],
      dbtb: {
        ma_tb: "",
        ten_kh: "",
        ma_kh: "",
        diachi_kh: "",
      },
      ttkh: {
        phanvung_id: 0,
        thongtin_id: 0,
        ma_kh: "",
        doituongcs_id: 0,
        tendoituong: "",
        diachi: "",
        dienthoai: "",
        email: "",
        fax: "",
        ngaysinh: "",
        gioitinh_id: 1,
        quoctich_id: 0,
        dantoc_id: 0,
        nganhnghe_id: 0,
        trinhdo: "",
        bophancongtac: "",
        kethon: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        so_nha: "",
        donvi_id: 0,
        may_cn: "localhost",
        ngay_cn: new Date(),
        nguoi_cn: this.$root.token.getUserName(),
        ghichu: "",
        ma_kh_cs: "",
        tendoituongcs: "",
      },
      ds_cskh: {
        list: [],
        header: [
          {
            fieldName: "tendoituongcs",
            headerText: "Loại đối tượng",
            allowFiltering: true,
          },
          {
            fieldName: "tendoituong",
            headerText: "Tên đối tượng",
            allowFiltering: true,
          },
          {
            fieldName: "diachi",
            headerText: "Địa chỉ",
            allowFiltering: true,
          },
          {
            fieldName: "trinhdo",
            headerText: "Trình độ",
            allowFiltering: true,
          },
          {
            fieldName: "bophancongtac",
            headerText: "Bộ phận",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      diachiDT: {
        DIACHI: "",
        DIACHI_ID: 0,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: "",
        Lng: 0,
        Lat: 0,
        BLOCK: null,
        TANG: null,
        PHONG: null,
        MOTA: null,
      },
    };
  },
  created() {},
  computed: {
    cp_quoctich_id: {
      get() {
        return this.ttkh.quoctich_id;
      },
      set(value) {
        return (this.ttkh.quoctich_id = value);
      },
    },
  },
  methods: {
    addTTCSKH: async function (data) {
      var input = {
        vdsst_kh: data.vdsst_kh,
        vdstt_cskh: data.vdstt_cskh,
      };
      let response = await api.addTTCSKH(this.axios, input);
      if (response.data.error === 200 || response.data.error === "200") {
        await this.getDSCSKH(this.dbtb.ma_kh);
        this.ShowSuccess("Thêm dữ liệu thành công!");
      } else {
        this.ShowError("Thêm dữ liệu thất bại!");
      }
    },
    updateTTCSKH: async function (data) {
      var input = {
        vdsst_kh: data.vdsst_kh,
        vdstt_cskh: data.vdstt_cskh,
      };
      let response = await api.updateTTCSKH(this.axios, input);
      if (response.data.error === 200 || response.data.error === "200") {
        await this.getDSCSKH(this.dbtb.ma_kh);
        this.ShowSuccess("Cập nhật dữ liệu thành công!");
      } else {
        this.ShowError("Cập nhật dữ liệu thất bại!");
      }
    },
    deleteTTCSKH: async function (thongtin_id) {
      var input = {
        vthongtin_id: thongtin_id,
      };
      let response = await api.deleteTTCSKH(this.axios, input);
      if (response.data.error === 200 || response.data.error === "200") {
        this.ShowSuccess("Xóa dữ liệu thành công!");
      } else {
        this.ShowError("Xóa dữ liệu thất bại!");
      }
    },
    getDSNganhNghe: async function () {
      let data = this.GetData(await api.getDSNganhNghe(this.axios));
      this.ds_nganhnghe = data.sort(
        (a, b) => parseFloat(a.NGANHNGHE_ID) - parseFloat(b.NGANHNGHE_ID)
      );
    },
    getDSQuocTich: async function () {
      let data = this.GetData(await api.getDSQuocTich(this.axios));
      this.ds_quoctich = data.sort(
        (a, b) => parseFloat(a.QUOCTICH_ID) - parseFloat(b.QUOCTICH_ID)
      );
    },
    getDSDanToc: async function () {
      let data = this.GetData(await api.getDSDanToc(this.axios));
      this.ds_dantoc_all = data;
    },
    getDSDoiTuongCS: async function () {
      let data = this.GetData(await api.getDSDoiTuongCS(this.axios));
      this.ds_doituongcs = data.sort(
        (a, b) => parseFloat(a.doituongcs_id) - parseFloat(b.doituongcs_id)
      );
    },
    getDSSoThich: async function () {
      let data = this.GetData(await api.getDSSoThich(this.axios));
      data.forEach((e) => {
        e.isChecked = false;
      });
      this.ds_sothich = data.sort(
        (a, b) => parseFloat(a.sothich_id) - parseFloat(b.sothich_id)
      );
    },
    getDSSothichTheoDT: async function (thongtin_id) {
      var input = {
        vthongtin_id: thongtin_id,
      };
      let data = this.GetData(await api.getDSSothichTheoDT(this.axios, input));
      if (data.length > 0) {
        this.ds_sothich.forEach((e) => {
          const index = data.findIndex((x) => x.sothich_id == e.sothich_id);
          if (index > -1) {
            e.isChecked = true;
          } else {
            e.isChecked = false;
          }
        });
      } else {
        this.ds_sothich.forEach((e) => {
          e.isChecked = false;
        });
      }
    },
    getdiachiDT(data) {
      this["diachiDT"].DIACHI = data.diachimoi ? data.diachimoi : "";
      this["diachiDT"].TINH_ID = data.tinh_id ? data.tinh_id : 0;
      this["diachiDT"].QUAN_ID = data.quan_id ? data.quan_id : 0;
      this["diachiDT"].PHUONG_ID = data.phuong_id ? data.phuong_id : 0;
      this["diachiDT"].PHO_ID = data.pho_id ? data.pho_id : 0;
      this["diachiDT"].AP_ID = data.ap_id ? data.ap_id : 0;
      this["diachiDT"].KHU_ID = data.khu_id ? data.khu_id : 0;
      this["diachiDT"].DACDIEM_ID = data.dacdiem_id ? data.dacdiem_id : 0;
      this["diachiDT"].SO_NHA = data.so_nha ? data.so_nha : "";
      this["diachiDT"].Lat = data.lat ? data.lat : "";
      this["diachiDT"].Lng = data.lng ? data.lng : "";
      this.ttkh.diachi = data.diachimoi ? data.diachimoi : "";
      this.ttkh.phanvung_id = data.tinh_id ? data.tinh_id : 0;
      this.ttkh.quan_id = data.quan_id ? data.quan_id : 0;
      this.ttkh.phuong_id = data.phuong_id ? data.phuong_id : 0;
      this.ttkh.pho_id = data.pho_id ? data.pho_id : 0;
      this.ttkh.so_nha = data.so_nha ? data.so_nha : "";
    },
    getDBTB: async function (ma_tb) {
      var input = {
        ma_tb: ma_tb,
        donvi_dl_id: 0,
        dichvuvt_id: 0,
      };
      let data = this.GetData(await api.getDBTB(this.axios, input));
      if (data.length > 0) {
        if (data[0].ky_hd != 1) {
          this.setButton(0);
          this.clearData();
          this.$root.ShowError(
            "Khách hàng có mã " +
              ma_tb +
              " chưa ký hợp đồng gốc. Đề nghị bạn ký hợp đồng gốc trước khi lập phụ lục !"
          );
        } else {
          this.setButton(2);
          this.dbtb = data[0];
          this.getDSCSKH(data[0].ma_kh);
        }
      } else {
        this.ShowError("Không tìm thấy dữ liệu!");
        this.setButton(0);
        this.clearData();
      }
    },
    getDSCSKH: async function (ma_kh) {
      var input = {
        vma_kh: ma_kh,
      };
      let data = this.GetData(await api.getDSCSKH(this.axios, input));
      this.ds_cskh.list = data;
    },
    selectedDoiTuongCS_click: function (value) {
      this.ttkh = value[0];
      this["diachiDT"].DIACHI = value[0].diachi ? value[0].diachi : "";
      this["diachiDT"].TINH_ID = value[0].phanvung_id
        ? value[0].phanvung_id
        : 0;
      this["diachiDT"].QUAN_ID = value[0].quan_id ? value[0].quan_id : 0;
      this["diachiDT"].PHUONG_ID = value[0].phuong_id ? value[0].phuong_id : 0;
      this["diachiDT"].PHO_ID = value[0].pho_id ? value[0].pho_id : 0;
      this["diachiDT"].SO_NHA = value[0].so_nha ? value[0].so_nha : "";
      this.getDSSothichTheoDT(value[0].thongtin_id);
    },
    handleBtnNhapMoi: function () {
      this.setButton(1);
      this.ttkh = {
        phanvung_id: 0,
        thongtin_id: 0,
        ma_kh: "",
        doituongcs_id: 0,
        tendoituong: "",
        diachi: "",
        dienthoai: "",
        email: "",
        fax: "",
        ngaysinh: "",
        gioitinh_id: 1,
        quoctich_id: 1,
        dantoc_id: 1,
        nganhnghe_id: 0,
        trinhdo: "",
        bophancongtac: "",
        kethon: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        so_nha: "",
        donvi_id: 0,
        may_cn: "localhost",
        ngay_cn: new Date(),
        nguoi_cn: this.$root.token.getUserName(),
        ghichu: "",
        ma_kh_cs: "",
        tendoituongcs: "",
      };
      this.diachiDT = {
        DIACHI: "",
        DIACHI_ID: 0,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: "",
        Lng: 0,
        Lat: 0,
        BLOCK: null,
        TANG: null,
        PHONG: null,
        MOTA: null,
      };
      this.ds_sothich.forEach((e) => {
        e.isChecked = false;
      });
    },
    handleBtnCapNhat: async function () {
      try {
        this.$root.showLoading(true);
        if (!this.kiemTraDuLieu()) return;
        if (this.ttkh.thongtin_id > 0) {
          let data = this.taoDuLieu(false);
          await this.updateTTCSKH(data);
        }
      } catch (e) {
        this.ShowError(
          "Có lỗi trong quá trình xử lý: " +
            e.data.message +
            " " +
            e.data.message_detail
        );
      } finally {
        this.$root.showLoading(false);
      }
    },
    handleBtnThemCS: async function () {
      try {
        this.$root.showLoading(true);
        if (!this.kiemTraDuLieu()) return;
        if (this.ttkh.thongtin_id == 0) {
          let data = this.taoDuLieu(true);
          await this.addTTCSKH(data);
          this.setButton(2);
        }
      } catch (e) {
        console.log(e);
        this.ShowError(
          "Có lỗi trong quá trình xử lý: " +
            e.data.message +
            " " +
            e.data.message_detail
        );
      } finally {
        this.$root.showLoading(false);
      }
    },
    handleBtnXoa: async function () {
      if (this.ttkh.thongtin_id > 0) {
        this.$bvModal
          .msgBoxConfirm(
            "Bạn thật sự muốn xóa đối tượng " +
              this.ttkh.tendoituong +
              " không?",
            {
              title: "Thông báo!",
              size: "m",
              okTitle: "Đồng ý",
              cancelTitle: "Hủy",
            }
          )
          .then(async (v) => {
            if (v) {
              this.loading(true);
              await this.deleteTTCSKH(this.ttkh.thongtin_id);
              await this.getDSCSKH(this.dbtb.ma_kh);
              this.setButton(2);
              this.loading(false);
            }
          })
          .catch((e) => {
            this.ShowError(e);
            this.loading(false);
          });
      }
    },
    onMaTBKeyPress: function () {
      this.getDBTB(this.dbtb.ma_tb);
    },
    handleShowModal: async function () {
      this.loading(true);
      try {
        await Promise.all([
          this.setButton(0),
          this.clearData(),
          this.getDSDoiTuongCS(),
          this.getDSNganhNghe(),
          this.getDSQuocTich(),
          this.getDSDanToc(),
          this.getDSSoThich(),
        ]);
        this.ttkh.doituongcs_id = this.ds_doituongcs[0].doituongcs_id;
        this.ttkh.nganhnghe_id = this.ds_nganhnghe[0].NGANHNGHE_ID;
        this.ttkh.quoctich_id = this.ds_quoctich[0].QUOCTICH_ID;
        this.ds_dantoc = this.ds_dantoc_all.filter(
          (e) => e.QUOCTICH_ID == this.ds_quoctich[0].QUOCTICH_ID
        );
        this.ttkh.dantoc_id = this.ds_dantoc[0].DANTOC_ID;
        this.loading(false);
      } catch (ex) {
        this.loading(false);
      }
    },
    clearData: function () {
      this.dbtb = {
        ma_tb: "",
        ten_kh: "",
        ma_kh: "",
        diachi_kh: "",
      };
      this.ttkh = {
        phanvung_id: 0,
        thongtin_id: 0,
        ma_kh: "",
        doituongcs_id: 0,
        tendoituong: "",
        diachi: "",
        dienthoai: "",
        email: "",
        fax: "",
        ngaysinh: "",
        gioitinh_id: 1,
        quoctich_id: 1,
        dantoc_id: 1,
        nganhnghe_id: 0,
        trinhdo: "",
        bophancongtac: "",
        kethon: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        so_nha: "",
        donvi_id: 0,
        may_cn: "localhost",
        ngay_cn: new Date(),
        nguoi_cn: this.$root.token.getUserName(),
        ghichu: "",
        ma_kh_cs: "",
        tendoituongcs: "",
      };
      this.ds_cskh = {
        list: [],
        header: [
          {
            fieldName: "tendoituongcs",
            headerText: "Loại đối tượng",
            allowFiltering: true,
          },
          {
            fieldName: "tendoituong",
            headerText: "Tên đối tượng",
            allowFiltering: true,
          },
          {
            fieldName: "diachi",
            headerText: "Địa chỉ",
            allowFiltering: true,
          },
          {
            fieldName: "trinhdo",
            headerText: "Trình độ",
            allowFiltering: true,
          },
          {
            fieldName: "bophancongtac",
            headerText: "Bộ phận",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      };
      this.diachiDT = {
        DIACHI: "",
        DIACHI_ID: 0,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: "",
        Lng: 0,
        Lat: 0,
        BLOCK: null,
        TANG: null,
        PHONG: null,
        MOTA: null,
      };
      if (this.ds_sothich.length > 0) {
        this.ds_sothich.forEach((e) => {
          e.isChecked = false;
        });
      } else {
        this.ds_sothich = [];
      }
    },
    taoDuLieu(isThemMoi) {
      let data = {
        vdstt_cskh: [
          {
            THONGTIN_ID: isThemMoi ? 0 : Number(this.ttkh.thongtin_id),
            MA_KH: this.dbtb.ma_kh,
            DOITUONGCS_ID: this.ttkh.doituongcs_id
              ? Number(this.ttkh.doituongcs_id)
              : 0,
            TENDOITUONG: (this.ttkh.tendoituong + "").trim(),
            DIACHI: (this.ttkh.diachi + "").trim(),
            DIENTHOAI: (this.ttkh.dienthoai + "").trim(),
            EMAIL: (this.ttkh.email + "").trim(),
            FAX: (this.ttkh.fax + "").trim(),
            NGAYSINH: moment(this.ttkh.ngaysinh).format("DD/MM/YYYY"),
            GIOITINH_ID: Number(this.ttkh.gioitinh_id),
            QUOCTICH_ID: Number(this.ttkh.quoctich_id),
            DANTOC_ID: Number(this.ttkh.dantoc_id),
            NGANHNGHE_ID: Number(this.ttkh.nganhnghe_id),
            TRINHDO: (this.ttkh.trinhdo + "").trim(),
            BOPHANCONGTAC: (this.ttkh.bophancongtac + "").trim(),
            KETHON: Number(this.ttkh.kethon),
            QUAN_ID: Number(this.ttkh.quan_id),
            PHUONG_ID: Number(this.ttkh.phuong_id),
            PHO_ID: Number(this.ttkh.pho_id),
            SO_NHA: this.ttkh.so_nha,
            DONVI_ID: this.$auth.getDonViID()
              ? Number(this.$auth.getDonViID())
              : 0,
            GHICHU: (this.ttkh.ghichu + "").trim(),
            MAY_CN: this.$auth.getUserName(),
            NGUOI_CN: this.$auth.getUserName(),
          },
        ],
        vdsst_kh: this.ds_sothich
          .filter((x) => x.isChecked)
          .map((x) => ({
            THONGTIN_ID: isThemMoi ? 0 : this.ttkh.thongtin_id,
            SOTHICH_ID: x.sothich_id,
          })),
      };
      return data;
    },
    kiemTraDuLieu() {
      if ((this.dbtb.ma_kh.trim() + "").length == 0) {
        this.ShowError("Mã khách hàng không được trống !");
        this.focusInput("txtMaKH");
        return false;
      }
      if ((this.ttkh.tendoituong.trim() + "").length == 0) {
        this.ShowError("Hãy nhập tên đối đượng chăm sóc !");
        this.focusInput("txtTenDT");
        return false;
      }
      if ((this.ttkh.diachi + "").trim().length == 0) {
        this.ShowError("Hãy nhập địa chỉ đối đượng chăm sóc !");
        this.focusInput("txtDiaChiDT");
        return false;
      }
      if (!this.ttkh.ngaysinh) {
        this.ShowError(
          "Hãy nhập ngày sinh đối tượng chăm sóc (định dạng 'DD/MM/YYYY') !"
        );
        this.$refs.txtNgaySN.focusIn();
        return false;
      }
      return true;
    },
    onChangeQuocTich: function (value) {
      this.ttkh.dantoc_id = this.ds_dantoc_all.filter(
        (e) => e.QUOCTICH_ID == value
      )[0].DANTOC_ID;
    },
    changeCheckBox(event, item) {
      const index = this.ds_sothich.findIndex(
        (x) => x.sothich_id == item.sothich_id
      );
      if (index > -1) {
        this.ds_sothich[index].isChecked = event.target.checked;
      }
    },
    showModal: function () {
      this.$refs["popupCustomerCareInformationModal"].show();
    },
    hideModal: function () {
      this.$refs["popupCustomerCareInformationModal"].hide();
    },
    showLookUpModal: function () {
      this.$refs.ModalLookUpCustomerCareInformation.showModal();
    },
    focusInput(id) {
      this.$refs[id].focus();
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else {
        console.log(response.data.error_code);
        // this.ShowError(response.data.message);
      }
      return [];
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
    setButton: function (kieu) {
      this.button = {
        nhapmoi: true,
        capnhat: true,
        themCS: true,
        xoa: true,
        tracuu: true,
      };
      switch (kieu) {
        case 0: //Bat dau
          this.button.themCS = false;
          this.button.tracuu = false;
          break;
        case 1: //Them moi
          this.button.themCS = false;
          this.button.xoa = false;
          this.button.tracuu = false;
          break;
        case 2: //Search
          this.button.nhapmoi = false;
          this.button.xoa = false;
          this.button.capnhat = false;
          this.button.themCS = false;
          this.button.tracuu = false;
          break;
      }
    },
  },
  watch: {
    cp_quoctich_id: {
      handler: async function (newValue, oldValue) {
        if (this.ds_dantoc_all.length > 0) {
          this.ds_dantoc = this.ds_dantoc_all.filter(
            (e) => e.QUOCTICH_ID == newValue
          );
        }
      },
    },
  },
};
</script>
<style scoped>
.disabled {
  cursor: not-allowed;
  color: gray !important;
}
.select2 {
  display: table !important;
  width: 100% !important;
  table-layout: fixed !important;
}
</style>