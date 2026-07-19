<template>
  <ejs-dialog
    :enableResize="true"
    :allowDragging="true"
    header='<div class="title"><span class="icon one-notepad"></span> Cập nhật thông tin mã in ghép</div>'
    ref="popupCapNhapMaInGhep"
    showCloseIcon="true"
    width="90%"
    target='.main-wrapper'
    :visible="false"
    :open="dialogOpen"
    :close="closeDialog"
    :overlayClick="closeDialog"
    id="timkiemmainghep"
  >
    <!-- <b-modal
    id="popup-capNhapMaInGhep"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    @show="onShow"
    @hide="onHide"
  > -->
    <div class="modal-content popup-box">
      <!-- <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Cập nhật thông tin mã in ghép
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="$bvModal.hide('popup-capNhapMaInGhep')"
        ></div>
      </div> -->
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a
              href="javascript: void(0)"
              @click="onTaoMoiClicked"
              data-toggle="tooltip"
              title="Tạo mới mã in ghép"
            >
              <span class="icon nc-icon-glyph ui-1_circle-add"></span> Tạo mới
            </a>
            <appYesNoModal
              content="Bạn có muốn sinh mã in ghép không ?"
              idModal="popup-taoMoiConfirm"
              @onOk="onTaoMoiConfirmed"
            />
          </li>
          <li>
            <a
              href="javascript: void(0)"
              @click="onCapNhapClicked"
              data-toggle="tooltip"
              title="Cập nhật thông tin mã in ghép"
            >
              <span class="icon one-calendar-sync"></span>Cập nhật
            </a>
          </li>
          <li>
            <a
              href="javascript: void(0)"
              @click="onXoaClicked"
              data-toggle="tooltip"
              title="Xóa mã in ghép"
            >
              <span class="icon one-trash"></span> Xóa
            </a>
            <appYesNoModal
              content="Bạn thật sự muốn xóa mã in ghép không ?"
              idModal="popup-xoaConfirm"
              @onOk="onXoaConfirmed"
            />
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin tạo mã in ghép</div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w90">Mã KH</div>
                <div class="value">
                  <div class="input-more-button">
                    <button class="btn" @click.prevent="ShowSearchAccount">
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                    <input
                      type="text"
                      class="form-control font-weight-bold"
                      ref="textMaKH"
                      v-model="params.txtMaKH"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w90">Mã in ghép</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control highlight font-weight-bold"
                    v-model="params.txtMaIG"
                    disabled
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">Đơn vị quản lý</div>
                <div class="value">
                  <div class="select-custom">
                    <b-form-select
                      id="id-chuky-no"
                      v-model="donViQuanLySelected"
                      :options="dsDonViQuanLyGetter"
                      value-field="DONVI_ID"
                      text-field="TEN_DV"
                      class="form-control"
                      disabled
                    >
                    </b-form-select>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w90">Mã NVTC</div>
                <div class="value">
                  <div class="input-more-button">
                    <button class="btn" @click="onNVTC">
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                    <input
                      type="text"
                      disabled
                      class="form-control highlight font-weight-bold"
                      v-model="params.txtMaNVTC"
                    />
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w90">Tuyến thu</div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control highlight font-weight-bold"
                    v-model="params.txtTuyenThu"
                    disabled
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w120">Địa chỉ IG</div>
                <div class="value">
                  <div class="select-custom">
                    <div class="input-more-button">
                      <button class="btn" @click="chonDiaChiKH">
                        <span class="-ap icon-more_horiz"></span>
                      </button>
                      <input
                        type="text"
                        class="form-control font-weight-bold"
                        v-model="params.txtDiaChiIG"
                        disabled
                      />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            <div class="title">Danh sách mã in ghép</div>
          </div>
          <div class="table-content">
            <CSKHGrid
              :columns="fieldDsMaInGhep"
              ref="gridDsMaInGhep"
              :dataSource="dsMIGTheoKhachHangGetter"
              :showColumnCheckbox="false"
              dataKeyField="MIG_ID"
              :showFilter="false"
              @selectedRowChanged="onDsMaInGhepRowChange"
              :allowPaging="true"
              :enablePagingServer="false"
              :enabledSelectFirstRow="true"
              addClassWhenRowChecked="font-weight-bold"
            >
            </CSKHGrid>
          </div>
        </div>
        <div class="box-move-select-table">
          <div class="box-col">
            <div class="box-form">
              <div class="legend-title">
                <div class="title">Danh sách mã thanh toán đã gán</div>
              </div>
              <div class="table-content" style="height: 300px">
                <CSKHGrid
                  :columns="fieldDsMaThanhToanDaGan"
                  ref="gridDsMaThanhToanDaGan"
                  :dataSource="pDsMaTTDaGan"
                  :showColumnCheckbox="true"
                  :defaultColumnCheckboxChecked="true"
                  dataKeyField="THANHTOAN_ID"
                  :textBoldWhenSelected="true"
                  :showFilter="false"
                  @selectedRowChanged="onDsMaThanhToanDaGanRowChange"
                  :allowPaging="true"
                  :enablePagingServer="false"
                  :enabledSelectFirstRow="true"
                  addClassWhenRowChecked="font-weight-bold"
                >
                </CSKHGrid>
              </div>
            </div>
          </div>
          <div class="actions padt0">
            <button class="btn" @click="onSetChuaGan">
              <span class="nc-icon-outline arrows-1_minimal-right"></span>
            </button>
            <button class="btn" @click="onSetTatCaChuaGan">
              <span class="nc-icon-outline arrows-1_double-right"></span>
            </button>
            <button class="btn" @click="onSetDaGan">
              <span class="nc-icon-outline arrows-1_minimal-left"></span>
            </button>
            <button class="btn" @click="onSetTatCaDaGan">
              <span class="nc-icon-outline arrows-1_double-left"></span>
            </button>
          </div>
          <div class="box-col">
            <div class="box-form">
              <div class="legend-title">
                <div class="title">Danh sách mã thanh toán chưa gán</div>
              </div>
              <div class="table-content" style="height: 300px">
                <CSKHGrid
                  :columns="fieldDsMaThanhToanChuaGan"
                  ref="gridDsMaThanhToanChuaGan"
                  :dataSource="pDsMaTTChuaGan"
                  :showColumnCheckbox="true"
                  dataKeyField="THANHTOAN_ID"
                  :showFilter="false"
                  :textBoldWhenSelected="true"
                  :defaultColumnCheckboxChecked="true"
                  @selectedRowChanged="onDsMaThanhToanChuaGanRowChange"
                  :allowPaging="true"
                  :enablePagingServer="false"
                  :enabledSelectFirstRow="true"
                  addClassWhenRowChecked="font-weight-bold"
                >
                </CSKHGrid>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <b-modal
      id="popupComponents_MIG"
      size="xl"
      hide-footer
      hide-header
      hide-header-close
      body-class="modal-body p-0"
      scrollable
    >
      <component
        :is="popupComponent"
        @form-close="searchAccountClosed"
        :isPopup="true"
      ></component>
    </b-modal>
    <!-- </b-modal> -->
    <DiaChiKhachHangModal
      ref="diaChiKhachHangModal"
      :data="dataDiaChiKH"
      @accept="acceptDiaChiKH"
    />
    <PopupTimKiemNhanVienThuCuoc target="#timkiemmainghep" @acceptChange="getNVTCProcess($event)" ref="dlgTimKiemNVTC"></PopupTimKiemNhanVienThuCuoc>
  </ejs-dialog>
