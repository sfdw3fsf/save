<template src="./DanhSachThanhNguon.html"></template>
<script>

import axios from 'axios';

export default {
  name: 'ModalDanhSachThanhNguon',
  props: {
    modalId: {
      type: Number,
      default: 0
    }
  },
  data: function () {
    return {
      thanhNguonItemList: [],
    }
  },
  methods: {
    getHieuLucText: function (value) {
      return value == 1 ? "Hiệu lực" : "Không hiệu lực";
    },
    getColumnHieuLucTemplate: function (value) {
      return function () {
        return {
          template: {
            template: `<span>{{ getText() }}</span>`,
            methods: {
              getText: function () {
                return value.getHieuLucText(this.data.SUDUNG);
              },
            },
          },
        };
      };
    },

    closeModal: function () {},

    showModal: async function (id) {
      try {
        this.loading(true)
        this.thanhNguonItemList = await this.getThongTinThanhNguon(id)

      } catch (err) {
      } finally {
        this.loading(false)
      }

      this.$refs.modalDanhSachThanhNguon.show()
    },

    async getThongTinThanhNguon(id) {
      let result;
      try {
        let rs = await this.$root.context.post('/web-ecms/thongtin-rack/get-danhmuc-rack', {
          loaiDanhMuc: "THANHNGUON",
          thamSo1: id
        });
        result = rs.data || [];
      } catch (e) {
        if (e.response?.data?.message_detail) {
          this.$root.toastError(e.response.data.message_detail);
        }
      }
      return result;
    }
  }
}
</script>
