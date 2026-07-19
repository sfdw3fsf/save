<!-- <template>
  <b-modal ref="checkPortRef" size="xl" dialog-class="dialog-content" body-class="body-content" header-class="popup-header" content-class="popup-box" hide-footer no-close-on-backdrop no-close-on-esc static>
    <CheckPort ref="checkPort" v-bind="checkPortDialog.input" />
  </b-modal>
</template>

<script>
import CheckPort from '@/modules/search/subscriber/CheckPort/index.vue'
export default {
  name: 'PopupCheckPort',
  components: {
    CheckPort
  },
  props: {
    selectedRow: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      checkPortDialog: {
        isVisiable: false,
        input: {
          vma_tb: null,
          vthuebao_id: null,
          vkieu: null
        }
      }
    }
  },
  methods: {
    async show() {
      document.querySelector('.dashboard-header').style.zIndex = -1
      this.checkPortDialog.input.vma_tb = this.selectedRow.ma_tb
      this.$refs.checkPortRef.show()
    },
    onClose() {
      document.querySelector('.dashboard-header').style.zIndex = 'initial'
      this.$refs.checkPortRef.hide()
    }
  }
}
</script>

<style>
.check-port-body {
  min-height: 85vh;
}
</style> -->
