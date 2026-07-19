<template>
  <b-modal
    id="modal-diemtn-tbc"
    size="lg"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    no-close-on-backdrop
    @show="openForm"
    @hide="resetData"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Cấu hình điểm tín nhiệm nhận
          thông báo
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="onClose"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a
              href="#"
              @click.prevent="NhapMoi()"
              :class="{ disabled: !enabledNhapMoi }"
            >
              <span class="icon one-file-plus"></span>Nhập mới
            </a>
          </li>
          <li>
            <a
              href="#"
              @click.prevent="GhiLai()"
              :class="{ disabled: !enabledGhiLai }"
            >
              <span class="icon one-save"></span>Ghi lại
            </a>
          </li>
          <li>
            <a
              href="#"
              @click.prevent="Huy()"
              :class="{ disabled: !enabledHuyBo }"
            >
              <span class="icon one-cancel"></span>Hủy
            </a>
          </li>
          <li>
            <a
              href="#"
              @click.prevent="Xoa()"
              :class="{ disabled: !enabledXoa }"
            >
              <span class="icon one-trash"></span>Xóa
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin điểm tín nhiệm</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w90">Điểm TN từ</div>
                <div class="value">
                  <input
                    v-model="tuDiemTN"
                    ref="refTuTN"
                    type="number"
                    class="form-control text-right"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w90">đến</div>
                <div class="value">
                  <input
                    v-model="denDiemTN"
                    ref="refDenTN"
                    type="number"
                    class="form-control text-right"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="legend-title mart10">
            Danh sách điểm tín nhiệm đã cấu hình
          </div>
          <DataGrid
            :columns="[
              {
                fieldName: 'DIEMTN_TU',
                headerText: 'Điểm TN từ',
                allowFiltering: true,
              },
              {
                fieldName: 'DIEMTN_DEN',
                headerText: 'Điểm TN đến',
                allowFiltering: true,
              },
            ]"
            :dataSource="gridViewData"
            :showFilter="true"
            ref="gridDsDiemTN"
            :allowPaging="false"
            @selectedRowChanged="diemTN_selectedRowChanged"
            panelDataHeight="300"
          />
        </div>
      </div>
    </div>
  </b-modal>
</template>
<style></style>
<script>
import API from "../../../api/ThietLapTBC";
export default {
  props: {},
  data() {
    return {
      gridViewData: [],
      tuDiemTN: 0,
      denDiemTN: 0,
      diem_tbc_id: -1,
      enabledNhapMoi: true,
      enabledGhiLai: true,
      enabledXoa: true,
      enabledHuyBo: true,
    };
  },
  methods: {
    onClose() {
      this.$bvModal.hide("modal-diemtn-tbc");
    },
    setButton(kieu) {
      this.enabledNhapMoi = false;
      this.enabledGhiLai = false;
      this.enabledXoa = false;
      this.enabledHuyBo = false;
      if (kieu == -1) {
        this.enabledGhiLai = true;
        this.enabledHuyBo = true;
      }
      if (kieu == 0) {
        this.enabledNhapMoi = true;
        this.clearData();
      }
      if (kieu == 1) {
        this.enabledGhiLai = true;
        this.enabledHuyBo = true;
        this.clearData();
      }
      if (kieu == 2) {
        this.enabledNhapMoi = true;
        this.enabledXoa = true;
        this.clearData();
      }
      if (kieu == 3) {
        this.enabledNhapMoi = true;
        this.enabledGhiLai = true;
        this.enabledXoa = true;
        this.enabledHuyBo = true;
      }
    },
    clearData() {
      this.tuDiemTN = 0;
      this.denDiemTN = 0;
      this.diem_tbc_id = -1;
    },
    NhapMoi() {
      this.setButton(1);
    },
    async GhiLai() {
      if (!this.kiemTraDuLieu()) {
        return false;
      }
      //   this.TaoDL(!this.enabledNhapMoi)
      if (!this.enabledNhapMoi) {
        await this.TaoDL(true);
      } else {
        await this.TaoDL(false);
      }
      await this.fetchDSDiemTN();
    },
    kiemTraDuLieu() {
      if (this.tuDiemTN < 0) {
        this.$root.toastError("Điểm TN từ phải lớn hơn 0!");
        this.$refs.refTuTN.focus();
        return false;
      }
      if (this.denDiemTN < 0) {
        this.$root.toastError("Điểm TN đến phải lớn hơn 0");
        this.$refs.refDenTN.focus();
        return false;
      }
      if (this.tuDiemTN != 0 && parseInt(this.tuDiemTN) > (this.denDiemTN)) {
        this.$root.toastError("Điểm TN từ không thể lớn hơn Điểm TN đến!");
        this.$refs.refTuTN.focus();
        return false;
      }
      return true;
    },
    async TaoDL(themmoi) {
      try {
        this.loading(true);
        const data = {
          diemTNTBCId: themmoi ? null : this.diem_tbc_id,
          diemTNTu: this.tuDiemTN,
          diemTNDen: this.denDiemTN,
        };
        const response = await API.updateDiemTNTBC(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          //   this.$root.toastSuccess("Xóa dữ liệu thành công!");
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    Huy() {
      this.setButton(0);
    },
    Xoa() {
      this.$confirm(`Bạn chắc chắn muốn xóa không?`, "Thông báo", {
        confirmButtonText: "Đồng ý",
        cancelButtonText: "Hủy",
      })
        .then(() => {
          this.fetchXoaDiemTN();
        })
        .catch(() => {});
    },
    async fetchXoaDiemTN() {
      try {
        this.loading(true);
        const response = await API.xoaDiemTNTBC(this.axios, this.diem_tbc_id);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Xóa dữ liệu thành công!");
          await this.fetchDSDiemTN();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    resetData() {
        this.clearData()
        this.gridViewData = []
    },
    openForm() {
      this.setButton(-1);
      this.fetchDSDiemTN();
    },
    async fetchDSDiemTN() {
      const response = await API.getDSDanhMuc(this.axios, "DIEMTN_TBC");
      if (response.data.error_code === "BSS-00000000") {
        this.gridViewData = response.data.data;
      }
    },
    diemTN_selectedRowChanged(data) {
      if (data) {
        this.diem_tbc_id = data.DIEMTN_TBC_ID;
        this.tuDiemTN = data.DIEMTN_TU;
        this.denDiemTN = data.DIEMTN_DEN;
        this.setButton(3);
      }
    },
  },
};
</script>
