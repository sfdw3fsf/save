<template src="./InvoiceSerial.html">
  
</template>

<script>
import gridview from '@/components/Shared/gridview'
import API from './API'

export default {
  components: { gridview },
  name: 'InvoiceSerial',

  data(){
    return {

      su_dung:false,
      seri:"",

      tsbtnNhapMoi:{Visible : true},
      tsbtnGhiLai:{Visible : true},
      tsbtnHuyBo:{Visible : true},
      tsbtnXoa:{Visible : true},

      listbox:{

        ds_kieuhd:{
          list:[],
          value:""
        },
        ds_seri:{
          list:[],
          header:[],
          value:""
        },
         ds_dagan:{
          list:[],
          header:[],
          checked:[],
          value:""
        },
         ds_chuagan:{
          list:[],
          header:[],
          checked:[],
          value:""
        },
      }
       

    }

  },

  mounted(){
    API.lay_ds_kieuhd(this.axios,null).then(
       (response) => { 
           if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.ds_kieuhd.list = response.data.data;
            this.listbox.ds_kieuhd.value = 1;
          }
        }
    );
    this.listbox.ds_dagan.header = [{fieldName:'tentat',headerText:'Tên tắt'},{fieldName:'kyhieu',headerText:'Ký hiệu'}];
    this.listbox.ds_chuagan.header = [{fieldName:'tentat',headerText:'Tên tắt'},{fieldName:'kyhieu',headerText:'Ký hiệu'}];
    this.load_ds_seri();
    
  },

  methods:{
    load_ds_seri(){
      API.lay_ds_seri(this.axios,null).then(
       (response) => { 
           if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
            this.listbox.ds_seri.list = response.data.data;
            
            for(var i in this.listbox.ds_seri.list) this.listbox.ds_seri.list[i].STT=(parseInt(i)+1).toString();

            this.listbox.ds_seri.header = [{fieldName:'STT',headerText:'STT'},{fieldName:'seri',headerText:'Seri'}];

            this.listbox.ds_seri.list = response.data.data;
            this.listbox.ds_seri.value = response.data.data[0];


            
            if (response.data.data[0].sudung == 1)
              this.su_dung = true;
            else
              this.su_dung = false;

            this.seri = response.data.data[0].seri;
            this.listbox.ds_kieuhd.value = response.data.data[0].kieu;

            //load danh sach hop dong seri da gan
            var data = {
                  "p_kieu": 0,
                  "p_seri_id": response.data.data[0].seri_id
                };
            this.load_ds_hd_seri(data);

            ////load danh sach hop dong seri chua gan
            data = {
                "p_kieu": 1,
                "p_seri_id": response.data.data[0].seri_id
              }
            this.load_ds_hd_seri(data);

          }
        }
    );

    },

    load_ds_hd_seri(data){
      //p_kieu = 1: đã gan
      if (data.p_kieu == 1){
        this.listbox.ds_dagan.list = [];
        API.lay_ds_loaihd_seri(this.axios,data).then(
          (response) => { 
            if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                this.listbox.ds_dagan.list = response.data.data;
                this.listbox.ds_dagan.header = [{fieldName:'tentat',headerText:'Tên tắt'},{fieldName:'kyhieu',headerText:'Ký hiệu'}];
            }
          }
        );

      }
      //p_kieu = 0: chưa gán
      else if (data.p_kieu == 0){
        this.listbox.ds_chuagan.list = [];
        API.lay_ds_loaihd_seri(this.axios,data).then(
          (response) => { 
            if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') {
                this.listbox.ds_chuagan.list = response.data.data;
                this.listbox.ds_chuagan.header = [{fieldName:'tentat',headerText:'Tên tắt'},{fieldName:'kyhieu',headerText:'Ký hiệu'}];
            }
          }
        );

      }
      

    },

    SetButton: function(kieu)  {
            this.tsbtnNhapMoi.Visible = false;
            this.tsbtnGhiLai.Visible = false;
            this.tsbtnXoa.Visible = false;
            if (kieu == -1)//Bat dau
            {
                this.tsbtnGhiLai.Visible = true;
            }
            if (kieu == 0)//Bat dau
            {
                this.tsbtnNhapMoi.Visible = true;
            }                                             
            if (kieu == 1)//Them moi
            {

                //this.tsbtnGhiLai.Visible = true;

                this.tsbtnNhapMoi.Visible = false;
                this.tsbtnXoa.Visible = true;
                this.tsbtnGhiLai.Visible = true;
                this.tsbtnHuyBo.Visible = true;
            }
            if (kieu == 2)//Huy - Ghi lai
            {
                this.tsbtnNhapMoi.Visible = true;
                this.tsbtnXoa.Visible = true;
                this.tsbtnGhiLai.Visible = true;
                this.tsbtnHuyBo.Visible = true;
            }
            if (kieu == 3)//Edit
            {
                this.tsbtnNhapMoi.Visible = true;
                this.tsbtnXoa.Visible = true;
                this.tsbtnGhiLai.Visible = true;
            }
    },



    grdChanged(obj){
      this.listbox.ds_seri.value = obj[0];
      //console.log(this.listbox.ds_seri.value);
      if (obj[0].sudung == 1)
        this.su_dung = true;
      else
        this.su_dung = false;

      this.seri = obj[0].seri;
      this.listbox.ds_kieuhd.value = obj[0].kieu;
      //load danh sach hop dong seri da gan
      var data = {
            "p_kieu": 0,
            "p_seri_id": obj[0].seri_id
          };
      this.load_ds_hd_seri(data);

      ////load danh sach hop dong seri chua gan
      data = {
          "p_kieu": 1,
          "p_seri_id": obj[0].seri_id
        }
      this.load_ds_hd_seri(data);
      this.SetButton(3);

    },

    grd_dagan_Changed(obj){
      this.listbox.ds_dagan.checked = obj;

      this.listbox.ds_dagan.list.forEach(function (item) {
        if (obj.filter(x => x === item).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    },

    grd_chuagan_Changed(obj){
       this.listbox.ds_chuagan.checked = obj;

      this.listbox.ds_chuagan.list.forEach(function (item) {
        if (obj.filter(x => x === item).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    },

    btn_chuyen_sang_trai(){
      //var dscheck = this.listbox.ds_chuagan.list.filter(x=>x.ischecked);
      
      this.listbox.ds_chuagan.list.filter(x=>x.ischecked).forEach(item=>{
        item.ischecked = false;
        this.listbox.ds_dagan.list.push(item);
        var idx = this.listbox.ds_chuagan.list.indexOf(item);
        this.listbox.ds_chuagan.list.splice(idx,1);
        
      });
      
    },

    btn_chuyen_sang_phai(){
      //var dscheck = this.listbox.ds_dagan.list.filter(x=>x.ischecked);
      this.listbox.ds_dagan.list.filter(x=>x.ischecked).forEach(item=>{
        item.ischecked = false;
        this.listbox.ds_chuagan.list.push(item);
        var idx = this.listbox.ds_dagan.list.indexOf(item);
        this.listbox.ds_dagan.list.splice(idx,1);
      });
    },

    btn_chuyen_tat_sang_trai(){
     
      this.listbox.ds_chuagan.list.forEach(item=>{
        this.listbox.ds_dagan.list.push(item);
      });
      this.listbox.ds_chuagan.list = [];
    },

    btn_chuyen_tat_sang_phai(){
      this.listbox.ds_dagan.list.forEach(item=>{
        this.listbox.ds_chuagan.list.push(item);
      });
      this.listbox.ds_dagan.list = [];
    },

    btn_nhapmoi(){
      this.SetButton(1);
      this.su_dung = false;
      this.listbox.ds_kieuhd.value = 1;
      this.seri = "";
      this.listbox.ds_seri.value = "";
      
      this.listbox.ds_dagan.list = [];
             ////load danh sach hop dong seri chua gan
      var data = {
          "p_kieu": 0,
          "p_seri_id": 0
        }
      this.load_ds_hd_seri(data);

    },

    btn_ghilai: async function(){
      var ser_id=this.listbox.ds_seri.value.seri_id;
      console.log(ser_id);
      if (this.seri == "" || this.seri == undefined){
        this.$toast.error("Bạn chưa nhập Seri");
        this.$refs.txt_seri.focus();
        return;
      }
      
    

      var p_js_loai_serihd = [];
      var p_js_serihd = [];
      
      this.listbox.ds_dagan.list.forEach(item=>{
        var data = {
          "LOAIHOADON_ID":item.loaihoadon_id,
          "SERI_ID": ser_id
        }
        p_js_loai_serihd.push(data);
      });
      
      var sudung = (this.su_dung)?1:0;
      
      p_js_serihd.push({
                    "DEN_SO": 0,
                    "KIEU": this.listbox.ds_kieuhd.value,
                    "LOAIHOADON_ID": 0,
                    "NGAY_PH": "27/08/2021",
                    "SERI": this.seri,
                    "SERI_ID": ser_id,
                    "SOLUONG": 0,
                    "SUDUNG": sudung,
                    "THUTU": 0,
                    "TRANGTHAI": 0,
                    "TU_SO": 0
                  });  


      var data = {
                "p_js_loai_serihd": p_js_loai_serihd,
                "p_js_serihd": p_js_serihd,
                "p_seri_id": ser_id
              }

      

     
       this.$confirm('Thực hiện lưu lại?', 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(async () => {
            this.loading(true);
            await API.insert_update_serihd(this.axios,data).then((response) => { 
              this.load_ds_seri();
              this.SetButton(2);
              this.$toast.success("Cập nhật Seri thành công !");
              this.loading(false);
            });
        
          })

   
    },

    btn_huy(){
      this.SetButton(2);
      this.seri = "";

    },

    btn_xoa : async function(){
      var data={
                "p_seri_id": this.listbox.ds_seri.value.seri_id
              }
      

      
      this.$confirm('Thực hiện xóa seri?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
          this.loading(true);
          await API.delete_serihd(this.axios,data).then(
            response=>{
              if(response && response.data  && response.data.error && response.data.error_code === 'BSS-00000000') {
                this.$toast.success("Xóa Seri thành công !");
                this.load_ds_seri();
                this.loading(false);
              }
              else
                this.$toast.error("Xóa Seri không thành công !");
                this.loading(false);
            });
      
        })
      
    },

  },

 
 
}
</script>