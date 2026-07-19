<template>
  <b-modal
    ref="popupNhapText"
    size="md"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    centered
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> {{ headerTitle }}
        </div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li v-for="item in actions" :key="item.id" @click="onActionClick(item)"
              :class="{disabled: !item.active}" v-show="item.visible">
            <a style="cursor: pointer;" :class="{disabled_color: !item.active}" :ref="item.id">
              <span class="icon" :class="item.icon_class"></span> {{ item.name }}
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Nhập lý do</div>
          <div class="row">
            <div class="col-12">
              <div class="info-row">
                <input type="text" class="form-control" v-model="reason">
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>

export default {
  name: 'NhapText',
  data() {
    return {
      headerTitle: 'Nhập lý do vì sao không tái sử dụng được',
      actions: [
        {
          id: 'btnXacNhan',
          name: 'Do lý do khác',
          active: true,
          icon_class: 'one-save',
          visible: true
        },
        {
          id: 'btnHuyBo',
          name: 'Do bị hỏng',
          active: true,
          icon_class: 'one-cancel',
          visible: true
        },
        {
          id: 'btnHuyThat',
          name: 'Hủy',
          active: true,
          icon_class: 'one-cancel',
          visible: true
        }
      ],
      result: -1,
      reason: '',
      resolvePromise: null,
    }
  },
  methods: {
    handleShowModal() {
      this.result = -1;
      this.reason = '';
      this.resolvePromise = null;
    },

    hideModal() {
      this.$refs.popupNhapText.hide()
    },

    showModal() {
      return new Promise((resolve, reject) => {
        this.$refs.popupNhapText.show()

        this.resolvePromise = resolve;
        this.rejectPromise = reject;
      });
    },

    onActionClick(action) {
      if (action.id === 'btnXacNhan') {
        if (this.reason == '') {
          this.$root.$toast.error('Bạn chưa nhập lý do');
          return;
        }

        this.resolvePromise({
          result: 'ok',
          reason: this.reason
        });
      } else if (action.id === 'btnHuyBo') {
        this.resolvePromise({
          result: 'notok',
          reason: ''
        });
      } else {
        this.resolvePromise({
          result: 'abort',
          reason: ''
        });
      }

      this.hideModal();
    },
  }
}
</script>

<style scoped>
</style>
