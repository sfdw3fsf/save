<template>
  <b-modal
    ref="popupModalLookUpCustomerCareInformation"
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
            ><span class="icon one-notepad"></span>Tra cứu thông tin chăm sóc
            khách hàng</span
          >
        </div>
        <div
          @click="hideModal"
          class="close -ap icon-close"
          data-dismiss="modal"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript:;" @click="onTimKiemClicked">
              <span class="icon one-search"></span>Tìm kiếm
            </a>
          </li>
          <li>
            <a href="javascript:;">
              <downloadexcel
                :fields="exportExcelFields"
                :data="ds_cskh.list"
                worksheet="export"
                name="export.xls"
                :before-generate="onStartDownload"
              >
                <span class="icon one-excel-download"></span>Xuất Excel
              </downloadexcel>
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Chọn tiêu chí tìm kiếm</div>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w120">
                  <div class="check-action -color">
                    <input
                      type="checkbox"
                      class="check"
                      v-model="dbtb.loai_doituong.isChecked"
                      @change="getDSDoiTuongCS"
                    />
                    <span class="name">Loại đối tượng</span>
                  </div>
                </div>
                <div class="value">
                  <div class="select-custom" ref="doituongcs">
                    <select2
                      :z-index="2000"
                      :settings="{ dropdownParent: $refs['doituongcs'] }"
                      v-model="dbtb.loai_doituong.id"
                      class="select2"
                      :disabled="!dbtb.loai_doituong.isChecked"
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
            <div class="col-sm-8 col-12">
              <div class="info-row">
                <div class="key w120">
                  <div class="check-action -color">
                    <input
                      type="checkbox"
                      class="check"
                      v-model="dbtb.ten_doituong.isChecked"
                    />
                    <span class="name">Tên đối tượng</span>
                  </div>
                </div>
                <div class="value">
                  <input
                    type="text"
                    v-model="dbtb.ten_doituong.text"
                    class="form-control"
                    :disabled="!dbtb.ten_doituong.isChecked"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w120">
              <div class="check-action -color">
                <input
                  type="checkbox"
                  class="check"
                  v-model="dbtb.diachi.isChecked"
                />
                <span class="name">Địa chỉ</span>
              </div>
            </div>
            <div class="value">
              <div class="input-more-button">
                <button
                  class="btn"
                  :disabled="!dbtb.diachi.isChecked"
                  v-b-modal.popupDiaChiKH
                >
                  <span class="-ap icon-more_horiz"></span>
                </button>
                <ModalChonDiaChi
                  modalId="popupDiaChiKH"
                  :data="diachiKH"
                  @xacnhan="getdiachiKH"
                />
                <bss-error-marker :has-error="$v.diachiKH.DIACHI.$error">
                  <input
                    type="text"
                    v-model="$v.diachiKH.DIACHI.$model"
                    class="form-control disabled"
                    :disabled="!dbtb.diachi.isChecked"
                    required
                    title="Bạn chưa nhập địa chỉ"
                  />
                </bss-error-marker>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-8 col-12">
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="dbtb.ngaysinh.isChecked"
                        />
                        <span class="name">Ngày sinh</span>
                      </div>
                    </div>
                    <div class="value">
                      <ejs-datepicker
                        :enabled="dbtb.ngaysinh.isChecked"
                        v-model="dbtb.ngaysinh.text"
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
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="dbtb.gioitinh.isChecked"
                        />
                        <span class="name">Giới tính</span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="list-checks mart5">
                        <div class="item" style="width: 110px">
                          <div class="check-action">
                            <input
                              type="radio"
                              class="check"
                              name="gioiTinh"
                              :disabled="!dbtb.gioitinh.isChecked"
                              :value="1"
                              v-model="dbtb.gioitinh.id"
                            />
                            <span class="name">Nam</span>
                          </div>
                        </div>
                        <div class="item" style="width: 110px">
                          <div class="check-action">
                            <input
                              type="radio"
                              class="check"
                              name="gioiTinh"
                              :disabled="!dbtb.gioitinh.isChecked"
                              :value="0"
                              v-model="dbtb.gioitinh.id"
                            />
                            <span class="name">Nữ</span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="dbtb.quoctich.isChecked"
                          @change="getDSQuocTich"
                        />
                        <span class="name">Quốc tịch</span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="select-custom" ref="quoctich">
                        <select2
                          :z-index="2000"
                          :settings="{ dropdownParent: $refs['quoctich'] }"
                          v-model="dbtb.quoctich.id"
                          @change="onChangeQuocTich"
                          class="select2"
                          :disabled="!dbtb.quoctich.isChecked"
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
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="dbtb.honnhan.isChecked"
                        />
                        <span class="name">Hôn nhân</span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="list-checks mart5">
                        <div class="item" style="width: 110px">
                          <div class="check-action">
                            <input
                              type="radio"
                              class="check"
                              name="honnhan"
                              :disabled="!dbtb.honnhan.isChecked"
                              :value="0"
                              v-model="dbtb.honnhan.id"
                            />
                            <span class="name">Chưa kết hôn</span>
                          </div>
                        </div>
                        <div class="item" style="width: 110px">
                          <div class="check-action">
                            <input
                              type="radio"
                              class="check"
                              name="honnhan"
                              :disabled="!dbtb.honnhan.isChecked"
                              :value="1"
                              v-model="dbtb.honnhan.id"
                            />
                            <span class="name">Đã kết hôn</span>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="dbtb.dienthoai.isChecked"
                        />
                        <span class="name">Điện thoại</span>
                      </div>
                    </div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        :disabled="!dbtb.dienthoai.isChecked"
                        v-model="dbtb.dienthoai.text"
                      />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="dbtb.dantoc.isChecked"
                          @change="getDSDanToc"
                        />
                        <span class="name">Dân tộc</span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="select-custom" ref="dantoc">
                        <select2
                          :z-index="2000"
                          :settings="{ dropdownParent: $refs['dantoc'] }"
                          v-model="dbtb.dantoc.id"
                          class="select2"
                          :disabled="!dbtb.dantoc.isChecked"
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
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="dbtb.email.isChecked"
                        />
                        <span class="name">Email</span>
                      </div>
                    </div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        :disabled="!dbtb.email.isChecked"
                        v-model="dbtb.email.text"
                      />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="dbtb.fax.isChecked"
                        />
                        <span class="name">Fax</span>
                      </div>
                    </div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        :disabled="!dbtb.fax.isChecked"
                        v-model="dbtb.fax.text"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="dbtb.trinhdo.isChecked"
                        />
                        <span class="name">Trình độ</span>
                      </div>
                    </div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        :disabled="!dbtb.trinhdo.isChecked"
                        v-model="dbtb.trinhdo.text"
                      />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action -color">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="dbtb.nganhnghe.isChecked"
                          @change="getDSNganhNghe"
                        />
                        <span class="name">Ngành nghề</span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="select-custom" ref="nganhnghe">
                        <select2
                          :z-index="2000"
                          :settings="{ dropdownParent: $refs['nganhnghe'] }"
                          v-model="dbtb.nganhnghe.id"
                          class="select2"
                          :disabled="!dbtb.nganhnghe.isChecked"
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
                <div class="key w120">
                  <div class="check-action -color">
                    <input
                      type="checkbox"
                      class="check"
                      v-model="dbtb.bophanCT.isChecked"
                    />
                    <span class="name">Bộ phận CT</span>
                  </div>
                </div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    :disabled="!dbtb.bophanCT.isChecked"
                    v-model="dbtb.bophanCT.text"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="check-action -color marl10 marb10">
                <input
                  type="checkbox"
                  class="check"
                  v-model="dbtb.sothich.isChecked"
                  @change="getDSSoThich"
                />
                <span class="name">Sở thích</span>
              </div>
              <div class="box-form" style="border-radius: 0px; box-shadow: 0px">
                <div
                  class="list-checks-box"
                  style="height: 155px; overflow: auto"
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
                          :disabled="!dbtb.sothich.isChecked"
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
          />
        </div>
      </div>
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
import downloadexcel from "vue-json-excel";
import ModalChonDiaChi from "@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi";

