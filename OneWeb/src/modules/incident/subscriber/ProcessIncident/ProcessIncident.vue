<template src="./ProcessIncident.html"></template>
<style src="./ProcessIncident.scss"></style>
<script src="@/static/vendor/jquery/split.js"></script>

<script>
import breadcrumb from '@/components/breadcrumb'
import ActionTop from '@/components/kylq_components/ActionTop'

export default {
  name: 'ProcessIncident',
  components: {
    breadcrumb, ActionTop
  },
  data () {
    return {
      header: {
        title: 'Đối tác xã hội hóa xử lý báo hỏng',
        list: [
          {
            name: 'Lập hợp đồng',
            link: {name: 'Ui.cards'},
            active: false
          },
          {
            name: 'Lắp đặt mới',
            link: {name: 'Ui.buttons'},
            active: true
          }
        ]
      },
      actions: [
        {
          id: 'laythongtin',
          name: 'Lấy TT (F5)',
          active: true,
          icon_class: 'one-file-attach1'
        },
        {
          id: 'capnhat',
          name: 'Cập nhật (F9)',
          active: true,
          icon_class: 'one-reload1'
        },
        {
          id: 'hoanthanh',
          name: 'Hoàn thành',
          active: true,
          icon_class: 'one-file-check'
        },
        {
          id: 'layphieu',
          name: 'Lấy phiếu',
          active: true,
          icon_class: 'one-file-back'
        }
      ]
    }
  },
  mounted () {
    Split(['#boxLeft','#boxRight'], {
      sizes: [50, 50],
      gutterSize: 16,
      onDragEnd: function (sizes) {
      }
    })
  },
  computed: {
  },
  watch: {

  },
  methods: {
    onActionClick (action) {
      if (action.id === 'laythongtin') {
        this.setActiveActions(1)
      } else if (action.id === 'capnhat') {
        this.saveData()
      } else if (action.id === 'hoanthanh') {
        this.setActiveActions(2)
      } else if (action.id === 'layphieu') {
        if (this.donviId === -1) {
          return
        }
        this.deleteData()
      } else if (action.id === 'ganloaict') {
        this.openModalLCT()
      } else if (action.id === 'ganvitri') {
        this.openModalVT()
      }
    },
    setActiveActions (kieu) {
      this.actions.forEach((item) => {
        item.active = false
      })
      if (kieu === -1) { // bat dau
        this.actions[this.actions.findIndex(x => x.id === 'ghilai')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'huy')].active = true
      } else if (kieu === 0) { // bắt đầu
        this.actions[this.actions.findIndex(x => x.id === 'nhapmoi')].active = true
        this.clear()
      } else if (kieu === 1) { // thêm mới
        this.actions[this.actions.findIndex(x => x.id === 'ghilai')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'huy')].active = true
        this.isCreate = true
        this.clear()
      } else if (kieu === 2) { // Huỷ
        this.actions[this.actions.findIndex(x => x.id === 'nhapmoi')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'xoa')].active = true
        this.clear()
      } else if (kieu === 3) { // edit
        this.actions[this.actions.findIndex(x => x.id === 'nhapmoi')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'ghilai')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'huy')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'xoa')].active = true
      }
    }
  }
}
</script>

<style scoped>

</style>
