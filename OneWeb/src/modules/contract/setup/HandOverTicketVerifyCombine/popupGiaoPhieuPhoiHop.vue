<template>
  <b-modal
    ref="popupGiaoPhieuPhoiHop"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Giao phiếu xác minh, phối hợp
          giao việc giữa các đơn vị
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
            <a href="javascript:;" @click="tsbtnTimKiem_Click">
              <span class="icon one-search"></span> Tìm kiếm
            </a>
          </li>
          <li>
            <a href="javascript:;" @click="tsbtnGiaoPhieu_Click">
              <span class="icon one-file-arrow-right1"></span> Giao phiếu
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="title padt5 padb25 bold h5">
          GIAO PHIẾU XÁC MINH, PHỐI HỢP
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title">Thông tin tìm kiếm</div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w80">Ngày BĐ</div>
                    <div class="value">
                      <DatePicker
                        ref="dtpTuNgay"
                        :format="dateFormat"
                        :value-type="'format'"
                        v-model="dtpTuNgay.value"
                        @change="onChangeTuNgay"
                      >
                      </DatePicker>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w80">Ngày KT</div>
                    <div class="value">
                      <DatePicker
                        ref="dtpDenNgay"
                        :format="dateFormat"
                        :value-type="'format'"
                        v-model="dtpDenNgay.value"
                        @change="onChangeDenNgay"
                      >
                      </DatePicker>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w80">
                      <div class="check-action">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="chkTKDichVuVT.Checked"
                        />
                        <span class="name">Dịch vụ</span>
                      </div>
                    </div>
                    <div class="value">
                      <select2
                        class="select2"
                        :z-index="2000"
                        :disabled="!cboDichVuVT.Enabled"
                        v-model="cboDichVuVT.value"
                        :options="
                          cboDichVuVT.DataSource.map((e) => ({
                            id: e.DICHVUVT_ID,
                            text: e.TEN_DVVT,
                          }))
                        "
                      />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title">Thông tin thuê bao</div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w80">Số máy/Acc</div>
                    <div class="value">
                      <input
                        ref="txtMaTB"
                        type="text"
                        class="form-control"
                        v-bind:value="txtMaTB.value"
                        @change="(e) => (txtMaTB.value = e.target.value)"
                      />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w80">Tên TB</div>
                    <div class="value">
                      <input
                        ref="txtTenTB"
                        type="text"
                        class="form-control"
                        v-bind:value="txtTenTB.value"
                        @change="(e) => (txtTenTB.value = e.target.value)"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w80">Địa chỉ LĐ</div>
                    <div class="value">
                      <input
                        ref="txtDiaChiTB"
                        type="text"
                        class="form-control"
                        v-bind:value="txtDiaChiTB.value"
                        @change="(e) => (txtDiaChiTB.value = e.target.value)"
                      />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-12 col-12">
            <div class="box-form">
              <div class="legend-title">Thông tin giao phiếu</div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="row">
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Hướng giao</div>
                        <div class="value">
                          <select2
                            class="select2"
                            :z-index="2000"
                            v-model="cboHuongGiao.value"
                            :options="
                              cboHuongGiao.DataSource.map((e) => ({
                                id: e.HUONGGIAO_ID,
                                text: e.HUONGGIAO,
                              }))
                            "
                          />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-6 col-12">
                      <div class="info-row">
                        <div class="key w80">Ngày giao</div>
                        <div class="value">
                          <DatePicker
                            ref="dtpNgayGiao"
                            :format="dateFormat"
                            :value-type="'format'"
                            v-model="dtpNgayGiao.value"
                            :disabled="!dtpNgayGiao.Enabled"
                            @change="onChangeNgayGiao"
                          >
                          </DatePicker>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-sm-12 col-12">
                      <div class="info-row">
                        <div class="key w80">Đơn vị nhận</div>
                        <div class="value">
                          <select2
                            class="select2"
                            :z-index="2000"
                            v-model="ccbDonViNhan.value"
                            :options="
                              ccbDonViNhan.DataSource.map((e) => ({
                                id: e.DONVI_ID,
                                text: e.TEN_DV,
                              }))
                            "
                          />
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w80">Nội dung giao</div>
                    <div class="value">
                      <textarea
                        ref="txtNoiDung"
                        class="form-control"
                        style="height: 100px; resize: vertical"
                        v-bind:value="txtNoiDung.value"
                        @change="(e) => (txtNoiDung.value = e.target.value)"
                      ></textarea>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title">Danh sách phiếu yêu cầu</div>
              <DataGrid
                class="table-result table-gachle"
                ref="gridDSChuaGiao"
                v-bind:columns="gridDSChuaGiao.header"
                v-bind:dataSource="gridDSChuaGiao.list"
                :enable-paging-server="false"
                :allowPaging="true"
                :showFilter="true"
                :showColumnCheckbox="false"
                @selectedItemsChanged="gridDSChuaGiao_FocusedRowChanged"
              >
              </DataGrid>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title">Danh sách phiếu đã giao đơn vị</div>
              <DataGrid
                class="table-result table-gachle"
                ref="gridDSDaGiao"
                v-bind:columns="gridDSDaGiao.header"
                v-bind:dataSource="gridDSDaGiao.list"
                :enable-paging-server="false"
                :allowPaging="true"
                :showFilter="true"
                :showColumnCheckbox="false"
                :commands="[
                  {
                    name: 'xoa',
                    cssClass: 'btn btn-danger',
                    width: 20,
                    text: 'X',
                    title: 'Xóa',
                  },
                ]"
                :commandColumn="{
                  headerText: '',
                  width: 80,
                  cellCssClass: 'css1',
                  firstColumn: false,
                  show: true,
                }"
                @commandClicked="tsbtnHuyGiaoPhieu_Click"
              >
              </DataGrid>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import moment from "moment";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import api from "./API.js";
