<template>
  <b-modal
    ref="modelCuocThueCongMGW"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Cước thuê cổng Megawan
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="tsbtnGhiLai_Click">
                    <span class="icon one-save"></span>Ghi lại
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="tsbtnHuyBo_Click">
                    <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát
                </a>
            </li>
            
            
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin cước thuê cổng megawan</div>
          <div class="row">
            <div class="col-sm6 col-12">
              <div class="info-row">
                <div class="key w90">Loại hình TB</div>
               <div class="value">
                     <input type="text" class="form-control" v-model="txtTocDo">
                  </div>
              </div>
            </div>
             <div class="col-sm6 col-12">
              <div class="info-row">
                <div class="key w90">Mức cước</div>
               <div class="value">
                     <input type="text" class="form-control" v-model="txtMucCuoc">
                  </div>
              </div>
            </div>
            
          </div>
        </div>
        <div class="box-form marb0" id="boxBottom">
              <div class="legend-title">
                  <span class="fa fa-angle-up icon"></span> Danh sách cước thuê cổng megawan
              </div>
              
              <DataGrid
                  v-bind:columns="listbox.ds_cuoc.headers"                    
                  v-bind:dataSource="listbox.ds_cuoc.list"
                  :enable-paging-server="false"                                        
                  :allowPaging="true"
                  :showFilter="true"  @selectedItemsChanged="dgvDsChange"> 
              </DataGrid> 
          </div>
        
      </div>
    </div>
  </b-modal>
</template>

<script>

import API from './API'
import DatabaseConstants from '@/const/enums/DBConstants'
import DichVuVienThong from '@/const/enums/DichVuVienThong'
export default {
    components: { DatabaseConstants,DichVuVienThong },
    name: 'CuocThueCongMGW',
  
    data(){
        return {
          txtMucCuoc:"",
          txtTocDo:"",
         loaitb_id:"",
            listbox:{
               ds_cuoc:{
                    list:[],
                      headers:[{fieldName:'STT',headerText:'STT',allowfiltering: true},
                      {fieldName:'LOAIHINH_TB',headerText:'Loại hình TB',allowfiltering: true},
                      {fieldName:'CUOCTC_WAN',headerText:'Mức cước',allowfiltering: true}]
                },
            }
        }
    },
    methods: {
        handleShowModal: async function () {
            this.SelectList();
        
        },
        hideModal () {
        this.$refs.modelCuocThueCongMGW.hide()
        },
        
        showModal () {
            
            this.$refs.modelCuocThueCongMGW.show()
        },

        SelectList:async function(){
          this.loading(true);

          // var data={
          //           "command":"select rownum stt, a.loaitb_id,a.loaihinh_tb,a.cuoctc_wan from " + DatabaseConstants.DB2 + ".loaihinh_tb a where a.dichvuvt_id = " + DichVuVienThong.MEGAWAN + ""
          //       };

          var data= "p_dichvuvt_id="+ DichVuVienThong.MEGAWAN;

          await API.sp_lay_loaitb_v2(this.axios,data).then(response=>{
            if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000') 
                this.listbox.ds_cuoc.list = response.data.data;
            else
                this.listbox.ds_cuoc.list = [];
            });

          
          this.loading(false);
            
        },




        tsbtnGhiLai_Click: async function()
        {
        
          if (this.txtMucCuoc == "" || this.txtMucCuoc == null){
            this.$toast.error('Bạn chưa nhập mức cước!');
            return;
          }
          
          var data = {
                        "loaitb_id": this.loaitb_id,
                        "cuoctc_wan": this.txtMucCuoc
                      }
          
          await API.sp_capnhat_cuoctc_lhtb(this.axios,data).then(response=>{
            if(response.data.error_code === 'BSS-00000000') 
              this.$toast.success("Cập nhật thành công");
            else
              this.$toast.error(response.data.message);
          });
          this.SelectList();
          
        },

        dgvDsChange: function(obj)
        {
          //console.log(obj);

          this.txtTocDo = obj[0].LOAIHINH_TB;
          if (obj[0].CUOCTC_WAN == "" || obj[0].CUOCTC_WAN == null)
              this.txtMucCuoc = "0";
          else
              this.txtMucCuoc = obj[0].CUOCTC_WAN;
          this.loaitb_id = obj[0].LOAITB_ID;
        },

        tsbtnHuyBo_Click: function()
        {
          this.$refs.modelCuocThueCongMGW.hide();
        }
        
    }
}
</script>
