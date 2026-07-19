<template>
  <ejs-dialog
    target=".main-wrapper"
    :position="{ X: 'center', Y: 'top' }"
    :visible="false"
    ref="frmNguoiGT"
    width="60%"
    id="frmNguoiGT_form"
    :showCloseIcon="false"
    :header="headerTemplate"
  >
    <div class="popup-content">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a ref="tsbtnChapNhan" @click="tsbtnChapNhan_Click">
              <span class="icon one-file-attach"></span> Chấp nhận
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="row">
            <div class="col-sm-7 col-12">
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w90">Nhóm</div>
                    <div class="value">
                      <div class="select-custom">
                        <Select2Ext
                          @change="cboNhomCTV_ChangeValue"
                          ref="cboNhomCTV"
                          :allowFiltering="true"
                        ></Select2Ext>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w90">
                      <div class="value padt7">
                        <div class="check-action">
                          <input
                            type="checkbox"
                            @change="chkCTV_CheckedChanged"
                            ref="chkCTV"
                          />
                          <span class="name"></span>
                        </div>
                      </div>
                      <div class="key w40 nowrap">Đơn vị</div>
                    </div>
                    <div class="value">
                      <div class="select-custom">
                        <Select2Ext
                          disable
                          @change="cboDonVi_CTV_ChangeValue"
                          ref="cboDonVi_CTV"
                          :allowFiltering="true"
                        ></Select2Ext>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="table-content">
            <DataGridCustom
              v-bind:columns="columns"
              ref="gridDanhSach"
              :showFilter="false"
              :allowPaging="false"
              @rowSelected="gridviewDanhSach_FocusedRowChanged"
              @recordDoubleClick="gridviewDanhSach_DoubleClick"
              :enablePagingServer="false"
              :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
              panelDataHeight="80"
            ></DataGridCustom>
          </div>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import Select2Ext from "@/modules/contract/setup/DeclareLandline/components/Select2Ext";
