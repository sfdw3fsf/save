<template>
    <b-modal
        ref="popupDieuChinh"
        size="lg"
        body-class="p-0"
        hide-header
        hide-header-close
        hide-footer
    >
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span>Bạn muốn thay thế hay điều chỉnh hóa đơn?
                </div>
                <div class="close -ap icon-close" @click="hideModal"></div>
            </div>
            <div class="popup-body">
                <!-- <form ref="form" @submit.stop.prevent="handleSubmit">
                    <b-form-group
                        label="Name"
                        label-for="name-input"
                        invalid-feedback="Name is required"
                        :state="nameState"
                    >
                    
                    </b-form-group>
                </form> -->
                <div class="row">
                    <div class="col-sm-2 col-12"></div>
                    <div class="col-sm-4 col-12">
                        <input type="radio" id="dieu_chinh" value="1" v-model="picked" />
                        <label for="one">Điều chỉnh</label>
                    </div>
                    <div class="col-sm-2 col-12"></div>
                    <div class="col-sm-4 col-12">
                        <input type="radio" id="thay_the" value="2" v-model="picked" />
                        <label for="two">Thay thế</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-11 col-12"></div>
                    <div class="col-sm-1 col-12">
                        <button class="btn btn-primary" @click.prevent="clickOK">
                            <span>OK</span>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </b-modal>
</template>

<script>
export default {
    props: [],
    components: {
    },
    data() {
        return {
            picked: 2
        }
    },
    methods: {

        showModal() {
            this.$refs.popupDieuChinh.show();
        },
        hideModal() {
            this.$refs.popupDieuChinh.hide();
        },
        clickOK() {
            this.$emit('confirmPopup', this.picked);
            this.hideModal();
        }
    },

}
</script>