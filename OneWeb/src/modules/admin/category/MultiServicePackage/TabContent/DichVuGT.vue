<template>
  <div>
    <div class="row">
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w60 nowrap">Giảm LĐ</div>
          <div class="value">
            <input
              type="text"
              v-model="input_giam_ld"
              class="form-control tright red fw6"
              value="0"
            />
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w90 nowrap">Tiền giảm SD</div>
          <div class="value">
            <input
              type="text"
              v-model="cp_tien_giam_sd"
              class="form-control tright red fw6"
              @keypress="isNumber($event)"
            />
          </div>
        </div>
      </div>
      <div class="col-sm-6 col-12">
        <div class="info-row">
          <div class="key">
            <div class="check-action">
              <input type="checkbox" v-model="check_ngay_kt" class="check" />
              <span class="name">Ngày kết thúc</span>
            </div>
          </div>
          <div class="value">
            <div class="input-icon-right">
              <k-date-picker
                v-model="ngay_kt"
                :disabled="!check_ngay_kt"
                format="DD/MM/YYYY HH:mm:ss"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w60 nowrap">Giảm SD</div>
          <div class="value">
            <input
              type="text"
              v-model="input_giam_sd"
              class="form-control tright red fw6"
              value="0"
            />
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w90 nowrap">Vat giảm SD</div>
          <div class="value">
            <input
              type="text"
              v-model="cp_vat_giam_sd"
              class="form-control tright red fw6"
              @keypress="isNumber($event)"
            />
          </div>
        </div>
      </div>
    </div>
    <div class="box-move-select-table fullh">
      <div class="box-col">
        <k-table
          :columns="columnsGan"
          :dataSources="dataSourcesGan"
          @onSelectedRow="onSelectedRow"
          @dataCheckeds="onDataCheckedsGan"
          :allowFilter="true"
          :allowCheckBox="true"
          :tableHeight="200"
        />
      </div>
      <div class="actions">
        <button class="btn" @click="boGan">
          <span class="fa fa-angle-right"></span>
        </button>

        <button class="btn" @click="gan">
          <span class="fa fa-angle-left"></span>
        </button>
      </div>
      <div class="box-col">
        <k-table
          :columns="columnsChuaGan"
          :dataSources="dataSourcesChuaGan"
          @dataCheckeds="onDataCheckedsChuaGan"
          :allowFilter="true"
          :allowCheckBox="true"
          :tableHeight="200"
        />
      </div>
    </div>
  </div>
