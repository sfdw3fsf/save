<template>
  <div>
    <breadcrumb :header="header" />
    <div class="page-content" style="top: 50px;">
      <vue-card label="Thông tin tra cứu">
        <b-row>
          <b-col class="col-sm-8 col-12">
            <div class="info-row">
              <div class="key w100">Đài viễn thông</div>
              <div class="value">
                <div class="select-custom">
                  <SelectExt
                    dataValueField="donvi_id"
                    dataTextField="ten_dv"
                    v-model="daiVienThongId"
                    :settings="{ dropdownParent: $refs['daiVienThong'] }"
                    :dataSource="danhSachDaiVienThong"
                    @change="onChangeDaiVienThong()"
                  />
                </div>
              </div>
            </div>
          </b-col>
        </b-row>
      </vue-card>

      <vue-card label="Danh sách port">
        <DataGrid
          :columns="dataGrid.columns"
          :dataSource="dataGrid.dataSource"
          :showColumnCheckbox="false"
          :enable-paging-server="false"
          :allowPaging="true"
          :showFilter="true"
        />
      </vue-card>
    </div>
  </div>
</template>

<script>
import breadcrumb from '@/components/breadcrumb'
import api from './api'

const LOAI_DON_VI_ID = 3

export default {
  name: 'SearchPort',
  components: { breadcrumb },
  data () {
    return {
      header: {
        title: 'Tra cứu port',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.buttons' } }
        ]
      },
      daiVienThongId: null,
      danhSachDaiVienThong: [],
      dataGrid: {
        columns: [
          { fieldName: 'ma_dv', headerText: 'Mã VT', allowFiltering: true, allowSorting: false },
          { fieldName: 'vetinh', headerText: 'Vệ tinh', allowFiltering: true, allowSorting: false },
          { fieldName: 'chuasd', headerText: 'Port còn', allowFiltering: true, allowSorting: false },
          { fieldName: 'da_sd', headerText: 'Port dùng', allowFiltering: true, allowSorting: false },
          { fieldName: 'hong', headerText: 'Port hỏng', allowFiltering: true, allowSorting: false },
          { fieldName: 'dutru', headerText: 'Port dự trữ', allowFiltering: true, allowSorting: false },
          { fieldName: 'tongcong', headerText: 'Tổng port', allowFiltering: true, allowSorting: true }
        ],
        dataSource: []
      }
    }
  },

  async created () {
    await this.getDanhSachDaiVienThong()
  },

  methods: {
    async getDanhSachDaiVienThong () {
      this.loading(true)
      try {
        const response = await api.getDanhSachDaiVienThong(this.axios, { loaiDVId: LOAI_DON_VI_ID })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          this.danhSachDaiVienThong = data.data
          if (data.data.length > 0) {
            this.daiVienThongId = data.data[0].donvi_id
            await this.onChangeDaiVienThong()
          }
        } else {
          this.danhSachDaiVienThong = []
          this.daiVienThongId = null
        }
      } catch (e) {
        this.$toast.error('Lấy danh sách đài viễn thông không thành công')
      } finally {
        this.loading(false)
      }
    },
    async onChangeDaiVienThong () {
      this.loading(true)
      try {
        const response = await api.getDanhSachPortTheoDaiVienThong(this.axios, { donViId: this.daiVienThongId })
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') this.dataGrid.dataSource = data.data
        else this.dataGrid.dataSource = []
      } catch (e) {
        this.$toast.error('Lấy danh sách port không thành công')
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>
