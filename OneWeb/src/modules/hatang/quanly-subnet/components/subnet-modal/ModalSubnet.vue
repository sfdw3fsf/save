<template src="./modal-subnet.html"></template>
<style src="./subnet-modal.scss"></style>
<script>
import HaTangSubnet from '../hatang-subnet/HaTangSubnet.vue'
import IpStore from '../ip-store/IpStore.vue'
import LichSuThayDoi from '../lichsu-thaydoi/LichSuThayDoi.vue'
import SubnetCon from '../subnet-con/SubnetCon.vue'
import ThongTinChung from '../thongtin-chung/ThongTinChung.vue'
import Vrf from '../vrf/vrf.vue'

export default {
  name: 'ModalSubnet',

  components: {
    LichSuThayDoi,
    SubnetCon,
    IpStore,
    ThongTinChung,
    HaTangSubnet,
    Vrf
  },

  props: {
    item: { type: Object, default: () => ({}) },
    mode: { type: String, default: 'VIEW' },
    visible: { type: Boolean, default: false },

    subnetId: { type: Number, default: null },

    loaiIPList: {
      type: Array,
      default: () => []
    },

    phanLoaiIPList: {
      type: Array,
      default: () => []
    },

    IDCList: {
      type: Array,
      default: () => []
    },

    VlanList: {
      type: Array,
      default: () => []
    },

    SubnetList: {
      type: Array,
      default: () => []
    },

    QuyHoachSubnetList: {
      type: Array,
      default: () => []
    },

    ipInvalid: {
      type: Boolean,
      default: false
    }
  },

  data() {
    return {
      animationSettings: { effect: 'Zoom' },
      activeTab: 1,
      detailSubnetData: {},
      subnetConData: [],
      danhSachIpData: [],
      danhSachVrfData: [],
      danhSachZoneData: []
    }
  },

  methods: {
    close() {
      this.$emit('close')
    },
    async loadIpBySubnetAndSubnetChild(subnetId) {
      const rs = await this.$root.context.get(`http://localhost:10990/subnet-tai-nguyen/ds-ip-${subnetId}`)
      this.danhSachIpData = rs.data
    },

    async save() {
      const valid = this.$refs.thongtin.validateThongTinChung()
      if (!valid) return

      const payload = this.$refs.thongtin.form
      console.log('test payload', payload)

      try {
        const rs = await this.$root.context.post(
          `http://localhost:10990/subnet-tai-nguyen/update-subnet/${this.subnetId}`,
          {
            TEN: payload.tenSubnet,
            HTM_ID: payload.htm,
            LOAIIP_ID: payload.loaiIp,
            PHANLOAIIP_ID: payload.phanLoaiIp,
            GATEWAY: payload.gateWay,
            SUBNET: payload.subnet,
            SBMASK: payload.subnetMask,
            IDC_ID: payload.idc,
            SNCHA_ID: payload.subnetCha,
            SINH_IP: payload.tuDong ? 1 : 0,
            QUANGBA_IP: payload.quangBa ? 1 : 0,
            TT_CCDV_ID: payload.ccdv,
            MUCDICHSD_ID: payload.mucDich || null,
            THAMCHIEU: payload.thamChieu || null,
            HIEU_LUC: payload.hieuLuc ? 1 : 0,
            GHI_CHU: payload.ghiChu || null,
            NOIBO: payload.noiBo || null,
            DONVIQL_ID: payload.donVi || null,
            VLAN: payload.vlan.map((item) => ({ vlan_id: item.VLAN_ID }))
          }
        )

        this.$toast.success('Cập nhật subnet thành công!')
        this.$emit('close')
        this.$emit('updated', rs.data) // optional emit to parent
      } catch (error) {
        console.error('Update subnet error:', error)
        this.$toast.error('Cập nhật thất bại!')
      }
    },

    changeTab(index) {
      this.activeTab = index
    },

    async loadSubnetDetail() {
      const rs = await this.$root.context.get(
        `http://localhost:10990/subnet-tai-nguyen/chi-tiet-subnet/${this.subnetId}`
      )
      this.detailSubnetData = this.mapSubnetResponse(rs.data)
    },

    async loadSubnetConBySubnetParentId(parentId) {
      const rs = await this.$root.context.get(
        `http://localhost:10990/subnet-tai-nguyen/danhsach-subnet-con/parent-${parentId}`
      )
      this.subnetConData = rs.data.map((item) => this.mapSubnetResponse(item))
    },

    async loadVrfBySubnetId(subnetId) {
      const rs = await this.$root.context.get(`http://localhost:10990/subnet-tai-nguyen/danhsach-vrf-${subnetId}`)
      this.danhSachVrfData = rs.data
    },

    async loadZoneBySubnetId(subnetId) {
      const rs = await this.$root.context.get(`http://localhost:10990/subnet-tai-nguyen/danhsach-zone-${subnetId}`)
      this.danhSachZoneData = rs.data
    },

    mapSubnetResponse(data) {
      return {
        tenSubnet: data.TEN || '',
        loaiIp: data.LOAIIP_ID || '',
        phanLoaiIp: data.PHANLOAIIP_ID || '',
        subnetMask: data.SBMASK || '',
        idc: data.IDC_ID || '',
        gateWay: data.GATEWAY || '',
        subnet: data.SUBNET || '',
        htm: data.HTM_ID || '',
        subnetCha: data.SNCHA_ID || '',
        donVi: data.DON_VI || '',
        ghiChu: data.GHI_CHU || '',
        trangThaiCcdv: data.TT_CCDV_ID || '',
        vlan: data.VLAN || []
      }
    }
  },

  computed: {
    isViewMode() {
      return this.mode === 'VIEW'
    },
    isEditMode() {
      return this.mode === 'EDIT'
    }
  },

  watch: {
    subnetId(val) {
      if (val && this.subnetId) {
        this.loadSubnetDetail()
        this.loadSubnetConBySubnetParentId(this.subnetId)
        // this.loadIpBySubnetAndSubnetChild(this.subnetId)
        this.loadIpBySubnetAndSubnetChild(this.subnetId)
        this.loadZoneBySubnetId(this.subnetId)
        this.loadVrfBySubnetId(this.subnetId)
      }
    }
  }
}
</script>
