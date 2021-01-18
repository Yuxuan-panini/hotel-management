<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/Main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>退房查询</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="16">
          <el-input placeholder="请使用客户账号查询" v-model="consumer_id" clearable></el-input>
        </el-col>
        <el-col :span="8">
            <el-button @click="getorderList" type ="primary">查询</el-button>
        </el-col>
      </el-row>
      <el-table :data="orderList" style="width: 100%">
        <el-table-column prop="id" label="订单号" width="180"> </el-table-column>
        <el-table-column prop="Order_day" label="下单日期" width="180"> </el-table-column>
        <el-table-column prop="Date_in" label="入住日期" width="180"> </el-table-column>
        <el-table-column prop="Date_out" label="退房日期" width="180"> </el-table-column>
        <el-table-column prop="room_type" label="房型" width="180"> </el-table-column>
        <el-table-column prop="Stay_days" label="预定天数" width="180"> </el-table-column>
        <el-table-column prop="fee" label="房费" width="180"> </el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
            <el-button @click="confirmcheckout(scope.row.id)" type ="primary">确认退房</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'roomquery',
  data () {
    return {
      orderList: [],
      total: 0,
      consumer_id: ''
    }
  },
  methods: {
    async getorderList () {
      const result = await this.$http.post('getcheckoutmsg', {'token': window.sessionStorage.getItem('token'), 'consumer_id': this.consumer_id})
      const res = result.data
      if (res.code === 0) {
        return this.$message.error('获取信息失败请重新输入')
      }
      this.orderList = res.orderlist
      this.total = result.total
    },
    async confirmcheckout (orderid) {
      const result = await this.$http.post('checkout', {'token': window.sessionStorage.getItem('token'), 'order_id': orderid})
      const res = result.data
      if (res.code === 0) {
        return this.$message.error('退房失败请与管理员联系')
      }
      location.reload()
      return this.$message.success('退房成功')
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
