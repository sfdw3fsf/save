<template>
<b-modal 
    :id="modalId" 
    size="lg" 
    :data="data"    
    no-close-on-backdrop
    hide-footer 
    hide-header 
    hide-header-close     
    body-class="modal-body p-0">
<div class="modal-content popup-box">    
    <div class="popup-header">
            <div class="title">
                <span class="icon one-notepad"></span> Danh sách nhân viên thuộc địa bàn
            </div>
            <div
                class="close -ap icon-close"
                data-dismiss="modal"
                @click="$bvModal.hide(modalId)">
            </div>
        </div>
    <div class="popup-body">
        <div class="row">
            <div class="col-12">
                <div class="box-form">
                    
                    <DataGrid 
                        v-bind:columns="NhanVienDiaBan.cols" 
                        :enable-paging-server="false"
                        :enabled-select-first-row="false"
                        :show-checkbox = "false"
                        :allowPaging="true"                         
                        v-bind:dataSource="data" :showFilter="false" :showColumnCheckbox="false" 
                        :defaultColumnCheckboxChecked="false" > 
                    </DataGrid>
                </div>
            </div>
        </div>
        <div class="group-buttons -bottom center">            
            <button class="btn btn-second nocorner" style="width: 120px;" @click="$bvModal.hide(modalId)">
                <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Đóng
            </button>
        </div>
    </div>
</div>
</b-modal>
</template>
<style scoped>

</style>
<script>
export default {
    name: 'popupNhanVienDiaBan',
    props: [ "modalId", "data" ],
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
            hdkh_id: 0,
            vkhachhang_id: 0,
            vkieuld_id: 0,
            loaitb_id: 0,
            btnThoat: true,
            NhanVienDiaBan: {
                list: [],
                value: {},
                cols: [
                    {fieldName: 'ten_kv', headerText: 'Khu vực', allowFiltering: true, allowSorting: true, textAlign: 'center',autoFit: "true",},
                    {fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true, allowSorting: true,  textAlign: 'center',autoFit: "true",},
                    {fieldName: 'ma_nv', headerText: 'Mã NV', allowFiltering: true, allowSorting: true,  textAlign: 'center',autoFit: "true",},
                    {fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true, allowSorting: true,  textAlign: 'center',autoFit: "true",},
                    {fieldName: 'loai_nv', headerText: 'Loại nhân viên', allowFiltering: true, allowSorting: true,  textAlign: 'center',autoFit: "true",},
                    {fieldName: 'nhiemvu', headerText: 'Nhiệm vụ', allowFiltering: true, allowSorting: true,  textAlign: 'center',autoFit: "true",},
                    {fieldName: 'so_dt', headerText: 'Số điện thoại', allowFiltering: true, allowSorting: true, textAlign: 'right',autoFit: "true", },
                    {fieldName: 'daucuoi', headerText: 'Điểm', allowFiltering: true, allowSorting: true,  textAlign: 'center',autoFit: "true",},
                    ]
            },
        }
    },
    async mounted() {       
    },
    computed: {
    },
    methods: {
        async closeForm() {
            this.$emit('form-close',0);
        },
        terminateForm() {
            this.$emit('form-close',null);
        },        
    }
}
</script>