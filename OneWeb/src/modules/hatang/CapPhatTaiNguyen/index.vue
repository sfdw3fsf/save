<template src="./index.html"></template>

<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import Vue from 'vue'
import TuChoi from './Modal/TuChoi.vue'
import ChiTietIP from './Modal/ChiTietIP.vue'
import EventBus from '../../../utils/eventBus'
export default {
  components: {
    TuChoi,
    ChiTietIP
  },
  data: function() {
    return {
      capphat_id: 0,
      dataItems: [],
      dsAoHoaNC: [],
      dsK8sNC: [],
      dsIDGNC: [],
      dsBKNC: [],
      dsAoHoaCP: [],
      dsK8sCP: [],
      dsIDGCP: [],
      dsBKCP: [],
      files: [],
      currentTab: 1,
      currentTab2: 5,
      currentItem: {},
      currentItemAoHoaCP: {},
      controls: {
        txtTen: {
          enabled: false,
          invalid: false
        },
        txtGhiChu: {
          enabled: false
        }
      },
      buttons: {
        btnThucHien: {
          enabled: false
        },
        btnXacNhan: {
          enabled: false
        },
        btnTuChoi: {
          enabled: false
        }
      }
    }
  },
  computed: {
    gridItems: function() {
      return this.$refs.gridItems
    }
  },
  watch: {
    'currentItem.id': function(newVal, oldVal) {
      if (newVal !== oldVal && newVal != 0) {
        const path = this.currentItem.duong_dan
        if (path) {
          this.getFileMinio(path).then((res) => {
            this.files = [{ name: 'Tải file', url: res, file: null }]
          })
        }
        this.buttons.btnXacNhan.enabled = this.currentItem.trangthai_xacnhan
        const isEnabled = this.currentItem.trangthai == 1
        this.buttons.btnThucHien.enabled = isEnabled
        this.buttons.btnTuChoi.enabled = isEnabled
      }
    }
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      this.dataItems = await this.apiListDanhSach()
    } finally {
      this.$root.showLoading(false)
    }
    EventBus.$on('onChiTietIPClick', this.onChiTietIP)
  },
  methods: {
    getFileMinio: async function(path) {
      let result = null
      try {
        let rs = await this.$root.context.post('/web-ecms/storage/getPresignedUrl', {
          fileSource: path
        })
        result = rs.data.result
      } catch (e) {
        this.handleErrorResponse(e)
      } finally {
        return result
      }
    },
    openFile(file) {
      const a = document.createElement('a')
      a.href = file.url
      a.download = file.name // Ten file khi tai xuong
      a.target = '_blank'
      document.body.appendChild(a)
      a.click()
      document.body.removeChild(a)
    },
    getNhuCauAoHoa: async function(ycID) {
      let result = []
      try {
        const rs = await this.$root.context.get(`/web-ecms/phieu-cap-phat/${ycID}/nhu-cau-ao-hoa`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    getNhuCauK8s: async function(ycID) {
      let result = []
      try {
        const rs = await this.$root.context.get(`/web-ecms/phieu-cap-phat/${ycID}/nhu-cau-k8s`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    getNhuCauIDG: async function(ycID) {
      let result = []
      try {
        const rs = await this.$root.context.get(`/web-ecms/phieu-cap-phat/${ycID}/nhu-cau-idg`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    getNhuCauBackup: async function(ycID) {
      let result = []
      try {
        const rs = await this.$root.context.get(`/web-ecms/phieu-cap-phat/${ycID}/nhu-cau-backup`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    getTTCPAoHoa: async function(ycID) {
      let result = []
      try {
        const rs = await this.$root.context.get(`/web-ecms/phieu-cap-phat/${ycID}/thong-tin-cap-phat-ao-hoa`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    getTTCPK8s: async function(ycID) {
      let result = []
      try {
        const rs = await this.$root.context.get(`/web-ecms/phieu-cap-phat/${ycID}/thong-tin-cap-phat-k8s`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    getTTCPIDG: async function(ycID) {
      let result = []
      try {
        const rs = await this.$root.context.get(`/web-ecms/phieu-cap-phat/${ycID}/thong-tin-cap-phat-idg`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    getTTCPBackup: async function(ycID) {
      let result = []
      try {
        const rs = await this.$root.context.get(`/web-ecms/phieu-cap-phat/${ycID}/thong-tin-cap-phat-backup`)
        result = rs.data
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },
    apiListDanhSach: async function() {
      let result = []
      try {
        let rs = await this.$root.context.get(`/web-ecms/phieu-cap-phat?type=2`)
        result = rs.data
        if (this.capphat_id != 0) {
          const newList = result.filter(item => item.id == this.capphat_id);
          return newList;
        }
      } catch (error) {
        this.handleErrorResponse(error)
      }
      return result
    },

    handleErrorResponse(error) {
      if (!(error.response == null || error.response == undefined)) {
        if (!(error.response.data == null || error.response.data == undefined)) {
          this.$root.toastError(error.response.data.message_detail)
        }
      }
    },

    apiThucHienCapPhat: async function(ycID) {
      let result
      try {
        let rs = await this.$root.context.post('/web-ecms/phieu-cap-phat/thuc-hien-cap-phat', {
          p_capphat_id: ycID
        })
        result = rs.data
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },

    apiXacNhanCapPhat: async function(ycID) {
      let result
      try {
        let rs = await this.$root.context.post('/web-ecms/phieu-cap-phat/xac-nhan-cap-phat', {
          p_capphat_id: ycID
        })
        result = rs.data
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },

    apiTuChoiCapPhat: async function(ycID, lydo) {
      let result
      try {
        let rs = await this.$root.context.post('/web-ecms/phieu-cap-phat/tu-choi-cap-phat', {
          p_capphat_id: ycID,
          p_lydotuchoi: lydo
        })
        result = rs.data
      } catch (e) {
        if (!(e.response == null || e.response == undefined)) {
          if (!(e.response.data == null || e.response.data == undefined)) {
            this.$root.toastError(e.response.data.message_detail)
          }
        }
      }
      return result
    },

    enableControls: function(args) {
      this.buttons.btnThucHien.enabled = args
      this.buttons.btnXacNhan.enabled = args
      this.buttons.btnTuChoi.enabled = args
    },

    onShowDataZone: async function(pId) {
      if (pId > 0) {
        try {
          this.$root.showLoading(true)
          this.dsAoHoaNC = await this.getNhuCauAoHoa(pId)
          this.dsK8sNC = await this.getNhuCauK8s(pId)
          this.dsIDGNC = await this.getNhuCauIDG(pId)
          this.dsBKNC = await this.getNhuCauBackup(pId)
          this.dsAoHoaCP = await this.getTTCPAoHoa(pId)
          this.dsK8sCP = await this.getTTCPK8s(pId)
          this.dsIDGCP = await this.getTTCPIDG(pId)
          this.dsBKCP = await this.getTTCPBackup(pId)
        } finally {
          this.$root.showLoading(false)
        }
      }
    },

    setCurrentItem: function(item) {
      console.log('setCurrentItem', item);
      this.currentItem = item
      this.buttons.btnXacNhan.enabled = this.currentItem.trangthai_xacnhan
      const isEnabled = this.currentItem.trangthai == 1
      this.buttons.btnThucHien.enabled = isEnabled
      this.buttons.btnTuChoi.enabled = isEnabled
      this.onShowDataZone(item.id)
    },

    setCurrentItemDefault: function() {
      this.currentItem = null
      this.dsAoHoaNC = []
      this.dsK8sNC = []
      this.dsIDGNC = []
      this.dsBKNC = []
      this.dsAoHoaCP = []
      this.dsK8sCP = []
      this.dsIDGCP = []
      this.dsBKCP = []
      this.currentItemAoHoaCP = null
      let isEnabled = 0
      this.buttons.btnXacNhan.enabled = isEnabled
      this.buttons.btnThucHien.enabled = isEnabled
      this.buttons.btnTuChoi.enabled = isEnabled
    },

    grdItems_RowSelected: async function(args) {
      try {
        this.$root.showLoading(true)
        this.setCurrentItem(args.data)
      } finally {
        this.$root.showLoading(false)
      }
    },

    grdItems_RowDeselected: async function() {
      let items = this.gridItems.getSelectedRecords()
      if (items && items.length > 0) {
        try {
          this.$root.showLoading(true)
          this.setCurrentItem(items[0])
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        this.setCurrentItemDefault()
      }
    },

    grdItemsAoHoaCP_RowSelected: async function(args) {
      try {
        this.$root.showLoading(true)
        this.setCurrentItemsAoHoaCP(args.data)
      } finally {
        this.$root.showLoading(false)
      }
    },

    grdItemsAoHoaCP_RowDeselected: async function() {
      let items = this.gridItems.getSelectedRecords()
      if (items && items.length > 0) {
        try {
          this.$root.showLoading(true)
          this.setCurrentItemsAoHoaCP(items[0])
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        this.setCurrentItemAoHoaCPDefault()
      }
    },

    setCurrentItemAoHoaCPDefault: async function() {
      this.currentItemAoHoaCP = null
    },

    setCurrentItemsAoHoaCP: async function(item) {
      this.currentItemAoHoaCP = item
    },

    onThucHien: async function(args) {
      let flag = await this.$confirm('Có chắc bạn muốn thực hiện cấp phát tài nguyên?', 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => {
          return false
        })
      if (flag) {
        try {
          this.$root.showLoading(true)
          let rs = await this.apiThucHienCapPhat(this.currentItem.id)
          if (rs) {
            this.dataItems = await this.apiListDanhSach()
            if (this.dataItems) {
              this.setCurrentItem(this.dataItems[0])
            }
            this.$root.toastSuccess('Thực hiện cấp phát tài nguyên thành công')
          } else {
            this.$root.toastError('Thực hiện cấp phát tài nguyên thất bại')
          }
        } finally {
          this.$root.showLoading(false)
        }
      }
    },
    onXacNhan: async function(args) {
      let flag = await this.$confirm('Có chắc bạn muốn xác nhận cấp phát tài nguyên?', 'Xác nhận thực hiện', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'info',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => {
          return false
        })
      if (flag) {
        try {
          this.$root.showLoading(true)
          let rs = await this.apiXacNhanCapPhat(this.currentItem.id)
          if (rs) {
            this.dataItems = await this.apiListDanhSach()
            this.$root.toastSuccess('Xác nhận cấp phát tài nguyên thành công')
          } else {
            this.$root.toastError('Xác nhận cấp phát tài nguyên thất bại')
          }
        } finally {
          this.$root.showLoading(false)
        }
      }
    },
    onTuChoi: async function(args) {
      this.$refs.dialogTuChoi.dialogOpen()
    },
    onChiTietIP: async function(args) {
      await this.setCurrentItemsAoHoaCP(args)
      await this.$refs.dialogChiTietIP.dialogOpen()
    },
    colChiTietIP: function() {
      return {
        template: Vue.component('columnTemplate', {
          template: `<div>
                          <span class="ipCell" @click="chiTietIP">{{data.ip}}</span>
                      </div>`,
          data() {
            return { data: {} }
          },
          methods: {
            chiTietIP() {
              EventBus.$emit('onChiTietIPClick', this.data)
            }
          }
        })
      }
    },
    refreshData: async function() {
      this.dataItems = await this.apiListDanhSach()
    },
    refreshDataWithCapPhatId: async function(capphat_id) {
      this.capphat_id = capphat_id;
      this.dataItems = await this.apiListDanhSach();
      if (this.dataItems.length > 0) {
        this.setCurrentItem(this.dataItems[0])
      }
    },

  },
  detroyed() {
    EventBus.$off('onChiTietIPClick', this.onChiTietIP)
  }
}
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type='checkbox']:checked:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type='checkbox']:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
}

span.ipCell {
  color: #007bff;
  text-decoration: none;
}

span.ipCell:hover {
  color: #0056b3;
  text-decoration: underline;
  cursor: pointer;
}
</style>
