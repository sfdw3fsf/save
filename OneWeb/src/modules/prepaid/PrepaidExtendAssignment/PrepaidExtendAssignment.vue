<template src="./PrepaidExtendAssignment.html"></template>

<script>
  import breadcrumb from "@/components/breadcrumb";
  import moment from 'moment'
  import DatePicker from 'vue2-datepicker'
  import 'vue2-datepicker/locale/vi.js'
  import 'vue2-datepicker/index.css'
  import API from './PrepaidExtendAssignment_API.js'

  export default {
    name: 'PrepaidExtendAssignment',
    components: {
      breadcrumb,
    },
    data() {
      return {
        currentTab: 1,
        tt_nd: {},
        header: {
          title: 'Phân giao thuê bao BRCĐ/DĐTS',
          list: [
            {
              name: 'Phân giao thuê bao BRCĐ/DĐTS',
              link: { 
                name: 'Ui.buttons',
                active: true 
              }
            }
          ],
        },
      }
    },
    computed: {},
    async created() {},
    async mounted() {
      this.loading(true)      
      try {
        this.tt_nd = await this.$root.token.getThongTinNguoiDung()
        this.tt_nd.machine = await this.$root.token.getMachine()        

        
      } catch (err) {
        console.log(err)
      } finally {
        this.loading(false)
      }
    },
    watch: {
      currentTab: function (newVal, oldval) {
        // this.tab_Detail_SelectedPageChanged({Page: newVal})
      },
    },
    methods: {      
      GetDataList: function (response) {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else if (response && response.data && response.data.error_code == 'BSS-00000204') {
          return []
        } else {
          this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
          return []
        }
      },
      GetData: function (response) {
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else if (response && response.data && response.data.error_code == 'BSS-00000204') {
          return ''
        } else {
          this.$root.$toast.error('Lỗi: ' + (response.data.message_detail ? response.data.message_detail : response.data.message))
          return ''
        }
      },      
      // Change current tab
      tab_Detail_SelectedPageChanged: async function (currentTab) {
        try {
          this.loading(true)
          this.currentTab = currentTab
        } catch (e) {
          console.log('tab_Detail_SelectedPageChanged',e)
        } finally {
          this.loading(false)
        }
      },
      
    },
  }
</script>

