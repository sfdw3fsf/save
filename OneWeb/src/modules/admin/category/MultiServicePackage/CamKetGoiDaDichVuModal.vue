<template>
  <b-modal
    ref="popupCamKetGoiDaDichVu"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Cam kết gói đa dịch vụ
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick" />

      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Nội dung cam kết gói đa dịch vụ</div>

          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w80">Tên gói</div>
                <div class="value">
                  <input
                    type="text"
                    v-model="l_goi.ten_goi"
                    class="form-control"
                  />
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w60">Cam kết</div>
                <div class="value">
                  <input
                    v-model="input_camket"
                    type="text"
                    @keypress="isNumber($event)"
                    class="form-control"
                  />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w80">Loại hình TB</div>
                <div class="value">
                  <div class="select-custom">
                    <select
                      v-model="loaihinh_selected"
                      @change="changeLoaiHinhTB"
                      class="form-control"
                    >
                      <option
                        v-for="item in danhsach_loaihinh"
                        :key="item.loaitb_id"
                        :value="item.loaitb_id"
                      >
                        {{ item.loaihinh_tb }}
                      </option>
                    </select>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w60">Tiêu chí</div>
                <div class="value">
                  <div class="select-custom">
                    <select
                      v-model="tieuchi_selected"
                      @change="changeTieuChi"
                      class="form-control"
                    >
                      <option
                        v-for="item in danhsach_tieuchi"
                        :key="item.tieuchi_id"
                        :value="item.tieuchi_id"
                      >
                        {{ item.tieuchi }}
                      </option>
                    </select>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w80">Mô tả</div>
            <div class="value">
              <textarea
                name=""
                v-model="input_mota"
                ref="inpuMoTa"
                class="form-control"
                style="height: 100px; resize: none"
              ></textarea>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách cam kết gói đa dịch vụ</div>
          <k-table
            :columns="columns"
            :dataSources="dataSources"
            :allowCheckBox="true"
            @onSelectedRow="onSelectedRow"
            :rowSelectedIndex="1"
            @dataCheckeds="onDataCheckeds"
            :allowFilter="true"
          />
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import KTable from "@/components/kylq_components/KTable.vue";
import ActionTop from "@/components/kylq_components/ActionTop.vue";
import MultiServicePackageAPI from "./MultiServicePackageAPI";
export default {
  name: "CamKetGoiDaDichVuModal",
  components: {
    KTable,
    ActionTop,
  },
  props: {
    goi: {
      type: Object,
      default: () => {},
    },
  },
  data() {
    return {
      actions: [
        {
          id: "nhapmoi",
          name: "Nhập mới",
          active: true,
          icon_class: "one-file-plus",
        },
        {
          id: "ghilai",
          name: "Ghi lại",
          active: true,
          icon_class: "one-save",
        },
        {
          id: "huy",
          name: "Huỷ",
          active: true,
          icon_class: "nc-icon-glyph ui-1_circle-remove",
        },
        {
          id: "xoa",
          name: "Xoá",
          active: true,
          icon_class: "one-trash",
        },
      ],
      columns: [
        {
          field: "loaihinh_tb",
          label: "Loại hình thuê bao",
          allowFilter: true,
        },
        {
          field: "tg_ck",
          label: "Tháng cam kết",
          allowFilter: true,
        },
      ],
      dataSources: [],
      l_goi: this.goi != null ? this.goi : {},
      input_tengoi: "",
      input_camket: "0",
      input_mota: "",
      danhsach_tieuchi: [],
      tieuchi_selected: null,
      loaihinh_selected: null,
      danhsach_loaihinh: [],
      rowSelected: null,
    };
  },
  methods: {
    onActionClick(action) {
      if (action.id == "nhapmoi") {
        this.setActiveActions(1);
      } else if (action.id == "ghilai") {
        this.ghiDuLieu();
      } else if (action.id == "huy") {
        //Huỷ nếu đang có row focus table thì init lại row đấy
        if (this.rowSelected != null) {
          this.onSelectedRow(this.rowSelected);
        } else {
          this.setActiveActions(2);
        }
      } else if (action.id == "xoa") {
        if (this.rowSelected == null) {
          this.$alert("Hãy chọn cam kết muốn xoá!", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "warning",
          });
          return;
        }
        this.$confirm("Bạn thật sự muốn xóa dữ liệu không?", "Thông báo", {
          confirmButtonText: "Đồng ý",
          cancelButtonText: "Huỷ",
        }).then(() => {
          this.sp_del_camket(
            this.rowSelected.goi_id,
            this.rowSelected.loaitb_id,
            this.rowSelected.tieuchi_id
          );
        });
      }
    },
    setActiveActions(kieu) {
      this.actions.forEach((item) => {
        item.active = false;
      });
      if (kieu == -1) {
        //bắt đầu
        this.actions[
          this.actions.findIndex((x) => x.id == "ghilai")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "huy")
        ].active = true;
      } else if (kieu == 0) {
        //bắt đầu
        this.actions[
          this.actions.findIndex((x) => x.id == "nhapmoi")
        ].active = true;
        this.clear();
      } else if (kieu == 1) {
        //thêm mới
        this.actions[
          this.actions.findIndex((x) => x.id == "ghilai")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "huy")
        ].active = true;
        this.clear();
      } else if (kieu == 2) {
        //Huỷ
        this.actions[
          this.actions.findIndex((x) => x.id == "nhapmoi")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "xoa")
        ].active = true;
        this.clear();
      } else if (kieu == 3) {
        //edit
        this.actions[
          this.actions.findIndex((x) => x.id == "nhapmoi")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "ghilai")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "huy")
        ].active = true;
        this.actions[
          this.actions.findIndex((x) => x.id == "xoa")
        ].active = true;
      }
    },
    clear() {
      this.input_mota = "";
      this.input_camket = "0";
    },
    showModal() {
      this.$refs["popupCamKetGoiDaDichVu"].show();
    },
    hideModal() {
      this.$refs["popupCamKetGoiDaDichVu"].hide();
    },
    handleShowModal() {
      this.dataSources = [];
      this.l_goi = this.goi;
      this.input_tengoi = this.l_goi.ten_goi;
      this.danhsach_tieuchi = [];
      this.tieuchi_selected = null;
      this.danhsach_loaihinh = this.loaihinh;
      this.loaihinh_selected = null;
      this.rowSelected = null;

      setTimeout(() => {
        this.initDulieu();
      }, 200);
    },
    onDataCheckeds(data) {},
    changeLoaiHinhTB() {
      if (this.loaihinh_selected == null || this.rowSelected == null) {
        return;
      }
      //edit
      if (
        this.actions[this.actions.findIndex((x) => x.id == "nhapmoi")].active ==
        true
      ) {
        if (this.loaihinh_selected != this.rowSelected.loaitb_id) {
          this.$alert("Không được đổi loại hình cam kết !", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "info",
          });
          this.loaihinh_selected = this.rowSelected.loaitb_id;
          return;
        }
      }
    },
    ghiDuLieu() {
      let is_insert = this.actions[
        this.actions.findIndex((x) => x.id == "nhapmoi")
      ].active
        ? 0
        : 1;
      //kiểm tra dữ liệu
      if (!this.kiemtra_dulieu(is_insert)) {
        return;
      }
      //tạo dữ liệu
      let goi_id = this.l_goi.goi_id;
      let json_camket =
        '[{"GOI_ID":#1,"LOAITB_ID":#2,"TIEUCHI_ID":#3,"TG_CK": #4,"MOTA_CK": "#5"}]';
      json_camket = json_camket.replace("#1", goi_id);
      json_camket = json_camket.replace("#2", this.loaihinh_selected);
      json_camket = json_camket.replace("#3", this.tieuchi_selected);
      json_camket = json_camket.replace("#4", this.input_camket.trim());
      json_camket = json_camket.replace("#5", this.input_mota.trim());
      //thực thi
      this.sp_upd_camket(
        goi_id,
        this.loaihinh_selected,
        this.tieuchi_selected,
        json_camket
      );
    },
    kiemtra_dulieu(is_insert) {
      if (this.input_mota.trim() == "") {
        this.$alert("Bạn chưa nhập mô tả cam kết !", "", {
          dangerouslyUseHTMLString: true,
          confirmButtonText: "OK",
          type: "warning",
        });
        this.$refs.inpuMoTa.focus();
        return false;
      }
      if (this.dataSources.length == 0) {
        return true;
      }
      console.log("is_insert", is_insert);
      if (is_insert == 1) {
        const index = this.dataSources.findIndex(
          (x) =>
            x.loaitb_id == this.loaihinh_selected &&
            x.tieuchi_id == this.tieuchi_selected
        );
        if (index > -1) {
          this.$alert("Loại hình, tiêu chí cam kết này đã có !", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "warning",
          });
          return false;
        }
      }
      return true;
    },
    changeTieuChi() {
      if (this.tieuchi_selected == null || this.rowSelected == null) {
        return;
      }
      //edit
      if (
        this.actions[this.actions.findIndex((x) => x.id == "nhapmoi")].active ==
        true
      ) {
        if (this.tieuchi_selected != this.rowSelected.tieuchi_id) {
          this.$alert("Không được đổi tiêu chi cam kết !", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "info",
          });
          this.tieuchi_selected = this.rowSelected.tieuchi_id;
          return;
        }
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
    onSelectedRow(item) {
      this.rowSelected = item;
      this.input_camket = item.tg_ck != null ? item.tg_ck.toString() : "0";
      if (item.loaitb_id != null) {
        this.loaihinh_selected = parseInt(item.loaitb_id);
      } else {
        this.loaihinh_selected = null;
      }
      if (item.tieuchi_id != null) {
        this.tieuchi_selected = item.tieuchi_id;
      } else {
        this.tieuchi_selected = 0;
      }
      this.input_mota = item.mota_ck != null ? item.mota_ck : "";

      this.setActiveActions(3);
    },
    async sp_cbb_tieuchi() {
      try {
        this.loading(true);
        this.danhsach_tieuchi = [];
        let response = await MultiServicePackageAPI.sp_cbb_tieuchi(this.axios);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          this.danhsach_tieuchi = response.data.data;
          if (this.danhsach_tieuchi.length > 0) {
            this.tieuchi_selected = this.danhsach_tieuchi[0].tieuchi_id;
          }
        } else {
          this.danhsach_tieuchi = [];
        }
      } catch (e) {
        this.$toast.error("Không load được danh sách tiêu chí");
      } finally {
        this.loading(false);
      }
    },
    async sp_cbb_loaihinh_tb(goi_id) {
      try {
        this.loading(true);
        this.danhsach_loaihinh = [];
        let response = await MultiServicePackageAPI.sp_cbb_loaihinh_tb(
          this.axios,
          goi_id
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          this.danhsach_loaihinh = response.data.data;
          if (this.danhsach_loaihinh.length > 0) {
            this.loaihinh_selected = this.danhsach_loaihinh[0].loaitb_id;
          }
        } else {
          this.danhsach_loaihinh = [];
        }
      } catch (e) {
        console.log(e);
        this.$toast.error("Không load được danh sách loại hình");
      } finally {
        this.loading(false);
      }
    },
    async sp_grv_camket(goi_id) {
      try {
        this.loading(true);
        this.dataSources = [];
        let response = await MultiServicePackageAPI.sp_grv_camket(
          this.axios,
          goi_id
        );
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000" &&
          response.data.data
        ) {
          this.dataSources = response.data.data;
          if (this.dataSources.length > 0) {
            this.onSelectedRow(this.dataSources[0]);
          } else {
            this.setActiveActions(1);
          }
        } else {
          this.dataSources = [];
          this.setActiveActions(1);
        }
      } catch (e) {
        this.$toast.error("Không load được danh sách cam kết");
      } finally {
        this.loading(false);
      }
    },
    async sp_upd_camket(goi_id, loaitb_id, tieuchi_id, json_camket) {
      try {
        this.loading(true);
        let response = await MultiServicePackageAPI.sp_upd_camket(
          this.axios,
          goi_id,
          loaitb_id,
          tieuchi_id,
          json_camket
        );
        this.loading(false);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          this.sp_grv_camket(this.l_goi.goi_id);
        } else {
          if (response && response.data && response.data.message) {
            this.$alert(response.data.message, "", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "error",
            });
          } else {
            this.$alert("Đã xảy ra lỗi, thao tác không thành công!", "", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "error",
            });
          }
        }
      } catch (e) {
        //console.log(e)
        this.loading(false);
        if (e.data && e.data.message) {
          this.$alert(e.data.message, "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$alert(e.response.data.message, "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        } else {
          this.$alert("Đã xảy ra lỗi, thao tác không thành công!", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        }
      }
    },
    async sp_del_camket(goi_id, loaitb_id, tieuchi_id) {
      try {
        this.loading(true);
        let response = await MultiServicePackageAPI.sp_del_camket(
          this.axios,
          goi_id,
          loaitb_id,
          tieuchi_id
        );
        this.loading(false);
        if (
          response &&
          response.data &&
          response.data.error_code &&
          response.data.error_code == "BSS-00000000"
        ) {
          this.rowSelected = null;
          this.sp_grv_camket(this.l_goi.goi_id);
        } else {
          if (response && response.data && response.data.message) {
            this.$alert(response.data.message, "", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "error",
            });
          } else {
            this.$alert("Đã xảy ra lỗi, xoá không thành công!", "", {
              dangerouslyUseHTMLString: true,
              confirmButtonText: "OK",
              type: "error",
            });
          }
        }
      } catch (e) {
        //console.log(e)
        this.loading(false);
        if (e.data && e.data.message) {
          this.$alert(e.data.message, "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        } else if (e.response && e.response.data && e.response.data.message) {
          this.$alert(e.response.data.message, "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        } else {
          this.$alert("Đã xảy ra lỗi, xoá không thành công!", "", {
            dangerouslyUseHTMLString: true,
            confirmButtonText: "OK",
            type: "error",
          });
        }
      }
    },
    async initDulieu() {
      this.setActiveActions(-1);
      await this.sp_cbb_tieuchi();
      await this.sp_cbb_loaihinh_tb(this.l_goi.goi_id);
      await this.sp_grv_camket(this.l_goi.goi_id);
    },
  },
  watch: {
    goi(val) {
      this.l_goi = val;
      this.input_tengoi = this.l_goi.ten_goi;
    },
  },
};
</script>