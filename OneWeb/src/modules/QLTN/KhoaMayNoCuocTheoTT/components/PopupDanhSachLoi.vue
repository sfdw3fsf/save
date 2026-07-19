<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgDanhSachLoi"
        :header='"Danh sách lỗi"'
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
                                {fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting: false, width: 100},
                                {fieldName: 'MA_TB', headerText: 'Mã TB', allowFiltering: true, allowSorting: false, width: 120},
                                {fieldName: 'MA_KV', headerText: 'Mã KV', allowFiltering: true, allowSorting: false, width: 120},
                                {fieldName: 'LYDO_LOI', headerText: 'Lỗi', allowFiltering: true, allowSorting: false, width: 200},
                            ]"
                                v-bind:dataSource="error.items"                                
                                :showFilter="true"                    
                                :allowPaging="true"
                                :enablePagingServer="false"
                                ref="errorGrid"
                            >
                            </DataGrid>
                        </div>                        
                    </div>
                </div>
            </div>
    </ejs-dialog>
</template>
<style></style>
<script>
export default {    
    components: {},
    name: 'PopupDanhSachLoi',

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
            this.$refs.dlgDanhSachLoi.show();
        },

        close() {
            this.error.items = [];
            this.$refs.dlgDanhSachLoi.hide();
        }
    }
}
</script>
