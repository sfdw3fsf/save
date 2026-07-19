<template src="./SearchAccount.html"></template>
<style scoped src="./SearchAccount.scss"></style>
<script>


import SearchAccountAPI from './SearchAccountAPI'
export default {
  components: {  },
  name: 'SearchAccount',
  props: [ 'isPopup' ],
  mounted () {
    SearchAccountAPI.get_dichvu_vienthong(this.axios, {}).then(
        (response) => {
          if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.listbox.dichvu_vienthong.list = response.data.data;
            this.filterbox.dichvu_vienthong.list = response.data.data;
          }
        }
      );
    // nhomId: 2 = KHCN, 98 = KHDN, 100 = ????
    SearchAccountAPI.get_kieu_taikhoan(this.axios, {params:{nhomId:2}}).then((response) => {
      if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.dk_tracuu.list = response.data.data;
        //this.filterbox.dichvu_vienthong.list = response.data.data;
      }
    });
    //donvi_dl_id -> đơn vị quản lý (phân quyền đơn vị của người dùng)
    SearchAccountAPI.get_donvi_ql_tt(this.axios, {}).then((response) => {
      if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.donvi_quanly.list = response.data.data;
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
            {fieldName:'stt',headerText:'STT', allowFiltering: true, },
            {fieldName:'ma_kh',headerText:'Mã KH', allowFiltering: true, },
            {fieldName:'makh_cu',headerText:'Mã KH cũ',allowFiltering: true, },//format:"number",textAlign:"right", 
            {fieldName:'ma_hd',headerText:'Mã HĐ/PL', allowFiltering: true },
            {fieldName:'ten_kh',headerText:'Tên khách hàng', allowFiltering: true },
            {fieldName:'diachi_kh',headerText:'Địa chỉ khách hàng', allowFiltering: true },
          ],
        },
        thanhtoan: {
          list:[],
          value: "",
          // header: [{name:'ma_tt',text:'Mã TT'},{name:'matt_cu',text:'Mã TT cũ'},{name:'matb_dd',text:'Mã đại diện'},{name:'mst',text:'MST'},{name:'ten_tt',text:'Tên thanh toán'},{name:'diachi_tt',text:'Địa chỉ thanh toán'},{name:'main_ghep',text:'Mã in ghép'},],
          cols: [
            {fieldName:'ma_tt',headerText:'Mã TT', allowFiltering: true, },
            {fieldName:'matt_cu',headerText:'Mã TT cũ', allowFiltering: true, },
            {fieldName:'matb_dd',headerText:'Mã đại diện',allowFiltering: true, },
            {fieldName:'mst',headerText:'MST', allowFiltering: true },
            {fieldName:'ten_tt',headerText:'Tên thanh toán', allowFiltering: true },
            {fieldName:'diachi_tt',headerText:'Địa chỉ thanh toán', allowFiltering: true },
            {fieldName:'main_ghep',headerText:'Mã in ghép', allowFiltering: true },
          ],
        },
        thuebao: {
          fulllist:[],
          list:[],
          value: "",
          dichvuvt_id:"",
          header: [{name:'MA_TT',text:'Mã TT'},{name:'MA_TB',text:'Số máy/Acc'},{name:'ma_lt',text:'Số ảo'},{name:'ten_tb',text:'Tên thuê bao'},{name:'diachi_tb',text:'Địa chỉ thuê bao'},{name:'diachi_ld',text:'Địa chỉ lắp đặt'},{name:'loaihinh_tb',text:'Loại hình'},{name:'ngay_sddv',text:'Ngày SD'},{name:'trangthai_tb',text:'Trạng thái'},{name:'macuoc',text:'Mã cước'},{name:'',text:'Site ID'},{name:'',text:'Brand'},],
          cols: [
            {fieldName:'MA_TT',headerText:'Mã TT', allowFiltering: true, },
            {fieldName:'MA_TB',headerText:'Số máy/Acc', allowFiltering: true, },
            {fieldName:'MA_LT',headerText:'Số ảo',allowFiltering: true, },
            {fieldName:'TEN_TB',headerText:'Tên thuê bao', allowFiltering: true },
            {fieldName:'DIACHI_TB',headerText:'Địa chỉ thuê bao', allowFiltering: true },
            {fieldName:'DIACHI_LD',headerText:'Địa chỉ lắp đặt', allowFiltering: true },
            {fieldName:'LOAIHINH_TB',headerText:'Loại hình', allowFiltering: true },
            {fieldName:'NGAY_SD',headerText:'Ngày SD', allowFiltering: true },
            {fieldName:'TRANGTHAI_TB',headerText:'Trạng thái', allowFiltering: true },
            {fieldName:'MUCCUOC',headerText:'Mã cước', allowFiltering: true },
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
      to_date: new Date()
    }
  },
  methods: {
    alert: function () {
      alert('Hello')
    },
    filterServiceChanged() {
      SearchAccountAPI.get_loaihinh_thuebao(this.axios, {dvvt_id:this.filterbox.dichvu_vienthong.value}).then((response) => {
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.filterbox.loaihinh_thuebao.list = response.data.data;
        }
      });
      this.filterThueBao();
    },
    async SearchClient() {
      //SearchAccountAPI.get_khachang_theo_site_id(this.axios, {siteId:'Q01004F'}).then((response) => {
      //SearchAccountAPI.get_khachang_theo_matb(this.axios, {params:{maTB:'canh479',donViDLId:0,dichVuVTId:4}}).then((response) => {
      this.loading(true);
      let dsID='';
      await SearchAccountAPI.get_tracuu(this.axios, this.searchCondition).then((response) => {
        this.gridbox.khachhang.list=[];
        this.gridbox.thanhtoan.list=[];
        this.gridbox.thuebao.fulllist=[];
        this.gridbox.thuebao.list=[];
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data.length>0) {
          this.gridbox.khachhang.list = response.data.data;                              
            let i=0;
          
            for (i=0;i<this.gridbox.khachhang.list.length;i++)
            {
                dsID=dsID+','+this.gridbox.khachhang.list[i].khachhang_id;
            }     
      
          //this.loadThueBao(this.gridbox.khachhang.list[0]);
        }
        else {
          this.$toast.error("Không tìm thấy dữ liệu thoả mãn yêu cầu tìm kiếm!");
          this.gridbox.khachhang.list = [];
        }
      }).finally(()=> this.loading(false));
      
      if (dsID!='')
      await this.loadDSThueBao(dsID.substring(1));
    },
    grdKhachHangChanged(obj) {
      this.loading(true);
      this.gridbox.khachhang.value = obj.ma_kh;
      var api1 = this.loadThanhToan(obj);
      var api2 = this.loadThueBao(obj);
      Promise.all([api1,api2,]).then(()=>{
        this.loading(false);
      });
    },
    grdThueBaoChanged(obj) {
      if (obj)
      {
      this.gridbox.thuebao.value = obj.MA_TB;
      this.gridbox.thuebao.dichvuvt_id = obj.DICHVUVT_ID;
      }
    },
    grdThanhToanChanged(obj) {
      this.gridbox.thanhtoan.value = obj.ma_tt;
    },
    async loadThanhToan(obj) {
      await SearchAccountAPI.get_dbtt_theo_khid(this.axios, {params:{khachHangId:obj.khachhang_id}}).then((response) => {
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.gridbox.thanhtoan.list = response.data.data;
        }
        else this.gridbox.thanhtoan.list = [];
      });
    },
    async loadDSThueBao(ds){
       try {
        
        const rs = await this.$root.context.get(
          `/web-thuno/api/thu-no/css/ds-thue-bao-theo-ds-khach-hang?dsKhachHangId=${ds}`
          );
          if (rs.data.length>0)
          {
            this.gridbox.thuebao.list = rs.data;          
            this.gridbox.thuebao.fulllist = rs.data;
            this.$toast.success("Tìm thấy "+this.gridbox.thuebao.list.length+" thuê bao thoả mãn điều kiện tìm!")
            this.filterThueBao();
          }
          else
          {
          this.gridbox.thuebao.list = [];
          this.gridbox.thuebao.fulllist = [];
          }  
          

          

      } catch (error) {}
      
    },
    async loadThueBao(obj) {
      await SearchAccountAPI.get_dbtb_theo_khid(this.axios, {params:{khachHangId:obj.khachhang_id}}).then((response) => {
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.gridbox.thuebao.list = response.data.data;
          console.log(this.gridbox.thuebao.list);
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
    },
    closeForm() {
      this.$emit('form-close',{ ma_tb:this.gridbox.thuebao.value,dichvuvt_id:this.gridbox.thuebao.dichvuvt_id,ma_kh:this.gridbox.khachhang.value, ma_tt:this.gridbox.thanhtoan.value}); //THANGDM1 changed 2021-11-04 them thong tin
    }
  }
}
</script>
