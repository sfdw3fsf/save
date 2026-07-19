<template>
    <div class="m-4">
        <button @click="$bvModal.show('dictionary-config')" class="btn btn-success">
            Show modal Cấu hình bảng từ điển
        </button>
        <b-modal
            id="dictionary-config"
            modal-class="popup-box"
            header-class="popup-header"
            body-class="p-0"
            size="xl"
            hide-footer
            title="Cấu hình bảng từ điển">
            <DictionaryConfig />
        </b-modal>
    </div>
</template>
<script>
import DictionaryConfig from './popups/DictionaryConfig'
export default {
  components: {
    DictionaryConfig
  }
}
</script>
