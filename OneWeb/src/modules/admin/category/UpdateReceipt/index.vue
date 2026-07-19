<template>
    <div>
        <!-- <button @click="openLBL" class="btn btn-success m-5">Cập nhật loại biên lai</button> 
         -->
  <b-modal
    id="capnhatlbl"
    modal-class="popup-box"
    header-class="popup-header"
    body-class="none-padding"
    size="xl"
    hide-footer
  >
    <template #modal-header="{ close }">
      <div class="title">
        <span class="icon nc-icon-outline ui-1_settings"></span> Loại
        biên lai
      </div>
      <div
        @click="close"
        class="close -ap icon-close"
        data-dismiss="modal"
      ></div>
    </template>

    <div class="list-actions-top">
      <ul class="list">
        <li
          @click="clickButton('nhapmoi')"
          :class="this.button.nhapmoi ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-plus"></span>Nhập mới(F5) </a>
        </li>
        <li
          @click="clickButton('ghilai')"
          :class="this.button.ghilai ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-plus"></span>Ghi lại(F9) </a>
        </li>
        <li
          @click="clickButton('huy')"
          :class="this.button.huy ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-plus"></span>Hủy(F7) </a>
        </li>
        <li
          @click="clickButton('xoa')" :style="this.button.xoa ? '' : 'pointer-events: none'"
          :class="this.button.xoa ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-plus"></span>Xóa(F8) </a>
        </li>
      </ul>
    </div>
    <div class="popup-body mart10">
      <div class="box-form">
        <div class="legend-title">Thông tin loại biên lai</div>




        <div class="row">
<div class="col-sm-3 col-lg-6">
            <div class="info-row">
                <div class="key w88">Kiểu biên lai</div>
                <div class="value">
                    <div class="select-custom">
                      
                                   
                      <SelectExt v-model="danh_sach_bien_lai" :options="this.options.loaibienlai"> 
                        <option disabled value="0">Select one</option> 
                      </SelectExt>

                                   
                    </div>         
                </div>
            </div>
</div>
          <div class="col-sm-3 col-lg-6">
            <div class="info-row">
              <div class="value">
                <vue-input
                  label="Ký hiệu"
                  labelWidth="85"
                  v-model="ky_hieu"
                ></vue-input>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-lg-6">
            <div class="info-row">
              <div class="key w84">Loại biên lai</div>
              <div class="value">
                <vue-input
                  v-model="loai_bien_lai"
                ></vue-input>
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-lg-6">
            <div class="info-row">
              <div class="value">
                <vue-checkbox
                  labelWidth="150"
                  label="Sử dụng"
                  v-model="su_dung"
                ></vue-checkbox>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="popup-body mart10">
      <div class="box-form" style="margin-top: -35px">
        <div class="legend-title">Danh sách loại biên lai</div>
        <div class="table-content">


                <DataGridEdit
                  ref="grid_danhsach"
                  v-bind:columns="this.options.cols"
                  v-bind:dataSource="this.options.danhsachbienlai_new"
                  :showFilter="true"
                  :enabledSelectFirstRow="true"
                  :editSettings="{ allowEditing: false,allowAdding: false,allowDeleting: false, mode: 'Normal' }"
                  :showColumnPointer="false"
                  :enable-paging-server="false"
                  :allowPaging="true"

                  @rowSelected="rowSelected"
                >
                </DataGridEdit>

<!-- <ejs-grid ref="grid" 
:dataSource="this.options.danhsachbienlai_new" locale="vi-VN" 

:allowSorting='true' 
:allowFiltering='true'  :filterSettings="{showFilterBarOperator: false, mode: 'Immediate', immediateModeDelay: 500}"
:allowPaging='true' 
:pageSettings='{pageSize:10,pageSizes: [10,50,100,200]}' 
:gridLines='1' 
:editSettings="{ allowEditing: false, mode: 'Normal' }" 
:rowSelected="rowSelected"
>


            <e-columns>
              <e-column
                v-for="col in this.options.cols"
                :width="col.width ? col.width : 'auto'"
                :key="col.fieldName"
                :field="col.fieldName"
                :type="col.type"
                :allowEditing="col.allowEditing"
                :allowSorting="col.allowSorting"
                :allowFiltering="col.allowFiltering"
                :clipMode="'EllipsisWithTooltip'"
                :customAttributes="col.customAttributes"
                :headerText="col.headerText"
                :textAlign="col.textAlign"
                headerTextAlign="center"
                :editType="col.editType"
                :format="col.format"
                :isPrimaryKey="col.isPrimaryKey"
                :visible="col.visible"
                :displayAsCheckBox="col.displayAsCheckBox"
              ></e-column>
            </e-columns>
          </ejs-grid> -->
        </div>
      </div>
    </div>
  </b-modal>


    </div>
</template>
<script>
import api from "./api";
import breadcrumb from '@/components/breadcrumb'
import {
  Aggregate,
  CommandColumn,
  Edit,
  Filter,
  Freeze,
  Group,
  Page,
  Resize,
  Sort,
  Toolbar
} from '@syncfusion/ej2-vue-grids'
import DataGridEdit from './components/DataGrid'
import Vue from 'vue'

