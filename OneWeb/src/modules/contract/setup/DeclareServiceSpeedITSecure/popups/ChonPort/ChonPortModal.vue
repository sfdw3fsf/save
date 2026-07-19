<template>
  <b-modal
    ref="popupChonPort"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Chọn Port
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick"/>

      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin tìm kiếm</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">Phường/Xã</div>
                <div class="value w20 padt7">
                  <div class="check-action">
                    <input type="checkbox" class="check" />
                    <span class="name"></span>
                  </div>
                </div>
                <div class="value">
                  <div class="select-custom">
                    <select name="" id="" class="form-control">
                      <option value=""></option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key">Đường/Phố</div>
                <div class="value w20 padt7">
                  <div class="check-action">
                    <input type="checkbox" class="check" />
                    <span class="name"></span>
                  </div>
                </div>
                <div class="value">
                  <div class="select-custom">
                    <select name="" id="" class="form-control">
                      <option value=""></option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key">Tổ thi công</div>
                <div class="value">
                  <div class="select-custom">
                    <select name="" id="" class="form-control">
                      <option value=""></option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key">Đơn vị QL</div>
                <div class="value">
                  <div class="select-custom">
                    <select name="" id="" class="form-control">
                      <option value=""></option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key">Tổ QL thiết bị</div>
                <div class="value">
                  <div class="select-custom">
                    <select name="" id="" class="form-control">
                      <option value=""></option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key">Trạng thái</div>
                <div class="value">
                  <div class="select-custom">
                    <select name="" id="" class="form-control">
                      <option value=""></option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">System</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control fw6 text highlight"
                      />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w60">IP</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control fw6 text highlight"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Rack</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control fw6 text highlight"
                      />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w60">Shelf</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control fw6 text highlight"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Slot</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control fw6 text highlight"
                      />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w60">Port</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control fw6 text highlight"
                      />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key text-main">Tìm port theo file</div>
                    <div class="value">
                      <div class="input-more-button">
                        <button class="btn">
                          <span class="-ap icon-more_horiz"></span>
                        </button>
                        <input
                          type="text"
                          class="form-control"
                          style="background-color: #f9e6e6"
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w60">Kết cuối</div>
                    <div class="value">
                      <input
                        type="text"
                        class="form-control"
                        style="background-color: #f9e6e6"
                      />
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">Ngõ/Ấp</div>
                <div class="value w20 padt7">
                  <div class="check-action">
                    <input type="checkbox" class="check" />
                    <span class="name"></span>
                  </div>
                </div>
                <div class="value">
                  <div class="select-custom">
                    <select name="" id="" class="form-control">
                      <option value=""></option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key">Khu</div>
                <div class="value w20 padt7">
                  <div class="check-action">
                    <input type="checkbox" class="check" />
                    <span class="name"></span>
                  </div>
                </div>
                <div class="value">
                  <div class="select-custom">
                    <select name="" id="" class="form-control">
                      <option value=""></option>
                    </select>
                  </div>
                </div>
              </div>

              <div class="row">
                <div class="col-sm-9 col-12">
                  <div class="info-row">
                    <div class="key">Trạm thiết bị</div>
                    <div class="value">
                      <div class="select-custom">
                        <select name="" id="" class="form-control">
                          <option value=""></option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-3 col-12">
                  <div class="info-row">
                    <div class="key w70">Công nghệ</div>
                    <div class="value">
                      <div class="select-custom">
                        <select name="" id="" class="form-control">
                          <option value=""></option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key">Dslam</div>
                <div class="value">
                  <div class="select-custom">
                    <select name="" id="" class="form-control">
                      <option value=""></option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key">D/U Link</div>
                <div class="value">
                  <div class="select-custom">
                    <select name="" id="" class="form-control">
                      <option value=""></option>
                    </select>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-9 col-12">
                  <div class="info-row">
                    <div class="key">Vpi/Svlan</div>
                    <div class="value">
                      <input type="text" class="form-control highlight" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-3 col-12">
                  <div class="info-row">
                    <div class="key w70">Vci/Cvlan</div>
                    <div class="value">
                      <input type="text" class="form-control highlight" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-9 col-12">
                  <div class="info-row">
                    <div class="key">Self</div>
                    <div class="value">
                      <input type="text" class="form-control highlight" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-3 col-12">
                  <div class="info-row">
                    <div class="key w70">OLT Slot</div>
                    <div class="value">
                      <input type="text" class="form-control highlight" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-9 col-12">
                  <div class="info-row">
                    <div class="key">OLT Port</div>
                    <div class="value">
                      <input type="text" class="form-control highlight" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-3 col-12">
                  <div class="info-row">
                    <div class="key w70">ONUID</div>
                    <div class="value">
                      <input type="text" class="form-control highlight" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-9 col-12">
                  <div class="info-row">
                    <div class="key">ADSL Port</div>
                    <div class="value">
                      <input type="text" class="form-control highlight" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-3 col-12">
                  <div class="info-row">
                    <div class="key w70">Onu Pass</div>
                    <div class="value">
                      <input type="text" class="form-control highlight" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-9 col-12">
                  <div class="info-row">
                    <div class="key">Spliter</div>
                    <div class="value w20 padt7">
                      <div class="check-action">
                        <input type="checkbox" class="check" />
                        <span class="name"></span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="select-custom">
                        <select name="" id="" class="form-control">
                          <option value=""></option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-3 col-12">
                  <div class="info-row">
                    <div class="key w70">Vị trí</div>
                    <div class="value w20 padt7">
                      <div class="check-action">
                        <input type="checkbox" class="check" />
                        <span class="name"></span>
                      </div>
                    </div>
                    <div class="value">
                      <div class="select-custom">
                        <select name="" id="" class="form-control">
                          <option value=""></option>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">
            <div class="pull-left">Danh sách Port Fix</div>
            <div class="pull-right normal">
              <div class="info-row">
                <div class="key nowrap text-main">
                  Tìm kiếm Vlan quy hoạch theo thuê bao
                </div>
                <div class="value">
                  <input
                    type="text"
                    class="form-control"
                    style="width: 170px; background-color: #f9e6e6"
                  />
                </div>
              </div>
            </div>
            <div class="clearfix"></div>
          </div>
          <KTableV3 :columns="columns"
            :dataSources="dataSources"
            @onSelectedRow="onSelectedRow"
            :rowSelectedId="rowSelectedId"
            :allowCheckBox="true"
            @dataCheckeds="onDataCheckeds"
            :allowFilter="true"
            id="id"/>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
