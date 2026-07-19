<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a @click="tsbtnLayTT_Click()">
                        <span class="icon one-file-plus"></span> Lấy TT
                    </a>
                </li>
                <li>
                   <a @click="tsbtnXoaCache_Click()">
                        <span class="icon one-save"></span> Xóa cache
                    </a>
                </li>
            </ul>
        </div>
        <div class="page-content">
        <div class="box-form">
            <div class="legend-title">Danh sách bảng</div>
            <div class="table-content" style="height: 480px;">
                <DataGrid
                    ref="dtListTable"
                    v-bind:columns="[
                        {fieldName: 'name_table', headerText: 'Tên bảng', allowFiltering: true} 
                    ]"
                    v-bind:dataSource="dtListTable"
                    :showColumnCheckbox="true"
                    :showFilter="true"
                    :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                    panelDataHeight="355"
                    @selectedItemsChanged="grid_SelectRow"
                    :allowPaging="true" :enablePagingServer="false"
                ></DataGrid>
            </div>
        </div>
    </div>
  </div>
</template>
<script>

import breadcrumb from '@/components/breadcrumb'
import api from "./DeleteCache.js";

export default {
    components: {
        breadcrumb
    },
    data() {
        return {
            dtListTable: [],
            dtListTable_Selected: [],
            header: {
                title: "Xóa cache",
                list: [
                    { name: "Quản trị hệ thống", link: { name: "Ui.cards" } },
                    { name: "Xóa cache", link: { name: "Ui.buttons" }, },
                ],
            },
        };
    },
    mounted() {
        this.init();
    },
    methods: {
        async init() {
            await this.tsbtnLayTT_Click()
        },      
        async tsbtnLayTT_Click() {
            try {
                this.loading(true);
                this.dtListTable = await this.loadAllCacheTable();
                this.dtListTable_Selected = []
            } catch (e) {
                console.log(e);
            } finally {
                this.loading(false);
            }
        },
        async loadAllCacheTable() {
            let result = []
            try {
                let response = await api.list_table_cache(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data    
                }
            } catch (ex) {
                console.log(ex)
            }
            return result
        },
        grid_SelectRow(value) {
            this.dtListTable_Selected = value;
        },
        async tsbtnXoaCache_Click() {
            try {
                this.loading(true);
                let allTableS = ""
                this.dtListTable_Selected.forEach(item => {
                    allTableS += (item.name_table + "|")
                })
                await this.deleteCache(allTableS)
            } catch (ex) {
                console.log(ex)
            } finally {
                this.loading(false);
            }  
        },
        async deleteCache(name) {
            try {
                let params = {
                    "name": name
                }
                let response = await api.delete_cache_name_app(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                    this.$toast.success("Xóa cache thành công")
                }
            } catch (ex) {
                console.log(ex)
                this.$toast.error("Xóa cache thất bại: " + ex)
            }
        },
    }
};
</script>
