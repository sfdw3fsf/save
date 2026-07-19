<template>
<ejs-dialog
        :enableResize='true'
        :allowDragging='true'
        :visible='false'
        ref="dlgPreviewHTML"
        :header='"Hiển thị mẫu HTML"'
        showCloseIcon=true width='80%'
        :open="openDialog"
        :close="close"
        :isModal="true"
        height="80%"
    >
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title"><span class="icon one-notepad"></span>Chọn mẫu hóa đơn</div>
            <div class="close -ap icon-close hidden" data-dismiss="modal">
            </div>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <!-- <li>
                    <a href="#" @click.prevent="closeForm">
                        <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Đóng
                    </a>
                </li> -->
                <li>
                    <a href="#" @click.prevent="downloadHTML">
                        Tải mẫu HTML
                    </a>
                </li>
                <li :class="{hidden:!btnThoat}">
                    <a href="#" @click.prevent="terminateForm">
                        <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
                    </a>
                </li>
            </ul>
        </div>
        <div class="popup-body">
            <iframe :srcdoc="html" title="Mẫu HTML" width="100%" height="100%"></iframe>
            <!-- {{html}} -->
        </div>
    </div>
</ejs-dialog>
</template>
<style scoped>
.popup-box,.popup-body { height:100%; }
</style>
<script>
import fileDownload from 'js-file-download'
export default {
    name: 'popupPreviewHTML',
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            html: '',
            btnThoat: true,
        }
    },
    mounted() {
        if(this.dulieu.html) this.html = this.dulieu.html;
        if(this.html) {
            this.html = this.html.replaceAll("&lt;","<").replaceAll("&gt;",">").replaceAll("&amp;","&");
        }
    },
    computed: {
    },
    methods: {
        terminateForm() {
            this.$refs.dlgPreviewHTML.hide();
            // this.$emit('form-close',null);
        },
        openDialog () {
            if(this.dulieu.html) this.html = this.dulieu.html;
            if(this.html) {
                this.html = this.html.replaceAll("&lt;","<").replaceAll("&gt;",">").replaceAll("&amp;","&");
            }
            this.$refs.dlgPreviewHTML.show();
        },
        downloadHTML() {
            fileDownload(this.html, 'preview.html', 'text/html')
        },
        close() {
            this.terminateForm();
        },
    }
}
</script>
