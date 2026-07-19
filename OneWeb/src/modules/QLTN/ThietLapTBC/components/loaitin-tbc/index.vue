<template>
  <b-modal
    id="modal-loaitin-tbc"
    size="xl"
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
          <span class="icon one-notepad"></span> Cấu hình loại tin/ loại yêu cầu
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
              @click.prevent="GhiLai()"
              :class="{ disabled: !enabledGhiLai }"
            >
              <span class="icon one-save"></span>Ghi lại
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin loại tin thông báo</div>
          <div class="info-row">
            <div class="key w110">ND nội mạng</div>
            <div class="value">
              <SelectExt
                v-model.trim="idNoiMang"
                :dataSource="dsNoiMang"
                dataTextField="CAUTRUC_TBC"
                dataValueField="CAUTRUC_TBC_ID"
                placeholder="Chọn"
              />
            </div>
          </div>
          <div class="info-row">
            <div class="key w110">ND ngoại mạng</div>
            <div class="value">
              <SelectExt
                v-model.trim="idNgoaiMang"
                :dataSource="dsNgoaiMang"
                dataTextField="CAUTRUC_TBC"
                dataValueField="CAUTRUC_TBC_ID"
                placeholder="Chọn"
              />
            </div>
          </div>
          <div class="legend-title mart10">Danh sách loại tin</div>
          <DataGrid
            :columns="[
              {
                fieldName: 'LOAITIN',
                headerText: 'Loại tin',
                allowFiltering: true,
              },
              {
                fieldName: 'NOIDUNG_NG',
                headerText: 'Nội mạng',
                allowFiltering: true,
              },
              {
                fieldName: 'NOIDUNG_NM',
                headerText: 'Ngoại mạng',
                allowFiltering: true,
              },
            ]"
            :dataSource="gridViewData"
            :showFilter="true"
            ref="gridDsLoaiTin"
            :allowPaging="false"
            @selectedRowChanged="loaitin_selectedRowChanged"
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
      idNoiMang: "",
      dsNoiMang: [],
      idNgoaiMang: "",
      dsNgoaiMang: [],
      loaitin_id: -1,
      enabledGhiLai: true,
      loaitin: "",
    };
  },
  methods: {
    onClose() {
      this.$bvModal.hide("modal-loaitin-tbc");
    },
    setButton(kieu) {
      this.enabledGhiLai = false;
      if (kieu == -1) {
        this.enabledGhiLai = true;
      }
      if (kieu == 1) {
        this.enabledGhiLai = true;
        this.clearData();
      }
      if (kieu == 3) {
        this.enabledGhiLai = true;
      }
    },
    clearData() {
      this.loaitin_id = -1;
    },
    GhiLai() {
      if (!this.kiemTraDuLieu()) {
        return false;
      }
      this.TaoDL();
    },
    kiemTraDuLieu() {
      return true;
    },
    async TaoDL() {
      try {
        this.loading(true);
        const data = {
          loaiTinId: this.loaitin_id,
          cauTrucNMId: this.idNoiMang,
          cauTrucNGId: this.idNgoaiMang,
          loaiTin: this.loaitin,
        };
        const response = await API.updateLoaiTinTBC(this.axios, data);
        if (response.data.error_code === "BSS-00000000") {
          this.$root.toastSuccess("Cập nhật thành công!");
          await this.fetchDSLoaiTin();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    resetData() {
      this.loaitin_id = -1;
      this.gridViewData = [];
      this.idNoiMang = "";
      this.dsNoiMang = [];
      this.idNgoaiMang = "";
      this.dsNgoaiMang = [];
    },
    openForm() {
      this.setButton(-1);
      this.fetchDSLoaiTin();
    },
    async fetchDSLoaiTin() {
      const response = await API.getDSDanhMuc(this.axios, "LOAITIN_TBC");
      if (response.data.error_code === "BSS-00000000") {
        this.gridViewData = response.data.data;
      }
    },
    async loaitin_selectedRowChanged(data) {
      if (data) {
        this.loaitin_id = data.LOAITIN_ID;
        this.loaitin = data.LOAITIN;
        await this.fetchDSNoiMang(data.LOAITIN_ID);
        await this.fetchDSNgoaiMang(data.LOAITIN_ID);
        this.idNoiMang = data.CAUTRUC_NOIMANG ? data.CAUTRUC_NOIMANG : -1;
        this.idNgoaiMang = data.CAUTRUC_NGOAIMANG ? data.CAUTRUC_NGOAIMANG : -1;
      }
    },
    async fetchDSNoiMang(idLoaiTin) {
        try {
            const response = await API.getDSNoiMang(this.axios, idLoaiTin);
            if (response.data.error_code === "BSS-00000000") {
                this.dsNoiMang = response.data.data;
            } else {
                this.dsNoiMang = [];
            }
        } catch (error) {
            this.dsNoiMang = [];
        }
    },
    async fetchDSNgoaiMang(idLoaiTin) {
        try {
            const response = await API.getDSNgoaiMang(this.axios, idLoaiTin);
            if (response.data.error_code === "BSS-00000000") {
            this.dsNgoaiMang = response.data.data;
            } else {
            this.dsNgoaiMang = [];
            }
        } catch (error) {
            this.dsNgoaiMang = [];
        }
    },
  },
};
</script>
