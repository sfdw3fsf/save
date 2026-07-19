<template>
    <b-modal
        id="popup-frmLichSuHenBaoHong"
        size="xl"
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0"
        centered 
        @show="onShow"
    >
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span>{{title}}
                </div>
                <div class="close -ap icon-close" data-dismiss="modal" 
                    @click="$bvModal.hide('popup-frmLichSuHenBaoHong')">
                </div>
            </div>
            <div class="popup-body">
                <div class="box-form">
                 <DataGrid v-if="kieu==2"
                        v-bind:columns="collumnsLSHenBaohong" 
                        v-bind:dataSource="pLichSuHen" 
                        :showFilter="true" 
                        ref="gridLichSuHenBaoHong"
                        :allowPaging="true" 
                        :enablePagingServer="false" 
                        :enabledSelectFirstRow="true" 
                        :enableVirtualization="true"
                        :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                        panelDataHeight="250">
                </DataGrid>
                <DataGrid v-if="kieu==1"
                        v-bind:columns="collumnsLSHenThicong" 
                        v-bind:dataSource="pLichSuHen" 
                        :showFilter="true" 
                        ref="gridLichSuHenBaoHong"
                        :allowPaging="true" 
                        :enablePagingServer="false" 
                        :enabledSelectFirstRow="true" 
                        :enableVirtualization="true"
                        :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                        panelDataHeight="250">
                </DataGrid>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<script>
import collums from "../define/collums.js";
export default {
    name: "frmLichSuHenBaoHong",
    props: {
        baoHongId: {
            type: Number,
            default: 0
        },
        hdtbId: {
            type: Number,
            default: 0
        },
        kieu: {
            type: Number,
            default: 0
        }
    },
    data() {
        return {
            pLichSuHen: [],
             ...collums,
        };
    },
    methods: {
        async onShow() {
            this.$root.showLoading(true);
            try {
                if (this.kieu == 2) {
                    this.title = "Lịch sử hẹn báo hỏng"
                    this.pLichSuHen = await this.getLichSuHenBaoHong();
                }
                else {
                    this.title = "Lịch sử hẹn thi công"
                    this.pLichSuHen = await this.getLichSuHenThiCong();
                }
            } catch (e) {
            } finally {
                this.$root.showLoading(false);
            }
        },
        async getLichSuHenBaoHong() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-thue-bao/lich-su-hen-bao-hong",
                    {
                        baoHongId: this.baoHongId,
                        pageNo: 1,
                        pageSize: 10000
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data.data || [];
                }
                return [];
            } catch (e) {
                return [];
            }
        },
        async getLichSuHenThiCong() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-kh-hd-tb/lich-hen-thi-cong",
                    {
                        hdtbId: this.hdtbId,
                        pageNo: 1,
                        pageSize: 10000
                    }
                );
                if (response.error_code === "BSS-00000000") {
                    return response.data.data || [];
                }
                return [];
            } catch (e) {
                return [];
            }
        }
    }
};
</script>
