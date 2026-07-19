<template src="./index.template.html"></template>

<script>
import { COLUMNS_VISIBLE_CONFIG, getVisibleColumnsHelper } from './fieldVisibility'

export default {
  name: 'DanhSachHaTangAoHoa',

  props: {
    currentItem: { type: Object, required: true },
    deviceTypeInfo: {
      type: Object,
      default: () => ({
        phanLoai: null,
        ten: null,
        id: null,
        isServer: false,
        isNetwork: false,
        isServerBlade: false,
        isBlade: false,
        isTuDia: false,
        isBoDieuKhien: false,
        isKhayDia: false,
        isLinecard: false,
        isPowercard: false,
        isConsolecard: false
      })
    }
  },

  data() {
    return {
      dataItems: []
    }
  },

  mounted() {
    this.loadData()
  },

  methods: {
    actionColumn(parent) {
      return function() {
        return {
          template: {
            template: `
              <button class="btn btn-sm btn-outline-primary"
                @click="viewItem()">Xem chi tiết </button>
            `,
            methods: {
              viewItem() {
                parent.viewDetail(this.data)
              }
            }
          }
        }
      }
    },

    getVisibleColumns() {
      const allColumns = [
        { key: 'STT', fieldName: 'STT', headerText: 'STT', textAlign: 'Center', width: '60px' },
        { key: 'ma_hatang_aohoa', fieldName: 'MA_HATANGAOHOA', headerText: 'Mã hạ tầng ảo hóa' },
        { key: 'ten_hatang_aohoa', fieldName: 'TEN_HATANGAOHOA', headerText: 'Tên hạ tầng ảo hóa' },
        { key: 'gan_voi_vm', fieldName: 'GAN_VOI_VM', headerText: 'Gắn với VM' },
        { key: 'dungluong', fieldName: 'DUNGLUONG', headerText: 'Dung lượng' },
        { key: 'trang_thai', fieldName: 'TRANG_THAI', headerText: 'Trạng thái' },
        {
          key: 'ACTIONS',
          fieldName: 'ACTIONS',
          headerText: 'Thao tác',
          textAlign: 'Center',
          template: this.actionColumn(this),
          width: '120px'
        }
      ]

      return getVisibleColumnsHelper(allColumns, this.deviceTypeInfo)
    },

    async viewDetail(row) {
      this.$toast.info(`Tính năng này sẽ được thực hiện ở giai đoạn sau`)
    },

    async loadData() {
      try {
        this.dataItems =  [
          {
            STT: 1,
            MA_HATANGAOHOA: "HT00",
            TEN_HATANGAOHOA: "1 pha / 3 pha",
            GAN_VOI_VM: "VM-001",
            DUNGLUONG: "500 GB",
            TRANG_THAI: "Hoạt động",
          },
          {
            STT: 2,
            MA_HATANGAOHOA: "HT02",
            TEN_HATANGAOHOA: "Điện mục tiêu VNPT",
            GAN_VOI_VM: "Không",
            DUNGLUONG: null,
            TRANG_THAI: "Bảo trì",
          },
          {
            STT: 3,
            MA_HATANGAOHOA: "HT03",
            TEN_HATANGAOHOA: "Nguồn UPS",
            GAN_VOI_VM: "VM-023",
            DUNGLUONG: "1 TB",
            TRANG_THAI: "Đang sử dụng",
          }
        ]
        
      } catch (err) {
        console.error(err)
        this.$toast.error('Lỗi khi tải dữ liệu')
      }
    }
  }
}
</script>
