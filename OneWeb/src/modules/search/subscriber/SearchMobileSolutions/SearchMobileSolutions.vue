<template>
  <div>
    <b-breadcrumb :items="header"></b-breadcrumb>


    <div>
      <b-nav tabs>
        <b-nav-item ><router-link class="nav-link" to="/search/SearchMobileSolutions/khach_hang" active-class="active">Khách hàng</router-link> </b-nav-item>
        <!-- <b-nav-item><router-link class="nav-link" to="/search/SearchMobileSolutions/lich_su_tac_dong" active-class="active">Lịch sử tác động</router-link></b-nav-item> -->
      </b-nav>
    </div>
  
    <div class="page-content">
      <div class="row">
        <div class="col-md-12">
          <router-view></router-view>
        </div>
      </div>

    </div>
    <footer class="footer">
      VNPT-IT &copy; 2021
    </footer>
  </div>
</template>
<script src="@/static/vendor/jquery/split.js"></script>
<style>
.e-grid .e-movableheader {
  overflow: scroll;
}
</style>
<script>
import breadcrumb from '@/components/breadcrumb'
import SearchMobileSolutionAPI from './api.js'

export default {
  props: ['thuebao_id', 'loaitb_id'],
  mounted() {
    //let getData = {};
  },
  destroyed() {},

  data() {
    return {
      header:[
          {
            text: 'Tra cứu',
            href: '#'
          },
          {
            text: 'Phát triển thuê bao',
            href: '#'
          },
          {
            text: 'Tra cứu dịch vụ Mobile Solutions',
            active: true
          }
        ],
      listbox: {
        dk_tracuu: {
          list: [],
          dk1: {
            value: '',
            isEnabled: true
          },
          dk2: {
            value: '',
            isEnabled: false
          },
          dk3: {
            value: '',
            isEnabled: false
          }
        }
      },

      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date()
    }
  },
  methods: {
    async tsbtnTimKiem_Click() {
      this.$toast.success('test')
    }
  }
}
</script>
<style>
.non-ative a {
  color: #d3d3d3 !important;
}

.breadcrumb {
  align-items: center;
  margin-bottom: 0px;
  padding: 10px;
  text-transform: uppercase;
}



</style>
