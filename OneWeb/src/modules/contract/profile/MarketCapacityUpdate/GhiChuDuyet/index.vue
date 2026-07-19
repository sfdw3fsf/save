<template src="./index.html"></template>
<script>
import API from '../API';
import Define from '../define';

export default {
  name: "GhiChuDuyet",
  props: {
    id: String,
    hoDan: Object,
    isDuyet: Boolean,
  },
  data: function () {
    return {
      key: 1,
      isShown: false,
      ghichuDuyet: "",
    }
  },
  methods: {
    onHiddenModal() {
      this.isShown = false
      this.key++
    },
    async onShownModal() {
      this.isShown = true
      if (this.isDuyet) this.ghichuDuyet = "Đạt yêu cầu"
      else this.ghichuDuyet = "Không đạt yêu cầu"
    },

    async btnXacNhan() {
      if (!this.isDuyet && this.ghichuDuyet.trim().length === 0) {
        this.$toast.warning("Ghi nhập ghi chú duyệt!\r\nLý do không đạt?")
        return
      }
      let ghichu = this.convertDate(new Date()) + ' | ' + this.$auth.getUserName() + ': ' + this.ghichuDuyet

      try {
        this.loading(true)
        await API.sp_duyet_hodan(this.axios, {
          sdkh_id: this.hoDan.sdkh_id,
          duyet: this.isDuyet ? Define.stt.dat : Define.stt.khong_dat
        }).then(res => {
          if (res.data.error === '200') {
            this.$toast.success("Cập nhật thành công")
            this.$emit('reload');
          }
        });
      } catch (e) {
        this.$toast.error("Có lỗi xảy ra trong quá trình cập nhật đối tượng")
      } finally {
        this.loading(false)
        this.$bvModal.hide(this.id)
      }
    },

    convertDate: function (now) {
      let date = new Date(now)
      let dd = String(date.getDate()).padStart(2, '0')
      let mm = String(date.getMonth() + 1).padStart(2, '0') //January is 0!
      let yyyy = date.getFullYear()
      let h = this.addZero(date.getHours())
      let m = this.addZero(date.getMinutes())
      let s = this.addZero(date.getSeconds());
      return dd + '/' + mm + '/' + yyyy + ' ' + h + ':' + m + ':' + s
    },

    addZero(i) {
      if (i < 10) {
        i = "0" + i;
      }
      return i;
    }
  }
}
</script>
