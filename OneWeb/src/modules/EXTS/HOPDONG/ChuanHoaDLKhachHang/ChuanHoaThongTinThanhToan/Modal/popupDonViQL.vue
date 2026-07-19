<template>
    <b-modal id="popupDonViQL" size="xl"
        hide-footer
        hide-header
        hide-header-close
        @shown="focusMyElement"
        body-class="modal-body p-0"
    >
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title"><span class="icon one-notepad"></span>Chọn đơn vị quản lý</div>
                <div class="close -ap icon-close" data-dismiss="modal" @click="closeForm()">
                </div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="javascript:void(0)" @click="chapNhan()" title="Chấp nhận">
                            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="legend-title">
                        <div class="pull-left">
                            Danh sách đơn vị
                        </div>
                        <div class="pull-right red">
                            Lựa chọn tổ thi công, đơn vị quản lý
                        </div>
                        <div class="clearfix"></div>

                    </div>
                    <div class="table-content">
                        <DataGrid ref="gridDsDonVi" v-bind:columns="[
                                {fieldName: 'donviql_id', headerText: 'ID', allowFiltering: true, allowSorting: true, width: 50, cssClass: 'text-bold-blue'},
                                {fieldName: 'ten_dvql', headerText: 'Đơn vị', allowFiltering: true, allowSorting: true, width: 120, cssClass: 'text-bold-red'},
                                {fieldName: 'ten_dv', headerText: 'Tổ thi công', allowFiltering: true, allowSorting: true, width: 120}
                            ]"
                            v-bind:dataSource="dsDonVi"
                            :showFilter="true"
                            dataKeyField="donvi_id"
                            @selectedRowChanged="grid_selectedRowChanged"
                            @dataBound="gridDataBound"
                        >
                        </DataGrid>
                    </div>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<style scoped>
    >>>.text-bold-blue {
        color: blue !important;
        font-weight: bold !important;
    }
    >>>.text-bold-red {
        color: red !important;
        font-weight: bold !important;
    }
</style>
<script>
export default {
    name: 'popupDVQL',
    props: ["data"],
    watch: {
    },
    data() {
        return {
            dsDonVi: [],
            donViSelected:null,
        }
    },
    mounted() {
    },
    computed: {
    },
    methods: {
        focusMyElement: async function () {
            this.dsDonVi = this.data.ds
        },
        gridDataBound() {
            if (this.data.donvi_index_id != null && this.data.donvi_index_id !== 0) {
                let index = this.dsDonVi.findIndex(e => Number(e.donviql_id) === Number(this.data.donvi_index_id))
                if (index !== -1)
                    this.$refs.gridDsDonVi.setCurrentSelectedRow(index);
            }
        },
        grid_selectedRowChanged: async function(dataItem){
            this.donViSelected =  dataItem
        },
        chapNhan: async function() {
            this.$emit('acceptChangeDVQL',this.donViSelected)
            this.$bvModal.hide('popupDonViQL')
        },
        closeForm: async function() {
            this.$emit('form-close',this.donViSelected)
            this.$bvModal.hide('popupDonViQL')
        }
    }
}
</script>
