<template>
  <div>
    <breadcrumb :header="header"/>
    <div class="list-actions-top">
      <ul class="list">
        <li title="Tra cứu">
          <a @click="tracuu()" style="cursor: pointer">
            <span class="icon one-search"></span>Tra cứu
          </a>
        </li>
      </ul>
    </div>
    <div class="page-content" style="top: 100px;">
      <vue-card label="Thông tin tra cứu">
        <b-row>
          <b-col class="col-sm-3 col-3">
            <div class="info-row">
              <div class="key w100">Mã giao dịch</div>
              <div class="value">
                <input  @keypress.enter="tracuu()" type="text" class="form-control" v-model="p_ma_gd"/>
              </div>
            </div>
          </b-col>
          <b-col class="col-sm-3 col-3">
            <div class="info-row">
              <div class="key w100">Mã thuê bao</div>
              <div class="value">
                <input @keypress.enter="tracuu()" type="text" class="form-control" v-model="p_ma_tb"/>
              </div>
            </div>
          </b-col>
          <b-col class="col-sm-3 col-3">
            <div class="info-row">
              <div class="key w100">Trạng thái</div>
              <div class="value">
                <select2 :options="trangthai.list" v-model="trangthai.value">
                </select2>
              </div>
            </div>
          </b-col>
        </b-row>
        <b-row>
          <b-col class="col-sm-3 col-3">
            <div class="info-row">
              <div class="key w100">Từ ngày</div>
              <vue-date format="DD/MM/YYYY"
                        v-model="p_tungay">
              </vue-date>
            </div>
          </b-col>
          <b-col class="col-sm-3 col-3">
            <div class="info-row">
              <div class="key w100">Đến ngày</div>
              <vue-date format="DD/MM/YYYY"
                        v-model="p_denngay">
              </vue-date>
            </div>
          </b-col>
        </b-row>
      </vue-card>

      <vue-card label="Danh sách quà tặng">
        <DataGrid
          ref="gridSplitter"
          :columns="dataGrid.columns"
          :dataSource="dataGrid.dataSource"
          :showColumnCheckbox="false"
          :enable-paging-server="false"
          @commandClick="commandClick"
          :allowPaging="true"
          :showFilter="true"
        />
      </vue-card>

    </div>
    <ejs-dialog :enableResize='true' :isModal="true" :allowDragging="true" :visible="false" ref="dlgDetailsInformation" :header="'Chi tiết đơn hàng'"
                showCloseIcon=true target=".main-wrapper" :closeOnEscape="true" width="40%" height="800" @close="closedlgDetailsInformation">
      <div>
        <DetailsInformation v-if="isShow.DetailsInformation" :detailData="dataGrid.value " @formClose="handleFormClose"/>
      </div>
    </ejs-dialog>
  </div>
</template>

<script>
  import breadcrumb from '@/components/breadcrumb'
  import api from './api'
  import KDataGrid from '@/components/kylq_components/KDataGrid'
  import { defineAsyncComponent } from '@vue/composition-api'

  export default {
    name: 'TraCuuQuaTang',
    components: {
      breadcrumb,
      KDataGrid,
      DetailsInformation: defineAsyncComponent(() => import('./popup/DetailsInformation.vue'))
    },
    data() {
      return {
        header: {
          title: 'Tra cứu danh sách quà tặng',
          list: [
            { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
            { name: 'Lắp đặt mới', link: { name: 'Ui.buttons' } }
          ]
        },
        trangthai: {
          value: 0,
          list: [
            {id: 0, text: "Chưa cập nhật"},
            {id: 1, text: "Đã cập nhật"}
          ]
        },
        daiVienThongId: null,
        danhSachDaiVienThong: [],
        dataGrid: {
          columns: [
            { fieldName: 'stt', headerText: 'STT', allowFiltering: true, allowSorting: false },
            { fieldName: 'ma_gd', headerText: 'Mã giao dịch', allowFiltering: true, allowSorting: false },
            { fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowFiltering: true, allowSorting: false },
            { fieldName: 'so_gt', headerText: 'Số giấy tờ', allowFiltering: true, allowSorting: false },
            { fieldName: 'sdt_kh', headerText: 'SĐT liên hệ', allowFiltering: true, allowSorting: false },
            { fieldName: 'diachi_kh', headerText: 'Địa chỉ', allowFiltering: true, allowSorting: false },
            { fieldName: 'ngay_cn', headerText: 'Ngày tạo', allowFiltering: true, allowSorting: false },
            { headerText: '',width: 100, allowHtml: true, textAlign: 'center',
              commands: [{ name: "chitiet", type: "chitiet", title: "Chi tiết",buttonOption: {content: 'Chi tiết', cssClass: 'e-default e-small'
              }}]}
          ],
          dataSource: [],
          value: {}
        },
        p_ma_gd: null,
        p_ma_tb: null,
        p_tungay: null,
        p_denngay: null,
        isShow: {
          DetailsInformation: false
        }
      }
    },
    methods: {
      async tracuu() {
        this.loading(true)
        try {
          if (this.p_tungay && this.p_denngay && this.p_tungay > this.p_denngay) {
            return this.$toast.error("Thời gian từ ngày phải nhỏ hơn đến ngày!");
          }

          const response = await api.sp_tracuu_tangqua_yagi_v2(this.axios, {
            'p_ma_gd': this.p_ma_gd,
            'p_ma_tb': this.p_ma_tb,
            'p_tungay': this.p_tungay,
            'p_denngay': this.p_denngay,
            'trangthai': parseInt(this.trangthai.value)
          })

          const data = response.data
          if (data.error_code === 'BSS-00000000' && data.error === '200') {

            if(response.data.data.length > 0) {
              this.dataGrid.dataSource = response.data.data.map((e, index) => {
                e.stt = index+1;
                return e;
              })
            }

          } else {
            this.dataGrid.dataSource = []
          }
        } catch (e) {
          this.$toast.error(e.message)
        } finally {
          this.loading(false)
        }
      },
      commandClick(args) {
        console.log(args.rowData)
        this.dataGrid.value = args.rowData
        this.isShow.DetailsInformation = true
        this.$refs.dlgDetailsInformation.show()
      },
      closedlgDetailsInformation() {
        this.isShow.DetailsInformation = false
      },
      handleFormClose() {
        this.$refs.dlgDetailsInformation.hide()
        this.tracuu();
      }
    }
  }
</script>


