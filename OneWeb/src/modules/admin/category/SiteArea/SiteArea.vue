<template src='./SiteArea.html'></template>
<style scoped src='./SiteArea.scss'></style>
<script>
import { mapActions } from 'vuex'
import { GridPlugin, VirtualScroll, Sort, Filter, Page, Selection, GridComponent } from '@syncfusion/ej2-vue-grids'
import { TreeGridPlugin } from '@syncfusion/ej2-vue-treegrid'
import TreeView from './parts/tree/TreeView.vue'
import 'vue2-datepicker/index.css'
import { mixin } from '../Staff/mixins/index'
export default {
  components: {
    GridPlugin,
    VirtualScroll,
    Sort,
    Selection,
    GridComponent,
    TreeGridPlugin,
    TreeView
  },
  name: 'SiteArea',
  mixins: [mixin],
  data: () => {
    return {
      pageSettings: { pageSize: 5 },
      data: [{value: 2}, {value: 'z'}, {value: 1}],
      selectionOptions: { type: 'Multiple', checkBoxOnly: true },
      // Button controls
      tsbtnNhapMoi: {
        Enabled: true
      },
      tsbtnGhiLai: {
        Enabled: true
      },
      tsbtnXoa: {
        Enabled: true
      },
      tsbtnHuyBo: {
        Enabled: true
      },
      txtMa_KhuVuc: {
        Text: ''
      },
      kvDaChon: null,
      khuVuc: {
        ma_kv: '',
        khuvuc_id: '',
        ten_kv: '',
        ghichu: ''
      },
      temp_storesData: [],
      dgvLoai_KV_ChuaGan: {
        selectionOptions: { type: 'Multiple' },
        dataSource: []
      },
      dgvLoai_KV_DaGan: {
        selectionOptions: { type: 'Multiple' },
        dataSource: []
      },
      dgvDuLieuNap: {
        selectionOptions: { type: 'Multiple' },
        dataSource: []
      },
      selectedDistrict: null,
      selectedWard: null,
      dgvPho_ChuaGan: {
        selectionOptions: { type: 'Multiple' },
        dataSource: []
      },
      dgvPho_DaGan: {
        selectionOptions: { type: 'Multiple' },
        dataSource: []
      },
      dgvAp_ChuaGan: {
        selectionOptions: { type: 'Multiple' },
        dataSource: []
      },
      dgvAp_DaGan: {
        selectionOptions: { type: 'Multiple' },
        dataSource: []
      },
      dgvKhu_ChuaGan: {
        selectionOptions: { type: 'Multiple' },
        dataSource: []
      },
      dgvKhu_DaGan: {
        selectionOptions: { type: 'Multiple' },
        dataSource: []
      }
    }
  },
  computed: {
    lay_ds_khuvuc_theo_diemchia () {
      return this.buildTreeFromList(this.$store.getters['siteArea/areaListFollowDividedPoint'], 'ten_kv', 'khuvuc_id', 'khuvuc_cha_id')
    },
    sp_lay_danh_sach_khu_vuc_cha () {
      return this.$store.getters['siteArea/parentAreaList']
    },
    lay_danh_sach_loai_khu_vuc_da_gan () {
      return this.$store.getters['siteArea/assignedArea']
    },
    lay_danh_sach_loai_khu_vuc_chua_gan () {
      return this.$store.getters['siteArea/unassignedArea']
    },
    lay_danh_sach_quan_huyen () {
      return this.$store.getters['siteArea/districts']
    },
    lay_danh_sach_phuong_xa () {
      return this.$store.getters['siteArea/wards']
    },
    lay_danh_sach_phuong () {
      const data = []
      this.lay_danh_sach_phuong_xa.forEach(rs => {
        if (+rs.quan_id === +this.selectedDistrict && !data.find(item => item.phuong_id === rs.phuong_id)) {
          data.push(rs)
        }
      })
      return data
    },
    lay_ds_pho () {
      return this.$store.getters['siteArea/towns'].map(rs => {
        return {
          ...rs,
          ten_phuong: this.lay_danh_sach_phuong.find(item => item.phuong_id === this.selectedWard).ten_phuong
        }
      })
    },
    lay_ds_ap () {
      return this.$store.getters['siteArea/hamlets'].map(rs => {
        return {
          ...rs,
          ten_phuong: this.lay_danh_sach_phuong.find(item => item.phuong_id === this.selectedWard).ten_phuong
        }
      })
    },
    lay_ds_khu () {
      return this.$store.getters['siteArea/areas'].map(rs => {
        return {
          ...rs,
          ten_phuong: this.lay_danh_sach_phuong.find(item => item.phuong_id === this.selectedWard).ten_phuong
        }
      })
    },
    lay_ds_du_lieu_nap () {
      return this.$store.getters['siteArea/loadData']
    }
  },
  mounted () {
    window['auth'] = this.$auth
    window['thanhly'] = this
    this.loadData()
    // this.initSpliter()
  },
  methods: {
    ...mapActions('liquidateCommon', [
      'getListTelecomService',
      'getListSubscriptionType',
      'getListRequestType',
      'getListChanelSpeed',
      'getListSatellite',
      'getListContract',
      'layDsThueBao',
      'siteArea'
    ]),
    async loadData () {
      this.setButton(0)
      this.$store.dispatch('siteArea/getAreaListFollowDividePoint', {vnhanvien_id: 3})
      this.$store.dispatch('siteArea/getParentAreaList', {})
      this.$store.dispatch('siteArea/getDistricts')
      this.$store.dispatch('siteArea/getWards', { khuvuc_id: 1 })
    },
    buildTreeFromList: function (originalList, fieldName, fieldId, fieldParentId) {
      // remove duplicate element
      const list = []
      for (let i = 0; i < originalList.length; i++) {
        const item = originalList[i]
        if (!list.find(l => l.id.toString() === item[fieldId].toString())) {
          list.push({
            name: (item[fieldName] === undefined || item[fieldName] === null) ? '' : item[fieldName].toString(),
            id: (item[fieldId] === undefined || item[fieldId] === null) ? '' : item[fieldId].toString(),
            pid: (item[fieldParentId] === undefined || item[fieldParentId] === null) ? '' : item[fieldParentId].toString(),
            children: []
          })
        }
      }

      const map = {}
      let node
      const roots = []

      for (let i = 0; i < list.length; i += 1) {
        map[list[i].id] = i // initialize the map
        // list[i].children = [] // initialize the children
      }

      for (let i = 0; i < list.length; i += 1) {
        node = list[i]
        if (node.pid && node.pid !== '-1' && list[map[node.pid]]) {
          // if you have dangling branches check that map[node.parentId] exists
          if (!list[map[node.pid]].children) {
            list[map[node.pid]].children = []
          }
          list[map[node.pid]].children.push(node)
        } else {
          roots.push(node)
        }
      }
      return roots
    },
    setButton (kieu) {
      this.tsbtnNhapMoi.Enabled = false
      this.tsbtnGhiLai.Enabled = false
      this.tsbtnXoa.Enabled = false
      this.tsbtnHuyBo.Enabled = false

      if (kieu === -1) { // Bat dau
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
      }

      if (kieu === 0) { // Bat dau
        this.tsbtnNhapMoi.Enabled = true
        setTimeout(() => {
          this.onSelect(this.kvDaChon)
        }, 500)
        // this.clear()
      }

      if (kieu === 1) { // Them moi
        this.$store.dispatch('siteArea/clearAssignData')
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
        this.clear()
      }

      if (kieu === 2) { // Huy
        this.$store.dispatch('siteArea/clearAssignData')
        this.tsbtnNhapMoi.Enabled = true
        this.tsbtnXoa.Enabled = true
        this.dgvDuLieuNap.dataSource = this.lay_ds_du_lieu_nap
        this.clear()
        this.onSelect(this.kvDaChon)
      }

      if (kieu === 3) { // Edit
        this.tsbtnNhapMoi.Enabled = true
        this.tsbtnXoa.Enabled = true
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
      }
    },
    clear () {
      this.khuVuc = {
        ma_kv: '',
        khuvuc_id: '',
        ten_kv: '',
        ghichu: ''
      }
      this.dgvPho_ChuaGan.dataSource = []
      this.dgvPho_DaGan.dataSource = []
      this.dgvAp_ChuaGan.dataSource = []
      this.dgvAp_DaGan.dataSource = []
      this.dgvKhu_ChuaGan.dataSource = []
      this.dgvKhu_DaGan.dataSource = []
      this.dgvDuLieuNap.dataSource = []
      this.transferToUnassignArea(true)
      this.selectedDistrict = null
      this.selectedWard = null
      this.HT_LoaiKV(-1)
      // ds_pho_dagan.clear()
      // ds_pho_chuagan.clear()
      // ds_ap_chuagan.clear()
      // ds_ap_dagan.clear()
      // ds_khu_chuagan.clear()
      // ds_khu_dagan.clear()
      // ds_lkv_dagan.clear()
      // ds_lkv_chuagan.clear()
      // ds_khuvuc_px.clear()
      // this.cboQuan_SelectedValueChanged()
      // cboQuan_EditValueChanged()
    },
    HT_LoaiKV (khuVucId) {
      this.$store.dispatch('siteArea/getAssignedArea', { p_khuvuc_id: khuVucId, p_kieu: 1 })
      this.$store.dispatch('siteArea/getUnassignedArea', { p_khuvuc_id: khuVucId, p_kieu: 2 })
    },
    onSelect (e) {
      console.log('select tree node', e)
      this.kvDaChon = e
      if (this.tsbtnNhapMoi.Enabled) { // TODO: why?
        this.khuVuc = this.$store.getters['siteArea/areaListFollowDividedPoint'].find(item => +item.khuvuc_id === +e.id)
        this.$store.dispatch('siteArea/getAssignedArea', { p_khuvuc_id: +e.id, p_kieu: 1 })
        this.$store.dispatch('siteArea/getUnassignedArea', { p_khuvuc_id: +e.id, p_kieu: 2 })
        this.$store.dispatch('siteArea/getLoadData', { khuvuc_id: +e.id })
      }

      // TODO: SetButton(3)
    },
    isOpenChange () {

    },
    transferToAssignArea: async function (isTransferAll) {
      this.$root.showLoading(true)
      try {
        if (isTransferAll) {
          this.dgvLoai_KV_DaGan.dataSource = [...this.dgvLoai_KV_ChuaGan.dataSource, ...this.dgvLoai_KV_DaGan.dataSource]
          this.dgvLoai_KV_ChuaGan.dataSource = []
        } else {
          const data = this.$refs.gridUnassigned.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvLoai_KV_ChuaGan.dataSource = this.dgvLoai_KV_ChuaGan.dataSource.filter(item => !data.includes(item))
            this.dgvLoai_KV_DaGan.dataSource = [...data, ...this.dgvLoai_KV_DaGan.dataSource]
          }
        }
      } catch (error) {
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    transferToUnassignArea: async function (isTransferAll) {
      this.$root.showLoading(true)
      try {
        if (isTransferAll) {
          this.dgvLoai_KV_ChuaGan.dataSource = [...this.dgvLoai_KV_DaGan.dataSource, ...this.dgvLoai_KV_ChuaGan.dataSource]
          this.dgvLoai_KV_DaGan.dataSource = []
        } else {
          const data = this.$refs.gridAssigned.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvLoai_KV_DaGan.dataSource = this.dgvLoai_KV_DaGan.dataSource.filter(item => !data.includes(item))
            this.dgvLoai_KV_ChuaGan.dataSource = [...data, ...this.dgvLoai_KV_ChuaGan.dataSource]
          }
        }
      } catch (error) {
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    transferToAssignTown: async function (isTransferAll) {
      this.$root.showLoading(true)
      try {
        if (isTransferAll) {
          this.dgvPho_DaGan.dataSource = [...this.dgvPho_ChuaGan.dataSource, ...this.dgvPho_DaGan.dataSource]
          this.dgvPho_ChuaGan.dataSource = []
        } else {
          const data = this.$refs.unassignedTownGrid.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvPho_ChuaGan.dataSource = this.dgvPho_ChuaGan.dataSource.filter(item => !data.includes(item))
            this.dgvPho_DaGan.dataSource = [...data, ...this.dgvPho_DaGan.dataSource]
          }
        }
      } catch (error) {
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    transferToUnassignTown: async function (isTransferAll) {
      this.$root.showLoading(true)
      try {
        if (isTransferAll) {
          this.dgvPho_ChuaGan.dataSource = [...this.dgvPho_DaGan.dataSource, ...this.dgvPho_ChuaGan.dataSource]
          this.dgvPho_DaGan.dataSource = []
        } else {
          const data = this.$refs.assignedTownGrid.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvPho_DaGan.dataSource = this.dgvPho_DaGan.dataSource.filter(item => !data.includes(item))
            this.dgvPho_ChuaGan.dataSource = [...data, ...this.dgvPho_ChuaGan.dataSource]
          }
        }
      } catch (error) {
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    transferToAssignHamlet: async function (isTransferAll) {
      this.$root.showLoading(true)
      try {
        if (isTransferAll) {
          this.dgvAp_DaGan.dataSource = [...this.dgvAp_ChuaGan.dataSource, ...this.dgvAp_DaGan.dataSource]
          this.dgvAp_ChuaGan.dataSource = []
        } else {
          const data = this.$refs.unassignedHamletGrid.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvAp_ChuaGan.dataSource = this.dgvAp_ChuaGan.dataSource.filter(item => !data.includes(item))
            this.dgvAp_DaGan.dataSource = [...data, ...this.dgvAp_DaGan.dataSource]
          }
        }
      } catch (error) {
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    transferToUnassignHamlet: async function (isTransferAll) {
      this.$root.showLoading(true)
      try {
        if (isTransferAll) {
          this.dgvAp_ChuaGan.dataSource = [...this.dgvAp_DaGan.dataSource, ...this.dgvAp_ChuaGan.dataSource]
          this.dgvAp_DaGan.dataSource = []
        } else {
          const data = this.$refs.assignedHamletGrid.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvAp_DaGan.dataSource = this.dgvAp_DaGan.dataSource.filter(item => !data.includes(item))
            this.dgvAp_ChuaGan.dataSource = [...data, ...this.dgvAp_ChuaGan.dataSource]
          }
        }
      } catch (error) {
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    transferToAssignAreas: async function (isTransferAll) {
      this.$root.showLoading(true)
      try {
        if (isTransferAll) {
          this.dgvKhu_DaGan.dataSource = [...this.dgvKhu_ChuaGan.dataSource, ...this.dgvKhu_DaGan.dataSource]
          this.dgvKhu_ChuaGan.dataSource = []
        } else {
          const data = this.$refs.unassignedAreaGrid.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvKhu_ChuaGan.dataSource = this.dgvKhu_ChuaGan.dataSource.filter(item => !data.includes(item))
            this.dgvKhu_DaGan.dataSource = [...data, ...this.dgvKhu_DaGan.dataSource]
          }
        }
      } catch (error) {
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    transferToUnassignAreas: async function (isTransferAll) {
      this.$root.showLoading(true)
      try {
        if (isTransferAll) {
          this.dgvKhu_ChuaGan.dataSource = [...this.dgvKhu_DaGan.dataSource, ...this.dgvKhu_ChuaGan.dataSource]
          this.dgvKhu_DaGan.dataSource = []
        } else {
          const data = this.$refs.assignedAreaGrid.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvKhu_DaGan.dataSource = this.dgvKhu_DaGan.dataSource.filter(item => !data.includes(item))
            this.dgvKhu_ChuaGan.dataSource = [...data, ...this.dgvKhu_ChuaGan.dataSource]
          }
        }
      } catch (error) {
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    onChangeWard (data) {
      this.$store.dispatch('siteArea/getTowns', data)
      this.$store.dispatch('siteArea/getHamlets', data)
      this.$store.dispatch('siteArea/getAreas', data)
    },
    tsbtnNapDL_Click () {
      if (this.tsbtnNhapMoi.Enabled ||
        (this.dgvPho_DaGan.dataSource.length === 0 &&
        this.dgvAp_DaGan.dataSource.length === 0 &&
        this.dgvKhu_DaGan.dataSource.length === 0)) return

      const newListData = []

      if (this.dgvPho_DaGan.dataSource.length !== 0) {
        for (let pho of this.dgvPho_DaGan.dataSource) {
          if (this.dgvAp_DaGan.dataSource.length !== 0) {
            for (let ap of this.dgvAp_DaGan.dataSource) {
              if (this.dgvKhu_DaGan.dataSource.length !== 0) {
                for (let khu of this.dgvKhu_DaGan.dataSource) {
                  if (this.kiemtra_trunglap(this.selectedDistrict, this.selectedWard, pho.pho_id, ap.pho_id, khu.pho_id)) {
                    newListData.push({
                      khuvuc_id: this.khuVuc.khuvuc_id,
                      quan_id: this.selectedDistrict,
                      phuong_id: this.selectedWard,
                      pho_id: pho.pho_id,
                      ap_id: ap.pho_id,
                      khu_id: khu.pho_id,
                      ten_quan: this.lay_danh_sach_quan_huyen.find(rs => rs.QUAN_ID === this.selectedDistrict).TEN_QUAN,
                      ten_phuong: this.lay_danh_sach_phuong_xa.find(rs => rs.phuong_id === this.selectedWard).ten_phuong,
                      ten_pho: pho.ten_pho,
                      ten_ap: ap.ten_pho,
                      ten_khu: khu.ten_pho
                    })
                  }
                }
              } else {
                const khu_id = 0
                const ten_khu = ""
                if (this.kiemtra_trunglap(this.selectedDistrict, this.selectedWard, pho.pho_id, ap.pho_id, khu_id)) {
                  newListData.push({
                    khuvuc_id: this.khuVuc.khuvuc_id,
                    quan_id: this.selectedDistrict,
                    phuong_id: this.selectedWard,
                    pho_id: pho.pho_id,
                    ap_id: ap.pho_id,
                    khu_id: khu_id,
                    ten_quan: this.lay_danh_sach_quan_huyen.find(rs => rs.QUAN_ID === this.selectedDistrict).TEN_QUAN,
                    ten_phuong: this.lay_danh_sach_phuong_xa.find(rs => rs.phuong_id === this.selectedWard).ten_phuong,
                    ten_pho: pho.ten_pho,
                    ten_ap: ap.ten_pho,
                    ten_khu: ten_khu
                  })
                }
              }
            }
          } else {
            const ap_id = 0
            const ten_ap = ""

            if (this.dgvKhu_DaGan.dataSource.length !== 0) {
              for (let khu of this.dgvKhu_DaGan.dataSource) {
                if (this.kiemtra_trunglap(this.selectedDistrict, this.selectedWard, pho.pho_id, ap_id, khu.pho_id)) {
                  newListData.push({
                    khuvuc_id: this.khuVuc.khuvuc_id,
                    quan_id: this.selectedDistrict,
                    phuong_id: this.selectedWard,
                    pho_id: pho.pho_id,
                    ap_id: ap_id,
                    khu_id: khu.pho_id,
                    ten_quan: this.lay_danh_sach_quan_huyen.find(rs => rs.QUAN_ID === this.selectedDistrict).TEN_QUAN,
                    ten_phuong: this.lay_danh_sach_phuong_xa.find(rs => rs.phuong_id === this.selectedWard).ten_phuong,
                    ten_pho: pho.ten_pho,
                    ten_ap: ten_ap,
                    ten_khu: khu.ten_pho
                  })
                }
              }
            } else {
              const khu_id = 0
              const ten_khu = ""
              if (this.kiemtra_trunglap(this.selectedDistrict, this.selectedWard, pho.pho_id, ap_id, khu_id)) {
                newListData.push({
                  khuvuc_id: this.khuVuc.khuvuc_id,
                  quan_id: this.selectedDistrict,
                  phuong_id: this.selectedWard,
                  pho_id: pho.pho_id,
                  ap_id: ap_id,
                  khu_id: khu_id,
                  ten_quan: this.lay_danh_sach_quan_huyen.find(rs => rs.QUAN_ID === this.selectedDistrict).TEN_QUAN,
                  ten_phuong: this.lay_danh_sach_phuong_xa.find(rs => rs.phuong_id === this.selectedWard).ten_phuong,
                  ten_pho: pho.ten_pho,
                  ten_ap: ten_ap,
                  ten_khu: ten_khu
                })
              }
            }
          }
        }
      } else {
        const pho_id = 0
        const ten_pho = ""
        if (this.dgvAp_DaGan.dataSource.length !== 0) {
          for (let ap of this.dgvAp_DaGan.dataSource) {
            if (this.dgvKhu_DaGan.dataSource.length !== 0) {
              for (let khu of this.dgvKhu_DaGan.dataSource) {
                if (this.kiemtra_trunglap(this.selectedDistrict, this.selectedWard, pho_id, ap.pho_id, khu.pho_id)) {
                  newListData.push({
                    khuvuc_id: this.khuVuc.khuvuc_id,
                    quan_id: this.selectedDistrict,
                    phuong_id: this.selectedWard,
                    pho_id: pho_id,
                    ap_id: ap.pho_id,
                    khu_id: khu.pho_id,
                    ten_quan: this.lay_danh_sach_quan_huyen.find(rs => rs.QUAN_ID === this.selectedDistrict).TEN_QUAN,
                    ten_phuong: this.lay_danh_sach_phuong_xa.find(rs => rs.phuong_id === this.selectedWard).ten_phuong,
                    ten_pho: ten_pho,
                    ten_ap: ap.ten_pho,
                    ten_khu: khu.ten_pho
                  })
                }
              }
            } else {
              const khu_id = 0
              const ten_khu = ""
              if (this.kiemtra_trunglap(this.selectedDistrict, this.selectedWard, pho_id, ap.pho_id, khu_id)) {
                newListData.push({
                  khuvuc_id: this.khuVuc.khuvuc_id,
                  quan_id: this.selectedDistrict,
                  phuong_id: this.selectedWard,
                  pho_id: pho_id,
                  ap_id: ap.pho_id,
                  khu_id: khu_id,
                  ten_quan: this.lay_danh_sach_quan_huyen.find(rs => rs.QUAN_ID === this.selectedDistrict).TEN_QUAN,
                  ten_phuong: this.lay_danh_sach_phuong_xa.find(rs => rs.phuong_id === this.selectedWard).ten_phuong,
                  ten_pho: ten_pho,
                  ten_ap: ap.ten_pho,
                  ten_khu: ten_khu
                })
              }
            }
          }
        } else {
          const ap_id = 0
          const ten_ap = ""

          if (this.dgvKhu_DaGan.dataSource.length !== 0) {
            for (let khu of this.dgvKhu_DaGan.dataSource) {
              if (this.kiemtra_trunglap(this.selectedDistrict, this.selectedWard, pho_id, ap_id, khu.pho_id)) {
                newListData.push({
                  khuvuc_id: this.khuVuc.khuvuc_id,
                  quan_id: this.selectedDistrict,
                  phuong_id: this.selectedWard,
                  pho_id: pho_id,
                  ap_id: ap_id,
                  khu_id: khu.pho_id,
                  ten_quan: this.lay_danh_sach_quan_huyen.find(rs => rs.QUAN_ID === this.selectedDistrict).TEN_QUAN,
                  ten_phuong: this.lay_danh_sach_phuong_xa.find(rs => rs.phuong_id === this.selectedWard).ten_phuong,
                  ten_pho: ten_pho,
                  ten_ap: ten_ap,
                  ten_khu: khu.ten_pho
                })
              }
            }
          } else {
            const khu_id = 0
            const ten_khu = ""
            if (this.kiemtra_trunglap(this.selectedDistrict, this.selectedWard, pho_id, ap_id, khu_id)) {
              newListData.push({
                khuvuc_id: this.khuVuc.khuvuc_id,
                quan_id: this.selectedDistrict,
                phuong_id: this.selectedWard,
                pho_id: pho_id,
                ap_id: ap_id,
                khu_id: khu_id,
                ten_quan: this.lay_danh_sach_quan_huyen.find(rs => rs.QUAN_ID === this.selectedDistrict).TEN_QUAN,
                ten_phuong: this.lay_danh_sach_phuong_xa.find(rs => rs.phuong_id === this.selectedWard).ten_phuong,
                ten_pho: ten_pho,
                ten_ap: ten_ap,
                ten_khu: ten_khu
              })
            }
          }
        }
      }

      this.dgvDuLieuNap.dataSource = [...newListData, ...this.dgvDuLieuNap.dataSource]
    },
    kiemtra_trunglap (vquan, vphuong, vpho, vap, vkhu) {
      for (let i = 0; i < this.dgvDuLieuNap.dataSource.length; i++) {
        if (vquan === this.dgvDuLieuNap.dataSource[i].quan_id &&
            vphuong === this.dgvDuLieuNap.dataSource[i].phuong_id &&
            vpho === this.dgvDuLieuNap.dataSource[i].pho_id &&
            vap === this.dgvDuLieuNap.dataSource[i].ap_id &&
            vkhu === this.dgvDuLieuNap.dataSource[i].khu_id) {
          return false
        }
      }
      return true
    },
    tsbtnXoaDL_Nap_Click () {
      const data = this.$refs.loadDataGrid.getSelectedRecords()
      this.dgvDuLieuNap.dataSource = this.dgvDuLieuNap.dataSource.filter(item => !data.includes(item))
    },
    CapNhat () {
      // if (tsbtnNhapMoi.Enabled == false) {
      //   let khuvuccha_id = cboKhuVucCha.EditValue.ToString()
      //   // var lstRow = from DataRow dr in ds_kv.Tables[0].Rows
      //   //              where dr["KHUVUC_CHA_ID"].ToString() == khuvuccha_id
      //   //                  && dr["MA_KV"].ToString() == txtMa_KhuVuc.Text
      //   //              select dr;
      //   if (lstRow.Count() > 0) {
      //     Message_Box.ShowWarning("Mã khu vực đã tồn tại")
      //     return
      //   }

      //   TaoDuLieu(true)
      //   KhuVucKM.Insert(dsKhuVuc)
      //   KhuVucKM.CapNhat_KhuVuc_LKV(khuvuc_id, dsKhuvuc_lkv)
      //   KhuVucKM.CapNhat_KhuVuc_PX(khuvuc_id, dsKhuvuc_px)
      //   HienThiDanhSachKhuVuc()
      // } else {
      //   TaoDuLieu(false)
      //   KhuVucKM.Update(dsKhuVuc)
      //   KhuVucKM.CapNhat_KhuVuc_LKV(khuvuc_id, dsKhuvuc_lkv)
      //   KhuVucKM.CapNhat_KhuVuc_PX(khuvuc_id, dsKhuvuc_px)
      //   HienThiDanhSachKhuVuc()
      // }
      // SetButton(3)
    }
  },
  provide: {
    grid: [Filter, Page]
  },
  watch: {
    lay_ds_khuvuc_theo_diemchia: {
      handler (val) {
        if (val.length !== 0) {
          this.kvDaChon = val[0]
          this.khuVuc = this.$store.getters['siteArea/areaListFollowDividedPoint'].find(item => +item.khuvuc_id === +val[0].id)
        } else { this.kvDaChon = null }
      },
      deep: true
    },
    lay_danh_sach_loai_khu_vuc_da_gan: {
      handler (val) {
        this.dgvLoai_KV_DaGan.dataSource = val
      }
    },
    lay_danh_sach_loai_khu_vuc_chua_gan: {
      handler (val) {
        this.dgvLoai_KV_ChuaGan.dataSource = val
      }
    },
    lay_ds_pho: {
      handler (val) {
        this.dgvPho_DaGan.dataSource = []
        this.dgvPho_ChuaGan.dataSource = val
      }
    },
    lay_ds_ap: {
      handler (val) {
        this.dgvAp_DaGan.dataSource = []
        this.dgvAp_ChuaGan.dataSource = val
      }
    },
    lay_ds_khu: {
      handler (val) {
        this.dgvKhu_DaGan.dataSource = []
        this.dgvKhu_ChuaGan.dataSource = val
      }
    },
    lay_ds_du_lieu_nap: {
      handler (val) {
        this.dgvDuLieuNap.dataSource = val
      }
    }
  }
}
</script>
<style>
  .list-lydohuy{
    height: 140px;
  }
  .list-lydohuy .e-list-parent .e-list-item.e-selected{
    background-color: #bae7ff;
  }
  #thanhLy .gutter.gutter-horizontal{
    height: auto !important;
    margin: 0px 10px;
  }
</style>
