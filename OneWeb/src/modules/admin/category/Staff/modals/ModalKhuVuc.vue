<template>
  <ejs-dialog
    :enableResize='true'
    :visible="false"
    :isModal="true"
    ref="modalGanKhuVuc"
    :header='"Danh sách các khu vực của nhân viên đang phụ trách quản lý"'
    showCloseIcon=true
    :target="'.main-wrapper'"
    width='60%'
    height="800"
  >
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="updateGanViTriNhanVien">
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp nhận
          </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="box-form">
        <div class="legend-title">
          Thông tin mã nhân viên tìm kiếm
        </div>
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w50">Mã NV</div>
              <div class="value">
                <input type="text" class="form-control highlight" v-model="maNhanVien" @keyup.enter="loadDataToTable">
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">Danh sách khu vực nhân viên đang quản lý</div>
        <div class="grid-ext grid-ext-freeze">
          <ejs-grid
            ref="tableViTriDaGan"
            :dataSource="danhSachKhuVuc"
            :allowPaging="true"
            :pageSettings="pageSettings"
            :allowSorting="false"
            :allowFiltering="true"
            :allowSelection="true"
            :selectionSettings="selectionOptionsMultiple"
            :pagerTemplate='pagerTemplate'
            :allowResizing="true"
            gridLines="Both"
          >
            <e-columns>
              <e-column template="<span class='fa selected-flag'></span>" :allowFiltering="false"
                        filterTemplate="<span></span>"
                        width="26" maxWidth="26" minWidth="26" textAlign="center"
                        :customAttributes="{class: 'column-selected-flag'}"
                        :freeze="'left'" :allowResizing="false" clipMode="Ellipsis"></e-column>
              <e-column type="checkbox" width="39" maxWidth="39" minWidth="39" headerTextAlign="center"
                        textAlign="center" :customAttributes="{class: 'column-check-selected'}"
                        :allowResizing="false" clipMode="Ellipsis"></e-column>

              <e-column field="nhanvien_id" headerText="ID nhân Viên" width="150" :filter="filterOptions"></e-column>
              <e-column field="ten_nv" headerText="Tên nhân Viên" :filter="filterOptions"></e-column>
              <e-column field="ma_kv" headerText="Mã khu vực" :filter="filterOptions"></e-column>
              <e-column field="ten_kv" headerText="Tên khu vực" :filter="filterOptions"></e-column>
              <e-column field="loai_kv" headerText="Loại khu vực" :filter="filterOptions"></e-column>
              <e-column field="loai_nv" headerText="Loại NV" width="150" :filter="filterOptions"></e-column>
              <e-column field="ma_kv_cha" headerText="Mã KV cha" width="150" :filter="filterOptions"></e-column>
              <e-column field="ten_kv_cha" headerText="Tên KV cha" width="150" :filter="filterOptions"></e-column>
            </e-columns>
          </ejs-grid>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import { pagingAndFilter } from '../mixins/pagingAndFilter.js'
import { getDanhSachKhuVucTable } from '../api'
export default {
  mixins: [pagingAndFilter],
  components: {},
  data () {
    return {
      maNhanVien: '',
      danhSachKhuVuc: []
    }
  },
  mounted () {
  },
  watch: {},
  methods: {
    show () {
      this.$refs.modalGanKhuVuc.show()
    },

    setMaNhanVien (maNhanVien) {
      this.maNhanVien = maNhanVien
    },

    async loadDataToTable () {
      if (!this.maNhanVien) {
        return
      }
      this.loading(true)
      this.danhSachKhuVuc = await getDanhSachKhuVucTable(this.axios, {
        ma_nv: this.maNhanVien.trim()
      }).catch(() => {
        this.$toast.error('Lỗi tải dữ liệu')
      }).finally(() => {
        this.loading(false)
      })
    },

    updateGanViTriNhanVien () {
      this.$refs.modalGanKhuVuc.hide()
    }
  }
}
</script>

<style scoped>
/deep/ .dialog-content {
  width: 100%;
  max-width: 1220px;
  min-height: 700px;
}

/deep/ .body-content {
  padding: 0px !important;
  background-color: #fafafa;
}
</style>
