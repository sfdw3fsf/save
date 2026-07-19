<template>
  <div>
    <div class="row">
      <div class="col-sm-9 col-12">
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w70 nowrap">Phút giảm</div>
              <div class="value">
                <input
                  type="text"
                  @keypress="isNumber($event)"
                  v-model="input_phutgiam"
                  class="form-control tright red fw6"
                />
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w80 nowrap">Tiền giảm</div>
              <div class="value">
                <input
                  type="text"
                  v-model="input_tiengiam"
                  @keypress="isNumber($event)"
                  class="form-control tright red fw6"
                />
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w60 nowrap">Phút đầu</div>
              <div class="value">
                <input
                  type="text"
                  v-model="input_phutdau"
                  @keypress="isNumber($event)"
                  class="form-control tright red fw6"
                />
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w70 nowrap">SL từ</div>
              <div class="value">
                <input
                  type="text"
                  v-model="input_sl_tu"
                  @keypress="isNumber($event)"
                  class="form-control tright red fw6"
                />
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w80 nowrap">SL đến</div>
              <div class="value">
                <input
                  type="text"
                  v-model="input_sl_den"
                  @keypress="isNumber($event)"
                  class="form-control tright red fw6"
                />
              </div>
            </div>
          </div>
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w60 nowrap">Tỷ lệ</div>
              <div class="value">
                <input
                  type="text"
                  v-model="input_tyle"
                  @keypress="isNumber($event)"
                  class="form-control tright red fw6"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-3 col-12">
        <div class="list-checks-ver">
          <div class="item mart7 marb10">
            <div class="check-action">
              <input
                type="radio"
                name="loai_giam"
                value="1"
                checked
                @change="changeLoaiGiam($event)"
                class="check"
              />
              <span class="name">Giảm từng thuê bao</span>
            </div>
          </div>
          <div class="item mart7 marb10">
            <div class="check-action">
              <input
                type="radio"
                name="loai_giam"
                value="2"
                @change="changeLoaiGiam($event)"
                class="check"
              />
              <span class="name">Giảm cả nhóm</span>
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
  </div>