</template>
<style>
.text-mig {
  font-weight: bold;
  color: blue;
}
</style>
<script>
import API from "../../api/TraCuuKhachHangLonApi";
import { mapActions, mapGetters } from "vuex";
import YesNoModal from "../../utils/YesNoModal";
import CustomDataGrid from "../../utils/CustomDataGrid";
import CSKHGrid from "../../utils/CSKHGrid";
import SearchAccount from "@/modules/search/subscriber/SearchAccount";
import DiaChiKhachHangModal from "./../../../contract/setup/SurveyRequest/popups/ChonDiaChi/DiaChiKhachHangModal.vue";
import PopupTimKiemNhanVienThuCuoc from '../../utils/search-nvtc/PopupTimKiemNhanVienThuCuoc'

export default {
  props: {
    // khachHangId: {
    //   type: Number,
    //   default: 0
    // },
    // tucthi: {
    //   type: Number,
    //   default: 0,
    // },
    dbId: {
      type: Number,
    },
    // maKH: {
    //   type: String,
    //   default: "",
    // },
    fhdmig_id: {
      type: Number,
      default: 0,
    },
    fmig_id: {
      type: Number,
      default: 0,
    },
  },
  components: {
    appYesNoModal: YesNoModal,
    appCustomDataGrid: CustomDataGrid,
    CSKHGrid,
    SearchAccount,
    DiaChiKhachHangModal,
    PopupTimKiemNhanVienThuCuoc
  },
  async created() {},
  data() {
    return {
      donViQuanLySelected: 0,
      maInGhepSelected: null,
      gridDonViKSDaGanSelectedValues: [],
      gridDonViKSChuaGanSelectedValues: [],
      params: {
        txtMaKH: "",
        txtMaIG: "",
        txtTuyenThu: "",
        txtDiaChiIG: "",
        txtMaNVTC: "",
      },
      pDiaChi: {
        diaChiId: 0,
        tinhId: 0,
        quanId: 0,
        phuongId: 0,
        phoId: 0,
        apId: 0,
        khuId: 0,
        soNha: "",
        dacDiemId: 0,
      },
      dataDiaChiKH: {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: "",
        diachi: "",
      },
      pDsMaTTDaGan: [],
      pDsMaTTChuaGan: [],
      API_SUCCESS: "BSS-00000000",

      fieldDsMaInGhep: [
        {
          fieldName: "MAIN_GHEP",
          headerText: "Mã IG",
          allowFiltering: true,
          cssClass: "ma-in-ghep",
        },
        {
          fieldName: "DIACHI_IG",
          headerText: "Địa chỉ IG",
          allowFiltering: true,
        },
        { fieldName: "MA_NV", headerText: "Mã NVTC", allowFiltering: true },
      ],
      fieldDsMaThanhToanDaGan: [
        { fieldName: "MA_KH", headerText: "Mã KH", allowFiltering: true },
        { fieldName: "MA_TT", headerText: "Mã TT", allowFiltering: true },
        {
          fieldName: "HINHTHUC_TT",
          headerText: "Hình thức TT",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_TT",
          headerText: "Tên thanh toán",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_TT",
          headerText: "Địa chỉ TT",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_BC",
          headerText: "Địa chỉ BC",
          allowFiltering: true,
        },
        { fieldName: "MST", headerText: "MST", allowFiltering: true },
        {
          fieldName: "MANV_TC MANV_TC",
          headerText: "Đại lý",
          allowFiltering: true,
        },
      ],
      fieldDsMaThanhToanChuaGan: [
        { fieldName: "MA_TT", headerText: "Mã TT", allowFiltering: true },
        {
          fieldName: "HINHTHUC_TT",
          headerText: "Hình thức TT",
          allowFiltering: true,
        },
        {
          fieldName: "TEN_TT",
          headerText: "Tên thanh toán",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_TT",
          headerText: "Địa chỉ TT",
          allowFiltering: true,
        },
        {
          fieldName: "DIACHI_BC",
          headerText: "Địa chỉ BC",
          allowFiltering: true,
        },
        { fieldName: "MST", headerText: "MST", allowFiltering: true },
      ],
      popupComponent: null,
      khachHangId:0,
      maKH:"",
      tucthi: 0,
    };
  },
  computed: {
    ...mapGetters("tracuukhachhanglon", [
      "dsDonViQuanLyGetter",
      "dsMIGTheoKhachHangGetter",
      "dsMaThanhToanDaGanGetter",
      "dsMaThanhToanChuaGanGetter",
    ]),
  },
  methods: {
    ...mapActions("tracuukhachhanglon", [
      "getDsDonViQuanLy",
      "getDsMIGTheoKhachHang",
      "getDsMaThanhToanDaGan",
      "getDsMaThanhToanChuaGan",
    ]),
    async dialogOpen() {
      await this.onShow();
    },
    openDialog() {
      this.$refs.popupCapNhapMaInGhep.show();
    },
    closeDialog() {
      this.$emit("onHideEvent");
      this.$refs.popupCapNhapMaInGhep.hide();
    },
    async onShow() {
      // this.$nextTick(() => this.$refs.textMaKH.select());
      await this.getDsDonViQuanLy({ loaiDvId: 2 });
      if (this.dsDonViQuanLyGetter && this.dsDonViQuanLyGetter.length > 0) {
        this.donViQuanLySelected = this.dsDonViQuanLyGetter[0].DONVI_ID;
      }
      this.params.txtMaKH = this.maKH;
      await this.getDsMIGTheoKhachHang({ khachHangId: this.khachHangId });
    },
    async onDsMaInGhepRowChange(dataItem) {
      this.maInGhepSelected = dataItem;
      // params: {
      //   txtMaKH: '',
      //   txtMaIG: '',
      //   txtTuyenThu:'',
      //   txtDiaChiIG: '',
      //   txtMaNVTC: ''
      // }
      if (dataItem) {
        // this.params.txtMaKH = dataItem.MAIN_GHEP
        this.params.txtMaIG = dataItem.MAIN_GHEP;
        this.params.txtTuyenThu = dataItem.TUYENTHU_ID;
        this.params.txtDiaChiIG = dataItem.DIACHI_IG;
        this.params.txtMaNVTC = dataItem.MA_NV;
        let postData = {
          khachHangId: this.khachHangId,
          migId: dataItem.MIG_ID,
        };
        await this.getMaTTDaGan({
          khachHangId: this.khachHangId,
          migId: dataItem.MIG_ID,
          daGan: 1,
        });
        await this.getMaTTChuaGan({
          khachHangId: this.khachHangId,
          migId: dataItem.MIG_ID,
          daGan: 0,
        });

        await this.getDiaChiTheoMIG(dataItem.MIG_ID);
        // await Promise.all[
        //   this.getDsMaThanhToanDaGan(postData),
        //   this.getDsMaThanhToanChuaGan(postData)
        // ];
      }
    },
    async getMaTTDaGan(data) {
      try {
        this.$root.showLoading(true);
        this.pDsMaTTDaGan = [];
        let rs = await this.$root.context.get(
          "/web-cskh/api/mig/thanh-toan",
          data
        );
        if (rs.error_code === this.API_SUCCESS) {
          this.pDsMaTTDaGan = rs.data.data || [];
        }
      } catch (e) {
        console.log(e);
      } finally {
        this.$root.showLoading(false);
      }
    },
    async getMaTTChuaGan(data) {
      try {
        this.$root.showLoading(true);
        this.pDsMaTTChuaGan = [];
        let rs = await this.$root.context.get(
          "/web-cskh/api/mig/thanh-toan",
          data
        );
        if (rs.error_code === this.API_SUCCESS) {
          console.log(rs.data);
          this.pDsMaTTChuaGan = rs.data.data || [];
        }
      } catch (e) {
        console.log(e);
      } finally {
        this.$root.showLoading(false);
      }
    },
    async getDiaChiTheoMIG(mig) {
      try {
        this.pDiaChi = {
          diaChiId: 0,
          tinhId: 0,
          quanId: 0,
          phuongId: 0,
          phoId: 0,
          apId: 0,
          khuId: 0,
          soNha: "",
          dacDiemId: 0,
        };
        let rs = await this.$root.context.get(
          "/web-cskh/api/danh-muc/dia-chi",
          { migId: mig }
        );
        if (rs.error_code === this.API_SUCCESS) {
          let dsDiaChi = rs.data.data || [];
          if (dsDiaChi.length > 0) {
            this.pDiaChi = {
              diaChiId: dsDiaChi[0].DIACHI_ID,
              tinhId: dsDiaChi[0].TINH_ID,
              quanId: dsDiaChi[0].QUAN_ID,
              phuongId: dsDiaChi[0].PHUONG_ID,
              phoId: dsDiaChi[0].PHO_ID,
              apId: dsDiaChi[0].AP_ID,
              khuId: dsDiaChi[0].KHU_ID,
              soNha: dsDiaChi[0].SONHA,
              dacDiemId: dsDiaChi[0].DACDIEM_ID,
            };
          }
        }
      } catch (e) {
      } finally {
      }
    },
    onDsMaThanhToanChuaGanRowChange(dataItem) {},
    onDsMaThanhToanDaGanRowChange(dataItem) {},
    onSetChuaGan() {
      let dsChon = this.$refs.gridDsMaThanhToanDaGan.grid
        .getSelectedRecords()
        .map((x) => x.THANHTOAN_ID);
      let dsItemChon = this.pDsMaTTDaGan.filter((x) =>
        dsChon.includes(x.THANHTOAN_ID)
      );
      this.pDsMaTTDaGan = this.pDsMaTTDaGan.filter(
        (x) => !dsChon.includes(x.THANHTOAN_ID)
      );
      dsItemChon.forEach((item) => this.pDsMaTTChuaGan.push(item));
      this.$refs.gridDsMaThanhToanChuaGan.setCurrentSelectedRow(0);
    },
    onSetDaGan() {
      let dsChon = this.$refs.gridDsMaThanhToanChuaGan.grid
        .getSelectedRecords()
        .map((x) => x.THANHTOAN_ID);
      let dsItemChon = this.pDsMaTTChuaGan.filter((x) =>
        dsChon.includes(x.THANHTOAN_ID)
      );
      this.pDsMaTTChuaGan = this.pDsMaTTChuaGan.filter(
        (x) => !dsChon.includes(x.THANHTOAN_ID)
      );
      dsItemChon.forEach((item) => this.pDsMaTTDaGan.push(item));
      this.$refs.gridDsMaThanhToanDaGan.setCurrentSelectedRow(0);
    },
    onSetTatCaChuaGan() {
      this.pDsMaTTChuaGan = this.pDsMaTTChuaGan.concat(this.pDsMaTTDaGan);
      this.pDsMaTTDaGan = [];
    },
    onSetTatCaDaGan() {
      this.pDsMaTTDaGan = this.pDsMaTTDaGan.concat(this.pDsMaTTChuaGan);
      this.pDsMaTTChuaGan = [];
    },
    onTaoMoiClicked() {
      console.log(this.pDiaChi.phuongId)

      if (this.pDiaChi.phuongId == 0) {
        this.$root.toastError("Bạn chưa chọn địa chỉ in ghép");
        return;
      }

      if (this.params.txtMaNVTC == "") {
        this.$root.toastError("Bạn chưa chọn nhân viên thu cước");
        return;
      }

      this.$bvModal.show("popup-taoMoiConfirm");
    },
    async onTaoMoiConfirmed() {
      try {
        this.$root.showLoading(true);
        let data = {
          khachHangId: this.khachHangId,
          diaChiIg: this.params.txtDiaChiIG,
          tuyenThuId: this.params.txtTuyenThu,
          maNvtc: this.params.txtMaNVTC,
          tinhId: this.dataDiaChiKH.tinh_id,
          quanId: this.dataDiaChiKH.quan_id,
          phuongId: this.dataDiaChiKH.phuong_id,
          phoId: this.dataDiaChiKH.pho_id,
          apId: this.dataDiaChiKH.ap_id,
          khuId: this.dataDiaChiKH.khu_id,
          dacDiemId: this.dataDiaChiKH.dacdiem_id,
          soNha: this.dataDiaChiKH.sonha,
          diaChi: this.params.txtDiaChiIG,
        };
        let rs = await this.$root.context.post("/web-cskh/api/mig", data);
        if (rs.error_code === this.API_SUCCESS) {
          this.$root.toastSuccess("Thêm mới thành công!");
          this.clear();
          await this.onShow();
        }
      } catch (e) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    async onCapNhapClicked() {

      //console.log(this.dsMIGTheoKhachHangGetter)

      // if (this.dsMIGTheoKhachHangGetter.length == 0) {
      //   this.$root.toastError("Chưa có thông tin mã in ghép để Cập nhật!");
      //   return;
      // }

      if (this.params.txtMaIG.length == 0) {
        this.$root.toastError("Chưa có thông tin mã in ghép để Cập nhật!");
        return;
      }

      try {
        this.$root.showLoading(true);
        // this.pDsMaTTDaGan.forEach(e)
        let data = {
          dsMigId: "" + this.maInGhepSelected.MIG_ID,
          dsMtt: "",
        };
        let dsMaTT = [];
        let dsMaIG = [];

        if (this.pDsMaTTDaGan.length > 0) {
          this.pDsMaTTDaGan.forEach((maTT) => {
            dsMaTT.push(maTT.MA_TT);
            if (this.maInGhepSelected)
              dsMaIG.push(this.maInGhepSelected.MIG_ID);
          });

          // data.dsMigId = dsMaIG.join()
          data.dsMtt = dsMaTT.join();
        }
        console.log("data", data);

        let rs = await this.$root.context.post(
          "/web-cskh/api/mig/cap-nhat",
          data
        );
        if (rs.error_code === this.API_SUCCESS) {
          this.$root.toastSuccess("Cập nhập thành công");
        } else {
          this.$root.toastError("Cập nhập thất bại " + rs.message);
        }
      } catch (e) {
        console.log(e);
        this.$root.toastError("Cập nhập thất bại: " + e.response.data.message);
      } finally {
        this.$root.showLoading(false);
      }
    },
    onXoaClicked() {
      //CN MIG- xóa thanh toán khỏi mig
      if (this.dsMIGTheoKhachHangGetter.length == 0) {
        this.$root.toastError("Chưa có thông tin mã in ghép để Xóa!");
        return;
      }
      this.$bvModal.show("popup-xoaConfirm");
    },
    async onXoaConfirmed() {
      try {
        this.$root.showLoading(true);
        if (this.maInGhepSelected) {
          let data = {
            khachHangId: this.khachHangId,
            migId: this.maInGhepSelected.MIG_ID,
          };

          let rs = await this.$root.context.post(
            "/web-cskh/api/mig/delete",
            data
          );
          if (rs.error_code == this.API_SUCCESS) {
            this.$root.toastSuccess("Xóa dữ liệu thành công!");
            await this.getDsMIGTheoKhachHang({ khachHangId: this.khachHangId });
          } else {
            this.$root.toastError("Xóa dữ liệu thất bại!");
          }
        }
      } catch (e) {
        this.$root.toastError("Có lỗi: " + e);
      } finally {
        this.$root.showLoading(false);
      }
    },
    onHide() {
      this.$emit("onHide");
    },
    clear() {
      this.donViQuanLySelected = 0;
      this.maInGhepSelected = null;
      this.gridDonViKSDaGanSelectedValues = [];
      this.gridDonViKSChuaGanSelectedValues = [];
      this.params = {
        txtMaKH: "",
        txtMaIG: "",
        txtTuyenThu: "",
        txtDiaChiIG: "",
        txtMaNVTC: "",
      };
      this.pDiaChi = {
        diaChiId: 0,
        tinhId: 0,
        quanId: 0,
        phuongId: 0,
        phoId: 0,
        apId: 0,
        khuId: 0,
        soNha: "",
        dacDiemId: 0,
      };
      this.dataDiaChiKH = {
        diachi_id: 0,
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        sonha: "",
        diachi: "",
      };
      this.pDsMaTTDaGan = [];
      this.pDsMaTTChuaGan = [];

      // this.$nextTick(() => this.$refs.textMaKH.select())
    },
    ShowSearchAccount() {
      this.popupComponent = SearchAccount;
      SearchAccount.data.isPopup = true;
      this.Popup("popupComponents_MIG");
      //this.$refs.SearchAccountComponent.show();
    },
    onNVTC() {
      this.$refs.dlgTimKiemNVTC.openDialog();
    },
    getNVTCProcess(value) {
      this.params.txtMaNVTC = value.maNVTC;
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
    },
    searchAccountClosed(val) {
      if(val){
        this.params.txtMaKH = val.ma_kh;
      }
      this.ClosePopup("popupComponents_MIG");
    },
    acceptDiaChiKH(data) {
      this.params.txtDiaChiIG = data.diachi;
      this.dataDiaChiKH.diachi_id = data.diachi_id;
      this.dataDiaChiKH.tinh_id = data.tinh_id;
      this.dataDiaChiKH.quan_id = data.quan_id;
      this.dataDiaChiKH.phuong_id = data.phuong_id;
      this.dataDiaChiKH.pho_id = data.pho_id;
      this.dataDiaChiKH.ap_id = data.ap_id;
      this.dataDiaChiKH.khu_id = data.khu_id;
      this.dataDiaChiKH.dacdiem_id = data.dacdiem_id;
      this.dataDiaChiKH.sonha = data.sonha;
      this.dataDiaChiKH.diachi = data.diachi;

      this.pDiaChi.diaChiId = data.diachi_id;
      this.pDiaChi.tinhId = data.tinh_id;
      this.pDiaChi.quanId = data.quan_id;
      this.pDiaChi.phuongId = data.phuong_id;
      this.pDiaChi.phoId = data.pho_id;
      this.pDiaChi.apId = data.ap_id;
      this.pDiaChi.khuId = data.khu_id;
      this.pDiaChi.soNha = data.sonha;
      this.pDiaChi.dacDiemId = data.diachi;

    },
    chonDiaChiKH() {
      //địa chỉ khách hàng
      this.$refs.diaChiKhachHangModal.showModal();
    },
  },
  watch: {
    pDiaChi: {
      handler: async function (value) {},
    },
  },
};
</script>
