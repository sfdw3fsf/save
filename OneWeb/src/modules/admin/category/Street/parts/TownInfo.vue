<template>
    <div class="box-form">
        <div class="legend-title">Thông tin phố</div>
        <div class="info-row">
            <div class="key w80">Pho ID Neo</div>
            <div class="value">
                <input
                  ref="townIdNeo"
                  v-model="townIdNeo"
                  type="number"
                  class="form-control highlight">
            </div>
        </div>
        <div class="info-row">
            <div class="key w80">Tên phố</div>
            <div class="value">
                <input
                ref="townName"
                :class="{ 'error-input' : isErrorTownName }"
                v-model="townName"
                type="text"
                class="form-control">
            </div>
        </div>
        <div class="info-row">
              <div class="key w80 padt0">Nhóm phố</div>
              <div class="value">
                <ejs-dropdownlist
                  :allowFiltering="true"
                  v-model="selectedTownAreaId"
                  :filtering="onFilteringTown"
                  :placeholder="'Chọn nhóm phố'"
                  :dataSource="townAreas"
                  :fields="{ text: 'NAME', value: 'ID' }"/>
              </div>
        </div>
        <div class="info-row">
            <div class="key w80">Ghi chú</div>
            <div class="value">
                <input
                  ref="note"
                  :class="{ 'error-input' : isErrorNote }"
                  v-model="note"
                  type="text"
                  class="form-control">
            </div>
        </div>
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import {Predicate, Query} from '@syncfusion/ej2-data'

export default {
  data () {
    return {
      townIdNeo: null,
      townName: '',
      selectedTownAreaId: 0,
      note: '',
      isErrorTownName: false,
      isErrorNote: false
    }
  },
  computed: {
    ...mapGetters('streetCommon', [
      'getTownIdNeo',
      'getTownName',
      'getSelectedTownAreaId',
      'getNote',
      'getTownAreas',
      'getCurrentSearchWardId'
    ]),
    townAreas: function () {
      return this.$store.state.street.streetCommon.street.townAreas
    }
  },
  created: async function () {
    this.$root.showLoading(true)
    await this.setTownAreas()
    this.$root.showLoading(false)
  },
  methods: {
    ...mapActions('streetCommon', [
      'setTownAreas',
      'setSelectedTownArea',
      'setTownName',
      'setTownIdNeo',
      'setNote',
      'loadTownsByWard'
    ]),
    updateNote (e) {
      this.setNote(e.target.value)
    },
    onSelectedTownAreaId: async function (e) {
      if (!e) return
      try {
        this.loading(true)
        this.setSelectedTownArea(e)
      } catch (error) {
        console.log(error)
        this.$toast.error(error.toString())
      }

      this.loading(false)
    },
    onFilteringTown (e) {
      const query = new Query()
      // frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        const predicate = new Predicate('NAME', 'contains', e.text.trim(), true)
        query.where(predicate)
      }
      // pass the filter data source, filter query to updateData method.
      e.updateData(this.townAreas, query)
    }
  },
  watch: {
    getTownIdNeo: function (newValue, oldValue) {
      this.townIdNeo = newValue
    },
    getTownName: function (newValue, oldValue) {
      this.townName = newValue
    },
    getSelectedTownAreaId: function (newValue, oldValue) {
      this.selectedTownAreaId = newValue
    },
    getNote: function (newValue, oldValue) {
      this.note = newValue
    },
    townIdNeo: function (newValue, oldValue) {
      this.setTownIdNeo(newValue)
    },
    townName: function (newValue, oldValue) {
      this.setTownName(newValue)
    },
    note: function (newValue, oldValue) {
      this.setNote(newValue)
    },
    getTownAreas: function (newValue, oldValue) {
      if (newValue.length > 0) {
        this.selectedTownAreaId = newValue[0].ID
        this.setSelectedTownArea(this.selectedTownAreaId)
      }
    },
    selectedTownAreaId: function (newValue, oldValue) {
      this.onSelectedTownAreaId(newValue)
    }
  }
}
</script>

<style scoped>
.error-input {
  border: 1px solid #f57f6c;
  animation-name: bounce;
  animation-duration: 0.5s;
  animation-delay: 0.25s;
}

@keyframes bounce {
  0% {
    transform: translateX(0px);
    timing-function: ease-in;
  }
  37% {
    transform: translateX(5px);
    timing-function: ease-out;
  }
  55% {
    transform: translateX(-5px);
    timing-function: ease-in;
  }
  73% {
    transform: translateX(4px);
    timing-function: ease-out;
  }
  82% {
    transform: translateX(-4px);
    timing-function: ease-in;
  }
  91% {
    transform: translateX(2px);
    timing-function: ease-out;
  }
  96% {
    transform: translateX(-2px);
    timing-function: ease-in;
  }
  100% {
    transform: translateX(0px);
    timing-function: ease-in;
  }
}
</style>
