<template>
    <b-modal
        id="pop_dsbd"
        size="xl"
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0"
    >  
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title">
                <span class="icon one-notepad"></span> Danh sách các biến động
            </div>
            <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('pop_dsbd')">
            </div>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="#" @click.prevent="chapnhan">
                        <span class="icon one-check" ></span> Chấp nhận
                    </a>
                    
                </li>
                <li>
                    <a href="#" @click="$bvModal.hide('pop_dsbd')">
                        <span class="icon one-remove-outline" style="color: darkred;" ></span> Thoát
                    </a>
                    
                </li>
            </ul>
            
        </div>
        <div class="popup-body">        
            <div class="box-form">
                <DataGrid
                v-bind:columns="list_bd.header"                    
                v-bind:dataSource="list_bd.list"
                :enable-paging-server="false"                                        
                :allowPaging="true"
                :showFilter="true"
                @selectedItemsChanged="grdBiendongChanged"
            > 
            </DataGrid> 
            </div>
        </div>
    </div>
    </b-modal>
</template>
<script>
    import breadcrumb from '@/components/breadcrumb'
    export default {
        components: {  },
        name: 'DSBDmodal',
        props: {
            list_bd:""
        },

        mounted () {
            
        },

        data () {
            return {
                biendong:{},
            }
        },

        methods: {
            grdBiendongChanged:function(obj){
                this.biendong = obj[0];
            },

            chapnhan:function(){
                this.$bvModal.hide('pop_dsbd');
                this.$emit('get_dsbd', this.biendong);
            }
        }
    }
</script>