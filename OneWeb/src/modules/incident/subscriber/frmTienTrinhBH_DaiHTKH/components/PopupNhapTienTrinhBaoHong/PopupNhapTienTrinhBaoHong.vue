<script>
import api from "../../api";

export default {
  name: "PopupNhapTienTrinhBaoHong",
  props: {
    show: {
      type: Boolean,
      default: false
    },
    tienTrinhBaoHong: {
      type: Object,
      default: () => {}
    },
    phieuYeuCau: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      showModal: false,
      noidung: ""
    };
  },
  watch: {
    show(newValue) {
      this.showModal = newValue;
    },
    tienTrinhBaoHong(val) {
      const { baohong_id, noidung } = val;
      if (baohong_id) this.noidung = noidung;
    }
  },
  methods: {
    closeModal() {
      this.$emit("update:show", false);
      this.showModal = false;
    },
    async submitEdit() {
      const { baohong_id, tientrinhbh_id } = this.tienTrinhBaoHong;
      const payload = {
        baohong_id,
        tientrinh_id: tientrinhbh_id,
        noidung: this.noidung
      };
      try {
        const response = await api.editTienTrinhBaoHong(
          this.$root.axios,
          payload
        );
        const { data, error_code, message } = response.data;
        if (error_code === "BSS-00000000") {
          if (data === "Bạn không thể cập nhật tiến trình của người khác")
            this.$toast.error(data, "title");
          else {
            this.$toast.success(data, "title");
            this.$emit("submit:edit");
            this.closeModal();
            this.noidung = "";
          }
        } else this.$toast.error(message, "title");
      } catch (error) {
        this.$toast.error(error, "title");
      }
    },
    async submitAdd() {
      console.log(this.phieuYeuCau);
      const { phanvung_id, baohong_id, nguoi_cn } = this.phieuYeuCau;
      const payload = {
        vtientrinh_id: 0,
        vbaohong_id: baohong_id,
        vnguoi_cn: nguoi_cn,
        vphanvung_id: phanvung_id,
        vnoidung: this.noidung
      };
      try {
        const response = await api.editTienTrinhBaoHong(
          this.$root.axios,
          payload
        );
        const { data, error_code, message } = response.data;
        if (error_code === "BSS-00000000") {
          this.$toast.success(data, "title");
          this.$emit("submit:add");
          this.closeModal();
          this.noidung = "";
        } else this.$toast.error(message, "title");
      } catch (error) {
        this.$toast.error(error, "title");
      }
    }
  }
};
</script>

<template>
  <b-modal
    size="lg"
    dialog-class="dialog-content"
    body-class="body-content"
    header-class="popup-header"
    content-class="popup-box"
    v-model="showModal"
    hide-footer
    no-close-on-backdrop
    no-close-on-esc
  >
    <template #modal-header
      ><div class="title">
        <div class="title">
          <span class="icon one-notepad"></span> Nhập nội dung tiến trình báo
          hỏng
        </div>
      </div>
      <div
        class="close -ap icon-close"
        data-dismiss="modal"
        @click="closeModal"
      ></div>
    </template>
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="tienTrinhBaoHong && tienTrinhBaoHong.baohong_id">
          <a @click="submitEdit"> <span class="icon one-save"></span> Sửa </a>
        </li>
        <li v-else>
          <a @click="submitAdd">
            <span class="icon one-save"></span> Ghi lại
          </a>
        </li>

        <li>
          <a href="#"> <span class="icon one-book1"></span> Hướng dẫn </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="info-row">
          <div class="key w130">Nội dung nhắc mẫu</div>
          <div class="value">
            <div class="select-custom">
              <select name="" id="" class="form-control">
                <option value=""></option>
              </select>
            </div>
          </div>
        </div>
        <textarea
          autofocus
          name=""
          class="form-control"
          style="height: 86px;resize: none;"
          id=""
          cols="30"
          rows="10"
          v-model="noidung"
        ></textarea>
      </div>
    </div>
  </b-modal>
</template>
