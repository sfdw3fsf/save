<template>
 <div class="modal-content popup-box">
    <div class="popup-body">
      <div class="box-form">
          <div class="legend-title"></div>
          <DataGrid v-bind:columns="input_data.headers"
                    v-bind:dataSource="input_data.list" :showColumnCheckbox="false"
                    :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                    :enabled-select-first-row="true"
          ></DataGrid>
        </div>   

    </div>
  </div>
</template>
<script>

export default {
    name:'grvTT',
    props:{
      input_data:{
        type:Object
      },
      
    },

    components:{
       
    },
    data(){
        return {
            grvdatalist:[],
            grvheaders: [
                { fieldName: 'ma_gd', headerText: 'Mã GD' },   
                { fieldName: 'ngay_yc', headerText: 'Ngày yêu cầu' },   
                { fieldName: 'ten_loaihd', headerText: 'Loại HĐ' },   
                { fieldName: 'ten_dv', headerText: 'Đơn vị lập HĐ/PL' },   
                { fieldName: '', headerText: '' }
            ],
            
        }
    },
    
    methods:{
       chapnhan:function(obj){
          //console.log(this.biendong);
          this.$emit('emit_chapnhan', obj);

        }
    }

}
</script>
