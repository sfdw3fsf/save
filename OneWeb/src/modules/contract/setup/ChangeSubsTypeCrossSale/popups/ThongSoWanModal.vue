<template>
    <b-modal
    ref="popupThonSoWan"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Thông số Megawan -  Metronet
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>

      <ActionTop :actions="actions" @onActionClick="onActionClick" />

      <div class="popup-body">

        <div class="grid-stack-box">
          <div class="box-col box-form" style="width: calc(50% - 8px);">
            <div class="legend-title">Thông tin dịch vụ tỉnh Bán</div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Phân loại</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Phân tách TĐ</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Chủ quản</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Trang bị</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Tốc độ CIR</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">
                            <div class="check-action">
                                <input type="checkbox" class="check">
                                <span class="name">Tốc độ PIR</span>
                            </div>
                        </div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Loại kênh</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">
                            <div class="check-action">
                                <input type="checkbox" class="check">
                                <span class="name">Tốc độ NIX</span>
                            </div>
                        </div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Modem</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">
                            <div class="check-action">
                                <input type="checkbox" class="check">
                                <span class="name">Tốc độ ISP</span>
                            </div>
                        </div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Loại thiết bị</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Thời hạn</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Đối tượng</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Mức cước TB</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12 col-12">
                    <div class="info-row">
                        <div class="key w100">Đơn vị TC</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

          </div>
          <div class="gutter gutter-horizontal" style="width: 16px;"></div>
          <div class="box-col box-form" style="width: calc(50% - 8px);" >
            <div class="legend-title">Thông tin dịch vụ tỉnh Thi công</div>

            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Kiểu yêu cầu</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Mã TN</div>
                        <div class="value">
                            <input type="text" class="form-control tright bold"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Chủ quản</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Trang bị</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Tốc độ CIR</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Tốc độ PIR</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Loại kênh</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Tốc độ NIX</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Modem</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Tốc độ ISP</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Loại thiết bị</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Thời hạn</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Đối tượng</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="info-row">
                        <div class="key w100">Mức cước TB</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-12 col-12">
                    <div class="info-row">
                        <div class="key w100">Đơn vị TC</div>
                        <div class="value">
                            <div class="select-custom">
                                <select class="form-control">
                                    <option></option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
export default {
    name:'ThongSoWanModal',
    components:{
        ActionTop
    },
    data(){
        return {
            actions:[
                {
                    id:'capnhat',
                    name:'Cập nhật',
                    active: true,
                    icon_class:'one-save'
                }
            ],
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='capnhat'){

            }
        },
        showModal() {
            this.$refs["popupThonSoWan"].show()
        },
        hideModal() {
            this.$refs["popupThonSoWan"].hide()
        },
        handleShowModal(){

        }
    }
}
</script>