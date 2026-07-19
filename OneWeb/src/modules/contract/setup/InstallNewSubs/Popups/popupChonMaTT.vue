<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Chọn mã thanh toán</div>
        <div class="close -ap icon-close" data-dismiss="modal" @click="terminateForm">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="closeForm">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận (F5)
                </a>
            </li>
            <!-- <li>
                <a href="#">
                    <span class="icon one-file-attach1"></span>Lấy TT
                </a>
            </li>
            <li>
                <a href="#">
                    <span class="icon one-reload1"></span> Cập nhật
                </a>
            </li>
            <li>
                <a href="#">
                    <span class="icon one-book1"></span> Hướng dẫn
                </a>
            </li> -->
        </ul>
    </div>
    <div class="popup-body">
        <div class="box-form padt8 padb8 fw6 upper">
            CHỌN MÃ THANH TOÁN ĐỂ GHÉP CƯỚC
        </div>
        <div class="box-form">
            <div class="legend-title">Thông tin thanh toán</div>
            <gridview id="table2" :datalist="gridbox.thanhtoan.list" :headers="gridbox.thanhtoan.header" v-bind:flcol="false" v-bind:checkheader="false" lastcoltemplate="0" @recordChange="grdThanhToanChanged"></gridview>
        </div>
        <div class="box-form">
            <div class="legend-title">Thông tin thuê bao</div>
            <gridview id="table3" :datalist="gridbox.thuebao.list" :headers="gridbox.thuebao.header" v-bind:flcol="false" v-bind:checkheader="false" lastcoltemplate="0" @recordChange="grdThueBaoChanged"></gridview>
        </div>
    </div>
</div>
</template>
<style scoped>

</style>
<script>
import API from '../InstallNewSubsAPI'
import SearchAccountAPI from '@/modules/search/api/SearchAccountAPI'
import gridview from '@/components/Shared/gridview'
export default {
    name: 'popupChonMaTT',
    components: { gridview },
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            page: 0, //Page number
            khachhang_id: this.dulieu,
            gridbox: {
                thanhtoan: {
                    list:[],
                    value: "",
                    inghep: null,
                    header: [],
                },
                thuebao: {
                    fulllist:[],
                    list:[],
                    value: "",
                    header: [],
                },
            },
        }
    },
    mounted() {
        this.loadThanhToan();
        this.loadThueBao();
    },
    computed: {
    },
    methods: {
        closeForm() {
            this.$emit('form-close',{ma_tt:this.gridbox.thanhtoan.value,ma_tb:this.gridbox.thuebao.value});
        },
        terminateForm() {
            this.$emit('form-close',null);
        },
        loadThanhToan() {
            SearchAccountAPI.get_dbtt_theo_khid(this.axios, {params:{khachHangId:this.khachhang_id}}).then((response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
                this.gridbox.thanhtoan.list = response.data.data;
                    if(this.gridbox.thanhtoan.list && this.gridbox.thanhtoan.list.length>0) {
                        this.gridbox.thanhtoan.value = this.gridbox.thanhtoan.list[0].ma_tt;
                        this.gridbox.thanhtoan.inghep = this.gridbox.thanhtoan.list[0].main_ghep;
                    }
                    else { 
                        this.gridbox.thanhtoan.value = "";
                        this.gridbox.thanhtoan.inghep = null;
                    }
                    this.gridbox.thanhtoan.header = [{name:'ma_tt',text:'Mã TT'},{name:'matb_dd',text:'Mã đại diện'},{name:'ten_tt',text:'Tên thanh toán'},{name:'diachi_tt',text:'Địa chỉ thanh toán'},{name:'main_ghep',text:'Mã in ghép'},]
                }
            });
        },
        loadThueBao() {
            SearchAccountAPI.get_dbtb_theo_khid(this.axios, {params:{khachHangId:this.khachhang_id}}).then((response) => {
                if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
                this.gridbox.thuebao.list = response.data.data;
                this.gridbox.thuebao.fulllist = response.data.data;
                if(this.gridbox.thuebao.list && this.gridbox.thuebao.list.length>0)
                    this.gridbox.thuebao.value = this.gridbox.thuebao.list[0].ma_tb;
                else this.gridbox.thuebao.value = "";
                this.gridbox.thuebao.header = [{name:'ma_tt',text:'Mã TT'},{name:'ma_tb',text:'Số máy/Acc'},{name:'ten_tb',text:'Tên thuê bao'},{name:'diachi_ld',text:'Địa chỉ lắp đặt'},{name:'loaihinh_tb',text:'Loại hình TB'},{name:'trangthai_tb',text:'Trạng thái'},]
                }
            });
        },
        grdThueBaoChanged(obj) {
            this.gridbox.thuebao.value = obj.ma_tb;
        },
        grdThanhToanChanged(obj) {
            this.gridbox.thanhtoan.value = obj.ma_tt;
            this.gridbox.thanhtoan.inghep = obj.main_ghep;
        },
    }
}
</script>
