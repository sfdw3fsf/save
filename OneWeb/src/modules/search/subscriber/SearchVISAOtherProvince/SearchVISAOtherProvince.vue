<template src="./SearchVISAOtherProvince.html"></template>
<style src="./SearchVISAOtherProvince.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import API from './API'
import gridview from '@/components/Shared/gridview'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: {breadcrumb,VueFlatPickr,gridview,API },
  name: 'SearchVISAOtherProvince',
  props: [ 'accountProp' ],
  mounted () {
   
  },
  data () {
    return {
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput:true,
      },
      ngay_ht: new Date(),
      header: {
        title: 'TRA THÔNG TIN TÀI KHOẢN ADSL',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      lichsutacdong: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
          columns: [
          { fieldName: 'dateTime', headerText: 'Thời gian', allowFiltering: true, width: "120px"},
          { fieldName: 'userName', headerText: 'Admin', allowFiltering: true, width: "120px"},
          { fieldName: '', headerText: 'Tác động', allowFiltering: true, width: "150px" },
          { fieldName: 'actionDesc', headerText: 'Ghi chú', allowFiltering: true},       
        ],
      },
      listbox: {
        ds_data: {
          list: [],
          header: [],
          value: {}
        }
      
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date(),
      success_code: "0",
      LoaiTaiKhoan: '',
      Account: '',
      TinhTP: '',
      tinh_id: 0,
      MaTaiKhoan: '',
      NgayTao: '',
      NgayBanGiao: '',
      MaHopDong: '',
      TenKH: '',
      LoaiKH: '',
      SDT: '',
      DiaChiKH: '',
      Bras: '',
      DSLam: '',
      HinhThucTT: '',
      BrasIP: '',
      TrangThai: '',
      GoiCuoc: '',
      DichVuSD: '',
      NasportID: '',
      SlotPort: '',
      MaTruyCap: '',
      ThongTinTC: '',
      GhiChu: '',
      IPTinh: '',
      MatKhau: '',
    }
  },
  computed: {
    chuanhoaphuong: {
      get() { return this.isChuanHoaPhuong; },
      set(value) { this.isChuanHoaPhuong=value; 
      }
    }
  },
  methods: {
    ShowAlert(mesage,val)
    {
      if (val==0)
      {
        this.$toast.success(mesage);
      }
      else if (val==1)
      {
        this.$toast.warning(mesage);
      }
      else if (val==2)
      {
        this.$toast.error(mesage);
      }
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },

    tracuuADSL: async function () {
      this.Clear();
      var input = {};
      var output= [];
      var Account =[];
      var Customer =[];
      var AccountService = [];
      var log = [];
      if(this.Account == "" || this.Account == null){
        this.$toast.error('Bạn chưa nhập tài khoản');
        this.$refs.Account.focus();
      }
      else {
      this.Account = this.Account.trim();
      input.accountName = this.Account;
      await API.lookup_account(this.axios, input).then(async (response) => {
        if(response.data.errorCode != 0){
          this.$toast.error('Không tìm thấy thông tin tài khoản')
        }
        if(response.data.errorCode == 0) {
          output = response.data.data;
          // for (var mo of output)
          //   switch(mo['_class'])
          //   {
          //     case "AccountModel":
          //       Account = mo[0];
          //       break;
          //     case "CustomerModel":
          //       Customer = mo;
          //       break;
          //     case "AccountServiceModel":
          //       AccountService = mo[0];
          //       break;
          //     case "ReserveModel":
          //       // Gán reserve
          //       break;
          //   }
          // //hiển thị thông tin tài khoản
          // console.log(AccountService);
          this.LoaiTaiKhoan = output[0].serviceName;
          this.MaTaiKhoan = output[0].accountId;
          this.NgayBanGiao = output[0].lastModifiedDate;
          this.NgayTao = output[0].createDate;
        //  console.log(AccountService.customerId);
          this.TinhTP = output[1].city;
          var tinh_id = 0
          await API.fn_tt_tinh(this.axios, {
            type: 9,
            param: this.TinhTP
          }).then((response) => {
            if (response.data.error_code == "BSS-00000000") {
              tinh_id = response.data.data;
            }
          });
          this.tinh_id = tinh_id ? tinh_id : this.$root.token.getPhanVungID()
          //thông tin khách hàng
          this.MaHopDong="";
          var customerType;
          var input1 = {};
          input1.tinhthicong_id = this.tinh_id
          input1.in_id_neo="ten_dt";
          input1.in_table="doituong_vdc"; 
          input1.in_dk="where doituong_id = 1";
          API.map_id(this.axios, input1).then((response) => {
            if(response.data.error_code) {
              customerType= response.data.data;
              if (customerType=="-1")
                this.LoaiKH =""; 
              else
                this.LoaiKH = customerType;
            }
          });
          this.TenKH = output[1].firstName;
          this.DiaChiKH = output[1].address;
          this.SDT = output[1].phone;
          //thông tin dịch vụ
          var slot = "";
          var port = "";
          var vpi = "";
          var vci = "";
          console.log(output[2].attrTemp)
          var at = output[2].attrTemp
          
          
                this.MaHopDong = at.HD_VDC;

                console.log(this.MaHopDong);
                
              
                var bras1 = at['BRAS-ID'];

                console.log(bras1 );
                var input = {};
                input.tinhthicong_id = this.tinh_id
                input.in_id_neo="tenbras";
                input.in_table="bras"; 
                input.in_dk="where bras_id = '" + bras1 + "'";
                API.map_id(this.axios, input).then((response) => {

                  if(response.data.error_code) {
                    this.Bras = response.data.data;
                  }
                });
               
              
                this.BrasIP = at['BRAS-IPADDRESS'];
                
                var dslam_ = at['DSLAM-ID'];
                var input = { type: 3, param: dslam_, tinhthicong_id: this.tinh_id };
                API.fn_tt_dslam(this.axios, input).then((response) => {
                  if(response.data.error_code) {
                    this.DSLam = response.data.data;
                  }
                });
                
                slot = at.SLOT;
               
                port = at.PORT;
                
                vpi = at.VPI;
                
                vci = at.VCI;
                
                this.ThongTinTC = at.STRBILLING;
                
                
                this.NasportID = at['nas-port-id'];

                
                this.IPTinh = at['frame-ip-address'];
                
          }
          var tariffType = output[tariffType];
          if (tariffType != null && tariffType != undefined) {
            var input = {};
            input.tinhthicong_id = this.tinh_id
            input.in_id_neo="tenmuc";
            input.in_table="css.muccuoc"; 
            input.in_dk="where muccuoc_id_vdc =" + tariffType;
            API.map_id(this.axios, input).then((response) => {
              if(response.data.error_code) {
                this.HinhThucTT = response.data.data;
              }
            });
          }
          
          this.TrangThai = output[0].status;
          this.SlotPort = slot + "/" + port + "/" + vpi + "/" + vci;
          this.DichVuSD = output.serviceName;
          //hiển thị thông tin log object

        
      });
      }
    },
    
    btnTraCuu()
    {
      this.tracuuADSL();
    },
    showModal() {
        this.$bvModal.show('my-modal')
    },
    getPass: async function () {
          if(this.Account == "" || this.Account == null){
            this.$toast.error('Bạn chưa nhập tài khoản')
          }
          else {
          var input = {};
          this.Account = this.Account.trim();
          input.accountName = this.Account;
          await API.lay_ls_tac_dong(this.axios, input).then((response) => {
          //  console.log(response.data.data);
          if(response.data.errorCode != 0){
            this.$toast.error('Tài khoản không tồn tại');
          }
          else if(response.data.errorCode == 0) {
          this.showModal();
          console.log(response.data.data[0].accountPassword);
          this.MatKhau = response.data.data[0].accountPassword;
        }
      });
    }
    },
    btnXemMK()
    {
      this.getPass();
    },
    hideModal(){
      this.$bvModal.hide('lichsu')
    },
    Xem_LS_TacDong(){
      if(this.Account == "" || this.Account == null){
        this.$toast.error('Bạn chưa nhập tài khoản');
        this.$refs.Account.focus();
      }
      else{
      var input = {};
      this.Account = this.Account.trim();
      input.accountName = this.Account;
      API.lay_ls_tac_dong(this.axios, input).then((response) => {
        if(response && response.data && response.data.data) {
            this.lichsutacdong.list = response.data.data[4];
        }
      });
      }
    },
    Clear(){
      this.LoaiTaiKhoan = "";
      this.TinhTP = "";
      this.tinh_id = 0;
      this.MaTaiKhoan = "";
      this.NgayTao = "";
      this.NgayBanGiao = "";
      this.MaHopDong = "";
      this.TenKH = "";
      this.LoaiKH = "";
      this.SDT = "";
      this.DiaChiKH = "";
      this.Bras = "";
      this.DSLam = "";
      this.HinhThucTT = "";
      this.BrasIP = "";
      this.TrangThai = "";
      this.GoiCuoc = "";
      this.DichVuSD = "";
      this.NasportID = "";
      this.SlotPort = "";
      this.MaTruyCap = "";
      this.ThongTinTC = "";
      this.GhiChu = "";
      this.IPTinh = "";
      this.MatKhau = "";
      //this.$refs.Account.focus();
    },
    async init () {
      this.Account = this.accountProp
      await this.tracuuADSL()
    }
  },
};

</script>
