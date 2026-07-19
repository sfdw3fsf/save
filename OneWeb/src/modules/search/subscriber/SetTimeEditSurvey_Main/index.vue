<template>
<div>
    <li>
      <a v-b-modal.popup-thietlap-tg-suaphieu-ksat>
        <span class="icon  one-calendar-sync"></span> Thiết lập thời gian sửa phiếu khảo sát
      </a>
      <SetTimeEditSurvey />
    </li>
</div>
</template>
<script>
import SetTimeEditSurvey from '@/modules/search/subscriber/SetTimeEditSurvey/SetTimeEditSurvey'
export default {
  name: 'SetTimeEditSurvey_Main',
  components: {
        SetTimeEditSurvey,
    },
  mounted () {
    
  },
  destroyed () {},
  computed: {    
  },
  data () {
    return {
      
    }
  },
  methods: {
    
  },
   watch: {       
        
    }
}
</script>
