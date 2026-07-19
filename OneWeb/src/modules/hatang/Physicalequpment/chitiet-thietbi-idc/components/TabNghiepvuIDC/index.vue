<template src="./index.html"></template>

<script>
export default {
  name: 'NghiepvuIDC',
  props: {
    currentItem: {
      type: Object,
      required: true
    },
  },
  data: function () {
    return {
      nghiepvuList: [
        
      ]
    };
  },
  components: {
  },
  computed: {
   
  },
  watch: {
    'currentItem.id': async function(newVal) {
      if (newVal && newVal !== 0) {
        await this.getNghiepvuList()
      } else {
        this.nghiepvuList = []
      }
    }
  },
  created: async function () {
  },
  mounted: async function () {    
  },
  methods: {
    async getNghiepvuList() {
      try {
        const response = await this.$root.context.get('/web-ecms/idc/thiet-bi-vat-ly/get-nghiep-vu-idc?thietBiId='+this.currentItem.id);
        this.nghiepvuList = response.data || [];
        this.nghiepvuList.forEach((item, index) => {
          item.STT = index + 1; // Thêm trường STT
        });
      } catch (error) {
        console.error('Error fetching nghiệp vụ list:', error);
      }
    },
    async refreshData() {
      await this.getNghiepvuList();
    },
  },
};
</script>
<style>

</style>
