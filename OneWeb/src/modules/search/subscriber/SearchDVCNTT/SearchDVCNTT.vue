<script>
import breadcrumb from '@/components/breadcrumb'
import api from './api'
import axios from 'axios'
export default {
  components: {
    breadcrumb,
  },
  name: 'SearchDVCNTT',

  data() {
    return {
      header: {
        title: 'TRA CỨU THÔNG TIN BACKEND DỊCH VỤ CNTT',
        list: [
          { name: 'Tra cứu', link: { name: 'Ui.cards' } },
          {
            name: 'Tra cứu thông tin backend DVCNTT',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      ds_tinh: {
        list: [],
        selected: {},
        value: ''
      },
      txtHDTB_ID: '',
      json_html: null,
    }
  },

  async mounted () {
    await this.load_ds_tinh()
  },

  methods: {
    async load_ds_tinh() {
      try {
        let rs = await api.lay_ds_tinh()
        if (rs.data.error_code == 'BSS-00000000') {
          return this.ds_tinh.list = rs.data.data
        }
      }
      catch (e) {
        console.log(e)
      }
    },

    async TraCuu() {
      try {
        let kq = await axios.post('/web-thicong/cnttdynamic/api', {
          hdtb_id: this.txtHDTB_ID,
          vtype: 'TRACUU',
          vphanvung_id: this.ds_tinh.value
        })

        let jsonObject = JSON.parse(kq.data.data.message)
        var html = "<ul>";
        for (var key in jsonObject) {
          html += "<li><strong>" + key + ":</strong> ";
          if (typeof jsonObject[key] === 'object') {
            html += "<div class='nested'>" + this.convertObjectToHtml(jsonObject[key]) + "</div>";
          } else {
            html += jsonObject[key];
          }
          html += "</li>";
        }
        html += "</ul>";

        return this.json_html = html
      }
      catch (e) {
        console.log(e)
      }
    },

    convertObjectToHtml(obj) {
      var result = "<ul>";
      for (var key in obj) {
        result += "<li><strong>" + key + ":</strong> ";
        if (typeof obj[key] === 'object') {
          result += "<div class='nested'>" + this.convertObjectToHtml(obj[key]) + "</div>";
        } else {
          result += obj[key];
        }
        result += "</li>";
      }
      result += "</ul>";
      return result;
    }
  }
}
</script>

<template src="./SearchDVCNTT.html">

</template>

<style scoped>

</style>
