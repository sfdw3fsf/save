<template src='./SubsInfo.html'></template>
<style scoped src='./SubsInfo.scss'></style>
<script>
import gridview from '@/components/Shared/gridview.vue'
import select3 from '@/components/form/VueSelect.vue'
import input3 from '@/components/form/VueInput.vue'
import DatePicker from 'vue2-datepicker'
import { mapActions } from 'vuex'
import 'vue2-datepicker/index.css'
// import * as moment from 'moment'
// import LiquidateAPI from './LiquidateAPI'

export default {
  components: {
    gridview,
    select3,
    input3,
    DatePicker
  },
  name: 'SubsInfo',
  props: ['serviceType'], // serviceType = 2: co dinh, 3: MegawanMetronet, nguoc lai: giao dien chinh
  data: () => {
    return {
    }
  },
  computed: {
  },
  mounted () {
  },
  methods: {
    ...mapActions('receiveProfileCommon', [])
  }
}
</script>
