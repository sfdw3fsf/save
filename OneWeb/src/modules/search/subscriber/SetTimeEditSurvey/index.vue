<template>
<div>    
    <SetTimeEditSurvey />
</div>
</template>
<script>
import SetTimeEditSurvey from './SetTimeEditSurvey.vue'
export default {
  name: 'SetTimeEditSurvey_Main',
  components: {
        SetTimeEditSurvey,
    },
  mounted () {
    console.log('mounted')
    this.$bvModal.show('popup-thietlap-tg-suaphieu-ksat')
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
