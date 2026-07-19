<template src="./SearchAccount.html"></template>
<style scoped src="./SearchAccount.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import SearchAccountAPI from './SearchAccountAPI'
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
export default {
  components: { breadcrumb,ExportDataModal },
  name: 'SearchAccount',
  props: [ 'isPopup','vma_tb','vdichvuvt_id','vISKHDN' ],
  mounted () {
    if(this.vma_tb) this.ma_tb = this.vma_tb;
    if(this.vdichvuvt_id) {
      this.dichvuvt_id = this.vdichvuvt_id;
      this.listbox.dichvu_vienthong.value = this.vdichvuvt_id;
      this.listbox.dichvu_vienthong.isEnabled = true;
    }
    if(this.vISKHDN) this.ISKHDN = this.vISKHDN;
    else if (this.$route.query.iskhdn) this.ISKHDN = this.$route.query.iskhdn;
    SearchAccountAPI.get_dichvu_vienthong(this.axios, {}).then(
        (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.dichvu_vienthong.list = response.data.data;
            this.filterbox.dichvu_vienthong.list = response.data.data;
            if(this.listbox.dichvu_vienthong.list && this.listbox.dichvu_vienthong.list.length>0)
              this.listbox.dichvu_vienthong.value = this.listbox.dichvu_vienthong.list[0].ID;
            if(this.filterbox.dichvu_vienthong.list && this.filterbox.dichvu_vienthong.list.length>0) {
              this.filterbox.dichvu_vienthong.value = this.filterbox.dichvu_vienthong.list[0].ID;
              this.filterServiceChanged();
            }
          }
        }
      );
    // nhomId: 2 = KHCN, 98 = KHDN, 100 = ????
    SearchAccountAPI.get_kieu_taikhoan(this.axios, {params:{nhomId:(this.ISKHDN?98:2)}}).then((response) => {
      if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.dk_tracuu.list = response.data.data;
        if(this.listbox.dk_tracuu.list && this.listbox.dk_tracuu.list.length>0) {
          this.listbox.dk_tracuu.dk1.value = this.listbox.dk_tracuu.list[0].ID;
          this.listbox.dk_tracuu.dk2.value = this.listbox.dk_tracuu.list[0].ID;
          this.listbox.dk_tracuu.dk3.value = this.listbox.dk_tracuu.list[0].ID;
        }
        //this.filterbox.dichvu_vienthong.list = response.data.data;
      }
    });
    //donvi_dl_id -> đơn vị quản lý (phân quyền đơn vị của người dùng)
    SearchAccountAPI.get_donvi_ql_tt(this.axios, {}).then((response) => {
      if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.donvi_quanly.list = response.data.data;
        if(this.listbox.donvi_quanly.list && this.listbox.donvi_quanly.list.length>0)
          this.listbox.donvi_quanly.value = this.listbox.donvi_quanly.list[0].ID;
      }
    });
  },
  destroyed () {},
  computed: {
    searchCondition() {
      var dieukiens = [];
      for(var i=1;i<=3;i++)
        if(this.listbox.dk_tracuu['dk'+i].isEnabled && this.listbox.dk_tracuu['dk'+i].value && this.listbox.dk_tracuu['dk'+i].value_text)
          dieukiens.push({dieukien_id:this.listbox.dk_tracuu['dk'+i].value,dieukien_value:this.listbox.dk_tracuu['dk'+i].value_text});
      return {
        dichvu_vienthong_id: (this.listbox.dichvu_vienthong.isEnabled && this.listbox.dichvu_vienthong.value)?this.listbox.dichvu_vienthong.value:0,
        donvi_id:(this.listbox.donvi_quanly.isEnabled && this.listbox.donvi_quanly.value)?this.listbox.donvi_quanly.value:0,
        ma_kh:this.maKH,
        dieukiens:dieukiens,
      };
    }
  },
  data () {
    return {
      header: {
        title: 'TRA CỨU DANH BẠ',
        list: [
          { name: 'Tra cứu danh bạ', link: { name: 'Ui.cards' } },
          {
            name: 'Tra cứu phiếu thanh toán theo ngày',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      ISKHDN: false,
      ma_tb: '',
      so_ao: '',
      dichvuvt_id: 0,
      maKH: "",
      listbox: {
        dichvu_vienthong:{
          list:[],
          value: "",
          isEnabled:false,
        },
        donvi_quanly: {
          list:[],
          value: "",
          isEnabled:false,
        },
        dk_tracuu: {
          dk1: {
            value:"",
            value_text: "",
            isEnabled:true
          },
          dk2: {
            value:"",
            value_text: "",
            isEnabled:false
          },
          dk3: {
            value:"",
            value_text: "",
            isEnabled:false
          }
        }
      },
      filterbox: {
        dichvu_vienthong:{
          list:[],
          value: "",
          isEnabled:false,
        },
        loaihinh_thuebao:{
          list:[],
          value: "",
          isEnabled:false,
        },
        isType:false,
      },
      gridbox: {
        khachhang: {
          list:[],
          value: "",
          // header: [{name:'stt',text:'STT'},{name:'ma_kh',text:'Mã KH'},{name:'makh_cu',text:'Mã KH cũ'},{name:'ma_hd',text:'Mã HĐ/PL'},{name:'ten_kh',text:'Tên khách hàng'},{name:'diachi_kh',text:'Địa chỉ khách hàng'},],
          cols: [
            {fieldName:'stt',headerText:'STT',width: "80" , allowFiltering: true,freeze:'Left' },
            {fieldName:'ma_kh',headerText:'Mã KH', allowFiltering: true,freeze:'Left' },
            {fieldName:'makh_cu',headerText:'Mã KH cũ',allowFiltering: true, freeze:'Left'},//format:"number",textAlign:"right",
            {fieldName:'ma_hd',headerText:'Mã HĐ/PL', allowFiltering: true },
            {fieldName:'ten_kh',headerText:'Tên khách hàng', allowFiltering: true ,  
              template:'<span style="font-weight: bold">${ten_kh}</span>'},
            {fieldName:'diachi_kh',width: "300",headerText:'Địa chỉ khách hàng', allowFiltering: true },
          ],
        },
        thanhtoan: {
          list:[],
          value: "",
          // header: [{name:'ma_tt',text:'Mã TT'},{name:'matt_cu',text:'Mã TT cũ'},{name:'matb_dd',text:'Mã đại diện'},{name:'mst',text:'MST'},{name:'ten_tt',text:'Tên thanh toán'},{name:'diachi_tt',text:'Địa chỉ thanh toán'},{name:'main_ghep',text:'Mã in ghép'},],
          cols: [
            {fieldName:'ma_tt',width: "100",headerText:'Mã TT', allowFiltering: true, freeze:'Left'},
            {fieldName:'matt_cu', width: "120",headerText:'Mã TT cũ', allowFiltering: true, freeze:'Left'},
            {fieldName:'matb_dd',width: "120",headerText:'Mã đại diện',allowFiltering: true, freeze:'Left'},
            {fieldName:'mst',width: "120",headerText:'MST', allowFiltering: true },
            {fieldName:'ten_tt',headerText:'Tên thanh toán', allowFiltering: true ,
            template:'<span style="font-weight: bold">${ten_tt}</span>'},
            {fieldName:'diachi_tt',headerText:'Địa chỉ thanh toán', allowFiltering: true },
            {fieldName:'main_ghep',headerText:'Mã in ghép', allowFiltering: true },
          ],
        },
        thuebao: {
          fulllist:[],
          list:[],
          value: "",
          header: [{name:'ma_tt',text:'Mã TT'},{name:'ma_tb',text:'Số máy/Acc'},{name:'ma_lt',text:'Số ảo'},{name:'ten_tb',text:'Tên thuê bao'},{name:'diachi_tb',text:'Địa chỉ thuê bao'},{name:'diachi_ld',text:'Địa chỉ lắp đặt'},{name:'loaihinh_tb',text:'Loại hình'},{name:'ngay_sddv',text:'Ngày SD'},{name:'trangthai_tb',text:'Trạng thái'},{name:'macuoc',text:'Mã cước'},{name:'',text:'Site ID'},{name:'',text:'Brand'},],
          cols: [
            {fieldName:'ma_tt',width: "120",headerText:'Mã TT', allowFiltering: true, freeze:'Left'},
            {fieldName:'ma_tb',width: "120",headerText:'Số máy/Acc', allowFiltering: true, freeze:'Left'},
            {fieldName:'ma_lt',width: "120",headerText:'Số ảo',allowFiltering: true, freeze:'Left'},
            {fieldName:'ten_tb',headerText:'Tên thuê bao', allowFiltering: true },
            {fieldName:'diachi_tb',width: "300",headerText:'Địa chỉ thuê bao', allowFiltering: true ,
            template:'<span style="font-weight: bold">${diachi_tb}</span>'},
            {fieldName:'diachi_ld',width: "300",headerText:'Địa chỉ lắp đặt', allowFiltering: true },
            {fieldName:'loaihinh_tb',headerText:'Loại hình', allowFiltering: true },
            {fieldName:'ngay_sddv',width: "120",headerText:'Ngày SD', allowFiltering: true },
            {fieldName:'trangthai_tb',headerText:'Trạng thái', allowFiltering: true },
            {fieldName:'macuoc',headerText:'Mã cước', allowFiltering: true },
            {fieldName:'',headerText:'Site ID', allowFiltering: true },
            {fieldName:'',headerText:'Brand', allowFiltering: true },
          ],
        },
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date(),
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close':this.popupClosed,
        'acceptChangeCTV':this.popupClosed,
        'acceptChangeNGT':this.popupClosed,
        'PopUpCongTacVien_Thoat':this.popupClosed,
      },
      popupComponentName: '',
      // clickcount: 0,
    }
  },
  methods: {

    filterServiceChanged() {
      SearchAccountAPI.get_loaihinh_thuebao(this.axios, {dvvt_id:this.filterbox.dichvu_vienthong.value}).then((response) => {
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.filterbox.loaihinh_thuebao.list = response.data.data;
          if(this.filterbox.loaihinh_thuebao.list && this.filterbox.loaihinh_thuebao.list.length>0)
            this.filterbox.loaihinh_thuebao.value = this.filterbox.loaihinh_thuebao.list[0].ID;
        }
      });
      this.filterThueBao();
    },
    SearchClient() {
      //SearchAccountAPI.get_khachang_theo_site_id(this.axios, {siteId:'Q01004F'}).then((response) => {
      //SearchAccountAPI.get_khachang_theo_matb(this.axios, {params:{maTB:'canh479',donViDLId:0,dichVuVTId:4}}).then((response) => {
      this.loading(true);
      SearchAccountAPI.get_tracuu(this.axios, this.searchCondition).then((response) => {
        this.gridbox.khachhang.list=[];
        this.gridbox.thanhtoan.list=[];
        this.gridbox.thuebao.fulllist=[];
        this.gridbox.thuebao.list=[];
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data.length>0) {
          this.gridbox.khachhang.list = response.data.data;
          for(var i=0; i<this.gridbox.khachhang.list.length;i++)
            this.gridbox.khachhang.list[i].stt = i+1;
          if(this.gridbox.khachhang.list && this.gridbox.khachhang.list.length>0)
            this.grdKhachHangChanged({rowData:this.gridbox.khachhang.list[0]});
          this.$toast.success("Tìm thấy "+this.gridbox.khachhang.list.length+" thuê bao thoả mãn điều kiện tìm!")
        }
        else {
          this.$toast.error("Không tìm thấy dữ liệu thoả mãn yêu cầu tìm kiếm!");
          this.gridbox.khachhang.list = [];
        }
      }).finally(()=> this.loading(false));
    },
   async grdKhachHangChanged(item) {
      console.log("item change", item);
      let obj ={};
      if(!item) return;
      if(item.rowData)
        obj = item.rowData;
      else
        obj = item
      
      // this.clickcount++;
      // this.loading(true);
      this.gridbox.khachhang.value = obj;

      this.maKH = obj.ma_kh;
      await this.loadThanhToan(obj);
      await this.loadThueBao(obj);
      
    },
    grdThueBaoChanged(obj) {
      if(!obj) return;
      // console.log("vào đây này", obj)
      this.gridbox.thuebao.value = obj;
      this.ma_tb = obj.ma_tb;
      this.so_ao = obj.ma_lt;
      // this.maKH = obj.ma_kh
    },
    grdThanhToanChanged(obj) {
      if(!obj) return;
      this.gridbox.thanhtoan.value = obj;
      this.ma_tb = obj.ma_tb;
      this.so_ao = obj.ma_lt;
      // this.maKH = obj.ma_kh
    },
    async loadThanhToan(obj) {
      if(!obj) return;
      await SearchAccountAPI.get_dbtt_theo_khid(this.axios, {params:{khachHangId:obj.khachhang_id}}).then((response) => {
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.gridbox.thanhtoan.list = response.data.data;
          if(this.gridbox.thanhtoan.list && this.gridbox.thanhtoan.list.length>0)
            this.grdThanhToanChanged(this.gridbox.thanhtoan.list[0]);
        }
        else this.gridbox.thanhtoan.list = [];
      });
    },
    async loadThueBao(obj) {
      if(!obj) return;
      await SearchAccountAPI.get_dbtb_theo_khid(this.axios, {params:{khachHangId:obj.khachhang_id}}).then((response) => {
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.gridbox.thuebao.list = response.data.data;
          this.gridbox.thuebao.fulllist = response.data.data;
          this.filterThueBao();
        }
        else {
          this.gridbox.thuebao.list = [];
          this.gridbox.thuebao.fulllist = [];
        }
      });
    },
    filterThueBao() {
      let list = this.gridbox.thuebao.fulllist;
      if(this.filterbox.dichvu_vienthong.isEnabled && this.filterbox.dichvu_vienthong.value)
        list = list.filter(a=>a.dichvuvt_id==this.filterbox.dichvu_vienthong.value);
      if(this.filterbox.loaihinh_thuebao.isEnabled && this.filterbox.loaihinh_thuebao.value)
        list = list.filter(a=>a.loaitb_id==this.filterbox.loaihinh_thuebao.value);

      this.gridbox.thuebao.list = list;
      if(this.gridbox.thuebao.list && this.gridbox.thuebao.list.length>0)
        this.grdThueBaoChanged(this.gridbox.thuebao.list[0]);//this.gridbox.thuebao.value = this.gridbox.thuebao.list[0];
    },
    closeForm() {
      this.$emit('form-close',{ ma_tb:this.gridbox.thuebao.value.ma_tb,ma_kh:this.gridbox.khachhang.value.ma_kh, ma_tt:this.gridbox.thanhtoan.value.ma_tt,thuebao:this.gridbox.thuebao.value, khachhang:this.gridbox.khachhang.value}); //THANGDM1 changed 2021-11-04 them thong tin
    },
    terminateForm() {
      this.$emit('form-close');
    },
    async tsbtnNgayTruyThu_Click() {
        var rs1 = await this.$root.context.get("/web-tracuu/danhba/lay_tt_hdtb_truythu_tbid?thuebaoId="+this.gridbox.thuebao.value.thuebao_id, {});
        var upperCase = function UpperCasePropertyList(obj) {
          return obj.map( function( item ){
              for(var key in item){
                  var upper = key.toUpperCase();
                  // check if it already wasn't uppercase
                  if( upper !== key ){
                      item[ upper ] = item[key];
                      delete item[key];
                  }
              }
              return item;
          });
        };
        var ds = upperCase(rs1.data);
        if(ds && ds.length>0) {
          this.popupComponent = ()=>import('@/modules/contract/setup/InstallNewSubs/Popups/popupChonGrid.vue');
          this.popupComponentName = "popupTraCuu_CuocTruyThu";
          this.popupComponentData = {"ds":ds,cols:[
            {fieldName: 'STT', headerText: 'Stt', allowFiltering: false, allowSorting: false},
            {fieldName: 'MA_TB', headerText: 'Số máy/Account', allowFiltering: true, allowSorting: true},
            {fieldName: 'TEN_LOAIHD', headerText: 'Loại hợp đồng', allowFiltering: true, allowSorting: true},
            {fieldName: 'MA_GD', headerText: 'Mã giao dịch', allowFiltering: true, allowSorting: true},
            {fieldName: 'NGAY_HT', headerText: 'Ngày HT', allowFiltering: true, allowSorting: true},
            {fieldName: 'NGAY_TRUYTHU', headerText: 'Ngày truy thu', allowFiltering: true, allowSorting: true},
            {fieldName: 'NGUOI_CN', headerText: 'Người CN', allowFiltering: true, allowSorting: true},
            {fieldName: 'TEN_DV', headerText: 'Đơn vị CN', allowFiltering: true, allowSorting: true},
            {fieldName: 'NGAY_CN', headerText: 'Ngày CN', allowFiltering: true, allowSorting: true},
          ],keycol: "STT",allowExit:true,allowAccept:false,tieude:"Thông tin cước truy thu",tieudebang:"Thông tin cước truy thu",}
          this.Popup('popupSAComponents');
        }
        // DataSet ds = new DataSet();
        // ds = bangts.getDataFromSQL(str, "ngay_truythu");
    },
    grdKhachHangDoubleClick(obj) {
      if(!obj || !obj.rowData) return;
      obj = obj.rowData;
      // this.clickcount++;
      // this.loading(true);
      this.gridbox.khachhang.value = obj;
      
      var api1 = this.loadThanhToan(obj);
      var api2 = this.loadThueBao(obj);
      Promise.all([api1,api2,]).then(()=>{
        // this.loading(false);
        this.closeForm();
        // this.clickcount = 0;
      });
    },
    Popup(modalId) {
      this.$bvModal.show(modalId);
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId);
      this.popupComponent = null;
    },
    async popupClosed(val) {
      this.ClosePopup('popupSAComponents');
    },
    tsbtnXuatExcel_Click() {
      if(this.gridbox.khachhang.list && this.gridbox.khachhang.list.length>0)
        this.$refs.exportDataModal.showModal()
      else this.$root.$toast.error('Không có dữ liệu để xuất File!')
    },
  }
}
</script>
<style scoped>
.template_bold_text{
  font-weight: bold !important;
}
</style>