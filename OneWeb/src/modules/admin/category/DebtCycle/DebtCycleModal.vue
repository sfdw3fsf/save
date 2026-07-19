<template>
  <b-modal
    ref="popupDebtCycleModal"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Cập nhật chu kỳ nợ
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="hideModal"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li
            v-on:click="handleButtonCreate"
            :class="{ disabled: !button.nhapmoi }"
          >
            <a :class="{ disabled: !button.nhapmoi }">
              <span class="icon nc-icon-glyph ui-1_circle-add"></span> Nhập mới
            </a>
          </li>
          <li
            v-on:click="handleButtonSave"
            :class="{ disabled: !button.ghilai }"
          >
            <a :class="{ disabled: !button.ghilai }">
              <span class="icon one-save"></span> Ghi lại
            </a>
          </li>
          <li v-on:click="handleButtonHuy" :class="{ disabled: !button.huybo }">
            <a :class="{ disabled: !button.huybo }">
              <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy
            </a>
          </li>
          <li v-on:click="handleButtonXoa" :class="{ disabled: !button.xoa }">
            <a :class="{ disabled: !button.xoa }">
              <span class="icon one-trash"></span> Xóa
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin chu kỳ nợ</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <app-ky-cuoc
                  ref="cboKyHoaDon"
                  :value="kyhoadon"
                  :chukyno="chuky"
                  :isCreate="input.is_insert == 1 ? true : false"
                  @onChuKyNoChange="handleKyHoaDon"
                ></app-ky-cuoc>
              </div>
              <div class="info-row">
                <div class="key w80"></div>
                <div class="value">
                  <div class="list-checks-box">
                    <div class="item marb10 mart10">
                      <div class="check-action">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="is_GachNo"
                        />
                        <span class="name">Không cho phép gạch nợ</span>
                      </div>
                    </div>
                    <div class="item marb10 mart10">
                      <div class="check-action">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="is_PhieuTra"
                        />
                        <span class="name">Khóa bảng phiếu trả</span>
                      </div>
                    </div>
                    <div class="item marb10 mart10">
                      <div class="check-action">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="is_Cttra"
                        />
                        <span class="name">Khóa bảng chi tiết trả</span>
                      </div>
                    </div>
                    <div class="item marb10 mart10">
                      <div class="check-action">
                        <input
                          type="checkbox"
                          class="check"
                          v-model="is_Ctno"
                        />
                        <span class="name">Khóa bảng chi tiết nợ</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w80">Ngày BĐ</div>
                <div class="value">
                  <!-- <ejs-datepicker
                    v-model="ngay_bd"
                    :format="dateFormat"
                    :allowEdit="true"
                    :showClearButton="true"
                    @change="changeNgayBD"
                  >
                  </ejs-datepicker> -->
                  <DatePicker
                    :format="dateFormat"
                    :value-type="'format'"
                    v-model="ngay_bd"
                    :input-attr="{
                      required: 'true',
                      title: 'Bạn chưa nhập ngày bắt đầu!',
                    }"
                    @change="changeNgayBD"
                  ></DatePicker>
                </div>
              </div>

              <div class="info-row">
                <div class="key w80">Ngày KT</div>
                <div class="value">
                  <!-- <ejs-datepicker
                    v-model="ngay_kt"
                    :format="dateFormat"
                    :allowEdit="true"
                    :showClearButton="true"
                    @change="changeNgayKT"
                  >
                  </ejs-datepicker> -->
                  <DatePicker
                    :format="dateFormat"
                    :value-type="'format'"
                    v-model="ngay_kt"
                    :input-attr="{
                      required: 'true',
                      title: 'Bạn chưa nhập ngày kết thúc!',
                    }"
                    @change="changeNgayKT"
                  ></DatePicker>
                </div>
              </div>
              <div class="info-row">
                <div class="key w80">Ghi chú</div>
                <div class="value">
                  <textarea
                    v-model="params.chuthich"
                    class="form-control"
                    style="height: 100px; resize: vertical"
                  ></textarea>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách chu kỳ nợ</div>
          <DataGrid
            class="table-result table-gachle"
            v-bind:columns="cp_dschukyno.header"
            v-bind:dataSource="cp_dschukyno.list"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            :showColumnCheckbox="false"
            ref="dschukyno"
            :rowIndex="rowIndex"
            @rowClicked="getRowClick"
            @selectedItemsChanged="selectedItems_click"
          >
          </DataGrid>
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
import KyCuoc from "./KyCuoc.vue";
import EventBus from "@/utils/eventBus";

