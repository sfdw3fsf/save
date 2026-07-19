<template src="./thongtin-chung.html"></template>

<script>
import VLanSelectModal from './components/VLanSelectModal.vue'
import { thongTinChungSubnetSchema } from './thongTinChungSchema'

export default {
  name: 'ThongTinChungSubnet',

  components: {
    VLanSelectModal
  },

  props: {
    mode: {
      type: String,
      default: 'EDIT'
    },
    subnetID: {
      type: String
    },
    currentItem: {
      type: Object,
      default: () => ({})
    }
  },

  data() {
    return {
      form: {
        tenSubnet: '',
        loaiIp: '',
        phanLoaiIp: '',
        subnetMask: '',
        idc: '',
        vrf: '',
        zone: '',
        thietBiVatLy: '',
        noiBo: '',
        mucDich: '',
        thamChieu: '',
        hieuLuc: false,
        quangBa: false,
        tuDong: false,
        ccdv: '',
        subnet: '',
        gateWay: '',
        htm: '',
        subnetCha: '',
        donVi: '',
        ghiChu: '',
        vlan: []
      },

      allVlanList: [],
      vlanList: [],
      showVlanSelectModal: false,
      fieldErrors: {},
      selectedVlanRows: [],

      loaiIPList: [],
      phanLoaiIPList: [],
      subnetMask: [],
      idcList: [],
      allSubnetList: [],
      quyHoachSubnetList: [],
      subnetDropdownList: [],
      hatangMangDropList: []
    }
  },

  mounted() {
    this.getPhanLoaiIPList()
    this.getLoaiIPList()
    this.getSubnetMask()
    this.getIDCList()
    this.loadFakeVlanList()
    this.getSubnetDropdownList()
    this.getDanhSachVlanDropdownList()
    this.getDanhSachHaTangMangDropList()
  },

  computed: {
    isViewMode() {
      return this.mode === 'VIEW'
    },
    isEditMode() {
      return this.mode === 'EDIT'
    }
  },

  methods: {
    async getLoaiIPList() {
      try {
        const rs = await this.$root.context.get('http://localhost:10990/subnet-tai-nguyen/loai-ip')

        if (rs && rs.data) {
          this.loaiIPList = rs.data
        }
      } catch (error) {
        console.error('Error loading Loai IP:', error)
      }
    },

    async getDanhSachHaTangMangDropList() {
      try {
        const rs = await this.$root.context.get('http://localhost:10990/subnet-tai-nguyen/danhsach-htm')

        if (rs && rs.data) {
          this.hatangMangDropList = rs.data
        }
      } catch (error) {
        console.error('Error loading Ha Tang Mang:', error)
      }
    },

    async getSubnetDropdownList() {
      try {
        const rs = await this.$root.context.get('http://localhost:10990/subnet-tai-nguyen/danhsach-subnet-droplist')

        if (rs && rs.data) {
          this.subnetDropdownList = rs.data
        }
      } catch (error) {
        console.error('Error loading Loai Subbnet:', error)
      }
    },

    async loadAllVlan() {
      try {
        const rs = await this.$root.context.get('/web-ecms/danhmuc/vlan/danhsach')
        if (rs?.data) {
          this.allVlanList = rs.data
        }
      } catch (e) {
        console.error('Error loading VLAN list', e)
      }
    },

    loadFakeVlanList() {
      this.allVlanList = [
        { ID: 1, TEN: 'VLAN Core 10', VLAN_ID: 10, MO_TA: 'Core network VLAN' },
        { ID: 2, TEN: 'VLAN Office 20', VLAN_ID: 20, MO_TA: 'Office devices' },
        { ID: 3, TEN: 'VLAN Server 30', VLAN_ID: 30, MO_TA: 'Server rack zone' },
        { ID: 4, TEN: 'VLAN Public 40', VLAN_ID: 40, MO_TA: 'Public user VLAN' },
        { ID: 5, TEN: 'VLAN Camera 50', VLAN_ID: 50, MO_TA: 'Camera surveillance' }
      ]
    },

    openVlanSelect() {
      this.selectedVlanRows = []
      this.showVlanSelectModal = true
    },

    onSaveSelectedVlan(rows) {
      // Normalize all selected rows
      const normalized = rows.map((r) => ({
        VLAN_ID: r.VLAN_ID ?? r.ID,
        VLAN_TEN: r.VLAN_TEN ?? r.TEN,
        VLAN_HIEU_LUC: r.VLAN_HIEU_LUC ?? r.HIEU_LUC,
        IDC_TEN: r.IDC_TEN
      }))

      // Duplicate check by VLAN_ID
      const existing = new Set(this.form.vlan.map((v) => v.VLAN_ID))
      const toAdd = normalized.filter((n) => !existing.has(n.VLAN_ID))
      this.form.vlan = [...this.form.vlan, ...toAdd]
      this.showVlanSelectModal = false
    },

    validateThongTinChung() {
      const { valid, errors } = thongTinChungSubnetSchema.validate(this.form)
      console.log('Valid validate', valid), console.log('Errors validate', errors)
      this.fieldErrors = errors

      if (!valid) {
        const allMessages = Object.values(errors)
          .flat()
          .map((msg) => `${msg}`)
          .join(', ')

        this.$toast.error(allMessages, {
          timeout: 5000,
          dangerouslyHTMLString: true
        })
      }

      return valid
    },

    async getPhanLoaiIPList() {
      try {
        const rs = await this.$root.context.get('http://localhost:10990/subnet-tai-nguyen/phanloai-ip')

        if (rs && rs.data) {
          this.phanLoaiIPList = rs.data
        }
      } catch (error) {
        console.error('Error loading Loai IP:', error)
      }
    },

    onRowSelected(e) {
      const r = e.data || e.rowData
      if (!r) return

      const mapped = {
        VLAN_ID: r.VLAN_ID ?? r.ID,
        VLAN_TEN: r.VLAN_TEN ?? r.TEN,
        VLAN_HIEU_LUC: r.VLAN_HIEU_LUC ?? r.HIEU_LUC,
        IDC_TEN: r.IDC_TEN
      }

      if (!this.selectedVlanRows.some((v) => v.VLAN_ID === mapped.VLAN_ID)) {
        this.selectedVlanRows.push(mapped)
      }
    },

    onRowDeselected(e) {
      const row = e.data || e.rowData
      this.selectedVlanRows = this.selectedVlanRows.filter((r) => r.ID !== row.ID)
    },

    deleteSelectedVlan() {
      if (this.selectedVlanRows.length === 0) return

      const idsToDelete = new Set(this.selectedVlanRows.map((r) => r.VLAN_ID))

      this.form.vlan = this.form.vlan.filter((v) => !idsToDelete.has(v.VLAN_ID))

      this.selectedVlanRows = []

      this.$refs.thuocVlanGrid.clearSelection()
    },

    async getSubnetMask() {
      try {
        const rs = await this.$root.context.post('/web-ecms/danhmuc/subnetmask/danhsach')

        if (rs && rs.data) {
          this.subnetMask = rs.data
        }
      } catch (error) {
        console.error('Error loading Loai IP:', error)
      }
    },

    async getIDCList() {
      try {
        const response = await this.$root.context.get('/web-ecms/danhmuc/ha-tang-idc')
        if (response?.data) {
          this.idcList = response.data
        }
      } catch (error) {
        console.error('Error loaddinf getIdc ', error)
      }
    },

    async getQuyHoachSubnetList() {
      try {
        const response = await this.$root.context.get('/web-ecms/danhmuc/quy-hoach-subnet/danh-sach')
        if (response?.data) {
          this.idcList = response.data
        }
      } catch (error) {
        console.error('Error loaddinf getIdc ', error)
      }
    },

    async getDanhSachVlanDropdownList() {
      try {
        const rs = await this.$root.context.get('http://localhost:10990/subnet-tai-nguyen/danhsach-vlan')

        if (rs && rs.data) {
          this.vlanList = rs.data
        }
      } catch (error) {
        console.error('Error loading Vlan dropList:', error)
      }
    }
  },

  watch: {
    currentItem: {
      handler(newVal) {
        if (newVal && Object.keys(newVal).length) {
          this.form = { ...newVal }
        }
      }
    }
  }
}
</script>
