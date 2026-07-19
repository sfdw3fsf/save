<template>
    <div class="box-form">
        <div class="legend-title">
            <div class="pull-left">
                Danh sách thuê bao thay dây
            </div>
            <div class="pull-right" style="margin: 0 0 0 20px">
            <a href="#" class="link" @click.prevent="onBtnXuatFileClicked"> <span class="icon one-excel"></span> Xuất File </a>
          </div>
            <div class="pull-right">
                <div class="list-checks">
                    <div class="item red">
                        <div class="check-action">
                            <input
                                type="radio"
                                class="check"
                                name="card_radio"
                                value="newCard"
                                v-model="card">
                            <span class="name">Phiếu mới</span>
                        </div>
                    </div>
                    <div class="item red">
                        <div class="check-action">
                            <input
                                type="radio"
                                class="check"
                                name="card_radio"
                                value="returnCard"
                                v-model="card">
                            <span class="name">Phiếu trả lại</span>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="clearfix"></div>
        </div>
        <DataGrid
            ref="grcDanhSach"
            :columns="table.columns"
            :dataSource="changeWireSubcribers"
            :enable-paging-server="false"
            :showColumnCheckbox="true"
            :allowPaging="true"
            :loading="true"
            :showFilter="true"
            :selectionSettings="{ type: 'Multiple'}"
            @rowSelected="onSelect"
            @selectedItemsChanged="onSelectedItemsChanged"
        >
        </DataGrid>
    </div>
</template>

<script>
import XLSX from 'xlsx'
import gridView from '@/components/Shared/gridview.vue'
import { mapActions, mapGetters } from 'vuex'
export default {
  components: {
    gridView
  },
  data () {
    return {
      card: 'newCard',
      changeWireSubcribers: [],
      table: {
        selected: null,
        columns: [{
          fieldName: 'ma_tb',
          headerText: 'Số máy/Acc'
        }, {
          fieldName: 'ma_gd',
          headerText: 'Mã GD'
        }, {
          fieldName: 'ten_tb',
          headerText: 'Tên thuê bao'
        },{
          fieldName: 'ten_kieuld',
          headerText: 'Kiểu lắp đặt'
        }, {
          fieldName: 'diachi_ld',
          headerText: 'Địa chỉ lắp đặt'
        }, 
        {
          fieldName: 'ngaygiao',
          headerText: 'Ngày giao'
        },
        {
          fieldName: 'huonggiao',
          headerText: 'Hướng giao'
        }, {
          fieldName: 'trangthai_hd',
          headerText: 'Trạng thái HĐ'
        }, {
          fieldName: 'ngay_ht',
          headerText: 'Ngày hoàn thành'
        }, {
          fieldName: 'ma_kv',
          headerText: 'Mã khu vực'
        }, {
          fieldName: 'ten_kv',
          headerText: 'Tên khu vực'
        }, {
          fieldName: 'luu_in',
          headerText: 'Trạng thái in'
        }, {
          fieldName: 'ghichu_tb',
          headerText: 'Ghi chú'
        }]
      },
      selectedItems: []
    }
  },
  props: {
    inputChangeWireSubcribers: Array
  },
  computed: {
    ...mapGetters('installReplaceRecallWifiCommon', [
      'getDirections'
    ])
  },
  watch: {
    card: function () {
      this.setIsNewCard(this.card === 'newCard')
    },
    inputChangeWireSubcribers: function () {
      this.changeWireSubcribers = this.inputChangeWireSubcribers
    }
  },
  methods: {
    ...mapActions('installReplaceRecallWifiCommon', [
      'setIsNewCard',
      'loadEmployees',
      'haveToDo',
      'setSelectedSubcriber'
    ]),
    onSelect: async function (e) {
      this.$root.showLoading(true)
      try {
        if (e && e.data) {
          this.setSelectedSubcriber(e.data)

          await this.loadEmployees({
            kieu_id: e.data.kieuld_id,
            nhanvien_id: this.$root.token.getNhanVienID(),
            phanvung_id: this.$root.token.getPhanVungID(),
            phieu_id: e.data.phieu_id
          })

          await this.haveToDo({
            code: 'PHAILAM',
            luong_id: this.getDirections.length > 0 ? this.getDirections[0].luong_id : 0
          })
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    onSelectedItemsChanged: function (e) {
      this.selectedItems = e
    },
    onBtnXuatFileClicked() {
      this.exportExcelByCols(this.changeWireSubcribers, this.table.columns)
    },
    exportExcelByCols(inputArray, arrayFields) {
      var exportData = []
      exportData = inputArray.map((e) => {
        var exportEntity = {}
        arrayFields.map((x) => {
          exportEntity[x.headerText] = e[x.fieldName]
        })
        return exportEntity
      })
      if (exportData.length > 0) {
        let worksheet = XLSX.utils.json_to_sheet(exportData)
        let workbook = XLSX.utils.book_new()
        XLSX.utils.book_append_sheet(workbook, worksheet, 'Dữ liệu')
        XLSX.writeFile(workbook, 'export.xlsx')
      } else {
        this.$toast.warning('Không có dữ liệu để xuất file!')
      }
    },
  }
}
</script>
