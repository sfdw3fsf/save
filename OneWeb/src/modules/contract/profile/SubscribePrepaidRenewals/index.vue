<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li
          @click="clickButton('timkiem')"
          :class="this.button.timkiem ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-search"></span>Tìm kiếm </a>
        </li>
        <li
          @click="clickButton('capnhat')"
          :class="this.button.capnhat ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-reload1"></span>Cập nhật</a>
        </li>
        <li
          @click="clickButton('huygan')"
          :class="this.button.huygan ? 'active' : 'non-active'"
        >
          <a>
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy gán
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">
      <div class="row">
        <div class="col-sm-12 col-lg-12">
          <div class="box-form">
            <div class="legend-title">Thông tin tìm kiếm</div>

            <div class="row">
              <div class="col-sm-3 col-lg-3">
                <div class="info-row">
                  <div class="key w80">Kỳ HĐ</div>
                  <div class="value">
                    <vue-date
                      format="MM/YYYY"
                      type="datetime"
                      v-model="ky_hd"
                    ></vue-date>
                  </div>
                </div>
              </div>
              <div class="col-sm-3 col-lg-3">
            <div class="info-row">
                <div class="key w80">
                  <vue-checkbox label="Đơn vị" v-model="chk_donvi"></vue-checkbox>
                </div>
                <div class="value">
                    <div class="select-custom">    
                      <SelectExt v-model="donvi" :options="this.options.danhsachdonvi" :disabled="!this.chk_donvi"> 
                        <option disabled value="0">Select one</option> 
                      </SelectExt>        
                    </div>         
                </div>
            </div>
              </div>
              <div class="col-sm-3 col-lg-3">

            <div class="info-row">
                <div class="key w110">
                  <vue-checkbox label="Nhân viên QL" v-model="chk_nvql"></vue-checkbox>
                </div>
                <div class="value">
                    <div class="select-custom">    
                      <SelectExt v-model="nvql" :options="this.options.danhsachnvql" :disabled="!this.chk_nvql"> 
                        <option disabled value="0">Select one</option> 
                      </SelectExt>        
                    </div>         
                </div>
            </div>

              </div>
              <div class="col-sm-3 col-3">

            <div class="info-row">
                <div class="key w110">
                  <vue-checkbox label="Nhân viên TC" v-model="chk_nvtc"></vue-checkbox>
                </div>
                <div class="value">
                    <div class="select-custom">    
                      <SelectExt v-model="nvtc" :options="this.options.danhsachnvtc" :disabled="!this.chk_nvtc"> 
                        <option disabled value="0">Select one</option> 
                      </SelectExt>        
                    </div>         
                </div>
            </div>
              </div>
            </div>
          </div>

          <div class="box-form">
            <div class="legend-title">Thông tin cập nhật</div>

            <div class="row">
              <div class="col-sm-3 col-lg-6">
            <div class="info-row">
                <div class="key w80">ĐV nhận</div>
                <div class="value">
                    <div class="select-custom">    
                      <SelectExt v-model="donvi_nhan_id" :options="this.options.danhsachdonvi"> 
                        <option disabled value="0">Select one</option> 
                      </SelectExt>        
                    </div>         
                </div>
            </div>
              </div>
              <div class="col-sm-3 col-lg-6">
                <div class="info-row">
                  <div class="key w110">Ghi chú</div>
                  <div class="value">
                    <vue-input
                      aria-multiline="true"
                      v-model="ghi_chu"
                    ></vue-input>
                  </div>
                </div>
              </div>
              <div class="col-sm-3 col-lg-6">
                <div class="info-row">
                  <div class="key w80">Người nhận</div>
                  <div class="value">
                      <SelectExt v-model="nguoi_nhan" :options="this.options.nguoi_nhan"> 
                        <option disabled value="0">Select one</option> 
                      </SelectExt> 
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="box-form">
            <div class="legend-title">
              <div class="pull-left">
                Danh sách đặt cọc / trả trước sắp hết hạn
              </div>
              <div class="pull-right">
                <div class="list-checks">
                  <div class="item">
                    <div class="check-action">
                      <input
                        type="radio"
                        class="check"
                        name="options"
                        value="0"
                        v-model="rdo_gan"
                      />
                      <span class="name">Chưa gán</span>
                    </div>
                  </div>
                  <div class="item">
                    <div class="check-action">
                      <input
                        type="radio"
                        class="check"
                        name="options"
                        value="1"
                        v-model="rdo_gan"
                      />
                      <span class="name">Đã gán</span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="clearfix"></div>
            </div>

            <div class="table-content">
              <DataGrid
                :columns="options.cols"
                v-bind:dataSource="options.danhsach"
                :showColumnCheckbox="true"
                :enable-paging-server="false"
                :allowPaging="true"
                :showFilter="true"
                :enabledSelectFirstRow="true"
                @rowSelected="rowSelected"
                @rowDeselected="rowDeselected"
              >
              </DataGrid>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import VueInput from "../../../search/subscriber/components/form/VueInput.vue";
