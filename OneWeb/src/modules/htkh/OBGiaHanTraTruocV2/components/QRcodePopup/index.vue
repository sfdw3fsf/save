<template>
    <ejs-dialog
    :enableResize="true"
    :allowDragging="true"
    :visible="false"
    ref="dialogQRcode"
    :animationSettings="animationSettings"
    showCloseIcon="true"
    width="90%"
    height="90%"
    target=".main-wrapper"
    :close="closeDialog"
    header='<div class="main-title">VNPT QRcode</div>'
    >
    <div id="iframe-view" class="iframe-econtract" style="display: block;">
        <div class="iframe-content">
            <iframe id="econtract-wv-iframe" :src="link" style="width: 100%; height: 100%;"></iframe>
        </div>
    </div>

    </ejs-dialog>   
</template>
<script>
import Vue from 'vue';

import _ from 'lodash';

export default {
    name: 'QRCodeDialog',
    components: {
    },
    props: {
       
    },

    data() {
        return {
            animationSettings: { effect: "Zoom" },
            link: "",
        }
    },
    methods: {
        openDialog(link) {
            this.link = link;
            this.$refs.dialogQRcode.show();

        },
        closeDialog() {
            this.$refs.dialogQRcode.hide();
        },
    },
    computed: {

    }
}

</script>

<style scoped>
.iframe-econtract {
    top: 0;
    left: 0;
    width: 100%;
    height: 100vh;
    background: white;
}



.iframe-content {
    display: block;
    width: 100%;
    height: 100%;
}

#econtract-wv-iframe {
    border: 0;
    outline: none;
}
.w3-btn-sm {
    padding: 0.4rem 0.75rem;
}
.w3-blue, .w3-hover-blue:hover {
    color: rgb(255, 255, 255) !important;
    background-color: rgb(33, 150, 243) !important;
}
.w3-btn, .w3-button {
    display: inline-block;
    vertical-align: middle;
    color: inherit;
    background-color: inherit;
    text-align: center;
    cursor: pointer;
    border-width: initial;
    border-style: none;
    border-color: initial;
    border-image: initial;
    padding: 8px 16px;
    overflow: hidden;
    text-decoration: none;
    white-space: nowrap;
}
</style>
