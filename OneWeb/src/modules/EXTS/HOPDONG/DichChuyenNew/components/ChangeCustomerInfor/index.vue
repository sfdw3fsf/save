<template src="./index.template.html"></template>
<style>
    .change-customer-infor .page-content {
        overflow: visible !important;
        height: fit-content;
        background-color: white;
    }
    @media (min-width: 992px) {
    .modal .modal-huge {
      max-width: 90% !important;
      width: 90% !important;
      padding: 120px !important;
    }
    .modal-content.popup-box.change-customer-infor .breadcrumb-top { display: none;}
}
</style>
<script>
import ChangeCustomerInfor from '@/modules/contract/setup/ChangeCustomerInfor'

export default {
  components: {
    ChangeCustomerInfor
  },
  data () {
    return {
      data: {
        hdkh_id: null
      },
      tag: 0,
      modalId: 'modalChangeCustomerInfor'
    }
  },
  computed: {
    title: function () {
      if (this.data.tag == '0') {
        return 'Thay đổi thông tin khách hàng'
      } else {
        return 'Phê duyệt phiếu thay đổi thông tin khách hàng'
      }
    }
  },
  methods: {
    onModalShown () {
    },
    show () {
      return this.$refs['ref-modal'].show()
    },
    hide () {
      this.$refs['ref-modal'].hide()
    },
    onModalHidden () {
      this.$emit('hidden', this.data)
    },
    setData (data) {
      this.data = data
    }
  }
}
</script>
