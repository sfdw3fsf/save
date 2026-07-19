<template src="./SearchSubcriberIMSNew.html"></template>
<style src="./SearchSubcriberIMSNew.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import SearchSubcriberIMSNewAPI from './SearchSubcriberIMSNewAPI'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: { breadcrumb },
  name: 'SearchSubcriberIMSNewAPI',
  mounted () {
    //load ds loại hình thuê bao
    SearchSubcriberIMSNewAPI.lay_loai_hinh_tb(this.axios, {"vdichvuvt_id": 11}).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.loai_hinh.list = response.data.data;
        }
      }
    );
    var input = {};
      input.p_tentat = this.$root.token.getMaTinh();
      SearchSubcriberIMSNewAPI.getdauso(this.axios, input).then((response) => {
        // console.log(response.data.data[0].dauso_ims);
        this.Matinh = response.data.data[0].dauso_ims;
        console.log(this.Matinh);
      })
  },
  data () {
    return {
      header: {
        title: 'Tra cứu thông tin IMS',
        list: [
          { 
            name: 'Lắp đặt mới', 
            link: { name: 'Ui.cards' } 
          },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          },
        ]
      },
      TxtSoMay: "",
      TxtTrangThai: "",
      TxtTenTB: "",
      TxtNgaySD: "",
      TxtMatKhau: "",
      TxtDiaChi: "",
      TxtTongDai: "",
      TxtLog: "",
      TxtTTtracuu: "",
      Matinh: "",
      CheckMTAS: false,
      CheckHSS: false,
      CheckIPW: false,
      resultTab: 'dsPhieu',
      ketquatimkiem: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
      },
      listbox: {
        loai_hinh:{
          list:[],
          value: 62,
          isEnabled:false,
        },
      },
       config: {
                dateConfig: {
                    value: new Date(),
                    format: "dd/MM/yyyy",
                    max: new Date()
                }
            },
    }
  },
  methods: {
    btnSearchClicked() {
      this.ketquatimkiem.list = "";
      this.Clear();
      var pdata = {};
      if(this.TxtSoMay == ""){
        this.$toast.error('Bạn chưa nhập số máy!');
        this.TxtLog = "Bạn chưa nhập số máy!"
      }
      else
      pdata.vma_tb = this.TxtSoMay.trim();
      SearchSubcriberIMSNewAPI.tracuu(this.axios, pdata).then((response) => {
        if(response.data.data.length == 0){
            this.TxtLog = "Không tìm thấy thông tin về thuê bao!"
            this.$toast.error('Không tìm thấy thông tin về thuê bao')
        }
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            this.NodeCore();
            this.ketquatimkiem.list = response.data.data;
            this.listbox.loai_hinh.value = this.ketquatimkiem.list[0].loaitb_id;
            this.TxtTrangThai = this.ketquatimkiem.list[0].trangthai;
            this.TxtTenTB = this.ketquatimkiem.list[0].ten_tb;
            this.TxtNgaySD = this.ketquatimkiem.list[0].ngaycap;
            this.TxtDiaChi = this.ketquatimkiem.list[0].diachi_ld;
            this.TxtMatKhau = this.ketquatimkiem.list[0].password;


            // var input = {};
            // input.msisdn = this.TxtSoMay;
            // input.node = "HSS";
            // input.type = "POTS";
            // SearchSubcriberIMSNewAPI.ttthuebao(this.axios, input).then((response) => {
            //         this.TxtTTtracuu = response.data.data;
            //     }
            //   );
        }
      });
    },
    Clear() {
      this.TxtTrangThai = "";
      this.TxtTenTB = "";
      this.TxtNgaySD = "";
      this.TxtDiaChi = "";
      this.TxtDiaChi = "";
      this.TxtTongDai = "";
      this.TxtMatKhau = "";
      this.listbox.loai_hinh.value = 1;
      this.TxtLog = "";
      this.TxtTTtracuu = "";
      this.CheckMTAS = false;
      this.CheckHSS = false;
      this.CheckIPW = false;
      document.getElementById('HSS').style.color = 'red'
      document.getElementById('MTSA').style.color = 'red'
      document.getElementById('IPW').style.color = 'red'
    },
    NodeCore() {
      // var input = {};
      // input.p_tentat = this.$root.token.getMaTinh();
      // SearchSubcriberIMSNewAPI.getdauso(this.axios, input).then((response) => {
      //   // console.log(response.data.data[0].dauso_ims);
      //   this.Matinh = response.data.data[0].dauso_ims;
      //   // console.log(this.Matinh);
      // })
      var pdata = {};
      // console.log(this.Matinh);
      // console.log("84"+this.Matinh+this.TxtSoMay);
      pdata.msisdn = "84"+this.Matinh+this.TxtSoMay;
      pdata.ll_product_name = "TS_MTAS_DISPLAY_SUBSCRIBER";
      pdata.operation = "Display";
      pdata.order_no = "12345";
      pdata.item_type = "Technical Service";
      pdata.subtype = "SIP";
      SearchSubcriberIMSNewAPI.lay_node(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data) {
          if(response.data.data[0].Status == 9){
            this.TxtTTtracuu = response.data.data[1].FULL_DETAIL;
            this.TxtTTtracuu = this.TxtTTtracuu.replace("'\'", "");
            // console.log(this.TxtTTtracuu);
            this.TxtTTtracuu = JSON.stringify(JSON.parse(this.TxtTTtracuu), null, "\t");
            // console.log(this.TxtTTtracuu);
            if(response.data.data[1].OC_STATUS == 1 ){
              this.TxtTongDai = this.TxtTongDai+"Mở gọi đi/"
            }
          else this.TxtTongDai = this.TxtTongDai+"Khoá gọi đi/"
            
          if(response.data.data[1].IC_STATUS == 1 ){
              this.TxtTongDai = this.TxtTongDai+"Mở gọi đến"
            }
          else this.TxtTongDai = this.TxtTongDai+"Khoá gọi đến"
            this.CheckMTAS = true;
            document.getElementById('MTSA').style.color = '#219653'
          }
        }
      });
      pdata.ll_product_name = "TS_ISM_DISPLAY_SUBSCRIBER";
      SearchSubcriberIMSNewAPI.lay_node(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data) {
          if(response.data.data[0].Status == 9){
            this.CheckHSS = true;
            document.getElementById('HSS').style.color = '#219653'
          }
        }
      });
      pdata.ll_product_name = "TS_IPW_DISPLAY_SUBSCRIBER";
      SearchSubcriberIMSNewAPI.lay_node(this.axios, pdata).then((response) => {
        if(response && response.data && response.data.data) {
          if(response.data.data[0].Status == 9){
            this.CheckIPW = true;
            document.getElementById('IPW').style.color = '#219653'
          }
        }
      });
    }
  },
};
</script>
