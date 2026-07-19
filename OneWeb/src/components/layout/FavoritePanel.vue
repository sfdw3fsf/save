<template>
  <div class="dropdown-menu">
    <div class="action-panel">
      <div class="action-item" title="refresh" @click="loadFavorite">
          <icon icon="ooui:reload" height="20px" width="20px"/>
      </div>
      <div class="action-item" title="Thêm trang hiện tại vào danh sách">
          <icon icon="uil:favorite" height="20px" width="20px"/>
      </div>
      <div class="action-item" @click="gotoSetting" title="Tùy chỉnh danh sách yêu thích">
          <icon icon="tabler:settings-cog" height="20px" width="20px"/>
      </div>
      <el-input placeholder="Tìm kiếm chức năng..." prefix-icon="el-icon-search">
          </el-input>
    </div>
    <div class="list-favorite">
      <div class="fav-item" v-for="item in favoriteObjects"
      :key="item.id"
      :title="item.full_name"
      @click="goTo(item.url)"
      >
      <div class="icon"><icon :icon="item.icon" width="20" height="20" /></div>
        <div class="name-item">{{item.name}}</div>
      </div>
    </div>
  </div>
</template>

<script>
import { Icon } from "@iconify/vue2";
import toolFunc from "@/modules/admin/category/SetupFavoriteFunctional/AutoAssignIcon.js"
  export default {
    components: {
      Icon
    },
    data() {
      return {
        favoriteItems: [],
        favoriteObjects: [],
        listMenu: [],
      }
    },
    mounted() {
      this.loadFavorite()
    },
    methods: {
      async loadFavorite() {
        let res = await this.$root.context.get('/quantri/user/khoitao_ungdung?p_idmodule=12')
        if(res.data.ds_yeuthich)
        this.favoriteItems = res.data.ds_yeuthich
        this.listMenu = res.data.ds_chucnang
        this.transformFavoriteIcon()
        // console.log(this.favoriteObjects);
      },
      transformFavoriteIcon() {
      this.favoriteObjects = []
      this.favoriteItems.forEach((item) => {
        this.listMenu.forEach((menu) => {
          if (menu.id_cn === item) {
            const newItem = {
              // ...menu,
              name: menu.displayName.length > 30 ? menu.displayName.slice(0, 30) + '...' : menu.displayName,
              full_name: menu.displayName,
              icon: toolFunc.autoAssignIcon(menu.displayName),
              url: menu.url,
              id: menu.id_cn,
              // selected: false,
              description: menu.mota_cn
            }
            this.favoriteObjects.push(newItem)
          }
        })
      })
    },
    goTo(url) {
      // const href = this.getHref(url);
      this.$router.push(url.replace("/#", ""));
    },
    gotoSetting() {
      this.$router.push({ name: "SetupFavoriteFunctional" });
    },
    getHref: function (args) {
      if (args == null) return "javascript:void(0)";
      else if(args.indexOf("tag=")>=0) return args.substring(0,args.indexOf("tag=")+4)+encodeURIComponent(args.substring(args.indexOf("tag=")+4));
      else return args;
    },
    },
  }
</script>

<style lang="scss" scoped>
  .dropdown-menu{
    background-color: #E6F0FD !important;
    font-size: 15px !important;
    overflow-y: auto !important;
    // padding-right: 20px !important;
    // padding-left: 10px !important;
    max-height: 300px !important;
  }
  .action-panel{
    display: flex;
    align-items: center;
    padding: 3px 10px;
    margin: 5px 10px;
    // margin-bottom: 10px;
    border-radius: 10px;
    background-color: #fff;
    .action-item{
      width: 25px;
      height: 25px;
      display: flex;
      justify-content: center;
      align-items: center;
      margin: 0 5px;
      color: #035FCC;
      cursor: pointer;
      &:hover{
        color: #044390;
      }
    }
  }
  .list-favorite{
    .fav-item{
      display: flex;
      align-items: center;
      padding: 5px 10px;
      margin: 5px 10px;
      // border-radius: 10px;
      // background-color: #fff;
      cursor: pointer;
      &:hover{
        border-bottom: 1px solid #035FCC;
      }
      .icon{
        width: 25px;
        height: 25px;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-right: 5px;
        color: #035FCC;
      }
      .name-item{
        font-size: 14px;
        font-weight: 500;
        color: #035FCC;
      }
    }
  }
</style>