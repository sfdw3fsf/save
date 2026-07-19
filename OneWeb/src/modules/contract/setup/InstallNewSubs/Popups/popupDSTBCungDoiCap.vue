<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Chọn mã truy nhập (Khách hàng khác)</div>
        <div class="close -ap icon-close hidden" data-dismiss="modal">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="closeForm" :class="{ disabled:!(ds.list && ds.list.length>0 && ds.value) }">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận (F5)
                </a>
            </li>
            <li :class="{hidden:!btnThoat}">
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
                    <DataGrid2
                        v-bind:columns="ds.cols"
                        v-bind:dataSource="ds.list" :showFilter="true" :showColumnCheckbox="false"
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
    name: 'popupDSTBCungDoiCap',
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            ds: {
                list: [],
                cols: [
                    {fieldName: 'ma_tb', headerText: 'Số máy / Acc', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'trangthai_tb', headerText: 'Trạng thái', allowFiltering: true, allowSorting: true, },{fieldName: 'matb_tn', headerText: 'Mã truy nhập', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'madoicap', headerText: 'Mã đôi cáp', allowFiltering: true, allowSorting: true, },
                    {fieldName: 'diachi_ld', headerText: 'Địa chỉ LĐ', allowFiltering: true, allowSorting: true, },
                ],
                keycol: "ma_tb",
                value: {},
            },
            // hdkh_id: 0,
            khachhang_id: 0,
            kieuld_id: 0,
            loaitb_id: 0,
            dichvuvt_id: 0,
            ma_tn: "",
            ma_doicap: "",
            btnThoat:this.dulieu.btnThoat?this.dulieu.btnThoat:false,
        }
    },
    async mounted() {
        if(this.dulieu.khachhang_id) this.khachhang_id = this.dulieu.khachhang_id;
        if(this.dulieu.kieuld_id) this.kieuld_id = this.dulieu.kieuld_id;
        if(this.dulieu.loaitb_id) this.loaitb_id = this.dulieu.loaitb_id;
        if(this.dulieu.dichvuvt_id) this.dichvuvt_id = this.dulieu.dichvuvt_id;
        this.$root.showLoading(true);
        if(this.dulieu.bancheo) {
            var rs = await this.$root.context.post("/web-bancheo/tracuu/lay_ds_tb_cungdoicap_theo_khid",{ "tinhthicong_id": this.dulieu.tinhthicong_id,"khachhang_id": this.khachhang_id,"kieuld_id": this.kieuld_id });
            this.ds.list = rs.data;
            if(this.ds.list && this.ds.list.length>0 && (!this.ds.value || !this.ds.value['ma_tb']))
                this.rowChanged(0,this.ds.list[0]);
        }
        else {
            var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/lay_ds_tb_cungdoicap_theo_khid",{ "vkhachhang_id": this.khachhang_id,"vkieuld_id": this.kieuld_id });
            this.ds.list = rs.data;
            if(this.ds.list && this.ds.list.length>0 && (!this.ds.value || !this.ds.value['ma_tb']))
                this.rowChanged(0,this.ds.list[0]);
        }
        this.$root.showLoading(false);
    },
    computed: {
    },
    methods: {
        async closeForm() {
            if(!this.ds.value || !this.ds.value['ma_tb']) {
                this.$toast.error("Hãy chọn mã thuê bao!");
                return;
            }
            if(this.dulieu.bancheo) {
                this.$emit('form-close',this.UpperCasePropertyList(this.ds.value));
                return;
            }
            var rs = await this.$root.context.post("/web-hopdong/lapdatmoi/fn_kt_tt_dstb_cung_doicap",{ "ds_para": JSON.stringify({"KHACHHANG_ID":this.khachhang_id,"DICHVUVT_ID":this.dichvuvt_id,"LOAITB_ID":this.ds.value['loaitb_id'],"TRANGTHAITB_ID":this.ds.value['trangthaitb_id'],"MA_TN":this.ds.value['ma_tb'],"TRANGTHAI_TB":this.ds.value['trangthai_tb'],"KIEULD_ID":this.kieuld_id,"VLOAITB_ID":this.loaitb_id,"THUEBAO_ID":this.ds.value['thuebao_id'],"MADOICAP":this.ds.value['madoicap']}) });
            var apidata = JSON.parse(rs.data);
            if(apidata['ERROR_CODE']==1) {
                this.$emit('form-close',this.UpperCasePropertyList(this.ds.value));
            }
            else if(apidata['ERROR_CODE']==-2) {
                var cfr = await this.$bvModal.msgBoxConfirm(apidata['MESSAGE'], {
                    title: 'Xác nhận hành động',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    return v;
                });
                if(cfr)
                    this.$emit('form-close',this.UpperCasePropertyList(this.ds.value));
            }
            else if(apidata['ERROR_CODE']==-2) {
                var cfr = await this.$bvModal.msgBoxConfirm(apidata['MESSAGE'], {
                    title: 'Xác nhận hành động',
                    size: 'sm',
                    okTitle: 'Đồng ý',
                    cancelTitle: 'Hủy',
                }).then(async v => {
                    return v;
                });
                if(cfr)
                    this.$emit('form-close',this.ds.value);
            }
            else {
                this.$toast.error(apidata['MESSAGE']);
            }
        },

        UpperCasePropertyList(obj) {
            var item = obj;
                for(var key in item){
                    var upper = key.toUpperCase();
                    // check if it already wasn't uppercase
                    if( upper !== key ){
                        item[ upper ] = item[key];
                        delete item[key];
                    }
                }
                return item;
            
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
