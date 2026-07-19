<template src="./ManagementUnit.html">
  
</template>

<script>
import gridview from '@/components/Shared/gridview'
import API from './API'

export default {
  components: { gridview },
  name: 'ManagementUnit',

  data(){
    return {
        donvi_ql:"",
        donvi_dagan_ql:"",
        donvi_chuagan_ql:"",
        tong_so_da_gan:0,
        tong_so_chua_gan:0,

        listbox: {
        don_vi_ql:{
          list:[],
          value: "",
        },
        loai_don_vi_ql:{
          list:[],
          value: "",
        },
        don_vi_ql_con:{
          list:[],
          value: "",
        },
        loai_don_vi_con:{
          list:[],
          value: "",
        },
        ds_donvi:{
          list:[],
          header:[{fieldName:'ten_dv',headerText:'Tên đơn vị'},{fieldName:'ten_dv_cha',headerText:'Đơn vị cha'}],
        },
        ds_donvi_dagan:{
          list:[],
          header:[{fieldName:'ten_dv',headerText:'Tên đơn vị'},{fieldName:'ten_dv_cha',headerText:'Đơn vị cha'}],
        },
        ds_donvi_chuagan:{
          list:[],
          header:[{fieldName:'ten_dv',headerText:'Tên đơn vị'},{fieldName:'ten_dv_cha',headerText:'Đơn vị cha'}],
        }
      },

    }

  },

  mounted(){
    API.lay_donvi_ql(this.axios,null).then(
       (response) => { 
           if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.don_vi_ql.list = response.data.data.map(x=>{return {id:x['donvi_id'],text:x['ten_dv']}});;
            this.listbox.don_vi_ql_con.list = response.data.data.map(x=>{return {id:x['donvi_id'],text:x['ten_dv']}});;
            //this.filterbox.dichvu_vienthong.list = response.data.data;
          }
        }
    );
   
   API.lay_loai_dv(this.axios,null).then(
          (response) => { 
            //console.log(response);
              if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                this.listbox.loai_don_vi_ql.list = response.data.data.map(x=>{return {id:x['loaidv_id'],text:x['ten_loaidv']}});;
                this.listbox.loai_don_vi_con.list = response.data.data.map(x=>{return {id:x['loaidv_id'],text:x['ten_loaidv']}});;
              }
            }
        );
   
  },

  methods:{

    load_ds_donvi(){
      if (this.listbox.don_vi_ql.value == null || this.listbox.loai_don_vi_ql.value == null)
        return;

      var data = {
          "donvi_cha_id": (this.listbox.don_vi_ql.value == null)?0:this.listbox.don_vi_ql.value,
          "loaidv_id": (this.listbox.loai_don_vi_ql.value == null)?0:this.listbox.loai_don_vi_ql.value
      }
      console.log(data);
      API.lay_ds_donvi_ql(this.axios,data).then(
        (response) => { 
            console.log(response);
            if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
              this.listbox.ds_donvi.list = response.data.data;
            }
            else
              this.listbox.ds_donvi.list = [];
          }
      );
    },

   

    load_ds_donvi_dagan(){
      var data = {
                    "donvi_ql_id": (this.donvi_ql.donvi_id==null||this.donvi_ql.donvi_id=="")?0:this.donvi_ql.donvi_id,
                    "loaidv_id": (this.listbox.loai_don_vi_con.value == null||this.listbox.loai_don_vi_con.value == "")?0:this.listbox.loai_don_vi_con.value,
                    "loaidv_ql_id": (this.listbox.loai_don_vi_ql.value == null||this.listbox.loai_don_vi_ql.value == "")?0:this.listbox.loai_don_vi_ql.value
                  }
      console.log("load_ds_donvi_dagan");
      console.log(data);
      API.lay_ds_donvi_dagan(this.axios,data).then(
        (response) => { 
            console.log(response);
            if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
              this.listbox.ds_donvi_dagan.list = response.data.data;
              this.tong_so_da_gan = this.listbox.ds_donvi_dagan.list.length;
            }
            else{
              this.listbox.ds_donvi_dagan.list = [];
              this.tong_so_da_gan = 0;
            }
          }
      );
    },

    load_ds_donvi_chuagan(){
      var data = {
                    "donvi_cha_id": (this.listbox.don_vi_ql_con.value==null||this.listbox.don_vi_ql_con.value=="")?0:this.listbox.don_vi_ql_con.value,
                    "donvi_ql_id": (this.donvi_ql.donvi_id==null||this.donvi_ql.donvi_id=="")?0:this.donvi_ql.donvi_id,
                    "loaidv_id": (this.listbox.loai_don_vi_con.value == null||this.listbox.loai_don_vi_con.value == "")?0:this.listbox.loai_don_vi_con.value,
                    "loaidv_ql_id": (this.listbox.loai_don_vi_ql.value == null||this.listbox.loai_don_vi_ql.value == "")?0:this.listbox.loai_don_vi_ql.value
                  }
      console.log("load_ds_donvi_chuagan");
      console.log(data);
      API.lay_ds_donvi_chuagan(this.axios,data).then(
        (response) => { 
            console.log(response);
            if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
              this.listbox.ds_donvi_chuagan.list = response.data.data;
              this.tong_so_chua_gan = this.listbox.ds_donvi_chuagan.list.length;
            }
            else{
              this.listbox.ds_donvi_chuagan.list = [];
              this.tong_so_chua_gan = 0;
            }
          }
      );
    },

    grdChanged(obj) {
      this.donvi_ql = obj[0];
      this.load_ds_donvi_dagan();
      this.load_ds_donvi_chuagan();
      
     
    },


    grd_dagan_Changed(obj) {
      this.donvi_dagan_ql = obj[0];

       this.listbox.ds_donvi_dagan.list.forEach(function (item) {
        if (obj.filter(x => x === item).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
      
    },

    grd_chuagan_Changed(obj) {
      this.donvi_chuagan_ql= obj[0];

        this.listbox.ds_donvi_chuagan.list.forEach(function (item) {
        if (obj.filter(x => x === item).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
      
    },

    btn_chuyen_1_sang_trai(){
      if (this.listbox.ds_donvi_chuagan.list.length == 0)
        return;
      
      // this.listbox.ds_donvi_chuagan.list = this.listbox.ds_donvi_chuagan.list.filter(element => element !== this.donvi_chuagan_ql);
      // this.listbox.ds_donvi_dagan.list.push(this.donvi_chuagan_ql);
        this.listbox.ds_donvi_chuagan.list.filter(x=>x.ischecked).forEach(item=>{
        item.ischecked = false;
        this.listbox.ds_donvi_dagan.list.push(item);
        var idx = this.listbox.ds_donvi_chuagan.list.indexOf(item);
        this.listbox.ds_donvi_chuagan.list.splice(idx,1);
        
      });

      this.tong_so_chua_gan = this.listbox.ds_donvi_chuagan.list.length;
      this.tong_so_da_gan = this.listbox.ds_donvi_dagan.list.length;

    },

    btn_chuyen_1_sang_phai(){
      if (this.listbox.ds_donvi_dagan.list.length == 0)
        return;
      // this.listbox.ds_donvi_dagan.list = this.listbox.ds_donvi_dagan.list.filter(element => element !== this.donvi_dagan_ql);
      // this.listbox.ds_donvi_chuagan.list.push(this.donvi_dagan_ql);
      this.listbox.ds_donvi_dagan.list.filter(x=>x.ischecked).forEach(item=>{
        item.ischecked = false;
        this.listbox.ds_donvi_chuagan.list.push(item);
        var idx = this.listbox.ds_donvi_dagan.list.indexOf(item);
        this.listbox.ds_donvi_dagan.list.splice(idx,1);
      });

      this.tong_so_chua_gan = this.listbox.ds_donvi_chuagan.list.length;
      this.tong_so_da_gan = this.listbox.ds_donvi_dagan.list.length;
    },

    btn_chuyen_tat_sang_trai(){
      this.listbox.ds_donvi_chuagan.list.forEach(element => {
        const index = this.listbox.ds_donvi_chuagan.list.indexOf(element);
        if (index > -1) {
          
          this.listbox.ds_donvi_dagan.list.push(element);
        }
      });
      this.listbox.ds_donvi_chuagan.list = [];

      this.tong_so_chua_gan = this.listbox.ds_donvi_chuagan.list.length;
      this.tong_so_da_gan = this.listbox.ds_donvi_dagan.list.length;
    },

    btn_chuyen_tat_sang_phai(){
      this.listbox.ds_donvi_dagan.list.forEach(element => {
        const index = this.listbox.ds_donvi_dagan.list.indexOf(element);
        if (index > -1) {
         
          this.listbox.ds_donvi_chuagan.list.push(element);
        }
      });
       this.listbox.ds_donvi_dagan.list = [];

       this.tong_so_chua_gan = this.listbox.ds_donvi_chuagan.list.length;
       this.tong_so_da_gan = this.listbox.ds_donvi_dagan.list.length;

    },

     cap_nhat(){
       var list_dagan=[];
       this.listbox.ds_donvi_dagan.list.forEach(element =>{
         var item = {
            "DONVI_ID": element.donvi_id,    //id của đơn vị trên đã gán
            "DONVI_QL_ID": (this.donvi_ql==null||this.donvi_ql=="")?0:this.donvi_ql.donvi_id,//giá trị của dòng trên lưới bên trái
            "LOAIDV_ID": (this.listbox.loai_don_vi_con.value==null||this.listbox.loai_don_vi_con.value=="")?0:this.listbox.loai_don_vi_con.value,//gia tri cbb loai don vi ben phai
            "LOAIDV_QL_ID": (this.listbox.loai_don_vi_ql.value==null||this.listbox.loai_don_vi_ql.value=="")?0:this.listbox.loai_don_vi_ql.value//giá trị của dòng trên lưới bên trai
          };
          list_dagan.push(item);
       })
       var data ={
                  "donvi_ql_id": (this.donvi_ql==null||this.donvi_ql=="")?0:this.donvi_ql.donvi_id, //giá trị của dòng trên lưới bên trái
                  "json_donvi_ql": list_dagan,
                  "loaidv_id": (this.listbox.loai_don_vi_con.value==null||this.listbox.loai_don_vi_con.value=="")?0:this.listbox.loai_don_vi_con.value,
                  "loaidv_ql_id": (this.listbox.loai_don_vi_ql.value==null||this.listbox.loai_don_vi_ql.value=="")?0:this.listbox.loai_don_vi_ql.value,
                }
        console.log(data);

        API.capnhat(this.axios,data).then(
           (response) => { 
              console.log(response);
              if(response && response.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                this.load_ds_donvi_dagan();
                this.load_ds_donvi_chuagan();
                this.$toast.success("Cập nhật thành công!");
              }
              else
                this.$toast.success(response.data.message);
           }
        )
                      
    },


  }
 
}
</script>