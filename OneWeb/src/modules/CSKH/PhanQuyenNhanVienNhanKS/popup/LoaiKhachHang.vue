<template>
  <ejs-dialog
      :enableResize="true"
      :allowDragging="true"
      ref="frmPhanQuyenNVKS_LoaiKhachHang"
      :header="'Phân quyền nhân viên khảo sát - Loại khách hàng'"
      showCloseIcon="true"
      width="60%"
      :visible="false"
      :open="dialogOpen"
      :close="closeDialog"
      :overlayClick="closeDialog"
      :isModal="true"
  >
  <!-- <b-modal
    id="popup-loaikhachhang"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    @show="getDsLoaiKhachHang"
  > -->
    <div class="modal-content popup-box">
      <!-- <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Phân quyền nhân viên khảo sát -
          loại khách hàng
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="$bvModal.hide('popup-loaikhachhang')"
        ></div>
      </div> -->
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="javascript: void(0)" @click="capNhap"> <span class="icon one-save"></span> Ghi lại</a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="box-move-select-table">
            <div class="box-col">
              <div class="legend-title">Loại KH đã gán</div>
                <CSKHGrid :columns="dgvLoaiKhachHangKSDaGan.columns" ref="dgvLoaiKhachHangKSDaGan"
                    :dataSource="dgvLoaiKhachHangKSDaGan.dataSource" :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    addClassWhenRowChecked="font-weight-bold"
                    @dataBound="onGridDataBound(1)"
                    dataKeyField="LOAIKH_ID" :showFilter="true" :allowPaging="true" :enablePagingServer="false">
                </CSKHGrid>
                <!-- <DataGrid2
                  :columns="fieldLoaiKhachHangKSDaGan"
                  ref="gridLoaiKhachHangKSDaGan"
                  :dataSource="dsLoaiKhachHangKSDaGanGetter"
                  :showColumnCheckbox="true"
                  dataKeyField="LOAIKH_ID"
                  :showFilter="true"
                  @selectedItemsChanged="
                    (data) => (gridLoaiKhachHangKSDaGanSelectedValues = data)
                  "
                >
                </DataGrid2> -->
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
              <div class="legend-title">Loại KH chưa gán</div>
                <CSKHGrid :columns="dgvLoaiKhachHangKSChuaGan.columns" ref="dgvLoaiKhachHangKSChuaGan"
                    :dataSource="dgvLoaiKhachHangKSChuaGan.dataSource" :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    addClassWhenRowChecked="font-weight-bold"
                    @dataBound="onGridDataBound(2)"
                    dataKeyField="LOAIKH_ID" :showFilter="true" :allowPaging="true" :enablePagingServer="false">
                </CSKHGrid>
                <!-- <DataGrid2
                  :columns="fieldLoaiKhachHangKSChuaGan"
                  ref="gridLoaiKhachHangKSChuaGan"
                  :dataSource="dsLoaiKhachHangKSChuaGanGetter"
                  :showColumnCheckbox="true"
                  dataKeyField="LOAIKH_ID"
                  :showFilter="true"
                  @selectedItemsChanged="
                    (data) => (gridLoaiKhachHangKSChuaGanSelectedValues = data)
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
import CSKHGrid from "../../utils/CSKHGrid"
import { mapActions, mapState, mapGetters } from "vuex";
export default {
  components: {CSKHGrid},
  props: {
  },
  created() {},
  async mounted() {},
  data() {
    return {
      nhanVienId: 0,
      gridLoaiKhachHangKSDaGanSelectedValues: [],
      gridLoaiKhachHangKSChuaGanSelectedValues: [],
      fieldLoaiKhachHangKSDaGan: [
        {
          fieldName: "TEN_LOAIKH",
          headerText: "KH đã gán",
          allowFiltering: true,
          allowSorting: true,
        },
      ],
      fieldLoaiKhachHangKSChuaGan: [
        {
          fieldName: "TEN_LOAIKH",
          headerText: "KH chưa gán",
          allowFiltering: true,
          allowSorting: true,
        },
      ],
      dgvLoaiKhachHangKSDaGan: {
        columns: [ { fieldName: "TEN_LOAIKH", headerText: "KH đã gán", allowFiltering: true, allowSorting: true } ],
        dataSource: [],
        selectedRows: []
      },
      dgvLoaiKhachHangKSChuaGan: {
        columns: [ { fieldName: "TEN_LOAIKH", headerText: "KH chưa gán", allowFiltering: true, allowSorting: true } ],
        dataSource: [],
        selectedRows: []
      },
    };
  },
  computed: {
    ...mapGetters("phanquyennhanviennhankhaosat", [
      "dsLoaiKhachHangKSDaGanGetter",
      "dsLoaiKhachHangKSChuaGanGetter",
    ]),
  },
  methods: {
    ...mapActions("phanquyennhanviennhankhaosat", [
      "getDsLoaiKhachHangKSChuaGan",
      "getDsLoaiKhachHangKSDaGan",
      "setDanhSachGanChuaGan",
    ]),
    async dialogOpen() {
      try {
        this.$root.showLoading(true);
        this.dgvLoaiKhachHangKSDaGan.dataSource = []
        this.dgvLoaiKhachHangKSChuaGan.dataSource = []
        await Promise.all([
          this.getDsLoaiHinhThueBao(true),
          this.getDsLoaiHinhThueBao(false),
        ]);
        this.$root.showLoading(false);
      }
      catch(e) {
        console.log(e)
      }
      finally {
        this.$root.showLoading(false);
      }
    },
    openDialog() {
        // this.getDsLoaiKhachHang()
        this.$refs.frmPhanQuyenNVKS_LoaiKhachHang.show();
    },
    closeDialog() {
        this.$emit("onHide");
        this.$refs.frmPhanQuyenNVKS_LoaiKhachHang.hide();
    },
    async getDsLoaiHinhThueBao(daGan) {
      try {
        let data = {
          nhanVienId: this.nhanVienId,
          kieu: daGan ? 2 : 1,
          pageSize: 1000,
          pageNo: 1
        };

        let response = await this.$root.context.get("/web-cskh/api/phan-quyen-nvks/loai-khach-hang", data)
        if (daGan) {
          this.dgvLoaiKhachHangKSDaGan.dataSource = []
          if (response.error_code == "BSS-00000000") {
            this.dgvLoaiKhachHangKSDaGan.dataSource = response.data.data || []
            this.dgvLoaiKhachHangKSDaGan.dataSource = this.dgvLoaiKhachHangKSDaGan.dataSource.sort((a, b) => a.TEN_LOAIKH.localeCompare(b.TEN_LOAIKH));
          }
        }
        else {
          this.dgvLoaiKhachHangKSChuaGan.dataSource = []
          if (response.error_code == "BSS-00000000") {
            this.dgvLoaiKhachHangKSChuaGan.dataSource = response.data.data || []
            this.dgvLoaiKhachHangKSChuaGan.dataSource = this.dgvLoaiKhachHangKSChuaGan.dataSource.sort((a, b) => a.TEN_LOAIKH.localeCompare(b.TEN_LOAIKH));
          }
        }
      }
      catch (e) {
        console.log(e)
      }
    },
    //chuyển từ đã gán sang chưa gán
    onAddChuaGan() {
      let ds = this.$refs.dgvLoaiKhachHangKSDaGan.grid.getSelectedRecords()
      // this.dgvLoaiKhachHangKSChuaGan.dataSource = this.dgvLoaiKhachHangKSChuaGan.dataSource.concat(ds)

      let indexes = this.$refs.dgvLoaiKhachHangKSChuaGan.grid.getSelectedRowIndexes()

      this.dgvLoaiKhachHangKSChuaGan.dataSource = this.dgvLoaiKhachHangKSChuaGan.dataSource.concat(ds)
      let dsIds = this.dgvLoaiKhachHangKSChuaGan.dataSource.map(x => x.LOAIKH_ID)

      ds.forEach(e => {
        indexes.push(dsIds.indexOf(e.LOAIKH_ID))
      })

      // this.$refs.dgvLoaiHinhThueBaoChuaGan.flagSelectedRowIndexes = indexes;
      this.dgvLoaiKhachHangKSChuaGan.selectedRows = indexes

      this.dgvLoaiKhachHangKSDaGan.dataSource = this.dgvLoaiKhachHangKSDaGan.dataSource.filter(x => !ds.map(x => x.LOAIKH_ID).includes(x.LOAIKH_ID))
    },
    onAddDaGan() {
      let ds = this.$refs.dgvLoaiKhachHangKSChuaGan.grid.getSelectedRecords()
      // this.dgvLoaiKhachHangKSDaGan.dataSource = this.dgvLoaiKhachHangKSDaGan.dataSource.concat(ds)

      let indexes = this.$refs.dgvLoaiKhachHangKSDaGan.grid.getSelectedRowIndexes()

      this.dgvLoaiKhachHangKSDaGan.dataSource = this.dgvLoaiKhachHangKSDaGan.dataSource.concat(ds)
      let dsIds = this.dgvLoaiKhachHangKSDaGan.dataSource.map(x => x.LOAIKH_ID)

      ds.forEach(e => {
        indexes.push(dsIds.indexOf(e.LOAIKH_ID))
      })

      // this.$refs.dgvLoaiHinhThueBaoChuaGan.flagSelectedRowIndexes = indexes;
      this.dgvLoaiKhachHangKSDaGan.selectedRows = indexes

      this.dgvLoaiKhachHangKSChuaGan.dataSource = this.dgvLoaiKhachHangKSChuaGan.dataSource.filter(x => !ds.map(x => x.LOAIKH_ID).includes(x.LOAIKH_ID))
    },
    getListItem(arr, id, ids) {
      return arr.filter((x) => ids.includes(x[id]));
    },
    async getDsLoaiKhachHang() {
      let dataLoaiHinh = {
        nhanVienId: this.nhanVienId,
        pageSize: 100000,
        pageNo: 1,
      };
      this.loading(true);
      await Promise.all([
        this.getDsLoaiKhachHangKSChuaGan(dataLoaiHinh),
        this.getDsLoaiKhachHangKSDaGan(dataLoaiHinh),
      ]);
      this.loading(false);
    },
    async capNhap() {
      if (this.nhanVienId == -1) {
        this.$root.toastError("Bạn chưa chọn nhân viên")
        return
      }
      this.loading(true)
      let data = {
        nhanVienId: this.nhanVienId,
        dsLoaiKhId: this.dgvLoaiKhachHangKSDaGan.dataSource.map((x) => x.LOAIKH_ID).join(),
      };
      PhanQuyenNhanVienNhanKSApi.capNhapLoaiKhachHangKS(this.axios, data)
        .then((response) => {
          console.log(response)
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
        this.loading(false)
    },
    onGridDataBound(type) {
      switch (type) {
        case 1:
          this.$refs.dgvLoaiKhachHangKSDaGan.grid.selectRows(this.dgvLoaiKhachHangKSDaGan.selectedRows)
          this.$refs.dgvLoaiKhachHangKSDaGan.enableCheckAll();
          this.dgvLoaiKhachHangKSDaGan.selectedRows = []
          break;
        case 2:
          this.$refs.dgvLoaiKhachHangKSChuaGan.grid.selectRows(this.dgvLoaiKhachHangKSChuaGan.selectedRows)
          this.$refs.dgvLoaiKhachHangKSChuaGan.enableCheckAll();
          this.dgvLoaiKhachHangKSChuaGan.selectedRows = []
          break;
      }
    },
  },
  watch: {},
};
</script>
