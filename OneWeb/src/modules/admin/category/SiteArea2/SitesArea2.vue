<template src='./SiteArea2.html'></template>
<style scoped src='./SitesArea.scss'></style>
<script>
import { GridPlugin, VirtualScroll, Sort, Filter, Page, Selection, GridComponent } from '@syncfusion/ej2-vue-grids'

import TreeView from './components/TreeView/TreeView.vue'
import api from "./SitesAreaAPI"
import DialogManagementUnit from './components/dialogManagementUnit.vue'
import DialogLookUpArea from './components/dialogLookUpArea.vue'
import DialogMap from './components/dialogMap.vue'
import { NHOMPHO, SUCCESS_CODE } from "@/constants.js"
import DrawAreaModal from './popups/DrawAreaModal'
export default {
  props: ['ManagementArea', "title"],
  components: {
    GridPlugin,
    VirtualScroll,
    Sort,
    Selection,
    GridComponent,
    TreeView,
    DialogManagementUnit,
    DialogLookUpArea,
    DialogMap,
    DrawAreaModal
  },
  name: 'SiteArea',
  data: () => {
    return {
      tt_nd: {},
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

      dgvPho_ChuaGan: { DataSource: [], 
        cols: [{fieldName:'ten_phuong',width: "150",headerText:'Phường', allowFiltering: true},
        {fieldName:'ten_pho',width: "150",headerText:'Pho', allowFiltering: true},
        {fieldName:'nhom_pho',width: "150",headerText:'Loai', allowFiltering: true}],
        Selection: [], 
      },
      dgvPho_DaGan: { DataSource: [], 
      cols: [{fieldName:'ten_phuong',width: "150",headerText:'Phường', allowFiltering: true},
      {fieldName:'ten_pho',width: "150",headerText:'Pho', allowFiltering: true},
      {fieldName:'nhom_pho',width: "150",headerText:'Loai', allowFiltering: true}],
      Selection: [], },
  
      dgvDacDiem_DaGan: { DataSource: [], 
        cols: [{fieldName:'ten_pho',width: "150",headerText:'Phố/Ấp/Khu', allowFiltering: true},
      {fieldName:'dacdiem',width: "150",headerText:'Đặc điểm', allowFiltering: true},
      ] },
      dgvDacDiem_ChuaGan: { DataSource: [],  
        cols: [{fieldName:'ten_pho',width: "150",headerText:'Phố/Ấp/Khu', allowFiltering: true},
      {fieldName:'dacdiem',width: "150",headerText:'Đặc điểm', allowFiltering: true},
      ] },
      dgvDuLieuNap: { DataSource: [], Selection: null, cols: [
        {fieldName:'ten_quan',width: "150",headerText:'Quận', allowFiltering: true},
        {fieldName:'ten_phuong',width: "150",headerText:'Phường', allowFiltering: true},
        {fieldName:'ten_pho',width: "150",headerText:'Phố', allowFiltering: true},        
        {fieldName:'dacdiem',width: "150",headerText:'Đặc điểm', allowFiltering: true},
        {fieldName:'ten_ap',width: "150",headerText:'Ấp', allowFiltering: true},
        {fieldName:'ten_khu',width: "150",headerText:'Khu', allowFiltering: true},
      ] },
      dgvDuLieuChoNap: { DataSource: [], cols: [
        {fieldName:'ten_pho',width: "150",headerText:'Phố', allowFiltering: true},
        {fieldName:'ten_phuong',width: "150",headerText:'Phường', allowFiltering: true},
        {fieldName:'dacdiem',width: "150",headerText:'Đặc điểm', allowFiltering: true},
        {fieldName:'ten_quan',width: "150",headerText:'Quận', allowFiltering: true},
        {fieldName:'nhom_pho',width: "150",headerText:'Loại', allowFiltering: true},
      ] },

      
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
  watch:{
    
    'dgvDacDiem_DaGan.DataSource'(val){
      if(!val) return;
      this.createDataWaiting(this.dgvPho_DaGan.DataSource, val)
      console.log("dgvDacDiem_DaGan=>", val)
    },
  'dgvPho_DaGan.DataSource'(val){
    if(!val) return;
    this.createDataWaiting(val, this.dgvDacDiem_DaGan.DataSource)
    console.log("dgvPho_DaGan=>", val)
  },

  },
  computed: {
    lay_ds_khuvuc_theo_diemchia () {
      return this.buildTreeFromList(this.tvwKhuVuc.DataSource, 'ten_kv', 'khuvuc_id', 'khuvuc_cha_id')
    }
  },
  async mounted () {
    this.tt_nd = await this.$root.token.getThongTinNguoiDung()
    await this.loadData()
    if (this.ManagementArea) {
      this.tsbtnNhapMoi.Visible = false
    }
  },
  methods: {
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
      this.dgvDacDiem_ChuaGan.DataSource = []
      this.dgvDacDiem_DaGan.DataSource = []
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
      this.dgvDuLieuChoNap.DataSource = []
      this.HT_LoaiKV(-1)
      this.cboQuan_EditValueChanged()
    },
    async loadData () {
      this.SetButton(-1)

      api.lay_ds_tinh().then(ds=>{
        this.cboTinh.Items=ds
        if (this.cboTinh.Items.length > 0) {
          this.cboTinh.EditValue = this.tt_nd.phanvung_id
        }
      })

      api.sp_lay_danh_sach_khu_vuc_cha().then(ds => {
        this.cboKhuVucCha.Items = ds
        if (this.cboKhuVucCha.Items.length > 0) {
          this.cboKhuVucCha.EditValue = this.cboKhuVucCha.Items[0]['khuvuc_id']
        }
      })

      api.lay_ds_quan(Number(this.tt_nd.phanvung_id)).then(ds => {
        this.cboQuan.Items = ds
        if (this.cboQuan.Items.length > 0) {
          this.cboQuan.EditValue = this.cboQuan.Items[0]['QUAN_ID']
          this.cboQuan_EditValueChanged()
        }
      })

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
      this.ds_khuvuc_px = await api.lay_ds_phuong_xa(this.khuvuc_id) //todo

      if (this.ds_khuvuc_px.length > 0) {
        this.dgvDuLieuNap.DataSource = this.ds_khuvuc_px
        this.$refs.loadDataGrid.setCurrentSelectedRow(-1);
      } else { this.dgvDuLieuNap.DataSource = [] }

      this.HT_LoaiKV(this.khuvuc_id)
      this.cboQuan_EditValueChanged()
      this.SetButton(3)
    },
    async cboTinh_EditValueChanged(){
      if(!this.cboTinh.EditValue) return

      this.cboQuan.Items =await api.lay_ds_quan(this.cboTinh.EditValue)
      if (this.cboQuan.Items.length > 0) {
        this.cboQuan.EditValue = this.cboQuan.Items[0]['QUAN_ID']
        this.cboQuan_EditValueChanged()
      }
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
    async cboPhuong_EditValueChanged () {
      if (!this.cboPhuong.EditValue) return

      const phuong_id = parseInt(this.cboPhuong.EditValue)

      this.dgvPho_ChuaGan.DataSource = []
      this.dgvPho_DaGan.DataSource = []
      this.ds_pho_chuagan = []
      this.ds_pho_dagan = []

      //get danh phố 
      this.ds_pho_chuagan  = await this.get_ds_pho_v3(phuong_id);
      // this.ds_pho_chuagan = await api.sp_lay_ds_pho(phuong_id, NHOMPHO.DUONGPHO)
      if (this.ds_pho_chuagan.length > 0) {
        this.dgvPho_ChuaGan.DataSource = this.ds_pho_chuagan
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

    //Pho chua an -
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
          let data = this.dgvPho_ChuaGan.Selection;
          
          if (data && data.length > 0) {
            this.dgvPho_ChuaGan.DataSource = this.dgvPho_ChuaGan.DataSource.filter(item => !data.includes(item))
            this.dgvPho_DaGan.DataSource = [...data, ...this.dgvPho_DaGan.DataSource];
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
          const data = this.dgvPho_DaGan.Selection
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

     transferToAssignProperties: async function (isTransferAll) {

      //check đã gán phố chưa
      if(this.dgvPho_DaGan.DataSource.length === 0){
        this.$toast.error('Bạn chưa gán phố')
        return ;
      }

      
     
      this.$root.showLoading(true);
      //lấy danh sách đặc điểm chưa gán được chọn
      const dt_dd =  this.$refs.unassignedPropertiesGrid.getSelectedRecords()

      try {
  
      //kiểm tra dữ liệu trùng lặp
       
      //get danh sách loại khu vực

      const resKv = await api.get_ds_khuvuc_loai_kv(this.khuvuc_id);
   
      const dsKv  = this.getData(resKv)

      //loại khu vực = 4
      if(dsKv.length  && dsKv[0] && dsKv[0]['kvid']){

      
     
     //kiem tra cac dia diem co bị trung lap du lieu khong
     let ktdl = 0;
     for(let j = 0; j  < dt_dd.length ; j ++){
         const dt = dt_dd[j]; 
         const isCheck = dt['ischeck']
         if(isCheck == 0){
           let {phuong_id, pho_id, dacdiem_id, nhompho_id  } = dt
           let ap_id = "0" ;  let khu_id = "0" ;

           if (nhompho_id == "1"){
             {
                   ap_id  = "0";
                   khu_id = "0";
               }
               if (nhompho_id == "2")
               {
                   ap_id = pho_id;
                   pho_id = "0";
                   khu_id = "0";
               }
               if (nhompho_id == "3")
               {
                   khu_id = pho_id;
                   pho_id  = "0";
                   ap_id = "0" ;
               }
           }
              
          const resCheck = await api.ktra_trunglap_frmkhuvuckm_v2({ vphuong_id: phuong_id
               , vpho_id: pho_id
               , vap_id: ap_id
               , vkhu_id: khu_id
               , vdacdiem_id: dacdiem_id
               } )
           if(resCheck === 1){
             
             const mes = `Dữ liệu đã tồn tại: + \n + 
             Quận: ${dt.ten_quan} \n 
             Phường:  ${dt.ten_phuong} \n 
             Phố/Ấp/Khu:  ${dt.ten_pho} \n 
             Đặc điểm:  ${dt.dacdiem} \n 
             Khu vực:  ${dt.ten_kv} \n`
             return  this.$toast.error(mes)
        
           }

     
         }
        }
      }else // loại khu vực != 4
      {
        for(let i = 0; i< dt_dd.length ; i++){
          let dulieu = dt_dd[i]['ischeck'];
          if(dulieu == '1'){
            let {phuong_id, pho_id, dacdiem_id, nhompho_id  } = dt
            let ap_id = "" ;  let khu_id = "" ;
            if (nhompho_id == "1"){
              {
                  ap_id ='0' 
                  khu_id = "0";
                }
                if (nhompho_id == "2")
                {
                    ap_id = pho_id;
                    pho_id = '0';
                    khu_id = "0";
                }
                if (nhompho_id == "3")
                {
                    khu_id = pho_id;
                    pho_id = '0';
                    ap_id = "0";
                }
            }
            //kiểm đặc điểm được gán có trùng lặp với dữ liệu nạp ko
            for(let n = 0; n < this.dgvDuLieuNap.DataSource.length; n ++){
                const item = this.dgvDuLieuNap.DataSource[n];
                let phuong_id_ds = item["phuong_id"] ;
                let pho_id_ds = item["phuong_id"] ;
                let ap_id_ds = item["ap_id"] ;
                let khu_id_ds = item["khu_id"] ;
                let dacdiem_ds = item["dacdiem_id"] ;
                if(phuong_id_ds == phuong_id && pho_id_ds == pho_id && ap_id_ds == ap_id && khu_id_ds == khu_id && dacdiem_ds == dacdiem_id){
                  ktdl = 1
                }
            }
            if (ktdl == 1)
              {
                return this.$toast.error("Một địa danh không thể gán hai lần vào địa bàn !!!");
              }
                 
          }
        }
      }
        
        if (isTransferAll) {
          this.dgvDacDiem_DaGan.DataSource = [...this.dgvDacDiem_ChuaGan.DataSource, ...this.dgvDacDiem_DaGan.DataSource]
          this.dgvDacDiem_ChuaGan.DataSource = []
        } else {
          const data = this.$refs.unassignedPropertiesGrid.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvDacDiem_ChuaGan.DataSource = this.dgvDacDiem_ChuaGan.DataSource.filter(item => !data.includes(item))
            this.dgvDacDiem_DaGan.DataSource = [...data, ...this.dgvDacDiem_DaGan.DataSource]
          }
        }
      } catch (error) {
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    transferToUnAssignProperties: async function (isTransferAll) {
      this.$root.showLoading(true)
      try {
        if (isTransferAll) {
          this.dgvDacDiem_ChuaGan.DataSource = [...this.dgvDacDiem_DaGan.DataSource, ...this.dgvDacDiem_ChuaGan.DataSource]
          this.dgvDacDiem_DaGan.DataSource = []
        } else {
          const data = this.$refs.assignedPropertiesGrid.getSelectedRecords()
          if (data && data.length > 0) {
            this.dgvDacDiem_DaGan.DataSource = this.dgvDacDiem_DaGan.DataSource.filter(item => !data.includes(item))
            this.dgvDacDiem_ChuaGan.DataSource = [...data, ...this.dgvDacDiem_ChuaGan.DataSource]
          }
        }
      } catch (error) {
        this.$toast.error(error.toString())
      }
      this.$root.showLoading(false)
    },
    async tsbtnNapDL_Click () {

      let ds_dlnap = []
        try{
       
        if(this.dgvDuLieuNap.DataSource.length == 0){
          const resKv = await api.lay_ds_khuvuc_px_v2(this.khuvuc_id)
          
          ds_dlnap = this.getData(resKv)

        }else{
          ds_dlnap = [...this.dgvDuLieuNap.DataSource];
        }

        let data = [... this.dgvDuLieuChoNap.DataSource];

        for(let i = 0; i < data.length; i++){
          const dl = data[i];
          console.log(dl)
          let pho_id = dl['pho_id'] || "";
          let ap_id = "";
          let khu_id = "";
          let ten_pho = dl['ten_pho'] || "";;
          let ten_ap = "";
          let ten_khu = "";
          let phuong_id = dl['phuong_id'] || "";;
          let {quan_id, ten_quan,ten_phuong , nhompho_id, dacdiem_id, dacdiem} = dl;
          if (nhompho_id == "1")
          {
              ap_id = 0;
              khu_id = "0";
              ten_ap ="";
              ten_khu = "";
          }
          if (nhompho_id == "2")
          {
              ap_id = pho_id;
              ten_ap = ten_pho;
              pho_id = "0";
              khu_id = "0";
              ten_pho = "";
              ten_khu = "";

          }
          if (nhompho_id == "3")
          {
              ten_khu = ten_pho;
              khu_id = pho_id;
              pho_id = ap_id = "0";
              ten_pho = ten_ap = "";
          }
          if (!dacdiem_id)
          {
              dacdiem_id = "0";
          }

          //push vao danh sach nap
          ds_dlnap.push({
            khuvuc_id: this.khuvuc_id,
            quan_id, phuong_id, pho_id, ap_id, khu_id, dacdiem_id,
            ten_quan, ten_phuong, ten_pho, ten_ap, ten_khu, dacdiem
          })
        }
        }catch(err){
          console.log(err)
        }
        
   
        this.dgvDuLieuNap.DataSource = [...ds_dlnap]; 
        debugger
        this.dgvDuLieuChoNap = [];
        this.dgvDacDiem_DaGan = [];
        this.dgvPho_DaGan.DataSource = [];
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
          dacdiem_id: this.dgvDuLieuNap.DataSource[j].dacdiem_id //hotfix
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
     this.$refs.drawAreaModal.showModal()
    },
    onDialogLookupAccept (isAccept, data) {
      if (isAccept) {
        this.khuvuc_id_tc = data[0] ? data[0]['khuvuc_id'] : 0
        let area = this.tvwKhuVuc.DataSource.find(el => el.khuvuc_id == this.khuvuc_id_tc)
        if (area) {
          this.tvwKhuVuc = {...this.tvwKhuVuc, Selection: area.khuvuc_id}
        }
      }
    },
   async handleOnchangeTownUnassignSelect(data){
    if(!data.length ) return;
    //gan cac item dc selected
    this.dgvPho_ChuaGan.Selection =  data;

    },
    async handleOnchangeTownAssignSelect(data){

      this.dgvDacDiem_ChuaGan.DataSource = [];
      if(!data.length ) return;
      //gan cac item dc selected
      this.dgvPho_DaGan.Selection =  data;

      for(let i = 0; i < data.length; i++){
        let item = data[i];
        if(item){
          const properties = await this.nap_ds_dacdiem_frm_khuvuckm_v2(item.phuong_id, item.pho_id)
          // console.log(properties);
          if(properties.length>0) 
            this.dgvDacDiem_ChuaGan.DataSource = [...this.dgvDacDiem_ChuaGan.DataSource, ...properties]
        }
      }
},
    //update  apiV2
   async get_ds_pho_v3(phuong_id){
      try{
          const response = await api.get_ds_pho_v3(phuong_id);
          return this.getData(response)
      }
      catch(err){
        console.log("xay ra loi: ", err )
      }
    },
    async get_ds_khuvuc_loai_kv(vkhuvuc_id){
    try{
          const response = await api.get_ds_khuvuc_loai_kv(vkhuvuc_id);
         return this.getData(response)
      }
      catch(err){

      }
  },

  async get_ds_khuvuc_loai_kv(vkhuvuc_id){
    try{
          const response = await api.get_ds_khuvuc_loai_kv(vkhuvuc_id);
          return this.getData(response)
      }
      catch(err){

      }
  },
  async ktra_trunglap_frmkhuvuckm_v2(){
    try{
        const params = { vphuong_id, vpho_id, vap_id, vkhu_id, vdacdiem_id }
        const response = await api.ktra_trunglap_frmkhuvuckm_v2(params);
        //to do
        const ar =  this.getData(response);
        if(ar.length >0) return 1;
       return 0;
      }
      catch(err){

      }
  },
  async nap_ds_dacdiem_frm_khuvuckm_v2(vds_phuong_id, vds_pho_id){
    try{
          const response = await api.nap_ds_dacdiem_frm_khuvuckm_v2(vds_phuong_id, vds_pho_id );
          return this.getData(response)
      }
      catch(err){

      }
  },

  //tao du lieu cho nap
  createDataWaiting(towns, properties){
    try{
      let data = []
        for (let i = 0; i < towns.length; i ++) {
         
            const town = towns[i]
  
            let ktra = 0;
            let nhompho = town['nhom_pho']
            let ten_phuong = town["ten_phuong"];
            let phuong_dt_phuong = town["phuong_id"];
            let ten_pho = town["ten_pho"];
            let pho_dt_pho = town["pho_id"];
            let quan_id = town["quan_id"];
            let ten_quan = town["ten_quan"];
            let nhompho_id = town["nhompho_id"];

            if(properties.length >0){
              for(let pro of properties ){

              let pho_dt_dacdiem = pro["pho_id"];
              let phuong_dt_dacdiem = pro['phuong_id'];

              if (pho_dt_pho == pho_dt_dacdiem && phuong_dt_phuong == phuong_dt_dacdiem)
              {
                  ktra = 1;
                  const dacdiem = pro["dacdiem"];
                  const dacdiem_id = pro["dacdiem_id"];
                  data.push(
                    {
                      dacdiem, dacdiem_id,ten_quan,nhompho_id,
                      ten_pho, quan_id, nhompho, ten_phuong,
                      phuong_id: phuong_dt_phuong, 
                      pho_id: pho_dt_dacdiem, //hotfix
                    }
                  ) 
              }

              }
            }
            
            if (ktra == 0)
            data.push({
                      dacdiem:"Không xác định" , dacdiem_id: "0",ten_quan,nhompho_id,
                      ten_pho, quan_id, nhompho, ten_phuong, phuong_id: phuong_dt_phuong,
                      pho_id: pho_dt_pho,  //hotfix
                    }) 
      }
      this.dgvDuLieuChoNap.DataSource = [...data];
    }
    catch(err){
      console.log("co loi xay ra: ", err)
    }
 

  },
  //get data from response
  getData(response) {
      if (response.data.error === 200 || response.data.error === '200') {
       
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },


  },
  provide: {
    grid: [Filter, Page]
  },

}

</script>
