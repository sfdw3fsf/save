export default {
    ShowWarning: function (msg) {
        this.$root.toastWarning(msg)
    },
    ShowError: function (msg) {
           this.$toast.error(msg)
    }
}