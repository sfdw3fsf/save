<template>
  <b-modal id="thongTinSoNe" size="xl" hide-footer hide-header class="content">
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Thay đổi thông số cố định
        </div>
        <div
          @click="closeModal"
          class="close -ap icon-close"
          data-dismiss="modal"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li title="Cập nhật thông tin trong danh bạ">
            <a @click="summitForm">
              <span class="icon one-save"></span> Ghi lại
            </a>
          </li>
          <li title="Đổi vị trí trên tổng đài và cập nhật trong danh bạ">
            <a @click="summitFormActivated">
              <span class="icon one-file-arrow-up"></span> Kích hoạt
            </a>
          </li>
          <li title="import dữ liệu" @click="tsbtnImportFile_Click">
            <a>
              <span class="icon one-file-excel"></span> Import dữ liệu
            </a>
          </li>
          <li>
            <a @click="resetForm">
              <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy
            </a>
          </li>
        </ul>
      </div>
      <ThongTinThueBao
        :formSumbit="formSumbit"
        :checkResetForm="checkResetForm"
        :checkResetFormActivated="checkResetFormActivated"
      />
      <ChonNEMoi />
      <frmDocFileGanThueBao_NE ref="frmDocFileGanThueBao_NE" />
    </div>
    <!-- </div> -->
  </b-modal>
</template>

<script>
import frmDocFileGanThueBao_NE from './part/frmDocFileGanThueBao_NE'
import ThongTinThueBao from './part/ThongTinThueBao'
import ChonNEMoi from './ChonNEMoi'
export default {
  components: {
    ThongTinThueBao,
    ChonNEMoi,
    frmDocFileGanThueBao_NE
  },
  data () {
    return {
      formSumbit: false,
      checkResetForm: false,
      submitted: false,
      checkResetFormActivated: false
    }
  },

  methods: {
    closeModal () {
      this.$bvModal.hide('thongTinSoNe')
    },
    summitForm () {
      this.formSumbit = !this.formSumbit
    },
    resetForm () {
      this.checkResetForm = !this.checkResetForm
    },
    summitFormActivated () {
      this.checkResetFormActivated = !this.checkResetFormActivated
    },
    tsbtnImportFile_Click() {
      this.$refs.frmDocFileGanThueBao_NE.show()
    }
  }
}
</script>

<style scoped>
.list-actions-top .list li {
  cursor: pointer;
}
.icon-close {
  cursor: pointer;
}
.title {
  font-weight: 400;
}
.modal-body {
  padding: 0 !important;
}

.popup-header {
  width: 100%;
  font-size: 20px;
}
</style>
