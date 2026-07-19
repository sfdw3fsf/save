<template src="./index.html"></template>
<style scoped>
	.displayNone {
		display: none;
	}
	table tbody tr:hover {
		background-color: #035FCC;
		cursor: pointer;
		color: #fff;
	}
	table tbody tr input:hover, label:hover {
		cursor: pointer;
	}
	.container {
		max-height: 150px;
		overflow-y: scroll;
		padding-left: 0;
		padding-right: 0;
	}
	table tr td:first-child {
		width: 10%;
	}
</style>
<script>
export default {
  name: "ComboTreeView",
  components: {},
  props: {},
  data: function() {
    return {
        selectedValue: [],
        isHide: true,
        isCheckAll: false,
        rootCanhan:false,  
        rootDoanhNghiep:false, 
        loaikh_id:0,
        ten_loaikh:'',
        category:{
                canhan:0,
                doanhnghiep:0,                          
            },
        dsNhomCaNhan:[],
        dsNhomDoanhNghiep:[],
        tmp_dsNhomLKH:[],
        tmp_dsLoaiKH:[],
        

    };
  },
  validations: {},
  watch: {},
  created: async function() {
  },
  async mounted() {
       this.getNhomLKH();  
       this.getdsLoaiKH();    
       var i=0;
       for (i = 0; i < 11; i++) {
            this.selectedValue[i] = false;           
          } 
  },
  methods: {      
      catalogCaNhan : async function (index) {
          console.log(index)        
          if (this.rootCanhan)
            this.rootCanhan=false;                                    
            this.selectedValue[index]=!this.selectedValue[index]            
            this.rootCanhan=true;
            
      },
    catalogDoanhNghiep : async function (index) {
          console.log(index)        
          if (this.rootDoanhNghiep)
            this.rootDoanhNghiep=false;                                    
            this.selectedValue[index]=!this.selectedValue[index]            
            this.rootDoanhNghiep=true;
            
      },      
    ClickLoaiKH: function (loaikh_id) {

        let data = {
            loaikh_id:loaikh_id,
            ten_loaikh:this.tmp_dsLoaiKH.filter((item)=>item.LOAIKH_ID===loaikh_id)[0].TEN_LOAIKH
            }
        this.$emit('clickComboLoaiKH', data);

    },
    	getdsLoaiKH: async function () {
            try {
                const rs = await this.$root.context.get(
                `/web-quantri/quan-ly-danh-ba/lay-danhmuc/LOAI_KH`
                );
                this.tmp_dsLoaiKH = rs.data;                       
            } catch (error) {}
            }, 
            getNhomLKH: async function () {
            try {
                const rs = await this.$root.context.get(
                `/web-quantri/danhmuc-chung/CSS_NHOM_LKH`
                );
                this.tmp_dsNhomLKH = rs.data;
                this.dsNhomCaNhan=this.tmp_dsNhomLKH.filter((item)=>item.LOAI===0);
                 this.dsNhomDoanhNghiep=this.tmp_dsNhomLKH.filter((item)=>item.LOAI===1);
                //this.dsNhomLKH=this.tmp_dsNhomLKH;//.filter((item)=>item.LOAI===this.param.kieu_id);
            } catch (error) {}
            }, 
}
};
</script>
<style>
div#popupQLBe___BV_modal_outer_ {
  z-index: 10 !important;
  margin-top: 100px;
}
.dashboard-header {
  z-index: 9 !important;
}
a.disabled {
  pointer-events: none;
  cursor: default;
}
</style>