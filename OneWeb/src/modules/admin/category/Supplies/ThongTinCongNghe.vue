<template>
  <div class="box-move-select-table">
    <div class="box-col">
      <div class="legend-title">Công nghệ đã gán</div>
      <DataGrid :columns="gridCongNgheDaGan.header" :dataSource="gridCongNgheDaGan.list" :showColumnCheckbox="true" :enable-paging-server="false" :allowPaging="true" :showFilter="true" ref="gridCongNgheDaGan" :enabledSelectFirstRow="false" panelDataHeight="250px" />
    </div>
    <div class="actions">
      <button class="btn" @click="boGan">
        <span class="fa fa-angle-right"></span>
      </button>
      <button class="btn" @click="tsbtnGan_Clicked">
        <span class="fa fa-angle-left"></span>
      </button>
    </div>
    <div class="box-col">
      <div class="legend-title">Công nghệ chưa gán</div>
      <DataGrid :columns="gridCongNgheChuaGan.header" :dataSource="gridCongNgheChuaGan.list" :showColumnCheckbox="true" :enable-paging-server="false" :allowPaging="true" :showFilter="true" ref="gridCongNgheChuaGan" :enabledSelectFirstRow="false" panelDataHeight="250px" />
    </div>
  </div>
</template>
<script>
import api from './SuppliesAPI'

export default {
  name: 'ThongTinCongNghe',
  props: {
    v_vattu_id: {
      type: [String, Number],
      required: true
    }
  },
  data() {
    return {
      gridCongNgheChuaGan: {
        header: [
          {
            fieldName: 'congnghe_id',
            headerText: 'Mã công nghệ',
            allowFiltering: true
          },
          {
            fieldName: 'congnghe',
            headerText: 'Tên công nghệ',
            allowFiltering: true
          }
        ],
        list: [],
        selected: [],
        value: null
      },
      gridCongNgheDaGan: {
        header: [
          {
            fieldName: 'congnghe_id',
            headerText: 'Mã công nghệ',
            allowFiltering: true
          },
          {
            fieldName: 'congnghe',
            headerText: 'Tên công nghệ',
            allowFiltering: true
          }
        ],
        list: [],
        selected: [],
        value: null
      }
    }
  },
  methods: {
    async tsbtnLayThongTin() {
      this.gridCongNgheChuaGan.list = await this.lay_ds_vattu_congnghe(0, this.v_vattu_id)
      this.gridCongNgheDaGan.list = await this.lay_ds_vattu_congnghe(1, this.v_vattu_id)
    },
    async clearData() {},
    async boGan() {
      let data = this.$refs.gridCongNgheDaGan.getSelectedRecords()
      if (data.length == 0) {
        this.$toast.warning('Vui lòng chọn công nghệ cần gỡ!')
        return
      }
      if (!confirm('Bạn có chắc chắn muốn gỡ công nghệ!')) return
      await this.gango_vattu_congnghe(0, data)
      await this.tsbtnLayThongTin()
    },
    async tsbtnGan_Clicked() {
      let data = this.$refs.gridCongNgheChuaGan.getSelectedRecords()
      if (data.length == 0) {
        this.$toast.warning('Vui lòng chọn công nghệ cần gán!')
        return
      }
      if (!confirm('Bạn có chắc chắn muốn gán công nghệ!')) return
      await this.gango_vattu_congnghe(1, data)
      await this.tsbtnLayThongTin()
    },
    async lay_ds_vattu_congnghe(kieu, vattu_id) {
      try {
        this.loading(true)
        let kq = await api.lay_ds_vattu_congnghe(this.axios, { vds_para: JSON.stringify({ KIEU: kieu, VATTU_ID: vattu_id }) })
        console.log(kq.data.data)
        if (kq.data.data && kq.data.data.length) {
          return kq.data.data
        } else {
          return []
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
      return []
    },
    async gango_vattu_congnghe(vkieu, dscn) {
      try {
        this.loading(true)

        let kq = await api.gango_vattu_congnghe(this.axios, {
          vkieu: vkieu,
          vds: JSON.stringify(
            dscn.map((e) => {
              return {
                PHANVUNG_ID: this.$root.token.getPhanVungID(),
                VATTU_ID: this.v_vattu_id,
                CONGNGHE_ID: e.congnghe_id
              }
            })
          )
        })
        if (kq.data.error_code == 'BSS-00000000') {
          this.$toast.success(`Thành công`)
        } else {
          this.$toast.error(`${kq.data.message}`)
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    }
  },
  async mounted() {
    await this.tsbtnLayThongTin()
  }
}
</script>
