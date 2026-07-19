<template src="./index.html"></template>

<script>
export default {
  name: 'TabNghiepvuIDC',
  props: {
    khoaDuLieu: {
      type: Number,
      default: null,
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
  },
  created: async function () {
  },
  mounted: async function () {
    await this.getNghiepvuList();
  },
  methods: {
    async getNghiepvuList() {
      try {
        const response = await this.$root.context.get('/web-ecms/idc/thiet-bi-vat-ly/get-nghiep-vu-idc?thietBiId=727');
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
