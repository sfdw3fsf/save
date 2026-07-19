<template>
    <ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgSapXep"
        header="Sắp xếp kết xuất chi tiết công nợ"
        :animationSettings='animationSettings'
        showCloseIcon=true width='30%'
        :target='target'
        :open="dialogOpen"
        :close="close"
        :isModal="true"
    >
        <div class="">                
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href="javascript: void(0)" @click="onSort">
                                <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Sắp xếp
                            </a>
                        </li>
                        <li>
                            <div class="sort" style="border-left: 1px solid #BAE7FF;padding-left: 10px;padding-right: 10px;">
                                <a href="javascript: void(0)" @click="onUp" class="btn btn-main">
                                    <span class="-ap icon-chevron-thin-up"></span>
                                </a>
                                <a href="javascript: void(0)" @click="onDown" class="btn btn-main">
                                    <span class="-ap icon-chevron-thin-down"></span>
                                </a>
                                
                            </div>
                        </li>
                        <li>
                            <div class="select-custom" style="min-width: 200px;">
                                <select name="" id="" class="form-control" v-model="params.truong_Selected">
                                    <option v-for="item in params.truong_Options" :key="item.MA" :value="item.MA">{{ item.TEN }}</option>
                                </select>
                            </div>
                        </li>
                
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="table-content">
                            <table class="table-result table-gachle">
                                <thead>
                                    <th class="w20 center"></th>
                                    <th class="w20 center">Xóa</th>
                                    <th>Tên</th>
                                    <th>Cách sắp xếp</th>
                                </thead>
                                <tbody>
                                    <tr v-for="(item, idx) in items" :key="item.MA" :style="{background: index == idx ? '#FFF0C8 !important' : null }" @click="onSelectRow(idx)">
                                        <td>
                                            <span class="fa fa-play text-main" v-if="index == idx"></span>
                                        </td>
                                        <td class="w20 center">
                                            <a href="javascript: void(0)" @click="onDelete(item.MA)" class="act red">
                                                <span class="one-trash"></span>
                                            </a>
                                        </td>
                                        <td>{{ item.TEN }}</td>
                                        <td>
                                            <select name="" id="" class="form-control" v-model="item.SAPXEP_ID">
                                                <option value='1'>Tăng</option>
                                                <option value='2'>Giảm</option>
                                            </select>
                                        </td>
                                    </tr>                                    
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
    </ejs-dialog>    
</template>
<style scoped>
    .btn {
        color: aliceblue !important
    }
