<template src="./UpdateCancelDemoReason.html">

 
</template>

<style src="./UpdateCancelDemoReason.scss"></style>
<script>
import gridview from './gridview.vue'
import breadcrumb from '@/components/breadcrumb'
import API from '../../API/API_Admin'
import bankmodal from '../../category/Bank/Bankmodal.vue'
import moment from 'moment'
export default {
  components: { breadcrumb,gridview,bankmodal },
  name: 'UpdateCancelDemoReason',
  mounted () {
     API.get_ds_nhom_huy(this.axios, {params:{loaild_id:'3', loaitb_id:this.phieu_huy.loaitb_id,  kieu:'1'}}).then(
        (response) => { 
           if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            
            this.listbox.nhom_huy.list = response.data.data;
            //this.filterbox.dichvu_vienthong.list = response.data.data;
          }
        }
      );

    // API.get_ds_lydo_huy(this.axios, {params:{loaild_id:'3', loaitb_id:this.phieu_huy.loaitb_id, kieu:'1', nhomhuy_id: '1'}}).then(
    //     (response) => {
    //       if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
    //         this.listbox.lydo_huy.list = response.data.data;
    //         //this.filterbox.dichvu_vienthong.list = response.data.data;
    //       }
    //     }
    //   );

     

      var date = new Date();
      this.date_month = (date.getMonth()+1)+'/'+date.getFullYear();
      
  },
  //end mounted
  
  destroyed () {},
  data () {
    return {
       dateconfig: {
        altFormat: 'm/Y',
        altInput: true,
        dateFormat: 'm/Y',
        allowInput: true,
      },
      date_enable: false,
      ghichu_huy:"",
        date_month:"",
        phieu_huy:{
          loaitb_id:""
        },

       listbox: {
        ds_phieu:{
          list:[],
          header:   [{fieldName:'STT',headerText:'Số TT',width: 90},{fieldName:'ma_tb',headerText:'Số máy/Acc'},{fieldName:'ten_tb',headerText:'Tên TB'},{fieldName:'diachi_tb',headerText:'Địa chỉ TB'},
                {fieldName:'loaihinh_tb',headerText:'Loại hình TB'},{fieldName:'nguoi_cn',headerText:'Người lập HĐ'},{fieldName:'songay',headerText:'Số ngày'},
                // {fieldName:'ngay_bd',headerText:'Ngày BĐ',format: {type: 'date', format: 'dd/MM/yyyy'}, type: 'date'},
                // {fieldName:'ngay_tb',headerText:'Ngày TB',format: {type: 'date', format: 'dd/MM/yyyy'}, type: 'date'},
                // {fieldName:'ngay_kt',headerText:'Ngày KT',format: {type: 'date', format: 'dd/MM/yyyy'}, type: 'date'},
                {fieldName:'ngay_bd',headerText:'Ngày BĐ'},
                {fieldName:'ngay_tb',headerText:'Ngày TB'},
                {fieldName:'ngay_kt',headerText:'Ngày KT'},
                {fieldName:'ten_nvgt',headerText:'NV giới thiệu'},{fieldName:'dv_lhd',headerText:'ĐV lập HĐ'} ],
          value: "",
          isEnabled:false,
        },
         nhom_huy:{
          list:[],
          header:[],
          value: "",
          isEnabled:false,
        },
        lydo_huy:{
          list:[],
          header:[],
          value: "",
          isEnabled:false,
        }
      },
      
    }
  },



  methods:{
    load_ds_phieu_huy: function (event) {
        $('.overlay-common').addClass('show');

        API.get_ds_phieu_huy(this.axios,this.$root.token.getNhanVienID()).then((response) => {
       if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.ds_phieu.list = response.data.data;
            
            for(var i in this.listbox.ds_phieu.list){
              this.listbox.ds_phieu.list[i].STT=(parseInt(i)+1).toString();
              this.listbox.ds_phieu.list[i].ngay_bd=this.listbox.ds_phieu.list[i].ngay_bd?this.listbox.ds_phieu.list[i].ngay_bd.split(' ')[0]:null;
              this.listbox.ds_phieu.list[i].ngay_tb=this.listbox.ds_phieu.list[i].ngay_tb?this.listbox.ds_phieu.list[i].ngay_tb.split(' ')[0]:null;
              this.listbox.ds_phieu.list[i].ngay_kt=this.listbox.ds_phieu.list[i].ngay_kt?this.listbox.ds_phieu.list[i].ngay_kt.split(' ')[0]:null;
              
            } 
             	 	 	 	 	 	 		 	 	

              
        }
            
        else {
            this.listbox.ds_phieu.list = [];
        }
        this.phieu_huy.loaitb_id = "";
        });
    },

    load_ds_lydo_huy: function (event) {
        API.get_ds_lydo_huy(this.axios, {params:{loaild_id:'3', loaitb_id:this.phieu_huy.loaitb_id, kieu:'1', nhomhuy_id: this.listbox.nhom_huy.value}}).then(
        (response) => {
          if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.lydo_huy.list = response.data.data.map(x=>{return {id:x['lydohuy_id'],text:x['lydohuy']}});;
            this.listbox.lydo_huy.value = response.data.data[0].lydohuy_id;
            //this.filterbox.dichvu_vienthong.list = response.data.data;
          }
          else {
            this.listbox.lydo_huy.list  = [];
        }
        }
      );
    },

    grdChanged(obj) {
      this.phieu_huy = obj[0];

       API.get_ds_nhom_huy(this.axios, {params:{loaild_id:'3', loaitb_id:this.phieu_huy.loaitb_id,  kieu:'1'}}).then(
        (response) => { 
           if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            //console.log(response.data.data);
            this.listbox.nhom_huy.list = response.data.data.map(x=>{return {id:x['nhomhuy_id'],text:x['ten_nhom']}});
            this.listbox.nhom_huy.value = response.data.data[0].nhomhuy_id;
            this.load_ds_lydo_huy();
            //this.filterbox.dichvu_vienthong.list = response.data.data;
          }
          else{
            this.listbox.nhom_huy.list = [];
          }
        }
      );
    },

    lydo_huy(){
      //  if(confirm("Bạn chắc chắn muốn thực hiện không?")){
      //   this.$bvModal.show('bv-modal-example');
      //  }
      console.log(this.phieu_huy)
        if (this.phieu_huy.loaitb_id == ""){
          this.$toast.error("Bạn chưa chọn thuê bao");
          return;
        }
        this.date_enable = false;
      
       this.$confirm('Bạn chắc chắn muốn thực hiện không?', 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(async () => {
            this.$bvModal.show('bv-modal-example');
        
          })
    },

    capnhat_lydo_huy(){
        var date = new Date();

        var thang_ktck = null;
        if (this.date_enable){
          var date = moment(this.date_month, 'MM/YYYY')
          thang_ktck = date.format('YYYYMM');
          //thang_ktck = (date.getMonth()+1);
          // thang_ktck = moment(this.date_month).format('YYYYMM');
        }
          

        if (this.listbox.lydo_huy.value == "" ||this.listbox.lydo_huy.value == null ||this.listbox.lydo_huy.value == undefined){
          this.$toast.error("Bạn phải chọn lý do hủy");
          return;
        }
    
        var param = {
                      "hdtb_id": this.phieu_huy.hdtb_id,
                      "lydohuy_id": this.listbox.lydo_huy.value,
                      "thang_ktck": thang_ktck,
                      "ghichu_huy": this.ghichu_huy,
                      "donvi_id": this.$root.token.getDonViID(),
                      "nhanvien_id":this.$root.token.getNhanVienID()};

        API.capnhatld_huy_hd_dungthu(this.axios,param).then((response) => {
        console.log(response);
        if(response && response.data.error && response.data.error_code === 'BSS-00000000') 
        {
            this.$bvModal.hide('bv-modal-example');
            this.$toast.success('Cập nhật lý do hủy dùng thử thành công');
            this.ghichu_huy = "";
            this.date_enable = false;
            this.load_ds_phieu_huy();
              
        }
          else {
            this.$bvModal.hide('bv-modal-example');
            this.$toast.error('Cập nhật lý do hủy dùng thử lỗi');
          }
            
      });
    },

    filterMedia: function () {
        if (!this.date_enable)
          this.date_enable = true;
        else
           this.date_enable = false;
          
    },
    
  }
}
</script>
