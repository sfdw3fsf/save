<template>
    <b-modal :id="modalId" size="lg" :data="data" :isStrictMode="isStrictMode" no-close-on-backdrop hide-footer
        hide-header hide-header-close body-class="modal-body p-0">
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span> {{ tieuDe }}
                </div>
                <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide(modalId)">
                </div>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="row">

                        <p style="cursor: pointer" v-for="item in data" :key="item.file_id"
                            v-if="data != undefined && data.length > 0">
                            <i class="icon one-file-attach"></i>
                            <a href="#" @click.prevent="downloadFile(item.url)">{{ item.ten_file }}</a>
                        </p>
                        <p v-if="data == undefined || data.length == 0">Không có file đính kèm</p>
                    </div>

                </div>
            </div>
        </div>
    </b-modal>
</template>
<script>
import api from './api/index';
export default {
    name: 'popupAttachFiles',
    props: ["modalId", "data", "isStrictMode"],
    data() {
        return {
            tieuDe: "File đính kèm",
            url_downfile: '#'
        }
    },
    methods: {
        hideModal() {
            this.$refs['popupAttachFiles'].hide()
        },
        onActionClick(action) {

        },
        downloadFile(url) {
            this.$root.loading(true);
            let params = {
                "list_file": url
            }
            api.download_file(this.axios, params)
                .then((response) => {
                    this.$root.loading(false);
                    if (response.data.data && response.data.data.length > 0) {
                        // console.log(response.data);
                        let file_url = response.data.data[0].file_url;
                        window.location.href = file_url;
                    }
                    else {
                        this.url_downfile = "#";
                        this.$root.loading(false);
                    }
                });
        }
    }
}
</script>
<style>
div#popupTCTTTB___BV_modal_outer_ {
    z-index: 10 !important;
    margin-top: 100px;
}

.dashboard-header {
    z-index: 9 !important;
}

a.disabled {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
}
</style>