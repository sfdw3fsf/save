<template src="./index.html"></template>
<script>
import { listOfFields, defaultItem } from '../../fields'
import { buildController, createControls, rules as R } from '../../../../controls'
import api from '../../../../api/danhmuc'
import apiTrangThaiVH from '../../../../../danhmuc-trangthai-vh/api'

export default {
  name: 'ChiTietSubnet',
  props: {
    currentItem: {
      type: Object,
      default: () => ({})
    },
    mode: {
      type: String,
      default: 'view'
    },
    zoneId: {
      type: [Number, String],
      default: null
    }
  },
  data() {
    return {
      controls: {},
      controlsController: null,
      dropdowns: {
        ipLoai: [],
        ipPhanLoai: [],
        trangthaiCcdv: [],
        subnet: [],
        subnetMask: [],
        gateway: [],
        idc: [],
        isIdc: [],
        subnetCha: [],
        mucDichSd: [],
        donViQuanLy: []
      },
      dropdownFields: { value: 'id', text: 'name' },
      listOfFields
    }
  },
  created() {
    // Khởi tạo controls trước khi render template
    this.controls = createControls(Object.keys(listOfFields))
    this.controlsController = buildController(this.controls, {
      ten: [R.required(), R.maxLength(255)],
      ip_loai_id: [R.required()],
      ip_phanloai_id: [R.required()],
      subnet: [R.required()],
      subnet_mask_id: [R.required()],
      gateway: [R.required()],
      ghi_chu: [R.maxLength(500)]
    })
    this.applyMode(this.mode)
    this.loadDanhMucMaster()
  },
  mounted() {
    // this.dropdowns.isIdc = [
    //   {id: 1, name: 'Nội bộ'},
    //   {id: 0, name: 'Khách hàng'}
    // ]
  },
  watch: {
    zoneId: {
      immediate: true,
      handler(newZoneId) {
        if (newZoneId) {
          this.loadZoneData(newZoneId)
        }
      }
    },
    mode(newMode) {
      this.applyMode(newMode)
    }
  },
  methods: {
    async loadDanhMucMaster() {
      try {
        this.setGlobalLoading(true)
        await Promise.all([
          this.fetchIpLoaiOptions(),
          this.fetchIpPhanLoaiOptions(),
        ])
      } catch (error) {
        this.$toast.error(error.message || 'Không thể tải dữ liệu ban đầu')
      } finally {
        this.setGlobalLoading(false)
      }
    },

    /**
     * Save / Update handlers
     */

    async onClickSave() {
      if (!this.validateControls()) {
        return
      }
      // Implement save logic here
      console.log('Save clicked', this.currentItem)
    },

    /**
     * Các hàm fetch data từ API
     */

    // Lấy Loại IP
    async fetchIpLoaiOptions() {
      try {
        const raw = await api.getLoaiIp(this.$root.context)
        this.dropdowns.ipLoai = this.normalizeOptions(raw)
        console.log('Loai IP', this.dropdowns.ipLoai)
      } catch (error) {
        this.dropdowns.ipLoai = []
        this.$toast.error(error.message || 'Không thể tải danh sách Loại IP')
      }
    },

    // Lấy Phân loại IP
    async fetchIpPhanLoaiOptions() {
      try {
        const raw = await api.getPhanLoaiIp(this.$root.context)
        this.dropdowns.ipPhanLoai = this.normalizeOptions(raw)
      } catch (error) {
        this.dropdowns.ipPhanLoai = []
        this.$toast.error(error.message || 'Không thể tải danh sách Phân loại IP')
      }
    },

    // Lấy Subnet
    async fetchSubnet(zoneId) {
      try {
        const raw = await api.getDanhMucVlan(this.$root.context, 'SUBNET-ZONE', zoneId)
        this.dropdowns.subnets = this.normalizeOptions(raw)
      } catch (error) {
        this.dropdowns.subnets = []
        this.$toast.error(error.message || 'Không thể tải danh sách Subnet')
      }
    },

    // Lấy Gateway
    async fetchGateway(zoneId) {
      try {
        const raw = await api.getDanhMucVlan(this.$root.context, 'GATEWAY-ZONE', zoneId)
        this.dropdowns.gateways = this.normalizeOptions(raw)
      } catch (error) {
        this.dropdowns.gateways = []
        this.$toast.error(error.message || 'Không thể tải danh sách Gateway')
      }
    },

    // Lấy Subnet mask từ danh mục trạng thái vận hành
    async fetchSubnetMaskOptions() {
      try {
        const raw = await apiTrangThaiVH.getDanhSach(this.$root.context)
        this.dropdowns.subnetMasks = this.normalizeOptions(raw, 'ID', 'TEN')
      } catch (error) {
        this.dropdowns.subnetMasks = []
        this.$toast.error(error.message || 'Không thể tải danh sách Subnet mask')
      }
    },

    // Lấy Trạng thái CCDV
    async fetchTrangThaiCcdvOptions() {
      try {
        const raw = await api.getDanhMucVlan(this.$root.context, 'TRANGTHAI_CCDV')
        this.dropdowns.trangthaiCcdv = this.normalizeOptions(raw)
      } catch (error) {
        this.dropdowns.trangthaiCcdv = []
        this.$toast.error(error.message || 'Không thể tải danh sách Trạng thái CCDV')
      }
    },

    normalizeOptions(list, valueKeys = 'ID', textKeys = 'TEN') {
      if (!Array.isArray(list)) return []
      return list
        .map((item) => {
          const id = item[valueKeys[0]]
          const name = item[textKeys[0]]
          if (id === undefined || id === null) return null
          return { id: id, name: name }
        })
        .filter(Boolean)
    },

    /**
     * helper functions
     */
    validateControls() {
      const { isValid, errors } = this.controlsController.validate(this.currentItem)
      if (!isValid) {
        Object.keys(errors || {}).forEach((field) => {
          const messages = errors[field]
          if (messages && messages.length > 0) {
            const fieldLabel = this.listOfFields[field] || field
            this.$toast.error('Lỗi trường ', `${fieldLabel}: ${messages[0]}`)
          }
        })
      }
      return isValid
    },
    applyMode(mode) {
      if (!['view', 'add', 'edit'].includes(mode)) return

      if (mode === 'add' || mode === 'edit') {
        this.controlsController.setEnabledAll(true)
        // Keep readonly fields disabled
        // this.controlsController.setEnabled(['ip_loai_id', 'ip_phanloai_id', 'subnet', 'gateway'], false)
      } else if (mode === 'view') {
        this.controlsController.setEnabledAll(false)
      }
    },
    setGlobalLoading(state) {
      if (this.$root && typeof this.$root.loading === 'function') {
        this.$root.loading(state)
      }
    }
  }
}
</script>
