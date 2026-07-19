<template src="./index.template.html"></template>
<script>
export default {
    data() {
        return {
            fileImage: null
        }
    },
    watch: {
        fileImage(value) {

            let previewImage = this.$refs['preview-image']

            if (!!previewImage) {
                previewImage.src = !value ? null : URL.createObjectURL(value)
            }
        }
    },
    methods: {
        show() {
            return this.$refs['ref-modal'].show()
        },
        hide() {
            this.$refs['ref-modal'].hide()
        },
        setFileImage(fileBase64String) {

            if (!fileBase64String) {
                return
            }

            let url = `data:image/png;base64,${fileBase64String}`
            
            fetch(url)
                .then(res => res.blob())
                .then(blob => this.fileImage = new File([blob], 'image.png', {type : 'image/png'}))
        },
        onModalHidden() {
            this.fileImage = null
        },
        async onModalShown() {

        },
        onClickBtnXoaFile() {
            this.fileImage = null
        },
        onFileChange() {
            this.fileImage = this.$refs['ref-input-file'].$refs.input.files[0]
        },
        onClickBtnNapFile() {
            this.$refs['ref-input-file'].$el.querySelector('input').click()
        },
        onClickBtnLuuFile() {
            let a = document.createElement('a')
            a.href = URL.createObjectURL(this.fileImage)
            a.download = 'image.png'
            document.body.appendChild(a)
            a.click()
            document.body.removeChild(a)
        },
        onClickBtnXacNhan() {
            this.$emit('select', this.fileImage)
            this.hide()
        }
    }
}
</script>

