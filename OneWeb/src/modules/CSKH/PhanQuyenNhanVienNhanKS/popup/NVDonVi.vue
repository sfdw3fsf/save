<template>
  <ejs-dialog
      :enableResize="true"
      :allowDragging="true"
      ref="frmPhanQuyenNVKS_LoaiDonVi"
      :header="'Phân quyền nhân viên khảo sát - Đơn vị'"
      showCloseIcon="true"
      width="60%"
      :visible="false"
      :open="dialogOpen"
      :close="closeDialog"
      :overlayClick="closeDialog"
      :isModal="true"
  >
  <!-- <b-modal
    id="popup-nvDonVi"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    @show="getDsDonViKS"
  > -->
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript: void(0)" @click="capNhap">
              <span class="icon one-save"></span> Ghi lại
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="box-move-select-table">
            <div class="box-col">
              <div class="legend-title">Đơn vị đã gán</div>
                <CSKHGrid :columns="dgvDonViKSDaGan.columns" ref="dgvDonViKSDaGan"
                    :dataSource="dgvDonViKSDaGan.dataSource" :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    addClassWhenRowChecked="font-weight-bold"
                    @dataBound="onGridDataBound(1)"
                    dataKeyField="DONVI_ID" :showFilter="true" :allowPaging="true" :enablePagingServer="false">
                </CSKHGrid>
                <!-- <DataGrid2

                  :columns="fieldDonViKSDaGan"
                  ref="gridDonViKSDaGan"
                  :dataSource="dsDonViKSDaGanGetter"
                  :showColumnCheckbox="true"
                  dataKeyField="DONVI_ID"
                  :showFilter="true"
                  @selectedItemsChanged="
                    (data) => (gridDonViKSDaGanSelectedValues = data)
                  "
                /> -->
            </div>
            <div class="actions">
              <button class="btn" @click="onAddChuaGan()">
                <span class="-ap icon-chevron-thin-right"></span>
              </button>
              <button class="btn" @click="onAddDaGan()">
                <span class="-ap icon-chevron-thin-left"></span>
              </button>
            </div>
            <div class="box-col">
              <div class="legend-title">Đơn vị chưa gán</div>
                <CSKHGrid :columns="dgvDonViKSChuaGan.columns" ref="dgvDonViKSChuaGan"
                    :dataSource="dgvDonViKSChuaGan.dataSource" :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    addClassWhenRowChecked="font-weight-bold"
                    @dataBound="onGridDataBound(2)"
                    dataKeyField="DONVI_ID" :showFilter="true" :allowPaging="true" :enablePagingServer="false">
                </CSKHGrid>
                <!-- <DataGrid2

                  :columns="fieldDonViKSChuaGan"
                  ref="gridDonViKSChuaGan"
                  :dataSource="dsDonViKSChuaGanGetter"
                  :showColumnCheckbox="true"
                  dataKeyField="DONVI_ID"
                  :showFilter="true"
                  @selectedItemsChanged="
                    (data) => (gridDonViKSChuaGanSelectedValues = data)
                  "
                >
                </DataGrid2> -->
            </div>
          </div>
        </div>
      </div>
    </div>
  <!-- </b-modal> -->
  </ejs-dialog>
