<template src="./index.template.html"></template>
<script>
export default {
    props:{
        modalId: String
    },
    data() {
        return {
            title: null,
            content: null
        }
    },
    methods: {
        onModalHidden() {
            this.title = null
            this.content = null
        },
        setTitle(title) {
            this.title = title
        },
        setContent(content) {
            this.content = content
        },
        onClickBtnSaoChep() {
            
            this.$refs['ref-textarea'].select()
            document.execCommand('copy')

            if (window.getSelection) {
                window.getSelection().removeAllRanges()
            }
            else if (document.selection) {
                document.selection.empty()
            }
        }
    }
}
</script>
