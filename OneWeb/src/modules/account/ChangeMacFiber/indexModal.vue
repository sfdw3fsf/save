<template>
  <div>
    <div>
      <p class="text-center">
        <a @click.prevent="ShowPopUp" v-show="false" data-toggle="modal" class="btn btn-action">Show modal</a>
      </p>
    </div>
    <ChangeMacFiberPopUp ref="dialogchangeMacFiberPopUp"></ChangeMacFiberPopUp>
  </div>
</template>
<script>
import ChangeMacFiberPopUp from './PopUps/ChangeMacFiberPopUp'
export default {
  components: {
    ChangeMacFiberPopUp
  },
  name: 'ChangeMacFiber',
  created() {},
  mounted() {
    this.$refs.dialogchangeMacFiberPopUp.openDialog()
    // this.$bvModal.show('popupTCTTTB')
    // this.$root.$on('bv::modal::shown', (bvEvent, modalId) => {
    //   this.$refs.dialogchangeMacFiberPopUp.openDialog()
    // })
  },
  data() {
    return {
      header: {
        title: 'Tra cứu',
        list: [
          { name: ' Thay đổi địa chỉ MAC FIBER', link: { name: 'Ui.cards' } },
          {
            name: 'Thay đổi địa chỉ MAC FIBER',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      Loading: false,
      ReadOnly: false
    }
  },
  methods: {
    ShowPopUp: function () {
      this.$refs.dialogchangeMacFiberPopUp.openDialog()
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    },
    ShowAlert: function (msg) {
      this.$toast.error(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    }
  }
}
</script>
