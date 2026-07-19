<template src="./index.html"></template>
<script>
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";
import gridview from "@/components/Shared/gridview";
import api from "./api/index.js";
import state from "./define.js";
import { required, minValue, maxValue, maxLength,minLength,numeric } from 'vuelidate/lib/validators'

export default {
  components: { 
    moment,
    breadcrumb, 
    gridview,
  },
  name: "",  
  data() {
    return {
      header: {
        title: "MÃ LỖI ĐƠN VỊ NHẬN",
        list: [
          { name: "QLSC", link: { name: "Ui.cards" } },
          {
            name: "Mã lỗi đơn vị nhận",
            link: { name: "Ui.buttons" },
          },
        ],
      },      
      ...state
      
    };
  },
  created() {
    this.init()
    this.loadDanhSach()

  },
  mounted() {
    
  },
  computed: {},
  watch: { 
    
  },
  methods: {
    async init(){
      try {        
        
        await api.sp_layds_tinhtrangsc(this.axios).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
            this.options.cbo_tinhtrang.list = response.data.data;    
          }
        });

        await api.sp_layds_donvi(this.axios).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
            this.options.cbo_donvinhan.list = response.data.data;    
          }
        });
        await api.sp_layds_lam(this.axios).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
            this.options.cbo_thietbi.list = response.data.data;    
          }
        });
        
        this.SetButton(1)

        this.may_cn = await this.$root.token.getMachine()
        this.ip_cn = await this.$root.token.getIP()
        
      } catch (error) {
        console.log(error)        
      }
    },
    setActiveButton (id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].active = status
      }      
    },
    setVisibleButton (id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].visible = status
      }      
    },
    SetButton(kieu){
      this.setActiveButton('nhapmoi',false)
      this.setActiveButton('ghilai',false)
      this.setActiveButton('xoa',false)
      this.giaophieu_active=false      
      if (kieu == -1)//Bat dau
      {          
          this.setActiveButton('ghilai',true)
      }
      else if (kieu == 0)//Bat dau
      {          
          this.setActiveButton('nhapmoi',true)          
          this.ClearGUI()
      }
      else if (kieu == 1)//Them moi
      {
          this.setActiveButton('ghilai',true)
          this.ClearGUI()
      }
      else if (kieu == 2)//Huy
      {          
          this.setActiveButton('nhapmoi',true)
          this.ClearGUI()
      }
      else if (kieu == 3)//Edit
      {
          this.is_enable_nhapmoi = true
          this.setActiveButton('nhapmoi',true)
          this.setActiveButton('ghilai',true)
          this.setActiveButton('xoa',true)
          this.giaophieu_active=true
      }
      
    },
    
    selectItem(item){
      console.log(item.data)
      //return;
      if (!item || !item.data) return
      this.SetButton(3)
      
      //hiển thị thông tin chi tiết
      this.idgan = item.data.id
      this.options.cbo_tinhtrang.value = item.data.tinhtrang_id
      this.options.cbo_thietbi.value = item.data.thietbi_id
      this.options.cbo_donvinhan.value = item.data.donvi_id
      this.ghichu = item.data.ghichu

    },    
    async loadDanhSach() {
      try {        
        
        this.gvDanhSach.tableData = []
        await api.sp_layds_maloi_dvn(this.axios).then((response) => {
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000"&& response.data.data.length>0) {            
            this.gvDanhSach.tableData = response.data.data;    
                
          }
        });
        
      } catch (error) {
        console.log(error)        
      }
    },
    
    btnThemmoi_Click(){      
      console.log('click thêm mới')
      this.SetButton(1)
      this.ClearGUI()
    },  
    async btnGhilai_Click(){      
      console.log('click ghi lại')      
      try
      {
        if (this.options.cbo_tinhtrang.value == 0){
          this.$toast.error("Vui lòng chọn tình trạng!") 
          return
        }
        if (this.options.cbo_donvinhan.value == 0){
          this.$toast.error("Vui lòng chọn đơn vị!") 
          return
        }
        if (this.options.cbo_thietbi.value == 0){
          this.$toast.error("Vui lòng chọn thiết bị!") 
          return
        }
        if (this.idgan == 0){
          //thêm mới
          let input = {
            "p_tinhtrang_id":this.options.cbo_tinhtrang.value,
            "p_donvi_id":this.options.cbo_donvinhan.value,
            "p_thietbi_id":this.options.cbo_thietbi.value,
            "p_ghichu":this.ghichu,
          }
          
          await api.sp_tao_maloi_dvn(this.axios, input).then((response) => {
            this.loading(false)
            if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
              console.log(response.data.data)
              this.$toast.success("Thêm mới thành công.")
              this.loadDanhSach()  
            }
          });
        }
        else {
          //cập nhật
          let input = {
            "p_id":this.idgan,
            "p_tinhtrang_id":this.options.cbo_tinhtrang.value,
            "p_donvi_id":this.options.cbo_donvinhan.value,
            "p_thietbi_id":this.options.cbo_thietbi.value,
            "p_ghichu":this.ghichu,
          }
          
          await api.sp_capnhat_maloi_dvn(this.axios, input).then((response) => {
            this.loading(false)
            if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
              console.log(response.data.data)
              this.$toast.success("Cập nhật thành công.")
              this.loadDanhSach()  
            }
          });
        }
      }
      catch (ex)
      {
          this.loading(false)
          this.$toast.error("Có lỗi khi thêm mới thông tin!"+ex)          
      }

    },
    
    async btnXoa_Click(){      
      console.log('click xóa thông tin')
      if (this.idgan == 0) {
        return;
      }
      if (!window.confirm("Bạn thật sự muốn xóa dữ liệu không ?")) {
        return
      }
      this.loading(true)
      try
      {
        let input = {
          "p_id":this.idgan,          
        }
        console.log(input)
        
        await api.sp_xoa_maloi_dvn(this.axios, input).then((response) => {
          this.loading(false)
          if (response &&response.data &&response.data.data &&response.data.error_code &&response.data.error_code === "BSS-00000000") {
            console.log(response.data.data)
            this.$toast.success("Xóa thành công.")
            this.loadDanhSach()  
          }
        });    
      }
      catch (ex)
      {
          this.loading(false)
          this.$toast.error("Có lỗi khi xóa thông tin!"+ex)          
      }
    },    
    btnLayDS_Click(){      
      console.log('click nạp lại')
      this.SetButton(1)
      this.loadDanhSach()
      
    },
    onActionClick (action) {
      if (action.id === 'nhapmoi') {
        this.btnThemmoi_Click()
      } else if (action.id === 'ghilai') {
        this.btnGhilai_Click()
      } else if (action.id === 'xoa') {
        this.btnXoa_Click()
      } else if (action.id === 'naplai') {
        this.btnLayDS_Click()
      } 
    },
    
    ClearGUI(){
      this.idgan = 0
      this.options.cbo_tinhtrang.value = 0
      this.options.cbo_thietbi.value = 0
      this.options.cbo_donvinhan.value = 0
      this.ghichu = ""
    },
    
  },
};
</script>
<style scoped>
  .input-icon-right.disabled input{
    cursor: default;
    pointer-events: none;
    text-decoration: none!important;
    color: grey!important;
  }
</style>