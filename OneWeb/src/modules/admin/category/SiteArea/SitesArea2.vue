<template src='./SiteArea2.html'></template>
<style scoped src='./SiteArea.scss'></style>
<script>
import { GridPlugin, VirtualScroll, Sort, Filter, Page, Selection, GridComponent } from '@syncfusion/ej2-vue-grids'
import { TreeGridPlugin } from '@syncfusion/ej2-vue-treegrid'
import TreeView from './parts/tree/TreeView.vue'
import 'vue2-datepicker/index.css'
import { mixin } from '../Staff/mixins/index'
import api from "./SiteAreaAPI"
import { NHOMPHO } from "@/constants.js"

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
      txtKV: { Text: '', Enabled: true },
      txtTen_KhuVuc: { Text: '', Enabled: true },
      txtGhiChu: { Text: '', Enabled: true },
      cboKhuVucCha: { Items: [], EditValue: null, Enabled: true },
      cboQuan: { Items: [], EditValue: null, Enabled: true },
      cboPhuong: { Items: [], EditValue: null, Enabled: true },
      dgvLoai_KV_DaGan: { DataSource: [] },
      dgvLoai_KV_ChuaGan: { DataSource: [] },
      dgvPho_ChuaGan: { DataSource: [] },
      dgvAp_DaGan: { DataSource: [] },
      dgvAp_ChuaGan: { DataSource: [] },
      dgvKhu_DaGan: { DataSource: [] },
      dgvKhu_ChuaGan: { DataSource: [] },
      dgvDuLieuNap: { DataSource: [] },
      dgvPho_DaGan: { DataSource: [] },
      tvwKhuVuc: { DataSource: [], Selection: null },
      // -----------------

      khuvuc_id: null,
      khuvuc_id_tc: null,
      KhuVucKM: [],
      getkey: [],
      dsKhuVuc: [],
      dsKhuvuc_lkv: [],
      dsKhuvuc_px: [],
      idSelected: true,
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
      this.SetButton(-1)
      api.lay_ds_quan().then(rs => {
        this.cboQuan.Items = rs
      })

      this.HienThiDanhSachKhuVuc()
      this.HT_LoaiKV(-1)
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
    async HT_LoaiKV (vkhuvuc_id) {
      if (vkhuvuc_id) {
        this.dgvLoai_KV_DaGan.DataSource = []
        this.dgvLoai_KV_ChuaGan.DataSource = []
        return
      }

      this.ds_lkv_dagan = []
      this.ds_lkv_dagan = await api.sp_lay_danh_sach_loai_khu_vuc(vkhuvuc_id, 1)
      if (this.ds_lkv_dagan.length > 0) {
        this.dgvLoai_KV_DaGan.DataSource = this.ds_lkv_dagan
      } else { this.dgvLoai_KV_DaGan.DataSource = [] }

      this.ds_lkv_chuagan = []
      this.ds_lkv_chuagan = await api.sp_lay_danh_sach_loai_khu_vuc(vkhuvuc_id, 2)
      if (this.ds_lkv_chuagan.length > 0) {
        this.dgvLoai_KV_ChuaGan.DataSource = this.ds_lkv_chuagan
      } else { this.dgvLoai_KV_ChuaGan.DataSource = [] }
    },
    async cboQuan_EditValueChanged () {
      if (!this.cboQuan.EditValue || !this.cboQuan.EditValue) return
      const quan_id = parseInt(this.cboQuan.EditValue)
      this.cboPhuong.Items = await api.lay_ds_phuong(quan_id)
    },
    async cboPhuong_EditValueChanged () {
      if (!this.cboPhuong.EditValue || !this.cboPhuong.EditValue) return
      const phuong_id = parseInt(this.cboPhuong.EditValue)

      this.dgvPho_ChuaGan.DataSource = null
      this.dgvPho_DaGan.DataSource = null
      this.ds_pho_chuagan = []
      this.ds_pho_dagan = []
      this.ds_pho_chuagan = await api.sp_lay_ds_pho(phuong_id, NHOMPHO.DUONGPHO)
      if (this.ds_pho_chuagan.length > 0) {
        this.dgvPho_ChuaGan.DataSource = this.ds_pho_chuagan
      }

      this.dgvAp_ChuaGan.DataSource = null
      this.dgvAp_DaGan.DataSource = null
      this.ds_ap_chuagan = []
      this.ds_ap_dagan = []
      this.ds_ap_chuagan = await api.sp_lay_ds_pho(phuong_id, NHOMPHO.AP)
      if (this.ds_ap_chuagan.length > 0) {
        // this.dgvAp_ChuaGan.AutoGenerateColumns = false;
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
    async HienThiDanhSachKhuVuc () {
      this.loading(true)
      this.ds_kv = await api.lay_ds_khuvuc_theo_diemchia()
      this.dtList = this.ds_kv
      this.tvwKhuVuc.DataSource = this.dtList
      this.cboKhuVucCha.Items = await api.sp_lay_danh_sach_khu_vuc_cha()
      this.loading(false)
    },
    async tvwKhuVuc_AfterFocusNode (e) {
      const selectedNodeData = this.tvwKhuVuc.DataSource.find(i => i.khuvuc_id === e.id)
      if (!selectedNodeData) return

      e = selectedNodeData

      if (!this.tvwKhuVuc.Selection) {
        this.SetButton(0)
        return
      }
      // tvwKhuVuc.Selection[0].Tag
      if (!e["khuvuc_id"]) {
        this.SetButton(0)
        return
      }
      this.khuvuc_id = parseInt(e["khuvuc_id"])

      this.txtGhiChu.Text = e["ghichu"]
      this.txtMa_KhuVuc.Text = e["ma_kv"]
      this.txtTen_KhuVuc.Text = e["ten_kv"]

      if (!e["khuvuc_cha_id"]) { this.cboKhuVucCha.EditValue = null } else { this.cboKhuVucCha.EditValue = e["khuvuc_cha_id"] }

      this.ds_khuvuc_px = []
      this.ds_khuvuc_px = await api.lay_ds_phuong_xa(this.khuvuc_id)
      if (this.ds_khuvuc_px.length > 0) {
        this.dgvDuLieuNap.DataSource = this.ds_khuvuc_px
      } else { this.dgvDuLieuNap.DataSource = [] }

      this.HT_LoaiKV(this.khuvuc_id)
      this.cboQuan_EditValueChanged()
      this.SetButton(3)
    },
    tsbtnNhapMoi_Click () {
      this.SetButton(1)
    },
    tsbtnGhiLai_Click () {

    },
    tsbtnHuyBo_Click () {
      this.SetButton(0)
    },
    async tsbtnXoa_Click () {
      if (confirm("Bạn thật sự muốn xóa dữ liệu không ?")) return
      try {
        const rs = await api.xoa_khuvuc(this.khuvuc_id)
        if (rs.message != 'Success') {
          this.$toast.error(rs.message)
          return
        }

        this.HienThiDanhSachKhuVuc()
      } catch (err) {
        this.$toast.error(err)
      }
    },
     tsbtnNapDL_Click(object sender, EventArgs e)
        {
            try
            {
                Int32 vquan = Convert.ToInt32(cboQuan.EditValue);
                Int64 vphuong = Convert.ToInt64(cboPhuong.EditValue);
                string vpho = "";
                string vap = "";
                string vkhu = "";

                string tenquan = cboQuan.Text.ToString();
                string tenphuong = cboPhuong.Text.ToString();
                string tenpho = "";
                string tenap = "";
                string tenkhu = "";

                if (ds_khuvuc_px.Tables.Count <= 0)
                {
                    ds_khuvuc_px = new DataSet();
                    ds_khuvuc_px = KhuVucKM.Lay_DS_KhuVuc_PX(-1);
                }

                if (grvPho_DaGan.RowCount > 0 || grvAp_DaGan.RowCount > 0 || grvKhu_DaGan.RowCount > 0)
                {
                    if (grvPho_DaGan.RowCount > 0)
                    {
                        for (int i = 0; i < grvPho_DaGan.RowCount; i++)
                        {
                            vpho = grvPho_DaGan.GetRowCellValue(i, "PHO_ID").ToString();
                            tenpho = grvPho_DaGan.GetRowCellValue(i, "TEN_PHO").ToString();

                            if (grvAp_DaGan.RowCount > 0)
                            {
                                for (int j = 0; j < grvAp_DaGan.RowCount; j++)
                                {
                                    vap = grvAp_DaGan.GetRowCellValue(j, "PHO_ID").ToString();
                                    tenap = grvAp_DaGan.GetRowCellValue(j, "TEN_PHO").ToString();

                                    if (grvKhu_DaGan.RowCount > 0)
                                    {
                                        for (int k = 0; k < grvKhu_DaGan.RowCount; k++)
                                        {
                                            vkhu = grvKhu_DaGan.GetRowCellValue(k, "PHO_ID").ToString();
                                            tenkhu = grvKhu_DaGan.GetRowCellValue(k, "TEN_PHO").ToString();

                                            if (!kiemtra_trunglap(vquan, vphuong, vpho, vap, vkhu)) return;
                                            ds_khuvuc_px.Tables[0].Rows.Add(0, khuvuc_id, vquan, vphuong, vpho, vap, vkhu,
                                                                            0, tenquan, tenphuong, tenpho, tenap, tenkhu);
                                        }
                                    }
                                    else
                                    {
                                        vkhu = "0";
                                        tenkhu = "";

                                        if (!kiemtra_trunglap(vquan, vphuong, vpho, vap, vkhu)) return;
                                        ds_khuvuc_px.Tables[0].Rows.Add(0, khuvuc_id, vquan, vphuong, vpho, vap, vkhu,
                                                                        0, tenquan, tenphuong, tenpho, tenap, tenkhu);
                                    }
                                }
                            }
                            else
                            {
                                vap = "0";
                                tenap = "";

                                if (grvKhu_DaGan.RowCount > 0)
                                {
                                    for (int k = 0; k < grvKhu_DaGan.RowCount; k++)
                                    {
                                        vkhu = grvKhu_DaGan.GetRowCellValue(k, "PHO_ID").ToString();
                                        tenkhu = grvKhu_DaGan.GetRowCellValue(k, "TEN_PHO").ToString();

                                        if (!kiemtra_trunglap(vquan, vphuong, vpho, vap, vkhu)) return;
                                        ds_khuvuc_px.Tables[0].Rows.Add(0, khuvuc_id, vquan, vphuong, vpho, vap, vkhu,
                                                                        0, tenquan, tenphuong, tenpho, tenap, tenkhu);
                                    }
                                }
                                else
                                {
                                    vkhu = "0";
                                    tenkhu = "";

                                    if (!kiemtra_trunglap(vquan, vphuong, vpho, vap, vkhu)) return;
                                    ds_khuvuc_px.Tables[0].Rows.Add(0, khuvuc_id, vquan, vphuong, vpho, vap, vkhu,
                                                                    0, tenquan, tenphuong, tenpho, tenap, tenkhu);
                                }
                            }
                        }
                    }
                    else
                    {
                        vpho = "0";
                        tenpho = "";

                        if (grvAp_DaGan.RowCount > 0)
                        {
                            for (int j = 0; j < grvAp_DaGan.RowCount; j++)
                            {
                                vap = grvAp_DaGan.GetRowCellValue(j, "PHO_ID").ToString();
                                tenap = grvAp_DaGan.GetRowCellValue(j, "TEN_PHO").ToString();

                                if (grvKhu_DaGan.RowCount > 0)
                                {
                                    for (int k = 0; k < grvKhu_DaGan.RowCount; k++)
                                    {
                                        vkhu = grvKhu_DaGan.GetRowCellValue(k, "PHO_ID").ToString();
                                        tenkhu = grvKhu_DaGan.GetRowCellValue(k, "TEN_PHO").ToString();

                                        if (!kiemtra_trunglap(vquan, vphuong, vpho, vap, vkhu)) return;
                                        ds_khuvuc_px.Tables[0].Rows.Add(0, khuvuc_id, vquan, vphuong, vpho, vap, vkhu,
                                                                        0, tenquan, tenphuong, tenpho, tenap, tenkhu);
                                    }
                                }
                                else
                                {
                                    vkhu = "0";
                                    tenkhu = "";

                                    if (!kiemtra_trunglap(vquan, vphuong, vpho, vap, vkhu)) return;
                                    ds_khuvuc_px.Tables[0].Rows.Add(0, khuvuc_id, vquan, vphuong, vpho, vap, vkhu,
                                                                        0, tenquan, tenphuong, tenpho, tenap, tenkhu);
                                }
                            }
                        }
                        else
                        {
                            vap = "0";
                            tenap = "";

                            if (grvKhu_DaGan.RowCount > 0)
                            {
                                for (int k = 0; k < grvKhu_DaGan.RowCount; k++)
                                {
                                    vkhu = grvKhu_DaGan.GetRowCellValue(k, "PHO_ID").ToString();
                                    tenkhu = grvKhu_DaGan.GetRowCellValue(k, "TEN_PHO").ToString();

                                    if (!kiemtra_trunglap(vquan, vphuong, vpho, vap, vkhu)) return;
                                    ds_khuvuc_px.Tables[0].Rows.Add(0, khuvuc_id, vquan, vphuong, vpho, vap, vkhu,
                                                                        0, tenquan, tenphuong, tenpho, tenap, tenkhu);
                                }
                            }
                        }
                    }
                    DataTable dt_khuvuc_px = ds_khuvuc_px.Tables[0];
                    DataView view = new DataView(dt_khuvuc_px);
                    DataTable dt = view.ToTable(true, "SELECTED", "KHUVUC_ID", "QUAN_ID", "PHUONG_ID", "PHO_ID", "AP_ID", "KHU_ID", "TEN_QUAN", "TEN_PHUONG", "TEN_PHO", "TEN_AP", "TEN_KHU");
                    dt_khuvuc_px = dt;

                    dgvDuLieuNap.DataSource = dt_khuvuc_px;
                }
            }
            catch (Exception ex)
            {
                Message_Box.ShowError("Có lỗi : " + ex);
            }
        }
  },
  provide: {
    grid: [Filter, Page]
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