export default {
  name: "DebtCycleModal",
  components: { DatePicker, DataGrid, appKyCuoc: KyCuoc },
  props: {
    data: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      button: {
        nhapmoi: true,
        ghilai: true,
        xoa: true,
        huybo: true,
      },
      dateFormat: "DD/MM/YYYY",
      isGachNo: false,
      isPhieuTra: false,
      isCttra: false,
      isCtno: false,
      p_kyhoadon: moment(new Date()).add(-1, "M").format("YYYYMMDD").toString(),
      input: {
        is_insert: 0,
        data: "",
      },
      chuky: "",
      params: {
        chukyno: "",
        noton: 0,
        ngay_cn: "",
        nguoi_cn: this.$root.token.getUserName(),
        may_cn: "localhost",
        chuthich: "",
        ngay_bd: "",
        ngay_kt: "",
        status: 0,
        khoa_bpt: 1,
        khoa_ctt: 1,
        khoa_ctn: 1,
        nam: "",
        thang: "",
        chukyno_vnp: "",
      },
      themmoi: false,
      init: [],
      ds_chukyno: {
        list: [],
        header: [
          {
            fieldName: "thangno",
            headerText: "Tháng nợ",
            allowFiltering: true,
            width: 100,
          },
          {
            fieldName: "chuky",
            headerText: "Chu kỳ",
            allowFiltering: true,
            width: 100,
          },
          {
            fieldName: "bang_pt",
            headerText: "Bảng PT",
            allowFiltering: true,
            width: 100,
          },
          {
            fieldName: "bang_cttra",
            headerText: "Bảng CTTrả",
            allowFiltering: true,
            width: 100,
          },
          {
            fieldName: "bang_ctno",
            headerText: "Bảng nợ",
            allowFiltering: true,
            width: 100,
          },
          {
            fieldName: "trangthai",
            headerText: "Trạng thái",
            allowFiltering: true,
          },
          {
            fieldName: "chuthich",
            headerText: "Ghi chú",
            allowFiltering: true,
          },
        ],
        value: {},
        isEnabled: true,
        checked: [],
        rowIndex: 0,
      },
    };
  },
  async created() {
    this.loading(true);
    try {
      this.rowIndex = 0
      Promise.all([this.getDsChuKyNo()]);
      EventBus.$on("chukycuocChange", this.handleKyHoaDon);
      this.loading(false);
    } catch (ex) {
      this.loading(false);
    }
    this.loading(false);
  },
  computed: {
    kyhoadon: {
      get() {
        return this.p_kyhoadon;
      },
      set(value) {
        return (this.p_kyhoadon = value);
      },
    },
    ngay_bd: {
      get() {
        return this.params.ngay_bd;
      },
      set(value) {
        return (this.params.ngay_bd = value);
      },
    },
    ngay_kt: {
      get() {
        return this.params.ngay_kt;
      },
      set(value) {
        return (this.params.ngay_kt = value);
      },
    },
    is_GachNo: {
      get() {
        return this.isGachNo;
      },
      set(value) {
        return (
          (this.isGachNo = value),
          value == false ? (this.params.status = 0) : (this.params.status = 1)
        );
      },
    },
    is_PhieuTra: {
      get() {
        return this.isPhieuTra;
      },
      set(value) {
        return (
          (this.isPhieuTra = value),
          value == false
            ? (this.params.khoa_bpt = 1)
            : (this.params.khoa_bpt = 0)
        );
      },
    },
    is_Cttra: {
      get() {
        return this.isCttra;
      },
      set(value) {
        return (
          (this.isCttra = value),
          value == false
            ? (this.params.khoa_ctt = 1)
            : (this.params.khoa_ctt = 0)
        );
      },
    },
    is_Ctno: {
      get() {
        return this.isCtno;
      },
      set(value) {
        return (
          (this.isCtno = value),
          value == false
            ? (this.params.khoa_ctn = 1)
            : (this.params.khoa_ctn = 0)
        );
      },
    },
    cp_dschukyno: {
      get() {
        return this.ds_chukyno;
      },
      set(value) {
        return (this.ds_chukyno = value);
      },
    },
  },
  methods: {
    handleKyHoaDon(e) {
      this.params.chukyno = "";
      this.params.nam = "";
      this.params.thang = "";
      if (e.p_kyhoadon && e.p_chuky_no) {
        var chukyno = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;

        this.params.chukyno = chukyno;
        this.params.nam = moment(chukyno).format("YYYY");
        this.params.thang = moment(chukyno).format("MM");
      }
    },
    getDsChuKyNo: async function () {
      var input = {};
      let data = this.GetData(await api.ds_chukyno(this.axios, input));
      this.ds_chukyno.list = data;
      this.init = data;
      if (!data || data.length < 0) {
        this.SetButton(0);
      }
    },
    changeNgayBD: function (value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.ngay_bd = "01/01/2000";
      } else {
        this.ngay_bd = value_;
      }
    },
    changeNgayKT: function (value_) {
      if (!moment(value_, "DD/MM/YYYY").isValid()) {
        this.ngay_kt = "01/01/2000";
      } else {
        this.ngay_kt = value_;
      }
    },
    handleButtonCreate: function () {
      this.SetButton(1);
      this.themmoi = true;
      var date = new Date();
      var kyhoadon = moment(
        new Date(date.getFullYear(), date.getMonth(), 1)
      ).add(-1, "M");

      this.p_kyhoadon = kyhoadon.format("YYYYMMDD");
      this.isGachNo = false;
      this.isPhieuTra = false;
      this.isCttra = false;
      this.isCtno = false;
      this.input = {
        is_insert: 1,
        data: "",
      };
      this.params = {
        chukyno: this.p_kyhoadon,
        noton: 0,
        ngay_cn: moment(new Date()).format("DD/MM/YYYY").toString(),
        nguoi_cn: this.$root.token.getUserName(),
        may_cn: "localhost",
        chuthich: "",
        ngay_bd: moment(new Date()).format("DD/MM/YYYY").toString(),
        ngay_kt: moment(new Date()).format("DD/MM/YYYY").toString(),
        status: 0,
        khoa_bpt: 1,
        khoa_ctt: 1,
        khoa_ctn: 1,
        nam: kyhoadon.format("YYYY"),
        thang: kyhoadon.format("MM"),
        chukyno_vnp:
          Number(kyhoadon.format("YYYY")) * 13 + Number(kyhoadon.format("MM")),
      };
    },
    handleButtonHuy: async function () {
      var blank = [];
      this.loading(true);
      this.themmoi = false;
      this.cp_dschukyno.list = await blank;
      this.cp_dschukyno.list = await this.init;
      this.input.is_insert = 0;
      this.chuky = this.init[0].chuky;
      this.params = this.init[0];
      this.params && this.params.length > 0
        ? this.SetButton(2)
        : this.SetButton(0);
      this.p_kyhoadon = this.params.chukyno.toString();
      Number(this.init[0].status) == 0
        ? (this.isGachNo = false)
        : (this.isGachNo = true);
      Number(this.init[0].khoa_bpt) == 1
        ? (this.isPhieuTra = false)
        : (this.isPhieuTra = true);
      Number(this.init[0].khoa_ctt) == 1
        ? (this.isCttra = false)
        : (this.isCttra = true);
      Number(this.init[0].khoa_ctn) == 1
        ? (this.isCtno = false)
        : (this.isCtno = true);
      this.loading(false);
    },
    handleButtonSave: async function () {
      this.$bvModal
        .msgBoxConfirm("Bạn thật sự muốn cập nhật dữ liệu không?", {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            this.loading(true);
            try {
              let check = await this.validate();
              if (check == true) {
                await this.saveChuKyNo();
                await this.getDsChuKyNo();
                this.themmoi = false;
              }
            } catch (e) {
              if (e.data && e.data.message_detail) {
                this.ShowError(e.data.message_detail);
              } else {
                this.ShowError("Có lỗi trong quá trình cập nhật dữ liệu");
              }
            }
            this.loading(false);
          }
        })
        .catch((e) => {
          this.ShowError("Lỗi cập nhật " + e);
          this.loading(false);
        });
    },
    handleButtonXoa: async function () {
      this.$bvModal
        .msgBoxConfirm("Bạn thật sự muốn xóa dữ liệu không ?", {
          title: "Thông báo",
          size: "sm",
          okTitle: "Đồng ý",
          cancelTitle: "Hủy",
        })
        .then(async (v) => {
          if (v) {
            this.loading(true);
            await this.xoaChuKyNo();
            this.themmoi = false;
            this.loading(false);
          }
        })
        .catch((e) => {
          this.ShowError("Lỗi xóa dữ liệu " + e);
          this.loading(false);
        });
    },
    saveChuKyNo: async function () {
      var chukyno = [
        {
          CHUKYNO: this.params.chukyno.toString(),
          NOTON: this.params.noton.toString(),
          NGAY_CN: moment(new Date()).format("DD/MM/YYYY"),
          NGUOI_CN: this.$root.token.getUserName(),
          MAY_CN: "localhost",
          CHUTHICH: this.params.chuthich ? this.params.chuthich : "",
          NGAY_BD: this.params.ngay_bd,
          NGAY_KT: this.params.ngay_kt,
          STATUS: this.params.status.toString(),
          KHOA_BPT: this.params.khoa_bpt.toString(),
          KHOA_CTT: this.params.khoa_ctt.toString(),
          KHOA_CTN: this.params.khoa_ctn.toString(),
          NAM: this.params.nam
            ? this.params.nam
            : moment(this.params.chukyno, "YYYYMMDD").format("YYYY"),
          THANG: this.params.thang
            ? this.params.thang
            : moment(this.params.chukyno, "YYYYMMDD").format("MM"),
          CHUKYNO_VNP: this.params.chukyno_vnp
            ? this.params.chukyno_vnp.toString()
            : "",
        },
      ];
      this.input.data = JSON.stringify(chukyno);
      let response = await api.update_chukyno(this.axios, this.input);
      if (response.data.error === 200 || response.data.error === "200") {
        if (this.themmoi) {
          this.ShowSuccess("Thêm mới thành công!");
        } else this.ShowSuccess("Cập nhật thành công!");
      } else {
        this.ShowError(response.data.message);
      }
    },
    xoaChuKyNo: async function () {
      var input = {
        chukyno: this.params.chukyno.toString(),
      };

      let response = await api.delete_chukyno(this.axios, input);
      if (
        response &&
        response.data &&
        response.data.error_code == "BSS-00000000"
      ) {
        this.ShowSuccess("Xoá dữ liệu trên viễn thông tỉnh thành công!");
        await this.getDsChuKyNo();
      } else {
        this.ShowError(response.data.message);
      }
    },
    validate: async function () {
      if (!this.params.ngay_bd || this.params.ngay_bd == "") {
        this.ShowError("Bạn chưa nhập ngày bắt đầu!");
        return false;
      }
      if (!this.params.ngay_kt || this.params.ngay_kt == "") {
        this.ShowError("Bạn chưa nhập ngày kết thúc!");
        return false;
      }
      if (
        moment(this.params.ngay_bd, "DD/MM/YYYY") >
        moment(this.params.ngay_kt, "DD/MM/YYYY")
      ) {
        this.ShowError("Ngày bắt đầu phải bé hơn bằng ngày kết thúc!");
        return false;
      }
      if (this.themmoi) {
        if (this.ds_chukyno.list && this.ds_chukyno.list.length > 0) {
          for (var i = 0; i < this.ds_chukyno.list.length; i++) {
            if (
              this.params.chukyno.toString() ==
              this.ds_chukyno.list[i].chukyno.toString()
            ) {
              this.ShowError("Chu kỳ nợ đã tồn tại, bạn không được thêm mới!");
              return false;
            }
          }
        }
      }
      var check_tg_trung = await this.Kiemtra_chukyno();
      if (!check_tg_trung) return false;
      return true;
    },
    Kiemtra_chukyno: async function () {
      try {
        var input = {
          vngay_bd: this.params.ngay_bd,
          vngay_kt: this.params.ngay_kt,
          vchukyno: this.params.chukyno.toString(),
        };
        let response = await api.kiemtra_chukyno(this.axios, input);
        if (response.data && response.data.error_code == "BSS-00000000") {
          if (response.data.data == "0") return true;
          else {
            this.ShowError("Dữ liệu thời gian bị trùng. Hãy kiểm tra lại!");
            return false;
          }
        } else {
          this.ShowError("Lỗi kiểm tra chu kỳ nợ " + response.data.message);
          return false;
        }
      } catch (e) {
        if (e.data && e.data.message)
          this.ShowError("Lỗi kiểm tra chu kỳ nợ " + e.data.message);
        else this.ShowError("Lỗi kiểm tra chu kỳ nợ " + e);
        return false;
      }
    },
    showModal: function () {
      this.$refs["popupDebtCycleModal"].show();
    },
    hideModal: function () {
      this.$refs["popupDebtCycleModal"].hide();
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === "200") {
        return response.data.data;
      } else {
        // console.log(response.data.error_code);
        this.ShowError(response.data.message);
      }
      return [];
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg);
    },
    ShowError: function (msg) {
      this.$toast.error(msg);
    },
    getRowClick: function (value) {
      if (value) {
        this.rowIndex = value.rowIndex
      }     
    },
    selectedItems_click: function (val) {
      val && val.length > 0 ? this.SetButton(3) : this.SetButton(0);
      this.input.is_insert = 0;
      this.chuky = val[0].chuky;
      this.params = val[0];
      this.p_kyhoadon = this.params.chukyno.toString();
      Number(val[0].status) == 0
        ? (this.isGachNo = false)
        : (this.isGachNo = true);
      Number(val[0].khoa_bpt) == 1
        ? (this.isPhieuTra = false)
        : (this.isPhieuTra = true);
      Number(val[0].khoa_ctt) == 1
        ? (this.isCttra = false)
        : (this.isCttra = true);
      Number(val[0].khoa_ctn) == 1
        ? (this.isCtno = false)
        : (this.isCtno = true);
    },
    SetButton(kieu) {
      this.button.nhapmoi = false;
      this.button.xoa = false;
      this.button.ghilai = false;
      this.button.huybo = false;
      switch (kieu) {
        case -1: //batdau
          this.button.nhapmoi = true;
          this.button.xoa = true;
          break;
        case 0: //Bat dau
          this.button.nhapmoi = true;
          break;
        case 1: //Them moi
          this.button.ghilai = true;
          this.button.huybo = true;
          break;
        case 2: //huy
          this.button.nhapmoi = true;
          this.button.xoa = true;
          break;
        case 3: //edit
          this.button.nhapmoi = true;
          this.button.xoa = true;
          this.button.ghilai = true;
          this.button.huybo = true;
          break;
      }
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
</style>