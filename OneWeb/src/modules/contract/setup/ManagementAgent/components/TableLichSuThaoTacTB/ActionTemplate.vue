<template>
  <div>
    <a
      @click="onClickAction(actionTypes.RESET_PASWORD)"
      class="act f20 one-reload-next"
    ></a>
    <!-- <a
      @click="onClickAction(actionTypes.CHANGE_PASSWORD)"
      class="act f20 one-key-outline"
    ></a> -->
    <a
      @click="onClickAction(actionTypes.DS_THONG_TIN_DANG_KY)"
      class="act f20 nc-icon-outline ui-1_circle-add"
    ></a>
    <a
      @click="onClickAction(actionTypes.CHANGE_PERMISSION)"
      class="act f20 one-calendar-key"
    ></a>
  </div>
</template>

<script>
import EventBus from "@/utils/eventBus";
import { actionTypes } from "../../index";
export default {
  name: "ActionTemplate",
  data: function() {
    return {
      data: {},
      actionTypes: { ...actionTypes }
    };
  },
  methods: {
    onClickAction(type) {
      EventBus.$emit("dai-ly-action", {
        row: this.data,
        type: actionTypes[type]
      });
    }
  }
};
</script>