</style>>
<script>
export default {
    components: {},
    name: "PopupSapXep",

    props: {
        // items: []
    },

    async created() {},

    mounted () {
        this.items = [
            { MA: 12, TEN: 'Loại khách hàng', SAPXEP_ID: '1', FIELD: 'LOAIKH_ID' },
            { MA: 8, TEN: 'Đơn vị quản lý', SAPXEP_ID: '1', FIELD: 'DONVIQL_ID' },
            { MA: 9, TEN: 'Nhân viên quản lý', SAPXEP_ID: '1', FIELD: 'NHANVIENQL_ID' },
            { MA: 10, TEN: 'Tuyến thu', SAPXEP_ID: '1', FIELD: 'TUYENTHU_ID' },
            { MA: 24, TEN: 'Thứ tự in', SAPXEP_ID: '1', FIELD: 'THUTU_IN' },
            { MA: 2, TEN: 'Mã thanh toán', SAPXEP_ID: '1', FIELD: 'MA_TT' },
        ]
    },

    data() {
        return {
            target: '.main-wrapper',
            animationSettings: { effect: 'Zoom' },
            params: {
                truong_Options: [
                    { MA: 0, TEN: 'Lần tạo', SAPXEP_ID: '1', FIELD: 'LANTAO_ID' },
                    { MA: 1, TEN: 'Kỳ hóa đơn', SAPXEP_ID: '1', FIELD: 'KYHOADON' },
                    { MA: 2, TEN: 'Mã thanh toán', SAPXEP_ID: '1', FIELD: 'MA_TT' },
                    { MA: 3, TEN: 'Tên thanh toán', SAPXEP_ID: '1', FIELD: 'TEN_TT' },
                    { MA: 4, TEN: 'Địa chỉ thanh toán', SAPXEP_ID: '1', FIELD: 'DIACHI_TT' },
                    { MA: 5, TEN: 'Địa chỉ CT', SAPXEP_ID: '1', FIELD: 'DIACHI_CT' },
                    { MA: 6, TEN: 'Số điện thoại', SAPXEP_ID: '1', FIELD: 'SO_DT' },
                    { MA: 7, TEN: 'Fax', SAPXEP_ID: '1', FIELD: 'FAX' },
                    { MA: 8, TEN: 'Đơn vị quản lý', SAPXEP_ID: '1', FIELD: 'DONVIQL_ID' },
                    { MA: 9, TEN: 'Nhân viên quản lý', SAPXEP_ID: '1', FIELD: 'NHANVIENQL_ID' },
                    { MA: 10, TEN: 'Tuyến thu', SAPXEP_ID: '1', FIELD: 'TUYENTHU_ID' },
                    { MA: 11, TEN: 'Nhân viên thu cước', SAPXEP_ID: '1', FIELD: 'MA_NVTC' },
                    { MA: 12, TEN: 'Loại khách hàng', SAPXEP_ID: '1', FIELD: 'LOAIKH_ID' },
                    { MA: 13, TEN: 'Hình thức thanh toán', SAPXEP_ID: '1', FIELD: 'HTTT_ID' },
                    { MA: 14, TEN: 'Tên Công ty TTKD', SAPXEP_ID: '1', FIELD: 'TEN_CTY' },
                    { MA: 15, TEN: 'Địa chỉ công ty TTKD', SAPXEP_ID: '1', FIELD: 'DIACHI_CTY' },
                    { MA: 16, TEN: 'Người nhận', SAPXEP_ID: '1', FIELD: 'NGUOINHAN' },
                    { MA: 17, TEN: 'Tên đơn vị', SAPXEP_ID: '1', FIELD: 'TEN_DV' },
                    { MA: 18, TEN: 'Số DT người nhận', SAPXEP_ID: '1', FIELD: 'SO_DT_NN' },
                    { MA: 19, TEN: 'Fax người nhận', SAPXEP_ID: '1', FIELD: 'FAX_NN' },
                    { MA: 20, TEN: 'EMAil người nhận', SAPXEP_ID: '1', FIELD: 'EMAIL_NN' },
                    { MA: 21, TEN: 'Chức danh thư kí xác nhận bên TTKD', SAPXEP_ID: '1', FIELD: 'CHUCDANH' },
                    { MA: 22, TEN: 'Người kí bên TTKD', SAPXEP_ID: '1', FIELD: 'NGUOIKY' },
                    { MA: 23, TEN: 'Nhóm công nợ', SAPXEP_ID: '1', FIELD: 'NHOMCN_ID' },
                    { MA: 24, TEN: 'Thứ tự in', SAPXEP_ID: '1', FIELD: 'THUTU_IN' }
                ],
                truong_Selected: 0                
            },            
            items: [],
            index: 0,
            isClose: false
        }
    },

    methods: {
        dialogOpen() {
        },

        dialogClose() {        
        },

        openDialog () {
            this.$refs.dlgSapXep.show();
        },

        onSelectRow(idx) {
            this.index = idx;
        },

        close() {            
            this.index = 0;
            this.isClose = true;
            this.params.truong_Selected = 0;
            this.items = [
                { MA: 12, TEN: 'Loại khách hàng', SAPXEP_ID: '1', FIELD: 'LOAIKH_ID' },
                { MA: 8, TEN: 'Đơn vị quản lý', SAPXEP_ID: '1', FIELD: 'DONVIQL_ID' },
                { MA: 9, TEN: 'Nhân viên quản lý', SAPXEP_ID: '1', FIELD: 'NHANVIENQL_ID' },
                { MA: 10, TEN: 'Tuyến thu', SAPXEP_ID: '1', FIELD: 'TUYENTHU_ID' },
                { MA: 24, TEN: 'Thứ tự in', SAPXEP_ID: '1', FIELD: 'THUTU_IN' },
                { MA: 2, TEN: 'Mã thanh toán', SAPXEP_ID: '1', FIELD: 'MA_TT' },
            ];
            this.$refs.dlgSapXep.hide();
        },

        onSort() {            
            let sort = this.items.map(({ FIELD, SAPXEP_ID }) => ({ FIELD, SAPXEP_ID }));
            this.$emit("sort", sort);
            this.close();
        },

        onUp() {
            if (this.index > 0) {                
                let temp = this.items[this.index];
                this.items[this.index] = this.items[this.index-1];
                this.items[this.index-1] = temp;
                this.index -= 1;
            }            
        },

        onDown() {
            if (this.index < this.items.length-1) {
                let temp = this.items[this.index];
                this.items[this.index] = this.items[this.index+1];
                this.items[this.index+1] = temp;
                this.index += 1;
            }            
        },        

        onDelete(ma) {            
            if (this.items.length > 0) {
                let index = this.items.findIndex(e => e.MA === ma);
                if (index !== -1) {                    
                    this.items.splice(index, 1);                    
                }
            }            
        }
    },

    watch: {
        'params.truong_Selected': async function(newVal, oldVal) {
            if (!this.isClose) {
                let checkIndex = this.items.findIndex(e => e.MA === newVal);            

                if (checkIndex === -1) {
                    let item = this.params.truong_Options.find(e => e.MA === newVal);
                    this.items.push(item);
                }
            } 
            this.isClose = false;
        },
    }
}
</script>
