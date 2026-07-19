<template src='./ChonNhanVienCCDC.html'></template>

<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import ChonNhanVienCCDCAPI from  '../api/ChonNhanVienCCDCAPI'
import { getterName,actionName,statePropertyName,mutationName} from '../store/chonnhanvienccdc'
import select2 from '@/components/Select2.vue'
import { Group, Page, Filter, Sort, Resize, Edit} from "@syncfusion/ej2-vue-grids";
import { Query,Predicate } from '@syncfusion/ej2-data';
import EventBus from '@/utils/eventBus'
import XLSX from "xlsx";

export default {

  components: { XLSX, breadcrumb, appSelect2: select2 },
  name: "ChonNhanVienCCDC",
  mounted() {
  },
  provide: {
    grid: [ Group, Page,Filter,Sort,Resize, Edit ]
  },
  computed: {
    ...mapState("chonnhanvienccdc", statePropertyName),
    ...mapGetters("chonnhanvienccdc", getterName),
    p_donvi_id: {
      get() {
        return this.params.p_donvi_id
      },
      set(value) {
        return this.params.p_donvi_id = value
      }
    },
    p_radio_pb: {
      get() {
        return this.params.p_radio_pb
      },
      set(value) {
        return this.params.p_radio_pb = value
      }
    },
    p_ngay_ct: {
      get() {
        return this.params.p_ngay_ct
      },
      set(value) {
        return this.params.p_ngay_ct = value
      }
    },
  },
  data() {
    return {
        header: {
            title: "LỰA CHỌN CCDC VỚI NHÂN VIÊN",
            list: [
            { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
            {
                name: "Lắp đặt mới",
                link: { name: "Ui.buttons" },
            },
            ],
        },
        params: {
          p_donvi_id: "",
          p_radio_pb: 1,
          p_ngay_ct: new Date(),

        },
        
        ds_VT: [],
        ds_nv: [], // DS nhân viên đã checked box
        ds_ccdc: [], // DS vật tư đã checked box
        ds_capphat:[],
        ds_luuCP: [],
        ds_serial: [], // danh sách serial nhân value từ frmCapPhatCCDC

        cboDenDonVi: [],
        ds_Excel: [],
        

        grcDanhSach: [], //grcDanhSach
        grcVatTu: [], // grcVatTu
        GridDS_SERIAL: [], //grcDS_SERIAL

        grcCCDC: [],

        

        dt_nv_grid: [], // danh sách tất cả nhân viên để tham chiếu đơn vị

        xacnhan: false,
        kieu_pb: 0,
        capnv: 0,
        ma_md: "",
        tusinh: 0,
        file: "",
        Visible: {
          splitContainer2: {
            Panel2Collapsed: false,
           }
        },
        

        animationSettings: { effect: 'Zoom' },
        target: '.main-wrapper',
        filterOptions: {enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true},
        format:{ format:'N3' , useGrouping: true},
        templateOptions : {
          create: function (args) {
            if(['STT','btnXoaVT','btnEditTbi'].includes(args.column.field)){
              return '<span class="fa input-icon-right"></span>'
            } 
            
            return '<div class="input-icon-right"><input id="' + args.column.field + '_filterBarcell" type="search" class="form-control"> <span class="icon nc-icon-outline ui-1_zoom"></span></div>';
          },
          write: function (args) {
          }
        },
        cRowSelected: function() {
          return {
            template : Vue.component('columntemplate', {
              template : '<span class="fa"></span>'
            })  // end of template
          }
        },

        iTemplate: function(e) {
                return {
                    template: Vue.component("itemTemplate", {
                      template: ` <div class="row">
                                    <div class="col-sm-2 border">
                                      <span>{{data.MA_DV}}</span>
                                    </div>
                                    <div class="col-sm-4 border">
                                      <span>{{data.TEN_DV}}</span>
                                    </div>
                                    <div class="col-sm-6 border">
                                      <span>{{data.TEN_DV_CHA}}</span>
                                    </div>
                                  </div>`,
                      data() {
                        return {
                          data: {}
                        };
                      }
                    })
                };
            },
        onFiltering: function(e) {
            var query = new Query();
            let predicate;  
            
            //frame the query based on search string with filter type.
            if (e.text !== ''){
              predicate  = new Predicate('MA_DV', 'Contains', e.text);
              predicate = predicate.or('TEN_DV', 'contains', e.text);
              query.where(predicate)
            }

            //pass the filter data source, filter query to updateData method.
            e.updateData(this.dataSource, query);
        },
        pageSettings: { pageSizes: ['10','20','50','100','All'], pageSize: 10 },

        grvDS_SERIAL_Columns: [
          {fieldName: 'CHON', headerText: 'Chọn', textAlign: 'Left', allowFiltering: true, template:function () {
            return {
                template: Vue.component('GanSeriTemplate', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnGanSeri_ButtonClick">
                                  <span class="act -ap  icon-chevron-thin-left text-white"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    methods:{
                      async btnGanSeri_ButtonClick(){
                        await this.$parent.$parent.$parent.$parent.btnGanSeri_ButtonClick(this.data)
                      }

                    }
                })
            }
        },width: '70'},
          {fieldName: 'SERIAL', headerText: 'Serial', textAlign: 'Left', allowFiltering: true},
        ],

        grcCCDC_Columns: [
          { allowEditing:false ,fieldName: 'btnXoaVT' ,width: '70', headerText: 'Xóa', textAlign: 'Center', allowFiltering: true, template: function () {
            return {
                template: Vue.component('XoaVTTemplate', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnXoaVT_ButtonClick">
                                  <span class="-ap icon-close"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    methods:{
                      async btnXoaVT_ButtonClick(){
                        await this.$parent.$parent.$parent.$parent.btnXoaVT_ButtonClick(this.data)
                      }

                    }
                })
            }
        }},
          { allowEditing:false ,fieldName: 'MA_VT' , headerText: 'Mã vật tư', textAlign: 'Left', allowFiltering: true,},
          { allowEditing:false ,fieldName: 'TEN_VT' , headerText: 'Tên vật tư', textAlign: 'Left', allowFiltering: true,},
          { allowEditing:false ,fieldName: 'DONVI_NHAN'  , headerText: 'Đến đơn vị', textAlign: 'Left', allowFiltering: true,},
          
          { allowEditing:false ,fieldName: 'NHANVIEN_NHAN' , headerText: 'Đến nhân viên', textAlign: 'Left', allowFiltering: true,},
          { allowEditing:false ,fieldName: 'DVI_TINH' , headerText: 'ĐVT', textAlign: 'Left', allowFiltering: true,},
          { allowEditing:true ,fieldName: 'SERIAL' , headerText: 'SERIAL', textAlign: 'Left', allowFiltering: true,},
          { allowEditing:true ,fieldName: 'MA_TBI' , headerText: 'Tài sản cố định', textAlign: 'Left', allowFiltering: true,},

          { allowEditing:false ,fieldName: 'DONGIA' , headerText: 'Đơn giá', textAlign: 'Right', allowFiltering: true,},
          { allowEditing:true ,fieldName: 'SOTHANG_PB' , headerText: 'Phân bổ(tháng)' , textAlign: 'Right', allowFiltering: true,},
          { allowEditing:false ,fieldName: 'TIEN_PB' , headerText: 'Tiền phân bổ', textAlign: 'Right', allowFiltering: true,},
          { allowEditing:true ,fieldName: 'THANG_BD' , headerText: 'Từ tháng', textAlign: 'Left', allowFiltering: true,},

          { allowEditing:false ,fieldName: 'SOLUONG' , headerText: 'Số lượng', textAlign: 'Right', allowFiltering: true,},
          { allowEditing:false ,fieldName: 'THANG_KT' , headerText: 'Đến tháng', textAlign: 'Left', allowFiltering: true,},
          { allowEditing:false ,fieldName: 'LOAI_VT' , headerText: 'Loại vật tư', textAlign: 'Left', allowFiltering: true,},
          { allowEditing:false ,fieldName: 'GHICHU' , headerText: 'Vị trí đặt', textAlign: 'Left', allowFiltering: true,},

          { allowEditing:false ,fieldName: 'LOAI_TBI' , headerText: 'Loại Tbị', textAlign: 'Left', allowFiltering: true,},
          { allowEditing:true ,fieldName: 'TINHTRANGTBI_ID' , headerText: 'Tình trạng' , textAlign: 'Left', allowFiltering: true,},
          { allowEditing:false ,fieldName: 'THIETBI_ID' , headerText: 'THIETBI_ID' , textAlign: 'Left', allowFiltering: true,isPrimaryKey:true,visible: false},

          { allowEditing:false ,fieldName: 'btnGoSeri', width: '70', headerText: 'Xóa Sr', textAlign: 'Left', allowFiltering: true, template: function () {
            return {
                template: Vue.component('GoSeriTemplate', {
                    template: `<button class="btn btn-main pad4 lh14 w60 btn-primary" @click="btnGoSeri_ButtonClick">
                                  <span class="act -ap  icon-chevron-thin-right text-white"></span>
                              </button>`,
                    data: function () { return { data: {} }; },
                    methods:{
                      async btnGoSeri_ButtonClick(){
                        await this.$parent.$parent.$parent.$parent.btnGoSeri_ButtonClick(this.data)
                      }

                    }
                })
            }
        }},
          
          
          
        ],


        arrayImportFile: {
          filexcel:null,
          ok: false,
          dsImportExcel: [],
          dataExcel: [],
          ds_file: []
        },
        TINHTRANGTBI_Params: {
          params:   {
            allowFiltering: true,
            dataSource: [],
            fields: {text:"tinhTrangTBi",value:"tinhTrangTBiId"},
            query: new Query(),
            actionComplete: () => false,
          },
          // read: function() {
          //       return this.obj.typedString || '';
          //     },
        },
        cboTrangThaiFilter: [],
        grvCCDCFocusedRow: {},
        grvCCDC_rowIndex: 0,
        // TableCCDCPage : {
        //   page: 0,
        //   maxSize: 10,
        //   totalElement: 0,
        //   totalPages: 0,
        //   sort: null,
        //   propertiesSort: null,
        //   currentRow: 0
        // },

        
        
    };
  },
  methods: {
    ...mapActions("chonnhanvienccdc", actionName),
    ...mapMutations("chonnhanvienccdc", mutationName),
    
    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    f_DateParse(){
      try {
        moment(value,format).toDate()
        return true  
      } catch (error) {
        return false
      }
    },
    f_AddMonth(value,number,format){
      return moment(value).add(number,'months').format(format)
    },
    // TableCCDCPageHandler(e) {
    //   this.TableCCDCPage.page = e.pageIndex
    //   this.TableCCDCPage.maxSize = e.pageSize
    // },
    covertParseInt_grcCCDC(){
      this.grcCCDC.forEach(a=>{
        a.DONGIA ? a.DONGIA = parseInt(a.DONGIA) || 0 : ''
        a.SOTHANG_PB ? a.SOTHANG_PB = parseInt(a.SOTHANG_PB) || 0 : ''
        a.TIEN_PB ? a.TIEN_PB = parseInt(a.TIEN_PB) || 0 : ''
        a.SOLUONG ? a.SOLUONG = parseInt(a.SOLUONG) || 0 : ''
        // a.TINHTRANGTBI_ID ? a.TINHTRANGTBI = this.cboTrangThaiFilter.find(b=>b.tinhTrangTBiId == a.TINHTRANGTBI_ID).tinhTrangTBi : ''
      })
    },
    dialogOpen(){

    },
    dialogClose(){

    },
    // resetParams(){
      // this.grcVatTu = []
      // this.ds_luuCP = []
      // this.ds_capphat = []
      // this.GridDS_SERIAL = []
      // this.ds_serial = []
      // this.grcDanhSach = []
    // },
    async ShowDialog(){
      // this.resetParams()
      await this.frmChonNV_CCDC_Load()
      this.$refs.popup_chonnhanvienccdc.show();
    },
    async Close(){
      
      await this.$parent.XacNhan_frmChonNV_CCDC()
      this.$refs.popup_chonnhanvienccdc.hide();
    },
    async frmChonNV_CCDC(){
      this.ds_VT = []
      this.ds_nv = []
      this.ds_ccdc = []
      this.ds_capphat = []
    },
    async NAP_COMBO(){
      // try {
        
        this.cboDenDonVi = await this.getDanhMucDonVi({nhanVienId: this.$auth.getNhanVienID()})
        // await this.getDSTinhTrangTBi() 
        // lay tong ds nhan vien 1 lan trong ham load// quy optimize
        this.dt_nv_grid = await this.getDSNhanVien()
        
        this.p_donvi_id = this.cboDenDonVi.length ? this.cboDenDonVi[0].DONVI_ID : ''
        await this.cboDenDonVi_EditValueChanged(this.cboDenDonVi.length && this.cboDenDonVi[0] || '')

        this.cboTrangThaiFilter = await this.getDSTinhTrangTBi()
        this.TINHTRANGTBI_Params.params.dataSource = this.cboTrangThaiFilter
      // } catch (error) {
      //   this.$toast.error("Lỗi hàm nạp combo ")
      // }
    },
    async NAP_DS_VATTU(){
      // try {
        this.grcVatTu = this.ds_VT.filter(a=> parseInt(a.SL_TREO) > 0 )
        this.grcVatTu.forEach(a=>{
          a.SOLUONG = parseInt(a.SOLUONG) || 0;
          a.SL_XUAT = parseInt(a.SL_XUAT) || 0;
          a.DONGIA = parseInt(a.DONGIA) || 0;
          a.TIEN = parseInt(a.TIEN) || 0;
        })
        // if(!this.grcVatTu.length){
        //   this.$toast.error("Chưa có nhân viên nào được chọn")
        // }
      // } catch (error) {
      //   this.$toast.error("Lỗi khi nạp danh sách CCDC")
      // }
    },
    async NAP_DS_NV_CHON(){
      // try {
        this.ds_nv =  this.$refs.TableDanhSach.getSelectedRecords()
        if(!this.ds_nv.length){
          this.$toast.error("Chưa có nhân viên nào được chọn")
        }
      // } catch (error) {
      //   this.$toast.error("Lỗi hàm lấy danh sách nhân viên chọn")
      // } 
    },
    async NAP_DS_CCDC_CHON(){
      // try {
        this.ds_ccdc = this.$refs.grvVatTu.getSelectedRecords()
        if (!this.ds_ccdc.length){
          this.$toast.error("Chưa có vật tư nào được chọn")
        }
      // } catch (error) {
      //   this.$toast.error("Lỗi hàm lấy danh sách vật tư chọn")
      // }
    },
    async KIEMTRA_DULIEU(){
      if (!this.grcCCDC.length){
        this.$toast.error("Chưa có danh sách CCDC được cấp cho nhân viên")
        return false
      }
      let KT = this.grcCCDC
      
      let q = KT.filter(a=> parseInt(a.SOTHANG_PB) <= 0 )
      if (q.length){
        this.$toast.error("Số tháng phân bổ phải > 0. Bạn hãy kiểm trả lại danh sách")
        return false
      }

      let kt_tt = KT.filter(a=> a.TINHTRANGTBI_ID == null || a.TINHTRANGTBI_ID == '' )
      if (kt_tt.length){
        this.$toast.error("Có vật tư chưa có tình trạng thiết bị. Bạn hãy kiểm tra lại danh sách")
        return false
      }

      if(this.grcCCDC.length){
        let c = this.grcCCDC
        for(let i = 0; i< c.length;i++){
          let d_rnv = c[i]
          for(let j=i+1; j< c ;j++){
            let d_r = c[j]
            
            if(d_r.NHANVIEN_ID.toString() == d_rnv.NHANVIEN_ID.toString()
              && d_r.DONVI_ID.toString() == d_rnv.DONVI_ID.toString()
              && d_r.MA_VT.toString() == d_rnv.MA_VT.toString()
              && d_r.LOAITBI_ID.toString() != "2"
              && d_r.TINHTRANGTBI_ID.toString() == d_rnv.TINHTRANGTBI_ID.toString()
            ){
              this.$toast.error("Nhân viên " + d_r.NHANVIEN_NHAN.ToString() + " , Đơn vị " + 
              this.cboDenDonVi.find(a=>a.DONVI_ID == this.p_donvi_id)
              + " được cấp quá số lượng. Nếu bạn muốn cấp nhiều hơn hay xóa đi và sửa số lượng xuất"
              )
              return false
            }
          }
        }
      }

      if(this.grcVatTu.length){
        for(let item of this.grcVatTu){
          // need check again
          let q_t = this.grcCCDC
          let q_K = q_t.filter(a=>a.VATTU_ID.toString() == item.VATTU_ID.toString()).reduce((sum,b)=>sum + parseInt(b.SOLUONG),0)
          if(q_K){
            let sl_cl = parseInt(item.SOLUONG) - parseInt(q_K)
            
            if (sl_cl > 0){
              this.$toast.error("Số lượng của vật tư chưa được cấp đủ so với số nhân viên bạn chọn")
              return false
            }
          }
        }
      }

      this.tusinh = 0
      if (!this.Visible.splitContainer2.Panel2Collapsed){
        let kt_ltb = KT.filter(a=>a.LOAITBI_ID.toString() == "2" && (a.SERIAL == '' || a.SERIAL == null))
        if (kt_ltb.length){
          this.$toast("Vật tư của bạn có quản lý theo serial.Bạn hãy điền đẩy đủ serial với vật tư đó")
          return false
        }
      }
      if (this.splitContainer2){
        let kt_ltb2 = KT.filter(a=>a.LOAITBI_ID.toString() == 2 && (a.SERIAL == '' || a.SERIAL == null))
        if (kt_ltb2.length){
          await this.$bvModal.msgBoxConfirm('Vật tư của bạn có quản lý theo serial.Nếu bạn để trống serial sẽ tự sinh', {
            "title": '',
            "size": 'lg',
            "buttonSize": 'lg',
            "okVariant": 'danger',
            "okTitle": 'Đồng ý',
            "cancelTitle": 'Hủy',
            "hideHeaderClose": false,
            "centered": true,
            "modal-class": ["f18","text-center"],
          })
          .then(async(value) => {
            if (value){
              this.tusinh = 1
            }
            else{
              this.tusinh = 0
            }
          })
          .catch(err => {
            // An error occurred
          })
          
          // quy edit
          if (this.tusinh == 0){
            return false
          }
        }
      }
      return true
    },

    async LAY_TT_EXCEL(ma_nv,ma_dv,ma_vt){
      let vnv_id = 0
      let vdv_id = 0
      let vvattu_id = 0

      let vten_nv = ""
      let vten_dv = ""

      let dv = await this.getThongTinDonVi({ma_dv: ma_dv})
      let nv = await this.getThongTinNhanVien({ma_nv: ma_nv})
      let vt = await this.getThongTinVatTu({ma_vt: ma_vt, type: 'vt'}) // chả hiểu, API có

      if (dv.length && nv.length && vt.length){
        vnv_id = nv[0].nhanVienId
        vten_nv = nv[0].tenNV

        vdv_id = dv[0].donViId
        vten_dv = dv[0].tenDV

        vvattu_id = vt[0].vatTuId
      }

      return { vvattu_id, vdv_id, vten_dv, vnv_id, vten_nv }

    },
    async CAP_NHAT_TBI_EXCEL(ds){
      // try {
        let ds_temp = []
        
        for (let item of ds){
          // let item = ds[i]
          // k là serial
          let { vvattu_id, vdv_id, vten_dv, vnv_id, vten_nv } = await this.LAY_TT_EXCEL(item.MA_NVIEN_NHAN, item.MA_DONVI_NHAN, item.MA_VT)

          if (item.LOAITBI_ID.toString() != "2"){
            let row = {}
            row.THIETBI_ID = vvattu_id
            row.DONVI_ID = vdv_id
            row.DONVI_NHAN = vten_dv
            row.NHANVIEN_ID = vnv_id
            row.NHANVIEN_NHAN = vten_nv
            row.VATTU_ID = vvattu_id
            row.MA_VT = item.MA_VT.toString()
            row.TEN_VT = item.TEN_VT.toString()
            row.DVI_TINH = item.DVI_TINH.toString()
            row.SOLUONG = parseInt(item.SL_XUAT)
            row.DONGIA = parseInt(item.DONGIA)
            var tien = parseInt(item.SL_XUAT) * parseInt(item.DONGIA)

            if (parseInt(item.SOTHANG_PB) > 0){
              row.TIEN_PB = Math.round(parseInt(item.DONGIA) / parseInt(item.SOTHANG_PB))
            }
            else{
              row.TIEN_PB = Math.round(parseInt(item.DONGIA) * parseInt(item.SL_XUAT))
            }
            row.SOTHANG_PB = parseInt(item.SOTHANG_PB)
            row.LOAI_TBI = item.LOAI_TBI.toString()
            row.LOAI_VT = item.LOAI_VT.toString()
            var thang_bh = item.THANG_BD
            if (this.f_DateParse(thang_bh,'MM/YYYY')){
              row.THANG_BD = this.thang_bh
            }
            else{
              row.THANG_BD = this.f_DateToString(new Date(),'MM/YYYY')
            }
            row.THANG_KT = this.f_AddMonth(this.p_ngay_ct,parseInt(item.SOTHANG_PB),'MM/YYYY')

            row.CHUNGTU_ID = ""
            row.GHICHU = ""
            row.SERIAL = ""
            row.MA_TBI = ""
            row.LOAITBI_ID = item.LOAITBI_ID

            ds_temp.push(row)
          }
          // là serial
          if (parseInt(item.SL_XUAT)){
            if(item.LOAITBI_ID.toString() == "2"){
              if(parseInt(item.SL_XUAT) > 0){
                for (let i = 0; i < parseInt(item.SL_XUAT); i++){
                  let r = {}
                  r.THIETBI_ID = vvattu_id
                  r.DONVI_ID = vdv_id
                  r.DONVI_NHAN = vten_dv
                  r.NHANVIEN_ID = vnv_id
                  r.NHANVIEN_NHAN = vten_nv
                  r.VATTU_ID = vvattu_id
                  r.MA_VT = item.MA_VT.toString()
                  r.TEN_VT = item.TEN_VT.toString()
                  r.DVI_TINH = item.DVI_TINH.toString()
                  r.SOLUONG = 1
                  r.DONGIA = parseInt(item.DONGIA)
                  if (parseInt(item.SOTHANG_PB) > 0){
                    r.TIEN_PB = Math.round(parseInt(item.DONGIA) *1 / parseInt(item.SOTHANG_PB))
                  }
                  else{
                    r.TIEN_PB = Math.round(parseInt(item.DONGIA) *1)
                  }

                  r.SOTHANG_PB = parseInt(item.SOTHANG_PB)
                  r.LOAI_TBI = item.LOAI_TBI.toString()
                  r.LOAI_VT = item.LOAI_VT.toString()
                  var thang_bh = item.THANG_BD
                  if (this.f_DateParse(thang_bh,'MM/YYYY')){
                    r.THANG_BD = this.thang_bh
                  }
                  else{
                    r.THANG_BD = this.f_DateToString(new Date(),'MM/YYYY')
                  }
                  r.THANG_KT = this.f_AddMonth(this.p_ngay_ct,parseInt(item.SOTHANG_PB),'MM/YYYY')

                  r.CHUNGTU_ID = ""
                  r.GHICHU = ""
                  r.SERIAL = ""
                  r.MA_TBI = ""
                  r.LOAITBI_ID = item.LOAITBI_ID

                  ds_temp.push(r)
                }
              }
            }
          }
        }

        if (!this.ds_capphat.length){
          this.ds_capphat = ds_temp
        }
        else{
          this.ds_capphat.push(...ds_temp)
        }
        this.grcCCDC = this.ds_capphat
        this.covertParseInt_grcCCDC()
      // } catch (error) {
      //   this.$toast.error("Lỗi định dạng file excel, bạn xem lại!")
      // }
    },  
    async frmChonNV_CCDC_Load(){
      this.$root.showLoading(true)
      // this.clearData();
      await this.NAP_COMBO()
      await this.NAP_DS_VATTU()
      
      if (this.ds_capphat.length){
        this.grcCCDC = this.ds_capphat
        this.covertParseInt_grcCCDC()
      }
      else{
        this.grcCCDC = []
      }
      this.Visible.splitContainer2.Panel2Collapsed = true;

      if (this.ds_luuCP.length){
        this.ds_capphat = this.ds_luuCP   // set 
        this.covertParseInt_grcCCDC()
      }
      
      if(this.ds_serial.length){
        for(let k in this.ds_serial){
          let i = 0;
          if(this.ds_luuCP.length){
            for(let dr_CP of this.ds_luuCP){
              if(this.ds_serial[k]["SERIAL"].toString() == dr_CP["SERIAL"].toString()){
                this.ds_serial[k].ISCHOOSE = "1"
                i = 1;
              }
            }
          }
          if(i==0){
            this.ds_serial[k].ISCHOOSE = "0"
          }
        }
        this.GridDS_SERIAL = this.ds_serial.filter(a=>a.ISCHOOSE != "1")
        this.Visible.splitContainer2.Panel2Collapsed = false;
      }
      this.$root.showLoading(false)
    },
    async cboDenDonVi_EditValueChanged(dataItem){
      // try {
        if (dataItem == null || dataItem == ''){ return }
        this.params.p_donvi_id = dataItem.DONVI_ID
        if(this.capnv.toString() == '1'){
          
          if(this.p_donvi_id != null && this.p_donvi_id != '' ){
            
            this.grcDanhSach = this.dt_nv_grid.filter(a=>a.donViId.toString() == this.p_donvi_id.toString() || a.donViId == null || a.donViId.toString() == '') 
            
            if(this.grcDanhSach.length == 1){
              var dt_dv = await this.getDSDonViV2({donViId:this.p_donvi_id})
              this.grcDanhSach = this.dt_nv_grid.filter(a=>a.donViId.toString() == this.p_donvi_id.toString() || a.donViId == null || a.donViId.toString() == ''
               || a.nhanVienId.toString() == this.$auth.getNhanVienID().toString())

              if(dt_dv.length){
                for(let i in dt_dv){
                  
                  let ds_dv = await this.getDSDonViV3({donViId: dt_dv[i].donViId})
                  if(ds_dv.length){
                    let number = await this.KiemTraToTruong({donViId:dt_dv[i].donViId})
                    if (number.length){
                      this.grcDanhSach = this.dt_nv_grid.filter(a=> a.donViId.toString() == this.p_donvi_id.toString() || a.donViId == null || a.donViId.toString() == ''
                      || a.nhanVienId.toString() == number[0].toString())
                    }
                    else{return }
                    break;
                  }
                }
              } 
            }
          }
          else{
            this.grcDanhSach = []
          }
        }
        else{
          if(this.p_donvi_id != null && this.p_donvi_id != ''){
            // gom goi tong ds_nv_grid thanh 1 api trong ham load de toi uu perform
            this.grcDanhSach = this.dt_nv_grid.filter(a=>a.donViId.toString() == this.p_donvi_id.toString() || a.donViId == null || a.donViId.toString() == '')

            if(this.grcDanhSach.length <= 1){
              var dt_dv = await this.getDSDonViV2({donViId: this.p_donvi_id})

              this.grcDanhSach = this.dt_nv_grid.filter(a=>a.donViId.toString() == this.p_donvi_id.toString() || a.donViId == null || a.donViId.toString() == ''
              || a.nhanVienId.toString() == this.$auth.getNhanVienID().toString())

              if(dt_dv.length){
                for(let i =0; i < dt_dv.length; i++){
                  
                  let ds_dv = await this.getDSDonViV3({donViId: dt_dv[i].donViId})
                  if (ds_dv.length){
                    let number = await this.KiemTraToTruong({donViId:dt_dv[i].donViId})
                    if (number.length){
                      this.grcDanhSach = this.dt_nv_grid.filter(a=> a.donViId.toString() == this.p_donvi_id.toString() || a.donViId == null || a.donViId.toString() == ''
                      || a.nhanVienId.toString() == number[0].toString())
                    }
                    else{return }
                    break; 
                  }
                }
              }
            }
          }
          else{
            this.grcDanhSach = []
          }
        }
      // } catch (error) {
      //   this.$toast.error("Lỗi hàm lấy danh sách nhân viên")
      // }
    },
    async tsbtnXacNhan_Click(){

      // if (this.capnv == 1){
        await this.NAP_DS_NV_CHON()
        await this.NAP_DS_CCDC_CHON()        
        // let ds_temp = [] 
        this.ds_capphat = this.grcCCDC
        if(this.ds_nv.length && this.ds_ccdc.length){
          let ds_cc = []
          let nv = this.ds_nv.length;

          this.grcCCDC.length ?  ds_cc = this.grcCCDC : ''

          for(let dr of this.ds_nv){
            let ds_temp = []
            // let dr = this.ds_nv[i]
            for (let item of this.ds_ccdc){
              // let item = this.ds_ccdc[j]
              let ngay_ct_temp = new Date(this.p_ngay_ct)
              let sl_dacap = 0
              if(ds_cc.length){
                let q = ds_cc.reduce((sum,b)=> (b.VATTU_ID.toString() == item.VATTU_ID.toString() ?  (sum + parseInt(b.SOLUONG)) : sum ),0)
                if(q){
                  let sl_cl = parseInt(item.SOLUONG) - parseInt(q)
                  if (nv == 0){
                    nv = 1;
                  }
                  if (sl_cl / item.SL_XUAT / nv < 1){
                    this.$toast.error("Số lượng của vật tư không đủ với số lượng nhân viên bạn chọn")
                    return
                  }
                  nv -= 1;
                }
              }

              else{
                let s1 = parseInt(item.SOLUONG) / parseInt(item.SL_XUAT) / nv
                
                if (s1 < 1){
                  this.$toast.error("Số lượng của vật tư không đủ với số lượng nhân viên bạn chọn")
                  return
                }
              }

              // Không là serial
              if (item.LOAITBI_ID.toString() != "2") {
                let row = {}
                
                if (item.THIETBI_ID.toString()){
                  row.THIETBI_ID = parseInt(item.THIETBI_ID)
                }
                else{
                  row.THIETBI_ID = parseInt(item.VATTU_ID)
                }

                row.DONVI_ID = this.p_donvi_id
                row.DONVI_NHAN = this.cboDenDonVi.find(a=>a.DONVI_ID.toString() == this.p_donvi_id.toString()).TEN_DV || "" // có thể dùng cách khác
                row.NHANVIEN_ID = dr.nhanVienId
                row.NHANVIEN_NHAN = dr.tenNv
                row.VATTU_ID = parseInt(item.VATTU_ID)
                row.MA_VT = item.MA_VT.toString()
                row.TEN_VT = item.TEN_VT.toString()
                row.DVI_TINH = item.DVI_TINH.toString()
                row.SOLUONG = parseInt(item.SL_XUAT)
                row.DONGIA = parseInt(item.DONGIA)
                let tien = parseInt(item.SL_XUAT)*parseInt(item.DONGIA)
                parseInt(item.SOTHANG_PB) > 0 ? 
                  row.TIEN_PB = Math.round(parseInt(item.TIEN) / parseInt(item.SOTHANG_PB)) :
                  row.TIEN_PB = Math.round(tien)
                row.SOTHANG_PB = parseInt(item.SOTHANG_PB)
                row.LOAI_TBI = item.LOAI_TBI.toString()
                row.LOAI_VT = item.LOAI_VT
                row.THANG_BD = this.f_DateToString(ngay_ct_temp,'MM/yyyy')
                
                row.THANG_KT = this.f_DateToString(ngay_ct_temp.setMonth(ngay_ct_temp.getMonth() + parseInt(item.SOTHANG_PB)),'MM/yyyy')
                row.CHUNGTU_ID = ""
                row.GHICHU = ""
                row.SERIAL = ""
                row.MA_TBI = ""
                row.LOAITBI_ID = item.LOAITBI_ID
                row.TINHTRANGTBI_ID = 1
                ds_temp.push(row)
              }

              // là serial
              let soluong_tbi = parseInt(item.SL_XUAT)
              if (soluong_tbi){
                if(item.LOAITBI_ID.toString() == "2"){
                  for (let k = 0; k < soluong_tbi; k++){
                    let row = {}
                    row.THIETBI_ID = parseInt(item.VATTU_ID)
                    row.DONVI_ID = this.p_donvi_id
                    row.DONVI_NHAN = this.cboDenDonVi.find(a=>a.DONVI_ID.toString() == this.p_donvi_id.toString()).TEN_DV || ""
                    row.NHANVIEN_ID = dr.nhanVienId
                    row.NHANVIEN_NHAN = dr.tenNv
                    row.VATTU_ID = parseInt(item.VATTU_ID)
                    row.MA_VT = item.MA_VT.toString()
                    row.TEN_VT = item.TEN_VT.toString()
                    row.DVI_TINH = item.DVI_TINH.toString()
                    row.SOLUONG = 1
                    row.DONGIA = parseInt(item.DONGIA)
                    parseInt(item.SOTHANG_PB) > 0 ? 
                      row.TIEN_PB = Math.round(parseInt(item.DONGIA) / parseInt(item.SOTHANG_PB)) :
                      row.TIEN_PB = Math.round(parseInt(item.DONGIA))
                    row.SOTHANG_PB = parseInt(item.SOTHANG_PB)
                    row.LOAI_TBI = item.LOAI_TBI.toString()
                    row.LOAI_VT = item.LOAI_VT
                    row.THANG_BD = this.f_DateToString(ngay_ct_temp,'MM/yyyy')
                    row.THANG_KT = this.f_DateToString(ngay_ct_temp.setMonth(ngay_ct_temp.getMonth() + parseInt(item.SOTHANG_PB)),'MM/yyyy')
                    row.CHUNGTU_ID = ""
                    row.GHICHU = ""
                    row.SERIAL = ""
                    row.MA_TBI = ""
                    row.LOAITBI_ID = item.LOAITBI_ID
                    row.TINHTRANGTBI_ID = 1
                    ds_temp.push(row)
                  }
                }
              }
            }
            if (!this.ds_capphat.length){
              this.ds_capphat = ds_temp
            }
            else{
              this.ds_capphat.push(...ds_temp)
            }
            this.grcCCDC = this.ds_capphat
            this.covertParseInt_grcCCDC()
            
            // await this.Close()
          }
          // Cập nhật danh sách nhân viên
          // await this.cboDenDonVi_EditValueChanged()
          // reset Danh sách vật tư đã check 
          
        }
        
      // }
      // else{
      //   // so sánh thấy code vẫn giống capnv = 1 nên ẩn đk 
        
      // }
    },
    grvCCDC_ValidatingEditor(){
      // lam sau
    },
    btnXoaVT_ButtonClick(row){
      this.ds_capphat.splice(parseInt(row.index),1)
      this.grcCCDC = this.ds_capphat
    },
    btnXoaHetVT_Click(){
      this.ds_capphat = []
      this.grcCCDC = []
    },
    async tsbtnGhiLai_Click(){
      if(await this.KIEMTRA_DULIEU()){
        this.kieu_pb = this.p_radio_pb
        this.xacnhan = true
        await this.Close()
      }
      else{
        this.xacnhan = false
      }
      
    },
    async btnGanSeri_ButtonClick(row){
      // try {
        // quy bo sung dk
        console.log(this.grvCCDCFocusedRow , " = this.grvCCDCFocusedRow")
        console.log(row , " = this.row")
        if (this.grvCCDCFocusedRow && this.grvCCDCFocusedRow.VATTU_ID  == row.VATTU_ID){
            for(let i in this.GridDS_SERIAL){
              let dr = this.GridDS_SERIAL[i]
              if (dr.VATTU_ID == this.grvCCDCFocusedRow.VATTU_ID){
                this.GridDS_SERIAL[i].ISCHOOSE = '0'
                }
            }
            var tbi = row.THIETBI_ID
            this.grcCCDC[this.grvCCDC_rowIndex].SERIAL = row.SERIAL
            this.grcCCDC[this.grvCCDC_rowIndex].THIETBI_ID = tbi

            // grvDS_SERIAL.SetRowCellValue(grvDS_SERIAL.FocusedRowHandle, "ISCHOOSE", "1");
            this.GridDS_SERIAL[row.index].ISCHOOSE = "1"

            this.GridDS_SERIAL = this.GridDS_SERIAL.filter((a,index)=> index != row.index) // checkbox ko xài ISCHOOSE, xài index
            
            // tự động nhảy selected xuống dòng dưới và trigger event focusedRow
            if (this.grcCCDC.length > (this.grvCCDC_rowIndex + 1) ){
              this.grvCCDC_rowIndex += 1
              this.grvCCDCFocusedRow = this.grcCCDC[this.grvCCDC_rowIndex]
              this.$refs.grvCCDC.flagSelectedRowIndexes = [this.grvCCDC_rowIndex]
            }

            this.$refs.grvCCDC.grid.refreshColumns()
            
        }
        
        
        
      // } catch (error) {
        
      // }
    },
    async btnGoSeri_ButtonClick(row){
      // try {
        // mục đích sau khi gán serial , thì hủy gán thì serial về lại danh sách, grid
        this.grvCCDCFocusedRow = row
        this.grvCCDC_rowIndex = row.index
        if (!this.Visible.splitContainer2.Panel2Collapsed){
          // for (let i in this.GridDS_SERIAL){
          //   let dr = this.GridDS_SERIAL[i]
          //   var a = dr.THIETBI_ID
          //   var b = this.grvCCDCFocusedRow.THIETBI_ID
          //   if (dr.THIETBI_ID.toString() == b){
          //     this.GridDS_SERIAL[i].ISCHOOSE = "0"
          //   }
          // }
          let serialArray = this.GridDS_SERIAL.map(a=>a.SERIAL)
          if (!serialArray.includes(row.SERIAL)){
            // hủy value SERIAL của ds CCDC
            this.grcCCDC[row.index].SERIAL = ""
            this.grcCCDC[row.index].THIETBI_ID = row.VATTU_ID
            
            this.ds_serial.forEach(item => {
              if (item.SERIAL == row.SERIAL){
                item.ISCHOOSE = "0"
              }
            })
          }
          
          this.$refs.grvDS_SERIAL.grid.refreshColumns()
          this.$refs.grvCCDC.grid.refreshColumns()
        }
      // } catch (error) {
        
      // }
    },
    async grvCCDC_FocusedRowChanged(args){
      if (args){
        
        this.grvCCDCFocusedRow = args.rowData
        this.grvCCDC_rowIndex = args.rowIndex
        
        this.GridDS_SERIAL = this.ds_serial.filter(a=>a.ISCHOOSE != '1'&& a.VATTU_ID.toString() == this.grvCCDCFocusedRow.VATTU_ID.toString() )
        }
    },
    txtExcelTbiMau_Click(){
      // try {
        let dt = []
        for (let j in this.grcVatTu){
          let item =  this.grcVatTu[j]
          let sl_x = parseInt(item.SL_XUAT)
          let sl = parseInt(item.SOLUONG)
          for (let i = 0; i <= sl-sl_x; i++){
            if (item.LOAITBI_ID.toString() == "2"){
              dt.push({
                MA_DONVI_NHAN: "",
                MA_NVIEN_NHAN: "",
                MA_VT: item.MA_VT.toString(),
                TEN_VT: item.TEN_VT.toString(),
                DVI_TINH: item.DVI_TINH.toString(),
                SOLUONG: 1,
                SL_XUAT: 1,
                SERIAL: item.SERIAL,
                MA_TBI: item.MA_TBI,                
                DONGIA: parseInt(item.DONGIA),
                SOTHANG_PB: parseInt(item.SOTHANG_PB),
                LOAI_TBI: item.LOAI_TBI,
                LOAI_VT: item.LOAI_VT,
                THANG_BD: "",
                GHICHU: "",
                LOAITBI_ID: item.LOAITBI_ID,
              })
            }
            else{
              dt.push({
                MA_DONVI_NHAN: "",
                MA_NVIEN_NHAN: "",
                MA_VT: item.MA_VT.toString(),
                TEN_VT: item.TEN_VT.toString(),
                DVI_TINH: item.DVI_TINH.toString(),
                SOLUONG: sl_x,
                SL_XUAT: sl_x,
                SERIAL: item.SERIAL,
                MA_TBI: item.MA_TBI,
                DONGIA: parseInt(item.DONGIA),
                SOTHANG_PB: parseInt(item.SOTHANG_PB),
                LOAI_TBI: item.LOAI_TBI,
                LOAI_VT: item.LOAI_VT,
                THANG_BD: "",
                GHICHU: "",
                LOAITBI_ID: item.LOAITBI_ID,
              })
            }
          }
        }

        if (!dt.length){
          dt = [{
            MA_DONVI_NHAN: "",
            MA_NVIEN_NHAN: "",
            MA_VT: "",
            TEN_VT: "",
            DVI_TINH: "",
            SOLUONG: 0,
            SL_XUAT: 0,
            SERIAL: "",
            MA_TBI: "",
            DONGIA: 0,
            SOTHANG_PB: 0,
            LOAI_TBI: "",
            LOAI_VT: "",
            THANG_BD: "",
            GHICHU: "",
            LOAITBI_ID: "",

          }]
        }
        
        let data = XLSX.utils.json_to_sheet(dt);
        let wb = XLSX.utils.book_new(); // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, "ThietBi"); // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, "fileNhapTbi.xlsx");
      // } catch (error) {
      //   this.$toast.error("Có lỗi xuất file thiết bị mẫu")
      // }
      

    },
    btnNhapExcel_Click(){
      this.$refs.fileUpload.$el.click()
    },
    async setdsVT_NVCCDC(value){
      this.ds_VT = value
      await this.NAP_DS_VATTU()
      
    },
    checkfile: function () {
      if (this.arrayImportFile.filexcel == null) {
        this.$toast.error("Chưa chọn file nào!")
        return false
      }
      if (
        this.arrayImportFile.filexcel.type !=
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
      ) {
        this.$toast.error("File không phải định dạng xlsx hoặc xls!")
        return false
      }
      return true
    },
    async onChange() {
      this.arrayImportFile.filexcel = this.$refs.fileUpload.$refs.input.files[0]
      
      let json_list = []
      try {
        this.$root.showLoading(true)
        if (this.checkfile()) {
          let tencot
          const reader = new FileReader()
          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result
            const wb = XLSX.read(bstr, { type: "binary" })
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
            // retrieve headers (i.e. remove first row)
            let headers  = data.shift() // bỏ dòng header đầu tiên trong data
            
            // then build the json for each row
            var result = data.map(function(a) {
                var jsonRow = {};
                a.forEach(function(cellValue, cellIndex) { 
                    jsonRow[headers[cellIndex]] = cellValue;
                });
                return jsonRow;
            });
            
            
            await this.CAP_NHAT_TBI_EXCEL(JSON.parse(JSON.stringify(result))) 
            
            
          }
          reader.readAsBinaryString(this.arrayImportFile.filexcel)
          
        }
      } catch (error)  {
        this.$toast.error("Có lỗi xảy ra: " + error)
        return
      } finally {
        this.$root.showLoading(false)
      }
      this.arrayImportFile.filexcel = null
    },
  async edit_TableVatTu(args){
      var TableVatTu = this.$refs.grvVatTu
      var changes = TableVatTu.getBatchChanges() 
      changes.changedRecords.length ? TableVatTu.batchUpdate(changes) : ''
    },
  async edit_TableCCDC(arg){
      console.log(arg , " = arg")
    
      let changes = this.$refs.grvCCDC.getBatchChanges();

      if (changes.changedRecords.length == 0) {
        return;
      }
      if (arg.columnName == "SOTHANG_PB" || arg.columnName == "THANG_BD") {
        if(parseInt(changes.changedRecords[0].SOTHANG_PB) <= 0){
          this.$toast.error("Số tháng phân bổ phải lớn hơn 0")
          this.$refs.grvCCDC.grid.refreshColumns()
          return
        }
        let THANG_BD = this.f_StringToDate(changes.changedRecords[0].THANG_BD , 'MM/YYYY')
        changes.changedRecords[0].THANG_KT = this.f_DateToString(THANG_BD.setMonth(THANG_BD.getMonth() + parseInt(changes.changedRecords[0].SOTHANG_PB)),'MM/yyyy')
       
      }

      this.$refs.grvCCDC.batchUpdate(changes) 
      this.$refs.grvCCDC.grid.refreshColumns()
    },
  },
created: async function() {
    // this.frmChonNV_CCDC_Load()

  },
  watch: {
    
  },
  destroyed () {
  }
};
</script>
<style>
  .e-grid .e-gridheader {  
    position: -webkit-sticky;  
    position: sticky;  
    top: 0px;                                   
    z-index: 1;  
  }  

  .frmChonNV_CCDC .e-content {
    height: 230px !important;
  }

  .frmChonNV_CCDC .grid-ext-nodata .e-content{
    height: auto !important
  }

</style>