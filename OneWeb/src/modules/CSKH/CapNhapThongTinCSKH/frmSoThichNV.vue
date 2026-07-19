<template>
  <b-modal
    id="frmSoThichNV"
    size="xl"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
    @show="onShow"
    @hide="onHide"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span>Cập nhập sở thích khách hàng
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="$bvModal.hide('frmSoThichNV')"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a
              href="javascript: void(0)"
              :class="{ disabled: !btnNhapMoiEnabled }"
              @click="setButton(1)"
            >
              <span class="icon one-file-plus"></span>Nhập mới
            </a>
          </li>
          <li>
            <a
              href="javascript: void(0)"
              :class="{ disabled: !btnGhiLaiEnabled }"
              @click="onGhiLaiClicked()"
            >
              <span class="icon one-save"></span>Ghi lại
            </a>
          </li>
          <li>
            <a
              href="javascript: void(0)"
              :class="{ disabled: !btnHuyBoEnabled }"
              @click="setButton(0)"
            >
              <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
            </a>
          </li>
          <li>
            <a
              href="javascript: void(0)"
              :class="{ disabled: !btnXoaEnabled }"
              @click="onXoaClicked()"
            >
              <span class="icon one-trash"></span>Xóa
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin sở thích</div>
          <div class="info-row">
            <div class="key">Sở thích</div>
            <div class="value">
              <input
                type="text"
                class="form-control highlight font-weight-bold"
                v-model="pTxtSoThich"
                ref="txtSoThich"
                maxlength="30"
              />
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách sở thích</div>
          <div class="table-content">
            <DataGrid
              v-bind:columns="gridColumns"
              :selectionSettings="selectionOptions"
              :gridLines="'Both'"
              ref="gridDsSoThich"
              dataKeyField="STT"
              :dataSource="pDsSoThich"
              :allowPaging="true"
              :enablePagingServer="false"
              :showFilter="true"
              @selectedRowChanged="onGridSoThich_RowSelected"
            >
            </DataGrid>
          </div>
        </div>
      </div>
    </div>
    <YesNoModal :content="xoaContent" idModal='popup-confirmXoa' @onOk="onXoaConfirm" />
  </b-modal>
</template>
<style>
a.disabled {
  color: lightgrey !important;
  pointer-events: none;
  cursor: default;
  text-decoration: none;
}

