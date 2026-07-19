<template>
  <b-modal
    ref="popupModalNormalizeCAinformation"
    id="frmTimKiemHDTB"
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
          <span class="icon one-notepad"></span> Tìm kiếm hợp đồng CNTT
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
            <a href="javascript:;" @click="btnChapNhan_click">
              <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp
              nhận
            </a>
          </li>
          <li>
            <a href="javascript:;" @click="btnTimKiem_click">
              <span class="icon one-search"></span>Tìm kiếm
            </a>
          </li>
          <li>
            <a href="javascript:;">
              <span class="icon one-download"></span>Lấy thông tin từ Token
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">
            <span
              @click="isShowForm = !isShowForm"
              class="icon fa"
              :class="{
                'fa-angle-up': isShowForm,
                'fa-angle-down': !isShowForm,
              }"
            ></span>
            Thông tin tìm kiếm
          </div>
          <div class="info-row" v-if="isShowForm">
            <div class="key" style="width: 180px">Serial Number</div>
            <div class="value">
              <input type="text" class="form-control" v-model="form.serial" />
            </div>
          </div>
          <div class="info-row" v-if="isShowForm">
            <div class="key" style="width: 180px">Tên thuê bao</div>
            <div class="value">
              <input type="text" class="form-control" v-model="form.ten_tb" />
            </div>
          </div>
          <div class="info-row" v-if="isShowForm">
            <div class="key" style="width: 180px">Mã số thuế (Thuê bao)</div>
            <div class="value">
              <input type="text" class="form-control" v-model="form.mst_tb" />
            </div>
          </div>
          <div class="info-row" v-if="isShowForm">
            <div class="key" style="width: 180px">Số giấy tờ (Thuê bao)</div>
            <div class="value">
              <input type="text" class="form-control" v-model="form.sogt_tb" />
            </div>
          </div>
          <div class="info-row" v-if="isShowForm">
            <div class="key" style="width: 180px">Số điện thoại (Thuê bao)</div>
            <div class="value">
              <input type="text" class="form-control" v-model="form.sdt_tb" />
            </div>
          </div>
          <div class="info-row" v-if="isShowForm">
            <div class="key" style="width: 180px">Tên khách hàng</div>
            <div class="value">
              <input type="text" class="form-control" v-model="form.ten_kh" />
            </div>
          </div>
          <div class="info-row" v-if="isShowForm">
            <div class="key" style="width: 180px">Mã số thuế (Khách hàng)</div>
            <div class="value">
              <input type="text" class="form-control" v-model="form.mst_kh" />
            </div>
          </div>
          <div class="info-row" v-if="isShowForm">
            <div class="key" style="width: 180px">Số giấy tờ (Khách hàng)</div>
            <div class="value">
              <input type="text" class="form-control" v-model="form.sogt_kh" />
            </div>
          </div>
          <div class="info-row" v-if="isShowForm">
            <div class="key" style="width: 180px">
              Số điện thoại (Khách hàng)
            </div>
            <div class="value">
              <input type="text" class="form-control" v-model="form.sdt_kh" />
            </div>
          </div>
          <div class="info-row" v-if="isShowForm">
            <div class="key" style="width: 180px">USB TOKEN INFOR</div>
            <div class="value">
              <input
                type="text"
                class="form-control"
                disabled
                v-model="form.usb_token_info"
              />
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Kết quả tìm kiếm</div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w30 nowrap">Mã giao dịch</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    disabled
                    v-model="result_magd"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w30 nowrap">Mã thuê bao</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    disabled
                    v-model="result_matb"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w30 nowrap">Tên khách hàng</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    disabled
                    v-model="result_tenkh"
                  />
                </div>
              </div>
            </div>
          </div>
          <DataGrid
            class="table-result table-gachle"
            :columns="grcKetQuaTimKiem.header"
            :dataSource="grcKetQuaTimKiem.list"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            :showColumnCheckbox="false"
            :enabledSelectFirstRow="true"
            ref="grcKetQuaTimKiem"
            @selectedItemsChanged="selectedHDTB_click"
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

