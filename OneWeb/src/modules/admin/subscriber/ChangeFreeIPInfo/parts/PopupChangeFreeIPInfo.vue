<template>
    <div class="modal" id="popupTCTTTB" aria-hidden="true" v-if="1==2"><!-- Khoa theo yeu cau cua sep Hieu -->
        <div class="modal-dialog" style="width: 100%; max-width: 1220px;">
            <div class="modal-content popup-box">
                <div class="popup-header">
                    <div class="title"><span class="icon one-notepad"></span> Thay đổi thông tin tính cước cho tài khoản băng thông rộng</div>
                    <div class="close -ap icon-close" data-dismiss="modal" v-on:click="close">
                    </div>
                </div>
                <div class="list-actions-top">
                    <ul class="list">
                        <li :class="{ '-disabled': !isEnableAddNew }">
                            <a @click="addNew">
                                <span class="icon one-file-plus"></span>Nhập mới(F5)
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
    </div>
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
  methods: {
    ...mapActions('changeFreeIPInfoCommon', [
      'clearSubcriberInfo',
      'submitChangeSubcriber'
    ]),
    close: function () {
      this.$emit('closePopup')
    },
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
    }
  },
  components: {
    Infor: () => import('./Infor.vue'),
    Fee: () => import('./Fee.vue')
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
