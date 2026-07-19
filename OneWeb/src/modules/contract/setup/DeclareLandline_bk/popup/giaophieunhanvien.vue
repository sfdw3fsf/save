<template>
  <div>
    <vue-nav>
      <ul class="list">
        <li>
          <a @click="ghilai()"> <span class="icon one-save"></span> Ghi lại </a>
        </li>
        <li>
          <a @click="xoagiao()">
            <span class="icon one-trash"></span> Xoá giao
          </a>
        </li>
        <li>
          <a> <span class="icon one-email"></span>Gửi SMS </a>
        </li>
        <li>
          <a @click="capnhatKQXL()">
            <span class="icon one-reload1"></span>Cập nhật KQXL
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="popup-body">
      <vue-card label="Danh sách nhân viên giao việc">
        <b-row>
          <b-col md="4">
            <div class="info-row">
              <div class="value w20 padt7">
                <div class="check-action">
                  <input
                    type="checkbox"
                    class="check"
                  />
                  <span class="name"></span>
                </div>
              </div>
              <div class="key w100">Trung tâm</div>
              <ejs-dropdownlist
                locale="vi-VN"
                v-model="model.donvi_id"
                :dataSource="options.trungtam"
                :allowFiltering="true"
                :fields="{ value: 'donvi_id', text: 'ten_dv' }"
                popupWidth="auto"
              />
            </div>
          </b-col>
          <vue-element label="Tổ TC" class="col-md-4" labelWidth="100">
            <ejs-dropdownlist
              locale="vi-VN"
              v-model="model.totc_id"
              :dataSource="options.to_tc"
              :allowFiltering="true"
              popupWidth="auto"
              :fields="{ value: 'donvi_id', text: 'ten_dv' }"
            />
          </vue-element>
          <b-col md="4">
            <div class="info-row">
              <div class="value w20 padt7">
                <div class="check-action">
                  <input
                    type="checkbox"
                    class="check"
                  />
                  <span class="name"></span>
                </div>
              </div>
              <div class="key w100">Loại nhân viên</div>
              <ejs-dropdownlist
                  locale="vi-VN"
                  v-model="model.loainv_id"
                  :dataSource="options.cboLoaiNhanvien"
                  :allowFiltering="true"
                  popupWidth="auto"
                  :fields="{ value: 'loainv_id', text: 'ten' }"
                />
            </div>
          </b-col>
          <b-col md="4">
            <div class="info-row">
              <div class="value w20 padt7">
                <div class="check-action">
                  <input
                    type="checkbox"
                    class="check"
                  />
                  <span class="name"></span>
                </div>
              </div>
              <div class="key w100">Trung tâm BH</div>
              <ejs-dropdownlist
                  locale="vi-VN"
                  v-model="model.donvibh_id"
                  :dataSource="options.dvbanhang"
                  :allowFiltering="true"
                  popupWidth="auto"
                  :fields="{ value: 'donvi_id', text: 'ten_dv' }"
                />
            </div>
          </b-col>
          <div class="col-sm-8 col-12">
            <div class="note text-main mart7">
              Cấu trúc nhắn tin phản hồi: LD [Cách] Mã tỉnh [Cách] ID phiếu
            </div>
          </div>
          <!-- <DataGrid
              ref="gridNV"
              v-bind:columns="collumns"
              :dataSource="options.dsNhanvien"
              :showFilter="true"
              :allowPaging="true"
              :allowDeleting="true"
              :enablePagingServer="false"
              panelDataHeight="170"
              :editSettings="{ allowEditing: true, allowAdding: true, allowDeleting: true, mode: 'Dialog' }"
              @actionBegin='actionBegin'
            ></DataGrid> -->
          <div class="grid-ext">
            <ejs-grid
              :dataSource="options.dsNhanvien"
              :editSettings="editSettings"
              :actionBegin="actionBegin"
              :actionComplete="actionComplete"
              :allowFiltering="true"
              gridLines="Both"
              filterTemplate="<span></span>"
              :filterSettings="{
                showFilterBarOperator: true,
                mode: 'Immediate',
                immediateModeDelay: 500,
              }"
              height="200px"
            >
              <e-columns>
                <e-column
                  field="ten_dv"
                  headerText="Đơn vị"
                  textAlign="Right"
                  :isPrimaryKey="true"
                  :allowFiltering="true"
                  :filter="{ operator: 'contains' }"
                  width="100"
                ></e-column>
                <e-column
                  field="ma_nv"
                  headerText="Mã nhân viên"
                  :allowFiltering="true"
                  :filter="{ operator: 'contains' }"
                  width="120"
                ></e-column>
                <e-column
                  field="ten_nv"
                  headerText="Tên nhân viên"
                  :allowFiltering="true"
                  :filter="{ operator: 'contains' }"
                  width="150"
                ></e-column>
                <e-column
                  field="so_dt"
                  headerText="Điện thoại"
                  :allowFiltering="true"
                  :filter="{ operator: 'contains' }"
                  width="150"
                ></e-column>
                <e-column
                  field="nhiemvu_str"
                  headerText="Nhiệm vụ"
                  :allowFiltering="true"
                  :filter="{ operator: 'contains' }"
                  width="150"
                ></e-column>
                <e-column
                  field="ghichu"
                  :allowEditing="true"
                  headerText="Ghi chú"
                  :allowFiltering="true"
                  :filter="{ operator: 'contains' }"
                  width="150"
                ></e-column>
                <e-column
                  field="ma_nd_th"
                  headerText="Mã ND"
                  :allowFiltering="true"
                  :filter="{ operator: 'contains' }"
                  width="150"
                ></e-column>
              </e-columns>
            </ejs-grid>
          </div>
        </b-row>
      </vue-card>
      <vue-card label="Danh sách nhân viên đã giao nhiệm vụ">
        <DataGrid
          v-bind:columns="collumns_dg"
          :dataSource="options.dsNhanvienDG"
          :showColumnCheckbox="false"
          :showFilter="false"
          :allowPaging="true"
          panelDataHeight="170"
        ></DataGrid>
      </vue-card>
    </div>
  </div>
