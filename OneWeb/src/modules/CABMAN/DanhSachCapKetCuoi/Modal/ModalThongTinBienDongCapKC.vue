<template src="./ModalThongTinBienDongCapKC.html"></template>
<script>
export default {
  name: 'ModalThongTinBienDongCapKC',
  data: function () {
    return {
      modalId: '',
      dsBienDong: [],
      doi_tuong: 0
    }
  },
  methods: {
    closeModal: function () {},

    showModal: async function (args) {
      try {
        this.loading(true)
        this.doi_tuong = args.doi_tuong
        this.dsBienDong = await this.getThongTinBienDong(args)
      } catch (err) {
      } finally {
        this.loading(false)
      }

      this.$refs.modalBienDong.show()
    },

    async getThongTinBienDong(args) {
      let dsBD = []
      try {
        let response = await this.$root.context.post('/web-cabman/danhmuc/ds-cap-ketcuoi/thongtin-biendong', {
          bd_id: args.bd_id,
          doi_tuong: args.doi_tuong
        })

        if (response.error_code === 'BSS-00000000' && response.data != null) {
          dsBD = response.data
        } else {
          dsBD = []
        }
      } catch (err) {
        dsBD = []
      }

      return dsBD
    }
  }
}
</script>
