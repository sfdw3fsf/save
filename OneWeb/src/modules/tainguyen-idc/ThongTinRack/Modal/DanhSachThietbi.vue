<template src="./DanhSachThietbi.html"></template>
<script>

import axios from 'axios';

export default {
  name: 'ModalDanhSachThietBi',
  props: {
    modalId: {
      type: Number,
      default: 0
    },
    soHuuRack: {
      type: Number,
      default: 1  // 1: IDC, 0: Khách hàng
    }
  },
  data: function () {
    return {
      thietbiItemList: [],
      soHuuRack: 1
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
                return value.getHieuLucText(this.data.HIEU_LUC);
              },
            },
          },
        };
      };
    },

    closeModal: function () {},

    showModal: async function (id, soHuuRack) {
      try {
        this.loading(true)
        this.thietbiItemList = await this.getThongTinThietBi(id)
        this.soHuuRack = soHuuRack;
      } catch (err) {
      } finally {
        this.loading(false)
      }

      this.$refs.modalDanhSachThietBi.show()
    },

    async getThongTinThietBi(id) {
      let result;
      try {
        let rs = await this.$root.context.post('/web-ecms/thongtin-rack/get-danhmuc-rack', {
          loaiDanhMuc: "THIETBI",
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
