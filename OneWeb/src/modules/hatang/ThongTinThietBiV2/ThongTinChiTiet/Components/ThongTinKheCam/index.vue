<template src="./index.template.html"></template>

<script>
import { USAGE_STATUS_LIST } from '../../../const.js'
import { createLookupTemplate } from '../../../template/lookupTemplate.js'
export default {
  name: 'ThongTinKheCam',
  props: {
    deviceData: {
      type: Object,
      default: () => ({})
    },
    kheCamList: {
      type: Array,
      default: () => []
    }
  },
  computed: {
    gridColumns() {
      return [
        {
          fieldName: 'ma_khe',
          headerText: 'Mã khe',
          width: 50
        },
        {
          fieldName: 'ten_khe',
          headerText: 'Tên khe'
        },
        {
          fieldName: 'loai_khe',
          headerText: 'Loại khe',
          width: 70
        },
        {
          fieldName: 'trang_thai',
          headerText: 'Tình trạng sử dụng',
          width: 50,
          textAlign: 'Center',
          template: createLookupTemplate({
            field: 'trang_thai',
            list: USAGE_STATUS_LIST,
            valueKey: 'ID',
            labelKey: 'TEN'
          })
        },
        {
          fieldName: 'card_info',
          headerText: 'Card đang cắm',
          width: 50,
          textAlign: 'Center'
        },
        {
          fieldName: '__actions',
          headerText: 'Hành động',
          width: 50,
          textAlign: 'Center',
          template: this.getColumnActionsTemplate(this)
        }
      ]
    }
  },
  methods: {
    getStatusText(status) {
      return status === 1 ? 'Đã sử dụng' : 'Trống'
    },
    onRowDataBound(args) {
      if (args.data && args.data.trang_thai === 1) {
        args.row.setAttribute(
          'style',
          'background-color: #c9f0d0 !important; border-left: 4px solid #3aa655 !important;'
        )
      }
    },
    getColumnActionsTemplate(parent) {
      return () => {
        return {
          template: {
            template: `
              <div style="display: flex; justify-content: center; gap: 10px">
                <template v-if="data.trang_thai === 1">
                  <button class="btn btn-outline-primary" @click="parent.viewCardDetail(data.card_info)" title="Xem chi tiết card">
                    <span class="fa fa-eye"></span>
                  </button>
                  <button class="btn btn-outline-primary" @click="parent.changeCard(data)" title="Thay đổi card">
                    <i class="fa fa-refresh"></i>
                  </button>
                </template>
                <template v-else>
                  <button class="btn btn-outline-success" @click="parent.insertCard(data)" title="Cắm card vào">
                    <span class="fa fa-plus"></span>
                  </button>
                </template>
              </div>
            `,
            data() {
              return {
                parent: parent,
                data: {}
              }
            }
          }
        }
      }
    },
    viewCardDetail(cardInfo) {
      if (!cardInfo) return
      // Emit event để parent component xử lý việc xem chi tiết card
      this.$emit('view-card-detail', cardInfo)
    },
    insertCard(slotData) {
      // Xử lý logic cắm card vào khe
      console.log('Cắm card vào khe:', slotData)
      this.$emit('insert-card', slotData)
    },
    changeCard(slotData) {
      // Xử lý logic thay đổi card
      console.log('Thay đổi card trong khe:', slotData)
      this.$emit('change-card', slotData)
    }
  }
}
</script>

<style scoped>
.grid-container {
  margin-top: 1rem;
}
</style>