import DataGrid from "@/components/Controls/DataGrid";

export default {
  name: "popupGiaoPhieuPhoiHop",
  components: { DatePicker, DataGrid },
  props: {
    vhdtb_id_cur: {
      type: Number,
      default: 0,
    },
    vphieu_id_cur: {
      type: Number,
      default: 0,
    },
    vquytrinh_id: {
      type: Number,
      default: 0,
    },
    vnguoigiao_id: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      tt_nd: {
        phanvung_id: 0,
        nhanvien_id: 0,
        may_cn: "",
        nguoi_cn: "",
        ip_cn: "",
        ngay_cn: moment(new Date()).format("DD/MM/YYYY"),
      },
      dateFormat: "DD/MM/YYYY",
      dtpTuNgay: { value: "" },
      dtpDenNgay: { value: "" },
      dtpNgayGiao: { value: "", Enabled: false },
      txtMaTB: { value: "" },
      txtTenTB: { value: "" },
      txtDiaChiTB: { value: "" },
      txtNoiDung: { value: "" },
      chkTKDichVuVT: { Checked: false },
      cboDichVuVT: {
        value: -1,
        DataSource: [],
        Enabled: false,
      },
      cboHuongGiao: {
        value: -1,
        DataSource: [],
      },
      ccbDonViNhan: {
        value: -1,
        DataSource: [],
      },
      phieu_id: 0,
      hdtb_id: 0,
      nhanvien_giao_id: 0,
      gridDSDaGiao: {
        list: [],
        header: [
          {
            fieldName: "PHIEU_ID",
            headerText: "ID phiếu",
            allowFiltering: true,
          },
          {
            fieldName: "ND_GIAO",
            headerText: "Nội dung giao",
            allowFiltering: true,
          },
          {
            fieldName: "NGAYGIAO",
            headerText: "Ngày giao",
            allowFiltering: true,
          },
          {
            fieldName: "NGUOIGIAO",
            headerText: "Người giao",
            allowFiltering: true,
          },
          {
            fieldName: "TEN_DV",
            headerText: "Đơn vị nhận",
            allowFiltering: true,
          },
          {
            fieldName: "KETQUA_XL",
            headerText: "KQ xử lý",
            allowFiltering: true,
          },
          {
            fieldName: "ND_THUCHIEN",
            headerText: "ND thực hiện",
            allowFiltering: true,
          },
          {
            fieldName: "NV_THUCHIEN",
            headerText: "Người thực hiện",
            allowFiltering: true,
          },
          {
            fieldName: "NGAY_TH",
            headerText: "Ngày thực hiện",
            allowFiltering: true,
          },
          {
            fieldName: "HUONGGIAO",
            headerText: "Hướng giao",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
      gridDSChuaGiao: {
        list: [],
        header: [
          {
            fieldName: "PHIEU_ID",
            headerText: "ID phiếu",
            allowFiltering: true,
          },
          {
            fieldName: "MA_TB",
            headerText: "Số máy/Acc",
            allowFiltering: true,
          },
          {
            fieldName: "TEN_TB",
            headerText: "Tên TB",
            allowFiltering: true,
          },
          {
            fieldName: "NGAYGIAO",
            headerText: "Ngày giao",
            allowFiltering: true,
          },
          {
            fieldName: "NGUOIGIAO",
            headerText: "Người giao",
            allowFiltering: true,
          },
          {
            fieldName: "DV_NHAN",
            headerText: "Đơn vị nhận",
            allowFiltering: true,
          },
          {
            fieldName: "ND_GIAO",
            headerText: "Nội dung giao",
            allowFiltering: true,
          },
          {
            fieldName: "TRANGTHAI",
            headerText: "Trạng thái",
            allowFiltering: true,
          },
          {
            fieldName: "NGAY_TH",
            headerText: "Ngày thực hiện",
            allowFiltering: true,
          },
          {
            fieldName: "NGUOI_TH",
            headerText: "Người thực hiện",
            allowFiltering: true,
          },
          {
            fieldName: "ND_THUCHIEN",
            headerText: "Nội dung thực hiện",
            allowFiltering: true,
          },
          {
            fieldName: "NGAYTRA",
            headerText: "Ngày trả",
            allowFiltering: true,
          },
          {
            fieldName: "ND_TRA",
            headerText: "Nội dung trả",
            allowFiltering: true,
          },
          {
            fieldName: "LYDO_TON",
            headerText: "Lý do tồn",
            allowFiltering: true,
          },
          {
            fieldName: "HUONGGIAO",
            headerText: "Hướng giao",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
      },
    };
  },
  methods: {
    clear() {
      this.dtpTuNgay.value = "";
      this.dtpDenNgay.value = "";
      this.cboDichVuVT.value = -1;
      this.txtMaTB.value = "";
      this.txtTenTB.value = "";
      this.txtDiaChiTB.value = "";
      this.cboHuongGiao.value = -1;
      this.dtpNgayGiao.value = "";
      this.ccbDonViNhan.DataSource = [];
      this.txtNoiDung.value = "";
      this.gridDSChuaGiao.list = [];
      this.gridDSDaGiao.list = [];
      this.chkTKDichVuVT.Checked = false;
    },
    async frmGiaoPhieu_PH_Load() {
      try {
        var tungay = moment(
          new Date(new Date().getFullYear(), new Date().getMonth(), 1)
        ).format("DD/MM/YYYY");
        this.dtpTuNgay.value = tungay; //"19/11/2021"; //tungay;
        this.dtpDenNgay.value = this.tt_nd.ngay_cn; //"22/11/2021"; //this.tt_nd.ngay_cn;
        this.dtpNgayGiao.value = this.tt_nd.ngay_cn;

        await Promise.all([this.LoadDichVuVT(), this.LoadHuongGiao()]);
        if (this.cboHuongGiao.value != -1)
          await this.HienThiDanhSach_ChuaGiao();
      } catch (exp) {
        if (exp.data && exp.data.message_detail)
          this.$toast.error("" + exp.data.message_detail);
        else this.$toast.error("" + exp);
      }
    },
    async LoadDichVuVT() {
      let response = await api.sp_giaophieu_ph_loaddichvuvt(
        this.axios,
        this.vquytrinh_id
      );
      if (
        response &&
        response.data &&
        response.data.error_code == "BSS-00000000" &&
        response.data.data
      ) {
        var data = this.UpperCasePropertyList(response.data.data);
        this.cboDichVuVT.DataSource = data;
        if (this.cboDichVuVT.DataSource.length > 0) {
          this.cboDichVuVT.value = this.cboDichVuVT.DataSource[0].DICHVUVT_ID;
        }
      } else if (
        response &&
        response.data &&
        response.data.error_code == "BSS-00000204"
      ) {
        this.$toast.error("Không có DS dịch vụ, hãy liên hệ admin!");
      } else {
        this.$toast.error("Lỗi lấy DS dịch vụ " + response.data.message_detail);
      }
    },
    async LoadHuongGiao() {
      let response = await api.sp_lay_ds_huonggiao_ph(
        this.axios,
        this.vquytrinh_id
      );

      if (
        response &&
        response.data &&
        response.data.error_code == "BSS-00000000" &&
        response.data.data
      ) {
        var data = this.UpperCasePropertyList(response.data.data);
        this.cboHuongGiao.DataSource = data;
        this.cboHuongGiao.value = this.cboHuongGiao.DataSource[0].HUONGGIAO_ID;
      } else if (
        response &&
        response.data &&
        response.data.error_code == "BSS-00000204"
      ) {
        this.$toast.error(
          "Quy trình chưa được thiết lập hướng giao, hãy liên hệ admin!"
        );
      } else {
        this.$toast.error(
          "Lỗi lấy DS hướng giao " + response.data.message_detail
        );
      }
    },
    async LayDanhSachDonViNhan() {
      var input = {
        huonggiao_id: Number(this.cboHuongGiao.value),
        quytrinh_id: this.vquytrinh_id,
        phieu_id: this.phieu_id,
        nhanvien_id: this.tt_nd.nhanvien_id,
      };
      let response = await api.sp_lay_ds_donvi_nhan_phieu_ph(this.axios, input);
      if (
        response &&
        response.data &&
        response.data.error_code == "BSS-00000000" &&
        response.data.data
      ) {
        var data = this.UpperCasePropertyList(response.data.data);
        this.ccbDonViNhan.DataSource = data;
      } else if (
        response &&
        response.data &&
        response.data.error_code == "BSS-00000204"
      ) {
        this.$toast.error("Không có DS đơn vị nhận, hãy liên hệ admin!");
      } else {
        this.$toast.error(
          "Lỗi lấy DS đơn vị nhận " + response.data.message_detail
        );
      }
    },
    async HienThiDanhSach_ChuaGiao() {
      this.gridDSChuaGiao.list = [];

      let dichvuvt_id = 0;
      if (this.chkTKDichVuVT.Checked) {
        dichvuvt_id = Number(this.cboDichVuVT.value);
      }

      var params = {
        huonggiao_id: Number(this.cboHuongGiao.value),
        quytrinh_id: this.vquytrinh_id,
        nhanvien_id: this.tt_nd.nhanvien_id,
        dichvuvt_id: Number(dichvuvt_id),
        tungay: this.dtpTuNgay.value,
        denngay: this.dtpDenNgay.value,
      };
      let response = await api.sp_lay_ds_hdtb_phieu_ph(this.axios, params);
      if (
        response &&
        response.data &&
        response.data.error_code == "BSS-00000000"        
      ) {
        if (response.data.data) {
          this.gridDSChuaGiao.list = this.UpperCasePropertyList(
            response.data.data
          );
        } else {
          this.$toast.error("Không có phiếu chưa giao!");
        }       
      } else if (
        response &&
        response.data &&
        response.data.error_code == "BSS-00000204"
      ) {
        this.$toast.error("Không có phiếu chưa giao!");
      } else {
        this.$toast.error(
          "Lỗi lấy DS chưa giao " + response.data.message_detail
        );
      }
    },
    async gridDSChuaGiao_FocusedRowChanged(value) {
      try {
        if (this.gridDSChuaGiao.list.length > 0 && value.length > 0) {
          this.txtMaTB.value = value[0]["MA_TB"];
          this.txtTenTB.value = value[0]["TEN_TB"];
          this.txtDiaChiTB.value = value[0]["DIACHI_LD"];
          this.phieu_id = Number(value[0]["PHIEU_ID"]);
          this.hdtb_id = Number(value[0]["HDTB_ID"]);
          this.nhanvien_giao_id = Number(value[0]["NHANVIEN_GIAO_ID"]);
          this.txtNoiDung.value = "";
          this.dtpNgayGiao.value = this.tt_nd.ngay_cn;
          this.ccbDonViNhan.value = -1;

          await Promise.all([
            this.LayDanhSachDonViNhan(),
            this.HienThiDanhSach_DaGiao(),
          ]);
        }
      } catch (exp) {
        if (exp.data && exp.data.message_detail)
          this.$toast.error("Có lỗi xảy ra: " + exp.data.message_detail);
        else this.$toast.error("Có lỗi xảy ra: " + exp);
      }
    },
    async HienThiDanhSach_DaGiao() {
      this.gridDSDaGiao.list = [];
      var params = {
        phieu_cha_id: this.phieu_id,
        nhanvien_giao_id: 0,
      };
      let response = await api.lay_danhsach_phieu_ph_dagiao(this.axios, params);
      if (
        response &&
        response.data &&
        response.data.error_code == "BSS-00000000"        
      ) {
        if (response.data.data) this.gridDSDaGiao.list = this.UpperCasePropertyList(response.data.data);
      } else if (
        response &&
        response.data &&
        response.data.error_code == "BSS-00000204"
      ) {
        // this.$toast.error("Không có DS đã giao!");
      } else {
        this.$toast.error("Lỗi lấy DS đã giao " + response.data.message_detail);
      }
    },
    chkTKDichVuVT_CheckedChanged(value) {
      try {
        if (value) {
          this.cboDichVuVT.Enabled = true;
        } else {
          this.cboDichVuVT.Enabled = false;
        }
      } catch (exp) {
        this.$toast.error("Có lỗi xảy ra: " + exp.toString());
      }
    },
    onChangeTuNgay(value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.dtpTuNgay.value = "01/01/2000";
      } else {
        this.dtpTuNgay.value = value_;
      }
    },
    onChangeDenNgay(value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.dtpDenNgay.value = "01/01/2000";
      } else {
        this.dtpDenNgay.value = value_;
      }
    },
    onChangeNgayGiao(value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.dtpNgayGiao.value = "01/01/2000";
      } else {
        this.dtpNgayGiao.value = value_;
      }
    },
    async cboHuongGiao_EditValueChanged() {
      try {
        this.loading(true);
        if (this.cboHuongGiao.value != -1)
          await this.HienThiDanhSach_ChuaGiao();
      } catch (ex) {
        this.$toast.error(ex);
      } finally {
        this.loading(false);
      }
    },
    async GiaoPhieu() {
      try {
        if (this.txtNoiDung.value.trim() == "") {
          this.$toast.error("Hãy nhập nội dung giao");
          this.$refs.txtNoiDung.focus();
          return;
        }
        if (!this.ccbDonViNhan.value || this.ccbDonViNhan.value == -1) {
          this.$toast.error("Hãy chọn đơn vị nhận");
          return;
        }
        var input = {
          phieu_id: this.phieu_id,
          huonggiao_id: Number(this.cboHuongGiao.value),
          quytrinh_id: this.vquytrinh_id,
          donvi_nhan_id: Number(this.ccbDonViNhan.value),
          nhanvien_giao_id: this.tt_nd.nhanvien_id,
          noidung: this.txtNoiDung.value,
          may_cn: this.tt_nd.may_cn,
          nguoi_cn: this.tt_nd.nguoi_cn,
          ip_cn: this.tt_nd.ip_cn,
        };
        let response = await api.fn_giaophieu_ph(this.axios, input);
        if (
          response &&
          response.data &&
          response.data.error_code == "BSS-00000000"
        ) {
          if (response.data.data == "OK") {
            this.$toast.success("Đã giao phiếu thành công");
            await this.HienThiDanhSach_DaGiao();
          } else {
            this.$toast.error(response.data.data);
          }
        } else {
          this.$toast.error(response.data.message_detail);
        }
      } catch (exp) {
        if (exp.data && exp.data.message_detail)
          this.$toast.error("Có lỗi xảy ra: " + exp.data.message_detail);
        else this.$toast.error("Có lỗi xảy ra: " + exp);
      }
    },
    async tsbtnHuyGiaoPhieu_Click(cmdName, rowData) {
      this.$bvModal
        .msgBoxConfirm("Bạn chắc chắn muốn xóa phiếu?", {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            this.loading(true);
            let response = await api.fn_huygiao_ph_thicong(
              this.axios,
              rowData.PHIEU_ID
            );
            if (
              response &&
              response.data &&
              response.data.error_code == "BSS-00000000"
            ) {
              if (response.data.data == "OK") {
                this.$toast.success("Hủy giao phiếu thành công");
                await this.HienThiDanhSach_DaGiao();
              } else {
                this.$toast.error(response.data.data);
              }
            } else {
              this.$toast.error(response.data.message_detail);
            }
            this.loading(false);
          }
        })
        .catch((exp) => {
          if (exp.data && exp.data.message_detail)
            this.$toast.error("Có lỗi xảy ra: " + exp.data.message_detail);
          else this.$toast.error("Có lỗi xảy ra: " + exp);
          this.loading(false);
        });
    },
    async tsbtnGiaoPhieu_Click() {
      try {
        this.loading(true);
        await this.GiaoPhieu();
      } catch (ex) {
        this.$toast.error("Có lỗi xảy ra: " + ex);
      } finally {
        this.loading(false);
      }
    },
    async tsbtnTimKiem_Click() {
      try {
        if (this.cboHuongGiao.value == -1) {
          this.$toast.error("Bạn chưa chọn hướng giao!");
          return;
        }
        this.loading(true);
        await this.HienThiDanhSach_ChuaGiao();
      } catch (ex) {
        this.$toast.error("Có lỗi xảy ra: " + ex);
      } finally {
        this.loading(false);
      }
    },
    showModal: async function () {
      this.clear();
      this.$refs["popupGiaoPhieuPhoiHop"].show();
      this.loading(true);
      try {
        this.tt_nd.phanvung_id = Number(this.$root.token.getPhanVungID());
        this.tt_nd.nhanvien_id = this.vnguoigiao_id
          ? Number(this.vnguoigiao_id)
          : Number(this.$root.token.getNhanVienID());
        this.tt_nd.may_cn = await this.$root.token.getMachine();
        this.tt_nd.nguoi_cn = await this.$root.token.getMaNhanVien();
        this.tt_nd.ip_cn = await this.$root.token.getIP();

        await Promise.all([this.frmGiaoPhieu_PH_Load()]);
        this.loading(false);
      } catch (ex) {
        this.loading(false);
      }
    },
    hideModal: function () {
      this.$refs["popupGiaoPhieuPhoiHop"].hide();
    },
    GetData: function (response) {
      if (response.data && response.data.error_code == "BSS-00000000") {
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
    LowerCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toLowerCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
    UpperCasePropertyList(obj) {
      return obj.map(function (item) {
        for (var key in item) {
          var upper = key.toUpperCase();
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key];
            delete item[key];
          }
        }
        return item;
      });
    },
  },
  mounted() {},
  computed: {},
  watch: {
    "chkTKDichVuVT.Checked"(val) {
      this.chkTKDichVuVT_CheckedChanged(val);
    },
  },
};
</script>

<style scoped>
.disabled {
  cursor: not-allowed;
  color: gray !important;
}
.mx-datepicker {
  width: 100% !important;
}
.select2 {
  display: table !important;
  width: 100% !important;
  table-layout: fixed !important;
}
</style>