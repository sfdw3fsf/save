<template src="./Bank.html">
  
</template>

<script>
import gridview from '@/components/Shared/gridview'
import API from './API'

export default {
    components: { gridview },
  name: 'Bankmodal',
  props: {
    msg: String,
  },

  mounted () {
     API.ds_loai_nh(this.axios, null).then(
        (response) => { 
           if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            
            this.listbox.ds_loai_nh.list = response.data.data;
          }
        }
      );

    API.layds_nganhang(this.axios, null).then(
        (response) => {
          if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.ds_nganhang.list = response.data.data;
          }
        }
      );

     this.load_dulieu() ;
     
      var data = {
            "stt": 4,
            "nganhang_id": 54,
            "ma_nh": "NH001",
            "ten_nh": "Ngân hàng VCB Test tanhn",
            "diachi_nh": "Hải11 Phòng",
            "mst": "809657675",
            "stk": null,
            "ghichu": "11111111",
            "loai_nh": 0,
            "loainh": "Kho bạc",
            "nganhang_id_vt": 0,
            "stk_vt": null,
            "so_dvqhns": null
        };
    this.listbox.dulieu_nganhang.list.push(data);
   
  },

  data () {
    return {
      nganhang:{
        "diachi_nh": "",
        "ghichu": "",
        "loai_nh": 0,
        "ma_nh": "",
        "mst": "",
        "nganhang_id": 0,
        "nganhang_id_vt": 0,
        "so_dvqhns": "",
        "stk": "",
        "stk_vt": "",
        "ten_nh": "",
        "tinh_id": 26
      },
       listbox: {
        ds_loai_nh:{
          list:[],
          value: "",
        },
        ds_nganhang:{
          list:[],
          value: "",
        },
        dulieu_nganhang:{
          list:[],
           header:[{name:'STT',text:'STT'},{name:'ten_nh',text:'Tên ngân hàng'},{name:'ma_nh',text:'Mã ngân hàng'},{name:'diachi_nh',text:'Địa chỉ'},
                        {name:'loainh',text:'Loại'},{name:'stk_vt',text:'Số tài khoản VT'},{name:'so_dvqhns',text:'Số DVQHNS'} ],
          value: "",
        }
      },
      
    }
  },

  methods: {
    grdChanged(obj) {
        this.nganhang = obj;
    },

    nhapmoi() {
        this.nganhang = {
        "diachi_nh": "",
        "ghichu": "",
        "loai_nh": 0,
        "ma_nh": "",
        "mst": "",
        "nganhang_id": 0,
        "nganhang_id_vt": 0,
        "so_dvqhns": "",
        "stk": "",
        "stk_vt": "",
        "ten_nh": "",
        "tinh_id": 26
      };
        //console.log(this.nganhang);
    },

     luulai() {
        //onsole.log(this.nganhang);
       //them moi
       if (this.nganhang.nganhang_id == null || this.nganhang.nganhang_id == 0){
         var data = {
                    "dulieu": [
                     this.nganhang
                    ],
                    "id_nganhang_neo": 0,
                    "isdongbo_vina_trasau": 0,
                    "isthemmoi": 1   
                  }
         console.log ("them moi");
         console.log (data);
       }
       //cap nhat
       else {
         var data = {
                    "dulieu": [
                      this.nganhang
                    ],
                    "id_nganhang_neo": 0,
                    "isdongbo_vina_trasau": 0,
                    "isthemmoi": 0
                  }
        console.log ("cap nhat");
        console.log (data);
       }
      API.capnhat_nganhang(this.axios, data).then(
        (response) => {
          if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            //neu cap nhat thanh cong thi load lai danh sach ngan hang
            this.load_dulieu() ;
          }
          alert (response.data.message)
        }
      );
        
    },


    xoa() {
      var data={
              "isdongbo_vina_trasau": 0,
              "nganhang_id": this.nganhang.nganhang_id
            }

      API.xoa_nganhang(this.axios, data).then(
        (response) => {
          if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            //neu xoa thanh cong thi load lai danh sach ngan hang
            this.load_dulieu() ;
          }
          alert (response.data.message)
        }
      );
    },

    load_dulieu() {
       API.laydulieu_nganhang(this.axios, null).then((response) => {
              if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                    this.listbox.dulieu_nganhang.list = response.data.data;
                      for(var i in this.listbox.ds_phieu.list) this.listbox.ds_phieu.list[i].STT=(parseInt(i)+1).toString();
                    this.listbox.dulieu_nganhang.header = 
                        [{name:'STT',text:'STT'},{name:'ten_nh',text:'Tên ngân hàng'},{name:'ma_nh',text:'Mã ngân hàng'},{name:'diachi_nh',text:'Địa chỉ'},
                        {name:'loainh',text:'Loại'},{name:'stk_vt',text:'Số tài khoản VT'},{name:'so_dvqhns',text:'Số DVQHNS'} ]
                  }
                     else{
                   this.listbox.dulieu_nganhang.list = [];
                }
                }
             
              );
    }
  }
}
</script>