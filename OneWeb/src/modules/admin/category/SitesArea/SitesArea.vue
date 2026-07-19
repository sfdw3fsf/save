<template src='./SitesArea.html'></template>
<style scoped src='./SitesArea.scss'></style>
<script>
import { GridPlugin, VirtualScroll, Sort, Filter, Page, Selection, GridComponent } from '@syncfusion/ej2-vue-grids'
import breadcrumb from '@/components/breadcrumb'
import TreeView from './components/TreeView/TreeView.vue'
import api from "./SitesAreaAPI"
import DialogManagementUnit from './components/dialogManagementUnit.vue'
import DialogLookUpArea from './components/dialogLookUpArea.vue'
// import DialogMap from './components/dialogMap.vue'
import { NHOMPHO, SUCCESS_CODE } from "@/constants.js"

export default {
  props: ['ManagementArea', "title"],
  components: {
    breadcrumb,
    GridPlugin,
    VirtualScroll,
    Sort,
    Selection,
    GridComponent,
    TreeView,
    DialogManagementUnit,
    DialogLookUpArea,
    // DialogMap
  },
  name: 'SiteArea',
  data: () => {
    return {
      header: {
        title: 'Phân tập khách hàng b2a',
        list: [
          { name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
        ]
      },
      pageSettings: { pageSize: 5 },
      selectionOptions: { type: 'Multiple', checkBoxOnly: true },
      tsbtnNhapMoi: { Enabled: true, Visible: true },
      tsbtnGhiLai: { Enabled: true },
      tsbtnHuyBo: { Enabled: true },
      tsbtnXoa: { Enabled: true },
      tsbtnThoat: { Enabled: true },
      tsbtnNapDL: { Enabled: true },
      tsbtnXoaDL_Nap: { Enabled: true },
      tsbtnTraCuu: { Enabled: true },
      tsbtnBanDo: { Enabled: true },
      tsbGanDvi: { Enabled: true },
      txtMa_KhuVuc: { Text: '', Enabled: true },
      txtTen_KhuVuc: { Text: '', Enabled: true },
      txtKV: { Text: '', Enabled: true },
      txtGhiChu: { Text: '', Enabled: true },
      cboKhuVucCha: { Items: [], EditValue: null, Enabled: true },
      cboTinh: { Items: [], EditValue: null, Enabled: true },
      cboQuan: { Items: [], EditValue: null, Enabled: true },
      cboPhuong: { Items: [], EditValue: null, Enabled: true },
      dgvLoai_KV_DaGan: { DataSource: [], Selection: null },
      dgvLoai_KV_ChuaGan: { DataSource: [], Selection: null },
      dgvPho_ChuaGan: { DataSource: [], Selection: null },
      dgvAp_DaGan: { DataSource: [], Selection: null },
      dgvAp_ChuaGan: { DataSource: [], Selection: null },
      dgvKhu_DaGan: { DataSource: [], Selection: null },
      dgvKhu_ChuaGan: { DataSource: [], Selection: null },
      dgvDuLieuNap: { DataSource: [], Selection: null },
      dgvPho_DaGan: { DataSource: [], Selection: null },
      tvwKhuVuc: { DataSource: [], Selection: null },
      // -------------
      khuvuc_id: 0,
      khuvuc_id_tc: 0,
      KhuVucKM: [],
      fcdCommon: [],
      bangts: [],
      getkey: [],
      dsKhuVuc: [],
      dsKhuvuc_lkv: [],
      dsKhuvuc_px: [],
      idSelected: null,
      dtList: [],
      ds_pho_dagan: [],
      ds_pho_chuagan: [],
      ds_ap_chuagan: [],
      ds_ap_dagan: [],
      ds_khu_chuagan: [],
      ds_khu_dagan: [],
      ds_lkv_dagan: [],
      ds_lkv_chuagan: [],
      ds_khuvuc_px: [],
      ds_kv: [],
      currentFindIndex: 0
    }
  },

  computed: {
    lay_ds_khuvuc_theo_diemchia () {
      return this.buildTreeFromList(this.tvwKhuVuc.DataSource, 'ten_kv', 'khuvuc_id', 'khuvuc_cha_id')
    }
  },
  async mounted () {
    this.initSliter()
    await this.loadData()
    if (this.ManagementArea) {
      this.tsbtnNhapMoi.Visible = false
    }
  },
  methods: {
    initSliter () {
      window['Split'](['#boxLeft', '#boxRight'], {
        sizes: [18, 82],
        gutterSize: 16
      })
    },
    SetButton (kieu) {
      this.tsbtnNhapMoi.Enabled = false
      this.tsbtnGhiLai.Enabled = false
      this.tsbtnXoa.Enabled = false
      this.tsbtnHuyBo.Enabled = false

      if (kieu == -1) {
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
      }

      if (kieu == 0) {
        this.tsbtnNhapMoi.Enabled = true
        this.Clear()
      }

      if (kieu == 1) {
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
        this.Clear()
      }

      if (kieu == 2) {
        this.tsbtnNhapMoi.Enabled = true
        this.tsbtnXoa.Enabled = true
        this.Clear()
      }

      if (kieu == 3) {
        this.tsbtnNhapMoi.Enabled = true
        this.tsbtnXoa.Enabled = true
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
      }
    },
    Clear () {
      this.txtMa_KhuVuc.Text = ""
      this.txtTen_KhuVuc.Text = ""
      this.txtGhiChu.Text = ""
      this.khuvuc_id = 0
      this.dgvPho_ChuaGan.DataSource = []
      this.dgvPho_DaGan.DataSource = []
      this.dgvAp_ChuaGan.DataSource = []
      this.dgvAp_DaGan.DataSource = []
      this.dgvKhu_ChuaGan.DataSource = []
      this.dgvKhu_DaGan.DataSource = []
      this.ds_pho_dagan = []
      this.ds_pho_chuagan = []
      this.ds_ap_chuagan = []
      this.ds_ap_dagan = []
      this.ds_khu_chuagan = []
      this.ds_khu_dagan = []
      this.ds_lkv_dagan = []
      this.ds_lkv_chuagan = []
      this.ds_khuvuc_px = []
      this.dgvDuLieuNap.DataSource = []
      this.HT_LoaiKV(-1)
      this.cboQuan_EditValueChanged()
    },
    async loadData () {
      this.SetButton(-1)

      api.sp_lay_danh_sach_khu_vuc_cha().then(ds => {
        this.cboKhuVucCha.Items = ds
        if (this.cboKhuVucCha.Items.length > 0) {
          this.cboKhuVucCha.EditValue = this.cboKhuVucCha.Items[0]['khuvuc_id']
        }
      })
      api.lay_ds_tinh().then(ds => {
        this.cboTinh.Items = ds
        if (this.cboTinh.Items.length > 0) {
          this.cboTinh.EditValue = Number(this.$root.token.getPhanVungID())
          this.cboTinh_EditValueChanged()
        }
      })
      // api.lay_ds_quan().then(ds => {
      //   this.cboQuan.Items = ds
      //   if (this.cboQuan.Items.length > 0) {
      //     this.cboQuan.EditValue = this.cboQuan.Items[0]['QUAN_ID']
      //     this.cboQuan_EditValueChanged()
      //   }
      // })

      this.HienThiDanhSachKhuVuc()
      this.HT_LoaiKV(-1)
    },
    async HienThiDanhSachKhuVuc () {
      this.loading(true)
      this.ds_kv = await api.lay_ds_khuvuc_theo_diemchia(this.$auth.getNhanVienID())
      this.dtList = this.ds_kv
      this.tvwKhuVuc.DataSource = this.dtList
      this.cboKhuVucCha.Items = await api.sp_lay_danh_sach_khu_vuc_cha()
      this.loading(false)
    },
    async HT_LoaiKV (vkhuvuc_id) {
      if (vkhuvuc_id === -1) {
        this.dgvLoai_KV_DaGan.DataSource = []
        this.dgvLoai_KV_ChuaGan.DataSource = await api.sp_lay_danh_sach_loai_khu_vuc(vkhuvuc_id, 2)
        return
      }

      this.ds_lkv_dagan = await api.sp_lay_danh_sach_loai_khu_vuc(vkhuvuc_id, 1)
      if (this.ds_lkv_dagan.length > 0) {
        this.dgvLoai_KV_DaGan.DataSource = this.ds_lkv_dagan
      } else { this.dgvLoai_KV_DaGan.DataSource = [] }

      this.ds_lkv_chuagan = await api.sp_lay_danh_sach_loai_khu_vuc(vkhuvuc_id, 2)
      if (this.ds_lkv_chuagan.length > 0) {
        this.dgvLoai_KV_ChuaGan.DataSource = this.ds_lkv_chuagan
      } else { this.dgvLoai_KV_ChuaGan.DataSource = [] }
    },

    async tvwKhuVuc_AfterFocusNode (e) {
      const selectedNodeData = this.tvwKhuVuc.DataSource.find(i => i.khuvuc_id == e.khuvuc_id)
      e = selectedNodeData

      this.tvwKhuVuc.Selection = e.khuvuc_id
      if (this.tvwKhuVuc.Selection == null || this.tvwKhuVuc.Selection == undefined) {
        this.SetButton(0)
        return
      }
      if (e["khuvuc_id"] == null || e["khuvuc_id"] == undefined) {
        this.SetButton(0)
        return
      }
      this.khuvuc_id = parseInt(e["khuvuc_id"])

      this.txtGhiChu.Text = e["ghichu"]
      this.txtMa_KhuVuc.Text = e["ma_kv"]
      this.txtTen_KhuVuc.Text = e["ten_kv"]
      
      if (!e["khuvuc_cha_id"]) { this.cboKhuVucCha.EditValue = null } else { this.cboKhuVucCha.EditValue = e["khuvuc_cha_id"] }
      
      await this.HT_TT_NodeKhuVuc()
    },
    HT_TT_NodeKhuVuc: async function () {  
      this.ds_khuvuc_px = await api.lay_ds_phuong_xa(this.khuvuc_id)
      if (this.ds_khuvuc_px.length > 0) {
        this.dgvDuLieuNap.DataSource = this.ds_khuvuc_px
      } else { this.dgvDuLieuNap.DataSource = [] }

      this.HT_LoaiKV(this.khuvuc_id)
      this.cboQuan_EditValueChanged()
      this.SetButton(3)
    },
    async cboQuan_EditValueChanged () {
      if (!this.cboQuan.EditValue) return
      const quan_id = parseInt(this.cboQuan.EditValue)
      this.cboPhuong.Items = await api.lay_ds_phuong(quan_id)
      if (this.cboPhuong.Items.length > 0) {
        this.cboPhuong.EditValue = this.cboPhuong.Items[0]['PHUONG_ID']
        this.cboPhuong_EditValueChanged()
      }
    },
    async cboTinh_EditValueChanged () {
      if (!this.cboTinh.EditValue) return
      const tinh_id = parseInt(this.cboTinh.EditValue)
      this.cboQuan.Items = await api.lay_ds_quan(tinh_id)
      if (this.cboQuan.Items.length > 0) {
        this.cboQuan.EditValue = this.cboQuan.Items[0]['QUAN_ID']
        this.cboQuan_EditValueChanged()
      }
    },
    
    async cboPhuong_EditValueChanged () {
      if (!this.cboPhuong.EditValue) return

      const phuong_id = parseInt(this.cboPhuong.EditValue)

      this.dgvPho_ChuaGan.DataSource = []
      this.dgvPho_DaGan.DataSource = []
      this.ds_pho_chuagan = []
      this.ds_pho_dagan = []
      this.ds_pho_chuagan = await api.sp_lay_ds_pho(phuong_id, NHOMPHO.DUONGPHO)
      if (this.ds_pho_chuagan.length > 0) {
        this.dgvPho_ChuaGan.DataSource = this.ds_pho_chuagan
      }

      this.dgvAp_ChuaGan.DataSource = []
      this.dgvAp_DaGan.DataSource = []
      this.ds_ap_chuagan = []
      this.ds_ap_dagan = []
      this.ds_ap_chuagan = await api.sp_lay_ds_pho(phuong_id, NHOMPHO.AP)
      if (this.ds_ap_chuagan.length > 0) {
        this.dgvAp_ChuaGan.DataSource = this.ds_ap_chuagan
      }

      this.dgvKhu_ChuaGan.DataSource = []
      this.dgvKhu_DaGan.DataSource = []
      this.ds_khu_chuagan = []
      this.ds_khu_dagan = []
      this.ds_khu_chuagan = await api.sp_lay_ds_pho(phuong_id, NHOMPHO.KHU)
      if (this.ds_khu_chuagan.length > 0) {
        this.dgvKhu_ChuaGan.DataSource = this.ds_khu_chuagan
      }
    },
    tsbtnNhapMoi_Click () {
      this.SetButton(1)
    },
    tsbtnGhiLai_Click () {
      this.CapNhat()
    },
    tsbtnHuyBo_Click () {
      this.SetButton(0)
    },
    async tsbtnXoa_Click () {
      const cf = await this.$bvModal.msgBoxConfirm("Bạn thật sự muốn xóa dữ liệu không ?", {
        okTitle: 'Có',
        cancelTitle: 'Không'
      })
      if (!cf) return

      try {
        const rs = await api.xoa_khuvuc(this.khuvuc_id)
        if (rs.error_code != SUCCESS_CODE) {
          this.$toast.error(rs.message)
          return
        }
        this.$toast.success("Xóa thành công")
        this.Clear()
        this.HienThiDanhSachKhuVuc()
      } catch (err) {
        this.$toast.error(err)
      }
    },
    tsbtnXoaDL_Nap_Click () {
      const data = this.$refs.loadDataGrid.getSelectedRecords()

      if (data.length === 0) return
      this.dgvDuLieuNap.DataSource = this.dgvDuLieuNap.DataSource.filter(item => !data.includes(item))
    },
    transferToAssignArea: async function (isTransferAll) {
      this.$root.showLoading(true)
      try {
        if (isTransferAll) {
          this.dgvLoai_KV_DaGan.DataSource = [...this.dgvLoai_KV_ChuaGan.DataSource, ...this.dgvLoai_KV_DaGan.DataSource]
          this.dgvLoai_KV_ChuaGan.DataSource = []
        } else {
          const data = this.$refs.gridUnassigned.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvLoai_KV_ChuaGan.DataSource = this.dgvLoai_KV_ChuaGan.DataSource.filter(item => !data.includes(item))
            this.dgvLoai_KV_DaGan.DataSource = [...data, ...this.dgvLoai_KV_DaGan.DataSource]
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
          this.dgvLoai_KV_ChuaGan.DataSource = [...this.dgvLoai_KV_DaGan.DataSource, ...this.dgvLoai_KV_ChuaGan.DataSource]
          this.dgvLoai_KV_DaGan.DataSource = []
        } else {
          const data = this.$refs.gridAssigned.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvLoai_KV_DaGan.DataSource = this.dgvLoai_KV_DaGan.DataSource.filter(item => !data.includes(item))
            this.dgvLoai_KV_ChuaGan.DataSource = [...data, ...this.dgvLoai_KV_ChuaGan.DataSource]
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
          this.dgvPho_DaGan.DataSource = [...this.dgvPho_ChuaGan.DataSource, ...this.dgvPho_DaGan.DataSource]
          this.dgvPho_ChuaGan.DataSource = []
        } else {
          const data = this.$refs.unassignedTownGrid.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvPho_ChuaGan.DataSource = this.dgvPho_ChuaGan.DataSource.filter(item => !data.includes(item))
            this.dgvPho_DaGan.DataSource = [...data, ...this.dgvPho_DaGan.DataSource]
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
          this.dgvPho_ChuaGan.DataSource = [...this.dgvPho_DaGan.DataSource, ...this.dgvPho_ChuaGan.DataSource]
          this.dgvPho_DaGan.DataSource = []
        } else {
          const data = this.$refs.assignedTownGrid.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvPho_DaGan.DataSource = this.dgvPho_DaGan.DataSource.filter(item => !data.includes(item))
            this.dgvPho_ChuaGan.DataSource = [...data, ...this.dgvPho_ChuaGan.DataSource]
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
          this.dgvAp_DaGan.DataSource = [...this.dgvAp_ChuaGan.DataSource, ...this.dgvAp_DaGan.DataSource]
          this.dgvAp_ChuaGan.DataSource = []
        } else {
          const data = this.$refs.unassignedHamletGrid.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvAp_ChuaGan.DataSource = this.dgvAp_ChuaGan.DataSource.filter(item => !data.includes(item))
            this.dgvAp_DaGan.DataSource = [...data, ...this.dgvAp_DaGan.DataSource]
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
          this.dgvAp_ChuaGan.DataSource = [...this.dgvAp_DaGan.DataSource, ...this.dgvAp_ChuaGan.DataSource]
          this.dgvAp_DaGan.DataSource = []
        } else {
          const data = this.$refs.assignedHamletGrid.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvAp_DaGan.DataSource = this.dgvAp_DaGan.DataSource.filter(item => !data.includes(item))
            this.dgvAp_ChuaGan.DataSource = [...data, ...this.dgvAp_ChuaGan.DataSource]
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
          this.dgvKhu_DaGan.DataSource = [...this.dgvKhu_ChuaGan.DataSource, ...this.dgvKhu_DaGan.DataSource]
          this.dgvKhu_ChuaGan.DataSource = []
        } else {
          const data = this.$refs.unassignedAreaGrid.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvKhu_ChuaGan.DataSource = this.dgvKhu_ChuaGan.DataSource.filter(item => !data.includes(item))
            this.dgvKhu_DaGan.DataSource = [...data, ...this.dgvKhu_DaGan.DataSource]
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
          this.dgvKhu_ChuaGan.DataSource = [...this.dgvKhu_DaGan.DataSource, ...this.dgvKhu_ChuaGan.DataSource]
          this.dgvKhu_DaGan.DataSource = []
        } else {
          const data = this.$refs.assignedAreaGrid.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvKhu_DaGan.DataSource = this.dgvKhu_DaGan.DataSource.filter(item => !data.includes(item))
            this.dgvKhu_ChuaGan.DataSource = [...data, ...this.dgvKhu_ChuaGan.DataSource]
          }
        }
      } catch (error) {
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    async tsbtnNapDL_Click () {
      if (this.ds_khuvuc_px.length <= 0) {
        this.ds_khuvuc_px = []
      }

      if (this.dgvPho_DaGan.DataSource.length > 0 || this.dgvAp_DaGan.DataSource.length > 0 || this.dgvKhu_DaGan.DataSource.length > 0) {
        const tenQuan = this.cboQuan.Items.find(i => i.QUAN_ID === this.cboQuan.EditValue).TEN_QUAN
        const tenPhuong = this.cboPhuong.Items.find(i => i.PHUONG_ID === this.cboPhuong.EditValue).TEN_PHUONG

        let kiemtraTrungLap = false
        let khongTrungLap = true

        if (this.dgvPho_DaGan.DataSource.length > 0) {
          for (let i = 0; i < this.dgvPho_DaGan.DataSource.length; i++) {
            if (this.dgvAp_DaGan.DataSource.length > 0) {
              for (let j = 0; j < this.dgvAp_DaGan.DataSource.length; j++) {
                if (this.dgvKhu_DaGan.DataSource.length > 0) {
                  for (let k = 0; k < this.dgvKhu_DaGan.DataSource.length; k++) {
                    kiemtraTrungLap = this.kiemtra_trunglap(
                      this.cboQuan.EditValue,
                      this.cboPhuong.EditValue,
                      this.dgvPho_DaGan.DataSource[i].pho_id,
                      this.dgvAp_DaGan.DataSource[j].pho_id,
                      this.dgvKhu_DaGan.DataSource[k].pho_id)
                    khongTrungLap = khongTrungLap && kiemtraTrungLap
                    if (kiemtraTrungLap) {
                      this.ds_khuvuc_px.push({
                        "khuvuc_id": this.khuvuc_id,
                        "quan_id": this.cboQuan.EditValue,
                        "phuong_id": this.cboPhuong.EditValue,
                        "pho_id": this.dgvPho_DaGan.DataSource[i].pho_id,
                        "ap_id": this.dgvAp_DaGan.DataSource[j].pho_id,
                        "khu_id": this.dgvKhu_DaGan.DataSource[k].pho_id,
                        "ten_quan": tenQuan,
                        "ten_phuong": tenPhuong,
                        "ten_pho": this.dgvPho_DaGan.DataSource[i].ten_pho,
                        "ten_ap": this.dgvAp_DaGan.DataSource[j].ten_pho,
                        "ten_khu": this.dgvKhu_DaGan.DataSource[k].ten_pho
                      })
                    }
                  }
                } else {
                  kiemtraTrungLap = this.kiemtra_trunglap(
                    this.cboQuan.EditValue,
                    this.cboPhuong.EditValue,
                    this.dgvPho_DaGan.DataSource[i].pho_id,
                    this.dgvAp_DaGan.DataSource[j].pho_id,
                    0)
                  khongTrungLap = khongTrungLap && kiemtraTrungLap
                  if (kiemtraTrungLap) {
                    this.ds_khuvuc_px.push({
                      "khuvuc_id": this.khuvuc_id,
                      "quan_id": this.cboQuan.EditValue,
                      "phuong_id": this.cboPhuong.EditValue,
                      "pho_id": this.dgvPho_DaGan.DataSource[i].pho_id,
                      "ap_id": this.dgvAp_DaGan.DataSource[j].pho_id,
                      "khu_id": "0",
                      "ten_quan": tenQuan,
                      "ten_phuong": tenPhuong,
                      "ten_pho": this.dgvPho_DaGan.DataSource[i].ten_pho,
                      "ten_ap": this.dgvAp_DaGan.DataSource[j].ten_pho,
                      "ten_khu": ""
                    })
                  }
                }
              }
            } else {
              if (this.dgvKhu_DaGan.DataSource.length > 0) {
                for (let k = 0; k < this.dgvKhu_DaGan.DataSource.length; k++) {
                  kiemtraTrungLap = this.kiemtra_trunglap(
                    this.cboQuan.EditValue,
                    this.cboPhuong.EditValue,
                    this.dgvPho_DaGan.DataSource[i].pho_id,
                    0,
                    this.dgvKhu_DaGan.DataSource[k].pho_id)
                  khongTrungLap = khongTrungLap && kiemtraTrungLap
                  if (kiemtraTrungLap) {
                    this.ds_khuvuc_px.push({
                      "khuvuc_id": this.khuvuc_id,
                      "quan_id": this.cboQuan.EditValue,
                      "phuong_id": this.cboPhuong.EditValue,
                      "pho_id": this.dgvPho_DaGan.DataSource[i].pho_id,
                      "ap_id": "0",
                      "khu_id": this.dgvKhu_DaGan.DataSource[k].pho_id,
                      "ten_quan": tenQuan,
                      "ten_phuong": tenPhuong,
                      "ten_pho": this.dgvPho_DaGan.DataSource[i].ten_pho,
                      "ten_ap": "",
                      "ten_khu": this.dgvKhu_DaGan.DataSource[k].ten_pho
                    })
                  }
                }
              } else {
                kiemtraTrungLap = this.kiemtra_trunglap(
                  this.cboQuan.EditValue,
                  this.cboPhuong.EditValue,
                  this.dgvPho_DaGan.DataSource[i].pho_id,
                  0,
                  0)
                khongTrungLap = khongTrungLap && kiemtraTrungLap
                if (kiemtraTrungLap) {
                  this.ds_khuvuc_px.push({
                    "khuvuc_id": this.khuvuc_id,
                    "quan_id": this.cboQuan.EditValue,
                    "phuong_id": this.cboPhuong.EditValue,
                    "pho_id": this.dgvPho_DaGan.DataSource[i].pho_id,
                    "ap_id": "0",
                    "khu_id": "0",
                    "ten_quan": tenQuan,
                    "ten_phuong": tenPhuong,
                    "ten_pho": this.dgvPho_DaGan.DataSource[i].ten_pho,
                    "ten_ap": "",
                    "ten_khu": ""
                  })
                }
              }
            }
          }
        } else {
          if (this.dgvAp_DaGan.DataSource.length > 0) {
            for (let j = 0; j < this.dgvAp_DaGan.DataSource.length; j++) {
              if (this.dgvKhu_DaGan.DataSource.length > 0) {
                for (let k = 0; k < this.dgvKhu_DaGan.DataSource.length; k++) {
                  kiemtraTrungLap = this.kiemtra_trunglap(
                    this.cboQuan.EditValue,
                    this.cboPhuong.EditValue,
                    0,
                    this.dgvAp_DaGan.DataSource[j].pho_id,
                    this.dgvKhu_DaGan.DataSource[k].pho_id)
                  khongTrungLap = khongTrungLap && kiemtraTrungLap
                  if (kiemtraTrungLap) {
                    this.ds_khuvuc_px.push({
                      "khuvuc_id": this.khuvuc_id,
                      "quan_id": this.cboQuan.EditValue,
                      "phuong_id": this.cboPhuong.EditValue,
                      "pho_id": "0",
                      "ap_id": this.dgvAp_DaGan.DataSource[j].pho_id,
                      "khu_id": this.dgvKhu_DaGan.DataSource[k].pho_id,
                      "ten_quan": tenQuan,
                      "ten_phuong": tenPhuong,
                      "ten_pho": "",
                      "ten_ap": this.dgvAp_DaGan.DataSource[j].ten_pho,
                      "ten_khu": this.dgvKhu_DaGan.DataSource[k].ten_pho
                    })
                  }
                }
              } else {
                kiemtraTrungLap = this.kiemtra_trunglap(
                  this.cboQuan.EditValue,
                  this.cboPhuong.EditValue,
                  0,
                  this.dgvAp_DaGan.DataSource[j].pho_id,
                  0)
                khongTrungLap = khongTrungLap && kiemtraTrungLap
                if (kiemtraTrungLap) {
                  this.ds_khuvuc_px.push({
                    "khuvuc_id": this.khuvuc_id,
                    "quan_id": this.cboQuan.EditValue,
                    "phuong_id": this.cboPhuong.EditValue,
                    "pho_id": "0",
                    "ap_id": this.dgvAp_DaGan.DataSource[j].pho_id,
                    "khu_id": "0",
                    "ten_quan": tenQuan,
                    "ten_phuong": tenPhuong,
                    "ten_pho": "",
                    "ten_ap": this.dgvAp_DaGan.DataSource[j].ten_pho,
                    "ten_khu": ""
                  })
                }
              }
            }
          } else {
            if (this.dgvKhu_DaGan.DataSource.length > 0) {
              for (let k = 0; k < this.dgvKhu_DaGan.DataSource.length; k++) {
                kiemtraTrungLap = this.kiemtra_trunglap(
                  this.cboQuan.EditValue,
                  this.cboPhuong.EditValue,
                  0,
                  0,
                  this.dgvKhu_DaGan.DataSource[k].pho_id)
                khongTrungLap = khongTrungLap && kiemtraTrungLap
                if (kiemtraTrungLap) {
                  this.ds_khuvuc_px.push({
                    "khuvuc_id": this.khuvuc_id,
                    "quan_id": this.cboQuan.EditValue,
                    "phuong_id": this.cboPhuong.EditValue,
                    "pho_id": "0",
                    "ap_id": "0",
                    "khu_id": this.dgvKhu_DaGan.DataSource[k].pho_id,
                    "ten_quan": tenQuan,
                    "ten_phuong": tenPhuong,
                    "ten_pho": "",
                    "ten_ap": "",
                    "ten_khu": this.dgvKhu_DaGan.DataSource[k].ten_pho
                  })
                }
              }
            }
          }
        }

        if (!khongTrungLap) {
          this.$toast.error('Dữ liệu trùng lặp. Vui lòng kiểm tra lại.')
        }

        this.dgvDuLieuNap.DataSource = [...this.ds_khuvuc_px]
      }
    },
    isTextHasSpecialCharacter (text) {
      let pattern = /[^a-zA-Z0-9\.\-_]/
      return pattern.test(text)
    },
    async CapNhat () {
      if (!this.txtMa_KhuVuc.Text || this.txtMa_KhuVuc.Text.trim() == "") {
        this.$toast.error("Hãy nhập mã khu vực!")
        return
      } else {
        if (this.isTextHasSpecialCharacter(this.txtMa_KhuVuc.Text)) {
          this.$toast.error("Mã khu vực không bao gồm ký tự đặt biệt, khoảng trắng, ký tự có dấu!")
          return
        }
      }
      if (!this.txtTen_KhuVuc.Text || this.txtTen_KhuVuc.Text.trim() == "") {
        this.$toast.error("Hãy nhập tên khu vực!")
        return
      }
      if (!this.tsbtnNhapMoi.Enabled) {
        const existed = this.ds_kv.filter(r => r.ma_kv == this.txtMa_KhuVuc.Text && r.khuvuc_cha_id == this.cboKhuVucCha.EditValue)
        if (existed.length > 0) {
          this.$toast.error("Mã khu vực đã tồn tại")
          return
        }
        await this.TaoDuLieu(true)
        const rs = await api.sp_khuvuc_cap_nhat(
          1,
          this.dsKhuVuc,
          this.khuvuc_id,
          this.dsKhuvuc_lkv,
          this.dsKhuvuc_px
        )
        if (rs.error_code != SUCCESS_CODE) {
          this.$toast.error(rs.message)
        } else {
          await this.HienThiDanhSachKhuVuc()
          await this.HT_TT_NodeKhuVuc()
          this.$toast.success('Tạo thành công')
        }
      } else {
        await this.TaoDuLieu(false)
        const rs = await api.sp_khuvuc_cap_nhat(
          2,
          this.dsKhuVuc,
          this.khuvuc_id,
          this.dsKhuvuc_lkv,
          this.dsKhuvuc_px
        )
        if (rs.error_code != SUCCESS_CODE) {
          this.$toast.error(rs.message)
        } else {
          await this.HienThiDanhSachKhuVuc()
          await this.HT_TT_NodeKhuVuc()
          this.$toast.success('Cập nhật thành công')
        }
      }
      this.SetButton(3)
    },
    kiemtra_trunglap (vquan, vphuong, vpho, vap, vkhu) {
      for (let i = 0; i < this.dgvDuLieuNap.DataSource.length; i++) {
        if (+vquan === +this.dgvDuLieuNap.DataSource[i].quan_id &&
            +vphuong === +this.dgvDuLieuNap.DataSource[i].phuong_id &&
            +vpho === +this.dgvDuLieuNap.DataSource[i].pho_id &&
            +vap === +this.dgvDuLieuNap.DataSource[i].ap_id &&
            +vkhu === +this.dgvDuLieuNap.DataSource[i].khu_id) {
          return false
        }
      }
      return true
    },
    async TaoDuLieu (themmoi) {
      this.dsKhuVuc = []

      if (themmoi) { this.khuvuc_id = await api.get_keys('KHUVUC') }

      const rowdata = {
        khuvuc_id: this.khuvuc_id,
        ma_kv: this.txtMa_KhuVuc.Text,
        ten_kv: this.txtTen_KhuVuc.Text,
        ghichu: this.txtGhiChu.Text,
        khuvuc_cha_id: ''
      }

      if (this.cboKhuVucCha.EditValue != null || this.cboKhuVucCha.EditValue != undefined) { rowdata.khuvuc_cha_id = parseInt(this.cboKhuVucCha.EditValue) }

      this.dsKhuVuc.push(rowdata)
      if (themmoi == false) {
        rowdata.khuvuc_id = this.khuvuc_id
      }

      this.dsKhuvuc_lkv = []
      for (let i = 0; i < this.dgvLoai_KV_DaGan.DataSource.length; i++) {
        const row = {
          khuvuc_id: this.khuvuc_id,
          loaikv_id: this.dgvLoai_KV_DaGan.DataSource[i].LOAIKV_ID
        }

        this.dsKhuvuc_lkv.push(row)
      }

      this.dsKhuvuc_px = []
      for (let j = 0; j < this.dgvDuLieuNap.DataSource.length; j++) {
        const row_kvpx = {
          khuvuc_id: this.khuvuc_id,
          quan_id: this.dgvDuLieuNap.DataSource[j].quan_id,
          phuong_id: this.dgvDuLieuNap.DataSource[j].phuong_id,
          pho_id: this.dgvDuLieuNap.DataSource[j].pho_id,
          ap_id: this.dgvDuLieuNap.DataSource[j].ap_id,
          khu_id: this.dgvDuLieuNap.DataSource[j].khu_id,
          dacdiem_id: 0
        }
        this.dsKhuvuc_px.push(row_kvpx)
      }
    },
    tsbGanDvi_Click () {
      if (this.khuvuc_id) {
        this.$refs.dlgMngUnit.show(true, {
          in_khuvuc_id: this.khuvuc_id,
          ma_kv: this.txtMa_KhuVuc.Text,
          ten_kv: this.txtTen_KhuVuc.Text
        })
      } else { this.$toast.error('Hãy chọn khu vực!.') }
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
            ten_kv: (item[fieldName] === undefined || item[fieldName] === null) ? '' : item[fieldName].toString(),
            khuvuc_id: (item[fieldId] === undefined || item[fieldId] === null) ? '' : item[fieldId].toString(),
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
    tsbtnTraCuu_Click () {
      const f = this.$refs.dlgLookUpArea
      f.show(true)
      if (f.chapnhan) {
        this.khuvuc_id_tc = f.khuvuc_id
      }
    },
    txtKV_KeyPress (e) {
      if (e.key === "Enter") {
        try {
          const data = this.dtList.filter(item => item.ten_kv.toLowerCase().includes(this.txtKV.Text.toLowerCase()))
          if (data.length > 0) {
            this.tvwKhuVuc.DataSource = data
          } else {
            this.$toast.error('Không tìm thấy đơn vị phù hợp')
          }
        } catch (e) {
          this.$toast.error('Không tìm thấy đơn vị phù hợp')
        }
      }
    },
    tsbtnBanDo_Click () {
      // const f = this.$refs.dlgMap
      // f.show(true, { khuvuc_id: this.khuvuc_id })
    },
    onDialogLookupAccept (isAccept, data) {
      if (isAccept) {
        this.khuvuc_id_tc = data[0] ? data[0]['khuvuc_id'] : 0
        let area = this.tvwKhuVuc.DataSource.find(el => el.khuvuc_id == this.khuvuc_id_tc)
        if (area) {
          this.tvwKhuVuc = {...this.tvwKhuVuc, Selection: area.khuvuc_id}
        }
      }
    }
  },
  provide: {
    grid: [Filter, Page]
  }
}

</script>
