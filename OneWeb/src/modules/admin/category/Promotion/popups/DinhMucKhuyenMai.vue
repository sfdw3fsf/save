<template>
  <div>
    <vue-nav>
      <ul class="list">
        <li>
          <a href="#">
            <span class="icon one-save"></span> Ghi lại (F9)
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon one-trash"></span> Xóa
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="popup-body">
        <b-row>
            <b-col md="6">
                <vue-card label="Cước sử dụng" class="row">
                    <div class="title red fw6 marb5">Khuyến mại tặng tiền</div>
                    <vue-element labelWidth="60" class="col-md-6" label="Tối thiểu">
                        <input type="number" class="form-control tright" />
                    </vue-element>
                    <vue-element labelWidth="60" class="col-md-6" label="Tối đa">
                        <input type="number" class="form-control tright" />
                    </vue-element>
                    <div class="title red fw6 marb5">Khuyến mại theo tỷ lệ</div>
                     <vue-element labelWidth="60" class="col-md-6" label="Tối thiểu">
                        <input type="number" class="form-control tright" />
                    </vue-element>
                    <vue-element labelWidth="60" class="col-md-6" label="Tối đa">
                        <input type="number" class="form-control tright" />
                    </vue-element>
                    <div class="title red fw6 marb5">Khuyến mại theo tiền phải trả</div>
                     <vue-element labelWidth="60" class="col-md-6" label="Tối thiểu">
                        <input type="number" class="form-control tright" />
                    </vue-element>
                    <vue-element labelWidth="60" class="col-md-6" label="Tối đa">
                        <input type="number" class="form-control tright" />
                    </vue-element>
                </vue-card>
            </b-col>
            <b-col md="6">
                <vue-card  label="Cước thuê bao" class="row">
                    <div class="title red fw6 marb5">Khuyến mại tặng tiền</div>
                     <vue-element labelWidth="60" class="col-md-6" label="Tối thiểu">
                        <input type="number" class="form-control tright" />
                    </vue-element>
                    <vue-element labelWidth="60" class="col-md-6" label="Tối đa">
                        <input type="number" class="form-control tright" />
                    </vue-element>
                    <div class="title red fw6 marb5">Khuyến mại theo tỷ lệ</div>
                     <vue-element labelWidth="60" class="col-md-6" label="Tối thiểu">
                        <input type="number" class="form-control tright" />
                    </vue-element>
                    <vue-element labelWidth="60" class="col-md-6" label="Tối đa">
                        <input type="number" class="form-control tright" />
                    </vue-element>
                    <div class="title red fw6 marb5">Khuyến mại theo tiền phải trả</div>
                     <vue-element labelWidth="60" class="col-md-6" label="Tối thiểu">
                        <input type="number" class="form-control tright" />
                    </vue-element>
                    <vue-element labelWidth="60" class="col-md-6" label="Tối đa">
                        <input type="number" class="form-control tright" />
                    </vue-element>
                </vue-card>
            </b-col>
        </b-row>
    </div>
  </div>
</template>