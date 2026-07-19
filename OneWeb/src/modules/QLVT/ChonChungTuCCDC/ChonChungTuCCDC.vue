<template src='./ChonChungTuCCDC.html'></template>
<style scoped src='./ChonChungTuCCDC.scss'></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import ChonChungTuCCDCAPI from  '../api/ChonChungTuCCDCAPI'
import { getterName,actionName,statePropertyName,mutationName} from '../store/chonchungtuccdc'
import select2 from '@/components/Select2.vue'
import { Group, Page, Filter, Sort, Resize} from "@syncfusion/ej2-vue-grids";
import { Query,Predicate } from '@syncfusion/ej2-data';
import EventBus from '@/utils/eventBus'

export default {

  components: {breadcrumb, appSelect2: select2 },
  name: "ChonChungTuCCDC",
  mounted() {
  },
  provide: {
    grid: [ Group, Page,Filter,Sort,Resize ]
  },
  computed: {
    ...mapState("chonchungtuccdc", statePropertyName),
    ...mapGetters("chonchungtuccdc", getterName),
    p_loai_chungtu: {
      get() {
        return this.params.p_loai_chungtu
      },
      set(value) {
        return this.params.p_loai_chungtu = value
      }
    },
    p_tungay: {
      get() {
        return this.params.p_tungay
      },
      set(value) {
        return this.params.p_tungay = value
      }
    },
    p_denngay: {
      get() {
        return this.params.p_denngay
      },
      set(value) {
        return this.params.p_denngay = value
      }
    },
    p_checkall_phieu: {
      get() {
        return this.params.p_checkall_phieu
      },
      set(value) {
        return this.params.p_checkall_phieu = value
      }
    },
   
  },
  data() {
    return {
        header: {
            title: "DANH SÁCH CHỨNG TỪ",
            list: [
            { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
            {
                name: "Lắp đặt mới",
                link: { name: "Ui.buttons" },
            },
            ],
        },
        params:{
          p_loai_chungtu: "",
          p_tungay: new Date((new Date()).getTime() - 7*24*60*60*1000),
          p_denngay: new Date(),
          p_checkall_phieu: false,
        },

        gridVatTu: [],
        gridThietBi: [],
        gridChungTu: [],
        cboLoaiChungTu: [],

        ds_ncc: [],
        dt: [],
        dtChungTu: [],
        dtThietBi: [],
        dtVatTu: [],
        dsKhoNCC: [],
        dsKhoBH: [],

        // DSLoaiChungTu: [],
        // DSChungTuCCDC: [],
        // DSThietBi: [],
        // DSVatTu: [],
        tag: 0,

        vchungtu_id: "",
        sophieu: "",
        donvi_giao_id: -1,
        donvi_nhan_id: -1,
        kho_id: -1,
        // tag: 0,
        dsQuyen: [],
        tukho: 0,
        denkho: 0,
        vchungtu_ccdc: 0,
        
        XacNhan: false,
        Visible:{
          checkall_phieu: true,
          Panel1Collapsed: false,
          Panel2Collapsed: false,
        },
        LOAI_KHO: {
          KHO_NHA_CC: 2,
          KHO_BAOHANH: 6
        },
        dateConfig: {
          default: new Date(),
          max:  new Date(),
          format: 'dd/MM/yyyy',
          waterMark : 'Chọn ngày',
        },
        filterOptions: {enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true},
        selectionOptions: {
          type: 'Single'
        },
        templateOptions : {
          create: function (args) {
            if(args.column.field === 'STT'){
              return '<span class="fa input-icon-right"></span>'
            } 
            return '<div class="input-icon-right"><input id="' + args.column.field + '_filterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>';
          },
          write: function (args) {
          }
        },
        cRowSelected: function() {
          return {
            template : Vue.component('FirstColtemplate', {
              template : '<span class="fa"></span>'
            })  // end of template
          }
        },
        animationSettings: { effect: 'Zoom' },
        target: '.main-wrapper',
        intialRow: -1,
        format:{ format:'N3' , useGrouping: true},
        pageSettings: { pageSizes: ['10','20','50','100','All'], pageSize: 10 },
    };
  },
  methods: {
    ...mapActions("chonchungtuccdc", actionName),
    ...mapMutations("chonchungtuccdc", mutationName),
    ...mapMutations("capphatccdc", ["setStore_frmCapPhatCCDC"]),
    
    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    async Load_quyen(){
      this.dsQuyen = await getDSQuyenNguoiDung({nguoiDungId: this.$auth.getNguoiDungID()})
    },
    frmChonChungTu_CCDC(){
      this.Visible.ckbAllPhieu = true
      this.p_denngay = new Date()
      this.p_tungay = new Date((new Date()).getTime() - 7*24*60*60*1000)
      this.Visible.Panel1Collapsed =true
    },
    async frmChonChungTu_CCDC_2(bool = false){
      this.tag = 1
      this.Load_quyen()
      this.Visible.checkall_phieu = bool
      this.p_checkall_phieu = !bool
      this.p_denngay = new Date()
      this.p_tungay = new Date((new Date()).getTime() - 7*24*60*60*1000)

      this.Visible.Panel1Collapsed = bool
      this.Visible.Panel2Collapsed = !bool

    },
    async frmChonChungTu_CCDC_Load(){
      if (this.tag == 0){
        this.cboLoaiChungTu = await this.getDSLoaiChungTu()
        this.cboLoaiChungTu.length ?  this.p_loai_chungtu = this.cboLoaiChungTu[0].loaiCtId : ''
        await this.Load_ds_chungtu()
        await this.Load_ds_thietbi()
      }
      if (this.tag == 1){
        this.cboLoaiChungTu = await this.getDanhMucLoaiChungTu()
        this.cboLoaiChungTu.length ?  this.p_loai_chungtu = this.cboLoaiChungTu[0].loaiCtId : ''
        await this.Load_ds_chungtu()
        await this.Load_ds_thietbi()
      }
      
      
      
      
    },
    async Load_ds_chungtu(){
      // try {
        this.$root.showLoading(true)
        this.$refs.TableChungTu.grid.clearFiltering()
        this.$refs.TableChungTu.grid.refresh()
        this.vchungtu_id = 0

        this.dtChungTu = await this.getDSChungTuCCDC({
            tag: this.tag,
            params: {
              tatCa: this.p_checkall_phieu ? 1 : 0,
              donViId: this.$auth.getDonViID(),
              nhanVienId: this.$auth.getNhanVienID(),
              tuNgay: this.f_DateToString(this.p_tungay),
              denNgay: this.f_DateToString(this.p_denngay),
              loaiCT: parseInt(this.p_loai_chungtu)
            }
           })
        if (this.tag == 0){
          this.gridChungTu = this.dtChungTu
          await this.showData_gridChungTu()
        }
        
        if (this.tag == 1){
          // Combo kho giao
          this.dsKhoNCC = await this.getDSKho({loaikho_id: this.LOAI_KHO.KHO_NHA_CC,nhanvien_id: this.$auth.getNhanVienID()})
          if (!this.dsKhoNCC.length){
            this.$toast.error("Nhân viên chưa được phân quyền kho nhà cung cấp, liên hệ admin phân quyền! ")
            
            return
          }
          // this.dsKhoNCC = khoGiao;
          // Combo kho nhận
          this.dsKhoBH = await this.getDSKho({loaikho_id: this.LOAI_KHO.KHO_BAOHANH,nhanvien_id: this.$auth.getNhanVienID()})
          if(!dsKhoBH.length){
            this.$toast.error("Nhân viên chưa được phân quyền kho bảo hành, liên hệ admin phân quyền! ")
            return;
          }
          // dsKhoBH = khoNhan
          // this.setViewChungTu({dsKhoNCC:dsKhoNCC,dsKhoBH:dsKhoBH})
          var q = this.dtChungTu.filter(r=> this.dsKhoNCC.map(r1=>r1.khoId).includes(r.KHO_NHAN_ID) || this.dsKhoBH.map(r2=>r2.khoId).includes(r.KHO_GIAO_ID))
          if (q.length){
            this.gridChungTu = q
            this.showData_gridChungTu()
          }
          else{
            this.gridChungTu = []
          }
        }
        this.$root.showLoading(false)

      // } catch (error) {
      //   this.$toast.error("Có lỗi hàm lấy ds chứng từ")
      // }
    },
    async showData_gridChungTu(){
      this.gridChungTu.forEach(a=>{
        a.NGAY_CT ? a.NGAY_CT = moment(a.NGAY_CT,'DD/MM/YYYY hh:mm:ss').toISOString() : ""
        a.TIEN ? a.TIEN = parseInt(a.TIEN) : ""
      })
    },
    async Load_ds_thietbi(){
      // try {
        this.$root.showLoading(true)
        this.$refs.TableThietBi.grid.clearFiltering()
        this.$refs.TableThietBi.grid.refresh()

        if (this.tag == 0 && this.vchungtu_id != 0){
          this.dtThietBi = await this.getDSThietBi({chungtu_id: this.vchungtu_id})
          this.gridThietBi = this.dtThietBi
          await this.showData_gridThietBi()
        }
        else if (this.tag==0 && this.vchungtu_id==0){
          this.gridThietBi = []
        }
        else if(this.tag==1 && this.vchungtu_id!=0){
          this.gridVatTu =  await this.getDSVatTu({chungTuId: vchungtu_id})
        }
        else if (this.tag == 1 && this.vchungtu_id == 0){
          this.gridVatTu = []
        }
        this.$root.showLoading(false)
      // } catch (error) {
      //   this.$toast.error("Có lỗi hàm lấy ds thiết bị")
      // }
    },
    async showData_gridThietBi(){
      this.gridThietBi.forEach(a=>{
        a.SOLUONG = parseInt(a.SOLUONG) || 0;
        a.SL_XUAT = parseInt(a.SL_XUAT) || 0;
        a.DONGIA = parseInt(a.DONGIA) || 0;
        a.TIEN = parseInt(a.TIEN) || 0;
      })
    },
    tsbtnHoanThanh_Click(){
      
      if (this.tag == 0){
        let a = this.$refs.TableThietBi.getSelectedRecords()
        
        if (!a.length){
          this.$toast.error("Chưa chọn vật tư nào!")
          this.XacNhan = false
          return
        }
        // Kiểm tra
        this.dt = a
        let kt = this.dt.filter(p=> parseInt(p.SL_XUAT) > parseInt(p.SO_LUONG))
        if (kt.length){
          this.$toast.error("Không được xuất quá lượng tồn của vật tư !")
          this.XacNhan = false
          return
        }
        let kt_1 = this.dt.filter(p=> (p.SL_XUAT) <= 0)
        if (kt_1.length){
          this.$toast.error("Không thể xuất số lượng bằng 0!")
          this.XacNhan = false
          return
        }
        this.XacNhan = true

        if (this.dt.length > 0){
          for (let i = 0; i < this.dt.length; i++){
            this.dt[i].SOLUONG = this.dt[i].SL_XUAT
          }
          // this.setSoLuongDSThietBi(a)
        }
        // let value = {
        //       XacNhan: this.XacNhan,
        //       dt:  this.DSThietBi.filter((b,index)=>a.includes(index)),
        //       vchungtu_id: this.vchungtu_id,
        //       sophieu: this.sophieu,
        //       kho_id: this.kho_id,
        //       txtSoChungTu: true
        //     }
        // this.dt =JSON.parse(JSON.stringify(this.DSThietBi.filter((b,index)=>a.includes(index))))  
        // EventBus.$emit('XacNhan_frmChonChungTu', value)
        this.Close()
      }
      if (this.tag == 1 && this.dsQuyen.includes(99)){
          if(this.tukho != null && this.tukho.toString() != "0"  && this.denkho != null && this.denkho.toString() != "0" ){
            this.ds_ncc = this.gridVatTu
            this.XacNhan = true
            this.Close()
          }
        }
    },
    async gridviewChungTu_FocusedRowChanged(row){
      if (row.data){
        this.vchungtu_id = parseInt(row.data.CHUNGTU_ID)
        this.sophieu = row.data.SOPHIEU
        this.donvi_giao_id = parseInt(row.data.DONVI_GIAO_ID)
        this.donvi_nhan_id = parseInt(row.data.DONVI_NHAN_ID)
        this.kho_id = parseInt(row.data.KHO_NHAN_ID)
        this.tukho = parseInt(row.data.KHO_NHAN_ID)
        this.denkho = parseInt(row.data.KHO_GIAO_ID)
        await this.Load_ds_thietbi()
      }
      else{
        this.gridVatTu = []
        this.gridThietBi = []
        this.vchungtu_id = 0;
        this.sophieu = ""
        this.donvi_giao_id = 0
        this.donvi_nhan_id = 0;
        this.kho_id = 0;
        this.tukho = 0;
        this.denkho = 0;
      }
    },
    async tsbtnThoat_Click(){
      this.XacNhan = false
      await this.Close()
    },
    async btnTimKiem_Click(){
      await this.Load_ds_chungtu()
      await this.Load_ds_thietbi()
    },
    async dialogOpen () {
      this.clearData()
      await this.frmChonChungTu_CCDC_Load()
    },
    dialogClose(){

    },
    Open (){
      this.$refs.popup_chonchungtuccdc.show();
    },
    async Close () {
      await this.$parent.XacNhan_frmChonChungTu_CCDC()
      this.$refs.popup_chonchungtuccdc.hide();
    },
    clearData(){
      this.cboLoaiChungTu = []
      this.gridChungTu = []
      this.gridThietBi = []
      this.gridVatTu = []
      this.tag = 0
    },
    async edit_gridThietBi(args){
      let changes = this.$refs.TableThietBi.grid.getBatchChanges() 
      this.$refs.TableThietBi.grid.batchUpdate(changes)
    },
    async edit_gridVatTu(args){
      let changes = this.$refs.gridViewVatTu.grid.getBatchChanges() 
      this.$refs.gridViewVatTu.grid.batchUpdate(changes)
    }
  },
created: async function() {
    this.clearData();
    // await this.frmChonChungTu_CCDC_Load()
  },
  watch: {
    
  },
  destroyed () {
  }
};
</script>