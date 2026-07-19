<template>
  <b-modal
    id="modal-dsphieugachnovnp"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    no-close-on-backdrop
    @hide="resetData"
    @show="openForm"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Danh sách phiếu thanh toán di
          động
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="close()"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a
              href="#"
              @click.prevent="LayDS"
              title="Lấy danh sách phiếu thanh toán"
            >
              <span class="icon one-file-attach1"></span>
              Lấy DS
            </a>
          </li>
          <li>
            <a
              href="#"
              v-on:click.prevent="XoaPhieu"
              :class="{ disabled: !thanhtoan_selected }"
              title="Xóa phiếu"
            >
              <span class="icon one-file-trash"></span> Xóa phiếu
            </a>
          </li>
          <li>
            <a
              href="#"
              v-on:click.prevent="InBL"
              :class="{ disabled: dsPhieuThanhToan.length <= 0 }"
              title="In biên lai"
            >
              <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> In
              BL
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin thanh toán</div>
          <div class="row">
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w30 nowrap">User gạch nợ</div>
                <div class="value">
                  <input
                    v-model="userGachNo"
                    type="text"
                    class="form-control highlight"
                    @keyup.enter="enterUser"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-2 col-12">
              <div class="info-row">
                <div class="key w30 nowrap">Mã TT</div>
                <div class="value">
                  <input
                    autofocus
                    ref="maTT"
                    v-model="maTT"
                    type="text"
                    class="form-control highlight"
                    @keyup.enter="enterMaTT"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-3 col-12">
              <div class="info-row">
                <div class="key w30 nowrap">Tiền trả</div>
                <div class="value">
                  <input
                    v-model="tienTra"
                    type="text"
                    class="form-control text-right"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12 custom-kycuoc">
              <app-ky-cuoc @handleChangeKyCuoc="handleKyHoaDon"></app-ky-cuoc>
            </div>
          </div>
          <div class="legend-title mart10">Danh sách phiếu TT</div>
          <DataGrid
            :columns="[
              { fieldName: 'MA_TN', headerText: 'Mã TN', allowFiltering: true },
              {
                fieldName: 'TEN_NV',
                headerText: 'Thu ngân',
                allowFiltering: true,
              },
              { fieldName: 'MA_TT', headerText: 'Mã TT', allowFiltering: true },
              {
                fieldName: 'MANV_TC',
                headerText: 'Mã NVTC',
                allowFiltering: true,
              },
              {
                fieldName: 'SOPHIEU',
                headerText: 'Số phiếu',
                allowFiltering: true,
              },
              {
                fieldName: 'USER_GACH',
                headerText: 'User gạch',
                allowFiltering: true,
              },
              {
                fieldName: 'NGAY_TT',
                headerText: 'Ngày TT',
                allowFiltering: true,
              },
              {
                fieldName: 'SOTIENTRA',
                headerText: 'Tổng trả',
                allowFiltering: true,
                type: 'number',
                format: 'N0',
              },
              {
                fieldName: 'PHIEU_ID_NEO',
                headerText: 'Phiếu ID VNP',
                allowFiltering: true,
              },
              {
                fieldName: 'SOPHIEU_NEO',
                headerText: 'Số phiếu VNP',
                allowFiltering: true,
              },
            ]"
            :dataSource="dsPhieuThanhToan"
            :showFilter="true"
            @selectedRowChanged="thanhtoan_selectedRowChanged"
            ref="gridThanhToan"
            :allowPaging="true"
            :enablePagingServer="true"
            :totalRecords="totalItems"
            @pageChanged="thanhtoan_gridChanged"
            :key="keyReset"
          />
        </div>
      </div>
    </div>
  </b-modal>
</template>
<style src="./style.scss" scoped></style>
<script>
import moment from "moment";
import KyCuoc from "../../components/KyCuoc.vue";
import API from "../../api/GachNoDiDongTienMat";
import API2 from "../../api/DieuChinhNoKhieuNai";
import { previewPrint } from "../../../../utils/util";

