<template>
<b-modal ref="popupGanChuongTrinh" size="xl" hide-header hide-header-close hide-footer body-class="p-0" no-close-on-backdrop @show="handleShowModal" @hide="handleHideModal">
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title">
                <span class="icon one-appstore"></span> Gán loại chương trình
            </div>
            <div class="close -ap icon-close" @click="hideModal"></div>
        </div>
        <vue-nav>
            <ul class="list">
                <li>
                    <a @click="btnCapNhat_Click()">
                        <span class="icon one-reload1"></span> Cập nhật (F5)
                    </a>
                </li>
            </ul>
        </vue-nav>
        <div class="popup-body">

            <b-row>
                <vue-card>
                    <DataGrid :dataSource="options.dsLoaiChuongTrinh" 
                    v-bind:columns="[
                        {
                            fieldName: 'MA_DV',
                            headerText: 'Mã đơn vị',
                            allowSorting: false,
                            textAlign: 'center',
                            autoFit: 'true',
                            template: NA_DVTemplate
                        },
                        {
                            fieldName: 'TENLOAI_CT',
                            headerText: 'Loại chương trình',
                            allowSorting: false,
                            textAlign: 'left',
                            autoFit: 'true'
                        },
                        {
                            fieldName: 'GHICHU',
                            headerText: 'Ghi chú',
                            allowSorting: false,
                            textAlign: 'left',
                            autoFit: 'true'
                        },
                        {
                            fieldName: 'LOAI_CT_ID',
                            visible: false
                        }
                    ]" 
                    :enablePagingServer="false" 
                    :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }" 
                    :enabledSelectFirstRow="false" 
                    :allowPaging="true"
                    :showColumnCheckbox="true" 
                    :showFilter="true" 
                    @actionComplete="selectRowsChon" @selectedItemsChanged="select_selectedItemsChanged" panelDataHeight="270" ref="dtLoaiCT"></DataGrid>
                </vue-card>
            </b-row>
        </div>
    </div>
</b-modal>
</template>

<script>
import collums from '../define/collums'
import VueElement from '../components/VueElement.vue'
import DepartmentAPI from '../API'
import DataGridCustom from '../components/DataGrid'
import Vue from "vue"
import EventBus from '@/utils/eventBus';
export default {
    components: {
        VueElement,
        DataGridCustom
    },
    props: {
        donviId: {
            type: Number,
            default: function () {
                return 0
            }
        }
    },
    data() {
        return {
            ...collums,
            target: '.main-wrapper',
            model: {},
            checkedLCT: [],
            options: {
                dsLoaiChuongTrinh: []
            },
            editedRowIndex: null, // Chỉ số row đang được chỉnh sửa
            editableValue: null, // Giá trị của input
            columns_LCT: [],
            NA_DVTemplate: function () {
                return {
                template: {
                    template: `<div>
                                <input type="text" class="form-control" v-model="data.MA_DV" @input="updateGridJson"/>
                                <span class="name"></span>
                            </div>`,
                    data() { return { data: {}}},
                    methods: {
                        updateGridJson() {
                            EventBus.$emit('updateGridJson', this.data);
                        }
                    }
            }}},
        }
    },
    mounted() {
          
    },
    created() {
        EventBus.$on('updateGridJson', this.handleUpdateGridJson);
    },
    computed: {},
    methods: {
       handleUpdateGridJson(updatedData) {
            // Giả sử bạn có một mảng các đối tượng trong gridData
            let item = this.options.dsLoaiChuongTrinh.find(item => item.LOAI_CT_ID === updatedData.LOAI_CT_ID);
            if (item) {
                // Cập nhật dữ liệu của đối tượng đó với thông tin mới từ updatedData
                item.MA_DV = updatedData.MA_DV;
            }
            // Nếu bạn muốn cập nhật toàn bộ gridData, bạn có thể thay thế hoặc thêm logic ở đây
        },
        async handleShowModal() {
            try {
                await this.SP_DONVI_LCT_NAPDULIEU()
            } catch (e) {
                console.log(e)
                this.$root.$toast.error(e.data ? e.data.message : 'Có lỗi xảy ra')
            }
        },
        handleHideModal() {

        },
        showModal() {
            this.$refs.popupGanChuongTrinh.show()
        },
        hideModal() {
            this.$refs.popupGanChuongTrinh.hide()
        },
        selectRowsChon() {
            let selectedRows = []
            for (let index in this.options.dsLoaiChuongTrinh) {
                if (this.options.dsLoaiChuongTrinh[index].CHON == '1') {
                    selectedRows.push(index)
                }
            }
            this.$refs.dtLoaiCT.$refs.grid.selectRows(selectedRows)
        },
        async btnCapNhat_Click() {
            this.loading(true)
            try {
                if (this.checkedLCT.length > 0) {
                    let ds = '['
                    for (let index in this.checkedLCT) {
                        try {
                            //let ov = '{LOAICT_ID: ' + this.checkedLCT[index].LOAI_CT_ID + ', MA_DV: ' + this.checkedLCT[index].MA_DV.toString() + '}' 
                            let ov = '{"LOAICT_ID": "' + this.checkedLCT[index].LOAI_CT_ID + '", "MA_DV": "' + this.checkedLCT[index].MA_DV.toString() + '"}';
                            if (index != this.checkedLCT.length - 1) ov += ','
                            ds += ov
                        } catch (e) {

                        }
                    }

                    ds += ']'
                    await this.SP_DONVI_LCT_CAPNHAT(ds)
                } else {
                    await this.SP_DONVI_LCT_CAPNHAT('')
                }

                await this.SP_DONVI_LCT_NAPDULIEU()
            } catch (e) {
                console.log(e)
                this.$toast.error('Cập nhật thất bại ' + e)
            }
            this.loading(false)
        },
        select_selectedItemsChanged(dataKeys) {
            this.checkedLCT = dataKeys
            this.options.dsLoaiChuongTrinh.forEach(function (item) {
                if (dataKeys.filter(x => x.CHON == '1')) {
                    item.ischecked = true
                } else {
                    item.ischecked = false
                }
            })
        },
        async SP_DONVI_LCT_NAPDULIEU() {
            this.options.dsLoaiChuongTrinh = []
            const params = {
                p_donvi_id: this.donviId
            }
            const response = await DepartmentAPI.SP_DONVI_LCT_NAPDULIEU(this.axios, params)
            if (response && response.data && response.data.error_code === 'BSS-00000000') {
                this.checkedLCT = []
                this.options.dsLoaiChuongTrinh = response.data.data
                if (this.options.dsLoaiChuongTrinh != undefined && this.options.dsLoaiChuongTrinh.length > 0) {
                    this.$refs.dtLoaiCT.setCurrentSelectedRow(0)
                    for (let index in this.options.dsLoaiChuongTrinh) {
                        if (this.options.dsLoaiChuongTrinh[index].CHON == '1') {
                            this.$refs.dtLoaiCT.$refs.grid.selectRow(index)
                        }
                    }
                }
            }
        },
        async SP_DONVI_LCT_CAPNHAT(ds) {
            const params = {
                p_donvi_id: this.donviId,
                p_loaict_id: 0,
                p_ma_dv: ds
            }
            const response = await DepartmentAPI.SP_DONVI_LCT_CAPNHAT(this.axios, params)
            console.log(response)
        }
    },
    watch: {}
}
</script>
