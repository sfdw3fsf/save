<template src='./ChangeSubsManageAreaBTS.html'></template>
<style src='./ChangeSubsManageAreaBTS.scss'></style>
<script>
import gridView from '@/components/Shared/gridview.vue'
import treegridView from '@/modules/complaint/ResovleComplaintAfterSales/components/treegridview.vue'
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
import FormGanTheoFile from '@/modules/contract/profile/AssignSubscriberManagementAreaByFileBTS/index.vue';
import breadcrumb from '@/components/breadcrumb'
import 'flatpickr/dist/plugins/monthSelect/style.css'
import bssFlatPicker from '@/components/BssDatePicker'
import moment from 'moment'
import XLSX from 'xlsx'
import api from './Api'

export default {
  components: {
    breadcrumb,
    gridView,
    treegridView,
    bssFlatPicker,
    moment,
    XLSX,
    ExportDataModal,
    FormGanTheoFile
  },
  name: 'ChangeSubsManageAreaBTS',
  data() {
    return {
      animationSettings: { effect: 'None' },
      header: {
        title: 'Phân tập thuê bao theo BTS',
        list: [
          { name: 'Phân tập thuê bao theo BTS', link: { name: 'Ui.cards' } }
        ]
      },
      Loading: false,
      headersKhuVuc: [
        {
          fieldName: 'ma_kv',
          headerText: 'Mã khu vực',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'ten_kv',
          headerText: 'Tên khu vực',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'ma_node',
          headerText: 'Mã node',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'ten_node',
          headerText: 'Tên node',
          allowFiltering: true,
          allowSorting: false
        }
      ],
      headersDanhSach: [
        { fieldName: 'ma_tb', headerText: 'Mã thuê bao', allowFiltering: true, allowSorting: false, width: 120 },
        { fieldName: 'ma_node', headerText: 'Mã node', allowFiltering: true, allowSorting: false, width: 120 },
        { fieldName: 'bts_id', headerText: 'BTS ID', allowFiltering: true, allowSorting: false, width: 90 },
        { fieldName: 'diachi_tb', headerText: 'Địa chỉ', allowFiltering: true, allowSorting: false }
      ],
      DmKhuVuc1: {
        valueField: 'ma_node',
        textField: 'ten_node',
        panelDataHeight: 'auto',
        selected: null,
        data: []
      },
      DmKhuVuc2: {
        valueField: 'ma_node',
        textField: 'ten_node',
        panelDataHeight: 'auto',
        selected: null,
        data: []
      },
      selIndex: [],
      movedList2: [],
      DsThueBao1: {
        data: [],
        selectedItems: []
      },
      DsThueBao2: {
        data: [],
        selectedItems: []
      },
      frmGanTheoFilDialog: {
        isVisible: false
      }
    }
  },
  watch: {
    Loading: function(val) {
      this.$root.showLoading(val)
    }
  },
  created() {
    this.DsThueBao1.selectedItems = []
    this.DsThueBao2.selectedItems = []
    this.Loading = true
    Promise.all([this.getDmKhuVuc()])
      .then((responses) => {
        // this.Loading = true
      })
      .finally((x) => {
        this.Loading = false
      })
  },
  mounted() {
    this.DmKhuVuc1.selected = null
    this.DmKhuVuc2.selected = null
  },
  methods: {
    getDmKhuVuc: async function() {
      let data = this.GetData(await api.getDmKhuVuc(this.axios))
      this.DmKhuVuc1.data = data.slice(0)
      this.DmKhuVuc2.data = data.slice(0)
    },

    DmKhuVuc1_Changed: async function(item) {
      this.DmKhuVuc1.selected = item
      if (item == null) {
        this.DsThueBao1.data = []
      } else {
        await this.getDSThueBaoTheoBTS(item.khuvuc_id, item.bts_id, 1)
      }
    },

    DmKhuVuc2_Changed: async function(item) {
      this.DmKhuVuc2.selected = item
      if (item == null) {
        this.DsThueBao2.data = []
      } else {
        await this.getDSThueBaoTheoBTS(item.khuvuc_id, item.bts_id, 2)
      }
    },

    getDSThueBaoTheoBTS: async function(p_khuvuc_id, p_bts_id, type) {
      var input = { p_khuvuc_id, p_bts_id }
      let data = []
      this.Loading = true
      try {
        data = this.GetData(await api.getDSThueBaoTheoBTS(this.axios, input))
      } catch (ex) {
        data = []
      } finally {
        this.Loading = false
        if (type == 1) {
          this.DsThueBao1.data = data
        } else if (type == 2) {
          this.DsThueBao2.data = data
        }
      }
    },

    ganTheoFile: function() {
      this.frmGanTheoFilDialog.isVisible = true
      this.$refs.frmGanTheoFile.show()
    },

    chuyenThueBaoTheoBTS: async function() {
      this.$bvModal
        .msgBoxConfirm('Bạn có thật sự muốn cập nhật thông tin?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (!v) {
            return
          }
          // Xử lý code ở đây
          if (this.DmKhuVuc2.selected == null) {
            this.ShowAlert('Chưa chọn khu vực ')
            return
          }

          let selectedItems = this.DsThueBao2.data.filter((x) => x.NEW == 1)
          if (selectedItems.length <= 0) {
            this.ShowAlert('Chưa có thuê bao cần chuyển đến. Hãy kiểm tra lại!')
            return
          }

          if (!this.KiemTra()) return

          let dsThueBaoId = []
          selectedItems.forEach(function(item) {
            dsThueBaoId.push(item.thuebao_id)
          })

          let input = {
            'p_khuvuc_id_moi': this.DmKhuVuc2.selected.khuvuc_id,
            'p_bts_id_moi': this.DmKhuVuc2.selected.bts_id,
            'p_khuvuc_id_cu': this.DmKhuVuc1.selected.khuvuc_id,
            'p_bts_id_cu': this.DmKhuVuc1.selected.bts_id,
            'p_ds_tb_id': dsThueBaoId.toString()
          }

          this.Loading = true
          let response = await api.chuyenThueBaoTheoKhuVucBTS(this.axios, input)
          if (response.data.error == 200 && response.data.error_code == 'BSS-00000000') {
            this.Loading = false
            await this.DmKhuVuc1_Changed(this.DmKhuVuc1.selected)
            await this.DmKhuVuc2_Changed(this.DmKhuVuc2.selected)
            this.ShowSuccess(`Thay đổi khu vực quản lý thuê bao thành công!`)
          } else {
            this.Loading = false
            this.ShowError(`Có lỗi: ${response.data.message}`)
          }
          this.Loading = false
        })
    },

    gridThueBao2_RowDataBound(args) {
      try {
        if (args.data['CHON'] == 1) {
          this.selIndex.push(parseInt(args.row.getAttribute('aria-rowindex')))
        }
        if (args.data['NEW'] == 1) {
          args.row.classList.add('new-red-item')
        }
      } catch (err) {
        console.log('gridThueBao2_RowDataBound', err)
      }
    },

    select_selectedItemsChangedChuagan: function(selectedItems) {
      this.DsThueBao1.selectedItems = selectedItems
    },
    select_selectedItemsChangedDagan: function(selectedItems) {
      this.DsThueBao2.selectedItems = selectedItems
    },
    RightOne: function() {
      if (!this.KiemTra()) return
      var self = this
      if (this.DsThueBao1.data) {
        let selectedItems = this.$refs.refDsThueBao1.getSelectedRecords()
        if (selectedItems.length === 0) {
          self.ShowAlert(`Chưa chọn thuê bao !`)
          return
        }
        let movedList = []
        for (const item of selectedItems) {
          if (self.DsThueBao2.data.filter((x) => x.thuebao_id === item.thuebao_id).length > 0) {
            self.ShowError(`Mã TB: ${item.ma_tb}  đã có trên lưới 'Danh sách thuê bao được chuyển'. Bạn không được chuyển tiếp!`)
            return
          } else {
            item.NEW = 1
            item.CHON = 1
            self.DsThueBao2.data.unshift(item)
            movedList.push(item)
          }
        }

        this.DsThueBao1.data = this.DsThueBao1.data.filter((x) => movedList.filter((y) => y.thuebao_id == x.thuebao_id).length == 0)
        this.DsThueBao1.selectedItems = []
      }
    },

    RightAll: function() {
      if (!this.KiemTra()) return
      var self = this
      if (this.DsThueBao1.data) {
        let selectedItems = this.DsThueBao1.data
        //let selectedItems = this.$refs.refDsThueBao1.getSelectedRecords()
        if (selectedItems.length === 0) {
          self.ShowAlert(`Chưa chọn thuê bao !`)
          return
        }
        for (const item of selectedItems) {
          if (self.DsThueBao2.data.filter((x) => x.thuebao_id === item.thuebao_id).length > 0) {
            self.ShowError(`Mã TB: ${item.ma_tb}  đã có trên lưới 'Danh sách thuê bao được chuyển'. Bạn không được chuyển tiếp!`)
            return
          } else {
            item.NEW = 1
            item.CHON = 1
            self.DsThueBao2.data.unshift(item)
          }
        }

        this.DsThueBao1.data = []
      }
    },

    LeftOne: function() {
      if (!this.KiemTra()) return
      if (this.DsThueBao2.data) {
        var self = this
        let selectedItems = this.$refs.refDsThueBao2.getSelectedRecords() // this.DsThueBao2.selectedItems
        if (selectedItems.length === 0) {
          self.ShowAlert(`Chưa chọn thuê bao hủy thay đổi !`)
          return
        }
        let movedList = []
        selectedItems.forEach(function(item) {
          if (self.DsThueBao1.data.filter((x) => x.thuebao_id === item.thuebao_id).length == 0) {
            self.DsThueBao1.data.unshift(item)
            movedList.push(item)
          }
        })
        this.DsThueBao2.data = this.DsThueBao2.data.filter((x) => movedList.filter((y) => y.thuebao_id == x.thuebao_id).length == 0)
        this.DsThueBao2.selectedItems = []
      }
    },

    LeftAll: async function() {
      if (!this.KiemTra()) return
      var self = this
      let selectedItems = self.DsThueBao2.data
      if (selectedItems.length === 0) {
        self.ShowAlert(`Chưa chọn thuê bao hủy thay đổi !`)
        return
      }
      selectedItems.forEach(function(item) {
        if (self.DsThueBao1.data.filter((x) => x.thuebao_id === item.thuebao_id).length == 0) {
          self.DsThueBao1.data.unshift(item)
        }
      })
      this.DsThueBao2.data = []
      this.Loading = true
      try {
        await this.DmKhuVuc1_Changed(this.DmKhuVuc1.selected)
        await this.DmKhuVuc2_Changed(this.DmKhuVuc2.selected)
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },

    KiemTra() {
      let kvCu = this.DmKhuVuc1.selected
      let kvMoi = this.DmKhuVuc2.selected

      if (kvCu == null) {
        this.ShowError(`Chưa chọn khu vực cũ!`)
        return false
      }

      if (kvMoi == null) {
        this.ShowError(`Chưa chọn khu vực mới!`)
        return false
      }

      if (kvCu.khuvuc_id == kvMoi.khuvuc_id && kvCu.bts_id == kvMoi.bts_id) {
        this.ShowError(`Khu vực cũ và mới trùng nhau!`)
        return false
      }
      return true
    },

    XuatFile: function() {
      if (this.DsThueBao1.data != null && this.DsThueBao1.data.length > 0) {
        this.$refs.exportDataModal.showModal()
      } else this.$toast.error('Không có dữ liệu!')
    },

    GetData: function(response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        return []
      }
    },

    ShowAlert: function(message) {
      // alert(message)
      this.$toast.error(message)
    },
    ShowError: function(message) {
      // alert(message)
      this.$toast.error(message)
    },
    ShowSuccess: function(message) {
      // alert(message)
      this.$toast.success(message)
    },

    async frmGanTheoFileOnClose() {
      this.frmGanTheoFilDialog.isVisiable = false
    }
  }
}
</script>
