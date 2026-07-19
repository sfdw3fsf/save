<template>
  <b-modal
    ref="modelDSBD"
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
            <span class="icon one-notepad"></span> Danh sách các biến động
          </div>
          <div class="close -ap icon-close" @click="hideModal"></div>
        </div>
        <div class="list-actions-top">
          <ul class="list">
              <li>
                  <a href="#" @click.prevent="chapnhan">
                      <span class="icon one-notepad" ></span> Chấp nhận
                  </a>
              </li>
              <li>
                  <a href="#" @click.prevent="tsbtnThoat_Click">
                      <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát
                  </a>
              </li>
              
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form">
            <div class="legend-title">Danh sách các biến động</div>
            <DataGrid
              v-bind:columns="list_bd.header"                    
              v-bind:dataSource="list_bd_data"
              :enable-paging-server="false"                                        
              :allowPaging="true"
              :showFilter="true"
              @selectedItemsChanged="grdBiendongChanged"> 
            </DataGrid> 
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>

import API from './API'

export default {
   name: 'DSBDmodal',
    props: {
      list_bd_data:[]
    },
  
    data(){
        return {
           biendong:{},
           list_bd:{
             header:  [{fieldName:'ma_gd',headerText:'Mã GD',allowFiltering: true},{fieldName:'ngay_yc',headerText:'Ngày yêu cầu',allowFiltering: true},
            {fieldName:'trangthai_hd',headerText:'Trạng thái',allowFiltering: true},{fieldName:'ten_loaihd',headerText:'Loại hợp đồng',allowFiltering: true},
            {fieldName:'ten_kieuld',headerText:'Kiếu lắp đặt',allowFiltering: true},{fieldName:'ten_dv',headerText:'Đơn vị lập HĐ/PL',allowFiltering: true}  ],
             list:[]
           }
        }
    },
    methods: {
      handleShowModal: async function () {
       //do nothing
      
      },
      hideModal () {
      this.$refs.modelDSBD.hide();
      },
      
      showModal () {
          this.$refs.modelDSBD.show();
      },

      grdBiendongChanged:function(obj){
        this.biendong = obj[0];
      },

      chapnhan:function(){
        //console.log(this.biendong);
       
        this.$emit('popup_put_dsbd', this.biendong);
        this.$refs.modelDSBD.hide();

      },

      tsbtnThoat_Click:function(){
        this.$refs.modelDSBD.hide()
      }
        
    }
}
</script>
