<template src="./StaffStock.html">
  
</template>

<script>
import gridview from '@/components/Shared/gridview'
import treemenu from './treemenu'
import treekho from './treekho'
import API from './API'
import gridcheckbox from './gridcheckbox.vue'

export default {
  components: { gridview,treemenu,treekho,gridcheckbox },
  name: 'StaffStock',

  data(){
    return {
      tu_ngay:"",
      den_ngay:"",
      listbox:{
        tree_donvi:{
          list:{}
        },
         ds_nhanvien:{
          list:[],
          value:"",
          header:[{name:'STT',text:'STT'},{name:'ma_nv',text:'Mã nhân viên'},{name:'ten_nv',text:'Tên nhân viên'},{name:'ma_dv',text:'Mã đơn vị'},{name:'chucdanh',text:'Chức danh'}
          ,{name:'vitri',text:'Vị trí'},{name:'so_dt',text:'Điện thoại'},{name:'diachi_nv',text:'Địa chỉ'}],
          isEnabled: false,
        },
        tree_chuagan:{
          list:{}
        },
        tree_dagan:{
         list:[],
          value:"",
          header:[{name:'ten_kho',text:'Tên kho'}],
        },
         quyen_xacnhan:{
          list:[],
          value:"",
          header:[{name:'ten_kho',text:'Kho'},{name:'ten_dv',text:'Đơn vị'},{name:'quyen_kg',text:'Quyền gửi'},{name:'quyen_kn',text:'Quyền nhận'}],
          isEnabled: false,
          data_update:[],
        },
       
      },
       
    }
  },

  mounted(){
  
    API.tree_donvi(this.axios,null).then(response=>{
      
        if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          var dsdoituong = response.data.data;
         
          dsdoituong.forEach(item=>{
              if(item.donvi_cha_id==null)
              item.donvi_cha_id = -1;
            });

            var root= {
                "unitlevel": 0,
                "ten_dv": "Root",
                "donvi_id": -1,
                "donvi_cha_id": null,
                "id": -1
            };

            dsdoituong.push(root);

          var tree = dsdoituong.filter((a)=>a.donvi_cha_id==null);
          function addTree(item) {

            var dsitems = dsdoituong.filter((a)=>a.donvi_cha_id===item.donvi_id);
            if(dsitems && dsitems.length>0){
              for(var i=0;i<dsitems.length;i++)
                dsitems[i] = addTree(dsitems[i]);
            item.$nodes = dsitems;
            }
            return item;
          }
          
          for(var j=0;j<tree.length;j++) tree[j] = addTree(tree[j]);

         
          this.listbox.tree_donvi.list = tree[0];
        }

    });



  

  },

  methods:{
    

    treeClicked(obj) {
      var data={
                  "p_pagesize": 5000,
                  "p_donvi_id": obj,
                  "p_pagenumber": 1
                }
      API.grv_nhanvien(this.axios,data).then(response=>{
         if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
           this.listbox.ds_nhanvien.list = response.data.data;
         }
         else{
           this.listbox.ds_nhanvien.list =[];
         }
      });
      this.listbox.tree_dagan.list = [];
      this.listbox.tree_chuagan.list = [];
       this.listbox.quyen_xacnhan.list = [];
      
    },

    grd_nhanvien_Changed(obj){
      this.listbox.ds_nhanvien.value = obj;

      //load ds kho da gan
      var data={
                  "p_pagesize": 5000,
                  "p_nhanvien_id": obj.nhanvien_id,
                  //"p_nhanvien_id": 3945,
                  "p_pagenumber": 1
                }
                console.log(data);
                
      API.kho_dagan(this.axios,data).then(response=>{
         if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
         

          this.listbox.tree_dagan.list = response.data.data;
         }
         else{
           this.listbox.tree_dagan.list =[];
         }
      });


        //load ds kho chua gan
       API.kho_chuagan(this.axios,data).then(response=>{
         if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
           var dsdoituong = response.data.data;
         
          dsdoituong.forEach(item=>{
              if(item.kho_cha_id==null)
              item.kho_cha_id = -1;
            });

            var root= {
                          "stt": 0,
                          "kho_id": -1,
                          "kho_cha_id": null,
                          "ten_kho": "Root",
                          "r_": 0
                      }

          dsdoituong.push(root);

          var tree = dsdoituong.filter((a)=>a.kho_cha_id==null);
          function addTree(item) {

            var dsitems = dsdoituong.filter((a)=>a.kho_cha_id===item.kho_id);
            if(dsitems && dsitems.length>0){
              for(var i=0;i<dsitems.length;i++)
                dsitems[i] = addTree(dsitems[i]);
            item.$nodes = dsitems;
            }
            return item;
          }
          
          for(var j=0;j<tree.length;j++) tree[j] = addTree(tree[j]);

         
          this.listbox.tree_chuagan.list = tree[0];       

         }
         else{
           this.listbox.tree_chuagan.list =[];
         }
      });

      //load ds quyen xac nhan
      API.quyen_xacnhan(this.axios,data).then(response=>{
          if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            var dsdoituong = response.data.data;
            dsdoituong.forEach(item=>{
              item.quyen_kg = item.quyen_kg==1?true:false;
              item.quyen_kn = item.quyen_kn==1?true:false;
            });
            this.listbox.quyen_xacnhan.list = dsdoituong;
          }
          else
            this.listbox.quyen_xacnhan.list = [];

      });
      console.log(this.listbox.quyen_xacnhan.list);


    
     
    },

    btn_ghilai(){
      if (confirm('Bạn muốn ghi lại?')){
        var data = [];

        this.listbox.quyen_xacnhan.data_update.forEach(element=>{
                var item = {
                          "QUYEN_KN": element.quyen_kn==true?1:0,
                          "NHANVIEN_ID": this.listbox.ds_nhanvien.value.nhanvien_id,
                          "QUYEN_KG": element.quyen_kg==true?1:0,
                          "KHO_ID": element.kho_id
                        }
                data.push(item);
              }); 
        
        var post_data = {
          "p_json_quyenxn":data
        }

        console.log(post_data)  ;

        API.upd_quyexn(this.axios,post_data).then(response=>{
          alert(response.data.message);
        });

      }
      
    },

    testtree(){
      var data={
                "p_pagesize": 5000,
                //"p_nhanvien_id": obj.nhanvien_id,
                "p_nhanvien_id": 4361,
                "p_pagenumber": 1
              };
      
      console.log(data);
      
      API.kho_chuagan(this.axios,data).then(response=>{
         
         console.log(response);

         if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
          var dsdoituong = response.data.data;
          dsdoituong.forEach(item=>{
              if(item.kho_cha_id==null)
                item.kho_cha_id = -1;
            });

            var root= {
                          "stt": 0,
                          "kho_id": -1,
                          "kho_cha_id": null,
                          "ten_kho": "Root",
                          "r_": 0
                      }

          dsdoituong.push(root);

          var tree = dsdoituong.filter((a)=>a.kho_cha_id==null);
          function addTree(item) {

            var dsitems = dsdoituong.filter((a)=>a.kho_cha_id===item.kho_id);
            if(dsitems && dsitems.length>0){
              for(var i=0;i<dsitems.length;i++)
                dsitems[i] = addTree(dsitems[i]);
            item.$nodes = dsitems;
            }
            return item;
          }
          
          for(var j=0;j<tree.length;j++) tree[j] = addTree(tree[j]);

         
          this.listbox.tree_chuagan.list = tree[0];       

         }
         else{
           this.listbox.tree_chuagan.list =[];
         }
      });


    },

    updatedata(data_update){
      this.listbox.quyen_xacnhan.data_update = data_update;
    },

    loaddatafile(){
      var ds_check = [];
      console.log( this.listbox.quyen_xacnhan.tanhn);

    },

   

    refresh(){
      //this.listbox.tree_donvi.list = [];
      this.listbox.ds_nhanvien.list = [];
      this.listbox.tree_dagan.list = [];
      this.listbox.tree_chuagan.list = [];
      this.listbox.quyen_xacnhan.list = [];

    },

    

  }

 
 
}
</script>
