<template>
    <b-modal 
        v-model="modalShow"
        size="lg" 
        no-close-on-backdrop
        hide-footer 
        hide-header 
        hide-header-close >
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title"><span class="icon one-notepad"></span>Lịch sử thay đổi thông tin cáp</div>
                <div class="close -ap icon-close" data-dismiss="modal" @click="closeModal">
                </div>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="legend-title">
                        <div class="title">Lịch sử thay đổi cáp của thuê bao {{ tenThueBao }}</div>
                    </div>
                    <DataGrid v-bind:columns="[
                        {fieldName: 'TEN_DC', headerText: 'Đầu cuối', allowFiltering: true},
                        {fieldName: 'TEN_KC', headerText: 'Tên kết cuối', allowFiltering: true},
                        {fieldName: 'MA_KC', headerText: 'Mã kết cuối', allowFiltering: true},
                        {fieldName: 'VITRI', headerText: 'Vị trí', allowFiltering: true},
                        {fieldName: 'MAT', headerText: 'Mặt', allowFiltering: true},
                        {fieldName: 'TRANGTHAI', headerText: 'Trạng thái', allowFiltering: true},
                        {fieldName: 'NGUOI_CN', headerText: 'Người cập nhật', allowFiltering: true},
                        {fieldName: 'MAY_CN', headerText: 'Máy cập nhật', allowFiltering: true},
                        {fieldName: 'IP_CN', headerText: 'IP', allowFiltering: true},
                        {fieldName: 'NGAY_CN', headerText: 'Ngày cập nhật', allowFiltering: true},
                        {fieldName: 'ACTION', headerText: 'Hành động', allowFiltering: true}
                    ]" 
                    v-bind:dataSource="lichSuCap" :showFilter="true" @selectedRowChanged="grid_selectedRowChanged"></DataGrid>
                </div>
            </div>
        </div>
    </b-modal>
</template>

<script>
export default {
    name: "Modal",
    props: {
        modalShow: Boolean,
        thueBaoId: Number,
        tenThueBao: String
    },
    data() {
        return {
            lichSuCap: null
        }
    },
    methods: {
        closeModal: function() {
            this.$emit('closeModal', this.modalShow);
        },
        grid_selectedRowChanged: function() {

        }
    },
    async mounted() {
        if(this.thueBaoId) {
            try {
                this.loading(true);
                var rs = await this.$root.context.post(`/web-ecms/tracuu/lichsucap/${this.thueBaoId}`);
                this.lichSuCap = rs.data;
            } catch (error) {
                this.$toast.error('Xảy ra lỗi');
            } finally {
                this.loading(false);
            }
        }
    }
}
</script>
