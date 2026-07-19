<template src="./index.template.html"></template>
<script>
import api from '../../../api'

export default {
  name: 'HaTangAoHoa',
  props: {
    id: {
      type: Number,
      default: null
    }
  },
  computed: {
    currentHaTangTitle() {
      const selectedId = parseInt(this.selectedHaTangType)
      const selected = this.dsHaTangTypes.find((item) => item.ID === selectedId)
      return selected ? `Danh sách ${selected.TEN.toLowerCase()}` : 'Danh sách hạ tầng ảo hóa'
    },
    currentDataSource() {
      const selectedId = parseInt(this.selectedHaTangType)
      switch (selectedId) {
        case 1:
          return this.dsHaTangAoHoa
        case 2:
          return this.dsSmartCloud
        case 3:
          return this.dsIDG
        case 4:
          return this.dsBackup
        default:
          return this.dsHaTangAoHoa
      }
    }
  },
  data() {
    return {
      selectedHaTangType: 1,
      dsHaTangTypes: [],
      dsHaTangAoHoa: [
        {
          STT: 1,
          MA_HATANG: 'HTAH_001',
          TEN_HATANG: 'HTIDG_beo',
          CONG_NGHE: 'dịn công nghệ',
          IDC: 'IDC_NTL',
          NGUOI_QUANLY: 'nguyễn văn a',
          DONVI_QUANLY: 'cloud',
          TRANGTHAI_CCDV: 'đã cung cấp / chưa cung cấp'
        }
      ],
      dsSmartCloud: [
        {
          STT: 1,
          MA_HATANG: 'SC_001',
          TEN_HATANG: 'SmartCloud_01',
          CONG_NGHE: 'Cloud technology',
          IDC: 'IDC_NTL',
          NGUOI_QUANLY: 'nguyễn văn b',
          DONVI_QUANLY: 'cloud',
          TRANGTHAI_CCDV: 'đã cung cấp'
        }
      ],
      dsIDG: [
        {
          STT: 1,
          MA_HATANG: 'HT1DG_01',
          TEN_HATANG: 'HTIDG_beo',
          CONG_NGHE: 'dịn công nghệ',
          IDC: 'IDC_NTL',
          NGUOI_QUANLY: 'nguyễn văn a',
          DONVI_QUANLY: 'cloud',
          TRANGTHAI_CCDV: 'đã cung cấp / chưa cung cấp'
        }
      ],
      dsBackup: [
        {
          STT: 1,
          MA_HATANG: 'HT1DG_01',
          TEN_HATANG: 'HTIDG_beo',
          CONG_NGHE: 'dịn công nghệ',
          IDC: 'IDC_NTL',
          NGUOI_QUANLY: 'nguyễn văn a',
          DONVI_QUANLY: 'cloud',
          TRANGTHAI_CCDV: 'đã cung cấp / chưa cung cấp'
        }
      ]
    }
  },
  mounted() {
    this.loadHaTangData()
  },
  methods: {
    actionColumn: function(parent) {
      return function() {
        return {
          template: {
            template: `
              <div>
                <button class="btn btn-sm btn-outline-success" @click="onViewDetail(data)" title="Xem chi tiết">
                  <i class="fa fa-eye"></i>
                </button>
              </div>
            `,
            methods: {
              onViewDetail: (data) => {
                parent.onViewDetail(data)
              }
            }
          }
        }
      }
    },
    onHaTangTypeChange() {
      this.loadHaTangData()
    },
    async loadHaTangData() {
      // if (!this.id) {
      //   // console.warn('Không có ID IP Address để tải danh sách hạ tầng')
      //   return
      // }
      try {
        this.loading(true)
        const selectedId = parseInt(this.selectedHaTangType)
        const data = {
          ipAddressId: this.id,
          loaiHaTangId: selectedId
        }
        const response = await api.getHaTangListByType(this.$root.context, data)

        if (response && response.data) {
          const mappedData = response.data.map((item, index) => ({
            STT: index + 1,
            ID: item.ID || item.id,
            MA_HATANG: item.MA_HATANG || item.maHaTang,
            TEN_HATANG: item.TEN_HATANG || item.tenHaTang,
            CONG_NGHE: item.CONG_NGHE || item.congNghe,
            IDC: item.IDC || item.idc,
            NGUOI_QUANLY: item.NGUOI_QUANLY || item.nguoiQuanLy,
            DONVI_QUANLY: item.DONVI_QUANLY || item.donViQuanLy,
            TRANGTHAI_CCDV: item.TRANGTHAI_CCDV || item.trangThaiCCDV
          }))

          switch (selectedId) {
            case 1:
              this.dsHaTangAoHoa = mappedData
              break
            case 2:
              this.dsSmartCloud = mappedData
              break
            case 3:
              this.dsIDG = mappedData
              break
            case 4:
              this.dsBackup = mappedData
              break
          }
        } else {
          this.dsHaTangAoHoa = []
          this.dsSmartCloud = []
          this.dsIDG = []
          this.dsBackup = []
        }
      } catch (error) {
        console.error('Lỗi khi tải danh sách hạ tầng:', error)
        this.$root.$toastr.error('Không thể tải danh sách hạ tầng. Vui lòng thử lại!')
        this.dsHaTangAoHoa = []
        this.dsSmartCloud = []
        this.dsIDG = []
        this.dsBackup = []
      } finally {
        this.loading(false)
      }
    },
    onViewDetail(data) {
      console.log('View detail:', data)
      // TODO: Open detail modal or navigate to detail page
      this.$emit('view-hatang-detail', data)
    },
    grdHaTang_RowSelected(args) {
      console.log('Row selected:', args)
    }
  }
}
</script>
<style scoped></style>
