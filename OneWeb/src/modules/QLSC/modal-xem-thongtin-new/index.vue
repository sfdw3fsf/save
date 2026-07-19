<template src="./index.template.html"></template>
<style>
    .modal-xem-thong-tin pre {
        font-size: 1rem;
        line-height: 1.5;
        font-family: "Roboto", Arial;
        height: 300px;
        color: #666;
        font-weight: 400;
        white-space: normal;
    }

    .modal-xem-thong-tin.e-dialog {
        border: transparent !important;
        box-shadow: none !important;
    }

    .modal-xem-thong-tin .e-dlg-header {
        font-weight: bold !important;
    }

    .modal-xem-thong-tin .e-dlg-content {
        padding: 0 !important;
    }
</style>
<script>
export default {
    data() {
        return {
            title: null,
            content: null
        }
    },
    methods: {
        show() {
            this.$refs.dialogObj.show()
        },
        setTitle(title) {
            this.title = title
        },
        setContent(content) {
            this.content = content
        },
        onModalShown() {
            this.$refs.dialogObj.ej2Instances.width = "500px"
            this.$refs.dialogObj.ej2Instances.height = "auto"
            this.$refs.dialogObj.ej2Instances.position = { X: "center", Y: "center" }
            

            window.xxx = this.$refs.dialogObj
        },
        onModalHidden() {
            this.title = null
            this.content = null
            this.$refs.dialogObj.ej2Instances.width = "100px"
            this.$refs.dialogObj.ej2Instances.height = "100px"
            this.$refs.dialogObj.ej2Instances.position = { X: "left", Y: "top" }
        },
        onClickBtnSaoChep() {
            let preTag = this.$refs['ref-textarea']
            let copyText = preTag.textContent
            let textArea = document.createElement('textarea')
            textArea.style.position = 'absolute'
            textArea.style.left = '-100%'
            textArea.textContent = copyText
            preTag.append(textArea)
            textArea.select()
            document.execCommand("copy")
            textArea.remove()
        }
    }
}
</script>