import api from "./api";
import define from "./define";
import moment from "moment";
import VueCheckbox from '../../../search/subscriber/components/form/VueCheckbox.vue';

export default {
  components: { VueInput, VueCheckbox },
  data() {
    return {
      ...define,
      options: {
        danhsachnvql: [],
        danhsachnvtc: [],
        danhsachdonvi: [],
        danhsach: [],
        dv_nhan: [],
        nguoi_nhan: [],
        thamsomd: [],
        cols: [
          {
            fieldName: "ma_tb",
            headerText: "Số máy/Acc",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ten_tb",
            headerText: "Tên TB",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ma_tt",
            headerText: "Mã TT",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "diachi_tt",
            headerText: "Địa chỉ TT",
            textAlign: "left",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "so_dt",
            headerText: "Số ĐT",
            textAlign: "left",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "manv_tc",
            headerText: "Mã NVTC",
            textAlign: "left",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ma_nv_gt",
            headerText: "Mã NV giới thiệu",
            textAlign: "left",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ten_nv_gt",
            headerText: "Tên NV giới thiệu",
            textAlign: "left",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ngay_dk",
            headerText: "Ngày ĐK",
            textAlign: "left",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "thang_bd",
            headerText: "Tháng BĐ",
            textAlign: "left",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "thang_kt",
            headerText: "Tháng KT",
            textAlign: "left",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "datcoc_csd",
            headerText: "Đặt cọc CSD",
            type: "Number",
            format: "N",
            textAlign: "right",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "tien_td",
            headerText: "Tiền trừ dần",
            type: "Number",
            format: "N",
            textAlign: "right",
            allowSorting: true,
            autoFit: true,
          },
        ],
      },
      chk_donvi: false,
      chk_nvql: false,
      chk_nvtc: false,
      donvi: "-1",
      nvql: "-1",
      nvtc: "-1",
      ky_hd: moment(new Date()).format("MM/YYYY"),
      donvi_nhan_id: "",
      nguoi_nhan: "",
      ghi_chu: "",
      rdo_gan: 0,
      array_json: [],
    };
  },
  created() {
    this.check_radio();
    this.getDanhSachDonVi(); //alert('dfdf');
  },
  watch: {

chk_donvi(newdata) {
      this.getDanhSachNVQL();
    },
chk_nvql(newdata) {
      this.getDanhSachNVTC();
    },

    donvi(newdata) {
      this.check_radio();
      this.getDanhSachNVQL();
    },
    nvql(newdata) {
      this.check_radio();
      this.getDanhSachNVTC();
    },

    donvi_nhan_id(newdata) {
      this.check_radio();
      this.getNguoiNhan();
    },
    rdo_gan(newdata) {
      this.check_radio();
      this.getDanhSach();
    },
  },
  props: {
    value: Object,
  },
  methods: {

    getDanhSachDonVi() {
      api.getDanhSachDonVi(this.axios, {}).then((res) => {
              let items = [];
              res.data.data.forEach(function (item) {
                items.push({ id: item.donvi_id, text: item.ten_dv });
              });
              this.options.danhsachdonvi = items;
              this.donvi = items[0].id;
              this.donvi_nhan_id= items[0].id;
      });
    },

    getDanhSachNVQL() {
      api.getDanhSachNVQL(this.axios, {
          vdonvi_id: this.donvi,
        })
        .then((res) => {
              let items = [];
              res.data.data.forEach(function (item) {
                items.push({ id: item.nhanvien_id, text: item.ghepten });
              });
              this.options.danhsachnvql = items;
              this.nvql = items[0].id;
        });
    },

    getDanhSachNVTC() {
      var v_nvql = "-1";
      if (this.nvql != "") {
        v_nvql = this.nvql;
      }
      api
        .getDanhSachNVTC(this.axios, {
          vnhanvienql_id: v_nvql,
        })
        .then((res) => {
              let items = [];
              res.data.data.forEach(function (item) {
                items.push({ id: item.tuyenthu_id, text: item.gheptuyen });
              });
              this.options.danhsachnvtc = items;
              this.nvtc = items[0].id;
        });
    },
    getDanhSach() {
      this.loading(true);

      console.log( 'vdonviql_id',this.donvi,
          'vnhanvienql_id', this.nvql,
          'vtuyenthu_id', this.nvtc,
          'vdagan', this.rdo_gan,
          'vkyhoadon', moment(this.ky_hd,'MM/YYYY').format("YYYYMM")+"01")

      api
        .getDanhSach(this.axios, {
          vdonviql_id: this.donvi,
          vnhanvienql_id: this.nvql,
          vtuyenthu_id: this.nvtc,
          vdagan: this.rdo_gan,
          vkyhoadon: moment(this.ky_hd,'MM/YYYY').format("YYYYMM")+"01"
        })
        .then((res) => {
          this.options.danhsach = res.data.data ? res.data.data : [];
          console.log('this.options.danhsach',this.options.danhsach)
          if (this.options.danhsach.length < 1) {
            this.$toast.error("Không có dữ liệu tìm kiếm!");
          }
          else
          {
            for(var i =0; i< this.options.danhsach.length; i++)
            {
              this.options.danhsach[i].ngay_dk = moment(this.options.danhsach[i].ngay_dk).format("DD/MM/YYYY")
            }
          }
          this.loading(false);
        });
    },

    check_radio() {
      if (this.rdo_gan == 0) {
        this.button.huygan = false;
        this.button.capnhat = true;
      } else {
        this.button.capnhat = false;
        this.button.huygan = true;
      }
    },

    getNguoiNhan() {
      api
        .getNguoiNhan(this.axios, {
          vloainv_id: 0,
          vdonvi_id: this.donvi_nhan_id,
        })
        .then((res) => {
              let items = [];
              res.data.data.forEach(function (item) {
                items.push({ id: item.nhanvien_id, text: item.ten_nv });
              });
              this.options.nguoi_nhan = items;
              this.nguoi_nhan = items[0].id;          
        });
    },

    HuyGiao() {


      try {
        api
          .CapNhatDL(this.axios, {
            giaophieu: 2,
            nguoinhan_id:-1,
            donvi_nhan_id: -1,
            ghichu: "",
            json_data: this.array_json,
          })
          .then((res) => {
            var mes = res.data.message ? res.data.message : [];
            if (mes == "Success") {
              this.$toast.success('Hủy gán thành công!');
            } else {
              this.$toast.error('Hủy gán lỗi!');
            }
          });
        this.array_json = [];
      } catch (error) {}
    },

    

    CapNhat() {
      try {

      this.$confirm(`Bạn thật sự muốn cập nhật không ?`, {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
              type: "warning",
              }).then(async () => {

              api.CapNhatDL(this.axios, {
                  giaophieu: 1,
                  nguoinhan_id: this.nguoi_nhan,
                  donvi_nhan_id: this.donvi_nhan_id,
                  ghichu: this.ghi_chu,
                  json_data: this.array_json,
                })
                .then((res) => {
                  var mes = res.data.message ? res.data.message : [];
                  if (mes == "Success") {
                    this.$toast.success('Cập nhật dữ liệu thành công !');
                  } else {
                    this.$toast.error('Cập nhật dữ liệu lỗi !');
                  }
                });
              this.array_json = [];

              })


      } catch (error) {}
    },

    rowSelected(subject) {
      if(this.rdo_gan == 0)
      {
        this.array_json.push({
          RKM_ID: subject.data.rkm_id,
          KIEU: 1,
          TTPH_ID: 1,
        });
      }
      else
      {
          this.array_json.push({
          GIAOPHIEU_ID: subject.data.giaophieu_id,
        });
        console.log(this.array_json);
      }

    },

    rowDeselected(subject) {
      if(this.rdo_gan == 0)
      {
        const itemToBeRemoved = {
          RKM_ID: subject.data.rkm_id,
          KIEU: 1,
          TTPH_ID: 1,
        };
        this.array_json.splice(
          this.array_json.findIndex((a) => a.RKM_ID == itemToBeRemoved.RKM_ID),
          1
        );
      }
      else
      {
        const itemToBeRemoved = {
          GIAOPHIEU_ID: subject.data.giaophieu_id,
        };
        this.array_json.splice(
          this.array_json.findIndex((a) => a.GIAOPHIEU_ID == itemToBeRemoved.GIAOPHIEU_ID),
          1
        );
      }
    },

    KiemTra_DuLieu() {
      if (this.ghi_chu.length > 200) {
        this.$toast.error('Trường "Ghi chú" nhập dữ liệu vượt quá độ dài cho phép là 200 ký tự!');
        return false;
      }

      if (this.array_json.length < 1) {
        this.$toast.error('Hãy lấy danh sách trước khi ghi lại !');
        return false;
      }

      if (!this.donvi_nhan_id) {
        this.$toast.error('Bạn chưa chọn "Đơn vị nhận"!');
        return false;
      }
      if (!this.nguoi_nhan || this.nguoi_nhan == "-1") {
        this.$toast.error('Bạn chưa chọn "Người nhận"!');
        return false;
      }
      return true;
    },

    clickButton(key) {
      if (key == "timkiem") {
        this.array_json = [];
        this.getDanhSach();
      }

      if (key == "capnhat") {
        if (this.button.capnhat == true) {

          if (this.KiemTra_DuLieu() == true) {
            this.CapNhat();
            this.getDanhSach();
          }
        }
      }
      if (key == "huygan") {



        if (this.button.huygan == true) {
          if(this.array_json.length < 1)
          {
            this.$toast.error('Hãy lấy danh sách trước khi hủy !');
            return
          }
          this.HuyGiao();
          this.getDanhSach();
        }
      }
    },
  },
};
</script>
<style>
li.non-active a {
  color: #d3d3d3 !important;
}
</style>