</template>
<script>
import KTable from "@/components/kylq_components/KTable.vue";
import MultiServicePackageAPI from "../MultiServicePackageAPI";
export default {
  name: "NhomTichHop",
  props: {
    rg_id: {
      type: Number,
      default: 0,
    },
    dvvt_id: {
      type: Number,
      default: 0,
    },
    ds_nhomth_all: {
      type: Array,
      default() {
        return [];
      },
    },
  },
  components: {
    KTable,
  },
  data() {
    return {
      columnsGan: [
        {
          field: "ten_dvvt",
          label: "Dịch vụ VT đã gán",
          allowFilter: true,
        },
        {
          field: "phut_giam",
          label: "Phút giảm",
          allowFilter: true,
        },
        {
          field: "soluong_tu",
          label: "SL từ",
          allowFilter: true,
        },
        {
          field: "tien_giam",
          label: "Tiền giảm",
          allowFilter: true,
        },
        {
          field: "soluong_den",
          label: "SL đến",
          allowFilter: true,
        },
        {
          field: "phut_dau",
          label: "Phút đầu",
          allowFilter: true,
        },
        {
          field: "tyle_giam",
          label: "Tỷ lệ",
          allowFilter: true,
        },
      ],
      dataSourcesGan: [],
      columnsChuaGan: [
        {
          field: "ten_dvvt",
          label: "Dịch vụ VT chưa gán",
          allowFilter: true,
        },
      ],
      dataSourcesChuaGan: [],
      dataCheckGan: [],
      dataCheckChuaGan: [],
      l_rg_id: this.rg_id,
      l_dvvt_id: this.dvvt_id,
      input_phutgiam: "0",
      input_sl_tu: "0",
      input_tiengiam: "0",
      input_sl_den: "0",
      input_phutdau: "0",
      input_tyle: "0",
      loai_giam: 1,
      DichVuVienThong: {
        CO_DINH: 1,
        DI_DONG: 2,
        GPHONE: 10,
        IMS: 11,
      },
    };
  },
  methods: {
    onSelectedRow(item) {
      if (item) {
        this.input_phutgiam = item.phut_giam ? item.phut_giam : 0;
        this.input_sl_tu = item.soluong_tu ? item.soluong_tu : 0;
        this.input_tiengiam = item.tien_giam ? item.tien_giam : 0;
        this.input_sl_den = item.soluong_den ? item.soluong_den : 0;
        this.input_phutdau = item.phut_dau ? item.phut_dau : 0;
        this.input_tyle = item.tyle_giam ? item.tyle_giam : 0;
        this.loai_giam = item.kieu ? item.kieu : 1;
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
        // const index1 = this.ds_nhomth_all.findIndex((x) => x == item);
        // if (index1 == -1) {
        //   this.ds_nhomth_all.splice(index1, 1);
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
      if (
        this.dvvt_id != this.DichVuVienThong.CO_DINH &&
        this.dvvt_id != this.DichVuVienThong.DI_DONG &&
        this.dvvt_id != this.DichVuVienThong.GPHONE &&
        this.dvvt_id != this.DichVuVienThong.IMS
      ) {
        this.$alert(
          "Hướng gọi được giảm trừ chỉ áp dụng cho thuê bao Cố định, Di động, Gpone, IMS",
          "",
          {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "info",
          }
        );
        return;
      }
      if (this.input_phutgiam.trim() == "") {
        this.$alert("Hãy nhập số phút giảm!", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "info",
        });
        return;
      }
      if (this.input_tiengiam.trim() == "") {
        this.$alert("Hãy nhập tiền giảm!", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "info",
        });
        return;
      }
      if (this.input_phutdau.trim() == "") {
        this.$alert("Hãy nhập số phút đầu tiên được giảm!", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "info",
        });
        return;
      }
      if (this.input_sl_tu.trim() == "") {
        this.$alert("Hãy nhập số lượng từ!", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "info",
        });
        return;
      }
      if (this.input_sl_den.trim() == "") {
        this.$alert("Hãy nhập số lượng đến!", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "info",
        });
        return;
      }
      if (this.dataCheckChuaGan.length == 0) {
        return;
      }
      this.dataCheckChuaGan.forEach((item) => {
        var row = Object.assign(item, {
          phut_dau: Number(this.input_phutdau).toString(),
          phut_giam: Number(this.input_phutgiam).toString(),
          soluong_tu: Number(this.input_sl_tu).toString(),
          tyle_giam: Number(this.input_tyle).toString(),
          tien_giam: Number(this.input_tiengiam).toString(),
          soluong_den: Number(this.input_sl_den).toString(),
          kieu: Number(this.loai_giam),
        });
        // this.ds_nhomth_all.push(row);
        this.dataSourcesGan.push(row);
        const index = this.dataSourcesChuaGan.findIndex((x) => x == item);
        if (index > -1) {
          this.dataSourcesChuaGan.splice(index, 1);
        }
      });
      this.dataCheckGan = [];
      this.dataCheckChuaGan = [];
    },
    getDsNhomTHAll() {
      return this.ds_nhomth_all;
    },
    getDsNhomTHGan() {
      return this.dataSourcesGan;
    },
    async initDulieu() {
      await this.sp_grv_dvvt(this.l_rg_id, 0);
      await this.sp_grv_dvvt(this.l_rg_id, 1);
      // if (this.dataSourcesGan.length > 0 && this.ds_nhomth_all.length == 0) {
      //   this.dataSourcesGan.forEach((item) => {
      //     this.ds_nhomth_all.push(item);
      //   });
      // }
    },
    async clearDuLieu() {
      this.input_phutgiam = "0";
      this.input_sl_tu = "0";
      this.input_tiengiam = "0";
      this.input_sl_den = "0";
      this.input_phutdau = "0";
      this.input_tyle = "0";
      this.loai_giam = 1;
      this.dataSourcesGan = [];
      this.dataSourcesChuaGan = [];
      this.dataCheckGan = [];
      this.dataCheckChuaGan = [];
      //init value 0
      await this.sp_grv_dvvt(0, 0);
      await this.sp_grv_dvvt(0, 1);
    },
    changeLoaiGiam(event) {
      this.loai_giam = event.target.value;
    },
    async sp_grv_dvvt(rg_id, type) {
      try {
        this.loading(true);
        if (type == 0) {
          this.dataSourcesChuaGan = [];
        } else {
          this.dataSourcesGan = [];
        }
        var filter = [];
        // if (this.ds_nhomth_all.length > 0) {
        //   filter = this.ds_nhomth_all.filter((x) => x.rg_id == rg_id);
        // }
        let response = await MultiServicePackageAPI.sp_grv_dvvt(
          this.axios,
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
                    x.dichvuvt_id == item.dichvuvt_id && x.rg_id == item.rg_id
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
        this.$toast.error("Không load được danh sách dịch vụ viễn thông");
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
  },
  watch: {
    rg_id(newval, oldval) {
      if (newval && newval != oldval) {
        this.l_rg_id = newval;
        this.initDulieu();
      }
    },
    dvvt_id(val) {
      this.l_dvvt_id = val;
    },
  },
};
</script>