</template>
<script>
import KTable from "@/components/kylq_components/KTable.vue";
import MultiServicePackageAPI from "../MultiServicePackageAPI";
import KDatePicker from "@/components/kylq_components/KDatePicker.vue";
import moment from "moment";
export default {
  name: "DichVuGT",
  props: {
    dvvt_id: {
      type: Number,
      default: 0,
    },
    rg_id: {
      type: Number,
      default: 0,
    },
    ds_dichvugt_all: {
      type: Array,
      default() {
        return [];
      },
    },
  },
  components: {
    KTable,
    KDatePicker,
  },
  data() {
    return {
      columnsGan: [
        {
          field: "ten_dvgt",
          label: "Dịch vụ đã gán",
          allowFilter: true,
        },
        {
          field: "tyle_giamcuoc_ld",
          label: "Giảm LĐ",
          allowFilter: true,
        },
        {
          field: "tyle_giamcuoc_sd",
          label: "Giảm SD",
          allowFilter: true,
        },
        {
          field: "tien_giamcuoc_sd",
          label: "Tiền giảm SD",
          allowFilter: true,
        },
        {
          field: "vat_giamcuoc_sd",
          label: "Vat giảm SD",
          allowFilter: true,
        },
        {
          field: "denngay",
          label: "Ngày kết thúc",
          allowFilter: true,
        },
      ],
      dataSourcesGan: [],
      columnsChuaGan: [
        {
          field: "ten_dvgt",
          label: "Dịch vụ chưa gán",
          allowFilter: true,
        },
      ],
      dataSourcesChuaGan: [],
      dataCheckGan: [],
      dataCheckChuaGan: [],
      l_rg_id: this.rg_id,
      l_dvvt_id: this.dvvt_id,
      input_giam_ld: "0",
      input_giam_sd: "0",
      input_tien_giam_sd: "0",
      input_vat_giam_sd: "0",
      check_ngay_kt: false,
      ngay_kt: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
    };
  },
  methods: {
    onSelectedRow(item) {
      if (item) {
        this.input_giam_ld = item.tyle_giamcuoc_ld ? item.tyle_giamcuoc_ld : 0;
        this.input_giam_sd = item.tyle_giamcuoc_sd ? item.tyle_giamcuoc_sd : 0;
        this.input_tien_giam_sd = item.tien_giamcuoc_sd
          ? item.tien_giamcuoc_sd
          : 0;
        this.input_vat_giam_sd = item.vat_giamcuoc_sd
          ? item.vat_giamcuoc_sd
          : 0;
        this.check_ngay_kt = item.denngay ? true : false;
        this.ngay_kt = item.denngay
          ? moment(item.denngay).format("DD/MM/YYYY HH:mm:ss")
          : "";
      }
    },
    onDataCheckedsGan(data) {
      this.dataCheckGan = data;
    },
    onDataCheckedsChuaGan(data) {
      this.dataCheckChuaGan = data;
    },
    boGan() {
      if (this.dataCheckGan.length == 0) {
        return;
      }
      this.dataCheckGan.forEach((item) => {
        this.dataSourcesChuaGan.push(item);
        const index = this.dataSourcesGan.findIndex((x) => x == item);
        if (index > -1) {
          this.dataSourcesGan.splice(index, 1);
        }
        // const index1 = this.ds_dichvugt_all.findIndex((x) => x == item);
        // if (index1 == -1) {
        //   this.ds_dichvugt_all.splice(index1, 1);
        // }
      });
      this.dataCheckGan = [];
      this.dataCheckChuaGan = [];
    },
    gan() {
      if (this.rg_id == 0) {
        this.$alert("Chưa có loại hình thuê bao nào được gán", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "info",
        });
        return;
      }
      //  dr["TYLE_GIAMCUOC_LD"] = txtTyleLDDV.Text.Trim();
      //               dr["TYLE_GIAMCUOC_SD"] = txtTyleSDDV.Text.Trim();
      //               dr["TIEN_GIAMCUOC_SD"] = txtTienGiamDV.Text.Trim();
      //               dr["VAT_GIAMCUOC_SD"] = txtVatGiamDV.Text.Trim();
      if (this.input_giam_sd.toString().trim() == "") {
        this.$alert(
          "Hãy nhập số tỷ lệ giảm cước sử dụng dịch vụ gia tăng!",
          "",
          {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "info",
          }
        );
        return;
      }
      if (this.input_giam_ld.toString().trim() == "") {
        this.$alert(
          "Hãy nhập số tỷ lệ giảm cước lắp đặt dịch vụ gia tăng!",
          "",
          {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "info",
          }
        );
        return;
      }
      if (this.input_tien_giam_sd.toString().trim() == "") {
        this.$alert(
          "Hãy nhập số tiền giảm cước sử dụng dịch vụ gia tăng!",
          "",
          {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "info",
          }
        );
        return;
      }
      if (this.input_vat_giam_sd.toString().trim() == "") {
        this.$alert(
          "Hãy nhập số tiền vat giảm cước sử dụng dịch vụ gia tăng!",
          "",
          {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "info",
          }
        );
        return;
      }
      if (this.dataCheckChuaGan.length == 0) {
        return;
      }
      this.dataCheckChuaGan.forEach((item) => {
        var row = Object.assign(item, {
          vat_giamcuoc_sd: Number(this.input_vat_giam_sd).toString(),
          tien_giamcuoc_sd: Number(this.input_tien_giam_sd).toString(),
          tyle_giamcuoc_ld: Number(this.input_giam_ld).toString(),
          tyle_giamcuoc_sd: Number(this.input_giam_sd).toString(),
          denngay: this.check_ngay_kt ? this.ngay_kt : null,
        });
        // this.ds_dichvugt_all.push(row);
        this.dataSourcesGan.push(row);
        const index = this.dataSourcesChuaGan.findIndex((x) => x == item);
        if (index > -1) {
          this.dataSourcesChuaGan.splice(index, 1);
        }
      });
      this.dataCheckGan = [];
      this.dataCheckChuaGan = [];
    },
    getDsDichVuGTAll() {
      return this.ds_dichvugt_all;
    },
    getDsDichVuGTGan() {
      return this.dataSourcesGan;
    },
    async initDulieu() {
      await this.sp_grv_dichvu_gt(this.l_dvvt_id, this.l_rg_id, 0);
      await this.sp_grv_dichvu_gt(this.l_dvvt_id, this.l_rg_id, 1);
      // if (this.dataSourcesGan.length > 0 && this.ds_dichvugt_all.length == 0) {
      //   this.dataSourcesGan.forEach((item) => {
      //     this.ds_dichvugt_all.push(item);
      //   });
      // }
    },
    async clearDuLieu() {
      this.input_giam_ld = "0";
      this.input_giam_sd = "0";
      this.input_tien_giam_sd = "0";
      this.input_vat_giam_sd = "0";
      this.check_ngay_kt = false;
      this.ngay_kt = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");

      this.dataSourcesGan = [];
      this.dataSourcesChuaGan = [];
      this.dataCheckGan = [];
      this.dataCheckChuaGan = [];

      await this.sp_grv_dichvu_gt(0, 0, 0);
      await this.sp_grv_dichvu_gt(0, 0, 1);
    },
    async sp_grv_dichvu_gt(dvvt_id, rg_id, type) {
      try {
        this.loading(true);
        if (type == 0) {
          this.dataSourcesChuaGan = [];
        } else {
          this.dataSourcesGan = [];
        }
        var filter = [];
        // if (this.ds_dichvugt_all.length > 0) {
        //   filter = this.ds_dichvugt_all.filter((x) => x.rg_id == rg_id);
        // }
        let response = await MultiServicePackageAPI.sp_grv_dichvu_gt(
          this.axios,
          dvvt_id,
          rg_id,
          type
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          var dt = response.data.data;
          if (dt.length > 0) {
            dt = dt.map((e) => Object.assign(e, { rg_id: rg_id }));
          }
          if (type == 0) {
            if (filter.length > 0) {
              filter.forEach((item) => {
                const index = dt.findIndex(
                  (x) =>
                    x.dichvugt_id == item.dichvugt_id && x.rg_id == item.rg_id
                );
                if (index > -1) {
                  dt.splice(index, 1);
                }
              });
            }
            this.dataSourcesChuaGan = dt;
          } else {
            if (filter.length > 0) {
              this.dataSourcesGan = filter;
            } else {
              this.dataSourcesGan = dt;
            }
          }
        } else if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000204"
        ) {
          if (type == 0) {
            this.dataSourcesChuaGan = [];
          } else {
            if (filter.length > 0) {
              this.dataSourcesGan = filter;
            } else {
              this.dataSourcesGan = [];
            }
          }
        } else {
          if (type == 0) {
            this.dataSourcesChuaGan = [];
          } else {
            this.dataSourcesGan = [];
          }
        }
      } catch (e) {
        this.$toast.error("Không load được danh sách dịch vụ gia tăng");
      } finally {
        this.loading(false);
      }
    },
    isNumber(evt) {
      evt = evt ? evt : window.event;
      var charCode = evt.which ? evt.which : evt.keyCode;
      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        evt.preventDefault();
      } else {
        return true;
      }
    },
    numberWithCommas(n) {
      var parts = n.toString().split(".");
      return (
        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
        (parts[1] ? "." + parts[1] : "")
      );
    },
  },
  computed: {
    cp_tien_giam_sd: {
      get() {
        return this.numberWithCommas(this.input_tien_giam_sd);
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.input_tien_giam_sd = Number(replaced);
      },
    },
    cp_vat_giam_sd: {
      get() {
        return this.numberWithCommas(this.input_vat_giam_sd);
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.input_vat_giam_sd = Number(replaced);
      },
    },
  },
  watch: {
    dvvt_id(val) {
      this.l_dvvt_id = val;
    },
    rg_id(newval, oldval) {
      if (newval && newval != oldval) {
        this.l_rg_id = newval;
        this.initDulieu();
      }
    },
    input_tien_giam_sd(val) {
      this.input_vat_giam_sd = Math.round((Number(val) * 10) / 100);
    },
  },
};
</script>