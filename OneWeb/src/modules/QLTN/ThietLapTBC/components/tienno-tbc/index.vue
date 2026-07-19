<template>
  <b-modal
    id="modal-tienno-tbc"
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
          <span class="icon one-notepad"></span> Cấu hình tiền nợ nhận thông báo
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
          <div class="legend-title">Thông tin tiền nợ</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w90">Tiền nợ từ</div>
                <div class="value">
                  <input
                    v-model="tuTienNo"
                    ref="refTuTien"
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
                    v-model="denTienNo"
                    ref="refDenTien"
                    type="number"
                    class="form-control text-right"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="legend-title mart10">Danh sách tiền nợ đã cấu hình</div>
          <DataGrid
            :columns="[
              {
                fieldName: 'TIENNO_TU',
                headerText: 'Tiền nợ từ',
                allowFiltering: true,
              },
              {
                fieldName: 'TIENNO_DEN',
                headerText: 'Tiền nợ đến',
                allowFiltering: true,
              },
            ]"
            :dataSource="gridViewData"
            :showFilter="true"
            ref="gridDsTienNo"
            :allowPaging="false"
            @selectedRowChanged="tieno_selectedRowChanged"
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
      tuTienNo: 0,
      denTienNo: 0,
      tienno_tbc_id: -1,
      enabledNhapMoi: true,
      enabledGhiLai: true,
      enabledXoa: true,
      enabledHuyBo: true,
    };
  },
  methods: {
    onClose() {
      this.$bvModal.hide("modal-tienno-tbc");
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
      this.tuTienNo = 0;
      this.denTienNo = 0;
      this.tienno_tbc_id = -1;
    },
    NhapMoi() {
      this.setButton(1);
    },
    async GhiLai() {
      if (!this.kiemTraDuLieu()) {
        return false;
      }
      if (!this.enabledNhapMoi) {
        await this.TaoDL(true);
      } else {
        await this.TaoDL(false);
      }
      await this.fetchDSTienNo();
    },
    kiemTraDuLieu() {
      if (this.tuTienNo < 0) {
        this.$root.toastError("Tiền nợ từ phải lớn hơn 0!");
        this.$refs.refTuTien.focus();
        return false;
      }
      if (this.denTienNo < 0) {
        this.$root.toastError("Tiền nợ đến phải lớn hơn 0");
        this.$refs.refDenTien.focus();
        return false;
      }
      if (this.tuTienNo != 0 && parseInt(this.tuTienNo) > parseInt(this.denTienNo)) {
        this.$root.toastError("Tiền nợ từ không thể lớn hơn Tiền nợ đến!");
        this.$refs.refTuTien.focus();
        return false;
      }
      return true;
    },
    async TaoDL(themmoi) {
      try {
        this.loading(true);
        const data = {
          tienNoTBCId: themmoi ? null : this.tienno_tbc_id,
          tienNoTu: this.tuTienNo,
          tienNoDen: this.denTienNo,
        };
        const response = await API.updateTienNoTBC(this.axios, data);
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
          this.fetchXoaTienNo();
        })
        .catch(() => {});
    },
    async fetchXoaTienNo() {
      try {
        this.loading(true);
        const response = await API.xoaTienNoTBC(this.axios, this.tienno_tbc_id);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Xóa dữ liệu thành công!");
          await this.fetchDSTienNo();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    resetData() {
      this.clearData();
      this.gridViewData = [];
    },
    openForm() {
      this.setButton(-1);
      this.fetchDSTienNo();
    },
    async fetchDSTienNo() {
      const response = await API.getDSDanhMuc(this.axios, "TIENNO_TBC");
      if (response.data.error_code === "BSS-00000000") {
        this.gridViewData = response.data.data;
      }
    },
    tieno_selectedRowChanged(data) {
      if (data) {
        this.tienno_tbc_id = data.TIENNO_TBC_ID;
        this.tuTienNo = data.TIENNO_TU;
        this.denTienNo = data.TIENNO_DEN;
        this.setButton(3);
      }
    },
  },
};
</script>
