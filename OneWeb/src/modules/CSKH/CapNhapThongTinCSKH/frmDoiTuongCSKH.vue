<template>
  <b-modal
    id="frmDoiTuongCSKH"
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
          <span class="icon one-notepad"></span>Cập nhập đối tượng chăm sóc
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="$bvModal.hide('frmDoiTuongCSKH')"
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
          <div class="legend-title">Thông tin đối tượng</div>
          <div class="info-row">
            <div class="key">Tên đối tượng</div>
            <div class="value">
              <input
                type="text"
                class="form-control highlight font-weight-bold"
                v-model="pTxtDoiTuong"
                ref="txtDoiTuong"
              />
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách đối tượng</div>
          <div class="table-content">
            <DataGrid
              v-bind:columns="gridColumns"
              :selectionSettings="selectionOptions"
              :gridLines="'Both'"
              ref="gridDsDoiTuong"
              dataKeyField="STT"
              :dataSource="pDsDoiTuongChamSoc"
              :showFilter="true"
              :allowPaging="true"
              :enablePagingServer="false"
              @selectedRowChanged="onGridDsDoiTuong_RowSelected"
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
  name: "frmDoiTuongCSKH",
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
      pDsDoiTuongChamSoc: [],
      pTxtDoiTuong: "",
      pDoiTuongId: 0,

      btnNhapMoiEnabled: false,
      btnGhiLaiEnabled: false,
      btnXoaEnabled: false,
      btnHuyBoEnabled: false,

      gridColumns: [
        { 
          fieldName: "STT", 
          headerText: "Stt", 
          allowFiltering: true,
          width: 20,
        },
        {
          fieldName: "tenDoiTuongCs",
          headerText: "Tên đối tượng",
          allowFiltering: true,
        },
      ],
    };
  },
  computed: {
    xoaContent() {
      return 'Bạn có chắc chắn xóa ' + this.pTxtDoiTuong + '?'
    }
  },
  methods: {
    async onShow() {
      this.pDsDoiTuongChamSoc = [];
      try {
        this.$root.showLoading(true);
        await this.getDsDoiTuongChamSoc();
      } catch (e) {
      } finally {
        this.$root.showLoading(false);
      }
    },
    onGridDsDoiTuong_RowSelected(dataItem) {
      console.log(dataItem);
      if (dataItem) {
        this.pTxtDoiTuong = dataItem.tenDoiTuongCs;
        this.pDoiTuongId = dataItem.doiTuongCsId;
        this.setButton(3);
      }
    },
    async getDsDoiTuongChamSoc() {
      try {
        let response = await this.$root.context.get(
          "/web-cskh/api/danh-muc/loai-doi-tuong"
        );
        if (response.error_code === "BSS-00000000") {
          this.pDsDoiTuongChamSoc = response.data || [];
          this.pDsDoiTuongChamSoc.sort((a, b) =>
            a.doiTuongCsId > b.doiTuongCsId ? 1 : -1
          );
           this.pDsDoiTuongChamSoc.forEach((e, i) => e.STT = i + 1)
        }
      } catch (e) {
        this.pDsDoiTuongChamSoc = [];
      }
    },
    clear() {
      this.pTxtDoiTuong = "";
      this.pDoiTuongId = 0;
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
        this.focusInput("txtDoiTuong");
        this.btnNhapMoiEnabled = true;
        this.clear();
      }
      if (kieu == 1) {
        //Them moi
        this.focusInput("txtDoiTuong");
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
      if (this.pTxtDoiTuong.trim().length == 0) {
        this.$root.toastError("Bạn chưa nhập tên đối tượng !");
        this.focusInput("txtDoiTuong");
        return;
      }

      let data = {
        doiTuongCsId: this.btnNhapMoiEnabled ? this.pDoiTuongId : 0,
        tenDoiTuongCs: this.pTxtDoiTuong.trim()
      };

      try {
        this.$root.showLoading(true)
        let response = await this.$root.context.post('/web-cskh/api/danh-muc/doi-tuong-cskh', data)
        if (response.error_code === "BSS-00000000") {
          this.$root.toastSuccess('Cập nhật dữ liệu vào hệ thống của viễn thông tỉnh thành công !')
          await this.getDsDoiTuongChamSoc()
        }
        else {
          this.$root.toastError('Cập nhật dữ liệu không thành công')
        }
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
              this.$root.toastError(`${error.fieldName == 'tenDoiTuongCs' ? 'Tên đối tượng' : ''}: ${error.message}`)
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
      if (this.pDsDoiTuongChamSoc.length <= 0) {
        this.$root.toastError('Không có dữ liệu để xóa !')
        return
      }
      this.$bvModal.show('popup-confirmXoa')
    },
    async onXoaConfirm() {
      try {
        this.$root.showLoading(true)
        let response = await DanhMucApi.xoaDoiTuong(this.axios, this.pDoiTuongId)
        if (response.data.error_code === "BSS-00000000") {
          await this.getDsDoiTuongChamSoc()
        }
        else {
          this.$root.toastError('Có lỗi trong quá trình xóa dữ liệu !')
        }
      }
      catch (e) {
         this.$root.toastError('Có lỗi: ' +  e.data.message)
      }
      finally {
        this.$root.showLoading(false)
      }
    },
    onHide() {
      this.$emit('onDoiTuongHide')
    }
  },
};
</script>