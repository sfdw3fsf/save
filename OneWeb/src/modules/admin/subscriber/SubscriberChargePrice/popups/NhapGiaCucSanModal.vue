<template>
  <b-modal
    ref="popupNhapGiaCucSan"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Nhập giá cước trần, cước sàn
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick" />

      <div class="popup-body">
        <div class="box-form">
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">Cước TB từ</div>
                <div class="value">
                  <input
                    ref="txtTutien"
                    type="text"
                    v-model="cp_txtTutien"
                    @keypress="isNumber($event)"
                    class="form-control tright"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w40">Đến</div>
                <div class="value">
                  <input
                    type="text"
                    ref="txtDenTien"
                    v-model="cp_txtDenTien"
                    @keypress="isNumber($event)"
                    class="form-control tright"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">Cước khoán từ</div>
                <div class="value">
                  <input
                    ref="txtCuocKhoanTu"
                    type="text"
                    v-model="cp_txtCuocKhoanTu"
                    @keypress="isNumber($event)"
                    class="form-control tright"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w40">Đến</div>
                <div class="value">
                  <input
                    ref="txtCuocKhoanDen"
                    type="text"
                    v-model="cp_txtCuocKhoanDen"
                    @keypress="isNumber($event)"
                    class="form-control tright"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w100">Cước TG từ</div>
                <div class="value">
                  <input
                    ref="txtCuocTG_Tu"
                    type="text"
                    v-model="cp_txtCuocTG_Tu"
                    @keypress="isNumber($event)"
                    class="form-control tright"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w40">Đến</div>
                <div class="value">
                  <input
                    ref="txtCuocTG_Den"
                    type="text"
                    v-model="cp_txtCuocTG_Den"
                    @keypress="isNumber($event)"
                    class="form-control tright"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import api from "../SubscriberChargePriceAPI";