export default {
    name:'ChonPortModal',
    components:{
        ActionTop,
        KTableV3
    },
    data(){
        return {
            actions:[
                {
                    id:'nhapmoi',
                    name:'Nhập mới',
                    active: true,
                    icon_class:'one-file-plus'
                },
                {
                    id:'lay_tt',
                    name:'Lấy TT (F5)',
                    active: true,
                    icon_class:'one-file-attach'
                },
                {
                    id:'chapnhan',
                    name:'Chấp nhận',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_check-circle-08'
                },
                {
                    id:'capnhat',
                    name:'Cập nhật',
                    active: true,
                    icon_class:'one-reload1'
                },
                {
                    id:'sinhpass',
                    name:'Sinh pass',
                    active: true,
                    icon_class:'one-circle-key'
                },
                {
                    id:'tbcungcap',
                    name:'TB cùng cáp',
                    active: true,
                    icon_class:'one-reload-mobile'
                },
            ],
            columns:[
                {
                    field: "card",
                    label: "Card",
                    allowFilter: true
                },
                {
                    field: "vitri_port",
                    label: "Vị trí Port",
                    allowFilter: true
                },
                {
                    field: "slot",
                    label: "Slot",
                    allowFilter: true
                },
                {
                    field: "port",
                    label: "Port",
                    allowFilter: true
                },
                {
                    field: "vpi",
                    label: "Vpi/Svlan",
                    allowFilter: true
                },
                {
                    field: "vci",
                    label: "Vci/Cvlan",
                    allowFilter: true
                },
                {
                    field: "rack",
                    label: "Rack",
                    allowFilter: true
                },
                {
                    field: "shelf",
                    label: "Shelf",
                    allowFilter: true
                },
                {
                    field: "adsl_port",
                    label: "ADSL Port",
                    allowFilter: true
                },
                {
                    field: "ngaygiao",
                    label: "Ngày giao",
                    allowFilter: true
                },
                {
                    field: "ont_id",
                    label: "ONT ID",
                    allowFilter: true
                },
                {
                    field: "port_mdf",
                    label: "Port MDF",
                    allowFilter: true
                },
                {
                    field: "trang_thai",
                    label: "Trạng thái",
                    allowFilter: true
                },
                {
                    field: "ds_thuebao",
                    label: "DS thuê bao",
                    allowFilter: true
                },
                {
                    field: "tb_cungport",
                    label: "TB cùng Port",
                    allowFilter: true
                },
                {
                    field: "onu_pass",
                    label: "Onu Pass",
                    allowFilter: true
                },
                {
                    field: "system",
                    label: "System",
                    allowFilter: true
                },
                {
                    field: "spliter",
                    label: "Spliter",
                    allowFilter: true
                },
                {
                    field: "ketcuoi_id",
                    label: "KETCUOI_ID",
                    allowFilter: true
                },
                {
                    field: "port_logic_id",
                    label: "Port Logic ID",
                    allowFilter: true
                },
                {
                    field: "port_vatly",
                    label: "Port vật lý ID",
                    allowFilter: true
                },

            ],
            dataSources:[],
            rowSelectedId:-1,

        }
    },
    methods:{
        onActionClick(action){

        },
        showModal() {
            this.$refs["popupChonPort"].show()
        },
        hideModal() {
            this.$refs["popupChonPort"].hide()
        },
        handleShowModal(){
            this.dataSources=[]
            this.rowSelectedId=-1
            setTimeout(()=>{
                this.initDuLieu()
            },500)
        },
        async initDuLieu(){

        },
        onSelectedRow(item){

        },
        onDataCheckeds(data){
            
        }
    }

}
</script>