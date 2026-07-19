<template>
  <b-modal id="popupTCTTTB" size="huge" hide-footer class="content">
    <template #modal-title>
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Quản lý danh mục vai trò
        </div>
      </div>
    </template>

    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click="handlenewEntry" :class="isNhapMoi || (dataItem && dataItem.vaitro_id>0) ?'':'disabled'"> <span class="icon one-file-plus"></span> Nhập mới </a>
        </li>
        <li>
          <a @click="handleRecord" :class="isGhiLai || (dataItem && dataItem.vaitro_id>0) ?'':'disabled'"> <span class="icon one-save"></span> Ghi lại </a>
        </li>
        <li>
          <a @click="handleCancel" :class="isHuy || (dataItem && dataItem.vaitro_id>0) ?'':'disabled'">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
          </a>
        </li>
        <li>
          <a @click="handleDelete" :class="dataItem && dataItem.vaitro_id>0 ?'':'disabled'"> <span class="icon one-trash"></span> Xóa </a>
        </li>
        <li>
          <a href="#">
            <span class="icon one-circle-question"></span> Trợ giúp
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
    <ThongTinVaiTro :checkRecord="checkRecord" :checkDelete="checkDelete" :checkCancel="checkCancel" @go-to="goFirstPage" @update-row="updateRow"/>
    <DanhSachVaiTro ref="listVaiTro" />
    </div>
  </b-modal>
</template>

<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import ThongTinVaiTro from './part/ThongTinVaiTro'
import DanhSachVaiTro from './part/DanhSachVaiTro'
import { T } from 'caniuse-lite/data/browserVersions'
export default {
  components: {
    ThongTinVaiTro,
    DanhSachVaiTro
  },
  data () {
    return {
      formSumbit: false,
      checkResetForm: false,
      submitted: false,
      checkResetFormActivated: false,
      checkRecord: false,
      checkDelete: false,
      checkCancel: false,
      isNhapMoi : true,
      isGhiLai : true,
      isHuy : true
    }
  },
  computed: {
    ...mapState('contractorRole', ['danhSachVaiTro', 'isLoading', 'dataItem'])
  },
  created () {
    this.layDanhSachVaiTro()
  },
  methods: {
    ...mapActions('contractorRole', ['layDanhSachVaiTro']),
    goFirstPage() {
      this.$refs.listVaiTro.goToFirstPage()
    },
    updateRow() {
      this.$refs.listVaiTro.focusUpdateRow()
    },
    handlenewEntry () {
      this.isNhapMoi = false
      this.isGhiLai = true
      this.isHuy = true
      this.$store.commit('contractorRole/SET_DATA_ITEM', {})
    },
    handleCancel () {
      this.checkCancel = !this.checkCancel
      this.$store.commit('contractorRole/SET_DATA_ITEM', {})
      this.isNhapMoi = true
      this.isHuy = false
      this.isGhiLai = false
    },
    summitForm () {
      this.formSumbit = !this.formSumbit
    },
    handleRecord () {
      this.checkRecord = !this.checkRecord
    },
    handleDelete () {
      this.checkDelete = !this.checkDelete
    },
    resetForm () {
      this.checkResetForm = !this.checkResetForm
    },
    summitFormActivated () {
      this.checkResetFormActivated = !this.checkResetFormActivated
    }
  }
}
</script>

<style scoped>
.disabled{
    /* background: #ccc; */
  cursor: inherit !important;
}
.list-actions-top .list li {
  cursor: pointer;
}
>>> .modal .modal-huge {
  max-width: 700px;
  width: 700px;
}
>>> .modal-body {
  padding: 0 !important;
}

.popup-header {
  width: 100%;
  font-size: 20px;
}
>>>.modal-header {
     padding: 10px 1rem !important;

}
>>> .popup-header {
    /* padding: 10px 15px; */
    font-size: 20px;
    background-color: #fff;
}
>>> .popup-header .title .icon {
    margin-right: 10px;
    color: #666;
    font-size: 24px;
    vertical-align: middle;
}
</style>
