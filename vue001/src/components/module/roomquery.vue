<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/Main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>住宿查询</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="16">
            <div class="oo">酒店的房间状况</div>
        </el-col>
        <el-col :span="8">
            <el-button @click="getRoomList" type ="primary">查询</el-button>
        </el-col>
      </el-row>
      <el-table :data="roomList" style="width: 100%">
        <el-table-column prop="room_type" label="房型" width="180"> </el-table-column>
        <el-table-column prop="room_price" label="价格" width="180"> </el-table-column>
        <el-table-column prop="total" label="总房间数" width="180"> </el-table-column>
        <el-table-column prop="rest" label="剩余房间数" width="180"> </el-table-column>
    </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'roomquery',
  data () {
    return {
      roomList: [],
      total: 0
    }
  },
  methods: {
    async getRoomList () {
      const result = await this.$http.post('getroommsg', {'token': window.sessionStorage.getItem('token')})
      const res = result.data
      console.log(res)
      if (res.code === 0) {
        return this.$message.error('获取信息失败请重新登陆')
      }
      this.roomList = res.roomlist
      this.total = result.total
    }
  }
}

</script>

<style scoped>
  .el-breadcrumb {
      margin-bottom: 15px;
      font-size: 12px;
  }
  .el-card {
      box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15) !important;
  }
  .oo {
      font-size: 25px;
      text-align: left;
  }
  .el-table {
      margin-top: 15px;
  }
</style>
