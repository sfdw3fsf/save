<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgDanhSachLoiChuyenPhieuLH"
        :header='"Danh sách lỗi đọc file giao phiếu XLN"'
        :animationSettings='animationSettings'
        showCloseIcon=true width='60%'
        :target='target'
        :open="dialogOpen"
        :close="close"
        :isModal="true"
    >
            <div class="">                
                <div class="list-actions-top">
                    <ul class="list">                        
                        <li>
                            <a href="javascript:void(0)" @click="close" title="Thoát">
                                <span class="icon one-export"></span> Thoát
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="popup-body">       
                    <div class="box-form">
                        <div class="legend-title">
                            <div class="pull-left">
                                Danh sách lỗi
                            </div>                            
                            <div class="clearfix"></div>
                        </div>
                        <div class="table-content">                            
                            <DataGrid v-bind:columns="[
                                {fieldName: 'MA_XLN', headerText: 'Mã XLN', allowFiltering: true, allowSorting: false, width: 100},
                                {fieldName: 'LYDO', headerText: 'Lỗi', allowFiltering: true, allowSorting: false, width: 200, cssClass: 'trangthai-col'}
                            ]"
                                v-bind:dataSource="error.items"
                                dataKeyField="MA_XLN"
                                :showFilter="true"                    
                                :allowPaging="true"
                                :enablePagingServer="false"
                                ref="errorGrid"
                                @selectedRowChanged="onErrorSelected"                            
                            >
                            </DataGrid>
                        </div>                        
                    </div>
                </div>
            </div>
    </ejs-dialog>
</template>
<style scoped src="../../MoMayTheoDanhSach/style.scss"></style>
<script>
export default {    
    components: {},
    name: 'PopupDanhSachLoiChuyenPhieuLH',

    props: {
        input: Array
    },
    
    computed: {        
    },
   
    data () {
        return {
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            error: {
                items: []
            }
        }
    },

    methods: {
        dialogOpen() {
            this.error.items = this.input;
        },

        dialogClose() {
        },

        openDialog () {
            this.$refs.dlgDanhSachLoiChuyenPhieuLH.show();
        },

        close() {
            this.error.items = [];
            this.$refs.dlgDanhSachLoiChuyenPhieuLH.hide();
        },

        onErrorSelected(e) {
        }
    }
}
</script>