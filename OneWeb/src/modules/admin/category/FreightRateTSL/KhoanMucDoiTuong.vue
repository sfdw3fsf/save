<template>
  <div>
    <div class="box-col box-form">
      <div class="info-row">
        <div class="key w90">Kiểu mức tiền</div>
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
            <div class="key w90">Tiền</div>
            <div class="value">
              <input type="text" class="form-control tright" value="0" />
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">VAT</div>
            <div class="value">
              <input type="text" class="form-control tright" value="0" />
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Tiền từ</div>
            <div class="value">
              <input type="text" class="form-control tright" value="0" />
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">VAT từ</div>
            <div class="value">
              <input type="text" class="form-control tright" value="0" />
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">Tiền đến</div>
            <div class="value">
              <input type="text" class="form-control tright" value="0" />
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="info-row">
            <div class="key w90">VAT đến</div>
            <div class="value">
              <input type="text" class="form-control tright" value="0" />
            </div>
          </div>
        </div>
      </div>
      <div class="box-move-select-table mart10">
        <div class="box-col">
          <KTableV3 
            :columns="columnsKhoanMucGan"
            :dataSources="dataSourcesKhoanMucGan"
            id="id"
            tableHeight="120px"
            :allowFilter="true"
            :allowCheckBox="true"/>
        </div>
        <div class="actions">
          <button class="btn">
            <span class="-ap icon-chevron-thin-right"></span>
          </button>
          <button class="btn">
            <span class="-ap icon-chevron-thin-left"></span>
          </button>
        </div>
        <div class="box-col">
          <KTableV3 
            :columns="columnsKhoanMucChuaGan"
            :dataSources="dataSourcesKhoanMucChuaGan"
            id="id"
            tableHeight="120px"
            :allowFilter="true"
            :allowCheckBox="true"/>
        </div>
      </div>
    </div>
    <div style="height: 4px"></div>
    <div class="box-col box-form">
      <div class="box-move-select-table fullh">
        <div class="box-col">
          <div class="legend-title">Đối tượng đã gán</div>
          <KTableV3 
            :columns="columnsDoiTuongGan"
            :dataSources="dataSourcesDoiTuongGan"
            id="id"
            tableHeight="calc(100% - 80px)"
            :allowFilter="true"
            :allowCheckBox="true"/>
        </div>
        <div class="actions">
          <button class="btn">
            <span class="-ap icon-chevron-thin-right"></span>
          </button>
          <button class="btn">
            <span class="-ap icon-chevron-thin-left"></span>
          </button>
        </div>
        <div class="box-col">
          <div class="legend-title">Đối tượng chưa gán</div>
          <KTableV3 
            :columns="columnsDoiTuongChuaGan"
            :dataSources="dataSourcesDoiTuongChuaGan"
            id="id"
            tableHeight="calc(100% - 80px)"
            :allowFilter="true"
            :allowCheckBox="true"/>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
export default {
    name:'KhoanMucDoiTuong',
    components:{
        KTableV3
    },
    data(){
        return {
            columnsKhoanMucGan:[
                {
                    field:'khoanmuc',
                    label:'Khoản mục',
                    allowFilter:true
                },
                {
                    field:'tien',
                    label:'Tiền',
                    allowFilter:true
                },
                {
                    field:'vat',
                    label:'VAT',
                    allowFilter:true
                },
                {
                    field:'tientu',
                    label:'Tiền từ',
                    allowFilter:true
                },
            ],
            dataSourcesKhoanMucGan:[],
            columnsKhoanMucChuaGan:[
                {
                    field:'khoanmuc',
                    label:'Khoản mục',
                    allowFilter:true
                }
            ],
            dataSourcesKhoanMucChuaGan:[],
            columnsDoiTuongGan:[
                {
                    field:'doituong',
                    label:'Đối tượng',
                    allowFilter:true
                }
            ],
            dataSourcesDoiTuongGan:[],
            columnsDoiTuongChuaGan:[
                {
                    field:'doituong',
                    label:'Đối tượng',
                    allowFilter:true
                }
            ],
            dataSourcesDoiTuongChuaGan:[],


        }
    }
}
</script>