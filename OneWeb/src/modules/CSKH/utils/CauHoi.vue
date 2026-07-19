<template>
  <div class="box-form">
    <div class="desc marb10">{{ dataSource.TENCAUHOI }}</div>
    <div class="list-check-ver">
      <div
        class="item marb5"
        v-for="cauHoiPhu in cauHoi.cauHoiPhu"
        :key="cauHoiPhu.RCH_ID"
      >
        <div class="check-action -color">
          <input
            type="checkbox"
            class="check"
            :name="cauHoi.CAUHOI_ID"
            v-model="cauHoiPhu.Checked"
          />
          <span class="name">{{ cauHoiPhu.NOIDUNG_TL }}</span>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    cauHoi: Object
  },
  data() {
    return {
      dataSource: {}
    };
  },
  created() {
    this.dataSource = this.cauHoi;
  },
  mounted() {},
  methods: {
    onCauHoiPhuChanged(index) {
      let backup = this.dataSource.cauHoiPhu;
      backup[index].Checked = !backup[index].Checked;
      this.dataSource.cauHoiPhu = [];
      this.dataSource.cauHoiPhu = backup;
    },
    checkIsDisplay(index) {
      return this.dataSource.cauHoiPhu[index].Checked;
    }
  }
};
</script>
