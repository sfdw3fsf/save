<template>
    <b-modal
        id="popup-frmLichSuBaoHong"
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
                    <span class="icon one-notepad"></span>Lịch sử báo hỏng
                </div>
                <div
                    class="close -ap icon-close"
                    data-dismiss="modal"
                    @click="$bvModal.hide('popup-frmLichSuBaoHong')"
                ></div>
            </div>
            <div class="popup-body">
                <div class="box-form">
                  <DataGrid v-bind:columns="collumnsLSBaohong" 
                        v-bind:dataSource="pLichSuBaoHong" 
                        :showFilter="true" 
                        ref="gridLichSuBaoHong"
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
    name: "frmLichSuBaoHong",
    props: {
        thueBaoId: {
            type: Number,
            default: 0
        }
    },
    data() {
        return {
            pLichSuBaoHong: [],
            ...collums,
        };
    },
    methods: {
        async onShow() {
            this.$root.showLoading(true);
            try {
                this.pLichSuBaoHong = await this.getLichSuBaoHongThueBao();
            } catch (e) {
            } finally {
                this.$root.showLoading(false);
            }
        },
        async getLichSuBaoHongThueBao() {
            try {
                let response = await this.$root.context.get(
                    "/web-cskh/api/khao-sat-thue-bao/lich-su-bao-hong",
                    {
                        thueBaoId: this.thueBaoId,
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
