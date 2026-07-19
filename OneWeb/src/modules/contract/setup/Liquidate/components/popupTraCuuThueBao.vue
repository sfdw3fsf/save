<template>
  <b-modal
      size="xl"
      dialog-class="dialog-content"
      body-class="body-content"
      header-class="popup-header"
      content-class="popup-box"
      v-model="isShowModal"
      hide-footer
    >
      <template #modal-header>
        <div class="title">
          <span class="icon one-notepad"></span> Danh sách các thuê bao của khách hàng đang lập hợp đồng/phụ lục
        </div>
        <!-- <div
          class="close -ap icon-close"
          @click.prevent="show(false)"
        ></div> -->
        <!-- <vue-nav>
      <ul class="list">
        <li @click="chapnhan()">
          <a>
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp
            nhận
          </a>
        </li>
        <li>
          <a>
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát
          </a>
        </li>
      </ul>
    </vue-nav> -->

      </template>
      <div style="margin: -1rem">
        <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a @click="chapnhan">
                                <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
                            </a>
                        </li>
                        <li>
                            <a @click="thoat">
                                <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Thoát
                            </a>
                        </li>
                    </ul>

                </div>
      </div>
        <div class="popup-body">
        <vue-card>
            <ejs-grid
                ref="lvwThueBao"
                :dataSource="dataSource"
                :allowPaging="true"
                :pageSettings="pageSettings"
                :pagerTemplate="pagerTemplate"
                :allowSorting="false"
                :allowFiltering="true"
                :allowTextWrap="true"
                :allowSelection="true"
                @rowSelected="lvwThueBao_SelectedIndexChanged"
                gridLines="Both"
                :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
              >
                <e-columns>
                  <e-column type='checkbox' width='50'></e-column>
                  <e-column v-for="col of columns" :field="col.fieldName" :headerText="col.headerText" :width="col.width" :key="col.fieldName"></e-column>
                </e-columns>
              </ejs-grid>
        </vue-card>
    </div>
    </b-modal>
</template>
<script>
import { pagingAndFilter } from '@/modules/admin/category/Staff/mixins/pagingAndFilter.js'

export default {
  mixins: [pagingAndFilter],
  methods: {
    async showModal (khachHangId, dichVuId, maGd, errorFunc = null) {
      this.loading(true)
      const data = await this.axios.post('web-thicong/thuebao/lay_ds_db_hdtb_theo_khid_magd', {
        'khachhang_id': khachHangId,
        'dichvuvt_id': dichVuId,
        'ma_gd': maGd
      })
      this.loading(false)
      if (data && data.data.data.length > 0) {
        this.dataSource = data.data.data
        this.isShowModal = true
        this.$refs.lvwThueBao.selectRow(this.dataSource.length - 1, false)
        return
      }

      if (errorFunc) {
        errorFunc()
      } else {
        this.isShowModal = true
      }
    },
    chapnhan () {
      this.$emit('chapnhan', this.selectedRow)
      this.isShowModal = false
    },
    thoat () {
      this.isShowModal = false
    },
    lvwThueBao_SelectedIndexChanged () {
      const selectedItems = this.$refs.lvwThueBao.getSelectedRecords()
      if (selectedItems.length > 0) {
        this.selectedRow = selectedItems[0]
      }
    }
  },
  data () {
    return {
      isShowModal: false,
      selectedRow: null,
      target: '.main-wrapper',
      columns: [
        {fieldName: 'ma_tt', headerText: 'Mã TT', allowFiltering: false, allowSorting: false},
        {fieldName: 'ma_tb', headerText: 'Mã thuê bao', allowFiltering: false, allowSorting: false, isPrimaryKey: true},
        {fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: false, allowSorting: false},
        {fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', allowFiltering: false, allowSorting: false},
        {fieldName: 'trangthai_tb', headerText: 'Trạng thái', allowFiltering: false, allowSorting: false}
      ],
      dataSource: []
    }
  }
}
</script>
