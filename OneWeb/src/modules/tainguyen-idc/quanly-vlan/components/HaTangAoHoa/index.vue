<template>
    <div class="ha-tang-ao-hoa-container">
        <div class="filter-section">
            <div class="select-wrapper">
                <div class="select-control">
                    <SelectExt v-model="selectedHaTang" dataValueField="id" dataTextField="ten"
                        @change="loadHaTangData(selectedHaTang)"
                        :dataSource="haTangTypes">
                    </SelectExt>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="row">
                <div class="legend-title">Danh sách {{ haTangName }}</div>
            </div>
            <div class="row">
                <div class="col-12">
                    <DataGrid ref="gridItems" :allowPaging="true" :enablePagingServer="false" :showColumnCheckbox="true"
                        :allowExcelExport="true" v-bind:columns="haTangColumns" :dataSource="[]">
                    </DataGrid>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import { haTangTypeOptions } from './const'
import api from '../../api/vlan'
export default {
    name: 'HaTangAoHoa',
    data () {
        return {
            haTangColumns: [
                { field: 'mahatang_infra', headerText: 'Mã hạ tầng', width: '150' },
                { field: 'ten', headerText: 'Tên hạ tầng', width: '200' },
                { field: 'hatang_congnghe', headerText: 'Công nghệ', width: '150' }, // chưa có
                { field: 'ten_idc', headerText: 'IDC', width: '150' },
                { field: 'ten_nguoiqly', headerText: 'Người quản lý', width: '150' },
                { field: 'ten_phongbanqly', headerText: 'Phòng ban quản lý', width: '150' },
                { field: 'trangthai_ccdv', headerText: 'Trạng thái CCDV', width: '150' }, // Chưa có
                { field: 'thao_tac', headerText: 'Thao tác', width: '100' }
            ],
            haTangTypes: [],
            selectedHaTang: null,
            datasource: []
        }
    },
    computed: {
        haTangName () {
            const selected = this.haTangTypes.find(type => type.id == this.selectedHaTang)
            return selected ? selected.ten : ''
        }
    },
    mounted () {
        this.haTangTypes = haTangTypeOptions
        this.selectedHaTang = this.haTangTypes[0].id
    },
    methods: {
        async loadHaTangData (type) {
            this.$root.showLoading(true)
            try {
                this.datasource = await api.getHaTang(type) 
            } catch (error) {
                this.$toast.error('Lỗi khi tải dữ liệu hạ tầng ảo hóa.')
            } finally {
                this.$root.showLoading(false)
            }
        }
    }
}
</script>
<style scoped>
.select-wrapper {
    display: flex;
    justify-content: flex-end;
}

.select-control {
    width: 250px;
}
</style>
