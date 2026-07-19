<template>
    <b-modal :id="modalID" size="xl" no-close-on-backdrop hide-footer hide-header-close hide-header>
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span> Thêm mới chủ đề cấp 1
                </div>
                <div class="close -ap icon-close" data-dismiss="modal" @click="closeModal()">
                </div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="javascript:void(0)" @click="btnCapNhatClick">
                            <span class="icon one-reload1"></span> Cập nhật
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="info-row">
                        <div class="key w80">Nội dung</div>
                        <div class="value">
                            <input class="form-control" v-model="ten_nhom"></input>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<script>
export default {
    props: { modalID: null, loai: null,nhom:null },
    data() {
        return {
            ten_nhom: '',
            resolvePromise: null,
        }
    },
    methods: {
        closeModal() { this.$bvModal.hide(this.modalID); },
        show() {
            this.$bvModal.show(this.modalID);
            return new Promise((resolve) => {
                this.resolvePromise = resolve;
            });
        },
        async apiThemChuDeCap1(loai,nhom,noidung) {
            try {
                let param = {
                    pLoaiId:loai,
					pNhomChaId:nhom,
					pNoiDung:noidung
                }
                const rs = await this.$root.context.post('/web-gqkn/xu-ly-khieu-nai/them-chude-cap1', param);
                if (rs.data.length>0) {
                    return rs.data[0].NHOM_ID 
                }
                else {
                    this.$root.$toast.error('Có lỗi xảy ra')
                    return null;
                }
            } catch (error) {                
                return null
            }
        },
        async btnCapNhatClick() {
            try {
                let nhom_id=await this.apiThemChuDeCap1(this.loai,this.nhom,this.ten_nhom)
                this.resolvePromise({
                    nhom_id: nhom_id,
                    ten_nhom: this.ten_nhom

                });
            } catch (e) {
                this.$root.$toast.error(e);
            }
            this.closeModal()
        },
    }

}
</script>