export default {
  name: "ModalNormalizeCAinformation",
  components: { DataGrid },
  props: {
    data: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      isShowForm: true,
      ketqua: {},
      result_magd: "",
      result_matb: "",
      result_tenkh: "",
      form: {
        serial: "",
        ten_tb: "Ủy Ban Nhân Dân Xã Đông Sơn",
        mst_tb: "",
        sogt_tb: "",
        sdt_tb: "",
        ten_kh: "",
        mst_kh: "",
        sogt_kh: "",
        sdt_kh: "",
        usb_token_info: "",
      },
      grcKetQuaTimKiem: {
        list: [],
        header: [
          {
            fieldName: "serial",
            headerText: "Serial Number",
            allowFiltering: true,
          },
          {
            fieldName: "ten_tb",
            headerText: "Tên thuê bao",
            allowFiltering: true,
          },
          {
            fieldName: "ten_kh",
            headerText: "Tên khách hàng",
            allowFiltering: true,
          },
          {
            fieldName: "ngay_ky_hd",
            headerText: "Ngày ký HĐ",
            allowFiltering: true,
            type: "date",
            format: "dd/MM/y",
          },
          {
            fieldName: "ngay_bd",
            headerText: "Ngày bắt đầu",
            allowFiltering: true,
            type: "date",
            format: "dd/MM/y",
          },
          {
            fieldName: "ngay_kt",
            headerText: "Ngày kết thúc",
            allowFiltering: true,
            type: "date",
            format: "dd/MM/y",
          },
          {
            fieldName: "so_thang",
            headerText: "Số tháng",
            allowFiltering: true,
            type: "Number",
            format: "N0",
          },
          {
            fieldName: "so_dt",
            headerText: "Số ĐT Thuê bao",
            allowFiltering: true,
          },
          {
            fieldName: "sodt_kh",
            headerText: "Số ĐT Khách hàng",
            allowFiltering: true,
          },
          {
            fieldName: "mst",
            headerText: "Mã số thuế TB",
            allowFiltering: true,
          },
          {
            fieldName: "mst_kh",
            headerText: "Mã số thuế KH",
            allowFiltering: true,
          },
          {
            fieldName: "so_gt",
            headerText: "Số giấy tờ",
            allowFiltering: true,
          },
          {
            fieldName: "sogt_kh",
            headerText: "Số giấy tờ KH",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
    };
  },
  async created() {
    this.loading(true);
    try {
      Promise.all([]);
      this.loading(false);
    } catch (ex) {
      this.loading(false);
    }
    this.loading(false);
  },
  computed: {},
  methods: {
    getds_timkiem_tb: async function () {
      var input = {
        vserial: this.form.serial ? this.form.serial : "",
        vtentb: this.form.ten_tb ? this.form.ten_tb : "",
        vmsttb: this.form.mst_tb ? this.form.mst_tb : "",
        vsogttb: this.form.sogt_tb ? this.form.sogt_tb : "",
        vsodttb: this.form.sdt_tb ? this.form.sdt_tb : "",
        vtenkh: this.form.ten_kh ? this.form.ten_kh : "",
        vmstkh: this.form.mst_kh ? this.form.mst_kh : "",
        vsogtkh: this.form.sogt_kh ? this.form.sogt_kh : "",
        vsdtkh: this.form.sdt_kh ? this.form.sdt_kh : "",
      };
      let data = this.GetData(await api.getds_timkiem_tb(this.axios, input));
      if (data.length > 0) {
        this.grcKetQuaTimKiem.list = data;
      } else {
        this.grcKetQuaTimKiem.list = [];
        this.ShowError("Không tìm thấy thông tin hợp đồng thê bao!");
      }
    },
    btnTimKiem_click: function () {
      this.loading(true);
      this.getds_timkiem_tb();
      this.loading(false);
    },
    btnChapNhan_click: function () {
      if (!this.ketqua || this.ketqua.length == 0) {
        return;
      } else {
        this.$emit("xacnhan", this.ketqua);
        this.$bvModal.hide("frmTimKiemHDTB");
      }
    },
    selectedHDTB_click: function (value) {
      if (value.length > 0) {
        this.form.serial = value[0].serial ? value[0].serial : "";
        this.form.ten_tb = value[0].ten_tb ? value[0].ten_tb : "";
        this.form.mst_tb = value[0].mst ? value[0].mst : "";
        this.form.sogt_tb = value[0].so_gt ? value[0].so_gt : "";
        this.form.sdt_tb = value[0].so_dt ? value[0].so_dt : "";
        this.form.ten_kh = value[0].ten_kh ? value[0].ten_kh : "";
        this.form.mst_kh = value[0].mst_kh ? value[0].mst_kh : "";
        this.form.sogt_kh = value[0].sogt_kh ? value[0].sogt_kh : "";
        this.form.sdt_kh = value[0].sodt_kh ? value[0].sodt_kh : "";
        this.result_magd = value[0].ma_gd ? value[0].ma_gd : "";
        this.result_matb = value[0].ma_tb ? value[0].ma_tb : "";
        this.result_tenkh = value[0].ten_kh ? value[0].ten_kh : "";
        this.ketqua = value[0];
      }
    },
    khoitaodulieu: function () {
      this.result_magd = "";
      this.result_matb = "";
      this.result_tenkh = "";
      this.form = {
        serial: "",
        ten_tb: "",
        mst_tb: "",
        sogt_tb: "",
        sdt_tb: "",
        ten_kh: "",
        mst_kh: "",
        sogt_kh: "",
        sdt_kh: "",
        usb_token_info: "",
      };
      this.grcKetQuaTimKiem.list = [];
      this.ketqua = {};
    },
    handleShowModal: async function () {
      this.loading(true);
      try {
        await Promise.all([this.khoitaodulieu()]);
        this.loading(false);
      } catch (ex) {
        this.loading(false);
      }
    },
    showModal: function () {
      this.$refs["popupModalNormalizeCAinformation"].show();
    },
    hideModal: function () {
      this.$refs["popupModalNormalizeCAinformation"].hide();
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else {
        // console.log(response.data.error_code);
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
</style>