</template>
<script>

import { MultiSelect, CheckBoxSelection } from '@syncfusion/ej2-dropdowns';
MultiSelect.Inject(CheckBoxSelection);

import VueElement from "../components/VueElement.vue";
import collums from "../define/collums_gp.js";
import api from "../api/index.js";
import { Page, Toolbar, Edit, Filter } from "@syncfusion/ej2-vue-grids";
import Vue from "vue";
import axios from "axios";
import moment from "moment"

var dsNhiemvu = [];
async function getNhiemvu() {
  let dataNhiemvu = await Vue.axios.get("web-quantri/danhmuc-chung/CSS_NHIEMVU");
  dsNhiemvu = dataNhiemvu.data.data ? dataNhiemvu.data.data : [];
}

getNhiemvu()

export default {
  components: {
    VueElement
  },
  data() {
    return {
      groupOptions: { showDropArea: true, columns: ["ten_nv"] },
      editSettings: {
        allowEditing: true,
        allowAdding: true,
        allowDeleting: true,
        mode: "Dialog",
        template: function () {
          return {
            template: Vue.component("todo-item", {
              template: `<div formGroup="orderForm" class="p-3" style="height: 300px;">
                  <div class="form-row">
                      <div class="form-group col-md-12">
                        <div class="e-float-input e-control-wrapper">
                          <label>Nhiệm vụ</label>
                          <ejs-multiselect v-model="nhiemvu" id='nhiemvu' :dataSource='dsNhiemvu' placeholder="Chọn nhiệm vụ" mode="CheckBox" :fields="{ text: 'NHIEMVU', value: 'NHIEMVU_ID' }"></ejs-multiselect>
                        </div>
                      </div>
                       <div class="form-group col-md-12">
                        <div class="e-float-input e-control-wrapper">
                          <input ref="ghichu" id="ghichu" name="ghichu" v-model="data.ghichu" type="text" />
                          <span class="e-float-line"></span>
                          <label class="e-float-text e-label-top" for="ghichu">Ghi chú</label>
                        </div>
                      </div>
                  </div>
              </div>`,
              data() {
                return {
                  data: {
                  },
                  dsNhiemvu: dsNhiemvu,
                };
              },
              computed: {
                nhiemvu: {
                  get() {
                    return this.data.nhiemvu
                  },
                  set(val) {
                    this.$set(this.data, 'nhiemvu', val)
                  }
                }
              },
              methods: {
                customLabel(option) {
                  return `${option.NHIEMVU}`;
                },
                onSelect(option) {
                },

                onRemove(option) {
                }
              },
            }),
          };
        },
      },
      toolbar: ["Add", "Edit", "Delete", "Update", "Cancel"],
      ...collums,
      options: {
        trungtam: [],
        cboLoaiNhanvien: [],
        dsNhanvien: [],
        dsNhanvienDG: [],
      },
      model: {
        donvi_id: 0,
        donvibh_id: 0,
        totc_id: 0,
        loainv_id: 0,
      },
    };
  },
  provide: {
    grid: [Page, Edit, Toolbar, Filter],
  },
  props: ["phieu_id", "nguoigv_id", "ngaygiao", 'ma_tb', 'hdtb_id', 'huonggiao_id', 'dsphieuGV'],
  watch: {
    "model.donvi_id"(val) {
      this.getDsTC();
    },
  },
  methods: {
    formatDate(value_) {
      if (moment(value_).isValid()) {
        return moment(value_).format("DD/MM/YYYY HH:mm:ss");
      } else {
        return value_;
      }
    },
    getFilterTemplate() {},
    async capnhatKQXL() {
      try {
        var data_gp = [];
        this.options.dsNhanvienDG.forEach((item) => {
          data_gp.push({
            PHIEU_ID: this.phieu_id,
            NHANVIEN_TH_ID: item.nhanvien_th_id,
            NHIEMVU: item.nhiemvu,
            GHICHU: item.ghichu,
            NHANVIEN_GIAO_ID: item.nhanvien_giao_id,
            NGAYGIAO: item.ngaygiao,
            SO_DT: item.so_dt ? item.so_dt : "",
            STATUS: 0,
            NHIEMVU_ID: item.nhiemvu_id,
            KQXL_ID: 1,
          });
        });
        let data = {
          phieu_id: this.phieu_id,
          json_giaophieu_nv: data_gp,
        };
        let capnhat = await this.axios.post(
          "web-thicong/hoinghi_truyenhinh/capnhat_kqxl_giaoviec",
          data
        );
        this.getGridDsnhanvien()
        this.$toast.success(
          capnhat.message ? capnhat.message : "Xử lý thành công"
        );
      } catch (error) {
        this.$toast.error(error.message ? error.message : "Xử lý thất bại");
      }
    },
    init() {
      try {
        this.getDsTrungTam();
        this.getComboLoaiNhanvien();
        this.getDsBH();
        this.getGridDsnhanvien();
        this.getGridDsnhanvienDagiaoNV();
      } catch (e) {
        this.$toast.error("Khởi tạo thất bại");
      }
    },
    actionBegin(args) {
      if (args.requestType === "save") {
        let data = dsNhiemvu.filter((item) =>
          args.data.nhiemvu.includes(item.NHIEMVU_ID)
        );
        if (data.length > 0)
          args.data.nhiemvu_str = data
            .map((item) => {
              return item.NHIEMVU;
            })
            .join("; ");
        else args.data.nhiemvu_str = "";
        args.data.nhiemvuArray = args.data.nhiemvu;
      }
    },
    actionComplete(args) {
      // Set initail Focus
      if (args.requestType === "beginEdit") {
        let dialog = args.dialog;
        dialog.width = 500;
        dialog.header =
          args.requestType === "beginEdit"
            ? "Nhân viên " + args.rowData["ten_nv"]
            : "Nhân viên";
        args.form.elements.namedItem("ghichu").focus();
      }
    },
    async getDsTrungTam() {
      let data = await api.ht_donvi_loaidv_combobox(this.axios, {
        loaidv_id: 3,
      });
      this.options.trungtam = data.data ? data.data.data : [];
      if (this.options.trungtam.length > 0) {
        this.model.donvi_id = this.options.trungtam[0].donvi_id;
      }
    },
    async getDsBH() {
      let data = await api.ht_donvi_loaidv_combobox(this.axios, {
        loaidv_id: 46,
      });
      this.options.dvbanhang = data.data ? data.data.data : [];
      if (this.options.dvbanhang.length > 0) {
        this.model.donvibh_id = this.options.dvbanhang[0].donvi_id;
      }
    },
    async getDsTC() {
      let data = await api.ht_donvi_loaidv_combobox(this.axios, {
        loaidv_id: this.model.donvi_id,
      });
      this.options.to_tc = data.data ? data.data.data : [];
      if (this.options.to_tc.length > 0) {
        this.model.totc_id = this.options.to_tc[0].donvi_id;
      }
    },
    async getComboLoaiNhanvien() {
      let data = await api.lay_ds_loainv(this.axios);
      this.options.cboLoaiNhanvien = data.data ? data.data.data : [];
      if (this.options.cboLoaiNhanvien.length > 0) {
        this.model.loainv_id = this.options.cboLoaiNhanvien[0].loainv_id;
      }
    },
    async getGridDsnhanvien() {
      let data = await api.sp_lay_ds_nhanvien_giaoviec_frmgiaoviecnv(
        this.axios,
        {
          vphieu_id: this.phieu_id,
          vnhanvien_id: this.$root.token.getNhanVienID(),
          vkieugiao: "1",
          vloainv: "0",
        }
      );

      this.options.dsNhanvien = data.data.data ? data.data.data : [];
      this.options.dsNhanvien.forEach((item, i) => {
        this.$set(this.options.dsNhanvien[i], "nhiemvu", []);
        this.$set(this.options.dsNhanvien[i], "ghichu", "");
      });
    },
    async getGridDsnhanvienDagiaoNV() {
      let data = await api.getDsNV(this.axios, {
        phieu_id: this.phieu_id,
        nhanvien_id: this.$root.token.getNhanVienID(),
        kieu_id: 3,
      });
      if (data.data.data.length > 0) {
      }
      this.options.dsNhanvienDG = data.data.data ? data.data.data : [];
    },
    async ghilai() {
      try {
        let data = [];
        console.log(this.dsphieuGV, "Đây này")
        console.log(this.phieu_id)
        if(this.dsphieuGV && this.dsphieuGV.length > 0) {
          this.options.dsNhanvien.forEach((item) => {
            item.nhiemvu.forEach((nhiemvu_id) => {
              let nhiemnhu = dsNhiemvu.find((nv) => nv.NHIEMVU_ID == nhiemvu_id);
              this.dsphieuGV.forEach((phieu_item) => {
                data.push({
                  PHIEU_ID: phieu_item,
                  NHANVIEN_TH_ID: item.nhanvien_th_id,
                  NHIEMVU: nhiemnhu.NHIEMVU,
                  GHICHU: item.ghichu,
                  NHANVIEN_GIAO_ID: this.nguoigv_id,
                  NGAYGIAO: this.formatDate(this.ngaygiao),
                  SO_DT: item.so_dt,
                  STATUS: 0,
                  NHIEMVU_ID: nhiemvu_id,
                  KQXL_ID: null,
                });
              })
            });
          });
        } else {
          this.options.dsNhanvien.forEach((item) => {
          item.nhiemvu.forEach((nhiemvu_id) => {
            let nhiemnhu = dsNhiemvu.find((nv) => nv.NHIEMVU_ID == nhiemvu_id);
            data.push({
              PHIEU_ID: this.phieu_id,
              NHANVIEN_TH_ID: item.nhanvien_th_id,
              NHIEMVU: nhiemnhu.NHIEMVU,
              GHICHU: item.ghichu,
              NHANVIEN_GIAO_ID: this.nguoigv_id,
              NGAYGIAO: this.formatDate(this.ngaygiao),
              SO_DT: item.so_dt,
              STATUS: 0,
              NHIEMVU_ID: nhiemvu_id,
              KQXL_ID: null,
            });
          });
        });
        }
        
        let check = await api.fn_an_capnhat_giaophieu_nv(this.axios, {
          json_giaophieu_nv: data,
        });
        this.$emit("giaoviec_success", true);
        this.$toast.success("Giao phiếu thành công");
        this.getGridDsnhanvien();
        this.getGridDsnhanvienDagiaoNV();
      } catch (e) {
        console.log(e);
        this.$toast.error("Giao phiếu không thành công");
      }
    },
    xoagiao() {
      this.$confirm(`Bạn thật sự muốn xoá danh mục này không?`, {
        confirmButtonText: "Có",
        cancelButtonText: "Không",
        type: "warning",
        }).then(async () => {
          try {
              if(this.dsphieuGV && this.dsphieuGV.length > 0) {
                this.dsphieuGV.forEach(async (phieuid) => {
                  await api.delete_giaophieu_nv(this.axios, {
                    phieu_id: phieuid
                  });
                })
              } else {
                await api.delete_giaophieu_nv(this.axios, {
                  phieu_id: this.phieu_id
                });
              }
              this.getGridDsnhanvien();
              this.getGridDsnhanvienDagiaoNV();
              this.$toast.success("Xóa giao việc thành công");
              this.$emit("giaoviec_success", true);
            } catch (e) {
              this.$toast.success("Xóa giao việc thất bại");
            }
        })
    },
  },
};
</script>
<style>
  .e-edit-dialog .e-gridform .e-table {
    border-spacing: 0px;
  }
</style>
