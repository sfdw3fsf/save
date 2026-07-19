<style scoped src="./InThuXacNhanCongNoSapXep.scss"></style>
<template>
  <b-modal
    id="popup-inthuxncn-sapxep"
    size="lg"
    hide-footer
    hide-header
    hide-header-close
    body-class="modal-body p-0"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Sắp xếp
        </div>
        <div
          class="close -ap icon-close"
          data-dismiss="modal"
          @click="$bvModal.hide('popup-inthuxncn-sapxep')"
        ></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#">
                    <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Sắp xếp
                </a>
            </li>
            <li>
                <div class="sort" style="border-left: 1px solid #BAE7FF;padding-left: 10px;">
                    <a href="#" class="btn btn-main">
                        <span class="-ap icon-chevron-thin-up"></span>
                    </a>
                    <a href="#" class="btn btn-main">
                        <span class="-ap icon-chevron-thin-down"></span>
                    </a>
                </div>
            </li>
            <li>
                <div class="select-custom" style="min-width: 200px;">
                    <select name="" id="" class="form-control">
                        <option value="">Địa chỉ CT</option>
                    </select>
                </div>
            </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
            <div class="table-content">
                <b-table class="table-result table-gachle" hover :items="items" :fields="fields" :tbody-tr-class="detailsClassFn">
                  <template #cell(is_delete)="data">
                    <a href="#" class="act red">
                      <span class="one-trash"></span>
                    </a>
                  </template>
                </b-table>
            </div>
        </div>
    </div>
    </div>
  </b-modal>
</template>

<script>
export default {
  name: 'InThuXacNhanCongNoSapXep',
  data () {
    return {
      fields: [
        {
          key: 'selected',
          sortable: false,
          label: '',
          class: 'w20 center'
        },
        {
          key: 'is_delete',
          sortable: false,
          label: '',
          class: 'w20 center'
        },
        {
          key: 'ten',
          sortable: false,
          label: 'Tên'
        },
        {
          key: 'sap_xep',
          sortable: false,
          label: 'Cách sắp xếp'
        }
      ],
      items: [
        {
          id: 100,
          selected: '',
          is_delete: true,
          ten: 'Đơn vị quản lý',
          sap_xep: 'Tăng'
        },
        {
          id: 101,
          selected: '',
          is_delete: true,
          ten: 'Loại Khách hàng',
          sap_xep: 'Tăng'
        },
        {
          id: 102,
          selected: '',
          is_delete: true,
          ten: 'Nhân viên quản lý',
          sap_xep: 'Tăng'
        }
      ]
    }
  },
  methods: {
    detailsClassFn (item, rowType) {
      // return 'fw6'
      return ''
    }
  }
}
</script>
