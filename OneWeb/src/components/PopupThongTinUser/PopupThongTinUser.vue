<template>
  <b-modal
    id="thongTinUser"
    modal-class="fullscreen-modal"
    body-class="body-content"
    header-class="popup-header"
    content-class="popup-box"
    hide-footer
    no-close-on-backdrop
    no-close-on-esc
    v-model="showModal"
  >
    <template #modal-header>
      <div class="title">
        <span class="icon icon-notepad"></span>
        Thông tin User
      </div>
      <div
        class="close -ap icon-close"
        data-dismiss="modal"
        @click="closeModal"
      ></div>
    </template>

    <div class="list-actions-top">
      <ul class="list">
        <li class="cursor-pointer">
          <a> <span class="icon one-file-attach"></span>Tạo mới </a>
        </li>
        <li class="cursor-pointer">
          <a> <span class="icon one-file-plus"></span>Cập nhật </a>
        </li>
        <li class="cursor-pointer">
          <a> <span class="icon one-cancel"></span>Xoá </a>
        </li>
        <li @click="closeModal" class="cursor-pointer">
          <a class="text-danger">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát
          </a>
        </li>
      </ul>
    </div>

    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">Thông tin User</div>
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w90 text-main fw6">Email</div>
                  <div class="value">
                    <div class="select-custom">
                      <SelectExt
                        :dataSource="emails"
                        :allowFiltering="true"
                        dataTextField="email"
                        dataValueField="email_id"
                      ></SelectExt>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w80 text-main fw6">Loại User</div>
                  <div class="value">
                    <div class="select-custom">
                      <SelectExt
                        :dataSource="[]"
                        :allowFiltering="true"
                        dataTextField="STATUS"
                        dataValueField="STATUS_ID"
                      ></SelectExt>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90 text-main fw6">Username</div>
              <div class="value">
                <input type="text" class="form-control" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w90 text-main fw6">Password</div>
              <div class="value">
                <input type="text" class="form-control" />
              </div>
            </div>
            <div class="box-form">
              <div class="legend-title">Danh sách thành viên của đại lý</div>
              <div class="table-content">
                <table class="table-result table-gachle">
                  <thead class="center">
                    <tr>
                      <th class="w20"></th>
                      <th>Username</th>
                      <th>Loại User</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr class="highlight">
                      <td class="w20 center">
                        <span class="fa fa-play text-main"></span>
                      </td>
                      <td class="selected highlight1"></td>
                      <td></td>
                    </tr>
                    <tr>
                      <td class="w20 center"></td>
                      <td></td>
                      <td></td>
                    </tr>
                    <tr>
                      <td class="w20 center"></td>
                      <td></td>
                      <td></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div class="tc-pagination">
                <a class="btn  one-arrow-left-forward"></a>
                <a class="btn fa fa-angle-double-left"></a>
                <a class="btn fa fa-angle-left"></a>
                <span class="text">Bản ghi</span>
                <div class="dropdown inline">
                  <div class="fw6 input-number" data-toggle="dropdown">
                    <span class="text-main">10</span>/1033
                  </div>
                  <div class="dropdown-menu">
                    <a class="dropdown-item">10</a>
                    <a class="dropdown-item">20</a>
                  </div>
                </div>
                <a class="btn fa fa-angle-right"></a>
                <a class="btn fa fa-angle-double-right"></a>
                <a class="btn one-arrow-right-forward"></a>
              </div>
            </div>
          </div>
          <div class="col-sm-8 col-12">
            <div class="box-form">
              <div class="legend-title">Thông tin liên hệ thanh toán</div>
              <div class="table-content">
                <table class="table-result table-gachle">
                  <thead class="center">
                    <tr>
                      <th class="w20"></th>
                      <th>STT</th>
                      <th>Mã thanh toán</th>
                      <th>Tên thanh toán</th>
                      <th>Mã thanh toán</th>
                      <th>Trạng thái</th>
                      <th>Đăng ký</th>
                      <th>Huỷ</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr class="highlight">
                      <td class="w20 center">
                        <span class="fa fa-play text-main"></span>
                      </td>
                      <td class="selected highlight1"></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                    </tr>
                    <tr>
                      <td class="w20 center"></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                    </tr>
                    <tr>
                      <td class="w20 center"></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                      <td></td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <div class="tc-pagination">
                <a class="btn  one-arrow-left-forward"></a>
                <a class="btn fa fa-angle-double-left"></a>
                <a class="btn fa fa-angle-left"></a>
                <span class="text">Bản ghi</span>
                <div class="dropdown inline">
                  <div class="fw6 input-number" data-toggle="dropdown">
                    <span class="text-main">10</span>/1033
                  </div>
                  <div class="dropdown-menu">
                    <a class="dropdown-item">10</a>
                    <a class="dropdown-item">20</a>
                  </div>
                </div>
                <a class="btn fa fa-angle-right"></a>
                <a class="btn fa fa-angle-double-right"></a>
                <a class="btn one-arrow-right-forward"></a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import api from "./api.js";
export default {
  name: "PopupThongTinUser",
  props: {
    khachHangId: {
      type: Number,
      default: 263205
    },
    hdkh_id: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      showModal: false,
      emails: [],
      loaiUsers: [],
      users: []
    };
  },
  watch: {
    async showModal(newValue) {
      if (newValue) {
        await this.onModalShow();
      }
    }
  },
  methods: {
    async onModalShow() {
      await this.getLoaiUsers();
      await this.getDanhSachEmail();
      await this.getDanhSachUser();
      await this.getDanhSachHopDongUser();
    },
    async getDanhSachHopDongUser(p_khachhang_id = 1639609, p_hdkh_id = 0) {
      const payload = {
        p_khachhang_id,
        p_hdkh_id,
        p_hduser_id: 0,
        p_user_id: 0
      };
      const response = await api.getDanhSachHopDongUser(
        this.$root.axios,
        payload
      );
      const { error_code, data } = response.data;
      if (error_code === "BSS-00000000") {
        this.emails = data;
      }
    },
    async getDanhSachEmail(p_khachhang_id = 263205, p_hdkh_id = 0) {
      const payload = {
        p_khachhang_id,
        p_hdkh_id
      };
      const response = await api.getDanhSachEmail(this.$root.axios, payload);
      const { error_code, data } = response.data;
      if (error_code === "BSS-00000000") {
        this.emails = data;
      }
    },
    async getDanhSachUser(p_khachhang_id = 263205, p_hdkh_id = 0) {
      const payload = {
        p_khachhang_id,
        p_hdkh_id
      };
      const response = await api.getDanhSachUser(this.$root.axios, payload);
      console.log("getDanhSachEmail ~ response", response.data);
    },
    async getLoaiUsers() {
      const response = await api.getLoaiUser(this.$root.axios);
      console.log("getLoaiUsers ~ response", response.data);
    },

    closeModal() {
      this.$bvModal.hide("thongTinUser");
    }
  }
};
</script>

<style>
.cursor-pointer {
  cursor: pointer;
}

.fullscreen-modal .modal-dialog {
  max-width: 100%;
  margin: 0;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  height: 100vh;
  padding: 25px;
  display: flex;
  position: fixed;
  z-index: 100000;
}
</style>