export default {
  name: "FormDSPhieuGachNoVNP",
  components: { appKyCuoc: KyCuoc },
  props: {},
  computed: {},
  mounted() {},
  data() {
    return {
      dsPhieuThanhToan: [],
      userGachNo: "",
      maTT: "",
      tienTra: "",
      kyCuoc: "",
      thanhtoan_selected: null,
      totalItems: 0,
      paramsSearch: {
        userGach: "",
        maTT: "",
      },
      keyReset: 1,
    };
  },
  methods: {
    handleKyHoaDon(e) {
      this.kyCuoc = moment(e.p_kyhoadon).format("YYYYMM") + e.p_chuky_no;
    },
    openForm() {
      //   this.$refs.maTT.focus()
    },
    close() {
      this.$bvModal.hide("modal-dsphieugachnovnp");
    },
    resetData() {
      this.dsPhieuThanhToan = [];
      this.userGachNo = "";
      this.maTT = "";
      this.tienTra = "";
    },
    async fetchDanhSach(page, size) {
      try {
        this.loading(true);
        const params = {
          kyCuoc: this.kyCuoc,
          userGach: this.paramsSearch.userGach,
          maTT: this.paramsSearch.maTT,
          pageNum: page + 1,
          pageSize: size,
          totalRow: 1,
        };
        const response = await API.getDanhSachPhieuTTDiDong(this.axios, params);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data.data.length > 0
        ) {
          this.dsPhieuThanhToan = response.data.data.data;
          this.totalItems = response.data.data.totalElement;
        } else {
          this.dsPhieuThanhToan = [];
          this.thanhtoan_selected = null;
          this.totalItems = 0;
          this.resetFill();
          this.$root.toastError("Không có dữ liệu. Mời thử lại!");
        }
      } catch (error) {
        this.dsPhieuThanhToan = [];
        this.thanhtoan_selected = null;
        this.totalItems = 0;
        this.resetFill();
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    thanhtoan_gridChanged(args) {
      this.fetchDanhSach(args.pageIndex, args.pageSize);
    },
    async fetchXoaPhieu() {
      this.loading(true);
      try {
        const data = {
          pKyCuoc: this.kyCuoc,
          pPhieuId: this.thanhtoan_selected.PHIEU_ID,
          pNguoiHuy: this.$auth.getUserName() || "",
        };
        const response = await API2.xoaPhieu(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Xóa phiếu thành công");
          this.paramsSearch.userGach = this.$auth.getUserName();
          this.paramsSearch.maTT = "";
          this.keyReset += 1;
          this.fetchDanhSach(0, 10);
        } else {
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    thanhtoan_selectedRowChanged(data) {
      if (data) {
        this.thanhtoan_selected = data;
        this.fillThongTin(data);
      } else {
        this.thanhtoan_selected = null;
        this.fillThongTin(null);
      }
    },
    fillThongTin(data) {
      if (data) {
        this.tienTra = Number(data.SOTIENTRA).toLocaleString("en-En");
        this.maTT = data.MA_TT;
        this.userGachNo = data.USER_GACH;
      } else {
        this.resetFill();
      }
    },
    resetFill() {
      this.tienTra = "";
      this.maTT = "";
      this.userGachNo = "";
    },
    LayDS() {
      this.paramsSearch.userGach = this.$auth.getUserName();
      this.paramsSearch.maTT = "";
      this.keyReset += 1;
      this.fetchDanhSach(0, 10);
    },
    XoaPhieu() {
      if (!this.thanhtoan_selected) {
        this.$root.toastError("Hãy chọn phiếu để xóa!");
        return;
      }
      // check user_gach # ma_nd
      if (this.thanhtoan_selected.USER_GACH != this.$auth.getUserName()) {
        this.$root.toastError("Bạn không được xóa phiếu thanh toán của user khác");
        return;
      }
      this.$confirm(
        `Bạn thật sự muốn xóa phiếu thanh toán không ?`,
        "Thông báo",
        {
          confirmButtonText: "Đồng ý",
          cancelButtonText: "Không đồng ý",
        }
      )
        .then(() => {
          this.fetchXoaPhieu();
        })
        .catch(() => {});
    },
    async InBL() {
      if (this.dsPhieuThanhToan.length <= 0) {
        this.$root.toastError("Chưa có dữ liệu để in biên lai. Bạn hãy kiểm tra lại!");
        return;
      }

      this.loading(true);
      try {
        const baseUrl = process.env.API;
        await this.axios({
          url: `${baseUrl}/web-thuno/api/quan-ly-thu-no/in-bien-nhan-thu-tien`,
          method: "POST",
          responseType: "blob",
          data: {
            ds: this.dsPhieuThanhToan,
          },
        }).then((response) => {
          previewPrint(response.data);
        });
      } catch (error) {
        this.$root.toastError("Có lỗi trong quá trình xem biên lai!");
      } finally {
        this.loading(false);
      }
    },
    enterUser() {
      if (this.userGachNo.trim() == "") {
        this.$root.toastError("Chưa nhập user gạch nợ!");
        return;
      }
      this.paramsSearch.userGach = this.userGachNo.trim();
      this.paramsSearch.maTT = "";
      this.keyReset += 1;
      this.fetchDanhSach(0, 10);
    },
    enterMaTT() {
      if (this.maTT.trim() == "") {
        this.$root.toastError("Chưa nhập mã thanh toán!");
        return;
      }
      this.paramsSearch.userGach = "";
      this.paramsSearch.maTT = this.maTT.trim();
      this.keyReset += 1;
      this.fetchDanhSach(0, 10);
    },
  },
};
</script>