export default {
  name: "NhapGiaCucSanModal",
  components: {
    ActionTop,
  },
  props: {
    muccuoctb_id: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      actions: [
        {
          id: "ghilai",
          name: "Ghi lại (F9)",
          active: true,
          icon_class: "one-save",
        },
      ],
      v_muccuoctb_id: this.muccuoctb_id,
      txtTutien: { value: null },
      txtDenTien: { value: null },

      txtCuocKhoanTu: { value: null },
      txtCuocKhoanDen: { value: null },

      txtCuocTG_Tu: { value: null },
      txtCuocTG_Den: { value: null },
    };
  },
  methods: {
    async frmMucCuocTuDen_Load() {
      try {
        let response = await api.frmMucCuocTuDen_Load(
          this.axios,
          this.v_muccuoctb_id
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          console.log(response.data.data);
          if (response.data.data.length > 0) {
            this.txtTutien.value = response.data.data[0].cuoc_tb_tu;
            this.txtDenTien.value = response.data.data[0].cuoc_tb_den;
            this.txtCuocKhoanTu.value = response.data.data[0].cuockhoan_tu;
            this.txtCuocKhoanDen.value = response.data.data[0].cuockhoan_den;
            this.txtCuocTG_Tu.value = response.data.data[0].cuoc_tg_tu;
            this.txtCuocTG_Den.value = response.data.data[0].cuoc_tg_den;
          }
        } else {
          this.$toast.error(
            "Lỗi load thông tin " + response.data.message_detail
          );
        }
      } catch (e) {}
    },
    onActionClick(action) {
      if (action.id == "ghilai") {
        this.tsbtnGhiLai_Click();
      }
    },
    async tsbtnGhiLai_Click() {
      try {
        if (!this.KiemTra_DuLieu()) return;

        var mt = {
          vmucuoctb_id: this.v_muccuoctb_id,
          vcuockhoan_tu: Number(this.txtCuocKhoanTu.value),
          vcuockhoan_den: Number(this.txtCuocKhoanDen.value),
          vcuoc_tg_tu: Number(this.txtCuocTG_Tu.value),
          vcuoc_tg_den: Number(this.txtCuocTG_Den.value),
          vcuoc_tb_tu: Number(this.txtTutien.value),
          vcuoc_tb_den: Number(this.txtDenTien.value),
        };
        await this.CAPNHAT_CUOC_TU_DEN(mt);
      } catch (ex) {
        this.$toast.error("Có lỗi: " + ex);
      }
    },
    async CAPNHAT_CUOC_TU_DEN(dt) {
      try {
        let response = await api.capnhat_cuoc_tu_den(this.axios, dt);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          this.$toast.success("Cập nhật thông tin thành công!");
          await this.frmMucCuocTuDen_Load();
        } else {
          this.$toast.error(
            "Lỗi cập nhật thông tin " + response.data.message_detail
          );
        }
      } catch (e) {}
    },
    KiemTra_DuLieu() {
      if (
        (!this.txtTutien.value && this.txtTutien.value != 0) ||
        Number(this.txtTutien.value.toString().trim()) < 0
      ) {
        this.$toast.error('"Cước TB từ" phải >= 0!');
        this.$refs.txtTutien.focus();
        return false;
      }
      if (
        (!this.txtDenTien.value && this.txtDenTien.value != 0) ||
        Number(this.txtDenTien.value.toString().trim()) < 0
      ) {
        this.$toast.error('"Cước TB đến" phải >= 0!');
        this.$refs.txtDenTien.focus();
        return false;
      }

      if (
        (!this.txtCuocKhoanTu.value && this.txtCuocKhoanTu.value != 0) ||
        Number(this.txtCuocKhoanTu.value.toString().trim()) < 0
      ) {
        this.$toast.error('"Cước khoán từ" phải >= 0!');
        this.$refs.txtCuocKhoanTu.focus();
        return false;
      }
      if (
        (!this.txtCuocKhoanDen.value && this.txtCuocKhoanDen.value != 0) ||
        Number(this.txtCuocKhoanDen.value.toString().trim()) < 0
      ) {
        this.$toast.error('"Cước khoán đến" phải >= 0!');
        this.$refs.txtCuocKhoanDen.focus();
        return false;
      }

      if (
        (!this.txtCuocTG_Tu.value && this.txtCuocTG_Tu.value != 0) ||
        Number(this.txtCuocTG_Tu.value.toString().trim()) < 0
      ) {
        this.$toast.error('"Cước trọn gói từ" phải >= 0!');
        this.$refs.txtCuocTG_Tu.focus();
        return false;
      }
      if (
        (!this.txtCuocTG_Den.value && this.txtCuocTG_Den.value != 0) ||
        Number(this.txtCuocTG_Den.value.toString().trim()) < 0
      ) {
        this.$toast.error('"Cước trọn gói đến" phải >= 0!');
        this.$refs.txtCuocTG_Den.focus();
        return false;
      }

      if (
        Number(this.txtDenTien.value) != 0 &&
        Number(this.txtTutien.value) > Number(this.txtDenTien.value)
      ) {
        this.$toast.error('"Cước TB đến" phải lớn hơn "Cước TB từ"!');
        this.$refs.txtTutien.focus();
        return false;
      }

      if (
        Number(this.txtCuocKhoanDen.value) != 0 &&
        Number(this.txtCuocKhoanTu.value) > Number(this.txtCuocKhoanDen.value)
      ) {
        this.$toast.error('"Cước khoán đến" phải lớn hơn "Cước khoán từ"!');
        this.$refs.txtCuocKhoanTu.focus();
        return false;
      }

      if (
        Number(this.txtCuocTG_Den.value) != 0 &&
        Number(this.txtCuocTG_Tu.value) > Number(this.txtCuocTG_Den.value)
      ) {
        this.$toast.error(
          '"Cước trọn gói đến" phải lớn hơn "Cước trọn gói từ"!'
        );
        this.$refs.txtCuocTG_Tu.focus();
        return false;
      }

      return true;
    },
    isNumeric(str) {
      if (typeof str != "string") return false;
      return !isNaN(str) && !isNaN(parseFloat(str));
    },
    showModal() {
      this.$refs["popupNhapGiaCucSan"].show();
    },
    hideModal() {
      this.$refs["popupNhapGiaCucSan"].hide();
    },
    async handleShowModal() {
      //init data show modal
      this.loading(true);
      try {
        await this.frmMucCuocTuDen_Load();
      } catch (e) {
        this.loading(false);
      } finally {
        this.loading(false);
      }
    },
    isNumber(evt) {
      evt = evt ? evt : window.event;
      var charCode = evt.which ? evt.which : evt.keyCode;
      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        evt.preventDefault();
      } else {
        return true;
      }
    },
    numberWithCommas(n) {
      var parts = n.toString().split(".");
      return (
        parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
        (parts[1] ? "." + parts[1] : "")
      );
    },
  },
  computed: {
    cp_txtTutien: {
      get() {
        if (Number(this.txtTutien.value) > 1000000000) {
          this.$toast.error('"Cước TB từ" phải nhỏ hơn 1,000,000,000!');
          this.txtTutien.value = 999999999;
          this.$refs.txtTutien.focus();
        }
        var txtTutien = this.txtTutien.value
          ? this.numberWithCommas(this.txtTutien.value.toString())
          : this.txtTutien.value == 0
          ? "0"
          : "";
        return txtTutien;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.txtTutien.value = replaced ? Number(replaced) : null;
      },
    },
    cp_txtDenTien: {
      get() {
        if (Number(this.txtDenTien.value) > 1000000000) {
          this.$toast.error('"Cước TB đến" phải nhỏ hơn 1,000,000,000!');
          this.txtDenTien.value = 999999999;
          this.$refs.txtDenTien.focus();
        }
        var txtDenTien = this.txtDenTien.value
          ? this.numberWithCommas(this.txtDenTien.value.toString())
          : this.txtDenTien.value == 0
          ? "0"
          : "";
        return txtDenTien;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.txtDenTien.value = replaced ? Number(replaced) : null;
      },
    },
    cp_txtCuocKhoanTu: {
      get() {
        if (Number(this.txtCuocKhoanTu.value) > 1000000000) {
          this.$toast.error('"Cước khoán từ" phải nhỏ hơn 1,000,000,000!');
          this.txtCuocKhoanTu.value = 999999999;
          this.$refs.txtCuocKhoanTu.focus();
        }
        var txtCuocKhoanTu = this.txtCuocKhoanTu.value
          ? this.numberWithCommas(this.txtCuocKhoanTu.value.toString())
          : this.txtCuocKhoanTu.value == 0
          ? "0"
          : "";
        return txtCuocKhoanTu;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.txtCuocKhoanTu.value = replaced ? Number(replaced) : null;
      },
    },
    cp_txtCuocKhoanDen: {
      get() {
        if (Number(this.txtCuocKhoanDen.value) > 1000000000) {
          this.$toast.error('"Cước khoán đến" phải nhỏ hơn 1,000,000,000!');
          this.txtCuocKhoanDen.value = 999999999;
          this.$refs.txtCuocKhoanDen.focus();
        }
        var txtCuocKhoanDen = this.txtCuocKhoanDen.value
          ? this.numberWithCommas(this.txtCuocKhoanDen.value.toString())
          : this.txtCuocKhoanDen.value == 0
          ? "0"
          : "";
        return txtCuocKhoanDen;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.txtCuocKhoanDen.value = replaced ? Number(replaced) : null;
      },
    },
    cp_txtCuocTG_Tu: {
      get() {
        if (Number(this.txtCuocTG_Tu.value) > 1000000000) {
          this.$toast.error('"Cước trọn gói từ" phải nhỏ hơn 1,000,000,000!');
          this.txtCuocTG_Tu.value = 999999999;
          this.$refs.txtCuocTG_Tu.focus();
        }
        var txtCuocTG_Tu = this.txtCuocTG_Tu.value
          ? this.numberWithCommas(this.txtCuocTG_Tu.value.toString())
          : this.txtCuocTG_Tu.value == 0
          ? "0"
          : "";
        return txtCuocTG_Tu;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.txtCuocTG_Tu.value = replaced ? Number(replaced) : null;
      },
    },
    cp_txtCuocTG_Den: {
      get() {
        if (Number(this.txtCuocTG_Den.value) > 1000000000) {
          this.$toast.error('"Cước trọn gói đến" phải nhỏ hơn 1,000,000,000!');
          this.txtCuocTG_Den.value = 999999999;
          this.$refs.txtCuocTG_Den.focus();
        }
        var txtCuocTG_Den = this.txtCuocTG_Den.value
          ? this.numberWithCommas(this.txtCuocTG_Den.value.toString())
          : this.txtCuocTG_Den.value == 0
          ? "0"
          : "";
        return txtCuocTG_Den;
      },
      set(value) {
        const replaced = value.replaceAll(",", "");
        this.txtCuocTG_Den.value = replaced ? Number(replaced) : null;
      },
    },
  },
  mounted() {},
  watch: {
    muccuoctb_id(val) {
      this.v_muccuoctb_id = val;
    },
  },
};
</script>
