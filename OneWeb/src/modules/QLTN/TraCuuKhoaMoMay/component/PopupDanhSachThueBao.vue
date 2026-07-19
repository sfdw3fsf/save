<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgDanhsachthuebao"
        :header='"Danh sách thuê bao"'
        :animationSettings='animationSettings'
        showCloseIcon=true width='80%'
        :target='target'
        :open="dialogOpen"
        :close="close"
        :isModal="true"
    >            
            <div class="">                
                <div class="list-actions-top">
                    <ul class="list">                       
                        <li>
                            <a href="javascript:void(0)" @click="onClickAccept" title="Chấp nhận">
                                <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận
                            </a>
                        </li>
                        <li>
                            <a href="javascript:void(0)" @click="close" title="Thoát">
                                <span class="icon one-export"></span> Thoát
                            </a>
                        </li>                        
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">                        
                        <DataGrid v-bind:columns="[
                            {fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'text-left'},
                            {fieldName: 'DICHVU_VT', headerText: 'Dịch vụ', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'text-danger'},
                            {fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true, allowSorting: false, width: 120, cssClass: 'text-primary'},                            
                            {fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: false, width: 120},
                            {fieldName: 'MA_TB_SUB', headerText: 'Mã TB sub', allowFiltering: true, allowSorting: false, width: 120},
                            {fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái thuê bao', allowFiltering: true, allowSorting: false, width: 120},
                            {fieldName: 'MA_TT', headerText: 'Mã thanh toán', allowFiltering: true, allowSorting: false, width: 120},
                            {fieldName: 'TEN_TT', headerText: 'Tên thanh toán', allowFiltering: true, allowSorting: false, width: 120},
                            {fieldName: 'DIACHI_LD', headerText: 'Địa chỉ LD', allowFiltering: true, allowSorting: false, width: 120}
                        ]"
                        v-bind:dataSource="$props.input"
                        dataKeyField="MA_TB"
                        :showFilter="true"                    
                        :allowPaging="true"
                        :enablePagingServer="false"                                               
                        ref="thueBaoGrid"
                        @selectedRowChanged="selectItem"
                    >
                    </DataGrid>
                    </div>
                </div>
            </div>        
    </ejs-dialog>
</template>
<style></style>
<script>
export default {
    name: 'PopupDanhSachThueBao',
    components: { },
    props: {
        input: Array
    },        
   
    data () {
        return {
            index: 0,
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' }            
        }
    },

    methods: {
        selectItem (item) {
            this.index = this.input.indexOf(item);
        },        
        
        onClickAccept() {
            this.$emit('acceptChange', this.index);
            this.close();
        },

        close() {
            this.index = 0;
            this.$refs.dlgDanhsachthuebao.hide();
        },

        openDialog () {
          this.$refs.dlgDanhsachthuebao.show();
        },

        dialogOpen() {
        }        
    },

    watch: {        
    }
}
</script>
