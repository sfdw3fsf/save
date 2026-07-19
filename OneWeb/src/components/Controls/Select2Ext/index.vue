<template>
  <Select2
    :options="options"
    :id="id"
    :placeholder="placeholder"
    :settings="currentSettings"
    :disabled="disabled"
    :required="required"
    v-bind:value="value"
    ref="select2"
    @change="change"
    @select="select"
    @closing="closing"
    @close="close"
    @opening="opening"
    @open="open"
    @clearing="clearing"
    @clear="clear"
  />
</template>
<script>
import Select2 from "v-select2-component";
export default {
  name: "Select2Ext",
  components: { Select2 },
  model: {
    event: "change",
    prop: "value",
  },
  props: {
    id: {
      type: String,
      default: "",
    },
    name: {
      type: String,
      default: "",
    },
    placeholder: {
      type: String,
      default: "",
    },
    options: {
      type: Array,
      default: () => [],
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    required: {
      type: Boolean,
      default: false,
    },
    settings: {
      type: Object,
      default: () => {},
    },
    value: null,
    dataSource: {
      type: Array,
      default: () => [],
    },
    dataTextField: String,
    dataValueField: String,
  },
  data: function(){
    return {
      isMounted: false,
      modalContent: null,
    };
  },
  mounted: function () {
    this.isMounted = true;
    this.initializeSelect2();
  },
  created: function() {
    this.modalContent = $(this.$el).parents('.modal-content[tabindex="-1"]:first');
    this.initializeSelect2();
  },
  updated: function () {
    this.modalContent = $(this.$el).parents('.modal-content[tabindex="-1"]:first');
    this.initializeSelect2();
  },
  destroyed: function() {
    this.destroySelect2();
  },
  watch: {
    dataSource: function (newValue) {
      this.setOption(this.getCustomOptions(newValue));
    },
    options(val) {
      this.setOption(val);
    },
    value(val) {
      this.setValue(val);
    },
  },
  computed: {
    currentSettings: function () {
      let s = this.settings;
      if (s == null || s == undefined) s = {};
      let defaultMatcher = $.fn.select2.defaults.defaults.matcher;
      function customMatcher(params, data) {
        if (
          !(
            params.term == undefined ||
            params.term == null ||
            params.term.length == 0
          )
        )
          params.term = params.term.trim();
        return defaultMatcher(params, data);
      }
      s.matcher = customMatcher;
      return s;
    },
  },
  methods: {
    initializeSelect2() {
      if (this.isMounted) {
        $.fn.select2.defaults.set("language", {
          errorLoading: () => "errorLoading",
          inputTooLong: (args) => "inputTooLong",
          inputTooShort: (args) => "inputTooShort",
          loadingMore: () => "loadingMore",
          maximumSelected: (args) => "maximumSelected",
          noResults: () => "(Không có lựa chọn)",
          searching: () => "searching",
        });

        this.setValue(this.value);
      }
    },
    destroySelect2() {
      this.isMounted = false;
      this.$nextTick(() => {
        try {
          $(this.$el).select2("destroy");
        } catch (e) { }
      });
    },
    getCustomOptions: function (fromData) {
      let data = [];
      if (fromData != null) {
        fromData.forEach((item) => {
          data.push({
            id: item[this.dataValueField],
            text: item[this.dataTextField],
          });
        });
      }
      return data;
    },
    setOption(val = []) {
      this.$refs.select2.setOption(val);
    },
    setValue(val) {
      this.$refs.select2.setValue(val);
    },
    change: function (args) {
      this.$emit("change", args);
    },
    select: function (args) {
      this.$emit("select", args);
    },
    closing: function (args) {
      this.$emit("closing", args);
    },
    close: function (args) {
      if (!(this.modalContent == null || this.modalContent.length === 0)) {
        this.modalContent.attr("tabindex", "-1");
      }
      this.$emit("close", args);
    },
    opening: function (args) {
      if (!(this.modalContent == null || this.modalContent.length === 0)) {
        this.modalContent.removeAttr("tabindex", "-1");
      }
      this.$emit("opening", args);
    },
    open: function (args) {
      this.$emit("open", args);
    },
    clearing: function (args) {
      this.$emit("clearing", args);
    },
    clear: function (args) {
      this.$emit("clear", args);
    },
  },
};
</script>
