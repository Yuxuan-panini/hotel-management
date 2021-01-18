<template>
  <el-container class="maincontainer">
    <el-header>
      <div class = 'headtext'>
        <img src="../../assets/hotel.png" alt="" height="50" width="50">
        <span class = 'text'>连锁酒店后台管理系统</span>
      </div>
        <el-button type="info" @click="logout">退出</el-button>
    </el-header>
    <el-container>
      <el-aside :width="isCollapse? '64px':' 200px'">
        <div class = "toggle-button" @click="toggleCollapse">|||</div>
        <el-menu :default-active="$route.path" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" background-color="rgb(24, 176, 196)"
         text-color="#fff" active-text-color="#ffd04b" unique-opened :collapse="isCollapse" router :collapse-transition = "false" >
        <el-submenu index="1" :disabled = 'ispermission1'>
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>前台权限</span>
          </template>
          <el-menu-item index="/roomquery">房间查询</el-menu-item>
          <el-menu-item index="/checkin">入住查询</el-menu-item>
          <el-menu-item index="/checkout">退房查询</el-menu-item>
      </el-submenu>
      <el-submenu index="2" :disabled = 'ispermission2'>
          <template slot="title">
            <i class="el-icon-user"></i>
            <span>经理权限</span>
          </template>
          <el-menu-item index="/roomop">房间操作</el-menu-item>
          <el-menu-item index="/hotelreportop">订单操作</el-menu-item>
      </el-submenu>
      <el-submenu index="3" :disabled = 'ispermission3'>
          <template slot="title">
            <i class="el-icon-user-solid"></i>
            <span slot="title">总经理权限</span>
          </template>
            <el-menu-item index="/addhotel">分店操作</el-menu-item>
            <el-menu-item index="/addstaff">职员操作</el-menu-item>
            <el-menu-item index="/staffhotel">工作分配</el-menu-item>
            <el-menu-item index="/printreportop">打印报表</el-menu-item>
      </el-submenu>
    </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'Main',
  data () {
    return {
      isCollapse: false,
      ispermission1: true,
      ispermission2: true,
      ispermission3: true
    }
  },
  created () {
    this.getpermission()
  },
  methods: {
    logout () {
      window.sessionStorage.clear()
      this.$router.push('/login')
    },
    toggleCollapse () {
      this.isCollapse = !this.isCollapse
    },
    getpermission () {
      let permission = window.sessionStorage.getItem('permission')
      if (permission === '1') {
        this.ispermission1 = false
      } else if (permission === '2') {
        this.ispermission2 = false
      } else if (permission === '3') {
        this.ispermission3 = false
      }
    }
  }
}
</script>

<style scoped>
  .el-header{
    background-color:rgb(24, 176, 196);
    display:flex;
    justify-content: space-between;
    padding-left: 0;
    align-items: center;
    color: white;
    font-size: 20px;
  }
  .headtext{
    display:flex;
    align-items: center;
  }
  .text{
    margin-left: 15px;
  }
  .el-aside{
    background-color: rgb(24, 176, 196);
  }
  .el-menu{
    border-right: none;
  }
  .el-main{
    background-color: azure;
  }
  .maincontainer{
    height: 100%;
  }
  .toggle-button{
    background-color:rgb(31, 217, 241);
    font-size: 10px;
    line-height: 24px;
    color: white;
    text-align: center;
  }
</style>