.btn-custom.btn-second {
  background-color: #fff !important;
  border-color: #0176ff !important;
  color: #0176ff !important;
  font-weight: 600 !important;
}
.btn-second.btn {
  padding: 5px 10px !important;
  border-radius: 4px !important;
}
</style>
<script>
import { mapState, mapGetters, mapActions } from "vuex";
import DanhMucApi from "../api/DanhMucApi"
import moment from "moment";
import YesNoModal from "../utils/YesNoModal"
export default {
  name: "frmSoThichNV",
  components: {YesNoModal},
  data() {
    return {
      selectionOptions: { type: "Single" },
      columnTemplate: function () {
        return {
          template: {
            template:
              '<span :id="data.index" :style="{display: display }" style="font-size: 12px;" class="col-mark-selected-row fa fa-play text-main"></span>',
            data() {
              return {
                data: {},
              };
            },
            computed: {
              display() {
                return parseInt(this.data.index) ==
                  this.$parent.getSelectedRowIndexes()[0]
                  ? "block"
                  : "none";
              },
            },
          },
        };
      },
      pDsSoThich: [],
      pTxtSoThich: "",
      pSoThichId: 0,

      btnNhapMoiEnabled: false,
      btnGhiLaiEnabled: false,
      btnXoaEnabled: false,
      btnHuyBoEnabled: false,

      gridColumns: [
        { 
          fieldName: "STT", 
          headerText: "Stt", 
          width: 20,
          allowFiltering: true 
        },
        {
          fieldName: "tenSoThich",
          headerText: "Tên sở thích",
          allowFiltering: true,
        },
      ],
    };
  },
  computed: {
    xoaContent() {
      return 'Bạn có chắc chắn xóa ' + this.pTxtSoThich + '?'
    }
  },
  methods: {
    async onShow() {
      this.pDsSoThich = [];
      try {
        this.$root.showLoading(true);
        await this.getDsSoThich();
      } catch (e) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    onGridSoThich_RowSelected(dataItem) {
      if (dataItem) {
        this.pTxtSoThich = dataItem.tenSoThich;
        this.pSoThichId = dataItem.soThichId;
        this.setButton(3);
      }
    },
    async getDsSoThich() {
      try {
        let response = await this.$root.context.get(
          "/web-cskh/api/danh-muc/so-thich"
        );
        if (response.error_code === "BSS-00000000") {
          this.pDsSoThich = response.data || [];
          this.pDsSoThich.sort((a, b) => (a.soThichId > b.soThichId ? 1 : -1));
          this.pDsSoThich.forEach((e, i) => (e.STT = i + 1));
        }

        if (this.pDsSoThich.length == 0) {
          this.setButton(1);
        }
      } catch (e) {
        this.pDsSoThich = [];
      }
    },
    clear() {
      this.pTxtSoThich = "";
      this.pSoThichId = 0;
    },
    setButton(kieu) {
      this.btnNhapMoiEnabled = false;
      this.btnGhiLaiEnabled = false;
      this.btnXoaEnabled = false;
      this.btnHuyBoEnabled = false;
      if (kieu == -1) {
        //Bat dau
        this.btnGhiLaiEnabled = true;
        this.btnHuyBoEnabled = true;
      }
      if (kieu == 0) {
        //Bat dau
        this.focusInput("txtSoThich");
        this.btnNhapMoiEnabled = true;
        this.clear();
      }
      if (kieu == 1) {
        //Them moi
        this.focusInput("txtSoThich");
        this.btnGhiLaiEnabled = true;
        this.btnHuyBoEnabled = true;
        this.clear();
      }
      if (kieu == 2) {
        //Huy
        this.btnNhapMoiEnabled = true;
        this.btnXoaEnabled = true;
        this.clear();
      }
      if (kieu == 3) {
        //Edit
        this.btnNhapMoiEnabled = true;
        this.btnXoaEnabled = true;
        this.btnGhiLaiEnabled = true;
        this.btnHuyBoEnabled = true;
      }
    },
    focusInput(id) {
      this.$refs[id].select();
    },
    async onGhiLaiClicked() {
      if (this.pTxtSoThich.trim().length == 0) {
        this.$root.toastError("Bạn chưa nhập tên sở thích !");
        this.focusInput("txtSoThich");
        return;
      }

      let data = {
        soThichId: this.btnNhapMoiEnabled ? this.pSoThichId : 0,
        tenSoThich: this.pTxtSoThich.trim()
      };

      try {
        this.$root.showLoading(true)
        let response = await this.$root.context.post('/web-cskh/api/danh-muc/so-thich', data)
        if (response.error_code === "BSS-00000000") {
          this.$root.toastSuccess('Cập nhật dữ liệu vào hệ thống của viễn thông tỉnh thành công !')
          await this.getDsSoThich()
        }
        else {
          this.$root.toastError('Cập nhật dữ liệu không thành công')
        }
        console.log(3)
      }
      catch (e) {
        if (e && e.response && e.response.data && e.response.data.data) {
          if (typeof(e.response.data.data) == "string")
          {
            this.$root.toastError("Có lỗi: " + e.response.data.data)
            return
          }

          let errors = e.response.data.data || []
          if (errors.length > 0) {
            errors.forEach(error => {
              this.$root.toastError(`${error.fieldName == 'tenSoThich' ? 'Tên sở thích' : ''}: ${error.message}`)
            })
            return
          }
        }
        else if (e && e.response && e.response.data.message) {
          this.$root.toastError("Cập nhật dữ liệu thất bại: " + e.response.data.message)
          return
        }
        this.$root.toastError("Cập nhật dữ liệu thất bại !")
      }
      finally {
        this.$root.showLoading(false)
      }
    },
    onXoaClicked() {
      if (this.pDsSoThich.length <= 0) {
        this.$root.toastError('Không có dữ liệu để xóa !')
        return
      }
      this.$bvModal.show('popup-confirmXoa')
    },
    async onXoaConfirm() {
      try {
        this.$root.showLoading(true)
        let response = await DanhMucApi.xoaSoThich(this.axios, this.pSoThichId)
        if (response.data.error_code === "BSS-00000000") {
          await this.getDsSoThich()
        }
        else {
          this.$root.toastError('Có lỗi trong quá trình xóa dữ liệu !')
        }
      }
      catch (e) {
         this.$root.toastError('Có lỗi: ' + e.data.message)
      }
      finally {
        this.$root.showLoading(false)
      }
    },
    onHide() {
      this.$emit('onSoThichHide')
    }
  },
};
</script>