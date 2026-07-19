<template>
  <select ref="select" id="select" class="w-100">
    <slot></slot>
  </select>
</template>

<script>
import api from '../api'
export default {
  name: 'Select2Ext',
  // props: ['options', 'value', 'url', 'selectedValue', 'dataValueField', 'dataTextField'],
  props:{
    options: {
      type: Array,
      default: () => [],
    },
    value: null,
    url: String,
    dataValueField:String,
    dataTextField:String,
  },
  data: function() {
    return {
      valueSelected:null,
      ajaxOptions: {
        textField: this.dataTextField,
        valueField:this.dataValueField,
        url: this.url,
        value:this.value,
        dataType: 'json',
        dataSelect:null,
        delay: 250,
        tags: true,
        headers: {"Authorization": 'Bearer ' + JSON.parse(localStorage.getItem('OneBSS-Token')).access_token},
        data: function(params) {
          let donvi_id = "";
          let root = $(this[0].__vue__)[0]
          params.page = params.page || 1
          if(root._props.value != 0 && root._props.value != null && root._props.value != undefined && params.page == 1){
            donvi_id = root._props.value;
          }
          if(params.term != "" && params.term != undefined){
            donvi_id = "";
          }
          return {
            ten_dv: params.term || "", // search term
            pageNum: params.page,
            pageSize:30,
            donvi_id:donvi_id,
          };
        },
        processResults: function(data, params) {
          let resultData = data.data.lstData
          const departments = []
          if (resultData != null) {
            resultData.forEach((item) => {
              let arrTextField = this.ajaxOptions.textField.split('+');
              let text = "";
              if (arrTextField.length > 1) {
                for (let i = 0; i < arrTextField.length; i++) {
                  if (i == arrTextField.length - 1)
                    text += item[arrTextField[i]];
                  else
                    text += item[arrTextField[i]] + " - ";
                }
              }
              if (arrTextField.length == 1)
                text = item[arrTextField[0]];
              departments.push({
                id: item[this.ajaxOptions.valueField],
                text: text,
              });
            });
          }
          return {
            results: departments,
            pagination: {
              more: (params.page * 30) < data.data.totalRecords,
            }
          };
        },
        cache: true
      }
    };
  },
  methods:{
    async getDsDonVi(donvi_id){
      console.log(donvi_id)
      let result = [];
      let response = await api.getDsDonVi(this.axios,{ten_dv:"",pageNum:1,pageSize:30,donvi_id:donvi_id})
      if (response.data.error_code === 'BSS-00000000' && response.data) {
        return response.data;
      }
      return null;
    },
  },
  mounted: function() {
    var vm = this
    $(this.$el)
      // init select2
      .select2({
        placeholder: "",
        ajax: this.ajaxOptions
      })
      .val(this.value)
      .trigger('change')
      // emit event on change.
      .on('change', function() {
        vm.$emit('input', this.value)
      })
  },
  computed:{

  },
  watch: {
    value: async function(value) {
      // console.log("value: ", value);
      if(value != "" && value != 0 && value != undefined){
        let result = await this.getDsDonVi(value);
        let text = "";
        let id = "";
        if(result != null){
          let data = result.data.lstData.find(x=>x.donvi_id == value);
          console.log(data);
          if(data != undefined){
            id = data.donvi_id
            text = data.ma_dv + " - " + data.ten_dv + " - " + data.ten_dv_cha;
          }
          else{
            id = value;
            text = "";
          }
        }
        //$("#select").empty().trigger("change");
        $(this.$el).append("<option value='"+id+"' data-select2-id='"+id+"'>"+text+"</option>")
      }

      $(this.$el).val(value).trigger('change');
      // update value
    },
    options: function(options) {

      // update options
      $(this.$el).empty().select2({
        data: options
      })
    },
    url: function(value) {
      this.ajaxOptions.url = this.url;
      $(this.$el).select2({
        ajax: this.ajaxOptions
      });
    },
  },
  destroyed: function() {
    $(this.$el).off().select2('destroy')
  }
}
</script>

<style scoped>

</style>
