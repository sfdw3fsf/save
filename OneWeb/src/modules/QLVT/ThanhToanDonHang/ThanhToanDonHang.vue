<template src='./ThanhToanDonHang.html'></template>

<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import moment from 'moment';
import Vue from "vue";
import breadcrumb from '@/components/breadcrumb'
import ThanhToanDonHangAPI from  '../api/ThanhToanDonHangAPI'
import { getterName,actionName,statePropertyName,mutationName} from '../store/thanhtoandonhang'
import select2 from '@/components/Select2.vue'
import { Freeze, Edit, Group, Page, Filter, Sort, Resize} from "@syncfusion/ej2-vue-grids";
import { Query,Predicate } from '@syncfusion/ej2-data';
import TraCuuHopDongVaDonHang from '../TraCuuHopDongVaDonHang/TraCuuHopDongVaDonHang.vue'
import { L10n } from '@syncfusion/ej2-base'

L10n.load({
    'vi-VN': {
        grid: {
          EmptyRecord: 'Không có bản ghi nào để hiển thị'
        }
    }
})

export default {

  components: {TraCuuHopDongVaDonHang, breadcrumb, appSelect2: select2 },
  name: "ThanhToanDonHang",
  mounted() {
  },
  provide: {
    grid: [ Freeze, Edit,Group, Page,Filter,Sort,Resize ]
  },
  computed: {
    // ...mapState("thanhtoandonhang", statePropertyName),
    // ...mapGetters("thanhtoandonhang", getterName),
    p_ma_donhang: {
      get() {
        return this.params.p_ma_donhang
      },
      set(value) {
        return this.params.p_ma_donhang = value
      }
    },
    p_hinhthuc_tra: {
      get() {
        return this.params.p_hinhthuc_tra
      },
      set(value) {
        return this.params.p_hinhthuc_tra = value
      }
    },
    p_ngay_tt: {
      get() {
        return this.params.p_ngay_tt
      },
      set(value) {
        return this.params.p_ngay_tt = value
      }
    },
    p_thu_quy: {
      get() {
        return this.params.p_thu_quy
      },
      set(value) {
        return this.params.p_thu_quy = value
      }
    },
    p_tien_tra: {
      get() {
        return this.params.p_tien_tra
      },
      set(value) {
        return this.params.p_tien_tra = value
      }
    },
    p_tienda_tra: {
      get() {
        return this.params.p_tienda_tra
      },
      set(value) {
        return this.params.p_tienda_tra = value
      }
    },
    p_ghichu: {
      get() {
        return this.params.p_ghichu
      },
      set(value) {
        return this.params.p_ghichu = value
      }
    },
  },
  data() {
    return {
        header: {
            title: "THANH TOÁN ĐƠN HÀNG",
            // list: [
            // { name: "Quản lý vật tư", link: { name: "Hợp đồng - Đơn hàng" } },
            // ],
        },
        params: {
            p_ma_donhang: "",
            p_hinhthuc_tra: "",
            p_ngay_tt: new Date(),
            p_thu_quy: "",
            p_tien_tra: "",
            p_tienda_tra: "",
            p_ghichu: "",
        },
        _owner: "", //frmDonHang
        _owner_tc: "", //frmTraCuuHopDong
        Loai_NV: {
            THUNGAN: 17,
        },
        enable: {
            tsbtnNhapMoi: false,
            tsbtnGhiLai: false,
            tsbtnXoa: false,
            tsbtnHuyBo: false,
            btnTimKiem: false
        },
        readonly: {
            spTienDaTra: false,
            spTienTra: false
        },
        donhang_id: null,//208
        phieutt_id: "",
        temp_id: "",
        dongy: false,
        sSTT: 0,
        filterOptions: {enableCaseSensitivity: false, mode: 'Immediate', ignoreAccent: true, },
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
        editSettings: { allowEditing: true},
        selectionSettings: {  type: 'Single', checkboxOnly: true },

        animationSettings: { effect: 'Zoom' },
        target: '.main-wrapper',
        iTemplate: function(e) {
                return {
                    template: Vue.component("itemTemplate", {
                      template: ` <div class="row">
                                    <div class="col-sm-5 border">
                                      <span>{{data.MA_HT_TRA}}</span>
                                    </div>
                                    <div class="col-sm-7 border">
                                      <span>{{data.HT_TRA}}</span>
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
              predicate  = new Predicate('MA_HT_TRA', 'Contains', e.text);
              predicate = predicate.or('HT_TRA', 'contains', e.text);
              query.where(predicate)
            }

            //pass the filter data source, filter query to updateData method.
            e.updateData(this.dataSource, query);
        },
        grcDS_ThanhToanPage : {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },
        GridDS_VT_TTPage : {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },
        GridDS_VT_DonHangPage : {
            page: 0,
            maxSize: 10,
            totalElement: 0,
            totalPages: 0,
            sort: null,
            propertiesSort: null,
            currentRow: 0
        },

        cboVatTuFilter: [],

        GridDS_ThanhToan: [],
        GridDS_VT_TT: [],
        GridDS_VT_DonHang: [],
        dvi_id: "", //frmTraCuuHopDong
        cboDonHang: [],
        cboHinhThucTra: [],
        cboNVThuNgan: [],
        cboDonHang_Filter: [],
        cboHinhThucTra_Filter: [],
        cboNVThuNgan_Filter: [],
        cboVatTu_Filter: [],
        DSDonViTinh_Filter: [],

        vdh_tracuu_id: 0
    };
  },
  methods: {
    ...mapActions("thanhtoandonhang", actionName),
    // ...mapMutations("thanhtoandonhang", mutationName),
    
    // chuyển Date thành type string
     f_DateToString: function (value,format='DD/MM/YYYY'){
      return moment(value).format(format)
    },
    f_StringToDate(value,format='DD/MM/YYYY'){
      return moment(value,format).toDate();
    },
    grcDS_ThanhToanHandler(e) {
      //this.pageinfo.page = e
      this.grcDS_ThanhToanPage.page = e.pageIndex
      this.grcDS_ThanhToanPage.maxSize = e.pageSize
    },
    GridDS_VT_TTHandler(e) {
      //this.pageinfo.page = e
      this.GridDS_VT_TTPage.page = e.pageIndex
      this.GridDS_VT_TTPage.maxSize = e.pageSize
    },
    GridDS_VT_DonHangHandler(e) {
      //this.pageinfo.page = e
      this.GridDS_VT_DonHangPage.page = e.pageIndex
      this.GridDS_VT_DonHangPage.maxSize = e.pageSize
    },
    async Load_cbo(){
        //cbo Đơn hàng - Filter
        this.cboDonHang_Filter = await this.getDSDonHangTheoDK({donHangChaId: -1})
        // Đơn hàng cbo
        if(!this._owner){
            // this.cboDonHang = await this.getDSDonHangTheoDK({donHangChaId: -1})
            this.cboDonHang = this.cboDonHang_Filter
        }
        else{
            let dvi = this.$auth.getDonViID()
            if (!this._owner_tc){
                dvi = this.dvi_id
            }
            this.cboDonHang = await this.getDSDonHangTheoDV({donvi_id: dvi})
        }
        //cbo Hình thức trả - Filter
        this.cboHinhThucTra_Filter = await this.getDSHinhThucTra({kieu: 1}) // no la 1 

        // Hình Thức Trả CBO
        this.cboHinhThucTra = await this.getDSHinhThucTra({kieu: -1})
        //cbo Thu ngân - Filter
        let dt_thungan = await this.getDSNhanVienThuNgan({
            "kieu" : 1,
            "nhanVienId" : this.$auth.getNhanVienID(),
            "nguoiDungId" : this.$auth.getNguoiDungID(),
            "loaiNV" : this.Loai_NV.THUNGAN
        })
        if(!dt_thungan.length){
            dt_thungan = await this.getDSNhanVienThuNgan({
            "kieu" : 2,
            "nhanVienId" : this.$auth.getNhanVienID(),//6996,
            "nguoiDungId" : this.$auth.getNguoiDungID(),//2519,
            "loaiNV" : this.Loai_NV.THUNGAN
            })
        }
        this.cboNVThuNgan_Filter = dt_thungan
        this.cboNVThuNgan = dt_thungan
        // Đơn vị tính Filter
        this.DSDonViTinh_Filter = await this.getDSDonViTinh()
        // Vật tư Filter
        // this.cboVatTu_Filter = await this.getDSVatTuLuaChon()
    },
    SetButton(kieu){
        this.enable.tsbtnNhapMoi = false
        this.enable.tsbtnGhiLai = false
        this.enable.tsbtnXoa = false
        this.enable.tsbtnHuyBo = false
        this.enable.btnTimKiem = false
        if (kieu == -1){ //Bat dau
            this.enable.tsbtnNhapMoi = true
            this.enable.tsbtnXoa = true
            this.enable.tsbtnGhiLai = true
            this.enable.tsbtnHuyBo = true
        } 
        if (kieu == 0){ //Bat dau
            this.enable.tsbtnNhapMoi = true
            this.Clear()
        } 
        if (kieu == 1){ //Them moi
            this.enable.tsbtnGhiLai = true
            this.enable.tsbtnHuyBo = true
            if (!this._owner_tc && !this._owner){
                this.enable.btnTimKiem = true
            }
            this.Clear()
            if (this.donhang_id != -1){
                this.p_ma_donhang = this.donhang_id
            }
        }
        if (kieu == 2){ //Huy
            this.enable.tsbtnNhapMoi = true
            this.enable.tsbtnXoa = true
            this.Clear()
        }
        if (kieu == 3){ // Edit
            this.enable.tsbtnNhapMoi = true
            this.enable.tsbtnXoa = true
            this.enable.tsbtnGhiLai = true
            this.enable.tsbtnHuyBo = true
        }
    },
    Clear(){
        this.p_ma_donhang = null
        this.p_hinhthuc_tra = null
        this.p_ngay_tt = new Date()
        this.p_thu_quy = null
        this.p_tien_tra = 0
        this.p_tienda_tra = 0
        this.p_ghichu = ""
        this.ClearGrid()
    },
    ClearGrid(){
        this.GridDS_VT_TT = []
        this.GridDS_VT_DonHang = []
    },
    async KiemTra_DuLieu(){
        let errorMessage = []
        if(!this.p_ma_donhang){
            errorMessage.push("Bạn chưa chọn đơn hàng") 
            // this.$toast.error("Bạn chưa chọn đơn hàng")
            // return false
        }
        if(!this.p_hinhthuc_tra){
            errorMessage.push("Bạn chưa chọn hình thức trả")
            // this.$toast.error("Bạn chưa chọn hình thức trả")
            // return false
        }
        if(!this.p_thu_quy){
            errorMessage.push("Bạn chưa chọn nv thu ngân")
            // this.$toast.error("Bạn chưa chọn nv thu ngân")
            // return false
        }
        if (errorMessage.length > 0) {
            this.$toast.error(errorMessage.join('\n'))
            return false
        }
        let rs = await this.KiemTraNgayTT({
            "donHangId" : this.donhang_id,
            "ngayThanhToan": this.f_DateToString(this.p_ngay_tt,'DD/MM/YYYY hh:mm:ss')
        })
        if (rs.length){
            errorMessage.push("Ngày thanh toán phải sau ngày yêu cầu (" + rs[0] +")")
            // this.$toast.error("Ngày thanh toán phải sau ngày yêu cầu (" + rs[0] +")")
            this.$refs.dtpNgayTT.focusIn()
            // return false
        }
        if (errorMessage.length > 0) {
            this.$toast.error(errorMessage.join('\n'))
            return false
        }
        
        return true
    },
    async TaoDuLieu(themmoi){
        // this.phieutt_id = ""
        let DS_PhieuTT = {}
        if (themmoi){
            this.phieutt_id = await this.getKeys({
                // "phanVungId" : this.getPhanVungID(),
                "keyName" : "PHIEUTT_ID",
                "numBlockSize" : 1,
                "numRetry": 10
            })
        }
        if (this.donhang_id != -1){
            DS_PhieuTT.donHangId = parseInt(this.donhang_id)
        }
        else{
            DS_PhieuTT.donHangId = parseInt(this.p_ma_donhang)
        }
        DS_PhieuTT.phieuTTId = this.phieutt_id
        DS_PhieuTT.htTraId = parseInt(this.p_hinhthuc_tra)
        DS_PhieuTT.ngayTT = this.f_DateToString(this.p_ngay_tt,'DD/MM/YYYY hh:mm:ss')
        DS_PhieuTT.thuNganId = parseInt(this.p_thu_quy)
        DS_PhieuTT.ghiChu = this.p_ghichu.trim()
        DS_PhieuTT.nhanVienId = parseInt(this.$auth.getNhanVienID())
        DS_PhieuTT.donViId = parseInt(this.$auth.getDonViID())
        DS_PhieuTT.ngayCN = this.f_DateToString(new Date(),'DD/MM/YYYY hh:mm:ss')
        DS_PhieuTT.nguoiCN =  this.$auth.getUserName()//tt_nd.ten_nd;
        DS_PhieuTT.mayCN = this.$auth.getUserName() //tt_nd.may_cn;
        DS_PhieuTT.ipCN = this.$auth.getUserName() //tt_nd.ip;
        return DS_PhieuTT
    },
    async GhiLai(){
        // try {
            if((await this.KiemTra_DuLieu())){
                if(!this.enable.tsbtnNhapMoi){
                    let DS_PhieuTT = await this.TaoDuLieu(true);
                    await this.$bvModal.msgBoxConfirm('Bạn có muốn cập nhật dữ liệu không?', {
                        "okVariant": 'success',
                        "okTitle": 'Đồng ý',
                        "cancelTitle": 'Hủy',
                        "centered": true,
                        })
                        .then(async(value) => {
                            if (value){
                                await this.TaoDonHang({type: 'themmoi',params:DS_PhieuTT})
                                this.$toast.success("Thêm mới thanh toán thành công !")
                            }
                        })
                        .catch(err => {
                            // An error occurred
                        })
                    
                }
                else{
                    let DS_PhieuTT = await this.TaoDuLieu(false);
                    
                    await this.$bvModal.msgBoxConfirm('Bạn có muốn cập nhật dữ liệu không?', {
                        "okVariant": 'success',
                        "okTitle": 'Đồng ý',
                        "cancelTitle": 'Hủy',
                        "centered": true,
                        })
                        .then(async(value) => {
                            if (value){
                               await this.CapNhatDonHang({type: 'capnhat',params:DS_PhieuTT})
                                this.$toast.success("Cập nhật thanh toán thành công !")
                            }
                        })
                        .catch(err => {
                            // An error occurred
                        })
                    
                }
                if (this.phieutt_id)
                {
                    await this.HienThi_DSTT();
                    this.Focus_Row(this.phieutt_id);
                }
            }
        // } catch (error) {
            // this.$toast.error("Có lỗi khi lưu")
        // }
    },
    Focus_Row(tmp){
        if (tmp){
            let rowHandle = this.GridDS_ThanhToan.findIndex(a=>a.PHIEUTT_ID.toString() == tmp.toString())
            // if (rowHandle.toString() != "-1"){
                // this.$refs.tableDSThanhToan.selectRow(parseInt(rowHandle))
            // }
        }
    },
    async HienThi_DSTT(){
        // try {
            if (this.donhang_id){
                this.$root.showLoading(true)
                this.GridDS_ThanhToan = await this.getDSThanhToanDK({
                    donvi_id: this.$auth.getDonViID(),//882,
                    donhang_id: this.donhang_id //-1,
                })
                await this.convert_GridDS_ThanhToan()
                // this.GridDS_ThanhToan.length ? this.$refs.tableDSThanhToan.selectRow(0) : ''
                this.$root.showLoading(false)
            }
        // } catch (error) {
        //     this.$toast.error("Lỗi khi lấy danh sách thanh toán !")
        // }
    },
    async convert_GridDS_ThanhToan(){
        this.GridDS_ThanhToan.forEach(a=>{
            let maDH = this.cboDonHang_Filter.find(b=>b.DONHANG_ID.toString() == a.DONHANG_ID.toString());
            maDH ? a['MA_DH_NEW'] = maDH.MA_DH_NEW : "";
            let htTra = this.cboHinhThucTra_Filter.find(b=>b.HT_TRA_ID.toString() == a.HT_TRA_ID.toString());
            htTra ? a['HT_TRA'] = htTra.HT_TRA : "";
            let nv = this.cboNVThuNgan_Filter.find(b=>b.NHANVIEN_ID.toString() == a.NHANVIEN_ID.toString());
            if (nv){
                let arrayTenNv = nv.TEN_NV.split('_')
                arrayTenNv.length > 2 ? arrayTenNv.splice(2,1) : ""
                a['TEN_NV'] = arrayTenNv.join("-")
            }
        })
    },
    async grvDS_ThanhToan_FocusedRowChanged(row){
        // try { 
            if(!row){
                return
            }
            this.phieutt_id = row.PHIEUTT_ID
            // tạm thêm vào để có đơn hàng id => test
            // this.donhang_id = row.DONHANG_ID.toString()

            if (this.donhang_id != -1){
                this.p_ma_donhang = this.donhang_id
            }
            else{
                this.p_ma_donhang = row.DONHANG_ID.toString()
            }
            this.p_ngay_tt = this.f_StringToDate(row.NGAY_TT,'DD/MM/YYYY hh:mm:ss')
            this.p_hinhthuc_tra = row.HT_TRA_ID
            this.p_thu_quy = row.THUNGAN_ID
            this.p_ghichu = row.GHICHU
            await this.Load_DS_VT()
            this.SetButton(3)
        // } catch (error) {
        //     this.$toast.error("Lỗi khi hiển thị danh sách thanh toán !")
        // }
    },
    async Load_DS_VT(){
        this.temp_id = ""
        if (this.donhang_id != -1){
            this.temp_id = parseInt(this.donhang_id);
        }
        else {
            this.temp_id = parseInt(this.p_ma_donhang)
        }
        this.GridDS_VT_DonHang = await this.getDSVatTuTheoDH({
            donhang_id: this.temp_id
        })
        await this.convert_GridDS_VT_DonHang()
        let resVT_TT = await this.getDSVatTuThanhToan({
            phieutt_id: this.phieutt_id
        })
        this.GridDS_VT_TT = resVT_TT
        await this.convert_GridDS_VT_TT()

        await this.Lay_TT_Tien()
    },
    async convert_GridDS_VT_TT (){
        var self = this
        this.GridDS_VT_TT.forEach(a=>{
            // let vatTu = self.cboVatTu_Filter.find(b=>b && b.vatTuID.toString() == a.VATTU_ID.toString());
            // vatTu ? a['MA_VT'] = vatTu.maVatTu : "";

            if (a.DVITINH_ID != null && a.DVITINH_ID != ''){
                let dvt = self.DSDonViTinh_Filter.find(b=>b && b.dviTinhId.toString() == a.DVITINH_ID.toString());
                dvt ? a['DVI_TINH'] = dvt.dviTinh : "";
            }

            a.SL_THANHTOAN.toString() ? a.SL_THANHTOAN = parseInt(a.SL_THANHTOAN) : ""
            a.DONGIA.toString() ? a.DONGIA = parseInt(a.DONGIA) : ""
            a.TIEN.toString() ? a.TIEN = parseInt(a.TIEN) : ""
            a.VAT.toString() ? a.VAT = parseInt(a.VAT) : ""
        })
    },
    async convert_GridDS_VT_DonHang(){
        var self = this
        this.GridDS_VT_DonHang.forEach(a=>{
        //    let vatTu = self.cboVatTu_Filter.find(b=>b && b.vatTuID.toString() == a.VATTU_ID.toString());
        //         vatTu ? a['MA_VT'] = vatTu.maVatTu : "";

                if (a.DVITINH_ID != null && a.DVITINH_ID != ''){
                    let dvt = self.DSDonViTinh_Filter.find(b=>b && b.dviTinhId.toString() == a.DVITINH_ID.toString());
                    dvt ? a['DVI_TINH'] = dvt.dviTinh : "";
                }
                

                a.SL_THANHTOAN.toString() ? a.SL_THANHTOAN = parseInt(a.SL_THANHTOAN) : ""
                a.SL_CHUAN.toString() ? a.SL_CHUAN = parseInt(a.SL_CHUAN) : ""
                a.SLTTCL.toString() ? a.SLTTCL = parseInt(a.SLTTCL) : ""
                a.TTCL.toString() ? a.TTCL = parseInt(a.TTCL) : ""
                if (a.hasOwnProperty('SL_DAT')){
                    a.SL_DAT.toString() ? a.SL_DAT = parseInt(a.SL_DAT) || 0 : ""
                }
                else{
                    a.SL_DAT = 0
                } 
        })
    },
    async Xoa(){
        // try {
            let res = await this.getDSVatTuThanhToan({ phieutt_id: this.phieutt_id })
            if (res.length){
                this.$toast.error("Bạn phải xóa các vật tư đã thanh toán mới được xóa phiếu")
                return
            }
            await this.$bvModal.msgBoxConfirm('Bạn có muốn xóa dữ liệu không ?', {
                "okVariant": 'success',
                "okTitle": 'Đồng ý',
                "cancelTitle": 'Hủy',
                "centered": true,
                })
                .then(async(value) => {
                    if (value){
                        await this.XoaPhieuThanhToan({phieutt_id: this.phieutt_id})
                        let resCapNhat = await this.CapNhatTrangThaiDH({donhang_id: this.donhang_id})
                        if (resCapNhat.toString() == "0"){
                            this.$toast.error("Cập nhật trạng thái đơn hàng bị lỗi")                
                        }
                        this.dongy = true
                        this.$toast.success("Xóa dữ liệu đơn hàng thành công !")
                        this.SetButton(0)
                        await this.HienThi_DSTT();
                    }
                })
                .catch(err => {
                    // An error occurred
                })
            
        // } catch (error) {
        //     this.$toast.error("Có lối ")
        // }
    },
    async frmThanhToanDH(){
        // this._owner = this.owner
        this.readonly.spTienDaTra = false
        this.readonly.spTienTra = false
        this.enable.cboDonHang = false
        this.enable.btnTimKiem = false
        this.donhang_id = 0//208
    },
    async Lay_TT_Tien(){
        this.p_tien_tra = this.sSTT
        this.p_tienda_tra = this.GridDS_VT_TT.map(a=>parseInt(a.TIEN)).reduce((sum,b)=> sum + b, 0)
    },
    async tsbtnNhapMoi_Click(){
        this.SetButton(1)
    },
    async tsbtnGhiLai_Click(){
        await this.GhiLai()
    },
    async tsbtnHuyBo_Click(){
        this.SetButton(0)
    },
    async tsbtnXoa_Click(){
        await this.Xoa()
    },
    async frmThanhToanDH_Load(){
        this.$root.showLoading(true)
        await this.Load_cbo()
        this.SetButton(0)
        await this.HienThi_DSTT()
        this.$root.showLoading(false)
    },
    ckbCheck_CheckedChanged(){

    },
    async btn_insert_Click(){
        // try {
            
            this.dongy = true
            var data_vt_dh = this.$refs.TableDS_VT_DonHang.getSelectedRecords()
            if (data_vt_dh.length){
                this.$root.showLoading(true)
                for (let i =0; i < data_vt_dh.length;i++ ){
                    let q = this.GridDS_VT_TT.find(a=> a.CTDH_ID.toString() == data_vt_dh[i].CTDH_ID.toString())
                    if (!q){
                        let s1 = 0
                        if (data_vt_dh[i].SL_THANHTOAN.toString() != "0") {
                            s1 = parseInt(data_vt_dh[i].SL_THANHTOAN)
                        }
                        else{
                            s1 = parseInt(data_vt_dh[i].SLTTCL)
                        }
                        await this.TaoBackUpDSVatTu({
                            type: 'themmoi',
                            params: {
                                "soLuong" :  s1 ,
                                "tien" : Math.round(parseInt(data_vt_dh[i].DONGIA) * s1)  ,
                                "vat" : Math.round(parseInt(data_vt_dh[i].DONGIA) * s1 * 0.1),
                                "ctdhId": data_vt_dh[i].CTDH_ID,
                                "phieuttId": this.phieutt_id,
                                "donGia": data_vt_dh[i].DONGIA
                            }
                        })
                    }
                    else{
                        let s1 = 0
                        if (data_vt_dh[i].SL_THANHTOAN.toString() != "0") {
                            s1 = parseInt(data_vt_dh[i].SL_THANHTOAN)
                        }
                        else{
                            s1 = parseInt(data_vt_dh[i].SLTTCL)
                        }
                        s1 = s1 + parseInt(q.SL_THANHTOAN)
                        await this.TaoBackUpDSVatTu({
                            type: 'capnhat',
                            params: {
                                "soLuong" :  s1 ,
                                "tien" : Math.round(parseInt(data_vt_dh[i].DONGIA) * s1)  ,
                                "vat" : Math.round(parseInt(data_vt_dh[i].DONGIA) * s1 * 0.1),
                                "ctdhId": data_vt_dh[i].CTDH_ID,
                                "phieuttId": this.phieutt_id,
                                "donGia": data_vt_dh[i].DONGIA
                            }
                        })
                    }
                } 
                this.$root.showLoading(false)  // bổ sung API load chậm chưa update kịp grid -> view còn show và có thể click -> double click/API
            }
            await this.Load_DS_VT()
            if ((await this.CapNhatTrangThaiDH({donhang_id:this.temp_id})) == "0"){
                this.$toast.error("Cập nhật trạng thái đơn hàng bị lỗi")
            }
        // } catch (error) {
        //     this.$toast.error("Lỗi ghi thêm chi tiết thanh toán")            
        // }
    },
    async btn_del_Click(){
        // try {
            this.dongy = true;
            this.$root.showLoading(true)
            this.$refs.TableDS_VT_TT.getSelectedRecords().forEach(async (dr)=> await this.XoaChungTuPhieuTT({
                phieutt_id: dr.PHIEUTT_ID,
                ctdh_id : dr.CTDH_ID
            }))
            
            if ((await this.CapNhatTrangThaiDH({donhang_id:this.temp_id})) == "0"){
                this.$toast.error("Cập nhật trạng thái đơn hàng bị lỗi")
            }
            await this.Load_DS_VT()
            this.$root.showLoading(false)
        // } catch (error) {
        //     this.$toast.error("Lỗi ghi xóa chi tiết thanh toán")
        // }
        
    },
    grvDS_VT_DonHang_ValidateRow(){

    },
    grvDS_VT_DonHang_CellValueChanged(args){
        let changes = this.$refs.TableDS_VT_DonHang.getBatchChanges() 

        if (changes.changedRecords.length == 0) { 
            return
        }

        if(args.columnName == "SL_THANHTOAN" ){
            if(changes.changedRecords[0].SLTTCL == 0 ){
                this.$toast.error("Số lượng thanh toán đã hết")
                this.$refs.TableDS_VT_DonHang.grid.refreshColumns()
                return
            }
            if(changes.changedRecords[0].SL_THANHTOAN <= 0 ){
                changes.changedRecords[0].SL_THANHTOAN = parseInt(changes.changedRecords[0].SL_CHUAN)
                this.$toast.error("Số lượng thanh toán phải lớn hơn 0!")
                this.$refs.TableDS_VT_DonHang.grid.refreshColumns()
            }else if(changes.changedRecords[0].SL_THANHTOAN > changes.changedRecords[0].SLTTCL){
                changes.changedRecords[0].SL_THANHTOAN = parseInt(changes.changedRecords[0].SL_CHUAN)
                this.$toast.error("Số lượng  thanh toán <= Số chưa thanh toán")
                this.$refs.TableDS_VT_DonHang.grid.refreshColumns()
            }   
        }

        this.$refs.TableDS_VT_DonHang.batchUpdate(changes)
        
    },
    cellEdit: function (args) {
        if (args.columnName === "TotalCost") {
            args.cancel = true;
        }
    },
    grvDS_VT_TT_CellValueChanged(args){
      
        var TableDS_VT_TT = this.$refs.TableDS_VT_TT
        let changes = TableDS_VT_TT.getBatchChanges() 

        if (changes.changedRecords.length == 0) { 
            return
        }

        if(args.columnName == "SL_THANHTOAN" ){
            if(changes.changedRecords[0].SL_THANHTOAN <= 0 ){
                changes.changedRecords[0].SL_THANHTOAN = parseInt(changes.changedRecords[0].SL_CHUAN)
                this.$toast.error("Số lượng thanh toán phải lớn hơn 0!")
                this.$refs.TableDS_VT_TT.grid.refreshColumns()
            }else if(changes.changedRecords[0].SL_THANHTOAN > changes.changedRecords[0].SL_CHUAN){
                changes.changedRecords[0].SL_THANHTOAN = parseInt(changes.changedRecords[0].SL_CHUAN)
                this.$toast.error("Số lượng thanh toán khồng được  lớn hơn số lượng chuẩn!")
                this.$refs.TableDS_VT_TT.grid.refreshColumns()
            }            
        }else if(args.columnName == "DONGIA"){
            if(changes.changedRecords[0].DONGIA < 0 ){
                changes.changedRecords[0].DONGIA = 0
                this.$toast.error("Đơn giá phải lớn hơn 0!")
                this.$refs.TableDS_VT_TT.grid.refreshColumns()
            }else{
                changes.changedRecords[0].TIEN = parseInt(changes.changedRecords[0].DONGIA) * parseInt(changes.changedRecords[0].SL_THANHTOAN)
                changes.changedRecords[0].VAT = changes.changedRecords[0].TIEN / 10
                this.$refs.TableDS_VT_TT.grid.refreshColumns()
            }
        }else if(args.columnName == "VAT"){
            if(changes.changedRecords[0].VAT < 0 ){
                changes.changedRecords[0].VAT = 0
                this.$toast.error("VAT phải lớn hơn 0!")
                this.$refs.TableDS_VT_TT.grid.refreshColumns()
            }
        }
        TableDS_VT_TT.batchUpdate(changes)       
       
    },
    async vbtnSave_MouseClick(){
        if (this.enable.tsbtnNhapMoi && this.enable.tsbtnGhiLai){
            // try {
                this.dongy = true;
                var dt = this.GridDS_VT_TT
                var a = this.$refs.TableDS_VT_TT.getSelectedRecords()
                if (!a.length){
                    this.$toast.error("Không có dữ liệu thay đổi để cập nhật")
                    return
                }
                await this.$bvModal.msgBoxConfirm('"Bạn có muốn cập nhật dữ liệu không ?"', {
                        "okVariant": 'success',
                        "okTitle": 'Đồng ý',
                        "cancelTitle": 'Hủy',
                        "centered": true,
                        })
                        .then(async(value) => {
                            if (value){
                                for (let dr of this.$refs.TableDS_VT_TT.getSelectedRecords()){
                                    await this.TaoBackUpDSVatTu({
                                        type: 'capnhat',
                                        params: {
                                            "soLuong" :  dr.SL_THANHTOAN ,
                                            "tien" : dr.TIEN ,
                                            "vat" : dr.VAT,
                                            "ctdhId": dr.CTDH_ID,
                                            "phieuttId": this.phieutt_id,
                                            "donGia": 0 // API dùng chung body vs insert
                                        }
                                    })
                                }
                                await this.Load_DS_VT()
                                let resCapNhat = await this.CapNhatTrangThaiDH({donhang_id: this.donhang_id})
                                if (resCapNhat.toString() == "0"){
                                    this.$toast.error("Cập nhật trạng thái đơn hàng bị lỗi")                
                                }
                                else{
                                    this.$toast.success("Cập nhật dữ liệu thành công")     
                                }
                            }
                        })
                        .catch(err => {
                            // An error occurred
                        })
                

            // } catch (error) {
                
            // }
        }
    },
    grvDS_VT_TT_RowStyle(){

    },
    async vbtnRefresh_MouseClick(){
        if (this.enable.tsbtnNhapMoi && this.enable.tsbtnGhiLai){
            await this.getDSVatTuThanhToan({
                phieutt_id: this.phieutt_id
            })
        }
    },
    grvDS_VT_DonHang_CustomSummaryCalculate(){

    },
    grvDS_VT_TT_CustomSummaryCalculate(){

    },
    async btnTimKiem_Click(){
        this.$refs.popup_frmTraCuuHopDong.show()
        let f = this.$refs.frmTraCuuHopDong

        f.resetParentForm()
        f.params.p_donvi_dk = this.$auth.getDonViID()
        f.enable.cboDviDK = false
        f.readonly.ckbDH_TimKiem = true
        f.readonly.ckbHDTimKiem = true

        f.params.p_ckb_tracuuhopdong = false
        f.params.p_ckb_tracuudonhang = true
        f._owner_TT = true
        await f.frmTraCuuHopDong()
        // f._owner_TT = this
    },
    grvDS_VT_TT_ShownEditor(){

    },
    grvDS_VT_DonHang_ShownEditor(){

    },
    async Open(){
        this.$refs.popup_frmThanhToanDH.show()
    },
    async Close(){
        await this.$parent.closeThanhToanDonHang()
        this.$refs.popup_frmThanhToanDH.hide()
    },
    async dialogOpen() {
      this.loading(true);
    //   this.clearData();
        this.resetData()
        await this.frmThanhToanDH_Load()
      this.loading(false)
    },
    // async TimKiem_Test(){
    //     this.donhang_id = this.p_ma_donhang
    //     await this.HienThi_DSTT()
    // }
    resetData(){
        this.GridDS_ThanhToan = []
        this.GridDS_VT_TT = []
        this.GridDS_VT_DonHang = []
        this.dvi_id = "" //frmTraCuuHopDong
        this.cboDonHang = []
        this.cboHinhThucTra = []
        this.cboNVThuNgan = []
        this.cboDonHang_Filter = []
        this.cboHinhThucTra_Filter = []
        this.cboNVThuNgan_Filter = []
        this.DSDonViTinh_Filter = []
    },
    async Close_popup_frmTraCuuHopDong(){
        var frm = this.$refs.frmTraCuuHopDong
        if (frm.chapnhan){
           this.params.p_ma_donhang = this.vdh_tracuu_id.toString() || ""
        }
    }

  },
    created: async function() {
    // this.clearData();
    // await this.frmThanhToanDH_Load()
  },
  watch: {
    
  },
  destroyed () {
  }
};
</script>
<style>
  .thanhtoan-donhang .e-grid tr.e-emptyrow td {
      text-align: center !important;
    }
</style>