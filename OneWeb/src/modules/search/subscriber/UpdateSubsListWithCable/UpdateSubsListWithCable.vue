<template src="./UpdateSubsListWithCable.html"></template>
<style src="./UpdateSubsListWithCable.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb';
import select2 from "@/components/Select2.vue";
import UpdateSubsListWithCableAPI from './UpdateSubsListWithCableAPI';
import gridview from './gridview';
import searchAccount from '@/modules/search/subscriber/SearchAccount/SearchAccount.vue'
//import VueElement from '../../DeclareLandline/components/VueElement.vue'

//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//

export default {
  components: { breadcrumb,gridview, select2,searchAccount },
  name: 'UpdateSubsListWithCableAPI',
  mounted () {
    
    this.listbox.ketquatimkiem.list = [];
    this.listbox.ketquatimkiem.header = [{name:'thuebao_id',text:'Số máy/Account'},{name:'',text:'Mã truy nhập'},{name:'',text:'Mã đôi cáp'},{name:'',text:'Loại hình'},{name:'',text:'Tên thuê bao'},{name:'',text:'Địa chỉ LĐ'}]

  },
  destroyed () {},
  data () {
    return {
      header: {
        title: 'Cập nhật danh sách thuê bao cùng đôi cáp',
        list: [
          { name: 'Nghiệm thu báo hỏng', link: { name: 'Ui.cards' } },
          { name: 'Cập nhật danh sách thuê bao cùng đôi cáp', link: { name: 'Ui.cards' } },
        ]
      },
      txtTraCuu: "",
      matn: "",
      statusSuaTN: false,
      statusTaoDC: false,
      statusGhepma: false,
      listbox: {
        thuebao:{
          list:[],
          value: "",
          isEnabled:false,
        },
        thuebao1:{
          list:[],
          value: "",
          isEnabled:false,
        },
        ds_tt_so: {
          list:[],
          value: "",
          isEnabled:false,
        },
        ds_kieu_so: {
          list:[],
           value: "",
          isEnabled:false,
        },
        ds_host: {
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
        },
        ketquatimkiem: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
        },
        ketquatimkiem1: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
        },
        ketquatimkiem2: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
        },
        ketquatimkiem_temp: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
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
    // Set kieu nút
      SetButton(kieu) {
        if (kieu == 1) {
          this.statusSuaTN = true;
          this.statusTaoDC = true;
        }
        if (kieu == 2) {
          this.statusGhepma = true;
        }
        if (kieu == 3) {
          this.statusGhepma = false;
          this.statusSuaTN = false;
          this.statusTaoDC = false;
        }
      },
    // Click tìm kiếm 
     btnSearchClicked() {
      this.SetButton(3);
      this.listbox.ketquatimkiem.list = "";
      this.listbox.ketquatimkiem1.list = "";
      var pdata = {};
      this.txtTraCuu = this.txtTraCuu.trim();
      pdata.vthuebao_id = this.txtTraCuu;
      pdata.vkieu = 1;
      if (this.txtTraCuu == "") {
        this.$toast.error("Vui lòng nhập số máy/Account");
        return;
      }
      UpdateSubsListWithCableAPI.tracuu(this.axios, pdata).then((response) => {
            if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data){
            this.SetButton(1);
            this.listbox.thuebao.list = response.data.data;
            this.listbox.ketquatimkiem.list = response.data.data;
	    this.listbox.ketquatimkiem1.list = this.listbox.ketquatimkiem.list[0];
            this.listbox.ketquatimkiem_temp.list=this.listbox.ketquatimkiem.list;
            this.listbox.ketquatimkiem.header = [{name:'thuebao_id',text:'Số máy/Account'},{name:'matb_tn',text:'Mã truy nhập'},{name:'madoicap',text:'Mã đôi cáp'},{name:'loaihinh_tb',text:'Loại hình'},{name:'ten_tb',text:'Tên thuê bao'},{name:'diachi_ld',text:'Địa chỉ LĐ'}]
          }
      });
    },
    grdKhachHangChanged(obj) {
      this.listbox.ketquatimkiem.value = obj;
      this.listbox.ketquatimkiem1.list = obj;
    },
    // Tạo đôi cáp mới
    AddDc(){
      var pdata = {};
      pdata.vdsthuebao_id = this.listbox.ketquatimkiem1.list.thuebao_id;
      console.log(pdata);
      UpdateSubsListWithCableAPI.tao_cap_moi(this.axios, {}).then((response) => {  
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            console.log(response.data);
            this.$toast.success("Cập nhật thông tin vào danh bạ thành công!");
        }
      });
    },
    UpdateTB(){
      var matn = this.matn.trim();
      if(matn==""){
        this.$toast.error("Vui lòng nhập mã truy nhập muốn thay đổi");
      }
      var pdata = {};
      pdata.vthuebao_id = this.listbox.ketquatimkiem1.list.thuebao_id;
      pdata.vmatb_tn = this.matn.trim();
      UpdateSubsListWithCableAPI.sua_ma_truy_nhap(this.axios, pdata).then((response) => {  
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            console.log(response.data);
            this.$toast.success("Cập nhật thông tin vào danh bạ thành công!");
        }
      });
      this.btnSearchClicked();
    },
    // Ghép mã đôi cáp
    GhepDC(){
      UpdateSubsListWithCableAPI.ghep_doi_ma_cap(this.axios, {}).then((response) => {  
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
            console.log(response.data);
            this.$toast.success("Ghép đôi mã cáp thành công");
        }
      });
    },
  },
};
</script>