import DataGridCustom from "@/modules/contract/setup/DeclareLandline/components/DataGridCustom";
import Vue from "vue";
var headerTemplateVue = Vue.component("demo1", {
  template:
    '<span><span class="title" id="frmNguoiGtTitle">Dialog</span><button id="closeButton" class="e-dlg-closeicon-btn e-control e-btn e-lib e-flat e-icon-btn" type="button" title="Close" aria-label="Close"><span class="e-btn-icon e-icon-dlg-close e-icons"></span></button></span>',
  data() {
    return {
      data: {},
    };
  },
});
import { NHOM_LNV } from "@/const/enums";
export default {
  components: {
    Select2Ext,
    DataGridCustom,
  },
  data() {
    return {
      nhanvien_id: 0,
      headerTemplate: function () {
        return { template: headerTemplateVue };
      },
      donvi_id: 0,
      loainv_id: 0,
      nhomlnv_id: 0,
      ctv_id: 0,
      ten_ctv: "",
      ma_ctv: "",
      kieu: 0,
      formload: true,
      chapnhan: false,
      Text: "",
      lblLienHe: "",
      columns: [
        {
          fieldName: "stt",
          headerText: "STT",
          allowSorting: false,
          width: "90",
          textAlign: "center",
          autoFit: "true",
        },
        {
          fieldName: "ma_the",
          headerText: "Mã thẻ",
          allowSorting: false,
          width: "100",
          textAlign: "center",
          autoFit: "true",
        },
        {
          fieldName: "ma_nv",
          headerText: "Mã nhân viên",
          allowSorting: false,
          width: "auto",
          textAlign: "center",
          autoFit: "true",
        },
        {
          fieldName: "ma_dv",
          headerText: "Mã đơn vị",
          allowSorting: false,
          width: "auto",
          textAlign: "center",
          autoFit: "true",
        },
        {
          fieldName: "ten_nv",
          headerText: "Tên nhân viên",
          allowSorting: false,
          width: "auto",
          textAlign: "center",
          autoFit: "true",
        },
        {
          fieldName: "ten_dv",
          headerText: "Tên đơn vị",
          allowSorting: false,
          width: "auto",
          textAlign: "center",
          autoFit: "true",
        },
      ],
    };
  },
  mounted() {
  },
  methods: {
    ShowDialog() {
      this.$nextTick(() => {
        this.frmNguoiGT_FormLoad();
        document.getElementById("closeButton").addEventListener("click",() => { this.closeForm()});
        this.$refs.frmNguoiGT.show();
      });
    },
    closeForm() {
      console.log("vào close form");
      this.$emit("chonNhanvien", false);
      this.$refs.frmNguoiGT.hide();
    },
    async frmNguoiGT_FormLoad() {
      this.loading(true);
      this.$refs.tsbtnChapNhan.classList.remove("disabled");
      if (this.nhomlnv_id == NHOM_LNV.CONGTACVIEN) {
        this.Text = "Chọn cộng tác viên";
        this.lblLienHe = "Cộng tác viên: ";
        document.getElementById("frmNguoiGtTitle").innerHTML = "Chọn cộng tác viên"
      } else {
        this.Text = "Chọn người giới thiệu";
        this.lblLienHe = "Người giới thiệu: ";
        document.getElementById("frmNguoiGtTitle").innerHTML = "Chọn người giới thiệu"
      }

      try {
        let rs = await this.axios.post(
          "/web-ccdv/dongbo_danhba_vnp/ht_ds_nhomnv_combobox",
          {}
        );
        this.$refs.cboNhomCTV.dataSource = rs.data.data;
        this.$refs.cboNhomCTV.dataTextField = "ten";
        this.$refs.cboNhomCTV.dataValueField = "loainv_id";
        console.log(rs.data.data);
        this.$refs.cboNhomCTV.SelectedIndex = 0;
        this.cboDonVi_CTV_ChangeValue();

        this.formload = false;
        if (this.ctv_id != 0) {
          this.$refs.cboNhomCTV.value = this.loainv_id;
          if (this.donvi_id != 0) {
            this.$refs.cboDonVi_CTV.value = this.donvi_id;
            this.$refs.chkCTV.checked = true;
          }
          this.HienThiDanhSachNhanVien();
          for (let i = 0; i < this.$refs.gridDanhSach.dataSource.length; i++) {
            if (
              this.ctv_id == this.$refs.gridDanhSach.dataSource[i].nhanvien_id
            ) {
              this.$$refs.gridDanhSach.selectRow(i);
              break;
            }
          }
        }
        this.loading(false);
      } catch (err) {
        this.loading(false);
        console.log(err);
        this.$toast.error(err.message);
      }
    },

    cboNhomCTV_ChangeValue() {
      this.$refs.gridDanhSach.dataSource = [];
      if (this.$refs.cboNhomCTV.dataSource.length > 0) {
        if (this.$refs.cboNhomCTV.value == null) return;
        this.HienThiDanhSachNhanVien();
      }
    },

    async chkCTV_CheckedChanged() {
      if (this.$refs.chkCTV.checked) {
        this.$refs.cboDonVi_CTV.disabled = false;
        try {
          let rs = await this.axios.post(
            "/web-quantri/gantbgoictv/lay_ds_donvi_ctv",
            {}
          );
          this.$refs.cboDonVi_CTV.dataSource = rs.data.data;
          console.log(rs.data.data);
          this.$refs.cboDonVi_CTV.dataTextField = "ten_dv";
          this.$refs.cboDonVi_CTV.dataValueField = "donvi_id";
          this.$refs.cboDonVi_CTV.value =
            this.$refs.cboDonVi_CTV.dataSource.length > 0
              ? this.$refs.cboDonVi_CTV.dataSource[0].donvi_id
              : null;
        } catch (err) {
          console.log(err);
          this.$toast.error(err.message);
        }
      } else {
        this.$refs.cboDonVi_CTV.disabled = true;
        this.HienThiDanhSachNhanVien();
      }
    },

    cboDonVi_CTV_ChangeValue() {
      this.$refs.gridDanhSach.dataSource = [];
      if (this.$refs.cboDonVi_CTV.dataSource.length > 0) {
        this.HienThiDanhSachNhanVien();
      }
    },
    async HienThiDanhSachNhanVien() {
      this.$refs.gridDanhSach.DataSource = [];
      try {
        this.loading(true);
        let rs;
        let dt = [];
        if (this.$refs.chkCTV.checked) {
          rs = await this.axios.post(
            "web-hopdong/hopdong/khac/sp_lay_ds_nhanvien_gt",
            {
              donvi_id: this.$refs.cboDonVi_CTV.value,
              loainv_id: this.$refs.cboNhomCTV.value,
            }
          );
          dt = rs.data.data;
        } else {
          rs = await this.axios.post(
            "web-hopdong/hopdong/khac/sp_lay_ds_nhanvien_gt",
            {
              donvi_id: -1,
              loainv_id: this.$refs.cboNhomCTV.value,
            }
          );
          dt = rs.data.data;
        }
        this.$refs.gridDanhSach.dataSource = dt;
        this.loading(false);
      } catch (err) {
        this.loading(false);
        this.$toast.error(err.message);
      }
    },

    async gridviewDanhSach_DoubleClick() {
      try {
        let item = this.$refs.gridDanhSach.getSelectedRecords();
        if (item.length <= 0) return;
        let data = item[0];
        data.loainv_id = this.$refs.cboNhomCTV.value;
        data.donvi_id = this.$refs.cboDonVi_CTV.value;
        this.$emit("chonNhanvien", item[0]);
        this.$refs.frmNguoiGT.hide();
      } catch (err) {
        console.log(err);
        this.$toast.error(err.message);
      }
    },

    async gridviewDanhSach_FocusedRowChanged() {
      try {
        let item = this.$refs.gridDanhSach.getSelectedRecords();
        if (item.length <= 0) return;
        if (this.nhomlnv_id == NHOM_LNV.CONGTACVIEN) {
          this.lblLienHe =
            "Cộng tác viên: " + item[0].ma_nv + " - " + item[0].ten_nv;
        } else {
          this.lblLienHe =
            "Người giới thiệu: " + +item[0].ma_nv + " - " + item[0].ten_nv;
        }
      } catch (err) {
        this.$toast.error(err.message);
      }
    },
    async tsbtnChapNhan_Click() {
      try {
        if (this.$refs.gridDanhSach.dataSource.length <= 0) {
          this.$toast.error("Không có nhân viên nào !");
          return;
        }
        let item = this.$refs.gridDanhSach.getSelectedRecords();
        this.$emit("chonNhanvien", item[0]);
        this.$refs.frmNguoiGT.hide();
      } catch (err) {
        this.$toast.error(err.message);
      }
    },
  },
};
</script>
<style>
#frmNguoiGT_form_title {
    width: 100% !important;
    font-weight: bold;
}
</style>
