<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>{{tieude}}</div>
        <div class="close -ap icon-close hidden" data-dismiss="modal">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li v-if="btnAccept">
                <a href="#" @click.prevent="closeForm">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
                </a>
            </li>
            <li v-if="btnThoat">
                <a href="#" @click.prevent="terminateForm">
                    <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">
        <div class="row">
            <div class="col-12">
                <div class="box-form">
                    <div class="legend-title mart20">{{tieudebang}}</div>
                    <DataGrid2
                        v-bind:columns="ds.cols"
                        v-bind:dataSource="ds.list" :showFilter="false" :showColumnCheckbox="false"
                        :dataKeyField="ds.keycol" :defaultColumnCheckboxChecked="false" :panelDataHeight="200" @rowClicked="rowChanged">
                    </DataGrid2>
                </div>
            </div>
        </div>
    </div>
</div>
</template>
<style scoped>

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
            btnAccept: true,
            tieude: "popup",
            tieudebang: "datapopup",
        }
    },
    mounted() {
        this.ds.list = this.dulieu.ds;
        if(this.ds.list && this.ds.list.length>0) this.ds.value = this.ds.list[0];
        this.ds.cols = this.dulieu.cols;
        this.ds.keycol = this.dulieu.keycol;
        if(this.dulieu.allowExit) this.btnThoat = this.dulieu.allowExit;
        if(this.dulieu.allowAccept!=null && this.dulieu.allowAccept!==undefined) this.btnAccept = this.dulieu.allowAccept;
        if(this.ds.list && this.ds.list.length>0) this.ds.value = this.ds.list[0];
        if(this.dulieu.tieude) this.tieude = this.dulieu.tieude;
        if(this.dulieu.tieudebang) this.tieudebang = this.dulieu.tieudebang;
    },
    computed: {
    },
    methods: {
        closeForm() {
            this.$emit('form-close',this.ds.value);
        },
        terminateForm() {
            this.$emit('form-close',null);
        },
        rowChanged(idx,item) {
            this.ds.value = item;
        },
    }
}
</script>
