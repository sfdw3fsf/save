<template src="./index.template.html"></template>
<script>
import FormAnhChup from "../../AnhChup";
export default {
    components: {
        FormAnhChup
    },
    data() {
        return {
            value: null,
            resolvePromise: null
        }
    },
    methods: {
        show() {
            this.$refs['ref-modal'].show()
            return new Promise(resolve => {
                this.resolvePromise = resolve
            })
        },
        hide() {
            this.resolvePromise(this.value)
            this.$refs['ref-modal'].hide()
        },
        onModalHidden() {
            this.value = null
            this.resolvePromise = null
        },
        async onModalShown() {

            let imageRelativeUrls = [];

            if (!!this.value) {
                imageRelativeUrls = this.value.split("|");
            }

            await this.$refs.formAnhChup.bindImages(imageRelativeUrls);
        },
        btnThemFile_Click: function () {
            this.$refs.formAnhChup.themFiles();
        },
        btnXoaFile_Click: function () {
            this.$refs.formAnhChup.xoaFiles();
        },
        formAnhChup_FilesChanged(args) {

            let s = "";

            if (args.length > 0) {

                for (let i = 0; i < args.length; i++) {

                    const file = args[i];
                    
                    if (!(file.relativeUrl == null || file.relativeUrl == undefined)) {

                        if (s != "") { 
                            s += "|";
                        }

                        s += file.relativeUrl;
                    }
                }
            }

            this.value = s;
        },
    }
}
</script>
