<template>
    <b-modal 
        :id="modalId" 
        size="xl" 
        :data="data"    
        no-close-on-backdrop
        hide-footer 
        hide-header 
        hide-header-close     
        body-class="modal-body p-0">
    <div class="modal-content popup-box">    
        <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span> Danh sách thiết bị IDC
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
                            v-bind:columns="DSThietbiIDC.cols" 
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
        name: 'popupThietbiIDC',
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
                DSThietbiIDC: {
                    list: [],
                    value: {},
                    cols: [
                        {fieldName: 'ma_tb', headerText: 'Mã TB(ĐHSX)', allowFiltering: true, allowSorting: true, autoFit: "true",},
                        {fieldName: 'id_tbi', headerText: 'ID thiết bị', allowFiltering: true, allowSorting: true, autoFit: "true",},
                        {fieldName: 'ten_tbi', headerText: 'Tên thiết bị', allowFiltering: true, allowSorting: true, autoFit: "true",},                    
                        {fieldName: 'loai_tbi', headerText: 'Loại tài sản/thiết bị', allowFiltering: true, allowSorting: true, autoFit: "true",},                    
                        {fieldName: 'serial', headerText: 'Serial number', allowFiltering: true, allowSorting: true,  textAlign: 'center',autoFit: "true",},                    
                        {fieldName: 'ip', headerText: 'IP', allowFiltering: true, allowSorting: true,  autoFit: "true",},
                        {fieldName: 'cong_suat', headerText: 'Công suất', allowFiltering: true, allowSorting: true,  autoFit: "true",},
                        {fieldName: 'khong_gian', headerText: 'Không gian', allowFiltering: true, allowSorting: true,  autoFit: "true",},
                        {fieldName: 'tang', headerText: 'Tầng', allowFiltering: true, allowSorting: true,  autoFit: "true",},
                        {fieldName: 'noi_lapdat', headerText: 'Nơi đặt (IDC)', allowFiltering: true, allowSorting: true,  autoFit: "true",},
                        {fieldName: 'diachi_rack', headerText: 'Địa chỉ RACK', allowFiltering: true, allowSorting: true,  autoFit: "true",},
                        {fieldName: 'u_duoi_tbi', headerText: 'U dưới', allowFiltering: true, allowSorting: true,  autoFit: "true",},
                        {fieldName: 'u_tren_tbi', headerText: 'U trên', allowFiltering: true, allowSorting: true,  autoFit: "true",},
                        {fieldName: 'ngay_tiepnhan', headerText: 'Ngày tiếp nhận (IDC)', allowFiltering: true, allowSorting: true,  textAlign: 'center',autoFit: "true",},
                        {fieldName: 'ngay_sd', headerText: 'Ngày sử dụng (IDC)', allowFiltering: true, allowSorting: true,  textAlign: 'center',autoFit: "true",},
                        {fieldName: 'ngay_nt', headerText: 'Ngày IDC nghiệm thu', allowFiltering: true, allowSorting: true,  textAlign: 'center',autoFit: "true",},
                        {fieldName: 'trangthai_hd', headerText: 'Trạng thái HĐ', allowFiltering: true, allowSorting: true,  autoFit: "true",},
                        {fieldName: 'vma_gd', headerText: 'Mã GD(ĐHSX)', allowFiltering: true, allowSorting: true,  autoFit: "true",},
                        {fieldName: 'so_pyc', headerText: 'Số PYC backend IDC', allowFiltering: true, allowSorting: true,  autoFit: "true",},
                        
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