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
                <a @click.prevent="closeForm">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
                </a>
            </li>
            <li v-if="btnThoat">
                <a @click.prevent="terminateForm">
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
                    <SelectExt class="select-custom" v-model="ds.value" :dataSource="ds.list" :dataTextField="ds.textcol" :dataValueField="ds.valuecol"></SelectExt>
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
    name: 'popupChonComboBox',
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            ds: {
                list: [],
                textcol: 'text',
                valuecol: 'id',
                value: 0,
            },
            btnThoat: false,
            btnAccept: true,
            tieude: "popup",
            tieudebang: "datapopup",
        }
    },
    mounted() {
        this.ds.list = this.dulieu.ds;
        // for(var item of this.ds.list) {
        //     if(!item[valuecol]) item[valuecol] = '';
        //     if(!item[textcol]) item[textcol] = '';
        // }
        if(this.ds.list && this.ds.list.length>0) this.ds.value = this.ds.list[0];
        if(this.dulieu.value) this.ds.value = this.dulieu.value;
        this.ds.textcol = this.dulieu.textcol;
        this.ds.valuecol = this.dulieu.valuecol;
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
            this.$emit('form-close',-1);
        },
    }
}
</script>