</template>
<style scoped>
.legend-title {
    font-weight: 600 !important;
}
</style>
<script>
import PhanQuyenNhanVienNhanKSApi from "./../../api/PhanQuyenNhanVienNhanKSApi";
import { mapActions, mapState, mapGetters } from "vuex";
import CSKHGrid from "../../utils/CSKHGrid"
export default {
  components: {CSKHGrid},
  props: {
    // nhanVienId: {
    //   type: Number,
    // },
  },
  created() {},
  data() {
    return {
      nhanVienId: 0,
      gridDonViKSDaGanSelectedValues: [],
      gridDonViKSChuaGanSelectedValues: [],

      dgvDonViKSDaGan: {
        columns: [ { fieldName: "TEN_DV", headerText: "Đơn vị đã gán", allowFiltering: true, allowSorting: true } ],
        dataSource: [],
        selectedRows: []
      },
      dgvDonViKSChuaGan: {
        columns: [ { fieldName: "TEN_DV", headerText: "Đơn vị chưa gán", allowFiltering: true, allowSorting: true } ],
        dataSource: [],
        selectedRows: []
      },
    };
  },
  computed: {
    ...mapGetters("phanquyennhanviennhankhaosat", [
      "dsDonViKSDaGanGetter",
      "dsDonViKSChuaGanGetter",
    ]),
  },
  methods: {
    ...mapActions("phanquyennhanviennhankhaosat", [
      "getDsDonViKSDaGan",
      "getDsDonViKSChuaGan",
      "setDanhSachGanChuaGan",
    ]),
    async dialogOpen() {
      try {
        this.$root.showLoading(true);
        this.dgvDonViKSDaGan.dataSource = []
        this.dgvDonViKSChuaGan.dataSource = []
        await Promise.all([
          this.getDsDonViKS(true),
          this.getDsDonViKS(false),
        ]);
      }
      catch (e) {
        console.log(e)
      }
      finally {
        this.$root.showLoading(false);
      }
    },
    openDialog() {
        this.$refs.frmPhanQuyenNVKS_LoaiDonVi.show();
    },
    closeDialog() {
        this.$emit("onHide");
        this.$refs.frmPhanQuyenNVKS_LoaiDonVi.hide();
    },
    onAddChuaGan() {
      let ds = this.$refs.dgvDonViKSDaGan.grid.getSelectedRecords()
      // this.dgvDonViKSChuaGan.dataSource = this.dgvDonViKSChuaGan.dataSource.concat(ds)

      let indexes = this.$refs.dgvDonViKSChuaGan.grid.getSelectedRowIndexes()

      this.dgvDonViKSChuaGan.dataSource = this.dgvDonViKSChuaGan.dataSource.concat(ds)
      let dsIds = this.dgvDonViKSChuaGan.dataSource.map(x => x.DONVI_ID)

      ds.forEach(e => {
        indexes.push(dsIds.indexOf(e.DONVI_ID))
      })

      // this.$refs.dgvLoaiHinhThueBaoChuaGan.flagSelectedRowIndexes = indexes;
      this.dgvDonViKSChuaGan.selectedRows = indexes


      this.dgvDonViKSDaGan.dataSource = this.dgvDonViKSDaGan.dataSource.filter(x => !ds.map(x => x.DONVI_ID).includes(x.DONVI_ID))
    },
    onAddDaGan() {
      let ds = this.$refs.dgvDonViKSChuaGan.grid.getSelectedRecords()
      // this.dgvDonViKSDaGan.dataSource = this.dgvDonViKSDaGan.dataSource.concat(ds)

      let indexes = this.$refs.dgvDonViKSDaGan.grid.getSelectedRowIndexes()

      this.dgvDonViKSDaGan.dataSource = this.dgvDonViKSDaGan.dataSource.concat(ds)
      let dsIds = this.dgvDonViKSDaGan.dataSource.map(x => x.DONVI_ID)

      ds.forEach(e => {
        indexes.push(dsIds.indexOf(e.DONVI_ID))
      })

      // this.$refs.dgvLoaiHinhThueBaoChuaGan.flagSelectedRowIndexes = indexes;
      this.dgvDonViKSDaGan.selectedRows = indexes

      this.dgvDonViKSChuaGan.dataSource = this.dgvDonViKSChuaGan.dataSource.filter(x => !ds.map(x => x.DONVI_ID).includes(x.DONVI_ID))
    },
    async getDsDonViKS(daGan) {
      try {
        let data = {
          nhanVienId: this.nhanVienId,
          kieu: daGan ? 2 : 1,
          pageSize: 1000,
          pageNo: 1
        };

        let response = await this.$root.context.get("/web-cskh/api/phan-quyen-nvks/don-vi", data)
        if (daGan) {
          this.dgvDonViKSDaGan.dataSource = []
          if (response.error_code == "BSS-00000000") {
            this.dgvDonViKSDaGan.dataSource = response.data.data || []
            this.dgvDonViKSDaGan.dataSource = this.dgvDonViKSDaGan.dataSource.sort((a, b) => a.TEN_DV.localeCompare(b.TEN_DV));
          }
        }
        else {
          this.dgvDonViKSChuaGan.dataSource = []
          if (response.error_code == "BSS-00000000") {
            this.dgvDonViKSChuaGan.dataSource = response.data.data || []
            this.dgvDonViKSChuaGan.dataSource = this.dgvDonViKSChuaGan.dataSource.sort((a, b) => a.TEN_DV.localeCompare(b.TEN_DV));
          }
        }
      }
      catch (e) {
        console.log(e)
      }
    },
    async capNhap() {
      if (this.nhanVienId == -1) {
        this.$root.toastError("Bạn chưa chọn nhân viên")
        return
      }
      this.loading(true);
      let data = {
        nhanVienId: this.nhanVienId,
        dsDonViId: this.dgvDonViKSDaGan.dataSource.map((x) => x.DONVI_ID).join(),
      };
      PhanQuyenNhanVienNhanKSApi.capNhapDonViKS(this.axios, data)
        .then((response) => {
          console.log(response);
          if (response.data.error_code === "BSS-00000000") {
            this.$root.toastSuccess("Cập nhật thành công !");
          } else {
            this.$root.toastError(response.data.message);
          }
        })
        .catch((reject) => {
          console.log(reject);
          if (
            reject.response &&
            reject.response.data &&
            reject.response.data.message
          )
            this.$root.toastError(reject.response.data.message);
          else this.$root.toastError("Có lỗi xảy ra!");
        });
      this.loading(false);
    },
    onGridDataBound(type) {
      switch (type) {
        case 1:
          this.$refs.dgvDonViKSDaGan.grid.selectRows(this.dgvDonViKSDaGan.selectedRows)
          this.$refs.dgvDonViKSDaGan.enableCheckAll();
          this.dgvDonViKSDaGan.selectedRows = []
          break;
        case 2:
          this.$refs.dgvDonViKSChuaGan.grid.selectRows(this.dgvDonViKSChuaGan.selectedRows)
          this.$refs.dgvDonViKSChuaGan.enableCheckAll();
          this.dgvDonViKSChuaGan.selectedRows = []
          break;
      }
    },
  },
};
</script>
