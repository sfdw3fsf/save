<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :isModal="true" ref="DongBoEticket" :header="'Đồng bộ mã thuê bao'" showCloseIcon="true" width="75%"  target="#app .main-wrapper">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="DongBo_Click"> <span class="icon one-luong"></span>Đồng bộ</a>
        </li>
      </ul>
    </div>
    <div class="box-form">
      <div class="row">
        <div class="col-12 col-sm-6">
          <div class="info-row">
            <div class="key">ID:</div>
            <div class="value">
              <input v-model="data_item_select.tenantId" type="text" class="form-control" disabled />
            </div>
          </div>
        </div>
        <div class="col-12 col-sm-6">
          <div class="info-row">
            <div class="key">Mã số thuế:</div>
            <div class="value">
              <input v-model="data_item_select.maSoThue" type="text" class="form-control" disabled />
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-12 col-sm-6">
          <div class="info-row">
            <div class="key">Tên đơn vị:</div>
            <div class="value">
              <input v-model="data_item_select.tenDonVi" type="text" class="form-control" disabled />
            </div>
          </div>
        </div>
        <div class="col-12 col-sm-6">
          <div class="info-row">
            <div class="key">Mã TB trên hệ thống vé:</div>
            <div class="value">
              <input v-model="data_item_select.tenantCode" type="text" class="form-control" disabled />
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-12 col-sm-12">
          <div class="info-row">
            <div class="key">Mã TB trên hệ thống OneBSS:</div>
            <div class="value">
              <input v-model="data.ma_tb" type="text" class="form-control" disabled />
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="box-form">
      <div class="legend-title">Danh sách thông tin khách hàng</div>
      <div class="table-content" style="height: 290px;">
        <DataGrid class="table-result table-gachle" v-bind:columns="dsDonVi.headers" v-bind:dataSource="dsDonVi.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" @selectedRowChanged="selectedRowChanged"> </DataGrid>
      </div>
    </div>
  </ejs-dialog>
</template>

<script>
import api from '@/modules/contract/complete_profile/CompleteProfileITVAS/API/CompleteProfileAPI.js'
export default {
  data() {
    return {
      dsDonVi: {
        headers: [
          { fieldName: 'tenantId', headerText: 'ID', allowFiltering: true },
          { fieldName: 'maSoThue', headerText: 'Mã số thuế', allowFiltering: true },
          { fieldName: 'tenDonVi', headerText: 'Tên đơn vị', allowFiltering: true },
          { fieldName: 'tenantCode', headerText: 'mã thuê bao đơn vị', allowFiltering: true }
        ],
        list: []
      },
      hdtb_id: '',
      data: {},
      data_item_select: {}
    }
  },
  methods: {
    async LayDSKhachHang(hdtb_id) {
      try {
        this.loading(true)
        const result_call_dynamic = await api.cntt_dynamic_api(this.axios, { hdtb_id: hdtb_id, vdem: 0, vtype: 'LAY_DS_KHACHHANG' })
        if (result_call_dynamic.data.data.status == '01') {
          this.$toast.error(result_call_dynamic.data.data.message)
          return
        }
        {
          this.dsDonVi.list = result_call_dynamic.data.data.data
        }
      } catch (e) {
        this.$toast.error('Đã có lỗi xảy ra' + e.message)
      } finally {
        this.loading(false)
      }
    },
    async FormLoad(data) {
      try {
        this.loading(true)
        await this.LayDSKhachHang(data.hdtb_id)
        this.hdtb_id = data.hdtb_id
        this.data = data
        this.$refs.DongBoEticket.show()
      } catch (e) {
        this.$toast.error('Đã có lỗi xảy ra' + e.message)
      } finally {
        this.loading(false)
      }
    },
    DongBo_Click() {
      console.log('DongBo_Click')
    },
    selectedRowChanged(item) {
      console.log(item)
      this.data_item_select = item
    },
    async DongBo_Click() {
      try {
        this.loading(true)
        const result_call_dynamic = await api.cntt_dynamic_api(this.axios, { hdtb_id: this.hdtb_id, vdem: this.data_item_select.tenantId, vtype: 'DONGBO_MATB' })
        if (result_call_dynamic.data.data.status == '00') {
          this.LayDSKhachHang(this.hdtb_id)
          this.$toast.success(result_call_dynamic.data.data.message)
        } else {
          this.$toast.error(result_call_dynamic.data.data.message)
        }
      } catch (e) {
        this.$toast.error('Đã có lỗi xảy ra' + e.message)
      } finally {
        this.loading(false)
      }
    }
  }
}
</script>

<style></style>
