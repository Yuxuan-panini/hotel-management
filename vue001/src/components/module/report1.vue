<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/Main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>打印报表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row :gutter="20">
        <el-col :span="16">
          <div class="oo">片区报表</div>
        </el-col>
        <el-col :span="8"></el-col>
      </el-row>
      <el-table :data="hotelreportList" style="width: 100%">
        <el-table-column prop="hotel_id" label="酒店号" width="180"> </el-table-column>
        <el-table-column prop="area" label="地域" width="180"> </el-table-column>
        <el-table-column prop="address" label="地址" width="180"> </el-table-column>
        <el-table-column prop="income" label="收入" width="180"> </el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scope" >
            <el-button type="primary" @click="showdetaildialog(scope.row)" > 详细报表 </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog title="详细报表" :visible.sync="detailDialogVisible" width="62%">
      <el-table :data="reportList" style="width: 100%">
        <el-table-column prop="id" label="订单号" width="180"> </el-table-column>
        <el-table-column prop="Date_in" label="入住日期" width="180"> </el-table-column>
        <el-table-column prop="room_type" label="房型" width="180"> </el-table-column>
        <el-table-column prop="Stay_days" label="预定天数" width="180"> </el-table-column>
        <el-table-column prop="fee" label="房费" width="180"> </el-table-column>
      </el-table>
      <div slot="footer" >
        <el-button type="primary" @click="detailialogVisible = false">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      detailDialogVisible: false,
      hotelreportList: [],
      reportList: [],
      editform: {
        hotel_id: '',
        area: '',
        address: '',
        income: ''
      }
    }
  },
  created () {
    this.gethotelreportList()
  },
  methods: {
    async gethotelreportList () {
      const result = await this.$http.post('gethotelreportop', {'token': window.sessionStorage.getItem('token')})
      const res = result.data
      if (res.code === 0) {
        return this.$message.error('获取信息失败,请重新登陆')
      }
      this.hotelreportList = res.hotelreportList
      console.log(this.hotelreportList)
    },
    async showdetaildialog (hotelmsg) {
      const result = await this.$http.post('getorderreportop', {'token': window.sessionStorage.getItem('token'), 'hotel_id': hotelmsg.hotel_id})
      const res = result.data
      if (res.code === 0) {
        return this.$message.error('获取信息失败,请重新登陆')
      }
      this.reportList = res.orderlist
      this.detailDialogVisible = true
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
