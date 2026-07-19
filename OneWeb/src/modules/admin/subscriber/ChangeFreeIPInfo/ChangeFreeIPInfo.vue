<template>
  <b-modal id="popupTCTTTB"
  hide-footer
  hide-header
  hide-header-close
  size="xl"
  body-class="modal-body p-0">
      <div class="modal-dialog" style="width: 100%;max-width: unset;margin: unset;" v-if="1==2"><!-- Khoa theo yeu cau cua sep Hieu -->
          <div class="modal-content popup-box">
              <div class="popup-header">
                  <div class="title"><span class="icon one-notepad"></span> Thay đổi thông tin tính cước cho tài khoản băng thông rộng</div>
                  <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('popupTCTTTB')">
                  </div>
              </div>
              <div class="list-actions-top">
                  <ul class="list">
                      <li :class="{ '-disabled': !isEnableAddNew }">
                          <a @click="addNew">
                              <span class="icon one-file-plus"></span>Nhập mới
                          </a>
                      </li>
                      <li>
                          <a @click="submitChange">
                              <span class="icon one-sync"></span>Thay đổi
                          </a>
                      </li>
                  </ul>
              </div>
              <div class="popup-body">
                  <infor
                    v-on:loadSubcriber="loadSubcriber"
                    :isEnableAddNew="isEnableAddNew"></infor>
                  <fee></fee>
              </div>
          </div>
      </div>
  </b-modal>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  data () {
    return {
      isEnableAddNew: true
    }
  },
  props: {
  },
  mounted: function () {
    // this.$bvModal.show('popupChangeFreeIPInfo')
  },
  methods: {
    ...mapActions('changeFreeIPInfoCommon', [
      'clearSubcriberInfo',
      'submitChangeSubcriber'
    ]),
    addNew () {
      this.isEnableAddNew = false
      this.clearSubcriberInfo()
    },
    async submitChange () {
      try {
        const isConfirm = await this.$bvModal
          .msgBoxConfirm(
            `Bạn có thực sự muốn thay đổi thông tin tính cước không?`,
            {
              title: '',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Hủy'
            }
          )
        if (isConfirm) {
          this.$root.showLoading(true)
          await this.submitChangeSubcriber({
            userName: this.$auth.getUserName(),
            userId: this.$auth.getNguoiDungID()
          })
          this.$root.showLoading(false)
        }
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }
    },
    loadSubcriber: function () {
      this.isEnableAddNew = true
    },
    show() {
      this.$bvModal.show('popupChangeFreeIPInfo');
    }
  },
  components: {
    Infor: () => import('./parts/Infor.vue'),
    Fee: () => import('./parts/Fee.vue')
  }
}
</script>

<style scoped>
.modal{
    display: unset;
    background-color: rgba(0,0,0,0.5);
    overflow-y: scroll;
}

.-disabled{
    pointer-events: none;
    cursor: default;
}
.-disabled:hover{
    color: #0176FF;
    background: unset;
}

a:hover {
  cursor: pointer;
}
</style>