export default {
  name: "ModalLookUpCustomerCareInformation",
  components: {
    SelectExt,
    DataGrid,
    DatePickerPlugin,
    BssErrorMarker,
    BssRequiredMarker,
    downloadexcel,
    ModalChonDiaChi,
  },
  validations: {
    diachiKH: {
      DIACHI: {
        required,
      },
    },
  },
  data() {
    return {
      target: ".main-wrapper",
      animationSettings: { effect: "Zoom" },
      today: new Date(),
      ds_nganhnghe: [],
      ds_quoctich: [],
      ds_dantoc: [],
      ds_dantoc_all: [],
      ds_doituongcs: [],
      ds_sothich: [],
      dbtb: {
        loai_doituong: {
          id: 0,
          isChecked: false,
        },
        ten_doituong: {
          text: "",
          isChecked: false,
        },
        diachi: {
          text: "",
          isChecked: false,
        },
        ngaysinh: {
          text: "",
          isChecked: false,
        },
        gioitinh: {
          id: 2,
          isChecked: false,
        },
        quoctich: {
          id: 0,
          isChecked: false,
        },
        honnhan: {
          id: 2,
          isChecked: false,
        },
        dienthoai: {
          text: "",
          isChecked: false,
        },
        dantoc: {
          id: 0,
          isChecked: false,
        },
        email: {
          text: "",
          isChecked: false,
        },
        fax: {
          text: "",
          isChecked: false,
        },
        trinhdo: {
          id: 0,
          isChecked: false,
        },
        nganhnghe: {
          id: 0,
          isChecked: false,
        },
        bophanCT: {
          text: "",
          isChecked: false,
        },
        sothich: {
          isChecked: false,
        },
      },
      diachiKH: {
        DIACHI: "",
        DIACHI_ID: 0,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: null,
        Lng: 0,
        Lat: 0,
        BLOCK: null,
        TANG: null,
        PHONG: null,
        MOTA: null,
      },
      ds_cskh: {
        list: [],
        header: [
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
            fieldName: "email",
            headerText: "Email",
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
      exportExcelFields: {
        THONGTIN_ID: "thongtin_id",
        MA_KH: "ma_kh",
        DOITUONGCS_ID: "doituongcs_id",
        TENDOITUONG: "tendoituong",
        DIACHI: "diachi",
        DIENTHOAI: "dienthoai",
        EMAIL: "email",
        FAX: "fax",
        NGAYSINH: "ngaysinh",
        GIOITINH_ID: "gioitinh_id",
        QUOCTICH_ID: "quoctich_id",
        DANTOC_ID: "dantoc_id",
        NGANHNGHE_ID: "nganhnghe_id",
        TRINHDO: "trinhdo",
        BOPHANCONGTAC: "bophancongtac",
        KETHON: "kethon",
        QUAN_ID: "quan_id",
        PHUONG_ID: "phuong_id",
        PHO_ID: "pho_id",
        SO_NHA: "so_nha",
        DONVI_ID: "donvi_id",
        MAY_CN: "may_cn",
        NGAY_CN: "ngay_cn",
        NGUOI_CN: "nguoi_cn",
        GHICHU: "ghichu",
        MA_KH_CS: "ma_kh_cs",
        SOTHICH_ID: "sothich_id",
      },
    };
  },
  methods: {
    getDSDoiTuongCS: async function () {
      if (this.dbtb.loai_doituong.isChecked) {
        let data = this.GetData(await api.getDSDoiTuongCS(this.axios));
        this.ds_doituongcs = data.sort(
          (a, b) => parseFloat(a.doituongcs_id) - parseFloat(b.doituongcs_id)
        );
        this.dbtb.loai_doituong.id = data[0].doituongcs_id;
      }
    },
    getDSQuocTich: async function () {
      if (this.dbtb.quoctich.isChecked) {
        let data = this.GetData(await api.getDSQuocTich(this.axios));
        this.ds_quoctich = data.sort(
          (a, b) => parseFloat(a.QUOCTICH_ID) - parseFloat(b.QUOCTICH_ID)
        );
        this.dbtb.quoctich.id = data[0].QUOCTICH_ID;
        if (this.dbtb.quoctich.isChecked) {
          this.getDSDanToc();
        }
      }
    },
    getDSDanToc: async function () {
      if (this.dbtb.dantoc.isChecked) {
        let data = this.GetData(await api.getDSDanToc(this.axios));
        this.ds_dantoc_all = data;
        if (this.dbtb.quoctich.isChecked) {
          this.ds_dantoc = data.filter(
            (e) => e.QUOCTICH_ID == this.dbtb.quoctich.id
          );
          this.dbtb.dantoc.id = this.ds_dantoc[0].DANTOC_ID;
        } else {
          this.ds_dantoc = data;
          this.dbtb.dantoc.id = data[0].DANTOC_ID;
        }
      }
    },
    getDSNganhNghe: async function () {
      if (this.dbtb.nganhnghe.isChecked) {
        let data = this.GetData(await api.getDSNganhNghe(this.axios));
        this.ds_nganhnghe = data.sort(
          (a, b) => parseFloat(a.NGANHNGHE_ID) - parseFloat(b.NGANHNGHE_ID)
        );
        this.dbtb.nganhnghe.id = data[0].NGANHNGHE_ID;
      }
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
    changeCheckBox: function (event, item) {
      const index = this.ds_sothich.findIndex(
        (x) => x.sothich_id == item.sothich_id
      );
      if (index > -1) {
        this.ds_sothich[index].isChecked = event.target.checked;
      }
    },
    onChangeQuocTich: function (value) {
      if (this.dbtb.dantoc.isChecked) {
        this.ds_dantoc = this.ds_dantoc_all.filter(
          (e) => e.QUOCTICH_ID == value
        );
        this.dbtb.dantoc.id = this.ds_dantoc[0].DANTOC_ID;
      }
    },
    getdiachiKH: function (data) {
      this["diachiKH"].DIACHI = data.diachimoi;
      this["diachiKH"].TINH_ID = data.tinh_id;
      this["diachiKH"].QUAN_ID = data.quan_id;
      this["diachiKH"].PHUONG_ID = data.phuong_id;
      this["diachiKH"].PHO_ID = data.pho_id;
      this["diachiKH"].AP_ID = data.ap_id;
      this["diachiKH"].KHU_ID = data.khu_id;
      this["diachiKH"].DACDIEM_ID = data.dacdiem_id;
      this["diachiKH"].SO_NHA = data.so_nha;
      this["diachiKH"].Lat = data.lat;
      this["diachiKH"].Lng = data.lng;
    },
    onTimKiemClicked: async function () {
      await this.traCuu();
    },
    onStartDownload: function () {
      if (this.ds_cskh.list.length == 0) {
        this.$root.toastError("Không có dữ liệu để xuất Excel !");
        return;
      }
    },
    async traCuu() {
      try {
        this.ds_cskh.list = [];
        this.$root.showLoading(true);
        let data = {};
        data.VDOITUONGCS_ID = this.dbtb.loai_doituong.isChecked
          ? this.dbtb.loai_doituong.id
          : 0;
        data.VTENDOITUONG = this.dbtb.ten_doituong.isChecked
          ? this.dbtb.ten_doituong.text.trim()
          : "";
        data.VDIACHI = this.dbtb.diachi.isChecked
          ? this.diachiKH.DIACHI.trim()
          : "";
        data.VDIENTHOAI = this.dbtb.dienthoai.isChecked
          ? this.dbtb.dienthoai.text.trim()
          : "";
        data.VEMAIL = this.dbtb.email.isChecked
          ? this.dbtb.email.text.trim()
          : "";
        data.VFAX = this.dbtb.fax.isChecked ? this.dbtb.fax.text.trim() : "";
        if (this.dbtb.ngaysinh.isChecked) {
          let date = moment(this.dbtb.ngaysinh.text, "DD/MM/YYYY", true);
          if (date.isValid()) {
            data.VNGAYSINH = moment(this.dbtb.ngaysinh.text).format(
              "DD/MM/YYYY"
            );
          } else {
            this.ShowError("Ngày sinh không hợp lệ!");
            return;
          }
        } else {
          data.VNGAYSINH = "";
        }
        data.VGIOITINH_ID = this.dbtb.gioitinh.isChecked
          ? this.dbtb.gioitinh.id
          : 2;
        data.VQUOCTICH_ID = this.dbtb.quoctich.isChecked
          ? this.dbtb.quoctich.id
          : 0;
        data.VDANTOC_ID = this.dbtb.dantoc.isChecked ? this.dbtb.dantoc.id : 0;
        data.VNGANHNGHE_ID = this.dbtb.nganhnghe.isChecked
          ? this.dbtb.nganhnghe.id
          : 0;
        data.VTRINHDO = this.dbtb.trinhdo.isChecked
          ? this.dbtb.trinhdo.text.trim()
          : "";
        data.VBOPHANCONGTAC = this.dbtb.bophanCT.isChecked
          ? this.dbtb.bophanCT.text.trim()
          : "";
        data.VKETHON = this.dbtb.honnhan.isChecked ? this.dbtb.honnhan.id : 2;
        data.VGHICHU = "";
        if (this.dbtb.sothich.isChecked)
          data.VDSSOTHICH_ID = this.ds_sothich
            .filter((x) => x.isChecked)
            .map((x) => x.sothich_id)
            .join();
        else {
          data.VDSSOTHICH_ID = "";
        }
        let response = await this.$root.context.post(
          "/web-tracuu/thongtin_cskh/tra_cuu_thongtin_cskh",
          data
        );
        if (response.error_code === "BSS-00000000") {
          this.ds_cskh.list = response.data || [];
        }
      } catch (e) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    handleShowModal: async function () {
      this.loading(true);
      try {
        await Promise.all([this.getDSSoThich()]);
        this.loading(false);
      } catch (ex) {
        this.loading(false);
      }
    },
    showModal: function () {
      this.$refs["popupModalLookUpCustomerCareInformation"].show();
    },
    hideModal: function () {
      this.$refs["popupModalLookUpCustomerCareInformation"].hide();
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