<template>
<div class="tree-plus tree-icon ovauto" style="height: 750px;">
    <tree-view :options="tvwKhuVuc.DataSource" labelField="ten_kv" valueField="khuvuc_id"
                        @changeSelectedItem="tvwKhuVuc_AfterFocusNode" @isOpenChange="true" :selectedValue="tvwKhuVuc.Selection">
                    </tree-view>
</div>
</template>

<script>
import TreeView from '@/components/Shared/TreeView/TreeView.vue'
import api from "../SiteArea2/SiteAreaAPI"
import mixinStore from '../Store/mixin'

export default {
  components: {
    TreeView
  },
  mixins: [mixinStore],
  data: () => {
    return {
      pageSettings: { pageSize: 5 },
      selectionOptions: { type: 'Multiple', checkBoxOnly: true },
      tsbtnNhapMoi: { Enabled: true },
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
      ds_kv: []
    }
  },
  mounted () {
    this.loadData()
  },
  methods: {
    async loadData () {
      this.HienThiDanhSachKhuVuc()
    },
    async HienThiDanhSachKhuVuc () {
      this.loading(true)
      this.ds_kv = await api.lay_ds_khuvuc_theo_diemchia(3)
      this.dtList = this.ds_kv
      this.tvwKhuVuc.DataSource = this.dtList
      this.cboKhuVucCha.Items = await api.sp_lay_danh_sach_khu_vuc_cha()
      this.loading(false)
    }
  }
}

</script>