export default {
  provide: {
    grid: [
      Sort,
      Filter,
      Resize,
      Group,
      Freeze,
      CommandColumn,
      Edit,
      Aggregate,
      Toolbar,
      Page,
    ],
  },
  data() {
    return {
      ktra_checkbox: 0,
      id_loaibienlai: "",
      ten_lbl: "",
      danh_sach_bien_lai: "",
      ky_hieu: "",
      su_dung: false,
      loai_bien_lai: "",
      colum_check: false,
      disabledxoa:true,
      button: {
        nhapmoi: true,
        ghilai: true,
        huy: true,
        xoa: true,
      },
      options: {
        danhsachbienlai: [],
        danhsachbienlai_new: [],
        loaibienlai: [],
        cols: [
          {
            fieldName: "stt",
            headerText: "STT",
            width: 80,
            textAlign: "center",
          },
          {
            fieldName: "ten_loaibl",
            headerText: "Loại biên lai",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "kyhieu",
            headerText: "Ký hiệu",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "ten_kieu",
            headerText: "Kiểu",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
          },
          {
            fieldName: "check",
            headerText: "Sử dụng",
            textAlign: "center",
            width: 120,
            type: "Boolean",
            displayAsCheckBox: true,
          },
        ],
      },
    };
  },
  components: { breadcrumb, DataGridEdit },
  created() {
    
    this.getLoaiBienLai();
    this.getDanhSachBienLai();
  },
  watch: {
    loai_bien_lai(newval)
    {
      this.$bvModal.show("capnhatlbl");
    }
  },
  props: {

  },
  methods: {

    getLoaiBienLai() {
      api.getLoaiBienLai(this.axios, {}).then((res) => {
              let items = [];
              res.data.data.forEach(function (item) {
                items.push({ id: item.kieu_id, text: item.kieu });
              });
              this.options.loaibienlai = items;
              this.$bvModal.show("capnhatlbl");
      });
    },



    getDanhSachBienLai() {
      this.options.danhsachbienlai = [];
      this.options.danhsachbienlai_new = [];
      api.getDanhSachBienLai(this.axios, {}).then((res) => {
        this.options.danhsachbienlai = res.data.data ? res.data.data : [];

        for (var i = this.options.danhsachbienlai.length - 1; i >= 0; i--) {
          if (this.options.danhsachbienlai[i].sudung == "s0") {
            this.colum_check = false;
          } else {
            this.colum_check = true;
          }

          this.options.danhsachbienlai_new.push({
            stt: this.options.danhsachbienlai[i].stt,
            loaibienlai_id: this.options.danhsachbienlai[i].loaibienlai_id,
            ten_loaibl: this.options.danhsachbienlai[i].ten_loaibl,
            kyhieu: this.options.danhsachbienlai[i].kyhieu,
            sudung: this.options.danhsachbienlai[i].sudung,
            kieu: this.options.danhsachbienlai[i].kieu,
            ten_kieu: this.options.danhsachbienlai[i].ten_kieu,
            check: this.colum_check,
          });
        }
        
        this.danh_sach_bien_lai = this.options.danhsachbienlai_new[0].kieu,
        this.ky_hieu = this.options.danhsachbienlai_new[0].kyhieu,
        this.su_dung = this.options.danhsachbienlai_new[0].check,
        this.loai_bien_lai = this.options.danhsachbienlai_new[0].ten_loaibl,
        this.id_loaibienlai = this.options.danhsachbienlai_new[0].loaibienlai_id,
        this.ten_lbl = this.options.danhsachbienlai_new[0].ten_loaibl
        

      });
    },

    rowSelected(args) {
        this.danh_sach_bien_lai = args.data.kieu,
        this.ky_hieu = args.data.kyhieu,
        this.su_dung = args.data.check,
        this.loai_bien_lai = args.data.ten_loaibl,
        this.id_loaibienlai = args.data.loaibienlai_id,
        this.ten_lbl = args.data.ten_loaibl
    },

    ThemMoi() {
      try {
        if (!this.KiemTra_DuLieu()) return false;

        if (this.su_dung == true) {
          this.ktra_checkbox = 1;
        } else {
          this.ktra_checkbox = 0;
        }
        var json_data = {
          TEN_LOAIBL: this.loai_bien_lai.trim(),
          KYHIEU: this.ky_hieu.trim(),
          SUDUNG: this.ktra_checkbox,
          KIEU: this.danh_sach_bien_lai,
        };
        console.log('dulieu',JSON.stringify(json_data))

        this.$confirm(
          `Bạn có chắc chắn muốn thêm mới loại biên lai này không?`,
          {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
          }
        ).then(async () => {
          if (this.ktra_checkbox == 0) {
            this.$confirm(
              `Chưa chọn tình trạng sử dụng, bạn có đồng ý tiếp túc không?`,
              {
                confirmButtonText: "Có",
                cancelButtonText: "Không",
              }
            ).then(async () => {
              this.loading(true);
              api.ThemMoi_CapNhat(this.axios, {
                  dulieu: JSON.stringify(json_data),
                  loai: 1,
                })
                .then((res) => {
                  var mes = res.data.error_code ? res.data.error_code : ''
                  if (mes == "BSS-00000000") {
                    this.loading(false);
                    this.$toast.success("Thêm mới dữ liệu thành công!");
                    this.getDanhSachBienLai();
                  } else {
                    this.loading(false);
                    this.$toast.error("Thêm mới dữ liệu lỗi!");
                    this.getDanhSachBienLai();
                  }
                });
            });
          } else {
            this.loading(true);
            api
              .ThemMoi_CapNhat(this.axios, {
                dulieu: JSON.stringify(json_data),
                loai: 1,
              })
              .then((res) => {
                var mes = res.data.error_code ? res.data.error_code : ''
                if (mes == "BSS-00000000") {
                  this.loading(false);
                  this.$toast.success("Thêm mới dữ liệu thành công!");
                  this.getDanhSachBienLai();
                } else {
                  this.loading(false);
                  this.$toast.error("Thêm mới dữ liệu lỗi!");
                  this.getDanhSachBienLai();
                }
              });
          }
        });
      } catch (error) {
        this.loading(false);
      }
    },

    CapNhat() {
      try {
        if (!this.KiemTra_DuLieu()) return false;

        if (this.su_dung == true) {
          this.ktra_checkbox = 1;
        } else {
          this.ktra_checkbox = 0;
        }

        var json_data = {
          LOAIBIENLAI_ID: this.id_loaibienlai,
          TEN_LOAIBL: this.loai_bien_lai.trim(),
          KYHIEU: this.ky_hieu.trim(),
          SUDUNG: this.ktra_checkbox,
          KIEU: this.danh_sach_bien_lai,
        };

        this.$confirm(`Bạn thật sự muốn cập nhật biên lai không ?`, {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
        }).then(async () => {

          this.loading(true);
          api
            .ThemMoi_CapNhat(this.axios, {
              dulieu: JSON.stringify(json_data),
              loai: 2,
            })
            .then((res) => {
              var mes = res.data.data ? res.data.data : [];
              if (mes == "ok") {
                this.$toast.success("Cập nhật dữ liệu thành công!");
              } else {
                this.$toast.error("Cập nhật dữ liệu lỗi!");
              }
              this.loading(false);
              this.getDanhSachBienLai();
            });
        });
      } catch (error) {
        this.loading(false);
      }
    },

    Xoa() {
      api
        .Xoa(this.axios, {
          loaibienlai_id: this.id_loaibienlai,
        })
        .then((res) => {
          var mes = res.data.data ? res.data.data : [];
          if (mes == "ok") {
            this.$toast.success("Xóa dữ liệu thành công!");
            this.getDanhSachBienLai();
          } else {
            this.$toast.error("Xóa dữ liệu lỗi!");
            this.getDanhSachBienLai();
          }
        });
    },

    clear() {
      this.button.nhapmoi = true;
      this.button.ghilai = true;
      this.button.huy = true;
      this.button.xoa = true;
      this.danh_sach_bien_lai = "";
      this.su_dung = false;
      this.loai_bien_lai = "";
      this.ky_hieu = "";
    },

    clickButton(key) {
      if (key == "nhapmoi") {
        this.clear();
        this.button.nhapmoi = false;
        this.button.xoa = false;
      }

      if (key == "ghilai") {
        
        if (this.button.nhapmoi == false) {
          this.ThemMoi();
        } else {
          this.CapNhat();
        }
        this.clear();
      }
      if (key == "huy") {
        this.clear();
        this.button.xoa = false
      }
      if (key == "xoa") {

        this.$confirm(`Bạn thật sự muốn xóa biên lai không ?`, {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
        }).then(async () => {
          this.Xoa();
        });
        this.clear();
      }
    },
openLBL()
{
    this.$bvModal.show("capnhatlbl");
},
    KiemTra_DuLieu() {
      if (!this.danh_sach_bien_lai) {
        this.$toast.error('Bạn chưa chọn "Kiểu biên lai"!');
        return false;
      }

      if (!this.ky_hieu) {
        this.$toast.error('Bạn chưa nhập "Ký hiệu"!');
        return false;
      }

      if (this.ky_hieu.trim().length > 100) {
        this.$toast.error('Trường "Ký hiệu" vượt quá độ dài cho phép !');
        return false;
      }

      if (!this.loai_bien_lai.trim()) {
        this.$toast.error('Bạn chưa nhập "Loại biên lai"!');
        return false;
      }

      if (this.loai_bien_lai.trim().length > 100) {
        this.$toast.error('Trường "Loại biên lai" vượt quá độ dài cho phép !');
        return false;
      }

      return true;
    },
  },
};
</script>
<style>
/* @import "https://cdn.syncfusion.com/ej2/material.css"; */
.none-padding {
  padding: 0 !important;
}
.modal-dialog {
  border-radius: 0px !important;
  width: 100% !important;
  max-width: 1220px !important;
}

li.non-active a {
  color: #d3d3d3 !important;
}

/* .e-row:active, e-row:focus{background: #8d8b8b !important} */


.e-grid td.e-active{ 
  background-color: #8d8b8b !important}

</style>