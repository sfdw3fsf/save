<template>
        <div class="table-content">
            <DataGrid
                v-bind:columns="[
                    // {fieldName: 'ID', headerText: 'ID', textAlign: 'Left', allowFiltering: true, width: '100px' },
                    { fieldName: 'TEN', headerText: 'Tên mặt sàn', textAlign: 'Left', allowFiltering: true },
                    // { fieldName: 'TEN_IDC', headerText: 'IDC', textAlign: 'Left', allowFiltering: true },
                    { fieldName: 'TEN_TOANHA', headerText: 'Toà nhà', textAlign: 'Left', allowFiltering: true },
                    { fieldName: 'DIEN_TICH', headerText: 'Diện tích (m²)', textAlign: 'Left', allowFiltering: true, width: '150px' },
                    { fieldName: 'CHIEU_DAI', headerText: 'Chiều dài (m)', textAlign: 'Left', allowFiltering: true,  width: '150px' },
                    { fieldName: 'CHIEU_RONG', headerText: 'Chiều rộng (m)', textAlign: 'Left', allowFiltering: true,  width: '150px' },
                    { fieldName: 'HIEU_LUC', headerText: 'Hiệu lực', textAlign: 'Left', allowFiltering: true,  width: '150px' },
                    { fieldName: 'GHI_CHU', headerText: 'Ghi chú', textAlign: 'Left', allowFiltering: true,  width: '150px' },
                    { fieldName: '', headerText: 'Hành động', textAlign: 'Left', allowFiltering: true, textAlign: 'Center', template: getColumnTemplateChucNang(this) }
                ]"
                :dataSource="listMatSan"
                :showFilter="true"
                :allowPaging="true"
                :enablePagingServer="false"
                :allowExcelExport="true"
                @recordDoubleClick="handleRowDoubleClick"
                dataKeyField="ID"
                ref="grdItems"
            />
        </div>
</template>

<script>
import api from '../api';

export default {
    name: 'GridMatSan',
    props: {
        toanhaId: {
            type: Number,
            default: 1
        }
    },
    data() {
        return {
            listMatSan: []
        };
    },
    mounted: async function () {
        // Load dữ liệu mặt sàn
        await this.loadMatSanData();
    },
    methods: {
        // ======== load data ========
        async loadMatSanData() {
            try {
                const response = await api.getDanhSachMatSan(this.axios, this.toanhaId);
                console.log('response ', response);
                
                this.listMatSan = response || [];
            } catch (error) {
                this.$toast.error(error.message);
            }
        },
        // ======== handle ui interaction ========
        onView (data) {
            this.$emit('viewMatSan', data.ID);
        },
        handleRowDoubleClick (args) {
            const selectedMatSan = {
                id: args.rowData.ID,
                type: 'MATSAN'
            }
            this.$emit('row-doubled-click', selectedMatSan)
        },
        getColumnTemplateChucNang(parent) {
            return function() {
                return {
                    template: {
                        template: `<div class="action-buttons">
                                        <button  class="btn btn-sm btn-outline-primary" @click="parent.onView(data)">
                                            <i class="fa fa-eye"></i>Xem
                                        </button>
                                    </div>`,
                        data() {
                            return {
                                parent: parent,
                                data: this.$parent?.data || {}
                            }
                        }
                    }
                }
            }
        },
    }
};
</script>
