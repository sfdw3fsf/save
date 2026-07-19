<template src="./index.template.html"></template>
<style scoped>
    pre {
        font-size: 1rem;
        line-height: 1.5;
        font-family: "Roboto", Arial;
        height: 300px;
        color: #666;
        font-weight: 400;
        white-space: normal;
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
            return this.$refs['ref-modal'].show()
        },
        hide() {
            this.$refs['ref-modal'].hide()
        },
        setTitle(title) {
            this.title = title
        },
        setContent(content) {
            this.content = content
        },
        onModalHidden() {
            this.title = null
            this.content = null
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
