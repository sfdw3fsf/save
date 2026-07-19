<template>
<div>
    <!-- <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>{{tieude}}</div>
        <div class="close -ap icon-close hidden" data-dismiss="modal">
        </div>
    </div> -->
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="closeForm">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận (F5)
                </a>
            </li>
            <li :class="{hidden:!btnThoat}">
                <a href="#" @click.prevent="terminateForm">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Thoát
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">        
        <div class="box-form">
            <div class="legend-title mart20">{{tieudebang}}</div>
            <DataGrid 
                v-bind:columns="ds.cols" 
                v-bind:dataSource="ds.list" 
                :showFilter="false" 
                :showColumnCheckbox="false" 
                :dataKeyField="ds.keycol" 
                :defaultColumnCheckboxChecked="false"                                              
                :enabled-select-first-row="true"
                :enable-paging-server="false"                                        
                :allowPaging="true"
                @rowSelected="rowChanged" 
                > 
            </DataGrid>            
        </div>            
    </div>
</div>
</template>
<style scoped>
    #_dialog-content {display: none;}
    .e-dlg-content {display: none;}
</style>
<script>
export default {
    name: 'popupChonGrid',
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            ds: {
                list: [],
                cols: [],
                keycol: "",
                value: {},
            },
            btnThoat: false,
            tieude: "popup",
            tieudebang: "datapopup",
        }
    },
    mounted() {
        
    },
    computed: {
    },
    methods: {
        loadData(dulieu){
            console.log(dulieu)
            this.ds.list = dulieu.ds;
            if(this.ds.list && this.ds.list.length>0) this.ds.value = this.ds.list[0];
            this.ds.cols = dulieu.cols;
            this.ds.keycol = dulieu.keycol;
            if(dulieu.allowExit) this.btnThoat = dulieu.allowExit;
            if(this.ds.list && this.ds.list.length>0) this.ds.value = this.ds.list[0];
            if(dulieu.tieude) this.tieude = dulieu.tieude;
            if(dulieu.tieudebang) this.tieudebang = dulieu.tieudebang;
        }
        ,
        closeForm() {
            console.log(this.ds.value);
            this.$emit('form-close',this.ds.value);
        },
        terminateForm() {
            this.$emit('form-close',null);
        },
        rowChanged(item) {
            this.ds.value = item.data;
        },
    }
}
</script>