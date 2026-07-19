<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Đăng ký cải tạo hạ tầng</div>
        <div class="close -ap icon-close hidden" data-dismiss="modal">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="btnTimKiem_ItemClick">
                    <span class="icon one-search"></span>Tìm kiếm
                </a>
            </li>
            <li v-if="btnAccept">
                <a href="#" @click.prevent="CapNhat">
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
            <div class="col-md-12 info-row form-group">
                <div class="key w90">Mã khách hàng</div>
                <div class="value"><input type="text" :value="tbMaKhachHang" @change="x=>tbMaKhachHang=x.target.value" /></div>
                <div class="key w90">ID khách hàng (IDC)</div>
                <div class="value"><input type="text" :value="tbKhachHangID_IDC" @change="x=>tbKhachHangID_IDC=x.target.value" /></div>
            </div>
        </div>
        <div class="row">
            <div class="col-12">
                <div class="box-form">
                    <div class="legend-title mart20">Danh sách thuê bao</div>
                    <DataGrid
                        v-bind:columns="ds.cols"
                        v-bind:dataSource="ds.list" :showFilter="true" :showColumnCheckbox="true"
                        :dataKeyField="ds.keycol" :defaultColumnCheckboxChecked="false" :panelDataHeight="200" @rowClicked="rowChanged" @selectedItemsChanged="rowsChanged">
                    </DataGrid>
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
    name: 'popupDangKyCaiTaoHaTangIDC',
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            ds: {
                list: [],
                cols: [
                    {fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true, allowSorting: true},
                    {fieldName: 'ma_kh', headerText: 'Mã KH', allowFiltering: true, allowSorting: true},
                    {fieldName: 'thietbi', headerText: 'Tên thiết bị', allowFiltering: true, allowSorting: true},
                    {fieldName: 'ma_phu', headerText: 'ID Khách hàng IDC', allowFiltering: true, allowSorting: true},
                ],
                keycol: "",
                value: {},
                values: [],
            },
            hdtb_id: 0,
            tbKhachHangID_IDC: '',
            tbMaKhachHang: '',
            btnThoat: false,
            btnAccept: true,
        }
    },
    mounted() {
        // this.ds.list = this.dulieu.ds;
        // if(this.ds.list && this.ds.list.length>0) this.ds.value = this.ds.list[0];
        // this.ds.cols = this.dulieu.cols;
        // this.ds.keycol = this.dulieu.keycol;
        // if(this.dulieu.allowExit) this.btnThoat = this.dulieu.allowExit;
        // if(this.dulieu.allowAccept!=null && this.dulieu.allowAccept!==undefined) this.btnAccept = this.dulieu.allowAccept;
        // if(this.ds.list && this.ds.list.length>0) this.ds.value = this.ds.list[0];
        if(this.dulieu.hdtb_id) this.hdtb_id = this.dulieu.hdtb_id;
    },
    computed: {
    },
    methods: {
        async CapNhat() {
            
            if (!this.hdtb_id)
            {
                this.$toast.error("Chưa có thuê bao được chọn");
                return;
            }
            //var selectList = this.ds.values;//this.ds.list.find(x=>x.chon);
            //POST web-thicong/host/capnhat_thuebao_caitao_hatang_idc { vhdtb_id, vdata } vdata in clob -- format [{"THUEBAO_ID": 12121212, "MA_TB": "hpg_colocation_00000001"}]
            var rs = await this.$root.context.post('/web-thicong/host/capnhat_thuebao_caitao_hatang_idc',{'vhdtb_id': this.hdtb_id,'vdata': JSON.stringify(this.ds.values.map(x=>({"THUEBAO_ID": x.thuebao_id, "MA_TB":x.ma_tb})))});
            if(rs && rs.data=="ok") {
                this.$toast.success("Cập nhật danh sách thuê bao cải tạo hạ tầng thành công");
                this.$emit('form-close',this.ds.value);
            }
            else {
                this.$toast.error(rs.message);
            }
        },
        terminateForm() {
            this.$emit('form-close',null);
        },
        async btnTimKiem_ItemClick() {
            //POST web-thicong/host/lay_danhsachtb_dangky_ctht_idc { vma_kh, vidkh_idc }
            this.ds.list = [];
            var rs = await this.$root.context.post('/web-thicong/host/lay_danhsachtb_dangky_ctht_idc',{'vma_kh': this.tbMaKhachHang,'vidkh_idc': this.tbKhachHangID_IDC});
            if(rs && rs.data) {
                this.ds.list = rs.data;
            }
            else {
                this.$toast.error(rs.message);
            }
        },
        rowChanged(idx,item) {
            this.ds.value = item;
        },
        rowsChanged(lstItems) {
            this.ds.values = lstItems;
        }
    }
}
</script>
